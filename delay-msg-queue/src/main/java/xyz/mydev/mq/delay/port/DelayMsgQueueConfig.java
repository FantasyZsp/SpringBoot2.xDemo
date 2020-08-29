package xyz.mydev.mq.delay.port;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.mq.delay.repository.MqMessageDelay;

/**
 * @author ZSP
 */
@Configuration
public class DelayMsgQueueConfig {

  @Bean
  public RedisDelayMsgQueue<MqMessageDelay> delayMsgQueue(RedissonClient redissonClient) {
    return new RedisDelayMsgQueue<>(redissonClient);
  }

}
