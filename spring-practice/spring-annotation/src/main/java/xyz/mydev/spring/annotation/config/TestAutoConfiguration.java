package xyz.mydev.spring.annotation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.redis.lock.annotation.RedissonClientAutoConfig;

/**
 * @author ZSP
 */
@Configuration
@AutoConfigureBefore(value = RedissonClientAutoConfig.class)
@Slf4j
public class TestAutoConfiguration {
  public TestAutoConfiguration() {
    log.info("TestAutoConfiguration instant");
  }

  @Bean
  public String autoConfigString() {
    log.info("autoConfigString instant");
    return "autoConfigString";
  }
}
