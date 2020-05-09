package xyz.mydev.practice;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * final与volatile结合，实现一个不可变对象，包装分散数据在并发场景的数据一致性
 * <p>
 * <p>
 * key value 缓存了一对键值对，外部在缓存未命中时会写入一对新的值。
 * 此类可保证在并发场景下key和value的一致性，但不保证在实际并发顺序下是最新的一对。要求业务也不需要关注是否最新，只关注缓存中key和value的一致性。
 *
 * @author ZSP
 */
@Immutable
@ThreadSafe
public final class KeyValueCacheDemo {
  private final String key;
  private final String value;

  private KeyValueCacheDemo(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getValue(String key) {

    if (key == null || !key.equals(this.key)) {
      return null;
    }
    return this.value;
  }
}
