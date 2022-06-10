package xyz.mydev.redisson;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

public class RedisTemplateTest extends RootTest {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;


  @Test
  public void test() {
    redisTemplate.opsForHash().increment("hh", "key1", 1);
    redisTemplate.opsForHash().putAll("testNumberValue", Map.of("test1", 1));
    redisTemplate.opsForHash().increment("testNumberValue", "test1", 1);
  }

}
