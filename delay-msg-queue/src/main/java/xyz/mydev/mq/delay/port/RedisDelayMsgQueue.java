package xyz.mydev.mq.delay.port;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonObject;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBucket;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import xyz.mydev.mq.delay.repository.MqMessageDelay;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * delayQueueName redis中延时队列基础名字/消息命名空间
 *
 * @author ZSP
 */
@Slf4j
public class RedisDelayMsgQueue<E extends MqMessageDelay> implements DelayMsgQueue<E> {

  /**
   * 就绪队列。当延时队列就绪时，会原子地将元素转移到此队列
   */
  private final RedissonClient redissonClient;
  private final String readyQueueName;
  private final RBlockingQueue<E> readyQueue;
  private final RBlockingQueue<E> consumingQueue;
  private final RDelayedQueue<E> rDelayedQueue;
  private final DistinctCache distinctCache;

  /**
   * redisson ZSet名字，用于倒计时
   */
  private final String timeoutSetName;
  /**
   * redisson List字，用于存储完整消息
   */
  private final String delayQueueName;
  /**
   * redisson 订阅发布频道名字，用于通知延时队列消费者
   */
  private final String channelName;


  public RedisDelayMsgQueue(RedissonClient redissonClient, String readyQueueName, String distinctCacheKeyPrefix) {
    this.redissonClient = redissonClient;
    this.readyQueueName = readyQueueName;
    this.readyQueue = redissonClient.getBlockingQueue(readyQueueName);
    this.consumingQueue = redissonClient.getBlockingQueue(readyQueueName + ":consuming");

    this.rDelayedQueue = redissonClient.getDelayedQueue(readyQueue);
    this.distinctCache = new DistinctCache(redissonClient, readyQueueName, distinctCacheKeyPrefix);


    this.channelName = RedissonObject.prefixName("redisson_delay_queue_channel", readyQueueName);
    this.timeoutSetName = RedissonObject.prefixName("redisson_delay_queue_timeout", readyQueueName);
    this.delayQueueName = RedissonObject.prefixName("redisson_delay_queue", readyQueueName);
  }

  public RedisDelayMsgQueue(RedissonClient redissonClient) {
    this(redissonClient, "aurora:commonDelayQueue", "distinctKey");
  }


  private static final String ATOMIC_OFFER_WITH_DISTINCT_CACHE_LUA_SCRIPT =
    "  if redis.call('setnx', KEYS[5], 1) == 1 then " +
      " redis.call('expire', KEYS[5], ARGV[4]);" +
      " local value = struct.pack('dLc0', tonumber(ARGV[2]), string.len(ARGV[3]), ARGV[3]);" +
      " redis.call('zadd', KEYS[2], ARGV[1], value);" +
      " redis.call('rpush', KEYS[3], value);" +
      " local v = redis.call('zrange', KEYS[2], 0, 0); " +
      " if v[1] == value then " +
      "  redis.call('publish', KEYS[4], ARGV[1]); " +
      " end;" +
      " return 1;" +
      "else " +
      " return 0 " +
      "end;";


  /**
   * 消息投递
   */
  @Override
  public boolean put(E msg) {
    boolean isNotExists = atomicOfferWithDistinctCache(msg);
    if (isNotExists) {
      log.info("put msg into redis delay queue [{}] msgId [{}] at [{}]", delayQueueName, msg.getId(), msg.getTime());
    } else {
      log.info("msg is already in redis delay queue [{}], msgId [{}]", delayQueueName, msg.getId());
    }

    return isNotExists;
  }

  @Override
  public boolean contains(E msg) {
    return distinctCache.contains(msg.getId());
  }

  /**
   * 原子的从readyQueue取出并放入consumingQueue
   */
  @Override
  public E take() throws InterruptedException {
    return readyQueue.takeLastAndOfferFirstTo(consumingQueue.getName());
  }

  /**
   * 启动时将 consumingQueue元素转移到readyQueue头部重新消费。（不要走投递，因为投递时的去重机制会导致无法成功投递消息）
   * 情况A：如果在发送消息更新完本地消息后掉电，那么就会存在消息表中显示已发送，但实际只发送了半消息。后续流程需要依赖回查机制。回查会正确的投递消息。
   * 情况B：如果在发送消息时半消息也没发就掉电，那么就会存在就绪队列中无值但消费中队列有值的情况。此种情况在应用重启时load模块会加载到，但是load模块会交给porter处理，可能会因为去重缓存导致无法重新投递。
   *
   * <p>
   * 综上，需要重新投递到就绪队列，保证消息一定投递。对于情况A造成的重复投递，会在发送时对消息的状态检测被避免掉。
   * <p>
   * 需要解决问题：
   * 1. 分布式部署下，每个服务都会处理自己的消息，当服务A挂了后，B仍在运行，此时如果A来操作 consumingQueue 所有的元素，可能会导致其他服务删元素时返回了false。所以，需要解决服务于队列的关系。
   * 2. 1中删除带来的影响可以忽略不计。因为删除consumingQueue元素本身是个ack操作，为了确保msg一定被消费。此时依然操作所有，最坏的情况是重复投递，但会被消息状态给截断。
   * <p>
   * 采用2，直接操作所有。
   */
  @Override
  public void start() {
    E e;
    while ((e = consumingQueue.pollLastAndOfferFirstTo(readyQueue.getName())) != null) {
      log.info("transfer {}", e);
    }
    log.info("failover finish");
  }

