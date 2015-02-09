package jp.co.sint.webshop.web.bean.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.CustomerCancelableFlg;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050220:ショップマスタ登録のデータモデルです。


 * 
 * @author System Integrator Corp.
 */
public class ShopEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード")
  private String shopCode;

  @Required
  @Datetime
  @Metadata(name = "開店日付")
  private String openDatetime;

  @Datetime
  // 10.1.7 10288 修正 ここから
  // @Metadata(name = "閉店日付 ")
  @Metadata(name = "閉店日付")
  // 10.1.7 10288 修正 ここまで
  private String closeDatetime;

  @Required
  @Length(30)
  @Metadata(name = "ショップ名")
  private String shopName;

  @Required
  @Length(10)
  @Metadata(name = "ショップ略名")
  private String shortShopName;

  @Required
  @Length(7)
  @PostalCode
  @Metadata(name = "郵便番号")
  private String postCode;

  @Required
  @Length(2)
  @Metadata(name = "住所1(都道府県)")
  private String prefectureCode;

  @Metadata(name = "住所1(都道府県)")
  private String address1;

  /** 市コード */
  @Required
  @Length(3)
  @Metadata(name = "市コード", order = 19)
  private String cityCode;
  
  //@Required
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
  @Digit(allowNegative = false)
  @Metadata(name = "電話番号1")
  private String tel1;

  @Length(8)
  @Digit(allowNegative = false)
  @Metadata(name = "電話番号2")
  private String tel2;

  @Length(6)
  @Digit(allowNegative = false)
  @Metadata(name = "電話番号3")
  private String tel3;

  //Add by V10-CH start
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码")
  private String mobileTel;
  //Add by V10-CH end
  
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String email;

  @Length(20)
  @Metadata(name = "担当")
  private String personInCharge;

  private String display;

  @Length(256)
  @Url
  @Metadata(name = "ショップ紹介")
  private String shopIntroducedUrl;
  
  //add by VC-H start  
  @Required
  @Length(20)
  @Metadata(name = "ICP登録コード")
  private String icpCode;
  // Add by V10-CH start end
  
  /** 顧客キャンセルフラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "フロント側からのキャンセル", order = 17)
  private Long customerCancelableFlg = CustomerCancelableFlg.ENABLED.longValue();
  
  private Date updatedDate;

  /** コンテンツのアップロード可否 */
  private boolean contentsUploadFlg;

  private boolean updateProcessFlg;

  private boolean deleteButtonDisplayFlg;

  private boolean nextButtonDisplayFlg;

  private boolean registerButtonDisplayFlg;

  private boolean updateButtonDisplayFlg;

  private boolean backButtonDisplayFlg;
  
  // modify by V10-CH 170 start
  private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();
  // modify by V10-CH 170 end
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
   *          registerButtonDisplayFlg
   */
  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
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
   * updateButtonDisplayFlgを設定します。


   * 
   * @param updateButtonDisplayFlg
   *          updateButtonDisplayFlg
   */
  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
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
   * backButtonDisplayFlgを設定します。


   * 
   * @param backButtonDisplayFlg
   *          backButtonDisplayFlg
   */
  public void setBackButtonDisplayFlg(boolean backButtonDisplayFlg) {
    this.backButtonDisplayFlg = backButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを取得します。


   * 
   * @return deleteButtonDisplayFlg
   */
  public boolean isDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを設定します。


   * 
   * @param deleteButtonDisplayFlg
   *          deleteButtonDisplayFlg
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
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
   * closeDatetimeを取得します。


   * 
   * @return closeDatetime
   */
  public String getCloseDatetime() {
    return closeDatetime;
  }

  /**
   * emailを取得します。


   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }
// Add by V10-CH start
  public String getIcpCode() {
    return icpCode;
  }
  public void setIcpCode(String icpCode) {
    this.icpCode = icpCode;
  }
//Add by V10-CH end  
  /**
   * openDatetimeを取得します。


   * 
   * @return openDatetime
   */
  public String getOpenDatetime() {
    return openDatetime;
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
   * shopIntroducedUrlを取得します。


   * 
   * @return shopIntroducedUrl
   */
  public String getShopIntroducedUrl() {
    return shopIntroducedUrl;
  }

  /**
   * shopNameを取得します。


   * 
   * @return shopName
   */
  public String getShopName() {
    return shopName;
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
   * closeDatetimeを設定します。


   * 
   * @param closeDatetime
   *          closeDatetime
   */
  public void setCloseDatetime(String closeDatetime) {
    this.closeDatetime = closeDatetime;
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
   * openDatetimeを設定します。


   * 
   * @param openDatetime
   *          openDatetime
   */
  public void setOpenDatetime(String openDatetime) {
    this.openDatetime = openDatetime;
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
   * shopIntroducedUrlを設定します。


   * 
   * @param shopIntroducedUrl
   *          shopIntroducedUrl
   */
  public void setShopIntroducedUrl(String shopIntroducedUrl) {
    this.shopIntroducedUrl = shopIntroducedUrl;
  }

  /**
   * shopNameを設定します。


   * 
   * @param shopName
   *          shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
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
    setCustomerCancelableFlg(NumUtil.toLong(reqparam.get("customerCancelableFlg")));
    setOpenDatetime(StringUtil.coalesce(reqparam.getDateString("openDatetime"), reqparam.get("openDatetime"), getOpenDatetime()));
    setCloseDatetime(StringUtil
        .coalesce(reqparam.getDateString("closeDatetime"), reqparam.get("closeDatetime"), getCloseDatetime()));
    if (StringUtil.hasValueAnyOf(reqparam.get("tel_1"), reqparam.get("tel_2"), reqparam.get("tel_3"))) {
      setTel1(reqparam.get("tel_1"));
      setTel2(reqparam.get("tel_2"));
      setTel3(reqparam.get("tel_3"));
    }
    if(StringUtil.hasValueAnyOf(reqparam.get("mobileTel"))){
      setMobileTel(reqparam.get("mobileTel"));
    }
  }

  /**
   * モジュールIDを取得します。


   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050220";
  }

  /**
   * モジュール名を取得します。


   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.ShopEditBean.0");
  }

  /**
   * displayを取得します。


   * 
   * @return display
   */
  public String getDisplay() {
    return display;
  }

  /**
   * displayを設定します。


   * 
   * @param display
   *          display
   */
  public void setDisplay(String display) {
    this.display = display;
  }

  /**
   * updateProcessFlgを取得します。


   * 
   * @return updateProcessFlg insert or update
   */
  public boolean isUpdateProcessFlg() {
    return updateProcessFlg;
  }

  /**
   * processModeを設定します。


   * 
   * @param processMode
   *          更新処理フラグ(insert or update)
   */
  public void setUpdateProcessFlg(boolean processMode) {
    this.updateProcessFlg = processMode;
  }

  /**
   * updatedDateを取得します。


   * 
   * @return updatedDate
   */
  public Date getUpdatedDate() {
    return DateUtil.immutableCopy(updatedDate);
  }

  /**
   * updatedDateを設定します。


   * 
   * @param updatedDate
   *          updatedDate
   */
  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = DateUtil.immutableCopy(updatedDate);
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
   * postCodeを取得します。


   * 
   * @return postCode
   */
  public String getPostCode() {
    return postCode;
  }

  /**
   * postCodeを設定します。


   * 
   * @param postCode
   *          postCode
   */
  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  /**
   * tel1を取得します。


   * 
   * @return tel1 tel1
   */
  public String getTel1() {
    return tel1;
  }

  /**
   * tel1を設定します。


   * 
   * @param tel1
   *          tel1
   */
  public void setTel1(String tel1) {
    this.tel1 = tel1;
  }

  /**
   * tel2を取得します。


   * 
   * @return tel2 tel2
   */
  public String getTel2() {
    return tel2;
  }

  /**
   * tel2を設定します。


   * 
   * @param tel2
   *          tel2
   */
  public void setTel2(String tel2) {
    this.tel2 = tel2;
  }

  /**
   * tel3を取得します。


   * 
   * @return tel3 tel3
   */
  public String getTel3() {
    return tel3;
  }

  /**
   * tel3を設定します。


   * 
   * @param tel3
   *          tel3
   */
  public void setTel3(String tel3) {
    this.tel3 = tel3;
  }

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
   * shortShopNameを取得します。


   * 
   * @return shortShopName
   */

  public String getShortShopName() {
    return shortShopName;
  }

  /**
   * shortShopNameを設定します。


   * 
   * @param shortShopName
   *          shortShopName
   */
  public void setShortShopName(String shortShopName) {
    this.shortShopName = shortShopName;
  }

  /**
   * contentsUploadFlgを取得します。


   * 
   * @return contentsUploadFlg
   */

  public boolean isContentsUploadFlg() {
    return contentsUploadFlg;
  }

  /**
   * contentsUploadFlgを設定します。


   * 
   * @param contentsUploadFlg
   *          contentsUploadFlg
   */
  public void setContentsUploadFlg(boolean contentsUploadFlg) {
    this.contentsUploadFlg = contentsUploadFlg;
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
   * mobileTelを取得します。

   *
   * @return mobileTel mobileTel
   */
  public String getMobileTel() {
    return mobileTel;
  }

  
  /**
   * mobileTelを設定します。

   *
   * @param mobileTel 
   *          mobileTel
   */
  public void setMobileTel(String mobileTel) {
    this.mobileTel = mobileTel;
  }

}
