package xyz.mydev.juc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author ZSP
 */
public class ScheduledThreadPoolExecutorDemo {
  public static void main(String[] args) {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
  }
}
