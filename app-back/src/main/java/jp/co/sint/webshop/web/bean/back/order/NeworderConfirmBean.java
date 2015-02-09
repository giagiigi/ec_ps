package jp.co.sint.webshop.web.bean.back.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020160:新規受注(確認)のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderConfirmBean extends NeworderBaseBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private BigDecimal displayGrandTotalPrice;

  // modified by zhanghaibin start 2010-05-21
  private BigDecimal displayPoint;

  // modified by zhanghaibin end 2010-05-21
  private boolean isNotPointInFull;

  private BigDecimal paymentPrice;

  // soukai add 2012/01/07 ob start
  
  // 优惠金额
  private BigDecimal discountPrice;
  
  //发票信息
  private InvoiceBean orderInvoice;
  
  private String deliveryCompanyName;
  
  // soukai add 2012/01/07 ob end
  public BigDecimal getPaymentPrice() {
    return paymentPrice;
  }

  public void setPaymentPrice(BigDecimal paymentPrice) {
    this.paymentPrice = paymentPrice;
  }

  /**
   * displayGrandTotalPriceを取得します。
   * 
   * @return displayGrandTotalPrice
   */
  public BigDecimal getDisplayGrandTotalPrice() {
    return displayGrandTotalPrice;
  }

  /**
   * isNotPointInFullを取得します。
   * 
   * @return isNotPointInFull
   */
  public boolean isNotPointInFull() {
    return isNotPointInFull;
  }

  /**
   * displayGrandTotalPriceを設定します。
   * 
   * @param displayGrandTotalPrice
   *          displayGrandTotalPrice
   */
  public void setDisplayGrandTotalPrice(BigDecimal displayGrandTotalPrice) {
    this.displayGrandTotalPrice = displayGrandTotalPrice;
  }

  /**
   * isNotPointInFullを設定します。
   * 
   * @param notPointInFull
   *          isNotPointInFull
   */
  public void setNotPointInFull(boolean notPointInFull) {
    this.isNotPointInFull = notPointInFull;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020160";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderConfirmBean.0");
  }

  public BigDecimal getDisplayPoint() {
    return displayPoint;
  }

  public void setDisplayPoint(BigDecimal displayPoint) {
    this.displayPoint = displayPoint;
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
 * @return the orderInvoice
 */
public InvoiceBean getOrderInvoice() {
	return orderInvoice;
}

/**
 * @param orderInvoice the orderInvoice to set
 */
public void setOrderInvoice(InvoiceBean orderInvoice) {
	this.orderInvoice = orderInvoice;
}

/**
 * @return the deliveryCompanyName
 */
public String getDeliveryCompanyName() {
	return deliveryCompanyName;
}

/**
 * @param deliveryCompanyName the deliveryCompanyName to set
 */
public void setDeliveryCompanyName(String deliveryCompanyName) {
	this.deliveryCompanyName = deliveryCompanyName;
}

}
