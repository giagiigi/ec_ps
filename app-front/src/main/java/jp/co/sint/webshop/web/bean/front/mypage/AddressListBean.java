package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030310:アドレス帳一覧のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class AddressListBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  private List<AddressListDetailBean> list = new ArrayList<AddressListDetailBean>();

  private PagerValue pagerValue;

  private String deleteAddressNo;

  @Length(8)
  @Digit
  @Metadata(name = "アドレス帳番号")
  private String addressNo;




  @Length(20)
  @Metadata(name = "お届け先名")
  private String addressAlias;

  @Required
  @Length(20)
  @Metadata(name = "氏名(姓)")
  private String addressLastName;

  @Length(20)
  @Metadata(name = "氏名(名)")
  private String addressFirstName;

  @Length(40)
  @Kana
  @Metadata(name = "氏名カナ(姓)")
  private String addressLastNameKana;

  @Length(40)
  @Kana
  @Metadata(name = "氏名カナ(名)")
  private String addressFirstNameKana;

  @Required
  @PostalCode
  @Metadata(name = "郵便番号")
  private String postalCode;

  @Required
  @Length(2)
  @AlphaNum2
  @Metadata(name = "都道府県")
  private String prefectureCode;

  @Metadata(name = "都道府県")
  private String address1;

  //@Required
  @Length(50)
  @Metadata(name = "市区町村")
  private String address2;

  /** 市コード */
  @Required
  @Length(3)
  @Metadata(name = "市コード")
  private String cityCode;

  // 20120107 os013 add start
  @Length(4)
  @Metadata(name = "区县", order = 22)
  private String areaCode;
  // 20120107 os013 add end

  @Length(50)
  @Metadata(name = "町名・番地")
  private String address3;
  
  @Required
  @Length(100)
  @Metadata(name = "アパート・マンション・ビル")
  private String address4;

  @Length(6)
  //@Digit(allowNegative = false)
  @Metadata(name = "電話番号1")
  private String phoneNumber1;

  @Length(10)
  //@Digit(allowNegative = false)
  @Metadata(name = "電話番号2")
  private String phoneNumber2;

  @Length(6)
  //@Digit(allowNegative = false)
  @Metadata(name = "電話番号3")
  private String phoneNumber3;

  @Phone
  @Metadata(name = "電話番号")
  private String phoneNumber;

  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码")
  private String mobileNumber;
  
  /** 次へ進むボタン表示フラグ */
  private boolean displayNextButtonFlg;

  /** 更新するボタン表示フラグ */
  private boolean displayUpdateButtonFlg;

  /** 登録するボタン表示フラグ */
  private boolean displayRegisterButtonFlg;

  /** 戻るボタン表示フラグ */
  private boolean displayBackButtonFlg;

  /** 一覧へ戻るボタン表示フラグ */
  private boolean displayListBackButtonFlg;

  /** 編集ード */
  private String editMode;

  /** 更新日時 */
  private Date updatedDatetime;

  // modify by V10-CH 170 start
  private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();
  // modify by V10-CH 170 end
  // 20120107 os013 add start
  private List<CodeAttribute> prefectureList = new ArrayList<CodeAttribute>();
  private List<CodeAttribute> areaList = new ArrayList<CodeAttribute>();
  private String addressScript;
  // 20120107 os013 add end
  public String getCityCode() {
    return cityCode;
  }

  
  public String getAreaCode() {
  return areaCode;
  }


  public void setAreaCode(String areaCode) {
  this.areaCode = areaCode;
  }


  public List<CodeAttribute> getPrefectureList() {
  return prefectureList;
  }


  public void setPrefectureList(List<CodeAttribute> prefectureList) {
  this.prefectureList = prefectureList;
  }


  public List<CodeAttribute> getAreaList() {
  return areaList;
  }


  public void setAreaList(List<CodeAttribute> areaList) {
  this.areaList = areaList;
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

  public String getAddressScript() {
  return addressScript;
  }


  public void setAddressScript(String addressScript) {
  this.addressScript = addressScript;
  }


/**
   * displayListBackButtonFlgを返します。
   * 
   * @return the displayListBackButtonFlg
   */
  public boolean isDisplayListBackButtonFlg() {
    return displayListBackButtonFlg;
  }

  /**
   * displayListBackButtonFlgを設定します。
   * 
   * @param displayListBackButtonFlg
   *          設定する displayListBackButtonFlg
   */
  public void setDisplayListBackButtonFlg(boolean displayListBackButtonFlg) {
    this.displayListBackButtonFlg = displayListBackButtonFlg;
  }

  /**
   * displayBackButtonFlgを返します。
   * 
   * @return the displayBackButtonFlg
   */
  public boolean isDisplayBackButtonFlg() {
    return displayBackButtonFlg;
  }

  /**
   * displayNextButtonFlgを返します。
   * 
   * @return the displayNextButtonFlg
   */
  public boolean isDisplayNextButtonFlg() {
    return displayNextButtonFlg;
  }

  /**
   * displayRegisterButtonFlgを返します。
   * 
   * @return the displayRegisterButtonFlg
   */
  public boolean isDisplayRegisterButtonFlg() {
    return displayRegisterButtonFlg;
  }

  /**
   * displayUpdateButtonFlgを返します。
   * 
   * @return the displayUpdateButtonFlg
   */
  public boolean isDisplayUpdateButtonFlg() {
    return displayUpdateButtonFlg;
  }

  /**
   * displayBackButtonFlgを設定します。
   * 
   * @param displayBackButtonFlg
   *          設定する displayBackButtonFlg
   */
  public void setDisplayBackButtonFlg(boolean displayBackButtonFlg) {
    this.displayBackButtonFlg = displayBackButtonFlg;
  }

  /**
   * displayNextButtonFlgを設定します。
   * 
   * @param displayNextButtonFlg
   *          設定する displayNextButtonFlg
   */
  public void setDisplayNextButtonFlg(boolean displayNextButtonFlg) {
    this.displayNextButtonFlg = displayNextButtonFlg;
  }

  /**
   * displayRegisterButtonFlgを設定します。
   * 
   * @param displayRegisterButtonFlg
   *          設定する displayRegisterButtonFlg
   */
  public void setDisplayRegisterButtonFlg(boolean displayRegisterButtonFlg) {
    this.displayRegisterButtonFlg = displayRegisterButtonFlg;
  }

  /**
   * displayUpdateButtonFlgを設定します。
   * 
   * @param displayUpdateButtonFlg
   *          設定する displayUpdateButtonFlg
   */
  public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
    this.displayUpdateButtonFlg = displayUpdateButtonFlg;
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
   * updatedDatetimeを取得します。
   * 
   * @return updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * updatedDatetimeを設定します。
   * 
   * @param updatedDatetime
   *          updatedDatetime
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
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
   * addressAliasを取得します。
   * 
   * @return addressAlias
   */
  public String getAddressAlias() {
    return addressAlias;
  }

  /**
   * addressFirstNameを取得します。
   * 
   * @return addressFirstName
   */
  public String getAddressFirstName() {
    return addressFirstName;
  }

  /**
   * addressFirstNameKanaを取得します。
   * 
   * @return addressFirstNameKana
   */
  public String getAddressFirstNameKana() {
    return addressFirstNameKana;
  }

  /**
   * addressLastNameを取得します。
   * 
   * @return addressLastName
   */
  public String getAddressLastName() {
    return addressLastName;
  }

  /**
   * addressLastNameKanaを取得します。
   * 
   * @return addressLastNameKana
   */
  public String getAddressLastNameKana() {
    return addressLastNameKana;
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
   * addressAliasを設定します。
   * 
   * @param addressAlias
   *          addressAlias
   */
  public void setAddressAlias(String addressAlias) {
    this.addressAlias = addressAlias;
  }

  /**
   * addressFirstNameを設定します。
   * 
   * @param addressFirstName
   *          addressFirstName
   */
  public void setAddressFirstName(String addressFirstName) {
    this.addressFirstName = addressFirstName;
  }

  /**
   * addressFirstNameKanaを設定します。
   * 
   * @param addressFirstNameKana
   *          addressFirstNameKana
   */
  public void setAddressFirstNameKana(String addressFirstNameKana) {
    this.addressFirstNameKana = addressFirstNameKana;
  }

  /**
   * addressLastNameを設定します。
   * 
   * @param addressLastName
   *          addressLastName
   */
  public void setAddressLastName(String addressLastName) {
    this.addressLastName = addressLastName;
  }

  /**
   * addressLastNameKanaを設定します。
   * 
   * @param addressLastNameKana
   *          addressLastNameKana
   */
  public void setAddressLastNameKana(String addressLastNameKana) {
    this.addressLastNameKana = addressLastNameKana;
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
   * phoneNumberを返します。
   * 
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * phoneNumberを設定します。
   * 
   * @param phoneNumber
   *          設定する phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
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
   * U2030310:アドレス帳一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AddressListDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** アドレス帳番号 */
    private String addressNo;

    /** お届け先名 */
    private String addressAlias;

    /** 氏名(姓) */
    private String addressLastName;

    /** 氏名(名) */
    private String addressFirstName;

    /** 郵便番号 */
    private String postalCode;

    /** 住所1 */
    private String address1;

    /** 住所2 */
    private String address2;

    /** 住所3 */
    private String address3;

    /** 住所4 */
    private String address4;

    /** 電話番号 */
    private String phoneNumber;
    
    /** 手机号码 */
    private String mobileNumber;

    /** 削除ボタン表示フラグ * */
    private boolean deleteDisplayFlg;

    /** 編集ボタン表示フラグ */
    private boolean editDisplayFlg;

    /**
     * editDisplayFlgを取得します。
     * 
     * @return editDisplayFlg
     */
    public boolean isEditDisplayFlg() {
      return editDisplayFlg;
    }

    /**
     * editDisplayFlgを設定します。
     * 
     * @param editDisplayFlg
     *          editDisplayFlg
     */
    public void setEditDisplayFlg(boolean editDisplayFlg) {
      this.editDisplayFlg = editDisplayFlg;
    }

    /**
     * deleteDisplayFlgを取得します。
     * 
     * @return deleteDisplayFlg
     */
    public boolean isDeleteDisplayFlg() {
      return deleteDisplayFlg;
    }

    /**
     * deleteDisplayFlgを設定します。
     * 
     * @param deleteDisplayFlg
     *          deleteDisplayFlg
     */
    public void setDeleteDisplayFlg(boolean deleteDisplayFlg) {
      this.deleteDisplayFlg = deleteDisplayFlg;
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
     * addressAliasを取得します。
     * 
     * @return addressAlias
     */
    public String getAddressAlias() {
      return addressAlias;
    }

    /**
     * addressFirstNameを取得します。
     * 
     * @return addressFirstName
     */
    public String getAddressFirstName() {
      return addressFirstName;
    }

    /**
     * addressLastNameを取得します。
     * 
     * @return addressLastName
     */
    public String getAddressLastName() {
      return addressLastName;
    }

    /**
     * addressNoを取得します。
     * 
     * @return addressNo
     */
    public String getAddressNo() {
      return addressNo;
    }

    /**
     * phoneNumberを取得します。
     * 
     * @return phoneNumber
     */
    public String getPhoneNumber() {
      return phoneNumber;
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
     * addressAliasを設定します。
     * 
     * @param addressAlias
     *          addressAlias
     */
    public void setAddressAlias(String addressAlias) {
      this.addressAlias = addressAlias;
    }

    /**
     * addressFirstNameを設定します。
     * 
     * @param addressFirstName
     *          addressFirstName
     */
    public void setAddressFirstName(String addressFirstName) {
      this.addressFirstName = addressFirstName;
    }

    /**
     * addressLastNameを設定します。
     * 
     * @param addressLastName
     *          addressLastName
     */
    public void setAddressLastName(String addressLastName) {
      this.addressLastName = addressLastName;
    }

    /**
     * addressNoを設定します。
     * 
     * @param addressNo
     *          addressNo
     */
    public void setAddressNo(String addressNo) {
      this.addressNo = addressNo;
    }

    /**
     * phoneNumberを設定します。
     * 
     * @param phoneNumber
     *          phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
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

  /**
   * addressNoを取得します。
   * 
   * @return addressNo
   */
  public String getAddressNo() {
    return addressNo;
  }

  /**
   * addressNoを設定します。
   * 
   * @param addressNo
   *          addressNo
   */
  public void setAddressNo(String addressNo) {
    this.addressNo = addressNo;
  }

  /**
   * アドレス一覧を取得します。
   * 
   * @return list
   */
  public List<AddressListDetailBean> getList() {
    return list;
  }

  /**
   * アドレス一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<AddressListDetailBean> list) {
    this.list = list;
  }
// del by lc 2012-02-21 start
//  /**
//   * サブJSPを設定します。
//   */
//  @Override
//  public void setSubJspId() {
//  }
//  del by lc 2012-02-21 end

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    setAddressLastName(StringUtil.parse(reqparam.get("addressLastName")));
    setAddress4(StringUtil.parse(reqparam.get("address4")));
    setPhoneNumber1(reqparam.get("phoneNumber_1"));
    //delete by V10-CH 170 start
    setPhoneNumber2(reqparam.get("phoneNumber_2"));
    setPhoneNumber3(reqparam.get("phoneNumber_3"));
    if(StringUtil.hasValueAllOf(getPhoneNumber1(),getPhoneNumber2(),getPhoneNumber3())){
      setPhoneNumber(StringUtil.joint('-',getPhoneNumber1(),getPhoneNumber2(),getPhoneNumber3()));
    }else if(StringUtil.hasValueAllOf(getPhoneNumber1(),getPhoneNumber2())){
      setPhoneNumber(StringUtil.joint('-',getPhoneNumber1(),getPhoneNumber2()));
    }else{
      setPhoneNumber("");
    }
   // setPhoneNumber(phoneNumber);
    if(reqparam.get("mobileNumber") != null){
      setMobileNumber(reqparam.get("mobileNumber"));
    }else{
      setMobileNumber("");
    }
    //delete by V10-CH 170 end
    if (StringUtil.hasValueAllOf(getPhoneNumber())) {
      setPhoneNumber(getPhoneNumber());
    } else {
      setPhoneNumber("");
    }
    this.setAddressAlias("本人");
  
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.AddressListBean.0");
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * deleteAddressNoを取得します。
   * 
   * @return deleteAddressNo
   */
  public String getDeleteAddressNo() {
    return deleteAddressNo;
  }

  /**
   * deleteAddressNoを設定します。
   * 
   * @param deleteAddressNo
   *          deleteAddressNo
   */
  public void setDeleteAddressNo(String deleteAddressNo) {
    this.deleteAddressNo = deleteAddressNo;
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
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString(
         "web.bean.front.mypage.AddressListBean.1"), "/app/mypage/mypage"));
    if(StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)){
      topicPath.add(new NameValue(Messages.getString(
           "web.bean.front.mypage.AddressListBean.0"), "/app/mypage/address_list/init"));
    }else{
      topicPath.add(new NameValue(Messages.getString(
      "web.bean.mobile.mypage.AdressListBean.1"), "/app/mypage/address_list/init"));
    }
    return topicPath;
  }

}
