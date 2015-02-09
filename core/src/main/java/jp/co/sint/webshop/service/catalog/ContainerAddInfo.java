package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContainerAddInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 利用可能な在庫 */
  private Long availableStockQuantity;

  /** 販売価格 */
  private BigDecimal retailPrice;

  /** カテゴリ関連付き件数 */
  private Long relatedCategoryCount;

  /** キャンペーン関連付き件数 */
  private Long relatedCampaignCount;

  /** ギフト関連付き件数 */
  private Long relatedGiftCount;
  
  // 2012/11/17 促销对应 ob add start
  /** 套餐明细件数 */
  private Long relatedCompositionCount;
  // 2012/11/17 促销对应 ob add end

  /** タグ関連付き件数 */
  private Long relatedTagCount;

  /** 手動関連付け商品関連付き件数 */
  private Long relatedCommodityACount;
  
  /** 自動関連付け商品関連付き件数 */
  private Long relatedCommodityBCount;

  private Long saleType;
  
  private Long saleStatus;

  private Long stockStatus;

  /**
   * @return the stockStatus
   */
  public Long getStockStatus() {
    return stockStatus;
  }

  /**
   * @param stockStatus
   *          the stockStatus to set
   */
  public void setStockStatus(Long stockStatus) {
    this.stockStatus = stockStatus;
  }

  /**
   * @return the saleType
   */
  public Long getSaleType() {
    return saleType;
  }

  /**
   * @param saleType
   *          the saleType to set
   */
  public void setSaleType(Long saleType) {
    this.saleType = saleType;
  }

  /**
   * @return the availableStockQuantity
   */
  public Long getAvailableStockQuantity() {
    return availableStockQuantity;
  }

  /**
   * @param availableStockQuantity
   *          the availableStockQuantity to set
   */
  public void setAvailableStockQuantity(Long availableStockQuantity) {
    this.availableStockQuantity = availableStockQuantity;
  }

  /**
   * @return the relatedCampaignCount
   */
  public Long getRelatedCampaignCount() {
    return relatedCampaignCount;
  }

  /**
   * @param relatedCampaignCount
   *          the relatedCampaignCount to set
   */
  public void setRelatedCampaignCount(Long relatedCampaignCount) {
    this.relatedCampaignCount = relatedCampaignCount;
  }

  /**
   * @return the relatedCategoryCount
   */
  public Long getRelatedCategoryCount() {
    return relatedCategoryCount;
  }

  /**
   * @param relatedCategoryCount
   *          the relatedCategoryCount to set
   */
  public void setRelatedCategoryCount(Long relatedCategoryCount) {
    this.relatedCategoryCount = relatedCategoryCount;
  }

  /**
   * @return the relatedGiftCount
   */
  public Long getRelatedGiftCount() {
    return relatedGiftCount;
  }

  /**
   * @param relatedGiftCount
   *          the relatedGiftCount to set
   */
  public void setRelatedGiftCount(Long relatedGiftCount) {
    this.relatedGiftCount = relatedGiftCount;
  }

  /**
   * @return the relatedTagCount
   */
  public Long getRelatedTagCount() {
    return relatedTagCount;
  }

  /**
   * @param relatedTagCount
   *          the relatedTagCount to set
   */
  public void setRelatedTagCount(Long relatedTagCount) {
    this.relatedTagCount = relatedTagCount;
  }

  /**
   * @return the retailPrice
   */
  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  /**
   * @param retailPrice
   *          the retailPrice to set
   */
  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  
  /**
   * relatedCommodityACountを返します。
   * @return the relatedCommodityACount
   */
  public Long getRelatedCommodityACount() {
    return relatedCommodityACount;
  }

  
  /**
   * relatedCommodityBCountを返します。
   * @return the relatedCommodityBCount
   */
  public Long getRelatedCommodityBCount() {
    return relatedCommodityBCount;
  }

  
  /**
   * relatedCommodityACountを設定します。
   * @param relatedCommodityACount 設定する relatedCommodityACount
   */
  public void setRelatedCommodityACount(Long relatedCommodityACount) {
    this.relatedCommodityACount = relatedCommodityACount;
  }

  
  /**
   * relatedCommodityBCountを設定します。
   * @param relatedCommodityBCount 設定する relatedCommodityBCount
   */
  public void setRelatedCommodityBCount(Long relatedCommodityBCount) {
    this.relatedCommodityBCount = relatedCommodityBCount;
  }

  
  /**
   * saleStatusを取得します。
   *
   * @return saleStatus
   */
  
  public Long getSaleStatus() {
    return saleStatus;
  }

  
  /**
   * saleStatusを設定します。
   *
   * @param saleStatus 
   *          saleStatus
   */
  public void setSaleStatus(Long saleStatus) {
    this.saleStatus = saleStatus;
  }
  //2012/11/17 促销对应 ob add start
  /**
   * relatedCompositionCountを取得します。
   *
   * @return relatedCompositionCount
   */
    
  public Long getRelatedCompositionCount() {
	return relatedCompositionCount;
  }
  /**
   * relatedCompositionCountを設定します。
   *
   * @param relatedCompositionCount 
   *          relatedCompositionCount
   */
  public void setRelatedCompositionCount(Long relatedCompositionCount) {
	this.relatedCompositionCount = relatedCompositionCount;
  }
//2012/11/17 促销对应 ob add end
}
