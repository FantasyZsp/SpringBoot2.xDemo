package xyz.mydev.spring.annotation.config;

import com.sishu.redis.lock.annotation.RedissonClientAutoConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
