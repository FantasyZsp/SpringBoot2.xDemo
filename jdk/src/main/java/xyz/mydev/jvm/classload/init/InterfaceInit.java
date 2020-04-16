package xyz.mydev.jvm.classload.init;

import java.util.Random;

/**
 * @author ZSP
 */
public class InterfaceInit {

  public static void main(String[] args) {
    System.out.println(Child.MSG);
    System.out.println(ChildClass.MSG);
    System.out.println("=========");
    System.out.println(Parent.OBJECT);
  }

  interface Parent {
    Object OBJECT = new Object() {
      {
        System.out.println("parent init");
      }
    };
  }

  interface Child extends Parent {
    String MSG = "Child init" + new Random().nextInt(3);
  }

  static class ChildClass implements Parent {
    public static final String MSG = "ChildClass init" + new Random().nextInt(3);
  }
}

