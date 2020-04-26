package xyz.mydev.jvm.bytecode;

/**
 * 方法重写与动态委派
 *
 * @author ZSP
 */
public class OverrideTest {

  public static void main(String[] args) {
    Fruit apple = new Apple();
    Fruit orange = new Orange();

    apple.test();
    orange.test();

    apple = new Orange();
    apple.test();
  }
}

class Fruit {
  public void test() {
  }
}

class Apple extends Fruit {
  @Override
  public void test() {
    System.out.println("Apple");
  }
}

class Orange extends Fruit {
  @Override
  public void test() {
    System.out.println("Orange");
  }
}

