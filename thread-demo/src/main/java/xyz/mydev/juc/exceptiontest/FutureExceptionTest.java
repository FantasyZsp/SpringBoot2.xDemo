package xyz.mydev.juc.exceptiontest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
public class FutureExceptionTest {
  private static ExecutorService executorService = Executors.newFixedThreadPool(1);


  public static void main(String[] args) throws InterruptedException {
    Future<?> testExceptionFuture = executorService.submit(() -> {
      System.out.println("testExceptionFuture executed...");
      if (true) {
        throw new RuntimeException("eee");
      }
      return "test";

    });
    try {
      TimeUnit.SECONDS.sleep(1);
      System.out.println(testExceptionFuture.isCancelled());
      System.out.println(testExceptionFuture.isDone());
    } finally {
      executorService.shutdown();
      TimeUnit.SECONDS.sleep(1);
      System.out.println(testExceptionFuture.isCancelled());
      System.out.println(testExceptionFuture.isDone());
    }


  }

}
