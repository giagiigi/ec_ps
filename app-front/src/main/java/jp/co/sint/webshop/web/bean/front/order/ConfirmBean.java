package jp.co.sint.webshop.web.bean.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020140:注文内容確認のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ConfirmBean extends OrderBean {

  private static final long serialVersionUID = 1L;

  private OrderContainer order;

  private BigDecimal totalAcquiredPoint;

  private BigDecimal displayGrandTotalPrice;

  // add by V10-CH start
  private BigDecimal displayPoint;

  private BigDecimal paymentPrice;

  // add V10-CH zhanghaibin end

  private boolean isNotPointInFull;

  // 20111226 shen add start
  private boolean needInvoice;

  private boolean useDiscount;

  // 20111226 shen add end

  // 20120221 shen add start
  private boolean displayDeliveryDate;

  // 20120221 shen add end

  /**
   * isNotPointInFullを返します。
   * 
   * @return the isNotPointInFull
   */
  public boolean isNotPointInFull() {
    return isNotPointInFull;
  }

  /**
   * isNotPointInFullを設定します。
   * 
   * @param notPointInFull
   *          設定する isNotPointInFull
   */
  public void setNotPointInFull(boolean notPointInFull) {
    this.isNotPointInFull = notPointInFull;
  }

  /**
   * totalAcquiredPointを取得します。
   * 
   * @return totalAcquiredPoint
   */
  public BigDecimal getTotalAcquiredPoint() {
    return totalAcquiredPoint;
  }

  /**
   * totalAcquiredPointを設定します。
   * 
   * @param totalAcquiredPoint
   *          totalAcquiredPoint
   */
  public void setTotalAcquiredPoint(BigDecimal totalAcquiredPoint) {
    this.totalAcquiredPoint = totalAcquiredPoint;
  }

  /**
   * orderを取得します。
   * 
   * @return order
   */
  public OrderContainer getOrder() {
    return order;
  }

  /**
   * orderを設定します。
   * 
   * @param order
   *          order
   */
  public void setOrder(OrderContainer order) {
    this.order = order;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
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
    return "U2020140";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.order.ConfirmBean.0");
  }

  /**
   * displayGrandTotalPriceを返します。
   * 
   * @return the displayGrandTotalPrice
   */
  public BigDecimal getDisplayGrandTotalPrice() {
    return displayGrandTotalPrice;
  }

  /**
   * displayGrandTotalPriceを設定します。
   * 
   * @param displayGrandTotalPrice
   *          設定する displayGrandTotalPrice
   */
  public void setDisplayGrandTotalPrice(BigDecimal displayGrandTotalPrice) {
    this.displayGrandTotalPrice = displayGrandTotalPrice;
  }

  public BigDecimal getDisplayPoint() {
    return displayPoint;
  }

  public void setDisplayPoint(BigDecimal displayPoint) {
    this.displayPoint = displayPoint;
  }

  public BigDecimal getPaymentPrice() {
    return paymentPrice;
  }

  public void setPaymentPrice(BigDecimal paymentPrice) {
    this.paymentPrice = paymentPrice;
  }

  public boolean isNeedInvoice() {
    return needInvoice;
  }

  public void setNeedInvoice(boolean needInvoice) {
    this.needInvoice = needInvoice;
  }

  public boolean isUseDiscount() {
    return useDiscount;
  }

  public void setUseDiscount(boolean useDiscount) {
    this.useDiscount = useDiscount;
  }

  /**
   * @return the displayDeliveryDate
   */
  public boolean isDisplayDeliveryDate() {
    return displayDeliveryDate;
  }

  /**
   * @param displayDeliveryDate
   *          the displayDeliveryDate to set
   */
  public void setDisplayDeliveryDate(boolean displayDeliveryDate) {
    this.displayDeliveryDate = displayDeliveryDate;
  }

}
