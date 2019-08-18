package xyz.mydev.jackson.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the ReleaseRule entity.
 */
public class ReleaseRuleDTO implements Serializable {

  private Long id;
  private String releaseRuleName;
  private String description;
  private Integer priority;
  private Boolean enabled;
  private String onPermit;
  private String onDeny;
  private String onPending;
  private String releaseRuleClass;
  private String releaseRuleMethod;
  private String releaseRuleParameters;
  private String createdBy;
  private Instant createdTime;
  private String lastModifiedBy;

  private Instant lastModifiedTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReleaseRuleName() {
    return releaseRuleName;
  }

  public void setReleaseRuleName(String releaseRuleName) {
    this.releaseRuleName = releaseRuleName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getOnPermit() {
    return onPermit;
  }

  public void setOnPermit(String onPermit) {
    this.onPermit = onPermit;
  }

  public String getOnDeny() {
    return onDeny;
  }

  public void setOnDeny(String onDeny) {
    this.onDeny = onDeny;
  }

  public String getOnPending() {
    return onPending;
  }

  public void setOnPending(String onPending) {
    this.onPending = onPending;
  }

  public String getReleaseRuleClass() {
    return releaseRuleClass;
  }

  public void setReleaseRuleClass(String releaseRuleClass) {
    this.releaseRuleClass = releaseRuleClass;
  }

  public String getReleaseRuleMethod() {
    return releaseRuleMethod;
  }

  public void setReleaseRuleMethod(String releaseRuleMethod) {
    this.releaseRuleMethod = releaseRuleMethod;
  }

  public String getReleaseRuleParameters() {
    return releaseRuleParameters;
  }

  public void setReleaseRuleParameters(String releaseRuleParameters) {
    this.releaseRuleParameters = releaseRuleParameters;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Instant getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Instant createdTime) {
    this.createdTime = createdTime;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Instant getLastModifiedTime() {
    return lastModifiedTime;
  }

  public void setLastModifiedTime(Instant lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReleaseRuleDTO releaseRuleDTO = (ReleaseRuleDTO) o;
    if (releaseRuleDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), releaseRuleDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "ReleaseRuleDTO{" +
      "id=" + getId() +
      ", releaseRuleName='" + getReleaseRuleName() + "'" +
      ", description='" + getDescription() + "'" +
      ", priority=" + getPriority() +
      ", enabled='" + isEnabled() + "'" +
      ", onPermit='" + getOnPermit() + "'" +
      ", onDeny='" + getOnDeny() + "'" +
      ", onPending='" + getOnPending() + "'" +
      ", releaseRuleClass='" + getReleaseRuleClass() + "'" +
      ", releaseRuleMethod='" + getReleaseRuleMethod() + "'" +
      ", releaseRuleParameters='" + getReleaseRuleParameters() + "'" +
      ", createdBy='" + getCreatedBy() + "'" +
      ", createdTime='" + getCreatedTime() + "'" +
      ", lastModifiedBy='" + getLastModifiedBy() + "'" +
      ", lastModifiedTime='" + getLastModifiedTime() + "'" +
      "}";
  }
}
