package xyz.mydev.mq.delay.load;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RObject;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.mydev.mq.delay.DelayMsgSender;
import xyz.mydev.mq.delay.SimpleDelayService;
import xyz.mydev.mq.delay.repository.MqMessageDelay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

import static xyz.mydev.mq.delay.TimeCalculator.formatTime4HalfHour;

/**
 * 先完善加载机制，再完善消费失败记录
 * 1. 应用启动时，从检查点开始扫描到当前格式化时间
 * 2. 定时调度，从当前格式化时间开始到一个间隔后格式化时间
 * 并发调度：
 * 当1和2多实例下并发时，启动优于调度。启动间需要争抢锁，调度间需要争抢锁。
 * 调度需要在无应用启动时进入调度逻辑。
 *
 * @author ZSP
 */
@Service
@Slf4j
public class DistributedScheduleMsgLoader implements ApplicationRunner, InitializingBean, MsgLoader {
  private final SimpleDelayService simpleDelayService;
  private final DelayMsgSender delayMsgSender;
  private final CheckPointService checkPointService;
  private final RedissonClient redissonClient;

  public DistributedScheduleMsgLoader(SimpleDelayService simpleDelayService,
                                      DelayMsgSender delayMsgSender,
                                      RedissonClient redissonClient,
                                      CheckPointService checkPointService) {
    this.simpleDelayService = simpleDelayService;
    this.delayMsgSender = delayMsgSender;
    this.redissonClient = redissonClient;
    this.checkPointService = checkPointService;
  }


  public static final String LOAD_FROM_CHECK_POINT_LOCK_KEY = "aurora:loadDelayMsgFromCheckPoint";
  public static final String LOAD_CYCLICALLY = "aurora:loadDelayMsgCyclically";
  private final AtomicBoolean initializationCompleted = new AtomicBoolean(false);


  /**
   * key -> {@link DistributedScheduleMsgLoader#LOAD_FROM_CHECK_POINT_LOCK_KEY}
   */
  private RObject appStartFlag;
  /**
   * key -> {@link DistributedScheduleMsgLoader#LOAD_FROM_CHECK_POINT_LOCK_KEY}
   */
  private Lock checkPointLoadLock;
  /**
   * key -> {@link DistributedScheduleMsgLoader#LOAD_CYCLICALLY}
   */
  private Lock cyclicLoadLock;


  @Scheduled(cron = "0 0/30 * * * ?")
  public void scheduleLoader() {

    if (!initializationCompleted.get()) {
      log.warn("app is starting, skip this task");
      return;
    }

    if (appStartFlag.isExists()) {
      log.info("there is a service starting, skip this task");
      return;
    }

    if (cyclicLoadLock.tryLock()) {
      log.info("lock LOAD_CYCLICALLY success");
      try {
        loadCyclically();
        log.info("invoke business success");

      } catch (Throwable ex) {
        log.info("business ex");
      } finally {
        cyclicLoadLock.unlock();
      }
    } else {
      log.info("loadLock failed");
    }
  }


  private synchronized void loadCyclically() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime formattedStartTime = formatTime4HalfHour(now);
    LocalDateTime endTime = formattedStartTime.plusMinutes(30);
    log.info("scheduleLoader working at [{}] ,formatted at [{}], end at [{}]", now, formattedStartTime, endTime);
    load(formattedStartTime, endTime);
  }


  private void putIntoQueue(List<MqMessageDelay> willSendList) {
    if (CollectionUtils.isNotEmpty(willSendList)) {
      for (MqMessageDelay delay : willSendList) {
        delayMsgSender.getPorter().put(delay);
      }
    }
  }

  /**
   * 启动加载时可能有调度正在加载，此时需要缓存层去重。
   */
  @Override
  public void run(ApplicationArguments args) throws Exception {

    if (checkPointLoadLock.tryLock()) {
      log.info("load delay msg in db when app up");
      try {
        log.info("invoke loadFromCheckPoint");
        loadFromCheckPoint();
        log.info("invoke loadFromCheckPoint success");

      } catch (Throwable ex) {
        log.error("loadFromCheckPoint ex", ex);
      } finally {
        try {
          checkPointLoadLock.unlock();
        } catch (Throwable ex) {
          log.warn("checkPointLoadLock unlock ex", ex);
        }
      }

    } else {
      log.info("delay msg is loading from db when app up by other app instance");
    }
    initializationCompleted.set(true);
  }

  private void loadFromCheckPoint() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime startTimeFromCheckPoint = checkPointService.readCheckPoint();
    LocalDateTime endTime = formatTime4HalfHour(now).plusMinutes(30);
    log.info("loadFromCheckPoint working at [{}] ,checkpoint  at [{}], end at [{}]", now, startTimeFromCheckPoint, endTime);
    load(startTimeFromCheckPoint, endTime);
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    // 获取锁操作对象
    appStartFlag = redissonClient.getBucket(LOAD_FROM_CHECK_POINT_LOCK_KEY);
    checkPointLoadLock = redissonClient.getLock(LOAD_FROM_CHECK_POINT_LOCK_KEY);
    cyclicLoadLock = redissonClient.getLock(LOAD_CYCLICALLY);
  }


  @Override
  public void load(LocalDateTime startTime, LocalDateTime endTime) {
    List<MqMessageDelay> willSendList = simpleDelayService.loadByTime(startTime, endTime);
    putIntoQueue(willSendList);
  }


}
