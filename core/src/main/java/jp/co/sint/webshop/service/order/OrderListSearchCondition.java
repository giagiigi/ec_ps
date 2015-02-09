package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.ArrayUtil;

public class OrderListSearchCondition extends SearchCondition {

  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String customerCode;

  private String customerName;

  private String customerNameKana;

  private String email;

  private String tel;

  // Add by V10-CH start
  private String mobileTel;

  // Add by V10-CH end

  private String orderNoStart;

  private String orderNoEnd;

  private String orderDatetimeStart;

  private String orderDatetimeEnd;

  private String paymentDatetimeStart;

  private String paymentDatetimeEnd;

  private String[] orderStatus = new String[0];

  private String[] shippingStatusSummary = new String[0];

  private String[] returnStatusSummary = new String[0];

  private boolean searchPaymentLimitOver;

  private String paymentLimitDays;

  private boolean searchWithSentPaymentReminderMail;

  private String paymentMethod;

  private String paymentStatus;

  private boolean searchFixedSalesDataFlg;

  private boolean searchDataTransportFlg;

  private boolean siteAdmin;

  private boolean customer;

  private boolean customerCancelableFlg;

  // soukai add 2012/01/05 ob start
  // 受注タイプ(EC/TMALL)
  private String searchOrderType;
  
  private String searchOrderClientType;

  // 検査フラグ
  private String searchOrderFlg;

  private String searchLanguageCode;

  private boolean totalPriceFlg = false;

  // add by lc 2012-04-13 start
  private boolean searchUnpaymentFlg = false;

  // add by lc 2012-04-13 end
  private String contentFlg;
  
  private String confirmFlg;

  private String searchFromTmallTid;
  
  private String searchFromJdDid;

  // soukai add 2012/01/05 ob end

  // 判断来源是手机还是pC
  private String searchMobileComputerType;
  
  private Long limitNum;

  public String getSearchMobileComputerType() {
    return searchMobileComputerType;
  }

  public void setSearchMobileComputerType(String searchMobileComputerType) {
    this.searchMobileComputerType = searchMobileComputerType;
  }

  /**
   * customerCancelableFlgを返します。
   * 
   * @return the customerCancelableFlg
   */
  public boolean isCustomerCancelableFlg() {
    return customerCancelableFlg;
  }

  /**
   * customerCancelableFlgを設定します。
   * 
   * @param customerCancelableFlg
   *          設定する customerCancelableFlg
   */
  public void setCustomerCancelableFlg(boolean customerCancelableFlg) {
    this.customerCancelableFlg = customerCancelableFlg;
  }

  public OrderListSearchCondition() {
    setSiteAdmin(false);
    setCustomer(false);
  }

  /** ソート順 */
  private String searchListSort;

  /**
   * customerNameを取得します。
   * 
   * @return customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * customerNameKanaを取得します。
   * 
   * @return customerNameKana
   */
  public String getCustomerNameKana() {
    return customerNameKana;
  }

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * orderCodeEndを取得します。
   * 
   * @return orderNoEnd
   */
  public String getOrderNoEnd() {
    return orderNoEnd;
  }

  /**
   * orderCodeStartを取得します。
   * 
   * @return orderNoStart
   */
  public String getOrderNoStart() {
    return orderNoStart;
  }

  /**
   * orderDatetimeEndを取得します。
   * 
   * @return orderDatetimeEnd
   */
  public String getOrderDatetimeEnd() {
    return orderDatetimeEnd;
  }

  /**
   * orderDatetimeStartを取得します。
   * 
   * @return orderDatetimeStart
   */
  public String getOrderDatetimeStart() {
    return orderDatetimeStart;
  }

  /**
   * orderStatusを取得します。
   * 
   * @return orderStatus
   */
  public String[] getOrderStatus() {
    return ArrayUtil.immutableCopy(orderStatus);
  }

  /**
   * paymentDatetimeEndを取得します。
   * 
   * @return paymentDatetimeEnd
   */
  public String getPaymentDatetimeEnd() {
    return paymentDatetimeEnd;
  }

