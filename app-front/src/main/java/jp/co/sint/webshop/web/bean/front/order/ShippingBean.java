package jp.co.sint.webshop.web.bean.front.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.BankCode;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.DeliveryDateType;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceSaveFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.front.order.PaymentSupporterFactory;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020120:お届け先設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingBean extends OrderBean {

  /**用户语言*/
  private String languageCode;
  
  
   
  
  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  
  /**
   * @param languageCode the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }
  /**
   * addSelectCommodityListを返します。
   * 
   * @return the addSelectCommodityList
   */
  public List<CodeAttribute> getAddSelectCommodityList() {
    return addSelectCommodityList;
  }

  //2013/04/09 优惠券对应 ob add start
  /**携帯情報*/
  private VerificationBean verificationBean = new VerificationBean();
  
  /**
   * 取得携帯情報を取得する
   * @return
   */
  public VerificationBean getVerificationBean() {
    return verificationBean;
  }
  
  /**
   * 携帯情報を設定する
   * @param verificationBean
   */
  public void setVerificationBean(VerificationBean verificationBean) {
    this.verificationBean = verificationBean;
  }

  //2013/04/09 优惠券对应 ob add end
  // 2012/11/27 促销对应 ob add start
  private String skuCodeSeleted;
  
  private String inputCouponCode;
  
  /**
   * @return the skuCodeSeleted
   */
  public String getSkuCodeSeleted() {
    return skuCodeSeleted;
  }

  /**
   * @param skuCodeSeleted the skuCodeSeleted to set
   */
  public void setSkuCodeSeleted(String skuCodeSeleted) {
    this.skuCodeSeleted = skuCodeSeleted;
  }
  //2012/11/27 促销对应 ob add end
  /**
   * @return the inputCouponCode
   */
  public String getInputCouponCode() {
    return inputCouponCode;
  }

  /**
   * @param inputCouponCode the inputCouponCode to set
   */
  public void setInputCouponCode(String inputCouponCode) {
    this.inputCouponCode = inputCouponCode;
  }

  /**
   * addSelectCommodityListを設定します。
   * 
   * @param addSelectCommodityList
   *          設定する addSelectCommodityList
   */
  public void setAddSelectCommodityList(List<CodeAttribute> addSelectCommodityList) {
    this.addSelectCommodityList = addSelectCommodityList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    
    LoginInfo loginInfo = WebLoginManager.createFrontNotLoginInfo();
    CustomerService service = ServiceLocator.getCustomerService(loginInfo);
    CustomerCardInfo cardInfo = service.getCustomerCardInfoByCustomerCode(customerCode);
    CustomerCardUseInfo cardUseInfo = service.getCustomerCardUseInfoBycustomerCode(customerCode);
    CustomerCardInfo cardInfoUnable = service.getCustomerCardInfoByCustomerCodeUnable(customerCode);
    GiftCardReturnConfirm confirmPrice = service.getGiftCardReturnConfirm(customerCode);
    
    BigDecimal avalibleMoney = cardInfo.getDenomination().add(confirmPrice.getReturnAmount()).subtract(cardUseInfo.getUseAmount()).subtract(cardInfoUnable.getDenomination());
    if (BigDecimalUtil.equals(avalibleMoney, BigDecimal.ZERO)) {
      setTotalAmount("0.00");
    } else {
      setTotalAmount(avalibleMoney.toString());
    }
    this.setUseAmount(reqparam.get("useAmount"));
    this.setPaymentPassword(reqparam.get("paymentPassword"));
    this.setUseAmountRight(reqparam.get("useAmountRight"));
    Calendar cal = Calendar.getInstance();
    List<String> deliveryDateDesignHoliDay = DIContainer.getWebshopConfig().getDeliveryDateDesignHoliDay();
    int weekday = cal.get(Calendar.DAY_OF_WEEK);
    String yearMonthDay = DateUtil.toDateTimeString(DateUtil.getSysdate(),"yyyy-MM-dd");
    int hour = cal.get(Calendar.HOUR_OF_DAY);
//    int min = cal.get(Calendar.MINUTE);
    String lang = DIContainer.getLocaleContext().getCurrentLanguageCode();
//    boolean timeFlag = true;
//    if ( hour > 14) {
//      timeFlag = false;
//    } else if (hour == 14 &&  min > 30){
//      timeFlag = false;
//    } else {
//      timeFlag = true;
//    }
    //英文地址拦截 周五到周日popup
    if ((weekday == 6 && hour >= 17) || weekday == 7 || weekday == 1 ) {
      checkAddressFlg = true;
    } else {
      checkAddressFlg = false;
    }
    //周五到周日popup的FLG 周二之前留言
    if (StringUtil.hasValue(lang)) {
      if (!lang.equals("zh-cn") && 
            (  ((weekday == 6 && hour >= 17) || weekday == 7 || weekday == 1 ) && !deliveryDateDesignHoliDay.contains(yearMonthDay)   )   ) {
        displayAjaxFlg = true;
      } else {
        displayAjaxFlg = false;
      }
    }
    //节假日前一天popup的FLG 假后两天前留言
    List<String> designHoliDayYesterday = DIContainer.getWebshopConfig().getDesignHoliDayYesterday();
    if (StringUtil.hasValue(lang)) {
      if (!lang.equals("zh-cn") &&  (designHoliDayYesterday.contains(yearMonthDay) && hour >= 17 || deliveryDateDesignHoliDay.contains(yearMonthDay))) {
        popupFlg = true;
      } else {
        popupFlg = false;
      }
    }
    
    reqparam.copy(additionalAddressEdit);
    //2013/04/09 优惠券对应 ob add start
    reqparam.copy(verificationBean);
    //2013/04/04 优惠券对应 ob add end
    additionalAddressEdit.setAdditionalAddressLastName(StringUtil.parse(reqparam.get("additionalAddressLastName")));
    additionalAddressEdit.setAdditionalAddress4(StringUtil.parse(reqparam.get("additionalAddress4")));
    // modify by V10-CH 170 start
    additionalAddressEdit.setAdditionalPhoneNumber1(reqparam.get("additionalPhoneNumber_1"));
    additionalAddressEdit.setAdditionalPhoneNumber2(reqparam.get("additionalPhoneNumber_2"));
    additionalAddressEdit.setAdditionalPhoneNumber3(reqparam.get("additionalPhoneNumber_3"));
    additionalAddressEdit.setAdditionalMobileNumber(reqparam.get("additionalMobileNumber"));
    if (StringUtil.hasValueAllOf(additionalAddressEdit.getAdditionalPhoneNumber1(), additionalAddressEdit
        .getAdditionalPhoneNumber2(), additionalAddressEdit.getAdditionalPhoneNumber3())) {
      additionalAddressEdit.setAdditionalPhoneNumber(StringUtil.joint('-', additionalAddressEdit.getAdditionalPhoneNumber1(),
          additionalAddressEdit.getAdditionalPhoneNumber2(), additionalAddressEdit.getAdditionalPhoneNumber3()));
    } else if (StringUtil.hasValueAllOf(additionalAddressEdit.getAdditionalPhoneNumber1(), additionalAddressEdit
        .getAdditionalPhoneNumber2())) {
      additionalAddressEdit.setAdditionalPhoneNumber(StringUtil.joint('-', additionalAddressEdit.getAdditionalPhoneNumber1(),
          additionalAddressEdit.getAdditionalPhoneNumber2()));
    } else {
      additionalAddressEdit.setAdditionalPhoneNumber("");
    }
    // Add by V10-CH start
    if (StringUtil.hasValue(additionalAddressEdit.getAdditionalMobileNumber())) {
      additionalAddressEdit.setAdditionalMobileNumber(additionalAddressEdit.getAdditionalMobileNumber());
    } else {
      additionalAddressEdit.setAdditionalMobileNumber("");
    }

    String[] addressKeyList = reqparam.getAll("addressNo");
    if (addressKeyList.length > 0) {
      addAddressNoList = new ArrayList<String>();
      addAddressNoList.addAll(Arrays.asList(addressKeyList));
    } else {
      addAddressNoList = null;
    }
    setAddCommodityKey(reqparam.get("commodityKey"));
    setMessage(StringUtil.parse(reqparam.get("message")));

    List<Map<String, String>> shippingDataDelivery = new ArrayList<Map<String, String>>();
    shippingDataDelivery.addAll(reqparam.getListData("oldAddreessNo", "deliveryTypeCode", "shippingShopCode",
        "deliveryAppointedDate", "deliveryAppointedTimeZone"));
    List<Map<String, String>> shippingDataAddress = new ArrayList<Map<String, String>>();
    shippingDataAddress.addAll(reqparam.getListData("shippingAddress", "oldAddreessNoWithAddress"));

    List<Map<String, String>> shippingData = new ArrayList<Map<String, String>>();
    for (Map<String, String> shipping : shippingDataDelivery) {
      for (Map<String, String> address : shippingDataAddress) {
        String oldAddressNo = shipping.get("oldAddreessNo");
        String oldAddressNoWithAddress = address.get("oldAddreessNoWithAddress");
        if (StringUtil.hasValueAllOf(oldAddressNo, oldAddressNoWithAddress) && oldAddressNo.equals(oldAddressNoWithAddress)) {
          shipping.put("shippingAddress", address.get("shippingAddress"));
        }
      }
      shippingData.add(shipping);
    }

    List<Map<String, String>> commodityData = new ArrayList<Map<String, String>>();
    commodityData.addAll(reqparam.getListData("commodityDeliveryTypeCode", "commoditySkuCode", "commodityShippingShopCode",
        "commodityAddreessNo", "commodityAmount", "giftCode"));
    // 出荷情報ループ
    for (ShippingHeaderBean shipping : shippingList) {
      Map<String, String> correctShipping = new HashMap<String, String>();
      for (ShippingDetailBean shippingDetail : shipping.getShippingList()) {
        for (Map<String, String> map : shippingData) {
          String strAddressNo = map.get("oldAddreessNo");
          String strShopCode = map.get("shippingShopCode");
          String strDeliveryTypeCode = map.get("deliveryTypeCode");
          if (shipping.getAddress() != null) {
            if (NumUtil.toString(shipping.getAddress().getAddressNo()).equals(strAddressNo)
                && shippingDetail.getShopCode().equals(strShopCode)
                && shippingDetail.getDeliveryTypeCode().equals(strDeliveryTypeCode)) {
              correctShipping = map;
              break;
            }
          }

        }

        // 出荷先情報設定
        shipping.setNewAddressNo(StringUtil.coalesce(correctShipping.get("shippingAddress"), NumUtil.toString(shipping.getAddress()
            .getAddressNo())));
        shippingDetail.setDeliveryAppointedDate(correctShipping.get("deliveryAppointedDate"));
        
        //取得距离系统时间最近的周二的日期 start
        Calendar tmpcal = Calendar.getInstance();
        int tmpweekday = tmpcal.get(Calendar.DAY_OF_WEEK);
        while(tmpweekday != 3){
          tmpcal.add(Calendar.DAY_OF_MONTH, 1);
          tmpweekday = tmpcal.get(Calendar.DAY_OF_WEEK);
        }
        setNextTuesdayDate(DateUtil.toDateTimeString(tmpcal.getTime(),"yyyy/MM/dd"));
        //取得距离系统时间最近的周二的日期 end
        
        shippingDetail.setDeliveryAppointedTimeZone(correctShipping.get("deliveryAppointedTimeZone"));
      }
    }

    // 20111220 shen add start
    // お支払い情報取得
    PaymentSupporterFactory.createPaymentSuppoerter().copyOrderPaymentMethod(reqparam, orderPayment);
    String invoiceFlg = reqparam.get("invoiceFlg");
    if (StringUtil.isNullOrEmpty(invoiceFlg)) {
      invoiceFlg = InvoiceFlg.NO_NEED.getValue();
    }
    this.orderInvoice.setInvoiceFlg(invoiceFlg);
    this.orderInvoice.setInvoiceCommodityName(reqparam.get("invoiceCommodityName"));
    this.orderInvoice.setInvoiceType(reqparam.get("invoiceType"));
    this.orderInvoice.setInvoiceCustomerName(StringUtil.parse(reqparam.get("invoiceCustomerName")));
    this.orderInvoice.setInvoiceCompanyName(StringUtil.parse(reqparam.get("invoiceCompanyName")));
    this.orderInvoice.setInvoiceTaxpayerCode(reqparam.get("invoiceTaxpayerCode"));
    this.orderInvoice.setInvoiceAddress(StringUtil.parse(reqparam.get("invoiceAddress")));
    this.orderInvoice.setInvoiceTel(reqparam.get("invoiceTel"));
    this.orderInvoice.setInvoiceBankName(StringUtil.parse(reqparam.get("invoiceBankName")));
    this.orderInvoice.setInvoiceBankNo(reqparam.get("invoiceBankNo"));
    String invoiceSaveFlg = reqparam.get("invoiceSaveFlg");
    if (StringUtil.isNullOrEmpty(invoiceSaveFlg)) {
      invoiceSaveFlg = InvoiceSaveFlg.NO_SAVE.getValue();
    }
    this.orderInvoice.setInvoiceSaveFlg(invoiceSaveFlg);
    this.setSelDiscountType(reqparam.get("selDiscountType"));
    // 20140312 hdh add start
    if(StringUtil.isNullOrEmpty(reqparam.get("hiddenSelDiscountType"))){
      this.setHiddenSelDiscountType(reqparam.get("selDiscountType"));
    }else{
      this.setHiddenSelDiscountType(reqparam.get("hiddenSelDiscountType"));
    }
    // 20140312 hdh add end
    this.setSelPersonalCouponCode(reqparam.get("selPersonalCouponCode"));
    this.setPublicCouponCode(reqparam.get("publicCouponCode").trim());
    // 20111220 shen add end

    
    if (!StringUtil.isNullOrEmpty(reqparam.get("deliveryCompanyCode"))) {
      DeliveryCompanyBean dcb = new DeliveryCompanyBean();
      dcb.setDeliveryCompanyNo(reqparam.get("deliveryCompanyCode"));
      this.setSelectedDeliveryCompany(dcb);
    }

    if (StringUtil.hasValue(this.orderInvoice.getInvoiceFlg())) {
      if (InvoiceFlg.fromValue(this.orderInvoice.getInvoiceFlg()) == null) {
        this.orderInvoice.setInvoiceFlg("");
      }
    }
    if (StringUtil.hasValue(this.orderInvoice.getInvoiceType())) {
      if (InvoiceType.fromValue(this.orderInvoice.getInvoiceType()) == null) {
        this.orderInvoice.setInvoiceType("");
      }
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2020120";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.order.ShippingBean.0");
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private boolean displayAjaxFlg = false;
  
  private boolean popupFlg = false;
  
  private boolean checkAddressFlg = false;
  
  /** お支払いユーザコード */
  private String customerCode;

  /** お支払いユーザ情報 */
  private Address paymentUser;

  private AdditionalAddressBean additionalAddressEdit = new AdditionalAddressBean();

  /** 追加できる商品一覧 */
  private List<AddCommodityBean> addCommodityList = new ArrayList<AddCommodityBean>();

  /** 追加できる商品一覧(選択用) */
  private List<CodeAttribute> addSelectCommodityList = new ArrayList<CodeAttribute>();

  /** 顧客のアドレス帳一覧(リスト用) */
  private List<CodeAttribute> addAddressSelectList = new ArrayList<CodeAttribute>();

  /** 顧客のアドレス帳一覧(チェック用) */
  private List<AddAddressBean> addAddressCheckList = new ArrayList<AddAddressBean>();

  /** 配送先一覧 */
  private List<ShippingHeaderBean> shippingList = new ArrayList<ShippingHeaderBean>();

  /** 配送公司関連情報をまとめたもの */
  private List<DeliveryCompanyBean> deliveryCompanyList = new ArrayList<DeliveryCompanyBean>();
  
  private DeliveryCompanyBean selectedDeliveryCompany = new DeliveryCompanyBean();
  
  @Length(400)
  @Metadata(name = "連絡事項", order = 2)
  private String message;

  private boolean availableAddDelivery;
  
  /** 追加する商品情報 */
  @Required
  @Metadata(name = "追加商品", order = 2)
  private String addCommodityKey;

  /** 商品を追加するアドレス番号 */
  @Required
  @Metadata(name = "商品追加先", order = 2)
  private List<String> addAddressNoList;

  private String additionalBlock;

  // 20111220 shen add start
  private List<CodeAttribute> invoiceCommodityNameList = new ArrayList<CodeAttribute>();

  /** お支払い情報 */
  private PaymentMethodBean orderPayment;

  /** 发票信息 */
  private InvoiceBean orderInvoice = new InvoiceBean();

  private List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> personalCouponList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> deliveryDateList = new ArrayList<CodeAttribute>();

  private String selDiscountType;
  
  private String hiddenSelDiscountType;
  
  private String nextTuesdayDate;

  private String selPersonalCouponCode;

  private String publicCouponCode;
  
  private String useAmount;
  
  private String useAmountRight;
  
  private String totalAmount;
  
  private String paymentPassword;

  private String selCodType;

  private String selPrefectureCode;
  
  // 2013/04/21 优惠券对应 ob add start
  private String selCityCode;
  // 2013/04/21 优惠券对应 ob add end

  private String selAreaCode;

  // 20111220 shen add end

  // 20120106 shen add start
  private String addressScript;

  // 20120106 shen add end

  // 20120716 shen add start
  private boolean displayCouponButton;

  private boolean displayPublicCouponButton;

  // 20120716 shen add end

  /**
   * U2020120:お届け先設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AddCommodityBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 追加商品を一意に特定する為のキー(連番) */
    private String commodityKey;

    /** 配送ショップコード */
    private String shopCode;

    /** SKUコード */
    private String skuCode;

    /** 商品名(規格１・規格２) */
    private String displayName;

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
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * skuCodeを設定します。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * displayNameを取得します。
     * 
     * @return the displayName
     */
    public String getDisplayName() {
      return displayName;
    }

    /**
     * displayNameを設定します。
     * 
     * @param displayName
     *          displayName
     */
    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    /**
     * commodityKeyを取得します。
     * 
     * @return commodityKey
     */
    public String getCommodityKey() {
      return commodityKey;
    }

    /**
     * commodityKeyを設定します。
     * 
     * @param commodityKey
     *          commodityKey
     */
    public void setCommodityKey(String commodityKey) {
      this.commodityKey = commodityKey;
    }

  }

  /**
   * U2020120:お届け先設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AddAddressBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** アドレス帳番号 */
    private String addressNo;

    /** お届け先名 */
    private String addressAlias;

    /** 氏名(姓) */
    private String addressLastName;

    /** 氏名(姓)カナ */
    private String addressLastNameKana;

    /** 氏名(名) */
    private String addressFirstName;

    /** 氏名(名)カナ */
    private String addressFirstNameKana;

    /** 郵便番号1 */
    private String postalCode1;

    /** 郵便番号2 */
    private String postalCode2;

    /** 都道府県コード */
    private String prefectureCode;

    /** 住所1(都道府県) */
    private String address1;

    private String cityCode;

    /** 住所2(市区町村) */
    private String address2;

    /** 住所3(町名・番地) */
    private String address3;

    /** 住所4(アパート・マンション・ビル) */
    private String address4;

    /** 電話番号 */
    @Length(20)
    @Phone
    private String phoneNumber;

    /** 手机号码 */
    @Length(11)
    @MobileNumber
    private String mobileNumber;

    // 20120113 shen add start
    private String areaCode;

    // 20120113 shen add end

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
     * addressAliasを返します。
     * 
     * @return the addressAlias
     */
    public String getAddressAlias() {
      return addressAlias;
    }

    /**
     * addressNoを返します。
     * 
     * @return the addressNo
     */
    public String getAddressNo() {
      return addressNo;
    }

    /**
     * addressAliasを設定します。
     * 
     * @param addressAlias
     *          設定する addressAlias
     */
    public void setAddressAlias(String addressAlias) {
      this.addressAlias = addressAlias;
    }

    /**
     * addressNoを設定します。
     * 
     * @param addressNo
     *          設定する addressNo
     */
    public void setAddressNo(String addressNo) {
      this.addressNo = addressNo;
    }

    /**
     * address2を返します。
     * 
     * @return the address2
     */
    public String getAddress2() {
      return address2;
    }

    /**
     * address3を返します。
     * 
     * @return the address3
     */
    public String getAddress3() {
      return address3;
    }

    /**
     * address4を返します。
     * 
     * @return the address4
     */
    public String getAddress4() {
      return address4;
    }

    /**
     * addressFirstNameを返します。
     * 
     * @return the addressFirstName
     */
    public String getAddressFirstName() {
      return addressFirstName;
    }

    /**
     * addressLastNameを返します。
     * 
     * @return the addressLastName
     */
    public String getAddressLastName() {
      return addressLastName;
    }

    /**
     * addressFirstNameを設定します。
     * 
     * @param addressFirstName
     *          設定する addressFirstName
     */
    public void setAddressFirstName(String addressFirstName) {
      this.addressFirstName = addressFirstName;
    }

    /**
     * addressLastNameを設定します。
     * 
     * @param addressLastName
     *          設定する addressLastName
     */
    public void setAddressLastName(String addressLastName) {
      this.addressLastName = addressLastName;
    }

    /**
     * address2を設定します。
     * 
     * @param address2
     *          設定する address2
     */
    public void setAddress2(String address2) {
      this.address2 = address2;
    }

    /**
     * address3を設定します。
     * 
     * @param address3
     *          設定する address3
     */
    public void setAddress3(String address3) {
      this.address3 = address3;
    }

    /**
     * address4を設定します。
     * 
     * @param address4
     *          設定する address4
     */
    public void setAddress4(String address4) {
      this.address4 = address4;
    }

    /**
     * postalCode1を返します。
     * 
     * @return the postalCode1
     */
    public String getPostalCode1() {
      return postalCode1;
    }

    /**
     * postalCode2を返します。
     * 
     * @return the postalCode2
     */
    public String getPostalCode2() {
      return postalCode2;
    }

    /**
     * postalCode1を設定します。
     * 
     * @param postalCode1
     *          設定する postalCode1
     */
    public void setPostalCode1(String postalCode1) {
      this.postalCode1 = postalCode1;
    }

    /**
     * postalCode2を設定します。
     * 
     * @param postalCode2
     *          設定する postalCode2
     */
    public void setPostalCode2(String postalCode2) {
      this.postalCode2 = postalCode2;
    }

    /**
     * address1を返します。
     * 
     * @return the address1
     */
    public String getAddress1() {
      return address1;
    }

    /**
     * address1を設定します。
     * 
     * @param address1
     *          設定する address1
     */
    public void setAddress1(String address1) {
      this.address1 = address1;
    }

    /**
     * addressLastNameKanaを取得します。
     * 
     * @return addressLastNameKana
     */
    public String getAddressLastNameKana() {
      return addressLastNameKana;
    }

    /**
     * addressLastNameKanaを設定します。
     * 
     * @param addressLastNameKana
     *          addressLastNameKana
     */
    public void setAddressLastNameKana(String addressLastNameKana) {
      this.addressLastNameKana = addressLastNameKana;
    }

    /**
     * addressFirstNameKanaを取得します。
     * 
     * @return addressFirstNameKana
     */
    public String getAddressFirstNameKana() {
      return addressFirstNameKana;
    }

    /**
     * addressFirstNameKanaを設定します。
     * 
     * @param addressFirstNameKana
     *          addressFirstNameKana
     */
    public void setAddressFirstNameKana(String addressFirstNameKana) {
      this.addressFirstNameKana = addressFirstNameKana;
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

    public String getCityCode() {
      return cityCode;
    }

    public void setCityCode(String cityCode) {
      this.cityCode = cityCode;
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
   * U2020120:お届け先設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AdditionalAddressBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(20)
    @Metadata(name = "お届け先名", order = 1)
    private String additionalAddressAlias;

    @Required
    @Length(20)
    @Metadata(name = "氏名(姓)", order = 2)
    private String additionalAddressLastName;

    @Required
    @Length(20)
    @Metadata(name = "氏名(名)", order = 3)
    private String additionalAddressFirstName;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(姓)", order = 4)
    private String additionalAddressLastNameKana;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(名)", order = 5)
    private String additionalAddressFirstNameKana;

    @Required
    @Length(6)
    @PostalCode
    @Metadata(name = "郵便番号", order = 6)
    private String additionalPostalCode;

    @Required
    @Length(2)
    @AlphaNum2
    @Metadata(name = "省份", order = 7)
    private String additionalPrefectureCode;

    @Required
    @Length(3)
    @Metadata(name = "城市", order = 8)
    private String additionalCityCode;

    // 20120106 shen add start
    @Length(4)
    @Metadata(name = "区县", order = 9)
    private String additionalAreaCode;

    // 20120106 shen add end

    // 20120106 shen delete start
    /*
     * // @Required
     * @Length(50)
     * @Metadata(name = "住所2(市区町村)") private String additionalAddress2;
     * @Required
     * @Length(50)
     * @Metadata(name = "住所3(町名・番地)", order = 10) private String
     * additionalAddress3;
     */
    // 20120106 shen delete end
    // 20120106 shen add start
    @Required
    // 20120106 shen add end
    @Length(100)
    @Metadata(name = "街道地址", order = 10)
    private String additionalAddress4;

    /** 電話番号1 */
    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号1")
    private String additionalPhoneNumber1;

    /** 電話番号2 */
    @Length(10)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号2")
    private String additionalPhoneNumber2;

    /** 電話番号3 */
    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号3")
    private String additionalPhoneNumber3;

    @Length(24)
    @Phone
    @Metadata(name = "電話番号")
    private String additionalPhoneNumber;

    // Add by V10-CH start
    @Length(11)
    @MobileNumber
    @Metadata(name = "手机号码")
    private String additionalMobileNumber;

    // Add by V10-CH end

    // modify by V10-CH 170 start
    private List<CodeAttribute> additionalCityList = new ArrayList<CodeAttribute>();

    // modify by V10-CH 170 end

    // 20120106 shen add start
    private List<CodeAttribute> additionalPrefectureList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> additionalAreaList = new ArrayList<CodeAttribute>();

    // 20120106 shen add end

    /**
     * additionalPhoneNumberを返します。
     * 
     * @return the additionalPhoneNumber
     */
    public String getAdditionalPhoneNumber() {
      return additionalPhoneNumber;
    }

    /**
     * additionalPhoneNumberを設定します。
     * 
     * @param additionalPhoneNumber
     *          設定する additionalPhoneNumber
     */
    public void setAdditionalPhoneNumber(String additionalPhoneNumber) {
      this.additionalPhoneNumber = additionalPhoneNumber;
    }

    /**
     * additionalAddress4を取得します。
     * 
     * @return additionalAddress4
     */
    public String getAdditionalAddress4() {
      return additionalAddress4;
    }

    /**
     * additionalAddress4を設定します。
     * 
     * @param additionalAddress4
     *          additionalAddress4
     */
    public void setAdditionalAddress4(String additionalAddress4) {
      this.additionalAddress4 = additionalAddress4;
    }

    /**
     * additionalAddressAliasを取得します。
     * 
     * @return additionalAddressAlias
     */
    public String getAdditionalAddressAlias() {
      return additionalAddressAlias;
    }

    /**
     * additionalAddressAliasを設定します。
     * 
     * @param additionalAddressAlias
     *          additionalAddressAlias
     */
    public void setAdditionalAddressAlias(String additionalAddressAlias) {
      this.additionalAddressAlias = additionalAddressAlias;
    }

    /**
     * additionalAddressFirstNameを取得します。
     * 
     * @return additionalAddressFirstName
     */
    public String getAdditionalAddressFirstName() {
      return additionalAddressFirstName;
    }

    /**
     * additionalAddressFirstNameを設定します。
     * 
     * @param additionalAddressFirstName
     *          additionalAddressFirstName
     */
    public void setAdditionalAddressFirstName(String additionalAddressFirstName) {
      this.additionalAddressFirstName = additionalAddressFirstName;
    }

    /**
     * additionalAddressFirstNameKanaを取得します。
     * 
     * @return additionalAddressFirstNameKana
     */
    public String getAdditionalAddressFirstNameKana() {
      return additionalAddressFirstNameKana;
    }

    /**
     * additionalAddressFirstNameKanaを設定します。
     * 
     * @param additionalAddressFirstNameKana
     *          additionalAddressFirstNameKana
     */
    public void setAdditionalAddressFirstNameKana(String additionalAddressFirstNameKana) {
      this.additionalAddressFirstNameKana = additionalAddressFirstNameKana;
    }

    /**
     * additionalAddressLastNameを取得します。
     * 
     * @return additionalAddressLastName
     */
    public String getAdditionalAddressLastName() {
      return additionalAddressLastName;
    }

    /**
     * additionalAddressLastNameを設定します。
     * 
     * @param additionalAddressLastName
     *          additionalAddressLastName
     */
    public void setAdditionalAddressLastName(String additionalAddressLastName) {
      this.additionalAddressLastName = additionalAddressLastName;
    }

    /**
     * additionalAddressLastNameKanaを取得します。
     * 
     * @return additionalAddressLastNameKana
     */
    public String getAdditionalAddressLastNameKana() {
      return additionalAddressLastNameKana;
    }

    /**
     * additionalAddressLastNameKanaを設定します。
     * 
     * @param additionalAddressLastNameKana
     *          additionalAddressLastNameKana
     */
    public void setAdditionalAddressLastNameKana(String additionalAddressLastNameKana) {
      this.additionalAddressLastNameKana = additionalAddressLastNameKana;
    }

    /**
     * additionalPhoneNumber1を返します。
     * 
     * @return the additionalPhoneNumber1
     */
    public String getAdditionalPhoneNumber1() {
      return additionalPhoneNumber1;
    }

    /**
     * additionalPhoneNumber2を返します。
     * 
     * @return the additionalPhoneNumber2
     */
    public String getAdditionalPhoneNumber2() {
      return additionalPhoneNumber2;
    }

    /**
     * additionalPhoneNumber3を返します。
     * 
     * @return the additionalPhoneNumber3
     */
    public String getAdditionalPhoneNumber3() {
      return additionalPhoneNumber3;
    }

    /**
     * additionalPhoneNumber1を設定します。
     * 
     * @param additionalPhoneNumber1
     *          設定する additionalPhoneNumber1
     */
    public void setAdditionalPhoneNumber1(String additionalPhoneNumber1) {
      this.additionalPhoneNumber1 = additionalPhoneNumber1;
    }

    /**
     * additionalPhoneNumber2を設定します。
     * 
     * @param additionalPhoneNumber2
     *          設定する additionalPhoneNumber2
     */
    public void setAdditionalPhoneNumber2(String additionalPhoneNumber2) {
      this.additionalPhoneNumber2 = additionalPhoneNumber2;
    }

    /**
     * additionalPhoneNumber3を設定します。
     * 
     * @param additionalPhoneNumber3
     *          設定する additionalPhoneNumber3
     */
    public void setAdditionalPhoneNumber3(String additionalPhoneNumber3) {
      this.additionalPhoneNumber3 = additionalPhoneNumber3;
    }

    /**
     * additionalPostalCodeを取得します。
     * 
     * @return additionalPostalCode
     */
    public String getAdditionalPostalCode() {
      return additionalPostalCode;
    }

    /**
     * additionalPostalCodeを設定します。
     * 
     * @param additionalPostalCode
     *          additionalPostalCode
     */
    public void setAdditionalPostalCode(String additionalPostalCode) {
      this.additionalPostalCode = additionalPostalCode;
    }

    /**
     * additionalPrefectureCodeを取得します。
     * 
     * @return additionalPrefectureCode
     */
    public String getAdditionalPrefectureCode() {
      return additionalPrefectureCode;
    }

    /**
     * additionalPrefectureCodeを設定します。
     * 
     * @param additionalPrefectureCode
     *          additionalPrefectureCode
     */
    public void setAdditionalPrefectureCode(String additionalPrefectureCode) {
      this.additionalPrefectureCode = additionalPrefectureCode;
    }

    public String getAdditionalCityCode() {
      return additionalCityCode;
    }

    public void setAdditionalCityCode(String additionalCityCode) {
      this.additionalCityCode = additionalCityCode;
    }

    /**
     * additionalMobileNumberを取得します。
     * 
     * @return additionalMobileNumber additionalMobileNumber
     */
    public String getAdditionalMobileNumber() {
      return additionalMobileNumber;
    }

    /**
     * additionalMobileNumberを設定します。
     * 
     * @param additionalMobileNumber
     *          additionalMobileNumber
     */
    public void setAdditionalMobileNumber(String additionalMobileNumber) {
      this.additionalMobileNumber = additionalMobileNumber;
    }

    public List<CodeAttribute> getAdditionalCityList() {
      return additionalCityList;
    }

    public void setAdditionalCityList(List<CodeAttribute> additionalCityList) {
      this.additionalCityList = additionalCityList;
    }

    /**
     * @return the additionalAreaCode
     */
    public String getAdditionalAreaCode() {
      return additionalAreaCode;
    }

    /**
     * @param additionalAreaCode
     *          the additionalAreaCode to set
     */
    public void setAdditionalAreaCode(String additionalAreaCode) {
      this.additionalAreaCode = additionalAreaCode;
    }

    /**
     * @return the additionalPrefectureList
     */
    public List<CodeAttribute> getAdditionalPrefectureList() {
      return additionalPrefectureList;
    }

    /**
     * @param additionalPrefectureList
     *          the additionalPrefectureList to set
     */
    public void setAdditionalPrefectureList(List<CodeAttribute> additionalPrefectureList) {
      this.additionalPrefectureList = additionalPrefectureList;
    }

    /**
     * @return the additionalAreaList
     */
    public List<CodeAttribute> getAdditionalAreaList() {
      return additionalAreaList;
    }

    /**
     * @param additionalAreaList
     *          the additionalAreaList to set
     */
    public void setAdditionalAreaList(List<CodeAttribute> additionalAreaList) {
      this.additionalAreaList = additionalAreaList;
    }

  }

  /**
   * U2020120:お届け先設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShippingHeaderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 表示用アドレス情報 */
    private Address address;

    private List<ShippingDetailBean> shippingList = new ArrayList<ShippingDetailBean>();

    // private Long totalShippingCharge; // 10.1.4 10139 削除

    @Required
    private String newAddressNo;

    private boolean displayDeleteButton;

    /**
     * displayDeleteButtonを取得します。
     * 
     * @return displayDeleteButton
     */
    public boolean isDisplayDeleteButton() {
      return displayDeleteButton;
    }

    /**
     * displayDeleteButtonを設定します。
     * 
     * @param displayDeleteButton
     *          displayDeleteButton
     */
    public void setDisplayDeleteButton(boolean displayDeleteButton) {
      this.displayDeleteButton = displayDeleteButton;
    }

    /**
     * addressを取得します。
     * 
     * @return address
     */
    public Address getAddress() {
      return address;
    }

    /**
     * addressを設定します。
     * 
     * @param address
     *          address
     */
    public void setAddress(Address address) {
      this.address = address;
    }

    /**
     * newAddressNoを取得します。
     * 
     * @return newAddressNo
     */
    public String getNewAddressNo() {
      return newAddressNo;
    }

    /**
     * newAddressNoを設定します。
     * 
     * @param newAddressNo
     *          newAddressNo
     */
    public void setNewAddressNo(String newAddressNo) {
      this.newAddressNo = newAddressNo;
    }

    /**
     * shippingListを取得します。
     * 
     * @return shippingList
     */
    public List<ShippingDetailBean> getShippingList() {
      return shippingList;
    }

    /**
     * shippingListを設定します。
     * 
     * @param shippingList
     *          shippingList
     */
    public void setShippingList(List<ShippingDetailBean> shippingList) {
      this.shippingList = shippingList;
    }

    /**
     * totalShippingChargeを取得します。
     * 
     * @return totalShippingCharge
     */
    public BigDecimal getTotalShippingCharge() {
      BigDecimal total = BigDecimal.ZERO;
      for (ShippingDetailBean sdb : this.getShippingList()) {
        total = total.add(NumUtil.coalesce(sdb.getShippingCharge(), BigDecimal.ZERO));
      }
      return total;
    }


  }

  /**
   * U2020120:お届け先設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShippingDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String shopName;

    private String deliveryRemark;

    private String deliveryTypeCode;

    private String deliveryTypeName;

    private BigDecimal shippingCharge;

    private List<ShippingCommodityBean> commodityList;
    
    // 2012/11/26 促销对应 ob add start
    private List<ShippingCommodityBean> acceptedGiftList = new ArrayList<ShippingCommodityBean>();
    // 2012/11/26 促销对应 ob add end

    private List<CodeAttribute> deliveryAppointedDateList = new ArrayList<CodeAttribute>();

    private String deliveryAppointedDate;

    private List<CodeAttribute> deliveryAppointedTimeZoneList = new ArrayList<CodeAttribute>();

    private String deliveryAppointedTimeZone;

    // 20111228 shen add start
    private String codType;

    // 20111228 shen add end

    /**
     * U2020120:お届け先設定のサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class ShippingCommodityBean implements Serializable {

      private static final long serialVersionUID = 1L;

      private String skuCode;

      private String commodityCode;

      private String commodityName;

      private String commodityDetail1Name;

      private String commodityDetail2Name;

      private boolean campaign;

      private BigDecimal unitPrice;

      private BigDecimal retailPrice;

      private boolean discount;

      private boolean reserve;

      private String campaignCode;

      private String campaignName;
      
      private String isDiscountCommodity;
      
      private boolean timeDiscount;

      /** 商品数量 */
      @Required
      @Digit
      @Range(max = 9999, min = 1)
      @Length(4)
      @Metadata(name = "数量", order = 1)
      private String commodityAmount;

      /** ギフトコード */
      @Length(16)
      @AlphaNum2
      @Metadata(name = "ギフトコード", order = 2)
      private String giftCode;

      /** 使用可能なギフトリスト */
      private List<CodeAttribute> giftList = new ArrayList<CodeAttribute>();

      /** 小計 */
      private BigDecimal subTotalPrice;

      private boolean displayDeleteButton;

      // 20120104 shen add start
      private BigDecimal weight;

      // 20120104 shen add end
      
      // 2012/11/26 促销对应 ob add start
      private BigDecimal retailPriceExceptCoupon;
      
      private List<CompositionBean> compositionList = new ArrayList<CompositionBean>();
      
      private List<GiftBean> campaignGiftList = new ArrayList<GiftBean>();
      
      private List<CouponDetailBean> couponList = new ArrayList<CouponDetailBean>();
      
      private String multipleCampaignCode;
      
      private String multipleCampaignName;
      
      //折扣券编号
      private String usedCouponCode;
      
      // 使用折扣券后的优惠金额
      private BigDecimal usedCouponPrice;
      
      // 折扣券的折扣类型
      private Long usedCouponType;
      
      //折扣券活动名称
      private String usedCouponName;
      
      private boolean gift;
      
      private boolean setCommodity;
      
      private String skuCodeOfSet;
      
      private String displayCouponMode = "0";
      
      private String originalCommodityCode;
      
      private long combinationAmount;

      
      /**
       * @return the compositionList
       */
      public List<CompositionBean> getCompositionList() {
        return compositionList;
      }

      /**
       * @param compositionList the compositionList to set
       */
      public void setCompositionList(List<CompositionBean> compositionList) {
        this.compositionList = compositionList;
      }

      /**
       * @return the campaignGiftList
       */
      public List<GiftBean> getCampaignGiftList() {
        return campaignGiftList;
      }

      /**
       * @param campaignGiftList the campaignGiftList to set
       */
      public void setCampaignGiftList(List<GiftBean> campaignGiftList) {
        this.campaignGiftList = campaignGiftList;
      }

      /**
       * @return the couponList
       */
      public List<CouponDetailBean> getCouponList() {
        return couponList;
      }

      /**
       * @param couponList the couponList to set
       */
      public void setCouponList(List<CouponDetailBean> couponList) {
        this.couponList = couponList;
      }

      /**
       * @return the multipleCampaignCode
       */
      public String getMultipleCampaignCode() {
        return multipleCampaignCode;
      }

      /**
       * @param multipleCampaignCode the multipleCampaignCode to set
       */
      public void setMultipleCampaignCode(String multipleCampaignCode) {
        this.multipleCampaignCode = multipleCampaignCode;
      }

      /**
       * @return the multipleCampaignName
       */
      public String getMultipleCampaignName() {
        return multipleCampaignName;
      }

      /**
       * @param multipleCampaignName the multipleCampaignName to set
       */
      public void setMultipleCampaignName(String multipleCampaignName) {
        this.multipleCampaignName = multipleCampaignName;
      }

      /**
       * @return the usedCouponCode
       */
      public String getUsedCouponCode() {
        return usedCouponCode;
      }

      /**
       * @param usedCouponCode the usedCouponCode to set
       */
      public void setUsedCouponCode(String usedCouponCode) {
        this.usedCouponCode = usedCouponCode;
      }

      /**
       * @return the usedCouponPrice
       */
      public BigDecimal getUsedCouponPrice() {
        return usedCouponPrice;
      }

      /**
       * @param usedCouponPrice the usedCouponPrice to set
       */
      public void setUsedCouponPrice(BigDecimal usedCouponPrice) {
        this.usedCouponPrice = usedCouponPrice;
      }

      /**
       * @return the usedCouponType
       */
      public Long getUsedCouponType() {
        return usedCouponType;
      }

      /**
       * @param usedCouponType the usedCouponType to set
       */
      public void setUsedCouponType(Long usedCouponType) {
        this.usedCouponType = usedCouponType;
      }

      /**
       * @return the usedCouponName
       */
      public String getUsedCouponName() {
        return usedCouponName;
      }

      /**
       * @param usedCouponName the usedCouponName to set
       */
      public void setUsedCouponName(String usedCouponName) {
        this.usedCouponName = usedCouponName;
      }

      /**
       * @return the gift
       */
      public boolean isGift() {
        return gift;
      }

      /**
       * @param gift the gift to set
       */
      public void setGift(boolean gift) {
        this.gift = gift;
      }

      /**
       * @return the setCommodity
       */
      public boolean isSetCommodity() {
        return setCommodity;
      }

      /**
       * @param setCommodity the setCommodity to set
       */
      public void setSetCommodity(boolean setCommodity) {
        this.setCommodity = setCommodity;
      }

      /**
       * @return the skuCodeOfSet
       */
      public String getSkuCodeOfSet() {
        return skuCodeOfSet;
      }

      /**
       * @param skuCodeOfSet the skuCodeOfSet to set
       */
      public void setSkuCodeOfSet(String skuCodeOfSet) {
        this.skuCodeOfSet = skuCodeOfSet;
      }

      /**
       * @return the displayCouponMode
       */
      public String getDisplayCouponMode() {
        return displayCouponMode;
      }

      /**
       * @param displayCouponMode the displayCouponMode to set
       */
      public void setDisplayCouponMode(String displayCouponMode) {
        this.displayCouponMode = displayCouponMode;
      }


      public static class CompositionBean implements Serializable {

        private static final long serialVersionUID = 1L;
        
        private String shopCode;

        private String commodityCode;

        private String skuCode;

        private String commodityName;

        private String standardDetail1Name;

        private String standardDetail2Name;

        /**
         * @return the shopCode
         */
        public String getShopCode() {
          return shopCode;
        }

        /**
         * @param shopCode the shopCode to set
         */
        public void setShopCode(String shopCode) {
          this.shopCode = shopCode;
        }

        /**
         * @return the commodityCode
         */
        public String getCommodityCode() {
          return commodityCode;
        }

        /**
         * @param commodityCode the commodityCode to set
         */
        public void setCommodityCode(String commodityCode) {
          this.commodityCode = commodityCode;
        }

        /**
         * @return the skuCode
         */
        public String getSkuCode() {
          return skuCode;
        }

        /**
         * @param skuCode the skuCode to set
         */
        public void setSkuCode(String skuCode) {
          this.skuCode = skuCode;
        }

        /**
         * @return the commodityName
         */
        public String getCommodityName() {
          return commodityName;
        }

        /**
         * @param commodityName the commodityName to set
         */
        public void setCommodityName(String commodityName) {
          this.commodityName = commodityName;
        }

        /**
         * @return the standardDetail1Name
         */
        public String getStandardDetail1Name() {
          return standardDetail1Name;
        }

        /**
         * @param standardDetail1Name the standardDetail1Name to set
         */
        public void setStandardDetail1Name(String standardDetail1Name) {
          this.standardDetail1Name = standardDetail1Name;
        }

        /**
         * @return the standardDetail2Name
         */
        public String getStandardDetail2Name() {
          return standardDetail2Name;
        }

        /**
         * @param standardDetail2Name the standardDetail2Name to set
         */
        public void setStandardDetail2Name(String standardDetail2Name) {
          this.standardDetail2Name = standardDetail2Name;
        }
        
      }
     
      public static class GiftBean implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private String shopCode;

        private String giftCode;

        private String giftName;

        private String standardDetail1Name;

        private String standardDetail2Name;

        private BigDecimal giftSalesPrice;
        
        private String giftAmount;

        private BigDecimal weight;
        
        private String campaignCode;

        private String campaignName;
        
        private BigDecimal subTotal;

        /**
         * @return the shopCode
         */
        public String getShopCode() {
          return shopCode;
        }

        /**
         * @param shopCode the shopCode to set
         */
        public void setShopCode(String shopCode) {
          this.shopCode = shopCode;
        }

        /**
         * @return the giftCode
         */
        public String getGiftCode() {
          return giftCode;
        }

        /**
         * @param giftCode the giftCode to set
         */
        public void setGiftCode(String giftCode) {
          this.giftCode = giftCode;
        }

        /**
         * @return the giftName
         */
        public String getGiftName() {
          return giftName;
        }

        /**
         * @param giftName the giftName to set
         */
        public void setGiftName(String giftName) {
          this.giftName = giftName;
        }

        /**
         * @return the standardDetail1Name
         */
        public String getStandardDetail1Name() {
          return standardDetail1Name;
        }

        /**
         * @param standardDetail1Name the standardDetail1Name to set
         */
        public void setStandardDetail1Name(String standardDetail1Name) {
          this.standardDetail1Name = standardDetail1Name;
        }

        /**
         * @return the standardDetail2Name
         */
        public String getStandardDetail2Name() {
          return standardDetail2Name;
        }

        /**
         * @param standardDetail2Name the standardDetail2Name to set
         */
        public void setStandardDetail2Name(String standardDetail2Name) {
          this.standardDetail2Name = standardDetail2Name;
        }

        /**
         * @return the giftSalesPrice
         */
        public BigDecimal getGiftSalesPrice() {
          return giftSalesPrice;
        }

        /**
         * @param giftSalesPrice the giftSalesPrice to set
         */
        public void setGiftSalesPrice(BigDecimal giftSalesPrice) {
          this.giftSalesPrice = giftSalesPrice;
        }

        /**
         * @return the giftAmount
         */
        public String getGiftAmount() {
          return giftAmount;
        }

        /**
         * @param giftAmount the giftAmount to set
         */
        public void setGiftAmount(String giftAmount) {
          this.giftAmount = giftAmount;
        }

        /**
         * @return the weight
         */
        public BigDecimal getWeight() {
          return weight;
        }

        /**
         * @param weight the weight to set
         */
        public void setWeight(BigDecimal weight) {
          this.weight = weight;
        }

        /**
         * @return the campaignCode
         */
        public String getCampaignCode() {
          return campaignCode;
        }

        /**
         * @param campaignCode the campaignCode to set
         */
        public void setCampaignCode(String campaignCode) {
          this.campaignCode = campaignCode;
        }

        /**
         * @return the campaignName
         */
        public String getCampaignName() {
          return campaignName;
        }

        /**
         * @param campaignName the campaignName to set
         */
        public void setCampaignName(String campaignName) {
          this.campaignName = campaignName;
        }

        /**
         * @return the subTotal
         */
        public BigDecimal getSubTotal() {
          return subTotal;
        }

        /**
         * @param subTotal the subTotal to set
         */
        public void setSubTotal(BigDecimal subTotal) {
          this.subTotal = subTotal;
        }
      }
      
      public static class CouponDetailBean implements Serializable {
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String campaignCode;
        
        private String campaignName;
      
        private Long discountType;
        
        private String couponValue;

        /**
         * @return the campaignCode
         */
        public String getCampaignCode() {
          return campaignCode;
        }

        /**
         * @param campaignCode the campaignCode to set
         */
        public void setCampaignCode(String campaignCode) {
          this.campaignCode = campaignCode;
        }

        /**
         * @return the campaignName
         */
        public String getCampaignName() {
          return campaignName;
        }

        /**
         * @param campaignName the campaignName to set
         */
        public void setCampaignName(String campaignName) {
          this.campaignName = campaignName;
        }

        /**
         * @return the discountType
         */
        public Long getDiscountType() {
          return discountType;
        }

        /**
         * @param discountType the discountType to set
         */
        public void setDiscountType(Long discountType) {
          this.discountType = discountType;
        }

        /**
         * @return the couponValue
         */
        public String getCouponValue() {
          return couponValue;
        }

        /**
         * @param couponValue the couponValue to set
         */
        public void setCouponValue(String couponValue) {
          this.couponValue = couponValue;
        }
      }
      // 2012/11/26 促销对应 ob add end

      /**
       * campaignNameを取得します。
       * 
       * @return campaignName
       */
      public String getCampaignName() {
        return campaignName;
      }

      /**
       * campaignNameを設定します。
       * 
       * @param campaignName
       *          campaignName
       */
      public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
      }

      /**
       * campaignを取得します。
       * 
       * @return campaign
       */
      public boolean isCampaign() {
        return campaign;
      }

      /**
       * campaignを設定します。
       * 
       * @param campaign
       *          campaign
       */
      public void setCampaign(boolean campaign) {
        this.campaign = campaign;
      }

      /**
       * commodityAmountを取得します。
       * 
       * @return commodityAmount
       */
      public String getCommodityAmount() {
        return commodityAmount;
      }

      /**
       * commodityAmountを設定します。
       * 
       * @param commodityAmount
       *          commodityAmount
       */
      public void setCommodityAmount(String commodityAmount) {
        this.commodityAmount = commodityAmount;
      }

      /**
       * commodityCodeを取得します。
       * 
       * @return commodityCode
       */
      public String getCommodityCode() {
        return commodityCode;
      }

      /**
       * commodityCodeを設定します。
       * 
       * @param commodityCode
       *          commodityCode
       */
      public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
      }

      /**
       * commodityDetail1Nameを取得します。
       * 
       * @return commodityDetail1Name
       */
      public String getCommodityDetail1Name() {
        return commodityDetail1Name;
      }

      /**
       * commodityDetail1Nameを設定します。
       * 
       * @param commodityDetail1Name
       *          commodityDetail1Name
       */
      public void setCommodityDetail1Name(String commodityDetail1Name) {
        this.commodityDetail1Name = commodityDetail1Name;
      }

      /**
       * commodityDetail2Nameを取得します。
       * 
       * @return commodityDetail2Name
       */
      public String getCommodityDetail2Name() {
        return commodityDetail2Name;
      }

      /**
       * commodityDetail2Nameを設定します。
       * 
       * @param commodityDetail2Name
       *          commodityDetail2Name
       */
      public void setCommodityDetail2Name(String commodityDetail2Name) {
        this.commodityDetail2Name = commodityDetail2Name;
      }

      /**
       * commodityNameを取得します。
       * 
       * @return commodityName
       */
      public String getCommodityName() {
        return commodityName;
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
       * discountを取得します。
       * 
       * @return discount
       */
      public boolean isDiscount() {
        return discount;
      }

      /**
       * discountを設定します。
       * 
       * @param discount
       *          discount
       */
      public void setDiscount(boolean discount) {
        this.discount = discount;
      }

      /**
       * giftCodeを取得します。
       * 
       * @return giftCode
       */
      public String getGiftCode() {
        return giftCode;
      }

      /**
       * giftCodeを設定します。
       * 
       * @param giftCode
       *          giftCode
       */
      public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
      }

      /**
       * giftListを取得します。
       * 
       * @return giftList
       */
      public List<CodeAttribute> getGiftList() {
        return giftList;
      }

      /**
       * giftListを設定します。
       * 
       * @param giftList
       *          giftList
       */
      public void setGiftList(List<CodeAttribute> giftList) {
        this.giftList = giftList;
      }

      /**
       * retailPriceを取得します。
       * 
       * @return retailPrice
       */
      public BigDecimal getRetailPrice() {
        return retailPrice;
      }

      /**
       * retailPriceを設定します。
       * 
       * @param retailPrice
       *          retailPrice
       */
      public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
      }

      /**
       * @return the retailPriceExceptCoupon
       */
      public BigDecimal getRetailPriceExceptCoupon() {
        return retailPriceExceptCoupon;
      }

      /**
       * @param retailPriceExceptCoupon the retailPriceExceptCoupon to set
       */
      public void setRetailPriceExceptCoupon(BigDecimal retailPriceExceptCoupon) {
        this.retailPriceExceptCoupon = retailPriceExceptCoupon;
      }

      /**
       * skuCodeを取得します。
       * 
       * @return skuCode
       */
      public String getSkuCode() {
        return skuCode;
      }

      /**
       * skuCodeを設定します。
       * 
       * @param skuCode
       *          skuCode
       */
      public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
      }

      /**
       * subTotalPriceを取得します。
       * 
       * @return subTotalPrice
       */
      public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
      }

      /**
       * subTotalPriceを設定します。
       * 
       * @param subTotalPrice
       *          subTotalPrice
       */
      public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
      }

      /**
       * unitPriceを取得します。
       * 
       * @return unitPrice
       */
      public BigDecimal getUnitPrice() {
        return unitPrice;
      }

      /**
       * unitPriceを設定します。
       * 
       * @param unitPrice
       *          unitPrice
       */
      public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
      }

      /**
       * campaignCodeを取得します。
       * 
       * @return campaignCode
       */
      public String getCampaignCode() {
        return campaignCode;
      }

      /**
       * campaignCodeを設定します。
       * 
       * @param campaignCode
       *          campaignCode
       */
      public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
      }

      /**
       * displayDeleteButtonを取得します。
       * 
       * @return displayDeleteButton
       */
      public boolean isDisplayDeleteButton() {
        return displayDeleteButton;
      }

      /**
       * displayDeleteButtonを設定します。
       * 
       * @param displayDeleteButton
       *          displayDeleteButton
       */
      public void setDisplayDeleteButton(boolean displayDeleteButton) {
        this.displayDeleteButton = displayDeleteButton;
      }

      /**
       * reserveを取得します。
       * 
       * @return reserve
       */

      public boolean isReserve() {
        return reserve;
      }

      /**
       * reserveを設定します。
       * 
       * @param reserve
       *          reserve
       */
      public void setReserve(boolean reserve) {
        this.reserve = reserve;
      }

      public BigDecimal getWeight() {
        return weight;
      }

      public void setWeight(BigDecimal weight) {
        this.weight = weight;
      }

      
      /**
       * @return the originalCommodityCode
       */
      public String getOriginalCommodityCode() {
        return originalCommodityCode;
      }

      
      /**
       * @param originalCommodityCode the originalCommodityCode to set
       */
      public void setOriginalCommodityCode(String originalCommodityCode) {
        this.originalCommodityCode = originalCommodityCode;
      }

      
      /**
       * @return the combinationAmount
       */
      public long getCombinationAmount() {
        return combinationAmount;
      }

      
      /**
       * @param combinationAmount the combinationAmount to set
       */
      public void setCombinationAmount(long combinationAmount) {
        this.combinationAmount = combinationAmount;
      }

      
      /**
       * @return the isDiscountCommodity
       */
      public String getIsDiscountCommodity() {
        return isDiscountCommodity;
      }

      
      /**
       * @param isDiscountCommodity the isDiscountCommodity to set
       */
      public void setIsDiscountCommodity(String isDiscountCommodity) {
        this.isDiscountCommodity = isDiscountCommodity;
      }

      
      /**
       * @return the timeDiscount
       */
      public boolean isTimeDiscount() {
        return timeDiscount;
      }

      
      /**
       * @param timeDiscount the timeDiscount to set
       */
      public void setTimeDiscount(boolean timeDiscount) {
        this.timeDiscount = timeDiscount;
      }

    }

    /**
     * commodityListを取得します。
     * 
     * @return commodityList
     */
    public List<ShippingCommodityBean> getCommodityList() {
      return commodityList;
    }

    /**
     * commodityListを設定します。
     * 
     * @param commodityList
     *          commodityList
     */
    public void setCommodityList(List<ShippingCommodityBean> commodityList) {
      this.commodityList = commodityList;
    }

    /**
     * @return the acceptedGiftList
     */
    public List<ShippingCommodityBean> getAcceptedGiftList() {
      return acceptedGiftList;
    }

    /**
     * @param acceptedGiftList the acceptedGiftList to set
     */
    public void setAcceptedGiftList(List<ShippingCommodityBean> acceptedGiftList) {
      this.acceptedGiftList = acceptedGiftList;
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
     * deliveryAppointedDateListを取得します。
     * 
     * @return deliveryAppointedDateList
     */
    public List<CodeAttribute> getDeliveryAppointedDateList() {
      return deliveryAppointedDateList;
    }

    /**
     * deliveryAppointedDateListを設定します。
     * 
     * @param deliveryAppointedDateList
     *          deliveryAppointedDateList
     */
    public void setDeliveryAppointedDateList(List<CodeAttribute> deliveryAppointedDateList) {
      this.deliveryAppointedDateList = deliveryAppointedDateList;
    }

    /**
     * deliveryAppointedTimeZoneを取得します。
     * 
     * @return deliveryAppointedTimeZone
     */
    public String getDeliveryAppointedTimeZone() {
      return deliveryAppointedTimeZone;
    }

    /**
     * deliveryAppointedTimeZoneを設定します。
     * 
     * @param deliveryAppointedTimeZone
     *          deliveryAppointedTimeZone
     */
    public void setDeliveryAppointedTimeZone(String deliveryAppointedTimeZone) {
      this.deliveryAppointedTimeZone = deliveryAppointedTimeZone;
    }

    /**
     * deliveryAppointedTimeZoneListを取得します。
     * 
     * @return deliveryAppointedTimeZoneList
     */
    public List<CodeAttribute> getDeliveryAppointedTimeZoneList() {
      return deliveryAppointedTimeZoneList;
    }

    /**
     * deliveryAppointedTimeZoneListを設定します。
     * 
     * @param deliveryAppointedTimeZoneList
     *          deliveryAppointedTimeZoneList
     */
    public void setDeliveryAppointedTimeZoneList(List<CodeAttribute> deliveryAppointedTimeZoneList) {
      this.deliveryAppointedTimeZoneList = deliveryAppointedTimeZoneList;
    }

    /**
     * deliveryTypeCodeを取得します。
     * 
     * @return deliveryTypeCode
     */
    public String getDeliveryTypeCode() {
      return deliveryTypeCode;
    }

    /**
     * deliveryTypeCodeを設定します。
     * 
     * @param deliveryTypeCode
     *          deliveryTypeCode
     */
    public void setDeliveryTypeCode(String deliveryTypeCode) {
      this.deliveryTypeCode = deliveryTypeCode;
    }

    /**
     * deliveryTypeNameを取得します。
     * 
     * @return deliveryTypeName
     */
    public String getDeliveryTypeName() {
      return deliveryTypeName;
    }

    /**
     * deliveryTypeNameを設定します。
     * 
     * @param deliveryTypeName
     *          deliveryTypeName
     */
    public void setDeliveryTypeName(String deliveryTypeName) {
      this.deliveryTypeName = deliveryTypeName;
    }

    /**
     * shippingChargeを取得します。
     * 
     * @return shippingCharge
     */
    public BigDecimal getShippingCharge() {
      return shippingCharge;
    }

    /**
     * shippingChargeを設定します。
     * 
     * @param shippingCharge
     *          shippingCharge
     */
    public void setShippingCharge(BigDecimal shippingCharge) {
      this.shippingCharge = shippingCharge;
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
     * deliveryRemarkを取得します。
     * 
     * @return deliveryRemark
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
     * shopNameを取得します。
     * 
     * @return shopName
     */
    public String getShopName() {
      return shopName;
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

    public String getCodType() {
      return codType;
    }

    public void setCodType(String codType) {
      this.codType = codType;
    }

  }

  /**
   * U2020120:お届け先設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long addressNo;

    private String lastName;

    private String firstName;

    private String lastNameKana;

    private String firstNameKana;

    private String postCode;

    private String address1;

    private String cityCode;

    private String address2;

    private String address3;

    private String address4;

    private String phoneNumber;

    // Add by V10-CH start
    private String mobileNumber;

    // Add by V10-CH end

    private String email;

    private String regionCode;

    /**
     * @return the address1
     */
    public String getAddress1() {
      return address1;
    }

    /**
     * @param address1
     *          the address1 to set
     */
    public void setAddress1(String address1) {
      this.address1 = address1;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
      return address2;
    }

    /**
     * @param address2
     *          the address2 to set
     */
    public void setAddress2(String address2) {
      this.address2 = address2;
    }

    /**
     * @return the address3
     */
    public String getAddress3() {
      return address3;
    }

    /**
     * @param address3
     *          the address3 to set
     */
    public void setAddress3(String address3) {
      this.address3 = address3;
    }

    /**
     * @return the address4
     */
    public String getAddress4() {
      return address4;
    }

    /**
     * @param address4
     *          the address4 to set
     */
    public void setAddress4(String address4) {
      this.address4 = address4;
    }

    /**
     * @return the addressNo
     */
    public Long getAddressNo() {
      return addressNo;
    }

    /**
     * @param addressNo
     *          the addressNo to set
     */
    public void setAddressNo(Long addressNo) {
      this.addressNo = addressNo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
      return email;
    }

    /**
     * @param email
     *          the email to set
     */
    public void setEmail(String email) {
      this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
      return firstName;
    }

    /**
     * @param firstName
     *          the firstName to set
     */
    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    /**
     * @return the firstNameKana
     */
    public String getFirstNameKana() {
      return firstNameKana;
    }

    /**
     * @param firstNameKana
     *          the firstNameKana to set
     */
    public void setFirstNameKana(String firstNameKana) {
      this.firstNameKana = firstNameKana;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
      return lastName;
    }

    /**
     * @param lastName
     *          the lastName to set
     */
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    /**
     * @return the lastNameKana
     */
    public String getLastNameKana() {
      return lastNameKana;
    }

    /**
     * @param lastNameKana
     *          the lastNameKana to set
     */
    public void setLastNameKana(String lastNameKana) {
      this.lastNameKana = lastNameKana;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
      return phoneNumber;
    }

    /**
     * @param phoneNumber
     *          the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }

    /**
     * @return the postCode
     */
    public String getPostCode() {
      return postCode;
    }

    /**
     * @param postCode
     *          the postCode to set
     */
    public void setPostCode(String postCode) {
      this.postCode = postCode;
    }

    public String getCityCode() {
      return cityCode;
    }

    public void setCityCode(String cityCode) {
      this.cityCode = cityCode;
    }

    public String getRegionCode() {
      return regionCode;
    }

    public void setRegionCode(String regionCode) {
      this.regionCode = regionCode;
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

  // 20111221 shen add start
  /**
   * U2020120:お届け先設定のサブモデルです。
   * 
   * @author Kousen.
   */
  public static class InvoiceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Domain(InvoiceFlg.class)
    private String invoiceFlg;

    @Required
    @Length(50)
    @Metadata(name = "商品规格", order = 1)
    private String invoiceCommodityName;

    @Required
    @Domain(InvoiceType.class)
    @Metadata(name = "发票类型", order = 2)
    private String invoiceType;

    @Required
    @Length(25)
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
    @Length(25)
    @BankCode
    @Metadata(name = "银行帐号", order = 9)
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

  // 20111221 shen add end
  //2013/04/04 优惠券对应 ob add start
  /**
   * 携帯情報
   */
  public static class VerificationBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**验证码*/
    @Required
    @Length(6)
    @Digit
    @Metadata(name = "验证码")
    private String authCode;
    
    /**手机*/
    @Required
    @Length(11)
    @MobileNumber
    @Metadata(name = "手机")
    private String mobileNumber;
    
    /**电话*/
    @Metadata(name = "手机")
    private String phoneNumber;
    
    /**显示隐藏控制模式*/
    private String displayMode = "none";
    
    /**
     * 取得验证码
     * @return
     */
    public String getAuthCode() {
      return authCode;
    }

    /**
     * 设定验证码
     * @param authCode
     */
    public void setAuthCode(String authCode) {
      this.authCode = authCode;
    }

    /**
     * 取得手机
     * @return
     */
    public String getMobileNumber() {
      return mobileNumber;
    }

    /**
     * 设定手机
     * @param mobileNumber
     */
    public void setMobileNumber(String mobileNumber) {
      this.mobileNumber = mobileNumber;
    }

    
    public String getDisplayMode() {
      return displayMode;
    }

    
    public void setDisplayMode(String displayMode) {
      this.displayMode = displayMode;
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
  //2013/04/04 优惠券对应 ob add end
  /**
   * addCommodityListを取得します。
   * 
   * @return addCommodityList
   */
  public List<AddCommodityBean> getAddCommodityList() {
    return addCommodityList;
  }

  /**
   * addCommodityListを設定します。
   * 
   * @param addCommodityList
   *          addCommodityList
   */
  public void setAddCommodityList(List<AddCommodityBean> addCommodityList) {
    this.addCommodityList = addCommodityList;
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
   * paymentUserを取得します。
   * 
   * @return paymentUser
   */
  public Address getPaymentUser() {
    return paymentUser;
  }

  /**
   * paymentUserを設定します。
   * 
   * @param paymentUser
   *          paymentUser
   */
  public void setPaymentUser(Address paymentUser) {
    this.paymentUser = paymentUser;
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
   * shippingListを設定します。
   * 
   * @param shippingList
   *          shippingList
   */
  public void setShippingList(List<ShippingHeaderBean> shippingList) {
    this.shippingList = shippingList;
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
   * messageを設定します。
   * 
   * @param message
   *          message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * additionalAddressEditを取得します。
   * 
   * @return additionalAddressEdit
   */
  public AdditionalAddressBean getAdditionalAddressEdit() {
    return additionalAddressEdit;
  }

  /**
   * additionalAddressEditを設定します。
   * 
   * @param additionalAddressEdit
   *          additionalAddressEdit
   */
  public void setAdditionalAddressEdit(AdditionalAddressBean additionalAddressEdit) {
    this.additionalAddressEdit = additionalAddressEdit;
  }

  /**
   * additionalBlockを取得します。
   * 
   * @return additionalBlock
   */
  public String getAdditionalBlock() {
    return additionalBlock;
  }

  /**
   * additionalBlockを設定します。
   * 
   * @param additionalBlock
   *          additionalBlock
   */
  public void setAdditionalBlock(String additionalBlock) {
    this.additionalBlock = additionalBlock;
  }

  /**
   * addAddressCheckListを返します。
   * 
   * @return the addAddressCheckList
   */
  public List<AddAddressBean> getAddAddressCheckList() {
    return addAddressCheckList;
  }

  /**
   * addAddressSelectListを返します。
   * 
   * @return the addAddressSelectList
   */
  public List<CodeAttribute> getAddAddressSelectList() {
    return addAddressSelectList;
  }

  /**
   * addAddressCheckListを設定します。
   * 
   * @param addAddressCheckList
   *          設定する addAddressCheckList
   */
  public void setAddAddressCheckList(List<AddAddressBean> addAddressCheckList) {
    this.addAddressCheckList = addAddressCheckList;
  }

  /**
   * addAddressSelectListを設定します。
   * 
   * @param addAddressSelectList
   *          設定する addAddressSelectList
   */
  public void setAddAddressSelectList(List<CodeAttribute> addAddressSelectList) {
    this.addAddressSelectList = addAddressSelectList;
  }

  /**
   * addAddressNoListを返します。
   * 
   * @return the addAddressNoList
   */
  public List<String> getAddAddressNoList() {
    return addAddressNoList;
  }

  /**
   * addAddressNoListを設定します。
   * 
   * @param addAddressNoList
   *          設定する addAddressNoList
   */
  public void setAddAddressNoList(List<String> addAddressNoList) {
    this.addAddressNoList = addAddressNoList;
  }

  /**
   * addCommodityKeyを返します。
   * 
   * @return the addCommodityKey
   */
  public String getAddCommodityKey() {
    return addCommodityKey;
  }

  /**
   * addCommodityKeyを設定します。
   * 
   * @param addCommodityKey
   *          設定する addCommodityKey
   */
  public void setAddCommodityKey(String addCommodityKey) {
    this.addCommodityKey = addCommodityKey;
  }

  /**
   * availableAddDeliveryを取得します。
   * 
   * @return availableAddDelivery
   */
  public boolean isAvailableAddDelivery() {
    return availableAddDelivery;
  }

  /**
   * availableAddDeliveryを設定します。
   * 
   * @param availableAddDelivery
   *          availableAddDelivery
   */
  public void setAvailableAddDelivery(boolean availableAddDelivery) {
    this.availableAddDelivery = availableAddDelivery;
  }

  public PaymentMethodBean getOrderPayment() {
    return orderPayment;
  }

  public void setOrderPayment(PaymentMethodBean orderPayment) {
    this.orderPayment = orderPayment;
  }

  public InvoiceBean getOrderInvoice() {
    return orderInvoice;
  }

  public void setOrderInvoice(InvoiceBean orderInvoice) {
    this.orderInvoice = orderInvoice;
  }

  public List<CodeAttribute> getInvoiceCommodityNameList() {
    return invoiceCommodityNameList;
  }

  public void setInvoiceCommodityNameList(List<CodeAttribute> invoiceCommodityNameList) {
    this.invoiceCommodityNameList = invoiceCommodityNameList;
  }

  public List<CodeAttribute> getDiscountTypeList() {
    return discountTypeList;
  }

  public void setDiscountTypeList(List<CodeAttribute> discountTypeList) {
    this.discountTypeList = discountTypeList;
  }

  public List<CodeAttribute> getPersonalCouponList() {
    return personalCouponList;
  }

  public void setPersonalCouponList(List<CodeAttribute> personalCouponList) {
    this.personalCouponList = personalCouponList;
  }

  public String getSelDiscountType() {
    return selDiscountType;
  }

  public void setSelDiscountType(String selDiscountType) {
    this.selDiscountType = selDiscountType;
  }

  public String getSelPersonalCouponCode() {
    return selPersonalCouponCode;
  }

  public void setSelPersonalCouponCode(String selPersonalCouponCode) {
    this.selPersonalCouponCode = selPersonalCouponCode;
  }

  public String getPublicCouponCode() {
    return publicCouponCode;
  }

  public void setPublicCouponCode(String publicCouponCode) {
    this.publicCouponCode = publicCouponCode;
  }

  public List<CodeAttribute> getDeliveryDateList() {
    return deliveryDateList;
  }

  public void setDeliveryDateList(List<CodeAttribute> deliveryDateList) {
    this.deliveryDateList = deliveryDateList;
  }

  public String getSelCodType() {
    return selCodType;
  }

  public void setSelCodType(String selCodType) {
    this.selCodType = selCodType;
  }

  public String getSelPrefectureCode() {
    return selPrefectureCode;
  }

  public void setSelPrefectureCode(String selPrefectureCode) {
    this.selPrefectureCode = selPrefectureCode;
  }

  /**
   * @return the selCityCode
   */
  public String getSelCityCode() {
    return selCityCode;
  }

  /**
   * @param selCityCode the selCityCode to set
   */
  public void setSelCityCode(String selCityCode) {
    this.selCityCode = selCityCode;
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
   * @return the selAreaCode
   */
  public String getSelAreaCode() {
    return selAreaCode;
  }

  /**
   * @param selAreaCode
   *          the selAreaCode to set
   */
  public void setSelAreaCode(String selAreaCode) {
    this.selAreaCode = selAreaCode;
  }

  /**
   * @return the displayCouponButton
   */
  public boolean isDisplayCouponButton() {
    return displayCouponButton;
  }

  /**
   * @param displayCouponButton
   *          the displayCouponButton to set
   */
  public void setDisplayCouponButton(boolean displayCouponButton) {
    this.displayCouponButton = displayCouponButton;
  }

  /**
   * @return the displayPublicCouponButton
   */
  public boolean isDisplayPublicCouponButton() {
    return displayPublicCouponButton;
  }

  /**
   * @param displayPublicCouponButton
   *          the displayPublicCouponButton to set
   */
  public void setDisplayPublicCouponButton(boolean displayPublicCouponButton) {
    this.displayPublicCouponButton = displayPublicCouponButton;
  }

  // 20120203 shen add start
  public boolean isHasDeliveryMessage() {
    List<ShippingHeaderBean> shippingList = getShippingList();
    if (shippingList != null && shippingList.size() > 0) {
      List<ShippingDetailBean> shippingDetail = shippingList.get(0).getShippingList();
      if (shippingDetail != null && shippingDetail.size() > 0) {
        List<CodeAttribute> deliveryAppointedDateList = shippingDetail.get(0).getDeliveryAppointedDateList();
        if (deliveryAppointedDateList != null && deliveryAppointedDateList.size() > 0) {
          for (CodeAttribute codeAttribute : deliveryAppointedDateList) {
            if (!codeAttribute.getName().equals(DeliveryDateType.UNUSABLE.getName())
                && !codeAttribute.getName().equals(DeliveryDateType.FERIAL.getName())
                && !codeAttribute.getName().equals(DeliveryDateType.HOLIDAY.getName())) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }
  // 20120203 shen add end


  public static class DeliveryCompanyBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String deliveryCompanyNo;
    
    private String deliveryCompanyName;

    private BigDecimal deliveryCharge;
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

    /**
     * @return the deliveryCompanyName
     */
    public String getDeliveryCompanyName() {
      return deliveryCompanyName;
    }

    /**
     * @param deliveryCompanyName the deliveryCompanyName to set
     */
    public void setDeliveryCompanyName(String deliveryCompanyName) {
      this.deliveryCompanyName = deliveryCompanyName;
    }

    /**
     * @return the deliveryCharge
     */
    public BigDecimal getDeliveryCharge() {
      return deliveryCharge;
    }

    /**
     * @param deliveryCharge the deliveryCharge to set
     */
    public void setDeliveryCharge(BigDecimal deliveryCharge) {
      this.deliveryCharge = deliveryCharge;
    }
  }

  
  /**
   * @return the deliveryCompanyList
   */
  public List<DeliveryCompanyBean> getDeliveryCompanyList() {
    return deliveryCompanyList;
  }

  
  /**
   * @param deliveryCompanyList the deliveryCompanyList to set
   */
  public void setDeliveryCompanyList(List<DeliveryCompanyBean> deliveryCompanyList) {
    this.deliveryCompanyList = deliveryCompanyList;
  }

  
  /**
   * @return the selectedDeliveryCompany
   */
  public DeliveryCompanyBean getSelectedDeliveryCompany() {
    return selectedDeliveryCompany;
  }

  
  /**
   * @param selectedDeliveryCompany the selectedDeliveryCompany to set
   */
  public void setSelectedDeliveryCompany(DeliveryCompanyBean selectedDeliveryCompany) {
    this.selectedDeliveryCompany = selectedDeliveryCompany;
  }


  
  /**
   * @return the useAmount
   */
  public String getUseAmount() {
    return useAmount;
  }


  
  /**
   * @param useAmount the useAmount to set
   */
  public void setUseAmount(String useAmount) {
    this.useAmount = useAmount;
  }


  
  /**
   * @return the totalAmount
   */
  public String getTotalAmount() {
    return totalAmount;
  }


  
  /**
   * @param totalAmount the totalAmount to set
   */
  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }


  
  /**
   * @return the paymentPassword
   */
  public String getPaymentPassword() {
    return paymentPassword;
  }


  
  /**
   * @param paymentPassword the paymentPassword to set
   */
  public void setPaymentPassword(String paymentPassword) {
    this.paymentPassword = paymentPassword;
  }


  
  /**
   * @return the useAmountRight
   */
  public String getUseAmountRight() {
    return useAmountRight;
  }


  
  /**
   * @param useAmountRight the useAmountRight to set
   */
  public void setUseAmountRight(String useAmountRight) {
    this.useAmountRight = useAmountRight;
  }


  
  /**
   * @return the displayAjaxFlg
   */
  public boolean isDisplayAjaxFlg() {
    return displayAjaxFlg;
  }


  
  /**
   * @param displayAjaxFlg the displayAjaxFlg to set
   */
  public void setDisplayAjaxFlg(boolean displayAjaxFlg) {
    this.displayAjaxFlg = displayAjaxFlg;
  }


  
  /**
   * @return the popupFlg
   */
  public boolean isPopupFlg() {
    return popupFlg;
  }


  
  /**
   * @param popupFlg the popupFlg to set
   */
  public void setPopupFlg(boolean popupFlg) {
    this.popupFlg = popupFlg;
  }


  
  /**
   * @return the hiddenSelDiscountType
   */
  public String getHiddenSelDiscountType() {
    return hiddenSelDiscountType;
  }


  
  /**
   * @param hiddenSelDiscountType the hiddenSelDiscountType to set
   */
  public void setHiddenSelDiscountType(String hiddenSelDiscountType) {
    this.hiddenSelDiscountType = hiddenSelDiscountType;
  }


  public void setNextTuesdayDate(String nextTuesdayDate) {
    this.nextTuesdayDate = nextTuesdayDate;
  }


  public String getNextTuesdayDate() {
    return nextTuesdayDate;
  }


  
  /**
   * @return the checkAddressFlg
   */
  public boolean isCheckAddressFlg() {
    return checkAddressFlg;
  }


  
  /**
   * @param checkAddressFlg the checkAddressFlg to set
   */
  public void setCheckAddressFlg(boolean checkAddressFlg) {
    this.checkAddressFlg = checkAddressFlg;
  }
  
}
