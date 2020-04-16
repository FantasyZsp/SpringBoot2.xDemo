package xyz.mydev.jvm.classload.loader;

import org.junit.Test;

/**
 * @author ZSP
 */
public class LoaderTest {
  public static void main(String[] args) throws ClassNotFoundException {

  }


  @Test
  public void testLoader() throws ClassNotFoundException {
    Class<?> aClass = Class.forName("java.lang.String");
    System.out.println("String loaded by " + aClass.getClassLoader());


    Class<?> thisClass = Class.forName("xyz.mydev.jvm.classload.loader.LoaderTest");
    System.out.println("LoaderTest loaded by " + thisClass.getClassLoader());
  }


  @Test
  public void listLoader() throws ClassNotFoundException {
    ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
    System.out.println(systemClassLoader);

    ClassLoader classLoader = systemClassLoader;
    while (classLoader != null) {
      classLoader = classLoader.getParent();
      System.out.println(classLoader);
    }
  }

  @Test
  public void threadContextClassLoader() throws ClassNotFoundException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    System.out.println(classLoader);
  }
}
