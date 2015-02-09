package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class RfAnalysisSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 顧客数合計 */
  private Long customerCount;

  /** 顧客構成率 */
  private String customerCountRatio;

  /** 平均購入回数 */
  private String orderCountAvarage;

  /** 購入回数構成率 */
  private String orderCountRatio;

  /** 平均購入商品数 */
  private String purchasingAmountAvarage;

  /** 購入商品数構成率 */
  private String purchasingAmountRatio;

  /** "R"のランク */
  private String recencyRank;

  /** "F"のランク */
  private String frequencyRank;

  /**
   * customerCountを取得します。
   * 
   * @return customerCount
   */
  public Long getCustomerCount() {
    return customerCount;
  }

  /**
   * frequencyRankを取得します。
   * 
   * @return frequencyRank
   */
  public String getFrequencyRank() {
    return frequencyRank;
  }

  /**
   * recencyRankを取得します。
   * 
   * @return recencyRank
   */
  public String getRecencyRank() {
    return recencyRank;
  }

  /**
   * customerCountRatioを取得します。
   * 
   * @return customerCountRatio
   */
  public String getCustomerCountRatio() {
    return customerCountRatio;
  }

  /**
   * orderCountRatioを取得します。
   * 
   * @return orderCountRatio
   */
  public String getOrderCountRatio() {
    return orderCountRatio;
  }

  /**
   * purchasingAmountRatioを取得します。
   * 
   * @return purchasingAmountRatio
   */
  public String getPurchasingAmountRatio() {
    return purchasingAmountRatio;
  }

  /**
   * orderCountRatioを設定します。
   * 
   * @param orderCountRatio
   *          orderCountRatio
   */
  public void setOrderCountRatio(String orderCountRatio) {
    this.orderCountRatio = orderCountRatio;
  }

  /**
   * purchasingAmountRatioを設定します。
   * 
   * @param purchasingAmountRatio
   *          purchasingAmountRatio
   */
  public void setPurchasingAmountRatio(String purchasingAmountRatio) {
    this.purchasingAmountRatio = purchasingAmountRatio;
  }

  /**
   * customerCountRatioを設定します。
   * 
   * @param customerCountRatio
   *          customerCountRatio
   */
  public void setCustomerCountRatio(String customerCountRatio) {
    this.customerCountRatio = customerCountRatio;
  }

  /**
   * customerCountを設定します。
   * 
   * @param customerCount
   *          customerCount
   */
  public void setCustomerCount(Long customerCount) {
    this.customerCount = customerCount;
  }

  /**
   * frequencyRankを設定します。
   * 
   * @param frequencyRank
   *          frequencyRank
   */
  public void setFrequencyRank(String frequencyRank) {
    this.frequencyRank = frequencyRank;
  }

  /**
   * recencyRankを設定します。
   * 
   * @param recencyRank
   *          recencyRank
   */
  public void setRecencyRank(String recencyRank) {
    this.recencyRank = recencyRank;
  }

  /**
   * purchasingAmountAvarageを取得します。
   * 
   * @return purchasingAmountAvarage
   */
  public String getPurchasingAmountAvarage() {
    return purchasingAmountAvarage;
  }

  /**
   * purchasingAmountAvarageを設定します。
   * 
   * @param purchasingAmountAvarage
   *          purchasingAmountAvarage
   */
  public void setPurchasingAmountAvarage(String purchasingAmountAvarage) {
    this.purchasingAmountAvarage = purchasingAmountAvarage;
  }

  /**
   * orderCountAvarageを取得します。
   * 
   * @return orderCountAvarage
   */
  public String getOrderCountAvarage() {
    return orderCountAvarage;
  }

  /**
   * orderCountAvarageを設定します。
   * 
   * @param orderCountAvarage
   *          orderCountAvarage
   */
  public void setOrderCountAvarage(String orderCountAvarage) {
    this.orderCountAvarage = orderCountAvarage;
  }
}
