package xyz.mydev.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP  2019/05/27 08:54
 */
@Slf4j
public class CyclicBarrierDemo {
  // 请求总数
  private final static int CLIENT_TOTAL = 100;
  // 满足往下执行的并发数
  private final static int CONCURRENT_TOTAL = 5;

  private final static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(1);
  private final static CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(CONCURRENT_TOTAL, () -> FIXED_THREAD_POOL.submit(() -> log.info("finished")));

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_TOTAL);

    for (int i = 0; i < CLIENT_TOTAL; i++) {
      final int threadNum = i;
      Thread.sleep(10);
      executorService.execute(() -> {
        race(threadNum);
      });
    }

    executorService.shutdown();
    log.info("finish");

    FIXED_THREAD_POOL.awaitTermination(20, TimeUnit.SECONDS);
    FIXED_THREAD_POOL.shutdown();

  }

  private static void race(int threadNum) {
    log.info("{} is ready", threadNum);
    try {
      Thread.sleep(200);
      CYCLIC_BARRIER.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      log.error("exception", e);
    }
    log.info("{} is finished ", threadNum);
  }

}
