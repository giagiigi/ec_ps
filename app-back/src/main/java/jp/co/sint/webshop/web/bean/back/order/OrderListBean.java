package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.EmailForSearch;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.AllOrderType;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.data.domain.OrderClientType;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderLanguageCode;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020210:受注入金管理のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class OrderListBean extends UIBackBean implements UISearchBean {

  private PagerValue pagerValue = new PagerValue();

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String advancedSearchMode;

  private List<String> checkedOrderList = new ArrayList<String>();

  private List<OrderSearchedBean> orderSearchedList = new ArrayList<OrderSearchedBean>();

  @Length(40)
  @Metadata(name = "顧客名", order = 1)
  private String searchCustomerName;

  @Kana
  @Length(80)
  @Metadata(name = "顧客名カナ", order = 2)
  private String searchCustomerNameKana;

  @Length(18)
  @Digit(allowNegative = false)
  @Metadata(name = "電話番号", order = 3)
  private String searchTel;

  // Add by V10-CH start
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码", order = 4)
  private String searchMobile;

  // Add by V10-CH end

  @Length(256)
  @EmailForSearch
  @Metadata(name = "メールアドレス", order = 5)
  private String searchEmail;

  // upd by lc 2012-03-06 start
  // @Digit
  @Length(16)
  @Metadata(name = "受注番号(From)", order = 6)
  private String searchFromOrderNo;

  // @Digit
  @Length(16)
  @Metadata(name = "受注番号(To)", order = 7)
  private String searchToOrderNo;

  // upd by lc 2012-03-06 end

  // @Digit
  @Length(16)
  @Metadata(name = "交易编号", order = 8)
  private String searchFromTmallTid;
  
//@Digit
  @Length(16)
  @Metadata(name = "京东交易编号", order = 9)
  private String searchFromJdDid;

  private boolean searchFixedSalesDataFlg;

  private boolean searchDataTransportFlg;

  private List<CodeAttribute> searchOrderStatusList = new ArrayList<CodeAttribute>();

  private List<String> searchOrderStatus = new ArrayList<String>();

  private String searchTargetedConnectedOrderData;

  private List<CodeAttribute> searchShippingStatusList = new ArrayList<CodeAttribute>();

  private List<String> searchShippingStatusSummary = new ArrayList<String>();

  private List<String> searchReturnStatusSummary = new ArrayList<String>();

  private boolean authorityRead = false;

  private boolean authorityUpdate = false;

  private boolean authorityDelete = false;

  private boolean authorityIO = false;

  @Datetime
  @Metadata(name = "受注日(From)", order = 10)
  private String searchFromOrderDatetime;

  @Datetime
  @Metadata(name = "受注日(To)", order = 11)
  private String searchToOrderDatetime;

  @Metadata(name = "支払方法", order = 12)
  private String searchPaymentMethod;

  @Metadata(name = "入金状況", order = 13)
  private String searchPaymentStatus;

  @Datetime
  @Metadata(name = "入金日(From)", order = 14)
  private String searchFromPaymentDatetime;

  @Datetime
  @Metadata(name = "入金日(To)", order = 15)
  private String searchToPaymentDatetime;

  private boolean searchPaymentLimitOver;

  @Digit
  @Length(2)
  @Metadata(name = "支払期限日数", order = 16)
  private String paymentLimitDays;

  private boolean withSentPaymentReminder;

  private boolean orderByOrderNo;

  private boolean orderByCustomerName;

  private boolean orderByOrderDatetime;

  private boolean orderByPaymentDatetime;

  private RegisterPaymentDateBean registerPaymentDateBean;

  private List<CodeAttribute> paymentMethodList;

  // soukai add 2011/12/29 ob start
  // 订单总合计金额
  private String purchasingPriceTotal;

  // soukai add 2011/12/29 ob end

  // soukai add 2012/01/05 ob start
  // 受注タイプ(EC/TMALL)
  private String orderType;
  
  private String orderClientType;

  // 検査フラグ
  private String orderFlg;

  private String languageCode;
  // soukai add 2012/01/05 ob end
  /**
   * 子订单编号
   */
  private String childOrderNo;

  /**
   * U1020210:受注入金管理のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RegisterPaymentDateBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Datetime
    @Metadata(name = "入金日(入金日設定)")
    private String registerPaymentDatetime;

    /**
     * registerPaymentDatetimeを取得します。
     * 
     * @return registerPaymentDatetime
     */
    public String getRegisterPaymentDatetime() {
      return registerPaymentDatetime;
    }

    /**
     * registerPaymentDatetimeを設定します。
     * 
     * @param registerPaymentDatetime
     *          registerPaymentDatetime
     */
    public void setRegisterPaymentDatetime(String registerPaymentDatetime) {
      this.registerPaymentDatetime = registerPaymentDatetime;
    }
  }

  /**
   * U1020210:受注入金管理のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderSearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String orderCheckbox;

    private String orderNo;

    private String guestFlg;

    private String customerName;

    private boolean memo;

    private String message;

    private String caution;

    private String tel;

    // Add by V10-CH start
    private String mobileTel;

    // Add by V10-CH end

    private String orderDatetime;

    private String paymentDate;

    private String paymentMethod;

    private String paymentLimitDate;

    private String purchasingPrice;

    private String orderStatus;

    private String shippingStatusSummary;

    private String returnStatusSummary;

    private Date updatedDatetime;

    private List<String> sentPaymentReceivedMailList;

    private List<String> sentPaymentReminderMailList;

    // soukai add 2011/12/29 ob start
    // 受注タイプ(名称)
    private String orderType;

    private String tmallTid;
    
    private String totalWeight;

    private String orderFlgName;

    // soukai add 2011/12/29 ob end
    /**
     * customerNameを取得します。
     * 
     * @return customerName
     */
    public String getCustomerName() {
      return customerName;
    }

    /**
     * memoを取得します。
     * 
     * @return memo
     */
    public boolean isMemo() {
      return memo;
    }

    public String getTmallTid() {
      return tmallTid;
    }

    public void setTmallTid(String tmallTid) {
      this.tmallTid = tmallTid;
    }

    
    /**
     * @return the totalWeight
     */
    public String getTotalWeight() {
      return totalWeight;
    }

    
    /**
     * @param totalWeight the totalWeight to set
     */
    public void setTotalWeight(String totalWeight) {
      this.totalWeight = totalWeight;
    }

    /**
     * orderCheckboxを取得します。
     * 
     * @return orderCheckbox
     */
    public String getOrderCheckbox() {
      return orderCheckbox;
    }

    /**
     * orderCodeを取得します。
     * 
     * @return orderNo
     */
    public String getOrderNo() {
      return orderNo;
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
     * paymentMethodを取得します。
     * 
     * @return paymentMethod
     */
    public String getPaymentMethod() {
      return paymentMethod;
    }

    /**
     * purchasingPriceを取得します。
     * 
     * @return purchasingPrice
     */
    public String getPurchasingPrice() {
      return purchasingPrice;
    }

    /**
     * shippingStatusSummaryを取得します。
     * 
     * @return shippingStatusSummary
     */
    public String getShippingStatusSummary() {
      return shippingStatusSummary;
    }

    /**
     * telを取得します。
     * 
     * @return tel
     */
    public String getTel() {
      return tel;
    }

    /**
     * customerNameを設定します。
     * 
     * @param customerName
     *          customerName
     */
    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

    /**
     * memoを設定します。
     * 
     * @param memo
     *          memo
     */
    public void setMemo(boolean memo) {
      this.memo = memo;
    }

    /**
     * orderCheckboxを設定します。
     * 
     * @param orderCheckbox
     *          orderCheckbox
     */
    public void setOrderCheckbox(String orderCheckbox) {
      this.orderCheckbox = orderCheckbox;
    }

    /**
     * orderCodeを設定します。
     * 
     * @param orderCode
     *          受注番号
     */
    public void setOrderNo(String orderCode) {
      this.orderNo = orderCode;
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
     * paymentMethodを設定します。
     * 
     * @param paymentMethod
     *          paymentMethod
     */
    public void setPaymentMethod(String paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    /**
     * purchasingPriceを設定します。
     * 
     * @param purchasingPrice
     *          purchasingPrice
     */
    public void setPurchasingPrice(String purchasingPrice) {
      this.purchasingPrice = purchasingPrice;
    }

    /**
     * shippingStatusSummaryを設定します。
     * 
     * @param shippingStatusSummary
     *          shippingStatusSummary
     */
    public void setShippingStatusSummary(String shippingStatusSummary) {
      this.shippingStatusSummary = shippingStatusSummary;
    }

    /**
     * telを設定します。
     * 
     * @param tel
     *          tel
     */
    public void setTel(String tel) {
      this.tel = tel;
    }

    /**
     * messageを取得します。
     * 
     * @return message
     */
    public String getMessage() {
      return message;
    }

    /**
     * messageを設定します。
     * 
     * @param message
     *          message
     */
    public void setMessage(String message) {
      this.message = message;
    }

    /**
     * cautionを取得します。
     * 
     * @return caution
     */
    public String getCaution() {
      return caution;
    }

    /**
     * cautionを設定します。
     * 
     * @param caution
     *          設定する caution
     */
    public void setCaution(String caution) {
      this.caution = caution;
    }

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * returnStatusSummaryを取得します。
     * 
     * @return returnStatusSummary
     */
    public String getReturnStatusSummary() {
      return returnStatusSummary;
    }

    /**
     * returnStatusSummaryを設定します。
     * 
     * @param returnStatusSummary
     *          returnStatusSummary
     */
    public void setReturnStatusSummary(String returnStatusSummary) {
      this.returnStatusSummary = returnStatusSummary;
    }

    /**
     * @return 連絡事項、注意事項（管理側のみ参照）に入力されていれば、該当する文字列を取得します。
     */
    public String getRemarksForDisplay() {
      StringBuilder builder = new StringBuilder();
      if (StringUtil.hasValue(this.getMessage())) {
        builder.append(Messages.getString("web.bean.back.order.OrderListBean.0"));
        builder.append(this.getMessage());
        builder.append(" "); //$NON-NLS-1$
      }
      if (StringUtil.hasValue(this.getCaution())) {
        builder.append(Messages.getString("web.bean.back.order.OrderListBean.1"));
        builder.append(this.getCaution());
        builder.append(" "); //$NON-NLS-1$
      }
      return builder.toString();
    }

    /**
     * guestFlgを取得します。
     * 
     * @return guestFlg
     */
    public String getGuestFlg() {
      return guestFlg;
    }

    /**
     * guestFlgを設定します。
     * 
     * @param guestFlg
     *          guestFlg
     */
    public void setGuestFlg(String guestFlg) {
      this.guestFlg = guestFlg;
    }

    /**
     * sentPaymentReceivedMailListを取得します。
     * 
     * @return sentPaymentReceivedMailList
     */
    public List<String> getSentPaymentReceivedMailList() {
      return sentPaymentReceivedMailList;
    }

    /**
     * sentPaymentReceivedMailListを設定します。
     * 
     * @param sentPaymentReceivedMailList
     *          sentPaymentReceivedMailList
     */
    public void setSentPaymentReceivedMailList(List<String> sentPaymentReceivedMailList) {
      this.sentPaymentReceivedMailList = sentPaymentReceivedMailList;
    }

    /**
     * sentPaymentReminderMailListを取得します。
     * 
     * @return sentPaymentReminderMailList
     */
    public List<String> getSentPaymentReminderMailList() {
      return sentPaymentReminderMailList;
    }

    /**
     * sentPaymentReminderMailListを設定します。
     * 
     * @param sentPaymentReminderMailList
     *          sentPaymentReminderMailList
     */
    public void setSentPaymentReminderMailList(List<String> sentPaymentReminderMailList) {
      this.sentPaymentReminderMailList = sentPaymentReminderMailList;
    }

    /**
     * paymentLimitDateを取得します。
     * 
     * @return paymentLimitDate
     */
    public String getPaymentLimitDate() {
      return paymentLimitDate;
    }

    /**
     * paymentLimitDateを設定します。
     * 
     * @param paymentLimitDate
     *          paymentLimitDate
     */
    public void setPaymentLimitDate(String paymentLimitDate) {
      this.paymentLimitDate = paymentLimitDate;
    }

    /**
     * mobileTelを取得します。
     * 
     * @return mobileTel mobileTel
     */
    public String getMobileTel() {
      return mobileTel;
    }

    /**
     * mobileTelを設定します。
     * 
     * @param mobileTel
     *          mobileTel
     */
    public void setMobileTel(String mobileTel) {
      this.mobileTel = mobileTel;
    }

    // soukai add 2011/12/29 ob start
    /**
     * 受注タイプを取得する
     * 
     * @return the orderType
     */
    public String getOrderType() {
      return orderType;
    }

    /**
     * 受注タイプを設定する
     * 
     * @param orderType
     *          the orderType to set
     */
    public void setOrderType(String orderType) {
      this.orderType = orderType;
    }

    // soukai add 2011/12/29 ob end

    /**
     * @return the orderFlgName
     */
    public String getOrderFlgName() {
      return orderFlgName;
    }

    /**
     * @param orderFlgName
     *          the orderFlgName to set
     */
    public void setOrderFlgName(String orderFlgName) {
      this.orderFlgName = orderFlgName;
    }

  }

  /**
   * orderByCustomerNameを取得します。
   * 
   * @return orderByCustomerName
   */
  public boolean isOrderByCustomerName() {
    return orderByCustomerName;
  }

  /**
   * orderByOrderNoを取得します。
   * 
   * @return orderByOrderNo
   */
  public boolean isOrderByOrderNo() {
    return orderByOrderNo;
  }

  /**
   * orderByOrderDatetimeを取得します。
   * 
   * @return orderByOrderDatetime
   */
  public boolean isOrderByOrderDatetime() {
    return orderByOrderDatetime;
  }

  /**
   * orderByPaymentDatetimeを取得します。
   * 
   * @return orderByPaymentDatetime
   */
  public boolean isOrderByPaymentDatetime() {
    return orderByPaymentDatetime;
  }

  /**
   * orderSearchedListを取得します。
   * 
   * @return orderSearchedList
   */
  public List<OrderSearchedBean> getOrderSearchedList() {
    return orderSearchedList;
  }

  /**
   * searchCustomerNameを取得します。
   * 
   * @return searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを取得します。
   * 
   * @return searchCustomerNameKana
   */
  public String getSearchCustomerNameKana() {
    return searchCustomerNameKana;
  }

  /**
   * searchEmailを取得します。
   * 
   * @return searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  /**
   * searchFromOrderNoを取得します。
   * 
   * @return searchFromOrderNo
   */
  public String getSearchFromOrderNo() {
    return searchFromOrderNo;
  }

  /**
   * searchFromOrderDatetimeを取得します。
   * 
   * @return searchFromOrderDatetime
   */
  public String getSearchFromOrderDatetime() {
    return searchFromOrderDatetime;
  }

  /**
   * searchFromPaymentDatetimeを取得します。
   * 
   * @return searchFromPaymentDatetime
   */
  public String getSearchFromPaymentDatetime() {
    return searchFromPaymentDatetime;
  }

  /**
   * searchPaymentMethodを取得します。
   * 
   * @return searchPaymentMethod
   */
  public String getSearchPaymentMethod() {
    return searchPaymentMethod;
  }

  /**
   * searchPaymentStatusを取得します。
   * 
   * @return searchPaymentStatus
   */
  public String getSearchPaymentStatus() {
    return searchPaymentStatus;
  }

  /**
   * searchTargetedConnectedOrderDataを取得します。
   * 
   * @return searchTargetedConnectedOrderData
   */
  public String getSearchTargetedConnectedOrderData() {
    return searchTargetedConnectedOrderData;
  }

  /**
   * searchTelを取得します。
   * 
   * @return searchTel
   */
  public String getSearchTel() {
    return searchTel;
  }

  /**
   * searchToOrderNoを取得します。
   * 
   * @return searchToOrderNo
   */
  public String getSearchToOrderNo() {
    return searchToOrderNo;
  }

  /**
   * searchToOrderDatetimeを取得します。
   * 
   * @return searchToOrderDatetime
   */
  public String getSearchToOrderDatetime() {
    return searchToOrderDatetime;
  }

  /**
   * searchToPaymentDatetimeを取得します。
   * 
   * @return searchToPaymentDatetime
   */
  public String getSearchToPaymentDatetime() {
    return searchToPaymentDatetime;
  }

  /**
   * orderByCustomerNameを設定します。
   * 
   * @param orderByCustomerName
   *          orderByCustomerName
   */
  public void setOrderByCustomerName(boolean orderByCustomerName) {
    this.orderByCustomerName = orderByCustomerName;
  }

  /**
   * orderByOrderNoを設定します。
   * 
   * @param orderByOrderNo
   *          orderByOrderNo
   */
  public void setOrderByOrderNo(boolean orderByOrderNo) {
    this.orderByOrderNo = orderByOrderNo;
  }

  /**
   * orderByOrderDatetimeを設定します。
   * 
   * @param orderByOrderDatetime
   *          orderByOrderDatetime
   */
  public void setOrderByOrderDatetime(boolean orderByOrderDatetime) {
    this.orderByOrderDatetime = orderByOrderDatetime;
  }

  /**
   * orderByPaymentDatetimeを設定します。
   * 
   * @param orderByPaymentDatetime
   *          orderByPaymentDatetime
   */
  public void setOrderByPaymentDatetime(boolean orderByPaymentDatetime) {
    this.orderByPaymentDatetime = orderByPaymentDatetime;
  }

  /**
   * orderSearchedListを設定します。
   * 
   * @param orderSearchedList
   *          orderSearchedList
   */
  public void setOrderSearchedList(List<OrderSearchedBean> orderSearchedList) {
    this.orderSearchedList = orderSearchedList;
  }

  /**
   * searchCustomerNameを設定します。
   * 
   * @param searchCustomerName
   *          searchCustomerName
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを設定します。
   * 
   * @param searchCustomerNameKana
   *          searchCustomerNameKana
   */
  public void setSearchCustomerNameKana(String searchCustomerNameKana) {
    this.searchCustomerNameKana = searchCustomerNameKana;
  }

  /**
   * searchEmailを設定します。
   * 
   * @param searchEmail
   *          searchEmail
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  /**
   * searchFromOrderNoを設定します。
   * 
   * @param searchFromOrderNo
   *          searchFromOrderNo
   */
  public void setSearchFromOrderNo(String searchFromOrderNo) {
    this.searchFromOrderNo = searchFromOrderNo;
  }

  /**
   * searchFromOrderDatetimeを設定します。
   * 
   * @param searchFromOrderDatetime
   *          searchFromOrderDatetime
   */
  public void setSearchFromOrderDatetime(String searchFromOrderDatetime) {
    this.searchFromOrderDatetime = searchFromOrderDatetime;
  }

  /**
   * searchFromPaymentDatetimeを設定します。
   * 
   * @param searchFromPaymentDatetime
   *          searchFromPaymentDatetime
   */
  public void setSearchFromPaymentDatetime(String searchFromPaymentDatetime) {
    this.searchFromPaymentDatetime = searchFromPaymentDatetime;
  }

  /**
   * searchPaymentMethodを設定します。
   * 
   * @param searchPaymentMethod
   *          searchPaymentMethod
   */
  public void setSearchPaymentMethod(String searchPaymentMethod) {
    this.searchPaymentMethod = searchPaymentMethod;
  }

  /**
   * searchPaymentStatusを設定します。
   * 
   * @param searchPaymentStatus
   *          searchPaymentStatus
   */
  public void setSearchPaymentStatus(String searchPaymentStatus) {
    this.searchPaymentStatus = searchPaymentStatus;
  }

  /**
   * searchTargetedConnectedOrderDataを設定します。
   * 
   * @param searchTargetedConnectedOrderData
   *          searchTargetedConnectedOrderData
   */
  public void setSearchTargetedConnectedOrderData(String searchTargetedConnectedOrderData) {
    this.searchTargetedConnectedOrderData = searchTargetedConnectedOrderData;
  }

  /**
   * searchTelを設定します。
   * 
   * @param searchTel
   *          searchTel
   */
  public void setSearchTel(String searchTel) {
    this.searchTel = searchTel;
  }

  /**
   * searchToOrderNoを設定します。
   * 
   * @param searchToOrderNo
   *          searchToOrderNo
   */
  public void setSearchToOrderNo(String searchToOrderNo) {
    this.searchToOrderNo = searchToOrderNo;
  }

  /**
   * searchToOrderDatetimeを設定します。
   * 
   * @param searchToOrderDatetime
   *          searchToOrderDatetime
   */
  public void setSearchToOrderDatetime(String searchToOrderDatetime) {
    this.searchToOrderDatetime = searchToOrderDatetime;
  }

  /**
   * searchToPaymentDatetimeを設定します。
   * 
   * @param searchToPaymentDatetime
   *          searchToPaymentDatetime
   */
  public void setSearchToPaymentDatetime(String searchToPaymentDatetime) {
    this.searchToPaymentDatetime = searchToPaymentDatetime;
  }

  /**
   * searchOrderStatusListを取得します。
   * 
   * @return searchOrderStatusList
   */
  public List<CodeAttribute> getSearchOrderStatusList() {
    return searchOrderStatusList;
  }

  /**
   * searchShippingStatusListを取得します。
   * 
   * @return searchShippingStatusList
   */
  public List<CodeAttribute> getSearchShippingStatusList() {
    return searchShippingStatusList;
  }

  /**
   * searchOrderStatusListを設定します。
   * 
   * @param searchOrderStatusList
   *          searchOrderStatusList
   */
  public void setSearchOrderStatusList(List<CodeAttribute> searchOrderStatusList) {
    this.searchOrderStatusList = searchOrderStatusList;
  }

  /**
   * searchShippingStatusListを設定します。
   * 
   * @param searchShippingStatusList
   *          searchShippingStatusList
   */
  public void setSearchShippingStatusList(List<CodeAttribute> searchShippingStatusList) {
    this.searchShippingStatusList = searchShippingStatusList;
  }

  /**
   * searchOrderStatusを取得します。
   * 
   * @return searchOrderStatus
   */
  public List<String> getSearchOrderStatus() {
    return searchOrderStatus;
  }

  /**
   * searchShippingStatusSummaryを取得します。
   * 
   * @return searchShippingStatusSummary
   */
  public List<String> getSearchShippingStatusSummary() {
    return searchShippingStatusSummary;
  }

  /**
   * searchOrderStatusを設定します。
   * 
   * @param searchOrderStatus
   *          searchOrderStatus
   */
  public void setSearchOrderStatus(List<String> searchOrderStatus) {
    this.searchOrderStatus = searchOrderStatus;
  }

  /**
   * searchShippingStatusSummaryを設定します。
   * 
   * @param searchShippingStatusSummary
   *          searchShippingStatusSummary
   */
  public void setSearchShippingStatusSummary(List<String> searchShippingStatusSummary) {
    this.searchShippingStatusSummary = searchShippingStatusSummary;
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
    reqparam.copy(this);

    setCheckedOrderList(Arrays.asList(reqparam.getAll("orderNo")));

    setSearchPaymentStatus(reqparam.get("searchPaymentStatus"));
    setSearchPaymentMethod(reqparam.get("searchPaymentMethod"));

    setSearchFromOrderDatetime(reqparam.getDateString("searchFromOrderDatetime"));
    setSearchToOrderDatetime(reqparam.getDateString("searchToOrderDatetime"));

    setSearchFromPaymentDatetime(reqparam.getDateString("searchFromPaymentDatetime"));
    setSearchToPaymentDatetime(reqparam.getDateString("searchToPaymentDatetime"));

    RegisterPaymentDateBean registerPaymentDate = new RegisterPaymentDateBean();
    registerPaymentDate.setRegisterPaymentDatetime(reqparam.getDateString("registerPaymentDatetime"));
    setRegisterPaymentDateBean(registerPaymentDate);

    setSearchOrderStatus(Arrays.asList(reqparam.getAll("searchOrderStatus")));
    setSearchShippingStatusSummary(Arrays.asList(reqparam.getAll("searchShippingStatusSummary")));
    setSearchReturnStatusSummary(Arrays.asList(reqparam.getAll("searchReturnStatusSummary")));

    setAdvancedSearchMode(reqparam.get("advancedSearchMode"));
    setSearchFixedSalesDataFlg(StringUtil.hasValue(reqparam.get("searchFixedSalesDataFlg")));
    setSearchDataTransportFlg(StringUtil.hasValue(reqparam.get("searchDataTransportFlg")));

    setSearchCustomerName(reqparam.get("searchCustomerName"));
    setSearchCustomerNameKana(reqparam.get("searchCustomerNameKana"));

    setSearchTel(StringUtil.toHalfWidth(reqparam.get("searchTel")));
    // Add by V10-CH start
    setSearchMobile(StringUtil.toHalfWidth(reqparam.get("searchMobile")));
    // Add by V10-CH end
    setSearchEmail(StringUtil.toHalfWidth(reqparam.get("searchEmail")));

    setSearchFromOrderNo(StringUtil.toHalfWidth(reqparam.get("searchFromOrderNo")));
    setSearchToOrderNo(StringUtil.toHalfWidth(reqparam.get("searchToOrderNo")));

    // 検索条件-支払期限
    setSearchPaymentLimitOver(StringUtil.hasValue(reqparam.get("searchPaymentLimitOver")));
    setPaymentLimitDays(reqparam.get("paymentLimitDays"));
    setWithSentPaymentReminder(StringUtil.hasValue(reqparam.get("withSentPaymentReminder")));

    setOrderByOrderNo(false);
    setOrderByCustomerName(false);
    setOrderByOrderDatetime(false);
    setOrderByPaymentDatetime(false);
    if (reqparam.get("sortColumName").equals("orderNo")) {
      setOrderByOrderNo(true);
    }
    if (reqparam.get("sortColumName").equals("customerName")) {
      setOrderByCustomerName(true);
    }
    if (reqparam.get("sortColumName").equals("orderDatetime")) {
      setOrderByOrderDatetime(true);
    }
    if (reqparam.get("sortColumName").equals("paymentDatetime")) {
      setOrderByPaymentDatetime(true);
    }
    // soukai add 2012/01/05 ob start
    // 受注タイプ
    this.setOrderType(reqparam.get("orderType"));
    
    this.setOrderClientType(reqparam.get("orderClientType"));
    // 検査フラグ
    this.setOrderFlg(reqparam.get("orderFlg"));
    // soukai add 2012/01/05 ob end
    //添加子订单编号检查
    this.setChildOrderNo(reqparam.get("childOrderNo"));
    
    }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.OrderListBean.2");
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
   * advancedSearchModeを取得します。
   * 
   * @return advancedSearchMode
   */
  public String getAdvancedSearchMode() {
    return advancedSearchMode;
  }

  /**
   * advancedSearchModeを設定します。
   * 
   * @param advancedSearchMode
   *          設定する advancedSearchMode
   */
  public void setAdvancedSearchMode(String advancedSearchMode) {
    this.advancedSearchMode = advancedSearchMode;
  }

  /**
   * 入金状況を取得します。
   * 
   * @return 入金状況のNameValueリスト
   */
  public List<NameValue> getPaymentStatusList() {
    return NameValue.asList(":" + Messages.getString("web.bean.back.order.OrderListBean.3") + "/0:"
        + Messages.getString("web.bean.back.order.OrderListBean.4") + "/1:"
        + Messages.getString("web.bean.back.order.OrderListBean.5"));
  }

  // Add by V10-CH start
  /**
   * 受注タイプラジオボタン内容を設定する
   */
  public List<NameValue> getOrderTypeList() {
    List<CodeAttribute> orderTypeList = new ArrayList<CodeAttribute>();
    orderTypeList.addAll(Arrays.asList(OrderType.values()));

    /*return NameValue.asList(":" + OrderType.ALL.getName() + "/" + OrderType.EC.getValue() + ":" + OrderType.EC.getName()+"[PC]" + "/"
        + OrderType.TMALL.getValue() + ":" + OrderType.TMALL.getName() + "/" + MobileComputerType.Mobile.getValue() + ":"
        + MobileComputerType.Mobile.getName());*/
    
    return NameValue.asList(":" + AllOrderType.ALL.getName() + "/" + AllOrderType.PC.getValue() + ":" + AllOrderType.PC.getName() + "/"
        + AllOrderType.TMALL.getValue() + ":" + AllOrderType.TMALL.getName() + "/" + AllOrderType.Mobile.getValue() + ":"
        + AllOrderType.Mobile.getName() + "/" + AllOrderType.JD.getValue() + ":" + AllOrderType.JD.getName());

    // return orderTypeList;
  }

  /**
   * 検査フラグラジオボタン内容を設定する
   */
  public List<NameValue> getOrderFlgList() {
    return NameValue.asList(":" + OrderFlg.ALL.getName() + "/" + OrderFlg.NOT_CHECKED.getValue() + ":"
        + OrderFlg.NOT_CHECKED.getName() + "/" + OrderFlg.CHECKED.getValue() + ":" + OrderFlg.CHECKED.getName());
  }

  /**
   * 使用语言区分
   */
  public List<CodeAttribute> getLanguageCodeList() {
    List<CodeAttribute> languageCodeList = new ArrayList<CodeAttribute>();
    languageCodeList.addAll(Arrays.asList(OrderLanguageCode.values()));
    return languageCodeList;
  }
  
  public List<CodeAttribute> getOrderClientTypeList() {
    List<CodeAttribute> orderClientTypeList = new ArrayList<CodeAttribute>();
    orderClientTypeList.addAll(Arrays.asList(OrderClientType.values()));
    return orderClientTypeList;
  }

  // Add by V10-CH end

  /**
   * authorityDeleteを取得します。
   * 
   * @return authorityDelete authorityDelete
   */
  public boolean isAuthorityDelete() {
    return authorityDelete;
  }

  /**
   * authorityDeleteを設定します。
   * 
   * @param authorityDelete
   *          authorityDelete
   */
  public void setAuthorityDelete(boolean authorityDelete) {
    this.authorityDelete = authorityDelete;
  }

  /**
   * authorityIOを取得します。
   * 
   * @return authorityIO authorityIO
   */
  public boolean isAuthorityIO() {
    return authorityIO;
  }

  /**
   * authorityIOを設定します。
   * 
   * @param authorityIO
   *          authorityIO
   */
  public void setAuthorityIO(boolean authorityIO) {
    this.authorityIO = authorityIO;
  }

  /**
   * authorityReadを取得します。
   * 
   * @return authorityRead authorityRead
   */
  public boolean isAuthorityRead() {
    return authorityRead;
  }

  /**
   * authorityReadを設定します。
   * 
   * @param authorityRead
   *          authorityRead
   */
  public void setAuthorityRead(boolean authorityRead) {
    this.authorityRead = authorityRead;
  }

  /**
   * authorityUpdateを取得します。
   * 
   * @return authorityUpdate authorityUpdate
   */
  public boolean isAuthorityUpdate() {
    return authorityUpdate;
  }

  /**
   * authorityUpdateを設定します。
   * 
   * @param authorityUpdate
   *          authorityUpdate
   */
  public void setAuthorityUpdate(boolean authorityUpdate) {
    this.authorityUpdate = authorityUpdate;
  }

  /**
   * registerPaymentDateBeanを取得します。
   * 
   * @return registerPaymentDateBean registerPaymentDateBean
   */
  public RegisterPaymentDateBean getRegisterPaymentDateBean() {
    return registerPaymentDateBean;
  }

  /**
   * registerPaymentDateBeanを設定します。
   * 
   * @param registerPaymentDateBean
   *          registerPaymentDateBean
   */
  public void setRegisterPaymentDateBean(RegisterPaymentDateBean registerPaymentDateBean) {
    this.registerPaymentDateBean = registerPaymentDateBean;
  }

  /**
   * searchFixedSalesDataFlgを取得します。
   * 
   * @return searchFixedSalesDataFlg
   */
  public boolean getSearchFixedSalesDataFlg() {
    return searchFixedSalesDataFlg;
  }

  /**
   * searchFixedSalesDataFlgを設定します。
   * 
   * @param searchFixedSalesDataFlg
   *          searchFixedSalesDataFlg
   */
  public void setSearchFixedSalesDataFlg(boolean searchFixedSalesDataFlg) {
    this.searchFixedSalesDataFlg = searchFixedSalesDataFlg;
  }

  /**
   * searchDataTransportFlgを取得します。
   * 
   * @return searchDataTransportFlg
   */
  public boolean getSearchDataTransportFlg() {
    return searchDataTransportFlg;
  }

  /**
   * searchDataTransportFlgを設定します。
   * 
   * @param searchDataTransportFlg
   *          searchDataTransportFlg
   */
  public void setSearchDataTransportFlg(boolean searchDataTransportFlg) {
    this.searchDataTransportFlg = searchDataTransportFlg;
  }

  /**
   * checkedOrderListを取得します。
   * 
   * @return checkedOrderList
   */
  public List<String> getCheckedOrderList() {
    return checkedOrderList;
  }

  /**
   * checkedOrderListを設定します。
   * 
   * @param checkedOrderList
   *          checkedOrderList
   */
  public void setCheckedOrderList(List<String> checkedOrderList) {
    this.checkedOrderList = checkedOrderList;
  }

  /**
   * searchReturnStatusSummaryを取得します。
   * 
   * @return searchReturnStatusSummary
   */
  public List<String> getSearchReturnStatusSummary() {
    return searchReturnStatusSummary;
  }

  /**
   * searchReturnStatusSummaryを設定します。
   * 
   * @param searchReturnStatusSummary
   *          searchReturnStatusSummary
   */
  public void setSearchReturnStatusSummary(List<String> searchReturnStatusSummary) {
    this.searchReturnStatusSummary = searchReturnStatusSummary;
  }

  /**
   * paymentMethodListを取得します。
   * 
   * @return paymentMethodList
   */
  public List<CodeAttribute> getPaymentMethodList() {
    return paymentMethodList;
  }

  /**
   * paymentMethodListを設定します。
   * 
   * @param paymentMethodList
   *          paymentMethodList
   */
  public void setPaymentMethodList(List<CodeAttribute> paymentMethodList) {
    this.paymentMethodList = paymentMethodList;
  }

  /**
   * searchPaymentLimitOverを取得します。
   * 
   * @return searchPaymentLimitOver
   */
  public boolean isSearchPaymentLimitOver() {
    return searchPaymentLimitOver;
  }

  /**
   * searchPaymentLimitOverを設定します。
   * 
   * @param searchPaymentLimitOver
   *          searchPaymentLimitOver
   */
  public void setSearchPaymentLimitOver(boolean searchPaymentLimitOver) {
    this.searchPaymentLimitOver = searchPaymentLimitOver;
  }

  /**
   * withSentPaymentReminderを取得します。
   * 
   * @return withSentPaymentReminder
   */
  public boolean isWithSentPaymentReminder() {
    return withSentPaymentReminder;
  }

  /**
   * withSentPaymentReminderを設定します。
   * 
   * @param withSentPaymentReminder
   *          withSentPaymentReminder
   */
  public void setWithSentPaymentReminder(boolean withSentPaymentReminder) {
    this.withSentPaymentReminder = withSentPaymentReminder;
  }

  /**
   * paymentLimitDaysを取得します。
   * 
   * @return paymentLimitDays
   */
  public String getPaymentLimitDays() {
    return paymentLimitDays;
  }

  /**
   * paymentLimitDaysを設定します。
   * 
   * @param paymentLimitDays
   *          paymentLimitDays
   */
  public void setPaymentLimitDays(String paymentLimitDays) {
    this.paymentLimitDays = paymentLimitDays;
  }

  /**
   * searchMobileを取得します。
   * 
   * @return searchMobile searchMobile
   */
  public String getSearchMobile() {
    return searchMobile;
  }

  /**
   * searchMobileを設定します。
   * 
   * @param searchMobile
   *          searchMobile
   */
  public void setSearchMobile(String searchMobile) {
    this.searchMobile = searchMobile;
  }

  // soukai add 2011/12/29 ob start
  /**
   * 订单总合计金额取得。
   * 
   * @return purchasingPriceTotal 订单总合计金额
   */
  public String getPurchasingPriceTotal() {
    return purchasingPriceTotal;
  }

  /**
   * 订单总合计金额设定
   * 
   * @param purchasingPriceTotal
   *          订单总合计金额
   */
  public void setPurchasingPriceTotal(String purchasingPriceTotal) {
    this.purchasingPriceTotal = purchasingPriceTotal;
  }

  // soukai add 2011/12/29 ob start

  // soukai add 2012/01/05 ob start
  /**
   * 受注タイプ(EC/TMALL)を取得する
   * 
   * @return the orderType
   */
  public String getOrderType() {
    return orderType;
  }

  /**
   * 受注タイプ(EC/TMALL)を設定する
   * 
   * @param orderType
   *          the orderType to set
   */
  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  /**
   * 検査フラグを取得する
   * 
   * @return the orderFlg
   */
  public String getOrderFlg() {
    return orderFlg;
  }

  /**
   * 検査フラグを設定する
   * 
   * @param orderFlg
   *          the orderFlg to set
   */
  public void setOrderFlg(String orderFlg) {
    this.orderFlg = orderFlg;
  }

  // soukai add 2012/01/05 ob end

  /**
   * @return the searchFromTmallTid
   */
  public String getSearchFromTmallTid() {
    return searchFromTmallTid;
  }

  /**
   * @param searchFromTmallTid
   *          the searchFromTmallTid to set
   */
  public void setSearchFromTmallTid(String searchFromTmallTid) {
    this.searchFromTmallTid = searchFromTmallTid;
  }
  
  /**
   * @return the searchFromJdDid
   */
  public String getSearchFromJdDid() {
    return searchFromJdDid;
  }

  /**
   * @param searchFromTmallTid
   *          the searchFromTmallTid to set
   */
  public void setSearchFromJdDid(String searchFromJdDid) {
    this.searchFromJdDid = searchFromJdDid;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  
  /**
   * @return the orderClientType
   */
  public String getOrderClientType() {
    return orderClientType;
  }

  
  /**
   * @param orderClientType the orderClientType to set
   */
  public void setOrderClientType(String orderClientType) {
    this.orderClientType = orderClientType;
  }

  
  /**
   * @return the childOrderNo
   */
  public String getChildOrderNo() {
    return childOrderNo;
  }

  
  /**
   * @param childOrderNo the childOrderNo to set
   */
  public void setChildOrderNo(String childOrderNo) {
    this.childOrderNo = childOrderNo;
  }

}
