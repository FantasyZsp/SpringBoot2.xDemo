package xyz.mydev.juc.exceptiontest;

import lombok.extern.slf4j.Slf4j;
import xyz.mydev.util.ThreadUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class TimeOutDemo {
  private static ExecutorService executorService = Executors.newCachedThreadPool();

  public static void main(String[] args) throws InterruptedException {
//    testTimeoutAndInterrupt();
    print("***********main start***********");
    testThreadThrowException();

    testExecutorThrowException();
    executorService.shutdown();
    print("***********main end***********");
  }


  /**
   * 任务本身异常
   */
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
    thread.join();

    ThreadUtils.sleepSeconds(1);

    print("main is still running...");

    print("thread status==>" + thread.getState());

//    Thread.currentThread().join();

  }

  private static void print(String msg) {
    log.info(msg);
  }


}
