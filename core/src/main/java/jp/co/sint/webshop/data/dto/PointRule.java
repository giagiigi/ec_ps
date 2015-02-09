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
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PointUtil;

/**
 * 「ポイントルール(POINT_RULE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class PointRule implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ポイントルール番号 */
  @PrimaryKey(1)
  @Length(1)
  @Digit
  @Metadata(name = "ポイントルール番号", order = 1)
  private Long pointRuleNo;

  /** ポイント機能使用フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "ポイント機能使用フラグ", order = 2)
  private Long pointFunctionEnabledFlg;

  /** ポイント使用期限(Xヶ月) */
  @Required
  @Length(3)
  @Metadata(name = "ポイント使用期限(Xヶ月)", order = 3)
  private Long pointPeriod;

  /** ポイント付与率 */
  @Required
  @Length(3)
  @Percentage
  @Metadata(name = "ポイント付与率", order = 4)
  private Long pointRate;

  /** ポイント付与最低購入金額 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "ポイント付与最低購入金額", order = 5)
  private BigDecimal pointInvestPurchasePrice;

  /** ボーナスポイント期間付与率 */
  @Required
  @Length(3)
  @Percentage
  @Metadata(name = "ボーナスポイント期間付与率", order = 6)
  private Long bonusPointTermRate;

  /** ボーナスポイント期間開始日 */
  @Metadata(name = "ボーナスポイント期間開始日", order = 7)
  private Date bonusPointStartDate;

  /** ボーナスポイント期間終了日 */
  @Metadata(name = "ボーナスポイント期間終了日", order = 8)
  private Date bonusPointEndDate;

  /** ボーナスポイント日 */
  @Length(2)
  @Metadata(name = "ボーナスポイント日", order = 9)
  private Long bonusPointDate;

  /** 会員登録時ポイント */
  @Required
  //@Precision(precision = 10, scale = 2)
  @Point
  @Metadata(name = "会員登録時ポイント", order = 10)
  private BigDecimal customerRegisterPoint;

  /** 初期購入時付与ポイント */
  @Required
  //@Precision(precision = 10, scale = 2)
  @Point
  @Metadata(name = "初期購入時付与ポイント", order = 11)
  private BigDecimal firstPurchaseInvestPoint;

  /** レビュー投稿時ポイント */
  @Required
  //@Precision(precision = 10, scale = 2)
  @Point
  @Metadata(name = "レビュー投稿時ポイント", order = 12)
  private BigDecimal reviewContributedPoint;

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

  // add by zhanghaibin start 2010-05-18
//  /** 最小ポイントを使います */
//  @Required
//  @Precision(precision = 10, scale = 2)
//  @Metadata(name = "最小ポイントを使います", order = 12)
//  private BigDecimal smallUsePoint;

