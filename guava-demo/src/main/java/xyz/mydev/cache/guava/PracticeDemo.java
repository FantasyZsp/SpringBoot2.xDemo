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
import java.util.stream.IntStream;

/**
 * @author ZSP
 */
@Slf4j
public class PracticeDemo {

  public static LoadingCache<DelayMsg, Integer> getInstance() {
    return Caffeine.newBuilder()
      .expireAfterWrite(1, TimeUnit.SECONDS)
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .build(key -> null);
  }

  public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(100);

    LoadingCache<DelayMsg, Integer> instance = getInstance();

    CyclicBarrier cyclicBarrier = new CyclicBarrier(101);
    IntStream.rangeClosed(1, 100).forEach(i -> {

      executorService.submit(() -> {

        DelayMsg msg = new DelayMsg("id-" + i, LocalDateTime.now().plusSeconds(i));
        try {
          cyclicBarrier.await();
          log.info("start");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        instance.put(msg, 0);
      });

    });

    log.info("wait threads ready");
    ThreadUtils.sleepSeconds(3);
    cyclicBarrier.await();
    log.info("threads starting");


    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    scheduledExecutorService.scheduleAtFixedRate(() -> {
      log.info("cache info: {}", instance.stats());
    }, 1L, 1L, TimeUnit.SECONDS);

    ThreadUtils.sleepSeconds(100);
  }
}
