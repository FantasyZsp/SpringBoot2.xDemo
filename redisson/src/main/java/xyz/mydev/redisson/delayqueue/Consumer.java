package xyz.mydev.redisson.delayqueue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZSP
 */
@Slf4j
@Getter
public class Consumer extends Thread {
  private transient static AtomicInteger counter = new AtomicInteger();
  private RedissonClient redissonClient;
  private String queueName;

  public Consumer(RedissonClient redissonClient) {
    this(redissonClient, "delay_queue");
  }

  public Consumer(RedissonClient redissonClient, String queueName) {
    super("consumer-" + counter.getAndIncrement());
    this.redissonClient = redissonClient;
    this.queueName = queueName;
  }

  @Override
  public void run() {
    RBlockingQueue<Order> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
    RDelayedQueue<Order> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
    log.info("开始获取.......");

    while (true) {
      Order order;
      try {
        order = blockingFairQueue.take();
        log.info("订单[{}]失效时间: {}: ", order.getId(), order.getInvalidTime());
      } catch (InterruptedException ignore) {
      } catch (Exception e) {
        delayedQueue.destroy();
        throw new RuntimeException(e);
      }
    }
  }
}
