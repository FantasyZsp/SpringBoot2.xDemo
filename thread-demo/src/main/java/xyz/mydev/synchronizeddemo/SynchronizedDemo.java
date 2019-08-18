package xyz.mydev.synchronizeddemo;


import lombok.extern.slf4j.Slf4j;

/**
 * synchronized 可重入
 */
@Slf4j
public class SynchronizedDemo extends Syn {
  @Override
  synchronized void get() {
    super.get();

    log.info("{}: get()...", Thread.currentThread().getName());
  }

  synchronized void set() {
    log.info("{}: set ...", Thread.currentThread().getName());

    synchronized (this) {
      log.info("{}: set synchronized()...", Thread.currentThread().getName());
    }

  }

  synchronized void test() {
    get();
    set();

  }

  public static void main(String[] args) {
    SynchronizedDemo demo = new SynchronizedDemo();
    demo.test();

    log.warn("===============");
    Consumer consumer = new Consumer(demo);
    consumer.test();

  }


}

@Slf4j
class Syn {
  synchronized void get() {
    log.info("{}: super.get()...", Thread.currentThread().getName());
  }
}

class Consumer {
  private final Syn syn;

  public Consumer(Syn syn) {
    this.syn = syn;
  }

  public void test() {
    synchronized (syn) {
      this.syn.get();
    }
  }
}


