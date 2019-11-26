package xyz.mydev.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ZSP
 */
@SpringBootApplication(scanBasePackages = "xyz.mydev")
@EnableTransactionManagement(proxyTargetClass = true)
public class TransactionDemoApp {
  public static void main(String[] args) {
    SpringApplication.run(TransactionDemoApp.class, args);
  }
}
