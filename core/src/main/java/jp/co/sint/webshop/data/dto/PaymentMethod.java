//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.CvsEnableType;
import jp.co.sint.webshop.data.domain.DigitalCashEnableType;
import jp.co.sint.webshop.data.domain.PaymentMethodDisplayType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「支払方法(PAYMENT_METHOD)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class PaymentMethod implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 支払方法番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "支払方法番号", order = 2)
  private Long paymentMethodNo;

  /** 支払方法名称 */
  @Required
  @Length(25)
  @Metadata(name = "支払方法名称", order = 3)
  private String paymentMethodName;

  /** 支払方法表示区分 */
  @Length(1)
  @Domain(PaymentMethodDisplayType.class)
  @Metadata(name = "支払方法表示区分", order = 4)
  private Long paymentMethodDisplayType;

  /** マーチャントID */
  @Length(100)
  @AlphaNum2
  @Metadata(name = "マーチャントID", order = 5)
  private String merchantId;

  /** サービスID */
  @Length(50)
  @Metadata(name = "サービスID", order = 6)
  private String serviceId;

  /** 秘密鍵 */
  @Length(100)
  @Metadata(name = "秘密鍵", order = 7)
  private String secretKey;

  /** 支払期限日数 */
  @Length(3)
  @Metadata(name = "支払期限日数", order = 8)
  private Long paymentLimitDays;

  /** 支払方法区分 */
  @Required
  @Length(2)
  @Domain(PaymentMethodType.class)
  @Metadata(name = "支払方法区分", order = 9)
  private String paymentMethodType;

  /** 先後払フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "先後払フラグ", order = 10)
  private Long advanceLaterFlg;

  /** 支払手数料消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "支払手数料消費税区分", order = 11)
  private Long paymentCommissionTaxType;

  /** 支払手数料消費税率 */
  @Length(3)
  @Percentage
  @Metadata(name = "支払手数料消費税率", order = 12)
  private Long paymentCommissionTaxRate;

  /** コンビニ使用区分 */
  @Length(1)
  @Domain(CvsEnableType.class)
  @Metadata(name = "コンビニ使用区分", order = 13)
  private Long cvsEnableType;

  /** 電子マネー使用区分 */
  @Length(1)
  @Domain(DigitalCashEnableType.class)
  @Metadata(name = "電子マネー使用区分", order = 14)
  private Long digitalCashEnableType;

  /** 削除フラグ */
  @Length(1)
  @Bool
  @Metadata(name = "削除フラグ", order = 15)
  private Long deleteFlg;

  /** 订单金额临界值 */
  @Length(11)
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "订单金额临界值", order = 16)
  private BigDecimal orderPriceThreshold;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 17)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 18)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 19)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 20)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 21)
  private Date updatedDatetime;

  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
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
   * 支払方法名称を取得します
   * 
   * @return 支払方法名称
   */
  public String getPaymentMethodName() {
    return this.paymentMethodName;
  }

  /**
   * 支払方法表示区分を取得します
   * 
   * @return 支払方法表示区分
   */
  public Long getPaymentMethodDisplayType() {
    return this.paymentMethodDisplayType;
  }

  /**
   * マーチャントIDを取得します
   * 
   * @return マーチャントID
   */
  public String getMerchantId() {
    return this.merchantId;
  }

  /**
   * サービスIDを取得します
   * 
   * @return サービスID
   */
  public String getServiceId() {
    return this.serviceId;
  }

  /**
   * 秘密鍵を取得します
   * 
   * @return 秘密鍵
   */
  public String getSecretKey() {
    return this.secretKey;
  }

  /**
   * 支払期限日数を取得します
   * 
   * @return 支払期限日数
   */
  public Long getPaymentLimitDays() {
    return this.paymentLimitDays;
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
   * 先後払フラグを取得します
   * 
   * @return 先後払フラグ
   */
  public Long getAdvanceLaterFlg() {
    return this.advanceLaterFlg;
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
   * 支払手数料消費税率を取得します
   * 
   * @return 支払手数料消費税率
   */
  public Long getPaymentCommissionTaxRate() {
    return this.paymentCommissionTaxRate;
  }

  /**
   * コンビニ使用区分を取得します
   * 
   * @return コンビニ使用区分
   */
  public Long getCvsEnableType() {
    return this.cvsEnableType;
  }

  /**
   * 電子マネー使用区分を取得します
   * 
   * @return 電子マネー使用区分
   */
  public Long getDigitalCashEnableType() {
    return this.digitalCashEnableType;
  }

  /**
   * 削除フラグを取得します
   * 
   * @return 削除フラグ
   */
  public Long getDeleteFlg() {
    return this.deleteFlg;
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
   * ショップコードを設定します
   * 
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
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
   * 支払方法名称を設定します
   * 
   * @param val
   *          支払方法名称
   */
  public void setPaymentMethodName(String val) {
    this.paymentMethodName = val;
  }

  /**
   * 支払方法表示区分を設定します
   * 
   * @param val
   *          支払方法表示区分
   */
  public void setPaymentMethodDisplayType(Long val) {
    this.paymentMethodDisplayType = val;
  }

  /**
   * マーチャントIDを設定します
   * 
   * @param val
   *          マーチャントID
   */
  public void setMerchantId(String val) {
    this.merchantId = val;
  }

  /**
   * サービスIDを設定します
   * 
   * @param val
   *          サービスID
   */
  public void setServiceId(String val) {
    this.serviceId = val;
  }

  /**
   * 秘密鍵を設定します
   * 
   * @param val
   *          秘密鍵
   */
  public void setSecretKey(String val) {
    this.secretKey = val;
  }

  /**
   * 支払期限日数を設定します
   * 
   * @param val
   *          支払期限日数
   */
  public void setPaymentLimitDays(Long val) {
    this.paymentLimitDays = val;
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
   * 先後払フラグを設定します
   * 
   * @param val
   *          先後払フラグ
   */
  public void setAdvanceLaterFlg(Long val) {
    this.advanceLaterFlg = val;
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
   * 支払手数料消費税率を設定します
   * 
   * @param val
   *          支払手数料消費税率
   */
  public void setPaymentCommissionTaxRate(Long val) {
    this.paymentCommissionTaxRate = val;
  }

  /**
   * コンビニ使用区分を設定します
   * 
   * @param val
   *          コンビニ使用区分
   */
  public void setCvsEnableType(Long val) {
    this.cvsEnableType = val;
  }

  /**
   * 電子マネー使用区分を設定します
   * 
   * @param val
   *          電子マネー使用区分
   */
  public void setDigitalCashEnableType(Long val) {
    this.digitalCashEnableType = val;
  }

  /**
   * 削除フラグを設定します
   * 
   * @param val
   *          削除フラグ
   */
  public void setDeleteFlg(Long val) {
    this.deleteFlg = val;
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

  /**
   * @return the orderPriceThreshold
   */
  public BigDecimal getOrderPriceThreshold() {
    return orderPriceThreshold;
  }

  /**
   * @param orderPriceThreshold
   *          the orderPriceThreshold to set
   */
  public void setOrderPriceThreshold(BigDecimal orderPriceThreshold) {
    this.orderPriceThreshold = orderPriceThreshold;
  }

}
