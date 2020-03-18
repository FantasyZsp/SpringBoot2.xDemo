package xyz.mydev.jdk.time.util;

import java.util.Objects;

/**
 *
 * @author ZSP
 */
public enum CycleTypeEnum {
  /**
   * 日
   */
  DAY(1),
  /**
   * 周
   */
  WEEK(1),
  /**
   * 自然月
   */
  CALENDAR_MONTH(30),
  /**
   * 周月
   */
  WEEKLY_MONTH(32),
  /**
   * 年
   */
  YEAR(12);

  private int code;

  CycleTypeEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }


  public static CycleTypeEnum fromCode(Integer code) {
    for (CycleTypeEnum cycleTypeEnum : CycleTypeEnum.values()) {
      if (Objects.equals(cycleTypeEnum.getCode(), code)) {
        return cycleTypeEnum;
      }
    }
    throw new IllegalArgumentException("Invalid cycle type!");
  }
}
