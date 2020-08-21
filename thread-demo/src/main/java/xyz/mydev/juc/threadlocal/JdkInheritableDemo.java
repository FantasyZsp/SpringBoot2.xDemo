package xyz.mydev.juc.threadlocal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mydev.common.utils.ThreadUtils;

/**
 * InheritableThreadLocal 重写了  ThreadLocal 的getMap方法，返回了线程的{@link java.lang.Thread#inheritableThreadLocals}变量
 *
 * @author ZSP
 */
public class JdkInheritableDemo {
  Logger log = LoggerFactory.getLogger("fast");

  /**
   * inheritableThreadLocal 与赋值、创建线程时机有关，且只在线程创建时能够传递。所以对线程池来说没什么大的作用，反而会导致意外的情况发生。
   */
  @Test
  public void testInheritable() throws Exception {
    InheritableThreadLocal<Text> inheritableThreadLocal = new InheritableThreadLocal<>();

    // 先行创建的线程，即使后续父线程设置了 inheritableThreadLocal，子线程也无法使用
    Thread beforeSet = new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.info("text before-set: {}", inheritableThreadLocal.get());
    }, "before-set");


    inheritableThreadLocal.set(new Text("parent"));
    log.info("text: {}", inheritableThreadLocal.get());


    // 新建线程时，会将当前线程的 inheritableThreadLocal 赋值到子线程中
    Thread afterSet = new Thread(() -> {
      log.info("text after-set: {}", inheritableThreadLocal.get());
    }, "after-set");

    ThreadUtils.startAndJoin(afterSet);
    ThreadUtils.startAndJoin(beforeSet);
  }

  @Test
  public void testInheritable2() throws Exception {
    InheritableThreadLocal<Text> local = new InheritableThreadLocal<>();
    local.set(new Text("parent"));
    log.info("text: {}", local.get());


    // 新建线程时，会将当前线程的 local 赋值到子线程中
    Thread afterSet = new Thread(() -> {
      log.info("text after-set: {}", local.get());
      local.get().setName("被修改了属性");
    }, "after-set");

    ThreadUtils.startAndJoin(afterSet);

    log.info("text: {}", local.get());
  }


  /**
   * TODO 构造一个并发案例
   */
  @Test
  public void testWhenConcurrentModify() throws Exception {

  }

}
