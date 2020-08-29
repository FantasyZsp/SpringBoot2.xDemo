package xyz.mydev.mq.delay.repository;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import xyz.mydev.mq.delay.dto.IDelayTagMessage;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
@Data
@Accessors(chain = true)
public class MqMessageDelay implements IDelayTagMessage {
  public static final int CONSUME_ERROR = -2;
  public static final int SEND_ERROR = -1;

  public static final int CREATED = 0;
  public static final int SENT = 1;
  public static final int CONSUMED = 2;

  private String id;
  private String channel;
  private String tag;
  /**
   * mq平台 1 RocketMQ 2 RabbitMQ 3 Kafka 4其他
   */
  private Integer mqPlatform;
  /**
   * 中间件提供的消息标识，如rocketmq中的msgId
   */
  private String mqPlatformMsgId;

  private String businessId;
  /**
   * json message
   */
  private String message;
  /**
   * 生效时间
   */
  private LocalDateTime time;

  /**
   * systemContext
   */
  private String systemContext;
  /**
   * 0: created default, 1: sent, 2: consumed
   * -1: send error  -2: consume error
   */
  private Integer status;

  /**
   * 当一次补偿失败后，具体的重试次数累加到retryTimesTotal，并在{@link MqMessageErrorRecord}中{@link MqMessageErrorRecord#retryTimes}记录此次补偿时重试次数。
   * 基于最终失败的状态，如果后续手动继续进行补偿，retryTimesWhenFailed将开始计数。
   */
  private Integer retryTimesWhenFailed;

  /**
   * 一共重试的次数
   */
  private Integer totalRetryTimes;

  private String mark;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Override
  public String getTag() {
    return tag;
  }


  /**
   * 业务方法扩展
   * 至少是已成功发送
   */
  @JsonIgnore
  public boolean atLeastSentSuccess() {
    return this.status != null && status >= 1;
  }

  @JsonIgnore
  public boolean hasSent() {
    return this.status != null && status > 1;
  }

}
