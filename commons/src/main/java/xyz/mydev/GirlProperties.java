package xyz.mydev;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zsp
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "girl")
public class GirlProperties {
  private String cupSize;
  private Integer age;
}
