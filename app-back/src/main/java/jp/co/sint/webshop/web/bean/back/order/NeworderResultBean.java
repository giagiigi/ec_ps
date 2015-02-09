package jp.co.sint.webshop.web.bean.back.order;

import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020170:新規受注(完了)のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderResultBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String orderNo;

  private String orderDatetime;

  private String lastName;

  private String firstName;

  private String customerCode;

  private boolean displayContinue;

  private boolean displayOrderLink;

  private boolean displayCustomerLink;

  /**
   * displayCustomerLinkを取得します。
   * 
   * @return displayCustomerLink
   */
  public boolean isDisplayCustomerLink() {
    return displayCustomerLink;
  }

  /**
   * displayCustomerLinkを設定します。
   * 
   * @param displayCustomerLink
   *          displayCustomerLink
   */
  public void setDisplayCustomerLink(boolean displayCustomerLink) {
    this.displayCustomerLink = displayCustomerLink;
  }

  /**
   * displayOrderLinkを取得します。
   * 
   * @return displayOrderLink
   */
  public boolean isDisplayOrderLink() {
    return displayOrderLink;
  }

  /**
   * displayOrderLinkを設定します。
   * 
   * @param displayOrderLink
   *          displayOrderLink
   */
  public void setDisplayOrderLink(boolean displayOrderLink) {
    this.displayOrderLink = displayOrderLink;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * firstNameを取得します。
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * lastNameを取得します。
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
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
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
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
    return "U1020170";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderResultBean.0");
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

}
