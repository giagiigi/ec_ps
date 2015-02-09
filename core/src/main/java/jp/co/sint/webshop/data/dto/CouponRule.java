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
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「クーポンルール(POINT_RULE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CouponRule implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** クーポンルール番号 */
  @PrimaryKey(1)
  @Length(1)
  @Digit
  @Metadata(name = "クーポンルール番号", order = 1)
  private Long couponRuleNo;

  /** クーポン機能使用フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "クーポン機能使用フラグ", order = 2)
  private Long couponFunctionEnabledFlg;

  /** クーポン付与最低購入金額 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "クーポン付与最低購入金額", order = 3)
  private BigDecimal couponInvestPurchasePrice;

  /** クーポン使用最大数 */
  @Required
  @Length(2)
  @Digit(allowNegative = false)
  @Metadata(name = "クーポン使用最大数", order = 4)
  private Long couponAmount;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 13)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 14)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 15)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 16)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 17)
  private Date updatedDatetime;

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

  public Long getCouponAmount() {
    return couponAmount;
  }

  public void setCouponAmount(Long couponAmount) {
    this.couponAmount = couponAmount;
  }

  public Long getCouponFunctionEnabledFlg() {
    return couponFunctionEnabledFlg;
  }

  public void setCouponFunctionEnabledFlg(Long couponFunctionEnabledFlg) {
    this.couponFunctionEnabledFlg = couponFunctionEnabledFlg;
  }

  public BigDecimal getCouponInvestPurchasePrice() {
    return couponInvestPurchasePrice;
  }

  public void setCouponInvestPurchasePrice(BigDecimal couponInvestPurchasePrice) {
    this.couponInvestPurchasePrice = couponInvestPurchasePrice;
  }

  public Long getCouponRuleNo() {
    return couponRuleNo;
  }

  public void setCouponRuleNo(Long couponRuleNo) {
    this.couponRuleNo = couponRuleNo;
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
}
