package xyz.mydev.junit4;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhao
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "person")
public class PersonProperties {
  private String app = "";
  private String name;
  private String age;
  private String address;
}
