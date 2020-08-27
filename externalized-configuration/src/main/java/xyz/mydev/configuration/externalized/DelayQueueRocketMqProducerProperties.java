package xyz.mydev.configuration.externalized;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 注意，最好不要将此类本身作为 spring的bean，而由外部去注入此BEAN，防止序列化时出现递归导致StackOverFlow
 *
 * @author ZSP
 */
@Data
@ConfigurationProperties(prefix = "delay-queue.rocketmq")
public class DelayQueueRocketMqProducerProperties {
  private String nameServer;
  private Map<String, ProducerProperties> producers = new HashMap<>();
  private Map<String, ConsumerProperties> consumers = new HashMap<>();


  @Data
  public static class ProducerProperties {
    private String group;
    private String instanceName;
  }

  @Data
  public static class ConsumerProperties {
    private String topic;
    private String group;
    private String instanceName;
    private String tag;
    private String consumeThreadMin;
    private String consumeThreadMax;
  }

}
