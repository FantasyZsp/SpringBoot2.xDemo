package xyz.mydev.redisson.delayqueue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
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
    delayedQueue.offer(order, order.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
    log.info("放入延时队列: {} {}", order.getId(), order.getInvalidTime());
  }

  public void produce(Order order) {
    delayedQueue.offer(order, order.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
    log.info("放入延时队列: {} {}", order.getId(), order.getInvalidTime());
  }


}
