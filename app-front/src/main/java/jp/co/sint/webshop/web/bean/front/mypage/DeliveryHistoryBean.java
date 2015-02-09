package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030630:配送履歴のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DeliveryHistoryBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue;

  private List<DeliveryHistoryDetail> list = new ArrayList<DeliveryHistoryDetail>();

  private String addressLastName;

  private String addressFirstName;

  private String postalCode;

  private String address1;

  private String address2;

  private String address3;

  private String address4;

  @Length(8)
  @Digit
  @Metadata(name = "アドレス帳番号")
  private String addressNo;

  private String customerCode;

  /**
   * U2030630:配送履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DeliveryHistoryDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shippingDate;

    private String orderNo;

    private String shippingNo;

    private String totalPrice;

    private String orderDatetime;

    private List<DeliveryHistoryCommodityDetail> commodityList = new ArrayList<DeliveryHistoryCommodityDetail>();

    /**
     * commodityListを取得します。
     * 
     * @return the commodityList
     */
    public List<DeliveryHistoryCommodityDetail> getCommodityList() {
      return commodityList;
    }

    /**
     * commodityListを設定します。
     * 
     * @param commodityList
     *          commodityList
     */
    public void setCommodityList(List<DeliveryHistoryCommodityDetail> commodityList) {
      this.commodityList = commodityList;
    }

    /**
     * orderDatetimeを取得します。
     * 
     * @return orderDatetime
     */
    public String getOrderDatetime() {
      return orderDatetime;
    }

    /**
     * orderNoを取得します。
     * 
     * @return orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    /**
     * shippingDateを取得します。
     * 
     * @return shippingDate
     */
    public String getShippingDate() {
      return shippingDate;
    }

    /**
     * shippingNoを取得します。
     * 
     * @return shippingNo
     */
    public String getShippingNo() {
      return shippingNo;
    }

    /**
     * orderDatetimeを設定します。
     * 
     * @param orderDatetime
     *          orderDatetime
     */
    public void setOrderDatetime(String orderDatetime) {
      this.orderDatetime = orderDatetime;
    }

    /**
     * orderNoを設定します。
     * 
     * @param orderNo
     *          orderNo
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    /**
     * shippingDateを設定します。
     * 
     * @param shippingDate
     *          shippingDate
     */
    public void setShippingDate(String shippingDate) {
      this.shippingDate = shippingDate;
    }

    /**
     * shippingNoを設定します。
     * 
     * @param shippingNo
     *          shippingNo
     */
    public void setShippingNo(String shippingNo) {
      this.shippingNo = shippingNo;
    }

    /**
     * totalPriceを取得します。
     * 
     * @return totalPrice
     */
    public String getTotalPrice() {
      return totalPrice;
    }

    /**
     * totalPriceを設定します。
     * 
     * @param totalPrice
     *          totalPrice
     */
    public void setTotalPrice(String totalPrice) {
      this.totalPrice = totalPrice;
    }
  }

  /**
   * U2030630:配送履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DeliveryHistoryCommodityDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String commodityName;

    private String standardDetail1Name;

    private String standardDetail2Name;

    /**
     * commodityNameを取得します。
     * 
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * standardDetail1Nameを取得します。
     * 
     * @return the standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * standardDetail2Nameを取得します。
     * 
     * @return the standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * standardDetail1Nameを設定します。
     * 
     * @param standardDetail1Name
     *          standardDetail1Name
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * standardDetail2Nameを設定します。
     * 
     * @param standardDetail2Name
     *          standardDetail2Name
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }
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
   * 配送履歴を取得します。
   * 
   * @return list
   */
  public List<DeliveryHistoryDetail> getList() {
    return list;
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
   * 配送履歴を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<DeliveryHistoryDetail> list) {
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
    return "U2030630";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.DeliveryHistoryBean.0");
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
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString(
        "web.bean.front.mypage.DeliveryHistoryBean.1"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(Messages.getString(
        "web.bean.front.mypage.DeliveryHistoryBean.2"), "/app/mypage/address_list/init"));
    topicPath.add(new NameValue(Messages.getString(
        "web.bean.front.mypage.DeliveryHistoryBean.0"),
        "/app/mypage/delivery_history/init/" + addressNo));
    return topicPath;
  }

}
