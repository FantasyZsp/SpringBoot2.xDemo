package jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ZSP
 */
@Slf4j
public class JdkProxySubject implements InvocationHandler {
  private RealSubject realSubject;

  public JdkProxySubject(RealSubject realSubject) {
    this.realSubject = realSubject;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    log.info("before");
    Object result;
    try {

      String className = method.getDeclaringClass().getName();
      String methodName = method.getName();
      Class<?>[] parameterTypes = method.getParameterTypes();
      log.info("className={},methodName={},parameterTypes={}", className, methodName, parameterTypes);
      result = method.invoke(realSubject, args);

    } catch (Exception e) {
      throw e;
    } finally {
      log.info("after");
    }
    return result;
  }
}
