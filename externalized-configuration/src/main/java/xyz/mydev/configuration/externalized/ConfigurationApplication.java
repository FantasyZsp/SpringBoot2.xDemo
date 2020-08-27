package xyz.mydev.configuration.externalized;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.mydev.common.utils.JsonUtil;

/**
 * @author zhaosp
 */
@SpringBootApplication
@EnableConfigurationProperties(DelayQueueRocketMqProducerProperties.class)
public class ConfigurationApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ConfigurationApplication.class)
      .web(WebApplicationType.NONE)
      .run(args);

    DelayQueueRocketMqProducerProperties properties = applicationContext.getBean(DelayQueueRocketMqProducerProperties.class);
    System.out.println(JsonUtil.obj2StringPretty(properties));

    applicationContext.close();


  }
}
