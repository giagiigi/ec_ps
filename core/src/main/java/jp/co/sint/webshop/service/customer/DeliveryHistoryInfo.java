package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.util.List;

/**
 * 配送履歴の検索結果
 */
public class DeliveryHistoryInfo implements Serializable {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  private String shippingDate;

  private String shippingNo;

  private String orderNo;

  private List<String> commodityName;

  private String totalPrice;

  private String orderDatetime;

  private Long returnItemType;

  private String paymentCommission; // 10.1.3 10126 追加

  /**
   * returnItemTypeを返します。
   * 
   * @return the returnItemType
   */
  public Long getReturnItemType() {
    return returnItemType;
  }

  /**
   * returnItemTypeを設定します。
   * 
   * @param returnItemType
   *          設定する returnItemType
   */
  public void setReturnItemType(Long returnItemType) {
    this.returnItemType = returnItemType;
  }

  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public List<String> getCommodityName() {
    return commodityName;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          設定する commodityName
   */
  public void setCommodityName(List<String> commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * orderDatetimeを取得します。
   * 
   * @return orderDatetime
   */
  public String getOrderDatetime() {
    return orderDatetime;
  }

  /**
   * orderDatetimeを設定します。
   * 
   * @param orderDatetime
   *          設定する orderDatetime
   */
  public void setOrderDatetime(String orderDatetime) {
    this.orderDatetime = orderDatetime;
  }

  /**
   * shippingDateを取得します。
   * 
   * @return shippingDate
   */
  public String getShippingDate() {
    return shippingDate;
  }

  /**
   * shippingDateを設定します。
   * 
   * @param shippingDate
   *          設定する shippingDate
   */
  public void setShippingDate(String shippingDate) {
    this.shippingDate = shippingDate;
  }

  /**
   * shippingNoを取得します。
   * 
   * @return shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }

  /**
   * shippingNoを設定します。
   * 
   * @param shippingNo
   *          設定する shippingNo
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }

  /**
   * totalPriceを取得します。
   * 
   * @return totalPrice
   */
  public String getTotalPrice() {
    return totalPrice;
  }

  /**
   * totalPriceを設定します。
   * 
   * @param totalPrice
   *          設定する totalPrice
   */
  public void setTotalPrice(String totalPrice) {
    this.totalPrice = totalPrice;
  }

  /**
   * orderNoを取得します。
   * 
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          設定する orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  // 10.1.3 10126 追加 ここから
  /**
   * paymentCommissionを取得します。
   *
   * @return paymentCommission paymentCommission
   */
  public String getPaymentCommission() {
    return paymentCommission;
  }

  /**
   * paymentCommissionを設定します。
   *
   * @param paymentCommission 
   *          paymentCommission
   */
  public void setPaymentCommission(String paymentCommission) {
    this.paymentCommission = paymentCommission;
  }
  // 10.1.3 10126 追加 ここまで
}
