package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.math.BigDecimal;

public class Coupon implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // SKUコード
  private String skuCode;
  
  //折扣券的折扣类型
  private Long couponType;
  
  // 使用折扣券后的优惠金额
  private BigDecimal couponPrice = BigDecimal.ZERO;
  
  //折扣券编号
  private String couponCode;
  
  //折扣券活动名称
  private String couponName;
  
  /**
   * 新しいSkuを生成します。
   */
  public Coupon() {
    this.skuCode = "";
    this.couponType = null;
    this.couponPrice = BigDecimal.ZERO;
    this.couponCode = "";
    this.setCouponName("");
  }

  /**
   * 引数で受け取った折扣券的折扣类型、优惠金额,折扣券编号を設定します。
   * 
   * @param couponType
   *          折扣类型
   * @param couponPrice
   *          优惠金额
   * @param couponCode
   *          折扣券编号
   */
  public Coupon(String skuCode, String couponCode, Long couponType, BigDecimal couponPrice, String couponName) {
    this.skuCode = skuCode;
    this.couponCode = couponCode;
    this.couponType = couponType;
    this.couponPrice = couponPrice;
    this.couponName = couponName;
  }

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * @param skuCode the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
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
   * @return the couponPrice
   */
  public BigDecimal getCouponPrice() {
    return couponPrice;
  }

  /**
   * @param couponPrice the couponPrice to set
   */
  public void setCouponPrice(BigDecimal couponPrice) {
    this.couponPrice = couponPrice;
  }

  /**
   * @return the couponCode
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * @param couponCode the couponCode to set
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
   * @param couponName the couponName to set
   */
  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Coupon)) {
      return false;
    }
    return this.equals((Coupon) object);
  }

  public boolean equals(Coupon coupon) {
    if (coupon == null || StringUtil.isNullOrEmpty(coupon.getSkuCode())) {
      return false;
    }
    if (!coupon.getSkuCode().equals(this.getSkuCode())) {
      return false;
    }
    return true;
  }
}
