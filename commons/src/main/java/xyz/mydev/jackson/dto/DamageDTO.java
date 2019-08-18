package xyz.mydev.jackson.dto;

import java.io.Serializable;

/**
 * @author  zhao
 * @date  2018/07/26:10/24
 * @description
 */
public class DamageDTO implements Serializable {
  private String side;
  private String damageCode;
  private String damageGrade;
  private String damagePart;
  private String remark;

  public DamageDTO() {
  }

  public DamageDTO(String side, String damageCode, String damageGrade, String damagePart, String remark) {
    this.side = side;
    this.damageCode = damageCode;
    this.damageGrade = damageGrade;
    this.damagePart = damagePart;
    this.remark = remark;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getDamageCode() {
    return damageCode;
  }

  public void setDamageCode(String damageCode) {
    this.damageCode = damageCode;
  }

  public String getDamageGrade() {
    return damageGrade;
  }

  public void setDamageGrade(String damageGrade) {
    this.damageGrade = damageGrade;
  }

  public String getDamagePart() {
    return damagePart;
  }

  public void setDamagePart(String damagePart) {
    this.damagePart = damagePart;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
