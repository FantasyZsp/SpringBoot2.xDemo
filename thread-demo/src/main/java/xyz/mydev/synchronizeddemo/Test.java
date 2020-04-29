package xyz.mydev.synchronizeddemo;

public class Test {
  public static void main(String[] args) throws InterruptedException {
    final TestChild t = new TestChild();

    new Thread(() -> t.doSomething()).start();
    Thread.sleep(100);
    System.out.println("main wake up");
    t.doSomethingElse();
  }

  public synchronized void doSomething() {
    System.out.println("Test doSomething");
    try {
      Thread.sleep(1000);
      System.out.println("Test doSomething wake up!");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static class TestChild extends Test {
    @Override
    public void doSomething() {
      super.doSomething();
    }

    public synchronized void doSomethingElse() {
      System.out.println("TestChild doSomethingElse");
    }
  }
}