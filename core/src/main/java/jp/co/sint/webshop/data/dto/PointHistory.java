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
import java.math.RoundingMode;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PointUtil;

/** 
 * 「ポイント履歴(POINT_HISTORY)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class PointHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ポイント履歴ID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "ポイント履歴ID", order = 1)
  private Long pointHistoryId;

  /** ポイント発行日時 */
  @Required
  @Metadata(name = "ポイント発行日時", order = 2)
  private Date pointIssueDatetime;

  /** 顧客コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 3)
  private String customerCode;

  /** ポイント発行ステータス */
  @Required
  @Length(1)
  @Domain(PointIssueStatus.class)
  @Metadata(name = "ポイント発行ステータス", order = 4)
  private Long pointIssueStatus;

  /** ポイント発行区分 */
  @Required
  @Length(1)
  @Metadata(name = "ポイント発行区分", order = 5)
  private Long pointIssueType;

  /** 受注番号 */
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 6)
  private String orderNo;

  /** レビューID */
  @Length(38)
  @Metadata(name = "レビューID", order = 7)
  private Long reviewId;

  /** アンケートコード */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "アンケートコード", order = 8)
  private String enqueteCode;

  /** ポイント付与行使理由 */
  @Length(100)
  @Metadata(name = "ポイント付与行使理由", order = 9)
  private String description;

  /** 発行ポイント */
  @Required
  //@Precision(precision = 10, scale = 2)
  @Point
  @Metadata(name = "発行ポイント", order = 10)
  private BigDecimal issuedPoint;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 11)
  private String shopCode;

  /** ポイント利用日時 */
  @Metadata(name = "ポイント利用日時", order = 12)
  private Date pointUsedDatetime;

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
   * ポイント履歴IDを取得します
   *
   * @return ポイント履歴ID
   */
  public Long getPointHistoryId() {
    return this.pointHistoryId;
  }

  /**
   * ポイント発行日時を取得します
   *
   * @return ポイント発行日時
   */
  public Date getPointIssueDatetime() {
    return DateUtil.immutableCopy(this.pointIssueDatetime);
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
   * ポイント発行ステータスを取得します
   *
   * @return ポイント発行ステータス
   */
  public Long getPointIssueStatus() {
    return this.pointIssueStatus;
  }

  /**
   * ポイント発行区分を取得します
   *
   * @return ポイント発行区分
   */
  public Long getPointIssueType() {
    return this.pointIssueType;
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
   * レビューIDを取得します
   *
   * @return レビューID
   */
  public Long getReviewId() {
    return this.reviewId;
  }

  /**
   * アンケートコードを取得します
   *
   * @return アンケートコード
   */
  public String getEnqueteCode() {
    return this.enqueteCode;
  }

  /**
   * ポイント付与行使理由を取得します
   *
   * @return ポイント付与行使理由
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * 発行ポイントを取得します
   *
   * @return 発行ポイント
   */
  public BigDecimal getIssuedPoint() {
    return this.issuedPoint;
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
   * ポイント利用日時を取得します
   *
   * @return ポイント利用日時
   */
  public Date getPointUsedDatetime() {
    return DateUtil.immutableCopy(this.pointUsedDatetime);
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
   * ポイント履歴IDを設定します
   *
   * @param  val ポイント履歴ID
   */
  public void setPointHistoryId(Long val) {
    this.pointHistoryId = val;
  }

  /**
   * ポイント発行日時を設定します
   *
   * @param  val ポイント発行日時
   */
  public void setPointIssueDatetime(Date val) {
    this.pointIssueDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 顧客コードを設定します
   *
   * @param  val 顧客コード
   */
  public void setCustomerCode(String val) {
    this.customerCode = val;
  }

  /**
   * ポイント発行ステータスを設定します
   *
   * @param  val ポイント発行ステータス
   */
  public void setPointIssueStatus(Long val) {
    this.pointIssueStatus = val;
  }

  /**
   * ポイント発行区分を設定します
   *
   * @param  val ポイント発行区分
   */
  public void setPointIssueType(Long val) {
    this.pointIssueType = val;
  }

  /**
   * 受注番号を設定します
   *
   * @param  val 受注番号
   */
  public void setOrderNo(String val) {
    this.orderNo = val;
  }

  /**
   * レビューIDを設定します
   *
   * @param  val レビューID
   */
  public void setReviewId(Long val) {
    this.reviewId = val;
  }

  /**
   * アンケートコードを設定します
   *
   * @param  val アンケートコード
   */
  public void setEnqueteCode(String val) {
    this.enqueteCode = val;
  }

  /**
   * ポイント付与行使理由を設定します
   *
   * @param  val ポイント付与行使理由
   */
  public void setDescription(String val) {
    this.description = val;
  }

  /**
   * 発行ポイントを設定します
   *
   * @param  val 発行ポイント
   */
  public void setIssuedPoint(BigDecimal val) {
    this.issuedPoint = val.setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
  }

  /**
   * ショップコードを設定します
   *
   * @param  val ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * ポイント利用日時を設定します
   *
   * @param  val ポイント利用日時
   */
  public void setPointUsedDatetime(Date val) {
    this.pointUsedDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * データ行IDを設定します
   *
   * @param  val データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   *
   * @param  val 作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   *
   * @param  val 作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   *
   * @param  val 更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   *
   * @param  val 更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

}