  /**
   * 简单的入延时队列操作
   * 存在原子问题。当trySet成功后，没有入实际的延时队列，导致同个key在TTL内无法被投递。
   * 解决：
   * 1.改造redisson延时队列，使用 delayedQueue 方法判重，代价是O(n)
   * 2.故障恢复时，清空缓存
   * 3.原子地入缓存和延时队列 {@link RedisDelayMsgQueue#atomicOfferWithDistinctCache(xyz.mydev.mq.delay.repository.MqMessageDelay)}
   * 不解决：
   * 极致的性能，极小可能的投递失败。
   */
  public boolean simplePut(E msg) {
    boolean isNotExists = distinctCache.trySet(msg.getId());
    if (isNotExists) {
      rDelayedQueue.offerAsync(msg, msg.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
    }
    return isNotExists;
  }


  /**
   * 原子地操作缓存和延时队列
   *
   * @param msg 延时消息
   * @return true代表投递成功，没有重复的消息，false代表存在重复的消息，未能投递
   */
  public boolean atomicOfferWithDistinctCache(E msg) {

    Codec codec = rDelayedQueue.getCodec();
    String value;
    try {
      value = convertByteBufToString(codec.getValueEncoder().encode(msg));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    String distinctKey = distinctCache.generateKey(msg.getId());
    long delayInMs = msg.getDelay(TimeUnit.MILLISECONDS);
    long timeout = (System.currentTimeMillis() + delayInMs);
    long randomId = ThreadLocalRandom.current().nextLong();

    long cacheToLive = distinctCache.getTimeUnit().toSeconds(distinctCache.getTimeToLive());

    return redissonClient.getScript(new StringCodec())
      .eval(RScript.Mode.READ_WRITE,
        ATOMIC_OFFER_WITH_DISTINCT_CACHE_LUA_SCRIPT,
        RScript.ReturnType.BOOLEAN,
        List.of(readyQueueName, timeoutSetName, delayQueueName, channelName, distinctKey),
        timeout, randomId, value, cacheToLive);
  }


  @Override
  public void destroy() {
    log.info("RedisDelayMsgQueue destroying, [{}] msg left, [{}] ready-msg left, [{}] consumingQueue-msg left", getTargetQueue().size(), readyQueue.size(), consumingQueue.size());
  }

  /**
   * delayedQueue 为倒计时队列，就绪队列在 blockingFairQueue 中
   */
  @Override
  public Queue<E> getTargetQueue() {
    return rDelayedQueue;
  }

  /**
   * 从正在消费的队列中删除
   */
  @Override
  public boolean remove(E e) {
    return consumingQueue.remove(e);
  }


  private static class DistinctCache {

    private final RedissonClient redissonClient;
    private final String queueName;
    private final String keyPrefix;
    @Getter
    private final long timeToLive;
    @Getter
    private final TimeUnit timeUnit;

    DistinctCache(RedissonClient redissonClient, String queueName, String keyPrefix) {
      this(redissonClient, queueName, keyPrefix, 32, TimeUnit.MINUTES);
    }

    DistinctCache(RedissonClient redissonClient,
                  String queueName,
                  String keyPrefix, long timeToLive,
                  TimeUnit timeUnit) {
      this.redissonClient = redissonClient;
      this.queueName = queueName;
      this.keyPrefix = keyPrefix;
      this.timeToLive = timeToLive;
      this.timeUnit = timeUnit;
    }


    public boolean trySet(String key) {
      return trySet(key, timeToLive, timeUnit);
    }

    /**
     * 35分钟
     */
    public boolean trySet(String key, long timeToLive, TimeUnit timeUnit) {
      RBucket<String> bucket = redissonClient.getBucket(generateKey(key), StringCodec.INSTANCE);
      return bucket.trySet(key, timeToLive, timeUnit);
    }

    public boolean contains(String key) {
      RBucket<String> bucket = redissonClient.getBucket(generateKey(key), StringCodec.INSTANCE);
      return bucket.isExists();
    }


    public String generateKey(String key) {
      return queueName + ":" + keyPrefix + ":" + key;
    }
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
}
