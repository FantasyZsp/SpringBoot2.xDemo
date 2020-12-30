package xyz.mydev.base;

import lombok.extern.slf4j.Slf4j;
import xyz.mydev.common.utils.ThreadUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 参考 org.apache.rocketmq.broker.transaction.TransactionalMessageCheckService 调度实现
 *
 * @author ZSP
 */
@Slf4j
public class WaitAndRun {
  /**
   * 相比jdk中的CountDownLatch，增加了reset方法
   */
  protected final CountDownLatch2 waitPoint = new CountDownLatch2(1);
  protected volatile AtomicBoolean hasNotified = new AtomicBoolean(false);


  public static void main(String[] args) {

    WaitAndRun waitAndRun = new WaitAndRun();

    Runnable runnable = () -> {
      int i = 10;
      while (i > 0) {
        waitAndRun.waitForRunning(2000);
        i--;
      }
    };


    runnable.run();

    ThreadUtils.start(new Thread(runnable, "T1"));
    ThreadUtils.start(new Thread(runnable, "T2"));

    ThreadUtils.join(200000);

  }


  protected void waitForRunning(long interval) {
    if (hasNotified.compareAndSet(true, false)) {
      log.info("compareAndSet onWaitEnd");
      return;
    }

    //entry to wait
    waitPoint.reset();

    try {
      waitPoint.await(interval, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      log.error("Interrupted", e);
    } finally {
      hasNotified.set(false);
      log.info("finally onWaitEnd");
    }
  }


}
