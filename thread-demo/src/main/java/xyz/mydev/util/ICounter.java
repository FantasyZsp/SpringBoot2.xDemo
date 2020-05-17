package xyz.mydev.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZSP
 */
public interface ICounter {

  long getAndIncrement();

  long getAndDecrement();

  long get();

  long decrementAndGet();

  long incrementAndGet();

  long addAndGet(long delta);

  void set(long value);




}
