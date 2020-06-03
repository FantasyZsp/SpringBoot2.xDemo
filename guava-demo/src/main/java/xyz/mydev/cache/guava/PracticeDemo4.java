package xyz.mydev.cache.guava;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.util.ThreadUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class PracticeDemo4 {

  public static final Object object = new Object();

  public static LoadingCache<String, Object> getInstance() {
    return Caffeine.newBuilder()
      .expireAfterWrite(10, TimeUnit.SECONDS)
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .build(key -> null);
  }

  public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
    LoadingCache<String, Object> instance = getInstance();

    instance.put("key1", object);
    System.out.println(instance.getIfPresent("key2"));
    System.out.println(instance.getIfPresent("key1"));
    System.out.println(instance.get("key3", key3 -> object));
    System.out.println(instance.getIfPresent("key3"));

    Object key4 = instance.getIfPresent("key4");
    if (key4 == null) {
      System.out.println("go on ...");
      instance.put("key4", object);
    }
    System.out.println(instance.getIfPresent("key4"));
    ThreadUtils.sleepSeconds(3);
  }
}
