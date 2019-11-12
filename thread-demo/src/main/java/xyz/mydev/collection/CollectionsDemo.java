package xyz.mydev.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP  2019/05/27 08:54
 */
@Slf4j
public class CollectionsDemo {
  // 请求总数
  private final static int CLIENT_TOTAL = 10000;
  // 并发数
  private final static int CONCURRENT_TOTAL = 20;

  private final static Map<Integer, Integer> MAP = new HashMap<>(10000);
  private final static Map<Integer, Integer> C_MAP = new ConcurrentHashMap<>(10000);

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(CONCURRENT_TOTAL);
    final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);

    for (int i = 0; i < CLIENT_TOTAL; i++) {
      int count = i;
      executorService.execute(() -> {
        try {
          semaphore.acquire();
          add(count);
          semaphore.release();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          countDownLatch.countDown();
        }
      });
    }
//        countDownLatch.await();
    countDownLatch.await(1000, TimeUnit.NANOSECONDS);
    executorService.shutdown();
    log.info("MAP size: {}", MAP.size());
    log.info("C_MAP size: {}", C_MAP.size());
  }

  private static void add(int number) {
    MAP.put(number, number);
    C_MAP.put(number, number);
  }

}
