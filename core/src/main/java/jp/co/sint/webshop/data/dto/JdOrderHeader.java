//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto; 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date; 
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity; 
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata; 
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/** 
 * 「タグ(JdCouponDetail)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class JdOrderHeader implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 订单编号 */
  @PrimaryKey(1)
  @Required 
  @Length(16)
  @Metadata(name = "订单编号", order = 1)
  private String orderNo; 
  
  /** 店铺编号 */
  @Required 
  @Length(16)
  @Metadata(name = "店铺编号", order = 2)
  private String shopCode; 
  
  /** 订单作成时间 */
  @Required 
  @Metadata(name = "订单作成时间", order = 3)
  private Date orderDatetime; 
  
  /** 京东订单号 */
  @Required 
  @Length(16)
  @Metadata(name = "京东订单号", order = 4)
  private String customerCode; 
  
  /** 访客区分 */
  @Required 
  @Length(1)
  @Metadata(name = "访客区分", order = 5)
  private Long guestFlg; 
  
  /** 收货人姓名 */
  @Required 
  @Length(20)
  @Metadata(name = "收货人姓名", order = 6)
  private String lastName; 
  
  /** first_name */
  @Required 
  @Length(20)
  @Metadata(name = "firstName", order = 7)
  private String firstName; 
  
  /** last_name_kana */
  @Required 
  @Length(40)
  @Metadata(name = "lastNameKana", order = 8)
  private String lastNameKana; 
  
  /** first_name_kana */
  @Required 
  @Length(40)
  @Metadata(name = "firstNameKana", order = 9)
  private String firstNameKana; 
  
  /** 收货人邮箱 */
  @Required 
  @Length(256)
  @Metadata(name = "收货人邮箱", order = 10)
  private String email; 
  
  /** 邮政编码 */
  @Required 
  @Length(7)
  @Metadata(name = "邮政编码", order = 11)
  private String postalCode; 
  
  /** 省份编号 */
  @Required 
  @Length(2)
  @Metadata(name = "省份编号", order = 12)
  private String prefectureCode; 
  
  /** 省份名 */
  @Required 
  @Length(50)
  @Metadata(name = "省份名", order = 13)
  private String address1; 
  
  /** 城市名 */
  @Required
  @Length(50)
  @Metadata(name = "城市名", order = 14)
  private String address2; 
  
  /** 区县名 */
  @Length(50)
  @Metadata(name = "区县名", order = 15)
  private String address3; 
  
  /** 收货人填写详细地址 */
  @Required
  @Length(100)
  @Metadata(name = "收货人填写详细地址", order = 16)
  private String address4; 
  
  /** 收货人固定电话 */
  @Required
  @Length(24)
  @Metadata(name = "收货人固定电话", order = 17)
  private String phoneNumber; 
  
  /** advance_later_flg */
  @Required
  @Length(1)
  @Metadata(name = "advanceLaterFlg", order = 18)
  private Long advanceLaterFlg; 
  
  /** 支付方式编号 */
  @Required
  @Length(8)
  @Metadata(name = "支付方式编号", order = 19)
  private Long paymentMethodNo; 
  
  /** 支付方式区分 */
  @Required
  @Length(2)
  @Metadata(name = "支付方式区分", order = 20)
  private String paymentMethodType; 
  
  /** 支付方式名称（京东在线支付） */
  @Length(25)
  @Metadata(name = "支付方式名称（京东在线支付）", order = 21)
  private String paymentMethodName; 
  
  /** payment_commission */
  @Required
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "paymentCommission", order = 22)
  private BigDecimal paymentCommission; 
  
  /** payment_commission_tax_rate */
  @Length(3)
  @Metadata(name = "paymentCommissionTaxRate", order = 23)
  private Long paymentCommissionTaxRate; 
  
  /** payment_commission_tax */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "paymentCommissionTax", order = 24)
  private BigDecimal paymentCommissionTax; 
  
  /** payment_commission_tax_type */
  @Required
  @Length(1)
  @Metadata(name = "paymentCommissionTaxType", order = 25)
  private Long paymentCommissionTaxType; 
  
  /** 使用京豆（100京豆=1块钱） */
  @Precision(precision = 14, scale = 2)
  @Metadata(name = "使用京豆（100京豆=1块钱）", order = 26)
  private BigDecimal usedPoint; 
  
  /** 付款日期 */
  @Metadata(name = "付款日期", order = 27)
  private Date paymentDate; 
  
  /** 支付期限日 */
  @Metadata(name = "支付期限日", order = 28)
  private Date paymentLimitDate; 
  
  /** 支付状态（0为支付 1已支付） */
  @Required
  @Length(1)
  @Metadata(name = "支付状态（0为支付 1已支付）", order = 29)
  private Long paymentStatus; 
  
  /** 顾客组编号 */
  @Length(16)
  @Metadata(name = "顾客组编号", order = 30)
  private String customerGroupCode; 
  
  /** 付款数据导出标志（0是未导出 1已导出） */
  @Required
  @Length(1)
  @Metadata(name = "付款数据导出标志（0是未导出 1已导出）", order = 31)
  private Long dataTransportStatus; 
  
  /** 订单状态（1为正常订单 2为取消） */
  @Required
  @Length(1)
  @Metadata(name = "订单状态（1为正常订单 2为取消）", order = 32)
  private Long orderStatus; 
  
  /** client_group */
  @Required
  @Length(2)
  @Metadata(name = "clientGroup", order = 33)
  private String clientGroup; 
  
  /** 买家留言 */
  @Length(200)
  @Metadata(name = "买家留言", order = 34)
  private String caution; 
  
  /** message */
  @Length(200)
  @Metadata(name = "message", order = 35)
  private String message; 
  
  /** 交易流水号 */
  @Length(38)
  @Metadata(name = "交易流水号", order = 36)
  private Long paymentOrderId; 
  
  /** cvs_code */
  @Length(2)
  @Metadata(name = "cvsCode", order = 37)
  private String cvsCode; 
  
  /** payment_receipt_no */
  @Length(50)
  @Metadata(name = "paymentReceiptNo", order = 38)
  private String paymentReceiptNo; 
  
  /** payment_receipt_url */
  @Length(500)
  @Metadata(name = "paymentReceiptUrl", order = 39)
  private String paymentReceiptUrl; 
  
  /** digital_cash_type */
  @Length(2)
  @Metadata(name = "digitalCashType", order = 40)
  private String digitalCashType; 
  
  /** warning_message */
  @Length(100)
  @Metadata(name = "warningMessage", order = 41)
  private String warningMessage; 
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 42)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 43)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 44)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 45)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 46)
  private Date updatedDatetime;
  
  /** 收货人城市编号 */
  @Required
  @Length(10)
  @Metadata(name = "收货人城市编号", order = 47)
  private String cityCode; 
  
  /** 收货人手机号码 */
  @Length(11)
  @Metadata(name = "收货人手机号码", order = 48)
  private String mobileNumber; 
  
  /** coupon_price */
  @Required
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "couponPrice", order = 49)
  private BigDecimal couponPrice; 
  
  /** 订单总金额 */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "订单总金额", order = 50)
  private BigDecimal paidPrice; 
  
  /** 发票领取标志（0未领取或1领取） */
  @Required
  @Length(1)
  @Metadata(name = "发票领取标志（0未领取或1领取）", order = 51)
  private Long invoiceFlg; 
  
  /** discount_type */
  @Length(1)
  @Metadata(name = "discountType", order = 52)
  private Long discountType; 
  
  /** discount_mode */
  @Length(1)
  @Metadata(name = "discountMode", order = 53)
  private Long discountMode; 
  
  /** discount_rate */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "discountRate", order = 54)
  private BigDecimal discountRate; 
  
  /** 除去京豆以外的全部优惠金额 */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "除去京豆以外的全部优惠金额", order = 55)
  private BigDecimal discountPrice;
  
  /** discount_code */
  @Length(16)
  @Metadata(name = "discountCode", order = 56)
  private String discountCode;
  
  /** discount_name */
  @Length(50)
  @Metadata(name = "discountName", order = 57)
  private String discountName;
  
  /** discount_detail_code */
  @Length(20)
  @Metadata(name = "discountDetailCode", order = 58)
  private String discountDetailCode;
  
  /** 订单检查标志（0未检查 1已检查 2tmall订单拦截 3京东订单拦截） */
  @Required
  @Length(20)
  @Metadata(name = "订单检查标志（0未检查 1已检查 2tmall订单拦截 3京东订单拦截）", order = 59)
  private Long orderFlg;
  
  /** 结单时间 */
  @Metadata(name = "结单时间", order = 60)
  private Date jdEndTime;
  
  /** 买家留言 */
  @Length(200)
  @Metadata(name = "买家留言", order = 61)
  private String jdBuyerMessage;
  
  /** 交易状态 */
  @Length(50)
  @Metadata(name = "交易状态", order = 62)
  private String jdOrderStatus;
  
  /** 京东上订单更新时间 */
  @Metadata(name = "京东上订单更新时间", order = 63)
  private Date jdModifiedTime;
  
  /** 京东交易编号 */
  @Length(16)
  @Metadata(name = "京东交易编号", order = 64)
  private String jdOrderNo;
  
  /** 用户应付金额 */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "用户应付金额", order = 65)
  private BigDecimal orderPayment;
  
  /** 订单货款金额（订单总金额-商家优惠金额） */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "订单货款金额（订单总金额-商家优惠金额）", order = 66)
  private BigDecimal orderSellerPrice;
  
  /** 京东发票内容 */
  @Length(200)
  @Metadata(name = "京东发票内容", order = 67)
  private String jdInvoiceName;
  
  /** 区县编号 */
  @Length(10)
  @Metadata(name = "区县编号", order = 68)
  private String areaCode;

  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  
  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  
  /**
   * @return the orderDatetime
   */
  public Date getOrderDatetime() {
    return orderDatetime;
  }

  
  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  
  /**
   * @return the guestFlg
   */
  public Long getGuestFlg() {
    return guestFlg;
  }

  
  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  
  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  
  /**
   * @return the lastNameKana
   */
  public String getLastNameKana() {
    return lastNameKana;
  }

  
  /**
   * @return the firstNameKana
   */
  public String getFirstNameKana() {
    return firstNameKana;
  }

  
  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  
  /**
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  
  /**
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  
  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  
  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  
  /**
   * @return the address3
   */
  public String getAddress3() {
    return address3;
  }

  
  /**
   * @return the address4
   */
  public String getAddress4() {
    return address4;
  }

  
  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  
  /**
   * @return the advanceLaterFlg
   */
  public Long getAdvanceLaterFlg() {
    return advanceLaterFlg;
  }

  
  /**
   * @return the paymentMethodNo
   */
  public Long getPaymentMethodNo() {
    return paymentMethodNo;
  }

  
  /**
   * @return the paymentMethodType
   */
  public String getPaymentMethodType() {
    return paymentMethodType;
  }

  
  /**
   * @return the paymentMethodName
   */
  public String getPaymentMethodName() {
    return paymentMethodName;
  }

  
  /**
   * @return the paymentCommission
   */
  public BigDecimal getPaymentCommission() {
    return paymentCommission;
  }

  
  /**
   * @return the paymentCommissionTaxRate
   */
  public Long getPaymentCommissionTaxRate() {
    return paymentCommissionTaxRate;
  }

  
  /**
   * @return the paymentCommissionTax
   */
  public BigDecimal getPaymentCommissionTax() {
    return paymentCommissionTax;
  }

  
  /**
   * @return the paymentCommissionTaxType
   */
  public Long getPaymentCommissionTaxType() {
    return paymentCommissionTaxType;
  }

  
  /**
   * @return the usedPoint
   */
  public BigDecimal getUsedPoint() {
    return usedPoint;
  }

  
  /**
   * @return the paymentDate
   */
  public Date getPaymentDate() {
    return paymentDate;
  }

  
  /**
   * @return the paymentLimitDate
   */
  public Date getPaymentLimitDate() {
    return paymentLimitDate;
  }

  
  /**
   * @return the paymentStatus
   */
  public Long getPaymentStatus() {
    return paymentStatus;
  }

  
  /**
   * @return the customerGroupCode
   */
  public String getCustomerGroupCode() {
    return customerGroupCode;
  }

  
  /**
   * @return the dataTransportStatus
   */
  public Long getDataTransportStatus() {
    return dataTransportStatus;
  }

  
  /**
   * @return the orderStatus
   */
  public Long getOrderStatus() {
    return orderStatus;
  }

  
  /**
   * @return the clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  
  /**
   * @return the caution
   */
  public String getCaution() {
    return caution;
  }

  
  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  
  /**
   * @return the paymentOrderId
   */
  public Long getPaymentOrderId() {
    return paymentOrderId;
  }

  
  /**
   * @return the cvsCode
   */
  public String getCvsCode() {
    return cvsCode;
  }

  
  /**
   * @return the paymentReceipt_no
   */
  public String getPaymentReceiptNo() {
    return paymentReceiptNo;
  }

  
  /**
   * @return the paymentReceiptUrl
   */
  public String getPaymentReceiptUrl() {
    return paymentReceiptUrl;
  }

  
  /**
   * @return the digitalCashType
   */
  public String getDigitalCashType() {
    return digitalCashType;
  }

  
  /**
   * @return the warningMessage
   */
  public String getWarningMessage() {
    return warningMessage;
  }

  
  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  
  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  
  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  
  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  
  /**
   * @return the cityCode
   */
  public String getCityCode() {
    return cityCode;
  }

  
  /**
   * @return the mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * @return the couponPrice
   */
  public BigDecimal getCouponPrice() {
    return couponPrice;
  }

  
  /**
   * @return the paidPrice
   */
  public BigDecimal getPaidPrice() {
    return paidPrice;
  }

  
  /**
   * @return the invoiceFlg
   */
  public Long getInvoiceFlg() {
    return invoiceFlg;
  }

  
  /**
   * @return the discountType
   */
  public Long getDiscountType() {
    return discountType;
  }

  
  /**
   * @return the discountMode
   */
  public Long getDiscountMode() {
    return discountMode;
  }

  
  /**
   * @return the discountRate
   */
  public BigDecimal getDiscountRate() {
    return discountRate;
  }

  
  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  
  /**
   * @return the discountCode
   */
  public String getDiscountCode() {
    return discountCode;
  }

  
  /**
   * @return the discountName
   */
  public String getDiscountName() {
    return discountName;
  }

  
  /**
   * @return the discountDetailCode
   */
  public String getDiscountDetailCode() {
    return discountDetailCode;
  }

  
  /**
   * @return the orderFlg
   */
  public Long getOrderFlg() {
    return orderFlg;
  }

  
  /**
   * @return the jdEndTime
   */
  public Date getJdEndTime() {
    return jdEndTime;
  }

  
  /**
   * @return the jdBuyerMessage
   */
  public String getJdBuyerMessage() {
    return jdBuyerMessage;
  }

  
  /**
   * @return the jdOrderStatus
   */
  public String getJdOrderStatus() {
    return jdOrderStatus;
  }

  
  /**
   * @return the jdModifiedTime
   */
  public Date getJdModifiedTime() {
    return jdModifiedTime;
  }

  
  /**
   * @return the jdOrderNo
   */
  public String getJdOrderNo() {
    return jdOrderNo;
  }

  
  /**
   * @return the orderPayment
   */
  public BigDecimal getOrderPayment() {
    return orderPayment;
  }

  
  /**
   * @return the orderSellerPrice
   */
  public BigDecimal getOrderSellerPrice() {
    return orderSellerPrice;
  }

  
  /**
   * @return the jdInvoiceName
   */
  public String getJdInvoiceName() {
    return jdInvoiceName;
  }

  
  /**
   * @return the areaCode
   */
  public String getAreaCode() {
    return areaCode;
  }

  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  
  /**
   * @param shopCode the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  
  /**
   * @param orderDatetime the orderDatetime to set
   */
  public void setOrderDatetime(Date orderDatetime) {
    this.orderDatetime = orderDatetime;
  }

  
  /**
   * @param customerCode the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  
  /**
   * @param guestFlg the guestFlg to set
   */
  public void setGuestFlg(Long guestFlg) {
    this.guestFlg = guestFlg;
  }

  
  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  
  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  
  /**
   * @param lastNameKana the lastNameKana to set
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }

  
  /**
   * @param firstNameKana the firstNameKana to set
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
  }

  
  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   * @param postalCode the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  
  /**
   * @param prefectureCode the prefectureCode to set
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  
  /**
   * @param address1 the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  
  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  
  /**
   * @param address3 the address3 to set
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  
  /**
   * @param address4 the address4 to set
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  
  /**
   * @param phoneNumber the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  
  /**
   * @param advanceLaterFlg the advanceLaterFlg to set
   */
  public void setAdvanceLaterFlg(Long advanceLaterFlg) {
    this.advanceLaterFlg = advanceLaterFlg;
  }

  
  /**
   * @param paymentMethodNo the paymentMethodNo to set
   */
  public void setPaymentMethodNo(Long paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  
  /**
   * @param paymentMethodType the paymentMethodType to set
   */
  public void setPaymentMethodType(String paymentMethodType) {
    this.paymentMethodType = paymentMethodType;
  }

  
  /**
   * @param paymentMethodName the paymentMethodName to set
   */
  public void setPaymentMethodName(String paymentMethodName) {
    this.paymentMethodName = paymentMethodName;
  }

  
  /**
   * @param paymentCommission the paymentCommission to set
   */
  public void setPaymentCommission(BigDecimal paymentCommission) {
    this.paymentCommission = paymentCommission;
  }

  
  /**
   * @param paymentCommissionTaxRate the paymentCommissionTaxRate to set
   */
  public void setPaymentCommissionTaxRate(Long paymentCommissionTaxRate) {
    this.paymentCommissionTaxRate = paymentCommissionTaxRate;
  }

  
  /**
   * @param paymentCommissionTax the paymentCommissionTax to set
   */
  public void setPaymentCommissionTax(BigDecimal paymentCommissionTax) {
    this.paymentCommissionTax = paymentCommissionTax;
  }

  
  /**
   * @param paymentCommissionTaxType the paymentCommissionTaxType to set
   */
  public void setPaymentCommissionTaxType(Long paymentCommissionTaxType) {
    this.paymentCommissionTaxType = paymentCommissionTaxType;
  }

  
  /**
   * @param usedPoint the usedPoint to set
   */
  public void setUsedPoint(BigDecimal usedPoint) {
    this.usedPoint = usedPoint;
  }

  
  /**
   * @param paymentDate the paymentDate to set
   */
  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  
  /**
   * @param paymentLimitDate the paymentLimitDate to set
   */
  public void setPaymentLimitDate(Date paymentLimitDate) {
    this.paymentLimitDate = paymentLimitDate;
  }

  
  /**
   * @param paymentStatus the paymentStatus to set
   */
  public void setPaymentStatus(Long paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  
  /**
   * @param customerGroupCode the customerGroupCode to set
   */
  public void setCustomerGroupCode(String customerGroupCode) {
    this.customerGroupCode = customerGroupCode;
  }

  
  /**
   * @param dataTransportStatus the dataTransportStatus to set
   */
  public void setDataTransportStatus(Long dataTransportStatus) {
    this.dataTransportStatus = dataTransportStatus;
  }

  
  /**
   * @param orderStatus the orderStatus to set
   */
  public void setOrderStatus(Long orderStatus) {
    this.orderStatus = orderStatus;
  }

  
  /**
   * @param clientGroup the clientGroup to set
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

  
  /**
   * @param caution the caution to set
   */
  public void setCaution(String caution) {
    this.caution = caution;
  }

  
  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  
  /**
   * @param paymentOrderId the paymentOrderId to set
   */
  public void setPaymentOrderId(Long paymentOrderId) {
    this.paymentOrderId = paymentOrderId;
  }

  
  /**
   * @param cvsCode the cvsCode to set
   */
  public void setCvsCode(String cvsCode) {
    this.cvsCode = cvsCode;
  }

  
  /**
   * @param paymentReceipt_no the paymentReceipt_no to set
   */
  public void setPaymentReceiptNo(String paymentReceiptNo) {
    this.paymentReceiptNo = paymentReceiptNo;
  }

  
  /**
   * @param paymentReceiptUrl the paymentReceiptUrl to set
   */
  public void setPaymentReceiptUrl(String paymentReceiptUrl) {
    this.paymentReceiptUrl = paymentReceiptUrl;
  }

  
  /**
   * @param digitalCashType the digitalCashType to set
   */
  public void setDigitalCashType(String digitalCashType) {
    this.digitalCashType = digitalCashType;
  }

  
  /**
   * @param warningMessage the warningMessage to set
   */
  public void setWarningMessage(String warningMessage) {
    this.warningMessage = warningMessage;
  }

  
  /**
   * @param ormRowid the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  
  /**
   * @param createdUser the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  
  /**
   * @param createdDatetime the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  
  /**
   * @param updatedUser the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  
  /**
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  
  /**
   * @param cityCode the cityCode to set
   */
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  
  /**
   * @param mobileNumber the mobileNumber to set
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  
  /**
   * @param couponPrice the couponPrice to set
   */
  public void setCouponPrice(BigDecimal couponPrice) {
    this.couponPrice = couponPrice;
  }

  
  /**
   * @param paidPrice the paidPrice to set
   */
  public void setPaidPrice(BigDecimal paidPrice) {
    this.paidPrice = paidPrice;
  }

  
  /**
   * @param invoiceFlg the invoiceFlg to set
   */
  public void setInvoiceFlg(Long invoiceFlg) {
    this.invoiceFlg = invoiceFlg;
  }

  
  /**
   * @param discountType the discountType to set
   */
  public void setDiscountType(Long discountType) {
    this.discountType = discountType;
  }

  
  /**
   * @param discountMode the discountMode to set
   */
  public void setDiscountMode(Long discountMode) {
    this.discountMode = discountMode;
  }

  
  /**
   * @param discountRate the discountRate to set
   */
  public void setDiscountRate(BigDecimal discountRate) {
    this.discountRate = discountRate;
  }

  
  /**
   * @param discountPrice the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  
  /**
   * @param discountCode the discountCode to set
   */
  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  
  /**
   * @param discountName the discountName to set
   */
  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  
  /**
   * @param discountDetailCode the discountDetailCode to set
   */
  public void setDiscountDetailCode(String discountDetailCode) {
    this.discountDetailCode = discountDetailCode;
  }

  
  /**
   * @param orderFlg the orderFlg to set
   */
  public void setOrderFlg(Long orderFlg) {
    this.orderFlg = orderFlg;
  }

  
  /**
   * @param jdEndTime the jdEndTime to set
   */
  public void setJdEndTime(Date jdEndTime) {
    this.jdEndTime = jdEndTime;
  }

  
  /**
   * @param jdBuyerMessage the jdBuyerMessage to set
   */
  public void setJdBuyerMessage(String jdBuyerMessage) {
    this.jdBuyerMessage = jdBuyerMessage;
  }

  
  /**
   * @param jdOrderStatus the jdOrderStatus to set
   */
  public void setJdOrderStatus(String jdOrderStatus) {
    this.jdOrderStatus = jdOrderStatus;
  }

  
  /**
   * @param jdModifiedTime the jdModifiedTime to set
   */
  public void setJdModifiedTime(Date jdModifiedTime) {
    this.jdModifiedTime = jdModifiedTime;
  }

  
  /**
   * @param jdOrderNo the jdOrderNo to set
   */
  public void setJdOrderNo(String jdOrderNo) {
    this.jdOrderNo = jdOrderNo;
  }

  
  /**
   * @param orderPayment the orderPayment to set
   */
  public void setOrderPayment(BigDecimal orderPayment) {
    this.orderPayment = orderPayment;
  }

  
  /**
   * @param orderSellerPrice the orderSellerPrice to set
   */
  public void setOrderSellerPrice(BigDecimal orderSellerPrice) {
    this.orderSellerPrice = orderSellerPrice;
  }

  
  /**
   * @param jdInvoiceName the jdInvoiceName to set
   */
  public void setJdInvoiceName(String jdInvoiceName) {
    this.jdInvoiceName = jdInvoiceName;
  }

  
  /**
   * @param areaCode the areaCode to set
   */
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

}
