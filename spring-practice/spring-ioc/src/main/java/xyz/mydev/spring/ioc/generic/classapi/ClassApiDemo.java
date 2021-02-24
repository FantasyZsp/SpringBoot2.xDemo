package xyz.mydev.spring.ioc.generic.classapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * @author ZSP
 */
@Slf4j
public class ClassApiDemo {


  public String simpleMethod() {
    System.out.println("simpleMethod invoke");
    return "simpleMethod return ";
  }

  public List<String> genericMethod(List<String> param) {
    System.out.println("genericMethod invoke");
    return List.of("genericMethod return ");
  }


  public <T> List<T> abstractGenericMethod(List<T> param) {
    System.out.println("abstractGenericMethod invoke");
    return param;
  }

  @Test
  public void testDate() {

    LocalDate startDate = LocalDate.of(2021, 1, 9);
    System.out.println(startDate.plusDays(45));
    System.out.println(startDate.plusDays(60));
  }


  @Test
  public void testMethodApi() throws Exception {

    ClassApiDemo classApiDemo = new ClassApiDemo();
    Class<? extends ClassApiDemo> aClass = classApiDemo.getClass();

    Method simpleMethod = aClass.getMethod("simpleMethod");
    Assert.assertEquals(simpleMethod.getDeclaringClass(), aClass);
    Assert.assertEquals(simpleMethod.getClass(), Method.class);
    simpleMethod.invoke(classApiDemo);

    GenericUtils.displayReturnTypeGenericInfo(aClass, Comparable.class, "simpleMethod");

    GenericUtils.displayReturnTypeGenericInfo(aClass, Iterable.class, "genericMethod", List.class);

    GenericUtils.displayReturnTypeGenericInfo(aClass, List.class, "abstractGenericMethod", List.class);

    System.out.println(GenericTypeResolver.getTypeVariableMap(GenericBean.class));

  }

  @Test
  public void testResolvableType() {
    exampleResolvableTypeApi();

  }


  @Test
  public void testMethodParameter() {

//    MethodParameter


  }

  private HashMap<Integer, List<String>> myMap;

  public void exampleResolvableTypeApi() {
    ResolvableType t;
    try {
      t = ResolvableType.forField(getClass().getDeclaredField("myMap"));
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }

    // 所见即所得 java.util.HashMap<java.lang.Integer, java.util.List<java.lang.String>>
    System.out.println(t);
    // class java.util.HashMap
    System.out.println(t.resolve());
    // java.util.AbstractMap<java.lang.Integer, java.util.List<java.lang.String>>
    System.out.println(t.getSuperType());

    // java.util.Map<java.lang.Integer, java.util.List<java.lang.String>>
    System.out.println(t.asMap());

    // java.lang.Integer
    System.out.println(t.getGeneric(0));
    // class java.lang.Integer
    System.out.println(t.getGeneric(0).resolve());
    // class java.lang.Integer
    System.out.println(t.getGeneric(0).getRawClass());

    // java.util.List<java.lang.String>
    System.out.println(t.getGeneric(1));

    // interface java.util.List
    System.out.println(t.getGeneric(1).resolve());
    // interface java.util.List
    System.out.println(t.getGeneric(1).getRawClass());
    // class java.lang.String
    System.out.println(t.resolveGeneric(1, 0));
    // java.lang.String
    System.out.println(t.getGeneric(1).getGeneric(0));
  }

}
