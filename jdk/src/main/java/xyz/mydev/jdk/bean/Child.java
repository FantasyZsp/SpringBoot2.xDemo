package xyz.mydev.jdk.bean;

public class Child extends Parent {
  public static final String CHILD_FINAL_ID = "childId";
  public static String CHILD_NAME = "childName";

  static {
    System.out.println("Child static invoke");
  }
}