package xyz.mydev.mq.delay.port;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.mq.delay.repository.MqMessageDelay;

import java.util.Queue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class LocalCacheDelayMsgQueue<E extends MqMessageDelay> implements DelayMsgQueue<E> {

  @Getter
  private final DelayQueue<E> delayQueue = new DelayQueue<>();

  private final LoadingCache<String, Object> distinctCache = getCaffeineInstance();

  private static final Object CACHE_VALUE = new Object();

  @Override
  public boolean put(E msg) {

    // 无需关注的并发，因为几率很小
    if (!contains(msg)) {
      distinctCache.put(msg.getId(), CACHE_VALUE);
      log.info("put msg into delay queue. msgId [{}] at [{}]", msg.getId(), msg.getTime());
      return delayQueue.offer(msg);
    } else {
      log.info("msg is already in delay queue. msgId [{}]", msg.getId());
      return false;
    }
  }

  @Override
  public boolean contains(MqMessageDelay msg) {
    return distinctCache.getIfPresent(msg.getId()) == null;
  }

  @Override
  public E take() throws InterruptedException {
    return delayQueue.take();
  }

  @Override
  public void start() {
    log.info("LocalCacheDelayMsgQueue starts to work");
  }

  public static LoadingCache<String, Object> getCaffeineInstance() {
    return Caffeine.newBuilder()
      .expireAfterWrite(45, TimeUnit.MINUTES)
      .recordStats()
      .removalListener(
        (k, v, cause) ->
          log.warn("removed key {} cause {}", k, cause))
      .build(key -> null);
  }

  @Override
  public void destroy() {

    log.info("LocalCacheDelayMsgQueue destroying, [{}] msg left", delayQueue.size());

    CacheStats stats = distinctCache.stats();

    log.info("distinctCache stats: {} ,size {}", stats, distinctCache.estimatedSize());
  }

  @Override
  public Queue<E> getTargetQueue() {
    return delayQueue;
  }

  /**
   * 不需要删除操作，因为 take已经删掉了
   */
  @Override
  public boolean remove(E e) {
    return true;
  }
}
