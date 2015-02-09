package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.data.domain.LockFlg;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030120:顧客登録のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CustomerAttributeListBean> attributeList = new ArrayList<CustomerAttributeListBean>();

  // modify by V10-CH 170 start
  private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();

  // modify by V10-CH 170 start

  /** 顧客コード */
  private String customerCode;

  /** 顧客グループコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客グループ")
  private String customerGroupCode;

  private List<CodeAttribute> customerGroupList = new ArrayList<CodeAttribute>();

  /** 姓 */
  @Required
  @Length(20)
  @Metadata(name = "顧客名(姓)")
  private String lastName;

  /** 名 */
  @Length(20)
  @Metadata(name = "顧客名(名)")
  private String firstName;

  /** 姓カナ */
  @Length(40)
  @Kana
  @Metadata(name = "顧客名カナ(姓)")
  private String lastNameKana;

  /** 名カナ */
  @Length(40)
  @Kana
  @Metadata(name = "顧客名カナ(カナ)")
  private String firstNameKana;

  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String email;

  /** メールアドレス */
  @Length(256)
  @Email
  @Metadata(name = "email 确认")
  private String emailCon;

  /** パスワード */
  @Length(50)
  @Metadata(name = "パスワード")
  private String password;

  /** パスワード(確認) */
  @Length(50)
  @Metadata(name = "パスワード(確認)")
  private String passwordCon;

  /** 生年月日 */
  @Required
  @Datetime
  @Metadata(name = "生年月日")
  private String birthDate;

  /** 郵便番号1 */
  // modify by V10-CH 170 start
  // @Length(7)
  @Length(6)
  // modify by V10-CH 170 end
  @PostalCode
  @Metadata(name = "郵便番号")
  private String postalCode;

  /** 都道府県コード */
  @Length(2)
  @AlphaNum2
  @Metadata(name = "住所1(都道府県)")
  private String prefectureCode;

  /** 住所1 */
  @Metadata(name = "住所1(都道府県)")
  private String address1;

  /** 住所2 */
  // @Required
  // @Length(50)
  @Metadata(name = "住所2(市区町村)")
  private String address2;

  /** 市コード */
  @Length(3)
  @Metadata(name = "市コード")
  private String cityCode;

  /** 住所3 */
  @Length(50)
  @Metadata(name = "住所3(町名・番地)")
  private String address3;

  /** 住所4 */
  @Length(100)
  @Metadata(name = "住所4(アパート・マンション・ビル)")
  private String address4;

  /** 連絡先電話番号1 */
  @Length(4)
  // @Digit(allowNegative = false)
  @Metadata(name = "連絡先電話番号1")
  private String phoneNumber1;

  // Add by V10-CH start
  /** 連絡先電話番号2 */
  @Length(8)
  // @Digit(allowNegative = false)
  @Metadata(name = "連絡先電話番号2")
  private String phoneNumber2;

  /** 連絡先電話番号3 */
  @Length(6)
  // @Digit(allowNegative = false)
  @Metadata(name = "連絡先電話番号3")
  private String phoneNumber3;

  /** 手机号码1 */

  @Metadata(name = "手机号码")
  private String mobileNumber;

  /** 手机号码 */
  @MobileNumber
  @Metadata(name = "手机号码")
  private String mobileNumberSucces;

  /**
   * @return the mobileNumberSucces
   */
  public String getMobileNumberSucces() {
    return mobileNumberSucces;
  }

  /**
   * @param mobileNumberSucces
   *          the mobileNumberSucces to set
   */
  public void setMobileNumberSucces(String mobileNumberSucces) {
    this.mobileNumberSucces = mobileNumberSucces;
  }

  /** 验证码 */
  @Length(6)
  @Digit
  @Metadata(name = "验证码")
  private String verification;

  // Add by V10-CH end

  /** 性別 */
  @Required
  @Length(1)
  @Metadata(name = "性別")
  private String sex;

  // 20120510 tuxinwei add start
  /** 语言编号 */
  @Required
  @Length(5)
  @Metadata(name = "语言编号")
  private String languageCode;

  // 20120510 tuxinwei add end

  /** 情報メール */
  @Required
  @Length(1)
  @Metadata(name = "情報メール")
  private String requestMailType;

  /** 注意事項（管理側のみ参照） */
  @Length(200)
  @Metadata(name = "注意事項(管理側のみ参照)")
  private String caution;

  /** ログインエラー回数 */
  @Required
  @Digit
  @Length(2)
  @Range(min = 0, max = 99)
  @Metadata(name = "ログインエラー回数")
  private String loginErrorCount;

  /** ログイン可否 */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "ログイン可否")
  private String loginLockedFlg;

  // 20131101 txw add start
  /** 礼品卡支付密码输入错误次数 */
  @Digit
  @Length(2)
  @Range(min = 0, max = 99)
  @Metadata(name = "礼品卡支付密码输入错误次数")
  private String errorTimes;

  /** 账户礼品卡使用锁定标志 */
  @Length(1)
  @Metadata(name = "账户礼品卡使用锁定标志")
  private String lockFlg;

  // 20131101 txw add end

  /** 顧客ステータス(退会依頼) */
  private boolean isReceivedWithdrawalNotice;

  /** 作成日時 */
  private String createdDatetime;

  /** 編集処理モード */
  private String editMode;

  /** 次へボタン表示フラグ */
  private boolean nextButtonDisplayFlg;

  /** 登録ボタン表示フラグ */
  private boolean registerButtonDisplayFlg;

  /** 更新ボタン表示フラグ */
  private boolean updateButtonDisplayFlg;

  /** 退会ボタン表示フラグ */
  private boolean withdrawalDisplayFlg;

  /** 戻るボタン表示フラグ */
  private boolean backButtonDisplayFlg;

  /** ポイント履歴ボタン表示フラグ */
  private boolean pointDisplayFlg;

  /** ポイント履歴ボタン表示フラグ */
  private boolean couponDisplayFlg;

  /** 受注履歴ボタン表示フラグ */
  private boolean orderDisplayFlg;

  /** 非顧客退会済みフラグ */
  private boolean notWithdrawedFlg;

  /** 更新日時順(顧客) */
  private Date updatedDatetimeCustomer;

  /** 更新日時順(アドレス帳) */
  private Date updatedDatetimeAddress;

  /**
   * U1030120:顧客登録のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeListBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 顧客属性番号 */
    private String customerAttributeNo;

    /** 顧客属性名称 */
    private String customerAttributeName;

    /** 顧客属性区分 */
    private String customerAttributeType;

    private List<CodeAttribute> attributeChoiceList = new ArrayList<CodeAttribute>();

    /** 顧客属性選択肢番号 */
    @Length(8)
    @Digit
    @Metadata(name = "顧客属性選択肢番号")
    private String attributeAnswer;

    /** 顧客属性選択肢回答リスト表示用 */
    private List<String> attributeAnswerItem = new ArrayList<String>();

    /** 顧客属性選択肢回答リスト */
    private List<CustomerAttributeListAnswerBean> attributeAnswerList = new ArrayList<CustomerAttributeListAnswerBean>();

    /** 顧客属性入力タグ名 */
    private String attributeTextName;

    /**
     * attributeAnswerItemを取得します。
     * 
     * @return attributeAnswerItem
     */
    public List<String> getAttributeAnswerItem() {
      return attributeAnswerItem;
    }

    /**
     * attributeAnswerListを取得します。
     * 
     * @return attributeAnswerList
     */
    public List<CustomerAttributeListAnswerBean> getAttributeAnswerList() {
      return attributeAnswerList;
    }

    /**
     * attributeAnswerItemを設定します。
     * 
     * @param attributeAnswerItem
     *          attributeAnswerItem
     */
    public void setAttributeAnswerItem(List<String> attributeAnswerItem) {
      this.attributeAnswerItem = attributeAnswerItem;
    }

    /**
     * attributeAnswerListを設定します。
     * 
     * @param attributeAnswerList
     *          attributeAnswerList
     */
    public void setAttributeAnswerList(List<CustomerAttributeListAnswerBean> attributeAnswerList) {
      this.attributeAnswerList = attributeAnswerList;
    }

    /**
     * attributeTextNameを取得します。
     * 
     * @return attributeTextName
     */
    public String getAttributeTextName() {
      return attributeTextName;
    }

    /**
     * attributeTextNameを設定します。
     * 
     * @param attributeTextName
     *          attributeTextName
     */
    public void setAttributeTextName(String attributeTextName) {
      this.attributeTextName = attributeTextName;
    }

    /**
     * attributeAnswerを取得します。
     * 
     * @return attributeAnswer
     */
    public String getAttributeAnswer() {
      return attributeAnswer;
    }

    /**
     * attributeChoiceListを取得します。
     * 
     * @return attributeChoiceList
     */
    public List<CodeAttribute> getAttributeChoiceList() {
      return attributeChoiceList;
    }

    /**
     * customerAttributeNameを取得します。
     * 
     * @return customerAttributeName
     */
    public String getCustomerAttributeName() {
      return customerAttributeName;
    }

    /**
     * customerAttributeNoを取得します。
     * 
     * @return customerAttributeNo
     */
    public String getCustomerAttributeNo() {
      return customerAttributeNo;
    }

    /**
     * customerAttributeTypeを取得します。
     * 
     * @return customerAttributeType
     */
    public String getCustomerAttributeType() {
      return customerAttributeType;
    }

    /**
     * attributeAnswerを設定します。
     * 
     * @param attributeAnswer
     *          attributeAnswer
     */
    public void setAttributeAnswer(String attributeAnswer) {
      this.attributeAnswer = attributeAnswer;
    }

    /**
     * attributeChoiceListを設定します。
     * 
     * @param attributeChoiceList
     *          attributeChoiceList
     */
    public void setAttributeChoiceList(List<CodeAttribute> attributeChoiceList) {
      this.attributeChoiceList = attributeChoiceList;
    }

    /**
     * customerAttributeNameを設定します。
     * 
     * @param customerAttributeName
     *          customerAttributeName
     */
    public void setCustomerAttributeName(String customerAttributeName) {
      this.customerAttributeName = customerAttributeName;
    }

    /**
     * customerAttributeNoを設定します。
     * 
     * @param customerAttributeNo
     *          customerAttributeNo
     */
    public void setCustomerAttributeNo(String customerAttributeNo) {
      this.customerAttributeNo = customerAttributeNo;
    }

    /**
     * customerAttributeTypeを設定します。
     * 
     * @param customerAttributeType
     *          customerAttributeType
     */
    public void setCustomerAttributeType(String customerAttributeType) {
      this.customerAttributeType = customerAttributeType;
    }
  }

  /**
   * U1030120:顧客登録のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeListAnswerBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 顧客属性選択肢回答(リスト) */
    @Length(8)
    @Digit
    @Metadata(name = "顧客属性選択肢番号")
    private String attributeAnswerList;

    /**
     * attributeAnswerListを取得します。
     * 
     * @return attributeAnswerList
     */
    public String getAttributeAnswerList() {
      return attributeAnswerList;
    }

    /**
     * attributeAnswerListを設定します。
     * 
     * @param attributeAnswerList
     *          attributeAnswerList
     */
    public void setAttributeAnswerList(String attributeAnswerList) {
      this.attributeAnswerList = attributeAnswerList;
    }

  }

  /**
   * notWithdrawedFlgを取得します。
   * 
   * @return notWithdrawedFlg
   */
  public boolean isNotWithdrawedFlg() {
    return notWithdrawedFlg;
  }

  /**
   * notWithdrawedFlgを設定します。
   * 
   * @param notWithdrawedFlg
   *          notWithdrawedFlg
   */
  public void setNotWithdrawedFlg(boolean notWithdrawedFlg) {
    this.notWithdrawedFlg = notWithdrawedFlg;
  }

  /**
   * isReceivedWithdrawalNoticeを取得します。
   * 
   * @return isReceivedWithdrawalNotice
   */
  public boolean isReceivedWithdrawalNotice() {
    return isReceivedWithdrawalNotice;
  }

  /**
   * isReceivedWithdrawalNoticeを設定します。
   * 
   * @param receivedWithdrawalNotice
   *          isReceivedWithdrawalNotice
   */
  public void setReceivedWithdrawalNotice(boolean receivedWithdrawalNotice) {
    this.isReceivedWithdrawalNotice = receivedWithdrawalNotice;
  }

  /**
   * backButtonDisplayFlgを取得します。
   * 
   * @return backButtonDisplayFlg
   */
  public boolean isBackButtonDisplayFlg() {
    return backButtonDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを取得します。
   * 
   * @return updateButtonDisplayFlg
   */
  public boolean isUpdateButtonDisplayFlg() {
    return updateButtonDisplayFlg;
  }

  /**
   * backButtonDisplayFlgを設定します。
   * 
   * @param backButtonDisplayFlg
   *          backButtonDisplayFlg
   */
  public void setBackButtonDisplayFlg(boolean backButtonDisplayFlg) {
    this.backButtonDisplayFlg = backButtonDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを設定します。
   * 
   * @param updateButtonDisplayFlg
   *          updateButtonDisplayFlg
   */
  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
  }

  /**
   * registerButtonDisplayFlgを取得します。
   * 
   * @return registerButtonDisplayFlg
   */
  public boolean isRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
  }

  /**
   * registerButtonDisplayFlgを設定します。
   * 
   * @param registerButtonDisplayFlg
   */
  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
  }

  /**
   * withdrawalDisplayFlgを取得します。
   * 
   * @return withdrawalDisplayFlg
   */
  public boolean isWithdrawalDisplayFlg() {
    return withdrawalDisplayFlg;
  }

  /**
   * withdrawalDisplayFlgを設定します。
   * 
   * @param withdrawalDisplayFlg
   *          withdrawalDisplayFlg
   */
  public void setWithdrawalDisplayFlg(boolean withdrawalDisplayFlg) {
    this.withdrawalDisplayFlg = withdrawalDisplayFlg;
  }

  /**
   * postalCodeを取得します。
   * 
   * @return postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * postalCodeを設定します。
   * 
   * @param postalCode
   *          postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * prefectureCodeを取得します。
   * 
   * @return prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * prefectureCodeを設定します。
   * 
   * @param prefectureCode
   *          prefectureCode
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * editModeを取得します。
   * 
   * @return editMode
   */
  public String getEditMode() {
    return editMode;
  }

  /**
   * editModeを設定します。
   * 
   * @param editMode
   *          editMode
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

  /**
   * nextButtonDisplayFlgを取得します。
   * 
   * @return nextButtonDisplayFlg
   */
  public boolean isNextButtonDisplayFlg() {
    return nextButtonDisplayFlg;
  }

  /**
   * nextButtonDisplayFlgを設定します。
   * 
   * @param nextButtonDisplayFlg
   *          nextButtonDisplayFlg
   */
  public void setNextButtonDisplayFlg(boolean nextButtonDisplayFlg) {
    this.nextButtonDisplayFlg = nextButtonDisplayFlg;
  }

  /**
   * updatedDatetimeAddressを取得します。
   * 
   * @return updatedDatetimeAddress
   */
  public Date getUpdatedDatetimeAddress() {
    return DateUtil.immutableCopy(updatedDatetimeAddress);
  }

  /**
   * updatedDatetimeCustomerを取得します。
   * 
   * @return updatedDatetimeCustomer
   */
  public Date getUpdatedDatetimeCustomer() {
    return DateUtil.immutableCopy(updatedDatetimeCustomer);
  }

  // add by V10-CH 170 start
  public List<CodeAttribute> getCityList() {
    return cityList;
  }

  public void setCityList(List<CodeAttribute> cityList) {
    this.cityList = cityList;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  // add by V10-CH 170 end

  /**
   * updatedDatetimeAddressを設定します。
   * 
   * @param updatedDatetimeAddress
   *          updatedDatetimeAddress
   */
  public void setUpdatedDatetimeAddress(Date updatedDatetimeAddress) {
    this.updatedDatetimeAddress = DateUtil.immutableCopy(updatedDatetimeAddress);
  }

  /**
   * updatedDatetimeCustomerを設定します。
   * 
   * @param updatedDatetimeCustomer
   *          updatedDatetimeCustomer
   */
  public void setUpdatedDatetimeCustomer(Date updatedDatetimeCustomer) {
    this.updatedDatetimeCustomer = DateUtil.immutableCopy(updatedDatetimeCustomer);
  }

  /**
   * passwordConを取得します。
   * 
   * @return passwordCon
   */
  public String getPasswordCon() {
    return passwordCon;
  }

  /**
   * passwordConを設定します。
   * 
   * @param passwordCon
   *          passwordCon
   */
  public void setPasswordCon(String passwordCon) {
    this.passwordCon = passwordCon;
  }

  /**
   * passwordを取得します。
   * 
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * passwordを設定します。
   * 
   * @param password
   *          password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * address1を取得します。
   * 
   * @return address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * address2を取得します。
   * 
   * @return address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * address3を取得します。
   * 
   * @return address3
   */
  public String getAddress3() {
    return address3;
  }

  /**
   * address4を取得します。
   * 
   * @return address4
   */
  public String getAddress4() {
    return address4;
  }

  /**
   * attributeListを取得します。
   * 
   * @return attributeList
   */
  public List<CustomerAttributeListBean> getAttributeList() {
    return attributeList;
  }

  /**
   * birthDateを取得します。
   * 
   * @return birthDate
   */
  public String getBirthDate() {
    return birthDate;
  }

  /**
   * cautionを取得します。
   * 
   * @return caution
   */
  public String getCaution() {
    return caution;
  }

  /**
   * createdDatetimeを取得します。
   * 
   * @return createdDatetime
   */
  public String getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * customerGroupCodeを取得します。
   * 
   * @return customerGroupCode
   */
  public String getCustomerGroupCode() {
    return customerGroupCode;
  }

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * firstNameを取得します。
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * firstNameKanaを取得します。
   * 
   * @return firstNameKana
   */
  public String getFirstNameKana() {
    return firstNameKana;
  }

  /**
   * lastNameを取得します。
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * lastNameKanaを取得します。
   * 
   * @return lastNameKana
   */
  public String getLastNameKana() {
    return lastNameKana;
  }

  /**
   * loginErrorCountを取得します。
   * 
   * @return loginErrorCount
   */
  public String getLoginErrorCount() {
    return loginErrorCount;
  }

  /**
   * loginLockedFlgを取得します。
   * 
   * @return loginLockedFlg
   */
  public String getLoginLockedFlg() {
    return loginLockedFlg;
  }

  /**
   * phoneNumber1を取得します。
   * 
   * @return phoneNumber1
   */
  public String getPhoneNumber1() {
    return phoneNumber1;
  }

  // /**
  // * phoneNumber2を取得します。

  // *
  // * @return phoneNumber2
  // */
  public String getPhoneNumber2() {
    return phoneNumber2;
  }

  //
  // /**
  // * phoneNumber3を取得します。

  // *
  // * @return phoneNumber3
  // */
  public String getPhoneNumber3() {
    return phoneNumber3;
  }

  /**
   * requestMailTypeを取得します。
   * 
   * @return requestMailType
   */
  public String getRequestMailType() {
    return requestMailType;
  }

  /**
   * sexを取得します。
   * 
   * @return sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * languageCodeを取得します。
   * 
   * @return languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * languageCodeを設定します。
   * 
   * @param languageCode
   *          languageCode
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  /**
   * address1を設定します。
   * 
   * @param address1
   *          address1
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * address2を設定します。
   * 
   * @param address2
   *          address2
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * address3を設定します。
   * 
   * @param address3
   *          address3
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  /**
   * address4を設定します。
   * 
   * @param address4
   *          address4
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  /**
   * attributeListを設定します。
   * 
   * @param attributeList
   *          attributeList
   */
  public void setAttributeList(List<CustomerAttributeListBean> attributeList) {
    this.attributeList = attributeList;
  }

  /**
   * birthDateを設定します。
   * 
   * @param birthDate
   *          birthDate
   */
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * cautionを設定します。
   * 
   * @param caution
   *          caution
   */
  public void setCaution(String caution) {
    this.caution = caution;
  }

  /**
   * createdDateTimeを設定します。
   * 
   * @param createdDatetime
   *          createdDatetime
   */
  public void setCreatedDatetime(String createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * customerGroupCodeを設定します。
   * 
   * @param customerGroupCode
   *          customerGroupCode
   */
  public void setCustomerGroupCode(String customerGroupCode) {
    this.customerGroupCode = customerGroupCode;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * firstNameKanaを設定します。
   * 
   * @param firstNameKana
   *          firstNameKana
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
  }

  /**
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * lastNameKanaを設定します。
   * 
   * @param lastNameKana
   *          lastNameKana
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }

  /**
   * loginErrorCountを設定します。
   * 
   * @param loginErrorCount
   *          loginErrorCount
   */
  public void setLoginErrorCount(String loginErrorCount) {
    this.loginErrorCount = loginErrorCount;
  }

  /**
   * loginLockFlgを設定します。
   * 
   * @param loginLockedFlg
   *          loginLockedFlg
   */
  public void setLoginLockedFlg(String loginLockedFlg) {
    this.loginLockedFlg = loginLockedFlg;
  }

  /**
   * phoneNumber1を設定します。
   * 
   * @param phoneNumber1
   *          phoneNumber1
   */
  public void setPhoneNumber1(String phoneNumber1) {
    this.phoneNumber1 = phoneNumber1;
  }

  // /**
  // * phoneNumber2を設定します。

  // *
  // * @param phoneNumber2
  // * phoneNumber2
  // */
  public void setPhoneNumber2(String phoneNumber2) {
    this.phoneNumber2 = phoneNumber2;
  }

  //
  // /**
  // * phoneNumber3を設定します。

  // *
  // * @param phoneNumber3
  // * phoneNumber3
  // */
  public void setPhoneNumber3(String phoneNumber3) {
    this.phoneNumber3 = phoneNumber3;
  }

  /**
   * requestMailTypeを設定します。
   * 
   * @param requestMailType
   *          requestMailType
   */
  public void setRequestMailType(String requestMailType) {
    this.requestMailType = requestMailType;
  }

  /**
   * sexを設定します。
   * 
   * @param sex
   *          sex
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

    reqparam.copy(this);
    setLastName(StringUtil.parse(reqparam.get("lastName")));
    setBirthDate(reqparam.getDateString("birthDate"));

    setPhoneNumber1(reqparam.get("phoneNumber_1"));
    setPhoneNumber2(reqparam.get("phoneNumber_2"));
    setPhoneNumber3(reqparam.get("phoneNumber_3"));
    // Add by V10-CH start
    setMobileNumber(reqparam.get("mobileNumber"));
    // Add by V10-CH end
    // 20120510 tuxinwei add start
    setLanguageCode(reqparam.get("languageCode"));
    // 20120510 tuxinwei add end
    if (StringUtil.isNullOrEmpty(reqparam.get("loginErrorCount"))) {
      setLoginErrorCount("0");
    }
    if (StringUtil.isNullOrEmpty(reqparam.get("loginLockedFlg"))) {
      setLoginLockedFlg(LoginLockedFlg.UNLOCKED.getValue());
    }
    if (StringUtil.isNullOrEmpty(reqparam.get("receivedWithdrawalNotice"))) {
      setReceivedWithdrawalNotice(false);
    } else {
      setReceivedWithdrawalNotice(true);
    }
    if (StringUtil.isNullOrEmpty(reqparam.get("lockFlg"))) {
      setLockFlg(LockFlg.UNLOCK.getValue());
    }

    // 顧客属性を設定
    setAttributeListParam(reqparam);
  }

  /**
   * 顧客属性を表示します。
   * 
   * @param bean
   * @param reqparam
   */
  private void setAttributeListParam(RequestParameter reqparam) {

    for (CustomerAttributeListBean ca : getAttributeList()) {
      if (ca.getCustomerAttributeType().equals(CustomerAttributeType.RADIO.getValue())) {
        // 単一選択の場合

        ca.setAttributeAnswer(reqparam.get(ca.getAttributeTextName()));
      } else {
        // 複数選択の場合

        List<String> answerItem = new ArrayList<String>();
        List<CustomerAttributeListAnswerBean> answerList = new ArrayList<CustomerAttributeListAnswerBean>();

        String[] paramList = reqparam.getAll(ca.getAttributeTextName());
        if (StringUtil.hasValueAllOf(paramList)) {
          for (String caa : paramList) {
            CustomerAttributeListAnswerBean answerEdit = new CustomerAttributeListAnswerBean();

            answerEdit.setAttributeAnswerList(caa);
            answerItem.add(caa);
            answerList.add(answerEdit);
          }
        }
        ca.setAttributeAnswerList(answerList);
        ca.setAttributeAnswerItem(answerItem); // リスト表示用
      }
    }
    setAttributeList(getAttributeList());
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1030120";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.CustomerEditBean.0");
  }

  /**
   * customerGroupListを取得します。
   * 
   * @return customerGroupList
   */
  public List<CodeAttribute> getCustomerGroupList() {
    return customerGroupList;
  }

  /**
   * customerGroupListを設定します。
   * 
   * @param customerGroupList
   *          customerGroupList
   */
  public void setCustomerGroupList(List<CodeAttribute> customerGroupList) {
    this.customerGroupList = customerGroupList;
  }

  /**
   * pointDisplayFlgを取得します。
   * 
   * @return pointDisplayFlg
   */
  public boolean isPointDisplayFlg() {
    return pointDisplayFlg;
  }

  /**
   * pointDisplayFlgを設定します。
   * 
   * @param pointDisplayFlg
   *          pointDisplayFlg
   */
  public void setPointDisplayFlg(boolean pointDisplayFlg) {
    this.pointDisplayFlg = pointDisplayFlg;
  }

  /**
   * orderDisplayFlgを取得します。
   * 
   * @return orderDisplayFlg
   */
  public boolean isOrderDisplayFlg() {
    return orderDisplayFlg;
  }

  /**
   * orderDisplayFlgを設定します。
   * 
   * @param orderDisplayFlg
   *          orderDisplayFlg
   */
  public void setOrderDisplayFlg(boolean orderDisplayFlg) {
    this.orderDisplayFlg = orderDisplayFlg;
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

  public boolean isCouponDisplayFlg() {
    return couponDisplayFlg;
  }

  public void setCouponDisplayFlg(boolean couponDisplayFlg) {
    this.couponDisplayFlg = couponDisplayFlg;
  }

  /**
   * @param emailCon
   *          the emailCon to set
   */
  public void setEmailCon(String emailCon) {
    this.emailCon = emailCon;
  }

  /**
   * @return the emailCon
   */
  public String getEmailCon() {
    return emailCon;
  }

  /**
   * @return the verification
   */
  public String getVerification() {
    return verification;
  }

  /**
   * @param verification
   *          the verification to set
   */
  public void setVerification(String verification) {
    this.verification = verification;
  }

  /**
   * @return the errorTimes
   */
  public String getErrorTimes() {
    return errorTimes;
  }

  /**
   * @return the lockFlg
   */
  public String getLockFlg() {
    return lockFlg;
  }

  /**
   * @param errorTimes
   *          the errorTimes to set
   */
  public void setErrorTimes(String errorTimes) {
    this.errorTimes = errorTimes;
  }

  /**
   * @param lockFlg
   *          the lockFlg to set
   */
  public void setLockFlg(String lockFlg) {
    this.lockFlg = lockFlg;
  }

}
