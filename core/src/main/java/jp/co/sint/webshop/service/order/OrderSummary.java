package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class OrderSummary implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 受注番号 */
  private String orderNo;

  /** 入金ステータス */
  private Long paymentStatus;

  /** 売上確定ステータス */
  private Long fixedSalesStatus;

  /** 出荷ステータス集計 */
  private Long shippingStatusSummary;

  /** 返品ステータス集計 */
  private Long returnStatusSummary;

  /** 送料手数料 */
  private BigDecimal shippingCharge;

  /** 送料手数料税額 */
  private BigDecimal shippingChargeTax;

  /** 返品金額 */
  private BigDecimal returnItemLossMoney;

  /** 商品金額 */
  private BigDecimal retailPrice;

  /** 商品金額税額 */
  private BigDecimal retailTax;

  /** ギフト価格 */
  private BigDecimal giftPrice;

  /** ギフト価格税額 */
  private BigDecimal giftTax;

  /** 合計金額 */
  private BigDecimal totalAmount;
  
  private BigDecimal outerCardUsePrice;

  /** 合計金額税額 */
  private BigDecimal taxAmount;

  /***/
  private BigDecimal acquiredPoint;

  /** 使用ポイント */
  private BigDecimal usedPoint;

  /** ショップコード */
  private String shopCode;

  /** 受注日時 */
  private Date orderDatetime;

  /** 顧客コード */
  private String customerCode;

  /** ゲストフラグ */
  private String guestFlg;

  /** 姓 */
  private String lastName;

  /** 名 */
  private String firstName;

  /** 姓カナ */
  private String lastNameKana;

  /** 名カナ */
  private String firstNameKana;

  /** メールアドレス */
  private String email;

  /** 郵便番号 */
  private String postalCode;

  /** 都道府県コード */
  private String prefectureCode;

  /** 住所1 */
  private String address1;

  /** 住所2 */
  private String address2;

  /** 住所3 */
  private String address3;

  /** 住所4 */
  private String address4;

  /** 電話番号 */
  private String phoneNumber;

  /** 手机号码 */
  private String mobileNumber;

  /** 先後払フラグ */
  private Long advanceLaterFlg;

  /** 支払方法番号 */
  private Long paymentMethodNo;

  /** 支払方法区分 */
  private String paymentMethodType;

  /** 支払方法名称 */
  private String paymentMethodName;

  /** 支払手数料 */
  private BigDecimal paymentCommission;

  /** 支払手数料消費税率 */
  private Long paymentCommissionTaxRate;

  /** 支払手数料消費税額 */
  private BigDecimal paymentCommissionTax;

  /** 支払手数料消費税区分 */
  private Long paymentCommissionTaxType;

  /** 入金日 */
  private Date paymentDate;

  /** 支払期限日 */
  private Date paymentLimitDate;

  /** 顧客グループコード */
  private String customerGroupCode;

  /** データ連携ステータス */
  private Long dataTransportStatus;

  /** 受注ステータス */
  private Long orderStatus;

  /** クライアントグループ */
  private String clientGroup;

  /** 注意事項（管理側のみ参照） */
  private String caution;

  /** 連絡事項 */
  private String message;

  /** 決済サービス取引ID */
  private Long paymentOrderId;

  /** コンビニコード */
  private String cvsCode;

  /** 承認番号 */
  private String paymentReceiptNo;

  /** 払込URL */
  private String paymentReceiptUrl;

  /** 電子マネー区分 */
  private Long digitalCashType;

  /** 警告メッセージ */
  private String warningMessage;

  /** データ行ID */
  private Long ormRowid;

  /** 作成ユーザ */
  private String createdUser;

  /** 作成日時 */
  private Date createdDatetime;

  /** 更新ユーザ */
  private String updatedUser;

  /** 更新日時 */
  private Date updatedDatetime;

  private BigDecimal couponPrice;

  // soukai add 2012/01/05 ob start
  // 受注タイプ
  private String orderType;

  // 検査フラグ
  private String orderFlg;

  // soukai add 2012/01/05 ob end

  // 20120120 ysy add start
  private BigDecimal discountPrice;

  // 20120120 ysy add end

  private String tmallBuyerMessage;

  private String tmallTid;
  /**
   * acquiredPointを取得します。
   * 
   * @return the acquiredPoint
   */
  public BigDecimal getAcquiredPoint() {
    return acquiredPoint;
  }
  
  /**
 * @return the discountPrice
 */
public BigDecimal getDiscountPrice() {
	return discountPrice;
}

/**
 * @param discountPrice the discountPrice to set
 */
public void setDiscountPrice(BigDecimal discountPrice) {
	this.discountPrice = discountPrice;
}

