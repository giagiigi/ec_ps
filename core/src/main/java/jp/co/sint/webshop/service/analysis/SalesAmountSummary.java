package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalesAmountSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCode;

  /** ショップ名 */
  private String shopName;

  /** 売上金額累計 */
  private BigDecimal totalSalesPrice;

  /** 売上金額消費税累計 */
  private BigDecimal totalSalesPriceTax;

  /** 返金額累計 */
  private BigDecimal totalRefund;

  /** 返金額消費税累計 */
  private BigDecimal totalRefundTax;

  /** 返品損金額累計 */
  private BigDecimal totalReturnItemLossMoney;

  /** 注文件数累計 */
  private Long totalOrderCount;

  /** 返品件数累計 */
  private Long totalReturnItemCount;

  /** 値引額累計 */
  private BigDecimal totalDiscountAmount;

  /** 使用ポイント累計 */
  private Long totalUsedPoint;

  /** 発行ポイント累計 */
  private Long totalIssuedPoint;

  /** 送料累計 */
  private BigDecimal totalShippingCharge;

  /** 送料消費税累計 */
  private BigDecimal totalShippingChargeTax;

  /** ギフト金額累計 */
  private BigDecimal totalGiftPrice;

  /** ギフト消費税額累計 */
  private BigDecimal totalGiftTax;

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
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
   * totalReturnItemLossMoneyを返します。
   * 
   * @return the totalReturnItemLossMoney
   */
  public BigDecimal getTotalReturnItemLossMoney() {
    return totalReturnItemLossMoney;
  }

  /**
   * totalOrderCountを返します。
   * 
   * @return the totalOrderCount
   */
  public Long getTotalOrderCount() {
    return totalOrderCount;
  }

  /**
   * totalReturnItemCountを返します。
   * 
   * @return the totalReturnItemCount
   */
  public Long getTotalReturnItemCount() {
    return totalReturnItemCount;
  }

  /**
   * totalDiscountAmountを返します。
   * 
   * @return the totalDiscountAmount
   */
  public BigDecimal getTotalDiscountAmount() {
    return totalDiscountAmount;
  }

  /**
   * totalUsedPointを返します。
   * 
   * @return the totalUsedPoint
   */
  public Long getTotalUsedPoint() {
    return totalUsedPoint;
  }

  /**
   * totalIssuedPointを返します。
   * 
   * @return the totalIssuedPoint
   */
  public Long getTotalIssuedPoint() {
    return totalIssuedPoint;
  }

  /**
   * totalShippingChargeを返します。
   * 
   * @return the totalShippingCharge
   */
  public BigDecimal getTotalShippingCharge() {
    return totalShippingCharge;
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
   * totalReturnItemLossMoneyを設定します。
   * 
   * @param totalReturnItemLossMoney
   *          設定する totalReturnItemLossMoney
   */
  public void setTotalReturnItemLossMoney(BigDecimal totalReturnItemLossMoney) {
    this.totalReturnItemLossMoney = totalReturnItemLossMoney;
  }

  /**
   * totalOrderCountを設定します。
   * 
   * @param totalOrderCount
   *          設定する totalOrderCount
   */
  public void setTotalOrderCount(Long totalOrderCount) {
    this.totalOrderCount = totalOrderCount;
  }

  /**
   * totalReturnItemCountを設定します。
   * 
   * @param totalReturnItemCount
   *          設定する totalReturnItemCount
   */
  public void setTotalReturnItemCount(Long totalReturnItemCount) {
    this.totalReturnItemCount = totalReturnItemCount;
  }

  /**
   * totalDiscountAmountを設定します。
   * 
   * @param totalDiscountAmount
   *          設定する totalDiscountAmount
   */
  public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
    this.totalDiscountAmount = totalDiscountAmount;
  }

  /**
   * totalUsedPointを設定します。
   * 
   * @param totalUsedPoint
   *          設定する totalUsedPoint
   */
  public void setTotalUsedPoint(Long totalUsedPoint) {
    this.totalUsedPoint = totalUsedPoint;
  }

  /**
   * totalIssuedPointを設定します。
   * 
   * @param totalIssuedPoint
   *          設定する totalIssuedPoint
   */
  public void setTotalIssuedPoint(Long totalIssuedPoint) {
    this.totalIssuedPoint = totalIssuedPoint;
  }

  /**
   * totalShippingChargeを設定します。
   * 
   * @param totalShippingCharge
   *          設定する totalShippingCharge
   */
  public void setTotalShippingCharge(BigDecimal totalShippingCharge) {
    this.totalShippingCharge = totalShippingCharge;
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

  public BigDecimal getTotalShippingChargeTax() {
    return totalShippingChargeTax;
  }

  public void setTotalShippingChargeTax(BigDecimal totalShippingChargeTax) {
    this.totalShippingChargeTax = totalShippingChargeTax;
  }
}
