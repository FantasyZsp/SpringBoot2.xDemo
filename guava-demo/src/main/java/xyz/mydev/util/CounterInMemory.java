package xyz.mydev.util;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZSP
 */
@ThreadSafe
public final class CounterInMemory implements ICounter {

  public static final AtomicLong ATOMIC_LONG = new AtomicLong();

  @Override
  public long getAndIncrement() {
    return ATOMIC_LONG.getAndIncrement();
  }

  @Override
  public long getAndDecrement() {
    return ATOMIC_LONG.getAndDecrement();
  }

  @Override
  public long get() {
    return ATOMIC_LONG.get();
  }

  @Override
  public long decrementAndGet() {
    return ATOMIC_LONG.decrementAndGet();
  }

  @Override
  public long incrementAndGet() {
    return ATOMIC_LONG.incrementAndGet();
  }

  @Override
  public long addAndGet(long delta) {
    return ATOMIC_LONG.addAndGet(delta);
  }

  @Override
  public void set(long value) {
    ATOMIC_LONG.set(value);
  }
}