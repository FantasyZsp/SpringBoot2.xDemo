package xyz.mydev.cache.guava;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.util.ThreadUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class SimpleCacheDemo {

  public static void main(String[] args) {
    Cache<String, String> cache = Caffeine.newBuilder()
      .expireAfterWrite(10, TimeUnit.MILLISECONDS)
      .recordStats()
      .removalListener((k, v, cause) -> {
        log.warn("removed key {}  value {}  cause {}", k, v, cause);
      })
      .maximumSize(10)
      .build();

    cache.put("hello", "world");
    System.out.println(cache.getIfPresent("hello"));
    System.out.println(cache.get("hello", k -> "worldNew"));


    ThreadUtils.sleepSeconds(1);
    System.out.println(cache.getIfPresent("hello"));
    System.out.println(cache.getIfPresent("aaa"));
    System.out.println(cache.get("hello", k -> "worldNew"));
    System.out.println(cache.getIfPresent("hello"));

    System.out.println(cache.asMap().containsKey("hello"));


    ThreadUtils.sleepSeconds(100);
  }


}
