package jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * link https://www.cnblogs.com/yeyang/p/10087293.html
 *
 * @author ZSP
 */
@Slf4j
public class Client {
  public static void main(String[] args) throws Exception {
//    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//    System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
    testByInnerClass();
  }

  private static RequestParam buildRequestParam(String content) {
    return RequestParam.builder().content(content).build();
  }


  private static void testByConcreate() {
    RequestParam requestParam = buildRequestParam("testByConcreate");

    Subject subject = (Subject) Proxy.newProxyInstance(
      Client.class.getClassLoader(),
      new Class[]{Subject.class},
      new JdkProxySubject(new RealSubject()));

    subject.request(requestParam);
  }

  private static void testByInnerClass() {
    RequestParam requestParam = buildRequestParam("testByInnerClass");

    Subject subject = (Subject) Proxy.newProxyInstance(
      Client.class.getClassLoader(),
      new Class[]{Subject.class},
      new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          String className = method.getDeclaringClass().getName();
          String methodName = method.getName();
          Class<?>[] parameterTypes = method.getParameterTypes();
          log.info("className={},methodName={},parameterTypes={}", className, methodName, parameterTypes);
          log.info("args={}", args);

          return "inner class";

        }
      });

    subject.request(requestParam);
  }
}