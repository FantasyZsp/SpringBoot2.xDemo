package xyz.mydev.synchronizeddemo;

import xyz.mydev.common.utils.ThreadUtils;

/**
 * @author ZSP
 */
public class Test2 {
  public static void main(String[] args) {
    final Child child = new Child();

    new Thread(() -> child.doSomething(), "myThread").start();
    ThreadUtils.join(200);
    System.out.println("main wake up");
    child.doSomethingElse();
  }

  public void doSomething() {
    synchronized (this) {
      System.out.println("Super doSomething" + this);
      ThreadUtils.sleepSeconds(20);
      System.out.println("Super doSomething wake up!" + this);
    }
  }

  private static class Child extends Test2 {
    @Override
    public void doSomething() {
      synchronized (this) {
        System.out.println("Child doSomething" + this);
        super.doSomething();
      }
    }

    public synchronized void doSomethingElse() {
      synchronized (this) {
        System.out.println("Child doSomethingElse: " + this);
      }
    }
  }
}