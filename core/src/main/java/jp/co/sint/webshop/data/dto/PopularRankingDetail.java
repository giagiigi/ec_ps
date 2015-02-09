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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「人気ランキング詳細(POPULAR_RANKING_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class PopularRankingDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 人気ランキング集計ID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "人気ランキング集計ID", order = 1)
  private Long popularRankingCountId;

  /** ショップコード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** 商品コード */
  @PrimaryKey(3)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /** 受注金額ランキング */
  @Required
  @Length(8)
  @Metadata(name = "受注金額ランキング", order = 4)
  private Long orderRanking;

  /** 前回受注金額ランキング */
  @Required
  @Length(8)
  @Metadata(name = "前回受注金額ランキング", order = 5)
  private Long lasttimeOrderRanking;

  /** 購入数量ランキング */
  @Required
  @Length(8)
  @Metadata(name = "購入数量ランキング", order = 6)
  private Long countRanking;

  /** 前回購入数量ランキング */
  @Required
  @Length(8)
  @Metadata(name = "前回購入数量ランキング", order = 7)
  private Long lasttimeCountRanking;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 8)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 9)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 10)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 11)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 12)
  private Date updatedDatetime;

  /**
   * 人気ランキング集計IDを取得します
   *
   * @return 人気ランキング集計ID
   */
  public Long getPopularRankingCountId() {
    return this.popularRankingCountId;
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
   * 受注金額ランキングを取得します
   *
   * @return 受注金額ランキング
   */
  public Long getOrderRanking() {
    return this.orderRanking;
  }

  /**
   * 前回受注金額ランキングを取得します
   *
   * @return 前回受注金額ランキング
   */
  public Long getLasttimeOrderRanking() {
    return this.lasttimeOrderRanking;
  }

  /**
   * 購入数量ランキングを取得します
   *
   * @return 購入数量ランキング
   */
  public Long getCountRanking() {
    return this.countRanking;
  }

  /**
   * 前回購入数量ランキングを取得します
   *
   * @return 前回購入数量ランキング
   */
  public Long getLasttimeCountRanking() {
    return this.lasttimeCountRanking;
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
   * 人気ランキング集計IDを設定します
   *
   * @param  val 人気ランキング集計ID
   */
  public void setPopularRankingCountId(Long val) {
    this.popularRankingCountId = val;
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
   * 受注金額ランキングを設定します
   *
   * @param  val 受注金額ランキング
   */
  public void setOrderRanking(Long val) {
    this.orderRanking = val;
  }

  /**
   * 前回受注金額ランキングを設定します
   *
   * @param  val 前回受注金額ランキング
   */
  public void setLasttimeOrderRanking(Long val) {
    this.lasttimeOrderRanking = val;
  }

  /**
   * 購入数量ランキングを設定します
   *
   * @param  val 購入数量ランキング
   */
  public void setCountRanking(Long val) {
    this.countRanking = val;
  }

  /**
   * 前回購入数量ランキングを設定します
   *
   * @param  val 前回購入数量ランキング
   */
  public void setLasttimeCountRanking(Long val) {
    this.lasttimeCountRanking = val;
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
