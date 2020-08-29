package xyz.mydev.mq.delay.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;
import xyz.mydev.mq.delay.IdGenerator;
import xyz.mydev.mq.delay.constant.MqPlatform;
import xyz.mydev.mq.delay.repository.MqMessageErrorRecord;
import xyz.mydev.mq.delay.repository.MqMessageErrorRecordService;

import java.time.LocalDateTime;

/**
 * MqMessageErrorRecord失败记录赋值：
 * 1.对于RocketMQ，msgId总会在RocketMq的Message keys字段中。
 * 2.对于RocketMQ，mq_msg_mark_id可以在MessageExt中的msgId中拿到。但是有些场景可能获取不到，比如发送环节未获取到SendResult，那么就拿不到msgId，可能需要依赖于发送环节完毕后的状态判断或回查环节。
 * <p>
 * 发送失败情形：
 * 1.在业务调用方环节。此环节必须要保证消息内容在业务维度的正确性（因为aurora可能要中转消息，导致中转流程异常时，再去回滚业务的成本高昂）。
 * 2.aurora中转环节
 * （1）如果发送消息经历了事务消息流程，那么在整个投递流程，需要关注 半消息投递异常、修改消息表状态异常和回查异常三种失败导致的投递失败。将失败原因写入error_reason字段。
 * （2）如果是同步发送，需要关注发送异常，修改消息表异常两种情况。
 * <p>
 * 消费失败情形：
 * 1.在rpc消费环节，业务错误重试一定次数后出错。
 * 2.rpc超时等。
 *
 * @author ZSP
 */
@Slf4j
@Service
public class RocketMqMsgSendFailureHandler {
  private final MqMessageErrorRecordService mqMessageErrorRecordService;
  private final IdGenerator idGenerator;
  private final int mqPlatForm = MqPlatform.RocketMQ.getCode();

  public RocketMqMsgSendFailureHandler(MqMessageErrorRecordService mqMessageErrorRecordService,
                                       IdGenerator idGenerator) {
    this.mqMessageErrorRecordService = mqMessageErrorRecordService;
    this.idGenerator = idGenerator;
  }


  /**
   * 记录发送错误的消息
   */
  public void recordSendFailure(String topic,
                                String idInMsgDb,
                                int matched,
                                String mqPlatformMsgId,
                                String businessId,
                                int retryTimes,
                                int retryTimesWhenFailed,
                                String errorReason,
                                int errorCode) {

    MqMessageErrorRecord errorRecord = MqMessageErrorRecord.builder()
      .id(idGenerator.get())
      // 消息表总会把主键放在RocketMq的Message中。除非消息本身不存储在消息表。
      .msgId(idInMsgDb)
      .matched(matched)

      .channel(topic)

      .mqPlatform(mqPlatForm)
      .mqPlatformMsgId(mqPlatformMsgId)

      .businessId(businessId)
      .errorType(MqMessageErrorRecord.ERROR_TYPE_SEND)

      .retryTimes(retryTimes)
      .retryTimesWhenFailed(retryTimesWhenFailed)
      .errorReason(errorReason)
      .errorCode(errorCode)
      .createdAt(LocalDateTime.now())

      .build();

    log.error("send error record id and reason: {}, {}", errorRecord.getId(), errorReason);
    mqMessageErrorRecordService.save(errorRecord);


  }


  public void log(Message msg) {
    log.error("send error msg: {}", msg);
  }

}
