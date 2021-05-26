package xyz.mydev.jdk.baseapi.time;

import java.time.LocalDate;

/**
 * @author ZSP  2019/08/24 09:55
 */
public class LocalDateDemo {
  public static void main(String[] args) {
    LocalDate localDate = LocalDate.now();
    LocalDate localDate1 = localDate.minusDays(7);
    System.out.println(localDate1);
  }

}
