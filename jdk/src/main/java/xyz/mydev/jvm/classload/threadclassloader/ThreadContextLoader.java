package xyz.mydev.jvm.classload.threadclassloader;

/**
 * 类A如果引用了类B，如果类B尚未加载，那么类A会用类A的定义加载器尝试加载B。
 *
 * @author ZSP
 */
public class ThreadContextLoader {

  public static void main(String[] args) {
    System.out.println(Thread.currentThread().getContextClassLoader());
    System.out.println(Thread.class.getClassLoader());

    Thread.currentThread().setContextClassLoader(null);
  }

}