  /**
   * paymentDatetimeStartを取得します。
   * 
   * @return paymentDatetimeStart
   */
  public String getPaymentDatetimeStart() {
    return paymentDatetimeStart;
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
   * paymentStatusを取得します。
   * 
   * @return paymentStatus
   */
  public String getPaymentStatus() {
    return paymentStatus;
  }

  /**
   * shippingStatusSummaryを取得します。
   * 
   * @return shippingStatusSummary
   */
  public String[] getShippingStatusSummary() {
    return ArrayUtil.immutableCopy(shippingStatusSummary);
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
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
   *          設定する customerName
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * customerNameKanaを設定します。
   * 
   * @param customerNameKana
   *          設定する customerNameKana
   */
  public void setCustomerNameKana(String customerNameKana) {
    this.customerNameKana = customerNameKana;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          設定する email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * orderCodeEndを設定します。
   * 
   * @param orderCodeEnd
   *          設定する orderCodeEnd
   */
  public void setOrderNoEnd(String orderCodeEnd) {
    this.orderNoEnd = orderCodeEnd;
  }

  /**
   * orderCodeStartを設定します。
   * 
   * @param orderCodeStart
   *          設定する orderCodeStart
   */
  public void setOrderNoStart(String orderCodeStart) {
    this.orderNoStart = orderCodeStart;
  }

  /**
   * orderDatetimeEndを設定します。
   * 
   * @param orderDatetimeEnd
   *          設定する orderDatetimeEnd
   */
  public void setOrderDatetimeEnd(String orderDatetimeEnd) {
    this.orderDatetimeEnd = orderDatetimeEnd;
  }

  /**
   * orderDatetimeStartを設定します。
   * 
   * @param orderDatetimeStart
   *          設定する orderDatetimeStart
   */
  public void setOrderDatetimeStart(String orderDatetimeStart) {
    this.orderDatetimeStart = orderDatetimeStart;
  }

  /**
   * orderStatusを設定します。
   * 
   * @param orderStatus
   *          設定する orderStatus
   */
  public void setOrderStatus(String... orderStatus) {
    this.orderStatus = ArrayUtil.immutableCopy(orderStatus);
  }

  /**
   * paymentDatetimeEndを設定します。
   * 
   * @param paymentDatetimeEnd
   *          設定する paymentDatetimeEnd
   */
  public void setPaymentDatetimeEnd(String paymentDatetimeEnd) {
    this.paymentDatetimeEnd = paymentDatetimeEnd;
  }

  /**
   * paymentDatetimeStartを設定します。
   * 
   * @param paymentDatetimeStart
   *          設定する paymentDatetimeStart
   */
  public void setPaymentDatetimeStart(String paymentDatetimeStart) {
    this.paymentDatetimeStart = paymentDatetimeStart;
  }

  /**
   * paymentMethodを設定します。
   * 
   * @param paymentMethod
   *          設定する paymentMethod
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /**
   * paymentStatusを設定します。
   * 
   * @param paymentStatus
   *          設定する paymentStatus
   */
  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  /**
   * shippingStatusSummaryを設定します。
   * 
   * @param shippingStatusSummary
   *          設定する shippingStatusSummary
   */
  public void setShippingStatusSummary(String... shippingStatusSummary) {
    this.shippingStatusSummary = ArrayUtil.immutableCopy(shippingStatusSummary);
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * telを設定します。
   * 
   * @param tel
   *          設定する tel
   */
  public void setTel(String tel) {
    this.tel = tel;
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
   *          設定する customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * searchListSortを取得します。
   * 
   * @return searchListSort
   */
  public String getSearchListSort() {
    return searchListSort;
  }

  /**
   * searchListSortを設定します。
   * 
   * @param searchListSort
   *          設定する searchListSort
   */
  public void setSearchListSort(String searchListSort) {
    this.searchListSort = searchListSort;
  }

  public boolean isSiteAdmin() {
    return siteAdmin;
  }

  public void setSiteAdmin(boolean siteAdmin) {
    this.siteAdmin = siteAdmin;
  }

  /**
   * customerを返します。
   * 
   * @return the customer
   */
  public boolean isCustomer() {
    return customer;
  }

  /**
   * customerを設定します。
   * 
   * @param customer
   *          設定する customer
   */
  public void setCustomer(boolean customer) {
    this.customer = customer;
  }

  /**
   * @return the searchFixedSalesDataFlg
   */
  public boolean isSearchFixedSalesDataFlg() {
    return searchFixedSalesDataFlg;
  }

  /**
   * @param searchFixedSalesDataFlg
   *          the searchFixedSalesDataFlg to set
   */
  public void setSearchFixedSalesDataFlg(boolean searchFixedSalesDataFlg) {
    this.searchFixedSalesDataFlg = searchFixedSalesDataFlg;
  }

  /**
   * @return the searchDataTransportFlg
   */
  public boolean isSearchDataTransportFlg() {
    return searchDataTransportFlg;
  }

  /**
   * @param searchDataTransportFlg
   *          the searchDataTransportFlg to set
   */
  public void setSearchDataTransportFlg(boolean searchDataTransportFlg) {
    this.searchDataTransportFlg = searchDataTransportFlg;
  }

  /**
   * @return the returnStatusSummary
   */
  public String[] getReturnStatusSummary() {
    return ArrayUtil.immutableCopy(returnStatusSummary);
  }

  /**
   * @param returnStatusSummary
   *          the returnStatusSummary to set
   */
  public void setReturnStatusSummary(String[] returnStatusSummary) {
    this.returnStatusSummary = ArrayUtil.immutableCopy(returnStatusSummary);
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
   * searchWithSentPaymentReminderMailを取得します。
   * 
   * @return searchWithSentPaymentReminderMail
   */
  public boolean isSearchWithSentPaymentReminderMail() {
    return searchWithSentPaymentReminderMail;
  }

  /**
   * searchWithSentPaymentReminderMailを設定します。
   * 
   * @param searchWithSentPaymentReminderMail
   *          searchWithSentPaymentReminderMail
   */
  public void setSearchWithSentPaymentReminderMail(boolean searchWithSentPaymentReminderMail) {
    this.searchWithSentPaymentReminderMail = searchWithSentPaymentReminderMail;
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

  // soukai add 2012/01/05 ob start
  /**
   * 受注タイプ(EC/TMALL)を取得する
   * 
   * @return the searchOrderType
   */
  public String getSearchOrderType() {
    return searchOrderType;
  }

  /**
   * 受注タイプ(EC/TMALL)を設定する
   * 
   * @param searchOrderType
   *          the searchOrderType to set
   */
  public void setSearchOrderType(String searchOrderType) {
    this.searchOrderType = searchOrderType;
  }

  /**
   * 検査フラグを取得する
   * 
   * @return the searchOrderFlg
   */
  public String getSearchOrderFlg() {
    return searchOrderFlg;
  }

  /**
   * 検査フラグを設定する
   * 
   * @param searchOrderFlg
   *          the searchOrderFlg to set
   */
  public void setSearchOrderFlg(String searchOrderFlg) {
    this.searchOrderFlg = searchOrderFlg;
  }

  // soukai add 2012/01/05 ob end

  /**
   * @return the totalPriceFlg
   */
  public boolean isTotalPriceFlg() {
    return totalPriceFlg;
  }

  /**
   * @param totalPriceFlg
   *          the totalPriceFlg to set
   */
  public void setTotalPriceFlg(boolean totalPriceFlg) {
    this.totalPriceFlg = totalPriceFlg;
  }

  /**
   * @return the contentFlg
   */
  public String getContentFlg() {
    return contentFlg;
  }

  /**
   * @param contentFlg
   *          the contentFlg to set
   */
  public void setContentFlg(String contentFlg) {
    this.contentFlg = contentFlg;
  }

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
   * @param searchFromJdDid
   *          the searchFromJdDid to set
   */
  public void setSearchFromJdDid(String searchFromJdDid) {
    this.searchFromJdDid = searchFromJdDid;
  }

  /**
   * @return the searchUnpaymentFlg
   */
  public boolean isSearchUnpaymentFlg() {
    return searchUnpaymentFlg;
  }

  /**
   * @param searchUnpaymentFlg
   *          the searchUnpaymentFlg to set
   */
  public void setSearchUnpaymentFlg(boolean searchUnpaymentFlg) {
    this.searchUnpaymentFlg = searchUnpaymentFlg;
  }

  public String getSearchLanguageCode() {
    return searchLanguageCode;
  }

  public void setSearchLanguageCode(String searchLanguageCode) {
    this.searchLanguageCode = searchLanguageCode;
  }

  
  /**
   * @return the confirmFlg
   */
  public String getConfirmFlg() {
    return confirmFlg;
  }

  
  /**
   * @param confirmFlg the confirmFlg to set
   */
  public void setConfirmFlg(String confirmFlg) {
    this.confirmFlg = confirmFlg;
  }

  
  /**
   * @return the searchOrderClientType
   */
  public String getSearchOrderClientType() {
    return searchOrderClientType;
  }

  
  /**
   * @param searchOrderClientType the searchOrderClientType to set
   */
  public void setSearchOrderClientType(String searchOrderClientType) {
    this.searchOrderClientType = searchOrderClientType;
  }

  
  /**
   * @return the limitNum
   */
  public Long getLimitNum() {
    return limitNum;
  }

  
  /**
   * @param limitNum the limitNum to set
   */
  public void setLimitNum(Long limitNum) {
    this.limitNum = limitNum;
  }

}
