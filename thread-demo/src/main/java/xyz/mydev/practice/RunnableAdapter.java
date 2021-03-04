package xyz.mydev.practice;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * {@link io.netty.util.concurrent.PromiseTask.RunnableAdapter}
 * {@link Executors.RunnableAdapter}
 *
 * @author ZSP
 */
public class RunnableAdapter<T> implements Callable<T> {

  private final Runnable runnable;
  private final T result;

  public RunnableAdapter(Runnable runnable, T result) {
    this.runnable = runnable;
    this.result = result;
  }


  @Override
  public T call() throws Exception {
    runnable.run();
    return result;
  }
}
