package xyz.mydev.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 参考 https://juejin.im/post/5b2fb328f265da59a36e3f48
 *
 * @author ZSP
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractGuavaCache<K, V> {


  private static final int MAX_SIZE = 1000;
  private static final int EXPIRE_TIME = 10;
  /**
   * 用于初始化cache的参数及其缺省值
   */
  private static final int DEFAULT_SIZE = 100;

  private int maxSize = MAX_SIZE;

  private int expireTime = EXPIRE_TIME;
  /**
   * 时间单位（分钟）
   */
  private TimeUnit timeUnit = TimeUnit.MINUTES;
  /**
   * Cache初始化或被重置的时间
   */
  private LocalDateTime resetTime;

  /**
   * 分别记录历史最多缓存个数及时间点
   */
  private long highestSize = 0;
  private LocalDateTime highestTime;

  private volatile LoadingCache<K, V> cache;

  public LoadingCache<K, V> getCache() {
    //使用双重校验锁保证只有一个cache实例
    if (cache == null) {
      synchronized (this) {
        if (cache == null) {
          cache = CacheBuilder.newBuilder()
            //设置缓存容器的初始容量为100
            .initialCapacity(DEFAULT_SIZE)
            //缓存数据的最大条目
            .maximumSize(maxSize)
            //定时回收:缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
            .expireAfterWrite(expireTime, timeUnit)
            //启用统计->统计缓存的命中率等
            .recordStats()
            //设置缓存的移除通知
            .removalListener((notification) -> {
              if (log.isDebugEnabled()) {
                //...
              }
            })
            .build(new CacheLoader<K, V>() {
              @Override
              public V load(K key) throws Exception {
                return fetchData(key);
              }
            });
          this.resetTime = LocalDateTime.now();
          this.highestTime = LocalDateTime.now();
          if (log.isInfoEnabled()) {
            //...
          }
        }
      }
    }

    return cache;
  }

  protected abstract V fetchData(K key);

  /**
   * 从缓存中获取数据（第一次自动调用fetchData从外部获取数据），并处理异常
   *
   * @param key
   * @return Value
   * @throws ExecutionException
   */
  protected V getValue(K key) throws ExecutionException {
    V result = getCache().get(key);
    if (getCache().size() > highestSize) {
      highestSize = getCache().size();
      highestTime = LocalDateTime.now();
    }
    return result;
  }
}
