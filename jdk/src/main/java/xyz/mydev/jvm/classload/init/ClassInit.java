package xyz.mydev.jvm.classload.init;

/**
 * 类初始化
 *
 * @author ZSP
 */
public class ClassInit {
  public static void main(String[] args) {
    System.out.println(Child.CHILD_FINAL_ID);
    System.out.println("Parent and Child has no loaded");
    System.out.println(Child.FINAL_NUMBER);
  }
}

class Parent {
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

class Child extends Parent {
  public static final String CHILD_FINAL_ID = "childId";
  public static String CHILD_NAME = "childName";

  static {
    System.out.println("Child static invoke");
  }
}