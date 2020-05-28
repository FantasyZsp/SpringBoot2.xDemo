package xyz.mydev.jdk.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author ZSP
 */
public class DurationTest {
  public static void main(String[] args) {
    long convert = TimeUnit.MINUTES.convert(Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(199)));
    System.out.println(convert);

    long convert2 = TimeUnit.MINUTES.convert(Duration.between(LocalDateTime.now().plusMinutes(199), LocalDateTime.now()));
    System.out.println(convert2);


  }
}
