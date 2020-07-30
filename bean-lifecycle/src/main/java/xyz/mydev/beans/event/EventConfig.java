package xyz.mydev.beans.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZSP
 */
@Configuration
public class EventConfig {
  @Bean
  public UserEventListener userEventListener() {
    return new UserEventListener();
  }

}
