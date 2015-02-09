//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.ReviewDisplayType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「レビュー投稿(REVIEW_POST)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class ReviewPost implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** レビューID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "レビューID", order = 1)
  private Long reviewId;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** 商品コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /** 顧客コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 4)
  private String customerCode;

  /** レビュータイトル */
  @Required
  @Length(50)
  @Metadata(name = "レビュータイトル", order = 5)
  private String reviewTitle;

  /** ニックネーム */
  @Required
  @Length(15)
  @Metadata(name = "ニックネーム", order = 6)
  private String nickname;

  /** レビュー投稿日時 */
  @Required
  @Metadata(name = "レビュー投稿日時", order = 7)
  private Date reviewContributedDatetime;

  /** レビュー内容 */
  @Required
  @Length(2000)
  @Metadata(name = "レビュー内容", order = 8)
  private String reviewDescription;

  /** レビュー点数 */
  @Required
  @Length(3)
  @Metadata(name = "レビュー点数", order = 9)
  private Long reviewScore;

  /** 商品レビュー表示区分 */
  @Required
  @Length(1)
  @Domain(ReviewDisplayType.class)
  @Metadata(name = "商品レビュー表示区分", order = 10)
  private Long reviewDisplayType;

  /** レビューポイント割当ステータス */
  @Length(1)
  @Bool
  @Metadata(name = "レビューポイント割当ステータス", order = 11)
  private Long reviewPointAllocatedStatus;
  
  //20111219 os013 add start
  /** 受注履歴ID */
  @Length(16)
  @Metadata(name = "受注履歴ID", order = 12)
  private String orderNo;
  //20111219 os013 add end
  
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
   * レビューIDを取得します
   *
   * @return レビューID
   */
  public Long getReviewId() {
    return this.reviewId;
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
   * 商品コードを取得します
   *
   * @return 商品コード
   */
  public String getCommodityCode() {
    return this.commodityCode;
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
   * レビュータイトルを取得します
   *
   * @return レビュータイトル
   */
  public String getReviewTitle() {
    return this.reviewTitle;
  }

  /**
   * ニックネームを取得します
   *
   * @return ニックネーム
   */
  public String getNickname() {
    return this.nickname;
  }

  /**
   * レビュー投稿日時を取得します
   *
   * @return レビュー投稿日時
   */
  public Date getReviewContributedDatetime() {
    return DateUtil.immutableCopy(this.reviewContributedDatetime);
  }

  /**
   * レビュー内容を取得します
   *
   * @return レビュー内容
   */
  public String getReviewDescription() {
    return this.reviewDescription;
  }

  /**
   * レビュー点数を取得します
   *
   * @return レビュー点数
   */
  public Long getReviewScore() {
    return this.reviewScore;
  }

  /**
   * 商品レビュー表示区分を取得します
   *
   * @return 商品レビュー表示区分
   */
  public Long getReviewDisplayType() {
    return this.reviewDisplayType;
  }

  /**
   * レビューポイント割当ステータスを取得します
   *
   * @return レビューポイント割当ステータス
   */
  public Long getReviewPointAllocatedStatus() {
    return this.reviewPointAllocatedStatus;
  }

  //20111219 os013 add start
  /**
   * 受注履歴IDを取得します
   *
   * @return 受注履歴ID
   */
  public String getOrderNo() {
    return orderNo;
  } 
  //20111219 os013 add end
  
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
   * レビューIDを設定します
   *
   * @param  val レビューID
   */
  public void setReviewId(Long val) {
    this.reviewId = val;
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
   * 商品コードを設定します
   *
   * @param  val 商品コード
   */
  public void setCommodityCode(String val) {
    this.commodityCode = val;
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
   * レビュータイトルを設定します
   *
   * @param  val レビュータイトル
   */
  public void setReviewTitle(String val) {
    this.reviewTitle = val;
  }

  /**
   * ニックネームを設定します
   *
   * @param  val ニックネーム
   */
  public void setNickname(String val) {
    this.nickname = val;
  }

  /**
   * レビュー投稿日時を設定します
   *
   * @param  val レビュー投稿日時
   */
  public void setReviewContributedDatetime(Date val) {
    this.reviewContributedDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * レビュー内容を設定します
   *
   * @param  val レビュー内容
   */
  public void setReviewDescription(String val) {
    this.reviewDescription = val;
  }

  /**
   * レビュー点数を設定します
   *
   * @param  val レビュー点数
   */
  public void setReviewScore(Long val) {
    this.reviewScore = val;
  }

  /**
   * 商品レビュー表示区分を設定します
   *
   * @param  val 商品レビュー表示区分
   */
  public void setReviewDisplayType(Long val) {
    this.reviewDisplayType = val;
  }

  /**
   * レビューポイント割当ステータスを設定します
   *
   * @param  val レビューポイント割当ステータス
   */
  public void setReviewPointAllocatedStatus(Long val) {
    this.reviewPointAllocatedStatus = val;
  }
  
  //20111219 os013 add start
  /**
   * 受注履歴IDを設定します
   *
   * @param  val 受注履歴ID
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  //20111219 os013 add end
  
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
