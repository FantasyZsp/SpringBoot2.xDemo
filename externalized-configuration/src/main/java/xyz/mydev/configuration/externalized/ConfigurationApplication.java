package xyz.mydev.configuration.externalized;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhaosp
 */
@SpringBootApplication
public class ConfigurationApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ConfigurationApplication.class)
      .web(WebApplicationType.SERVLET)
      .run(args);

    Object personProperties = applicationContext.getBean("personProperties");
    System.out.println(personProperties);

    applicationContext.close();


  }
}
