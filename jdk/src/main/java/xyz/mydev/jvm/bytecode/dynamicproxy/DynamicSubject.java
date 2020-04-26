package xyz.mydev.jvm.bytecode.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ZSP
 */
public class DynamicSubject implements InvocationHandler {

  private RealSubject realSubject;

  public DynamicSubject(RealSubject realSubject) {
    this.realSubject = realSubject;
  }


  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println();
    System.out.println("DynamicSubject invoke before calling " + method.getName());

    method.invoke(realSubject, args);

    System.out.println("DynamicSubject invoke after calling " + method.getName());
    System.out.println();

    return null;
  }
}
