package com.sishu;

import xyz.mydev.redis.lock.annotation.RedisLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * @author ZSP
 */
@Service
public class BusinessService {


  private final RedissonClient redissonClient;

  public BusinessService(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @RedisLock(key = "'test'")
  public void test() {
    System.out.println("testxxxxxxxxxxxxxxxx");

  }

}
