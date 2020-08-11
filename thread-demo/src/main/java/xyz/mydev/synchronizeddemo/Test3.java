package xyz.mydev.synchronizeddemo;

import xyz.mydev.common.utils.ThreadUtils;

/**
 * 当子类通过super调用父类的同步方法时，锁定的对象是子类的实例;
 * 子类通过父类实例的引用调用父类同步方法时，锁定对象是父类实例；
 * 父类直接调用自己的方法时，锁定的是自身。
 *
 * @author ZSP
 */
public class Test3 {
  public static void main(String[] args) {
    final Test3 test3 = new Test3();
    final Test3 child = new Child(test3); // 引用类型无所谓，同Child child

    new Thread(() -> {
      child.doSomething("Thread child");
      test3.doSomething("Thread test3");
    }, "myThread").start();
    ThreadUtils.sleepSeconds(5);

  }

  public void doSomething(String fromPath) {
    synchronized (this) {
      System.out.println();
      System.out.println(fromPath);
      System.out.println("Super doSomething:  " + this);
//      ThreadUtils.sleepSeconds(20);
      System.out.println("Super doSomething wake up!:  " + this);
      System.out.println();
    }
  }

  private static class Child extends Test3 {

    private Test3 test3;

    public Child(Test3 test3) {
      this.test3 = test3;
    }

    @Override
    public void doSomething(String fromPath) {
      synchronized (this) { // 这里无论是否锁this，在super.doSomething时，锁定的都是子类的实例this
        System.out.println();
        System.out.println(fromPath);
        System.out.println("Child doSomething:  " + this);
        System.out.println("super instance:  " + test3);
        super.doSomething("from Child doSomething :  super.doSomething");
        test3.doSomething("from Child doSomething :  test3.doSomething");
        System.out.println();
      }
    }
  }
}