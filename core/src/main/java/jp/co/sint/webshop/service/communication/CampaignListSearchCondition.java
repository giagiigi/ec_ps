package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class CampaignListSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String campaignCode;

  private String campaignName;

  private String campaignStartDateFrom;

  private String campaignStartDateTo;

  private String campaignEndDateFrom;

  private String campaignEndDateTo;

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * campaignEndDateFromを取得します。
   * 
   * @return campaignEndDateFrom
   */
  public String getCampaignEndDateFrom() {
    return campaignEndDateFrom;
  }

  /**
   * campaignEndDateToを取得します。
   * 
   * @return campaignEndDateTo
   */
  public String getCampaignEndDateTo() {
    return campaignEndDateTo;
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
   * campaignStartDateFromを取得します。
   * 
   * @return campaignStartDateFrom
   */
  public String getCampaignStartDateFrom() {
    return campaignStartDateFrom;
  }

  /**
   * campaignStartDateToを取得します。
   * 
   * @return campaignStartDateTo
   */
  public String getCampaignStartDateTo() {
    return campaignStartDateTo;
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
   * campaignCodeを設定します。
   * 
   * @param campaignCode
   *          設定する campaignCode
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * campaignEndDateFromを設定します。
   * 
   * @param campaignEndDateFrom
   *          設定する campaignEndDateFrom
   */
  public void setCampaignEndDateFrom(String campaignEndDateFrom) {
    this.campaignEndDateFrom = campaignEndDateFrom;
  }

  /**
   * campaignEndDateToを設定します。
   * 
   * @param campaignEndDateTo
   *          設定する campaignEndDateTo
   */
  public void setCampaignEndDateTo(String campaignEndDateTo) {
    this.campaignEndDateTo = campaignEndDateTo;
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
   * campaignStartDateFromを設定します。
   * 
   * @param campaignStartDateFrom
   *          設定する campaignStartDateFrom
   */
  public void setCampaignStartDateFrom(String campaignStartDateFrom) {
    this.campaignStartDateFrom = campaignStartDateFrom;
  }

  /**
   * campaignStartDateToを設定します。
   * 
   * @param campaignStartDateTo
   *          設定する campaignStartDateTo
   */
  public void setCampaignStartDateTo(String campaignStartDateTo) {
    this.campaignStartDateTo = campaignStartDateTo;
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

  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getCampaignStartDateFrom();
    String startDateTo = getCampaignStartDateTo();
    String endDateFrom = getCampaignEndDateFrom();
    String endDateTo = getCampaignEndDateTo();

    if (StringUtil.hasValueAllOf(startDateFrom, startDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, startDateTo);
    }
    if (StringUtil.hasValueAllOf(endDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(endDateFrom, endDateTo);
    }
    return result;

  }

}
