package xyz.mydev.juc.tea;

import lombok.extern.slf4j.Slf4j;
import xyz.mydev.juc.exception.ThreadExecutionException;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 洗茶壶、洗茶杯、拿茶叶
 *
 * @author ZSP
 */
@Slf4j
public class TeaExceptionTask implements Callable<String> {
  @Override
  public String call() throws Exception {
    log.info("{}: 洗茶壶...", Thread.currentThread().getName());
    TimeUnit.SECONDS.sleep(1);

    log.info("{}: 洗茶杯...", Thread.currentThread().getName());
    if (Thread.currentThread().getName() != null) {
      throw new ThreadExecutionException("茶杯破了");
    }
    TimeUnit.SECONDS.sleep(2);

    log.info("{}: 拿茶叶...", Thread.currentThread().getName());
    TimeUnit.SECONDS.sleep(1);
    return "西湖龙井";
  }
}
