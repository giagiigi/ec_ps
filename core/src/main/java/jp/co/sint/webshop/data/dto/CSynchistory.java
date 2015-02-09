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
 * 「アクセスログ(ACCESS_LOG)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CSynchistory implements Serializable, WebshopEntity ,Comparable<CSynchistory> {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "履历批次编号(yyyymmdd0001-yyyymmdd9999)", order = 1)
  private String syncCode;

  @Length(8)
  @Metadata(name = "同期商品总件数", order = 2)
  private Long totalCount;
  
  @Length(8)
  @Metadata(name = "成功件数", order = 3)
  private Long successCount;

  @Length(8)
  @Metadata(name = "失败件数", order = 4)
  private Long failureCount;

  /** ビジター数 */
  @Length(13)
  @Metadata(name = "同期操作者", order = 5)
  private String syncUser;

  /** 購入者数 */
  @Length(26)
  @Metadata(name = "同期开始时间", order = 6)
  private Date syncStarttime;

  /** 購入者数 */
  @Length(8)
  @Metadata(name = "同期结束时间", order = 7)
  private Date syncEndtime;
  
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
  
  public String getSyncCode() {
    return syncCode;
  }

  
  public void setSyncCode(String syncCode) {
    this.syncCode = syncCode;
  }

  
  public Long getTotalCount() {
    return totalCount;
  }

  
  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  
  public Long getSuccessCount() {
    return successCount;
  }

  
  public void setSuccessCount(Long successCount) {
    this.successCount = successCount;
  }

  
  public Long getFailureCount() {
    return failureCount;
  }

  
  public void setFailureCount(Long failureCount) {
    this.failureCount = failureCount;
  }

  
  public String getSyncUser() {
    return syncUser;
  }

  
  public void setSyncUser(String syncUser) {
    this.syncUser = syncUser;
  }

  

  
  /**
   * @return the syncStarttime
   */
  public Date getSyncStarttime() {
    return syncStarttime;
  }


  
  /**
   * @param syncStarttime the syncStarttime to set
   */
  public void setSyncStarttime(Date syncStarttime) {
    this.syncStarttime = syncStarttime;
  }


  
  /**
   * @return the syncEndtime
   */
  public Date getSyncEndtime() {
    return syncEndtime;
  }


  
  /**
   * @param syncEndtime the syncEndtime to set
   */
  public void setSyncEndtime(Date syncEndtime) {
    this.syncEndtime = syncEndtime;
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


  @Override
  public int compareTo(CSynchistory o) {
    return o.getSyncStarttime().getTime()>this.getSyncStarttime().getTime()?1:0;
  }

}
