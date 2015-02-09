package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class RelatedSearchConditionBaseCommodity extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String searchEffectualCodeStart;

  private String searchEffectualCodeEnd;

  private String searchEffectualName;

  private String searchStartDateFrom;

  private String searchStartDateTo;

  private String searchEndDateFrom;

  private String searchEndDateTo;

  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchStartDateFrom();
    String startDateTo = getSearchStartDateTo();
    String endDateFrom = getSearchEndDateFrom();
    String endDateTo = getSearchEndDateTo();

    if (StringUtil.hasValueAllOf(startDateFrom, startDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, startDateTo);
    }
    if (StringUtil.hasValueAllOf(endDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(endDateFrom, endDateTo);
    }
    return result;

  }

  
  /**
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  
  /**
   * @param commodityCode 設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
  /**
   * @return searchEffectualCodeEnd
   */
  public String getSearchEffectualCodeEnd() {
    return searchEffectualCodeEnd;
  }

  
  /**
   * @param searchEffectualCodeEnd 設定する searchEffectualCodeEnd
   */
  public void setSearchEffectualCodeEnd(String searchEffectualCodeEnd) {
    this.searchEffectualCodeEnd = searchEffectualCodeEnd;
  }

  
  /**
   * @return searchEffectualCodeStart
   */
  public String getSearchEffectualCodeStart() {
    return searchEffectualCodeStart;
  }

  
  /**
   * @param searchEffectualCodeStart 設定する searchEffectualCodeStart
   */
  public void setSearchEffectualCodeStart(String searchEffectualCodeStart) {
    this.searchEffectualCodeStart = searchEffectualCodeStart;
  }

  
  /**
   * @return searchEffectualName
   */
  public String getSearchEffectualName() {
    return searchEffectualName;
  }

  
  /**
   * @param searchEffectualName 設定する searchEffectualName
   */
  public void setSearchEffectualName(String searchEffectualName) {
    this.searchEffectualName = searchEffectualName;
  }

  
  /**
   * @return searchEndDateFrom
   */
  public String getSearchEndDateFrom() {
    return searchEndDateFrom;
  }

  
  /**
   * @param searchEndDateFrom 設定する searchEndDateFrom
   */
  public void setSearchEndDateFrom(String searchEndDateFrom) {
    this.searchEndDateFrom = searchEndDateFrom;
  }

  
  /**
   * @return searchEndDateTo
   */
  public String getSearchEndDateTo() {
    return searchEndDateTo;
  }

  
  /**
   * @param searchEndDateTo 設定する searchEndDateTo
   */
  public void setSearchEndDateTo(String searchEndDateTo) {
    this.searchEndDateTo = searchEndDateTo;
  }

  
  /**
   * @return searchStartDateFrom
   */
  public String getSearchStartDateFrom() {
    return searchStartDateFrom;
  }

  
  /**
   * @param searchStartDateFrom 設定する searchStartDateFrom
   */
  public void setSearchStartDateFrom(String searchStartDateFrom) {
    this.searchStartDateFrom = searchStartDateFrom;
  }

  
  /**
   * @return searchStartDateTo
   */
  public String getSearchStartDateTo() {
    return searchStartDateTo;
  }

  
  /**
   * @param searchStartDateTo 設定する searchStartDateTo
   */
  public void setSearchStartDateTo(String searchStartDateTo) {
    this.searchStartDateTo = searchStartDateTo;
  }

  
  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  
  /**
   * @param shopCode 設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
