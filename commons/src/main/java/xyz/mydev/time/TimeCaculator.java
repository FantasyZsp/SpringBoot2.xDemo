package xyz.mydev.time;

import xyz.mydev.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * @author  ZSP
 * @date  2018/10/20 17:53
 * @description
 */
public class TimeCaculator {
  public static final LocalTime END_TIME_OF_DAY = LocalTime.of(23, 59, 59);
  public static final LocalTime END_TIME_OF_DAY2 = LocalTime.MAX;
  public static final LocalTime END_TIME_OF_DAY3 = LocalTime.MIN;

  public static void main(String[] args) {
    System.out.println(DateUtil.obtainStartQuarterSequence(LocalTime.parse("08:30:02.002")));
    System.out.println(DateUtil.obtainStartQuarterSequence(LocalTime.parse("15:30:49")));
    System.out.println(DateUtil.obtainStartQuarterSequence(LocalTime.parse("15:30:49.002")));

    LocalDate localDate = LocalDate.now();
    System.out.println(localDate);

    LocalDate localDate1 = localDate.withDayOfYear(1);
    System.out.println(localDate1);

    LocalDate localDate2 = localDate.with(TemporalAdjusters.firstDayOfYear());
    LocalDate localDate3 = localDate.with(TemporalAdjusters.lastDayOfYear());
    System.out.println(localDate2);
    System.out.println(localDate3);


  }
}
