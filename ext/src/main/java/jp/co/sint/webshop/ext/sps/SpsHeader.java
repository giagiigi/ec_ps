package jp.co.sint.webshop.ext.sps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author System Integrator Corp.
 */
public abstract class SpsHeader<D extends SpsDetail> implements Serializable {

  private static final long serialVersionUID = 1L;


  private List<D> details = new ArrayList<D>();

  //--------------------------update 2011-01-25-----------------------------------
  //add start date 2011-01-26 by yyq decs：支付宝接口路径
  private String purchaseRequestUrl = "";
  
  /** サービスID */
  //add start date 2011-01-26 by yyq decs：支付宝接口类型即时到账
  private String serviceId = "create_direct_pay_by_user";
  
  //add start date 2011-01-26 by yyq decs：商品描述
  private String body = "资生堂";

  //add start date 2011-01-26 by yyq decs：加密方式
  private String sign_type = "MD5";
  
  //add start date 2011-01-26 by yyq decs：编码格式
  private String _input_charset = "utf-8";
  
  /** 金額（税込） */
  //add start date 2011-01-26 by yyq decs：商品总金额
  private String amount = "";
  
  /** SPS払出パスワード */
  //add start date 2011-01-26 by yyq decs：支付密码key
  private String password = "";
  
  //add start date 2011-01-26 by yyq decs：商户支付宝邮箱账户
  private String seller_email = "w_lu@senshukai.co.jp";
  
  /** 商品名称 */
  //add start date 2011-01-26 by yyq decs：商品名称
  private String itemName = "化妆品";
  
  /** マーチャントID */
//add start date 2011-01-26 by yyq decs：商户合作伙伴ID
  private String merchantId = "";
  
  //add start date 2011-01-26 by yyq decs：网站地址 设置域名以后对应
  private String show_url = "http://www.bellemaison.cn";
  
  //add start date 2011-01-26 by yyq decs：支付类型 1为购买
  private String payment_type = "1";
  
  
  /** 決済通知用CGI */
  //add start date 2011-01-26 by yyq decs：付完款后服务器通知的页面
  private String pageconUrl = "";
  
  /** 決済完了時URL */
  //add start date 2011-01-26 by yyq decs：付完款后立即返回到页面
  private String successUrl = "";
  
  //add start date 2011-01-26 by yyq decs：支付宝支付标志 非必须
  private boolean aliPayFlag = false;
  
  /** 購入ID */
  //add start date 2011-01-26 by yyq decs：外部交易号-系统订单编号
  private String orderId = "";
  
  //add start date 2011-01-26 by yyq decs：根据参数生成的唯一路径 加密后
  private String itemUrl = "";
  //--------------------------update 2011-01-25-----------------------------------
  

  /** 支払方法 */
  private String payMethod = "";

  
  /** 顧客ID */
  private String custCode = "";

  /** SPS顧客ID */
  private String spsCustNo = "";

  /** SPS支払方法管理番号 */
  private String spsPaymentNo = "";

  /** 商品ID */
  private String itemId = "";

  /** 外部決済機関商品ID */
  private String payItemId = "";


  /** 税額 */
  private String tax = "";


  /** 購入タイプ */
  private String payType = "";

  /** 自動課金タイプ */
  private String autoChargeType = "";

  /** サービスタイプ */
  private String serviceType = "";

  /** 決済区分 */
  private String divSettele = "";

  /** 最終課金月 */
  private String lastChargeMonth = "";

  /** キャンペーンタイプ */
  private String campType = "";

  /** トラッキングID */
  private String trackingId = "";

  /** 顧客利用端末タイプ */
  private String terminalType = "";

  /** 決済キャンセル時URL */
  private String cancelUrl = "";

  /** エラー時URL */
  private String errorUrl = "";

 

  /** 自由欄１ */
  private String free1 = "";

  /** 自由欄２ */
  private String free2 = "";

  /** 自由欄３ */
  private String free3 = "";

