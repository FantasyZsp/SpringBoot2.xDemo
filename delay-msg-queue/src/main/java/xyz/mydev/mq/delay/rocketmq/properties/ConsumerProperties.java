package xyz.mydev.mq.delay.rocketmq.properties;

import lombok.Data;

/**
 * @author zhaosp
 */
@Data
public class ConsumerProperties {
  private String nameServer;
  private String topic;
  private String group;
  private String instanceName;
  private String tag;
  private int consumeThreadMin = 2;
  private int consumeThreadMax = 4;
  private int batchSize = 1;
}