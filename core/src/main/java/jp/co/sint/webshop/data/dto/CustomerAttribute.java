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
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「顧客属性(CUSTOMER_ATTRIBUTE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerAttribute implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顧客属性番号 */
  @PrimaryKey(1)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "顧客属性番号", order = 1)
  private Long customerAttributeNo;

  /** 顧客属性名称 */
  @Required
  @Length(40)
  @Metadata(name = "顧客属性名称", order = 2)
  private String customerAttributeName;

  /** 顧客属性区分 */
  @Required
  @Length(1)
  @Domain(CustomerAttributeType.class)
  @Metadata(name = "顧客属性区分", order = 3)
  private Long customerAttributeType;

  /** 表示フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "表示フラグ", order = 4)
  private Long displayFlg;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 5)
  private Long displayOrder;

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
   * 顧客属性番号を取得します
   *
   * @return 顧客属性番号
   */
  public Long getCustomerAttributeNo() {
    return this.customerAttributeNo;
  }

  /**
   * 顧客属性名称を取得します
   *
   * @return 顧客属性名称
   */
  public String getCustomerAttributeName() {
    return this.customerAttributeName;
  }

  /**
   * 顧客属性区分を取得します
   *
   * @return 顧客属性区分
   */
  public Long getCustomerAttributeType() {
    return this.customerAttributeType;
  }

  /**
   * 表示フラグを取得します
   *
   * @return 表示フラグ
   */
  public Long getDisplayFlg() {
    return this.displayFlg;
  }

  /**
   * 表示順を取得します
   *
   * @return 表示順
   */
  public Long getDisplayOrder() {
    return this.displayOrder;
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
   * 顧客属性番号を設定します
   *
   * @param  val 顧客属性番号
   */
  public void setCustomerAttributeNo(Long val) {
    this.customerAttributeNo = val;
  }

  /**
   * 顧客属性名称を設定します
   *
   * @param  val 顧客属性名称
   */
  public void setCustomerAttributeName(String val) {
    this.customerAttributeName = val;
  }

  /**
   * 顧客属性区分を設定します
   *
   * @param  val 顧客属性区分
   */
  public void setCustomerAttributeType(Long val) {
    this.customerAttributeType = val;
  }

  /**
   * 表示フラグを設定します
   *
   * @param  val 表示フラグ
   */
  public void setDisplayFlg(Long val) {
    this.displayFlg = val;
  }

  /**
   * 表示順を設定します
   *
   * @param  val 表示順
   */
  public void setDisplayOrder(Long val) {
    this.displayOrder = val;
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
