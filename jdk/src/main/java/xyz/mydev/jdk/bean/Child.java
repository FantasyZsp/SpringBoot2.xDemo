package xyz.mydev.jdk.bean;

public class Child extends Parent implements TempInterface {
  public static final String CHILD_FINAL_ID = "childId";
  public static String CHILD_NAME = "childName";

  static {
    System.out.println("Child static invoke");
  }

  public Integer getMark() {
    return this.mark;
  }

  public static void main(String[] args) {

    Child child = new Child();
    child.getData();
  }

}