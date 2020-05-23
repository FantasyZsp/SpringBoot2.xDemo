package xyz.mydev.jdk.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author ZSP
 */
public class JsonUtil {
  public static ObjectMapper objectMapper = new ObjectMapper();

  static {
    // 将对象的所有字段全部序列化
    objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

    // 忽略空bean转json的错误
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    // 统一日期格式
    // objectMapper.setDateFormat(XXX);

    // 忽略在json中存在但是在java对象中不存在对应属性的情况，防止错误
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 配置支持java8 Instant
    objectMapper.registerModule(new JavaTimeModule());

    // 转换timestamp形式，默认为时间戳，false为instant
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    // 配置时间戳的单位
    objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    // map排序
    objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
  }
}
