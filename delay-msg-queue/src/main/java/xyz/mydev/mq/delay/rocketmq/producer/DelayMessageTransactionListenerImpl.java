package xyz.mydev.mq.delay.rocketmq.producer;

import com.google.common.util.concurrent.AtomicLongMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import xyz.mydev.mq.delay.SimpleDelayService;
import xyz.mydev.mq.delay.constant.MsgSendErrorCodeEnum;
import xyz.mydev.mq.delay.repository.MqMessageDelay;
import xyz.mydev.mq.delay.repository.MqMessageErrorRecord;
import xyz.mydev.mq.delay.rocketmq.RocketMqConst;
import xyz.mydev.mq.delay.rocketmq.RocketMqMsgSendFailureHandler;

/**
 * 延时消息的发送事务处理
 *
 * @author zhaosp
 */
@Component
@Slf4j
public class DelayMessageTransactionListenerImpl implements TransactionListener {

  private final SimpleDelayService simpleDelayService;
  private final RocketMqMsgSendFailureHandler rocketMqMsgSendFailureHandler;

  /**
   * id: check times
   */
  private static final AtomicLongMap<String> MSG_CHECK_TIMES_MAP = AtomicLongMap.create();

  public DelayMessageTransactionListenerImpl(SimpleDelayService simpleDelayService,
                                             RocketMqMsgSendFailureHandler rocketMqMsgSendFailureHandler) {
    this.simpleDelayService = simpleDelayService;
    this.rocketMqMsgSendFailureHandler = rocketMqMsgSendFailureHandler;
  }


  /**
   * 投递消息，当本地消息状态不是0时，默认其已经投递过，将会回滚消息。
   *
   * <p>
   * 答疑记录：
   * 1. 对于redis延时队列，可能因为故障重启时重新消费（投递消息），在此方法中，会将已经投递的消息截断不进行后续commit。
   * 2. 对于redis延时队列来说，只要执行过此方法，就可以认为消费成功了。因为后续的check流程可以与redis延时队列无关。
   * 3.如果后续有 手动/定时故障 重新投递的需求，需要另行实现，禁止重用此实现。因为对msgDb状态的校验会导致无法重新投递。
   *
   * @param businessId 透传业务id
   */
  @Override
  public LocalTransactionState executeLocalTransaction(Message msg, Object businessId) {
    log.info("executing LocalTransaction, msgKey [{}] topic [{}] , businessId [{}]------------", msg.getKeys(), msg.getTopic(), businessId);
    boolean success = simpleDelayService.updateToSent(msg.getKeys());

    if (!success) {
      if (log.isDebugEnabled()) {
        log.debug("msg [{}] update status error, rollback message[{}] ", msg.getKeys(), new String(msg.getBody()));
      }

      recordSendFailureAndRemoveCacheQuietly(msg.getTopic(), msg.getKeys(),
        MqMessageErrorRecord.MATCHED,
        null,
        msg.getProperty(RocketMqConst.MsgPropertiesKey.BUSINESS_ID),
        0, 0,
        MsgSendErrorCodeEnum.EXECUTE_LOCAL_TX.getDescription(),
        MsgSendErrorCodeEnum.EXECUTE_LOCAL_TX.getCode());
      return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    log.info("submit msg: msgKey [{}] topic [{}]------------", msg.getKeys(), msg.getTopic());
    return LocalTransactionState.COMMIT_MESSAGE;
  }


  /**
   * 检查消息是否成功
   * 两种情况(消息库中不一定有消息)
   * 1. 实际上消息状态已经被置为已发送，只是确认消息晚到达或者遗失。
   * 2. 半消息发送后，未来得及执行本地事务，出现异常，半消息也未回滚。
   */
  @Override
  public LocalTransactionState checkLocalTransaction(MessageExt msg) {
    String msgIdInDb = msg.getKeys();

    MqMessageDelay mqMessageDelay = simpleDelayService.findOne(msgIdInDb);

    if (localTransactionSuccess(mqMessageDelay)) {

      log.info("msg [{}] checkLocalTransaction success", msgIdInDb);

      return LocalTransactionState.COMMIT_MESSAGE;

    } else {

      int maxCheckTimes = 3;

      int checkTimes = (int) MSG_CHECK_TIMES_MAP.incrementAndGet(msgIdInDb);

      log.info("msg [{}] check times [{}]", msgIdInDb, checkTimes);

      if (checkTimes < maxCheckTimes) {

        return LocalTransactionState.UNKNOW;

      } else {
        // 超过最大重试次数，回滚消息。
        log.info("reach maxCheckTimes ,rollback msg [{}]", msg);

        if (mqMessageDelay == null) {

          recordSendFailureAndRemoveCacheQuietly(msg.getTopic(),
            msg.getKeys(),
            MqMessageErrorRecord.NOT_MATCHED,
            msg.getMsgId(),
            msg.getProperty(RocketMqConst.MsgPropertiesKey.BUSINESS_ID),
            checkTimes,
            0,
            MsgSendErrorCodeEnum.CHECK_LOCAL_TX_MAX_RETRY_NO_MSG.getDescription(),
            MsgSendErrorCodeEnum.CHECK_LOCAL_TX_MAX_RETRY_NO_MSG.getCode());

        } else {
          // 存在消息
          simpleDelayService.updateStatus(msgIdInDb, MqMessageDelay.SEND_ERROR);
          recordSendFailureAndRemoveCacheQuietly(msg.getTopic(),
            msg.getKeys(),
            MqMessageErrorRecord.MATCHED,
            msg.getMsgId(),
            msg.getProperty(RocketMqConst.MsgPropertiesKey.BUSINESS_ID),
            checkTimes,
            0,
            MsgSendErrorCodeEnum.CHECK_LOCAL_TX_MAX_RETRY.getDescription(),
            MsgSendErrorCodeEnum.CHECK_LOCAL_TX_MAX_RETRY.getCode());
        }


        return LocalTransactionState.ROLLBACK_MESSAGE;
      }
    }
  }

  private boolean localTransactionSuccess(MqMessageDelay mqMessageDelay) {
    // 如果状态是已发送，说明本地事务执行成功，可以进行发送了
    return mqMessageDelay != null && mqMessageDelay.getStatus() == 1;
  }


  /**
   * 记录失败的消息
   * 删除计数缓存
   *
   * @param mqPlatformMsgId 当执行本地事务时，总是拿不到。回查时才会有
   * @param matched         回查时查不到主表消息时，就是没匹配到
   */
  private void recordSendFailureAndRemoveCacheQuietly(String topic,
                                                      String idInMsgDb,
                                                      int matched,
                                                      String mqPlatformMsgId,
                                                      String businessId,
                                                      int retryTimes,
                                                      int retryTimesWhenFailed,
                                                      String errorReason,
                                                      int errorCode) {
    try {

      rocketMqMsgSendFailureHandler.recordSendFailure(topic, idInMsgDb, matched, mqPlatformMsgId, businessId, retryTimes, retryTimesWhenFailed, errorReason, errorCode);

    } catch (Throwable ignoredEx) {

      log.error("record send failed msg error, ignored but log", ignoredEx);

    } finally {
      MSG_CHECK_TIMES_MAP.remove(idInMsgDb);
    }
  }

}
