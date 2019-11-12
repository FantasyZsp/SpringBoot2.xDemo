package xyz.mydev.juc.exceptiontest;

import lombok.extern.slf4j.Slf4j;

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
 * 测试超时和打断情况下线程的状态转换以及对异常的感知
 *
 * @author ZSP
 */
@Slf4j
public class TimeoutAndInterruptDemo {
  private static ExecutorService executorService = Executors.newCachedThreadPool();

  public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
    testTimeoutAndInterrupt();
    executorService.shutdown();
    print("***********main end***********");
  }

  /**
   * 超时和打断
   */
  private static void testTimeoutAndInterrupt() throws ExecutionException, InterruptedException, TimeoutException {
    List<Thread> callableThread = new ArrayList<>(1);

    Callable<String> callable = () -> {
      try {
        callableThread.add(Thread.currentThread());
        TimeUnit.SECONDS.sleep(1);
        log.info("I am wake up naturally...");
        if (true) {
          throw new RuntimeException(" ex ...");
        }


        return "WAKE_UP_NATURALLY";
      } catch (InterruptedException e) {
        log.info("I am wake up interrupted...");
        log.info("after wake up inner state: {}", Thread.currentThread().getState().name());

        return "WAKE_UP_INTERRUPTED";
      }
    };

    Future<String> future = executorService.submit(callable);

//    executorService.submit(() -> {
//      try {
//        TimeUnit.MILLISECONDS.sleep(1990);
//        callableThread.get(0).interrupt();
//        print(callableThread.get(0).getState().name());
//
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    });

    String msg;
    try {
      msg = future.get(1, TimeUnit.SECONDS);
    } catch (TimeoutException e) {
      log.info("获取超时了，转为无限时获取");
      System.out.println(future.isCancelled());
      System.out.println(future.isDone());
      System.out.println(future.cancel(true));

      msg = future.get();
    }
    log.info("result: {}", msg);

    executorService.shutdown();
  }


  private static void print(String msg) {
    log.info(msg);
  }

}


