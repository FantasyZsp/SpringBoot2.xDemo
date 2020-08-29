package xyz.mydev.mq.delay.rocketmq.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.mq.delay.dto.SimpleDelayTagMessage;
import xyz.mydev.mq.delay.rocketmq.ComposedMsg;
import xyz.mydev.mq.delay.rocketmq.RocketMqMsgConsumeFailureHandler;
import xyz.mydev.mq.delay.rocketmq.properties.ConsumerProperties;
import xyz.mydev.mq.delay.rocketmq.properties.DelayQueueRocketMqProperties;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zhaosp
 */
@Slf4j
@Component
public class DelayMsgConsumerDemo implements InitializingBean {

  /**
   * 对应配置文件的key，一般都是以instanceName为key
   */
  @Getter
  private final static String PROPERTY_KEY = "delay-msg-consumer";

  private final ConsumerProperties consumerProperties;

  private final RocketMqMsgConsumeFailureHandler rocketMqMsgConsumeFailureHandler;

  public DelayMsgConsumerDemo(DelayQueueRocketMqProperties delayQueueRocketMqProperties,
                              RocketMqMsgConsumeFailureHandler rocketMqMsgConsumeFailureHandler) {
    this.consumerProperties = delayQueueRocketMqProperties.getConsumers().get(PROPERTY_KEY);
    Objects.requireNonNull(consumerProperties);
    this.rocketMqMsgConsumeFailureHandler = rocketMqMsgConsumeFailureHandler;
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("DelayMsgConsumer init...");
    DefaultMQPushConsumer consumerInstance =
      BusinessConsumerFactory.getConsumerInstance(consumerProperties,
        new SingleDelayMessageListenerConcurrently(msgMapper(), rpcConsumerProxy(), rocketMqMsgConsumeFailureHandler));

    consumerInstance.start();

  }

  public Consumer<ComposedMsg> rpcConsumerProxy() {
    return (ComposedMsg composedMsg) -> {
      // invoke rpc consume method
      log.info("msg received: [{}]", composedMsg);
      if (RandomUtils.nextBoolean()) {
        // 重试
        throw new RuntimeException("process failed, retry later");
      }
    };

  }

  public Function<MessageExt, SimpleDelayTagMessage> msgMapper() {
    return (MessageExt message) ->
    {
      Objects.requireNonNull(message);
      byte[] body = message.getBody();
      return JsonUtil.string2Obj(new String(body), SimpleDelayTagMessage.class);
    };
  }
}

