package xyz.mydev.redisson.test.script;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.redisson.RedissonDelayedQueue;
import org.redisson.RedissonObject;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RScript;
import org.redisson.client.codec.Codec;
import xyz.mydev.common.utils.ThreadUtils;
import xyz.mydev.redisson.RedissonClientTestApp;
import xyz.mydev.redisson.delayqueue.Order;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class LuaScriptTest extends RedissonClientTestApp {


  @Test
  public void simpleLua() {
    String luaScript = "local s = redis.call('exists',KEYS[1]);return s;";
    Object result = redissonClient.getScript().
      eval(RScript.Mode.READ_WRITE,
        luaScript,
        RScript.ReturnType.BOOLEAN,
        List.of("distinctByLua"));
    log.info("result : {}", result);
  }

  @Test
  public void simpleLua2() {
    String luaScript = "local value = struct.pack('dLc0', tonumber(ARGV[1]), string.len(ARGV[2]), ARGV[3]);" +
      "return string.len(ARGV[3]);";
    Order order = Order.ofSeconds(10);

    Object result = redissonClient.getScript()
      .eval(RScript.Mode.READ_WRITE,
        luaScript,
        RScript.ReturnType.STATUS,
        List.of("distinctByLua"), 1111, 1111, order);
    log.info("result : {}", result);
  }


  @Test
  public void composeLua() {
    // 如果不存在，将指定内容加入到list和缓存list中
    String luaScript = "if redis.call('setnx', KEYS[1], 1) == 1 then "
      + " redis.call('expire', KEYS[1], ARGV[1]);"
      + " redis.call('lpush',KEYS[2],ARGV[2]);"
      + " return 0;"

      + "else "
      +
      " return 1 end;";

    Order order = Order.ofSeconds(10);

    Object result = redissonClient.getScript()
      .eval(RScript.Mode.READ_WRITE,
        luaScript,
        RScript.ReturnType.BOOLEAN,
        List.of(generateKey("distinctByLua", order.getId()), "luaList"),
        45, order);
    log.info("result : {}", result);
  }


  @Test
  public void mockRedissonOfferAndDistinct() {
    String originalQueueName = "mockRedissonOfferAndDistinct";
    RBlockingQueue<Order> readyQueue = redissonClient.getBlockingQueue(originalQueueName);
    RedissonDelayedQueue<Order> rDelayedQueue = (RedissonDelayedQueue<Order>) redissonClient.getDelayedQueue(readyQueue);

    String channelName = RedissonObject.prefixName("redisson_delay_queue_channel", originalQueueName);
    String queueName = RedissonObject.prefixName("redisson_delay_queue", originalQueueName);
    String timeoutSetName = RedissonObject.prefixName("redisson_delay_queue_timeout", originalQueueName);

    Order order = Order.ofSeconds(10);
    order.setId(RandomStringUtils.random(10, false, true));

    long delayInMs = order.getDelay(TimeUnit.MILLISECONDS);
    Object timeout = (System.currentTimeMillis() + delayInMs);
    System.out.println(timeout);
    int distinctKeyTimeout = 500; // 45s

    long randomId = ThreadLocalRandom.current().nextLong();

    // 如果不存在，将指定内容加入到list和缓存list中
    String luaScript =
      "  if redis.call('setnx', KEYS[5], 1) == 1 then " +
        " redis.call('expire', KEYS[5], ARGV[4]);" +
        " local value = struct.pack('dLc0', tonumber(ARGV[2]), string.len(ARGV[3]), ARGV[3]);redis.call('zadd', KEYS[2], ARGV[1], value);redis.call('rpush', KEYS[3], value);local v = redis.call('zrange', KEYS[2], 0, 0); if v[1] == value then redis.call('publish', KEYS[4], ARGV[1]); end;" +
        " return 1;" +
        "else " +
        " return 0 " +
        "end;";

//
//    String luaScript =
//      " local value = ARGV[3];" +
//        " redis.call('setnx', KEYS[5], 1);  " +
//        " redis.call('expire', KEYS[5], ARGV[4]);" +
//        " redis.call('zadd', KEYS[2], ARGV[1], value);" +
//        " redis.call('rpush', KEYS[3], value);" +
//        " local v = redis.call('zrange', KEYS[2], 0, 0); " +
//        " if v[1] == value then " +
//        "   redis.call('publish', KEYS[4], ARGV[1]); " +
//        " end;" +
//        " return 1;";

    Codec codec = redissonClient.getConfig().getCodec();
    String value = null;
    try {
      value = String.valueOf(codec.getValueEncoder().encode(order));
    } catch (IOException e) {
      e.printStackTrace();
    }
    String distinctKey = generateKey("mockRedissonOfferByLua", order.getId());
    if (true) {

      Object result = redissonClient.getScript()
        .eval(RScript.Mode.READ_WRITE,
          luaScript,
          RScript.ReturnType.,
          List.of(originalQueueName, timeoutSetName, queueName, channelName, distinctKey),
          timeout, 1111, value, distinctKeyTimeout);
      log.info("mockRedissonOfferAndDistinct script eval result : {}", result);
    }
    rDelayedQueue.offer(order, order.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);

    ThreadUtils.sleepSeconds(10);

  }


  private static String generateKey(String prefix, String key) {
    if (StringUtils.isNotBlank(prefix)) {
      return prefix + ":" + "distinctKey:" + key;
    }
    return key;

  }

}
