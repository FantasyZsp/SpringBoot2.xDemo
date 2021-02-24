package xyz.mydev.spring.ioc.generic.classapi;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;

/**
 * @author ZSP
 */
public class GenericUtils {


  /**
   * resolveReturnTypeArgument方法拿的是 方法返回形参里指定了具体接口的泛型实参
   */
  public static void displayReturnTypeGenericInfo(Class<?> containingClass, Class<?> generic, String methodName, Class<?>... argumentTypes) {

    try {
      Method method = containingClass.getMethod(methodName, argumentTypes);
      Class<?> returnType = GenericTypeResolver.resolveReturnType(method, containingClass);
      System.out.printf("%s %s 返回值类型：%s \n", containingClass.getSimpleName(), methodName, returnType);

      // 获取的是返回值整体类型中，所有实现的接口、继承的类中 generic 对应的泛型参数是什么，这里只能给出具体泛型参数，无法给出T、E类型，会拿到null
      Class<?> returnTypeArgument = GenericTypeResolver.resolveReturnTypeArgument(method, generic);
      System.out.printf("%s %s 返回值 %s 的泛型类型：%s \n", containingClass.getSimpleName(), methodName, generic.getSimpleName(), returnTypeArgument);

    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }

  }

}
