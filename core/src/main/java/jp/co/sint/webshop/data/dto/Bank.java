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
import jp.co.sint.webshop.data.attribute.BankKana;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「金融機関(BANK)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Bank implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 支払方法番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "支払方法番号", order = 2)
  private Long paymentMethodNo;

  /** 金融機関コード */
  @PrimaryKey(3)
  @Length(4)
  @AlphaNum2
  @Metadata(name = "金融機関コード", order = 3)
  private String bankCode;

  /** 金融機関支店コード */
  @PrimaryKey(4)
  @Length(3)
  @AlphaNum2
  @Metadata(name = "金融機関支店コード", order = 4)
  private String bankBranchCode;

  /** 口座番号 */
  @PrimaryKey(5)
  @Length(7)
  @Digit
  @Metadata(name = "口座番号", order = 5)
  private String accountNo;

  /** 金融機関名称 */
  @Length(40)
  @Metadata(name = "金融機関名称", order = 6)
  private String bankName;

  /** 金融機関名カナ */
  @Length(40)
  @BankKana
  @Metadata(name = "金融機関名カナ", order = 7)
  private String bankKana;

  /** 金融機関支店名称 */
  @Length(40)
  @Metadata(name = "金融機関支店名称", order = 8)
  private String bankBranchName;

  /** 金融機関支店名カナ */
  @Length(40)
  @BankKana
  @Metadata(name = "金融機関支店名カナ", order = 9)
  private String bankBranchNameKana;

  /** 口座種類 */
  @Length(1)
  @Metadata(name = "口座種類", order = 10)
  private Long accountType;

  /** 口座名義 */
  @Length(40)
  @BankKana
  @Metadata(name = "口座名義", order = 11)
  private String accountName;

  /** SWIFTコード */
  @Length(11)
  @AlphaNum2
  @Metadata(name = "SWIFTコード", order = 12)
  private String swiftCode;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 13)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 14)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 15)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 16)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 17)
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
   * 支払方法番号を取得します
   *
   * @return 支払方法番号
   */
  public Long getPaymentMethodNo() {
    return this.paymentMethodNo;
  }

  /**
   * 金融機関コードを取得します
   *
   * @return 金融機関コード
   */
  public String getBankCode() {
    return this.bankCode;
  }

  /**
   * 金融機関支店コードを取得します
   *
   * @return 金融機関支店コード
   */
  public String getBankBranchCode() {
    return this.bankBranchCode;
  }

  /**
   * 口座番号を取得します
   *
   * @return 口座番号
   */
  public String getAccountNo() {
    return this.accountNo;
  }

  /**
   * 金融機関名称を取得します
   *
   * @return 金融機関名称
   */
  public String getBankName() {
    return this.bankName;
  }

  /**
   * 金融機関名カナを取得します
   *
   * @return 金融機関名カナ
   */
  public String getBankKana() {
    return this.bankKana;
  }

  /**
   * 金融機関支店名称を取得します
   *
   * @return 金融機関支店名称
   */
  public String getBankBranchName() {
    return this.bankBranchName;
  }

  /**
   * 金融機関支店名カナを取得します
   *
   * @return 金融機関支店名カナ
   */
  public String getBankBranchNameKana() {
    return this.bankBranchNameKana;
  }

  /**
   * 口座種類を取得します
   *
   * @return 口座種類
   */
  public Long getAccountType() {
    return this.accountType;
  }

  /**
   * 口座名義を取得します
   *
   * @return 口座名義
   */
  public String getAccountName() {
    return this.accountName;
  }

  /**
   * SWIFTコードを取得します
   *
   * @return SWIFTコード
   */
  public String getSwiftCode() {
    return this.swiftCode;
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
   * 支払方法番号を設定します
   *
   * @param  val 支払方法番号
   */
  public void setPaymentMethodNo(Long val) {
    this.paymentMethodNo = val;
  }

  /**
   * 金融機関コードを設定します
   *
   * @param  val 金融機関コード
   */
  public void setBankCode(String val) {
    this.bankCode = val;
  }

  /**
   * 金融機関支店コードを設定します
   *
   * @param  val 金融機関支店コード
   */
  public void setBankBranchCode(String val) {
    this.bankBranchCode = val;
  }

  /**
   * 口座番号を設定します
   *
   * @param  val 口座番号
   */
  public void setAccountNo(String val) {
    this.accountNo = val;
  }

  /**
   * 金融機関名称を設定します
   *
   * @param  val 金融機関名称
   */
  public void setBankName(String val) {
    this.bankName = val;
  }

  /**
   * 金融機関名カナを設定します
   *
   * @param  val 金融機関名カナ
   */
  public void setBankKana(String val) {
    this.bankKana = val;
  }

  /**
   * 金融機関支店名称を設定します
   *
   * @param  val 金融機関支店名称
   */
  public void setBankBranchName(String val) {
    this.bankBranchName = val;
  }

  /**
   * 金融機関支店名カナを設定します
   *
   * @param  val 金融機関支店名カナ
   */
  public void setBankBranchNameKana(String val) {
    this.bankBranchNameKana = val;
  }

  /**
   * 口座種類を設定します
   *
   * @param  val 口座種類
   */
  public void setAccountType(Long val) {
    this.accountType = val;
  }

  /**
   * 口座名義を設定します
   *
   * @param  val 口座名義
   */
  public void setAccountName(String val) {
    this.accountName = val;
  }

  /**
   * SWIFTコードを設定します
   *
   * @param  val SWIFTコード
   */
  public void setSwiftCode(String val) {
    this.swiftCode = val;
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
