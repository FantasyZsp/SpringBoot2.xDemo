package xyz.mydev.common.utils;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaosp
 */
public class DateUtil {

  /**
   * 默认日期格式
   */
  public static String DEFAULT_FORMAT = "yyyy-MM-dd";
  public static String DEFAULT_FORMAT_1 = "yyyyMMdd";
  public static String DEFAULT_FORMAT_2 = "yyyyMMddHHmmssSSS";
  public static String DEFAULT_FORMAT_3 = "yyyy-MM-dd HH:mm:ss:SSS";
  public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);

  public static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
  public static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);

  public static final LocalTime MAX_TIME_OF_DAY_ON_SECONDS = LocalTime.of(23, 59, 59);
  public static final LocalTime MIN_TIME_OF_DAY = LocalTime.MIN;


  public static final Map<String, String> DAY_OF_WEEK_CN = new HashMap<>();

  static {
    DAY_OF_WEEK_CN.put(DayOfWeek.MONDAY.name(), "星期一");
    DAY_OF_WEEK_CN.put(DayOfWeek.TUESDAY.name(), "星期二");
    DAY_OF_WEEK_CN.put(DayOfWeek.WEDNESDAY.name(), "星期三");
    DAY_OF_WEEK_CN.put(DayOfWeek.THURSDAY.name(), "星期四");
    DAY_OF_WEEK_CN.put(DayOfWeek.FRIDAY.name(), "星期五");
    DAY_OF_WEEK_CN.put(DayOfWeek.SATURDAY.name(), "星期六");
    DAY_OF_WEEK_CN.put(DayOfWeek.SUNDAY.name(), "星期日");
  }

  /**
   * @description 根据"yyyy-MM-dd"获取中文星期几
   * @author ZSP
   * @date 2018/10/17
   */
  public static String getDayOfWeek(String date) {
    return DAY_OF_WEEK_CN.get(getLocalDate(date).getDayOfWeek().name());
  }

  /**
   * @description 字符串日期"yyyy-MM-dd"转Localdate
   * @author ZSP
   * @date 2018/10/19
   */
  public static LocalDate getLocalDate(String localDate) {
    return LocalDate.parse(localDate, DATE_FORMATTER);
  }

  /**
   * @description Localdate转字符串日期"yyyy-MM-dd"
   * @author ZSP
   * @date 2018/10/19
   */
  public static String getLocalDateString(LocalDate localDate) {
    return localDate.format(DATE_FORMATTER);
  }

  /**
   * @description 字符串时间"HH:mm:ss"转LocalTime
   * @author ZSP
   * @date 2018/10/20
   */
  public static LocalTime getLocalTime(String localTime) {
    return LocalTime.parse(localTime, TIME_FORMATTER);
  }

  /**
   * @description 字符串时间"HH:mm:ss"转LocalTime
   * @author ZSP
   * @date 2018/10/20
   */
  public static String getLocalTimeString(LocalTime localTime) {
    return localTime.format(TIME_FORMATTER);
  }

  /**
   * @description 根据开始时间和结束时间生成连续日期数组。
   * @author ZSP
   * @date 2018/10/20
   */
  public static List<String> getDateListFromStartToEnd(LocalDate startDate, LocalDate endDate) {
    Long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;
    // 生成日期数组，用于检查出没有数据的日期，初始化数据
    List<String> dayListFromStartToEnd = new ArrayList<>(days.intValue());
    for (int i = 1; i <= days; i++) {
      if (!startDate.isAfter(endDate)) {
        dayListFromStartToEnd.add(getLocalDateString(startDate));
      }
      startDate = startDate.plus(1L, ChronoUnit.DAYS);
    }
    return dayListFromStartToEnd;
  }

  /**
   * @description 根据时间获取刻钟序号，以开始时间的算法为准，在计算结束序号时需要减1.
   * @author ZSP
   * @date 2018/10/20
   */
  public static int obtainStartQuarterSequence(LocalTime startTime) {
    int isQuarterTime = isQuarterTime(startTime) ? 0 : 1;
    return startTime.getHour() * 4 + startTime.getMinute() / 15 + 1 + isQuarterTime;
  }

  /**
   * @description 23:59:59与23:59:59.999999999之间的时间都认为是当天结束时间。
   * @author ZSP
   * @date 2018/10/24
   */
  public static int obtainEndQuarterSequence(LocalTime endTime) {
    return isEndTimeOfDay(endTime) ? 96 : obtainStartQuarterSequence(endTime) - 1;
  }


  /**
   * @description 判断是否有必要使用增量表。依据时间跨度
   * @author ZSP
   * @date 2018/10/20
   */
  public static boolean isNeedFastQuery(String startDate, String endDate) {
    return isNeedFastQuery(getLocalDate(startDate), getLocalDate(endDate));
  }

  public static boolean isNeedFastQuery(LocalDate startDate, LocalDate endDate) {
    return ((int) startDate.until(endDate).get(ChronoUnit.DAYS)) >= 2;
  }

  /**
   * @description 判断是否属于刻钟时间
   * @author ZSP
   * @date 2018/10/20
   */
  public static boolean isQuarterTime(LocalTime startTime, LocalTime endTime) {
    return isQuarterTime(startTime)
      && (isQuarterTime(endTime) || isStartTimeOrEndTimeOfDay(endTime));
  }

  /**
   * @description 整点与分钟00 15 30 45 时刻
   * @author ZSP
   * @date 2018/10/20
   */
  public static boolean isQuarterTime(LocalTime localTime) {
    return localTime.getNano() == 0 && localTime.getSecond() == 0 && localTime.getMinute() % 15 == 0;
  }

  /**
   * @description 23:59:59 和 00:00:00
   * @author ZSP
   * @date 2018/10/20
   */
  public static boolean isStartTimeOrEndTimeOfDay(LocalTime localTime) {
    return isStartTimeOfDay(localTime) || isEndTimeOfDay(localTime);
  }

  public static boolean isStartTimeOfDay(LocalTime localTime) {
    return MIN_TIME_OF_DAY.equals(localTime);
  }

  public static boolean isEndTimeOfDay(LocalTime localTime) {
    return (!localTime.isBefore(MAX_TIME_OF_DAY_ON_SECONDS) && !localTime.isAfter(LocalTime.MAX));
  }


  /**
   * @description 判断两组时间段跨度
   * Status        变量                         枚举            区域图示
   * -2  左全跨     isAllBeforeMinDate          BEFORE_MIN      ()       -> MIN
   * -1  左跨       isCrossMinDate              CROSS_MIN       ([MIN])  -> MAX
   * 0   命中       isBetweenMinAndMax          BETWEEN         MIN[]MAX
   * 1   右跨       isCrossMaxDate              CROSS_MAX       MIN      -> ([MAX])
   * 2   右全跨     isAllAfterMaxDate           AFTER_MAX       MAX      -> ()
   * 3   全跨       isCrossAllAndNotAtEdge      CROSS          (MIN,MAX)
   * <p>
   * <p>
   * 计算方法
   * 1、未在区间内(区间外处理)
   * boolean isAllBeforeMinDate = tmpEndDay.isBefore(minDate);
   * boolean isAllAfterMaxDate  = tmpStartDay.isAfter(maxDate);
   * 2、全命中(区间内处理)
   * boolean isBetweenMinAndMax = (!tmpStartDay.isBefore(minDate)) && (!tmpEndDay.isAfter(maxDate));
   * 3、跨越了区间(区间内外处理)
   * boolean isCrossMinDate     = tmpStartDay.isBefore(minDate) && (!tmpEndDay.isBefore(minDate) && !tmpEndDay.isAfter(maxDate));
   * boolean isCrossMaxDate     = (!tmpStartDay.isBefore(minDate) && !tmpStartDay.isAfter(maxDate)) && tmpEndDay.isAfter(maxDate);
   * 4、全跨越(左中右处理)
   * boolean isCrossAllAndNotAtEdge = tmpStartDay.isBefore(minDate) && tmpEndDay.isAfter(maxDate);
   * @author ZSP
   * @date 2018/10/20
   */

  public static int timeArrayCrossStatus(String minDate, String maxDate, String startDate, String endDate) {
    return timeArrayCrossStatus(getLocalDate(minDate), getLocalDate(maxDate), getLocalDate(startDate), getLocalDate(endDate));
  }

  public static int timeArrayCrossStatus(LocalDate minDate, LocalDate maxDate, LocalDate startDate, LocalDate endDate) {

    if (endDate.isBefore(minDate)) {
      return TimesCrossStatus.BEFORE_MIN.getCode();
    } else if (startDate.isAfter(maxDate)) {
      return TimesCrossStatus.AFTER_MAX.getCode();
    } else if ((!startDate.isBefore(minDate)) && (!endDate.isAfter(maxDate))) {
      return TimesCrossStatus.BETWEEN.getCode();
    } else if (startDate.isBefore(minDate) && (!endDate.isBefore(minDate) && !endDate.isAfter(maxDate))) {
      return TimesCrossStatus.CROSS_MIN.getCode();
    } else if (startDate.isBefore(minDate) && endDate.isAfter(maxDate)) {
      return TimesCrossStatus.CROSS.getCode();
    } else {
      // if ((!startDate.isBefore(minDate) && !startDate.isAfter(maxDate)) && endDate.isAfter(maxDate))
      return TimesCrossStatus.CROSS_MAX.getCode();
    }

  }

  /**
   * @description 根据日期和刻钟序列号计算同步时间(以时段结束时间为准 。 如果是当天最后时刻 ， 按照次日零点计算)。
   * @author ZSP
   * @date 2018/10/22
   */
  public static LocalDateTime generateSyncFlagTime(LocalDate syncDate, int quarterSequence) {
    return quarterSequence == 96
      ? LocalDateTime.of(syncDate.plusDays(1), MIN_TIME_OF_DAY)
      : LocalDateTime.of(syncDate, LocalTime.of(quarterSequence / 4, (quarterSequence % 4) * 15));
  }

  public static LocalDateTime generateSyncFlagTime(String syncDate, int quarterSequence) {
    return generateSyncFlagTime(getLocalDate(syncDate), quarterSequence);
  }

  /**
   * @description 获取两个日期间隔的天数。同一日期得到的结果为1
   * @author ZSP
   * @date 2018/10/22
   */

  public static int getDaysFromStart2End(LocalDate startDate, LocalDate endDate) {
    return (int) (startDate.until(endDate, ChronoUnit.DAYS) + 1);
  }

  public static int getDaysFromStart2End(String startDate, String endDate) {

    return getDaysFromStart2End(getLocalDate(startDate), getLocalDate(endDate));
  }

  /**
   * @description 根据开始结束日期，得到连续的日期list
   * @author ZSP
   * @date 2018/10/23
   */
  public static List<String> getDateListFromStart2End(LocalDate start, LocalDate end) {
    int days = getDaysFromStart2End(start, end);
    List<String> dayListFromStartToEnd = new ArrayList<>(days);
    for (int i = 1; i <= days; i++) {
      if (!start.isAfter(end)) {
        dayListFromStartToEnd.add(getLocalDateString(start));
      }
      start = start.plus(1L, ChronoUnit.DAYS);
    }
    return dayListFromStartToEnd;
  }

  public static List<String> getDateListFromStart2End(String start, String end) {
    return getDateListFromStart2End(getLocalDate(start), getLocalDate(end));
  }


}
