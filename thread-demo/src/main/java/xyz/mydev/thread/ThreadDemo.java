package xyz.mydev.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP  2019/04/25 17:28
 */
public class ThreadDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<String> wash = new FutureTask<>(new WashTea());
    FutureTask<String> serve = new FutureTask<>(new ServeTea(wash));
    Thread T1 = new Thread(serve);
    T1.start();
    Thread T2 = new Thread(wash);
    T2.start();
    System.out.println(serve.get());
  }

  static class ServeTea implements Callable<String> {
    FutureTask<String> ft2;

    ServeTea(FutureTask<String> ft2) {
      this.ft2 = ft2;
    }

    @Override
    public String call() throws Exception {
      System.out.println("T1: 洗水壶...");
      TimeUnit.SECONDS.sleep(1);

      System.out.println("T1: 烧开水...");
      TimeUnit.SECONDS.sleep(15);
      // 获取 T2 线程的茶叶
      String tf = ft2.get();
      System.out.println("T1: 拿到茶叶:" + tf);

      System.out.println("T1: 泡茶...");
      return " 上茶:" + tf;
    }
  }


  public static class WashTea implements Callable<String> {
    @Override
    public String call() throws Exception {
      System.out.println("T2: 洗茶壶...");
      TimeUnit.SECONDS.sleep(1);

      System.out.println("T2: 洗茶杯...");
      TimeUnit.SECONDS.sleep(1);

      System.out.println("T2: 拿茶叶...");
      TimeUnit.SECONDS.sleep(1);
      return " 龙井 ";
    }
  }

}
