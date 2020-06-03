package xyz.mydev.redisson.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author ZSP
 */
@Slf4j
public class DelayQueueTest2 {

  private RedissonClient redissonClient;

  @Before
  public void before() {

    Config config = new Config();
//    ClusterServersConfig clusterServersConfig = config.useClusterServers();
//    clusterServersConfig.
    Redisson.create();

    log.info("redis database：{}", redissonClient.getConfig().useSingleServer().getDatabase());
  }

  @After
  public void after() {
    redissonClient.shutdown();
  }

  @Test
  public void test() {

  }

}


