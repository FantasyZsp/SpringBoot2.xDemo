package xyz.mydev.spring.aop.reflection.utils;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author ZSP
 */
public class ReflectionUtilTest {

  @Test
  public void testMethodFindAndFilter() {
    ReflectionUtils.doWithMethods(Person.class, new ReflectionUtils.MethodCallback() { // 传入所有符合过滤条件的方法并执行
      @Override
      public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
        System.out.println(method.getName());
//        try {
//          method.invoke(new Person());
//        } catch (InvocationTargetException e) {
//          e.printStackTrace();
//        }
      }
    }, new ReflectionUtils.MethodFilter() { // 方法过滤，仅返回符合条件的方法
      @Override
      public boolean matches(Method method) {
        return !method.getDeclaringClass().equals(Object.class);
      }
    });

  }


}
