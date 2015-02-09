package jp.co.sint.webshop.web.bean.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020190:新規受注（顧客登録）のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerRegisterBean extends NeworderBaseBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 姓 */
  @Required
  @Length(20)
  @Metadata(name = "顧客名(姓)")
  private String lastName;

  /** 名 */
  @Required
  @Length(20)
  @Metadata(name = "顧客名(名)")
  private String firstName;

  /** 姓カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "顧客名カナ(姓)")
  private String lastNameKana;

  /** 名カナ */
  @Required
  @Length(40)
  @Kana
  @Metadata(name = "顧客名カナ(名)")
  private String firstNameKana;

  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String email;
  
  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String emailConfirm;

  /** パスワード */
  @Length(50)
  @Metadata(name = "パスワード")
  private String password;

  /** パスワード(確認) */
  @Length(50)
  @Metadata(name = "パスワード確認")
  private String passwordCon;

  /** 生年月日 */
  @Required
  @Datetime
  @Metadata(name = "生年月日")
  private String birthDate;

  //soukai delete 2012/1/2 ob start
  /** 郵便番号 *//*
  @Required
  @Length(7)
  @PostalCode
  @Metadata(name = "郵便番号")
  private String postalCode;

  @Required
  @Length(2)
  @Metadata(name = "住所1(都道府県)")
  private String prefectureCode;

  @Metadata(name = "住所1")
  private String address1;

  *//** 住所2 *//*
  //@Required
  @Length(50)
  @Metadata(name = "住所2(市区町村)")
  private String address2;

  *//** 市コード *//*
  @Required
  @Length(3)
  @Metadata(name = "市コード")
  private String cityCode;
  
  *//** 住所3 *//*
  @Required
  @Length(50)
  @Metadata(name = "住所3(町名・番地)")
  private String address3;

  *//** 住所4 *//*
  @Length(100)
  @Metadata(name = "住所4(アパート・マンション・ビル)")
  private String address4;

  *//** 電話番号1 *//*
  @Length(4)
  //@Digit(allowNegative = false)
  @Metadata(name = "連絡先電話番号1")
  private String phoneNumber1;

  *//** 電話番号2 *//*
  @Length(8)
  //@Digit(allowNegative = false)
  @Metadata(name = "連絡先電話番号2")
  private String phoneNumber2;

  *//** 電話番号3 *//*
  @Length(6)
  //@Digit(allowNegative = false)
  @Metadata(name = "連絡先電話番号3")
  private String phoneNumber3;

  *//** 手机号码 *//*
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码")
  private String mobileNumber;
  */
//soukai delete 2012/1/2 ob end
  /** 性別 */
  @Required
  @Length(1)
  @Metadata(name = "性別")
  private String sex;

  /** 希望メール区分 */
  @Required
  @Length(1)
  @Metadata(name = "情報メール")
  private String requestMailType;

  /** 注意事項（管理側のみ参照） */
  @Length(200)
  @Metadata(name = "注意事項（管理側のみ参照）")
  private String caution;

  /** テキスト表示モード */
  private String displayTextMode;

  /** 次へボタン表示モード */
  private boolean displayNextButtonModeFlg;

  /** 登録ボタン表示モード */
  private boolean displayRegisterButtonModeFlg;

  /** 戻るボタン表示モード */
  private boolean displayBackButtonModeFlg;

  // modify by V10-CH 170 start
  private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();

  // modify by V10-CH 170 end
  /**
   * displayBackButtonModeFlgを取得します。
   * 
   * @return displayBackButtonModeFlg
   */
  public boolean isDisplayBackButtonModeFlg() {
    return displayBackButtonModeFlg;
  }

  /**
   * displayBackButtonModeFlgを設定します。
   * 
   * @param displayBackButtonModeFlg
   *          displayBackButtonModeFlg
   */
  public void setDisplayBackButtonModeFlg(boolean displayBackButtonModeFlg) {
    this.displayBackButtonModeFlg = displayBackButtonModeFlg;
  }
//soukai delete 2012/1/2 ob start
  /**
   * postalCodeを取得します。
   * 
   * @return postalCode
   *//*
  public String getPostalCode() {
    return postalCode;
  }

  *//**
   * postalCodeを設定します。
   * 
   * @param postalCode
   *          postalCode
   *//*
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }*/
//soukai delete 2012/1/2 ob end
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
    setBirthDate(StringUtil.joint('/', reqparam.get("birthDate_year"), reqparam.get("birthDate_month"), reqparam
        .get("birthDate_day")));
  //soukai delete 2012/1/2 ob start
  /*  setPhoneNumber1(reqparam.get("phoneNumber_1"));
    //delete by V10-CH 170 start
    setPhoneNumber2(reqparam.get("phoneNumber_2"));
    setPhoneNumber3(reqparam.get("phoneNumber_3"));*/
  //soukai delete 2012/1/2 ob start
    //delete by V10-CH 170 end
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020190";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderCustomerRegisterBean.0");
  }
//soukai delete 2012/1/2 ob start
  /**
   * address1を取得します。
   * 
   * @return address1
   *//*
  public String getAddress1() {
    return address1;
  }

  *//**
   * address1を設定します。
   * 
   * @param address1
   *          address1
   *//*
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  *//**
   * address2を取得します。
   * 
   * @return address2
   *//*
  public String getAddress2() {
    return address2;
  }

  *//**
   * address2を設定します。
   * 
   * @param address2
   *          address2
   *//*
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  *//**
   * address3を取得します。
   * 
   * @return address3
   *//*
  public String getAddress3() {
    return address3;
  }

  *//**
   * address3を設定します。
   * 
   * @param address3
   *          address3
   *//*
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  *//**
   * address4を取得します。
   * 
   * @return address4
   *//*
  public String getAddress4() {
    return address4;
  }

  *//**
   * address4を設定します。
   * 
   * @param address4
   *          address4
   *//*
  public void setAddress4(String address4) {
    this.address4 = address4;
  }*/
