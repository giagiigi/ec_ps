package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalesAmountBySkuSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** ショップ名 */
  private String shopName;

  /** ショップコード */
  private String shopCode;

  /** 商品コード */
  private String commodityCode;

  /** SKUコード */
  private String skuCode;

  /** 規格別商品名称 */
  private String commoditySkuName;

  /** 売上金額累計 */
  private BigDecimal totalSalesPrice;

  /** 売上税額累計 */
  private BigDecimal totalSalesPriceTax;

  /** 返金額累計 */
  private BigDecimal totalRefund;

  /** 返金税額累計 */
  private BigDecimal totalRefundTax;

  /** 注文個数累計 */
  private Long totalOrderQuantity;

  /** 返品個数累計 */
  private Long totalReturnItemQuantity;

  /** 値引き額累計 */
  private BigDecimal totalDiscountAmount;

  /** ギフト金額累計 */
  private BigDecimal totalGiftPrice;

  /** ギフト税額累計 */
  private BigDecimal totalGiftTax;

  public BigDecimal getTotalDiscountAmount() {
    return totalDiscountAmount;
  }

  public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
    this.totalDiscountAmount = totalDiscountAmount;
  }

  public BigDecimal getTotalGiftTax() {
    return totalGiftTax;
  }

  public void setTotalGiftTax(BigDecimal totalGiftTax) {
    this.totalGiftTax = totalGiftTax;
  }

  public BigDecimal getTotalRefundTax() {
    return totalRefundTax;
  }

  public void setTotalRefundTax(BigDecimal totalRefundTax) {
    this.totalRefundTax = totalRefundTax;
  }

  public BigDecimal getTotalSalesPriceTax() {
    return totalSalesPriceTax;
  }

  public void setTotalSalesPriceTax(BigDecimal totalSalesPriceTax) {
    this.totalSalesPriceTax = totalSalesPriceTax;
  }

  /**
   * shopNameを返します。
   * 
   * @return the shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * commodityCodeを返します。
   * 
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * skuCodeを返します。
   * 
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * commoditySkuNameを返します。
   * 
   * @return the commoditySkuName
   */
  public String getCommoditySkuName() {
    return commoditySkuName;
  }

  /**
   * totalSalesPriceを返します。
   * 
   * @return the totalSalesPrice
   */
  public BigDecimal getTotalSalesPrice() {
    return totalSalesPrice;
  }

  /**
   * totalRefundを返します。
   * 
   * @return the totalRefund
   */
  public BigDecimal getTotalRefund() {
    return totalRefund;
  }

  /**
   * totalOrderQuantityを返します。
   * 
   * @return the totalOrderQuantity
   */
  public Long getTotalOrderQuantity() {
    return totalOrderQuantity;
  }

  /**
   * totalReturnItemQuantityを返します。
   * 
   * @return the totalReturnItemQuantity
   */
  public Long getTotalReturnItemQuantity() {
    return totalReturnItemQuantity;
  }

  /**
   * totalGiftPriceを返します。
   * 
   * @return the totalGiftPrice
   */
  public BigDecimal getTotalGiftPrice() {
    return totalGiftPrice;
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
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * skuCodeを設定します。
   * 
   * @param skuCode
   *          設定する skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * commoditySkuNameを設定します。
   * 
   * @param commoditySkuName
   *          設定する commoditySkuName
   */
  public void setCommoditySkuName(String commoditySkuName) {
    this.commoditySkuName = commoditySkuName;
  }

  /**
   * totalSalesPriceを設定します。
   * 
   * @param totalSalesPrice
   *          設定する totalSalesPrice
   */
  public void setTotalSalesPrice(BigDecimal totalSalesPrice) {
    this.totalSalesPrice = totalSalesPrice;
  }

  /**
   * totalRefundを設定します。
   * 
   * @param totalRefund
   *          設定する totalRefund
   */
  public void setTotalRefund(BigDecimal totalRefund) {
    this.totalRefund = totalRefund;
  }

  /**
   * totalOrderQuantityを設定します。
   * 
   * @param totalOrderQuantity
   *          設定する totalOrderQuantity
   */
  public void setTotalOrderQuantity(Long totalOrderQuantity) {
    this.totalOrderQuantity = totalOrderQuantity;
  }

  /**
   * totalReturnItemQuantityを設定します。
   * 
   * @param totalReturnItemQuantity
   *          設定する totalReturnItemQuantity
   */
  public void setTotalReturnItemQuantity(Long totalReturnItemQuantity) {
    this.totalReturnItemQuantity = totalReturnItemQuantity;
  }

  /**
   * totalGiftPriceを設定します。
   * 
   * @param totalGiftPrice
   *          設定する totalGiftPrice
   */
  public void setTotalGiftPrice(BigDecimal totalGiftPrice) {
    this.totalGiftPrice = totalGiftPrice;
  }

}
