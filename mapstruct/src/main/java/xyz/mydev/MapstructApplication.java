package xyz.mydev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhao
 */
@SpringBootApplication
@EnableSwagger2
public class MapstructApplication {
  public static void main(String[] args) {
    SpringApplication.run(MapstructApplication.class, args);
  }
}
