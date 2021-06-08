package xyz.mydev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhao
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "xyz.mydev.controller.feign")
public class CommonsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CommonsApplication.class, args);
  }
}
