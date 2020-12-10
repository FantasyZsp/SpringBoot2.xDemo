package com.sishu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ZSP
 */
@SpringBootApplication
@Slf4j
public class PackageTestApp implements ApplicationRunner {

  public PackageTestApp() {
    log.info("SpringFactoryDemo instant");
  }

  @Autowired
  private BusinessService businessService;

  public static void main(String[] args) {
    ConfigurableApplicationContext run = SpringApplication.run(PackageTestApp.class, args);

    run.close();

  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    businessService.test();
  }
}
