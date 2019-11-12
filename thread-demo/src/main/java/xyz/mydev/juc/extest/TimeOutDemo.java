package xyz.mydev.juc.extest;

import lombok.extern.slf4j.Slf4j;
import xyz.mydev.util.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author ZSP
 */
@Slf4j
public class TimeOutDemo {
  private static ExecutorService executorService = Executors.newCachedThreadPool();

  public static void main(String[] args) throws InterruptedException {
//    testTimeoutAndInterrupt();
    print("***********main start***********");

//    testExecutorThrowException();
    testThreadThrowException();
    executorService.shutdown();
    print("***********main end***********");
  }

  private static void testTimeoutAndInterrupt() throws ExecutionException, InterruptedException {
    List<Thread> callableThread = new ArrayList<>(1);

    Callable<String> callable = () -> {
      try {
        callableThread.add(Thread.currentThread());
        TimeUnit.SECONDS.sleep(2);
        log.info("I am wake up naturally...");
        return "WAKE_UP_NATURALLY";
      } catch (InterruptedException e) {
        log.info("I am wake up interrupted...");
        return "WAKE_UP_INTERRUPTED";
      }
    };

    Future<String> future = executorService.submit(callable);

    executorService.submit(() -> {
      try {
        TimeUnit.MILLISECONDS.sleep(1500);
        callableThread.get(0).interrupt();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    String msg;
    try {
      msg = future.get(1, TimeUnit.SECONDS);
    } catch (TimeoutException e) {
      log.info("获取超时了，转为无限时获取");
      msg = future.get();
    }
    log.info(msg);

    executorService.shutdown();
  }


  private static void testExecutorThrowException() throws InterruptedException {
    print("test start...");
    Future<String> future = executorService.submit(() -> {
      print("future commit");
      throw new RuntimeException("future internal ex");
    });
    TimeUnit.SECONDS.sleep(1);
    print("try to get result...");
    try {
      String result = future.get();
      print("result is " + result);
    } catch (InterruptedException e) {
      print("InterruptedException");
      e.printStackTrace();
    } catch (ExecutionException e) {
      print("ExecutionException");
      e.printStackTrace();
    } catch (RuntimeException e) {
      print("RuntimeException");
      e.printStackTrace();
    }
  }

  /**
   * 如果线程出现异常，内部不处理会直接终结
   *
   * @author ZSP
   */
  private static void testThreadThrowException() throws InterruptedException {
    print("test start...");
    Thread thread = new Thread(() -> {
      print("from thread runnable");
      throw new RuntimeException("thread runnable internal ex");
    });
    thread.setUncaughtExceptionHandler((t, ex) -> {
      print(t.getName() + "internal ex occur..." + "==>" + ex.getLocalizedMessage());
    });


    thread.start();

    ThreadUtils.sleep(1);

    print("main is still running...");

    print("thread status==>" + thread.getState());

    Thread.currentThread().join();

  }

  private static void print(String msg) {
    log.info(msg);
  }


}
