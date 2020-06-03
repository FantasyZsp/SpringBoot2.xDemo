package xyz.mydev.jackson.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ZSP  2018/12/8 18:21
 */
//@Configuration
public class JacksonConfig {
  @Bean("objectMapper")
  public ObjectMapper jacksonObjectMapper() {

    ObjectMapper objectMapper = new ObjectMapper();

    // 将对象的所有字段全部序列化
    objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

    // 忽略空bean转json的错误
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    // 忽略在json中存在但是在java对象中不存在对应属性的情况，防止错误
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 配置支持java8 Time
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    objectMapper.registerModule(javaTimeModule);

    objectMapper.registerModule(new Hibernate5Module());

    // 转换timestamp形式，默认为时间戳，false为instant
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    // 配置时间戳的单位
    objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    // map排序
    objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);

    // 全局小驼峰
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

    return objectMapper;
  }
}
