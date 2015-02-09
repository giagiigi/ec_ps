package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;

public class NewPublicCouponSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 集計日 */
  private String orderDate;
  
  /** 优惠代码 */
  private String discountCode;

  /** 注文件数累計 */
  private Long totalCall;
  
  /** 全部退货订单数 */
  private Long returnCall;

  /** 中文订单数 */
  private Long zhCall;
  
  /** 日文订单数 */
  private Long jpCall;
  
  /** 英文订单数 */
  private Long enCall;
  
  /** 折扣金额 */
  private BigDecimal discountPrice;
  
  /** 商品除折扣金额 */
  private BigDecimal paidPrice;
  
  /** 运费 */
  private BigDecimal shippingCharge;

  
  /**
   * @return the orderDate
   */
  public String getOrderDate() {
    return orderDate;
  }

  
  /**
   * @param orderDate the orderDate to set
   */
  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  
  /**
   * @return the discountCode
   */
  public String getDiscountCode() {
    return discountCode;
  }

  
  /**
   * @param discountCode the discountCode to set
   */
  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  
  /**
   * @return the totalCall
   */
  public Long getTotalCall() {
    return totalCall;
  }

  
  /**
   * @param totalCall the totalCall to set
   */
  public void setTotalCall(Long totalCall) {
    this.totalCall = totalCall;
  }

  
  /**
   * @return the zhCall
   */
  public Long getZhCall() {
    return zhCall;
  }

  
  /**
   * @param zhCall the zhCall to set
   */
  public void setZhCall(Long zhCall) {
    this.zhCall = zhCall;
  }

  
  /**
   * @return the jpCall
   */
  public Long getJpCall() {
    return jpCall;
  }

  
  /**
   * @param jpCall the jpCall to set
   */
  public void setJpCall(Long jpCall) {
    this.jpCall = jpCall;
  }

  
  /**
   * @return the enCall
   */
  public Long getEnCall() {
    return enCall;
  }

  
  /**
   * @param enCall the enCall to set
   */
  public void setEnCall(Long enCall) {
    this.enCall = enCall;
  }

  
  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  
  /**
   * @param discountPrice the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  
  /**
   * @return the paidPrice
   */
  public BigDecimal getPaidPrice() {
    return paidPrice;
  }

  
  /**
   * @param paidPrice the paidPrice to set
   */
  public void setPaidPrice(BigDecimal paidPrice) {
    this.paidPrice = paidPrice;
  }

  
  /**
   * @return the shippingCharge
   */
  public BigDecimal getShippingCharge() {
    return shippingCharge;
  }

  
  /**
   * @param shippingCharge the shippingCharge to set
   */
  public void setShippingCharge(BigDecimal shippingCharge) {
    this.shippingCharge = shippingCharge;
  }


  
  /**
   * @return the returnCall
   */
  public Long getReturnCall() {
    return returnCall;
  }


  
  /**
   * @param returnCall the returnCall to set
   */
  public void setReturnCall(Long returnCall) {
    this.returnCall = returnCall;
  }

}
