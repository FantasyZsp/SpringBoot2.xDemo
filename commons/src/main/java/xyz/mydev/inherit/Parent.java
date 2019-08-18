package xyz.mydev.inherit;

public class Parent {
  protected String parentName;
  protected static String PARENT_ADDRESS;

  public Parent() {
    System.out.println("Parent constructor");
  }

  {
    parentName = "parent init  parentName";
    System.out.println(parentName);
  }

  static {
    PARENT_ADDRESS = "parent static init address";
    System.out.println(PARENT_ADDRESS);
  }
}
