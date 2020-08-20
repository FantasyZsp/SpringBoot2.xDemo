package xyz.mydev.redisson.delayqueue;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBucket;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import xyz.mydev.common.utils.ThreadUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZSP
 */
@Slf4j
@Getter
public class Producer extends Thread {
  private transient static AtomicInteger counter = new AtomicInteger();
  private final RedissonClient redissonClient;
  private final int count;
  private final int minSecond;
  private final int maxSecond;
  private final String queueName;

  private final RBlockingQueue<Order> blockingFairQueue;
  private final RDelayedQueue<Order> delayedQueue;
  private final DistinctCache setCache;


  public Producer(RedissonClient redissonClient, int count, int minSecond, int maxSecond) {
    this(redissonClient, "delay_queue", count, minSecond, maxSecond);
  }

  public Producer(RedissonClient redissonClient, String queueName) {
    this(redissonClient, queueName, Integer.MAX_VALUE, 10, 25);
  }

  public Producer(RedissonClient redissonClient, String queueName, int count, int minSecond, int maxSecond) {
    super("producer-" + counter.getAndIncrement());
    this.redissonClient = redissonClient;
    this.count = count;
    this.minSecond = minSecond;
    this.maxSecond = maxSecond;
    this.queueName = queueName;
    this.blockingFairQueue = redissonClient.getBlockingQueue(queueName);
    this.delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
    setCache = new DistinctCache(redissonClient, queueName);

  }

  @Override
  public void run() {
    log.info("开始生产.......");
    for (int i = 0; i < count; i++) {
      ThreadUtils.sleepSeconds(ThreadLocalRandom.current().nextInt(2, 10));
      produce();
    }
  }

  public void produce() {
    Order order = Order.ofSeconds(ThreadLocalRandom.current().nextInt(minSecond, maxSecond));
    produceDistinct(order);
  }

  public void produce(Order order) {
    produceDistinct(order);
  }


  /**
   * 去重，无法复用 ZSet redisson_delay_queue_timeout:{same_key}，因为数据被pack加密
   * // TODO 改为lua脚本，原子地判重和入队列，防止数据不一致。
   */
  public void produceDistinct(Order order) {

    if (setCache.contains(order.getId())) {
      log.info("msg already exists in setCache: {}", order);
      return;
    }

    // 这种方式效率太低了，换一种O(1)的。但无法复用 delayedQueue，只能用空间去换
    if (false) {
      if (delayedQueue.contains(order)) {
        log.info("msg already exists in delayedQueue: {}", order);
        return;
      }
    }

    delayedQueue.offer(order, order.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
    log.info("放入延时队列: {} {}", order.getId(), order.getInvalidTime());
  }

  /**
   * https://blog.csdn.net/SCGH_Fx/article/details/90437766
   */
  public static String convertByteBufToString(ByteBuf buf) {
    String str;
    // 处理堆缓冲区
    if (buf.hasArray()) {
      str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
    } else { // 处理直接缓冲区以及复合缓冲区
      byte[] bytes = new byte[buf.readableBytes()];
      buf.getBytes(buf.readerIndex(), bytes);
      str = new String(bytes, 0, buf.readableBytes());
    }
    return str;
  }

  static class DistinctCache {

    private final RedissonClient redissonClient;
    private final String queueName;

    DistinctCache(RedissonClient redissonClient, String queueName) {
      this.redissonClient = redissonClient;
      this.queueName = queueName;
    }

    public boolean contains(String key) {
      RBucket<Object> bucket = redissonClient.getBucket(generateKey(key), StringCodec.INSTANCE);
      return !bucket.trySet(key, 45, TimeUnit.SECONDS);
    }

    private String generateKey(String key) {
      return queueName + ":" + "distinctKey:" + key;
    }


  }

}
