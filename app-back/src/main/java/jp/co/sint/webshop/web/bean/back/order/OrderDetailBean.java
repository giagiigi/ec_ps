package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.BankCode;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020220:受注管理明細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PaymentBean paymentEdit = new PaymentBean();

  private OrderHeaderBean orderHeaderEdit = new OrderHeaderBean();

  private List<ShippingHeaderBean> shippingList = new ArrayList<ShippingHeaderBean>();

  // add by lc 2012-03-22 start
  private List<ShippingHeaderBean> returnShippingList = new ArrayList<ShippingHeaderBean>();

  // add by lc 2012-03-22 end

  private String orderNo;

  private String orderEditMessage;

  private String orderStatus;

  private String orderStatusName;

  private String hasReturn;

  private String orderDataTransportFlg;

  private String totalCommodityPrice;

  private String totalGiftPrice;

  private String totalShippingCharge;

  private String paymentCommission;

  private String usedPoint;

  private String totalWeight;
  
  // add by V10-CH start
  private String usedCouponPrice;

  // add by V10-CH end

  private String allPaymentPrice;

  private String totalOrderPrice;

  private String orderDatetime;

  private String messagesEditMode = WebConstantCode.DISPLAY_EDIT;

  private boolean cancelButtonFlg = false;

  private boolean updateButtonFlg = false;

  private boolean modifyButtonFlg = false;

  private boolean returnButtonFlg = false;

  private boolean paymentDateClearButtonFlg = false;

  private boolean paymentDateSetButtonFlg = false;

  private boolean orderAndPaymentPartDisplayFlg = false;

  private String orderInformationDisplayMode = WebConstantCode.DISPLAY_READONLY;

  private String shippingInformationDisplayMode = WebConstantCode.DISPLAY_READONLY;

  private boolean isNotPointInFull;

  // soukai add 2011/12/27 ob start
  private List<CodeAttribute> invoiceCommodityNameList = new ArrayList<CodeAttribute>();

  /** 发票信息 */
  private InvoiceBean orderInvoice = new InvoiceBean();

  // 受注タイプ
  private String orderType;

  private String orderFlg;

  private String orderFlgDisplayMode = WebConstantCode.DISPLAY_HIDDEN;

  private String tmallDiscountPrice;

  private String jdDiscountPrice;
  
  private String pointConvertPrice;

  private String addressScript;

  private List<CodeAttribute> addressPrefectureList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> addressCityList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> addressAreaList = new ArrayList<CodeAttribute>();

  // 实收金额
  private BigDecimal paidPrice;

  private List<CodeAttribute> deliveryAppointedTimeList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> deliveryAppointedDateList = new ArrayList<CodeAttribute>();

  private boolean tmallOrderFlg = false;

  private boolean jdOrderFlg = false;
  // soukai add 2011/12/27 ob end

  // 淘宝订单交易号
  private String tmallTid;
  
  private String jdOrderNo;

  // 淘宝满就送优惠金额
  private String mjsDiscount;
  
  private boolean authority = true;
  
  // 20131114 txw add start
  private boolean orderEditButtonFlg = true;
  
  private String giftCardUsePrice;
  
  private String outerCardUsePrice;
  // 20131114 txw add end

  /**
   * U1020220:受注管理明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PaymentBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String paymentMethodCode;

    private String paymentMethodName;

    private String advanceLaterType;

    private String paymentCommission;

    private String paymentLimitDate;

    @Datetime
    @Metadata(name = "入金日付")
    private String paymentDate;

    private String clearPaymentDate;

    private String tradeID;

    private String cvsReceiptNo;

    private String cvsReceiptUrl;

    private String cvsName;

    private Date updateDatetime;

    /**
     * updateDatetimeを取得します。
     * 
     * @return updateDatetime updateDatetime
     */
    public Date getUpdateDatetime() {
      return DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * updateDatetimeを設定します。
     * 
     * @param updateDatetime
     *          updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * advanceLaterTypeを取得します。
     * 
     * @return advanceLaterType
     */
    public String getAdvanceLaterType() {
      return advanceLaterType;
    }

    /**
     * clearPaymentDateを取得します。
     * 
     * @return clearPaymentDate
     */
    public String getClearPaymentDate() {
      return clearPaymentDate;
    }

    /**
     * cvsReceiptNoを取得します。
     * 
     * @return cvsReceiptNo
     */
    public String getCvsReceiptNo() {
      return cvsReceiptNo;
    }

    /**
     * cvsReceiptUrlを取得します。
     * 
     * @return cvsReceiptUrl
     */
    public String getCvsReceiptUrl() {
      return cvsReceiptUrl;
    }

    /**
     * paymentCommissionを取得します。
     * 
     * @return paymentCommission
     */
    public String getPaymentCommission() {
      return paymentCommission;
    }

    /**
     * paymentDateを取得します。
     * 
     * @return paymentDate
     */
    public String getPaymentDate() {
      return paymentDate;
    }

    /**
     * paymentLimitDateを取得します。
     * 
     * @return paymentLimitDate
     */
    public String getPaymentLimitDate() {
      return paymentLimitDate;
    }

    /**
     * paymentMethodCodeを取得します。
     * 
     * @return paymentMethodCode
     */
    public String getPaymentMethodCode() {
      return paymentMethodCode;
    }

    /**
     * paymentMethodNameを取得します。
     * 
     * @return paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
    }

    /**
     * advanceLaterTypeを設定します。
     * 
     * @param advanceLaterType
     *          advanceLaterType
     */
    public void setAdvanceLaterType(String advanceLaterType) {
      this.advanceLaterType = advanceLaterType;
    }

    /**
     * clearPaymentDateを設定します。
     * 
     * @param clearPaymentDate
     *          clearPaymentDate
     */
    public void setClearPaymentDate(String clearPaymentDate) {
      this.clearPaymentDate = clearPaymentDate;
    }

    /**
     * cvsReceiptNoを設定します。
     * 
     * @param cvsReceiptNo
     *          cvsReceiptNo
     */
    public void setCvsReceiptNo(String cvsReceiptNo) {
      this.cvsReceiptNo = cvsReceiptNo;
    }

    /**
     * cvsReceiptUrlを設定します。
     * 
     * @param cvsReceiptUrl
     *          cvsReceiptUrl
     */
    public void setCvsReceiptUrl(String cvsReceiptUrl) {
      this.cvsReceiptUrl = cvsReceiptUrl;
    }

    /**
     * paymentCommissionを設定します。
     * 
     * @param paymentCommission
     *          paymentCommission
     */
    public void setPaymentCommission(String paymentCommission) {
      this.paymentCommission = paymentCommission;
    }

    /**
     * paymentDateを設定します。
     * 
     * @param paymentDate
     *          paymentDate
     */
    public void setPaymentDate(String paymentDate) {
      this.paymentDate = paymentDate;
    }

    /**
     * paymentLimitDateを設定します。
     * 
     * @param paymentLimitDate
     *          paymentLimitDate
     */
    public void setPaymentLimitDate(String paymentLimitDate) {
      this.paymentLimitDate = paymentLimitDate;
    }

    /**
     * paymentMethodCodeを設定します。
     * 
     * @param paymentMethodCode
     *          paymentMethodCode
     */
    public void setPaymentMethodCode(String paymentMethodCode) {
      this.paymentMethodCode = paymentMethodCode;
    }

    /**
     * paymentMethodNameを設定します。
     * 
     * @param paymentMethodName
     *          paymentMethodName
     */
    public void setPaymentMethodName(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
    }

    /**
     * cvsNameを取得します。
     * 
     * @return cvsName
     */
    public String getCvsName() {
      return cvsName;
    }

    /**
     * cvsNameを設定します。
     * 
     * @param cvsName
     *          cvsName
     */
    public void setCvsName(String cvsName) {
      this.cvsName = cvsName;
    }

    /**
     * tradeIDを取得します。
     * 
     * @return tradeID
     */
    public String getTradeID() {
      return tradeID;
    }

    /**
     * tradeIDを設定します。
     * 
     * @param tradeID
     *          tradeID
     */
    public void setTradeID(String tradeID) {
      this.tradeID = tradeID;
    }

  }

  /**
   * U1020220:受注管理明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderHeaderBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String customerCode;

    private String guestFlg;

    @Required
    @Length(20)
    @Metadata(name = "氏名(姓)")
    private String customerLastName;

    @Required
    @Length(20)
    @Metadata(name = "氏名(名)")
    private String customerFirstName;

    @Required
    @Kana
    @Length(40)
    @Metadata(name = "氏名カナ(姓)")
    private String customerLastNameKana;

    @Required
    @Kana
    @Length(40)
    @Metadata(name = "氏名カナ(名)")
    private String customerFirstNameKana;

    @Required
    @Email
    @Length(256)
    @Metadata(name = "メールアドレス")
    private String customerEmail;

    @Required
    @Length(7)
    @PostalCode
    @Metadata(name = "郵便番号")
    private String postalCode;

    @Length(2)
    @Metadata(name = "都道府県")
    private String prefectureCode;

    @Length(50)
    @Metadata(name = "都道府県")
    private String address1;

    /** 市コード */
    @Length(10)
    @Metadata(name = "市コード")
    private String cityCode;

    // @Required
    @Length(50)
    @Metadata(name = "市区町村")
    private String address2;

    @Length(50)
    @Metadata(name = "町名・番地")
    private String address3;

    // soukai add 2012/01/08 ob start
    @Length(10)
    @Metadata(name = "区县")
    private String areaCode;

    // soukai add 2012/01/08 ob end

    @Required
    @Length(100)
    @Metadata(name = "アパート・マンション・ビル")
    private String address4;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号1")
    private String customerTel1;

    // Add by V10-CH 170 start
    @Length(10)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号2")
    private String customerTel2;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号3")
    private String customerTel3;

    // Add by V10-CH 170 end

    @Length(24)
    // delete by V10-CH 170 start
    @Phone
    // delete by V10-CH 170 end
    @Metadata(name = "電話番号")
    private String customerTel;

    // Add by V10-CH start
    @Length(11)
    @MobileNumber
    @Metadata(name = "手机号码")
    private String customerMobile;

    // Add by V10-CH end

    @Length(200)
    @Metadata(name = "連絡事項")
    private String message;

    @Length(400)
    @Metadata(name = "注意事項（管理側のみ参照）")
    private String caution;

    private String recentEmail;

    @Phone
    private String recentPhoneNumber;

    // Add by V10-CH start
    @MobileNumber
    private String recentMobileNumber;

    // Add by V10-CH end

    private Date updateDatetime;

    // modify by V10-CH 170 start
    private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();

    // modify by V10-CH 170 end

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
     * updateDatetimeを取得します。
     * 
     * @return updateDatetime
     */
    public Date getUpdateDatetime() {
      return DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * updateDatetimeを設定します。
     * 
     * @param updateDatetime
     *          updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
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
     * cautionを取得します。
     * 
     * @return caution
     */
    public String getCaution() {
      return caution;
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
     * customerEmailを取得します。
     * 
     * @return customerEmail
     */
    public String getCustomerEmail() {
      return customerEmail;
    }

    /**
     * customerFirstNameを取得します。
     * 
     * @return customerFirstName
     */
    public String getCustomerFirstName() {
      return customerFirstName;
    }

    /**
     * customerFirstNameKanaを取得します。
     * 
     * @return customerFirstNameKana
     */
    public String getCustomerFirstNameKana() {
      return customerFirstNameKana;
    }

    /**
     * customerLastNameを取得します。
     * 
     * @return customerLastName
     */
    public String getCustomerLastName() {
      return customerLastName;
    }

    /**
     * customerLastNameKanaを取得します。
     * 
     * @return customerLastNameKana
     */
    public String getCustomerLastNameKana() {
      return customerLastNameKana;
    }

    /**
     * messageを取得します。
     * 
     * @return message
     */
    public String getMessage() {
      return message;
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
     * cautionを設定します。
     * 
     * @param caution
     *          caution
     */
    public void setCaution(String caution) {
      this.caution = caution;
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
     * customerEmailを設定します。
     * 
     * @param customerEmail
     *          customerEmail
     */
    public void setCustomerEmail(String customerEmail) {
      this.customerEmail = customerEmail;
    }

    /**
     * customerFirstNameを設定します。
     * 
     * @param customerFirstName
     *          customerFirstName
     */
    public void setCustomerFirstName(String customerFirstName) {
      this.customerFirstName = customerFirstName;
    }

    /**
     * customerFirstNameKanaを設定します。
     * 
     * @param customerFirstNameKana
     *          customerFirstNameKana
     */
    public void setCustomerFirstNameKana(String customerFirstNameKana) {
      this.customerFirstNameKana = customerFirstNameKana;
    }

    /**
     * customerLastNameを設定します。
     * 
     * @param customerLastName
     *          customerLastName
     */
    public void setCustomerLastName(String customerLastName) {
      this.customerLastName = customerLastName;
    }

    /**
     * customerLastNameKanaを設定します。
     * 
     * @param customerLastNameKana
     *          customerLastNameKana
     */
    public void setCustomerLastNameKana(String customerLastNameKana) {
      this.customerLastNameKana = customerLastNameKana;
    }

    /**
     * messageを設定します。
     * 
     * @param message
     *          message
     */
    public void setMessage(String message) {
      this.message = message;
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
     * customerTel1を取得します。
     * 
     * @return customerTel1
     */
    public String getCustomerTel1() {
      return customerTel1;
    }

    /**
     * customerTel1を設定します。
     * 
     * @param customerTel1
     *          customerTel1
     */
    public void setCustomerTel1(String customerTel1) {
      this.customerTel1 = customerTel1;
    }

    /**
     * customerTel2を取得します。
     * 
     * @return customerTel2
     */
    public String getCustomerTel2() {
      return customerTel2;
    }

    /**
     * customerTel2を設定します。
     * 
     * @param customerTel2
     *          customerTel2
     */
    public void setCustomerTel2(String customerTel2) {
      this.customerTel2 = customerTel2;
    }

    /**
     * customerTel3を取得します。
     * 
     * @return customerTel3
     */
    public String getCustomerTel3() {
      return customerTel3;
    }

    /**
     * customerTel3を設定します。
     * 
     * @param customerTel3
     *          customerTel3
     */
    public void setCustomerTel3(String customerTel3) {
      this.customerTel3 = customerTel3;
    }

    /**
     * @return recentEmail
     */
    public String getRecentEmail() {
      return recentEmail;
    }

    /**
     * @param recentEmail
     *          設定する recentEmail
     */
    public void setRecentEmail(String recentEmail) {
      this.recentEmail = recentEmail;
    }

    /**
     * @return recentPhoneNumber
     */
    public String getRecentPhoneNumber() {
      return recentPhoneNumber;
    }

    /**
     * @param recentPhoneNumber
     *          設定する recentPhoneNumber
     */
    public void setRecentPhoneNumber(String recentPhoneNumber) {
      this.recentPhoneNumber = recentPhoneNumber;
    }

    /**
     * guestFlgを取得します。
     * 
     * @return guestFlg
     */
    public String getGuestFlg() {
      return guestFlg;
    }

    /**
     * guestFlgを設定します。
     * 
     * @param guestFlg
     *          guestFlg
     */
    public void setGuestFlg(String guestFlg) {
      this.guestFlg = guestFlg;
    }

    /**
     * customerMobileを取得します。
     * 
     * @return customerMobile customerMobile
     */
    public String getCustomerMobile() {
      return customerMobile;
    }

    /**
     * customerMobileを設定します。
     * 
     * @param customerMobile
     *          customerMobile
     */
    public void setCustomerMobile(String customerMobile) {
      this.customerMobile = customerMobile;
    }

    /**
     * recentMobileNumberを取得します。
     * 
     * @return recentMobileNumber recentMobileNumber
     */
    public String getRecentMobileNumber() {
      return recentMobileNumber;
    }

    /**
     * recentMobileNumberを設定します。
     * 
     * @param recentMobileNumber
     *          recentMobileNumber
     */
    public void setRecentMobileNumber(String recentMobileNumber) {
      this.recentMobileNumber = recentMobileNumber;
    }

    /**
     * customerTelを取得します。
     * 
     * @return customerTel customerTel
     */
    public String getCustomerTel() {
      return customerTel;
    }

    /**
     * customerTelを設定します。
     * 
     * @param customerTel
     *          customerTel
     */
    public void setCustomerTel(String customerTel) {
      this.customerTel = customerTel;
    }

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
      return areaCode;
    }

    /**
     * @param areaCode
     *          the areaCode to set
     */
    public void setAreaCode(String areaCode) {
      this.areaCode = areaCode;
    }
  }

  /**
   * U1020220:受注管理明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShippingHeaderBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();

    private String shippingNo;

    // add by lc 2012-03-15 start
    private String returnItemType;

    // add by lc 2012-03-15 end
    @Required
    @Length(20)
    @Metadata(name = "宛名(姓)")
    private String shippingLastName;

    @Required
    @Length(20)
    @Metadata(name = "宛名(名)")
    private String shippingFirstName;

    @Required
    @Kana
    @Length(40)
    @Metadata(name = "宛名カナ(姓)")
    private String shippingLastNameKana;

    @Required
    @Kana
    @Length(40)
    @Metadata(name = "宛名カナ(名)")
    private String shippingFirstNameKana;

    @Required
    @Length(7)
    @Digit
    @Metadata(name = "郵便番号")
    private String shippingPostalCode;

    @Metadata(name = "取得元配送先都道府県")
    private String orgShippingPrefectureCode;

    // delete by V10-CH 170 start
    // @Required
    // delete by V10-CH 170 end
    @Length(2)
    @Metadata(name = "都道府県")
    private String shippingPrefectureCode;

    @Length(50)
    @Metadata(name = "都道府県")
    private String shippingAddress1;

    // delete by V10-CH 170 start
    // @Required
    // delete by V10-CH 170 end
    @Length(50)
    @Metadata(name = "市区町村")
    private String shippingAddress2;

    @Length(50)
    @Metadata(name = "町名・番地")
    private String shippingAddress3;

    @Required
    @Length(100)
    @Metadata(name = "アパート・マンション・ビル")
    private String shippingAddress4;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号1")
    private String shippingTel1;

    // modify by V10-CH 170 start
    /** 市コード */
    @Required
    @Length(10)
    @Metadata(name = "市コード")
    private String shippingCityCode;

    // modify by V10-CH 170 end

    // soukai add 2012/01/08 ob start
    @Length(10)
    @Metadata(name = "区县")
    private String areaCode;

    // soukai add 2012/01/08 ob end
    // delete by V10-CH 170 start
    @Length(10)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号2")
    private String shippingTel2;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号3")
    private String shippingTel3;

    // delete by V10-CH 170 end

    @Length(24)
    @Phone
    @Metadata(name = "電話番号", order = 15)
    private String shippingTel;

    // Add by V10-CH start
    @MobileNumber
    @Metadata(name = "手机号码", order = 16)
    private String mobileTel;

    // Add by V10-CH end

    private String shippingDirectDate;

    private String shippingDate;

    private String shippingShopCode;

    private String shippingShopName;

    private String shippingTypeCode;

    private String shippingTypeName;

    @Metadata(name = "配送指定日", order = 20)
    private String deliveryAppointedDate;

    private List<CodeAttribute> deliveryAppointedStartTimeList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> deliveryAppointedEndTimeList = new ArrayList<CodeAttribute>();

    private String deliveryAppointedStartTime;

    private String deliveryAppointedEndTime;

    private boolean displayDeliveryAppointedDate;

    private boolean displayDeliveryAppointedTime;

    @Length(500)
    @Metadata(name = "配送先備考")
    private String deliveryRemark;

    private String shippingCharge;

    private String shippingChargeTaxType;

    private Date updateDatetime;

    private String displayMode;

    private String displayMode2;

    // soukai add 2012/01/31 ob start
    private String deliveryCompanyName;
    
    private String deliveryCompanyNo;

    private String deliveryDateTime;

    private String deliveryDateTimeName;

    // soukai add 2012/01/31 ob end
    /**
     * U1020220:受注管理明細のサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class ShippingDetailBean implements Serializable {

      /** serial version uid */
      private static final long serialVersionUID = 1L;

      private String shippingDetailSkuCode;

      private String shippingDetailCommodityName;

      private String shippingDetailStandardDetail1Name;

      private String shippingDetailStandardDetail2Name;

      private String shippingDetailPurchasingAmount;

      private String shippingDetailSalesPrice;

      private String shippingDetailCommodityTaxType;

      private String shippingDetailGiftCode;

      private String shippingDetailGiftName;

      private String shippingDetailGiftPrice;

      private String shippingDetailGiftTaxType;

      private String shippingDetailTotalPrice;

      /**
       * shippingDetailComodityNameを取得します。
       * 
       * @return shippingDetailCommodityName
       */
      public String getShippingDetailCommodityName() {
        return shippingDetailCommodityName;
      }

      /**
       * shippingDetailComodityTaxTypeを取得します。
       * 
       * @return shippingDetailCommodityTaxType
       */
      public String getShippingDetailCommodityTaxType() {
        return shippingDetailCommodityTaxType;
      }

      /**
       * shippingDetailGiftCodeを取得します。
       * 
       * @return shippingDetailGiftCode
       */
      public String getShippingDetailGiftCode() {
        return shippingDetailGiftCode;
      }

      /**
       * shippingDetailGiftNameを取得します。
       * 
       * @return shippingDetailGiftName
       */
      public String getShippingDetailGiftName() {
        return shippingDetailGiftName;
      }

      /**
       * shippingDetailGiftPriceを取得します。
       * 
       * @return shippingDetailGiftPrice
       */
      public String getShippingDetailGiftPrice() {
        return shippingDetailGiftPrice;
      }

      /**
       * shippingDetailGiftTaxTypeを取得します。
       * 
       * @return shippingDetailGiftTaxType
       */
      public String getShippingDetailGiftTaxType() {
        return shippingDetailGiftTaxType;
      }

      /**
       * shippingDetailPurchasingAmountを取得します。
       * 
       * @return shippingDetailPurchasingAmount
       */
      public String getShippingDetailPurchasingAmount() {
        return shippingDetailPurchasingAmount;
      }

      /**
       * shippingDetailSalesPriceを取得します。
       * 
       * @return shippingDetailSalesPrice
       */
      public String getShippingDetailSalesPrice() {
        return shippingDetailSalesPrice;
      }

      /**
       * shippingDetailSkuCodeを取得します。
       * 
       * @return shippingDetailSkuCode
       */
      public String getShippingDetailSkuCode() {
        return shippingDetailSkuCode;
      }

      /**
       * shippingDetailStandardDetail1Nameを取得します。
       * 
       * @return shippingDetailStandardDetail1Name
       */
      public String getShippingDetailStandardDetail1Name() {
        return shippingDetailStandardDetail1Name;
      }

      /**
       * shippingDetailStandardDetail2Nameを取得します。
       * 
       * @return shippingDetailStandardDetail2Name
       */
      public String getShippingDetailStandardDetail2Name() {
        return shippingDetailStandardDetail2Name;
      }

      /**
       * shippingDetailTotalPriceを取得します。
       * 
       * @return shippingDetailTotalPrice
       */
      public String getShippingDetailTotalPrice() {
        return shippingDetailTotalPrice;
      }

      /**
       * shippingDetailComodityNameを設定します。
       * 
       * @param shippingDetailComodityName
       *          商品名称
       */
      public void setShippingDetailCommodityName(String shippingDetailComodityName) {
        this.shippingDetailCommodityName = shippingDetailComodityName;
      }

      /**
       * shippingDetailComodityTaxTypeを設定します。
       * 
       * @param shippingDetailComodityTaxType
       *          商品消費税区分
       */
      public void setShippingDetailCommodityTaxType(String shippingDetailComodityTaxType) {
        this.shippingDetailCommodityTaxType = shippingDetailComodityTaxType;
      }

      /**
       * shippingDetailGiftCodeを設定します。
       * 
       * @param shippingDetailGiftCode
       *          shippingDetailGiftCode
       */
      public void setShippingDetailGiftCode(String shippingDetailGiftCode) {
        this.shippingDetailGiftCode = shippingDetailGiftCode;
      }

      /**
       * shippingDetailGiftNameを設定します。
       * 
       * @param shippingDetailGiftName
       *          shippingDetailGiftName
       */
      public void setShippingDetailGiftName(String shippingDetailGiftName) {
        this.shippingDetailGiftName = shippingDetailGiftName;
      }

      /**
       * shippingDetailGiftPriceを設定します。
       * 
       * @param shippingDetailGiftPrice
       *          shippingDetailGiftPrice
       */
      public void setShippingDetailGiftPrice(String shippingDetailGiftPrice) {
        this.shippingDetailGiftPrice = shippingDetailGiftPrice;
      }

      /**
       * shippingDetailGiftTaxTypeを設定します。
       * 
       * @param shippingDetailGiftTaxType
       *          shippingDetailGiftTaxType
       */
      public void setShippingDetailGiftTaxType(String shippingDetailGiftTaxType) {
        this.shippingDetailGiftTaxType = shippingDetailGiftTaxType;
      }

      /**
       * shippingDetailPurchasingAmountを設定します。
       * 
       * @param shippingDetailPurchasingAmount
       *          shippingDetailPurchasingAmount
       */
      public void setShippingDetailPurchasingAmount(String shippingDetailPurchasingAmount) {
        this.shippingDetailPurchasingAmount = shippingDetailPurchasingAmount;
      }

      /**
       * shippingDetailSalesPriceを設定します。
       * 
       * @param shippingDetailSalesPrice
       *          shippingDetailSalesPrice
       */
      public void setShippingDetailSalesPrice(String shippingDetailSalesPrice) {
        this.shippingDetailSalesPrice = shippingDetailSalesPrice;
      }

      /**
       * shippingDetailSkuCodeを設定します。
       * 
       * @param shippingDetailSkuCode
       *          shippingDetailSkuCode
       */
      public void setShippingDetailSkuCode(String shippingDetailSkuCode) {
        this.shippingDetailSkuCode = shippingDetailSkuCode;
      }

      /**
       * shippingDetailStandardDetail1Nameを設定します。
       * 
       * @param shippingDetailStandardDetail1Name
       *          shippingDetailStandardDetail1Name
       */
      public void setShippingDetailStandardDetail1Name(String shippingDetailStandardDetail1Name) {
        this.shippingDetailStandardDetail1Name = shippingDetailStandardDetail1Name;
      }

      /**
       * shippingDetailStandardDetail2Nameを設定します。
       * 
       * @param shippingDetailStandardDetail2Name
       *          shippingDetailStandardDetail2Name
       */
      public void setShippingDetailStandardDetail2Name(String shippingDetailStandardDetail2Name) {
        this.shippingDetailStandardDetail2Name = shippingDetailStandardDetail2Name;
      }

      /**
       * shippingDetailTotalPriceを設定します。
       * 
       * @param shippingDetailTotalPrice
       *          shippingDetailTotalPrice
       */
      public void setShippingDetailTotalPrice(String shippingDetailTotalPrice) {
        this.shippingDetailTotalPrice = shippingDetailTotalPrice;
      }

    }

    /**
     * deliveryAppointedEndTimeを取得します。
     * 
     * @return deliveryAppointedEndTime
     */
    public String getDeliveryAppointedEndTime() {
      return deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedStartTimeを取得します。
     * 
     * @return deliveryAppointedStartTime
     */
    public String getDeliveryAppointedStartTime() {
      return deliveryAppointedStartTime;
    }

    /**
     * shippingAddress1を取得します。
     * 
     * @return shippingAddress1
     */
    public String getShippingAddress1() {
      return shippingAddress1;
    }

    /**
     * shippingAddress2を取得します。
     * 
     * @return shippingAddress2
     */
    public String getShippingAddress2() {
      return shippingAddress2;
    }

    /**
     * shippingAddress3を取得します。
     * 
     * @return shippingAddress3
     */
    public String getShippingAddress3() {
      return shippingAddress3;
    }

    /**
     * shippingAddress4を取得します。
     * 
     * @return shippingAddress4
     */
    public String getShippingAddress4() {
      return shippingAddress4;
    }

    /**
     * shippingChargeを取得します。
     * 
     * @return shippingCharge
     */
    public String getShippingCharge() {
      return shippingCharge;
    }

    /**
     * shippingChargeTaxTypeを取得します。
     * 
     * @return shippingChargeTaxType
     */
    public String getShippingChargeTaxType() {
      return shippingChargeTaxType;
    }

    /**
     * shippingDetailListを取得します。
     * 
     * @return shippingDetailList
     */
    public List<ShippingDetailBean> getShippingDetailList() {
      return shippingDetailList;
    }

    /**
     * shippingFirstNameを取得します。
     * 
     * @return shippingFirstName
     */
    public String getShippingFirstName() {
      return shippingFirstName;
    }

    /**
     * shippingFirstNameKanaを取得します。
     * 
     * @return shippingFirstNameKana
     */
    public String getShippingFirstNameKana() {
      return shippingFirstNameKana;
    }

    /**
     * shippingLastNameを取得します。
     * 
     * @return shippingLastName
     */
    public String getShippingLastName() {
      return shippingLastName;
    }

    /**
     * shippingLastNameKanaを取得します。
     * 
     * @return shippingLastNameKana
     */
    public String getShippingLastNameKana() {
      return shippingLastNameKana;
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
     * shippingShopCodeを取得します。
     * 
     * @return shippingShopCode
     */
    public String getShippingShopCode() {
      return shippingShopCode;
    }

    /**
     * shippingShopNameを取得します。
     * 
     * @return shippingShopName
     */
    public String getShippingShopName() {
      return shippingShopName;
    }

    /**
     * shippingTypeCodeを取得します。
     * 
     * @return shippingTypeCode
     */
    public String getShippingTypeCode() {
      return shippingTypeCode;
    }

    /**
     * shippingTypeNameを取得します。
     * 
     * @return shippingTypeName
     */
    public String getShippingTypeName() {
      return shippingTypeName;
    }

    /**
     * deliveryAppointedEndTimeを設定します。
     * 
     * @param deliveryAppointedEndTime
     *          deliveryAppointedEndTime
     */
    public void setDeliveryAppointedEndTime(String deliveryAppointedEndTime) {
      this.deliveryAppointedEndTime = deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedStartTimeを設定します。
     * 
     * @param deliveryAppointedStartTime
     *          deliveryAppointedStartTime
     */
    public void setDeliveryAppointedStartTime(String deliveryAppointedStartTime) {
      this.deliveryAppointedStartTime = deliveryAppointedStartTime;
    }

    /**
     * shippingAddress1を設定します。
     * 
     * @param shippingAddress1
     *          shippingAddress1
     */
    public void setShippingAddress1(String shippingAddress1) {
      this.shippingAddress1 = shippingAddress1;
    }

    /**
     * shippingAddress2を設定します。
     * 
     * @param shippingAddress2
     *          shippingAddress2
     */
    public void setShippingAddress2(String shippingAddress2) {
      this.shippingAddress2 = shippingAddress2;
    }

    /**
     * shippingAddress3を設定します。
     * 
     * @param shippingAddress3
     *          shippingAddress3
     */
    public void setShippingAddress3(String shippingAddress3) {
      this.shippingAddress3 = shippingAddress3;
    }

    /**
     * shippingAddress4を設定します。
     * 
     * @param shippingAddress4
     *          shippingAddress4
     */
    public void setShippingAddress4(String shippingAddress4) {
      this.shippingAddress4 = shippingAddress4;
    }

    /**
     * shippingChargeを設定します。
     * 
     * @param shippingCharge
     *          shippingCharge
     */
    public void setShippingCharge(String shippingCharge) {
      this.shippingCharge = shippingCharge;
    }

    /**
     * shippingChargeTaxTypeを設定します。
     * 
     * @param shippingChargeTaxType
     *          shippingChargeTaxType
     */
    public void setShippingChargeTaxType(String shippingChargeTaxType) {
      this.shippingChargeTaxType = shippingChargeTaxType;
    }

    /**
     * shippingDetailListを設定します。
     * 
     * @param shippingDetailList
     *          shippingDetailList
     */
    public void setShippingDetailList(List<ShippingDetailBean> shippingDetailList) {
      this.shippingDetailList = shippingDetailList;
    }

    /**
     * shippingFirstNameを設定します。
     * 
     * @param shippingFirstName
     *          shippingFirstName
     */
    public void setShippingFirstName(String shippingFirstName) {
      this.shippingFirstName = shippingFirstName;
    }

    /**
     * shippingFirstNameKanaを設定します。
     * 
     * @param shippingFirstNameKana
     *          shippingFirstNameKana
     */
    public void setShippingFirstNameKana(String shippingFirstNameKana) {
      this.shippingFirstNameKana = shippingFirstNameKana;
    }

    /**
     * shippingLastNameを設定します。
     * 
     * @param shippingLastName
     *          shippingLastName
     */
    public void setShippingLastName(String shippingLastName) {
      this.shippingLastName = shippingLastName;
    }

    /**
     * shippingLastNameKanaを設定します。
     * 
     * @param shippingLastNameKana
     *          shippingLastNameKana
     */
    public void setShippingLastNameKana(String shippingLastNameKana) {
      this.shippingLastNameKana = shippingLastNameKana;
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
     * shippingShopCodeを設定します。
     * 
     * @param shippingShopCode
     *          shippingShopCode
     */
    public void setShippingShopCode(String shippingShopCode) {
      this.shippingShopCode = shippingShopCode;
    }

    /**
     * shippingShopNameを設定します。
     * 
     * @param shippingShopName
     *          shippingShopName
     */
    public void setShippingShopName(String shippingShopName) {
      this.shippingShopName = shippingShopName;
    }

    /**
     * shippingTypeCodeを設定します。
     * 
     * @param shippingTypeCode
     *          shippingTypeCode
     */
    public void setShippingTypeCode(String shippingTypeCode) {
      this.shippingTypeCode = shippingTypeCode;
    }

    /**
     * shippingTypeNameを設定します。
     * 
     * @param shippingTypeName
     *          shippingTypeName
     */
    public void setShippingTypeName(String shippingTypeName) {
      this.shippingTypeName = shippingTypeName;
    }

    /**
     * deliveryRemarkを取得します。
     * 
     * @return deliveryRemark deliveryRemark
     */
    public String getDeliveryRemark() {
      return deliveryRemark;
    }

    /**
     * deliveryRemarkを設定します。
     * 
     * @param deliveryRemark
     *          deliveryRemark
     */
    public void setDeliveryRemark(String deliveryRemark) {
      this.deliveryRemark = deliveryRemark;
    }

    /**
     * updateDatetimeを取得します。
     * 
     * @return updateDatetime updateDatetime
     */
    public Date getUpdateDatetime() {
      return DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * updateDatetimeを設定します。
     * 
     * @param updateDatetime
     *          updateDatetime
     */
    public void setUpdateDatetime(Date updateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * shippingPrefectureCodeを取得します。
     * 
     * @return shippingPrefectureCode
     */
    public String getShippingPrefectureCode() {
      return shippingPrefectureCode;
    }

    /**
     * shippingPrefectureCodeを設定します。
     * 
     * @param shippingPrefectureCode
     *          shippingPrefectureCode
     */
    public void setShippingPrefectureCode(String shippingPrefectureCode) {
      this.shippingPrefectureCode = shippingPrefectureCode;
    }

    /**
     * orgShippingPrefectureCodeを取得します。
     * 
     * @return orgShippingPrefectureCode
     */
    public String getOrgShippingPrefectureCode() {
      return orgShippingPrefectureCode;
    }

    /**
     * orgShippingPrefectureCodeを設定します。
     * 
     * @param orgShippingPrefectureCode
     *          orgShippingPrefectureCode
     */
    public void setOrgShippingPrefectureCode(String orgShippingPrefectureCode) {
      this.orgShippingPrefectureCode = orgShippingPrefectureCode;
    }

    /**
     * shippingPostalCodeを取得します。
     * 
     * @return shippingPostalCode
     */
    public String getShippingPostalCode() {
      return shippingPostalCode;
    }

    /**
     * shippingPostalCodeを設定します。
     * 
     * @param shippingPostalCode
     *          shippingPostalCode
     */
    public void setShippingPostalCode(String shippingPostalCode) {
      this.shippingPostalCode = shippingPostalCode;
    }

    /**
     * shippingTel1を取得します。
     * 
     * @return shippingTel1 shippingTel1
     */
    public String getShippingTel1() {
      return shippingTel1;
    }

    /**
     * shippingTel1を設定します。
     * 
     * @param shippingTel1
     *          shippingTel1
     */
    public void setShippingTel1(String shippingTel1) {
      this.shippingTel1 = shippingTel1;
    }

    // delete by V10-CH 170 start
    /**
     * shippingTel2を取得します。
     * 
     * @return shippingTel2 shippingTel2
     */
    public String getShippingTel2() {
      return shippingTel2;
    }

    /**
     * shippingTel2を設定します。
     * 
     * @param shippingTel2
     *          shippingTel2
     */
    public void setShippingTel2(String shippingTel2) {
      this.shippingTel2 = shippingTel2;
    }

    /**
     * shippingTel3を取得します。
     * 
     * @return shippingTel3 shippingTel3
     */
    public String getShippingTel3() {
      return shippingTel3;
    }

    /**
     * shippingTel3を設定します。
     * 
     * @param shippingTel3
     *          shippingTel3
     */
    public void setShippingTel3(String shippingTel3) {
      this.shippingTel3 = shippingTel3;
    }

    // delete by V10-CH 170 end
    /**
     * shippingDateを取得します。
     * 
     * @return shippingDate
     */
    public String getShippingDate() {
      return shippingDate;
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
     * shippingDirectDateを取得します。
     * 
     * @return shippingDirectDate
     */
    public String getShippingDirectDate() {
      return shippingDirectDate;
    }

    /**
     * shippingDirectDateを設定します。
     * 
     * @param shippingDirectDate
     *          shippingDirectDate
     */
    public void setShippingDirectDate(String shippingDirectDate) {
      this.shippingDirectDate = shippingDirectDate;
    }

    /**
     * displayModeを取得します。
     * 
     * @return displayMode
     */
    public String getDisplayMode() {
      return displayMode;
    }

    /**
     * displayModeを設定します。
     * 
     * @param displayMode
     *          displayMode
     */
    public void setDisplayMode(String displayMode) {
      this.displayMode = displayMode;
    }

    /**
     * deliveryAppointedEndTimeListを取得します。
     * 
     * @return deliveryAppointedEndTimeList
     */
    public List<CodeAttribute> getDeliveryAppointedEndTimeList() {
      return deliveryAppointedEndTimeList;
    }

    /**
     * deliveryAppointedEndTimeListを設定します。
     * 
     * @param deliveryAppointedEndTimeList
     *          deliveryAppointedEndTimeList
     */
    public void setDeliveryAppointedEndTimeList(List<CodeAttribute> deliveryAppointedEndTimeList) {
      this.deliveryAppointedEndTimeList = deliveryAppointedEndTimeList;
    }

    /**
     * deliveryAppointedStartTimeListを取得します。
     * 
     * @return deliveryAppointedStartTimeList
     */
    public List<CodeAttribute> getDeliveryAppointedStartTimeList() {
      return deliveryAppointedStartTimeList;
    }

    /**
     * deliveryAppointedStartTimeListを設定します。
     * 
     * @param deliveryAppointedStartTimeList
     *          deliveryAppointedStartTimeList
     */
    public void setDeliveryAppointedStartTimeList(List<CodeAttribute> deliveryAppointedStartTimeList) {
      this.deliveryAppointedStartTimeList = deliveryAppointedStartTimeList;
    }

    /**
     * displayDeliveryAppointedDateを取得します。
     * 
     * @return displayDeliveryAppointedDate
     */
    public boolean isDisplayDeliveryAppointedDate() {
      return displayDeliveryAppointedDate;
    }

    /**
     * displayDeliveryAppointedDateを設定します。
     * 
     * @param displayDeliveryAppointedDate
     *          displayDeliveryAppointedDate
     */
    public void setDisplayDeliveryAppointedDate(boolean displayDeliveryAppointedDate) {
      this.displayDeliveryAppointedDate = displayDeliveryAppointedDate;
    }

    /**
     * displayDeliveryAppointedTimeを取得します。
     * 
     * @return displayDeliveryAppointedTime
     */
    public boolean isDisplayDeliveryAppointedTime() {
      return displayDeliveryAppointedTime;
    }

    /**
     * displayDeliveryAppointedTimeを設定します。
     * 
     * @param displayDeliveryAppointedTime
     *          displayDeliveryAppointedTime
     */
    public void setDisplayDeliveryAppointedTime(boolean displayDeliveryAppointedTime) {
      this.displayDeliveryAppointedTime = displayDeliveryAppointedTime;
    }

    /**
     * deliveryAppointedDateを取得します。
     * 
     * @return deliveryAppointedDate
     */
    public String getDeliveryAppointedDate() {
      return deliveryAppointedDate;
    }

    /**
     * deliveryAppointedDateを設定します。
     * 
     * @param deliveryAppointedDate
     *          deliveryAppointedDate
     */
    public void setDeliveryAppointedDate(String deliveryAppointedDate) {
      this.deliveryAppointedDate = deliveryAppointedDate;
    }

    /**
     * shippingTelを取得します。
     * 
     * @return shippingTel
     */
    public String getShippingTel() {
      return shippingTel;
    }

    /**
     * shippingTelを設定します。
     * 
     * @param shippingTel
     *          shippingTel
     */
    public void setShippingTel(String shippingTel) {
      this.shippingTel = shippingTel;
    }

    public String getShippingCityCode() {
      return shippingCityCode;
    }

    public void setShippingCityCode(String shippingCityCode) {
      this.shippingCityCode = shippingCityCode;
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

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
      return areaCode;
    }

    /**
     * @param areaCode
     *          the areaCode to set
     */
    public void setAreaCode(String areaCode) {
      this.areaCode = areaCode;
    }

    /**
     * @return the deliveryCompanyName
     */
    public String getDeliveryCompanyName() {
      return deliveryCompanyName;
    }

    /**
     * @param deliveryCompanyName
     *          the deliveryCompanyName to set
     */
    public void setDeliveryCompanyName(String deliveryCompanyName) {
      this.deliveryCompanyName = deliveryCompanyName;
    }

    /**
     * @return the returnItemType
     */
    public String getReturnItemType() {
      return returnItemType;
    }

    /**
     * @param returnItemType
     *          the returnItemType to set
     */
    public void setReturnItemType(String returnItemType) {
      this.returnItemType = returnItemType;
    }

    public String getDeliveryDateTime() {
      return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
      this.deliveryDateTime = deliveryDateTime;
    }

    public String getDisplayMode2() {
      return displayMode2;
    }

    public void setDisplayMode2(String displayMode2) {
      this.displayMode2 = displayMode2;
    }

    public String getDeliveryDateTimeName() {
      return deliveryDateTimeName;
    }

    public void setDeliveryDateTimeName(String deliveryDateTimeName) {
      this.deliveryDateTimeName = deliveryDateTimeName;
    }

    
    /**
     * @return the deliveryCompanyNo
     */
    public String getDeliveryCompanyNo() {
      return deliveryCompanyNo;
    }

    
    /**
     * @param deliveryCompanyNo the deliveryCompanyNo to set
     */
    public void setDeliveryCompanyNo(String deliveryCompanyNo) {
      this.deliveryCompanyNo = deliveryCompanyNo;
    }

  }

  // soukai add 2011/12/27 ob start
  /**
   * U1020220:受注管理明細のサブモデルです。
   * 
   * @author Kousen.
   */
  public static class InvoiceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String invoiceFlg;

    @Required
    @Length(50)
    @Metadata(name = "商品规格", order = 1)
    private String invoiceCommodityName;

    @Required
    @Metadata(name = "发票类型", order = 2)
    private String invoiceType;

    @Required
    @Length(70)
    @Metadata(name = "发票抬头", order = 3)
    private String invoiceCustomerName;

    @Required
    @Length(60)
    @Metadata(name = "公司名称", order = 4)
    private String invoiceCompanyName;

    @Required
    @Length(20)
    @AlphaNum2
    @Metadata(name = "纳税人识别号", order = 5)
    private String invoiceTaxpayerCode;

    @Required
    @Length(100)
    @Metadata(name = "地址", order = 6)
    private String invoiceAddress;

    @Required
    @Length(20)
    @Digit(allowNegative = false)
    @Metadata(name = "电话号码", order = 7)
    private String invoiceTel;

    @Required
    @Length(50)
    @Metadata(name = "银行名称", order = 8)
    private String invoiceBankName;

    @Required
    @Length(20)
    @BankCode
    @Metadata(name = "银行编号", order = 9)
    private String invoiceBankNo;

    private String invoiceSaveFlg;

    public String getInvoiceFlg() {
      return invoiceFlg;
    }

    public void setInvoiceFlg(String invoiceFlg) {
      this.invoiceFlg = invoiceFlg;
    }

    public String getInvoiceCommodityName() {
      return invoiceCommodityName;
    }

    public void setInvoiceCommodityName(String invoiceCommodityName) {
      this.invoiceCommodityName = invoiceCommodityName;
    }

    public String getInvoiceType() {
      return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
      this.invoiceType = invoiceType;
    }

    public String getInvoiceCustomerName() {
      return invoiceCustomerName;
    }

    public void setInvoiceCustomerName(String invoiceCustomerName) {
      this.invoiceCustomerName = invoiceCustomerName;
    }

    public String getInvoiceCompanyName() {
      return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
      this.invoiceCompanyName = invoiceCompanyName;
    }

    public String getInvoiceTaxpayerCode() {
      return invoiceTaxpayerCode;
    }

    public void setInvoiceTaxpayerCode(String invoiceTaxpayerCode) {
      this.invoiceTaxpayerCode = invoiceTaxpayerCode;
    }

    public String getInvoiceAddress() {
      return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
      this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceTel() {
      return invoiceTel;
    }

    public void setInvoiceTel(String invoiceTel) {
      this.invoiceTel = invoiceTel;
    }

    public String getInvoiceBankName() {
      return invoiceBankName;
    }

    public void setInvoiceBankName(String invoiceBankName) {
      this.invoiceBankName = invoiceBankName;
    }

    public String getInvoiceBankNo() {
      return invoiceBankNo;
    }

    public void setInvoiceBankNo(String invoiceBankNo) {
      this.invoiceBankNo = invoiceBankNo;
    }

    public String getInvoiceSaveFlg() {
      return invoiceSaveFlg;
    }

    public void setInvoiceSaveFlg(String invoiceSaveFlg) {
      this.invoiceSaveFlg = invoiceSaveFlg;
    }

  }

  // soukai add 2011/12/27 ob end
  /**
   * orderDatetimeを取得します。
   * 
   * @return orderDatetime
   */
  public String getOrderDatetime() {
    return orderDatetime;
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
   * allPaymentPriceを取得します。
   * 
   * @return allPaymentPrice
   */
  public String getAllPaymentPrice() {
    return allPaymentPrice;
  }

  /**
   * orderDataTransportFlgを取得します。
   * 
   * @return orderDataTransportFlg
   */
  public String getOrderDataTransportFlg() {
    return orderDataTransportFlg;
  }

  /**
   * orderEditMessageを取得します。
   * 
   * @return orderEditMessage
   */
  public String getOrderEditMessage() {
    return orderEditMessage;
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
   * orderStatusを取得します。
   * 
   * @return orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  /**
   * paymentCommissionを取得します。
   * 
   * @return paymentCommission
   */
  public String getPaymentCommission() {
    return paymentCommission;
  }

  /**
   * shippingListを取得します。
   * 
   * @return shippingList
   */
  public List<ShippingHeaderBean> getShippingList() {
    return shippingList;
  }

  /**
   * totalCommodityPriceを取得します。
   * 
   * @return totalCommodityPrice
   */
  public String getTotalCommodityPrice() {
    return totalCommodityPrice;
  }

  /**
   * totalGiftPriceを取得します。
   * 
   * @return totalGiftPrice
   */
  public String getTotalGiftPrice() {
    return totalGiftPrice;
  }

  /**
   * totalShippingChargeを取得します。
   * 
   * @return totalShippingCharge
   */
  public String getTotalShippingCharge() {
    return totalShippingCharge;
  }

  /**
   * usedPointを取得します。
   * 
   * @return usedPoint
   */
  public String getUsedPoint() {
    return usedPoint;
  }

  /**
   * allPaymentPriceを設定します。
   * 
   * @param allPaymentPrice
   *          allPaymentPrice
   */
  public void setAllPaymentPrice(String allPaymentPrice) {
    this.allPaymentPrice = allPaymentPrice;
  }

  /**
   * orderDataTransportFlgを設定します。
   * 
   * @param orderDataTransportFlg
   *          orderDataTransportFlg
   */
  public void setOrderDataTransportFlg(String orderDataTransportFlg) {
    this.orderDataTransportFlg = orderDataTransportFlg;
  }

  /**
   * orderEditMessageを設定します。
   * 
   * @param orderEditMessage
   *          orderEditMessage
   */
  public void setOrderEditMessage(String orderEditMessage) {
    this.orderEditMessage = orderEditMessage;
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
   * orderStatusを設定します。
   * 
   * @param orderStatus
   *          orderStatus
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * orderStatusNameを取得します。
   * 
   * @return orderStatusName
   */
  public String getOrderStatusName() {
    return orderStatusName;
  }

  /**
   * orderStatusNameを設定します。
   * 
   * @param orderStatusName
   *          orderStatusName
   */
  public void setOrderStatusName(String orderStatusName) {
    this.orderStatusName = orderStatusName;
  }

  /**
   * hasReturnを取得します。
   * 
   * @return hasReturn
   */
  public String getHasReturn() {
    return hasReturn;
  }

  /**
   * hasReturnを設定します。
   * 
   * @param hasReturn
   *          hasReturn
   */
  public void setHasReturn(String hasReturn) {
    this.hasReturn = hasReturn;
  }

  /**
   * paymentCommissionを設定します。
   * 
   * @param paymentCommission
   *          paymentCommission
   */
  public void setPaymentCommission(String paymentCommission) {
    this.paymentCommission = paymentCommission;
  }

  /**
   * shippingListを設定します。
   * 
   * @param shippingList
   *          shippingList
   */
  public void setShippingList(List<ShippingHeaderBean> shippingList) {
    this.shippingList = shippingList;
  }

  /**
   * totalCommodityPriceを設定します。
   * 
   * @param totalCommodityPrice
   *          totalCommodityPrice
   */
  public void setTotalCommodityPrice(String totalCommodityPrice) {
    this.totalCommodityPrice = totalCommodityPrice;
  }

  /**
   * totalGiftPriceを設定します。
   * 
   * @param totalGiftPrice
   *          totalGiftPrice
   */
  public void setTotalGiftPrice(String totalGiftPrice) {
    this.totalGiftPrice = totalGiftPrice;
  }

  /**
   * totalShippingChargeを設定します。
   * 
   * @param totalShippingCharge
   *          totalShippingCharge
   */
  public void setTotalShippingCharge(String totalShippingCharge) {
    this.totalShippingCharge = totalShippingCharge;
  }

  /**
   * usedPointを設定します。
   * 
   * @param usedPoint
   *          usedPoint
   */
  public void setUsedPoint(String usedPoint) {
    this.usedPoint = usedPoint;
  }

  /**
   * paymentEditを取得します。
   * 
   * @return paymentEdit
   */
  public PaymentBean getPaymentEdit() {
    return paymentEdit;
  }

  /**
   * paymentEditを設定します。
   * 
   * @param paymentEdit
   *          paymentEdit
   */
  public void setPaymentEdit(PaymentBean paymentEdit) {
    this.paymentEdit = paymentEdit;
  }

  /**
   * orderHeaderEditを取得します。
   * 
   * @return orderHeaderEdit
   */
  public OrderHeaderBean getOrderHeaderEdit() {
    return orderHeaderEdit;
  }

  /**
   * orderHeaderEditを設定します。
   * 
   * @param orderHeaderEdit
   *          orderHeaderEdit
   */
  public void setOrderHeaderEdit(OrderHeaderBean orderHeaderEdit) {
    this.orderHeaderEdit = orderHeaderEdit;
  }

  /**
   * totalOrderPriceを取得します。
   * 
   * @return totalOrderPrice
   */
  public String getTotalOrderPrice() {
    return totalOrderPrice;
  }

  /**
   * totalOrderPriceを設定します。
   * 
   * @param totalOrderPrice
   *          totalOrderPrice
   */
  public void setTotalOrderPrice(String totalOrderPrice) {
    this.totalOrderPrice = totalOrderPrice;
  }

  /**
   * cancelButtonFlgを取得します。
   * 
   * @return cancelButtonFlg
   */
  public boolean isCancelButtonFlg() {
    return cancelButtonFlg;
  }

  /**
   * cancelButtonFlgを設定します。
   * 
   * @param cancelButtonFlg
   *          cancelButtonFlg
   */
  public void setCancelButtonFlg(boolean cancelButtonFlg) {
    this.cancelButtonFlg = cancelButtonFlg;
  }

  /**
   * modifyButtonFlgを取得します。
   * 
   * @return modifyButtonFlg
   */
  public boolean isModifyButtonFlg() {
    return modifyButtonFlg;
  }

  /**
   * modifyButtonFlgを設定します。
   * 
   * @param modifyButtonFlg
   *          modifyButtonFlg
   */
  public void setModifyButtonFlg(boolean modifyButtonFlg) {
    this.modifyButtonFlg = modifyButtonFlg;
  }

  /**
   * paymentDateClearButtonFlgを取得します。
   * 
   * @return paymentDateClearButtonFlg
   */
  public boolean isPaymentDateClearButtonFlg() {
    return paymentDateClearButtonFlg;
  }

  /**
   * paymentDateClearButtonFlgを設定します。
   * 
   * @param paymentDateClearButtonFlg
   *          paymentDateClearButtonFlg
   */
  public void setPaymentDateClearButtonFlg(boolean paymentDateClearButtonFlg) {
    this.paymentDateClearButtonFlg = paymentDateClearButtonFlg;
  }

  /**
   * returnButtonFlgを取得します。
   * 
   * @return returnButtonFlg
   */
  public boolean isReturnButtonFlg() {
    return returnButtonFlg;
  }

  /**
   * returnButtonFlgを設定します。
   * 
   * @param returnButtonFlg
   *          returnButtonFlg
   */
  public void setReturnButtonFlg(boolean returnButtonFlg) {
    this.returnButtonFlg = returnButtonFlg;
  }

  /**
   * updateButtonFlgを取得します。
   * 
   * @return updateButtonFlg
   */
  public boolean isUpdateButtonFlg() {
    return updateButtonFlg;
  }

  /**
   * updateButtonFlgを設定します。
   * 
   * @param updateButtonFlg
   *          updateButtonFlg
   */
  public void setUpdateButtonFlg(boolean updateButtonFlg) {
    this.updateButtonFlg = updateButtonFlg;
  }

  /**
   * orderAndPaymentPartDisplayFlgを取得します。
   * 
   * @return orderAndPaymentPartDisplayFlg
   */
  public boolean isOrderAndPaymentPartDisplayFlg() {
    return orderAndPaymentPartDisplayFlg;
  }

  /**
   * orderAndPaymentPartDisplayFlgを設定します。
   * 
   * @param orderAndPaymentPartDisplayFlg
   *          orderAndPaymentPartDisplayFlg
   */
  public void setOrderAndPaymentPartDisplayFlg(boolean orderAndPaymentPartDisplayFlg) {
    this.orderAndPaymentPartDisplayFlg = orderAndPaymentPartDisplayFlg;
  }

  /**
   * orderInformationDisplayModeを取得します。
   * 
   * @return orderInformationDisplayMode
   */
  public String getOrderInformationDisplayMode() {
    return orderInformationDisplayMode;
  }

  /**
   * orderInformationDisplayModeを設定します。
   * 
   * @param orderInformationDisplayMode
   *          orderInformationDisplayMode
   */
  public void setOrderInformationDisplayMode(String orderInformationDisplayMode) {
    this.orderInformationDisplayMode = orderInformationDisplayMode;
  }

  /**
   * shippingInformationDisplayModeを取得します。
   * 
   * @return shippingInformationDisplayMode
   */
  public String getShippingInformationDisplayMode() {
    return shippingInformationDisplayMode;
  }

  /**
   * shippingInformationDisplayModeを設定します。
   * 
   * @param shippingInformationDisplayMode
   *          shippingInformationDisplayMode
   */
  public void setShippingInformationDisplayMode(String shippingInformationDisplayMode) {
    this.shippingInformationDisplayMode = shippingInformationDisplayMode;
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
    getPaymentEdit().setPaymentDate(reqparam.getDateString("paymentDate"));
    getOrderHeaderEdit().setMessage(reqparam.get("message"));
    getOrderHeaderEdit().setCaution(reqparam.get("caution"));
    getOrderHeaderEdit().setCustomerLastName(StringUtil.parse(reqparam.get("customerLastName")));
    getOrderHeaderEdit().setCustomerFirstName(reqparam.get("customerFirstName"));
    getOrderHeaderEdit().setCustomerLastNameKana(reqparam.get("customerLastNameKana"));
    getOrderHeaderEdit().setCustomerFirstNameKana(reqparam.get("customerFirstNameKana"));
    getOrderHeaderEdit().setCustomerEmail(reqparam.get("customerEmail"));
    getOrderHeaderEdit().setPostalCode(reqparam.get("postalCode"));
    // modify by V10-CH 170 start
    getOrderHeaderEdit().setPrefectureCode(reqparam.get("prefectureCode"));
    // getOrderHeaderEdit().setAddress2(reqparam.get("address2"));
    getOrderHeaderEdit().setCityCode(reqparam.get("cityCode"));
    // soukai add 2012/01/08 ob start
    getOrderHeaderEdit().setAreaCode(reqparam.get("areaCode"));
    // soukai add 2012/01/08 ob end
    // modify by V10-CH 170 end
    getOrderHeaderEdit().setAddress3(reqparam.get("address3"));
    getOrderHeaderEdit().setAddress4(reqparam.get("address4"));
    getOrderHeaderEdit().setCustomerTel1(reqparam.get("customerTel_1"));
    // modify by V10-CH 170 start
    getOrderHeaderEdit().setCustomerTel2(reqparam.get("customerTel_2"));
    getOrderHeaderEdit().setCustomerTel3(reqparam.get("customerTel_3"));
    if (StringUtil.hasValueAllOf(getOrderHeaderEdit().getCustomerTel1(), getOrderHeaderEdit().getCustomerTel2(),
        getOrderHeaderEdit().getCustomerTel3())) {
      getOrderHeaderEdit().setCustomerTel(
          StringUtil.joint('-', getOrderHeaderEdit().getCustomerTel1(), getOrderHeaderEdit().getCustomerTel2(),
              getOrderHeaderEdit().getCustomerTel3()));
    } else if (StringUtil.hasValueAllOf(getOrderHeaderEdit().getCustomerTel1(), getOrderHeaderEdit().getCustomerTel2())) {
      getOrderHeaderEdit().setCustomerTel(
          StringUtil.joint('-', getOrderHeaderEdit().getCustomerTel1(), getOrderHeaderEdit().getCustomerTel2()));
    } else {
      getOrderHeaderEdit().setCustomerTel("");
    }

    // modify by V10-CH 170 end
    // Add by V10-CH start
    getOrderHeaderEdit().setCustomerMobile(reqparam.get("customerMobile"));
    // Add by V10-Ch end

    String[] shippingNoList = reqparam.getAll("shippingNo");

    int count = 0; // 出荷明細をカウントする変数。1件目は0

    for (String shippingNo : shippingNoList) {
      for (ShippingHeaderBean shippingHeader : getShippingList()) {
        if (shippingHeader.getShippingNo().equals(shippingNo)) {
          shippingHeader.setShippingLastName(StringUtil.parse(reqparam.getAll("shippingLastName")[count]));
          shippingHeader.setShippingFirstName(reqparam.getAll("shippingFirstName")[count]);
          shippingHeader.setShippingLastNameKana(reqparam.getAll("shippingLastNameKana")[count]);
          shippingHeader.setShippingFirstNameKana(reqparam.getAll("shippingFirstNameKana")[count]);
          shippingHeader.setShippingPostalCode(reqparam.getAll("shippingPostalCode")[count]);
          shippingHeader.setShippingAddress1(reqparam.getAll("shippingAddress1")[count]);
          shippingHeader.setShippingAddress2(reqparam.getAll("shippingAddress2")[count]);
          shippingHeader.setShippingAddress3(reqparam.getAll("shippingAddress3")[count]);
          shippingHeader.setShippingAddress4(StringUtil.parse(reqparam.getAll("shippingAddress4")[count]));
          String shippingTel1 = reqparam.getAll("shippingTel_1")[count];
          // delete by V10-CH 170 start
          String shippingTel2 = reqparam.getAll("shippingTel_2")[count];
          String shippingTel3 = reqparam.getAll("shippingTel_3")[count];
          // delete by V10-CH 170 end
          shippingHeader.setShippingTel1(shippingTel1);
          // modify by V10-CH 170 start
          shippingHeader.setShippingTel2(shippingTel2);
          shippingHeader.setShippingTel3(shippingTel3);
          if (StringUtil.hasValueAllOf(shippingTel1, shippingTel2, shippingTel3)) {
            shippingHeader.setShippingTel(StringUtil.joint('-', shippingTel1, shippingTel2, shippingTel3));
          } else if (StringUtil.hasValueAllOf(shippingTel1, shippingTel2)) {
            shippingHeader.setShippingTel(StringUtil.joint('-', shippingTel1, shippingTel2));
          } else {
            shippingHeader.setShippingTel("");
          }
          // modify by V10-CH 170 end
          // Add by V10-CH start
          shippingHeader.setMobileTel(reqparam.getAll("mobileTel")[count]);
          // Add by V10-CH end

          String deliveryAppointedDate = getDateString(reqparam, "deliveryAppointedDate", count);
          if (StringUtil.isNullOrEmpty(deliveryAppointedDate) && reqparam.getAll("deliveryAppointedDate").length > count) {
            deliveryAppointedDate = reqparam.getAll("deliveryAppointedDate")[count];
          }
          shippingHeader.setDeliveryAppointedDate(deliveryAppointedDate);
          // soukai add 2012/03/27 ob start
          if (this.isTmallOrderFlg()) {
            shippingHeader.setDeliveryAppointedDate(reqparam.getAll("deliveryAppointedDate")[count]);
            shippingHeader.setDeliveryDateTime(reqparam.getAll("deliveryDateTime")[count]);
          }
          // soukai add 2012/03/27 ob end
          String deliveryStartTime = reqparam.getAll("deliveryAppointedStartTime")[count];
          String deliveryEndTime = reqparam.getAll("deliveryAppointedEndTime")[count];
          shippingHeader.setDeliveryAppointedStartTime(deliveryStartTime);
          shippingHeader.setDeliveryAppointedEndTime(deliveryEndTime);
          shippingHeader.setDeliveryRemark(reqparam.getAll("deliveryRemark")[count]);
        }

      }
      count += 1;
    }
    // soukai add 20111227 ob start
    // お支払い情報取得
    String invoiceFlg = reqparam.get("invoiceFlg");
    if (StringUtil.isNullOrEmpty(invoiceFlg)) {
      // 未领取发票
      invoiceFlg = InvoiceFlg.NO_NEED.getValue();
    }
    this.orderInvoice.setInvoiceFlg(invoiceFlg);

    // 已领取发票
    if (InvoiceFlg.NEED.getValue().equals(this.orderInvoice.getInvoiceFlg())) {
      this.orderInvoice.setInvoiceCommodityName(reqparam.get("invoiceCommodityName"));// 商品规格
      this.orderInvoice.setInvoiceType(reqparam.get("invoiceType"));// 发票类型

      if (InvoiceType.USUAL.getValue().equals(this.orderInvoice.getInvoiceType())) {
        // 通常发票
        this.orderInvoice.setInvoiceCustomerName(StringUtil.parse(reqparam.get("invoiceCustomerName")));// 顾客姓名

        // 清空
        this.orderInvoice.setInvoiceCompanyName(null);// 公司名称
        this.orderInvoice.setInvoiceTaxpayerCode(null);// 纳税人识别号
        this.orderInvoice.setInvoiceAddress(null);// 地址
        this.orderInvoice.setInvoiceTel(null);// 电话
        this.orderInvoice.setInvoiceBankName(null);// 银行名称
        this.orderInvoice.setInvoiceBankNo(null);// 银行支行编号
        this.orderInvoice.setInvoiceSaveFlg(null);
      } else if (InvoiceType.VAT.getValue().equals(this.orderInvoice.getInvoiceType())) {
        // 增值税发票
        this.orderInvoice.setInvoiceCompanyName(StringUtil.parse(reqparam.get("invoiceCompanyName")));// 公司名称
        this.orderInvoice.setInvoiceTaxpayerCode(reqparam.get("invoiceTaxpayerCode"));// 纳税人识别号
        this.orderInvoice.setInvoiceAddress(StringUtil.parse(reqparam.get("invoiceAddress")));// 地址
        this.orderInvoice.setInvoiceTel(reqparam.get("invoiceTel"));// 电话
        this.orderInvoice.setInvoiceBankName(StringUtil.parse(reqparam.get("invoiceBankName")));// 银行名称
        this.orderInvoice.setInvoiceBankNo(reqparam.get("invoiceBankNo"));// 银行支行编号
        this.orderInvoice.setInvoiceSaveFlg(reqparam.get("invoiceSaveFlg"));
        // 清空
        this.orderInvoice.setInvoiceCustomerName(null);// 顾客姓名
      }
    }
    // soukai add 20111227 ob end
    // soukai add 2012/03/27 ob start
    this.setOrderFlg(reqparam.get("orderFlg"));
    // soukai add 2012/03/27 ob end
  }

  public String getDateString(RequestParameter reqparam, String name, int num) {

    String[] yys = reqparam.getAll(name + "_year");
    String[] mms = reqparam.getAll(name + "_month");
    String[] dds = reqparam.getAll(name + "_day");
    if (yys.length > num && mms.length > num && dds.length > num) {
      String dateString = yys[num] + "/" + mms[num] + "/" + dds[num];
      if (dateString.equals("----/--/--")) {
        return "";
      } else if (StringUtil.isNullOrEmptyAnyOf(yys[num], mms[num], dds[num])) {
        return "";
      } else {
        return dateString;
      }
    } else {
      return "";
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020220";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.OrderDetailBean.0");
  }

  /**
   * paymentDateSetButtonFlgを取得します。
   * 
   * @return paymentDateSetButtonFlg
   */
  public boolean isPaymentDateSetButtonFlg() {
    return paymentDateSetButtonFlg;
  }

  /**
   * paymentDateSetButtonFlgを設定します。
   * 
   * @param paymentDateSetButtonFlg
   *          paymentDateSetButtonFlg
   */
  public void setPaymentDateSetButtonFlg(boolean paymentDateSetButtonFlg) {
    this.paymentDateSetButtonFlg = paymentDateSetButtonFlg;
  }

  /**
   * messagesEditModeを取得します。
   * 
   * @return messagesEditMode
   */
  public String getMessagesEditMode() {
    return messagesEditMode;
  }

  /**
   * messagesEditModeを設定します。
   * 
   * @param messagesEditMode
   *          messagesEditMode
   */
  public void setMessagesEditMode(String messagesEditMode) {
    this.messagesEditMode = messagesEditMode;
  }

  /**
   * isNotPointInFullを取得します。
   * 
   * @return isNotPointInFull
   */
  public boolean isNotPointInFull() {
    return isNotPointInFull;
  }

  /**
   * isNotPointInFullを設定します。
   * 
   * @param notPointInFull
   *          isNotPointInFull
   */
  public void setNotPointInFull(boolean notPointInFull) {
    this.isNotPointInFull = notPointInFull;
  }

  public String getUsedCouponPrice() {
    return usedCouponPrice;
  }

  public void setUsedCouponPrice(String usedCouponPrice) {
    this.usedCouponPrice = usedCouponPrice;
  }

  
  /**
   * @return the totalWeight
   */
  public String getTotalWeight() {
    return totalWeight;
  }

  
  /**
   * @param totalWeight the totalWeight to set
   */
  public void setTotalWeight(String totalWeight) {
    this.totalWeight = totalWeight;
  }

  // soukai add 2011/12/27 ob start
  /**
   * orderInvoice取得
   * 
   * @return orderInvoice
   */
  public InvoiceBean getOrderInvoice() {
    return orderInvoice;
  }

  /**
   * orderInvoice设定
   * 
   * @return orderInvoice
   */
  public void setOrderInvoice(InvoiceBean orderInvoice) {
    this.orderInvoice = orderInvoice;
  }

  /**
   * invoiceCommodityNameList取得
   * 
   * @return invoiceCommodityNameList 商品规格LIST
   */
  public List<CodeAttribute> getInvoiceCommodityNameList() {
    return invoiceCommodityNameList;
  }

  /**
   * invoiceCommodityNameList设定
   * 
   * @return invoiceCommodityNameList 商品规格LIST
   */
  public void setInvoiceCommodityNameList(List<CodeAttribute> invoiceCommodityNameList) {
    this.invoiceCommodityNameList = invoiceCommodityNameList;
  }

  /**
   * 受注タイプを取得する
   * 
   * @return the orderType
   */
  public String getOrderType() {
    return orderType;
  }

  /**
   * 受注タイプを設定する
   * 
   * @param orderType
   *          the orderType to set
   */
  public void setOrderType(String orderType) {
    this.orderType = orderType;
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
   * @return the paidPrice
   */
  public BigDecimal getPaidPrice() {
    return paidPrice;
  }

  /**
   * @param paidPrice
   *          the paidPrice to set
   */
  public void setPaidPrice(BigDecimal paidPrice) {
    this.paidPrice = paidPrice;
  }

  /**
   * @return the returnShippingList
   */
  public List<ShippingHeaderBean> getReturnShippingList() {
    return returnShippingList;
  }

  /**
   * @param returnShippingList
   *          the returnShippingList to set
   */
  public void setReturnShippingList(List<ShippingHeaderBean> returnShippingList) {
    this.returnShippingList = returnShippingList;
  }

  public String getOrderFlg() {
    return orderFlg;
  }

  public void setOrderFlg(String orderFlg) {
    this.orderFlg = orderFlg;
  }

  public String getOrderFlgDisplayMode() {
    return orderFlgDisplayMode;
  }

  public void setOrderFlgDisplayMode(String orderFlgDisplayMode) {
    this.orderFlgDisplayMode = orderFlgDisplayMode;
  }

  public List<CodeAttribute> getDeliveryAppointedTimeList() {
    return deliveryAppointedTimeList;
  }

  public void setDeliveryAppointedTimeList(List<CodeAttribute> deliveryAppointedTimeList) {
    this.deliveryAppointedTimeList = deliveryAppointedTimeList;
  }

  public boolean isTmallOrderFlg() {
    return tmallOrderFlg;
  }

  public void setTmallOrderFlg(boolean tmallOrderFlg) {
    this.tmallOrderFlg = tmallOrderFlg;
  }

  
  /**
   * @return the jdOrderFlg
   */
  public boolean isJdOrderFlg() {
    return jdOrderFlg;
  }

  
  /**
   * @param jdOrderFlg the jdOrderFlg to set
   */
  public void setJdOrderFlg(boolean jdOrderFlg) {
    this.jdOrderFlg = jdOrderFlg;
  }

  public List<CodeAttribute> getDeliveryAppointedDateList() {
    return deliveryAppointedDateList;
  }

  public void setDeliveryAppointedDateList(List<CodeAttribute> deliveryAppointedDateList) {
    this.deliveryAppointedDateList = deliveryAppointedDateList;
  }

  /**
   * @return the tmallDiscountPrice
   */
  public String getTmallDiscountPrice() {
    return tmallDiscountPrice;
  }

  /**
   * @param tmallDiscountPrice
   *          the tmallDiscountPrice to set
   */
  public void setTmallDiscountPrice(String tmallDiscountPrice) {
    this.tmallDiscountPrice = tmallDiscountPrice;
  }

  
  /**
   * @return the jdDiscountPrice
   */
  public String getJdDiscountPrice() {
    return jdDiscountPrice;
  }

  
  /**
   * @param jdDiscountPrice the jdDiscountPrice to set
   */
  public void setJdDiscountPrice(String jdDiscountPrice) {
    this.jdDiscountPrice = jdDiscountPrice;
  }

  /**
   * @return the pointConvertPrice
   */
  public String getPointConvertPrice() {
    return pointConvertPrice;
  }

  /**
   * @param pointConvertPrice
   *          the pointConvertPrice to set
   */
  public void setPointConvertPrice(String pointConvertPrice) {
    this.pointConvertPrice = pointConvertPrice;
  }

  public String getTmallTid() {
    return tmallTid;
  }

  public void setTmallTid(String tmallTid) {
    this.tmallTid = tmallTid;
  }

  
  /**
   * @return the jdOrderNo
   */
  public String getJdOrderNo() {
    return jdOrderNo;
  }

  
  /**
   * @param jdOrderNo the jdOrderNo to set
   */
  public void setJdOrderNo(String jdOrderNo) {
    this.jdOrderNo = jdOrderNo;
  }

  public String getMjsDiscount() {
    return mjsDiscount;
  }

  public void setMjsDiscount(String mjsDiscount) {
    this.mjsDiscount = mjsDiscount;
  }

  
  /**
   * @return the authority
   */
  public boolean isAuthority() {
    return authority;
  }

  
  /**
   * @param authority the authority to set
   */
  public void setAuthority(boolean authority) {
    this.authority = authority;
  }

  
  /**
   * @return the orderEditButtonFlg
   */
  public boolean isOrderEditButtonFlg() {
    return orderEditButtonFlg;
  }

  
  /**
   * @param orderEditButtonFlg the orderEditButtonFlg to set
   */
  public void setOrderEditButtonFlg(boolean orderEditButtonFlg) {
    this.orderEditButtonFlg = orderEditButtonFlg;
  }

  
  /**
   * @return the giftCardUsePrice
   */
  public String getGiftCardUsePrice() {
    return giftCardUsePrice;
  }

  
  /**
   * @param giftCardUsePrice the giftCardUsePrice to set
   */
  public void setGiftCardUsePrice(String giftCardUsePrice) {
    this.giftCardUsePrice = giftCardUsePrice;
  }

  
  /**
   * @return the outerCardUsePrice
   */
  public String getOuterCardUsePrice() {
    return outerCardUsePrice;
  }

  
  /**
   * @param outerCardUsePrice the outerCardUsePrice to set
   */
  public void setOuterCardUsePrice(String outerCardUsePrice) {
    this.outerCardUsePrice = outerCardUsePrice;
  }

  // soukai add 2011/12/27 ob start

}
