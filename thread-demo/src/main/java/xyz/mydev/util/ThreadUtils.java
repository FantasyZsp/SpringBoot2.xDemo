package xyz.mydev.util;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaosp
 */
public class ThreadUtils {
  public static void sleep(long timeout) {
    try {
      TimeUnit.SECONDS.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
