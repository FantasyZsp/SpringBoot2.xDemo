package xyz.mydev.jackson.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * @author zhao
 * @date 2018/09/12 11:26
 * @description
 */
public class MPlanVO {

  @JsonProperty("planId")
  private Long id;

  @JsonProperty("planCode")
  private String code;
  private Instant instant;

  public MPlanVO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Instant getInstant() {
    return instant;
  }

  public void setInstant(Instant instant) {
    this.instant = instant;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MPlanVO MPlanVO = (MPlanVO) o;

    if (!id.equals(MPlanVO.id)) return false;
    return code.equals(MPlanVO.code);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + code.hashCode();
    return result;
  }
}
