package jp.co.sint.webshop.web.bean.back.order;

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
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020120:新規受注（顧客選択）のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerBean extends NeworderBaseBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

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
  @Digit
  @Metadata(name = "电话号码")
  private String searchPhoneNumber;

  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码")
  private String searchMobileNumber;
  
  private List<CodeAttribute> searchCustomerGroupList = new ArrayList<CodeAttribute>();

  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客グループコード")
  private String searchCustomerGroupCode;

  private Cashier cashier;

  private List<SearchedCustomerListBean> list = new ArrayList<SearchedCustomerListBean>();

  private PagerValue pagerValue = new PagerValue();

  private boolean customerPermission;

  /**
   * U1020120:新規受注（顧客選択）のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class SearchedCustomerListBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String customerCode;

    private String customerGrouopCode;

    private String customerGroupName;

    private Long customerGroupPointRate;

    private String lastName;

    private String firstName;

    private String lastNameKana;

    private String firstNameKana;

    private String email;

    @Length(18)
    @Phone
    private String phoneNumber;
    
    //Add by V10-CH start
    @Length(11)
    @MobileNumber
    private String mobileNumber;
    //Add by V10-CH end
    
    private String caution;

    private boolean hasCaution;

    /**
     * customerCodeを取得します。
     * 
     * @return customerCode customerCode
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
     * customerGrouopCodeを取得します。
     * 
     * @return customerGrouopCode customerGrouopCode
     */
    public String getCustomerGrouopCode() {
      return customerGrouopCode;
    }

    /**
     * customerGrouopCodeを設定します。
     * 
     * @param customerGrouopCode
     *          customerGrouopCode
     */
    public void setCustomerGrouopCode(String customerGrouopCode) {
      this.customerGrouopCode = customerGrouopCode;
    }

    /**
     * customerGroupNameを取得します。
     * 
     * @return customerGroupName customerGroupName
     */
    public String getCustomerGroupName() {
      return customerGroupName;
    }

    /**
     * customerGroupNameを設定します。
     * 
     * @param customerGroupName
     *          customerGroupName
     */
    public void setCustomerGroupName(String customerGroupName) {
      this.customerGroupName = customerGroupName;
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
     * phoneNumberを取得します。
     * 
     * @return phoneNumber
     */
    public String getPhoneNumber() {
      return phoneNumber;
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
     * customerGroupPointRateを取得します。
     * 
     * @return customerGroupPointRate
     */
    public Long getCustomerGroupPointRate() {
      return customerGroupPointRate;
    }

    /**
     * customerGroupPointRateを設定します。
     * 
     * @param customerGroupPointRate
     *          customerGroupPointRate
     */
    public void setCustomerGroupPointRate(Long customerGroupPointRate) {
      this.customerGroupPointRate = customerGroupPointRate;
    }

    /**
     * hasCautionを取得します。
     * 
     * @return hasCaution
     */
    public boolean isHasCaution() {
      return hasCaution;
    }

    /**
     * hasCautionを設定します。
     * 
     * @param hasCaution
     *          hasCaution
     */
    public void setHasCaution(boolean hasCaution) {
      this.hasCaution = hasCaution;
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
   * listを取得します。
   * 
   * @return list
   */
  public List<SearchedCustomerListBean> getList() {
    return list;
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
   * searchEmailを取得します。
   * 
   * @return searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  /**
   * searchPhoneNumberを取得します。
   * 
   * @return searchPhoneNumber
   */
  public String getSearchPhoneNumber() {
    return searchPhoneNumber;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<SearchedCustomerListBean> list) {
    this.list = list;
  }

  /**
   * searchCustomerGroupを設定します。
   * 
   * @param searchCustomerGroupCode
   *          searchCustomerGroupCode
   */
  public void setSearchCustomerGroupCode(String searchCustomerGroupCode) {
    this.searchCustomerGroupCode = searchCustomerGroupCode;
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
   * searchEmailを設定します。
   * 
   * @param searchEmail
   *          searchEmail
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  /**
   * searchPhoneNumberを設定します。
   * 
   * @param searchPhoneNumber
   *          searchPhoneNumber
   */
  public void setSearchPhoneNumber(String searchPhoneNumber) {
    this.searchPhoneNumber = searchPhoneNumber;
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
   * searchCustomerGroupListを設定します。
   * 
   * @param searchCustomerGroupList
   *          searchCustomerGroupList
   */
  public void setSearchCustomerGroupList(List<CodeAttribute> searchCustomerGroupList) {
    this.searchCustomerGroupList = searchCustomerGroupList;
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
    return "U1020120";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderCustomerBean.0");
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
   * cashierを設定します。
   * 
   * @param cashier
   *          cashier
   */
  public void setCashier(Cashier cashier) {
    this.cashier = cashier;
  }

  /**
   * cashierを取得します。
   * 
   * @return cashier
   */
  public Cashier getCashier() {
    return cashier;
  }

  /**
   * customerPermissionを取得します。
   * 
   * @return customerPermission
   */
  public boolean isCustomerPermission() {
    return customerPermission;
  }

  /**
   * customerPermissionを設定します。
   * 
   * @param customerPermission
   *          customerPermission
   */
  public void setCustomerPermission(boolean customerPermission) {
    this.customerPermission = customerPermission;
  }

  
  /**
   * searchMobileNumberを取得します。
   *
   * @return searchMobileNumber searchMobileNumber
   */
  public String getSearchMobileNumber() {
    return searchMobileNumber;
  }

  
  /**
   * searchMobileNumberを設定します。
   *
   * @param searchMobileNumber 
   *          searchMobileNumber
   */
  public void setSearchMobileNumber(String searchMobileNumber) {
    this.searchMobileNumber = searchMobileNumber;
  }

}
