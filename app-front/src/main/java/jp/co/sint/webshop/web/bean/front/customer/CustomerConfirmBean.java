package jp.co.sint.webshop.web.bean.front.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030230:お客様情報登録確認のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerConfirmBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CustomerAttributeListConfirmBean> attributeList = new ArrayList<CustomerAttributeListConfirmBean>();

  /** 顧客コード */
  private String customerCode;

  /** 姓 */
  @Required
  private String lastName;

  /** 名 */
  @Required
  private String firstName;

  /** 姓カナ */
  @Required
  private String lastNameKana;

  /** 名カナ */
  @Required
  private String firstNameKana;

  /** 郵便番号 */
  @Required
  private String postalCode;

  /** 都道府県コード */
  @Required
  private String prefectureCode;

  /** 住所1 */
  @Required
  private String address1;

  @Required
  private String cityCode;
  
  /** 住所2 */
  @Required
  private String address2;

  /** 住所3 */
  @Required
  private String address3;

  /** 住所4 */
  private String address4;

  /** 電話番号1 */
  @Length(4)
  //@Digit
  private String phoneNumber1;

  //modify by V10-CH 170 start
  /** 電話番号2 */
  @Length(8)
  //@Digit
  private String phoneNumber2;

  /** 電話番号3 */
  @Length(6)
  //@Digit
  private String phoneNumber3;
  
  //modify by V10-CH 170 end
  @Length(20)
  @MobileNumber
  private String mobileNumber;
  //Add by V10-CH start
  
  //Add by V10-Ch end
  /** 生年月日 */
  @Required
  private String birthDate;

  /** メールアドレス */
  @Required
  private String email;

  /** パスワード */
  private String password;

  /** 性別 */
  @Required
  private String sex;

  /** 情報メール */
  @Required
  private String requestMailType;

  /** 更新日時(顧客) */
  private Date updatedDatetimeCustomer;

  /** 更新日時(アドレス帳) */
  private Date updatedDatetimeAddress;

  /** 更新処理モード */
  private String displayMode;

  /** 顧客属性存在フラグ */
  private boolean hasCustomerAttributeFlg;

  /** 次ページ指定パラメータ */
  private String nextPage = "";

  /** 登録/更新完了フラグ */
  private boolean complete;

  /** 不正遷移判定フラグ */
  private boolean isFailedTransitionFlg;

  /**
   * completeを返します。
   * 
   * @return the complete
   */
  public boolean isComplete() {
    return complete;
  }

  /**
   * completeを設定します。
   * 
   * @param complete
   *          設定する complete
   */
  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  /**
   * nextPageを取得します。
   * 
   * @return nextPage
   */
  public String getNextPage() {
    return nextPage;
  }

  /**
   * nextPageを設定します。
   * 
   * @param nextPage
   *          nextPage
   */
  public void setNextPage(String nextPage) {
    this.nextPage = nextPage;
  }

  /**
   * hasCustomerAttributeFlgを設定します。
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
   *          hasCustomerAttributeFlg
   */
  public void setHasCustomerAttributeFlg(boolean hasCustomerAttributeFlg) {
    this.hasCustomerAttributeFlg = hasCustomerAttributeFlg;
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
   *          displayMode
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * attributeListを取得します。
   * 
   * @return attributeList
   */
  public List<CustomerAttributeListConfirmBean> getAttributeList() {
    return attributeList;
  }

  /**
   * attributeListを設定します。
   * 
   * @param attributeList
   *          attributeList
   */
  public void setAttributeList(List<CustomerAttributeListConfirmBean> attributeList) {
    this.attributeList = attributeList;
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
   * passwordを設定します。
   * 
   * @param password
   *          password
   */
  public void setPassword(String password) {
    this.password = password;
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

  /**
   * phoneNumber2を設定します。
   * 
   * @param phoneNumber2
   *          phoneNumber2
   */
  public void setPhoneNumber2(String phoneNumber2) {
    this.phoneNumber2 = phoneNumber2;
  }

  /**
   * phoneNumber3を設定します。
   * 
   * @param phoneNumber3
   *          phoneNumber3
   */
  public void setPhoneNumber3(String phoneNumber3) {
    this.phoneNumber3 = phoneNumber3;
  }

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
   *          postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
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
   * U2030230:お客様情報登録確認のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeListConfirmBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 顧客属性番号 */
    private String customerAttributeNo;

    /** 顧客属性名称 */
    private String customerAttributeName;

    /** 顧客属性区分 */
    private String customerAttributeType;

    /** 顧客属性選択肢回答リスト */
    private List<String> attributeAnswerList = new ArrayList<String>();

    /** 表示用顧客属性選択肢回答リスト */
    private List<String> dispAttributeAnswerList = new ArrayList<String>();

    /** 顧客属性選択肢番号 */
    private String attributeAnswer;

    /** 表示用顧客属性選択肢名 */
    private String dispAttributeAnswer;

    /**
     * customerAttributeTypeを取得します。
     * 
     * @return customerAttributeType
     */
    public String getCustomerAttributeType() {
      return customerAttributeType;
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

    /**
     * customerAttributeNameを取得します。
     * 
     * @return customerAttributeName
     */
    public String getCustomerAttributeName() {
      return customerAttributeName;
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
     * dispAttributeAnswerを取得します。
     * 
     * @return dispAttributeAnswer
     */
    public String getDispAttributeAnswer() {
      return dispAttributeAnswer;
    }

    /**
     * dispAttributeAnswerを取得します。
     * 
     * @return dispAttributeAnswerList
     */
    public List<String> getDispAttributeAnswerList() {
      return dispAttributeAnswerList;
    }

    /**
     * dispAttributeAnswerを設定します。
     * 
     * @param dispAttributeAnswer
     *          dispAttributeAnswer
     */
    public void setDispAttributeAnswer(String dispAttributeAnswer) {
      this.dispAttributeAnswer = dispAttributeAnswer;
    }

    /**
     * dispAttributeAnswerListを設定します。
     * 
     * @param dispAttributeAnswerList
     *          dispAttributeAnswerList
     */
    public void setDispAttributeAnswerList(List<String> dispAttributeAnswerList) {
      this.dispAttributeAnswerList = dispAttributeAnswerList;
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
     * attributeAnswerを設定します。
     * 
     * @param attributeAnswer
     *          attributeAnswer
     */
    public void setAttributeAnswer(String attributeAnswer) {
      this.attributeAnswer = attributeAnswer;
    }

    /**
     * attributeAnswerListを取得します。
     * 
     * @return attributeAnswerList
     */
    public List<String> getAttributeAnswerList() {
      return attributeAnswerList;
    }

    /**
     * attributeAnswerListを設定します。
     * 
     * @param attributeAnswerList
     *          attributeAnswerList
     */
    public void setAttributeAnswerList(List<String> attributeAnswerList) {
      this.attributeAnswerList = attributeAnswerList;
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
     * customerAttributeNoを設定します。
     * 
     * @param customerAttributeNo
     *          customerAttributeNo
     */
    public void setCustomerAttributeNo(String customerAttributeNo) {
      this.customerAttributeNo = customerAttributeNo;
    }

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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030230";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.CustomerConfirmBean.0");
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
   *          prefectureCode
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * isFailedTransitionFlgを返します。
   * 
   * @return the isFailedTransitionFlg
   */
  public boolean isFailedTransitionFlg() {
    return isFailedTransitionFlg;
  }

  /**
   * isFailedTransitionFlgを設定します。
   * 
   * @param failedTransitionFlg
   *          設定する isFailedTransitionFlg
   */
  public void setFailedTransitionFlg(boolean failedTransitionFlg) {
    this.isFailedTransitionFlg = failedTransitionFlg;
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

}
