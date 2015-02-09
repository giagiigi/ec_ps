package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;

public class RmAnalysisSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 顧客数合計 */
  private Long customerCount;

  /** 顧客構成率 */
  private String customerCountRatio;

  /** 累計購入金額 */
  private BigDecimal totalPurchasedAmount;

  /** 購入金額構成率 */
  private String totalPurchasedAmountRatio;

  /** "R"のランク */
  private String recencyRank;

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
   * recencyRankを返します。
   * 
   * @return the recencyRank
   */
  public String getRecencyRank() {
    return recencyRank;
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
   * recencyRankを設定します。
   * 
   * @param recencyRank
   *          設定する recencyRank
   */
  public void setRecencyRank(String recencyRank) {
    this.recencyRank = recencyRank;
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
