package xyz.mydev.jdk;

import lombok.extern.slf4j.Slf4j;

/**
 * 懒汉模式
 *
 * @author ZSP
 */
@Slf4j
public class SingletonDemo {

  private SingletonDemo() {
//    if (object != null) {
//      throw new IllegalStateException();
//    }
  }

  private static volatile SingletonDemo object;

  /**
   * double check
   */
  public static SingletonDemo getObject() {
    if (object == null) { // 竞态条件
      synchronized (SingletonDemo.class) {
        if (object == null) {
          object = new SingletonDemo();
        }
      }
    }
    log.info("in constructor: {}", object);
    return object;
  }

  public static void main(String[] args) {

    Thread t1 = new Thread(() -> {
      SingletonDemo object = SingletonDemo.getObject();
      log.info(object.toString());
    }, "T1");

    Thread t2 = new Thread(() -> {
      SingletonDemo object = SingletonDemo.getObject();
      log.info(object.toString());
    }, "T2");

    t1.start();
    t2.start();

//    ThreadUtils.sleepSeconds(20);

  }

}
