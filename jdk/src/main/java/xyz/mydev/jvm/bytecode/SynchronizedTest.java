package xyz.mydev.jvm.bytecode;

import xyz.mydev.common.utils.ThreadUtils;

/**
 * @author ZSP
 */
public class SynchronizedTest {
  private static int COUNTER = 0;

  private static final Object LOCK = new Object();


  public void add() {
    synchronized (LOCK) {
      COUNTER++;
    }
  }

  public int get() {
    synchronized (LOCK) {
      return COUNTER;
    }
  }

  public static void main(String[] args) {
    SynchronizedTest test = new SynchronizedTest();
    new Thread(() -> {
      int i = 1;
      while (i < 100) {
        test.add();
        i++;
      }

    }).start();

    new Thread(() -> {
      int i = 1;
      while (i < 100) {
        System.out.println(test.get());
        i++;
      }

    }).start();


    ThreadUtils.sleepSeconds(3);

  }

}
