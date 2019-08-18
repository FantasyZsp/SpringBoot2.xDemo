package xyz.mydev.inherit;

public class Child extends Parent {
  public String childName;
  public static String CHILD_ADDRESS;

  static {
    CHILD_ADDRESS = "child static  address";
    System.out.println(CHILD_ADDRESS);
  }

  public static Parent parent = new Parent();

  public Child() {
    System.out.println("child constructor");
  }

  {
    childName = "child init childName";
    System.out.println(childName);
  }

}


class Test {
  public static void main(String[] args) {
    new Child();
  }
}