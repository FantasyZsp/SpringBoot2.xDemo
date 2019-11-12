package xyz.mydev.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author ZSP  2019/05/27 08:54
 */
@Slf4j
public class ConcurrentDemo {
  // 请求总数
  private final static int CLIENT_TOTAL = 500;
  // 并发数
  private final static int CONCURRENT_TOTAL = 20;

  private static int sum = 0;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(1);
    final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);

    for (int i = 0; i < CLIENT_TOTAL; i++) {
      executorService.execute(() -> {
        try {
          semaphore.acquire();
          addOne();
          semaphore.release();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executorService.shutdown();
    log.info("求和结果: {}", sum);
  }

  private static void addOne() {
    ++sum;
  }

}
