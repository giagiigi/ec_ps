package jp.co.sint.webshop.web.bean.front.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020210:ゲスト情報入力のデータモデルです。
 * 
 * @author Kousen.
 */
public class AddressBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String customerCode;

  private String customerName;

  private CustomerAddressBean address = new CustomerAddressBean();

  private String addressScript;

  private List<CartItem> cartItem;

  private BigDecimal totalCommodityPrice;

  private BigDecimal totalCommodityWeight;

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
    reqparam.copy(address);
    address.setAddressLastName(StringUtil.parse(reqparam.get("addressLastName")));
    address.setAddressAddress4(StringUtil.parse(reqparam.get("addressAddress4")));
    address.setPhoneNumber1(reqparam.get("addressPhoneNumber_1"));
    address.setPhoneNumber2(reqparam.get("addressPhoneNumber_2"));
    address.setPhoneNumber3(reqparam.get("addressPhoneNumber_3"));
  }

  /**
   * U2020210:ゲスト情報入力のサブモデルです。
   * 
   * @author Kousen.
   */
  public static class CustomerAddressBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(20)
    @Metadata(name = "收货人姓名", order = 1)
    private String addressLastName;

    @Required
    @Length(6)
    @PostalCode
    @Metadata(name = "邮政编码", order = 2)
    private String addressPostalCode;

    @Required
    @Length(2)
    @Metadata(name = "省份", order = 3)
    private String addressPrefectureCode;

    @Required
    @Length(3)
    @Metadata(name = "城市", order = 4)
    private String addressCityCode;

    @Length(4)
    @Metadata(name = "区县", order = 5)
    private String addressAreaCode;

    @Required
    @Length(100)
    @Metadata(name = "街道地址", order = 6)
    private String addressAddress4;

    @Length(4)
    @Metadata(name = "固定电话1", order = 7)
    private String phoneNumber1;

    @Length(8)
    @Metadata(name = "固定电话2", order = 8)
    private String phoneNumber2;

    @Length(6)
    @Metadata(name = "固定电话3", order = 9)
    private String phoneNumber3;

    @Length(20)
    @Metadata(name = "固定电话", order = 7)
    private String phoneNumber;
    
    @Length(11)
    @MobileNumber
    @Metadata(name = "手机号码", order = 10)
    private String mobileNumber;

    private List<CodeAttribute> addressPrefectureList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> addressCityList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> addressAreaList = new ArrayList<CodeAttribute>();

    /**
     * @return the addressLastName
     */
    public String getAddressLastName() {
      return addressLastName;
    }

    /**
     * @param addressLastName
     *          the addressLastName to set
     */
    public void setAddressLastName(String addressLastName) {
      this.addressLastName = addressLastName;
    }

    /**
     * @return the addressPostalCode
     */
    public String getAddressPostalCode() {
      return addressPostalCode;
    }

    /**
     * @param addressPostalCode
     *          the addressPostalCode to set
     */
    public void setAddressPostalCode(String addressPostalCode) {
      this.addressPostalCode = addressPostalCode;
    }

    /**
     * @return the addressPrefectureCode
     */
    public String getAddressPrefectureCode() {
      return addressPrefectureCode;
    }

    /**
     * @param addressPrefectureCode
     *          the addressPrefectureCode to set
     */
    public void setAddressPrefectureCode(String addressPrefectureCode) {
      this.addressPrefectureCode = addressPrefectureCode;
    }

    /**
     * @return the addressCityCode
     */
    public String getAddressCityCode() {
      return addressCityCode;
    }

    /**
     * @param addressCityCode
     *          the addressCityCode to set
     */
    public void setAddressCityCode(String addressCityCode) {
      this.addressCityCode = addressCityCode;
    }

    /**
     * @return the addressAreaCode
     */
    public String getAddressAreaCode() {
      return addressAreaCode;
    }

    /**
     * @param addressAreaCode
     *          the addressAreaCode to set
     */
    public void setAddressAreaCode(String addressAreaCode) {
      this.addressAreaCode = addressAreaCode;
    }

    /**
     * @return the addressAddress4
     */
    public String getAddressAddress4() {
      return addressAddress4;
    }

    /**
     * @param addressAddress4
     *          the addressAddress4 to set
     */
    public void setAddressAddress4(String addressAddress4) {
      this.addressAddress4 = addressAddress4;
    }

    /**
     * @return the phoneNumber1
     */
    public String getPhoneNumber1() {
      return phoneNumber1;
    }

    /**
     * @param phoneNumber1
     *          the phoneNumber1 to set
     */
    public void setPhoneNumber1(String phoneNumber1) {
      this.phoneNumber1 = phoneNumber1;
    }

    /**
     * @return the phoneNumber2
     */
    public String getPhoneNumber2() {
      return phoneNumber2;
    }

    /**
     * @param phoneNumber2
     *          the phoneNumber2 to set
     */
    public void setPhoneNumber2(String phoneNumber2) {
      this.phoneNumber2 = phoneNumber2;
    }

    /**
     * @return the phoneNumber3
     */
    public String getPhoneNumber3() {
      return phoneNumber3;
    }

    /**
     * @param phoneNumber3
     *          the phoneNumber3 to set
     */
    public void setPhoneNumber3(String phoneNumber3) {
      this.phoneNumber3 = phoneNumber3;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
      return mobileNumber;
    }

    /**
     * @param mobileNumber
     *          the mobileNumber to set
     */
    public void setMobileNumber(String mobileNumber) {
      this.mobileNumber = mobileNumber;
    }

    /**
     * @return the addressCityList
     */
    public List<CodeAttribute> getAddressCityList() {
      return addressCityList;
    }

    /**
     * @param addressCityList
     *          the addressCityList to set
     */
    public void setAddressCityList(List<CodeAttribute> addressCityList) {
      this.addressCityList = addressCityList;
    }

    /**
     * @return the addressPrefectureList
     */
    public List<CodeAttribute> getAddressPrefectureList() {
      return addressPrefectureList;
    }

    /**
     * @param addressPrefectureList
     *          the addressPrefectureList to set
     */
    public void setAddressPrefectureList(List<CodeAttribute> addressPrefectureList) {
      this.addressPrefectureList = addressPrefectureList;
    }

    /**
     * @return the addressAreaList
     */
    public List<CodeAttribute> getAddressAreaList() {
      return addressAreaList;
    }

    /**
     * @param addressAreaList
     *          the addressAreaList to set
     */
    public void setAddressAreaList(List<CodeAttribute> addressAreaList) {
      this.addressAreaList = addressAreaList;
    }

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

  }

  public CustomerAddressBean getAddress() {
    return address;
  }

  public void setAddress(CustomerAddressBean address) {
    this.address = address;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
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
    return Messages.getString("web.bean.front.order.Address.0");

  }

}
