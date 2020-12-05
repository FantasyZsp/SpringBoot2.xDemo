package xyz.mydev.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import xyz.mydev.ComponentNeedImport;

/**
 * @author ZSP
 */
@SpringBootApplication
@Slf4j
@Import(ComponentNeedImport.class)
public class SpringFactoryDemo {

  public SpringFactoryDemo() {
    log.info("SpringFactoryDemo instant");
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(SpringFactoryDemo.class, args);
    run.close();
  }
}
