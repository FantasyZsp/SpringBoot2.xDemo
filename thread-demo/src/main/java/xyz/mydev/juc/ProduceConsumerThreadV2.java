package xyz.mydev.juc;

import java.util.stream.Stream;

/**
 * @author ZSP
 * 多对多生产消费
 */
public class ProduceConsumerThreadV2 {

  private volatile boolean isProduced = false;
  private static int i = 1;

  final private Object LOCK = new Object();

  public void produce() {
    synchronized (LOCK) {
      while (isProduced) {
        try {
          LOCK.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println(Thread.currentThread().getName() + "=>>>" + (i++));
      isProduced = true;
      LOCK.notifyAll();
    }
  }

  public void consume() {
    synchronized (LOCK) {
      while (isProduced) {
        System.out.println(Thread.currentThread().getName() + "=>>>" + i);
        isProduced = false;
        LOCK.notifyAll();
      }
      try {
        LOCK.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  public static void main(String[] args) {

    ProduceConsumerThreadV2 pc = new ProduceConsumerThreadV2();

    Stream.of("P1").forEach((name) -> {
        new Thread(() -> {
          while (true) {
            pc.produce();
          }
        }, name).start();

      }

    );

    Stream.of("C1").forEach((name) -> {
        new Thread(() -> {
          while (true) {
            pc.consume();
          }
        }, name).start();

      }

    );
  }
}
