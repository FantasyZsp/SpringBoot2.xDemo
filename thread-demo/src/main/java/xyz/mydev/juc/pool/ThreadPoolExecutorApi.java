package xyz.mydev.juc.pool;

import org.junit.Test;
import xyz.mydev.common.utils.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZSP
 */
public class ThreadPoolExecutorApi {
  @Test
  public void testShutdown() {
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.shutdown();
    executorService.shutdown();
    ThreadUtils.sleepSeconds(3);
  }
}
