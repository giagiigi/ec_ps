package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

/**
 * ポイント利用状況の検索結果
 */
public class PointStatusAllSearchInfo implements Serializable {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String shopName;

  private String customerCode;

  private String lastName;

  private String firstName;

  private String issuedPoint;

  private String pointIssueStatus;

  private String pointIssueType;

  private String pointUsedDatetime;

  private String description;

  private String orderNo;

  private String orderDatetime;

  private String paymentDate;

  private String reviewId;

  private String enqueteCode;

  private String pointIssueDatetime;

  /**
   * pointIssueDatetimeを取得します。
   * 
   * @return pointIssueDatetime
   */
  public String getPointIssueDatetime() {
    return pointIssueDatetime;
  }

  /**
   * pointIssueDatetimeを設定します。
   * 
   * @param pointIssueDatetime
   *          pointIssueDatetime
   */
  public void setPointIssueDatetime(String pointIssueDatetime) {
    this.pointIssueDatetime = pointIssueDatetime;
  }

  /**
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          設定する customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return enqueteCode
   */
  public String getEnqueteCode() {
    return enqueteCode;
  }

  /**
   * @param enqueteCode
   *          設定する enqueteCode
   */
  public void setEnqueteCode(String enqueteCode) {
    this.enqueteCode = enqueteCode;
  }

  /**
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *          設定する firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return issuedPoint
   */
  public String getIssuedPoint() {
    return issuedPoint;
  }

  /**
   * @param issuedPoint
   *          設定する issuedPoint
   */
  public void setIssuedPoint(String issuedPoint) {
    this.issuedPoint = issuedPoint;
  }

  /**
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *          設定する lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return orderDatetime
   */
  public String getOrderDatetime() {
    return orderDatetime;
  }

  /**
   * @param orderDatetime
   *          設定する orderDatetime
   */
  public void setOrderDatetime(String orderDatetime) {
    this.orderDatetime = orderDatetime;
  }

  /**
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @param orderNo
   *          設定する orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return paymentDate
   */
  public String getPaymentDate() {
    return paymentDate;
  }

  /**
   * @param paymentDate
   *          設定する paymentDate
   */
  public void setPaymentDate(String paymentDate) {
    this.paymentDate = paymentDate;
  }

  /**
   * @return pointIssueStatus
   */
  public String getPointIssueStatus() {
    return pointIssueStatus;
  }

  /**
   * @param pointIssueStatus
   *          設定する pointIssueStatus
   */
  public void setPointIssueStatus(String pointIssueStatus) {
    this.pointIssueStatus = pointIssueStatus;
  }

  /**
   * @return reviewId
   */
  public String getReviewId() {
    return reviewId;
  }

  /**
   * @param reviewId
   *          設定する reviewId
   */
  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * @param shopName
   *          設定する shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          設定する description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return pointUsedDatetime
   */
  public String getPointUsedDatetime() {
    return pointUsedDatetime;
  }

  /**
   * @param pointUsedDatetime
   *          設定する pointUsedDatetime
   */
  public void setPointUsedDatetime(String pointUsedDatetime) {
    this.pointUsedDatetime = pointUsedDatetime;
  }

  /**
   * pointIssueTypeを取得します。
   * 
   * @return pointIssueType
   */
  public String getPointIssueType() {
    return pointIssueType;
  }

  /**
   * pointIssueTypeを設定します。
   * 
   * @param pointIssueType
   *          設定する pointIssueType
   */
  public void setPointIssueType(String pointIssueType) {
    this.pointIssueType = pointIssueType;
  }

}
