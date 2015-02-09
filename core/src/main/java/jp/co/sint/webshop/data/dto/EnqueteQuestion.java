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
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「アンケート設問(ENQUETE_QUESTION)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class EnqueteQuestion implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** アンケートコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "アンケートコード", order = 1)
  private String enqueteCode;

  /** アンケート設問番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "アンケート設問番号", order = 2)
  private Long enqueteQuestionNo;

  /** アンケート設問区分 */
  @Required
  @Length(1)
  @Domain(EnqueteQuestionType.class)
  @Metadata(name = "アンケート設問区分", order = 3)
  private Long enqueteQuestionType;

  /** アンケート設問内容 */
  @Required
  @Length(200)
  @Metadata(name = "アンケート設問内容", order = 4)
  private String enqueteQuestionContent;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 5)
  private Long displayOrder;

  /** 必須フラグ */
  @Length(1)
  @Bool
  @Metadata(name = "必須フラグ", order = 6)
  private Long necessaryFlg;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 8)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 9)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 10)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 11)
  private Date updatedDatetime;
  /** アンケート設問内容(英文) */
  @Required
  @Length(200)
  @Metadata(name = "アンケート設問内容(英文)", order = 12)
  private String enqueteQuestionContentEn;
  /** アンケート設問内容(日文) */
  @Required
  @Length(200)
  @Metadata(name = "アンケート設問内容(日文)", order = 13)
  private String enqueteQuestionContentJp;
  /**
   * アンケートコードを取得します
   *
   * @return アンケートコード
   */
  public String getEnqueteCode() {
    return this.enqueteCode;
  }

  /**
   * アンケート設問番号を取得します
   *
   * @return アンケート設問番号
   */
  public Long getEnqueteQuestionNo() {
    return this.enqueteQuestionNo;
  }

  /**
   * アンケート設問区分を取得します
   *
   * @return アンケート設問区分
   */
  public Long getEnqueteQuestionType() {
    return this.enqueteQuestionType;
  }

  /**
   * アンケート設問内容を取得します
   *
   * @return アンケート設問内容
   */
  public String getEnqueteQuestionContent() {
    return this.enqueteQuestionContent;
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
   * 必須フラグを取得します
   *
   * @return 必須フラグ
   */
  public Long getNecessaryFlg() {
    return this.necessaryFlg;
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
   * アンケートコードを設定します
   *
   * @param  val アンケートコード
   */
  public void setEnqueteCode(String val) {
    this.enqueteCode = val;
  }

  /**
   * アンケート設問番号を設定します
   *
   * @param  val アンケート設問番号
   */
  public void setEnqueteQuestionNo(Long val) {
    this.enqueteQuestionNo = val;
  }

  /**
   * アンケート設問区分を設定します
   *
   * @param  val アンケート設問区分
   */
  public void setEnqueteQuestionType(Long val) {
    this.enqueteQuestionType = val;
  }

  /**
   * アンケート設問内容を設定します
   *
   * @param  val アンケート設問内容
   */
  public void setEnqueteQuestionContent(String val) {
    this.enqueteQuestionContent = val;
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
   * 必須フラグを設定します
   *
   * @param  val 必須フラグ
   */
  public void setNecessaryFlg(Long val) {
    this.necessaryFlg = val;
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
 * @param enqueteQuestionContentEn the enqueteQuestionContentEn to set
 */
public void setEnqueteQuestionContentEn(String enqueteQuestionContentEn) {
	this.enqueteQuestionContentEn = enqueteQuestionContentEn;
}

/**
 * @return the enqueteQuestionContentEn
 */
public String getEnqueteQuestionContentEn() {
	return enqueteQuestionContentEn;
}

/**
 * @param enqueteQuestionContentJp the enqueteQuestionContentJp to set
 */
public void setEnqueteQuestionContentJp(String enqueteQuestionContentJp) {
	this.enqueteQuestionContentJp = enqueteQuestionContentJp;
}

/**
 * @return the enqueteQuestionContentJp
 */
public String getEnqueteQuestionContentJp() {
	return enqueteQuestionContentJp;
}

}
