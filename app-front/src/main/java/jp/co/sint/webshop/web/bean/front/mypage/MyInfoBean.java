package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030210:お客様情報登録1のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class MyInfoBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 顧客コード */
  private String customerCode;

  /** 氏名(姓) */
  @Length(20)
  @AlphaNum2
  @Metadata(name = "氏名(姓)")
  private String companyCode;

  /** 氏名(姓) */
  @Required
  @Length(20)
  @Metadata(name = "氏名(姓)")
  private String lastName;

  /** 氏名(名) */
  @Length(20)
  @Metadata(name = "氏名(名)")
  private String firstName;

  /** 氏名カナ(姓) */
  @Length(40)
  @Kana
  @Metadata(name = "氏名カナ(姓)")
  private String lastNameKana;

  /** 氏名カナ(名) */
  @Length(40)
  @Kana
  @Metadata(name = "氏名カナ(名)")
  private String firstNameKana;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "旧手机号码")
  private String oldMobileNumber;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "新的手机号码")
  private String mobileNumber;

  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String email;

  /** 生年月日 */
  @Datetime
  @Metadata(name = "生年月日")
  private String birthDate;

  /** 性別 */
  @Required
  @Length(1)
  @Metadata(name = "性別")
  private String sex;

  /** 是否接收邮件 */
  @Required
  @Length(1)
  @Metadata(name = "是否接收邮件")
  private String requestMailType;

  // 20111214 lirong add start
  /** 验证码 */
  @Length(6)
  @Digit
  @Metadata(name = "验证码")
  private String authCode;

  /**
   */
  /** 语言编号 */
  @Required
  @Length(5)
  @Metadata(name = "语言信息")
  private String languageCode;

  // 性别
  private List<CodeAttribute> sexList = new ArrayList<CodeAttribute>();

  // 接收邮件
  private List<CodeAttribute> requestMailTypeList = new ArrayList<CodeAttribute>();
  
  //生日更新标志
  private boolean birthdayUpdateFlag = false;

  /**
   * birthDateを取得します。
   * 
   * @return birthDate
   */
  public String getBirthDate() {
    return birthDate;
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
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
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
   * birthDateを設定します。
   * 
   * @param birthDate
   *          birthDate
   */
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
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
    addSubJspId("/common/header");
    addSubJspId("/catalog/topic_path");
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
    setMobileNumber(reqparam.get("mobileNumber"));
    setLanguageCode(reqparam.get("languageCode"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.MyInfoBean.0");
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
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * @param languageCode
   *          the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  /**
   * @return the companyCode
   */
  public String getCompanyCode() {
    return companyCode;
  }

  /**
   * @param companyCode
   *          the companyCode to set
   */
  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  /**
   * @return the sexList
   */
  public List<CodeAttribute> getSexList() {
    return sexList;
  }

  /**
   * @param sexList
   *          the sexList to set
   */
  public void setSexList(List<CodeAttribute> sexList) {
    this.sexList = sexList;
  }

  // 2013/04/01 优惠券对应 ob add end

  /**
   * @return the requestMailTypeList
   */
  public List<CodeAttribute> getRequestMailTypeList() {
    return requestMailTypeList;
  }

  /**
   * @param requestMailTypeList
   *          the requestMailTypeList to set
   */
  public void setRequestMailTypeList(List<CodeAttribute> requestMailTypeList) {
    this.requestMailTypeList = requestMailTypeList;
  }

  /**
   * @return the oldMobileNumber
   */
  public String getOldMobileNumber() {
    return oldMobileNumber;
  }

  /**
   * @param oldMobileNumber
   *          the oldMobileNumber to set
   */
  public void setOldMobileNumber(String oldMobileNumber) {
    this.oldMobileNumber = oldMobileNumber;
  }

  /**
   * @return the authCode
   */
  public String getAuthCode() {
    return authCode;
  }

  /**
   * @param authCode
   *          the authCode to set
   */
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.MyInfoBean.1"), "/app/mypage/mypage"));
    if (StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)) {
      topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.MyInfoBean.0"), "/app/mypage/my_info/init"));
    } else {
      topicPath.add(new NameValue(Messages.getString("web.bean.mobile.mypage.MyInfoBean.1"), "/app/mypage/my_info/init"));
    }
    return topicPath;
  }

  
  /**
   * @return the birthdayUpdateFlag
   */
  public boolean isBirthdayUpdateFlag() {
    return birthdayUpdateFlag;
  }

  
  /**
   * @param birthdayUpdateFlag the birthdayUpdateFlag to set
   */
  public void setBirthdayUpdateFlag(boolean birthdayUpdateFlag) {
    this.birthdayUpdateFlag = birthdayUpdateFlag;
  }
}
