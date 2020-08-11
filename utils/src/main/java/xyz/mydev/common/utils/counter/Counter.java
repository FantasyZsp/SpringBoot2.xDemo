package xyz.mydev.common.utils.counter;

/**
 * @author ZSP
 */
public class Counter {

  private static final ICounter COUNTER = getCounter();

  private Counter() {
    throw new UnsupportedOperationException();
  }

  protected static ICounter getCounter() {
    if (COUNTER != null) {
      return COUNTER;
    }
    return new CounterInMemory();
  }


  public static long getAndIncrement() {
    return COUNTER.getAndIncrement();
  }

  public static long getAndDecrement() {
    return COUNTER.getAndDecrement();
  }

  public static long get() {
    return COUNTER.get();
  }

  public static long decrementAndGet() {
    return COUNTER.decrementAndGet();
  }

  public static long incrementAndGet() {
    return COUNTER.incrementAndGet();
  }

  public static long addAndGet(long delta) {
    return COUNTER.addAndGet(delta);
  }

  public static void set(long value) {
    COUNTER.set(value);
  }


}
