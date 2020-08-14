package xyz.mydev.redisson.test.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.redisson.RedissonClientTestApp;
import xyz.mydev.redisson.utils.SimpleThreadFactory;

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


  /**
   * 用缓存去判断一把锁在不在
   */
  @Test
  public void rLockAndCache() {
    RLock lock = redissonClient.getLock("simpleLock");
    lock.lock();
    try {
      ThreadUtils.startAndJoin(SimpleThreadFactory.newT(() -> {
        RBucket<String> simpleLock = redissonClient.getBucket("simpleLock");
        log.info("simpleLock.isExists()==> {}", simpleLock.isExists());
      }));

    } catch (Throwable ex) {
      log.error("ex occur... ", ex);

    } finally {
//      lock.unlock();
    }


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
