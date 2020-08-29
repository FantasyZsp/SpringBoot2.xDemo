package xyz.mydev.mq.delay.constant;

import lombok.Getter;

/**
 * @author ZSP
 */
@Getter
public enum MqPlatform {
  // mq平台 1 RocketMQ 2 RabbitMQ 3 Kafka 4其他
  RocketMQ(1),
  RabbitMQ(2),
  Kafka(3);

  private int code;

  MqPlatform(int code) {
    this.code = code;
  }
}
