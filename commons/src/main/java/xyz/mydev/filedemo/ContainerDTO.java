package xyz.mydev.filedemo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import xyz.mydev.jackson.dto.DamageDTO;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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
      ", ocrContainerNumberSucceeded='" + getOcrContainerNumberSucceeded() + "'" +
      ", damages='" + getDamages() + "'" +
      ", sealNumber='" + getSealNumber() + "'" +
      ", createdBy='" + getCreatedBy() + "'" +
      ", createdTime='" + getCreatedTime() + "'" +
      ", lastModifiedBy='" + getLastModifiedBy() + "'" +
      ", lastModifiedTime='" + getLastModifiedTime() + "'" +
      "}";
  }
}
