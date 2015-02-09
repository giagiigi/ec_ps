package jp.co.sint.webshop.configure;

import java.io.Serializable;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 顧客情報登録時に表示させるサンプルデータです。 applicationContext-configure.xmlの内容を読み込んで初期化します。
 * 
 * @author System Integrator Corp.
 */
public class ExampleValue implements Serializable {

  private static final long serialVersionUID = 1L;

  /* 配送先呼称 */
  private String addressAlias;

  /* 氏名（名） */
  private String lastName;

  /* 氏名（姓） */
  private String firstName;

  /* 氏名（名：カタカナ） */
  private String lastNameKana;

  /* 氏名（姓：カタカナ） */
  private String firstNameKana;

  /* 郵便番号 */
  private String postalCode;

  /* 都道府県 */
  private String address1;

  /* 市区町村 */
  private String address2;

  /* 町名・番地 */
  private String address3;

  /* アパート・マンション・ビル */
  private String address4;

  /* 電話番号 */
  private String phoneNumber;
  
  //Add by V10-CH start
  /* 手机号码 */ 
  private String mobileNumber;
  //Add by V10-CH end
  
  /* 生年月日 */
  private String birthDate;

  /* 性別 */
  private String sex;

  /* E-mailアドレス */
  private String email;

  /**
   * @return 配送先呼称
   */
  public String getAddressAlias() {
    return StringUtil.coalesce(CodeUtil.getEntry("addressAlias"), addressAlias);
  }

  /**
   * @param addressAlias
   *          配送先呼称
   */
  public void setAddressAlias(String addressAlias) {
    this.addressAlias = addressAlias;
  }

  /**
   * @return 氏名（名）
   */
  public String getLastName() {
    return StringUtil.coalesce(CodeUtil.getEntry("lastName"), lastName);
  }

  /**
   * @param lastName
   *          氏名（名）
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return 氏名（姓）
   */
  public String getFirstName() {
    return StringUtil.coalesce(CodeUtil.getEntry("firstName"), firstName);
  }

  /**
   * @param firstName
   *          氏名（姓）
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return 氏名（名：カタカナ）
   */
  public String getLastNameKana() {
    return StringUtil.coalesce(CodeUtil.getEntry("lastNameKana"), lastNameKana);
  }

  /**
   * @param lastNameKana
   *          氏名（名：カタカナ）
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }

  /**
   * @return 氏名（姓：カタカナ）
   */
  public String getFirstNameKana() {
    return StringUtil.coalesce(CodeUtil.getEntry("firstNameKana"), firstNameKana);
  }

  /**
   * @param firstNameKana
   *          氏名（姓：カタカナ）
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
  }

  /**
   * @return 郵便番号
   */
  public String getPostalCode() {
    return StringUtil.coalesce(CodeUtil.getEntry("postalCode"), postalCode);
  }

  /**
   * @param postalCode
   *          郵便番号
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return 都道府県
   */
  public String getAddress1() {
    return StringUtil.coalesce(CodeUtil.getEntry("address1"), address1);
  }

  /**
   * @param address1
   *          都道府県
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @return 市区町村
   */
  public String getAddress2() {
    return StringUtil.coalesce(CodeUtil.getEntry("address2"), address2);
  }

  /**
   * @param address2
   *          市区町村
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * @return 町名・番地
   */
  public String getAddress3() {
    return StringUtil.coalesce(CodeUtil.getEntry("address3"), address3);
  }

  /**
   * @param address3
   *          町名・番地
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  /**
   * @return アパート・マンション・ビル
   */
  public String getAddress4() {
    return StringUtil.coalesce(CodeUtil.getEntry("address4"), address4);
  }

  /**
   * @param address4
   *          アパート・マンション・ビル
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  /**
   * @return 電話番号
   */
  public String getPhoneNumber() {
    return StringUtil.coalesce(CodeUtil.getEntry("phoneNumber"), phoneNumber);
  }

  /**
   * @param phoneNumber
   *          電話番号
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return 生年月日
   */
  public String getBirthDate() {
    return StringUtil.coalesce(CodeUtil.getEntry("birthDate"), birthDate);
  }

  /**
   * @param birthDate
   *          生年月日
   */
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * @return 性別
   */
  public String getSex() {
    return StringUtil.coalesce(CodeUtil.getEntry("sex"), sex);
  }

  /**
   * @param sex
   *          性別
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  /**
   * @return Emailアドレス
   */
  public String getEmail() {
    return StringUtil.coalesce(CodeUtil.getEntry("email"), email);
  }

  /**
   * @param email
   *          Emailアドレス
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return 氏名
   */
  public String getFullName() {
    return getLastName(); //$NON-NLS-1$
  }

  /**
   * @return 氏名：カタカナ
   */
  public String getFullNameKana() {
    return getLastNameKana() + " " + getFirstNameKana(); //$NON-NLS-1$
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
