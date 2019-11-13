package xyz.mydev.juc.tea;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 洗水壶、烧开水
 *
 * @author ZSP
 */
@Slf4j
public class WaterTask implements Callable<String> {

  private FutureTask<String> futureTask;

  public WaterTask(FutureTask<String> futureTask) {
    this.futureTask = futureTask;
  }

  @Override
  public String call() throws Exception {
    log.info("T1: 洗水壶...");
    TimeUnit.SECONDS.sleep(1);

    log.info("T1: 烧开水...");
    TimeUnit.SECONDS.sleep(3);

    log.info("T1: 等待茶叶...");
    String tf = futureTask.get();
    log.info("T1: 拿到茶叶:" + tf);

    log.info("T1: 泡茶...");
    return " 上茶:" + tf;
  }
}
