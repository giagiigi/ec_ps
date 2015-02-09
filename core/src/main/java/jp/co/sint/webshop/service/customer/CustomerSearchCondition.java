package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.ClientMailType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class CustomerSearchCondition extends SearchCondition {

  
  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  /** 顧客コード */
  @Required
  @Length(16)
  @Metadata(name = "顧客コード")
  private String customerCode;

  /** 顧客コード */
  @Required
  @Length(16)
  @Metadata(name = "顧客コード")
  private String searchCustomerFrom;

  /** 顧客コード */
  @Required
  @Length(16)
  @Metadata(name = "顧客コード")
  private String searchCustomerTo;

  /** 顧客グループコード */
  @Required
  @Length(5)
  @Metadata(name = "顧客グループコード")
  private String searchCustomerGroupCode;

  /** 姓, 名 */
  @Required
  @Length(40)
  @Metadata(name = "姓, 名")
  private String searchCustomerName;

  /** 姓カナ, 名カナ */
  @Required
  @Length(80)
  @Kana
  @Metadata(name = "姓カナ, 名カナ")
  private String searchCustomerNameKana;

  /** メールアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String searchEmail;

  /** 電話番号 */
  @Length(18)
  @Phone
  @Metadata(name = "電話番号")
  private String searchTel;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码")
  private String searchMobile;
  
  /** 顧客ステータス */
  @Required
  @Length(1)
  @Domain(CustomerStatus.class)
  @Metadata(name = "顧客ステータス")
  private String searchCustomerStatus;

  /** 希望メール区分 */
  @Required
  @Length(1)
  @Domain(RequestMailType.class)
  @Metadata(name = "希望メール区分")
  private String searchRequestMailType;

  /** 顧客属性番号 */
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "顧客属性番号")
  private String customerAttributeNo;

  /** 顧客属性選択肢番号 */
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "顧客属性選択肢番号")
  private String customerAttributeChoicesNo;

  /** ログインロックフラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "ログインロックフラグ")
  private String searchLoginLockedFlg;

  /** クライアントメール区分 */
  @Required
  @Length(1)
  @Domain(ClientMailType.class)
  @Metadata(name = "クライアントメール区分")
  private String searchClientMailType;

  /** ショップ管理者フラグ */
  private boolean isShop;

  /** ショップコード */
  private String shopCode;

  /**20111224 os013 add start**/
  /** 支付宝用户编号 */
  @Length(16)
  @Metadata(name = "支付宝用户编号", order = 30)
  private String tmallUserId;
  
  /** 会员区分 */
  @Length(1)
  @Metadata(name = "会员区分", order = 31)
  private String customerKbn;
  /**20111224 os013 add end**/
  
  /**
   * searchClientMailTypeを返します。
   * 
   * @return the searchClientMailType
   */
  public String getSearchClientMailType() {
    return searchClientMailType;
  }

  /**
   * searchClientMailTypeを設定します。
   * 
   * @param searchClientMailType
   *          設定する searchClientMailType
   */
  public void setSearchClientMailType(String searchClientMailType) {
    this.searchClientMailType = searchClientMailType;
  }

  /**
   * searchLoginLockedFlgを返します。
   * 
   * @return the searchLoginLockedFlg
   */
  public String getSearchLoginLockedFlg() {
    return searchLoginLockedFlg;
  }

  /**
   * searchLoginLockedFlgを設定します。
   * 
   * @param searchLoginLockedFlg
   *          設定する searchLoginLockedFlg
   */
  public void setSearchLoginLockedFlg(String searchLoginLockedFlg) {
    this.searchLoginLockedFlg = searchLoginLockedFlg;
  }

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * isShopを返します。
   * 
   * @return the isShop
   */
  public boolean isShop() {
    return isShop;
  }

  /**
   * ショップ検索モードかどうかを設定します。
   * 
   * @param shop
   *          ショップならtrue
   */
  public void setShop(boolean shop) {
    this.isShop = shop;
  }

  /**
   * customerAttributeChoicesNoを取得します。
   * 
   * @return customerAttributeChoicesNo
   */
  public String getCustomerAttributeChoicesNo() {
    return customerAttributeChoicesNo;
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
   * customerAttributeChoicesNoを設定します。
   * 
   * @param customerAttributeChoicesNo
   *          customerAttributeChoicesNo
   */
  public void setCustomerAttributeChoicesNo(String customerAttributeChoicesNo) {
    this.customerAttributeChoicesNo = customerAttributeChoicesNo;
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
   * searchCustomerFromを取得します。
   * 
   * @return searchCustomerFrom
   */
  public String getSearchCustomerFrom() {
    return searchCustomerFrom;
  }

  /**
   * searchCustomerGroupCodeを取得します。
   * 
   * @return searchCustomerGroupCode
   */
  public String getSearchCustomerGroupCode() {
    return searchCustomerGroupCode;
  }

  /**
   * searchCustomerNameを取得します。
   * 
   * @return searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを取得します。
   * 
   * @return searchCustomerNameKana
   */
  public String getSearchCustomerNameKana() {
    return searchCustomerNameKana;
  }

  /**
   * searchCustomerStatusを取得します。
   * 
   * @return searchCustomerStatus
   */
  public String getSearchCustomerStatus() {
    return searchCustomerStatus;
  }

  /**
   * searchCustomerToを取得します。
   * 
   * @return searchCustomerTo
   */
  public String getSearchCustomerTo() {
    return searchCustomerTo;
  }

  /**
   * searchEmailを取得します。
   * 
   * @return searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  /**
   * searchRequestMailTypeを取得します。
   * 
   * @return searchRequestMailType
   */
  public String getSearchRequestMailType() {
    return searchRequestMailType;
  }

  /**
   * searchTelを取得します。
   * 
   * @return searchTel
   */
  public String getSearchTel() {
    return searchTel;
  }

  /**
   * searchCustomerFromを設定します。
   * 
   * @param searchCustomerFrom
   *          searchCustomerFrom
   */
  public void setSearchCustomerFrom(String searchCustomerFrom) {
    if (searchCustomerFrom == null) {
      searchCustomerFrom = "";
    }
    this.searchCustomerFrom = searchCustomerFrom;
  }

  /**
   * searchCustomerGroupCodeを設定します。
   * 
   * @param searchCustomerGroupCode
   *          searchCustomerGroupCode
   */
  public void setSearchCustomerGroupCode(String searchCustomerGroupCode) {
    if (searchCustomerGroupCode == null) {
      searchCustomerGroupCode = "";
    }
    this.searchCustomerGroupCode = searchCustomerGroupCode;
  }

  /**
   * searchCustomerNameを設定します。
   * 
   * @param searchCustomerName
   *          searchCustomerName
   */
  public void setSearchCustomerName(String searchCustomerName) {
    if (searchCustomerName == null) {
      searchCustomerName = "";
    }
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを設定します。
   * 
   * @param searchCustomerNameKana
   *          searchCustomerNameKana
   */
  public void setSearchCustomerNameKana(String searchCustomerNameKana) {
    if (searchCustomerNameKana == null) {
      searchCustomerNameKana = "";
    }
    this.searchCustomerNameKana = searchCustomerNameKana;
  }

  /**
   * searchCustomerStatusを設定します。
   * 
   * @param searchCustomerStatus
   *          searchCustomerStatus
   */
  public void setSearchCustomerStatus(String searchCustomerStatus) {
    if (searchCustomerStatus == null) {
      searchCustomerStatus = "";
    }
    this.searchCustomerStatus = searchCustomerStatus;
  }

  /**
   * searchCustomerToを設定します。
   * 
   * @param searchCustomerTo
   *          searchCustomerTo
   */
  public void setSearchCustomerTo(String searchCustomerTo) {
    if (searchCustomerTo == null) {
      searchCustomerTo = "";
    }
    this.searchCustomerTo = searchCustomerTo;
  }

  /**
   * searchEmailを設定します。
   * 
   * @param searchEmail
   *          searchEmail
   */
  public void setSearchEmail(String searchEmail) {
    if (searchEmail == null) {
      searchEmail = "";
    }
    this.searchEmail = searchEmail;
  }

  /**
   * searchRequestMailTypeを設定します。
   * 
   * @param searchRequestMailType
   *          searchRequestMailType
   */
  public void setSearchRequestMailType(String searchRequestMailType) {
    if (searchRequestMailType == null) {
      searchRequestMailType = "";
    }
    this.searchRequestMailType = searchRequestMailType;
  }

  /**
   * searchTelを設定します。
   * 
   * @param searchTel
   *          searchTel
   */
  public void setSearchTel(String searchTel) {
    if (searchTel == null) {
      searchTel = "";
    }
    this.searchTel = searchTel;
  }

  /**
   * 顧客コードの大小関係をチェック
   */
  public boolean isValid() {

    boolean result = true;

    if (StringUtil.hasValueAllOf(getSearchCustomerFrom(), getSearchCustomerTo())) {
      result &= StringUtil.isCorrectRange(getSearchCustomerFrom(), getSearchCustomerTo());
    }
    return result;

  }

  
  /**
   * searchMobileを取得します。
   *
   * @return searchMobile searchMobile
   */
  public String getSearchMobile() {
    return searchMobile;
  }

  /**
   * searchMobileを設定します。
   *
   * @param searchMobile 
   *          searchMobile
   */
  public void setSearchMobile(String searchMobile) {
    if(searchMobile == null){
      searchMobile = "";
    }
    this.searchMobile = searchMobile;
  }
  
  /**20111224 os013 add start*/
  /**
   * 支付宝用户编号を取得します
   *
   * @return 支付宝用户编号
   */
  public String getTmallUserId() {
    return tmallUserId;
  }
  
  /**
   * 支付宝用户编号を設定します
   *
   * @param  val 支付宝用户编号
   */
  public void setTmallUserId(String tmallUserId) {
    this.tmallUserId = tmallUserId;
  }
  
  /**
   * 会员区分を取得します
   *
   * @return 会员区分
   */
  public String getCustomerKbn() {
    return customerKbn;
  }
  
  /**
   * 会员区分を設定します
   *
   * @param  val 会员区分
   */
  public void setCustomerKbn(String customerKbn) {
    this.customerKbn = customerKbn;
  }
  /**20111224 os013 add end*/
}
