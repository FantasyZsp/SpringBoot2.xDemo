package xyz.mydev.cache.guava;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.beans.DelayMsg;
import xyz.mydev.util.ThreadUtils;

import java.time.LocalDateTime;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * @author ZSP
 */
@Slf4j
public class PracticeDemo2 {

  public static final Object object = new Object();

  public static LoadingCache<String, Object> getInstance() {
    return Caffeine.newBuilder()
      .expireAfterWrite(2000, TimeUnit.SECONDS)
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .build(key -> null);
  }

  public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(100);

    LoadingCache<String, Object> instance = getInstance();

    CyclicBarrier cyclicBarrier = new CyclicBarrier(101);
    IntStream.rangeClosed(1, 100).forEach(i -> {

      executorService.submit(() -> {

        DelayMsg msg = new DelayMsg("id-" + i, LocalDateTime.now().plusSeconds(i));
        try {
          cyclicBarrier.await();
          log.info("start");
        } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
        }
        instance.put(msg.getId(), object);
      });

    });

    log.info("wait threads ready");
    ThreadUtils.sleepSeconds(3);
    cyclicBarrier.await();
    log.info("threads starting");


    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    scheduledExecutorService.scheduleAtFixedRate(() -> {
      log.info("cache info: {} ,size: {}", instance.stats(), instance.asMap().size());
    }, 10L, 20L, TimeUnit.SECONDS);

    AtomicLong atomicLong = new AtomicLong();
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      instance.put("id-L-" + atomicLong.getAndIncrement(), object);
    }, 1000L, 1L, TimeUnit.MILLISECONDS);

    ThreadUtils.sleepSeconds(100);
  }
}
