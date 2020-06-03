package xyz.mydev.externalizedconfiguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhao
 */
@Data
@ConfigurationProperties(prefix = "person")
public class PersonProperties {
  private String app = "";
  private String name;
  private String age;
  private String address;
}
