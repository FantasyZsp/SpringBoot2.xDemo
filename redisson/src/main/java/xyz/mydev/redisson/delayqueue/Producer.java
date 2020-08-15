package xyz.mydev.redisson.delayqueue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import xyz.mydev.common.utils.ThreadUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.redisson.RedissonObject.prefixName;

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
  private final RScoredSortedSet<Order> sortedSet;


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
    sortedSet = redissonClient.getScoredSortedSet(prefixName("redisson_delay_queue_timeout", getName()));
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
   * 去重
   */
  public void produceDistinct(Order order) {
    // TODO 这种方式效率太低了，换一种O(1)的
    // 延时队列对应的集合
    redissonClient.getBucket(prefixName("redisson_delay_queue_timeout", queueName)).isExists();
    System.out.println(sortedSet.contains(order));

    if (delayedQueue.contains(order)) {
      log.info("msg already exists: {}", order);
      return;
    }

    delayedQueue.offer(order, order.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
    log.info("放入延时队列: {} {}", order.getId(), order.getInvalidTime());
  }


}
