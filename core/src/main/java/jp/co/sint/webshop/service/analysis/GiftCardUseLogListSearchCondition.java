package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;

public class GiftCardUseLogListSearchCondition extends SearchCondition {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "顾客コード(From)", order = 1)
  private String searchCustomerCodeStart;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "顾客コード(To)", order = 2)
  private String searchCustomerCodeEnd;

  @Length(11)
  @AlphaNum2
  private String searchTelephoneNum;

  @Length(40)
  private String searchCustomerName;

  private String searchCardId;

  private String searchOrderNo;

  @Length(256)
  private String searchEmail;

  /** 礼品卡充值开始日时(From) */
  @Metadata(name = "礼品卡充值开始日时(From)", order = 3)
  private String searchShippingStartDatetimeFrom;

  /** 礼品卡充值开始日时(To) */
  @Metadata(name = "礼品卡充值开始日时(To)", order = 4)
  private String searchShippingStartDatetimeTo;

  /**
   * @return the searchCustomerCodeStart
   */
  public String getSearchCustomerCodeStart() {
    return searchCustomerCodeStart;
  }

  /**
   * @param searchCustomerCodeStart
   *          the searchCustomerCodeStart to set
   */
  public void setSearchCustomerCodeStart(String searchCustomerCodeStart) {
    this.searchCustomerCodeStart = searchCustomerCodeStart;
  }

  /**
   * @return the searchCustomerCodeEnd
   */
  public String getSearchCustomerCodeEnd() {
    return searchCustomerCodeEnd;
  }

  /**
   * @param searchCustomerCodeEnd
   *          the searchCustomerCodeEnd to set
   */
  public void setSearchCustomerCodeEnd(String searchCustomerCodeEnd) {
    this.searchCustomerCodeEnd = searchCustomerCodeEnd;
  }

  /**
   * @return the searchTelephoneNum
   */
  public String getSearchTelephoneNum() {
    return searchTelephoneNum;
  }

  /**
   * @param searchTelephoneNum
   *          the searchTelephoneNum to set
   */
  public void setSearchTelephoneNum(String searchTelephoneNum) {
    this.searchTelephoneNum = searchTelephoneNum;
  }

  /**
   * @return the searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * @param searchCustomerName
   *          the searchCustomerName to set
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * @return the searchCardId
   */
  public String getSearchCardId() {
    return searchCardId;
  }

  /**
   * @param searchCardId
   *          the searchCardId to set
   */
  public void setSearchCardId(String searchCardId) {
    this.searchCardId = searchCardId;
  }

  /**
   * @return the searchOrderNo
   */
  public String getSearchOrderNo() {
    return searchOrderNo;
  }

  /**
   * @param searchOrderNo
   *          the searchOrderNo to set
   */
  public void setSearchOrderNo(String searchOrderNo) {
    this.searchOrderNo = searchOrderNo;
  }

  /**
   * @return the searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  /**
   * @param searchEmail
   *          the searchEmail to set
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  /**
   * @return the searchShippingStartDatetimeFrom
   */
  public String getSearchShippingStartDatetimeFrom() {
    return searchShippingStartDatetimeFrom;
  }

  /**
   * @param searchShippingStartDatetimeFrom
   *          the searchShippingStartDatetimeFrom to set
   */
  public void setSearchShippingStartDatetimeFrom(String searchShippingStartDatetimeFrom) {
    this.searchShippingStartDatetimeFrom = searchShippingStartDatetimeFrom;
  }

  /**
   * @return the searchShippingStartDatetimeTo
   */
  public String getSearchShippingStartDatetimeTo() {
    return searchShippingStartDatetimeTo;
  }

  /**
   * @param searchShippingStartDatetimeTo
   *          the searchShippingStartDatetimeTo to set
   */
  public void setSearchShippingStartDatetimeTo(String searchShippingStartDatetimeTo) {
    this.searchShippingStartDatetimeTo = searchShippingStartDatetimeTo;
  }

}
