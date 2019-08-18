package xyz.mydev.jackson.dto;

import java.io.Serializable;

/**
 * @author  zhao
 * @date  2018/07/25:10/58
 * @description
 */
public class PictureDTO implements Serializable {
  private String vehiclePictureUrl;
  private String licencePlatePictureUrl;
  private String frontTopPictureUrl;
  private String backTopPictureUrl;
  private String leftFrontPictureUrl;
  private String leftMiddlePictureUrl;
  private String leftBackPictureUrl;
  private String rightFrontPictureUrl;
  private String rightMiddlePictureUrl;
  private String rightBackPictureUrl;
  private String leftFrontDamagePictureUrl;
  private String leftBackDamagePictureUrl;
  private String rightFrontDamagePictureUrl;
  private String rightBackDamagePictureUrl;
  private String leftMosaicPictureUrl;
  private String rightMosaicPictureUrl;
  private String topMosaicPictureUrl;
  private String vehicleBottomPictureUrl;

  public PictureDTO() {
  }

  public PictureDTO(String vehiclePictureUrl, String licencePlatePictureUrl, String frontTopPictureUrl, String backTopPictureUrl, String leftFrontPictureUrl, String leftMiddlePictureUrl, String leftBackPictureUrl, String rightFrontPictureUrl, String rightMiddlePictureUrl, String rightBackPictureUrl, String leftFrontDamagePictureUrl, String leftBackDamagePictureUrl, String rightFrontDamagePictureUrl, String rightBackDamagePictureUrl, String leftMosaicPictureUrl, String rightMosaicPictureUrl, String topMosaicPictureUrl, String vehicleBottomPictureUrl) {
    this.vehiclePictureUrl = vehiclePictureUrl;
    this.licencePlatePictureUrl = licencePlatePictureUrl;
    this.frontTopPictureUrl = frontTopPictureUrl;
    this.backTopPictureUrl = backTopPictureUrl;
    this.leftFrontPictureUrl = leftFrontPictureUrl;
    this.leftMiddlePictureUrl = leftMiddlePictureUrl;
    this.leftBackPictureUrl = leftBackPictureUrl;
    this.rightFrontPictureUrl = rightFrontPictureUrl;
    this.rightMiddlePictureUrl = rightMiddlePictureUrl;
    this.rightBackPictureUrl = rightBackPictureUrl;
    this.leftFrontDamagePictureUrl = leftFrontDamagePictureUrl;
    this.leftBackDamagePictureUrl = leftBackDamagePictureUrl;
    this.rightFrontDamagePictureUrl = rightFrontDamagePictureUrl;
    this.rightBackDamagePictureUrl = rightBackDamagePictureUrl;
    this.leftMosaicPictureUrl = leftMosaicPictureUrl;
    this.rightMosaicPictureUrl = rightMosaicPictureUrl;
    this.topMosaicPictureUrl = topMosaicPictureUrl;
    this.vehicleBottomPictureUrl = vehicleBottomPictureUrl;
  }

  public String getVehiclePictureUrl() {
    return vehiclePictureUrl;
  }

  public void setVehiclePictureUrl(String vehiclePictureUrl) {
    this.vehiclePictureUrl = vehiclePictureUrl;
  }

  public String getLicencePlatePictureUrl() {
    return licencePlatePictureUrl;
  }

  public void setLicencePlatePictureUrl(String licencePlatePictureUrl) {
    this.licencePlatePictureUrl = licencePlatePictureUrl;
  }

  public String getFrontTopPictureUrl() {
    return frontTopPictureUrl;
  }

  public void setFrontTopPictureUrl(String frontTopPictureUrl) {
    this.frontTopPictureUrl = frontTopPictureUrl;
  }

  public String getBackTopPictureUrl() {
    return backTopPictureUrl;
  }

  public void setBackTopPictureUrl(String backTopPictureUrl) {
    this.backTopPictureUrl = backTopPictureUrl;
  }

  public String getLeftFrontPictureUrl() {
    return leftFrontPictureUrl;
  }

  public void setLeftFrontPictureUrl(String leftFrontPictureUrl) {
    this.leftFrontPictureUrl = leftFrontPictureUrl;
  }

  public String getLeftMiddlePictureUrl() {
    return leftMiddlePictureUrl;
  }

  public void setLeftMiddlePictureUrl(String leftMiddlePictureUrl) {
    this.leftMiddlePictureUrl = leftMiddlePictureUrl;
  }

  public String getLeftBackPictureUrl() {
    return leftBackPictureUrl;
  }

  public void setLeftBackPictureUrl(String leftBackPictureUrl) {
    this.leftBackPictureUrl = leftBackPictureUrl;
  }

