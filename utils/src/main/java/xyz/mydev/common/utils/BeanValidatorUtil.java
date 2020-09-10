package xyz.mydev.common.utils;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 参数校验工具。
 *
 * @author ZSP  2018/12/19 16:25
 */
public class BeanValidatorUtil {
  private BeanValidatorUtil() {
  }

  private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  private static <T> Map<String, String> validateObject(T t, Class<?>... groups) {
    Set<ConstraintViolation<T>> validateResult = validator.validate(t, groups);
    return handleConstraintViolations(validateResult);
  }

  private static <T> Map<String, String> handleConstraintViolations(Set<ConstraintViolation<T>> validateResult) {
    if (validateResult.isEmpty()) {
      return Collections.emptyMap();
    } else {
      LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
      for (ConstraintViolation<T> error : validateResult) {
        errors.put(error.getPropertyPath().toString(), error.getMessage());
      }
      return errors;
    }
  }

  private static Map<String, String> validateList(Collection<?> collection) {
    if (CollectionUtils.isEmpty(collection)) {
      return Collections.emptyMap();
    }
    Iterator<?> iterator = collection.iterator();
    LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
    while (iterator.hasNext()) {
      Object obj = iterator.next();
      Map<String, String> validate = validateObject(obj);
      errors.putAll(validate);
    }
    return errors;
  }

  private static Map<String, String> validate(Object validatedBean) {
    if (validatedBean instanceof Collection) {
      return validateList((Collection<?>) validatedBean);
    }
    return validateObject(validatedBean);
  }

  /**
   * 校验参数
   *
   * @param param 校验参数
   */
  public static void checkValid(Object param) {
    Map<String, String> validate = validate(param);
    if (MapUtils.isNotEmpty(validate)) {
      String errMsg = mapToString(validate, ",");
      throw new ParamCheckException(errMsg);
    }
  }


  /**
   * 将map键值对拼接成字符串
   *
   * @param maps      字符串map
   * @param delimiter 分隔符
   * @return 拼接结果
   * @author ZSP
   */
  private static String mapToString(Map<String, String> maps, String delimiter) {
    if (MapUtils.isEmpty(maps)) {
      return "";
    }
    StringBuilder result = maps.entrySet().stream().collect(StringBuilder::new, (builder, e) -> builder.append(e.getKey()).append(e.getValue()).append(delimiter), StringBuilder::append);
    result.deleteCharAt(result.length() - 1);
    return result.toString();
  }
}
