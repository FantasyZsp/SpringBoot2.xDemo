package xyz.mydev.jackson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ExtraDTO implements Serializable {

  private Set<ReservationDTO> reservations = new HashSet<>();

  @JsonProperty("isOcrLicensePlateNumberActivated")
  private Boolean ocrLicensePlateNumberActivated;

  @JsonProperty("isOcrContainerNumberActivated")
  private Boolean ocrContainerNumberActivated;

  public ExtraDTO() {
  }

  public ExtraDTO(HashSet<ReservationDTO> reservations, Boolean ocrLicensePlateNumberActivated, Boolean ocrContainerNumberActivated) {
    this.reservations = reservations;
    this.ocrLicensePlateNumberActivated = ocrLicensePlateNumberActivated;
    this.ocrContainerNumberActivated = ocrContainerNumberActivated;
  }

  public static class ReservationDTO implements Serializable {

    private String reservationJobNumber;
    private String reservationJobType;

    public ReservationDTO() {
    }

    public ReservationDTO(String reservationJobNumber, String reservationJobType) {
      this.reservationJobNumber = reservationJobNumber;
      this.reservationJobType = reservationJobType;
    }

    public String getReservationJobNumber() {
      return reservationJobNumber;
    }

    public void setReservationJobNumber(String reservationJobNumber) {
      this.reservationJobNumber = reservationJobNumber;
    }

    public String getReservationJobType() {
      return reservationJobType;
    }

    public void setReservationJobType(String reservationJobType) {
      this.reservationJobType = reservationJobType;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      ReservationDTO that = (ReservationDTO) o;

      if (!reservationJobNumber.equals(that.reservationJobNumber)) return false;
      return reservationJobType.equals(that.reservationJobType);
    }

    @Override
    public int hashCode() {
      int result = reservationJobNumber.hashCode();
      result = 31 * result + reservationJobType.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return "ReservationDTO{" +
        "reservationJobNumber='" + reservationJobNumber + '\'' +
        ", reservationJobType='" + reservationJobType + '\'' +
        '}';
    }
  }


  public Set<ReservationDTO> getReservations() {
    return reservations;
  }

  public void setReservations(Set<ReservationDTO> reservations) {
    this.reservations = reservations;
  }

  public Boolean getisOcrLicensePlateNumberActivated() {
    return ocrLicensePlateNumberActivated;
  }

  public void setisOcrLicensePlateNumberActivated(Boolean ocrLicensePlateNumberActivated) {
    this.ocrLicensePlateNumberActivated = ocrLicensePlateNumberActivated;
  }

  public Boolean getisOcrContainerNumberActivated() {
    return ocrContainerNumberActivated;
  }

  public void setisOcrContainerNumberActivated(Boolean ocrContainerNumberActivated) {
    this.ocrContainerNumberActivated = ocrContainerNumberActivated;
  }

  @Override
  public String toString() {
    return "ExtrasDTO{" +
      "reservations=" + reservations +
      ", ocrLicensePlateNumberActivated=" + ocrLicensePlateNumberActivated +
      ", ocrContainerNumberActivated=" + ocrContainerNumberActivated +
      '}';
  }
}
