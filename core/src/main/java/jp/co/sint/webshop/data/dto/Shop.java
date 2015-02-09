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
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.PrefectureName;
import jp.co.sint.webshop.data.domain.ShopType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「ショップ(SHOP)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Shop implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** ショップ開店日時 */
  @Required
  @Metadata(name = "ショップ開店日時", order = 2)
  private Date openDatetime;

  /** ショップ閉店日時 */
  @Required
  @Metadata(name = "ショップ閉店日時", order = 3)
  private Date closeDatetime;

  /** ショップ名称 */
  @Required
  @Length(30)
  @Metadata(name = "ショップ名称", order = 4)
  private String shopName;

  /** ショップ略名 */
  @Required
  @Length(10)
  @Metadata(name = "ショップ略名", order = 5)
  private String shortShopName;

  /** ショップ紹介URL */
  @Length(256)
  @Url
  @Metadata(name = "ショップ紹介URL", order = 6)
  private String shopIntroducedUrl;

  /** メールアドレス */
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス", order = 7)
  private String email;

  /** 郵便番号 */
  @Length(7)
  @PostalCode
  @Metadata(name = "郵便番号", order = 8)
  private String postalCode;

  /** 都道府県コード */
  @Length(2)
  //@Domain(PrefectureCode.class)
  @Metadata(name = "都道府県コード", order = 9)
  private String prefectureCode;

  /** 住所1 */
  @Length(50)
  @Domain(PrefectureName.class)
  @Metadata(name = "住所1", order = 10)
  private String address1;

  /** 住所2 */
  @Length(50)
  @Metadata(name = "住所2", order = 11)
  private String address2;

  /** 住所3 */
  @Length(50)
  @Metadata(name = "住所3", order = 12)
  private String address3;

  /** 住所4 */
  @Length(100)
  @Metadata(name = "住所4", order = 13)
  private String address4;

  /** 電話番号 */
  @Length(20)
  @Phone
  @Metadata(name = "電話番号", order = 14)
  private String phoneNumber;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码", order = 15)
  private String mobileNumber;
  
  /** 担当者 */
  @Length(20)
  @Metadata(name = "担当者", order = 16)
  private String personInCharge;

  /** SSLページ */
  @Length(256)
  @Metadata(name = "SSLページ", order = 17)
  private String sslPage;

  /** ショップ区分 */
  @Required
  @Length(1)
  @Domain(ShopType.class)
  @Metadata(name = "ショップ区分", order = 18)
  private Long shopType;

  /** 顧客キャンセルフラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "顧客キャンセルフラグ", order = 19)
  private Long customerCancelableFlg;
//2010/04/27 ShiKui Add Start.
  /** ICP登録コード */
  @Required
  @Length(20)
  @Metadata(name = "ICP登録コード", order = 20)
  private String icpCode;
