//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.ClientMailType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LockFlg;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「顧客(CUSTOMER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class Customer implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顧客コード */
  @PrimaryKey(1)
  @Required
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
  // @Required
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
  @Precision(precision = 14, scale = 2)
  @Metadata(name = "ポイント残高", order = 21)
  private BigDecimal restPoint;

  /** 仮発行ポイント */
  @Precision(precision = 14, scale = 2)
  @Metadata(name = "仮発行ポイント", order = 22)
  private BigDecimal temporaryPoint;

  /** 退会希望日 */
  @Metadata(name = "退会希望日", order = 23)
  private Date withdrawalRequestDate;

  /** 退会日 */
  @Metadata(name = "退会日", order = 24)
  private Date withdrawalDate;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 25)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 26)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 27)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 28)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 29)
  private Date updatedDatetime;

  /** 20111224 os013 add start **/
  /** 支付宝用户编号 */
  @Length(16)
  @Metadata(name = "支付宝用户编号", order = 30)
  private Long tmallUserId;

  /** 会员区分 */
  @Length(1)
  @Metadata(name = "会员区分", order = 31)
  private Long customerKbn;

  /** 20111224 os013 add end **/
  // 20120307 os013 add start
  /** 导出区分 */
  @Length(1)
  @Metadata(name = "导出区分", order = 32)
  private Long exportKbn;

  // 20120307 os013 add end
  
  //20120510 tuxinwei add start
  /** 语言编号 */
  @Required
  @Length(5)
  @Metadata(name = "语言编号", order = 33)
  private String languageCode;
  //20120510 tuxinwei add end
  
  //2013/04/01 优惠券对应 add start
  //手机号码
  @Metadata(name = "手机号码", order = 34)
  private String mobileNumber;
  
  //验证码
  @Metadata(name = "验证码", order = 35)
  private String authCode;
  //2013/04/01 优惠券对应 add end
  
  // 20131101 txw add start
  /** 礼品卡支付密码输入错误次数 */
  @Length(2)
  @Metadata(name = "礼品卡支付密码输入错误次数", order = 36)
  private Long errorTimes;
  /** 账户礼品卡使用锁定标志 */
  @Length(1)
  @Domain(LockFlg.class)
  @Metadata(name = "账户礼品卡使用锁定标志", order = 37)
  private Long lockFlg;
  /** 礼品卡支付密码 */
  @Length(50)
  @Metadata(name = "礼品卡支付密码", order = 38)
  private String paymentPassword;
  // 20131101 txw add end
  
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
    return this.firstName;
  }

  /**
   * 姓カナを取得します
   * 
   * @return 姓カナ
   */
  public String getLastNameKana() {
    return this.lastNameKana;
  }

  /**
   * 名カナを取得します
   * 
   * @return 名カナ
   */
  public String getFirstNameKana() {
    return this.firstNameKana;
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
  public BigDecimal getRestPoint() {
    return this.restPoint;
  }

  /**
   * 仮発行ポイントを取得します
   * 
   * @return 仮発行ポイント
   */
  public BigDecimal getTemporaryPoint() {
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

  /** 20111224 os013 add start */
  /**
   * 支付宝用户编号を取得します
   * 
   * @return 支付宝用户编号
   */
  public Long getTmallUserId() {
    return tmallUserId;
  }

  /**
   * 会员区分を取得します
   * 
   * @return 会员区分
   */
  public Long getCustomerKbn() {
    return customerKbn;
  }

  /** 20111224 os013 add end */

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
  public void setRestPoint(BigDecimal val) {
    this.restPoint = val;
  }

  /**
   * 仮発行ポイントを設定します
   * 
   * @param val
   *          仮発行ポイント
   */
  public void setTemporaryPoint(BigDecimal val) {
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

  /** 20111224 os013 add start */
  /**
   * 支付宝用户编号を設定します
   * 
   * @param val
   *          支付宝用户编号
   */
  public void setTmallUserId(Long tmallUserId) {
    this.tmallUserId = tmallUserId;
  }

  /**
   * 会员区分を設定します
   * 
   * @param val
   *          会员区分
   */
  public void setCustomerKbn(Long customerKbn) {
    this.customerKbn = customerKbn;
  }

  /** 20111224 os013 add end */
  // 20120307 os013 add start

  /**
   * @return the exportKbn
   */
  public Long getExportKbn() {
    return exportKbn;
  }

  /**
   * @param exportKbn
   *          the exportKbn to set
   */
  public void setExportKbn(Long exportKbn) {
    this.exportKbn = exportKbn;
  }

  // 20120307 os013 add end
  
  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  
  /**
   * @param languageCode the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }
  
  //2013/04/01 优惠券对应 add start
  /**
   * 取得手机号码
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * 设定手机号码
   * @param mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * 取得验证码
   * @return
   */
  public String getAuthCode() {
    return authCode;
  }

  /**
   * 设定验证码
   * @param authCode
   */
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }
  //2013/04/01 优惠券对应 add end

  
  /**
   * @return the errorTimes
   */
  public Long getErrorTimes() {
    return errorTimes;
  }

  
  /**
   * @return the lockFlg
   */
  public Long getLockFlg() {
    return lockFlg;
  }

  
  /**
   * @return the paymentPassword
   */
  public String getPaymentPassword() {
    return paymentPassword;
  }

  
  /**
   * @param errorTimes the errorTimes to set
   */
  public void setErrorTimes(Long errorTimes) {
    this.errorTimes = errorTimes;
  }

  
  /**
   * @param lockFlg the lockFlg to set
   */
  public void setLockFlg(Long lockFlg) {
    this.lockFlg = lockFlg;
  }

  
  /**
   * @param paymentPassword the paymentPassword to set
   */
  public void setPaymentPassword(String paymentPassword) {
    this.paymentPassword = paymentPassword;
  }

  
}
