package xyz.mydev.redisson.utils;

import org.springframework.util.Assert;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZSP
 */
public class SimpleThreadFactory {
  private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

  private static final ThreadFactory INSTANCE = new Instance();


  public static Thread newT(Runnable r) {
    return INSTANCE.newThread(r);
  }

  private static class Instance implements ThreadFactory {

    String prefix;

    public Instance() {
      prefix = "";
    }

    public Instance(String prefix) {
      this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
      Assert.notNull(r, "r must not null");
      return new Thread(r, "T-" + ATOMIC_INTEGER.incrementAndGet());
    }
  }
}
