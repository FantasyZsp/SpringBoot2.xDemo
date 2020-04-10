package xyz.mydev.util;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaosp
 */
public class ThreadUtils {
  public static void sleepSeconds(long timeout) {
    try {
      TimeUnit.SECONDS.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void join(long milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
