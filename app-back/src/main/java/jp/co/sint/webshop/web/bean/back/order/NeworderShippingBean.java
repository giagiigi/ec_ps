package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.back.order.PaymentSupporterFactory;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean.OrderCommodityBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020130:新規受注(配送先設定)のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderShippingBean extends NeworderBaseBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  private String customerName;

  private String cartTotalCommodityPrice;

  private List<DeliveryBean> deliveryList = new ArrayList<DeliveryBean>();

  private AdditionalAddressBean additionalAddressEdit = new AdditionalAddressBean();

  private List<AddCommodityBean> addCommodityList = new ArrayList<AddCommodityBean>();

  private List<CodeAttribute> dispAddressList = new ArrayList<CodeAttribute>();

  /** 追加できる商品一覧(選択用) */
  private List<CodeAttribute> addSelectCommodityList = new ArrayList<CodeAttribute>();

  /** 顧客のアドレス帳一覧(チェック用) */
  private List<AddAddressBean> addAddressCheckList = new ArrayList<AddAddressBean>();

  /** 追加する商品情報 */
  @Required
  @Metadata(name = "追加商品", order = 2)
  private String addCommodityKey;

  /** 商品を追加するアドレス番号 */
  @Metadata(name = "商品追加先", order = 2)
  private List<String> addAddressNoList;

  private boolean displayAdditionalAddress;

  private boolean availableAddDelivery;

  private String additionalBlock;

  // soukai add 2012/01/03 add start
  // 配送地址
  private String shippingAddress;

  // 发票信息
  private InvoiceBean orderInvoice = new InvoiceBean();

  // 商品规格
  private List<CodeAttribute> invoiceCommodityNameList = new ArrayList<CodeAttribute>();

  /** お支払い情報 */
  private PaymentMethodBean orderPayment;

  /** 配送公司関連情報をまとめたもの */
  private List<DeliveryCompanyBean> deliveryCompanyList = new ArrayList<DeliveryCompanyBean>();

  private DeliveryCompanyBean selectedDeliveryCompany = new DeliveryCompanyBean();

  private List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> personalCouponList = new ArrayList<CodeAttribute>();

  private String selDiscountType;
  
  private String hiddenSelDiscountType;

  private String selPersonalCouponCode;

  private String publicCouponCode;

  private String selCodType;

  private String selPrefectureCode;

  private String addressScript;

  private List<CodeAttribute> addressPrefectureList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> addressCityList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> addressAreaList = new ArrayList<CodeAttribute>();

  // soukai add 2012/01/03 add end
  // 2012-11-27 促销对应 ob add start
  // 多关联活动的赠品
  private List<OrderCommodityBean> otherGiftList = new ArrayList<OrderCommodityBean>();

  // 礼品券使用flg
  private boolean discountUsedFlg;

  // 折扣券使用flg
  private boolean discountCouponUsedFlg;

  private Map<String, Long> discountCouponUsedMap = new HashMap<String, Long>();
  
  //会员折扣
  private CustomerGroupCampaign customerGroupCampaign;

  public List<OrderCommodityBean> getOtherGiftList() {
    return otherGiftList;
  }

  public void setOtherGiftList(List<OrderCommodityBean> otherGiftList) {
    this.otherGiftList = otherGiftList;
  }

  /**
   * @return the discountCouponUsedMap
   */
  public Map<String, Long> getDiscountCouponUsedMap() {
    return discountCouponUsedMap;
  }

  /**
   * @param discountCouponUsedMap
   *          the discountCouponUsedMap to set
   */
  public void setDiscountCouponUsedMap(Map<String, Long> discountCouponUsedMap) {
    this.discountCouponUsedMap = discountCouponUsedMap;
  }

  /**
   * @return the discountCouponUsedFlg
   */
  public boolean isDiscountCouponUsedFlg() {
    return discountCouponUsedFlg;
  }

  /**
   * @param discountCouponUsedFlg
   *          the discountCouponUsedFlg to set
   */
  public void setDiscountCouponUsedFlg(boolean discountCouponUsedFlg) {
    this.discountCouponUsedFlg = discountCouponUsedFlg;
  }

  public boolean isDiscountUsedFlg() {
    return discountUsedFlg;
  }

  public void setDiscountUsedFlg(boolean discountUsedFlg) {
    this.discountUsedFlg = discountUsedFlg;
  }

  // 2012-11-27 促销对应 ob add end

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    reqparam.copy(additionalAddressEdit);
    // 20140312 hdh add start
    if(StringUtil.isNullOrEmpty(reqparam.get("hiddenSelDiscountType"))){
      this.setHiddenSelDiscountType(this.getSelDiscountType());
    }
    // 20140312 hdh add end;
    additionalAddressEdit.setAdditionalPostalCode(reqparam.get("additionalPostalCode"));
    additionalAddressEdit.setAdditionalPhoneNumber1(reqparam.get("additionalPhoneNumber_1"));
    additionalAddressEdit.setAdditionalPhoneNumber2(reqparam.get("additionalPhoneNumber_2"));
    additionalAddressEdit.setAdditionalPhoneNumber3(reqparam.get("additionalPhoneNumber_3"));
    additionalAddressEdit.setAdditionalAddressLastName(StringUtil.parse(reqparam.get("additionalAddressLastName")));
    additionalAddressEdit.setAdditionalAddress4(StringUtil.parse(reqparam.get("additionalAddress4")));
    // Add by V10-CH start
    additionalAddressEdit.setAdditionalMobileNumber(reqparam.get("additionalMobileNumber"));
    // Add by V10-CH end
    setAddCommodityKey(reqparam.get("commodityKey"));
    String[] addressKeyList = reqparam.getAll("addressNo");
    if (addressKeyList.length > 0) {
      addAddressNoList = new ArrayList<String>();
      addAddressNoList.addAll(Arrays.asList(addressKeyList));
    } else {
      addAddressNoList = null;
    }

    List<DeliveryBean> deliveryBeanList = this.getDeliveryList();
    List<DeliveryBean> newDeliveryBeanList = new ArrayList<DeliveryBean>();
    // 配送ごとの入力情報の設定
    for (DeliveryBean deliveryBean : deliveryBeanList) {
      String deliveryKey = deliveryBean.getAddressNo() + "_" + deliveryBean.getShopCode() + "_" + deliveryBean.getDeliveryCode();
      deliveryBean.setNewAddressNo(reqparam.get("addressNo_" + deliveryKey));
      deliveryBean.setDeliveryRemark(reqparam.get("deliveryRemark_" + deliveryKey));
      deliveryBean.setDeliveryAppointedDate(reqparam.get("deliveryAppointedDate_" + deliveryKey));

      String deliveryAppointedTime = reqparam.get("deliveryAppointedTime_" + deliveryKey);
      deliveryBean.setDeliveryAppointedTime(deliveryAppointedTime);

      // 商品ごとの入力情報の設定
      List<OrderCommodityBean> commodityBeanList = deliveryBean.getOrderCommodityList();
      List<OrderCommodityBean> newCommodityBeanList = new ArrayList<OrderCommodityBean>();
      for (OrderCommodityBean commodityBean : commodityBeanList) {
        String detailKey = deliveryKey + "_" + commodityBean.getSkuCode() ;
        commodityBean.setGiftCode(reqparam.get("giftCode_" + detailKey));
        commodityBean.setPurchasingAmount(reqparam.get("purchasingAmount_" + detailKey + "_" +  commodityBean.getIsDiscountCommodity()));
        if (!commodityBean.isDiscountCouponUsedFlg()) {
          commodityBean.setDiscountCouponCode(reqparam.get("discountCouponCode_" + detailKey));
        }
        newCommodityBeanList.add(commodityBean);
      }
      deliveryBean.setOrderCommodityList(newCommodityBeanList);
      newDeliveryBeanList.add(deliveryBean);
    }
    // soukai add 2012/01/03 ob start
    // 发票信息获得
    orderInvoice.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
    if (StringUtil.hasValue(reqparam.get("invoiceFlg"))) {
      orderInvoice.setInvoiceFlg(reqparam.get("invoiceFlg"));
      if (InvoiceFlg.NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        orderInvoice.setInvoiceCommodityName(reqparam.get("invoiceCommodityName"));
        orderInvoice.setInvoiceType(reqparam.get("invoiceType"));
        // upd by lc 2012-03-08 start
        if (InvoiceType.USUAL.getValue().equals(orderInvoice.getInvoiceType())) {
          orderInvoice.setInvoiceCustomerName(StringUtil.parse(reqparam.get("invoiceCustomerName")));
        } else if (InvoiceType.VAT.getValue().equals(orderInvoice.getInvoiceType())) {
          orderInvoice.setInvoiceCompanyName(StringUtil.parse(reqparam.get("invoiceCompanyName")));
          orderInvoice.setInvoiceTaxpayerCode(reqparam.get("invoiceTaxpayerCode"));
          orderInvoice.setInvoiceAddress(StringUtil.parse(reqparam.get("invoiceAddress")));
          orderInvoice.setInvoiceTel(reqparam.get("invoiceTel"));
          orderInvoice.setInvoiceBankName(StringUtil.parse(reqparam.get("invoiceBankName")));
          // upd by lc 2012-03-08 end
          orderInvoice.setInvoiceBankNo(reqparam.get("invoiceBankNo"));
          orderInvoice.setInvoiceSaveFlg(reqparam.get("invoiceSaveFlg"));
        }
      }
    }
    // 获得支付方式
    PaymentSupporterFactory.createPaymentSuppoerter().copyOrderPaymentMethod(reqparam, orderPayment);
    // soukai add 2012/01/03 ob start
    this.setDeliveryList(newDeliveryBeanList);
    if (!StringUtil.isNullOrEmpty(reqparam.get("deliveryCompanyCode"))) {
      DeliveryCompanyBean dcb = new DeliveryCompanyBean();
      dcb.setDeliveryCompanyNo(reqparam.get("deliveryCompanyCode"));
      this.setSelectedDeliveryCompany(dcb);
    }

  }

  public static class DeliveryCompanyBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String deliveryCompanyNo;

    private String deliveryCompanyName;

    /**
     * @return the deliveryCompanyNo
     */
    public String getDeliveryCompanyNo() {
      return deliveryCompanyNo;
    }

    /**
     * @param deliveryCompanyNo
     *          the deliveryCompanyNo to set
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
     * @param deliveryCompanyName
     *          the deliveryCompanyName to set
     */
    public void setDeliveryCompanyName(String deliveryCompanyName) {
      this.deliveryCompanyName = deliveryCompanyName;
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020130";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderShippingBean.0");
  }

  /**
   * U1020130:新規受注(配送先設定)のサブモデルです。
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

    /** 商品コード */
    private String commodityCode;

    /** 商品名(規格１・規格２) */
    private String displayName;

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

    /**
     * displayNameを取得します。
     * 
     * @return displayName
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

  }

  /**
   * U1020130:新規受注(配送先設定)のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DeliveryBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private Long addressNo;

    private String deliveryCode;

    private String deliveryName;

    private String deliveryAlias;

    private String postalCode;

    private String lastName;

    private String firstName;

    private String prefectureCode;

    // 2013/04/21 优惠券对应 ob add start
    private String areaCode;

    // 2013/04/21 优惠券对应 ob add end

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    @Phone
    private String phoneNumber;

    // Add by V10-CH start
    @MobileNumber
    private String mobileNumber;

    private String cityCode;

    // Add by V10-CH end

    // soukai add 2012/01/03 ob start

    // soukai add 2012/01/03 ob end

    private BigDecimal deliveryTotalCommodityPrice;

    private List<CodeAttribute> deliveryAppointedDateList = new ArrayList<CodeAttribute>();

    private String deliveryAppointedDate;

    private String deliveryAppointedTimeCode;

    private List<CodeAttribute> deliveryAppointedTimeList = new ArrayList<CodeAttribute>();

    private String deliveryAppointedTime;

    private String shippingCharge;

    private String shippingChargeTax;

    @Length(500)
    @Metadata(name = "配送先備考")
    private String deliveryRemark;

    @Digit
    @Metadata(name = "アドレス帳番号")
    private String newAddressNo;

    // soukai add 2012/01/03 ob start
    private String codType;

    // soukai add 2012/01/03 ob end

    private List<OrderCommodityBean> orderCommodityList = new ArrayList<OrderCommodityBean>();

    /**
     * U1020130:新規受注(配送先設定)のサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class OrderCommodityBean implements Serializable {

      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private String shopCode;

      private String shopName;

      private String commodityCode;

      private String skuCode;

      private String commodityName;

      private String standardName1;

      private String standardName2;

      private Long stockQuantity;

      private Long stockManagementType;

      @Required
      @Digit
      @Length(4)
      @Range(min = 1, max = 9999)
      @Metadata(name = "数量")
      private String purchasingAmount;

      private BigDecimal retailPrice;

      private Long commodityTaxType;

      private String commodityTotalPrice;

      @AlphaNum2
      @Metadata(name = "ギフト")
      private String giftCode;

      private List<CodeAttribute> giftList = new ArrayList<CodeAttribute>();

      private String giftPrice;

      private String subTotalPrice;

      // 2012-11-21 促销对应 ob add start
      @AlphaNum2
      @Length(16)
      // 折扣券编号
      private String discountCouponCode;

      // 折扣券使用flg
      private boolean discountCouponUsedFlg = false;

      // 折扣券表示flg
      private boolean discountCouponDisplayFlg;

      // 套餐flg
      private boolean setCommodityFlg;

      // 特定商品赠品 flg
      private boolean giftFlg;

      // 描述信息
      private String commodityAttributes;

      // 该商品的赠品信息列表
      private List<GiftItemBean> commodityGiftList = new ArrayList<GiftItemBean>();

      // 套餐商品的构成品列表
      private List<CompositionItem> compositionList = new ArrayList<CompositionItem>();

      // 促销活动编号
      private String multipleCampaignCode;

      // 促销活动名称
      private String multipleCampaignName;

      // 折扣金额
      BigDecimal discountPrice = BigDecimal.ZERO;

      // 折扣合计金额
      BigDecimal totalDiscountPrice = BigDecimal.ZERO;

      private String originalCommodityCode;

      private Long combinationAmount;
      
      private String isDiscountCommodity = "false";

      /**
       * @return the originalCommodityCode
       */
      public String getOriginalCommodityCode() {
        return originalCommodityCode;
      }

      /**
       * @return the combinationAmount
       */
      public Long getCombinationAmount() {
        return combinationAmount;
      }

      /**
       * @param originalCommodityCode
       *          the originalCommodityCode to set
       */
      public void setOriginalCommodityCode(String originalCommodityCode) {
        this.originalCommodityCode = originalCommodityCode;
      }

      /**
       * @param combinationAmount
       *          the combinationAmount to set
       */
      public void setCombinationAmount(Long combinationAmount) {
        this.combinationAmount = combinationAmount;
      }

      public String getMultipleCampaignCode() {
        return multipleCampaignCode;
      }

      public void setMultipleCampaignCode(String multipleCampaignCode) {
        this.multipleCampaignCode = multipleCampaignCode;
      }

      public String getMultipleCampaignName() {
        return multipleCampaignName;
      }

      public void setMultipleCampaignName(String multipleCampaignName) {
        this.multipleCampaignName = multipleCampaignName;
      }

      public List<CompositionItem> getCompositionList() {
        return compositionList;
      }

      public void setCompositionList(List<CompositionItem> compositionList) {
        this.compositionList = compositionList;
      }

      public BigDecimal getDiscountPrice() {
        return discountPrice;
      }

      public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
      }

      public BigDecimal getTotalDiscountPrice() {
        return totalDiscountPrice;
      }

      public void setTotalDiscountPrice(BigDecimal totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
      }

      public boolean isSetCommodityFlg() {
        return setCommodityFlg;
      }

      public void setSetCommodityFlg(boolean setCommodityFlg) {
        this.setCommodityFlg = setCommodityFlg;
      }

      public boolean isGiftFlg() {
        return giftFlg;
      }

      public void setGiftFlg(boolean giftFlg) {
        this.giftFlg = giftFlg;
      }

      public String getCommodityAttributes() {
        return commodityAttributes;
      }

      public void setCommodityAttributes(String commodityAttributes) {
        this.commodityAttributes = commodityAttributes;
      }

      public String getDiscountCouponCode() {
        return discountCouponCode;
      }

      public void setDiscountCouponCode(String discountCouponCode) {
        this.discountCouponCode = discountCouponCode;
      }

      public boolean isDiscountCouponUsedFlg() {
        return discountCouponUsedFlg;
      }

      public void setDiscountCouponUsedFlg(boolean discountCouponUsedFlg) {
        this.discountCouponUsedFlg = discountCouponUsedFlg;
      }

      public List<GiftItemBean> getCommodityGiftList() {
        return commodityGiftList;
      }

      public void setCommodityGiftList(List<GiftItemBean> commodityGiftList) {
        this.commodityGiftList = commodityGiftList;
      }

      public boolean isDiscountCouponDisplayFlg() {
        return discountCouponDisplayFlg;
      }

      public void setDiscountCouponDisplayFlg(boolean discountCouponDisplayFlg) {
        this.discountCouponDisplayFlg = discountCouponDisplayFlg;
      }

      // 2012-11-21 促销对应 ob add end
      /**
       * commodityNameを取得します。
       * 
       * @return commodityName
       */
      public String getCommodityName() {
        return commodityName;
      }

      /**
       * commodityTaxTypeを取得します。
       * 
       * @return commodityTaxType
       */
      public Long getCommodityTaxType() {
        return commodityTaxType;
      }

      /**
       * commodityTotalPriceを取得します。
       * 
       * @return commodityTotalPrice
       */
      public String getCommodityTotalPrice() {
        return commodityTotalPrice;
      }

      /**
       * purchasingAmountを取得します。
       * 
       * @return purchasingAmount
       */
      public String getPurchasingAmount() {
        return purchasingAmount;
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
       * shopNameを取得します。
       * 
       * @return shopName
       */
      public String getShopName() {
        return shopName;
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
       * standardName1を取得します。
       * 
       * @return standardName1
       */
      public String getStandardName1() {
        return standardName1;
      }

      /**
       * standardName2を取得します。
       * 
       * @return standardName2
       */
      public String getStandardName2() {
        return standardName2;
      }

      /**
       * stockQuantityを取得します。
       * 
       * @return stockQuantity
       */
      public Long getStockQuantity() {
        return stockQuantity;
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
       * commodityTaxTypeを設定します。
       * 
       * @param commodityTaxType
       *          commodityTaxType
       */
      public void setCommodityTaxType(Long commodityTaxType) {
        this.commodityTaxType = commodityTaxType;
      }

      /**
       * commodityTotalPriceを設定します。
       * 
       * @param commodityTotalPrice
       *          commodityTotalPrice
       */
      public void setCommodityTotalPrice(String commodityTotalPrice) {
        this.commodityTotalPrice = commodityTotalPrice;
      }

      /**
       * purchasingAmountを設定します。
       * 
       * @param purchasingAmount
       *          purchasingAmount
       */
      public void setPurchasingAmount(String purchasingAmount) {
        this.purchasingAmount = purchasingAmount;
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
       * shopNameを設定します。
       * 
       * @param shopName
       *          shopName
       */
      public void setShopName(String shopName) {
        this.shopName = shopName;
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
       * standardName1を設定します。
       * 
       * @param standardName1
       *          standardName1
       */
      public void setStandardName1(String standardName1) {
        this.standardName1 = standardName1;
      }

      /**
       * standardName2を設定します。
       * 
       * @param standardName2
       *          standardName2
       */
      public void setStandardName2(String standardName2) {
        this.standardName2 = standardName2;
      }

      /**
       * stockQuantityを設定します。
       * 
       * @param stockQuantity
       *          stockQuantity
       */
      public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
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
       * giftPriceを取得します。
       * 
       * @return giftPrice
       */
      public String getGiftPrice() {
        return giftPrice;
      }

      /**
       * giftPriceを設定します。
       * 
       * @param giftPrice
       *          giftPrice
       */
      public void setGiftPrice(String giftPrice) {
        this.giftPrice = giftPrice;
      }

      /**
       * subTotalPriceを取得します。
       * 
       * @return subTotalPrice
       */
      public String getSubTotalPrice() {
        return subTotalPrice;
      }

      /**
       * subTotalPriceを設定します。
       * 
       * @param subTotalPrice
       *          subTotalPrice
       */
      public void setSubTotalPrice(String subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
      }

      /**
       * stockManagementTypeを取得します。
       * 
       * @return stockManagementType
       */
      public Long getStockManagementType() {
        return stockManagementType;
      }

      /**
       * stockManagementTypeを設定します。
       * 
       * @param stockManagementType
       *          stockManagementType
       */
      public void setStockManagementType(Long stockManagementType) {
        this.stockManagementType = stockManagementType;
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

    }

    // 2012-11-26 促销对应 ob add start
    public static class GiftItemBean implements Serializable {

      private static final long serialVersionUID = 1L;

      private String shopCode;

      private String giftCode;

      private String giftName;

      private String giftSkuCode;

      private Long taxType;

      private BigDecimal unitPrice;

      private BigDecimal unitTaxCharge;

      private BigDecimal retailPrice;

      private BigDecimal subTotalPrice;

      private String standardDetail1Name;

      private String standardDetail2Name;

      private int quantity;

      private String campaignCode;

      private String campaignName;

      private boolean discount;

      private BigDecimal weight;

      private Long stockManagementType;

      private Long StockQuantity;

      public String getShopCode() {
        return shopCode;
      }

      public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
      }

      public String getGiftCode() {
        return giftCode;
      }

      public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
      }

      public String getGiftName() {
        return giftName;
      }

      public void setGiftName(String giftName) {
        this.giftName = giftName;
      }

      public String getGiftSkuCode() {
        return giftSkuCode;
      }

      public void setGiftSkuCode(String giftSkuCode) {
        this.giftSkuCode = giftSkuCode;
      }

      public Long getTaxType() {
        return taxType;
      }

      public void setTaxType(Long taxType) {
        this.taxType = taxType;
      }

      public BigDecimal getUnitPrice() {
        return unitPrice;
      }

      public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
      }

      public BigDecimal getUnitTaxCharge() {
        return unitTaxCharge;
      }

      public void setUnitTaxCharge(BigDecimal unitTaxCharge) {
        this.unitTaxCharge = unitTaxCharge;
      }

      public BigDecimal getRetailPrice() {
        return retailPrice;
      }

      public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
      }

      public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
      }

      public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
      }

      public String getStandardDetail1Name() {
        return standardDetail1Name;
      }

      public void setStandardDetail1Name(String standardDetail1Name) {
        this.standardDetail1Name = standardDetail1Name;
      }

      public String getStandardDetail2Name() {
        return standardDetail2Name;
      }

      public void setStandardDetail2Name(String standardDetail2Name) {
        this.standardDetail2Name = standardDetail2Name;
      }

      public int getQuantity() {
        return quantity;
      }

      public void setQuantity(int quantity) {
        this.quantity = quantity;
      }

      public String getCampaignCode() {
        return campaignCode;
      }

      public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
      }

      public String getCampaignName() {
        return campaignName;
      }

      public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
      }

      public boolean isDiscount() {
        return discount;
      }

      public void setDiscount(boolean discount) {
        this.discount = discount;
      }

      public BigDecimal getWeight() {
        return weight;
      }

      public void setWeight(BigDecimal weight) {
        this.weight = weight;
      }

      public Long getStockManagementType() {
        return stockManagementType;
      }

      public void setStockManagementType(Long stockManagementType) {
        this.stockManagementType = stockManagementType;
      }

      public Long getStockQuantity() {
        return StockQuantity;
      }

      public void setStockQuantity(Long stockQuantity) {
        StockQuantity = stockQuantity;
      }
    }

    // 2012-11-26 促销对应 ob add end

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
     * addressNoを取得します。
     * 
     * @return addressNo
     */
    public Long getAddressNo() {
      return addressNo;
    }

    /**
     * deliveryAliasを取得します。
     * 
     * @return deliveryAlias
     */
    public String getDeliveryAlias() {
      return deliveryAlias;
    }

    /**
     * deliveryNameを取得します。
     * 
     * @return deliveryName
     */
    public String getDeliveryName() {
      return deliveryName;
    }

    /**
     * deliveryTotalCommodityPriceを取得します。
     * 
     * @return deliveryTotalCommodityPrice
     */
    public BigDecimal getDeliveryTotalCommodityPrice() {
      return deliveryTotalCommodityPrice;
    }

    /**
     * orderCommodityListを取得します。
     * 
     * @return orderCommodityList
     */
    public List<OrderCommodityBean> getOrderCommodityList() {
      return orderCommodityList;
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
     * addressNoを設定します。
     * 
     * @param addressNo
     *          addressNo
     */
    public void setAddressNo(Long addressNo) {
      this.addressNo = addressNo;
    }

    /**
     * deliveryAliasを設定します。
     * 
     * @param deliveryAlias
     *          deliveryAlias
     */
    public void setDeliveryAlias(String deliveryAlias) {
      this.deliveryAlias = deliveryAlias;
    }

    /**
     * deliveryNameを設定します。
     * 
     * @param deliveryName
     *          deliveryName
     */
    public void setDeliveryName(String deliveryName) {
      this.deliveryName = deliveryName;
    }

    /**
     * deliveryTotalCommodityPriceを設定します。
     * 
     * @param deliveryTotalCommodityPrice
     *          deliveryTotalCommodityPrice
     */
    public void setDeliveryTotalCommodityPrice(BigDecimal deliveryTotalCommodityPrice) {
      this.deliveryTotalCommodityPrice = deliveryTotalCommodityPrice;
    }

    /**
     * orderCommodityListを設定します。
     * 
     * @param orderCommodityList
     *          orderCommodityList
     */
    public void setOrderCommodityList(List<OrderCommodityBean> orderCommodityList) {
      this.orderCommodityList = orderCommodityList;
    }

    /**
     * deliveryCodeを取得します。
     * 
     * @return deliveryCode
     */
    public String getDeliveryCode() {
      return deliveryCode;
    }

    /**
     * deliveryCodeを設定します。
     * 
     * @param deliveryCode
     *          deliveryCode
     */
    public void setDeliveryCode(String deliveryCode) {
      this.deliveryCode = deliveryCode;
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
     * shippingChargeを取得します。
     * 
     * @return shippingCharge
     */
    public String getShippingCharge() {
      return shippingCharge;
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
     * shippingChargeTaxを取得します。
     * 
     * @return shippingChargeTax
     */
    public String getShippingChargeTax() {
      return shippingChargeTax;
    }

    /**
     * shippingChargeTaxを設定します。
     * 
     * @param shippingChargeTax
     *          shippingChargeTax
     */
    public void setShippingChargeTax(String shippingChargeTax) {
      this.shippingChargeTax = shippingChargeTax;
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
     * deliveryAppointedTimeを取得します。
     * 
     * @return deliveryAppointedTime
     */
    public String getDeliveryAppointedTime() {
      return deliveryAppointedTime;
    }

    /**
     * deliveryAppointedTimeを設定します。
     * 
     * @param deliveryAppointedTime
     *          deliveryAppointedTime
     */
    public void setDeliveryAppointedTime(String deliveryAppointedTime) {
      this.deliveryAppointedTime = deliveryAppointedTime;
    }

    /**
     * deliveryAppointedTimeListを取得します。
     * 
     * @return deliveryAppointedTimeList
     */
    public List<CodeAttribute> getDeliveryAppointedTimeList() {
      return deliveryAppointedTimeList;
    }

    /**
     * deliveryAppointedTimeListを設定します。
     * 
     * @param deliveryAppointedTimeList
     *          deliveryAppointedTimeList
     */
    public void setDeliveryAppointedTimeList(List<CodeAttribute> deliveryAppointedTimeList) {
      this.deliveryAppointedTimeList = deliveryAppointedTimeList;
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
     * deliveryAppointedTimeCodeを取得します。
     * 
     * @return deliveryAppointedTimeCode
     */
    public String getDeliveryAppointedTimeCode() {
      return deliveryAppointedTimeCode;
    }

    /**
     * deliveryAppointedTimeCodeを設定します。
     * 
     * @param deliveryAppointedTimeCode
     *          deliveryAppointedTimeCode
     */
    public void setDeliveryAppointedTimeCode(String deliveryAppointedTimeCode) {
      this.deliveryAppointedTimeCode = deliveryAppointedTimeCode;
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
     * address1を取得します。
     * 
     * @return address1
     */
    public String getAddress1() {
      return address1;
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

    /**
     * @return the codType
     */
    public String getCodType() {
      return codType;
    }

    /**
     * @param codType
     *          the codType to set
     */
    public void setCodType(String codType) {
      this.codType = codType;
    }
  }

  /**
   * U1020130:新規受注(配送先設定)のサブモデルです。
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
    @Metadata(name = "アドレス呼称", order = 1)
    private String additionalAddressAlias;

    @Required
    @Length(20)
    @Metadata(name = "宛名：姓", order = 2)
    private String additionalAddressLastName;

    @Required
    @Length(20)
    @Metadata(name = "宛名：名", order = 3)
    private String additionalAddressFirstName;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "宛名姓カナ", order = 4)
    private String additionalAddressLastNameKana;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "宛名名カナ", order = 5)
    private String additionalAddressFirstNameKana;

    @Required
    @Length(7)
    @PostalCode
    @Metadata(name = "郵便番号", order = 6)
    private String additionalPostalCode;

    @Required
    @Length(2)
    @AlphaNum2
    @Metadata(name = "住所1(都道府県)", order = 8)
    private String additionalPrefectureCode;

    // @Required
    // @Length(50)
    @Metadata(name = "住所2(市区町村)", order = 13)
    private String additionalAddress2;

    /** 市コード */
    @Required
    @Length(3)
    @Metadata(name = "市コード", order = 9)
    private String additionalCityCode;

    @Length(50)
    @Metadata(name = "住所3(町名・番地)", order = 10)
    private String additionalAddress3;

    @Required
    @Length(100)
    @Metadata(name = "住所4(アパート・マンション・ビル)", order = 11)
    private String additionalAddress4;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号1")
    private String additionalPhoneNumber1;

    @Length(10)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号2")
    private String additionalPhoneNumber2;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号3")
    private String additionalPhoneNumber3;

    // Add by V10-CH start
    @Length(11)
    @MobileNumber
    @Metadata(name = "手机号码")
    private String additionalMobileNumber;

    // Add by V10-CH end

    // modify by V10-CH 170 start
    private List<CodeAttribute> additionalCityList = new ArrayList<CodeAttribute>();

    // modify by V10-CH 170 start

    /**
     * additionalAddress2を取得します。
     * 
     * @return additionalAddress2
     */
    public String getAdditionalAddress2() {
      return additionalAddress2;
    }

    /**
     * additionalAddress3を取得します。
     * 
     * @return additionalAddress3
     */
    public String getAdditionalAddress3() {
      return additionalAddress3;
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
     * additionalAddressFirstNameを取得します。
     * 
     * @return additionalAddressFirstName
     */
    public String getAdditionalAddressFirstName() {
      return additionalAddressFirstName;
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
     * additionalAddressLastNameを取得します。
     * 
     * @return additionalAddressLastName
     */
    public String getAdditionalAddressLastName() {
      return additionalAddressLastName;
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
     * additionaladditionalAddressAliasを取得します。
     * 
     * @return additionaladditionalAddressAlias
     */
    public String getAdditionalAddressAlias() {
      return additionalAddressAlias;
    }

    /**
     * additionalAddress2を設定します。
     * 
     * @param additionalAddress2
     *          additionalAddress2
     */
    public void setAdditionalAddress2(String additionalAddress2) {
      this.additionalAddress2 = additionalAddress2;
    }

    /**
     * additionalAddress3を設定します。
     * 
     * @param additionalAddress3
     *          additionalAddress3
     */
    public void setAdditionalAddress3(String additionalAddress3) {
      this.additionalAddress3 = additionalAddress3;
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
     * additionalAddressFirstNameを設定します。
     * 
     * @param additionalAddressFirstName
     *          additionalAddressFirstName
     */
    public void setAdditionalAddressFirstName(String additionalAddressFirstName) {
      this.additionalAddressFirstName = additionalAddressFirstName;
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
     * additionalAddressLastNameを設定します。
     * 
     * @param additionalAddressLastName
     *          additionalAddressLastName
     */
    public void setAdditionalAddressLastName(String additionalAddressLastName) {
      this.additionalAddressLastName = additionalAddressLastName;
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
     * additionalAddressAliasを設定します。
     * 
     * @param additionalAddressAlias
     *          additionalAddressAlias
     */
    public void setAdditionalAddressAlias(String additionalAddressAlias) {
      this.additionalAddressAlias = additionalAddressAlias;
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
     * additionalPhoneNumber1を取得します。
     * 
     * @return additionalPhoneNumber1
     */
    public String getAdditionalPhoneNumber1() {
      return additionalPhoneNumber1;
    }

    /**
     * additionalPhoneNumber1を設定します。
     * 
     * @param additionalPhoneNumber1
     *          additionalPhoneNumber1
     */
    public void setAdditionalPhoneNumber1(String additionalPhoneNumber1) {
      this.additionalPhoneNumber1 = additionalPhoneNumber1;
    }

    /**
     * additionalPhoneNumber2を取得します。
     * 
     * @return additionalPhoneNumber2
     */
    public String getAdditionalPhoneNumber2() {
      return additionalPhoneNumber2;
    }

    /**
     * additionalPhoneNumber2を設定します。
     * 
     * @param additionalPhoneNumber2
     *          additionalPhoneNumber2
     */
    public void setAdditionalPhoneNumber2(String additionalPhoneNumber2) {
      this.additionalPhoneNumber2 = additionalPhoneNumber2;
    }

    /**
     * additionalPhoneNumber3を取得します。
     * 
     * @return additionalPhoneNumber3
     */
    public String getAdditionalPhoneNumber3() {
      return additionalPhoneNumber3;
    }

    /**
     * additionalPhoneNumber3を設定します。
     * 
     * @param additionalPhoneNumber3
     *          additionalPhoneNumber3
     */
    public void setAdditionalPhoneNumber3(String additionalPhoneNumber3) {
      this.additionalPhoneNumber3 = additionalPhoneNumber3;
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

    public List<CodeAttribute> getAdditionalCityList() {
      return additionalCityList;
    }

    public void setAdditionalCityList(List<CodeAttribute> additionalCityList) {
      this.additionalCityList = additionalCityList;
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

  }

  /**
   * U1020130:お届け先設定のサブモデルです。
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

    /** 住所2(市区町村) */
    private String address2;

    /** 住所3(町名・番地) */
    private String address3;

    /** 住所4(アパート・マンション・ビル) */
    private String address4;

    /** 電話番号 */
    @Phone
    private String phoneNumber;

    // Add by V10-CH start
    @MobileNumber
    private String mobileNumber;

    // Add by V10-CH end

    // modify by V10-CH 170 start
    private String cityCode;

    private String areaCode;

    // modify by V10-CH 170 start

    public String getCityCode() {
      return cityCode;
    }

    public void setCityCode(String cityCode) {
      this.cityCode = cityCode;
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
   * cartTotalCommodityPriceを取得します。
   * 
   * @return cartTotalCommodityPrice
   */
  public String getCartTotalCommodityPrice() {
    return cartTotalCommodityPrice;
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
   * customerNameを取得します。
   * 
   * @return customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * deliveryListを取得します。
   * 
   * @return deliveryList
   */
  public List<DeliveryBean> getDeliveryList() {
    return deliveryList;
  }

  /**
   * cartTotalCommodityPriceを設定します。
   * 
   * @param cartTotalCommodityPrice
   *          cartTotalCommodityPrice
   */
  public void setCartTotalCommodityPrice(String cartTotalCommodityPrice) {
    this.cartTotalCommodityPrice = cartTotalCommodityPrice;
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
   * customerNameを設定します。
   * 
   * @param customerName
   *          customerName
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * deliveryListを設定します。
   * 
   * @param deliveryList
   *          deliveryList
   */
  public void setDeliveryList(List<DeliveryBean> deliveryList) {
    this.deliveryList = deliveryList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

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
   * dispAddressListを取得します。
   * 
   * @return dispAddressList
   */
  public List<CodeAttribute> getDispAddressList() {
    return dispAddressList;
  }

  /**
   * dispAddressListを設定します。
   * 
   * @param dispAddressList
   *          dispAddressList
   */
  public void setDispAddressList(List<CodeAttribute> dispAddressList) {
    this.dispAddressList = dispAddressList;
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
   * displayAdditionalAddressを取得します。
   * 
   * @return displayAdditionalAddress
   */
  public boolean isDisplayAdditionalAddress() {
    return displayAdditionalAddress;
  }

  /**
   * displayAdditionalAddressを設定します。
   * 
   * @param displayAdditionalAddress
   *          displayAdditionalAddress
   */
  public void setDisplayAdditionalAddress(boolean displayAdditionalAddress) {
    this.displayAdditionalAddress = displayAdditionalAddress;
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

  /**
   * addSelectCommodityListを取得します。
   * 
   * @return addSelectCommodityList
   */
  public List<CodeAttribute> getAddSelectCommodityList() {
    return addSelectCommodityList;
  }

  /**
   * addSelectCommodityListを設定します。
   * 
   * @param addSelectCommodityList
   *          addSelectCommodityList
   */
  public void setAddSelectCommodityList(List<CodeAttribute> addSelectCommodityList) {
    this.addSelectCommodityList = addSelectCommodityList;
  }

  /**
   * addAddressCheckListを取得します。
   * 
   * @return addAddressCheckList
   */
  public List<AddAddressBean> getAddAddressCheckList() {
    return addAddressCheckList;
  }

  /**
   * addAddressCheckListを設定します。
   * 
   * @param addAddressCheckList
   *          addAddressCheckList
   */
  public void setAddAddressCheckList(List<AddAddressBean> addAddressCheckList) {
    this.addAddressCheckList = addAddressCheckList;
  }

  /**
   * addCommodityKeyを取得します。
   * 
   * @return addCommodityKey
   */
  public String getAddCommodityKey() {
    return addCommodityKey;
  }

  /**
   * addCommodityKeyを設定します。
   * 
   * @param addCommodityKey
   *          addCommodityKey
   */
  public void setAddCommodityKey(String addCommodityKey) {
    this.addCommodityKey = addCommodityKey;
  }

  /**
   * addAddressNoListを取得します。
   * 
   * @return addAddressNoList
   */
  public List<String> getAddAddressNoList() {
    return addAddressNoList;
  }

  /**
   * addAddressNoListを設定します。
   * 
   * @param addAddressNoList
   *          addAddressNoList
   */
  public void setAddAddressNoList(List<String> addAddressNoList) {
    this.addAddressNoList = addAddressNoList;
  }

  /**
   * @return the shippingAddress
   */
  public String getShippingAddress() {
    return shippingAddress;
  }

  /**
   * @param shippingAddress
   *          the shippingAddress to set
   */
  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  /**
   * @return the orderInvoice
   */
  public InvoiceBean getOrderInvoice() {
    return orderInvoice;
  }

  /**
   * @param orderInvoice
   *          the orderInvoice to set
   */
  public void setOrderInvoice(InvoiceBean orderInvoice) {
    this.orderInvoice = orderInvoice;
  }

  /**
   * @return the invoiceCommodityNameList
   */
  public List<CodeAttribute> getInvoiceCommodityNameList() {
    return invoiceCommodityNameList;
  }

  /**
   * @param invoiceCommodityNameList
   *          the invoiceCommodityNameList to set
   */
  public void setInvoiceCommodityNameList(List<CodeAttribute> invoiceCommodityNameList) {
    this.invoiceCommodityNameList = invoiceCommodityNameList;
  }

  /**
   * @return the orderPayment
   */
  public PaymentMethodBean getOrderPayment() {
    return orderPayment;
  }

  /**
   * @param orderPayment
   *          the orderPayment to set
   */
  public void setOrderPayment(PaymentMethodBean orderPayment) {
    this.orderPayment = orderPayment;
  }

  /**
   * @return the discountTypeList
   */
  public List<CodeAttribute> getDiscountTypeList() {
    return discountTypeList;
  }

  /**
   * @param discountTypeList
   *          the discountTypeList to set
   */
  public void setDiscountTypeList(List<CodeAttribute> discountTypeList) {
    this.discountTypeList = discountTypeList;
  }

  /**
   * @return the personalCouponList
   */
  public List<CodeAttribute> getPersonalCouponList() {
    return personalCouponList;
  }

  /**
   * @param personalCouponList
   *          the personalCouponList to set
   */
  public void setPersonalCouponList(List<CodeAttribute> personalCouponList) {
    this.personalCouponList = personalCouponList;
  }

  /**
   * @return the selDiscountType
   */
  public String getSelDiscountType() {
    return selDiscountType;
  }

  /**
   * @param selDiscountType
   *          the selDiscountType to set
   */
  public void setSelDiscountType(String selDiscountType) {
    this.selDiscountType = selDiscountType;
  }

  /**
   * @return the selPersonalCouponCode
   */
  public String getSelPersonalCouponCode() {
    return selPersonalCouponCode;
  }

  /**
   * @param selPersonalCouponCode
   *          the selPersonalCouponCode to set
   */
  public void setSelPersonalCouponCode(String selPersonalCouponCode) {
    this.selPersonalCouponCode = selPersonalCouponCode;
  }

  /**
   * @return the selCodType
   */
  public String getSelCodType() {
    return selCodType;
  }

  /**
   * @param selCodType
   *          the selCodType to set
   */
  public void setSelCodType(String selCodType) {
    this.selCodType = selCodType;
  }

  /**
   * @return the selPrefectureCode
   */
  public String getSelPrefectureCode() {
    return selPrefectureCode;
  }

  /**
   * @param selPrefectureCode
   *          the selPrefectureCode to set
   */
  public void setSelPrefectureCode(String selPrefectureCode) {
    this.selPrefectureCode = selPrefectureCode;
  }

  /**
   * @return the publicCouponCode
   */
  public String getPublicCouponCode() {
    return publicCouponCode;
  }

  /**
   * @param publicCouponCode
   *          the publicCouponCode to set
   */
  public void setPublicCouponCode(String publicCouponCode) {
    this.publicCouponCode = publicCouponCode;
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
   * @return the deliveryCompanyList
   */
  public List<DeliveryCompanyBean> getDeliveryCompanyList() {
    return deliveryCompanyList;
  }

  /**
   * @param deliveryCompanyList
   *          the deliveryCompanyList to set
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
   * @param selectedDeliveryCompany
   *          the selectedDeliveryCompany to set
   */
  public void setSelectedDeliveryCompany(DeliveryCompanyBean selectedDeliveryCompany) {
    this.selectedDeliveryCompany = selectedDeliveryCompany;
  }

  
  /**
   * @return the customerGroupCampaign
   */
  public CustomerGroupCampaign getCustomerGroupCampaign() {
    return customerGroupCampaign;
  }

  
  /**
   * @param customerGroupCampaign the customerGroupCampaign to set
   */
  public void setCustomerGroupCampaign(CustomerGroupCampaign customerGroupCampaign) {
    this.customerGroupCampaign = customerGroupCampaign;
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

}
