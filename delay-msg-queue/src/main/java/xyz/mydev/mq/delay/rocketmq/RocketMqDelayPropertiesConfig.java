package xyz.mydev.mq.delay.rocketmq;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.mq.delay.rocketmq.properties.DelayQueueRocketMqProperties;

/**
 * 目前提供属性配置注入
 *
 * @author ZSP
 */
@Configuration
@EnableConfigurationProperties(DelayQueueRocketMqProperties.class)
public class RocketMqDelayPropertiesConfig {
}
