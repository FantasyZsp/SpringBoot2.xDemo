package xyz.mydev.mq.delay.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import xyz.mydev.mq.delay.constant.MsgConsumeErrorCodeEnum;
import xyz.mydev.mq.delay.dto.IDelayTagMessage;
import xyz.mydev.mq.delay.rocketmq.ComposedMsg;
import xyz.mydev.mq.delay.rocketmq.RocketMqMsgConsumeFailureHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 针对单条（非批量消费）延时消息的监听器
 * 功能包含
 * 1. 调用业务消费者rpc接口
 * 2. 处理消费失败
 * TODO 消费失败后对主消息表的状态维护交给线程池，避免阻塞
 *
 * @author ZSP
 */
@Slf4j
public class SingleDelayMessageListenerConcurrently implements MessageListenerConcurrently {

  private final Function<MessageExt, ? extends IDelayTagMessage> msgMapper;
  private final Consumer<ComposedMsg> msgConsumer;
  private final BiFunction<ComposedMsg, Throwable, ConsumeConcurrentlyStatus> exceptionHandler;

  public SingleDelayMessageListenerConcurrently(Function<MessageExt, ? extends IDelayTagMessage> msgMapper,
                                                Consumer<ComposedMsg> msgConsumer,
                                                RocketMqMsgConsumeFailureHandler consumeFailureHandler) {
    this(msgMapper, msgConsumer, defaultExceptionHandler(consumeFailureHandler));
  }


  public SingleDelayMessageListenerConcurrently(Function<MessageExt, ? extends IDelayTagMessage> msgMapper,
                                                Consumer<ComposedMsg> msgConsumer,
                                                BiFunction<ComposedMsg, Throwable, ConsumeConcurrentlyStatus> exceptionHandler) {
    Objects.requireNonNull(msgConsumer);
    Objects.requireNonNull(exceptionHandler);
    this.msgConsumer = msgConsumer;
    this.msgMapper = msgMapper;
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
    MessageExt msg = msgList.get(0);
    String body = new String(msg.getBody(), StandardCharsets.UTF_8);
    log.info("收到消息, topic:{}, tags: {}, msgId: {}, keys: {}, consumeTimes: {}, body: [{}]", msg.getTopic(), msg.getTags(), msg.getMsgId(), msg.getKeys(), msg.getReconsumeTimes(), body);
    if (log.isDebugEnabled()) {
      log.debug("msg info [{}]", msg);
    }

    ComposedMsg composedMsg = null;
    try {
      IDelayTagMessage businessMsg = msgMapper.apply(msg);
      composedMsg = ComposedMsg.of(msg, businessMsg);
      // 消费rpc
      msgConsumer.accept(composedMsg);
    } catch (Throwable e) {
      log.error("consume msg error: msgKeys[{}] ,topic [{}], msgId[{}], consumeTimes {},  ex: [{}]", msg.getKeys(), msg.getTopic(), msg.getMsgId(), msg.getReconsumeTimes(), e.getMessage());
      // 处理异常，根据异常策略，返回是否看作是成功消费
      return handleException(composedMsg, e);
    }

    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
  }

  protected ConsumeConcurrentlyStatus handleException(ComposedMsg msg, Throwable e) {
    return exceptionHandler.apply(msg, e);
  }


  /**
   * 当为达到指定的消费次数时，选择稍后再消费
   * 否则，认为消费成功且计入消费失败记录，维护主表消息状态
   */
  private static BiFunction<ComposedMsg, Throwable, ConsumeConcurrentlyStatus> defaultExceptionHandler(RocketMqMsgConsumeFailureHandler consumeFailureHandler) {

    Objects.requireNonNull(consumeFailureHandler);

    return (composedMsg, ex) -> {

      MessageExt messageExt = composedMsg.getMessageExt();
      IDelayTagMessage businessMsg = composedMsg.getBusinessMsg();

      int maxReTryTimes = 3;
      int reconsumeTimes = messageExt.getReconsumeTimes();

      if (reconsumeTimes < maxReTryTimes) {
        log.warn("consume error but not reached maxRetryTimes, msg businessId: {}", businessMsg.getBusinessId());
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
      }


      // 如果超出了最大次数，进行日志记录和状态维护
      consumeFailureHandler.recordAndUpdateStatus(messageExt, ex.getClass().getSimpleName() + ": " + ex.getMessage(), MsgConsumeErrorCodeEnum.CONSUME_REACH_MAX_RETRY_TIMES.getCode(), businessMsg.getBusinessId());

      return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    };

  }


}