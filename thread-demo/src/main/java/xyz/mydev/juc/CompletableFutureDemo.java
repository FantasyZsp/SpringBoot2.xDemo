package xyz.mydev.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP  2019/04/25 17:52
 */
public class CompletableFutureDemo {

  public static void main(String[] args) {
    // 任务 1：洗水壶 -> 烧开水
    CompletableFuture<Void> f1 =
      CompletableFuture.runAsync(() -> {
        System.out.println("T1: 洗水壶...");
        sleep(1);

        System.out.println("T1: 烧开水...");
        sleep(1);
      });
    // 任务 2：洗茶壶 -> 洗茶杯 -> 拿茶叶
    CompletableFuture<String> f2 =
      CompletableFuture.supplyAsync(() -> {
        System.out.println("T2: 洗茶壶...");
        sleep(1);

        System.out.println("T2: 洗茶杯...");
        sleep(1);

        System.out.println("T2: 拿茶叶...");
        sleep(2);
        return " 龙井 ";
      });
    // 任务 3：任务 1 和任务 2 完成后执行：泡茶
    CompletableFuture<String> f3 =
      f1.thenCombine(f2, (__, tf) -> {
        System.out.println("T1: 拿到茶叶:" + tf);
        System.out.println("T1: 泡茶...");
        return " 上茶:" + tf;
      });

    // 等待任务 3 执行结果
    System.out.println(f3.join());
  }

  private static void sleep(int t) {
    try {
      TimeUnit.SECONDS.sleep(t);
    } catch (InterruptedException ignored) {
    }
  }
}
