package xyz.mydev.mq.delay.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;
import xyz.mydev.mq.delay.IdGenerator;
import xyz.mydev.mq.delay.SimpleDelayService;
import xyz.mydev.mq.delay.constant.MqPlatform;
import xyz.mydev.mq.delay.repository.MqMessageDelay;
import xyz.mydev.mq.delay.repository.MqMessageErrorRecord;
import xyz.mydev.mq.delay.repository.MqMessageErrorRecordService;

import java.time.LocalDateTime;
import java.util.Objects;

import static xyz.mydev.mq.delay.repository.MqMessageErrorRecord.MATCHED;
import static xyz.mydev.mq.delay.repository.MqMessageErrorRecord.NOT_MATCHED;


/**
 * MqMessageErrorRecord失败记录赋值：
 * 1.对于RocketMQ，msgId总会在RocketMq的Message keys字段中。
 * 2.对于RocketMQ，mq_msg_mark_id可以在MessageExt中的msgId中拿到。但是有些场景可能获取不到，比如发送环节未获取到SendResult，那么就拿不到msgId，可能需要依赖于发送环节完毕后的状态判断或回查环节。
 * <p>
 * <p>
 * 消费失败情形：
 * 1.在rpc消费环节，业务错误重试一定次数后出错。
 * 2.rpc超时等。
 * <p>
 * 注意：
 * 消费失败的消息，可能生产流程并不在aurora项目中，或者说没有入消息库。所以msgId一般采取生产者约定的字段，如rocketmq平台使用msgKeys字段存储消息id。
 *
 * @author ZSP
 */
@Slf4j
@Service
public class RocketMqMsgConsumeFailureHandler {
  private final MqMessageErrorRecordService mqMessageErrorRecordService;
  private final SimpleDelayService simpleDelayService;
  private final IdGenerator idGenerator;
  private final int mqPlatForm = MqPlatform.RocketMQ.getCode();

  public RocketMqMsgConsumeFailureHandler(MqMessageErrorRecordService mqMessageErrorRecordService,
                                          SimpleDelayService simpleDelayService, IdGenerator idGenerator) {
    this.mqMessageErrorRecordService = mqMessageErrorRecordService;
    this.simpleDelayService = simpleDelayService;
    this.idGenerator = idGenerator;
  }


  /**
   * 记录消费错误的消息
   */
  public void record(String topic,
                     String idInMsgDb,
                     int matched,
                     String mqPlatformMsgId,
                     String businessId,
                     int retryTimes,
                     int retryTimesWhenFailed,
                     String errorReason,
                     int errorCode) {

    Objects.requireNonNull(topic);
    Objects.requireNonNull(idInMsgDb);
    Objects.requireNonNull(mqPlatformMsgId);

    MqMessageErrorRecord errorRecord = MqMessageErrorRecord.builder()
      .id(idGenerator.get())
      // 消息表总会把主键放在RocketMq的Message中。除非消息本身不存储在消息表。
      .msgId(idInMsgDb)
      .matched(matched)
      .channel(topic)

      .mqPlatform(mqPlatForm)
      .mqPlatformMsgId(mqPlatformMsgId)

      .businessId(businessId)
      .errorType(MqMessageErrorRecord.ERROR_TYPE_CONSUME)

      .retryTimes(retryTimes)
      .retryTimesWhenFailed(retryTimesWhenFailed)
      .errorReason(errorReason)
      .errorCode(errorCode)
      .createdAt(LocalDateTime.now())

      .build();

    log.warn("consume error record id and reason: {}, {}", errorRecord.getId(), errorReason);
    mqMessageErrorRecordService.save(errorRecord);
  }

  /**
   * 记录消费错误的消息
   */
  public void recordAndUpdateStatus(MessageExt msg, String errorReason, int errorCode, String businessId) {

    Objects.requireNonNull(msg);

    String msgIdInDb = msg.getKeys();

    boolean isMatched = simpleDelayService.existById(msgIdInDb);

    int matched = isMatched ? MATCHED : NOT_MATCHED;

    record(msg.getTopic(), msgIdInDb, matched, msg.getMsgId(), businessId, msg.getReconsumeTimes(),
      // 暂不实现
      0,
      errorReason, errorCode);

    if (isMatched) {
      if (simpleDelayService.updateStatusWhenConsumeError(msgIdInDb, MqMessageDelay.CONSUME_ERROR, errorReason)) {
        log.info("update status success, msgIdInDb {}", msgIdInDb);
      } else {
        log.warn("update status failed, msgIdInDb {}", msgIdInDb);
      }
    }
  }


  public void log(Message msg) {
    log.error("send error msg: {}", msg);
  }

}
