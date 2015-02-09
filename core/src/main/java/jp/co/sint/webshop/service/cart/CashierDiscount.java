package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Kousen.
 */
public class CashierDiscount implements Serializable {

  private static final long serialVersionUID = 1L;

  // 折扣规则编号
  private String discountCode;

  // 折扣编号
  private String discountDetailCode;

  // 折扣规则名称
  private String discountName;

  // 折扣类型
  private String discountType;

  // 折扣方式
  private String discountMode;

  // 折扣比率
  private Long discountRate;

  // 折扣金额
  private BigDecimal discountPrice = BigDecimal.ZERO;

  // 优惠券类型
  private String couponType;
  
  // 2013/04/09 优惠券对应 ob add start
  private boolean isMyCoupon = false;
  // 2013/04/09 优惠券对应 ob add end

  public String getDiscountCode() {
    return discountCode;
  }

  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  public String getDiscountName() {
    return discountName;
  }

  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  public String getDiscountType() {
    return discountType;
  }

  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

  public String getDiscountMode() {
    return discountMode;
  }

  public void setDiscountMode(String discountMode) {
    this.discountMode = discountMode;
  }

  public Long getDiscountRate() {
    return discountRate;
  }

  public void setDiscountRate(Long discountRate) {
    this.discountRate = discountRate;
  }

  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  public String getDiscountDetailCode() {
    return discountDetailCode;
  }

  public void setDiscountDetailCode(String discountDetailCode) {
    this.discountDetailCode = discountDetailCode;
  }

  public String getCouponType() {
    return couponType;
  }

  public void setCouponType(String couponType) {
    this.couponType = couponType;
  }

  /**
   * @return the isMyCoupon
   */
  public boolean isMyCoupon() {
    return isMyCoupon;
  }

  /**
   * @param isMyCoupon the isMyCoupon to set
   */
  public void setMyCoupon(boolean isMyCoupon) {
    this.isMyCoupon = isMyCoupon;
  }

}
