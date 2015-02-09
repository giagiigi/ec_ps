package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;

public class FmAnalysisSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 顧客数合計 */
  private Long customerCount;

  /** 顧客構成率 */
  private String customerCountRatio;

  /** 平均購入商品数 */
  private String purchasingAmountAvarage;

  /** 購入商品数構成率 */
  private String purchasingAmountRatio;

  /** 平均購入回数 */
  private String orderCountAvarage;

  /** 購入回数構成率 */
  private String orderCountRatio;

  /** 累計購入金額 */
  private BigDecimal totalPurchasedAmount;

  /** 累計購入金額構成率 */
  private String totalPurchasedAmountRatio;

  /** "F"のランク */
  private String frequencyRank;

  /** "M"のランク */
  private String monetaryRank;

  /**
   * customerCountを返します。
   * 
   * @return the customerCount
   */
  public Long getCustomerCount() {
    return customerCount;
  }

  /**
   * customerCountRatioを返します。
   * 
   * @return the customerCountRatio
   */
  public String getCustomerCountRatio() {
    return customerCountRatio;
  }

  /**
   * purchasingAmountAvarageを返します。
   * 
   * @return the purchasingAmountAvarage
   */
  public String getPurchasingAmountAvarage() {
    return purchasingAmountAvarage;
  }

  /**
   * purchasingAmountRatioを返します。
   * 
   * @return the purchasingAmountRatio
   */
  public String getPurchasingAmountRatio() {
    return purchasingAmountRatio;
  }

  /**
   * orderCountAvarageを返します。
   * 
   * @return the orderCountAvarage
   */
  public String getOrderCountAvarage() {
    return orderCountAvarage;
  }

  /**
   * orderCountRatioを返します。
   * 
   * @return the orderCountRatio
   */
  public String getOrderCountRatio() {
    return orderCountRatio;
  }

  /**
   * totalPurchasedAmountを返します。
   * 
   * @return the totalPurchasedAmount
   */
  public BigDecimal getTotalPurchasedAmount() {
    return totalPurchasedAmount;
  }

  /**
   * totalPurchasedAmountRatioを返します。
   * 
   * @return the totalPurchasedAmountRatio
   */
  public String getTotalPurchasedAmountRatio() {
    return totalPurchasedAmountRatio;
  }

  /**
   * frequencyRankを返します。
   * 
   * @return the frequencyRank
   */
  public String getFrequencyRank() {
    return frequencyRank;
  }

  /**
   * monetaryRankを返します。
   * 
   * @return the monetaryRank
   */
  public String getMonetaryRank() {
    return monetaryRank;
  }

  /**
   * customerCountを設定します。
   * 
   * @param customerCount
   *          設定する customerCount
   */
  public void setCustomerCount(Long customerCount) {
    this.customerCount = customerCount;
  }

  /**
   * customerCountRatioを設定します。
   * 
   * @param customerCountRatio
   *          設定する customerCountRatio
   */
  public void setCustomerCountRatio(String customerCountRatio) {
    this.customerCountRatio = customerCountRatio;
  }

  /**
   * purchasingAmountAvarageを設定します。
   * 
   * @param purchasingAmountAvarage
   *          設定する purchasingAmountAvarage
   */
  public void setPurchasingAmountAvarage(String purchasingAmountAvarage) {
    this.purchasingAmountAvarage = purchasingAmountAvarage;
  }

  /**
   * purchasingAmountRatioを設定します。
   * 
   * @param purchasingAmountRatio
   *          設定する purchasingAmountRatio
   */
  public void setPurchasingAmountRatio(String purchasingAmountRatio) {
    this.purchasingAmountRatio = purchasingAmountRatio;
  }

  /**
   * orderCountAvarageを設定します。
   * 
   * @param orderCountAvarage
   *          設定する orderCountAvarage
   */
  public void setOrderCountAvarage(String orderCountAvarage) {
    this.orderCountAvarage = orderCountAvarage;
  }

  /**
   * orderCountRatioを設定します。
   * 
   * @param orderCountRatio
   *          設定する orderCountRatio
   */
  public void setOrderCountRatio(String orderCountRatio) {
    this.orderCountRatio = orderCountRatio;
  }

  /**
   * totalPurchasedAmountを設定します。
   * 
   * @param totalPurchasedAmount
   *          設定する totalPurchasedAmount
   */
  public void setTotalPurchasedAmount(BigDecimal totalPurchasedAmount) {
    this.totalPurchasedAmount = totalPurchasedAmount;
  }

  /**
   * totalPurchasedAmountRatioを設定します。
   * 
   * @param totalPurchasedAmountRatio
   *          設定する totalPurchasedAmountRatio
   */
  public void setTotalPurchasedAmountRatio(String totalPurchasedAmountRatio) {
    this.totalPurchasedAmountRatio = totalPurchasedAmountRatio;
  }

  /**
   * frequencyRankを設定します。
   * 
   * @param frequencyRank
   *          設定する frequencyRank
   */
  public void setFrequencyRank(String frequencyRank) {
    this.frequencyRank = frequencyRank;
  }

  /**
   * monetaryRankを設定します。
   * 
   * @param monetaryRank
   *          設定する monetaryRank
   */
  public void setMonetaryRank(String monetaryRank) {
    this.monetaryRank = monetaryRank;
  }

}
