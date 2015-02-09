package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.SearchCondition;

public class CustomerPreferenceSearchCondition extends SearchCondition {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCodeCondition;

  /** 検索開始年 */
  @Required
  private int searchYearStart;

  /** 検索終了年 */
  @Required
  private int searchYearEnd;

  /** 検索開始月 */
  @Required
  private int searchMonthStart;

  /** 検索終了月 */
  @Required
  private int searchMonthEnd;

  /** 顧客グループコード */
  private String customerGroupCondition;

  /** 検索開始年齢 */
  private Long searchAgeStart;

  /** 検索終了年齢 */
  private Long searchAgeEnd;

  /** 性別 */
  private String sexcondition;

  /** 並べ替えタイプ */
  @Required
  private RearrangeType rearrangeTypeCondition;

  private List<CustomerAttributeSearchCondition> customerAttributeCondition = new ArrayList<CustomerAttributeSearchCondition>();

  public static class CustomerAttributeSearchCondition implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 顧客属性番号 */
    private String customerAttributeNo;

    /** 顧客属性回答 */
    private List<String> attributeAnswerItem = new ArrayList<String>();

    /**
     * attributeAnswerItemを取得します。
     * 
     * @return attributeAnswerItem
     */
    public List<String> getAttributeAnswerItem() {
      return attributeAnswerItem;
    }

    /**
     * customerAttributeNoを取得します。
     * 
     * @return customerAttributeNo
     */
    public String getCustomerAttributeNo() {
      return customerAttributeNo;
    }

    /**
     * attributeAnswerItemを設定します。
     * 
     * @param attributeAnswerItem
     *          attributeAnswerItem
     */
    public void setAttributeAnswerItem(List<String> attributeAnswerItem) {
      this.attributeAnswerItem = attributeAnswerItem;
    }

    /**
     * customerAttributeNoを設定します。
     * 
     * @param customerAttributeNo
     *          customerAttributeNo
     */
    public void setCustomerAttributeNo(String customerAttributeNo) {
      this.customerAttributeNo = customerAttributeNo;
    }
  }

  /**
   * customerAttributeConditionを取得します。
   * 
   * @return customerAttributeCondition
   */
  public List<CustomerAttributeSearchCondition> getCustomerAttributeCondition() {
    return customerAttributeCondition;
  }

  /**
   * customerGroupConditionを取得します。
   * 
   * @return customerGroupCondition
   */
  public String getCustomerGroupCondition() {
    return customerGroupCondition;
  }

  /**
   * searchAgeEndを取得します。
   * 
   * @return searchAgeEnd
   */
  public Long getSearchAgeEnd() {
    return searchAgeEnd;
  }

  /**
   * searchAgeStartを取得します。
   * 
   * @return searchAgeStart
   */
  public Long getSearchAgeStart() {
    return searchAgeStart;
  }

  /**
   * searchMonthEndを取得します。
   * 
   * @return searchMonthEnd
   */
  public int getSearchMonthEnd() {
    return searchMonthEnd;
  }

  /**
   * searchMonthStartを取得します。
   * 
   * @return searchMonthStart
   */
  public int getSearchMonthStart() {
    return searchMonthStart;
  }

  /**
   * searchYearEndを取得します。
   * 
   * @return searchYearEnd
   */
  public int getSearchYearEnd() {
    return searchYearEnd;
  }

  /**
   * searchYearStartを取得します。
   * 
   * @return searchYearStart
   */
  public int getSearchYearStart() {
    return searchYearStart;
  }

  /**
   * sexconditionを取得します。
   * 
   * @return sexcondition
   */
  public String getSexcondition() {
    return sexcondition;
  }

  /**
   * shopCodeConditionを取得します。
   * 
   * @return shopCodeCondition
   */
  public String getShopCodeCondition() {
    return shopCodeCondition;
  }

  /**
   * customerAttributeConditionを設定します。
   * 
   * @param customerAttributeCondition
   *          customerAttributeCondition
   */
  public void setCustomerAttributeCondition(List<CustomerAttributeSearchCondition> customerAttributeCondition) {
    this.customerAttributeCondition = customerAttributeCondition;
  }

  /**
   * customerGroupConditionを設定します。
   * 
   * @param customerGroupCondition
   *          customerGroupCondition
   */
  public void setCustomerGroupCondition(String customerGroupCondition) {
    this.customerGroupCondition = customerGroupCondition;
  }

  /**
   * searchAgeEndを設定します。
   * 
   * @param searchAgeEnd
   *          searchAgeEnd
   */
  public void setSearchAgeEnd(Long searchAgeEnd) {
    this.searchAgeEnd = searchAgeEnd;
  }

  /**
   * searchAgeStartを設定します。
   * 
   * @param searchAgeStart
   *          searchAgeStart
   */
  public void setSearchAgeStart(Long searchAgeStart) {
    this.searchAgeStart = searchAgeStart;
  }

  /**
   * searchMonthEndを設定します。
   * 
   * @param searchMonthEnd
   *          searchMonthEnd
   */
  public void setSearchMonthEnd(int searchMonthEnd) {
    this.searchMonthEnd = searchMonthEnd;
  }

  /**
   * searchMonthStartを設定します。
   * 
   * @param searchMonthStart
   *          searchMonthStart
   */
  public void setSearchMonthStart(int searchMonthStart) {
    this.searchMonthStart = searchMonthStart;
  }

  /**
   * searchYearEndを設定します。
   * 
   * @param searchYearEnd
   *          searchYearEnd
   */
  public void setSearchYearEnd(int searchYearEnd) {
    this.searchYearEnd = searchYearEnd;
  }

  /**
   * searchYearStartを設定します。
   * 
   * @param searchYearStart
   *          searchYearStart
   */
  public void setSearchYearStart(int searchYearStart) {
    this.searchYearStart = searchYearStart;
  }

  /**
   * sexconditionを設定します。
   * 
   * @param sexcondition
   *          sexcondition
   */
  public void setSexcondition(String sexcondition) {
    this.sexcondition = sexcondition;
  }

  /**
   * shopCodeConditionを設定します。
   * 
   * @param shopCodeCondition
   *          shopCodeCondition
   */
  public void setShopCodeCondition(String shopCodeCondition) {
    this.shopCodeCondition = shopCodeCondition;
  }

  /**
   * rearrangeTypeConditionを取得します。
   * 
   * @return rearrangeTypeCondition
   */
  public RearrangeType getRearrangeTypeCondition() {
    return rearrangeTypeCondition;
  }

  /**
   * rearrangeTypeConditionを設定します。
   * 
   * @param rearrangeTypeCondition
   *          rearrangeTypeCondition
   */
  public void setRearrangeTypeCondition(RearrangeType rearrangeTypeCondition) {
    this.rearrangeTypeCondition = rearrangeTypeCondition;
  }

}
