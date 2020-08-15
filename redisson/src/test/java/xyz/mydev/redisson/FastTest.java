package xyz.mydev.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import xyz.mydev.common.utils.JsonUtil;

/**
 * @author ZSP
 */
public class FastTest {


  public static final RedissonClient redissonClient;

  static {
    Config config = new Config();
    config.setCodec(new JsonJacksonCodec(JsonUtil.objectMapper));
    config.useSingleServer()
      .setTimeout(1000000)
      .setDatabase(1)
      .setAddress("redis://127.0.0.1:6379")
    ;
    redissonClient = Redisson.create(config);
  }


  public RedissonClient getRedissonClient() {
    return redissonClient;
  }
}
