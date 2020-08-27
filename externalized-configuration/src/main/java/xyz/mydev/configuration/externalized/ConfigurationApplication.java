package xyz.mydev.configuration.externalized;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.configuration.externalized.properties.DelayQueueRocketMqProperties;

/**
 * @author zhaosp
 */
@SpringBootApplication
@EnableConfigurationProperties(DelayQueueRocketMqProperties.class)
public class ConfigurationApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ConfigurationApplication.class)
      .web(WebApplicationType.NONE)
      .run(args);

    DelayQueueRocketMqProperties properties = applicationContext.getBean(DelayQueueRocketMqProperties.class);
    System.out.println(JsonUtil.obj2StringPretty(properties));

    applicationContext.close();


  }
}
