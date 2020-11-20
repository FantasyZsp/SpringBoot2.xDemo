package xyz.mydev.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZSP
 */
@Configuration
@Slf4j
public class Test222Config {
  public Test222Config() {
    log.info("Test222Config instant");
  }

  @Bean
  public String test222StringBean() {
    log.info("test222StringBean instant");
    return "test222StringBean";
  }

  @Configuration
  @Slf4j
  static class TestInner333Config {
    @Bean
    public String test333StringBean() {
      log.info("test333StringBean instant");
      return "test333StringBean";
    }
  }
}