//soukai delete 2012/1/2 ob end
  /**
   * birthDateを取得します。
   * 
   * @return birthDate
   */
  public String getBirthDate() {
    return birthDate;
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
   * cautionを取得します。
   * 
   * @return caution
   */
  public String getCaution() {
    return caution;
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
   * displayNextButtonModeFlgを取得します。
   * 
   * @return displayNextButtonModeFlg
   */
  public boolean isDisplayNextButtonModeFlg() {
    return displayNextButtonModeFlg;
  }

  /**
   * displayRegisterButtonModeFlgを取得します。
   * 
   * @return displayRegisterButtonModeFlg
   */
  public boolean isDisplayRegisterButtonModeFlg() {
    return displayRegisterButtonModeFlg;
  }

  /**
   * displayNextButtonModeFlgを設定します。
   * 
   * @param displayNextButtonModeFlg
   *          displayNextButtonModeFlg
   */
  public void setDisplayNextButtonModeFlg(boolean displayNextButtonModeFlg) {
    this.displayNextButtonModeFlg = displayNextButtonModeFlg;
  }

  /**
   * displayRegisterButtonModeFlgを設定します。
   * 
   * @param displayRegisterButtonModeFlg
   *          displayRegisterButtonModeFlg
   */
  public void setDisplayRegisterButtonModeFlg(boolean displayRegisterButtonModeFlg) {
    this.displayRegisterButtonModeFlg = displayRegisterButtonModeFlg;
  }

  /**
   * displayTextModeを取得します。
   * 
   * @return displayTextMode
   */
  public String getDisplayTextMode() {
    return displayTextMode;
  }

  /**
   * displayTextModeを設定します。
   * 
   * @param displayTextMode
   *          displayTextMode
   */
  public void setDisplayTextMode(String displayTextMode) {
    this.displayTextMode = displayTextMode;
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
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
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
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
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
   * firstNameKanaを設定します。
   * 
   * @param firstNameKana
   *          firstNameKana
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
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
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
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
   * lastNameKanaを設定します。
   * 
   * @param lastNameKana
   *          lastNameKana
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }
//soukai delete 2012/1/2 ob start
//  /**
//   * phoneNumber1を取得します。
//   * 
//   * @return phoneNumber1
//   */
//  public String getPhoneNumber1() {
//    return phoneNumber1;
//  }
//
//  /**
//   * phoneNumber1を設定します。
//   * 
//   * @param phoneNumber1
//   *          phoneNumber1
//   */
//  public void setPhoneNumber1(String phoneNumber1) {
//    this.phoneNumber1 = phoneNumber1;
//  }
//
////  /**
////   * phoneNumber2を取得します。
////   * 
////   * @return phoneNumber2
////   */
//  public String getPhoneNumber2() {
//    return phoneNumber2;
//  }
////
////  /**
////   * phoneNumber2を設定します。
////   * 
////   * @param phoneNumber2
////   *          phoneNumber2
////   */
//  public void setPhoneNumber2(String phoneNumber2) {
//    this.phoneNumber2 = phoneNumber2;
//  }
////
////  /**
////   * phoneNumber3を取得します。
////   * 
////   * @return phoneNumber3
////   */
//  public String getPhoneNumber3() {
//    return phoneNumber3;
//  }
////
////  /**
////   * phoneNumber3を設定します。
////   * 
////   * @param phoneNumber3
////   *          phoneNumber3
////   */
//  public void setPhoneNumber3(String phoneNumber3) {
//    this.phoneNumber3 = phoneNumber3;
//  }
//
//  /**
//   * prefectureCodeを取得します。
//   * 
//   * @return prefectureCode
//   */
//  public String getPrefectureCode() {
//    return prefectureCode;
//  }
//
//  /**
//   * prefectureCodeを設定します。
//   * 
//   * @param prefectureCode
//   *          prefectureCode
//   */
//  public void setPrefectureCode(String prefectureCode) {
//    this.prefectureCode = prefectureCode;
//  }
//soukai delete 2012/1/2 ob end
  /**
   * requestMailTypeを取得します。
   * 
   * @return requestMailType
   */
  public String getRequestMailType() {
    return requestMailType;
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
   * sexを取得します。
   * 
   * @return sex
   */
  public String getSex() {
    return sex;
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
//soukai delete 2012/1/2 ob start
/*  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }*/
//soukai delete 2012/1/2 ob end
  public List<CodeAttribute> getCityList() {
    return cityList;
  }

  public void setCityList(List<CodeAttribute> cityList) {
    this.cityList = cityList;
  }

/**
 * @return the emailConfirm
 */
public String getEmailConfirm() {
	return emailConfirm;
}

/**
 * @param emailConfirm the emailConfirm to set
 */
public void setEmailConfirm(String emailConfirm) {
	this.emailConfirm = emailConfirm;
}

//soukai delete 2012/1/2 ob start
/*  *//**
   * mobileNumberを取得します。
   *
   * @return mobileNumber mobileNumber
   *//*
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  *//**
   * mobileNumberを設定します。
   *
   * @param mobileNumber 
   *          mobileNumber
   *//*
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }*/
//soukai delete 2012/1/2 ob end
}
