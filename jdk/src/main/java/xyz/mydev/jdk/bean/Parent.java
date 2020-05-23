package xyz.mydev.jdk.bean;

public class Parent {
  /**
   * 外部仅访问此final变量，不会导致初始化
   */
  public static final String ID = "parentId";
  public static String NAME = "parentName";
  public static final Integer FINAL_NUMBER = 1;
  public static Integer NUMBER = 1;

  static {
    System.out.println("Parent static invoke");
  }
}

