package xyz.mydev.juc.completablefuture;

import lombok.extern.slf4j.Slf4j;
import xyz.mydev.juc.exception.ThreadExecutionException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class CompletableFutureDemo {
  public static void main(String[] args) {
    testCompletableFuture();
  }


  private static void testCompletableFuture() {
    // 任务 1：洗水壶 -> 烧开水
    CompletableFuture<Void> f1 =
      CompletableFuture.runAsync(() -> {

        log.info("==============>T1: 洗水壶...");
        sleep(1, TimeUnit.SECONDS);

        log.info("==============>T1: 烧开水...");
        sleep(5, TimeUnit.SECONDS);
      });

    // 任务 2：洗茶壶 -> 洗茶杯 -> 拿茶叶
    CompletableFuture<String> f2 =
      CompletableFuture.supplyAsync(() -> {
        log.info("T2: 洗茶壶...");
        sleep(1, TimeUnit.SECONDS);

        log.info("T2: 洗茶杯...");
        sleep(2, TimeUnit.SECONDS);

        if (true) {
          throw new ThreadExecutionException("茶杯摔碎了");
        }

        log.info("T2: 拿茶叶...");
        sleep(1, TimeUnit.SECONDS);
        return " 龙井 ";
      });

    // 任务 3：任务 1 和任务 2 完成后执行：泡茶
    CompletableFuture<String> f3 =
      f1.thenCombine(f2, (aVoid, tf) -> {
        log.info("==============>T1: 拿到茶叶:" + tf);
        log.info("==============>T1: 泡茶...");
        return " 上茶:" + tf;
      });
    // 等待任务 3 执行结果
    log.info(f3.join());
  }


  private static void sleep(int t, TimeUnit u) {
    try {
      u.sleep(t);
    } catch (Exception e) {
      throw new ThreadExecutionException(e);
    }
  }
}
