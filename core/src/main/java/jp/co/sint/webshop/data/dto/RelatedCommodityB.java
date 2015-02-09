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
 * 「自動リコメンド(RELATED_COMMODITY_B)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class RelatedCommodityB implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 商品コード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 2)
  private String commodityCode;

  /** リンクショップコード */
  @PrimaryKey(3)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "リンクショップコード", order = 3)
  private String linkShopCode;

  /** リンク商品コード */
  @PrimaryKey(4)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "リンク商品コード", order = 4)
  private String linkCommodityCode;

  /** ランキング点数 */
  @Length(8)
  @Metadata(name = "ランキング点数", order = 5)
  private Long rankingScore;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 6)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 7)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 8)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 9)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 10)
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
   * 商品コードを取得します
   *
   * @return 商品コード
   */
  public String getCommodityCode() {
    return this.commodityCode;
  }

  /**
   * リンクショップコードを取得します
   *
   * @return リンクショップコード
   */
  public String getLinkShopCode() {
    return this.linkShopCode;
  }

  /**
   * リンク商品コードを取得します
   *
   * @return リンク商品コード
   */
  public String getLinkCommodityCode() {
    return this.linkCommodityCode;
  }

  /**
   * ランキング点数を取得します
   *
   * @return ランキング点数
   */
  public Long getRankingScore() {
    return this.rankingScore;
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
   * リンクショップコードを設定します
   *
   * @param  val リンクショップコード
   */
  public void setLinkShopCode(String val) {
    this.linkShopCode = val;
  }

  /**
   * リンク商品コードを設定します
   *
   * @param  val リンク商品コード
   */
  public void setLinkCommodityCode(String val) {
    this.linkCommodityCode = val;
  }

  /**
   * ランキング点数を設定します
   *
   * @param  val ランキング点数
   */
  public void setRankingScore(Long val) {
    this.rankingScore = val;
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
