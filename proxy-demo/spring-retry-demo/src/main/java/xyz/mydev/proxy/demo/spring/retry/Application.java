package xyz.mydev.proxy.demo.spring.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author ZSP
 */
@SpringBootApplication
@EnableRetry
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }
}
