package xyz.mydev.redisson.delayqueue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import xyz.mydev.common.utils.ThreadUtils;

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
  private String consumingQueueName;

  public Consumer(RedissonClient redissonClient) {
    this(redissonClient, "delay_queue");
  }

  public Consumer(RedissonClient redissonClient, String queueName) {
    super("consumer-" + counter.getAndIncrement());
    this.redissonClient = redissonClient;
    this.queueName = queueName;
    this.consumingQueueName = queueName + ":consuming";
  }

  @Override
  public void run() {
    RBlockingQueue<Order> readyQueue = redissonClient.getBlockingQueue(queueName);
    RBlockingQueue<Order> consumingQueue = redissonClient.getBlockingQueue(consumingQueueName);
    RDelayedQueue<Order> delayedQueue = redissonClient.getDelayedQueue(consumingQueue);
    log.info("开始获取.......");

    while (true) {
      Order order = null;
      try {
        order = readyQueue.takeLastAndOfferFirstTo(consumingQueueName);
        log.info("订单[{}]失效时间: {}: ", order.getId(), order.getInvalidTime());
        ThreadUtils.sleepSeconds(10);
      } catch (InterruptedException ignore) {
      } catch (Exception e) {
        delayedQueue.destroy();
        throw new RuntimeException(e);
      } finally {
        // 具体业务中，是在消费成功或达到指定失败次数后删除
        if (order != null) {
          log.info("remove {} , result {}", order, consumingQueue.remove(order));
        }
      }
    }
  }
}
