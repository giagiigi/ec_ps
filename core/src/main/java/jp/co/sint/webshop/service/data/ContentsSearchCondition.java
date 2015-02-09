package jp.co.sint.webshop.service.data;

import jp.co.sint.webshop.service.SearchCondition;

/**
 * コンテンツ情報を取得するための条件クラス<br>
 * 
 * @author System Integrator Corp.
 */
public class ContentsSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private ContentsType contentsType;

  private String shopCode;

  private String categoryCode;

  private String campaignCode;

  private String commodityCode;

  private String skuCode;

  private String giftCode;

  // 20131029 txw add start
  private String topImgLanguageCode;

  private String floorImgLanguageCode;
  // 20131029 txw add end

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * campaignCodeを設定します。
   * 
   * @param campaignCode
   *          campaignCode
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * categoryCodeを取得します。
   * 
   * @return categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * categoryCodeを設定します。
   * 
   * @param categoryCode
   *          categoryCode
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * contentsTypeを取得します。
   * 
   * @return contentsType
   */
  public ContentsType getContentsType() {
    return contentsType;
  }

  /**
   * contentsTypeを設定します。
   * 
   * @param contentsType
   *          contentsType
   */
  public void setContentsType(ContentsType contentsType) {
    this.contentsType = contentsType;
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
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * skuCodeを取得します。
   * 
   * @return skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * skuCodeを設定します。
   * 
   * @param skuCode
   *          skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * giftCodeを取得します。
   * 
   * @return giftCode
   */
  public String getGiftCode() {
    return giftCode;
  }

  /**
   * giftCodeを設定します。
   * 
   * @param giftCode
   *          giftCode
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  /**
   * @return the topImgLanguageCode
   */
  public String getTopImgLanguageCode() {
    return topImgLanguageCode;
  }

  /**
   * @param topImgLanguageCode
   *          the topImgLanguageCode to set
   */
  public void setTopImgLanguageCode(String topImgLanguageCode) {
    this.topImgLanguageCode = topImgLanguageCode;
  }

  
  /**
   * @return the floorImgLanguageCode
   */
  public String getFloorImgLanguageCode() {
    return floorImgLanguageCode;
  }

  
  /**
   * @param floorImgLanguageCode the floorImgLanguageCode to set
   */
  public void setFloorImgLanguageCode(String floorImgLanguageCode) {
    this.floorImgLanguageCode = floorImgLanguageCode;
  }

}
