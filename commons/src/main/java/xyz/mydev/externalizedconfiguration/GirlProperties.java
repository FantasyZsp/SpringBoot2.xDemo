package xyz.mydev.externalizedconfiguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZSP
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "girl")
public class GirlProperties {
  private String cupSize;
  private Integer age;
}