/**
   * acquiredPointを設定します。
   * 
   * @param acquiredPoint
   *          the acquiredPoint to set
   */
  public void setAcquiredPoint(BigDecimal acquiredPoint) {
    this.acquiredPoint = acquiredPoint;
  }

  /**
   * giftPriceを取得します。
   * 
   * @return the giftPrice
   */
  public BigDecimal getGiftPrice() {
    return giftPrice;
  }

  /**
   * giftPriceを設定します。
   * 
   * @param giftPrice
   *          the giftPrice to set
   */
  public void setGiftPrice(BigDecimal giftPrice) {
    this.giftPrice = giftPrice;
  }

  /**
   * giftTaxを取得します。
   * 
   * @return the giftTax
   */
  public BigDecimal getGiftTax() {
    return giftTax;
  }

  /**
   * giftTaxを設定します。
   * 
   * @param giftTax
   *          the giftTax to set
   */
  public void setGiftTax(BigDecimal giftTax) {
    this.giftTax = giftTax;
  }

  /**
   * orderNoを取得します。
   * 
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * retailPriceを取得します。
   * 
   * @return the retailPrice
   */
  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  /**
   * retailPriceを設定します。
   * 
   * @param retailPrice
   *          the retailPrice to set
   */
  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  /**
   * retailTaxを取得します。
   * 
   * @return the retailTax
   */
  public BigDecimal getRetailTax() {
    return retailTax;
  }

  /**
   * retailTaxを設定します。
   * 
   * @param retailTax
   *          the retailTax to set
   */
  public void setRetailTax(BigDecimal retailTax) {
    this.retailTax = retailTax;
  }

  /**
   * returnItemLossMoneyを取得します。
   * 
   * @return the returnItemLossMoney
   */
  public BigDecimal getReturnItemLossMoney() {
    return returnItemLossMoney;
  }

  /**
   * returnItemLossMoneyを設定します。
   * 
   * @param returnItemLossMoney
   *          the returnItemLossMoney to set
   */
  public void setReturnItemLossMoney(BigDecimal returnItemLossMoney) {
    this.returnItemLossMoney = returnItemLossMoney;
  }

  /**
   * shippingChargeを取得します。
   * 
   * @return the shippingCharge
   */
  public BigDecimal getShippingCharge() {
    return shippingCharge;
  }

  /**
   * shippingChargeを設定します。
   * 
   * @param shippingCharge
   *          the shippingCharge to set
   */
  public void setShippingCharge(BigDecimal shippingCharge) {
    this.shippingCharge = shippingCharge;
  }

  /**
   * shippingChargeTaxを取得します。
   * 
   * @return the shippingChargeTax
   */
  public BigDecimal getShippingChargeTax() {
    return shippingChargeTax;
  }

  /**
   * shippingChargeTaxを設定します。
   * 
   * @param shippingChargeTax
   *          the shippingChargeTax to set
   */
  public void setShippingChargeTax(BigDecimal shippingChargeTax) {
    this.shippingChargeTax = shippingChargeTax;
  }

  /**
   * taxAmountを取得します。
   * 
   * @return the taxAmount
   */
  public BigDecimal getTaxAmount() {
    return taxAmount;
  }

  /**
   * taxAmountを設定します。
   * 
   * @param taxAmount
   *          the taxAmount to set
   */
  public void setTaxAmount(BigDecimal taxAmount) {
    this.taxAmount = taxAmount;
  }

  /**
   * totalAmountを取得します。
   * 
   * @return the totalAmount
   */
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  /**
   * totalAmountを設定します。
   * 
   * @param totalAmount
   *          the totalAmount to set
   */
  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  /**
   * usedPointを取得します。
   * 
   * @return the usedPoint
   */
  public BigDecimal getUsedPoint() {
    return usedPoint;
  }

  /**
   * usedPointを設定します。
   * 
   * @param usedPoint
   *          the usedPoint to set
   */
  public void setUsedPoint(BigDecimal usedPoint) {
    this.usedPoint = usedPoint;
  }

  /**
   * @return the shippingStatusSummary
   */
  public Long getShippingStatusSummary() {
    return shippingStatusSummary;
  }

  /**
   * @param shippingStatusSummary
   *          the shippingStatusSummary to set
   */
  public void setShippingStatusSummary(Long shippingStatusSummary) {
    this.shippingStatusSummary = shippingStatusSummary;
  }

  /**
   * @return the fixedSalesStatus
   */
  public Long getFixedSalesStatus() {
    return fixedSalesStatus;
  }

  /**
   * @param fixedSalesStatus
   *          the fixedSalesStatus to set
   */
  public void setFixedSalesStatus(Long fixedSalesStatus) {
    this.fixedSalesStatus = fixedSalesStatus;
  }

  /**
   * @return the paymentStatus
   */
  public Long getPaymentStatus() {
    return paymentStatus;
  }

  /**
   * @param paymentStatus
   *          the paymentStatus to set
   */
  public void setPaymentStatus(Long paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  /**
   * @return the returnStatusSummary
   */
  public Long getReturnStatusSummary() {
    return returnStatusSummary;
  }

  /**
   * @param returnStatusSummary
   *          the returnStatusSummary to set
   */
  public void setReturnStatusSummary(Long returnStatusSummary) {
    this.returnStatusSummary = returnStatusSummary;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * @param address1
   *          the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * @param address2
   *          the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * @return the address3
   */
  public String getAddress3() {
    return address3;
  }

  /**
   * @param address3
   *          the address3 to set
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  /**
   * @return the address4
   */
  public String getAddress4() {
    return address4;
  }

  /**
   * @param address4
   *          the address4 to set
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  /**
   * @return the advanceLaterFlg
   */
  public Long getAdvanceLaterFlg() {
    return advanceLaterFlg;
  }

  /**
   * @param advanceLaterFlg
   *          the advanceLaterFlg to set
   */
  public void setAdvanceLaterFlg(Long advanceLaterFlg) {
    this.advanceLaterFlg = advanceLaterFlg;
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

  /**
   * @return the clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * @param clientGroup
   *          the clientGroup to set
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(createdDatetime);
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = DateUtil.immutableCopy(createdDatetime);
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
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
   * @return the cvsCode
   */
  public String getCvsCode() {
    return cvsCode;
  }

  /**
   * @param cvsCode
   *          the cvsCode to set
   */
  public void setCvsCode(String cvsCode) {
    this.cvsCode = cvsCode;
  }

  /**
   * @return the dataTransportStatus
   */
  public Long getDataTransportStatus() {
    return dataTransportStatus;
  }

  /**
   * @param dataTransportStatus
   *          the dataTransportStatus to set
   */
  public void setDataTransportStatus(Long dataTransportStatus) {
    this.dataTransportStatus = dataTransportStatus;
  }

  /**
   * @return the digitalCashType
   */
  public Long getDigitalCashType() {
    return digitalCashType;
  }

  /**
   * @param digitalCashType
   *          the digitalCashType to set
   */
  public void setDigitalCashType(Long digitalCashType) {
    this.digitalCashType = digitalCashType;
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
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the firstNameKana
   */
  public String getFirstNameKana() {
    return firstNameKana;
  }

  /**
   * @param firstNameKana
   *          the firstNameKana to set
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the lastNameKana
   */
  public String getLastNameKana() {
    return lastNameKana;
  }

  /**
   * @param lastNameKana
   *          the lastNameKana to set
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the orderDatetime
   */
  public Date getOrderDatetime() {
    return DateUtil.immutableCopy(orderDatetime);
  }

  /**
   * @param orderDatetime
   *          the orderDatetime to set
   */
  public void setOrderDatetime(Date orderDatetime) {
    this.orderDatetime = DateUtil.immutableCopy(orderDatetime);
  }

  /**
   * @return the orderStatus
   */
  public Long getOrderStatus() {
    return orderStatus;
  }

  /**
   * @param orderStatus
   *          the orderStatus to set
   */
  public void setOrderStatus(Long orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the paymentCommission
   */
  public BigDecimal getPaymentCommission() {
    return paymentCommission;
  }

  /**
   * @param paymentCommission
   *          the paymentCommission to set
   */
  public void setPaymentCommission(BigDecimal paymentCommission) {
    this.paymentCommission = paymentCommission;
  }

  /**
   * @return the paymentCommissionTax
   */
  public BigDecimal getPaymentCommissionTax() {
    return paymentCommissionTax;
  }

  /**
   * @param paymentCommissionTax
   *          the paymentCommissionTax to set
   */
  public void setPaymentCommissionTax(BigDecimal paymentCommissionTax) {
    this.paymentCommissionTax = paymentCommissionTax;
  }

  /**
   * @return the paymentCommissionTaxRate
   */
  public Long getPaymentCommissionTaxRate() {
    return paymentCommissionTaxRate;
  }

  /**
   * @param paymentCommissionTaxRate
   *          the paymentCommissionTaxRate to set
   */
  public void setPaymentCommissionTaxRate(Long paymentCommissionTaxRate) {
    this.paymentCommissionTaxRate = paymentCommissionTaxRate;
  }

  /**
   * @return the paymentCommissionTaxType
   */
  public Long getPaymentCommissionTaxType() {
    return paymentCommissionTaxType;
  }

  /**
   * @param paymentCommissionTaxType
   *          the paymentCommissionTaxType to set
   */
  public void setPaymentCommissionTaxType(Long paymentCommissionTaxType) {
    this.paymentCommissionTaxType = paymentCommissionTaxType;
  }

  /**
   * @return the paymentDate
   */
  public Date getPaymentDate() {
    return DateUtil.immutableCopy(paymentDate);
  }

  /**
   * @param paymentDate
   *          the paymentDate to set
   */
  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = DateUtil.immutableCopy(paymentDate);
  }

  /**
   * @return the paymentLimitDate
   */
  public Date getPaymentLimitDate() {
    return DateUtil.immutableCopy(paymentLimitDate);
  }

  /**
   * @param paymentLimitDate
   *          the paymentLimitDate to set
   */
  public void setPaymentLimitDate(Date paymentLimitDate) {
    this.paymentLimitDate = DateUtil.immutableCopy(paymentLimitDate);
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
   * @return the paymentMethodNo
   */
  public Long getPaymentMethodNo() {
    return paymentMethodNo;
  }

  /**
   * @param paymentMethodNo
   *          the paymentMethodNo to set
   */
  public void setPaymentMethodNo(Long paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  /**
   * @return the paymentMethodType
   */
  public String getPaymentMethodType() {
    return paymentMethodType;
  }

  /**
   * @param paymentMethodType
   *          the paymentMethodType to set
   */
  public void setPaymentMethodType(String paymentMethodType) {
    this.paymentMethodType = paymentMethodType;
  }

  /**
   * @return the paymentOrderId
   */
  public Long getPaymentOrderId() {
    return paymentOrderId;
  }

  /**
   * @param paymentOrderId
   *          the paymentOrderId to set
   */
  public void setPaymentOrderId(Long paymentOrderId) {
    this.paymentOrderId = paymentOrderId;
  }

  /**
   * @return the paymentReceiptNo
   */
  public String getPaymentReceiptNo() {
    return paymentReceiptNo;
  }

  /**
   * @param paymentReceiptNo
   *          the paymentReceiptNo to set
   */
  public void setPaymentReceiptNo(String paymentReceiptNo) {
    this.paymentReceiptNo = paymentReceiptNo;
  }

  /**
   * @return the paymentReceiptUrl
   */
  public String getPaymentReceiptUrl() {
    return paymentReceiptUrl;
  }

  /**
   * @param paymentReceiptUrl
   *          the paymentReceiptUrl to set
   */
  public void setPaymentReceiptUrl(String paymentReceiptUrl) {
    this.paymentReceiptUrl = paymentReceiptUrl;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber
   *          the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @param postalCode
   *          the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * @param prefectureCode
   *          the prefectureCode to set
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return the warningMessage
   */
  public String getWarningMessage() {
    return warningMessage;
  }

  /**
   * @param warningMessage
   *          the warningMessage to set
   */
  public void setWarningMessage(String warningMessage) {
    this.warningMessage = warningMessage;
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

  public BigDecimal getCouponPrice() {
    return couponPrice;
  }

  public void setCouponPrice(BigDecimal couponPrice) {
    this.couponPrice = couponPrice;
  }

  // soukai add 2012/01/05 ob start
  /**
   * @return the orderType
   */
  public String getOrderType() {
    return orderType;
  }

  /**
   * @param orderType
   *          the orderType to set
   */
  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  /**
   * @return the orderFlg
   */
  public String getOrderFlg() {
    return orderFlg;
  }

  /**
   * @param orderFlg
   *          the orderFlg to set
   */
  public void setOrderFlg(String orderFlg) {
    this.orderFlg = orderFlg;
  }
  // soukai add 2012/01/05 ob end

  
  /**
   * @return the tmallBuyerMessage
   */
  public String getTmallBuyerMessage() {
    return tmallBuyerMessage;
  }

  
  /**
   * @param tmallBuyerMessage the tmallBuyerMessage to set
   */
  public void setTmallBuyerMessage(String tmallBuyerMessage) {
    this.tmallBuyerMessage = tmallBuyerMessage;
  }

  
  /**
   * @return the tmallTid
   */
  public String getTmallTid() {
    return tmallTid;
  }

  
  /**
   * @param tmallTid the tmallTid to set
   */
  public void setTmallTid(String tmallTid) {
    this.tmallTid = tmallTid;
  }

  
  /**
   * @return the outerCardUsePrice
   */
  public BigDecimal getOuterCardUsePrice() {
    return outerCardUsePrice;
  }

  
  /**
   * @param outerCardUsePrice the outerCardUsePrice to set
   */
  public void setOuterCardUsePrice(BigDecimal outerCardUsePrice) {
    this.outerCardUsePrice = outerCardUsePrice;
  }
}
