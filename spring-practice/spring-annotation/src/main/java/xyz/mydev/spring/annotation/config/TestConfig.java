package xyz.mydev.spring.annotation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZSP
 */
@Configuration
@Slf4j
public class TestConfig {
  public TestConfig() {
    log.info("TestConfig instant");
  }


  @Bean
  public String testStringBean() {
    log.info("testStringBean instant");
    return "testStringBean";
  }

  @Configuration
  @Slf4j
  static class TestInner444Config {
    @Bean
    public String test444StringBean() {
      log.info("test444StringBean instant");
      return "test444StringBean";
    }
  }
}
