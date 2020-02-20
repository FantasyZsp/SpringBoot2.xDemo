package xyz.mydev.jackson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig2 {


  @Bean
  public Jdk8Module jdk8Module() {
    return new Jdk8Module();
  }

  @Bean("serviceObjectMapper")
  public ObjectMapper serviceObjectMapper(Jackson2ObjectMapperBuilder builder, Jdk8Module jdk8Module) {
    SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().setFailOnUnknownId(false);
    return builder
      .createXmlMapper(false)
      .modules(jdk8Module)
      .filters(simpleFilterProvider)
      .build();
  }
}
