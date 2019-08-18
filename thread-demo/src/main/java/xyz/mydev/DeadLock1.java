package xyz.mydev;


import lombok.extern.slf4j.Slf4j;

/**
 * 给出一个死锁demo
 */
@Slf4j
public class DeadLock1 extends Thread {
  public final static Object lockA = new Object();
  public final static Object lockB = new Object();

  public DeadLock1() {
    super("DeadLock111");
  }

  @Override
  public void run() {
    synchronized (lockA) {
      log.info(Thread.currentThread().getName() + "获取到lockA...");
      try {
        log.info(Thread.currentThread().getName() + "等一等其他人抢锁");
        Thread.sleep(1000);
        log.info(Thread.currentThread().getName() + "开始去抢B");
      } catch (InterruptedException e) {
        log.warn("异常打断。。");
      }
      synchronized (lockB) {
        log.info(Thread.currentThread().getName() + "获取到lockB...");
      }
    }
  }

  public static void main(String[] args) {
    DeadLock1 deadLock1 = new DeadLock1();
    DeadLock2 deadLock2 = new DeadLock2();
    deadLock1.start();
    deadLock2.start();
  }
}

@Slf4j
class DeadLock2 extends Thread {

  public DeadLock2() {
    super("DeadLock222");
  }

  @Override
  public void run() {
    synchronized (DeadLock1.lockB) {
      log.info(Thread.currentThread().getName() + "获取到lockB...");
      try {
        log.info(Thread.currentThread().getName() + "等一等其他人抢锁");
        Thread.sleep(1000);
        log.info(Thread.currentThread().getName() + "开始去抢A");
      } catch (InterruptedException e) {
        log.warn("异常打断。。");
      }
      synchronized (DeadLock1.lockA) {
        log.info(Thread.currentThread().getName() + "获取到lockA...");
      }
    }
  }
}



