package xyz.mydev.redisson.test.object;

import org.junit.Test;
import org.redisson.api.RScoredSortedSet;
import xyz.mydev.redisson.RedissonClientTestApp;
import xyz.mydev.redisson.delayqueue.Order;

/**
 * @author ZSP
 */
public class SetTest extends RedissonClientTestApp {
  @Test
  public void testSet() {
    RScoredSortedSet<String> scoredSortedSet = redissonClient.getScoredSortedSet("scoredSortedSet");

    scoredSortedSet.add(1.1, "test");
    scoredSortedSet.add(1.2, "test");
    scoredSortedSet.add(1.3, "test");
    scoredSortedSet.add(1.4, "test");
    scoredSortedSet.add(2, "甲");
    scoredSortedSet.add(3, "乙");
    scoredSortedSet.add(33, "丙");
    scoredSortedSet.add(44, "丁");
    scoredSortedSet.add(1.4, "tes111t");

    System.out.println(scoredSortedSet.pollFirst());
    System.out.println(scoredSortedSet.pollLast());

  }

  @Test
  public void testSet2() {
    RScoredSortedSet<Order> scoredSortedSet = redissonClient.getScoredSortedSet("scoredSortedSetOrder");


    Order order = Order.ofSeconds(10);
    scoredSortedSet.add(1.1, order);
    scoredSortedSet.add(1.2, order);
    scoredSortedSet.add(2, Order.ofSeconds(20));
    scoredSortedSet.add(3, Order.ofSeconds(30));
    scoredSortedSet.add(33, Order.ofSeconds(10));
    scoredSortedSet.add(44, Order.ofSeconds(10));
    scoredSortedSet.add(44, Order.ofSeconds(10));
    scoredSortedSet.add(77, Order.ofSeconds(10));
    scoredSortedSet.add(777, Order.ofSeconds(10));
    scoredSortedSet.add(777, Order.ofSeconds(10));
    scoredSortedSet.add(7777, Order.ofSeconds(10));
    scoredSortedSet.add(7777, Order.ofSeconds(10));
    scoredSortedSet.add(77777777, Order.ofSeconds(10));
    scoredSortedSet.add(77772, Order.ofSeconds(10));
    scoredSortedSet.add(77777777, Order.ofSeconds(10));
    scoredSortedSet.add(477777774, Order.ofSeconds(10));

//    System.out.println(scoredSortedSet.pollFirst());
//    System.out.println(scoredSortedSet.pollLast());

  }
}
