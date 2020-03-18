package xyz.mydev.jdk.time.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @author ZSP  2019/09/10 18:18
 */
public class Java8DateUtils {

  private Java8DateUtils() {
  }

  public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  public static final String REMOVE_SECOND_TIME_FORMAT = "HH:mm";
  public static final String FILE_SUFFIX_DATE_FORMAT = "MMdd";


  public static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
  public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
  public static final DateTimeFormatter REMOVE_SECOND_FORMATTER = DateTimeFormatter.ofPattern(REMOVE_SECOND_TIME_FORMAT);
  public static final DateTimeFormatter FILE_SUFFIX_DATE_FORMATTER = DateTimeFormatter.ofPattern(FILE_SUFFIX_DATE_FORMAT);

  public static final LocalTime MAX_TIME_OF_BUSINESS = LocalTime.of(23, 59, 59, 999_999_000);


  /**
   * 字符串日期"yyyy-MM-dd"转LocalDate
   *
   * @author ZSP
   */
  public static LocalDate toLocalDate(String localDate) {
    return LocalDate.parse(localDate, DEFAULT_DATE_FORMATTER);
  }

  /**
   * LocalDate转字符串日期"yyyy-MM-dd"
   *
   * @author ZSP
   */
  public static String toString(LocalDate localDate) {
    return localDate.format(DEFAULT_DATE_FORMATTER);
  }


  /**
   * 字符串日期"yyyy-MM-dd HH:mm:ss"转LocalDateTime
   *
   * @author ZSP
   */
  public static LocalDateTime toLocalDateTime(String localDateTime) {
    return LocalDateTime.parse(localDateTime, DEFAULT_DATETIME_FORMATTER);
  }

  /**
   * LocalDateTime转字符串日期"yyyy-MM-dd HH:mm:ss"
   *
   * @author ZSP
   */
  public static String toString(LocalDateTime localDateTime) {
    return localDateTime.format(DEFAULT_DATETIME_FORMATTER);
  }

  /**
   * LocalDateTime 时间指定为00:00:00
   */
  public static LocalDateTime toTimeMin(LocalDateTime dateTime) {
    return dateTime == null ? null : dateTime.with(LocalTime.MIN);
  }

  /**
   * LocalDate 时间指定为00:00:00
   *
   * @author ZSP
   */
  public static LocalDateTime toTimeMin(LocalDate date) {
    return date == null ? null : date.atTime(LocalTime.MIN);
  }


  /**
   * @author ZSP
   */
  public static LocalDateTime toTimeMax(LocalDate date) {
    return date == null ? null : date.atTime(MAX_TIME_OF_BUSINESS);
  }

  public static LocalDateTime toTimeMax(LocalDateTime dateTime) {
    return dateTime == null ? null : dateTime.with(MAX_TIME_OF_BUSINESS);
  }

  /**
   * start end是否按时间先后顺序的
   * 要求start不晚于end
   *
   * @param start 开始时间
   * @param end   结束时间
   * @return boolean
   * @author ZSP
   */
  public static boolean isChronological(LocalDateTime start, LocalDateTime end) {
    return !start.isAfter(end);
  }
  public static boolean isChronological(LocalDate start, LocalDate end) {
    return !start.isAfter(end);
  }

  public static void checkChronological(LocalDateTime start, LocalDateTime end, String errorMsg) {
    if (!isChronological(start, end)) {
      throw new RuntimeException(errorMsg);
    }
  }

  /**
   * 查询指定日期所在周的周一日期
   */
  public static LocalDate getMonday(LocalDate date) {
    return date.with(ChronoField.DAY_OF_WEEK, DayOfWeek.MONDAY.getValue());
  }

  /**
   * 查询指定日期所在周的周日
   *
   * @author ZSP
   */
  public static LocalDate getSunday(LocalDate date) {
    return date.with(ChronoField.DAY_OF_WEEK, DayOfWeek.SUNDAY.getValue());
  }

  /**
   * 所在月第一天
   *
   * @author ZSP
   */
  public static LocalDate getFirstDayOfMonth(LocalDate date) {
    return date.with(TemporalAdjusters.firstDayOfMonth());
  }

  /**
   * 所在月最后一天
   *
   * @author ZSP
   */
  public static LocalDate getLastDayOfMonth(LocalDate date) {
    return date.with(TemporalAdjusters.lastDayOfMonth());
  }

  /**
   * LocalTime 转时间 HH:mm
   */
  public static String toRemoveSecond(LocalTime time) {
    return time.format(REMOVE_SECOND_FORMATTER);
  }


  /**
   * 获取两个日期之间的天数
   */
  public static long getBetweenDays(LocalDate startDate, LocalDate endDate) {
    return ChronoUnit.DAYS.between(startDate, endDate);
  }

}
