package xyz.mydev.cache.guava;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.util.ThreadUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZSP
 */
@Slf4j
public class PracticeDemo3 {

  public static final Object object = new Object();

  public static LoadingCache<String, Object> getInstance() {
    return Caffeine.newBuilder()
      .expireAfterWrite(30, TimeUnit.MINUTES)
      .expireAfterAccess(10, TimeUnit.SECONDS)
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .build(key -> null);
  }

  public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
    LoadingCache<String, Object> instance = getInstance();
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    scheduledExecutorService.scheduleAtFixedRate(() -> {
      log.info("cache info: {} ,size: {}", instance.stats(), instance.asMap().size());
    }, 1, 2, TimeUnit.SECONDS);

    AtomicLong atomicLong = new AtomicLong();
    AtomicLong atomicLong2 = new AtomicLong();
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      instance.put("id-L-" + atomicLong.getAndIncrement(), object);
      String key = "id-L-" + atomicLong2.get();
      Object o = instance.get(key);

      if (atomicLong.get() % 1000 == 0) {
//        instance.invalidateAll();
        instance.asMap().clear();
      }

//      if (o != null) {
////        log.info("access key [{}]", key);
//      }
    }, 500, 5, TimeUnit.MILLISECONDS);


    ThreadUtils.sleepSeconds(100);
  }
}
