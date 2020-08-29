package xyz.mydev.mq.delay.load;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.mq.delay.SimpleDelayService;

/**
 * @author ZSP
 */
@Configuration
@Slf4j
public class CheckPointServiceConfig {

  @Bean(initMethod = "schedule", destroyMethod = "stop")
  @ConditionalOnProperty(prefix = "redisson", name = "address")
  public RedisCheckPointServiceImpl redisCheckPointService(RedissonClient redissonClient,
                                                           SimpleDelayService simpleDelayService) {
    log.warn("redisCheckPointService init");
    return new RedisCheckPointServiceImpl(redissonClient, simpleDelayService);
  }

  @Bean
  @ConditionalOnMissingBean({CheckPointService.class})
  @ConditionalOnProperty(prefix = "redisson", name = "address", matchIfMissing = true)
  public LocalCheckPointServiceImpl localCheckPointService(SimpleDelayService simpleDelayService) {
    log.warn("localCheckPointService init");
    return new LocalCheckPointServiceImpl(simpleDelayService);
  }


}
