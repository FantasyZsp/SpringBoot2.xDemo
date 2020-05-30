package xyz.mydev.util;

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
