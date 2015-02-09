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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.SmsType;
import jp.co.sint.webshop.utility.DateUtil;

public class SmsTemplateDetail implements Serializable, WebshopEntity {

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
  @Domain(SmsType.class)
  @Metadata(name = "メールタイプ", order = 2)
  private String smsType;

  /** メールテンプレート番号 */
  @PrimaryKey(3)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "メールテンプレート番号", order = 3)
  private Long smsTemplateNo;

  /** メール本文 */
  @Metadata(name = "メール本文", order = 4)
  private String smsText;
  
  //2013/05/05 优惠券对应 ob add start
  /** メール本文 */
  @Metadata(name = "メール本文EN", order = 4)
  private String smsTextEn;
  
  /** メール本文 */
  @Metadata(name = "メール本文JP", order = 4)
  private String smsTextJp;
 //2013/05/05 优惠券对应 ob add end
  
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

  /** 是否使用 */
  @Required
  @Metadata(name = "是否使用", order = 10)
  private Long smsUseFlg;

  private String smsCompositionName;

  private String templateTag;

  /**
   * sendeNameを取得します。
   * 
   * @return sendeName sendeName
   */
  public String getSenderName() {
    return updatedUser;
  }

  /**
   * templateTagを取得します。
   * 
   * @return templateTag templateTag
   */
  public String getTemplateTag() {
    return templateTag;
  }

  /**
   * templateTagを設定します。
   * 
   * @param templateTag
   *          templateTag
   */
  public void setTemplateTag(String templateTag) {
    this.templateTag = templateTag;
  }

  /**
   * smsCompositionNameを取得します。
   * 
   * @return smsCompositionName smsCompositionName
   */
  public String getSmsCompositionName() {
    return smsCompositionName;
  }

  /**
   * smsCompositionNameを設定します。
   * 
   * @param smsCompositionName
   *          smsCompositionName
   */
  public void setSmsCompositionName(String smsCompositionName) {
    this.smsCompositionName = smsCompositionName;
  }

  /**
   * smsUseFlgを取得します。
   * 
   * @return smsUseFlg smsUseFlg
   */
  public Long getSmsUseFlg() {
    return smsUseFlg;
  }

  /**
   * smsUseFlgを設定します。
   * 
   * @param smsUseFlg
   *          smsUseFlg
   */
  public void setSmsUseFlg(Long smsUseFlg) {
    this.smsUseFlg = smsUseFlg;
  }

  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * メールタイプの设定です
   * 
   * @return メールタイプです
   */
  public String getSmsType() {
    return this.smsType;
  }

  /**
   * メールをテンプレート番号を设定して下さい
   * 
   * @return メールをテンプレートの番号だった
   */
  public Long getSmsTemplateNo() {
    return this.smsTemplateNo;
  }

  /**
   * メール本文を设定して下さい
   * 
   * @return メール本文
   */
  public String getSmsText() {
    return this.smsText;
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
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * メールタイプの设定です
   * 
   * @param val
   *          メールタイプです
   */
  public void setSmsType(String val) {
    this.smsType = val;
  }

  /**
   * メールをテンプレート番号を设定して下さい
   * 
   * @param val
   *          メールをテンプレートの番号だった
   */
  public void setSmsTemplateNo(Long val) {
    this.smsTemplateNo = val;
  }

  /**
   * メール本文を设定して下さい
   * 
   * @param val
   *          メール本文
   */
  public void setSmsText(String val) {
    this.smsText = val;
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

  //2013/05/05 优惠券对应 ob add start
  /**
   * メール本文EN
   */
  public String getSmsTextEn() {
    return smsTextEn;
  }

  /**
   * メール本文EN
   * @param smsTextEn
   */
  public void setSmsTextEn(String smsTextEn) {
    this.smsTextEn = smsTextEn;
  }

  /**
   *   メール本文JP
   * @return
   */
  public String getSmsTextJp() {
    return smsTextJp;
  }

  /**
   * メール本文JP
   * @param smsTextJp
   */
  public void setSmsTextJp(String smsTextJp) {
    this.smsTextJp = smsTextJp;
  }
  //2013/05/05 优惠券对应 ob add end
}
