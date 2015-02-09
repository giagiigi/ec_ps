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
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「TMALL库存比率分配表(TmallStockAllocation)」テーブルの1行分のレコードを表すDTO(Data Transfer
 * Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CustomerCommodity implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商品コード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 1)
  private String commodityCode;

  /** 顾客编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顾客编号", order = 2)
  private String customerCode;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 3)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 4)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 5)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 6)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 7)
  private Date updatedDatetime;
  
  @Required
  @Length(5)
  @Metadata(name = "语言编号", order = 8)
  private String languageCode;
  
  /** 商品名称 */
  @Required
  @Length(200)
  @Metadata(name = "商品名称", order = 9)
  private String commodityName;
  
  /** 姓 */
  @Required
  @Length(20)
  @Metadata(name = "姓", order = 3)
  private String customerName;
  
  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス", order = 8)
  private String email;


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
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
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
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  
  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  
  /**
   * @param languageCode the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  
  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  
  /**
   * @param commodityName the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  
  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  
  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   * @return the customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  
  /**
   * @param customerName the customerName to set
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

}
