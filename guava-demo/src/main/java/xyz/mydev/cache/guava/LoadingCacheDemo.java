package xyz.mydev.cache.guava;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.mydev.beans.DelayMsg;
import xyz.mydev.util.ThreadUtils;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class LoadingCacheDemo {
  public static void main(String[] args) {


    LoadingCache<String, String> loadingCache = Caffeine.newBuilder()
      .expireAfterWrite(1000, TimeUnit.MILLISECONDS)
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .maximumSize(10)
      .build(key -> null);

    loadingCache.put("hello", "loadingCache");

    System.out.println(loadingCache.get("hello"));

    System.out.println(loadingCache.getIfPresent("hello"));

    ThreadUtils.sleepSeconds(1);

    System.out.println(loadingCache.get("hello"));

    System.out.println(loadingCache.getIfPresent("hello"));

    new Thread(() -> loadingCache.put("hello", "t1")).start();
    new Thread(() -> loadingCache.put("hello", "t2")).start();


    ThreadUtils.join(100);

    System.out.println(loadingCache.get("hello"));
    System.out.println(loadingCache.getIfPresent("hello"));

    loadingCache.put("hello2", "world2");


    System.out.println(loadingCache.getAllPresent(List.of("hello", "hello2", "hello3")));
    System.out.println(loadingCache.getAll(List.of("hello", "hello2", "hello3")));

  }

  @Test
  public void test() {

    LoadingCache<DelayMsg, Integer> loadingCache = Caffeine.newBuilder()
      .expireAfterWrite(1000, TimeUnit.MILLISECONDS)
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .maximumSize(10)
      .build(key -> null);

    DelayMsg delayMsg = new DelayMsg("id", LocalDateTime.now());

    loadingCache.put(delayMsg, 1);
    System.out.println(loadingCache.get(delayMsg));
    System.out.println(loadingCache.stats());
  }

  @Test
  public void testExpireBasedTime() {

    LoadingCache<DelayMsg, Integer> loadingCache = Caffeine.newBuilder()
      .expireAfter(new ExpiredStrategy())
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .build(key -> null);
    ThreadUtils.sleepSeconds(1);
    DelayMsg delayMsg = new DelayMsg("id", LocalDateTime.now().plusSeconds(2));

    loadingCache.put(delayMsg, 1);

    System.out.println(loadingCache.get(delayMsg));
    System.out.println(loadingCache.stats());
    ThreadUtils.sleepSeconds(1);
    System.out.println(loadingCache.get(delayMsg));
    System.out.println(loadingCache.get(delayMsg));
    ThreadUtils.sleepSeconds(1);
    loadingCache.cleanUp();
    System.out.println(loadingCache.get(delayMsg));

    ThreadUtils.sleepSeconds(10);

  }


  static class ExpiredStrategy implements Expiry<DelayMsg, Integer> {

    @Override
    public long expireAfterCreate(@Nonnull DelayMsg key, @Nonnull Integer value, long currentTime) {
      log.info("key {} value{} currentTime {}", key, value, currentTime);
      log.info("time {}  delay: {}", key.getTime(), key.getDelay(TimeUnit.MILLISECONDS));
      return key.getDelay(TimeUnit.NANOSECONDS);
    }

    @Override
    public long expireAfterUpdate(@Nonnull DelayMsg key, @Nonnull Integer value, long currentTime, long currentDuration) {
      log.info("expireAfterUpdate");
      return key.getDelay(TimeUnit.NANOSECONDS);
    }

    @Override
    public long expireAfterRead(@Nonnull DelayMsg key, @Nonnull Integer value, long currentTime, long currentDuration) {
      log.info("expireAfterRead");
      return key.getDelay(TimeUnit.NANOSECONDS);
    }
  }

}
