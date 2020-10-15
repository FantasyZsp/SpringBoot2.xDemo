package xyz.mydev.common.beans.vo;

import lombok.Getter;
import lombok.ToString;

/**
 * @author ZSP
 */
@Getter
@ToString
public class NumberVO {
  private final Number count;

  private NumberVO(Number count) {
    this.count = count;
  }

  public static NumberVO of(Number count) {
    return new NumberVO(count);
  }
}