//2010/04/27 ShiKui Add End.  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 21)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 22)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 23)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 24)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 25)
  private Date updatedDatetime;
  
  // add by V10-CH 170 start

  /** 城市コード */
  @Required
  @Length(3)
  @Metadata(name = "城市コード", order = 26)
  private String cityCode;

  // add by V10-CH 170 end
  /**
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * ショップ開店日時を取得します
   *
   * @return ショップ開店日時
   */
  public Date getOpenDatetime() {
    return DateUtil.immutableCopy(this.openDatetime);
  }

  /**
   * ショップ閉店日時を取得します
   *
   * @return ショップ閉店日時
   */
  public Date getCloseDatetime() {
    return DateUtil.immutableCopy(this.closeDatetime);
  }

  /**
   * ショップ名称を取得します
   *
   * @return ショップ名称
   */
  public String getShopName() {
    return this.shopName;
  }

  /**
   * ショップ略名を取得します
   *
   * @return ショップ略名
   */
  public String getShortShopName() {
    return this.shortShopName;
  }

  /**
   * ショップ紹介URLを取得します
   *
   * @return ショップ紹介URL
   */
  public String getShopIntroducedUrl() {
    return this.shopIntroducedUrl;
  }

  /**
   * メールアドレスを取得します
   *
   * @return メールアドレス
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * 郵便番号を取得します
   *
   * @return 郵便番号
   */
  public String getPostalCode() {
    return this.postalCode;
  }

  /**
   * 都道府県コードを取得します
   *
   * @return 都道府県コード
   */
  public String getPrefectureCode() {
    return this.prefectureCode;
  }

  /**
   * 住所1を取得します
   *
   * @return 住所1
   */
  public String getAddress1() {
    return this.address1;
  }

  /**
   * 住所2を取得します
   *
   * @return 住所2
   */
  public String getAddress2() {
    return this.address2;
  }

  /**
   * 住所3を取得します
   *
   * @return 住所3
   */
  public String getAddress3() {
    return this.address3;
  }

  /**
   * 住所4を取得します
   *
   * @return 住所4
   */
  public String getAddress4() {
    return this.address4;
  }

  /**
   * 電話番号を取得します
   *
   * @return 電話番号
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * 担当者を取得します
   *
   * @return 担当者
   */
  public String getPersonInCharge() {
    return this.personInCharge;
  }

  /**
   * SSLページを取得します
   *
   * @return SSLページ
   */
  public String getSslPage() {
    return this.sslPage;
  }

  /**
   * ショップ区分を取得します
   *
   * @return ショップ区分
   */
  public Long getShopType() {
    return this.shopType;
  }

  /**
   * 顧客キャンセルフラグを取得します
   *
   * @return 顧客キャンセルフラグ
   */
  public Long getCustomerCancelableFlg() {
    return this.customerCancelableFlg;
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
   * ショップ開店日時を設定します
   *
   * @param  val ショップ開店日時
   */
  public void setOpenDatetime(Date val) {
    this.openDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * ショップ閉店日時を設定します
   *
   * @param  val ショップ閉店日時
   */
  public void setCloseDatetime(Date val) {
    this.closeDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * ショップ名称を設定します
   *
   * @param  val ショップ名称
   */
  public void setShopName(String val) {
    this.shopName = val;
  }

  /**
   * ショップ略名を設定します
   *
   * @param  val ショップ略名
   */
  public void setShortShopName(String val) {
    this.shortShopName = val;
  }

  /**
   * ショップ紹介URLを設定します
   *
   * @param  val ショップ紹介URL
   */
  public void setShopIntroducedUrl(String val) {
    this.shopIntroducedUrl = val;
  }

  /**
   * メールアドレスを設定します
   *
   * @param  val メールアドレス
   */
  public void setEmail(String val) {
    this.email = val;
  }

  /**
   * 郵便番号を設定します
   *
   * @param  val 郵便番号
   */
  public void setPostalCode(String val) {
    this.postalCode = val;
  }

  /**
   * 都道府県コードを設定します
   *
   * @param  val 都道府県コード
   */
  public void setPrefectureCode(String val) {
    this.prefectureCode = val;
  }

  /**
   * 住所1を設定します
   *
   * @param  val 住所1
   */
  public void setAddress1(String val) {
    this.address1 = val;
  }

  /**
   * 住所2を設定します
   *
   * @param  val 住所2
   */
  public void setAddress2(String val) {
    this.address2 = val;
  }

  /**
   * 住所3を設定します
   *
   * @param  val 住所3
   */
  public void setAddress3(String val) {
    this.address3 = val;
  }

  /**
   * 住所4を設定します
   *
   * @param  val 住所4
   */
  public void setAddress4(String val) {
    this.address4 = val;
  }

  /**
   * 電話番号を設定します
   *
   * @param  val 電話番号
   */
  public void setPhoneNumber(String val) {
    this.phoneNumber = val;
  }

  /**
   * 担当者を設定します
   *
   * @param  val 担当者
   */
  public void setPersonInCharge(String val) {
    this.personInCharge = val;
  }

  /**
   * SSLページを設定します
   *
   * @param  val SSLページ
   */
  public void setSslPage(String val) {
    this.sslPage = val;
  }

  /**
   * ショップ区分を設定します
   *
   * @param  val ショップ区分
   */
  public void setShopType(Long val) {
    this.shopType = val;
  }

  /**
   * 顧客キャンセルフラグを設定します
   *
   * @param  val 顧客キャンセルフラグ
   */
  public void setCustomerCancelableFlg(Long val) {
    this.customerCancelableFlg = val;
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

//2010/04/27 ShiKui Add Start.
  public String getIcpCode() {
    return icpCode;
  }

  
  public void setIcpCode(String icpCode) {
    this.icpCode = icpCode;
  }
//2010/04/27 ShiKui Add End.

  
  public String getCityCode() {
    return cityCode;
  }

  
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  
  /**
   * mobileNumberを取得します。
   *
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * mobileNumberを設定します。
   *
   * @param mobileNumber 
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
}
