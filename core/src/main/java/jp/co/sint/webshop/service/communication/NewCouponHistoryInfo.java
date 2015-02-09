package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.util.Date;

public class NewCouponHistoryInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 优惠券名称 */
  private String couponName;

  /** 优惠券名称(英文) */
  private String couponNameEn;

  /** クーポン名称(日文) */
  private String couponNameJp;

  /** 优惠金额 */
  private String couponAmount;

  /** クーポン使用最小购买金额 */
  private String minUseOrderAmount;

  /** 使用开始日时 */
  private Date useStartDatetime;

  /** 优惠券发行日时 */
  private Date couponIssueDatetime;

  /** 使用结束日时 */
  private Date useEndDatetime;

  /** クーポン状態 */
  private String couponStatus;

  /** 优惠券发行类别 */
  private String couponIssueType;

  /** 优惠比例 */
  private String couponProportion;

  /** 使用状态 */
  private String useStatus;

  // 20140905 hdh add
  // 优惠券编号
  private String couponCode;

  /**
   * クーポン名称を取得する
   * 
   * @return
   */
  public String getCouponName() {
    return couponName;
  }

  /**
   * クーポン名称を設定する
   * 
   * @param couponName
   */
  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  /**
   * 优惠金額を取得する
   * 
   * @return
   */
  public String getCouponAmount() {
    return couponAmount;
  }

  /**
   * 优惠金額を設定する
   * 
   * @param couponAmount
   */
  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * クーポン使用最小购买金额を取得する
   * 
   * @return
   */
  public String getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * クーポン使用最小购买金額を設定する
   * 
   * @param minUseOrderAmount
   */
  public void setMinUseOrderAmount(String minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * 使用开始日时を取得する
   * 
   * @return
   */
  public Date getUseStartDatetime() {
    return useStartDatetime;
  }

  /**
   * 使用开始日时を設定する
   * 
   * @param minUseStartDatetime
   */
  public void setUseStartDatetime(Date useStartDatetime) {
    this.useStartDatetime = useStartDatetime;
  }

  /**
   * 使用结束日时を取得する
   * 
   * @return
   */
  public Date getUseEndDatetime() {
    return useEndDatetime;
  }

  /**
   * 使用结束日时を設定する
   * 
   * @param minUseEndDatetime
   */
  public void setUseEndDatetime(Date useEndDatetime) {
    this.useEndDatetime = useEndDatetime;
  }

  /**
   * @return the couponIssueDatetime
   */
  public Date getCouponIssueDatetime() {
    return couponIssueDatetime;
  }

  /**
   * @param couponIssueDatetime
   *          the couponIssueDatetime to set
   */
  public void setCouponIssueDatetime(Date couponIssueDatetime) {
    this.couponIssueDatetime = couponIssueDatetime;
  }

  /**
   * 取得クーポン状態
   * 
   * @return
   */
  public String getCouponStatus() {
    return couponStatus;
  }

  /**
   * 设定クーポン状態
   * 
   * @param couponStatus
   */
  public void setCouponStatus(String couponStatus) {
    this.couponStatus = couponStatus;
  }

  /**
   * クーポン名称(英文)を取得する
   * 
   * @return
   */
  public String getCouponNameEn() {
    return couponNameEn;
  }

  /**
   * クーポン名称(英文)を設定する
   * 
   * @param couponNameEn
   */
  public void setCouponNameEn(String couponNameEn) {
    this.couponNameEn = couponNameEn;
  }

  /**
   * クーポン名称(日文)を取得する
   * 
   * @return
   */
  public String getCouponNameJp() {
    return couponNameJp;
  }

  /**
   * クーポン名称(日文)を設定する
   * 
   * @param couponNameJp
   */
  public void setCouponNameJp(String couponNameJp) {
    this.couponNameJp = couponNameJp;
  }

  /**
   * 优惠券发行类别
   * 
   * @return
   */
  public String getCouponIssueType() {
    return couponIssueType;
  }

  /**
   * 优惠券发行类别
   * 
   * @param couponIssueType
   */
  public void setCouponIssueType(String couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  /**
   * 优惠比例
   * 
   * @return
   */
  public String getCouponProportion() {
    return couponProportion;
  }

  /**
   * 优惠比例
   * 
   * @param couponProportion
   */
  public void setCouponProportion(String couponProportion) {
    this.couponProportion = couponProportion;
  }

  /**
   * 使用状态
   * 
   * @return
   */
  public String getUseStatus() {
    return useStatus;
  }

  /**
   * 使用状态
   * 
   * @param useStatus
   */
  public void setUseStatus(String useStatus) {
    this.useStatus = useStatus;
  }

  /**
   * @return the couponCode
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * @param couponCode
   *          the couponCode to set
   */
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

}
