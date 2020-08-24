package xyz.mydev.redisson.test.delayqueue;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.redisson.RedissonClientTestApp;
import xyz.mydev.redisson.delayqueue.Consumer;
import xyz.mydev.redisson.delayqueue.Order;
import xyz.mydev.redisson.delayqueue.Producer;

import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class DelayQueueTest extends RedissonClientTestApp {

  @Before
  public void before() {
    log.info("redis database：{}", redissonClient.getConfig().useSingleServer().getDatabase());
  }

  @After
  public void after() {
    redissonClient.shutdown();
  }


  /**
   * 本身没有去重机制
   */
  @Test
  public void testSameKey() {
    String queueName = "same_key";

    Consumer consumer = new Consumer(redissonClient, queueName);
    consumer.start();

    Order order;

    Producer producer = new Producer(redissonClient, queueName);

    for (int i = 0; i < 9999; i++) {
      order = Order.ofSeconds(200000);
      producer.produce(order);
      producer.produce(order);
      ThreadUtils.join(1000);
    }

    ThreadUtils.sleepSeconds(10000);
  }

  @Test
  public void testProduceOne() {
    String queueName = "same_key";

    Producer producer = new Producer(redissonClient, queueName);
    producer.produce(Order.ofSeconds(200));


    ThreadUtils.sleepSeconds(10000);
  }

  @Test
  public void testConsumeOne() {
    String queueName = "same_key";


    Producer producer = new Producer(redissonClient, queueName);
    producer.produce(Order.ofSeconds(10));

    Consumer consumer = new Consumer(redissonClient, queueName);
    ThreadUtils.startAndJoin(consumer);
  }

  @Test
  public void testLoopConsumeOne() {
    String queueName = "same_key";


    Producer producer = new Producer(redissonClient, queueName);
    Consumer consumer = new Consumer(redissonClient, queueName);
    ThreadUtils.start(consumer);
    int i = 0;
    while (++i < 20) {
      producer.produce(Order.ofSeconds(10));

    }
    ThreadUtils.join(consumer);

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
    RBlockingQueue<Order> blockingFairQueue = redissonClient.getBlockingQueue("same_key");
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


  /**
   * 不会从队列中删除元素
   * 但是会拿到没到期的元素
   */
  @Test
  public void testPeek() {
    log.info("testConsumeEx start...");
    RBlockingQueue<Order> blockingFairQueue = redissonClient.getBlockingQueue("same_key");
    // 这里必须获取一下延时队列，否则blockingFairQueue.take()会一直阻塞
    RDelayedQueue<Order> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);

    Order input = Order.ofSeconds(1000);
    delayedQueue.offer(input, input.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);


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


