package xyz.mydev.jdk.util.time.until;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
@Slf4j
public class UntilTest {


  public static void main(String[] args) {
    LocalDateTime time = LocalDateTime.now();
    LocalDateTime now = time
      .minusDays(1)
      .plusMinutes(1);

    System.out.println(TimeUnit.DAYS.convert(Duration.between(now, time)));

    if (TimeUnit.DAYS.convert(Duration.between(now, time)) > 1) {
      log.warn("may be an expire msg: {}", time);
    }
  }
}
