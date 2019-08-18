package xyz.mydev.jackson.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the ReleaseRuleResult entity.
 */
public class ReleaseRuleResultDTO implements Serializable {

  private Long id;
  private Integer sequence;
  private String releaseRuleName;
  private String releaseResultMessage;
  private Instant updateTime;
  private Long jobQueueId;
  private String jobQueueCode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public String getReleaseRuleName() {
    return releaseRuleName;
  }

  public void setReleaseRuleName(String releaseRuleName) {
    this.releaseRuleName = releaseRuleName;
  }

  public String getReleaseResultMessage() {
    return releaseResultMessage;
  }

  public void setReleaseResultMessage(String releaseResultMessage) {
    this.releaseResultMessage = releaseResultMessage;
  }

  public Instant getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Instant updateTime) {
    this.updateTime = updateTime;
  }

  public Long getJobQueueId() {
    return jobQueueId;
  }

  public void setJobQueueId(Long jobQueueId) {
    this.jobQueueId = jobQueueId;
  }

  public String getJobQueueCode() {
    return jobQueueCode;
  }

  public void setJobQueueCode(String jobQueueCode) {
    this.jobQueueCode = jobQueueCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReleaseRuleResultDTO releaseRuleResultDTO = (ReleaseRuleResultDTO) o;
    if (releaseRuleResultDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), releaseRuleResultDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "ReleaseRuleResultDTO{" +
      "id=" + getId() +
      ", sequence=" + getSequence() +
      ", releaseRuleName='" + getReleaseRuleName() + "'" +
      ", releaseResultMessage='" + getReleaseResultMessage() + "'" +
      ", updateTime='" + getUpdateTime() + "'" +
      "}";
  }
}
