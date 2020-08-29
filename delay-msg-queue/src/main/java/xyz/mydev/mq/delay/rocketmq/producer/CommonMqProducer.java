package xyz.mydev.mq.delay.rocketmq.producer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import xyz.mydev.mq.delay.repository.MqMessageDelay;
import xyz.mydev.mq.delay.rocketmq.RocketMqConst;
import xyz.mydev.mq.delay.rocketmq.properties.DelayQueueRocketMqProperties;
import xyz.mydev.mq.delay.rocketmq.properties.ProducerProperties;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO 复用spring producer 实例
 * TODO 发送各种消息
 *
 * @author ZSP
 */
@Component
@Slf4j
public class CommonMqProducer implements InitializingBean {

  /**
   * 对应配置文件的key，一般都是以instanceName为key
   */
  @Getter
  private final static String PROPERTY_KEY = "common-producer";

  private final ProducerProperties producerProperties;

  @Getter
  private TransactionMQProducer producer;
  private final DelayMessageTransactionListenerImpl delayMessageTransactionListener;


  private CommonMqProducer(DelayMessageTransactionListenerImpl delayMessageTransactionListener,
                           DelayQueueRocketMqProperties delayQueueRocketMqProperties) {

    this.delayMessageTransactionListener = delayMessageTransactionListener;
    this.producerProperties = delayQueueRocketMqProperties.getProducers().get(PROPERTY_KEY);
    Objects.requireNonNull(producerProperties);
  }

  public TransactionSendResult sendDelayMessage(Message message, Object argument) {
    TransactionSendResult sendResult = null;
    try {
      sendResult = this.producer.sendMessageInTransaction(message, argument);
    } catch (Exception e) {
      log.error("delay meg send error: {} ,error info: [{}]", message, e);
    }

    log.info("delay msg send result: [{}] ", sendResult);

    return sendResult;
  }


  /**
   * @param argument 额外携带的参数，将会透传给 delayMessageTransactionListener，在此场景，Porter总是给业务id
   */
  public TransactionSendResult sendDelayMessage(MqMessageDelay messageDelay, Object argument) {
    log.info("sending delay msg: {} with args[{}] ", messageDelay, argument);
    String messageDelayId = messageDelay.getId();
    byte[] body = messageDelay.getMessage().getBytes(StandardCharsets.UTF_8);

    Message message = new Message(messageDelay.getChannel(), messageDelay.getTag(), messageDelayId, body);

    message.getProperties().put(RocketMqConst.MsgPropertiesKey.SYS_CONTEXT, messageDelay.getSystemContext());
    message.getProperties().put(RocketMqConst.MsgPropertiesKey.BUSINESS_ID, messageDelay.getBusinessId());
    return sendDelayMessage(message, argument);
  }


  @Override
  public void afterPropertiesSet() throws Exception {

    this.producer = new TransactionMQProducer(producerProperties.getGroup());
    this.producer.setInstanceName(producerProperties.getInstanceName());
    this.producer.setTransactionListener(delayMessageTransactionListener);
    int count = Math.min(Runtime.getRuntime().availableProcessors() / 2, 4);

    ExecutorService executorService = new ThreadPoolExecutor(count, count + 1, 30, TimeUnit.MINUTES,
      new ArrayBlockingQueue<>(2000), new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(producerProperties.getGroup() + "-check-thread");
        return thread;
      }
    });
    this.producer.setExecutorService(executorService);
    this.producer.setNamesrvAddr(producerProperties.getNameServer());

    startProducer();
  }

  private synchronized void startProducer() throws MQClientException {
    producer.start();
  }

}
