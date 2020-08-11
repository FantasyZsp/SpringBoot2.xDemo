package xyz.mydev.redisson.test.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RLock;
import xyz.mydev.redisson.RedissonClientTestApp;
import xyz.mydev.redisson.utils.SimpleThreadFactory;
import xyz.mydev.redisson.utils.ThreadUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ZSP
 */
@Slf4j
public class RLockTest extends RedissonClientTestApp {


  /**
   * 分布式锁
   */
  @Test
  public void tryLockConcurrent() {

    SimpleThreadFactory.newT(this::tryLock).start();
    SimpleThreadFactory.newT(this::tryLock).start();
    SimpleThreadFactory.newT(this::tryLock).start();

    ThreadUtils.sleepSeconds(5);
  }

  private void tryLock() {

    RLock lock = redissonClient.getLock("scheduleLock");

    if (lock.tryLock()) {
      log.info("tryLock success");
      try {
        log.info("invoke business method");
        randomError();
        log.info("invoke business success");

      } catch (Throwable ex) {
        log.info("business ex");
      } finally {
        lock.unlock();
      }

    } else {
      log.info("lock failed");
    }
  }

  public static void randomError() {
    if (ThreadLocalRandom.current().nextBoolean()) {
      throw new RuntimeException();
    }
  }

}
