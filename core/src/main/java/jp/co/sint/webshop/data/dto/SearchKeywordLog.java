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
 * 「検索キーワードログ(SEARCH_KEYWORD_LOG)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class SearchKeywordLog implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 検索キーワードログID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "検索キーワードログID", order = 1)
  private Long searchKeywordLogId;

  /** 検索日 */
  @Required
  @Metadata(name = "検索日", order = 2)
  private Date searchDate;

  /** 検索キー */
  @Required
  @Length(256)
  @Metadata(name = "検索キー", order = 3)
  private String searchKey;

  /** 検索内容 */
  @Required
  @Length(256)
  @Metadata(name = "検索内容", order = 4)
  private String searchWord;

  /** 検索件数 */
  @Required
  @Length(8)
  @Metadata(name = "検索件数", order = 5)
  private Long searchCount;

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
   * 検索キーワードログIDを取得します
   *
   * @return 検索キーワードログID
   */
  public Long getSearchKeywordLogId() {
    return this.searchKeywordLogId;
  }

  /**
   * 検索日を取得します
   *
   * @return 検索日
   */
  public Date getSearchDate() {
    return DateUtil.immutableCopy(this.searchDate);
  }

  /**
   * 検索キーを取得します
   *
   * @return 検索キー
   */
  public String getSearchKey() {
    return this.searchKey;
  }

  /**
   * 検索内容を取得します
   *
   * @return 検索内容
   */
  public String getSearchWord() {
    return this.searchWord;
  }

  /**
   * 検索件数を取得します
   *
   * @return 検索件数
   */
  public Long getSearchCount() {
    return this.searchCount;
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
   * 検索キーワードログIDを設定します
   *
   * @param  val 検索キーワードログID
   */
  public void setSearchKeywordLogId(Long val) {
    this.searchKeywordLogId = val;
  }

  /**
   * 検索日を設定します
   *
   * @param  val 検索日
   */
  public void setSearchDate(Date val) {
    this.searchDate = DateUtil.immutableCopy(val);
  }

  /**
   * 検索キーを設定します
   *
   * @param  val 検索キー
   */
  public void setSearchKey(String val) {
    this.searchKey = val;
  }

  /**
   * 検索内容を設定します
   *
   * @param  val 検索内容
   */
  public void setSearchWord(String val) {
    this.searchWord = val;
  }

  /**
   * 検索件数を設定します
   *
   * @param  val 検索件数
   */
  public void setSearchCount(Long val) {
    this.searchCount = val;
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
