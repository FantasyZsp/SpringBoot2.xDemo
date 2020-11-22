package xyz.mydev.jdk.getclass;

/**
 * @author zhao
 * 可以获取到任何形式的内部类（不包含父类）
 */
public class ClassApiDemo {
  public static void main(String[] args) {
    testGetDeclaredClassApi();
  }

  public static void testGetDeclaredClassApi() {

    for (Class<?> declaredClass : ClassApiDemo.class.getDeclaredClasses()) {
      System.out.println(declaredClass.getSimpleName());
    }

  }

  class InnerClass1 {

  }

  class InnerClass2 {

  }

  static class InnerStaticClass3 {

  }

  public static class InnerStaticClass4 {

  }
}
