package xyz.mydev.jdk.getclass;

import lombok.Getter;

/**
 * @author ZSP
 */
public class ClassTest {


  @Getter
  private final String name;

  public ClassTest(String name) {
    this.name = name;
  }

  public static void main(String[] args) {
    ClassTest classTest = new ClassTest("classTest");
    InnerClassDemo inner = classTest.new InnerClassDemo("inner");

    System.out.println(classTest);
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
      System.out.println(ClassTest.this);
      System.out.println("=========");
    }
  }

}
