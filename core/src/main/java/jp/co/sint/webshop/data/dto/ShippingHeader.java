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
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
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
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PointUtil;

/**
 * 「出荷ヘッダ(SHIPPING_HEADER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class ShippingHeader implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 出荷番号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "出荷番号", order = 1)
  private String shippingNo;

  /** 受注番号 */
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 2)
  private String orderNo;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 3)
  private String shopCode;

  /** 顧客コード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 4)
  private String customerCode;

  /** アドレス帳番号 */
  @Length(8)
  @Digit
  @Metadata(name = "アドレス帳番号", order = 5)
  private Long addressNo;

  /** 宛名：姓 */
  @Required
  @Length(20)
  @Metadata(name = "宛名：姓", order = 6)
  private String addressLastName;

  /** 宛名：名 */
  // @Required
  @Length(20)
  @Metadata(name = "宛名：名", order = 7)
  private String addressFirstName;

  /** 宛名姓カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "宛名姓カナ", order = 8)
  private String addressLastNameKana;

  /** 宛名名カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "宛名名カナ", order = 9)
  private String addressFirstNameKana;

  /** 郵便番号 */
  @Required
  @Length(7)
  @PostalCode
  @Metadata(name = "郵便番号", order = 10)
  private String postalCode;

  /** 省份编号 */
  @Required
  @Length(2)
  // 20120106 shen delete start
  // @Domain(PrefectureCode.class)
  // 20120106 shen delete end
  @Metadata(name = "省份编号", order = 11)
  private String prefectureCode;

  /** 省份名 */
  @Required
  @Length(50)
  // 20120106 shen delete start
  // @Domain(PrefectureName.class)
  // 20120106 shen delete end
  @Metadata(name = "省份名", order = 12)
  private String address1;

  /** 城市名 */
  @Required
  @Length(50)
  @Metadata(name = "城市名", order = 13)
  private String address2;

  /** 区县名 */
  // 20120106 shen delete start
  // @Required
  // 20120106 shen delete end
  @Length(50)
  @Metadata(name = "区县名", order = 14)
  private String address3;

  /** 街道地址 */
  // 20120106 shen add start
  @Required
  // 20120106 shen add end
  @Length(100)
  @Metadata(name = "街道地址", order = 15)
  private String address4;

  /** 電話番号 */
  @Length(24)
  @Phone
  @Metadata(name = "電話番号", order = 16)
  private String phoneNumber;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码", order = 17)
  private String mobileNumber;

  /** 配送先備考 */
  @Length(500)
  @Metadata(name = "配送先備考", order = 18)
  private String deliveryRemark;

  /** 獲得ポイント */
  // @Precision(precision = 10, scale = 2)
  @Point
  @Metadata(name = "獲得ポイント", order = 19)
  private BigDecimal acquiredPoint;

  /** 宅配便伝票番号 */
  @Length(500)
  @Metadata(name = "宅配便伝票番号", order = 20)
  private String deliverySlipNo;

  /** 送料 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "送料", order = 21)
  private BigDecimal shippingCharge;

  /** 送料消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "送料消費税区分", order = 22)
  private Long shippingChargeTaxType;

  /** 送料消費税率 */
  @Length(3)
  @Percentage
  @Metadata(name = "送料消費税率", order = 23)
  private Long shippingChargeTaxRate;

  /** 送料消費税額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "送料消費税額", order = 24)
  private BigDecimal shippingChargeTax;

  /** 配送種別番号 */
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "配送種別番号", order = 25)
  private Long deliveryTypeNo;

  /** 配送種別名称 */
  @Length(40)
  @Metadata(name = "配送種別名称", order = 26)
  private String deliveryTypeName;

  /** 配送指定日 */
  @Metadata(name = "配送指定日", order = 27)
  // 20111229 shen update start
  // private Date deliveryAppointedDate;
  private String deliveryAppointedDate;

  // 20111229 shen update end

  /** 配送指定時間開始 */
  @Length(2)
  @Metadata(name = "配送指定時間開始", order = 28)
  private Long deliveryAppointedTimeStart;

  /** 配送指定時間終了 */
  @Length(2)
  @Metadata(name = "配送指定時間終了", order = 29)
  private Long deliveryAppointedTimeEnd;

  /** 到着予定日 */
  @Metadata(name = "到着予定日", order = 30)
  private Date arrivalDate;

  /** 到着時間開始 */
  @Length(2)
  @Metadata(name = "到着時間開始", order = 31)
  private Long arrivalTimeStart;

  /** 到着時間終了 */
  @Length(2)
  @Metadata(name = "到着時間終了", order = 32)
  private Long arrivalTimeEnd;

  /** 売上確定ステータス */
  @Required
  @Length(1)
  @Domain(FixedSalesStatus.class)
  @Metadata(name = "売上確定ステータス", order = 33)
  private Long fixedSalesStatus;

  /** 出荷ステータス */
  @Required
  @Length(1)
  @Domain(ShippingStatus.class)
  @Metadata(name = "出荷ステータス", order = 34)
  private Long shippingStatus;

  /** 出荷指示日 */
  @Metadata(name = "出荷指示日", order = 35)
  private Date shippingDirectDate;

  /** 出荷日 */
  @Metadata(name = "出荷日", order = 36)
  private Date shippingDate;

  /** 元出荷番号 */
  @Length(16)
  @Digit
  @Metadata(name = "元出荷番号", order = 37)
  private Long originalShippingNo;

  /** 返品日 */
  @Metadata(name = "返品日", order = 38)
  private Date returnItemDate;

  /** 返品損金額 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "返品損金額", order = 39)
  private BigDecimal returnItemLossMoney;

  /** 返品区分 */
  @Length(1)
  @Domain(ReturnItemType.class)
  @Metadata(name = "返品区分", order = 40)
  private Long returnItemType;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 41)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 42)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 43)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 44)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 45)
  private Date updatedDatetime;

  // modify by V10-CH 170 start
  /** 城市编号 */
  @Required
  @Length(3)
  @Metadata(name = "城市编号", order = 46)
  private String cityCode;

  // modify by V10-CH 170 end

  // 20111229 shen add start
  /** 配送公司编号 */
  @Required
  @Length(16)
  @Metadata(name = "配送公司编号", order = 47)
  private String deliveryCompanyNo;

  /** 配送公司名称 */
  @Required
  @Length(50)
  @Metadata(name = "配送公司名称", order = 48)
  private String deliveryCompanyName;

  // 20111229 shen add end

  // 20120106 shen add start
  /** 区县编号 */
  @Length(4)
  @Metadata(name = "区县编号", order = 49)
  private String areaCode;

  // 20120106 shen add end

  /** 出荷ステータスWMS */
  @Length(1)
  @Domain(ShippingStatus.class)
  @Metadata(name = "出荷ステータス", order = 50)
  private Long shippingStatusWms;

  public Long getShippingStatusWms() {
    return shippingStatusWms;
  }

  public void setShippingStatusWms(Long shippingStatusWms) {
    this.shippingStatusWms = shippingStatusWms;
  }

  /**
   * 出荷番号を取得します
   * 
   * @return 出荷番号
   */
  public String getShippingNo() {
    return this.shippingNo;
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
   * 顧客コードを取得します
   * 
   * @return 顧客コード
   */
  public String getCustomerCode() {
    return this.customerCode;
  }

  /**
   * アドレス帳番号を取得します
   * 
   * @return アドレス帳番号
   */
  public Long getAddressNo() {
    return this.addressNo;
  }

  /**
   * 宛名：姓を取得します
   * 
   * @return 宛名：姓
   */
  public String getAddressLastName() {
    return this.addressLastName;
  }

  /**
   * 宛名：名を取得します
   * 
   * @return 宛名：名
   */
  public String getAddressFirstName() {
    return this.addressFirstName;
  }

  /**
   * 宛名姓カナを取得します
   * 
   * @return 宛名姓カナ
   */
  public String getAddressLastNameKana() {
    return this.addressLastNameKana;
  }

  /**
   * 宛名名カナを取得します
   * 
   * @return 宛名名カナ
   */
  public String getAddressFirstNameKana() {
    return this.addressFirstNameKana;
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
   * 配送先備考を取得します
   * 
   * @return 配送先備考
   */
  public String getDeliveryRemark() {
    return this.deliveryRemark;
  }

  /**
   * 獲得ポイントを取得します
   * 
   * @return 獲得ポイント
   */
  public BigDecimal getAcquiredPoint() {
    return this.acquiredPoint;
  }

  /**
   * 宅配便伝票番号を取得します
   * 
   * @return 宅配便伝票番号
   */
  public String getDeliverySlipNo() {
    return this.deliverySlipNo;
  }

  /**
   * 送料を取得します
   * 
   * @return 送料
   */
  public BigDecimal getShippingCharge() {
    return this.shippingCharge;
  }

  /**
   * 送料消費税区分を取得します
   * 
   * @return 送料消費税区分
   */
  public Long getShippingChargeTaxType() {
    return this.shippingChargeTaxType;
  }

  /**
   * 送料消費税率を取得します
   * 
   * @return 送料消費税率
   */
  public Long getShippingChargeTaxRate() {
    return this.shippingChargeTaxRate;
  }

  /**
   * 送料消費税額を取得します
   * 
   * @return 送料消費税額
   */
  public BigDecimal getShippingChargeTax() {
    return this.shippingChargeTax;
  }

  /**
   * 配送種別番号を取得します
   * 
   * @return 配送種別番号
   */
  public Long getDeliveryTypeNo() {
    return this.deliveryTypeNo;
  }

  /**
   * 配送種別名称を取得します
   * 
   * @return 配送種別名称
   */
  public String getDeliveryTypeName() {
    return this.deliveryTypeName;
  }

  // 20111229 shen delete start
  /**
   * 配送指定日を取得します
   * 
   * @return 配送指定日
   */
  /*
   * public Date getDeliveryAppointedDate() { return
   * DateUtil.immutableCopy(this.deliveryAppointedDate); }
   */
  // 20111229 shen delete end
  /**
   * 配送指定時間開始を取得します
   * 
   * @return 配送指定時間開始
   */
  public Long getDeliveryAppointedTimeStart() {
    return this.deliveryAppointedTimeStart;
  }

  /**
   * 配送指定時間終了を取得します
   * 
   * @return 配送指定時間終了
   */
  public Long getDeliveryAppointedTimeEnd() {
    return this.deliveryAppointedTimeEnd;
  }

  /**
   * 到着予定日を取得します
   * 
   * @return 到着予定日
   */
  public Date getArrivalDate() {
    return DateUtil.immutableCopy(this.arrivalDate);
  }

  /**
   * 到着時間開始を取得します
   * 
   * @return 到着時間開始
   */
  public Long getArrivalTimeStart() {
    return this.arrivalTimeStart;
  }

  /**
   * 到着時間終了を取得します
   * 
   * @return 到着時間終了
   */
  public Long getArrivalTimeEnd() {
    return this.arrivalTimeEnd;
  }

  /**
   * 売上確定ステータスを取得します
   * 
   * @return 売上確定ステータス
   */
  public Long getFixedSalesStatus() {
    return this.fixedSalesStatus;
  }

  /**
   * 出荷ステータスを取得します
   * 
   * @return 出荷ステータス
   */
  public Long getShippingStatus() {
    return this.shippingStatus;
  }

  /**
   * 出荷指示日を取得します
   * 
   * @return 出荷指示日
   */
  public Date getShippingDirectDate() {
    return DateUtil.immutableCopy(this.shippingDirectDate);
  }

  /**
   * 出荷日を取得します
   * 
   * @return 出荷日
   */
  public Date getShippingDate() {
    return DateUtil.immutableCopy(this.shippingDate);
  }

  /**
   * 元出荷番号を取得します
   * 
   * @return 元出荷番号
   */
  public Long getOriginalShippingNo() {
    return this.originalShippingNo;
  }

  /**
   * 返品日を取得します
   * 
   * @return 返品日
   */
  public Date getReturnItemDate() {
    return DateUtil.immutableCopy(this.returnItemDate);
  }

  /**
   * 返品損金額を取得します
   * 
   * @return 返品損金額
   */
  public BigDecimal getReturnItemLossMoney() {
    return this.returnItemLossMoney;
  }

  /**
   * 返品区分を取得します
   * 
   * @return 返品区分
   */
  public Long getReturnItemType() {
    return this.returnItemType;
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
   * 出荷番号を設定します
   * 
   * @param val
   *          出荷番号
   */
  public void setShippingNo(String val) {
    this.shippingNo = val;
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
   * 顧客コードを設定します
   * 
   * @param val
   *          顧客コード
   */
  public void setCustomerCode(String val) {
    this.customerCode = val;
  }

  /**
   * アドレス帳番号を設定します
   * 
   * @param val
   *          アドレス帳番号
   */
  public void setAddressNo(Long val) {
    this.addressNo = val;
  }

  /**
   * 宛名：姓を設定します
   * 
   * @param val
   *          宛名：姓
   */
  public void setAddressLastName(String val) {
    this.addressLastName = val;
  }

  /**
   * 宛名：名を設定します
   * 
   * @param val
   *          宛名：名
   */
  public void setAddressFirstName(String val) {
    this.addressFirstName = val;
  }

  /**
   * 宛名姓カナを設定します
   * 
   * @param val
   *          宛名姓カナ
   */
  public void setAddressLastNameKana(String val) {
    this.addressLastNameKana = val;
  }

  /**
   * 宛名名カナを設定します
   * 
   * @param val
   *          宛名名カナ
   */
  public void setAddressFirstNameKana(String val) {
    this.addressFirstNameKana = val;
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
   * 配送先備考を設定します
   * 
   * @param val
   *          配送先備考
   */
  public void setDeliveryRemark(String val) {
    this.deliveryRemark = val;
  }

  /**
   * 獲得ポイントを設定します
   * 
   * @param val
   *          獲得ポイント
   */
  public void setAcquiredPoint(BigDecimal val) {
    if (val == null) {
      acquiredPoint = BigDecimal.ZERO;
    } else {
      this.acquiredPoint = val.setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
    }
  }

  /**
   * 宅配便伝票番号を設定します
   * 
   * @param val
   *          宅配便伝票番号
   */
  public void setDeliverySlipNo(String val) {
    this.deliverySlipNo = val;
  }

  /**
   * 送料を設定します
   * 
   * @param val
   *          送料
   */
  public void setShippingCharge(BigDecimal val) {
    this.shippingCharge = val;
  }

  /**
   * 送料消費税区分を設定します
   * 
   * @param val
   *          送料消費税区分
   */
  public void setShippingChargeTaxType(Long val) {
    this.shippingChargeTaxType = val;
  }

  /**
   * 送料消費税率を設定します
   * 
   * @param val
   *          送料消費税率
   */
  public void setShippingChargeTaxRate(Long val) {
    this.shippingChargeTaxRate = val;
  }

  /**
   * 送料消費税額を設定します
   * 
   * @param val
   *          送料消費税額
   */
  public void setShippingChargeTax(BigDecimal val) {
    this.shippingChargeTax = val;
  }

  /**
   * 配送種別番号を設定します
   * 
   * @param val
   *          配送種別番号
   */
  public void setDeliveryTypeNo(Long val) {
    this.deliveryTypeNo = val;
  }

  /**
   * 配送種別名称を設定します
   * 
   * @param val
   *          配送種別名称
   */
  public void setDeliveryTypeName(String val) {
    this.deliveryTypeName = val;
  }

  // 20111229 shen delete start
  /**
   * 配送指定日を設定します
   * 
   * @param val
   *          配送指定日
   */
  /*
   * public void setDeliveryAppointedDate(Date val) { this.deliveryAppointedDate
   * = DateUtil.immutableCopy(val); }
   */
  // 20111229 shen delete end
  /**
   * 配送指定時間開始を設定します
   * 
   * @param val
   *          配送指定時間開始
   */
  public void setDeliveryAppointedTimeStart(Long val) {
    this.deliveryAppointedTimeStart = val;
  }

  /**
   * 配送指定時間終了を設定します
   * 
   * @param val
   *          配送指定時間終了
   */
  public void setDeliveryAppointedTimeEnd(Long val) {
    this.deliveryAppointedTimeEnd = val;
  }

  /**
   * 到着予定日を設定します
   * 
   * @param val
   *          到着予定日
   */
  public void setArrivalDate(Date val) {
    this.arrivalDate = DateUtil.immutableCopy(val);
  }

  /**
   * 到着時間開始を設定します
   * 
   * @param val
   *          到着時間開始
   */
  public void setArrivalTimeStart(Long val) {
    this.arrivalTimeStart = val;
  }

  /**
   * 到着時間終了を設定します
   * 
   * @param val
   *          到着時間終了
   */
  public void setArrivalTimeEnd(Long val) {
    this.arrivalTimeEnd = val;
  }

  /**
   * 売上確定ステータスを設定します
   * 
   * @param val
   *          売上確定ステータス
   */
  public void setFixedSalesStatus(Long val) {
    this.fixedSalesStatus = val;
  }

  /**
   * 出荷ステータスを設定します
   * 
   * @param val
   *          出荷ステータス
   */
  public void setShippingStatus(Long val) {
    this.shippingStatus = val;
  }

  /**
   * 出荷指示日を設定します
   * 
   * @param val
   *          出荷指示日
   */
  public void setShippingDirectDate(Date val) {
    this.shippingDirectDate = DateUtil.immutableCopy(val);
  }

  /**
   * 出荷日を設定します
   * 
   * @param val
   *          出荷日
   */
  public void setShippingDate(Date val) {
    this.shippingDate = DateUtil.immutableCopy(val);
  }

  /**
   * 元出荷番号を設定します
   * 
   * @param val
   *          元出荷番号
   */
  public void setOriginalShippingNo(Long val) {
    this.originalShippingNo = val;
  }

  /**
   * 返品日を設定します
   * 
   * @param val
   *          返品日
   */
  public void setReturnItemDate(Date val) {
    this.returnItemDate = DateUtil.immutableCopy(val);
  }

  /**
   * 返品損金額を設定します
   * 
   * @param val
   *          返品損金額
   */
  public void setReturnItemLossMoney(BigDecimal val) {
    this.returnItemLossMoney = val;
  }

  /**
   * 返品区分を設定します
   * 
   * @param val
   *          返品区分
   */
  public void setReturnItemType(Long val) {
    this.returnItemType = val;
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

  public String getDeliveryAppointedDate() {
    return deliveryAppointedDate;
  }

  public void setDeliveryAppointedDate(String deliveryAppointedDate) {
    this.deliveryAppointedDate = deliveryAppointedDate;
  }

  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }

  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }

  public String getDeliveryCompanyName() {
    return deliveryCompanyName;
  }

  public void setDeliveryCompanyName(String deliveryCompanyName) {
    this.deliveryCompanyName = deliveryCompanyName;
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

}
