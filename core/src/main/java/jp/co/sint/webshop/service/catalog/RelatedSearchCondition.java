package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class RelatedSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String effectualCode;

  private String searchCommodityCodeStart;

  private String searchCommodityCodeEnd;

  private String searchCommodityName;

  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return searchCommodityCodeEnd
   */
  public String getSearchCommodityCodeEnd() {
    return searchCommodityCodeEnd;
  }

  /**
   * @param searchCommodityCodeEnd
   *          設定する searchCommodityCodeEnd
   */
  public void setSearchCommodityCodeEnd(String searchCommodityCodeEnd) {
    this.searchCommodityCodeEnd = searchCommodityCodeEnd;
  }

  /**
   * @return searchCommodityCodeStart
   */
  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
  }

  /**
   * @param searchCommodityCodeStart
   *          設定する searchCommodityCodeStart
   */
  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
  }

  /**
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  /**
   * @param searchCommodityName
   *          設定する searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  public boolean isValid() {
    boolean result = true;

    String commodityCodeStart = getSearchCommodityCodeStart();
    String commodityCodeEnd = getSearchCommodityCodeEnd();

    if (StringUtil.hasValueAllOf(commodityCodeStart, commodityCodeEnd)) {
      result = StringUtil.isCorrectRange(commodityCodeStart, commodityCodeEnd);
    }
    return result;

  }

  /**
   * @param effectualCode
   *          設定する effectualCode
   */
  public void setEffectualCode(String effectualCode) {
    this.effectualCode = effectualCode;
  }

  /**
   * @return effectualCode
   */
  public String getEffectualCode() {
    return effectualCode;
  }
}
