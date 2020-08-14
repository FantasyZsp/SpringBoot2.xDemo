package xyz.mydev.redisson.test.object;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.redisson.RootTest;
import xyz.mydev.redisson.test.object.bean.Person;
import xyz.mydev.redisson.utils.SimpleThreadFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class MapTest extends RootTest {

  @Autowired(required = false)
  private RedissonClient redissonClient;


  @Before
  public void init() {
    if (redissonClient == null) {
      throw new RuntimeException("redissonClient has not been initialized");
    }
  }

  @Test
  public void map() {
    RMap<String, Person> testMap = redissonClient.getMap("testMap");

    testMap.put("1", Person.of("1", "小明", "河南"));
    testMap.put("2", Person.of("1", "小明", "河南"));
    log.info("testMap： {}", testMap);

    System.out.println(testMap.containsKey("1"));
    System.out.println(testMap.containsKey("2"));

  }


  /**
   * 可以控制每一个key的过期时间
   */
  @Test
  public void mapCache() {
    RMapCache<String, Person> mapCache = redissonClient.getMapCache("testMapCache");

    mapCache.put("1", Person.of("1", "小明", "河南"), 10L, TimeUnit.SECONDS);
    mapCache.put("2", Person.of("2", "小王", "郑州"), 30L, TimeUnit.SECONDS);
    mapCache.put("3", Person.of("3", "转瞬即逝", "嘤嘤嘤"), 1L, TimeUnit.SECONDS);
    System.out.println(mapCache.containsKey("1"));
    System.out.println(mapCache.containsKey("2"));
    System.out.println(mapCache.containsKey("3"));
    ThreadUtils.join(2000);
    System.out.println(mapCache.containsKey("3"));


    log.info("mapCache： {}", mapCache);

  }

  /**
   * 配置的过期参数需要结合过期策略才会生效
   */
  @Test
  public void localCacheMap() {
    LocalCachedMapOptions<Integer, Person> objectLocalCachedMapOptions = LocalCachedMapOptions
      .<Integer, Person>defaults()
      .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
      .timeToLive(20, TimeUnit.SECONDS);


    RLocalCachedMap<Integer, Person> testLocalCacheMap = redissonClient.getLocalCachedMap("testLocalCacheMap", objectLocalCachedMapOptions);
    testLocalCacheMap.put(1, Person.of("1", "小明", "河南"));
    ThreadUtils.join(2000);
    testLocalCacheMap.put(2, Person.of("2", "小大", "河南"));
    ThreadUtils.join(2000);

    testLocalCacheMap.put(3, Person.of("3", "小张", "河南"));
    ThreadUtils.join(2000);

    testLocalCacheMap.put(4, Person.of("4", "小李", "河南"));
    ThreadUtils.join(2000);

    testLocalCacheMap.put(5, Person.of("5", "小王", "河南"));
    ThreadUtils.join(2000);

    testLocalCacheMap.put(6, Person.of("6", "小小", "河南"));
    System.out.println(testLocalCacheMap.get(1));
    System.out.println(testLocalCacheMap.get(2));
    log.info("test");

  }

  /**
   * 配置的过期不生效
   */
  @Test
  public void localCacheMapCaseSize() {
    new Thread(this::localCacheByThread, "T-A").start();
    new Thread(this::localCacheByThread, "T-B").start();
    System.out.println();

  }

  private void localCacheByThread() {
    LocalCachedMapOptions<Integer, Person> objectLocalCachedMapOptions = LocalCachedMapOptions
      .<Integer, Person>defaults()
      .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
      .cacheSize(2);


    RLocalCachedMap<Integer, Person> testLocalCacheMap = redissonClient.getLocalCachedMap("testLocalCacheMap", objectLocalCachedMapOptions);
    testLocalCacheMap.put(1, Person.of("1", "小明", "河南"));
    testLocalCacheMap.put(2, Person.of("2", "小大", "河南"));

    testLocalCacheMap.put(3, Person.of("3", "小张", "河南"));

    testLocalCacheMap.put(4, Person.of("4", "小李", "河南"));

    testLocalCacheMap.put(5, Person.of("5", "小王", "河南"));

    testLocalCacheMap.put(6, Person.of("6", "小小", "河南"));

    System.out.println(testLocalCacheMap.get(1));
    System.out.println(testLocalCacheMap.get(2));
    log.info("test");
  }

  /**
   * 分布式计数器
   */
  @Test
  public void testAtomicLong() {
    new Thread(this::testAtomicLongCaseThread, "T-A").start();
    new Thread(this::testAtomicLongCaseThread, "T-B").start();
    System.out.println();
  }

  private void testAtomicLongCaseThread() {
    RAtomicLong testAtomicLong = redissonClient.getAtomicLong("testAtomicLong");
    long value = testAtomicLong.addAndGet(1);
    System.out.println(value);
    System.out.println(value);
    log.info("testAtomicLong： {}", testAtomicLong);
  }

  /**
   * 分布式计数器实际应用
   */
  @Test
  public void testAtomicLongApply() {
    String key = "key";
    String realKey = String.format("MY_KEY:%s", key);
    RAtomicLong testAtomicLong = redissonClient.getAtomicLong(realKey);


    SimpleThreadFactory.newT(() -> {
      log.info("初始化是否成功：{}", testAtomicLong.compareAndSet(0, 1));
      log.info("获取值：{}", testAtomicLong.get());
    }).start();

    SimpleThreadFactory.newT(() -> {
      log.info("初始化是否成功：{}", testAtomicLong.compareAndSet(0, 1));
      log.info("获取值：{}", testAtomicLong.get());
    }).start();


    System.out.println(testAtomicLong.get());
    System.out.println(testAtomicLong.incrementAndGet());
    System.out.println(testAtomicLong.incrementAndGet());
    System.out.println(testAtomicLong.incrementAndGet());
    System.out.println(testAtomicLong.incrementAndGet());
    if (testAtomicLong.get() > 3) {
      log.info("remove key : {}", realKey);
      testAtomicLong.delete();
    }

    System.out.println();

  }
}

