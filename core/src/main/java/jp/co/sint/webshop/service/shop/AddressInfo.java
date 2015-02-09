package jp.co.sint.webshop.service.shop;

import java.io.Serializable;

public class AddressInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String prefectureCode;

  private String prefectureName;

  private String cityCode;

  private String cityName;

  private String areaCode;

  private String areaName;

  /**
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * @param prefectureCode
   *          the prefectureCode to set
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * @return the prefectureName
   */
  public String getPrefectureName() {
    return prefectureName;
  }

  /**
   * @param prefectureName
   *          the prefectureName to set
   */
  public void setPrefectureName(String prefectureName) {
    this.prefectureName = prefectureName;
  }

  /**
   * @return the cityCode
   */
  public String getCityCode() {
    return cityCode;
  }

  /**
   * @param cityCode
   *          the cityCode to set
   */
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @param cityName
   *          the cityName to set
   */
  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  /**
   * @return the areaCode
   */
  public String getAreaCode() {
    return areaCode;
  }

  /**
   * @param areaCode
   *          the areaCode to set
   */
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  /**
   * @return the areaName
   */
  public String getAreaName() {
    return areaName;
  }

  /**
   * @param areaName
   *          the areaName to set
   */
  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

}
