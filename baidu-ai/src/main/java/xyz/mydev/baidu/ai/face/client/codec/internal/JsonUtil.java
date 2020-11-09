package xyz.mydev.baidu.ai.face.client.codec.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ZSP
 */
public class JsonUtil {

  private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
  public static final ObjectMapper BASE_OBJECT_MAPPER = new ObjectMapper();

  static {
    // 将对象的所有字段全部序列化
    BASE_OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);

    // 忽略空bean转json的错误
    BASE_OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    // 忽略在json中存在但是在java对象中不存在对应属性的情况，防止错误
    BASE_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 配置支持java8 Instant
    BASE_OBJECT_MAPPER.registerModule(new JavaTimeModule());

    // 转换timestamp形式，默认为时间戳，false为instant
    BASE_OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    // 配置时间戳的单位
    BASE_OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    // map排序
    BASE_OBJECT_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
  }

  public static final ObjectMapper LOWER_CAMEL_CASE_OBJECT_MAPPER = BASE_OBJECT_MAPPER.copy().setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
  public static final ObjectMapper SNAKE_CASE_OBJECT_MAPPER = BASE_OBJECT_MAPPER.copy().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

  @SuppressWarnings("Duplicates")
  public static <T> String obj2String(T obj, ObjectMapper objectMapper) {

    if (null == obj) {
      return null;
    }

    try {
      return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      logger.warn("Parse object to String error", e);
      return null;
    }
  }

  @SuppressWarnings("Duplicates")
  public static <T> String obj2StringSnakeCase(T obj) {
    return obj2String(obj, SNAKE_CASE_OBJECT_MAPPER);
  }

  @SuppressWarnings("Duplicates")
  public static <T> String obj2StringLowerCamelCase(T obj) {
    return obj2String(obj, LOWER_CAMEL_CASE_OBJECT_MAPPER);
  }

  @SuppressWarnings("unchecked")
  public static <T> T string2Obj(String str, Class<T> clazz, ObjectMapper objectMapper) {

    if (str == null || "".equals(str) || clazz == null) {
      return null;
    }
    try {
      return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
    } catch (Exception e) {
      logger.warn("Parse String to object error", e);
      return null;
    }
  }

  public static <T> T string2ObjSnakeCase(String str, Class<T> clazz) {
    return string2Obj(str, clazz, SNAKE_CASE_OBJECT_MAPPER);
  }

  public static <T> T string2ObjLowerCamelCase(String str, Class<T> clazz) {
    return string2Obj(str, clazz, LOWER_CAMEL_CASE_OBJECT_MAPPER);
  }


}

