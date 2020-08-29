package xyz.mydev.mq.delay.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import xyz.mydev.mq.delay.rocketmq.properties.ConsumerProperties;

/**
 * @author ZSP
 */
public class BusinessConsumerFactory {

  /**
   * 一般别用这个，只是多了 batchSize 和 ConsumeFromWhere
   * ConsumeFromWhere一般都是 CONSUME_FROM_LAST_OFFSET
   * batchSize一般都是1
   */
  public static DefaultMQPushConsumer getConsumerInstance(String nameServer,
                                                          String group,
                                                          String instanceName,
                                                          ConsumeFromWhere consumeFromWhere,
                                                          int batchSize,
                                                          int consumeThreadMin,
                                                          int consumeThreadMax,
                                                          String topic,
                                                          String tag,
                                                          MessageListenerConcurrently messageListenerConcurrently)
    throws MQClientException {

    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
    consumer.setConsumeMessageBatchMaxSize(batchSize);
    consumer.setInstanceName(instanceName);
    consumer.setConsumeThreadMin(consumeThreadMin);
    consumer.setConsumeThreadMax(consumeThreadMax);
    consumer.setNamesrvAddr(nameServer);
    consumer.setConsumeFromWhere(consumeFromWhere);
    consumer.subscribe(topic, tag);
    consumer.registerMessageListener(messageListenerConcurrently);
    return consumer;
  }

  public static DefaultMQPushConsumer getConsumerInstance(String nameServer,
                                                          String group,
                                                          String instanceName,

                                                          int consumeThreadMin,
                                                          int consumeThreadMax,
                                                          String topic,
                                                          String tag,
                                                          MessageListenerConcurrently listenerConcurrently)
    throws MQClientException {
    return
      getConsumerInstance(nameServer,
        group,
        instanceName,
        ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET,
        1,
        consumeThreadMin,
        consumeThreadMax,
        topic,
        tag,
        listenerConcurrently);
  }

  public static DefaultMQPushConsumer getConsumerInstance(ConsumerProperties properties,
                                                          MessageListenerConcurrently listenerConcurrently)
    throws MQClientException {
    return
      getConsumerInstance(properties.getNameServer(),
        properties.getGroup(),
        properties.getInstanceName(),
        ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET,
        properties.getBatchSize(),
        properties.getConsumeThreadMin(),
        properties.getConsumeThreadMax(),
        properties.getTopic(),
        properties.getTag(),
        listenerConcurrently);
  }
}