  public String getRightFrontPictureUrl() {
    return rightFrontPictureUrl;
  }

  public void setRightFrontPictureUrl(String rightFrontPictureUrl) {
    this.rightFrontPictureUrl = rightFrontPictureUrl;
  }

  public String getRightMiddlePictureUrl() {
    return rightMiddlePictureUrl;
  }

  public void setRightMiddlePictureUrl(String rightMiddlePictureUrl) {
    this.rightMiddlePictureUrl = rightMiddlePictureUrl;
  }

  public String getRightBackPictureUrl() {
    return rightBackPictureUrl;
  }

  public void setRightBackPictureUrl(String rightBackPictureUrl) {
    this.rightBackPictureUrl = rightBackPictureUrl;
  }

  public String getLeftFrontDamagePictureUrl() {
    return leftFrontDamagePictureUrl;
  }

  public void setLeftFrontDamagePictureUrl(String leftFrontDamagePictureUrl) {
    this.leftFrontDamagePictureUrl = leftFrontDamagePictureUrl;
  }

  public String getLeftBackDamagePictureUrl() {
    return leftBackDamagePictureUrl;
  }

  public void setLeftBackDamagePictureUrl(String leftBackDamagePictureUrl) {
    this.leftBackDamagePictureUrl = leftBackDamagePictureUrl;
  }

  public String getRightFrontDamagePictureUrl() {
    return rightFrontDamagePictureUrl;
  }

  public void setRightFrontDamagePictureUrl(String rightFrontDamagePictureUrl) {
    this.rightFrontDamagePictureUrl = rightFrontDamagePictureUrl;
  }

  public String getRightBackDamagePictureUrl() {
    return rightBackDamagePictureUrl;
  }

  public void setRightBackDamagePictureUrl(String rightBackDamagePictureUrl) {
    this.rightBackDamagePictureUrl = rightBackDamagePictureUrl;
  }

  public String getLeftMosaicPictureUrl() {
    return leftMosaicPictureUrl;
  }

  public void setLeftMosaicPictureUrl(String leftMosaicPictureUrl) {
    this.leftMosaicPictureUrl = leftMosaicPictureUrl;
  }

  public String getRightMosaicPictureUrl() {
    return rightMosaicPictureUrl;
  }

  public void setRightMosaicPictureUrl(String rightMosaicPictureUrl) {
    this.rightMosaicPictureUrl = rightMosaicPictureUrl;
  }

  public String getTopMosaicPictureUrl() {
    return topMosaicPictureUrl;
  }

  public void setTopMosaicPictureUrl(String topMosaicPictureUrl) {
    this.topMosaicPictureUrl = topMosaicPictureUrl;
  }

  public String getVehicleBottomPictureUrl() {
    return vehicleBottomPictureUrl;
  }

  public void setVehicleBottomPictureUrl(String vehicleBottomPictureUrl) {
    this.vehicleBottomPictureUrl = vehicleBottomPictureUrl;
  }

  @Override
  public String toString() {
    return "PictureDTO{" +
      "vehiclePictureUrl='" + vehiclePictureUrl + '\'' +
      ", licencePlatePictureUrl='" + licencePlatePictureUrl + '\'' +
      ", frontTopPictureUrl='" + frontTopPictureUrl + '\'' +
      ", backTopPictureUrl='" + backTopPictureUrl + '\'' +
      ", leftFrontPictureUrl='" + leftFrontPictureUrl + '\'' +
      ", leftMiddlePictureUrl='" + leftMiddlePictureUrl + '\'' +
      ", leftBackPictureUrl='" + leftBackPictureUrl + '\'' +
      ", rightFrontPictureUrl='" + rightFrontPictureUrl + '\'' +
      ", rightMiddlePictureUrl='" + rightMiddlePictureUrl + '\'' +
      ", rightBackPictureUrl='" + rightBackPictureUrl + '\'' +
      ", leftFrontDamagePictureUrl='" + leftFrontDamagePictureUrl + '\'' +
      ", leftBackDamagePictureUrl='" + leftBackDamagePictureUrl + '\'' +
      ", rightFrontDamagePictureUrl='" + rightFrontDamagePictureUrl + '\'' +
      ", rightBackDamagePictureUrl='" + rightBackDamagePictureUrl + '\'' +
      ", leftMosaicPictureUrl='" + leftMosaicPictureUrl + '\'' +
      ", rightMosaicPictureUrl='" + rightMosaicPictureUrl + '\'' +
      ", topMosaicPictureUrl='" + topMosaicPictureUrl + '\'' +
      ", vehicleBottomPictureUrl='" + vehicleBottomPictureUrl + '\'' +
      '}';
  }
}
