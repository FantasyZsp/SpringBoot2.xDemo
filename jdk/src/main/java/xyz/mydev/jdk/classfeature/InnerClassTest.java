package xyz.mydev.jdk.classfeature;

import lombok.Getter;

/**
 * @author ZSP
 */
public class InnerClassTest {


  @Getter
  private final String name;

  public InnerClassTest(String name) {
    this.name = name;
  }

  public static void main(String[] args) {
    InnerClassTest innerClassTest = new InnerClassTest("classTest");
    InnerClassDemo inner = innerClassTest.new InnerClassDemo("inner");

    System.out.println(innerClassTest);
    System.out.println(inner);
  }

  public void test() {
    InnerClassDemo inner = new InnerClassDemo("inner");
  }


  @Getter
  private final class InnerClassDemo {

    private final String myName;

    InnerClassDemo(String myName) {
      this.myName = myName;
      System.out.println("=========");

      System.out.println(getName());
      System.out.println(getMyName());

      System.out.println(InnerClassDemo.this);
      System.out.println(this);
      System.out.println(InnerClassTest.this);
      System.out.println("=========");
    }
  }

}
