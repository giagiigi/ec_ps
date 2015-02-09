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
 * 「顧客統計(CUSTOMER_STATISTICS)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerStatistics implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顧客統計ID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "顧客統計ID", order = 1)
  private Long customerStatisticsId;

  /** 統計グループ */
  @Required
  @Length(100)
  @Metadata(name = "統計グループ", order = 2)
  private String statisticsGroup;

  /** 統計項目 */
  @Required
  @Length(100)
  @Metadata(name = "統計項目", order = 3)
  private String statisticsItem;

  /** 顧客数 */
  @Required
  @Length(8)
  @Metadata(name = "顧客数", order = 4)
  private Long customerAmount;

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
   * 顧客統計IDを取得します
   *
   * @return 顧客統計ID
   */
  public Long getCustomerStatisticsId() {
    return this.customerStatisticsId;
  }

  /**
   * 統計グループを取得します
   *
   * @return 統計グループ
   */
  public String getStatisticsGroup() {
    return this.statisticsGroup;
  }

  /**
   * 統計項目を取得します
   *
   * @return 統計項目
   */
  public String getStatisticsItem() {
    return this.statisticsItem;
  }

  /**
   * 顧客数を取得します
   *
   * @return 顧客数
   */
  public Long getCustomerAmount() {
    return this.customerAmount;
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
   * 顧客統計IDを設定します
   *
   * @param  val 顧客統計ID
   */
  public void setCustomerStatisticsId(Long val) {
    this.customerStatisticsId = val;
  }

  /**
   * 統計グループを設定します
   *
   * @param  val 統計グループ
   */
  public void setStatisticsGroup(String val) {
    this.statisticsGroup = val;
  }

  /**
   * 統計項目を設定します
   *
   * @param  val 統計項目
   */
  public void setStatisticsItem(String val) {
    this.statisticsItem = val;
  }

  /**
   * 顧客数を設定します
   *
   * @param  val 顧客数
   */
  public void setCustomerAmount(Long val) {
    this.customerAmount = val;
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
