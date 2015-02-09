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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「メールテンプレート明細(MAIL_TEMPLATE_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class MailTemplateDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** メールタイプ */
  @PrimaryKey(2)
  @Required
  @Length(2)
  @Domain(MailType.class)
  @Metadata(name = "メールタイプ", order = 2)
  private String mailType;

  /** メールテンプレート番号 */
  @PrimaryKey(3)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "メールテンプレート番号", order = 3)
  private Long mailTemplateNo;

  /** メールテンプレート枝番 */
  @PrimaryKey(4)
  @Required
  @Length(3)
  @Metadata(name = "メールテンプレート枝番", order = 4)
  private Long mailTemplateBranchNo;

  /** 親メールテンプレート枝番 */
  @Length(3)
  @Metadata(name = "親メールテンプレート枝番", order = 5)
  private Long parentMailTemplateBranchNo;

  /** メールテンプレート階層 */
  @Length(1)
  @Metadata(name = "メールテンプレート階層", order = 6)
  private Long mailTemplateDepth;

  /** メール本文 */
  @Metadata(name = "メール本文", order = 7)
  private String mailText;

  /** メール構成名称 */
  @Length(20)
  @Metadata(name = "メール構成名称", order = 8)
  private String mailCompositionName;

  /** 置換タグ */
  @Length(35)
  @Metadata(name = "置換タグ", order = 9)
  private String substitutionTag;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 10)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 11)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 12)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 13)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 14)
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
   * メールタイプを取得します
   *
   * @return メールタイプ
   */
  public String getMailType() {
    return this.mailType;
  }

  /**
   * メールテンプレート番号を取得します
   *
   * @return メールテンプレート番号
   */
  public Long getMailTemplateNo() {
    return this.mailTemplateNo;
  }

  /**
   * メールテンプレート枝番を取得します
   *
   * @return メールテンプレート枝番
   */
  public Long getMailTemplateBranchNo() {
    return this.mailTemplateBranchNo;
  }

  /**
   * 親メールテンプレート枝番を取得します
   *
   * @return 親メールテンプレート枝番
   */
  public Long getParentMailTemplateBranchNo() {
    return this.parentMailTemplateBranchNo;
  }

  /**
   * メールテンプレート階層を取得します
   *
   * @return メールテンプレート階層
   */
  public Long getMailTemplateDepth() {
    return this.mailTemplateDepth;
  }

  /**
   * メール本文を取得します
   *
   * @return メール本文
   */
  public String getMailText() {
    return this.mailText;
  }

  /**
   * メール構成名称を取得します
   *
   * @return メール構成名称
   */
  public String getMailCompositionName() {
    return this.mailCompositionName;
  }

  /**
   * 置換タグを取得します
   *
   * @return 置換タグ
   */
  public String getSubstitutionTag() {
    return this.substitutionTag;
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
   * メールタイプを設定します
   *
   * @param  val メールタイプ
   */
  public void setMailType(String val) {
    this.mailType = val;
  }

  /**
   * メールテンプレート番号を設定します
   *
   * @param  val メールテンプレート番号
   */
  public void setMailTemplateNo(Long val) {
    this.mailTemplateNo = val;
  }

  /**
   * メールテンプレート枝番を設定します
   *
   * @param  val メールテンプレート枝番
   */
  public void setMailTemplateBranchNo(Long val) {
    this.mailTemplateBranchNo = val;
  }

  /**
   * 親メールテンプレート枝番を設定します
   *
   * @param  val 親メールテンプレート枝番
   */
  public void setParentMailTemplateBranchNo(Long val) {
    this.parentMailTemplateBranchNo = val;
  }

  /**
   * メールテンプレート階層を設定します
   *
   * @param  val メールテンプレート階層
   */
  public void setMailTemplateDepth(Long val) {
    this.mailTemplateDepth = val;
  }

  /**
   * メール本文を設定します
   *
   * @param  val メール本文
   */
  public void setMailText(String val) {
    this.mailText = val;
  }

  /**
   * メール構成名称を設定します
   *
   * @param  val メール構成名称
   */
  public void setMailCompositionName(String val) {
    this.mailCompositionName = val;
  }

  /**
   * 置換タグを設定します
   *
   * @param  val 置換タグ
   */
  public void setSubstitutionTag(String val) {
    this.substitutionTag = val;
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
