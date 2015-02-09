package jp.co.sint.webshop.web.bean.back.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.EmailForSearch;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class MemberInfoBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  /** 会员查询一览 */
  private List<CustomerSearchedBean> customerList = new ArrayList<CustomerSearchedBean>();

  /** 订单发货一览 */
  private List<ShippingSearchedBean> shippingList = new ArrayList<ShippingSearchedBean>();

  /** 咨询一览 */
  private List<InquirySearchedBean> inquiryList = new ArrayList<InquirySearchedBean>();

  /** 代金券一览 */
  private List<CouponSearchedBean> couponList = new ArrayList<CouponSearchedBean>();

  /** 会员情报 */
  private CustomerSearchedBean customerInfo = new CustomerSearchedBean();

  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码", order = 1)
  private String searchMobile;

  @Length(18)
  @Digit(allowNegative = false)
  @Metadata(name = "固定电话", order = 2)
  private String searchTel;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "会员编号", order = 3)
  private String searchCustomerCode;

  @Length(40)
  @Metadata(name = "会员姓名", order = 4)
  private String searchCustomerName;

  @Digit
  @Length(16)
  @Metadata(name = "订单编号", order = 5)
  private String searchOrderNo;

  @Length(256)
  @EmailForSearch
  @Metadata(name = "邮件地址", order = 6)
  private String searchEmail;

  // soukai add 2012/01/31 ob start
  @Length(60)
  @Metadata(name = "运单号", order = 7)
  private String searchDeliverySlipNo;

  // soukai add 2012/01/31 ob end

  // 画面模式　true:履历详细画面 false:会员选择画面
  private boolean pageMode = false;

  // 显示的履历模式
  private String displayHistoryMode;

  private boolean displayInquiryButton = false;

  private boolean displayCustomerEditButton = false;

  private boolean displayAddressButton = false;

  private boolean displayPointButton = false;

  private boolean displayInquiryEditButton = false;

  private boolean displayShippingButton = false;

  private boolean displayReturnButton = false;

  private boolean displayTryButton = false;

  private boolean displaySkinButton = false;

  private boolean displayOrderLink = false;

  private boolean displayHistoryArea = false;

  private boolean displayReturnEditButton = false;

  private boolean displayReturnGoodsConfirmButton = false;

  private boolean displayReturnRefundConfirmButton = false;

  private boolean displayReturnCompleteButton = false;

  /**
   * @return the customerList
   */
  public List<CustomerSearchedBean> getCustomerList() {
    return customerList;
  }

  /**
   * @param customerList
   *          the customerList to set
   */
  public void setCustomerList(List<CustomerSearchedBean> customerList) {
    this.customerList = customerList;
  }

  /**
   * @return the shippingList
   */
  public List<ShippingSearchedBean> getShippingList() {
    return shippingList;
  }

  /**
   * @param shippingList
   *          the shippingList to set
   */
  public void setShippingList(List<ShippingSearchedBean> shippingList) {
    this.shippingList = shippingList;
  }

  /**
   * @return the inquiryList
   */
  public List<InquirySearchedBean> getInquiryList() {
    return inquiryList;
  }

  /**
   * @param inquiryList
   *          the inquiryList to set
   */
  public void setInquiryList(List<InquirySearchedBean> inquiryList) {
    this.inquiryList = inquiryList;
  }

  /**
   * @return the couponList
   */
  public List<CouponSearchedBean> getCouponList() {
    return couponList;
  }

  /**
   * @param couponList
   *          the couponList to set
   */
  public void setCouponList(List<CouponSearchedBean> couponList) {
    this.couponList = couponList;
  }

  /**
   * @return the customerInfo
   */
  public CustomerSearchedBean getCustomerInfo() {
    return customerInfo;
  }

  /**
   * @param customerInfo
   *          the customerInfo to set
   */
  public void setCustomerInfo(CustomerSearchedBean customerInfo) {
    this.customerInfo = customerInfo;
  }

  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * @return the searchMobile
   */
  public String getSearchMobile() {
    return searchMobile;
  }

  /**
   * @param searchMobile
   *          the searchMobile to set
   */
  public void setSearchMobile(String searchMobile) {
    this.searchMobile = searchMobile;
  }

  /**
   * @return the searchTel
   */
  public String getSearchTel() {
    return searchTel;
  }

  /**
   * @param searchTel
   *          the searchTel to set
   */
  public void setSearchTel(String searchTel) {
    this.searchTel = searchTel;
  }

  /**
   * @return the searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * @param searchCustomerCode
   *          the searchCustomerCode to set
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }

  /**
   * @return the searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * @param searchCustomerName
   *          the searchCustomerName to set
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * @return the searchOrderNo
   */
  public String getSearchOrderNo() {
    return searchOrderNo;
  }

  /**
   * @param searchOrderNo
   *          the searchOrderNo to set
   */
  public void setSearchOrderNo(String searchOrderNo) {
    this.searchOrderNo = searchOrderNo;
  }

  /**
   * @return the searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  /**
   * @param searchEmail
   *          the searchEmail to set
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  /**
   * @return the searchDeliverySlipNo
   */
  public String getSearchDeliverySlipNo() {
    return searchDeliverySlipNo;
  }

  /**
   * @param searchDeliverySlipNo
   *          the searchDeliverySlipNo to set
   */
  public void setSearchDeliverySlipNo(String searchDeliverySlipNo) {
    this.searchDeliverySlipNo = searchDeliverySlipNo;
  }

  /**
   * @return the pageMode
   */
  public boolean isPageMode() {
    return pageMode;
  }

  /**
   * @param pageMode
   *          the pageMode to set
   */
  public void setPageMode(boolean pageMode) {
    this.pageMode = pageMode;
  }

  /**
   * @return the displayHistoryMode
   */
  public String getDisplayHistoryMode() {
    return displayHistoryMode;
  }

  /**
   * @param displayHistoryMode
   *          the displayHistoryMode to set
   */
  public void setDisplayHistoryMode(String displayHistoryMode) {
    this.displayHistoryMode = displayHistoryMode;
  }

  /**
   * @return the displayInquiryButton
   */
  public boolean isDisplayInquiryButton() {
    return displayInquiryButton;
  }

  /**
   * @param displayInquiryButton
   *          the displayInquiryButton to set
   */
  public void setDisplayInquiryButton(boolean displayInquiryButton) {
    this.displayInquiryButton = displayInquiryButton;
  }

  /**
   * @return the displayCustomerEditButton
   */
  public boolean isDisplayCustomerEditButton() {
    return displayCustomerEditButton;
  }

  /**
   * @param displayCustomerEditButton
   *          the displayCustomerEditButton to set
   */
  public void setDisplayCustomerEditButton(boolean displayCustomerEditButton) {
    this.displayCustomerEditButton = displayCustomerEditButton;
  }

  /**
   * @return the displayAddressButton
   */
  public boolean isDisplayAddressButton() {
    return displayAddressButton;
  }

  /**
   * @param displayAddressButton
   *          the displayAddressButton to set
   */
  public void setDisplayAddressButton(boolean displayAddressButton) {
    this.displayAddressButton = displayAddressButton;
  }

  /**
   * @return the displayPointButton
   */
  public boolean isDisplayPointButton() {
    return displayPointButton;
  }

  /**
   * @param displayPointButton
   *          the displayPointButton to set
   */
  public void setDisplayPointButton(boolean displayPointButton) {
    this.displayPointButton = displayPointButton;
  }

  /**
   * @return the displayInquiryEditButton
   */
  public boolean isDisplayInquiryEditButton() {
    return displayInquiryEditButton;
  }

  /**
   * @param displayInquiryEditButton
   *          the displayInquiryEditButton to set
   */
  public void setDisplayInquiryEditButton(boolean displayInquiryEditButton) {
    this.displayInquiryEditButton = displayInquiryEditButton;
  }

  /**
   * @return the displayShippingButton
   */
  public boolean isDisplayShippingButton() {
    return displayShippingButton;
  }

  /**
   * @param displayShippingButton
   *          the displayShippingButton to set
   */
  public void setDisplayShippingButton(boolean displayShippingButton) {
    this.displayShippingButton = displayShippingButton;
  }

  /**
   * @return the displayReturnButton
   */
  public boolean isDisplayReturnButton() {
    return displayReturnButton;
  }

  /**
   * @param displayReturnButton
   *          the displayReturnButton to set
   */
  public void setDisplayReturnButton(boolean displayReturnButton) {
    this.displayReturnButton = displayReturnButton;
  }

  /**
   * @return the displayTryButton
   */
  public boolean isDisplayTryButton() {
    return displayTryButton;
  }

  /**
   * @param displayTryButton
   *          the displayTryButton to set
   */
  public void setDisplayTryButton(boolean displayTryButton) {
    this.displayTryButton = displayTryButton;
  }

  /**
   * @return the displaySkinButton
   */
  public boolean isDisplaySkinButton() {
    return displaySkinButton;
  }

  /**
   * @param displaySkinButton
   *          the displaySkinButton to set
   */
  public void setDisplaySkinButton(boolean displaySkinButton) {
    this.displaySkinButton = displaySkinButton;
  }

  /**
   * @return the displayOrderLink
   */
  public boolean isDisplayOrderLink() {
    return displayOrderLink;
  }

  /**
   * @param displayOrderLink
   *          the displayOrderLink to set
   */
  public void setDisplayOrderLink(boolean displayOrderLink) {
    this.displayOrderLink = displayOrderLink;
  }

  /**
   * @return the displayHistoryArea
   */
  public boolean isDisplayHistoryArea() {
    return displayHistoryArea;
  }

  /**
   * @param displayHistoryArea
   *          the displayHistoryArea to set
   */
  public void setDisplayHistoryArea(boolean displayHistoryArea) {
    this.displayHistoryArea = displayHistoryArea;
  }

  public static class CustomerSearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 会员编号 */
    private String customerCode;

    /** 会员姓名 */
    private String customerName;

    /** 邮件地址 */
    private String email;

    /** 手机号码 */
    private String mobile;

    /** 固定电话 */
    private String tel;

    /** 会员状态 */
    private String customerStatus;

    /** 性别 */
    private String sex;

    // soukai delete 2012/02/01 ob start
    // /** 邮政编码 */
    // private String postCode;
    //
    // /** 地址 */
    // private String address;
    // soukai delete 2012/02/01 ob end

    /** 注意事项 */
    private String caution;

    // 20120326 ysy add start
    /** 会员级别 */
    private String customerGroupCode;

    /** 支付宝快捷登录 */
    private String customerKbn;

    // 20120326 ysy add end

    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
      return customerCode;
    }

    /**
     * @return the customerGroupCode
     */
    public String getCustomerGroupCode() {
      return customerGroupCode;
    }

    /**
     * @param customerGroupCode
     *          the customerGroupCode to set
     */
    public void setCustomerGroupCode(String customerGroupCode) {
      this.customerGroupCode = customerGroupCode;
    }

    /**
     * @return the customerKbn
     */
    public String getCustomerKbn() {
      return customerKbn;
    }

    /**
     * @param customerKbn
     *          the customerKbn to set
     */
    public void setCustomerKbn(String customerKbn) {
      this.customerKbn = customerKbn;
    }

    /**
     * @param customerCode
     *          the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
      return customerName;
    }

    /**
     * @param customerName
     *          the customerName to set
     */
    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
      return email;
    }

    /**
     * @param email
     *          the email to set
     */
    public void setEmail(String email) {
      this.email = email;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
      return mobile;
    }

    /**
     * @param mobile
     *          the mobile to set
     */
    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    /**
     * @return the tel
     */
    public String getTel() {
      return tel;
    }

    /**
     * @param tel
     *          the tel to set
     */
    public void setTel(String tel) {
      this.tel = tel;
    }

    /**
     * @return the customerStatus
     */
    public String getCustomerStatus() {
      return customerStatus;
    }

    /**
     * @param customerStatus
     *          the customerStatus to set
     */
    public void setCustomerStatus(String customerStatus) {
      this.customerStatus = customerStatus;
    }

    /**
     * @return the sex
     */
    public String getSex() {
      return sex;
    }

    /**
     * @param sex
     *          the sex to set
     */
    public void setSex(String sex) {
      this.sex = sex;
    }

    /**
     * @return the caution
     */
    public String getCaution() {
      return caution;
    }

    /**
     * @param caution
     *          the caution to set
     */
    public void setCaution(String caution) {
      this.caution = caution;
    }

  }

  public static class ShippingSearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 订单编号 */
    private String orderNo;

    /** 发货编号 */
    private String shippingNo;

    /** 订单日 */
    private String orderDate;

    /** 付款日 */
    private String paymentDate;

    /** 支付方式 */
    private String paymentMethodName;

    /** 订单金额 */
    private String orderPrice;

    /** 发货状况 */
    private String shippingStatus;

    /** 发货状况名称 */
    private String shippingStatusName;

    /** 配送方式 */
    private String deliveryTypeName;

    /** 运单号 */
    private String deliverySlipNo;

    /** 发货指示日 */
    private String shippingDirectDate;

    /** 发货日 */
    private String shippingDate;

    /** 预计送达日 */
    private String arrivalDate;

    /** 区分 */
    private String orderStatus;

    /** 销售确定状态 */
    private String fixedSalesStatus;

    /** 配送公司 */
    private String deliveryCompanyName;

    // 20131106 txw add start
    private boolean displayRefundLink = false;

    // 20131106 txw add end

    public String getDeliveryCompanyName() {
      return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
      this.deliveryCompanyName = deliveryCompanyName;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    /**
     * @param orderNo
     *          the orderNo to set
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    /**
     * @return the shippingNo
     */
    public String getShippingNo() {
      return shippingNo;
    }

    /**
     * @param shippingNo
     *          the shippingNo to set
     */
    public void setShippingNo(String shippingNo) {
      this.shippingNo = shippingNo;
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
      return orderDate;
    }

    /**
     * @param orderDate
     *          the orderDate to set
     */
    public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
    }

    /**
     * @return the paymentDate
     */
    public String getPaymentDate() {
      return paymentDate;
    }

    /**
     * @param paymentDate
     *          the paymentDate to set
     */
    public void setPaymentDate(String paymentDate) {
      this.paymentDate = paymentDate;
    }

    /**
     * @return the paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
    }

    /**
     * @param paymentMethodName
     *          the paymentMethodName to set
     */
    public void setPaymentMethodName(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
    }

    /**
     * @return the orderPrice
     */
    public String getOrderPrice() {
      return orderPrice;
    }

    /**
     * @param orderPrice
     *          the orderPrice to set
     */
    public void setOrderPrice(String orderPrice) {
      this.orderPrice = orderPrice;
    }

    /**
     * @return the shippingStatus
     */
    public String getShippingStatus() {
      return shippingStatus;
    }

    /**
     * @param shippingStatus
     *          the shippingStatus to set
     */
    public void setShippingStatus(String shippingStatus) {
      this.shippingStatus = shippingStatus;
    }

    /**
     * @return the deliveryTypeName
     */
    public String getDeliveryTypeName() {
      return deliveryTypeName;
    }

    /**
     * @param deliveryTypeName
     *          the deliveryTypeName to set
     */
    public void setDeliveryTypeName(String deliveryTypeName) {
      this.deliveryTypeName = deliveryTypeName;
    }

    /**
     * @return the deliverySlipNo
     */
    public String getDeliverySlipNo() {
      return deliverySlipNo;
    }

    /**
     * @param deliverySlipNo
     *          the deliverySlipNo to set
     */
    public void setDeliverySlipNo(String deliverySlipNo) {
      this.deliverySlipNo = deliverySlipNo;
    }

    /**
     * @return the shippingDirectDate
     */
    public String getShippingDirectDate() {
      return shippingDirectDate;
    }

    /**
     * @param shippingDirectDate
     *          the shippingDirectDate to set
     */
    public void setShippingDirectDate(String shippingDirectDate) {
      this.shippingDirectDate = shippingDirectDate;
    }

    /**
     * @return the shippingDate
     */
    public String getShippingDate() {
      return shippingDate;
    }

    /**
     * @param shippingDate
     *          the shippingDate to set
     */
    public void setShippingDate(String shippingDate) {
      this.shippingDate = shippingDate;
    }

    /**
     * @return the arrivalDate
     */
    public String getArrivalDate() {
      return arrivalDate;
    }

    /**
     * @param arrivalDate
     *          the arrivalDate to set
     */
    public void setArrivalDate(String arrivalDate) {
      this.arrivalDate = arrivalDate;
    }

    /**
     * @return the orderStatus
     */
    public String getOrderStatus() {
      return orderStatus;
    }

    /**
     * @param orderStatus
     *          the orderStatus to set
     */
    public void setOrderStatus(String orderStatus) {
      this.orderStatus = orderStatus;
    }

    /**
     * @return the shippingStatusName
     */
    public String getShippingStatusName() {
      return shippingStatusName;
    }

    /**
     * @param shippingStatusName
     *          the shippingStatusName to set
     */
    public void setShippingStatusName(String shippingStatusName) {
      this.shippingStatusName = shippingStatusName;
    }

    /**
     * @return the fixedSalesStatus
     */
    public String getFixedSalesStatus() {
      return fixedSalesStatus;
    }

    /**
     * @param fixedSalesStatus
     *          the fixedSalesStatus to set
     */
    public void setFixedSalesStatus(String fixedSalesStatus) {
      this.fixedSalesStatus = fixedSalesStatus;
    }

    /**
     * @return the displayRefundLink
     */
    public boolean isDisplayRefundLink() {
      return displayRefundLink;
    }

    /**
     * @param displayRefundLink
     *          the displayRefundLink to set
     */
    public void setDisplayRefundLink(boolean displayRefundLink) {
      this.displayRefundLink = displayRefundLink;
    }

  }

  public static class InquirySearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 咨询编号 */
    private String inquiryHeaderNo;

    /** 受理日期 */
    private String acceptDate;

    /** 大分类 */
    private String largeCategory;

    /** 关联小分类 */
    private String smallCategory;

    /** 咨询途径 */
    private String inquiryWay;

    /** 咨询主题 */
    private String inquirySubject;

    /** 最终担当者 */
    private String personInChargeName;

    /** 担当者编号 */
    private String personInChargeNo;

    /** 更新时间 */
    private String acceptUpdate;

    /** 咨询状态 */
    private String inquiryStatus;

    /**
     * @return the inquiryHeaderNo
     */
    public String getInquiryHeaderNo() {
      return inquiryHeaderNo;
    }

    /**
     * @param inquiryHeaderNo
     *          the inquiryHeaderNo to set
     */
    public void setInquiryHeaderNo(String inquiryHeaderNo) {
      this.inquiryHeaderNo = inquiryHeaderNo;
    }

    /**
     * @return the acceptDate
     */
    public String getAcceptDate() {
      return acceptDate;
    }

    /**
     * @param acceptDate
     *          the acceptDate to set
     */
    public void setAcceptDate(String acceptDate) {
      this.acceptDate = acceptDate;
    }

    /**
     * @return the largeCategory
     */
    public String getLargeCategory() {
      return largeCategory;
    }

    /**
     * @param largeCategory
     *          the largeCategory to set
     */
    public void setLargeCategory(String largeCategory) {
      this.largeCategory = largeCategory;
    }

    /**
     * @return the smallCategory
     */
    public String getSmallCategory() {
      return smallCategory;
    }

    /**
     * @param smallCategory
     *          the smallCategory to set
     */
    public void setSmallCategory(String smallCategory) {
      this.smallCategory = smallCategory;
    }

    /**
     * @return the inquiryWay
     */
    public String getInquiryWay() {
      return inquiryWay;
    }

    /**
     * @param inquiryWay
     *          the inquiryWay to set
     */
    public void setInquiryWay(String inquiryWay) {
      this.inquiryWay = inquiryWay;
    }

    /**
     * @return the inquirySubject
     */
    public String getInquirySubject() {
      return inquirySubject;
    }

    /**
     * @param inquirySubject
     *          the inquirySubject to set
     */
    public void setInquirySubject(String inquirySubject) {
      this.inquirySubject = inquirySubject;
    }

    /**
     * @return the personInChargeName
     */
    public String getPersonInChargeName() {
      return personInChargeName;
    }

    /**
     * @param personInChargeName
     *          the personInChargeName to set
     */
    public void setPersonInChargeName(String personInChargeName) {
      this.personInChargeName = personInChargeName;
    }

    /**
     * @return the personInChargeNo
     */
    public String getPersonInChargeNo() {
      return personInChargeNo;
    }

    /**
     * @param personInChargeNo
     *          the personInChargeNo to set
     */
    public void setPersonInChargeNo(String personInChargeNo) {
      this.personInChargeNo = personInChargeNo;
    }

    /**
     * @return the acceptUpdate
     */
    public String getAcceptUpdate() {
      return acceptUpdate;
    }

    /**
     * @param acceptUpdate
     *          the acceptUpdate to set
     */
    public void setAcceptUpdate(String acceptUpdate) {
      this.acceptUpdate = acceptUpdate;
    }

    /**
     * @return the inquiryStatus
     */
    public String getInquiryStatus() {
      return inquiryStatus;
    }

    /**
     * @param inquiryStatus
     *          the inquiryStatus to set
     */
    public void setInquiryStatus(String inquiryStatus) {
      this.inquiryStatus = inquiryStatus;
    }

  }

  public static class CouponSearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 代金券发行订单编号 */
    private String couponOrderNo;

    /** 代金券规则编号 */
    private String couponRuleNo;

    // soukai add 2012/02/01 ob start
    /** 优惠券编号 */
    private String couponIssueNo;

    /** 优惠券名称 */
    private String couponRuleName;

    // soukai add 2012/02/01 ob end

    /** 代金券明细编号 */
    private String couponIssueDetailNo;

    /** 代金券使用开始日期 */
    private String couponUseStartDate;

    /** 代金券使用结束日期 */
    private String couponUseEndDate;

    /** 代金券发行日期 */
    private String couponIssueDate;

    /** 代金券金额 */
    private String couponPrice;

    /** 代金券使用最低购买金额 */
    private String couponInvestPurchasePrice;

    /** 代金券使用区分 */
    private String couponUse;

    /** 代金券使用订单编号 */
    private String couponUseOrderNo;

    // 20111107 yuyongqiang add start
    /** 代金券使用订单编号 */
    private String couponIssueReason;

    // 20111107 yuyongqiang add end

    // soukai add 2012/02/01 ob start
    private boolean dispalyReturnCouponStatus = false;

    // soukai add 2012/02/01 ob end

    /**
     * @return the couponOrderNo
     */
    public String getCouponOrderNo() {
      return couponOrderNo;
    }

    /**
     * @return the couponIssueReason
     */
    public String getCouponIssueReason() {
      return couponIssueReason;
    }

    /**
     * @param couponIssueReason
     *          the couponIssueReason to set
     */
    public void setCouponIssueReason(String couponIssueReason) {
      this.couponIssueReason = couponIssueReason;
    }

    /**
     * @param couponOrderNo
     *          the couponOrderNo to set
     */
    public void setCouponOrderNo(String couponOrderNo) {
      this.couponOrderNo = couponOrderNo;
    }

    /**
     * @return the couponRuleNo
     */
    public String getCouponRuleNo() {
      return couponRuleNo;
    }

    /**
     * @param couponRuleNo
     *          the couponRuleNo to set
     */
    public void setCouponRuleNo(String couponRuleNo) {
      this.couponRuleNo = couponRuleNo;
    }

    /**
     * @return the couponIssueDetailNo
     */
    public String getCouponIssueDetailNo() {
      return couponIssueDetailNo;
    }

    /**
     * @param couponIssueDetailNo
     *          the couponIssueDetailNo to set
     */
    public void setCouponIssueDetailNo(String couponIssueDetailNo) {
      this.couponIssueDetailNo = couponIssueDetailNo;
    }

    /**
     * @return the couponUseStartDate
     */
    public String getCouponUseStartDate() {
      return couponUseStartDate;
    }

    /**
     * @param couponUseStartDate
     *          the couponUseStartDate to set
     */
    public void setCouponUseStartDate(String couponUseStartDate) {
      this.couponUseStartDate = couponUseStartDate;
    }

    /**
     * @return the couponUseEndDate
     */
    public String getCouponUseEndDate() {
      return couponUseEndDate;
    }

    /**
     * @param couponUseEndDate
     *          the couponUseEndDate to set
     */
    public void setCouponUseEndDate(String couponUseEndDate) {
      this.couponUseEndDate = couponUseEndDate;
    }

    /**
     * @return the couponIssueDate
     */
    public String getCouponIssueDate() {
      return couponIssueDate;
    }

    /**
     * @param couponIssueDate
     *          the couponIssueDate to set
     */
    public void setCouponIssueDate(String couponIssueDate) {
      this.couponIssueDate = couponIssueDate;
    }

    /**
     * @return the couponPrice
     */
    public String getCouponPrice() {
      return couponPrice;
    }

    /**
     * @param couponPrice
     *          the couponPrice to set
     */
    public void setCouponPrice(String couponPrice) {
      this.couponPrice = couponPrice;
    }

    /**
     * @return the couponInvestPurchasePrice
     */
    public String getCouponInvestPurchasePrice() {
      return couponInvestPurchasePrice;
    }

    /**
     * @param couponInvestPurchasePrice
     *          the couponInvestPurchasePrice to set
     */
    public void setCouponInvestPurchasePrice(String couponInvestPurchasePrice) {
      this.couponInvestPurchasePrice = couponInvestPurchasePrice;
    }

    /**
     * @return the couponUse
     */
    public String getCouponUse() {
      return couponUse;
    }

    /**
     * @param couponUse
     *          the couponUse to set
     */
    public void setCouponUse(String couponUse) {
      this.couponUse = couponUse;
    }

    /**
     * @return the couponUseOrderNo
     */
    public String getCouponUseOrderNo() {
      return couponUseOrderNo;
    }

    /**
     * @param couponUseOrderNo
     *          the couponUseOrderNo to set
     */
    public void setCouponUseOrderNo(String couponUseOrderNo) {
      this.couponUseOrderNo = couponUseOrderNo;
    }

    /**
     * @return the couponRuleName
     */
    public String getCouponRuleName() {
      return couponRuleName;
    }

    /**
     * @param couponRuleName
     *          the couponRuleName to set
     */
    public void setCouponRuleName(String couponRuleName) {
      this.couponRuleName = couponRuleName;
    }

    /**
     * @return the couponIssueNo
     */
    public String getCouponIssueNo() {
      return couponIssueNo;
    }

    /**
     * @param couponIssueNo
     *          the couponIssueNo to set
     */
    public void setCouponIssueNo(String couponIssueNo) {
      this.couponIssueNo = couponIssueNo;
    }

    /**
     * @return the dispalyReturnCouponStatus
     */
    public boolean isDispalyReturnCouponStatus() {
      return dispalyReturnCouponStatus;
    }

    /**
     * @param dispalyReturnCouponStatus
     *          the dispalyReturnCouponStatus to set
     */
    public void setDispalyReturnCouponStatus(boolean dispalyReturnCouponStatus) {
      this.dispalyReturnCouponStatus = dispalyReturnCouponStatus;
    }

  }

  /**
   * @return the displayReturnEditButton
   */
  public boolean isDisplayReturnEditButton() {
    return displayReturnEditButton;
  }

  /**
   * @param displayReturnEditButton
   *          the displayReturnEditButton to set
   */
  public void setDisplayReturnEditButton(boolean displayReturnEditButton) {
    this.displayReturnEditButton = displayReturnEditButton;
  }

  /**
   * @return the displayReturnGoodsConfirmButton
   */
  public boolean isDisplayReturnGoodsConfirmButton() {
    return displayReturnGoodsConfirmButton;
  }

  /**
   * @param displayReturnGoodsConfirmButton
   *          the displayReturnGoodsConfirmButton to set
   */
  public void setDisplayReturnGoodsConfirmButton(boolean displayReturnGoodsConfirmButton) {
    this.displayReturnGoodsConfirmButton = displayReturnGoodsConfirmButton;
  }

  /**
   * @return the displayReturnRefundConfirmButton
   */
  public boolean isDisplayReturnRefundConfirmButton() {
    return displayReturnRefundConfirmButton;
  }

  /**
   * @param displayReturnRefundConfirmButton
   *          the displayReturnRefundConfirmButton to set
   */
  public void setDisplayReturnRefundConfirmButton(boolean displayReturnRefundConfirmButton) {
    this.displayReturnRefundConfirmButton = displayReturnRefundConfirmButton;
  }

  /**
   * @return the displayReturnCompleteButton
   */
  public boolean isDisplayReturnCompleteButton() {
    return displayReturnCompleteButton;
  }

  /**
   * @param displayReturnCompleteButton
   *          the displayReturnCompleteButton to set
   */
  public void setDisplayReturnCompleteButton(boolean displayReturnCompleteButton) {
    this.displayReturnCompleteButton = displayReturnCompleteButton;
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
    return Messages.getString("web.bean.back.service.MemberInfoBean.0");
  }
}
