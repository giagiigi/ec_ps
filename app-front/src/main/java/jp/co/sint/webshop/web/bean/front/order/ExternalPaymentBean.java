package jp.co.sint.webshop.web.bean.front.order;

import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * @author System Integrator Corp.
 */
public class ExternalPaymentBean extends UIFrontBean {

	  private static final long serialVersionUID = 1L;

	  private String orderNo;

	  private Object paymentFormObject;

	  private OrderContainer orderContainer;

	  private String paymentMethodName;

	  private String paymentTotalPrice;
	  
	// 银联支付信息表示Flg
	  private boolean displayChinapayInfo = false;

	  // 支付宝支付信息表示Flg
	  private boolean displayAlipayInfo = false; 
 
	  private boolean firstTimePay = false; 
	  /** 模式 */
	  private String pattern = "external_payment";

  /**
   * orderNoを取得します。
   * 
   * @return orderNo orderNo
   */
  public String getOrderNo() {
    return orderNo;
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
   * orderContainerを取得します。
   * 
   * @return orderContainer orderContainer
   */
  public OrderContainer getOrderContainer() {
    return orderContainer;
  }

  /**
   * orderContainerを設定します。
   * 
   * @param orderContainer
   *          orderContainer
   */
  public void setOrderContainer(OrderContainer orderContainer) {
    this.orderContainer = orderContainer;
  }

  public String getPaymentMethodName() {
    return paymentMethodName;
  }

  public void setPaymentMethodName(String paymentMethod) {
    this.paymentMethodName = paymentMethod;
  }

  public String getPaymentTotalPrice() {
    return paymentTotalPrice;
  }

  public void setPaymentTotalPrice(String paymentTotalPrice) {
    this.paymentTotalPrice = paymentTotalPrice;
  }

  public void createAttributes(RequestParameter reqparam) {

    if (reqparam.getPathArgs().length > 0) {
      setOrderNo(StringUtil.coalesce(reqparam.getPathArgs()[0], "")); //$NON-NLS-1$
    }

  }

  public String getModuleId() {
    return "U2020200"; //$NON-NLS-1$
  }

  public String getModuleName() {
    return Messages.getString("web.bean.front.order.ExternalPaymentBean.0"); //$NON-NLS-1$
  }

  public Object getPaymentConfiguration() {
    return new Object();
  }

  /**
   * paymentFormObjectを取得します。
   * 
   * @return paymentFormObject paymentFormObject
   */
  public Object getPaymentFormObject() {
    return paymentFormObject;
  }

  /**
   * paymentFormObjectを設定します。
   * 
   * @param paymentFormObject
   *          paymentFormObject
   */
  public void setPaymentFormObject(Object paymentFormObject) {
    this.paymentFormObject = paymentFormObject;
  }
  /**
   * 取得 displayChinapayInfo
   * 
   * @return the displayChinapayInfo
   */
  public boolean isDisplayChinapayInfo() {
    return displayChinapayInfo;
  }

  /**
   * 设定 displayChinapayInfo
   * 
   * @param displayChinapayInfo
   *          the displayChinapayInfo to set
   */
  public void setDisplayChinapayInfo(boolean displayChinapayInfo) {
    this.displayChinapayInfo = displayChinapayInfo;
  }
  /**
   * 取得 displayAlipayInfo
   * 
   * @return the displayAlipayInfo
   */
  public boolean isDisplayAlipayInfo() {
    return displayAlipayInfo;
  }

  /**
   * 设定 displayAlipayInfo
   * 
   * @param displayAlipayInfo
   *          the displayAlipayInfo to set
   */
  public void setDisplayAlipayInfo(boolean displayAlipayInfo) {
    this.displayAlipayInfo = displayAlipayInfo;
  }

  /**
   * @return the firstTimePay
   */
  public boolean isFirstTimePay() {
    return firstTimePay;
  }

  /**
   * @param firstTimePay
   *          the firstTimePay to set
   */
  public void setFirstTimePay(boolean firstTimePay) {
    this.firstTimePay = firstTimePay;
  }
  /**
   * 获得 pattern
   * 
   * @return pattern
   */
  
  public String getPattern() {
    return pattern;
  }

  
  /**
   * 设定 pattern
   * @param pattern
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
  }
}
