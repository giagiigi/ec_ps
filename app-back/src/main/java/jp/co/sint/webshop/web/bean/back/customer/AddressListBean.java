package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030210:アドレス帳一覧のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class AddressListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<AddressListDetailBean> list = new ArrayList<AddressListDetailBean>();

  private String customerCode;

  private String lastName;

  private String firstName;

  private String lastNameKana;

  private String firstNameKana;

  private String email;

  private String deleteAddressNo;

  /** 新規登録ボタン表示フラグ */
  private boolean insertDisplayFlg;

  /** 編集ボタン表示フラグ */
  private boolean editDisplayFlg;

  private PagerValue pagerValue;

  /**
   * U1030210:アドレス帳一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AddressListDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String addressNo;

    private String addressAlias;

    private String addressLastName;

    private String addressFirstName;

    private String postalCode;

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    //private String deliveryDatetime;

    private boolean deleteDisplayFlg;

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
     * deliveryDatetimeを取得します。
     * 
     * @return deliveryDatetime
     */
//    public String getDeliveryDatetime() {
//      return deliveryDatetime;
//    }

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
     * deliveryDatetimeを設定します。
     * 
     * @param deliveryDatetime
     *          deliveryDatetime
     */
//    public void setDeliveryDatetime(String deliveryDatetime) {
//      this.deliveryDatetime = deliveryDatetime;
//    }

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
     * deleteDisplayFlgを取得します。
     * 
     * @return deleteDisplayFlg
     */
    public boolean getDeleteDisplayFlg() {
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

  }

  /**
   * editDisplayFlgを取得します。
   * 
   * @return editDisplayFlg
   */
  public boolean isEditDisplayFlg() {
    return editDisplayFlg;
  }

  /**
   * insertDisplayFlgを取得します。
   * 
   * @return insertDisplayFlg
   */
  public boolean isInsertDisplayFlg() {
    return insertDisplayFlg;
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
   * insertDisplayFlgを設定します。
   * 
   * @param insertDisplayFlg
   *          insertDisplayFlg
   */
  public void setInsertDisplayFlg(boolean insertDisplayFlg) {
    this.insertDisplayFlg = insertDisplayFlg;
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
   * listを取得します。
   * 
   * @return list
   */
  public List<AddressListDetailBean> getList() {
    return list;
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
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<AddressListDetailBean> list) {
    this.list = list;
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
    return "U1030210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.AddressListBean.0");
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
}
