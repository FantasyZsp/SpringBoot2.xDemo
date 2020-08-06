package xyz.mydev.beans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.mydev.beans.event.GenericEvent;
import xyz.mydev.beans.pojo.User;

/**
 * @author zhaosp
 */
@SpringBootApplication
public class BeanApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(BeanApplication.class, args);
    applicationContext.publishEvent(new GenericEvent<>(new User("id", "zsp")));
    applicationContext.close();


  }
}
