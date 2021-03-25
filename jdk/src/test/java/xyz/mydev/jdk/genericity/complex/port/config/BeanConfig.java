package xyz.mydev.jdk.genericity.complex.port.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mydev.jdk.genericity.complex.msg.DelayMessage;
import xyz.mydev.jdk.genericity.complex.msg.SimpleStringMessage;
import xyz.mydev.jdk.genericity.complex.msg.TxMessage;
import xyz.mydev.jdk.genericity.complex.port.DefaultPorter;
import xyz.mydev.jdk.genericity.complex.port.GenericPorter;
import xyz.mydev.jdk.genericity.complex.route.DelayMessagePorterRouter;

/**
 * @author ZSP
 */
@Configuration
public class BeanConfig {

  @Bean
  public DefaultPorter defaultPorter() {
    return new DefaultPorter();
  }

  @Bean
  public DefaultPorter defaultPorter2() {
    return new DefaultPorter();
  }

  @Bean
  public GenericPorter genericPorter() {
    return new GenericPorter();
  }

  @Bean
  public GenericPorter<?> genericPorter2() {
    return new GenericPorter<>();
  }

  @Bean
  public GenericPorter<DelayMessagePorterRouter.PersonMessage> genericPersonPorter() {
    return new GenericPorter<>();
  }

  @Bean
  public GenericPorter<DelayMessage> genericDelayMessagePorter() {
    return new GenericPorter<>();
  }

  @Bean
  public GenericPorter<TxMessage> genericTxMessagePorter() {
    return new GenericPorter<>();
  }

  @Bean
  public GenericPorter<SimpleStringMessage> genericSimpleStringMessagePorter() {
    return new GenericPorter<>();
  }
}
