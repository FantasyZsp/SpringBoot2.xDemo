package xyz.mydev.spring.annotation;

import xyz.mydev.redis.lock.annotation.RedisLock;
import org.springframework.stereotype.Service;

/**
 * @author ZSP
 */
@Service
public class BusinessService {


  @RedisLock(key = "test")
  public void test() {

  }

}
