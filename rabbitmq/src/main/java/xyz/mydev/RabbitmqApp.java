package xyz.mydev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZSP
 */
@SpringBootApplication(scanBasePackages = "xyz.mydev")
public class RabbitmqApp {
  public static void main(String[] args) {
    SpringApplication.run(RabbitmqApp.class, args);
  }
}
