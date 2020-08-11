package xyz.mydev.redisson;

import org.junit.Before;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZSP
 */
public class RedissonClientTestApp extends RootTest {

  public RedissonClient redissonClient;


  @Before
  public void init() {
    if (redissonClient == null) {
      throw new RuntimeException("redissonClient has not been initialized");
    }
  }

  public RedissonClient getRedissonClient() {
    return redissonClient;
  }

  @Autowired(required = false)
  public void setRedissonClient(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }
}
