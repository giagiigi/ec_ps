package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车中商品的基本信息
 * 
 * @author OB
 */
public class CommodityOfCartInfo implements Serializable {

  /**
     * 
     */
  private static final long serialVersionUID = 1L;

  /** 商品コード */
  private String commodityCode;

  /** 商品名称 */
  private String commodityName;

  /** 購入商品数 */
  private Long purchasingAmount;

  /** 販売価格 */
  private BigDecimal retailPrice;

  /** 品牌编号 */
  private String brandCode;

  /** 进口商品区分 */
  private Long importCommodityType;

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return the purchasingAmount
   */
  public Long getPurchasingAmount() {
    return purchasingAmount;
  }

  /**
   * @param purchasingAmount
   *          the purchasingAmount to set
   */
  public void setPurchasingAmount(Long purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
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
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  /**
   * @param brandCode
   *          the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  /**
   * @return the importCommodityType
   */
  public Long getImportCommodityType() {
    return importCommodityType;
  }

  /**
   * @param importCommodityType
   *          the importCommodityType to set
   */
  public void setImportCommodityType(Long importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

}
