package xyz.mydev.jdk.bean;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author ZSP
 */
@Data
public class DefaultMultiKey implements IMultiKey {
  private Integer cycleSequence;
  private LocalDateTime cycleStartTime;
  private LocalDateTime cycleEndTime;
  private String orgId;
  private String tenantId;

  public static DefaultMultiKey extractKey(IMultiKey task) {
    if (task == null) {
      throw new IllegalArgumentException("task must not be null when extractKey");
    }

    DefaultMultiKey key = new DefaultMultiKey();

    key.setCycleSequence(task.getCycleSequence());
    key.setCycleStartTime(task.getCycleStartTime());
    key.setCycleEndTime(task.getCycleEndTime());
    key.setOrgId(task.getOrgId());
    key.setTenantId(task.getTenantId());

    return key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null) {
      return false;
    }

    if (!(o instanceof IMultiKey)) {
      return false;
    }
    IMultiKey that = (IMultiKey) o;
    return Objects.equals(getCycleSequence(), that.getCycleSequence()) &&
      getCycleStartTime().equals(that.getCycleStartTime()) &&
      getCycleEndTime().equals(that.getCycleEndTime()) &&
      getOrgId().equals(that.getOrgId()) &&
      getTenantId().equals(that.getTenantId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCycleSequence(), getCycleStartTime(), getCycleEndTime(), getOrgId(), getTenantId());
  }

  public boolean belongs(IMultiKey key) {
    return Objects.equals(getCycleSequence(), key.getCycleSequence()) &&
      getCycleStartTime().equals(key.getCycleStartTime()) &&
      getCycleEndTime().equals(key.getCycleEndTime()) &&
      getOrgId().equals(key.getOrgId()) &&
      getTenantId().equals(key.getTenantId());
  }
}
