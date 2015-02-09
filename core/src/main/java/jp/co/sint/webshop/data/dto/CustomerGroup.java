//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
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
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「顧客グループ(CUSTOMER_GROUP)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroup implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顧客グループコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客グループコード", order = 1)
  private String customerGroupCode;

  /** 顧客グループ名称 */
  @Required
  @Length(40)
  @Metadata(name = "顧客グループ名称", order = 2)
  private String customerGroupName;

  /** 顧客グループポイント率 */
  @Required
  @Length(3)
  @Percentage
  @Metadata(name = "顧客グループポイント率", order = 3)
  private Long customerGroupPointRate;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 4)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 5)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 6)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 7)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 8)
  private Date updatedDatetime;

  // 20120515 shen add start
  /** 顾客组名称(英文) */
  @Length(40)
  @Metadata(name = "顾客组名称(英文)", order = 9)
  private String customerGroupNameEn;

  /** 顾客组名称(日文) */
  @Length(40)
  @Metadata(name = "顾客组名称(日文)", order = 10)
  private String customerGroupNameJp;

  // 20120515 shen add end

  /**
   * 顧客グループコードを取得します
   * 
   * @return 顧客グループコード
   */
  public String getCustomerGroupCode() {
    return this.customerGroupCode;
  }

  /**
   * 顧客グループ名称を取得します
   * 
   * @return 顧客グループ名称
   */
  public String getCustomerGroupName() {
    return this.customerGroupName;
  }

  /**
   * 顧客グループポイント率を取得します
   * 
   * @return 顧客グループポイント率
   */
  public Long getCustomerGroupPointRate() {
    return this.customerGroupPointRate;
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
   * 顧客グループコードを設定します
   * 
   * @param val
   *          顧客グループコード
   */
  public void setCustomerGroupCode(String val) {
    this.customerGroupCode = val;
  }

  /**
   * 顧客グループ名称を設定します
   * 
   * @param val
   *          顧客グループ名称
   */
  public void setCustomerGroupName(String val) {
    this.customerGroupName = val;
  }

  /**
   * 顧客グループポイント率を設定します
   * 
   * @param val
   *          顧客グループポイント率
   */
  public void setCustomerGroupPointRate(Long val) {
    this.customerGroupPointRate = val;
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

  /**
   * @return the customerGroupNameEn
   */
  public String getCustomerGroupNameEn() {
    return customerGroupNameEn;
  }

  /**
   * @param customerGroupNameEn
   *          the customerGroupNameEn to set
   */
  public void setCustomerGroupNameEn(String customerGroupNameEn) {
    this.customerGroupNameEn = customerGroupNameEn;
  }

  /**
   * @return the customerGroupNameJp
   */
  public String getCustomerGroupNameJp() {
    return customerGroupNameJp;
  }

  /**
   * @param customerGroupNameJp
   *          the customerGroupNameJp to set
   */
  public void setCustomerGroupNameJp(String customerGroupNameJp) {
    this.customerGroupNameJp = customerGroupNameJp;
  }

}
