package xyz.mydev.mq.delay;

import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.common.utils.JsonUtil;

/**
 * @author ZSP
 */
@Configuration
public class RedisLockJacksonConfig {
  @Bean
  public Config redissonSingleServerConfig() {
    Config config = new Config();
    config.setCodec(new JsonJacksonCodec(JsonUtil.objectMapper.copy()));
    return config;
  }

}
