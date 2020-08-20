package xyz.mydev.redisson.test.script;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.redisson.api.RScript;
import xyz.mydev.redisson.RedissonClientTestApp;
import xyz.mydev.redisson.delayqueue.Order;

import java.util.List;

/**
 * @author ZSP
 */
@Slf4j
public class LuaScriptTest extends RedissonClientTestApp {


  @Test
  public void simpleLua() {
    String luaScript = "local s = redis.call('exists',KEYS[1]);return s;";

    Object result = redissonClient.getScript().eval(RScript.Mode.READ_WRITE, luaScript, RScript.ReturnType.BOOLEAN, List.of("distinctByLua"));
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

    Object result = redissonClient.getScript().eval(RScript.Mode.READ_WRITE, luaScript, RScript.ReturnType.BOOLEAN, List.of(generateKey("distinctByLua", order.getId()), "luaList"), 45, order);
    log.info("result : {}", result);
  }

  private static String generateKey(String prefix, String key) {
    if (StringUtils.isNotBlank(prefix)) {
      return prefix + ":" + "distinctKey:" + key;
    }
    return key;

  }

}
