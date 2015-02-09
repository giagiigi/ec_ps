package jp.co.sint.webshop.service.communication;

import java.io.Serializable;

public class CampaignHeadLine implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String shopName;

  private String campaignCode;

  private String campaignName;
  private String campaignNameEn;
  private String campaignNameJp;

  private String campaignStartDate;

  private String campaignEndDate;

  private String campaignDiscountRate;

  private boolean campaignExists;

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * campaignDiscountRateを取得します。
   * 
   * @return campaignDiscountRate
   */
  public String getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  /**
   * campaignEndDateを取得します。
   * 
   * @return campaignEndDate
   */
  public String getCampaignEndDate() {
    return campaignEndDate;
  }

  /**
   * campaignNameを取得します。
   * 
   * @return campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  /**
   * campaignStartDateを取得します。
   * 
   * @return campaignStartDate
   */
  public String getCampaignStartDate() {
    return campaignStartDate;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopNameを取得します。
   * 
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * campaignCodeを設定します。
   * 
   * @param campaignCode
   *          設定する campaignCode
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * campaignDiscountRateを設定します。
   * 
   * @param campaignDiscountRate
   *          設定する campaignDiscountRate
   */
  public void setCampaignDiscountRate(String campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * campaignEndDateを設定します。
   * 
   * @param campaignEndDate
   *          設定する campaignEndDate
   */
  public void setCampaignEndDate(String campaignEndDate) {
    this.campaignEndDate = campaignEndDate;
  }

  /**
   * campaignNameを設定します。
   * 
   * @param campaignName
   *          設定する campaignName
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * campaignStartDateを設定します。
   * 
   * @param campaignStartDate
   *          設定する campaignStartDate
   */
  public void setCampaignStartDate(String campaignStartDate) {
    this.campaignStartDate = campaignStartDate;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * shopNameを設定します。
   * 
   * @param shopName
   *          設定する shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * campaignExistsを取得します。
   * 
   * @return campaignExists
   */
  public boolean isCampaignExists() {
    return campaignExists;
  }

  /**
   * campaignExistsを設定します。
   * 
   * @param campaignExists
   *          campaignExists
   */
  public void setCampaignExists(boolean campaignExists) {
    this.campaignExists = campaignExists;
  }

/**
 * @param campaignNameEn the campaignNameEn to set
 */
public void setCampaignNameEn(String campaignNameEn) {
	this.campaignNameEn = campaignNameEn;
}

/**
 * @return the campaignNameEn
 */
public String getCampaignNameEn() {
	return campaignNameEn;
}

/**
 * @param campaignNameJp the campaignNameJp to set
 */
public void setCampaignNameJp(String campaignNameJp) {
	this.campaignNameJp = campaignNameJp;
}

/**
 * @return the campaignNameJp
 */
public String getCampaignNameJp() {
	return campaignNameJp;
}

}
