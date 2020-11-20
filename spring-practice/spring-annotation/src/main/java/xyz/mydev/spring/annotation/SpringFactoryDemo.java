package xyz.mydev.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ZSP
 */
@SpringBootApplication
@Slf4j
public class SpringFactoryDemo {

  public SpringFactoryDemo() {
    log.info("SpringFactoryDemo instant");
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(SpringFactoryDemo.class, args);
    run.close();
  }
}
