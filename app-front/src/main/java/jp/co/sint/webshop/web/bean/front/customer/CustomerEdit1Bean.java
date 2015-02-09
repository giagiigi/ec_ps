package jp.co.sint.webshop.web.bean.front.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030210:お客様情報登録1のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit1Bean extends UIFrontBean {

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
	// 20111212 lirong Delete start
	// @Required
	// 20111212 lirong Delete end
	@Length(20)
	@Metadata(name = "氏名(名)")
	private String firstName;

	/** 氏名カナ(姓) */
	// 20111212 lirong Delete start
	// @Required
	// 20111212 lirong Delete end
	@Length(40)
	@Kana
	@Metadata(name = "氏名カナ(姓)")
	private String lastNameKana;

	/** 氏名カナ(名) */
	// 20111212 lirong Delete start
	// @Required
	// 20111212 lirong Delete end
	@Length(40)
	@Kana
	@Metadata(name = "氏名カナ(名)")
	private String firstNameKana;

	/** 郵便番号 */
	// 20111212 lirong Delete start
	// @Required
	// 20111212 lirong Delete end
	@PostalCode
	@Metadata(name = "郵便番号")
	private String postalCode;
	// 20111212 lirong Delete start
	// @Required
	// 20111212 lirong Delete end
	@Length(2)
	@AlphaNum2
	@Metadata(name = "都道府県")
	private String prefectureCode;

	/** 住所1 */
	@Metadata(name = "都道府県")
	private String address1;

	/** 市コード */
	// 20111212 lirong Delete start
	// @Required
	// 20111212 lirong Delete end
	@Length(3)
	@Metadata(name = "市コード")
	private String cityCode;

	/** 住所2 */
	// @Required
	// @Length(50)
	@Metadata(name = "市区町村")
	private String address2;

	/** 住所3 */
	// 20111212 lirong Delete start
	// @Required
	@Length(50)
	@Metadata(name = "町名・番地")
	private String address3;

	/** 住所4 */
	@Length(100)
	@Metadata(name = "アパート・マンション・ビル")
	private String address4;

	/** 電話番号1 */
	@Length(4)
	// @Digit(allowNegative = false)
	@Metadata(name = "電話番号1")
	private String phoneNumber1;

	// delete by V10-CH 170 start
	/** 電話番号2 */
	@Length(8)
	// @Digit(allowNegative = false)
	@Metadata(name = "電話番号2")
	private String phoneNumber2;

	/** 電話番号3 */
	@Length(6)
	// @Digit(allowNegative = false)
	@Metadata(name = "電話番号3")
	private String phoneNumber3;

	// delete by V10-CH 170 end
	/** 手机号码 */
	@Length(11)
	@MobileNumber
	@Metadata(name = "手机号码")
	private String mobileNumber;
	// Add by V10-CH start

	// Add by V10-CH end

	/** メールアドレス */
	@Required
	@Length(256)
	@Email
	@Metadata(name = "メールアドレス")
	private String email;

	// 20111212 lirong add start
	/** メールアドレス(確認) */
	@Length(256)
	@Email
	@Metadata(name = "メールアドレス確認")
	private String emailCon;
	// 20111212 lirong add end

	/** パスワード */
	@Length(50)
	@Metadata(name = "パスワード")
	private String password;

	/** パスワード(確認) */
	@Length(50)
	@Metadata(name = "パスワード確認")
	private String passwordCon;

	/** 生年月日 */
	@Datetime
	@Metadata(name = "生年月日")
	private String birthDate;
	/** 性別 */
	@Required
	@Length(1)
	@Metadata(name = "性別")
	private String sex;

	/** 情報メール */
	@Required
	@Length(1)
	@Metadata(name = "情報メール")
	private String requestMailType;

	// 20111214 lirong add start
	/** 验证码 */
	@Length(4)
	@Metadata(name = "验证码")
	private String verificationCode;
	// 20111214 lirong add end
	// 20111225 os013 add start
	/** 支付宝用户编号 */
	@Length(16)
	@Metadata(name = "支付宝用户编号", order = 30)
	private Long tmallUserId;

	/** 会员区分 */
	@Length(1)
	@Metadata(name = "会员区分", order = 31)
	private Long customerKbn;
	// 20111225 os013 add end
	/** 更新日時(顧客) */
	private Date updatedDatetimeCustomer;

	/** 更新日時(アドレス帳) */
	private Date updatedDatetimeAddress;

	/** 更新処理モード */
	private String displayMode;

	/** 顧客属性存在フラグ */
	private boolean hasCustomerAttributeFlg;
	// 20111230 os013 add start
	/** 区分购物车进入还是我的页面进入 
	 *false表示从我的页面进入
	 */
	private boolean moveFlg=false;
	// 20111230 os013 add end
	// modify by V10-CH 170 start
	private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();

	// modify by V10-CH 170 end
	
  //20120510 tuxinwei add start
  /** 语言编号 */
  @Required
  @Length(5)
  @Metadata(name = "语言编号")
  private String languageCode;
  //20120510 tuxinwei add end
  
  //2013/04/01 优惠券对应 ob add start
  //携帯検証FLG
  private boolean validateMobileFlg = Boolean.FALSE;
  
  //携帯電話
  private String mobile;
  //2013/04/01 优惠券对应 ob add end

	/**
	 * postalCodeを取得します。
	 * 
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * postalCodeを設定します。
	 * 
	 * @param postalCode
	 *            postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * hasCustomerAttributeFlgを取得します。
	 * 
	 * @return hasCustomerAttributeFlg
	 */
	public boolean isHasCustomerAttributeFlg() {
		return hasCustomerAttributeFlg;
	}

	/**
	 * hasCustomerAttributeFlgを設定します。
	 * 
	 * @param hasCustomerAttributeFlg
	 *            hasCustomerAttributeFlg
	 */
	public void setHasCustomerAttributeFlg(boolean hasCustomerAttributeFlg) {
		this.hasCustomerAttributeFlg = hasCustomerAttributeFlg;
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
	 * emailCon確認を取得します。
	 * 
	 * @return emailCon
	 */
	public String getEmailCon() {
		return emailCon;
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
	 * passwordを取得します。
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * phoneNumber1を取得します。
	 * 
	 * @return phoneNumber1
	 */
	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	/**
	 * phoneNumber2を取得します。
	 * 
	 * @return phoneNumber2
	 */
	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	/**
	 * phoneNumber3を取得します。
	 * 
	 * @return phoneNumber3
	 */
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
	 * address1を設定します。
	 * 
	 * @param address1
	 *            address1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * address2を設定します。
	 * 
	 * @param address2
	 *            address2
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * address3を設定します。
	 * 
	 * @param address3
	 *            address3
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * address4を設定します。
	 * 
	 * @param address4
	 *            address4
	 */
	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	/**
	 * birthDateを設定します。
	 * 
	 * @param birthDate
	 *            birthDate
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * customerCodeを設定します。
	 * 
	 * @param customerCode
	 *            customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * emailを設定します。
	 * 
	 * @param email
	 *            email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * emailCon確認を設定します。
	 * 
	 * @param emailCon
	 *            emailCon
	 */
	public void setEmailCon(String emailCon) {
		this.emailCon = emailCon;
	}

	/**
	 * firstNameを設定します。
	 * 
	 * @param firstName
	 *            firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * firstNameKanaを設定します。
	 * 
	 * @param firstNameKana
	 *            firstNameKana
	 */
	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}

	/**
	 * lastNameを設定します。
	 * 
	 * @param lastName
	 *            lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * lastNameKanaを設定します。
	 * 
	 * @param lastNameKana
	 *            lastNameKana
	 */
	public void setLastNameKana(String lastNameKana) {
		this.lastNameKana = lastNameKana;
	}

	/**
	 * passwordを設定します。
	 * 
	 * @param password
	 *            password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * phoneNumber1を設定します。
	 * 
	 * @param phoneNumber1
	 *            phoneNumber1
	 */
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	/**
	 * phoneNumber2を設定します。
	 * 
	 * @param phoneNumber2
	 *            phoneNumber2
	 */
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	/**
	 * phoneNumber3を設定します。
	 * 
	 * @param phoneNumber3
	 *            phoneNumber3
	 */
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}

	/**
	 * requestMailTypeを設定します。
	 * 
	 * @param requestMailType
	 *            requestMailType
	 */
	public void setRequestMailType(String requestMailType) {
		this.requestMailType = requestMailType;
	}

	/**
	 * sexを設定します。
	 * 
	 * @param sex
	 *            sex
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
	 *            リクエストパラメータ
	 */
	public void createAttributes(RequestParameter reqparam) {
		reqparam.copy(this);
    setLastName(StringUtil.parse(reqparam.get("lastName")));
		setBirthDate(reqparam.getDateString("birthDate"));
		setPhoneNumber1(reqparam.get("phoneNumber_1"));
		// delete by V10-CH 170 start
		setPhoneNumber2(reqparam.get("phoneNumber_2"));
		setPhoneNumber3(reqparam.get("phoneNumber_3"));
		setMobileNumber(reqparam.get("mobileNumber"));
		// delete by V10-CH 170 end
	  //20120510 tuxinwei add start
		setLanguageCode(reqparam.get("languageCode"));
	  //20120510 tuxinwei add end
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
		return Messages
				.getString("web.bean.front.customer.CustomerEdit1Bean.1");
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

	/**
	 * updatedDatetimeAddressを設定します。
	 * 
	 * @param updatedDatetimeAddress
	 *            updatedDatetimeAddress
	 */
	public void setUpdatedDatetimeAddress(Date updatedDatetimeAddress) {
		this.updatedDatetimeAddress = DateUtil
				.immutableCopy(updatedDatetimeAddress);
	}

	/**
	 * updatedDatetimeAddressを取得します。
	 * 
	 * @param updatedDatetimeCustomer
	 *            updatedDatetimeCustomer
	 */
	public void setUpdatedDatetimeCustomer(Date updatedDatetimeCustomer) {
		this.updatedDatetimeCustomer = DateUtil
				.immutableCopy(updatedDatetimeCustomer);
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
	 *            passwordCon
	 */
	public void setPasswordCon(String passwordCon) {
		this.passwordCon = passwordCon;
	}

	/**
	 * displayModeを取得します。
	 * 
	 * @return displayMode
	 */
	public String getDisplayMode() {
		return displayMode;
	}

	/**
	 * displayModeを設定します。
	 * 
	 * @param displayMode
	 *            displayMode
	 */
	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}

	/**
	 * prefectureCodeを取得します。
	 * 
	 * @return the prefectureCode
	 */
	public String getPrefectureCode() {
		return prefectureCode;
	}

	/**
	 * prefectureCodeを設定します。
	 * 
	 * @param prefectureCode
	 *            prefectureCode
	 */
	public void setPrefectureCode(String prefectureCode) {
		this.prefectureCode = prefectureCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public List<CodeAttribute> getCityList() {
		return cityList;
	}

	public void setCityList(List<CodeAttribute> cityList) {
		this.cityList = cityList;
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
	 *            mobileNumber
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	// 20111214 lirong add start
	/**
	 * VerificationCodeを取得します。
	 * 
	 * @return VerificationCode VerificationCode
	 */
	public String getVerificationCode() {
		return verificationCode;
	}

	/**
	 * VerificationCodeを設定します。
	 * 
	 * @param VerificationCode
	 *            VerificationCode
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	// 20111214 lirong add end

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
	 * 支付宝用户编号を設定します
	 * 
	 * @param val
	 *            支付宝用户编号
	 */
	public void setTmallUserId(Long tmallUserId) {
		this.tmallUserId = tmallUserId;
	}

	/**
	 * 会员区分を取得します
	 * 
	 * @return 会员区分
	 */
	public Long getCustomerKbn() {
		return customerKbn;
	}

	/**
	 * 会员区分を設定します
	 * 
	 * @param val
	 *            会员区分
	 */
	public void setCustomerKbn(Long customerKbn) {
		this.customerKbn = customerKbn;
	}

	public boolean isMoveFlg() {
		return moveFlg;
	}

	public void setMoveFlg(boolean moveFlg) {
		this.moveFlg = moveFlg;
	}

  
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
  //2013/04/01 优惠券对应 ob add start
  /**
   * 携帯検証FLGを取得する
   * @return
   */
  public boolean isValidateMobileFlg() {
    return validateMobileFlg;
  }

  /**
   * 携帯検証FLGを設定する
   * @param validateMobileFlg
   */
  public void setValidateMobileFlg(boolean validateMobileFlg) {
    this.validateMobileFlg = validateMobileFlg;
  }

  /**
   * 携帯電話を取得する
   * @return
   */
  public String getMobile() {
    return mobile;
  }

  /**
   * 携帯電話を設定する
   * @param mobile
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  
  /**
   * @return the companyCode
   */
  public String getCompanyCode() {
    return companyCode;
  }

  
  /**
   * @param companyCode the companyCode to set
   */
  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }
  
 //2013/04/01 优惠券对应 ob add end
}
