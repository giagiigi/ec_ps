package jp.co.sint.webshop.web.bean.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020150:注文完了のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CompleteBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 1)
  private String orderNo;

  @Required
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "受注日時", order = 2)
  private String orderDatetime;

  @Required
  @Length(16)
  @AlphaNum2
  private String orderUserCode;

  private BigDecimal paymentTotalPrice;

  private OrderContainer orderContainer;

  private String enqueteCode;

  private BigDecimal enqueteInvestPoint;

  private boolean displayEnquete;

  private boolean displayContinue;

  private boolean enqueteInvestPointDisplayFlg;

  private String advertiseLinkUrl;

  private String advertiseImageUrl;

  private boolean hasCookieFlg;

  // Add by V10-CH start
  private String advertUrl;

  // Add by V10-CH end

  private boolean smsSend;

  /**
   * smsSendを取得します。
   * 
   * @return smsSend smsSend
   */
  public boolean isSmsSend() {
    return smsSend;
  }

  /**
   * smsSendを設定します。
   * 
   * @param smsSend
   *          smsSend
   */
  public void setSmsSend(boolean smsSend) {
    this.smsSend = smsSend;
  }

  /**
   * hasCookieFlgを取得します。
   * 
   * @return hasCookieFlg hasCookieFlg
   */
  public boolean isHasCookieFlg() {
    return hasCookieFlg;
  }

  /**
   * hasCookieFlgを設定します。
   * 
   * @param hasCookieFlg
   *          hasCookieFlg
   */
  public void setHasCookieFlg(boolean hasCookieFlg) {
    this.hasCookieFlg = hasCookieFlg;
  }

  /**
   * advertiseImageUrlを取得します。
   * 
   * @return advertiseImageUrl
   */
  public String getAdvertiseImageUrl() {
    return advertiseImageUrl;
  }

  /**
   * advertiseImageUrlを設定します。
   * 
   * @param advertiseImageUrl
   *          advertiseImageUrl
   */
  public void setAdvertiseImageUrl(String advertiseImageUrl) {
    this.advertiseImageUrl = advertiseImageUrl;
  }

  /**
   * advertiseLinkUrlを取得します。
   * 
   * @return advertiseLinkUrl
   */
  public String getAdvertiseLinkUrl() {
    return advertiseLinkUrl;
  }

  /**
   * advertiseLinkUrlを設定します。
   * 
   * @param advertiseLinkUrl
   *          advertiseLinkUrl
   */
  public void setAdvertiseLinkUrl(String advertiseLinkUrl) {
    this.advertiseLinkUrl = advertiseLinkUrl;
  }

  /**
   * displayContinueを取得します。
   * 
   * @return displayContinue
   */
  public boolean isDisplayContinue() {
    return displayContinue;
  }

  /**
   * displayContinueを設定します。
   * 
   * @param displayContinue
   *          displayContinue
   */
  public void setDisplayContinue(boolean displayContinue) {
    this.displayContinue = displayContinue;
  }

  /**
   * enqueteCodeを取得します。
   * 
   * @return enqueteCode
   */
  public String getEnqueteCode() {
    return enqueteCode;
  }

  /**
   * enqueteInvestPointを取得します。
   * 
   * @return enqueteInvestPoint
   */
  public BigDecimal getEnqueteInvestPoint() {
    return enqueteInvestPoint;
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
   * orderNoを取得します。
   * 
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * enqueteCodeを設定します。
   * 
   * @param enqueteCode
   *          enqueteCode
   */
  public void setEnqueteCode(String enqueteCode) {
    this.enqueteCode = enqueteCode;
  }

  /**
   * enqueteInvestPointを設定します。
   * 
   * @param enqueteInvestPoint
   *          enqueteInvestPoint
   */
  public void setEnqueteInvestPoint(BigDecimal enqueteInvestPoint) {
    this.enqueteInvestPoint = enqueteInvestPoint;
  }

  /**
   * orderDatetimeを設定します。
   * 
   * @param orderDatetime
   *          orderDatetime
   */
  public void setOrderDatetime(String orderDatetime) {
    this.orderDatetime = orderDatetime;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
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
    return "U2020150";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.order.CompleteBean.0");
  }

  /**
   * displayEnqueteを取得します。
   * 
   * @return displayEnquete
   */
  public boolean isDisplayEnquete() {
    return displayEnquete;
  }

  /**
   * displayEnqueteを設定します。
   * 
   * @param displayEnquete
   *          displayEnquete
   */
  public void setDisplayEnquete(boolean displayEnquete) {
    this.displayEnquete = displayEnquete;
  }

  /**
   * orderUserCodeを取得します。
   * 
   * @return orderUserCode
   */
  public String getOrderUserCode() {
    return orderUserCode;
  }

  /**
   * orderUserCodeを設定します。
   * 
   * @param orderUserCode
   *          orderUserCode
   */
  public void setOrderUserCode(String orderUserCode) {
    this.orderUserCode = orderUserCode;
  }

  public boolean isEnqueteInvestPointDisplayFlg() {
    return enqueteInvestPointDisplayFlg;
  }

  public void setEnqueteInvestPointDisplayFlg(boolean enqueteInvestPointDisplayFlg) {
    this.enqueteInvestPointDisplayFlg = enqueteInvestPointDisplayFlg;
  }

  /**
   * advertUrlを取得します。
   * 
   * @return advertUrl advertUrl
   */
  public String getAdvertUrl() {
    return advertUrl;
  }

  /**
   * advertUrlを設定します。
   * 
   * @param advertUrl
   *          advertUrl
   */
  public void setAdvertUrl(String advertUrl) {
    this.advertUrl = advertUrl;
  }

  public OrderContainer getOrderContainer() {
    return orderContainer;
  }

  public void setOrderContainer(OrderContainer orderContainer) {
    this.orderContainer = orderContainer;
  }

  public BigDecimal getPaymentTotalPrice() {
    return paymentTotalPrice;
  }

  public void setPaymentTotalPrice(BigDecimal paymentTotalPrice) {
    this.paymentTotalPrice = paymentTotalPrice;
  }

}
