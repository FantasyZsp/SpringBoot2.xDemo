package xyz.mydev.jackson.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "job_queue")
public class JobQueueDTO implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String jobQueueCode;
  private String businessType;
  private String laneCode;
  private String laneName;
  private String laneType;

  @Column(length = 4)
  private String direction;

  @Column(columnDefinition = "timestamp")
  private Instant startTime;

  private Instant endTime;
  private String licensePlateNumber;
  private String ocrLicensePlateNumber;

  private String rfidLicensePlateNumber;
  private String rfidCardNumber;
  private String icCardNumber;
  private String customsLockNumber;
  private Float totalWeight;

  private Integer containerQuantity;

  @Transient
  private List<ContainerDTO> containers = new ArrayList<>();

  private String releasedType;
  private String releasedBy;

  private Instant releasedTime;

  @JsonIgnore
  @Transient
  private List<ReleaseRuleResultDTO> releaseRuleResults = new ArrayList<>();

  @Transient
  @JsonIgnore
  private PictureDTO pictures;

  @Transient
  @JsonIgnore
  private List<ProcessDTO> processes;

  @Transient
  @JsonIgnore
  private ExtraDTO extras;

  private String createdBy;

  private Instant createdTime;


  private String lastModifiedBy;

  private Instant lastModifiedTime;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    JobQueueDTO jobQueueDTO = (JobQueueDTO) o;
    if (jobQueueDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), jobQueueDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "JobQueueDTO{" +
      "id=" + id +
      ", jobQueueCode='" + jobQueueCode + '\'' +
      ", businessType='" + businessType + '\'' +
      ", laneCode='" + laneCode + '\'' +
      ", laneName='" + laneName + '\'' +
      ", laneType='" + laneType + '\'' +
      ", direction='" + direction + '\'' +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      ", licensePlateNumber='" + licensePlateNumber + '\'' +
      ", ocrLicensePlateNumber='" + ocrLicensePlateNumber + '\'' +
      ", rfidLicensePlateNumber='" + rfidLicensePlateNumber + '\'' +
      ", rfidCardNumber='" + rfidCardNumber + '\'' +
      ", icCardNumber='" + icCardNumber + '\'' +
      ", customsLockNumber='" + customsLockNumber + '\'' +
      ", totalWeight=" + totalWeight +
      ", containerQuantity=" + containerQuantity +
      ", releaseRuleResults=" + releaseRuleResults +
      ", releasedType='" + releasedType + '\'' +
      ", releasedBy='" + releasedBy + '\'' +
      ", releasedTime=" + releasedTime +
      ", pictures=" + pictures +
      ", processes=" + processes +
      ", extras=" + extras +
      ", createdBy='" + createdBy + '\'' +
      ", createdTime=" + createdTime +
      ", lastModifiedBy='" + lastModifiedBy + '\'' +
      ", lastModifiedTime=" + lastModifiedTime +
      ", containers=" + containers +
      '}';
  }
}
