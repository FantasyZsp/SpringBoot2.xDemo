package xyz.mydev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author  zhao
 * @date  2018/07/27 15:55
 * @description
 */
@SpringBootApplication
@EnableSwagger2
public class StartUpApplication {
  public static void main(String[] args) {
    SpringApplication.run(StartUpApplication.class, args);
  }
}
