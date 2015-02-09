package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.ClientMailType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.domain.PrefectureName;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.utility.DateUtil;

public class InputCustomer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 顧客コード */
  @PrimaryKey(1)
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 1)
  private String customerCode;

  /** 顧客グループコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客グループコード", order = 2)
  private String customerGroupCode;

  /** 姓 */
  @Required
  @Length(20)
  @Metadata(name = "姓", order = 3)
  private String lastName;

  /** 名 */
  @Required
  @Length(20)
  @Metadata(name = "名", order = 4)
  private String firstName;

  /** 姓カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "姓カナ", order = 5)
  private String lastNameKana;

  /** 名カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "名カナ", order = 6)
  private String firstNameKana;

  /** ログインID */
  @Required
  @Length(256)
  @Metadata(name = "ログインID", order = 7)
  private String loginId;

  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス", order = 8)
  private String email;

  /** パスワード */
  @Required
  @Length(50)
  @Metadata(name = "パスワード", order = 9)
  private String password;

  /** 生年月日 */
  @Required
  @Metadata(name = "生年月日", order = 10)
  private Date birthDate;

  /** 性別 */
  @Required
  @Length(1)
  @Domain(Sex.class)
  @Metadata(name = "性別", order = 11)
  private Long sex;

  /** 希望メール区分 */
  @Required
  @Length(1)
  @Domain(RequestMailType.class)
  @Metadata(name = "希望メール区分", order = 12)
  private Long requestMailType;

  /** クライアントメール区分 */
  @Required
  @Length(1)
  @Domain(ClientMailType.class)
  @Metadata(name = "クライアントメール区分", order = 13)
  private Long clientMailType;

  /** 注意事項（管理側のみ参照） */
  @Length(200)
  @Metadata(name = "注意事項（管理側のみ参照）", order = 14)
  private String caution;

  /** ログイン日時 */
  @Metadata(name = "ログイン日時", order = 15)
  private Date loginDatetime;

  /** ログイン失敗回数 */
  @Required
  @Length(2)
  @Metadata(name = "ログイン失敗回数", order = 16)
  private Long loginErrorCount;

  /** ログインロックフラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "ログインロックフラグ", order = 17)
  private Long loginLockedFlg;

  /** 顧客ステータス */
  @Required
  @Length(1)
  @Domain(CustomerStatus.class)
  @Metadata(name = "顧客ステータス", order = 18)
  private Long customerStatus;

  /** 顧客属性回答日 */
  @Metadata(name = "顧客属性回答日", order = 19)
  private Date customerAttributeReplyDate;

  /** ポイント最終獲得日 */
  @Metadata(name = "ポイント最終獲得日", order = 20)
  private Date latestPointAcquiredDate;

  /** ポイント残高 */
  @Length(12)
  @Metadata(name = "ポイント残高", order = 21)
  private Long restPoint;

  /** 仮発行ポイント */
  @Length(12)
  @Metadata(name = "仮発行ポイント", order = 22)
  private Long temporaryPoint;

  /** 退会希望日 */
  @Metadata(name = "退会希望日", order = 23)
  private Date withdrawalRequestDate;

  /** 退会日 */
  @Metadata(name = "退会日", order = 24)
  private Date withdrawalDate;

  /** アドレス呼称 */
  @Required
  @Length(20)
  @Metadata(name = "アドレス呼称", order = 3)
  private String addressAlias;

  /** 郵便番号 */
  @Required
  @Length(6)
  @Digit
  @Metadata(name = "郵便番号", order = 8)
  private String postalCode;

  /** 都道府県コード */
  @Required
  @Length(2)
  @Domain(PrefectureCode.class)
  @Metadata(name = "都道府県コード", order = 9)
  private String prefectureCode;

  /** 住所1 */
  @Required
  @Length(50)
  @Domain(PrefectureName.class)
  @Metadata(name = "住所1", order = 10)
  private String address1;

  /** 住所2 */
  @Required
  @Length(50)
  @Metadata(name = "住所2", order = 11)
  private String address2;

  /** 住所3 */
  @Required
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
  @Metadata(name = "手机号码", order = 31)
  private String mobileNumber;
  
  /** データ行ID */
  @Length(38)
  @Metadata(name = "データ行ID", order = 25)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 26)
  private String createdUser;

  /** 作成日時 */
  @Metadata(name = "作成日時", order = 27)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 28)
  private String updatedUser;

  /** 更新日時 */
  @Metadata(name = "更新日時", order = 29)
  private Date updatedDatetime;

  // add by V10-CH 170 start
  /** 城市コード */
  @Required
  @Length(3)
  @Metadata(name = "城市コード", order = 30)
  private String cityCode;
  
  public String getCityCode() {
    return cityCode;
  }

  
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  // add by V10-CH 170 end
  /**
   * 顧客コードを取得します
   * 
   * @return 顧客コード
   */
  public String getCustomerCode() {
    return this.customerCode;
  }

  /**
   * 顧客グループコードを取得します
   * 
   * @return 顧客グループコード
   */
  public String getCustomerGroupCode() {
    return this.customerGroupCode;
  }

  /**
   * 姓を取得します
   * 
   * @return 姓
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * 名を取得します
   * 
   * @return 名
   */
  public String getFirstName() {
    return "姓";
  }

  /**
   * 姓カナを取得します
   * 
   * @return 姓カナ
   */
  public String getLastNameKana() {
    return "カナ";
  }

  /**
   * 名カナを取得します
   * 
   * @return 名カナ
   */
  public String getFirstNameKana() {
    return "カナ";
  }

  /**
   * ログインIDを取得します
   * 
   * @return ログインID
   */
  public String getLoginId() {
    return this.loginId;
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
   * パスワードを取得します
   * 
   * @return パスワード
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * 生年月日を取得します
   * 
   * @return 生年月日
   */
  public Date getBirthDate() {
    return DateUtil.immutableCopy(this.birthDate);
  }

  /**
   * 性別を取得します
   * 
   * @return 性別
   */
  public Long getSex() {
    return this.sex;
  }

  /**
   * 希望メール区分を取得します
   * 
   * @return 希望メール区分
   */
  public Long getRequestMailType() {
    return this.requestMailType;
  }

  /**
   * クライアントメール区分を取得します
   * 
   * @return クライアントメール区分
   */
  public Long getClientMailType() {
    return this.clientMailType;
  }

  /**
   * 注意事項（管理側のみ参照）を取得します
   * 
   * @return 注意事項（管理側のみ参照）
   */
  public String getCaution() {
    return this.caution;
  }

  /**
   * ログイン日時を取得します
   * 
   * @return ログイン日時
   */
  public Date getLoginDatetime() {
    return DateUtil.immutableCopy(this.loginDatetime);
  }

  /**
   * ログイン失敗回数を取得します
   * 
   * @return ログイン失敗回数
   */
  public Long getLoginErrorCount() {
    return this.loginErrorCount;
  }

  /**
   * ログインロックフラグを取得します
   * 
   * @return ログインロックフラグ
   */
  public Long getLoginLockedFlg() {
    return this.loginLockedFlg;
  }

  /**
   * 顧客ステータスを取得します
   * 
   * @return 顧客ステータス
   */
  public Long getCustomerStatus() {
    return this.customerStatus;
  }

  /**
   * 顧客属性回答日を取得します
   * 
   * @return 顧客属性回答日
   */
  public Date getCustomerAttributeReplyDate() {
    return DateUtil.immutableCopy(this.customerAttributeReplyDate);
  }

  /**
   * ポイント最終獲得日を取得します
   * 
   * @return ポイント最終獲得日
   */
  public Date getLatestPointAcquiredDate() {
    return DateUtil.immutableCopy(this.latestPointAcquiredDate);
  }

  /**
   * ポイント残高を取得します
   * 
   * @return ポイント残高
   */
  public Long getRestPoint() {
    return this.restPoint;
  }

  /**
   * 仮発行ポイントを取得します
   * 
   * @return 仮発行ポイント
   */
  public Long getTemporaryPoint() {
    return this.temporaryPoint;
  }

  /**
   * 退会希望日を取得します
   * 
   * @return 退会希望日
   */
  public Date getWithdrawalRequestDate() {
    return DateUtil.immutableCopy(this.withdrawalRequestDate);
  }

  /**
   * 退会日を取得します
   * 
   * @return 退会日
   */
  public Date getWithdrawalDate() {
    return DateUtil.immutableCopy(this.withdrawalDate);
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
   * 顧客グループコードを設定します
   * 
   * @param val
   *          顧客グループコード
   */
  public void setCustomerGroupCode(String val) {
    this.customerGroupCode = val;
  }

  /**
   * 姓を設定します
   * 
   * @param val
   *          姓
   */
  public void setLastName(String val) {
    this.lastName = val;
  }

  /**
   * 名を設定します
   * 
   * @param val
   *          名
   */
  public void setFirstName(String val) {
    this.firstName = val;
  }

  /**
   * 姓カナを設定します
   * 
   * @param val
   *          姓カナ
   */
  public void setLastNameKana(String val) {
    this.lastNameKana = val;
  }

  /**
   * 名カナを設定します
   * 
   * @param val
   *          名カナ
   */
  public void setFirstNameKana(String val) {
    this.firstNameKana = val;
  }

  /**
   * ログインIDを設定します
   * 
   * @param val
   *          ログインID
   */
  public void setLoginId(String val) {
    this.loginId = val;
  }

  /**
   * メールアドレスを設定します
   * 
   * @param val
   *          メールアドレス
   */
  public void setEmail(String val) {
    this.email = val;
  }

  /**
   * パスワードを設定します
   * 
   * @param val
   *          パスワード
   */
  public void setPassword(String val) {
    this.password = val;
  }

  /**
   * 生年月日を設定します
   * 
   * @param val
   *          生年月日
   */
  public void setBirthDate(Date val) {
    this.birthDate = DateUtil.immutableCopy(val);
  }

  /**
   * 性別を設定します
   * 
   * @param val
   *          性別
   */
  public void setSex(Long val) {
    this.sex = val;
  }

  /**
   * 希望メール区分を設定します
   * 
   * @param val
   *          希望メール区分
   */
  public void setRequestMailType(Long val) {
    this.requestMailType = val;
  }

  /**
   * クライアントメール区分を設定します
   * 
   * @param val
   *          クライアントメール区分
   */
  public void setClientMailType(Long val) {
    this.clientMailType = val;
  }

  /**
   * 注意事項（管理側のみ参照）を設定します
   * 
   * @param val
   *          注意事項（管理側のみ参照）
   */
  public void setCaution(String val) {
    this.caution = val;
  }

  /**
   * ログイン日時を設定します
   * 
   * @param val
   *          ログイン日時
   */
  public void setLoginDatetime(Date val) {
    this.loginDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * ログイン失敗回数を設定します
   * 
   * @param val
   *          ログイン失敗回数
   */
  public void setLoginErrorCount(Long val) {
    this.loginErrorCount = val;
  }

  /**
   * ログインロックフラグを設定します
   * 
   * @param val
   *          ログインロックフラグ
   */
  public void setLoginLockedFlg(Long val) {
    this.loginLockedFlg = val;
  }

  /**
   * 顧客ステータスを設定します
   * 
   * @param val
   *          顧客ステータス
   */
  public void setCustomerStatus(Long val) {
    this.customerStatus = val;
  }

  /**
   * 顧客属性回答日を設定します
   * 
   * @param val
   *          顧客属性回答日
   */
  public void setCustomerAttributeReplyDate(Date val) {
    this.customerAttributeReplyDate = DateUtil.immutableCopy(val);
  }

  /**
   * ポイント最終獲得日を設定します
   * 
   * @param val
   *          ポイント最終獲得日
   */
  public void setLatestPointAcquiredDate(Date val) {
    this.latestPointAcquiredDate = DateUtil.immutableCopy(val);
  }

  /**
   * ポイント残高を設定します
   * 
   * @param val
   *          ポイント残高
   */
  public void setRestPoint(Long val) {
    this.restPoint = val;
  }

  /**
   * 仮発行ポイントを設定します
   * 
   * @param val
   *          仮発行ポイント
   */
  public void setTemporaryPoint(Long val) {
    this.temporaryPoint = val;
  }

  /**
   * 退会希望日を設定します
   * 
   * @param val
   *          退会希望日
   */
  public void setWithdrawalRequestDate(Date val) {
    this.withdrawalRequestDate = DateUtil.immutableCopy(val);
  }

  /**
   * 退会日を設定します
   * 
   * @param val
   *          退会日
   */
  public void setWithdrawalDate(Date val) {
    this.withdrawalDate = DateUtil.immutableCopy(val);
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
   * アドレス呼称を取得します
   * 
   * @return アドレス呼称
   */
  public String getAddressAlias() {
    return this.addressAlias;
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
   * アドレス呼称を設定します
   * 
   * @param val
   *          アドレス呼称
   */
  public void setAddressAlias(String val) {
    this.addressAlias = val;
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
