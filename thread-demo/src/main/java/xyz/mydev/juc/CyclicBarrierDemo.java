package xyz.mydev.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZSP  2019/05/27 08:54
 */
@Slf4j
public class CyclicBarrierDemo {
  // 请求总数
  private final static int CLIENT_TOTAL = 500;
  // 满足往下执行的并发数
  private final static int CONCURRENT_TOTAL = 5;
  private final static CyclicBarrier cyclicBarrier = new CyclicBarrier(CONCURRENT_TOTAL);

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();

    for (int i = 0; i < CLIENT_TOTAL; i++) {
      final int threadNum = i;
      Thread.sleep(1000);
      executorService.execute(() -> {
        race(threadNum);
      });
    }

    executorService.shutdown();
    log.info("finish");
  }

  private static void race(int threadNum) {
    log.info("{} is ready", threadNum);
    try {
      Thread.sleep(1000);
      cyclicBarrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      log.error("exception", e);
    }
    log.info("{} is finished ", threadNum);
  }

}
