package xyz.mydev.jackson.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Container entity.
 */
public class ContainerDTO implements Serializable {

  private Long id;
  private String containerNumber;
  private String ocrContainerNumber;
  private String isoCode;
  @JsonProperty("isOcrContainerNumberSucceeded")
  private Boolean ocrContainerNumberSucceeded;
  private String sealNumber;
  private String createdBy;
  private Instant createdTime;
  private String lastModifiedBy;
  private Instant lastModifiedTime;
  private List<DamageDTO> damages;
  private Long jobQueueId;
  private String jobQueueCode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContainerNumber() {
    return containerNumber;
  }

  public void setContainerNumber(String containerNumber) {
    this.containerNumber = containerNumber;
  }

  public String getOcrContainerNumber() {
    return ocrContainerNumber;
  }

  public void setOcrContainerNumber(String ocrContainerNumber) {
    this.ocrContainerNumber = ocrContainerNumber;
  }

  public String getIsoCode() {
    return isoCode;
  }

  public void setIsoCode(String isoCode) {
    this.isoCode = isoCode;
  }

  public Boolean isOcrContainerNumberSucceeded() {
    return ocrContainerNumberSucceeded;
  }

  public void setOcrContainerNumberSucceeded(Boolean ocrContainerNumberSucceeded) {
    this.ocrContainerNumberSucceeded = ocrContainerNumberSucceeded;
  }

  public List<DamageDTO> getDamages() {
    return damages;
  }

  public void setDamages(List<DamageDTO> damages) {
    this.damages = damages;
  }

  public String getSealNumber() {
    return sealNumber;
  }

  public void setSealNumber(String sealNumber) {
    this.sealNumber = sealNumber;
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

    ContainerDTO containerDTO = (ContainerDTO) o;
    if (containerDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), containerDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "ContainerDTO{" +
      "id=" + getId() +
      ", containerNumber='" + getContainerNumber() + "'" +
      ", ocrContainerNumber='" + getOcrContainerNumber() + "'" +
      ", isoCode='" + getIsoCode() + "'" +
      ", ocrContainerNumberSucceeded='" + isOcrContainerNumberSucceeded() + "'" +
      ", damages='" + getDamages() + "'" +
      ", sealNumber='" + getSealNumber() + "'" +
      ", createdBy='" + getCreatedBy() + "'" +
      ", createdTime='" + getCreatedTime() + "'" +
      ", lastModifiedBy='" + getLastModifiedBy() + "'" +
      ", lastModifiedTime='" + getLastModifiedTime() + "'" +
      "}";
  }
}
