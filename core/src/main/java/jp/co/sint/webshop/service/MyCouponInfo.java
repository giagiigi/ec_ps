package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠券发行履历を表現する共通インターフェイスです。
 * 
 * @author OB
 */
public class MyCouponInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /** 优惠券编号 */
  private String couponIssueNo;

  /** 优惠券规则编号 */
  private String couponCode;

  /** 优惠券名称 */
  private String couponName;

  /** 优惠券名称（英文） */
  private String couponNameEn;

  /** 优惠券名称（日文） */
  private String couponNameJp;

  /** 优惠券发行类别（0:比例；1:固定金额） */
  private Long couponIssueType;

  /** 优惠券比例 */
  private Long couponProportion;

  /** 优惠金额 */
  private BigDecimal couponAmount;

  /** 优惠券利用最小购买金额 */
  private BigDecimal minUseOrderAmount;

  /** 优惠券最大金额优惠值（NULL：99999999） */
  private BigDecimal maxUseOrderAmount;

  /** 优惠券发行方式适用类别（0:无限制；1:仅限初次购买使用） */
  private Long applicableObjects;

  /** 优惠券发行金额类别（0:折扣前金额 1:折扣后金额） */
  private Long beforeAfterDiscountType;

  /** 有无商品区分 */
  private Long relatedCommodityFlg;
  
  /** 优惠券类别（0:购买发行；1:特别会员发行；2:公共发行） */
  private Long couponType;
  
  private Long useType;
  
  private BigDecimal objectTotalPrice = BigDecimal.ZERO;

  /**
   * @return the couponIssueNo
   */
  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  /**
   * @param couponIssueNo the couponIssueNo to set
   */
  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
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

  /**
   * @return the couponName
   */
  public String getCouponName() {
    return couponName;
  }

  /**
   * @param couponName
   *          the couponName to set
   */
  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  /**
   * @return the couponNameEn
   */
  public String getCouponNameEn() {
    return couponNameEn;
  }

  /**
   * @param couponNameEn
   *          the couponNameEn to set
   */
  public void setCouponNameEn(String couponNameEn) {
    this.couponNameEn = couponNameEn;
  }

  /**
   * @return the couponNameJp
   */
  public String getCouponNameJp() {
    return couponNameJp;
  }

  /**
   * @param couponNameJp
   *          the couponNameJp to set
   */
  public void setCouponNameJp(String couponNameJp) {
    this.couponNameJp = couponNameJp;
  }

  /**
   * @return the couponIssueType
   */
  public Long getCouponIssueType() {
    return couponIssueType;
  }

  /**
   * @param couponIssueType
   *          the couponIssueType to set
   */
  public void setCouponIssueType(Long couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  /**
   * @return the couponProportion
   */
  public Long getCouponProportion() {
    return couponProportion;
  }

  /**
   * @param couponProportion
   *          the couponProportion to set
   */
  public void setCouponProportion(Long couponProportion) {
    this.couponProportion = couponProportion;
  }

  /**
   * @return the couponAmount
   */
  public BigDecimal getCouponAmount() {
    return couponAmount;
  }

  /**
   * @param couponAmount
   *          the couponAmount to set
   */
  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * @return the minUseOrderAmount
   */
  public BigDecimal getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * @param minUseOrderAmount
   *          the minUseOrderAmount to set
   */
  public void setMinUseOrderAmount(BigDecimal minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * @return the maxUseOrderAmount
   */
  public BigDecimal getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  /**
   * @param maxUseOrderAmount
   *          the maxUseOrderAmount to set
   */
  public void setMaxUseOrderAmount(BigDecimal maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  /**
   * @return the applicableObjects
   */
  public Long getApplicableObjects() {
    return applicableObjects;
  }

  /**
   * @param applicableObjects
   *          the applicableObjects to set
   */
  public void setApplicableObjects(Long applicableObjects) {
    this.applicableObjects = applicableObjects;
  }

  /**
   * @return the beforeAfterDiscountType
   */
  public Long getBeforeAfterDiscountType() {
    return beforeAfterDiscountType;
  }

  /**
   * @param beforeAfterDiscountType
   *          the beforeAfterDiscountType to set
   */
  public void setBeforeAfterDiscountType(Long beforeAfterDiscountType) {
    this.beforeAfterDiscountType = beforeAfterDiscountType;
  }

  /**
   * @return the couponType
   */
  public Long getCouponType() {
    return couponType;
  }

  /**
   * @param couponType the couponType to set
   */
  public void setCouponType(Long couponType) {
    this.couponType = couponType;
  }

  /**
   * @return the relatedCommodityFlg
   */
  public Long getRelatedCommodityFlg() {
    return relatedCommodityFlg;
  }

  /**
   * @param relatedCommodityFlg the relatedCommodityFlg to set
   */
  public void setRelatedCommodityFlg(Long relatedCommodityFlg) {
    this.relatedCommodityFlg = relatedCommodityFlg;
  }

  /**
   * @return the objectTotalPrice
   */
  public BigDecimal getObjectTotalPrice() {
    return objectTotalPrice;
  }

  /**
   * @param objectTotalPrice the objectTotalPrice to set
   */
  public void setObjectTotalPrice(BigDecimal objectTotalPrice) {
    this.objectTotalPrice = objectTotalPrice;
  }

  
  /**
   * @return the useType
   */
  public Long getUseType() {
    return useType;
  }

  
  /**
   * @param useType the useType to set
   */
  public void setUseType(Long useType) {
    this.useType = useType;
  }

}
