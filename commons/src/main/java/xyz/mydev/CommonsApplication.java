package xyz.mydev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.mydev.externalizedconfiguration.GirlProperties;
import xyz.mydev.externalizedconfiguration.PersonProperties;

/**
 * @author zhao
 */
@SpringBootApplication
public class CommonsApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(CommonsApplication.class, args);

    GirlProperties girlProperties = applicationContext.getBean(GirlProperties.class);
    PersonProperties personProperties = applicationContext.getBean(PersonProperties.class);
    System.out.println(girlProperties);
    System.out.println(personProperties);
  }
}
