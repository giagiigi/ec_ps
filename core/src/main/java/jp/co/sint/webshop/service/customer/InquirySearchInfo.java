package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

public class InquirySearchInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 咨询编号 */
  private String inquiryHeaderNo;

  /** 受理日期 */
  private String acceptDatetime;

  /** 会员名 */
  private String customerName;

  /** 会员编号 */
  private String customerCode;

  /** 手机号码 */
  private String mobileNumber;

  /** 大分类 */
  private String largeCategory;

  /** 关联小分类 */
  private String smallCategory;

  /** 咨询途径 */
  private String inquiryWay;

  /** 咨询主题 */
  private String inquirySubject;

  /** 最终担当者 */
  private String personInChargeName;

  /** 担当者编号 */
  private String personInChargeNo;

  /** 更新时间 */
  private String acceptUpdatetime;

  /** 咨询状态 */
  private String inquiryStatus;

  /** 咨询状态统计数 */
  private String inquiryStatusCount;

  /** IB/OB区分 */
  private String ibObType;

  /** 商品编号 */
  private String commodityCode;

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
   * @return the ibObType
   */
  public String getIbObType() {
    return ibObType;
  }

  /**
   * @param ibObType
   *          the ibObType to set
   */
  public void setIbObType(String ibObType) {
    this.ibObType = ibObType;
  }

  /**
   * @return the inquiryHeaderNo
   */
  public String getInquiryHeaderNo() {
    return inquiryHeaderNo;
  }

  /**
   * @param inquiryHeaderNo
   *          the inquiryHeaderNo to set
   */
  public void setInquiryHeaderNo(String inquiryHeaderNo) {
    this.inquiryHeaderNo = inquiryHeaderNo;
  }

  /**
   * @return the acceptDate
   */
  public String getAcceptDatetime() {
    return acceptDatetime;
  }

  /**
   * @param acceptDate
   *          the acceptDate to set
   */
  public void setAcceptDatetime(String acceptDatetime) {
    this.acceptDatetime = acceptDatetime;
  }

  /**
   * @return the largeCategory
   */
  public String getLargeCategory() {
    return largeCategory;
  }

  /**
   * @param largeCategory
   *          the largeCategory to set
   */
  public void setLargeCategory(String largeCategory) {
    this.largeCategory = largeCategory;
  }

  /**
   * @return the smallCategory
   */
  public String getSmallCategory() {
    return smallCategory;
  }

  /**
   * @param smallCategory
   *          the smallCategory to set
   */
  public void setSmallCategory(String smallCategory) {
    this.smallCategory = smallCategory;
  }

  /**
   * @return the inquiryWay
   */
  public String getInquiryWay() {
    return inquiryWay;
  }

  /**
   * @param inquiryWay
   *          the inquiryWay to set
   */
  public void setInquiryWay(String inquiryWay) {
    this.inquiryWay = inquiryWay;
  }

  /**
   * @return the inquirySubject
   */
  public String getInquirySubject() {
    return inquirySubject;
  }

  /**
   * @param inquirySubject
   *          the inquirySubject to set
   */
  public void setInquirySubject(String inquirySubject) {
    this.inquirySubject = inquirySubject;
  }

  /**
   * @return the personInChargeName
   */
  public String getPersonInChargeName() {
    return personInChargeName;
  }

  /**
   * @param personInChargeName
   *          the personInChargeName to set
   */
  public void setPersonInChargeName(String personInChargeName) {
    this.personInChargeName = personInChargeName;
  }

  /**
   * @return the personInChargeNo
   */
  public String getPersonInChargeNo() {
    return personInChargeNo;
  }

  /**
   * @param personInChargeNo
   *          the personInChargeNo to set
   */
  public void setPersonInChargeNo(String personInChargeNo) {
    this.personInChargeNo = personInChargeNo;
  }

  /**
   * @return the acceptUpdatetime
   */
  public String getAcceptUpdatetime() {
    return acceptUpdatetime;
  }

  /**
   * @param acceptUpdatetime
   *          the acceptUpdatetime to set
   */
  public void setAcceptUpdatetime(String acceptUpdatetime) {
    this.acceptUpdatetime = acceptUpdatetime;
  }

  /**
   * @return the inquiryStatus
   */
  public String getInquiryStatus() {
    return inquiryStatus;
  }

  /**
   * @param inquiryStatus
   *          the inquiryStatus to set
   */
  public void setInquiryStatus(String inquiryStatus) {
    this.inquiryStatus = inquiryStatus;
  }

  /**
   * @return the customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * @param customerName
   *          the customerName to set
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return the mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * @param mobileNumber
   *          the mobileNumber to set
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * @return the inquiryStatusCount
   */
  public String getInquiryStatusCount() {
    return inquiryStatusCount;
  }

  /**
   * @param inquiryStatusCount
   *          the inquiryStatusCount to set
   */
  public void setInquiryStatusCount(String inquiryStatusCount) {
    this.inquiryStatusCount = inquiryStatusCount;
  }

}
