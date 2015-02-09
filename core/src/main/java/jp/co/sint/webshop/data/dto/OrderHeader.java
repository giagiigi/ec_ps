//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.GuestFlg;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PointUtil;

/**
 * 「受注ヘッダ(ORDER_HEADER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class OrderHeader implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 受注番号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  // @Digit
  @Metadata(name = "受注番号", order = 1)
  private String orderNo;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** 受注日時 */
  @Required
  @Metadata(name = "受注日時", order = 3)
  private Date orderDatetime;

  /** 顧客コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 4)
  private String customerCode;

  /** ゲストフラグ */
  @Required
  @Length(1)
  @Domain(GuestFlg.class)
  @Metadata(name = "ゲストフラグ", order = 5)
  private Long guestFlg;

  /** 姓 */
  @Required
  @Length(20)
  @Metadata(name = "姓", order = 6)
  private String lastName;

  /** 名 */
  @Required
  @Length(20)
  @Metadata(name = "名", order = 7)
  private String firstName;

  /** 姓カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "姓カナ", order = 8)
  private String lastNameKana;

  /** 名カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "名カナ", order = 9)
  private String firstNameKana;

  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス", order = 10)
  private String email;

  /** 郵便番号 */
  @Required
  @Length(7)
  @PostalCode
  @Metadata(name = "郵便番号", order = 11)
  private String postalCode;

  /** 省份编号 */
  @Required
  @Length(2)
  // 20120106 shen delete start
  // @Domain(PrefectureCode.class)
  // 20120106 shen delete end
  @Metadata(name = "省份编号", order = 12)
  private String prefectureCode;

  /** 省份名 */
  @Required
  @Length(50)
  // 20120106 shen delete start
  // @Domain(PrefectureName.class)
  // 20120106 shen delete end
  @Metadata(name = "省份名", order = 13)
  private String address1;

  /** 城市名 */
  @Required
  @Length(50)
  @Metadata(name = "城市名", order = 14)
  private String address2;

  /** 区县名 */
  // 20120106 shen delete start
  // @Required
  // 20120106 shen delete end
  @Length(50)
  @Metadata(name = "区县名", order = 15)
  private String address3;

  /** 街道地址 */
  // 20120106 shen add start
  @Required
  // 20120106 shen add end
  @Length(100)
  @Metadata(name = "街道地址", order = 16)
  private String address4;

  /** 電話番号 */
  @Length(24)
  @Phone
  @Metadata(name = "電話番号", order = 17)
  private String phoneNumber;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码", order = 47)
  private String mobileNumber;

  /** 先後払フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "先後払フラグ", order = 18)
  private Long advanceLaterFlg;

  /** 支払方法番号 */
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "支払方法番号", order = 19)
  private Long paymentMethodNo;

  /** 支払方法区分 */
  @Required
  @Length(2)
  @Domain(PaymentMethodType.class)
  @Metadata(name = "支払方法区分", order = 20)
  private String paymentMethodType;

  /** 支払方法名称 */
  @Length(25)
  @Metadata(name = "支払方法名称", order = 21)
  private String paymentMethodName;

  /** 支払手数料 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "支払手数料", order = 22)
  private BigDecimal paymentCommission;

  /** 支払手数料消費税率 */
  @Length(3)
  @Percentage
  @Metadata(name = "支払手数料消費税率", order = 23)
  private Long paymentCommissionTaxRate;

  /** 支払手数料消費税額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "支払手数料消費税額", order = 24)
  private BigDecimal paymentCommissionTax;

  /** 支払手数料消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "支払手数料消費税区分", order = 25)
  private Long paymentCommissionTaxType;

  /** 使用ポイント */
  // @Precision(precision = 10, scale = 2)
  @Metadata(name = "使用ポイント", order = 26)
  @Point
  private BigDecimal usedPoint;

  /** 入金日 */
  @Metadata(name = "入金日", order = 27)
  private Date paymentDate;

  /** 支払期限日 */
  @Metadata(name = "支払期限日", order = 28)
  private Date paymentLimitDate;

  /** 入金ステータス */
  @Required
  @Length(1)
  @Metadata(name = "入金ステータス", order = 29)
  private Long paymentStatus;

  /** 顧客グループコード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客グループコード", order = 30)
  private String customerGroupCode;

  /** データ連携ステータス */
  @Required
  @Length(1)
  @Metadata(name = "データ連携ステータス", order = 31)
  private Long dataTransportStatus;

  /** 受注ステータス */
  @Required
  @Length(1)
  @Domain(OrderStatus.class)
  @Metadata(name = "受注ステータス", order = 32)
  private Long orderStatus;

  /** クライアントグループ */
  @Required
  @Length(2)
  @Metadata(name = "クライアントグループ", order = 33)
  private String clientGroup;

  /** 注意事項（管理側のみ参照） */
  @Length(400)
  @Metadata(name = "注意事項（管理側のみ参照）", order = 34)
  private String caution;

  /** 連絡事項 */
  @Length(200)
  @Metadata(name = "連絡事項", order = 35)
  private String message;

  /** 決済サービス取引ID */
  @Length(38)
  @Metadata(name = "決済サービス取引ID", order = 36)
  private Long paymentOrderId;

  /** コンビニコード */
  @Length(2)
  @AlphaNum2
  @Metadata(name = "コンビニコード", order = 37)
  private String cvsCode;

  /** 承認番号 */
  @Length(50)
  @AlphaNum2
  @Metadata(name = "承認番号", order = 38)
  private String paymentReceiptNo;

  /** 払込URL */
  @Length(500)
  @Url
  @Metadata(name = "払込URL", order = 39)
  private String paymentReceiptUrl;

  /** 電子マネー区分 */
  @Length(2)
  @Metadata(name = "電子マネー区分", order = 40)
  private String digitalCashType;

  /** 警告メッセージ */
  @Length(100)
  @Metadata(name = "警告メッセージ", order = 41)
  private String warningMessage;

  /** 支払手数料消費税額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "電子クーポン", order = 42)
  private BigDecimal couponPrice;

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

  @Length(10)
  @Metadata(name = "实收金额", order = 47)
  private BigDecimal paidPrice;

  // add by V10-CH 170 start

  /** 城市编号 */
  @Required
  @Length(3)
  @Metadata(name = "城市编号", order = 51)
  private String cityCode;

  // add by V10-CH 170 end

  // 20111223 shen add start
  @Required
  @Length(1)
  @Domain(InvoiceFlg.class)
  @Metadata(name = "发票领取标志", order = 52)
  private Long invoiceFlg;

  @Length(1)
  @Domain(CouponType.class)
  @Metadata(name = "折扣类型", order = 53)
  private Long discountType;

  @Length(1)
  @Domain(CampaignType.class)
  @Metadata(name = "折扣方式", order = 54)
  private Long discountMode;

  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "折扣比率", order = 55)
  private Long discountRate;

  @Currency
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "折扣金额", order = 56)
  private BigDecimal discountPrice;

  @Length(16)
  @Metadata(name = "折扣规则编号", order = 57)
  private String discountCode;

  @Length(20)
  @Metadata(name = "折扣编号", order = 58)
  private String discountDetailCode;

  @Length(40)
  @Metadata(name = "折扣规则名称", order = 59)
  private String discountName;

  // 20111223 shen add end

  // soukai add 2012/01/05 ob start
  @Required
  @Length(1)
  @Metadata(name = "订单检查Flg", order = 61)
  private Long orderFlg;

  // soukai add 2012/01/05 ob end

  // 20120106 shen add start
  /** 区县编号 */
  @Length(4)
  @Metadata(name = "区县编号", order = 62)
  private String areaCode;

  // 20120106 shen add end

  /** 语言编号 */
  @Required
  @Length(5)
  @Metadata(name = "语言编号", order = 63)
  private String languageCode;

  /** 移动设备或者PC购买区分（2：移动设备 1：PC） */
  @Length(1)
  @Metadata(name = "移动设备或者PC购买区分（0：移动设备 2：PC）", order = 64)
  private Long mobileComputerType;
  
  /** http useAgent */
  @Length(500)
  @Metadata(name = "http请求头", order = 65)
  private String useAgent;
  
  /** http useAgent */
  @Length(1)
  @Metadata(name = "做成订单的设备类型", order = 66)
  private String orderClientType;
  
  /** 礼品卡使用金额 */
  @Length(12)
  @Metadata(name = "礼品卡使用金额", order = 67)
  private BigDecimal giftCardUsePrice;
  
  /** 外卡使用金额 */
  @Length(12)
  @Metadata(name = "外卡使用金额", order = 68)
  private BigDecimal outerCardPrice;

  public Long getMobileComputerType() {
    return mobileComputerType;
  }

  public void setMobileComputerType(Long mobileComputerType) {
    this.mobileComputerType = mobileComputerType;
  }

  /**
   * 受注番号を取得します
   * 
   * @return 受注番号
   */
  public String getOrderNo() {
    return this.orderNo;
  }

  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * 受注日時を取得します
   * 
   * @return 受注日時
   */
  public Date getOrderDatetime() {
    return DateUtil.immutableCopy(this.orderDatetime);
  }

  /**
   * 顧客コードを取得します
   * 
   * @return 顧客コード
   */
  public String getCustomerCode() {
    return this.customerCode;
  }

  /**
   * ゲストフラグを取得します
   * 
   * @return ゲストフラグ
   */
  public Long getGuestFlg() {
    return this.guestFlg;
  }

  /**
   * 姓を取得します
   * 
   * @return 姓
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * 名を取得します
   * 
   * @return 名
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * 姓カナを取得します
   * 
   * @return 姓カナ
   */
  public String getLastNameKana() {
    return this.lastNameKana;
  }

  /**
   * 名カナを取得します
   * 
   * @return 名カナ
   */
  public String getFirstNameKana() {
    return this.firstNameKana;
  }

  /**
   * メールアドレスを取得します
   * 
   * @return メールアドレス
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * 郵便番号を取得します
   * 
   * @return 郵便番号
   */
  public String getPostalCode() {
    return this.postalCode;
  }

  /**
   * 都道府県コードを取得します
   * 
   * @return 都道府県コード
   */
  public String getPrefectureCode() {
    return this.prefectureCode;
  }

  /**
   * 住所1を取得します
   * 
   * @return 住所1
   */
  public String getAddress1() {
    return this.address1;
  }

  /**
   * 住所2を取得します
   * 
   * @return 住所2
   */
  public String getAddress2() {
    return this.address2;
  }

  /**
   * 住所3を取得します
   * 
   * @return 住所3
   */
  public String getAddress3() {
    return this.address3;
  }

  /**
   * 住所4を取得します
   * 
   * @return 住所4
   */
  public String getAddress4() {
    return this.address4;
  }

  /**
   * 電話番号を取得します
   * 
   * @return 電話番号
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * 先後払フラグを取得します
   * 
   * @return 先後払フラグ
   */
  public Long getAdvanceLaterFlg() {
    return this.advanceLaterFlg;
  }

  /**
   * 支払方法番号を取得します
   * 
   * @return 支払方法番号
   */
  public Long getPaymentMethodNo() {
    return this.paymentMethodNo;
  }

  /**
   * 支払方法区分を取得します
   * 
   * @return 支払方法区分
   */
  public String getPaymentMethodType() {
    return this.paymentMethodType;
  }

  /**
   * 支払方法名称を取得します
   * 
   * @return 支払方法名称
   */
  public String getPaymentMethodName() {
    return this.paymentMethodName;
  }

  /**
   * 支払手数料を取得します
   * 
   * @return 支払手数料
   */
  public BigDecimal getPaymentCommission() {
    return this.paymentCommission;
  }

  /**
   * 支払手数料消費税率を取得します
   * 
   * @return 支払手数料消費税率
   */
  public Long getPaymentCommissionTaxRate() {
    return this.paymentCommissionTaxRate;
  }

  /**
   * 支払手数料消費税額を取得します
   * 
   * @return 支払手数料消費税額
   */
  public BigDecimal getPaymentCommissionTax() {
    return this.paymentCommissionTax;
  }

  /**
   * 支払手数料消費税区分を取得します
   * 
   * @return 支払手数料消費税区分
   */
  public Long getPaymentCommissionTaxType() {
    return this.paymentCommissionTaxType;
  }

  /**
   * 使用ポイントを取得します
   * 
   * @return 使用ポイント
   */
  public BigDecimal getUsedPoint() {
    return this.usedPoint;
  }

  /**
   * 入金日を取得します
   * 
   * @return 入金日
   */
  public Date getPaymentDate() {
    return DateUtil.immutableCopy(this.paymentDate);
  }

  /**
   * 支払期限日を取得します
   * 
   * @return 支払期限日
   */
  public Date getPaymentLimitDate() {
    return DateUtil.immutableCopy(this.paymentLimitDate);
  }

  /**
   * 入金ステータスを取得します
   * 
   * @return 入金ステータス
   */
  public Long getPaymentStatus() {
    return this.paymentStatus;
  }

  /**
   * 顧客グループコードを取得します
   * 
   * @return 顧客グループコード
   */
  public String getCustomerGroupCode() {
    return this.customerGroupCode;
  }

  /**
   * データ連携ステータスを取得します
   * 
   * @return データ連携ステータス
   */
  public Long getDataTransportStatus() {
    return this.dataTransportStatus;
  }

  /**
   * 受注ステータスを取得します
   * 
   * @return 受注ステータス
   */
  public Long getOrderStatus() {
    return this.orderStatus;
  }

  /**
   * クライアントグループを取得します
   * 
   * @return クライアントグループ
   */
  public String getClientGroup() {
    return this.clientGroup;
  }

  /**
   * 注意事項（管理側のみ参照）を取得します
   * 
   * @return 注意事項（管理側のみ参照）
   */
  public String getCaution() {
    return this.caution;
  }

  /**
   * 連絡事項を取得します
   * 
   * @return 連絡事項
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * 決済サービス取引IDを取得します
   * 
   * @return 決済サービス取引ID
   */
  public Long getPaymentOrderId() {
    return this.paymentOrderId;
  }

  /**
   * コンビニコードを取得します
   * 
   * @return コンビニコード
   */
  public String getCvsCode() {
    return this.cvsCode;
  }

  /**
   * 承認番号を取得します
   * 
   * @return 承認番号
   */
  public String getPaymentReceiptNo() {
    return this.paymentReceiptNo;
  }

  /**
   * 払込URLを取得します
   * 
   * @return 払込URL
   */
  public String getPaymentReceiptUrl() {
    return this.paymentReceiptUrl;
  }

  /**
   * 電子マネー区分を取得します
   * 
   * @return 電子マネー区分
   */
  public String getDigitalCashType() {
    return this.digitalCashType;
  }

  /**
   * 警告メッセージを取得します
   * 
   * @return 警告メッセージ
   */
  public String getWarningMessage() {
    return this.warningMessage;
  }

  /**
   * データ行IDを取得します
   * 
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   * 
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   * 
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   * 
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   * 
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * 受注番号を設定します
   * 
   * @param val
   *          受注番号
   */
  public void setOrderNo(String val) {
    this.orderNo = val;
  }

  /**
   * ショップコードを設定します
   * 
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * 受注日時を設定します
   * 
   * @param val
   *          受注日時
   */
  public void setOrderDatetime(Date val) {
    this.orderDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 顧客コードを設定します
   * 
   * @param val
   *          顧客コード
   */
  public void setCustomerCode(String val) {
    this.customerCode = val;
  }

  /**
   * ゲストフラグを設定します
   * 
   * @param val
   *          ゲストフラグ
   */
  public void setGuestFlg(Long val) {
    this.guestFlg = val;
  }

  /**
   * 姓を設定します
   * 
   * @param val
   *          姓
   */
  public void setLastName(String val) {
    this.lastName = val;
  }

  /**
   * 名を設定します
   * 
   * @param val
   *          名
   */
  public void setFirstName(String val) {
    this.firstName = val;
  }

  /**
   * 姓カナを設定します
   * 
   * @param val
   *          姓カナ
   */
  public void setLastNameKana(String val) {
    this.lastNameKana = val;
  }

  /**
   * 名カナを設定します
   * 
   * @param val
   *          名カナ
   */
  public void setFirstNameKana(String val) {
    this.firstNameKana = val;
  }

  /**
   * メールアドレスを設定します
   * 
   * @param val
   *          メールアドレス
   */
  public void setEmail(String val) {
    this.email = val;
  }

  /**
   * 郵便番号を設定します
   * 
   * @param val
   *          郵便番号
   */
  public void setPostalCode(String val) {
    this.postalCode = val;
  }

  /**
   * 都道府県コードを設定します
   * 
   * @param val
   *          都道府県コード
   */
  public void setPrefectureCode(String val) {
    this.prefectureCode = val;
  }

  /**
   * 住所1を設定します
   * 
   * @param val
   *          住所1
   */
  public void setAddress1(String val) {
    this.address1 = val;
  }

  /**
   * 住所2を設定します
   * 
   * @param val
   *          住所2
   */
  public void setAddress2(String val) {
    this.address2 = val;
  }

  /**
   * 住所3を設定します
   * 
   * @param val
   *          住所3
   */
  public void setAddress3(String val) {
    this.address3 = val;
  }

  /**
   * 住所4を設定します
   * 
   * @param val
   *          住所4
   */
  public void setAddress4(String val) {
    this.address4 = val;
  }

  /**
   * 電話番号を設定します
   * 
   * @param val
   *          電話番号
   */
  public void setPhoneNumber(String val) {
    this.phoneNumber = val;
  }

  /**
   * 先後払フラグを設定します
   * 
   * @param val
   *          先後払フラグ
   */
  public void setAdvanceLaterFlg(Long val) {
    this.advanceLaterFlg = val;
  }

  /**
   * 支払方法番号を設定します
   * 
   * @param val
   *          支払方法番号
   */
  public void setPaymentMethodNo(Long val) {
    this.paymentMethodNo = val;
  }

  /**
   * 支払方法区分を設定します
   * 
   * @param val
   *          支払方法区分
   */
  public void setPaymentMethodType(String val) {
    this.paymentMethodType = val;
  }

  /**
   * 支払方法名称を設定します
   * 
   * @param val
   *          支払方法名称
   */
  public void setPaymentMethodName(String val) {
    this.paymentMethodName = val;
  }

  /**
   * 支払手数料を設定します
   * 
   * @param val
   *          支払手数料
   */
  public void setPaymentCommission(BigDecimal val) {
    this.paymentCommission = val;
  }

  /**
   * 支払手数料消費税率を設定します
   * 
   * @param val
   *          支払手数料消費税率
   */
  public void setPaymentCommissionTaxRate(Long val) {
    this.paymentCommissionTaxRate = val;
  }

  /**
   * 支払手数料消費税額を設定します
   * 
   * @param val
   *          支払手数料消費税額
   */
  public void setPaymentCommissionTax(BigDecimal val) {
    this.paymentCommissionTax = val;
  }

  /**
   * 支払手数料消費税区分を設定します
   * 
   * @param val
   *          支払手数料消費税区分
   */
  public void setPaymentCommissionTaxType(Long val) {
    this.paymentCommissionTaxType = val;
  }

  /**
   * 使用ポイントを設定します
   * 
   * @param val
   *          使用ポイント
   */
  public void setUsedPoint(BigDecimal val) {
    this.usedPoint = val.setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
  }

  /**
   * 入金日を設定します
   * 
   * @param val
   *          入金日
   */
  public void setPaymentDate(Date val) {
    this.paymentDate = DateUtil.immutableCopy(val);
  }

  /**
   * 支払期限日を設定します
   * 
   * @param val
   *          支払期限日
   */
  public void setPaymentLimitDate(Date val) {
    this.paymentLimitDate = DateUtil.immutableCopy(val);
  }

  /**
   * 入金ステータスを設定します
   * 
   * @param val
   *          入金ステータス
   */
  public void setPaymentStatus(Long val) {
    this.paymentStatus = val;
  }

  /**
   * 顧客グループコードを設定します
   * 
   * @param val
   *          顧客グループコード
   */
  public void setCustomerGroupCode(String val) {
    this.customerGroupCode = val;
  }

  /**
   * データ連携ステータスを設定します
   * 
   * @param val
   *          データ連携ステータス
   */
  public void setDataTransportStatus(Long val) {
    this.dataTransportStatus = val;
  }

  /**
   * 受注ステータスを設定します
   * 
   * @param val
   *          受注ステータス
   */
  public void setOrderStatus(Long val) {
    this.orderStatus = val;
  }

  /**
   * クライアントグループを設定します
   * 
   * @param val
   *          クライアントグループ
   */
  public void setClientGroup(String val) {
    this.clientGroup = val;
  }

  /**
   * 注意事項（管理側のみ参照）を設定します
   * 
   * @param val
   *          注意事項（管理側のみ参照）
   */
  public void setCaution(String val) {
    this.caution = val;
  }

  /**
   * 連絡事項を設定します
   * 
   * @param val
   *          連絡事項
   */
  public void setMessage(String val) {
    this.message = val;
  }

  /**
   * 決済サービス取引IDを設定します
   * 
   * @param val
   *          決済サービス取引ID
   */
  public void setPaymentOrderId(Long val) {
    this.paymentOrderId = val;
  }

  /**
   * コンビニコードを設定します
   * 
   * @param val
   *          コンビニコード
   */
  public void setCvsCode(String val) {
    this.cvsCode = val;
  }

  /**
   * 承認番号を設定します
   * 
   * @param val
   *          承認番号
   */
  public void setPaymentReceiptNo(String val) {
    this.paymentReceiptNo = val;
  }

  /**
   * 払込URLを設定します
   * 
   * @param val
   *          払込URL
   */
  public void setPaymentReceiptUrl(String val) {
    this.paymentReceiptUrl = val;
  }

  /**
   * 電子マネー区分を設定します
   * 
   * @param val
   *          電子マネー区分
   */
  public void setDigitalCashType(String val) {
    this.digitalCashType = val;
  }

  /**
   * 警告メッセージを設定します
   * 
   * @param val
   *          警告メッセージ
   */
  public void setWarningMessage(String val) {
    this.warningMessage = val;
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
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

  public BigDecimal getPaidPrice() {
    return paidPrice;
  }

  public void setPaidPrice(BigDecimal paidPrice) {
    this.paidPrice = paidPrice;
  }

  public Long getInvoiceFlg() {
    return invoiceFlg;
  }

  public void setInvoiceFlg(Long invoiceFlg) {
    this.invoiceFlg = invoiceFlg;
  }

  public Long getDiscountType() {
    return discountType;
  }

  public void setDiscountType(Long discountType) {
    this.discountType = discountType;
  }

  public Long getDiscountMode() {
    return discountMode;
  }

  public void setDiscountMode(Long discountMode) {
    this.discountMode = discountMode;
  }

  public Long getDiscountRate() {
    return discountRate;
  }

  public void setDiscountRate(Long discountRate) {
    this.discountRate = discountRate;
  }

  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  public String getDiscountCode() {
    return discountCode;
  }

  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  public String getDiscountName() {
    return discountName;
  }

  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  public String getDiscountDetailCode() {
    return discountDetailCode;
  }

  public void setDiscountDetailCode(String discountDetailCode) {
    this.discountDetailCode = discountDetailCode;
  }

  /**
   * @return the orderFlg
   */
  public Long getOrderFlg() {
    return orderFlg;
  }

  /**
   * @param orderFlg
   *          the orderFlg to set
   */
  public void setOrderFlg(Long orderFlg) {
    this.orderFlg = orderFlg;
  }

  /**
   * @return the areaCode
   */
  public String getAreaCode() {
    return areaCode;
  }

  /**
   * @param areaCode
   *          the areaCode to set
   */
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  
  /**
   * @return the useAgent
   */
  public String getUseAgent() {
    return useAgent;
  }

  
  /**
   * @param useAgent the useAgent to set
   */
  public void setUseAgent(String useAgent) {
    this.useAgent = useAgent;
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
   * @return the giftCardUsePrice
   */
  public BigDecimal getGiftCardUsePrice() {
    return giftCardUsePrice;
  }

  
  /**
   * @param giftCardUsePrice the giftCardUsePrice to set
   */
  public void setGiftCardUsePrice(BigDecimal giftCardUsePrice) {
    this.giftCardUsePrice = giftCardUsePrice;
  }

  
  /**
   * @return the outerCardPrice
   */
  public BigDecimal getOuterCardPrice() {
    return outerCardPrice;
  }

  
  /**
   * @param outerCardPrice the outerCardPrice to set
   */
  public void setOuterCardPrice(BigDecimal outerCardPrice) {
    this.outerCardPrice = outerCardPrice;
  }
}
