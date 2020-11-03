package xyz.mydev.baidu.ai.face.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <Description:> <br>
 *
 * @author yangkai
 */
@Configuration
public class RateLimiterAutoConfiguration {

  @Bean
  public RateLimiterAspect rateLimiterAspect() {
    return new RateLimiterAspect();
  }
}