//  /** RMB兑换积分比率 */
//  @Required
//  @Length(3)
//  @Metadata(name = "RMB兑换积分比率", order = 18)
//  private BigDecimal rmbPointRate;

  // add by zhanghaibin end 2010-05-18

  /**
   * ポイントルール番号を取得します
   * 
   * @return ポイントルール番号
   */
  public Long getPointRuleNo() {
    return this.pointRuleNo;
  }

  /**
   * ポイント機能使用フラグを取得します
   * 
   * @return ポイント機能使用フラグ
   */
  public Long getPointFunctionEnabledFlg() {
    return this.pointFunctionEnabledFlg;
  }

  /**
   * ポイント使用期限(Xヶ月)を取得します
   * 
   * @return ポイント使用期限(Xヶ月)
   */
  public Long getPointPeriod() {
    return this.pointPeriod;
  }

  /**
   * ポイント付与率を取得します
   * 
   * @return ポイント付与率
   */
  public Long getPointRate() {
    return this.pointRate;
  }

  /**
   * ポイント付与最低購入金額を取得します
   * 
   * @return ポイント付与最低購入金額
   */
  public BigDecimal getPointInvestPurchasePrice() {
    return this.pointInvestPurchasePrice;
  }

  /**
   * ボーナスポイント期間付与率を取得します
   * 
   * @return ボーナスポイント期間付与率
   */
  public Long getBonusPointTermRate() {
    return this.bonusPointTermRate;
  }

  /**
   * ボーナスポイント期間開始日を取得します
   * 
   * @return ボーナスポイント期間開始日
   */
  public Date getBonusPointStartDate() {
    return DateUtil.immutableCopy(this.bonusPointStartDate);
  }

  /**
   * ボーナスポイント期間終了日を取得します
   * 
   * @return ボーナスポイント期間終了日
   */
  public Date getBonusPointEndDate() {
    return DateUtil.immutableCopy(this.bonusPointEndDate);
  }

  /**
   * ボーナスポイント日を取得します
   * 
   * @return ボーナスポイント日
   */
  public Long getBonusPointDate() {
    return this.bonusPointDate;
  }

  /**
   * 会員登録時ポイントを取得します
   * 
   * @return 会員登録時ポイント
   */
  public BigDecimal getCustomerRegisterPoint() {
    return this.customerRegisterPoint;
  }

  /**
   * 初期購入時付与ポイントを取得します
   * 
   * @return 初期購入時付与ポイント
   */
  public BigDecimal getFirstPurchaseInvestPoint() {
    return this.firstPurchaseInvestPoint;
  }

  /**
   * レビュー投稿時ポイントを取得します
   * 
   * @return レビュー投稿時ポイント
   */
  public BigDecimal getReviewContributedPoint() {
    return this.reviewContributedPoint;
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
   * ポイントルール番号を設定します
   * 
   * @param val
   *          ポイントルール番号
   */
  public void setPointRuleNo(Long val) {
    this.pointRuleNo = val;
  }

  /**
   * ポイント機能使用フラグを設定します
   * 
   * @param val
   *          ポイント機能使用フラグ
   */
  public void setPointFunctionEnabledFlg(Long val) {
    this.pointFunctionEnabledFlg = val;
  }

  /**
   * ポイント使用期限(Xヶ月)を設定します
   * 
   * @param val
   *          ポイント使用期限(Xヶ月)
   */
  public void setPointPeriod(Long val) {
    this.pointPeriod = val;
  }

  /**
   * ポイント付与率を設定します
   * 
   * @param val
   *          ポイント付与率
   */
  public void setPointRate(Long val) {
    this.pointRate = val;
  }

  /**
   * ポイント付与最低購入金額を設定します
   * 
   * @param val
   *          ポイント付与最低購入金額
   */
  public void setPointInvestPurchasePrice(BigDecimal val) {
    this.pointInvestPurchasePrice = val;
  }

  /**
   * ボーナスポイント期間付与率を設定します
   * 
   * @param val
   *          ボーナスポイント期間付与率
   */
  public void setBonusPointTermRate(Long val) {
    this.bonusPointTermRate = val;
  }

  /**
   * ボーナスポイント期間開始日を設定します
   * 
   * @param val
   *          ボーナスポイント期間開始日
   */
  public void setBonusPointStartDate(Date val) {
    this.bonusPointStartDate = DateUtil.immutableCopy(val);
  }

  /**
   * ボーナスポイント期間終了日を設定します
   * 
   * @param val
   *          ボーナスポイント期間終了日
   */
  public void setBonusPointEndDate(Date val) {
    this.bonusPointEndDate = DateUtil.immutableCopy(val);
  }

  /**
   * ボーナスポイント日を設定します
   * 
   * @param val
   *          ボーナスポイント日
   */
  public void setBonusPointDate(Long val) {
    this.bonusPointDate = val;
  }

  /**
   * 会員登録時ポイントを設定します
   * 
   * @param val
   *          会員登録時ポイント
   */
  public void setCustomerRegisterPoint(BigDecimal val) {
    this.customerRegisterPoint = val.setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
  }

  /**
   * 初期購入時付与ポイントを設定します
   * 
   * @param val
   *          初期購入時付与ポイント
   */
  public void setFirstPurchaseInvestPoint(BigDecimal val) {
    this.firstPurchaseInvestPoint = val.setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
  }

  /**
   * レビュー投稿時ポイントを設定します
   * 
   * @param val
   *          レビュー投稿時ポイント
   */
  public void setReviewContributedPoint(BigDecimal val) {
    this.reviewContributedPoint = val.setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
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

//  public BigDecimal getRmbPointRate() {
//    return rmbPointRate;
//  }
//
//  public void setRmbPointRate(BigDecimal rmbPointRate) {
//    this.rmbPointRate = rmbPointRate;
//  }

//  public BigDecimal getSmallUsePoint() {
//    return smallUsePoint;
//  }
//
//  public void setSmallUsePoint(BigDecimal smallUsePoint) {
//    this.smallUsePoint = smallUsePoint;
//  }

}
