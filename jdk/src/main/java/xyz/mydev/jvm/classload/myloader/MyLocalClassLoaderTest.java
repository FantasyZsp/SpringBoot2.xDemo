package xyz.mydev.jvm.classload.myloader;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassToBeLoaded资源可以在src/lib压缩文件中获取
 *
 * @author ZSP
 */
@Slf4j
public class MyLocalClassLoaderTest {

  public static final String DESKTOP_PATH = "C:\\Users\\zhaosp\\Desktop\\";
  public static final String PROJECT_CLASSPATH = "D:\\project\\SpringBoot2Demo\\jdk\\target\\classes\\";


  public static void main(String[] args) throws Exception {
//    testClassLoadNameSpace();
    testClassLoadNameSpaceByParent();
  }

  public static void testClassLoadNameSpace() throws Exception {
    // 重复调用两次，加载同一个class文件，发现class hashcode不同，因为类加载器不同，且没有依赖关系。此时就处于两个不同的命名空间中。
    testLoadByCustomOne();
    testLoadByCustomOne();
    // test一样，是因为都委托给了系统类加载器加载。
    test();
    test();
  }

  public static void testClassLoadNameSpaceByParent() throws Exception {
    MyLocalClassLoader localClassLoader1 = new MyLocalClassLoader("myClassLoader1");
    System.out.println("localClassLoader1加载一个classPath外部的类");
    localClassLoader1.setPath(DESKTOP_PATH);
    Class<?> aClass = localClassLoader1.loadClass("xyz.mydev.jvm.classload.init.ClassToBeLoaded");
    System.out.println(aClass);
    System.out.println("hashcode: " + aClass.hashCode());
    System.out.println(aClass.getClassLoader());
    System.out.println("类加载器名字: " + aClass.getClassLoader().getName());
    System.out.println("实例: " + aClass.newInstance());
    System.out.println();
    System.out.println();

    MyLocalClassLoader localClassLoader2 = new MyLocalClassLoader(localClassLoader1, "myClassLoader2");
    System.out.println("localClassLoader1配置为localClassLoader2的父类加载器");
    localClassLoader2.setPath(DESKTOP_PATH);
    Class<?> bClass = localClassLoader2.loadClass("xyz.mydev.jvm.classload.init.ClassToBeLoaded");
    System.out.println(bClass);
    System.out.println("hashcode: " + bClass.hashCode());
    System.out.println(bClass.getClassLoader());
    System.out.println("类加载器名字: " + bClass.getClassLoader().getName());
    System.out.println("实例: " + bClass.newInstance());
    System.out.println();
    System.out.println();
  }

  public static void testLoadByCustomOne() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    MyLocalClassLoader localClassLoader = new MyLocalClassLoader("myClassLoader");
    System.out.println("加载一个classPath外部的类");
    localClassLoader.setPath(DESKTOP_PATH);
    Class<?> bClass = localClassLoader.loadClass("xyz.mydev.jvm.classload.init.ClassToBeLoaded");
    System.out.println(bClass);
    System.out.println("hashcode: " + bClass.hashCode());
    System.out.println(bClass.getClassLoader());
    System.out.println("类加载器名字: " + bClass.getClassLoader().getName());
    System.out.println("实例: " + bClass.newInstance());
    System.out.println();
    System.out.println();
//    test();
  }

  public static void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // 仍然由系统类加载器加载.如果将父类加载器置为null，则是由此类加载器加载。
    MyLocalClassLoader localClassLoader = new MyLocalClassLoader("myClassLoader2");
    localClassLoader.setPath(PROJECT_CLASSPATH);
    Class<?> aClass = localClassLoader.loadClass("xyz.mydev.jvm.classload.loader.LoaderTest");
    System.out.println(aClass);
    System.out.println(aClass.hashCode());
    System.out.println(aClass.getClassLoader().getName());
    System.out.println(aClass.newInstance());
  }

}
