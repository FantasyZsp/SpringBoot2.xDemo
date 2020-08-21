package xyz.mydev.juc.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mydev.common.utils.PrefixNameThreadFactory;
import xyz.mydev.common.utils.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZSP
 */
public class AliBaBaInheritableDemo {

  Logger log = LoggerFactory.getLogger("fast");

  static PrefixNameThreadFactory factory = new PrefixNameThreadFactory("OOOOOO");
  static PrefixNameThreadFactory factory_2 = new PrefixNameThreadFactory("TTTTTT");

  static ExecutorService GLOBAL_EXECUTOR_1 = Executors.newFixedThreadPool(1, factory);
  static ExecutorService GLOBAL_EXECUTOR_2 = Executors.newFixedThreadPool(1, factory_2);
  static AtomicLong atomicLong = new AtomicLong();


  @Before
  public void before() {

  }

  @Test
  public void testBeforeSet() throws Exception {
    TransmittableThreadLocal<Text> local = new TransmittableThreadLocal<>();

    Thread beforeSet = new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.info("text before-set: {}", local.get());
    }, "before-set");

    local.set(new Text("xxx"));

    Thread afterSet = new Thread(() -> {
      log.info("text after-set: {}", local.get());
    }, "after-set");

    ThreadUtils.startAndJoin(afterSet);
    ThreadUtils.startAndJoin(beforeSet);
  }


  @Test
  public void testByPool() throws Exception {
    TransmittableThreadLocal<Text> local = new TransmittableThreadLocal<>();
    local.set(new Text("xxx"));


    Runnable setNewOne = () -> {
      log.info("before setNewOne: {}", local.get());
      local.set(new Text("child" + atomicLong.incrementAndGet()));
      log.info("after setNewOne: {}", local.get());
      local.remove();
    };

    Runnable updateField = () -> {
      log.info("before updateField: {}", local.get());
      try {
        local.get().setName("child");
      } catch (RuntimeException e) {
        log.error("error ", e);
      }
      log.info("after updateField: {}", local.get());
      local.remove();
    };


    Runnable getNow = () -> {
      log.info("getNow task : {}", local.get());
    };

    GLOBAL_EXECUTOR_1.submit(getNow);
    GLOBAL_EXECUTOR_2.submit(getNow);


    GLOBAL_EXECUTOR_1.submit(setNewOne);
    GLOBAL_EXECUTOR_2.submit(updateField);

    TimeUnit.SECONDS.sleep(1);


    GLOBAL_EXECUTOR_2.submit(setNewOne);
    GLOBAL_EXECUTOR_1.submit(updateField);
    TimeUnit.SECONDS.sleep(1);


    GLOBAL_EXECUTOR_1.submit(getNow);
    GLOBAL_EXECUTOR_2.submit(getNow);

    ThreadUtils.sleepSeconds(10);

  }

}
