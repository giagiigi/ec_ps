package jp.co.sint.webshop.web.bean.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050110:サイト設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SiteinfoEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  @Required
  @Length(30)
  @Metadata(name = "サイト名")
  private String siteName;

  @Required
  @Length(10)
  @Metadata(name = "サイト略名")
  private String shortSiteName;

  @Required
  @Length(7)
  @PostalCode
  @Metadata(name = "郵便番号")
  private String postalCode;

  @Required
  @Length(2)
  @Metadata(name = "住所1(都道府県)")
  private String prefectureCode;

  @Metadata(name = "住所1(都道府県)")
  private String address1;

  /** 市コード */
  @Required
  @Length(3)
  @Metadata(name = "市コード")
  private String cityCode;

  @Required
  @Length(50)
  @Metadata(name = "住所2(市区町村)")
  private String address2;

  @Required
  @Length(50)
  @Metadata(name = "住所3(町名・番地)")
  private String address3;

  @Length(100)
  @Metadata(name = "住所4(ビル名・階など)")
  private String address4;

  @Length(4)
  //@Digit(allowNegative = false)
  @Metadata(name = "電話番号1")
  private String phoneNumber1;

  @Length(8)
  //@Digit(allowNegative = false)
  @Metadata(name = "電話番号2")
  private String phoneNumber2;

  @Length(6)
  //@Digit(allowNegative = false)
  @Metadata(name = "電話番号3")
  private String phoneNumber3;

  // Add by V10-CH start
  @MobileNumber
  @Metadata(name = "手机号码")
  private String mobileNumber;

  // Add by V10-CH end

  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String mailAddress;

  @Required
  @Length(20)
  @Metadata(name = "担当")
  private String personInCharge;

  // 2010/04/27 ShiKui Add Start.
  @Required
  @Length(20)
  @Metadata(name = "ICP登録コード")
  private String icpCode;

  // 2010/04/27 ShiKui Add End.

  private Date updateDatetime;

  private String updateDatetimeStr;

  private String readonlyMode;

  private boolean confirmButtonDisplay;

  private boolean registerButtonDisplay;

  private boolean backButtonDisplay;

  @Length(256)
  @Url
  @Metadata(name = "ショップ紹介URL", order = 6)
  private String shopIntroducedUrl;

  /** 顧客キャンセルフラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "フロント側からのキャンセル", order = 17)
  private Long customerCancelableFlg;

  // modify by V10-CH 170 start
  private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();

  // modify by V10-CH 170 end

  /**
   * customerCancelableFlgを取得します。
   * 
   * @return customerCancelableFlg
   */
  public Long getCustomerCancelableFlg() {
    return customerCancelableFlg;
  }

  /**
   * customerCancelableFlgを設定します。
   * 
   * @param customerCancelableFlg
   *          customerCancelableFlg
   */
  public void setCustomerCancelableFlg(Long customerCancelableFlg) {
    this.customerCancelableFlg = customerCancelableFlg;
  }

  /**
   * backButtonDisplayを取得します。
   * 
   * @return backButtonDisplay
   */
  public boolean isBackButtonDisplay() {
    return backButtonDisplay;
  }

  /**
   * backButtonDisplayを設定します。
   * 
   * @param backButtonDisplay
   *          backButtonDisplay
   */
  public void setBackButtonDisplay(boolean backButtonDisplay) {
    this.backButtonDisplay = backButtonDisplay;
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
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param param
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter param) {
    param.copy(this);
    setCustomerCancelableFlg(NumUtil.toLong(param.get("customerCancelableFlg")));
    setPhoneNumber1(param.get("phoneNumber_1"));
    setPhoneNumber2(param.get("phoneNumber_2"));
    setPhoneNumber3(param.get("phoneNumber_3"));
    setMobileNumber(param.get("mobileNumber"));
    setAddress3(StringUtil.parse(param.get("address3")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.SiteinfoEditBean.0");
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
   * address1を設定します。
   * 
   * @param address1
   *          address1
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
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
   * address2を設定します。
   * 
   * @param address2
   *          address2
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
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
   * address3を設定します。
   * 
   * @param address3
   *          address3
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
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
   * address4を設定します。
   * 
   * @param address4
   *          address4
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  /**
   * mailAddressを取得します。
   * 
   * @return mailAddress
   */
  public String getMailAddress() {
    return mailAddress;
  }

  /**
   * mailAddressを設定します。
   * 
   * @param mailAddress
   *          mailAddress
   */
  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  /**
   * personInChargeを取得します。
   * 
   * @return personInCharge
   */
  public String getPersonInCharge() {
    return personInCharge;
  }

  /**
   * personInChargeを設定します。
   * 
   * @param personInCharge
   *          personInCharge
   */
  public void setPersonInCharge(String personInCharge) {
    this.personInCharge = personInCharge;
  }

  /**
   * readonlyModeを取得します。
   * 
   * @return readonlyMode
   */
  public String getReadonlyMode() {
    return readonlyMode;
  }

  /**
   * readonlyModeを設定します。
   * 
   * @param readonlyMode
   *          readonlyMode
   */
  public void setReadonlyMode(String readonlyMode) {
    this.readonlyMode = readonlyMode;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * siteNameを取得します。
   * 
   * @return siteName
   */
  public String getSiteName() {
    return siteName;
  }

  /**
   * siteNameを設定します。
   * 
   * @param siteName
   *          siteName
   */
  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  /**
   * updateDatetimeを取得します。
   * 
   * @return updateDatetime
   */
  public Date getUpdateDatetime() {
    return updateDatetime;
  }

  /**
   * updateDatetimeを設定します。
   * 
   * @param updateDatetime
   *          updateDatetime
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  /**
   * confirmButtonDisplayを取得します。
   * 
   * @return confirmButtonDisplay
   */
  public boolean isConfirmButtonDisplay() {
    return confirmButtonDisplay;
  }

  /**
   * confirmButtonDisplayを設定します。
   * 
   * @param confirmButtonDisplay
   *          confirmButtonDisplay
   */
  public void setConfirmButtonDisplay(boolean confirmButtonDisplay) {
    this.confirmButtonDisplay = confirmButtonDisplay;
  }

  /**
   * registerButtonDisplayを取得します。
   * 
   * @return registerButtonDisplay
   */
  public boolean isRegisterButtonDisplay() {
    return registerButtonDisplay;
  }

  /**
   * registerButtonDisplayを設定します。
   * 
   * @param registerButtonDisplay
   *          registerButtonDisplay
   */
  public void setRegisterButtonDisplay(boolean registerButtonDisplay) {
    this.registerButtonDisplay = registerButtonDisplay;
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
   * Number1を取得します。
   * 
   * @return phoneNumber1
   */
  public String getPhoneNumber1() {
    return phoneNumber1;
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
   * phoneNumber2を取得します。
   * 
   * @return phoneNumber2
   */
  public String getPhoneNumber2() {
    return phoneNumber2;
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
   * phoneNumber3を取得します。
   * 
   * @return phoneNumber3 phoneNumber3
   */
  public String getPhoneNumber3() {
    return phoneNumber3;
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
   * shortSiteNameを取得します。
   * 
   * @return shortSiteName
   */

  public String getShortSiteName() {
    return shortSiteName;
  }

  /**
   * shortSiteNameを設定します。
   * 
   * @param shortSiteName
   *          shortSiteName
   */
  public void setShortSiteName(String shortSiteName) {
    this.shortSiteName = shortSiteName;
  }

  /**
   * shopIntroducedUrlを取得します。
   * 
   * @return shopIntroducedUrl
   */
  public String getShopIntroducedUrl() {
    return shopIntroducedUrl;
  }

  /**
   * shopIntroducedUrlを設定します。
   * 
   * @param shopIntroducedUrl
   *          shopIntroducedUrl
   */
  public void setShopIntroducedUrl(String shopIntroducedUrl) {
    this.shopIntroducedUrl = shopIntroducedUrl;
  }

  // 2010/04/27 ShiKui Add Start.
  public String getIcpCode() {
    return icpCode;
  }

  public void setIcpCode(String icpCode) {
    this.icpCode = icpCode;
  }

  // 2010/04/27 ShiKui Add End.

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
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getUpdateDatetimeStr() {
    return updateDatetimeStr;
  }

  public void setUpdateDatetimeStr(String updateDatetimeStr) {
    this.updateDatetimeStr = updateDatetimeStr;
  }
}
