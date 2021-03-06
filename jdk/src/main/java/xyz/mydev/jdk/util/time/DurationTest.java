package xyz.mydev.jdk.util.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * 时差计算
 * 时间差
 *
 * @author ZSP
 */
public class DurationTest {
  public static void main(String[] args) {
    LocalDateTime now = LocalDateTime.now();
    long convert = TimeUnit.MINUTES.convert(Duration.between(now, now.plusMinutes(199)));
    System.out.println(convert);

    long convert2 = TimeUnit.MINUTES.convert(Duration.between(now.plusMinutes(199), now));
    System.out.println(convert2);

    System.out.println(now.until(now.plusMinutes(10L), ChronoUnit.MINUTES));
  }
}
