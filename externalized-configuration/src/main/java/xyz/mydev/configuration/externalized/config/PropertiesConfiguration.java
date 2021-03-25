package xyz.mydev.configuration.externalized.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.configuration.externalized.properties.Student;

/**
 * @author ZSP
 */
@Configuration
public class PropertiesConfiguration {


  @Bean
  @ConfigurationProperties(prefix = "student")
  public Student student() {
    return new Student();
  }



}
