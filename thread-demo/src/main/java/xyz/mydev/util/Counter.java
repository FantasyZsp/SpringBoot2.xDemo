package xyz.mydev.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZSP
 */
public final class Counter {

  public static final AtomicLong ATOMIC_LONG = new AtomicLong();

  public static long getAndIncrement() {
    return ATOMIC_LONG.getAndIncrement();
  }
}
