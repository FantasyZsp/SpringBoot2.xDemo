package xyz.mydev.jdk.util.time;

import java.time.LocalDateTime;

/**
 * @author ZSP
 */
public class TimeFormatter {
  public static void main(String[] args) {

    for (int i = 0; i < 100; i++) {
      LocalDateTime now = LocalDateTime.now();
      int nano = now.getNano();
      System.out.println(nano);
      nano = nano - nano % 1000;
      System.out.println(nano);
      System.out.println(now.withNano(nano));
    }


  }
}
