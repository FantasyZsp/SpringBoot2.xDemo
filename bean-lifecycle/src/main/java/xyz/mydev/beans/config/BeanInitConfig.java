package xyz.mydev.beans.config;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaosp
 */
@Configuration
public class BeanInitConfig {
  @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
  public ExampleBean exampleBean() {
    return new ExampleBean();
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(BeanInitConfig.class);

    try {
      Thread.currentThread().join(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    configApplicationContext.close();

  }
}
