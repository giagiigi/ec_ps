package jp.co.sint.webshop.web.bean.front.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020210:ゲスト情報入力のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class GuestBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private GuestOwnerBean guestOwner = new GuestOwnerBean();

  private String selectShopCode;

  private Sku selectReserveSkuSet;

  private Cashier cashier;

  // 20120112 shen add start
  private List<CartItem> cartItem;

  private BigDecimal totalCommodityPrice;

  private BigDecimal totalCommodityWeight;

  // 20120112 shen add end

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
    reqparam.copy(guestOwner);

    // 電話番号は手動コピー
    guestOwner.setPhoneNumber1(reqparam.get("ownerPhoneNumber_1"));
    guestOwner.setPhoneNumber2(reqparam.get("ownerPhoneNumber_2"));
    guestOwner.setPhoneNumber3(reqparam.get("ownerPhoneNumber_3"));
    guestOwner.setMobileNumber(reqparam.get("ownerMobileNumber"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2020210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.order.GuestBean.0");
  }

  /**
   * U2020210:ゲスト情報入力のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class GuestOwnerBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(20)
    @Metadata(name = "收货人姓名", order = 1)
    private String ownerLastName;

    @Length(20)
    @Metadata(name = "氏名(名)", order = 2)
    private String ownerFirstName;

    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(姓)", order = 3)
    private String ownerLastNameKana;

    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(名)", order = 4)
    private String ownerFirstNameKana;

    @Required
    @Length(6)
    @PostalCode
    @Metadata(name = "邮政编码", order = 5)
    private String ownerPostalCode;

    @Required
    @Length(2)
    @AlphaNum2
    @Metadata(name = "省份", order = 6)
    private String ownerPrefecture;

    /** 市コード */
    @Required
    @Length(3)
    @Metadata(name = "城市", order = 7)
    private String ownerCityCode;

    // 20120109 shen add start
    @Length(4)
    @Metadata(name = "区县", order = 8)
    private String ownerAreaCode;

    // 20120109 shen add end

    @Required
    @Length(50)
    @Metadata(name = "街道地址", order = 9)
    private String ownerAddress4;

    @Length(4)
    @Metadata(name = "固定电话1", order = 10)
    private String phoneNumber1;

    @Length(8)
    @Metadata(name = "固定电话2", order = 11)
    private String phoneNumber2;

    @Length(6)
    @Metadata(name = "固定电话3", order = 12)
    private String phoneNumber3;

    @Length(11)
    @MobileNumber
    @Metadata(name = "手机号码", order = 13)
    private String mobileNumber;

    @Required
    @Length(256)
    @Email
    @Metadata(name = "邮件地址", order = 14)
    private String ownerEmail;

    private List<CodeAttribute> ownerCityList = new ArrayList<CodeAttribute>();

    // 20120109 shen add start
    private List<CodeAttribute> ownerPrefectureList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> ownerAreaList = new ArrayList<CodeAttribute>();

    private String addressScript;

    // 20120109 shen add end

    /**
     * ownerEmailを取得します。
     * 
     * @return ownerEmail
     */
    public String getOwnerEmail() {
      return ownerEmail;
    }

    /**
     * ownerFirstNameを取得します。
     * 
     * @return ownerFirstName
     */
    public String getOwnerFirstName() {
      return ownerFirstName;
    }

    /**
     * ownerFirstNameKanaを取得します。
     * 
     * @return ownerFirstNameKana
     */
    public String getOwnerFirstNameKana() {
      return ownerFirstNameKana;
    }

    /**
     * ownerLastNameKanaを取得します。
     * 
     * @return ownerLastNameKana
     */
    public String getOwnerLastNameKana() {
      return ownerLastNameKana;
    }

    /**
     * ownerEmailを設定します。
     * 
     * @param ownerEmail
     *          ownerEmail
     */
    public void setOwnerEmail(String ownerEmail) {
      this.ownerEmail = ownerEmail;
    }

    /**
     * ownerFirstNameを設定します。
     * 
     * @param ownerFirstName
     *          ownerFirstName
     */
    public void setOwnerFirstName(String ownerFirstName) {
      this.ownerFirstName = ownerFirstName;
    }

    /**
     * ownerFirstNameKanaを設定します。
     * 
     * @param ownerFirstNameKana
     *          ownerFirstNameKana
     */
    public void setOwnerFirstNameKana(String ownerFirstNameKana) {
      this.ownerFirstNameKana = ownerFirstNameKana;
    }

    /**
     * ownerLastNameKanaを設定します。
     * 
     * @param ownerLastNameKana
     *          ownerLastNameKana
     */
    public void setOwnerLastNameKana(String ownerLastNameKana) {
      this.ownerLastNameKana = ownerLastNameKana;
    }

    /**
     * ownerLastNameを取得します。
     * 
     * @return ownerLastName
     */
    public String getOwnerLastName() {
      return ownerLastName;
    }

    /**
     * ownerLastNameを設定します。
     * 
     * @param ownerLastName
     *          ownerLastName
     */
    public void setOwnerLastName(String ownerLastName) {
      this.ownerLastName = ownerLastName;
    }

    /**
     * ownerPrefectureを取得します。
     * 
     * @return ownerPrefecture
     */
    public String getOwnerPrefecture() {
      return ownerPrefecture;
    }

    /**
     * ownerPrefectureを設定します。
     * 
     * @param ownerPrefecture
     *          ownerPrefecture
     */
    public void setOwnerPrefecture(String ownerPrefecture) {
      this.ownerPrefecture = ownerPrefecture;
    }

    /**
     * ownerPostalCodeを取得します。
     * 
     * @return ownerPostalCode
     */
    public String getOwnerPostalCode() {
      return ownerPostalCode;
    }

    /**
     * ownerPostalCodeを設定します。
     * 
     * @param ownerPostalCode
     *          ownerPostalCode
     */
    public void setOwnerPostalCode(String ownerPostalCode) {
      this.ownerPostalCode = ownerPostalCode;
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
     * phoneNumber1を設定します。
     * 
     * @param phoneNumber1
     *          phoneNumber1
     */
    public void setPhoneNumber1(String phoneNumber1) {
      this.phoneNumber1 = phoneNumber1;
    }

    public String getOwnerCityCode() {
      return ownerCityCode;
    }

    public void setOwnerCityCode(String ownerCityCode) {
      this.ownerCityCode = ownerCityCode;
    }

    public List<CodeAttribute> getOwnerCityList() {
      return ownerCityList;
    }

    public void setOwnerCityList(List<CodeAttribute> ownerCityList) {
      this.ownerCityList = ownerCityList;
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
     * @return phoneNumber3
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
     * @return the ownerAreaCode
     */
    public String getOwnerAreaCode() {
      return ownerAreaCode;
    }

    /**
     * @param ownerAreaCode
     *          the ownerAreaCode to set
     */
    public void setOwnerAreaCode(String ownerAreaCode) {
      this.ownerAreaCode = ownerAreaCode;
    }

    /**
     * @return the ownerAddress4
     */
    public String getOwnerAddress4() {
      return ownerAddress4;
    }

    /**
     * @param ownerAddress4
     *          the ownerAddress4 to set
     */
    public void setOwnerAddress4(String ownerAddress4) {
      this.ownerAddress4 = ownerAddress4;
    }

    /**
     * @return the ownerPrefectureList
     */
    public List<CodeAttribute> getOwnerPrefectureList() {
      return ownerPrefectureList;
    }

    /**
     * @param ownerPrefectureList
     *          the ownerPrefectureList to set
     */
    public void setOwnerPrefectureList(List<CodeAttribute> ownerPrefectureList) {
      this.ownerPrefectureList = ownerPrefectureList;
    }

    /**
     * @return the ownerAreaList
     */
    public List<CodeAttribute> getOwnerAreaList() {
      return ownerAreaList;
    }

    /**
     * @param ownerAreaList
     *          the ownerAreaList to set
     */
    public void setOwnerAreaList(List<CodeAttribute> ownerAreaList) {
      this.ownerAreaList = ownerAreaList;
    }

    /**
     * @return the addressScript
     */
    public String getAddressScript() {
      return addressScript;
    }

    /**
     * @param addressScript
     *          the addressScript to set
     */
    public void setAddressScript(String addressScript) {
      this.addressScript = addressScript;
    }

  }

  /**
   * guestOwnerを取得します。
   * 
   * @return guestOwner
   */
  public GuestOwnerBean getGuestOwner() {
    return guestOwner;
  }

  /**
   * guestOwnerを設定します。
   * 
   * @param guestOwner
   *          guestOwner
   */
  public void setGuestOwner(GuestOwnerBean guestOwner) {
    this.guestOwner = guestOwner;
  }

  /**
   * selectShopCodeを取得します。
   * 
   * @return the selectShopCode
   */
  public String getSelectShopCode() {
    return selectShopCode;
  }

  /**
   * selectShopCodeを設定します。
   * 
   * @param selectShopCode
   *          selectShopCode
   */
  public void setSelectShopCode(String selectShopCode) {
    this.selectShopCode = selectShopCode;
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
   * cashierを設定します。
   * 
   * @param cashier
   *          cashier
   */
  public void setCashier(Cashier cashier) {
    this.cashier = cashier;
  }

  /**
   * selectReserveSkuSetを取得します。
   * 
   * @return selectReserveSkuSet
   */
  public Sku getSelectReserveSkuSet() {
    return selectReserveSkuSet;
  }

  /**
   * selectReserveSkuSetを設定します。
   * 
   * @param selectReserveSkuSet
   *          selectReserveSkuSet
   */
  public void setSelectReserveSkuSet(Sku selectReserveSkuSet) {
    this.selectReserveSkuSet = selectReserveSkuSet;
  }

  /**
   * @return the cartItem
   */
  public List<CartItem> getCartItem() {
    return cartItem;
  }

  /**
   * @param cartItem
   *          the cartItem to set
   */
  public void setCartItem(List<CartItem> cartItem) {
    this.cartItem = cartItem;
  }

  /**
   * @return the totalCommodityPrice
   */
  public BigDecimal getTotalCommodityPrice() {
    return totalCommodityPrice;
  }

  /**
   * @param totalCommodityPrice
   *          the totalCommodityPrice to set
   */
  public void setTotalCommodityPrice(BigDecimal totalCommodityPrice) {
    this.totalCommodityPrice = totalCommodityPrice;
  }

  /**
   * @return the totalCommodityWeight
   */
  public BigDecimal getTotalCommodityWeight() {
    return totalCommodityWeight;
  }

  /**
   * @param totalCommodityWeight
   *          the totalCommodityWeight to set
   */
  public void setTotalCommodityWeight(BigDecimal totalCommodityWeight) {
    this.totalCommodityWeight = totalCommodityWeight;
  }

}
