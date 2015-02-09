package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;


public class CustomerStatisticsSummary implements Serializable {
  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private String id;

  private String statisticsGroup;

  private String statisticsItem;

  private String customerAmount;

  /**
   * idを返します。
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * statisticsGroupを返します。
   * 
   * @return the statisticsGroup
   */
  public String getStatisticsGroup() {
    return statisticsGroup;
  }

  /**
   * statisticsItemを返します。
   * 
   * @return the statisticsItem
   */
  public String getStatisticsItem() {
    return statisticsItem;
  }

  /**
   * customerAmountを返します。
   * 
   * @return the customerAmount
   */
  public String getCustomerAmount() {
    return customerAmount;
  }

  /**
   * idを設定します。
   * 
   * @param id
   *          設定する id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * statisticsGroupを設定します。
   * 
   * @param statisticsGroup
   *          設定する statisticsGroup
   */
  public void setStatisticsGroup(String statisticsGroup) {
    this.statisticsGroup = statisticsGroup;
  }

  /**
   * statisticsItemを設定します。
   * 
   * @param statisticsItem
   *          設定する statisticsItem
   */
  public void setStatisticsItem(String statisticsItem) {
    this.statisticsItem = statisticsItem;
  }

  /**
   * customerAmountを設定します。
   * 
   * @param customerAmount
   *          設定する customerAmount
   */
  public void setCustomerAmount(String customerAmount) {
    this.customerAmount = customerAmount;
  }
}