  /** 自由CSV */
  private String freeCsv = "";

  /** リクエスト日時 */
  private String requestDate = "";

  /** リクエスト許容時間 */
  private String limitSecond = "";

  /** SPSハッシュコード */
  private String spsHashCode = "";

  /**
   * purchaseRequestUrlを取得します。
   * 
   * @return purchaseRequestUrl purchaseRequestUrl
   */
  public String getPurchaseRequestUrl() {
    return purchaseRequestUrl;
  }

  /**
   * purchaseRequestUrlを設定します。
   * 
   * @param purchaseRequestUrl
   *          purchaseRequestUrl
   */
  public void setPurchaseRequestUrl(String purchaseRequestUrl) {
    this.purchaseRequestUrl = purchaseRequestUrl;
  }

  /**
   * detailsを返します。
   * 
   * @return details
   */
  public List<D> getDetails() {
    return details;
  }

  /**
   * detailsを設定します。
   * 
   * @param details
   *          details
   */
  public void setDetails(List<D> details) {
    this.details = details;
  }

  /**
   * passwordを返します。
   * 
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * passwordを設定します。
   * 
   * @param password
   *          password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * payMethodを返します。
   * 
   * @return payMethod
   */
  public String getPayMethod() {
    return payMethod;
  }

  /**
   * payMethodを設定します。
   * 
   * @param payMethod
   *          payMethod
   */
  public void setPayMethod(String payMethod) {
    this.payMethod = payMethod;
  }

  /**
   * merchantIdを返します。
   * 
   * @return merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * merchantIdを設定します。
   * 
   * @param merchantId
   *          merchantId
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * serviceIdを返します。
   * 
   * @return serviceId
   */
  public String getServiceId() {
    return serviceId;
  }

  /**
   * serviceIdを設定します。
   * 
   * @param serviceId
   *          serviceId
   */
  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  /**
   * custCodeを返します。
   * 
   * @return custCode
   */
  public String getCustCode() {
    return custCode;
  }

