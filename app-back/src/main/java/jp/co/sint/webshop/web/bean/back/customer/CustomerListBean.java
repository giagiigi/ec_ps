package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.EmailForSearch;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030110:顧客マスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CustomerDetail> list = new ArrayList<CustomerDetail>();

  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード(From)")
  private String searchCustomerFrom;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード(To)")
  private String searchCustomerTo;

  private PagerValue pagerValue;

  private List<CodeAttribute> searchCustomerGroupList = new ArrayList<CodeAttribute>();

  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客グループ")
  private String searchCustomerGroupCode;

  @Length(40)
  @Metadata(name = "顧客名")
  private String searchCustomerName;

  @Length(80)
  @Kana
  @Metadata(name = "顧客名カナ")
  private String searchCustomerNameKana;

  @Length(256)
  @EmailForSearch
  @Metadata(name = "メールアドレス")
  private String searchEmail;

  @Length(18)
  @Digit(allowNegative = false)
  @Metadata(name = "電話番号")
  private String searchTel;

  // Add by V10-CH start
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机番号")
  private String searchMobile;

  // Add by V10-CH end

  @Length(1)
  @Metadata(name = "会員状態")
  private String searchCustomerStatus;

  @Length(1)
  @Metadata(name = "情報メール")
  private String searchRequestMailType;

  @Length(1)
  @Metadata(name = "クライアントメール区分")
  private String searchClientMailType;

  /** データ入出力ボタン表示フラグ */
  private boolean iODisplayFlg;

  /** 削除ボタン表示フラグ */
  private boolean deleteDisplayFlg;

  /** ポイント履歴ボタン表示フラグ */
  private boolean pointDisplayFlg;

  // add by V10-CH start
  /** ポイント履歴ボタン表示フラグ */
  private boolean couponDisplayFlg;
  // add by V10-CH end

  /** 受注履歴ボタン表示フラグ */
  private boolean orderDisplayFlg;

  /** 情報メール送信ボタン表示フラグ */
  private boolean mailDisplayFlg;

  /** 情報メール希望顧客存在有無フラグ */
  private boolean hasNoRequestCustomer;

  /** ショップ管理者フラグ */
  private boolean isShop;

  /** 退会失敗顧客コードリスト */
  private List<String> errorCustomerCode;

  /** 退会成功フラグ */
  private boolean seccessWithdrawal;

  /**
   * U1030110:顧客マスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String customerCheck;

    private String customerCode;

    private String firstName;

    private String lastName;

    private String email;

    private String tel;

    // Add by V10-CH start
    private String mobileNumber;

    // Add by V10-CH end

    private String requestMailType;

    private boolean isWithDrawal;

    private boolean isReceivedWithdrawalNotice;

    private String caution;

    private boolean cautionFlg;

    /**
     * cautionを取得します。
     * 
     * @return caution
     */
    public String getCaution() {
      return caution;
    }

    /**
     * cautionFlgを取得します。
     * 
     * @return cautionFlg
     */
    public boolean isCautionFlg() {
      return cautionFlg;
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
     * cautionFlgを設定します。
     * 
     * @param cautionFlg
     *          cautionFlg
     */
    public void setCautionFlg(boolean cautionFlg) {
      this.cautionFlg = cautionFlg;
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
     * isWithDrawalを取得します。
     * 
     * @return isWithDrawal
     */
    public boolean isWithDrawal() {
      return isWithDrawal;
    }

    /**
     * isWithDrawalを設定します。
     * 
     * @param isWithDrawal
     *          isWithDrawal
     */
    public void setIsWithDrawal(boolean isWithDrawal) {
      this.isWithDrawal = isWithDrawal;
    }

    /**
     * customerCheckを取得します。
     * 
     * @return customerCheck
     */
    public String getCustomerCheck() {
      return customerCheck;
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
     * firstNameを取得します。
     * 
     * @return firstName
     */
    public String getFirstName() {
      return firstName;
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
     * requestMailTypeを取得します。
     * 
     * @return requestMailType
     */
    public String getRequestMailType() {
      return requestMailType;
    }

    /**
     * telを取得します。
     * 
     * @return tel
     */
    public String getTel() {
      return tel;
    }

    /**
     * customerCheckを設定します。
     * 
     * @param customerCheck
     *          customerCheck
     */
    public void setCustomerCheck(String customerCheck) {
      this.customerCheck = customerCheck;
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
     * firstNameを設定します。
     * 
     * @param firstName
     *          firstName
     */
    public void setFirstName(String firstName) {
      this.firstName = firstName;
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
     * requestMailTypeを設定します。
     * 
     * @param requestMailType
     *          requestMailType
     */
    public void setRequestMailType(String requestMailType) {
      this.requestMailType = requestMailType;
    }

    /**
     * telを設定します。
     * 
     * @param tel
     *          tel
     */
    public void setTel(String tel) {
      this.tel = tel;
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
     * @param inputEmail
     *          email情報
     */
    public void setEmail(String inputEmail) {
      this.email = inputEmail;
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
   * searchClientMailTypeを取得します。
   * 
   * @return searchClientMailType
   */
  public String getSearchClientMailType() {
    return searchClientMailType;
  }

  /**
   * searchClientMailTypeを設定します。
   * 
   * @param searchClientMailType
   *          searchClientMailType
   */
  public void setSearchClientMailType(String searchClientMailType) {
    this.searchClientMailType = searchClientMailType;
  }

  /**
   * customerStatusMemberを取得します。
   * 
   * @return CustomerStatus.MEMBER.getValue()
   */
  public String getCustomerStatusMemberValue() {
    return CustomerStatus.MEMBER.getValue();
  }

  /**
   * requestMailTypeUnwantedを取得します。
   * 
   * @return RequestMailType.UNWANTED.getValue()
   */
  public String getRequestMailTypeUnwantedValue() {
    return RequestMailType.UNWANTED.getValue();
  }

  /**
   * hasNoRequestCustomerを取得します。
   * 
   * @return hasNoRequestCustomer
   */
  public boolean isHasNoRequestCustomer() {
    return hasNoRequestCustomer;
  }

  /**
   * hasNoRequestCustomerを設定します。
   * 
   * @param hasNoRequestCustomer
   *          hasNoRequestCustomer
   */
  public void setHasNoRequestCustomer(boolean hasNoRequestCustomer) {
    this.hasNoRequestCustomer = hasNoRequestCustomer;
  }

  /**
   * mailDisplayFlgを取得します。
   * 
   * @return mailDisplayFlg
   */
  public boolean isMailDisplayFlg() {
    return mailDisplayFlg;
  }

  /**
   * mailDisplayFlgを設定します。
   * 
   * @param mailDisplayFlg
   *          mailDisplayFlg
   */
  public void setMailDisplayFlg(boolean mailDisplayFlg) {
    this.mailDisplayFlg = mailDisplayFlg;
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
   * seccessWithdrawalを取得します。
   * 
   * @return seccessWithdrawal
   */
  public boolean isSeccessWithdrawal() {
    return seccessWithdrawal;
  }

  /**
   * seccessWithdrawalを設定します。
   * 
   * @param seccessWithdrawal
   *          seccessWithdrawal
   */
  public void setSeccessWithdrawal(boolean seccessWithdrawal) {
    this.seccessWithdrawal = seccessWithdrawal;
  }

  /**
   * errorCustomerCodeを取得します。
   * 
   * @return errorCustomerCode
   */
  public List<String> getErrorCustomerCode() {
    return errorCustomerCode;
  }

  /**
   * errorCustomerCodeを設定します。
   * 
   * @param errorCustomerCode
   *          errorCustomerCode
   */
  public void setErrorCustomerCode(List<String> errorCustomerCode) {
    this.errorCustomerCode = errorCustomerCode;
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

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1030110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.CustomerListBean.0");
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<CustomerDetail> getList() {
    return list;
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
   * searchCustomerGroupListを取得します。
   * 
   * @return searchCustomerGroupList
   */
  public List<CodeAttribute> getSearchCustomerGroupList() {
    return searchCustomerGroupList;
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
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CustomerDetail> list) {
    this.list = list;
  }

  /**
   * searchCustomerFromを設定します。
   * 
   * @param searchCustomerFrom
   *          searchCustomerFrom
   */
  public void setSearchCustomerFrom(String searchCustomerFrom) {
    this.searchCustomerFrom = searchCustomerFrom;
  }

  /**
   * searchCustomerGroupCodeを設定します。
   * 
   * @param searchCustomerGroupCode
   *          searchCustomerGroupCode
   */
  public void setSearchCustomerGroupCode(String searchCustomerGroupCode) {
    this.searchCustomerGroupCode = searchCustomerGroupCode;
  }

  /**
   * searchCustomerGroupListを設定します。
   * 
   * @param searchCustomerGroupList
   *          searchCustomerGroupList
   */
  public void setSearchCustomerGroupList(List<CodeAttribute> searchCustomerGroupList) {
    this.searchCustomerGroupList = searchCustomerGroupList;
  }

  /**
   * searchCustomerNameを設定します。
   * 
   * @param searchCustomerName
   *          searchCustomerName
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを設定します。
   * 
   * @param searchCustomerNameKana
   *          searchCustomerNameKana
   */
  public void setSearchCustomerNameKana(String searchCustomerNameKana) {
    this.searchCustomerNameKana = searchCustomerNameKana;
  }

  /**
   * searchCustomerStatusを設定します。
   * 
   * @param searchCustomerStatus
   *          searchCustomerStatus
   */
  public void setSearchCustomerStatus(String searchCustomerStatus) {
    this.searchCustomerStatus = searchCustomerStatus;
  }

  /**
   * searchCustomerToを設定します。
   * 
   * @param searchCustomerTo
   *          searchCustomerTo
   */
  public void setSearchCustomerTo(String searchCustomerTo) {
    this.searchCustomerTo = searchCustomerTo;
  }

  /**
   * searchEmailを設定します。
   * 
   * @param searchEmail
   *          searchEmail
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  /**
   * searchRequestMailTypeを設定します。
   * 
   * @param searchRequestMailType
   *          searchRequestMailType
   */
  public void setSearchRequestMailType(String searchRequestMailType) {
    this.searchRequestMailType = searchRequestMailType;
  }

  /**
   * searchTelを設定します。
   * 
   * @param searchTel
   *          searchTel
   */
  public void setSearchTel(String searchTel) {
    this.searchTel = searchTel;
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
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
   * iODisplayFlgを取得します。
   * 
   * @return iODisplayFlg
   */
  public boolean isIODisplayFlg() {
    return iODisplayFlg;
  }

  /**
   * iODisplayFlgを設定します。
   * 
   * @param displayFlg
   *          iODisplayFlg
   */
  public void setIODisplayFlg(boolean displayFlg) {
    iODisplayFlg = displayFlg;
  }

  /**
   * isShopを取得します。
   * 
   * @return isShop
   */
  public boolean isShop() {
    return isShop;
  }

  /**
   * isShopを設定します。
   * 
   * @param shop
   *          ショップ管理者フラグ
   */
  public void setShop(boolean shop) {
    this.isShop = shop;
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

  // 10.1.4 10146 追加 ここから
  public boolean isSelectedWithdrawables() {
    return !CustomerStatus.WITHDRAWED.getValue().equals(StringUtil.coalesce(getSearchCustomerStatus(), ""));
  }

  // 10.1.4 10146 追加 ここまで

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
    this.searchMobile = searchMobile;
  }

  public boolean isCouponDisplayFlg() {
    return couponDisplayFlg;
  }

  public void setCouponDisplayFlg(boolean couponDisplayFlg) {
    this.couponDisplayFlg = couponDisplayFlg;
  }
}
