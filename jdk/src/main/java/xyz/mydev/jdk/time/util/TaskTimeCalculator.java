package xyz.mydev.jdk.time.util;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

import static xyz.mydev.jdk.time.util.Java8DateUtils.getFirstDayOfMonth;
import static xyz.mydev.jdk.time.util.Java8DateUtils.getLastDayOfMonth;
import static xyz.mydev.jdk.time.util.Java8DateUtils.getMonday;
import static xyz.mydev.jdk.time.util.Java8DateUtils.isChronological;
import static xyz.mydev.jdk.time.util.Java8DateUtils.toTimeMax;
import static xyz.mydev.jdk.time.util.Java8DateUtils.toTimeMin;

/**
 * 计算计件任务周期时间等
 * <p>
 * UTC+8
 *
 * @author ZSP
 */
public class TaskTimeCalculator {

  /**
   * 根据计件任务开始执行日期、周期类型就算对于发布消息时间处于的周期序列号和周期开始结束时间
   *
   * @param startDate        角色计件组任务开始执行日期
   * @param cycleType        周期类型 {@link CycleTypeEnum}
   * @param eventPublishTime 上游消息发布时间
   * @return Optional<CycleInfoWrapper> 当发布时间早于开始执行日期时返回empty。
   * @author ZSP
   */
  public static Optional<CycleInfoWrapper> calculate(LocalDate startDate, Integer cycleType, LocalDateTime eventPublishTime) {
    return calculate(startDate, CycleTypeEnum.fromCode(cycleType), eventPublishTime);
  }

  public static Optional<CycleInfoWrapper> calculate(LocalDate startDate, CycleTypeEnum cycleTypeEnum, LocalDateTime eventPublishTime) {

    LocalDate publishEventDate = eventPublishTime.toLocalDate();
    if (!isChronological(startDate, publishEventDate)) {
      return Optional.empty();
    }

    switch (cycleTypeEnum) {
      case DAY:
        return dayCase(startDate, publishEventDate);
      case WEEK:
        return weekCase(startDate, publishEventDate);
      case CALENDAR_MONTH:
        return calendarMonthCase(startDate, publishEventDate);
      case WEEK_MONTH:
        // TODO
        return weekMonthCase(startDate, publishEventDate);
      case YEAR:
        return yearCase(startDate, publishEventDate);
      default:
        throw new IllegalStateException("Unexpected value: " + cycleTypeEnum);
    }
  }

  public static Optional<CycleInfoWrapper> yearCase(LocalDate startDate, LocalDate publishEventDate) {
    if (!isChronological(startDate, publishEventDate)) {
      return Optional.empty();
    }

    int yearsOfStart = startDate.getYear();
    int yearsOfPublish = publishEventDate.getYear();
    int years = yearsOfPublish - yearsOfStart;

    CycleInfoWrapper wrapper = new CycleInfoWrapper();
    wrapper.setCycleSequence(years + 1);

    if (years == 0) {
      // 同周期
      wrapper.setCycleStartTime(toTimeMin(startDate));
      wrapper.setCycleEndTime(toTimeMax(startDate.with(TemporalAdjusters.lastDayOfYear())));
    } else {
      wrapper.setCycleStartTime(toTimeMin(publishEventDate.withDayOfYear(1)));
      wrapper.setCycleEndTime(toTimeMax(publishEventDate.with(TemporalAdjusters.lastDayOfYear())));
    }

    return Optional.of(wrapper);
  }

  public static Optional<CycleInfoWrapper> weekMonthCase(LocalDate startDate, LocalDate publishEventDate) {
    if (!isChronological(startDate, publishEventDate)) {
      return Optional.empty();
    }
    throw new RuntimeException("未实现周月计算！");
  }

  public static Optional<CycleInfoWrapper> calendarMonthCase(LocalDate startDate, LocalDate publishEventDate) {
    if (!isChronological(startDate, publishEventDate)) {
      return Optional.empty();
    }

    LocalDate firstDayOfStart = getFirstDayOfMonth(startDate);
    LocalDate firstDayOfPublish = getFirstDayOfMonth(publishEventDate);

    int months = (int) ChronoUnit.MONTHS.between(firstDayOfStart, firstDayOfPublish);

    CycleInfoWrapper wrapper = new CycleInfoWrapper();
    wrapper.setCycleSequence(months + 1);

    if (startDate.getYear() == publishEventDate.getYear() && startDate.getMonth().equals(publishEventDate.getMonth())) {
      // 同周期
      wrapper.setCycleStartTime(toTimeMin(startDate));
      wrapper.setCycleEndTime(toTimeMax(getLastDayOfMonth(startDate)));
    } else {
      wrapper.setCycleStartTime(toTimeMin(firstDayOfPublish));
      wrapper.setCycleEndTime(toTimeMax(getLastDayOfMonth(publishEventDate)));
    }

    return Optional.of(wrapper);
  }


  /**
   * WEEK CycleType
   * 计算针对 publishEventDate所在周期的开始执行时间
   *
   * @param startDate        计件开始执行日期
   * @param publishEventDate 消息发布日期
   * @author ZSP
   */
  public static Optional<CycleInfoWrapper> weekCase(LocalDate startDate, LocalDate publishEventDate) {
    if (!isChronological(startDate, publishEventDate)) {
      return Optional.empty();
    }
    LocalDate sundayOfStartDate = Java8DateUtils.getSunday(startDate);
    LocalDate sundayOfPublishing = Java8DateUtils.getSunday(publishEventDate);

    CycleInfoWrapper wrapper = new CycleInfoWrapper();
    int weeks = (int) ChronoUnit.WEEKS.between(sundayOfPublishing, sundayOfStartDate);
    wrapper.setCycleSequence(weeks + 1);
    if (weeks == 0) {
      // 同周期，也说明是第一个周期
      wrapper.setCycleStartTime(toTimeMin(startDate));
      wrapper.setCycleEndTime(toTimeMax(sundayOfStartDate));
    } else {
      wrapper.setCycleStartTime(toTimeMin(getMonday(publishEventDate)));
      wrapper.setCycleEndTime(toTimeMax(sundayOfPublishing));
    }

    return Optional.of(wrapper);
  }

  public static Optional<CycleInfoWrapper> dayCase(LocalDate startDate, LocalDate publishEventDate) {

    if (!isChronological(startDate, publishEventDate)) {
      return Optional.empty();
    }

    long days = startDate.until(publishEventDate, ChronoUnit.DAYS);
    int cycleSequence = (int) (days + 1);
    LocalDateTime cycleStartTime = startDate.plusDays(days).atTime(LocalTime.MIN);
    LocalDateTime cycleEndTime = toTimeMax(cycleStartTime);

    CycleInfoWrapper wrapper = new CycleInfoWrapper();
    wrapper.setCycleSequence(cycleSequence);
    wrapper.setCycleStartTime(cycleStartTime);
    wrapper.setCycleEndTime(cycleEndTime);
    return Optional.of(wrapper);
  }

  @Data
  public static class CycleInfoWrapper {
    private Integer cycleSequence;
    private LocalDateTime cycleStartTime;
    private LocalDateTime cycleEndTime;
  }
}
