package xyz.mydev.cache.guava;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考 https://juejin.im/post/5b2fb328f265da59a36e3f48
 *
 * @author ZSP
 */
@Slf4j
public class DefaultGuavaCacheManager {

  @Getter
  private static AbstractGuavaCache<String, Object> cacheWrapper;

  /**
   * 初始化缓存容器
   */
  public static void initGuavaCache() {
    cacheWrapper = DefaultGuavaCache.getInstance();
  }

  public static void put(String key, Object value) {
    cacheWrapper.getCache().put(key, value);
  }

  public static void invalidate(String key) {
    cacheWrapper.getCache().invalidate(key);
  }

  /**
   * 批量清除
   */
  public static void invalidateAll(Iterable<?> keys) {
    cacheWrapper.getCache().invalidateAll(keys);
  }

  /**
   * 清除所有缓存项 ： 慎用
   */
  public static void invalidateAll() {
    cacheWrapper.getCache().invalidateAll();
  }

  public static Object get(String key) {
    try {
      return cacheWrapper.getCache().get(key);
    } catch (Exception e) {
      log.error("Failed to get value from guava cache;", e);
    }
    return null;
  }

  /**
   * 使用静态内部类实现一个默认的缓存，委托给manager来管理
   * <p>
   * DefaultGuavaCache 使用一个简单的单例模式
   */
  private static class DefaultGuavaCache<K, V> extends AbstractGuavaCache<K, V> {

    @Override
    protected V fetchData(K key) {
      return null;
    }

    public static <K, V> AbstractGuavaCache<K, V> getInstance() {
      return new DefaultGuavaCache<K, V>();
    }

  }

}
