package xyz.mydev.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZSP
 */
@SpringBootApplication(scanBasePackages = "xyz.mydev")
public class TransactionDemoApp {
  public static void main(String[] args) {
    SpringApplication.run(TransactionDemoApp.class, args);
  }
}
