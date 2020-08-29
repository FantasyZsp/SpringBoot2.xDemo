package xyz.mydev.mq.delay.load;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import xyz.mydev.mq.delay.DelayMsgSender;
import xyz.mydev.mq.delay.SimpleDelayService;
import xyz.mydev.mq.delay.TimeCalculator;
import xyz.mydev.mq.delay.repository.MqMessageDelay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 先完善加载机制，再完善消费失败记录
 * <p>
 * 废弃，当分布式部署时存在过多重复消息
 *
 * @author ZSP
 */
@Slf4j
@Deprecated
public class SingleScheduleMsgLoader implements ApplicationRunner, MsgLoader {
  private final SimpleDelayService simpleDelayService;
  private final DelayMsgSender delayMsgSender;

  private final AtomicBoolean jobHasRunning = new AtomicBoolean(false);
  private final AtomicBoolean initializationCompleted = new AtomicBoolean(false);

  public SingleScheduleMsgLoader(SimpleDelayService simpleDelayService,
                                 DelayMsgSender delayMsgSender,
                                 CheckPointService checkPointService) {
    this.simpleDelayService = simpleDelayService;
    this.delayMsgSender = delayMsgSender;
  }


  /**
   * 定时任务 半小时扫一次 扫半小时后已经到了生效时间的记录，防遗漏
   */
  @Scheduled(cron = "0 0/30 * * * ?")
  public void scheduleLoader() {
    // 初始化完成后再来执行
    if (initializationCompleted.get() && jobHasRunning.compareAndSet(false, true)) {
      try {
        doLoad();
      } catch (Throwable e) {
        log.error("ex when load delay msg,show you and this will not be thrown ", e);
      } finally {
        jobHasRunning.set(false);
      }
    } else {
      log.info("skip scheduleLoader job because app is starting ");
    }
  }

  private synchronized void doLoad() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime formattedStartTime = TimeCalculator.formatTime4HalfHour(now);
    log.info("scheduleLoader working at [{}] ,formatted at [{}]", now, formattedStartTime);
    LocalDateTime endTime = formattedStartTime.plusMinutes(30);
    load(formattedStartTime, endTime);
  }

  private void putIntoQueue(List<MqMessageDelay> willSendList) {
    if (CollectionUtils.isNotEmpty(willSendList)) {
      for (MqMessageDelay delay : willSendList) {
        delayMsgSender.getPorter().put(delay);
      }
    }
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("load delay msg in db when app up");
    try {
      doLoad();
    } finally {
      initializationCompleted.set(true);
    }
  }


  /**
   * 闭区间，提前加载整点大批量数据
   */
  @Override
  public void load(LocalDateTime startTime, LocalDateTime endTime) {
    List<MqMessageDelay> willSendList = simpleDelayService.loadByTime(startTime, endTime);
    putIntoQueue(willSendList);
  }
}
