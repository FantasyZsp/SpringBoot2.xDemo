package xyz.mydev.jvm.classload.threadclassloader;

import java.net.URL;
import java.util.Enumeration;

/**
 * 类A如果引用了类B，如果类B尚未加载，那么类A会用类A的定义加载器尝试加载B。
 *
 * @author ZSP
 */
public class ThreadContextLoader {

  public static void main(String[] args) throws Exception {
    System.out.println(Thread.currentThread().getContextClassLoader());
    System.out.println(Thread.class.getClassLoader());

    Thread.currentThread().setContextClassLoader(null);

    testGetResource();
  }


  // 多个jar时，查找都处于哪些jar中
  public static void testGetResource() throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    Enumeration<URL> resources = cl.getResources("java/lang/String.class");
    while (resources.hasMoreElements()) {
      System.out.println(resources.nextElement());
    }
  }

}
