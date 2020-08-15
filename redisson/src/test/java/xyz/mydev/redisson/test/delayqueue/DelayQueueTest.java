package xyz.mydev.redisson.test.delayqueue;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.redisson.RootTest;
import xyz.mydev.redisson.delayqueue.Consumer;
import xyz.mydev.redisson.delayqueue.Order;
import xyz.mydev.redisson.delayqueue.Producer;

import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class DelayQueueTest extends RootTest {
  @Autowired(required = false)
  private RedissonClient redissonClient;

  @Before
  public void before() {
    log.info("redis database：{}", redissonClient.getConfig().useSingleServer().getDatabase());
  }

  @After
  public void after() {
    redissonClient.shutdown();
  }


  /**
   * 没有去重
   */
  @Test
  public void testSameKey() {
    String queueName = "same_key";

    Consumer consumer = new Consumer(redissonClient, queueName);
    consumer.start();

    Order order = Order.ofSeconds(180);

    Producer producer = new Producer(redissonClient, queueName);

    System.out.println(producer.getBlockingFairQueue().contains(order));

    for (int i = 0; i < 99; i++) {
      order = Order.ofSeconds(20);
      producer.produce(order);
      producer.produce(order);
      ThreadUtils.join(1000);
    }

    ThreadUtils.sleepSeconds(10000);
  }

  @Test
  public void testProducer() {
    Thread producer = new Producer(redissonClient, 10, 10, 60);
    Thread producer2 = new Producer(redissonClient, 10, 20, 180);
    ThreadUtils.start(producer);
    ThreadUtils.startAndJoin(producer2);
    ThreadUtils.join(producer);
  }

  @Test
  public void testProducer2() {
    int max = Integer.MAX_VALUE;
    Thread producer = new Producer(redissonClient, max, 2, 10);
    Thread producer2 = new Producer(redissonClient, max, 5, 20);
    ThreadUtils.start(producer);
    ThreadUtils.start(producer2);

    Thread consumer = new Consumer(redissonClient);
    Thread consumer2 = new Consumer(redissonClient);
    ThreadUtils.start(consumer);
    ThreadUtils.startAndJoin(consumer2);

  }

  @Test
  public void testConsume() {
    Consumer consumer = new Consumer(redissonClient);
    ThreadUtils.startAndJoin(consumer);
  }

  @Test
  public void testConsumeEx() {
    log.info("testConsumeEx start...");
    RBlockingQueue<Order> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue");
    // 这里必须获取一下延时队列，否则blockingFairQueue.take()会一直阻塞
    redissonClient.getDelayedQueue(blockingFairQueue);
    Order order;
    try {
      order = blockingFairQueue.take();
      // 异常后，redis中被取出的这条数据会丢失
      log.info("获取到order: {} 后异常", order.getId());
      throw new RuntimeException();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testPeek() {
    log.info("testConsumeEx start...");
    RBlockingQueue<Order> blockingFairQueue = redissonClient.getBlockingQueue("delay_queue");
    // 这里必须获取一下延时队列，否则blockingFairQueue.take()会一直阻塞
    redissonClient.getDelayedQueue(blockingFairQueue);
    Order order = null;
    try {

      while (order == null) {
        order = blockingFairQueue.peek();
        if (order == null) {
          log.info("没有获取到...");
          ThreadUtils.join(100);
        }
      }

      log.info("获取到order: {}", order.getId());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getDelayedTime() {
    System.out.println(Order.ofSeconds(10).getDelay(TimeUnit.NANOSECONDS));
  }


}


