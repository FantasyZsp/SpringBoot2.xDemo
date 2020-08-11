package xyz.mydev.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import xyz.mydev.common.beans.dto.AddressDTO;
import xyz.mydev.common.beans.dto.PersonDTO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhao
 * @date 2018/08/01 20:32
 * @description JSON操作工具类
 */
public class JsonUtil {
  private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
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

  /**
   * @description Object2String
   * @author ZSP
   * @date 2018/8/1
   * @param: [obj]
   * @return: java.lang.String
   */
  public static <T> String obj2String(T obj) {
    if (obj == null) {
      return null;
    }
    try {
      return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      logger.warn("Parse object to String error", e);
      return null;
    }
  }

  public static <T> String obj2StringPretty(T obj) {
    if (obj == null) {
      return null;
    }
    try {
      return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (Exception e) {
      logger.warn("Parse object to String error", e);
      return null;
    }
  }

  /**
   * @description 简单对象反序列化
   * @author ZSP
   * @date 2018/8/1
   * @param: [str, clazz]
   * @return: T
   */

  public static <T> T string2Obj(String str, Class<T> clazz) {
    if (StringUtils.isEmpty(str) || clazz == null) {
      return null;
    }
    try {
      return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
    } catch (Exception e) {
      logger.warn("Parse String to object error", e);
      return null;
    }

  }

  /**
   * @param str           字符串
   * @param typeReference 转换的类型
   * @return T
   * @description 字符串反序列化为组合对象。传入类型和返回类型一致为T。
   * @author ZSP
   * @date 2018/8/1
   */
  public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
    if (StringUtils.isEmpty(str) || typeReference == null) {
      return null;
    }
    try {
      return (typeReference.getType().equals(String.class) ? (T) str : objectMapper.readValue(str, typeReference));
    } catch (Exception e) {
      logger.warn("Parse String to objectT error", e);
      return null;
    }

  }

  public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
    if (StringUtils.isEmpty(str) || collectionClass == null) {
      return null;
    }
    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    try {
      return objectMapper.readValue(str, javaType);
    } catch (Exception e) {
      logger.warn("Parse String to object error", e);
      return null;
    }

  }


  public static void main(String[] args) {

    AddressDTO add1 = new AddressDTO("str1", "post1");
    AddressDTO add2 = new AddressDTO("str2", "post2");
    PersonDTO person1 = new PersonDTO(1L, "name1", add1, Instant.now());
    PersonDTO person2 = new PersonDTO(2L, "name2", add2, Instant.now());
    String add1Str = obj2StringPretty(add1);
    String add2Str = obj2StringPretty(add1);
    String person1Str = obj2StringPretty(person1);
    String person2Str = obj2StringPretty(person2);

    logger.info("===========obj2String==============");
    logger.info("\n{}", add1Str);
    logger.info("\n{}", add2Str);
    logger.info("\n{}", person1Str);
    logger.info("\n{}", person2Str);
    logger.info("===========list==============");
    List<PersonDTO> list = new ArrayList<>();


    Type genericSuperclass = list.getClass().getGenericSuperclass();
    if (genericSuperclass instanceof Class<?>) { // sanity check, should never happen
      throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
    }
    /* 22-Dec-2008, tatu: Not sure if this case is safe -- I suspect
     *   it is possible to make it fail?
     *   But let's deal with specific
     *   case when we know an actual use case, and thereby suitable
     *   workarounds for valid case(s) and/or error to throw
     *   on invalid one(s).
     */
    Type _type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];

    list.add(person1);
    list.add(person2);
    String listStr = obj2StringPretty(list);
    logger.info("\n{}", listStr);

    List<PersonDTO> list1 = string2Obj(listStr, List.class);
    logger.info("\n{}", list1);

    logger.info("===========DeSerialization==============");
    List<PersonDTO> list2 = string2Obj(listStr, new TypeReference<List<PersonDTO>>() {
    });
    logger.info("===========DeSerialization==============");
    List<PersonDTO> list3 = string2Obj(listStr, List.class, PersonDTO.class);
    logger.info("===========DeSerialization==============");
//        List<PersonDTO> list1 = string2Obj(listStr,)
//        System.out.println(obj2StringPretty(add1));
//        System.out.println(obj2StringPretty(add2));
//        System.out.println(obj2StringPretty(person1));
//        System.out.println(obj2StringPretty(person2));
  }
}
