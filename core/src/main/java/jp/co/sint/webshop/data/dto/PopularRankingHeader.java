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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「人気ランキングヘッダ(POPULAR_RANKING_HEADER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class PopularRankingHeader implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 人気ランキング集計ID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "人気ランキング集計ID", order = 1)
  private Long popularRankingCountId;

  /** 集計日時 */
  @Metadata(name = "集計日時", order = 2)
  private Date countDatetime;

  /** 集計期間開始日 */
  @Metadata(name = "集計期間開始日", order = 3)
  private Date countTermStartDate;

  /** 集計期間終了日 */
  @Metadata(name = "集計期間終了日", order = 4)
  private Date countTermEndDate;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 5)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 6)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 7)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 8)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 9)
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
   * 集計日時を取得します
   *
   * @return 集計日時
   */
  public Date getCountDatetime() {
    return DateUtil.immutableCopy(this.countDatetime);
  }

  /**
   * 集計期間開始日を取得します
   *
   * @return 集計期間開始日
   */
  public Date getCountTermStartDate() {
    return DateUtil.immutableCopy(this.countTermStartDate);
  }

  /**
   * 集計期間終了日を取得します
   *
   * @return 集計期間終了日
   */
  public Date getCountTermEndDate() {
    return DateUtil.immutableCopy(this.countTermEndDate);
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
   * 集計日時を設定します
   *
   * @param  val 集計日時
   */
  public void setCountDatetime(Date val) {
    this.countDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 集計期間開始日を設定します
   *
   * @param  val 集計期間開始日
   */
  public void setCountTermStartDate(Date val) {
    this.countTermStartDate = DateUtil.immutableCopy(val);
  }

  /**
   * 集計期間終了日を設定します
   *
   * @param  val 集計期間終了日
   */
  public void setCountTermEndDate(Date val) {
    this.countTermEndDate = DateUtil.immutableCopy(val);
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
