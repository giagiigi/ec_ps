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
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「顧客アドレス帳(CUSTOMER_ADDRESS)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CustomerAddress implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顧客コード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 1)
  private String customerCode;

  /** アドレス帳番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "アドレス帳番号", order = 2)
  private Long addressNo;

  /** アドレス呼称 */
  @Required
  @Length(20)
  @Metadata(name = "アドレス呼称", order = 3)
  private String addressAlias;

  /** 宛名：姓 */
  @Required
  @Length(20)
  @Metadata(name = "宛名：姓", order = 4)
  private String addressLastName;

  /** 宛名：名 */
  @Required
  @Length(20)
  @Metadata(name = "宛名：名", order = 5)
  private String addressFirstName;

  /** 宛名姓カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "宛名姓カナ", order = 6)
  private String addressLastNameKana;

  /** 宛名名カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "宛名名カナ", order = 7)
  private String addressFirstNameKana;

  /** 郵便番号 */
  @Required
  @Length(7)
  @PostalCode
  @Metadata(name = "郵便番号", order = 8)
  private String postalCode;

  /** 都道府県コード */
  // delete by V10-CH 170 start
  // @Required
  // delete by V10-CH 170 end
  @Length(2)
  // delete by V10-CH 170 start
  // @Domain(PrefectureCode.class)
  // delete by V10-CH 170 end
  @Metadata(name = "都道府県コード", order = 9)
  private String prefectureCode;

  /** 住所1 */
  @Required
  @Length(50)
  // 20120106 shen delete start
  // @Domain(PrefectureName.class)
  // 20120106 shen delete end
  @Metadata(name = "住所1", order = 10)
  private String address1;

  /** 住所2 */
  @Required
  @Length(50)
  @Metadata(name = "住所2", order = 11)
  private String address2;

  /** 住所3 */
  // 20120106 shen delete start
  // @Required
  // 20120106 shen delete end
  @Length(50)
  @Metadata(name = "住所3", order = 12)
  private String address3;

  /** 住所4 */
  @Length(100)
  @Metadata(name = "住所4", order = 13)
  private String address4;

  /** 電話番号 */
  @Length(24)
  @Phone
  @Metadata(name = "電話番号", order = 14)
  private String phoneNumber;

  // Add by V10-CH start
  /** 手机番号 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机番号", order = 22)
  private String mobileNumber;

  // Add by V10-CH end
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 15)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 16)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 17)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 18)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 19)
  private Date updatedDatetime;

  // add by V10-CH 170 start
  /** 城市コード */
  @Required
  @Length(3)
  @Metadata(name = "城市コード", order = 21)
  private String cityCode;

  // add by V10-CH 170 end

  // 20120106 shen add start
  @Length(4)
  @Metadata(name = "区县", order = 22)
  private String areaCode;

  // 20120106 shen add end

  /**
   * 顧客コードを取得します
   * 
   * @return 顧客コード
   */
  public String getCustomerCode() {
    return this.customerCode;
  }

  /**
   * アドレス帳番号を取得します
   * 
   * @return アドレス帳番号
   */
  public Long getAddressNo() {
    return this.addressNo;
  }

  /**
   * アドレス呼称を取得します
   * 
   * @return アドレス呼称
   */
  public String getAddressAlias() {
    return this.addressAlias;
  }

  /**
   * 宛名：姓を取得します
   * 
   * @return 宛名：姓
   */
  public String getAddressLastName() {
    return this.addressLastName;
  }

  /**
   * 宛名：名を取得します
   * 
   * @return 宛名：名
   */
  public String getAddressFirstName() {
    return this.addressFirstName;
  }

  /**
   * 宛名姓カナを取得します
   * 
   * @return 宛名姓カナ
   */
  public String getAddressLastNameKana() {
    return this.addressLastNameKana;
  }

  /**
   * 宛名名カナを取得します
   * 
   * @return 宛名名カナ
   */
  public String getAddressFirstNameKana() {
    return this.addressFirstNameKana;
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
   * 顧客コードを設定します
   * 
   * @param val
   *          顧客コード
   */
  public void setCustomerCode(String val) {
    this.customerCode = val;
  }

  /**
   * アドレス帳番号を設定します
   * 
   * @param val
   *          アドレス帳番号
   */
  public void setAddressNo(Long val) {
    this.addressNo = val;
  }

  /**
   * アドレス呼称を設定します
   * 
   * @param val
   *          アドレス呼称
   */
  public void setAddressAlias(String val) {
    this.addressAlias = val;
  }

  /**
   * 宛名：姓を設定します
   * 
   * @param val
   *          宛名：姓
   */
  public void setAddressLastName(String val) {
    this.addressLastName = val;
  }

  /**
   * 宛名：名を設定します
   * 
   * @param val
   *          宛名：名
   */
  public void setAddressFirstName(String val) {
    this.addressFirstName = val;
  }

  /**
   * 宛名姓カナを設定します
   * 
   * @param val
   *          宛名姓カナ
   */
  public void setAddressLastNameKana(String val) {
    this.addressLastNameKana = val;
  }

  /**
   * 宛名名カナを設定します
   * 
   * @param val
   *          宛名名カナ
   */
  public void setAddressFirstNameKana(String val) {
    this.addressFirstNameKana = val;
  }

  /**
   * 郵便番号を設定します
   * 
   * @param val
   *          郵便番号
   */
  public void setPostalCode(String val) {
    this.postalCode = val;
  }

  /**
   * 都道府県コードを設定します
   * 
   * @param val
   *          都道府県コード
   */
  public void setPrefectureCode(String val) {
    this.prefectureCode = val;
  }

  /**
   * 住所1を設定します
   * 
   * @param val
   *          住所1
   */
  public void setAddress1(String val) {
    this.address1 = val;
  }

  /**
   * 住所2を設定します
   * 
   * @param val
   *          住所2
   */
  public void setAddress2(String val) {
    this.address2 = val;
  }

  /**
   * 住所3を設定します
   * 
   * @param val
   *          住所3
   */
  public void setAddress3(String val) {
    this.address3 = val;
  }

  /**
   * 住所4を設定します
   * 
   * @param val
   *          住所4
   */
  public void setAddress4(String val) {
    this.address4 = val;
  }

  /**
   * 電話番号を設定します
   * 
   * @param val
   *          電話番号
   */
  public void setPhoneNumber(String val) {
    this.phoneNumber = val;
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

  /**
   * @return the areaCode
   */
  public String getAreaCode() {
    return areaCode;
  }

  /**
   * @param areaCode
   *          the areaCode to set
   */
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

}
