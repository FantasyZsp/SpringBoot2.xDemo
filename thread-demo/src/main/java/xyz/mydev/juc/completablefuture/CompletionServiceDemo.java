package xyz.mydev.juc.completablefuture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.juc.exception.ThreadExecutionException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZSP
 */
@Slf4j
public class CompletionServiceDemo {
  private static ExecutorService executor = Executors.newFixedThreadPool(6, new MyThreadFactory());

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    log.info("start at {}", LocalTime.now());

//    testMockCompletionService();
//    testCompletionService();
    System.out.println(testCompletionServiceFetchMin2());
//    System.out.println(testCompletionServiceFetchFirst());


    executor.shutdown();
    executor.awaitTermination(1L, TimeUnit.MINUTES);
    log.info("  end at {}", LocalTime.now());
  }


  private static void testConcurrentFindAndSave() throws ExecutionException, InterruptedException {
    // 异步向电商 S1 询价
    Future<Provider> f1 = executor.submit(CompletionServiceDemo::getPriceByS1);
    // 异步向电商 S1 询价
    Future<Provider> f2 = executor.submit(CompletionServiceDemo::getPriceByS2);
    // 异步向电商 S3 询价
    Future<Provider> f3 = executor.submit(CompletionServiceDemo::getPriceByS3);

    // 获取电商 S1 报价并保存(这里询价耗时长，导致主线程阻塞等待)
    Provider s1 = f1.get();
    executor.execute(() -> save(s1));

    Provider s2 = f2.get();
    executor.execute(() -> save(s2));

    Provider s3 = f3.get();
    executor.execute(() -> save(s3));
  }

  private static void testMockCompletionService() throws InterruptedException {
    BlockingQueue<Provider> blockingQueue = new LinkedBlockingQueue<>();
    Future<Provider> f1 = executor.submit(CompletionServiceDemo::getPriceByS1);
    Future<Provider> f2 = executor.submit(CompletionServiceDemo::getPriceByS2);
    Future<Provider> f3 = executor.submit(CompletionServiceDemo::getPriceByS3);


    executor.execute(() -> {
      try {
        blockingQueue.put(f1.get());
      } catch (Exception e) {
      }
    });
    executor.execute(() -> {
      try {
        blockingQueue.put(f2.get());
      } catch (Exception e) {
      }
    });
    executor.execute(() -> {
      try {
        blockingQueue.put(f3.get());
      } catch (Exception e) {
      }
    });


    for (int i = 0; i < 3; i++) {
      Provider result = blockingQueue.take();
      executor.execute(() -> save(result));
    }
  }

  private static void testCompletionService() throws InterruptedException, ExecutionException {
    CompletionService<Provider> cs = new ExecutorCompletionService<>(executor);

    cs.submit(CompletionServiceDemo::getPriceByS1);
    cs.submit(CompletionServiceDemo::getPriceByS2);
    cs.submit(CompletionServiceDemo::getPriceByS3);
    for (int i = 0; i < 3; i++) {
      Provider r = cs.take().get();
      executor.execute(() -> save(r));
    }

  }

  /**
   * 选择最小值
   */
  private static long testCompletionServiceFetchMin2() throws InterruptedException, ExecutionException {
    CompletionService<Provider> cs = new ExecutorCompletionService<>(executor);
    int taskSize = 3;
    CountDownLatch latch = new CountDownLatch(taskSize);

    AtomicLong min = new AtomicLong(Long.MAX_VALUE);
    cs.submit(CompletionServiceDemo::getPriceByS1);
    cs.submit(CompletionServiceDemo::getPriceByS2);
    cs.submit(CompletionServiceDemo::getPriceByS3);
    for (int i = 0; i < taskSize; i++) {
      Provider r = cs.take().get();
      // 取最小值
      min.set(Long.min(min.get(), r.getPrice()));
      latch.countDown();
      executor.execute(() -> save(r));
    }

    latch.await();
    // 返回最小值，注意做同步
    return min.get();
  }

  /**
   * 选择最先询到价的，入库返回价格
   */
  private static long testCompletionServiceFetchFirst() throws InterruptedException, ExecutionException {
    CompletionService<Provider> cs = new ExecutorCompletionService<>(executor);
    int taskSize = 3;
    AtomicLong min = new AtomicLong(Long.MAX_VALUE);

    List<Future> futures = new ArrayList<>(taskSize);

    futures.add(cs.submit(CompletionServiceDemo::getPriceByS1));
    futures.add(cs.submit(CompletionServiceDemo::getPriceByS2));
    futures.add(cs.submit(CompletionServiceDemo::getPriceByS3));

    try {
      Provider provider = cs.take().get();
      Integer price = provider.getPrice();
      min.set(Long.min(min.get(), price));
      executor.execute(() -> save(provider));
    } finally {
      futures.forEach(future -> future.cancel(true));
    }
    return min.get();
  }


  private static void save(Provider provider) {
    log.info("start saving =={}== at {}", provider, LocalTime.now());
    sleep(2);
    log.info("  end saving =={}== at {}", provider, LocalTime.now());
  }

  private static Provider getPriceByS1() {
    log.info("===开始询价s1===");
    sleep(10);
    log.info("***结束询价s1***");
    return new Provider("s1", 999);
  }

  private static Provider getPriceByS2() {
    log.info("===开始询价s2===");
    sleep(5);
    log.info("结束询价s2");
    return new Provider("s2", 888);
  }

  private static Provider getPriceByS3() {
    log.info("===开始询价s3===");
    sleep(11);
    log.info("***结束询价s3***");
    return new Provider("s3", 777);
  }

  private static void sleep(int t) {
    try {
      TimeUnit.SECONDS.sleep(t);
    } catch (Exception e) {
      throw new ThreadExecutionException(e);
    }
  }


  static class MyThreadFactory implements ThreadFactory {

    AtomicLong atomicLong = new AtomicLong(0);

    @Override
    public Thread newThread(Runnable r) {
      return new Thread(r, "myThread_" + atomicLong.getAndIncrement());
    }
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  static class Provider {
    private String name;
    private Integer price;

    @Override
    public String toString() {
      return "Provider{" +
        "name='" + name + ", price=" + price +
        '}';
    }
  }
}
