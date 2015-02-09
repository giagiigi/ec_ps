package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030150:注文履歴のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** ソート条件 */
  public static final String ORDER_DATETIME_DESC = "orderDatetime";

  private List<OrderHistoryDetail> list = new ArrayList<OrderHistoryDetail>();

  private String email;

  private String lastName;

  private String firstName;

  @Phone
  private String phoneNumber;
  
  //Add by V10-CH start
  @MobileNumber
  private String mobileNumber;
  //Add by V10-CH end

  private String customerCode;

  private PagerValue pagerValue;

  /**
   * U1030150:注文履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderHistoryDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String orderNo;

    private String orderDatetime;

    private String paymentDate;

    private String paymentMethodName;

    private String totalPrice;

    private String orderStatus;

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
     * orderStatusを取得します。
     * 
     * @return orderStatus
     */
    public String getOrderStatus() {
      return orderStatus;
    }

    /**
     * paymentDateを取得します。
     * 
     * @return paymentDate
     */
    public String getPaymentDate() {
      return paymentDate;
    }

    /**
     * paymentMethodNameを取得します。
     * 
     * @return paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
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
     * orderStatusを設定します。
     * 
     * @param orderStatus
     *          orderStatus
     */
    public void setOrderStatus(String orderStatus) {
      this.orderStatus = orderStatus;
    }

    /**
     * paymentDateを設定します。
     * 
     * @param paymentDate
     *          paymentDate
     */
    public void setPaymentDate(String paymentDate) {
      this.paymentDate = paymentDate;
    }

    /**
     * paymentMethodNameを設定します。
     * 
     * @param paymentMethodName
     *          paymentMethodName
     */
    public void setPaymentMethodName(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
    }

    /**
     * totalPriceを設定します。
     * 
     * @param totalPrice
     *          totalPrice
     */
    public void setTotalPrice(String totalPrice) {
      this.totalPrice = totalPrice;
    }

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
   * eMailを取得します。
   * 
   * @return eMail
   */
  public String getEmail() {
    return email;
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
   * listを取得します。
   * 
   * @return list
   */
  public List<OrderHistoryDetail> getList() {
    return list;
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
   * eMailを設定します。
   * 
   * @param mail
   *          eMail
   */
  public void setEmail(String mail) {
    email = mail;
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
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<OrderHistoryDetail> list) {
    this.list = list;
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
    return "U1030150";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.OrderHistoryBean.0");
  }

  /**
   * phoneNumberを取得します。
   * 
   * @return phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * phoneNumberを設定します。
   * 
   * @param phoneNumber
   *          phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  
  /**
   * mobileNumberを取得します。
   *
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * mobileNumberを設定します。
   *
   * @param mobileNumber 
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
}
