package xyz.mydev.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
public class SpringFactoryDemo implements ApplicationRunner {

  public SpringFactoryDemo() {
    log.info("SpringFactoryDemo instant");
  }

  @Autowired
  private BusinessService businessService;

  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(SpringFactoryDemo.class, args);

    run.close();

  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    businessService.test();
  }
}
