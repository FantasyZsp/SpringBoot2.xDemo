package xyz.mydev.mq.delay.rocketmq.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 注意，最好不要将此类本身作为 spring的bean，而由外部去注入此BEAN，防止序列化时出现递归导致StackOverFlow
 *
 * @author ZSP
 */
@Data
@ConfigurationProperties(prefix = "delay-queue.rocketmq")
public class DelayQueueRocketMqProperties implements InitializingBean {
  private String nameServer;
  private Map<String, ProducerProperties> producers = new HashMap<>();
  private Map<String, ConsumerProperties> consumers = new HashMap<>();

  @Override
  public void afterPropertiesSet() throws Exception {

    // 初始化属性
    producers.forEach((instanceName, producerProperty) -> {
      producerProperty.setNameServer(nameServer);
      if (StringUtils.isBlank(producerProperty.getInstanceName())) {
        producerProperty.setInstanceName(instanceName);
      }

    });

    consumers.forEach((instanceName, consumerProperty) -> {
      consumerProperty.setNameServer(nameServer);
      if (StringUtils.isBlank(consumerProperty.getInstanceName())) {
        consumerProperty.setInstanceName(instanceName);
      }
    });

  }
}
