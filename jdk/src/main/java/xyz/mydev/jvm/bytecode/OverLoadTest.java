package xyz.mydev.jvm.bytecode;

import xyz.mydev.jvm.bean.Father;
import xyz.mydev.jvm.bean.Grandpa;
import xyz.mydev.jvm.bean.Son;

/**
 * 方法重载与静态分派
 *
 * @author ZSP
 */
public class OverLoadTest {

  public void test(Grandpa grandpa) {
    System.out.println("Grandpa");
  }

  public void test(Father father) {
    System.out.println("Father");
  }

  public void test(Son son) {
    System.out.println("Son");
  }

  public static void main(String[] args) {
    OverLoadTest overLoadTest = new OverLoadTest();
    Grandpa grandpa = new Grandpa();
    Grandpa grandpa2 = new Father();
    Grandpa grandpa3 = new Son();
    overLoadTest.test(grandpa);
    overLoadTest.test(grandpa2);
    overLoadTest.test(grandpa3);
    overLoadTest.test(new Father());
  }
}