  /**
   * custCodeを設定します。
   * 
   * @param custCode
   *          custCode
   */
  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }

  /**
   * spsCustNoを返します。
   * 
   * @return spsCustNo
   */
  public String getSpsCustNo() {
    return spsCustNo;
  }

  /**
   * spsCustNoを設定します。
   * 
   * @param spsCustNo
   *          spsCustNo
   */
  public void setSpsCustNo(String spsCustNo) {
    this.spsCustNo = spsCustNo;
  }

  /**
   * spsPaymentNoを返します。
   * 
   * @return spsPaymentNo
   */
  public String getSpsPaymentNo() {
    return spsPaymentNo;
  }

  /**
   * spsPaymentNoを設定します。
   * 
   * @param spsPaymentNo
   *          spsPaymentNo
   */
  public void setSpsPaymentNo(String spsPaymentNo) {
    this.spsPaymentNo = spsPaymentNo;
  }

  /**
   * orderIdを返します。
   * 
   * @return orderId
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * orderIdを設定します。
   * 
   * @param orderId
   *          orderId
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * itemIdを返します。
   * 
   * @return itemId
   */
  public String getItemId() {
    return itemId;
  }

  /**
   * itemIdを設定します。
   * 
   * @param itemId
   *          itemId
   */
  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  /**
   * payItemIdを返します。
   * 
   * @return payItemId
   */
  public String getPayItemId() {
    return payItemId;
  }

  /**
   * payItemIdを設定します。
   * 
   * @param payItemId
   *          payItemId
   */
  public void setPayItemId(String payItemId) {
    this.payItemId = payItemId;
  }

  /**
   * itemNameを返します。
   * 
   * @return itemName
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * itemNameを設定します。
   * 
   * @param itemName
   *          itemName
   */
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  /**
   * taxを返します。
   * 
   * @return tax
   */
  public String getTax() {
    return tax;
  }

  /**
   * taxを設定します。
   * 
   * @param tax
   *          tax
   */
  public void setTax(String tax) {
    this.tax = tax;
  }

  /**
   * amountを返します。
   * 
   * @return amount
   */
  public String getAmount() {
    return amount;
  }

  /**
   * amountを設定します。
   * 
   * @param amount
   *          amount
   */
  public void setAmount(String amount) {
    this.amount = amount;
  }

  /**
   * payTypeを返します。
   * 
   * @return payType
   */
  public String getPayType() {
    return payType;
  }

  /**
   * payTypeを設定します。
   * 
   * @param payType
   *          payType
   */
  public void setPayType(String payType) {
    this.payType = payType;
  }

  /**
   * autoChargeTypeを返します。
   * 
   * @return autoChargeType
   */
  public String getAutoChargeType() {
    return autoChargeType;
  }

  /**
   * autoChargeTypeを設定します。
   * 
   * @param autoChargeType
   *          autoChargeType
   */
  public void setAutoChargeType(String autoChargeType) {
    this.autoChargeType = autoChargeType;
  }

  /**
   * serviceTypeを返します。
   * 
   * @return serviceType
   */
  public String getServiceType() {
    return serviceType;
  }

  /**
   * serviceTypeを設定します。
   * 
   * @param serviceType
   *          serviceType
   */
  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  /**
   * divSetteleを返します。
   * 
   * @return divSettele
   */
  public String getDivSettele() {
    return divSettele;
  }

  /**
   * divSetteleを設定します。
   * 
   * @param divSettele
   *          divSettele
   */
  public void setDivSettele(String divSettele) {
    this.divSettele = divSettele;
  }

  /**
   * lastChargeMonthを返します。
   * 
   * @return lastChargeMonth
   */
  public String getLastChargeMonth() {
    return lastChargeMonth;
  }

  /**
   * lastChargeMonthを設定します。
   * 
   * @param lastChargeMonth
   *          lastChargeMonth
   */
  public void setLastChargeMonth(String lastChargeMonth) {
    this.lastChargeMonth = lastChargeMonth;
  }

  /**
   * campTypeを返します。
   * 
   * @return campType
   */
  public String getCampType() {
    return campType;
  }

  /**
   * campTypeを設定します。
   * 
   * @param campType
   *          campType
   */
  public void setCampType(String campType) {
    this.campType = campType;
  }

  /**
   * trackingIdを返します。
   * 
   * @return trackingId
   */
  public String getTrackingId() {
    return trackingId;
  }

  /**
   * trackingIdを設定します。
   * 
   * @param trackingId
   *          trackingId
   */
  public void setTrackingId(String trackingId) {
    this.trackingId = trackingId;
  }

  /**
   * terminalTypeを返します。
   * 
   * @return terminalType
   */
  public String getTerminalType() {
    return terminalType;
  }

  /**
   * terminalTypeを設定します。
   * 
   * @param terminalType
   *          terminalType
   */
  public void setTerminalType(String terminalType) {
    this.terminalType = terminalType;
  }

  /**
   * successUrlを返します。
   * 
   * @return successUrl
   */
  public String getSuccessUrl() {
    return successUrl;
  }

  /**
   * successUrlを設定します。
   * 
   * @param successUrl
   *          successUrl
   */
  public void setSuccessUrl(String successUrl) {
    this.successUrl = successUrl;
  }

  /**
   * cancelUrlを返します。
   * 
   * @return cancelUrl
   */
  public String getCancelUrl() {
    return cancelUrl;
  }

  /**
   * cancelUrlを設定します。
   * 
   * @param cancelUrl
   *          cancelUrl
   */
  public void setCancelUrl(String cancelUrl) {
    this.cancelUrl = cancelUrl;
  }

  /**
   * errorUrlを返します。
   * 
   * @return errorUrl
   */
  public String getErrorUrl() {
    return errorUrl;
  }

  /**
   * errorUrlを設定します。
   * 
   * @param errorUrl
   *          errorUrl
   */
  public void setErrorUrl(String errorUrl) {
    this.errorUrl = errorUrl;
  }

  /**
   * pageconUrlを返します。
   * 
   * @return pageconUrl
   */
  public String getPageconUrl() {
    return pageconUrl;
  }

  /**
   * pageconUrlを設定します。
   * 
   * @param pageconUrl
   *          pageconUrl
   */
  public void setPageconUrl(String pageconUrl) {
    this.pageconUrl = pageconUrl;
  }

  /**
   * free1を返します。
   * 
   * @return free1
   */
  public String getFree1() {
    return free1;
  }

  /**
   * free1を設定します。
   * 
   * @param free1
   *          free1
   */
  public void setFree1(String free1) {
    this.free1 = free1;
  }

  /**
   * free2を返します。
   * 
   * @return free2
   */
  public String getFree2() {
    return free2;
  }

  /**
   * free2を設定します。
   * 
   * @param free2
   *          free2
   */
  public void setFree2(String free2) {
    this.free2 = free2;
  }

  /**
   * free3を返します。
   * 
   * @return free3
   */
  public String getFree3() {
    return free3;
  }

  /**
   * free3を設定します。
   * 
   * @param free3
   *          free3
   */
  public void setFree3(String free3) {
    this.free3 = free3;
  }

  /**
   * freeCsvを取得します。
   * 
   * @return freeCsv freeCsv
   */
  public String getFreeCsv() {
    return freeCsv;
  }

  /**
   * freeCsvを設定します。
   * 
   * @param freeCsv
   *          freeCsv
   */
  public void setFreeCsv(String freeCsv) {
    this.freeCsv = freeCsv;
  }

  /**
   * requestDateを返します。
   * 
   * @return requestDate
   */
  public String getRequestDate() {
    return requestDate;
  }

  /**
   * requestDateを設定します。
   * 
   * @param requestDate
   *          requestDate
   */
  public void setRequestDate(String requestDate) {
    this.requestDate = requestDate;
  }

  /**
   * limitSecondを返します。
   * 
   * @return limitSecond
   */
  public String getLimitSecond() {
    return limitSecond;
  }

  /**
   * limitSecondを設定します。
   * 
   * @param limitSecond
   *          limitSecond
   */
  public void setLimitSecond(String limitSecond) {
    this.limitSecond = limitSecond;
  }

  /**
   * spsHashCodeを取得します。
   * 
   * @return spsHashCode spsHashCode
   */
  public String getSpsHashCode() {
    return spsHashCode;
  }

  /**
   * spsHashCodeを設定します。
   * 
   * @param spsHashCode
   *          spsHashCode
   */
  public void setSpsHashCode(String spsHashCode) {
    this.spsHashCode = spsHashCode;
  }

  
  public String get_input_charset() {
    return _input_charset;
  }

  
  public void set_input_charset(String _input_charset) {
    this._input_charset = _input_charset;
  }

  
  public boolean isAliPayFlag() {
    return aliPayFlag;
  }

  
  public void setAliPayFlag(boolean aliPayFlag) {
    this.aliPayFlag = aliPayFlag;
  }

  
  public String getBody() {
    return body;
  }

  
  public void setBody(String body) {
    this.body = body;
  }

  
  
  public String getItemUrl() {
    return itemUrl;
  }

  
  public void setItemUrl(String itemUrl) {
    this.itemUrl = itemUrl;
  }

  

  
  public String getPayment_type() {
    return payment_type;
  }

  
  public void setPayment_type(String payment_type) {
    this.payment_type = payment_type;
  }

  
  public String getSeller_email() {
    return seller_email;
  }

  
  public void setSeller_email(String seller_email) {
    this.seller_email = seller_email;
  }

  
  public String getShow_url() {
    return show_url;
  }

  
  public void setShow_url(String show_url) {
    this.show_url = show_url;
  }

  
  public String getSign_type() {
    return sign_type;
  }

  
  public void setSign_type(String sign_type) {
    this.sign_type = sign_type;
  }

}
