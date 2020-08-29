package xyz.mydev.mq.delay.load;

import lombok.extern.slf4j.Slf4j;
import xyz.mydev.mq.delay.SimpleDelayService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 未发送的消息检查点
 * 基于时间索引
 *
 * @author ZSP
 */
@Slf4j
public class LocalCheckPointServiceImpl implements CheckPointService {

  public LocalCheckPointServiceImpl(SimpleDelayService simpleDelayService) {
    this.simpleDelayService = simpleDelayService;
    this.lock = new ReentrantLock();
  }

  private final SimpleDelayService simpleDelayService;
  private final Lock lock;


  @Override
  public LocalDateTime readCheckPoint() {
    return simpleDelayService.findCheckpoint().orElse(DEFAULT_CHECK_POINT);
  }

  @Override
  public LocalDateTime readNextCheckpoint(LocalDateTime currentCheckPoint) {
    Objects.requireNonNull(currentCheckPoint);
    return simpleDelayService.findNextCheckpointAfter(currentCheckPoint);
  }

  @Override
  public LocalDateTime readNextCheckpoint() {
    return simpleDelayService.findNextCheckpointAfter(DEFAULT_CHECK_POINT);
  }

  @Override
  public void writeCheckPoint(LocalDateTime checkPoint) {
    log.warn("not support writeCheckPoint");
  }

  @Override
  public Lock getWriteLock() {
    return lock;
  }

  @Override
  public Lock getReadLock() {
    return lock;
  }

  @Override
  public Lock getScheduleLock() {
    return lock;
  }
}
