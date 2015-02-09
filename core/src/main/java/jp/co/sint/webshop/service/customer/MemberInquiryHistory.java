package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

public class MemberInquiryHistory implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 咨询编号 */
  private String inquiryHeaderNo;

  /** 受理日期 */
  private String acceptDate;

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
  private String acceptUpdate;

  /** 咨询状态 */
  private String inquiryStatus;

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
  public String getAcceptDate() {
    return acceptDate;
  }

  /**
   * @param acceptDate
   *          the acceptDate to set
   */
  public void setAcceptDate(String acceptDate) {
    this.acceptDate = acceptDate;
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
   * @return the acceptUpdate
   */
  public String getAcceptUpdate() {
    return acceptUpdate;
  }

  /**
   * @param acceptUpdate
   *          the acceptUpdate to set
   */
  public void setAcceptUpdate(String acceptUpdate) {
    this.acceptUpdate = acceptUpdate;
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

}
