package xyz.mydev.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author ZSP
 */
public class CommonMethodInterceptor implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    System.out.println("before " + invocation.getMethod().getName());
    Object proceed = invocation.proceed();
    System.out.println("after " + invocation.getMethod().getName());
    return proceed;
  }
}
