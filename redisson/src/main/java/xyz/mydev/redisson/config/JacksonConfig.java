package xyz.mydev.redisson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author ZSP
 */
public class JacksonConfig {

  @Bean("redissonJavaTimeModule")
  public JavaTimeModule javaTimeModule() {
    return new JavaTimeModule();
  }

  @Bean("redissonJdk8Module")
  public Jdk8Module jdk8Module() {
    return new Jdk8Module();
  }

  @Bean("redissonObjectMapper")
  public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder, JavaTimeModule javaTimeModule, Jdk8Module jdk8Module) {
    SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().setFailOnUnknownId(false);
    ObjectMapper objectMapper = builder
      .createXmlMapper(false)
      .modules(javaTimeModule, jdk8Module)
      .filters(simpleFilterProvider)
      .build();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper;
  }

}
