package xyz.mydev.spring.ioc.generic.classapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
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

  }

}
