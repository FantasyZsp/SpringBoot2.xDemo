package xyz.mydev.mq.delay.load;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import xyz.mydev.mq.delay.SimpleDelayService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 未发送的消息检查点
 * 基于时间索引
 *
 * @author ZSP
 */
@Slf4j
public class RedisCheckPointServiceImpl implements CheckPointService {

  public RedisCheckPointServiceImpl(RedissonClient redissonClient,
                                    SimpleDelayService simpleDelayService) {
    Objects.requireNonNull(redissonClient);
    checkpointHolder = redissonClient.getBucket("aurora:delayMsgCheckPoint");
    writeLock = redissonClient.getLock("aurora:delayMsgCheckPointWriteLock");
    scheduleLock = redissonClient.getLock("aurora:delayMsgCheckPointScheduleLock");
    this.simpleDelayService = simpleDelayService;
  }

  private final RBucket<String> checkpointHolder;
  private final Lock writeLock;
  private final Lock scheduleLock;
  private final SimpleDelayService simpleDelayService;
  private final Lock readLock = new ReentrantLock();
  private CheckPointUpdater updater = new CheckPointUpdater(this);


  @Override
  public LocalDateTime readCheckPoint() {
    return getFromHolder()
      .orElseGet(() -> {
        Lock writeLock = getWriteLock();
        if (writeLock.tryLock()) {
          try {
            LocalDateTime checkpoint = simpleDelayService.findCheckpoint().orElse(DEFAULT_CHECK_POINT);
            writeCheckPoint(checkpoint);
            return checkpoint;
          } catch (Throwable ex) {
            log.error("ex when readWriteCheckPoint..", ex);
            return DEFAULT_CHECK_POINT;
          } finally {
            writeLock.unlock();
          }

        } else {
          return DEFAULT_CHECK_POINT;
        }

      });
  }

  @Override
  public LocalDateTime readNextCheckpoint(LocalDateTime currentCheckPoint) {
    Objects.requireNonNull(currentCheckPoint);
    LocalDateTime nextCheckpoint = simpleDelayService.findNextCheckpointAfter(currentCheckPoint);
    log.info("currentCheckPoint: {}, nextCheckpoint: {}", currentCheckPoint, nextCheckpoint);
    return nextCheckpoint;
  }

  @Override
  public LocalDateTime readNextCheckpoint() {
    return getFromHolder()
      .or(() -> Optional.of(DEFAULT_CHECK_POINT))
      .map(this::readNextCheckpoint)
      .get()
      ;
  }

  public Optional<LocalDateTime> getFromHolder() {
    String currentCheckPoint = checkpointHolder.get();
    if (currentCheckPoint == null) {
      return Optional.empty();
    }
    return Optional.of(LocalDateTime.parse(currentCheckPoint, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }

  @Override
  public void writeCheckPoint(LocalDateTime checkPoint) {
    Objects.requireNonNull(checkPoint);
    checkpointHolder.set(checkPoint.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    log.info("writeCheckPoint: {}", checkPoint);
  }


  @Override
  public Lock getWriteLock() {
    return writeLock;
  }

  /**
   * 不需要支持
   */
  @Override
  public Lock getReadLock() {
    return readLock;
  }

  @Override
  public Lock getScheduleLock() {
    return scheduleLock;
  }


  public void schedule() {
    updater.startWorking();
  }

  public void stop() {
    updater.stopWorking();
  }

  static class CheckPointUpdater {

    private final CheckPointService checkPointService;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "checkPointScheduleThread"));

    CheckPointUpdater(CheckPointService checkPointService) {
      this.checkPointService = checkPointService;
    }

    public void startWorking() {
      scheduledExecutorService.scheduleWithFixedDelay(this::updateCheckpoint, 2, 30, TimeUnit.MINUTES);
    }

    public void stopWorking() {
      scheduledExecutorService.shutdown();
    }

    private void updateCheckpoint() {

      Lock scheduleLock = checkPointService.getScheduleLock();

      boolean enableSchedule = scheduleLock.tryLock();

      if (!enableSchedule) {
        log.info("schedule to update checkpoint by other app");
      }

      if (enableSchedule) {
        try {
          Lock writeLock = checkPointService.getWriteLock();

          // 调度任务拿到的检查点一定是可靠的、最新的，阻塞写入
          writeLock.lock();
          try {
            LocalDateTime next = checkPointService.readNextCheckpoint();
            checkPointService.writeCheckPoint(next);
            log.info("update checkpoint success , {}", next);
          } finally {
            writeLock.unlock();
          }


        } catch (Throwable ex) {
          log.error("update checkpoint failed", ex);
        } finally {
          scheduleLock.unlock();
        }
      }


    }


  }
}
