package xyz.mydev.jackson.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author zhao
 * @date 2018/07/25:11/46
 * @description
 */
public class ProcessDTO implements Serializable {
  private String step;
  private Instant updateTime;

  public ProcessDTO() {
  }

  public ProcessDTO(String step, Instant updateTime) {
    this.step = step;
    this.updateTime = updateTime;
  }

  public String getStep() {
    return step;
  }

  public void setStep(String step) {
    this.step = step;
  }

  public Instant getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Instant updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public String toString() {
    return "ProcessDTO{" +
      "step='" + step + '\'' +
      ", updateTime='" + updateTime + '\'' +
      '}';
  }
}
