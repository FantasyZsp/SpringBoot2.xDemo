package xyz.mydev.jdk.baseapi.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author ZSP  2019/09/10 16:31
 */
public class TimeCompare {
  public static void main(String[] args) {
    LocalDateTime min = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    LocalDateTime min2 = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    System.out.println(min.isAfter(min2));
    System.out.println(min.isBefore(min2));

    System.out.println(LocalDateTime.now().toString());

  }

}
