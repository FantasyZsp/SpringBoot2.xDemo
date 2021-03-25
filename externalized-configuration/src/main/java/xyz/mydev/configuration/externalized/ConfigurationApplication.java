package xyz.mydev.configuration.externalized;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.configuration.externalized.properties.DelayQueueRocketMqProperties;
import xyz.mydev.configuration.externalized.properties.Student;

/**
 * @author zhaosp
 */
@SpringBootApplication
@EnableConfigurationProperties(DelayQueueRocketMqProperties.class)
public class ConfigurationApplication implements ApplicationRunner, ApplicationContextAware {

  static ApplicationContext applicationContext;

  public static void main(String[] args) {
    ConfigurableApplicationContext context = new SpringApplicationBuilder(ConfigurationApplication.class)
      .web(WebApplicationType.NONE)
      .run(args);

    DelayQueueRocketMqProperties properties = context.getBean(DelayQueueRocketMqProperties.class);
    System.out.println(JsonUtil.obj2StringPretty(properties));

    context.close();


  }


  @Override
  public void run(ApplicationArguments args) throws Exception {

    Student student = applicationContext.getBean(Student.class);
    System.out.println(student);


  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    ConfigurationApplication.applicationContext = applicationContext;

  }
}
