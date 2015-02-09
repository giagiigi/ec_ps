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
 * 「タグ(TAG)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Tag implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** タグコード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "タグコード", order = 2)
  private String tagCode;

  /** タグ名称 */
  @Required
  @Length(50)
  @Metadata(name = "タグ名称", order = 3)
  private String tagName;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 4)
  private Long displayOrder;

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

  //20120514 tuxinwei add start
  /** タグ名称(英文) */
  @Required
  @Length(50)
  @Metadata(name = "タグ名称(英文)", order = 10)
  private String tagNameEn;
  
  /** タグ名称(日本语) */
  @Required
  @Length(50)
  @Metadata(name = "タグ名称(日本语)", order = 11)
  private String tagNameJp;
  //20120514 tuxinwei add end
  /**
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * タグコードを取得します
   *
   * @return タグコード
   */
  public String getTagCode() {
    return this.tagCode;
  }

  /**
   * タグ名称を取得します
   *
   * @return タグ名称
   */
  public String getTagName() {
    return this.tagName;
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
   * ショップコードを設定します
   *
   * @param  val ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * タグコードを設定します
   *
   * @param  val タグコード
   */
  public void setTagCode(String val) {
    this.tagCode = val;
  }

  /**
   * タグ名称を設定します
   *
   * @param  val タグ名称
   */
  public void setTagName(String val) {
    this.tagName = val;
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

  
  /**
   * @return the tagNameEn
   */
  public String getTagNameEn() {
    return tagNameEn;
  }

  
  /**
   * @param tagNameEn the tagNameEn to set
   */
  public void setTagNameEn(String tagNameEn) {
    this.tagNameEn = tagNameEn;
  }

  
  /**
   * @return the tagNameJp
   */
  public String getTagNameJp() {
    return tagNameJp;
  }

  
  /**
   * @param tagNameJp the tagNameJp to set
   */
  public void setTagNameJp(String tagNameJp) {
    this.tagNameJp = tagNameJp;
  } 

}
