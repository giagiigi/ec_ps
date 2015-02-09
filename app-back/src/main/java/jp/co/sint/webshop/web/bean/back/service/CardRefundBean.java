package jp.co.sint.webshop.web.bean.back.service;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class CardRefundBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 订单编号 */
  private String orderNo;

  /** 订单总金额 */
  private String orderPrice;

  /** 礼品片使用金额 */
  private String cardUsedPrice;
  
  private String returnFlg;

  @Required
  @Length(11)
  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "退款金额")
  private String refundPrice;


  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @return the orderPrice
   */
  public String getOrderPrice() {
    return orderPrice;
  }

  /**
   * @return the cardUsedPrice
   */
  public String getCardUsedPrice() {
    return cardUsedPrice;
  }

  /**
   * @return the refundPrice
   */
  public String getRefundPrice() {
    return refundPrice;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @param orderPrice
   *          the orderPrice to set
   */
  public void setOrderPrice(String orderPrice) {
    this.orderPrice = orderPrice;
  }

  /**
   * @param cardUsedPrice
   *          the cardUsedPrice to set
   */
  public void setCardUsedPrice(String cardUsedPrice) {
    this.cardUsedPrice = cardUsedPrice;
  }

  /**
   * @param refundPrice
   *          the refundPrice to set
   */
  public void setRefundPrice(String refundPrice) {
    this.refundPrice = refundPrice;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1090110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.service.CardRefundBean.0");
  }

  
  /**
   * @return the returnFlg
   */
  public String getReturnFlg() {
    return returnFlg;
  }

  
  /**
   * @param returnFlg the returnFlg to set
   */
  public void setReturnFlg(String returnFlg) {
    this.returnFlg = returnFlg;
  }

  
}
