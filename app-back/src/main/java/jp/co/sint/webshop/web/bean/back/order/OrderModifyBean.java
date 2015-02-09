package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.BankCode;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.PostalCode;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.back.order.PaymentSupporterFactory;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020230:受注修正のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private OrderHeaderBean orderHeaderEdit = new OrderHeaderBean();

  private AddCommodityBean addCommodityEdit = new AddCommodityBean();

  private List<ShippingHeaderBean> shippingHeaderList = new ArrayList<ShippingHeaderBean>();

  private OrderContainer oldOrderContainer;

  /** 支払方法関連情報をまとめたもの */
  private PaymentMethodBean orderPayment;

  /** 配送公司関連情報をまとめたもの */
  private List<DeliveryCompanyBean> deliveryCompanyList;

  private PointBean pointInfo = new PointBean();

  private TotalPrice afterTotalPrice = new TotalPrice();

  private TotalPrice beforeTotalPrice = new TotalPrice();

  private List<String> useCouponList = new ArrayList<String>();

  private List<CustomerCoupon> customerCoupon = new ArrayList<CustomerCoupon>();

  // add by V10-CH start
  private CouponInfoBean couponInfoBean = new CouponInfoBean();
  // add by V10-CH end

  // 共通情報 from
  @Metadata(name = "受注番号")
  private String orderNo;

  @Metadata(name = "ショップコード")
  private String shopCode;

  @Metadata(name = "データ連携ステータス")
  private String orderDataTransportFlg;

  private boolean reserveFlg;
  // M17N 10361 追加 ここから
  private boolean phantomOrder;
  
  private boolean phantomReservation;
  // M17N 10361 追加 ここまで
  
  // 共通情報 to

  private boolean displayUpdateButton;

  private String displayMode;

  /** 出荷情報設定画面 shipping / 支払設定画面 payment / 確認画面 confirm */
  private String operationMode;

  private Date updateDatetime;

  private OrderStatus orderStatus;

  /** ポイント情報表示フラグ */
  private boolean pointPartDisplayFlg = false;

  // add by v10-ch start
  private String couponFunctionEnabledFlg;
  // add by v10-ch end

  //soukai add 2012/01/09 ob start
  private String addressScript;
  
  //发票信息
  private InvoiceBean orderInvoice = new InvoiceBean();
  
  //商品规格
  private List<CodeAttribute> invoiceCommodityNameList = new ArrayList<CodeAttribute>();
  
  //原优惠信息
  private CashierDiscount orgCashierDiscount = new CashierDiscount();
  //新优惠信息
  private CashierDiscount newCashierDiscount = new CashierDiscount();
  
  //优惠可能类型
  private List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();
  //个人优惠券
  private List<CodeAttribute> personalCouponList = new ArrayList<CodeAttribute>();
  //优惠类型
  private String selDiscountType;
  //个人优惠券编号
  private String selPersonalCouponCode;
  //公共优惠券
  private String publicCouponCode;
  
  private String useOrgDiscount;
  
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "优惠金额", order = 2)
  private String discountPrice;
  
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "运费", order = 1)
  private String shippingCharge;
  
  private boolean upadatePaymentFlg = false;
  
  private String orderFlg;
  //soukai add 2012/01/09 ob end
  
  private Long mobileComputerType;
  
  private String useAgentStr;
  
  private String orderClientType;
  
  private BigDecimal giftCardUsePrice;
  
  //2012/11/22 促销活动 ob add start
  private String advertCode;
  
  private List<ShippingDetailBean> giftList = new ArrayList<ShippingDetailBean>();
  
  private ShippingDetailBean setCommodityInfo;
  
  private Map<String,Long> orgStockMap = new HashMap<String,Long>();
  //2012/11/22 促销活动 ob add end
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
    reqparam.copy(addCommodityEdit);
    // 数量情報を更新する指定がある場合のみ
    boolean updateAddress = false;
    // 2012/11/29 ob add start
    boolean addSetFlg = false;
    boolean updatePayment = false;
    // 2012/11/29 ob add end
    reqparam.copy(orderHeaderEdit);
    for (String str : reqparam.getPathArgs()) {
      if (str.equals("update_payment")) {
        updateAddress = true;
        updatePayment = true;
      }
      // 2012/11/29 ob add start
      if (str.equals("add_set")) {
    	  addSetFlg = true;
      }
      // 2012/11/29 ob add end
    }
    // 数量情報を更新する指定がある場合のみ
    boolean updateAmount = false;
    for (String str : reqparam.getPathArgs()) {
      if (str.equals("update_amount")) {
        updateAmount = true;
      }
    }

    if (updateAddress) {
      reqparam.copy(orderHeaderEdit.getAddress());
      orderHeaderEdit.getAddress().setPhoneNumber1(reqparam.get("phoneNumber_1"));
      orderHeaderEdit.getAddress().setPhoneNumber2(reqparam.get("phoneNumber_2"));
      orderHeaderEdit.getAddress().setPhoneNumber3(reqparam.get("phoneNumber_3"));
      orderHeaderEdit.getAddress().setMobileNumber(reqparam.get("mobileNumber"));
      if (StringUtil.hasValueAllOf(orderHeaderEdit.getAddress().getPhoneNumber1(), orderHeaderEdit.getAddress().getPhoneNumber2(),
          orderHeaderEdit.getAddress().getPhoneNumber3())) {
        orderHeaderEdit.getAddress().setPhoneNumber(
            StringUtil.joint('-', orderHeaderEdit.getAddress().getPhoneNumber1(), orderHeaderEdit.getAddress().getPhoneNumber2(),
                orderHeaderEdit.getAddress().getPhoneNumber3()));
      } else if (StringUtil.hasValueAllOf(orderHeaderEdit.getAddress().getPhoneNumber1(), orderHeaderEdit.getAddress()
          .getPhoneNumber2())) {
        orderHeaderEdit.getAddress().setPhoneNumber(
            StringUtil.joint('-', orderHeaderEdit.getAddress().getPhoneNumber1(), orderHeaderEdit.getAddress().getPhoneNumber2()));
      } else {
        orderHeaderEdit.getAddress().setPhoneNumber("");
      }

      // Add by V10-CH start
      if (StringUtil.hasValueAnyOf(orderHeaderEdit.getAddress().getMobileNumber())) {
        orderHeaderEdit.getAddress().setMobileNumber(orderHeaderEdit.getAddress().getMobileNumber());
      } else {
        orderHeaderEdit.getAddress().setMobileNumber("");
      }
      // Add by V10-Ch end
      
      //add by lc 2012-03-08 start
      orderHeaderEdit.getAddress().setLastName(StringUtil.parse(reqparam.get("lastName")));
      //add by lc 2012-03-08 end
    }

    if (updateAddress || updateAmount) {
      String[] shippingShopCodeList = reqparam.getAll("shippingShopCode");
      for (int i = 0; i < shippingShopCodeList.length; i++) {
        String shopCodeTmp = shippingShopCodeList[i];
        String addressNoTmp = reqparam.getAll("addressNo")[i];
        String deliveryTypeTmp = reqparam.getAll("shippingTypeCode")[i];

        ShippingHeaderBean header = getShippingHeaderBean(shopCodeTmp, addressNoTmp, deliveryTypeTmp);
        if (header != null) {
          if (header.getAddress() == null) {
            header.setAddress(new ShippingAddress());
          }
          header.getAddress().setPrefectureCode(reqparam.getAll("shippingPrefecture")[i]);
          PrefectureCode prefecture = PrefectureCode.fromValue(reqparam.getAll("shippingPrefecture")[i]);
          if (prefecture != null) {
            header.getAddress().setAddress1(prefecture.getName());
          }
          // header.getAddress().setAddress2(reqparam.getAll("shippingAddress2")[i]);
          //header.getAddress().setAddress3(reqparam.getAll("shippingAddress3")[i]);
          header.getAddress().setAreaCode(reqparam.getAll("shippingAreaCode")[i]);
          header.getAddress().setAddress4(StringUtil.parse(reqparam.getAll("shippingAddress4")[i]));
          header.getAddress().setFirstName(reqparam.getAll("shippingFirstName")[i]);
          header.getAddress().setFirstNameKana(reqparam.getAll("shippingFirstNameKana")[i]);
          header.getAddress().setLastName(StringUtil.parse(reqparam.getAll("shippingLastName")[i]));
          header.getAddress().setLastNameKana(reqparam.getAll("shippingLastNameKana")[i]);
          header.getAddress().setPostalCode(reqparam.getAll("shippingPostalCode")[i]);
          String shippingTel1 = reqparam.getAll("shippingTel_1")[i];
          String shippingTel2 = reqparam.getAll("shippingTel_2")[i];
          String shippingTel3 = reqparam.getAll("shippingTel_3")[i];
          header.getAddress().setPhoneNumber1(shippingTel1);
          // modify by V10-CH 170 start
          header.getAddress().setPhoneNumber2(shippingTel2);
          header.getAddress().setPhoneNumber3(shippingTel3);
          header.getAddress().setCityCode(reqparam.getAll("shippingCityCode")[i]);
          // modify by V10-CH 170 end
          // Add by V10-CH start
          String shippingMobile = reqparam.getAll("shippingMobile")[i];
          header.getAddress().setMobileNumber(shippingMobile);
          // Add by V10-CH end
          if (StringUtil.hasValueAllOf(shippingTel1, shippingTel2, shippingTel3)) {
            header.getAddress().setPhoneNumber(StringUtil.joint('-', shippingTel1, shippingTel2, shippingTel3));
          } else if (StringUtil.hasValueAllOf(shippingTel1, shippingTel2)) {
            header.getAddress().setPhoneNumber(StringUtil.joint('-', shippingTel1, shippingTel2));
          } else {
            header.getAddress().setPhoneNumber("");
          }
          // Add by V10-CH start
          if (StringUtil.hasValueAnyOf(shippingMobile)) {
            header.getAddress().setMobileNumber(shippingMobile);
          } else {
            header.getAddress().setMobileNumber("");
          }
          header.setDeliveryRemark(reqparam.getAll("deliveryRemark")[i]);
        }
      }
    }

    if (updateAmount) {
      String updateShopNo = reqparam.getPathArgs()[0];
      String updateAddressNo = reqparam.getPathArgs()[1];
      String updateDeliveryTypeNo = reqparam.getPathArgs()[2];
      String[] commodityShippingDetailNoList = reqparam.getAll("commodityShippingDetailNo");
      ShippingHeaderBean header = getShippingHeaderBean(updateShopNo, updateAddressNo, updateDeliveryTypeNo);
      for (int i = 0; i < commodityShippingDetailNoList.length; i++) {
        String detailNo = commodityShippingDetailNoList[i];
        for (ShippingDetailBean detail : header.getShippingDetailList()) {
          if (detail.getDetailNo().equals(detailNo)) {
            detail.setShippingDetailCommodityAmount(reqparam.getAll("shippingDetailCommodityAmount")[i]);
            detail.setSkuCode(reqparam.getAll("shippingDetailSkuCode")[i]);
          }
        }
      }
    }
    // ポイント情報取得
    String usePoint = pointInfo.getUsePoint();
    reqparam.copy(pointInfo);
    if (reqparam.get("recomputeFlg").equals("false")) {
      pointInfo.setUsePoint(usePoint);
    }

    //soukai add 2012/01/10 ob start
    boolean updateDelivery = false;
    for (String str : reqparam.getPathArgs()) {
      if (str.equals("update_delivery")) {
    	  updateDelivery = true;
      }
    }
    if (updateDelivery) {
    	for (ShippingHeaderBean header :getShippingHeaderList()) {
    		header.setDeliveryAppointedDate(reqparam.get("deliveryAppointedDate"));
    		header.setDeliveryAppointedTime(reqparam.get("deliveryAppointedTime"));
    		header.setDeliveryAppointedStartTime("");
    		header.setDeliveryAppointedEndTime("");
    		// add by lc 2012-08-24 start
  		 if (!StringUtil.isNullOrEmpty(reqparam.get("deliveryCompanyCode"))) {
  		   header.setDeliveryCompanyNo(reqparam.get("deliveryCompanyCode"));
         DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
         DeliveryCompany deliveryCompany = dao.load(header.getDeliveryCompanyNo());
         header.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());  
  	    }
        // add by lc 2012-08-24 end
    			if (StringUtil.isNullOrEmpty(header.getDeliveryAppointedTime()) || header.getDeliveryAppointedTime().equals(AppointedTimeType.NOT.getValue())) {
    				header.setDeliveryAppointedStartTime("");
    				header.setDeliveryAppointedEndTime("");
    		      } else {
    		        String[] timeZone = header.getDeliveryAppointedTime().split("-");
    		        if (timeZone.length > 1) {
    		        	header.setDeliveryAppointedStartTime(timeZone[0]);
    		        	header.setDeliveryAppointedEndTime(timeZone[1]);
    		        } else if (header.getDeliveryAppointedTime().endsWith("-")) {
    		        	header.setDeliveryAppointedStartTime(header.getDeliveryAppointedTime().replace("-", ""));
    		        	header.setDeliveryAppointedEndTime("");
    		        } else {
    		        	header.setDeliveryAppointedStartTime("");
    		        	header.setDeliveryAppointedEndTime(header.getDeliveryAppointedTime().replace("-", ""));
    		        }
    		      }
    	}
    }
    if ("payment".equals(this.getOperationMode())) {
    	//发票信息获得
        orderInvoice = new InvoiceBean();
        orderInvoice.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
        if (StringUtil.hasValue(reqparam.get("invoiceFlg"))) {
        	orderInvoice.setInvoiceFlg(reqparam.get("invoiceFlg"));
        	if (InvoiceFlg.NEED.getValue().equals(orderInvoice.getInvoiceFlg())) {
        		orderInvoice.setInvoiceCommodityName(reqparam.get("invoiceCommodityName"));
        		orderInvoice.setInvoiceType(reqparam.get("invoiceType"));
        		if (InvoiceType.USUAL.getValue().equals(orderInvoice.getInvoiceType())) {
        		  orderInvoice.setInvoiceCustomerName(StringUtil.parse(reqparam.get("invoiceCustomerName")));
        		} else if (InvoiceType.VAT.getValue().equals(orderInvoice.getInvoiceType())) {
        			orderInvoice.setInvoiceCompanyName(StringUtil.parse(reqparam.get("invoiceCompanyName")));
        			orderInvoice.setInvoiceTaxpayerCode(reqparam.get("invoiceTaxpayerCode"));
        			orderInvoice.setInvoiceAddress(StringUtil.parse(reqparam.get("invoiceAddress")));
        			orderInvoice.setInvoiceTel(reqparam.get("invoiceTel"));
        			orderInvoice.setInvoiceBankName(StringUtil.parse(reqparam.get("invoiceBankName")));
        			orderInvoice.setInvoiceBankNo(reqparam.get("invoiceBankNo"));
        			orderInvoice.setInvoiceSaveFlg(reqparam.get("invoiceSaveFlg"));
        			
        		}
        	}
        }
        this.setOrderInvoice(orderInvoice);
        this.setUseOrgDiscount(reqparam.get("useOrgDiscount"));
    }
    boolean updateDiscount = false;
    for (String str : reqparam.getPathArgs()) {
      if (str.equals("update_discount")) {
    	  updateDiscount = true;
      }
    }
    if (updateDiscount) {
    	this.setSelDiscountType(reqparam.get("selDiscountType"));
    	this.setSelPersonalCouponCode(reqparam.get("selPersonalCouponCode"));
    	this.setPublicCouponCode(reqparam.get("publicCouponCode"));
    }
    //soukai add 2012/01/10 ob end
    setUseCouponList(Arrays.asList(reqparam.getAll("useCouponList")));
    // 支払情報取得
    PaymentSupporterFactory.createPaymentSuppoerter().copyOrderPaymentMethod(reqparam, orderPayment);
    
    this.setDiscountPrice(reqparam.get("discountPrice"));
    
    this.setShippingCharge(reqparam.get("shippingCharge"));
    
    if (StringUtil.hasValue(reqparam.get("orderFlg"))) {
      this.setOrderFlg(reqparam.get("orderFlg"));
    }
    if (addSetFlg && this.getSetCommodityInfo()!=null) {
    	for (int i = 0;i<getSetCommodityInfo().getCompositionList().size();i++) {
    		getSetCommodityInfo().getCompositionList().get(i).setSkuCode(reqparam.get("set_skuCode_" + String.valueOf(i)));
    	}
    }
    if (updatePayment || updateAmount){
    	for (ShippingHeaderBean headerBean : this.getShippingHeaderList()){
    		for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()){
    			if (!StringUtil.hasValue(detailBean.getCampaignName())){
    				detailBean.setCampaignCode(reqparam.get("campaignCode_"+detailBean.getSkuCode()));
    			}
    		}
    	}
    }
  }


  /**
   * アドレスNoと配送種別コードから出荷情報を取得する。<BR>
   * 該当データが存在しない場合Nullを返す。
   * 
   * @param strShopCode
   *          ショップコード
   * @param strAddressNo
   *          アドレスNo
   * @param deliveryTypeCode
   *          配送種別コード
   * @return 出荷情報
   */
  public ShippingHeaderBean getShippingHeaderBean(String strShopCode, String strAddressNo, String deliveryTypeCode) {
    for (ShippingHeaderBean header : this.getShippingHeaderList()) {
      if (header.getShippingShopCode().equals(strShopCode) && header.getShippingAddressNo().equals(NumUtil.toLong(strAddressNo))
          && header.getShippingTypeCode().equals(deliveryTypeCode)) {
        return header;
      }
    }
    return null;
  }

  /**
   * 商品情報連番から出荷情報に存在する商品情報を取得する。<BR>
   * 該当データが存在しない場合Nullを返す。
   * 
   * @param detailNo
   *          商品情報連番
   * @return 商品情報
   */
  public ShippingDetailBean getShippingDetailBean(String detailNo) {
    ShippingDetailBean detailBean = null;

    for (ShippingHeaderBean header : getShippingHeaderList()) {
      for (ShippingDetailBean detail : header.getShippingDetailList()) {
        if (detailNo.equals(detail.getDetailNo())) {
          detailBean = detail;
        }
      }
    }
    return detailBean;
  }

  /**
   * アドレスNoと配送種別コードとSKUコードから出荷情報に存在する商品情報を取得する。<BR>
   * 該当データが存在しない場合Nullを返す。
   * 
   * @param strShopCode
   *          ショップコード
   * @param addressNo
   *          アドレスNo
   * @param deliveryTypeCode
   *          配送種別コード
   * @param skuCode
   *          SKUコード
   * @return 商品情報
   */
  public ShippingDetailBean getShippingDetailBean(String strShopCode, String addressNo, String deliveryTypeCode, String skuCode, String campaignCode) {
    ShippingDetailBean detailBean = null;

    ShippingHeaderBean header = getShippingHeaderBean(strShopCode, addressNo, deliveryTypeCode);
    if (header != null) {
      detailBean = getShippingDetailBean(header, skuCode,campaignCode);
    }
    return detailBean;
  }

  /**
   * 出荷ヘッダとSKUコードから出荷情報に存在する商品情報を取得する。<BR>
   * 該当データが存在しない場合Nullを返す。
   * 
   * @param header
   *          出荷ヘッダ
   * @param skuCode
   *          SKUコード
   * @return 商品情報
   */
  public ShippingDetailBean getShippingDetailBean(ShippingHeaderBean header, String skuCode, String campaignCode) {
    for (ShippingDetailBean detail : header.getShippingDetailList()) {
      if (SetCommodityFlg.OBJECTIN.longValue().equals(detail.getSetCommodityFlg())) {
        if (detail.getSetCommoditySkuCode().equals(skuCode)) {
          return detail;
        }
      }
      if (CommodityType.GIFT.longValue().equals(detail.getCommodityType())) {
        if (detail.getSkuCode().equals(skuCode) && detail.getCampaignCode().equals(campaignCode)) {
          return detail;
        }
      }
    }
    for (ShippingDetailBean detail : header.getShippingDetailList()) {
      if (detail.getSkuCode().equals(skuCode)) {
        return detail;
      }
    }
    return null;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020230";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.OrderModifyBean.0");
  }

  /**
   * U1020230:受注修正のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderHeaderBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Metadata(name = "顧客コード")
    private String customerCode;

    @Metadata(name = "顧客グループコード", order = 2)
    private String customerGroupCode;

    @Metadata(name = "ゲストフラグ", order = 3)
    private String guestFlg;

    private PaymentAddress address = new PaymentAddress();

    @Length(200)
    @Metadata(name = "連絡事項", order = 3)
    private String message;

    @Length(200)
    @Metadata(name = "注意事項（管理側のみ参照）", order = 4)
    private String caution;

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
     * customerGroupCodeを取得します。
     * 
     * @return customerGroupCode
     */
    public String getCustomerGroupCode() {
      return customerGroupCode;
    }

    /**
     * customerGroupCodeを設定します。
     * 
     * @param customerGroupCode
     *          customerGroupCode
     */
    public void setCustomerGroupCode(String customerGroupCode) {
      this.customerGroupCode = customerGroupCode;
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
     * addressを取得します。
     * 
     * @return address
     */
    public PaymentAddress getAddress() {
      return address;
    }

    /**
     * addressを設定します。
     * 
     * @param address
     *          address
     */
    public void setAddress(PaymentAddress address) {
      this.address = address;
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

  }

  /**
   * U1020230:受注修正のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AddCommodityBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Metadata(name = "アドレス一覧")
    private List<CodeAttribute> addressList = new ArrayList<CodeAttribute>();

    //soukai delete 2012/01/09 ob start
    //@Required
    //soukai delete 2012/01/09 ob end
    @Metadata(name = "配送先", order = 1)
    private String addressListCode;

    private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

    @Required
    @Metadata(name = "ショップ", order = 2)
    private String shopListCode;

    @Required
    @Length(24)
    @AlphaNum2
    @Metadata(name = "SKUコード", order = 3)
    private String skuCode;

    @Required
    @Length(4)
    @Range(min = 1, max = 9999)
    @Metadata(name = "数量", order = 4)
    private String addAmount;

    private boolean displayAddressList;

    /**
     * addAmountを取得します。
     * 
     * @return addAmount
     */
    public String getAddAmount() {
      return addAmount;
    }

    /**
     * addressListCodeを取得します。
     * 
     * @return addressListCode
     */
    public String getAddressListCode() {
      return addressListCode;
    }

    /**
     * shopListCodeを取得します。
     * 
     * @return shopListCode
     */
    public String getShopListCode() {
      return shopListCode;
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
     * addAmountを設定します。
     * 
     * @param addAmount
     *          addAmount
     */
    public void setAddAmount(String addAmount) {
      this.addAmount = addAmount;
    }

    /**
     * addressListを設定します。
     * 
     * @param addressListCode
     *          addressListCode
     */
    public void setAddressListCode(String addressListCode) {
      this.addressListCode = addressListCode;
    }

    /**
     * shopListを設定します。
     * 
     * @param shopListCode
     *          shopListCode
     */
    public void setShopListCode(String shopListCode) {
      this.shopListCode = shopListCode;
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
     * addressListを取得します。
     * 
     * @return addressList
     */
    public List<CodeAttribute> getAddressList() {
      return addressList;
    }

    /**
     * shopListを取得します。
     * 
     * @return shopList
     */
    public List<CodeAttribute> getShopList() {
      return shopList;
    }

    /**
     * addressListを設定します。
     * 
     * @param addressList
     *          addressList
     */
    public void setAddressList(List<CodeAttribute> addressList) {
      this.addressList = addressList;
    }

    /**
     * shopListを設定します。
     * 
     * @param shopList
     *          shopList
     */
    public void setShopList(List<CodeAttribute> shopList) {
      this.shopList = shopList;
    }

    /**
     * displayAddressListを取得します。
     * 
     * @return displayAddressList
     */
    public boolean isDisplayAddressList() {
      return displayAddressList;
    }

    /**
     * displayAddressListを設定します。
     * 
     * @param displayAddressList
     *          displayAddressList
     */
    public void setDisplayAddressList(boolean displayAddressList) {
      this.displayAddressList = displayAddressList;
    }
  }

  /**
   * U1020230:受注修正のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShippingHeaderBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();

    @Length(16)
    @Digit
    @Metadata(name = "出荷番号", order = 1)
    private String shippingNo;

    @Length(8)
    @Digit
    @Metadata(name = "アドレス帳番号", order = 2)
    private Long shippingAddressNo;

    private ShippingAddress address;

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

    private String shippingCharge;

    private String shippingChargeTaxType;

    private String shippingChargeTaxTypeName;

    @Length(500)
    @Metadata(name = "配送先備考", order = 21)
    private String deliveryRemark;

    private boolean displayDeliveryAppointedDate;

    private boolean displayDeliveryAppointedTime;

    private boolean deletable;

    private boolean modified;
    
    private String shippingDirectDate; // 10.1.6 10276 追加 

    // soukai add 2012/01/10 ob start
    //配送可能日期
    private List<CodeAttribute> deliveryAppointedDateList = new ArrayList<CodeAttribute>();
    //配送可能时间段
    private List<CodeAttribute> deliveryAppointedTimeList = new ArrayList<CodeAttribute>();
    
    //配送时间段
    private String deliveryAppointedTime;
    
    //配送公司
    private String deliveryCompanyName;
    
    private String deliveryCompanyNo;
    
    // soukai add 2012/01/10 ob end
   
    // 2012/11/28 促销活动 ob add start
    public static class GiftInfo implements Serializable {
    	 /** serial version uid */
        private static final long serialVersionUID = 1L;
        
        private ShippingDetail shippingDetail;
        
        private String commodityName;
        
        private BigDecimal weight;

        private String standardName1;
        
        private String standardName2;
        
		public ShippingDetail getShippingDetail() {
			return shippingDetail;
		}

		public void setShippingDetail(ShippingDetail shippingDetail) {
			this.shippingDetail = shippingDetail;
		}

		public String getCommodityName() {
			return commodityName;
		}

		public void setCommodityName(String commodityName) {
			this.commodityName = commodityName;
		}

		public BigDecimal getWeight() {
			return weight;
		}

		public void setWeight(BigDecimal weight) {
			this.weight = weight;
		}

		public String getStandardName1() {
			return standardName1;
		}

		public void setStandardName1(String standardName1) {
			this.standardName1 = standardName1;
		}

		public String getStandardName2() {
			return standardName2;
		}

		public void setStandardName2(String standardName2) {
			this.standardName2 = standardName2;
		}
        
    }
    // 2012/11/28 促销活动 ob add end
    /**
     * U1020230:受注修正のサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class ShippingDetailBean implements Serializable {

      /** serial version uid */
      private static final long serialVersionUID = 1L;

      /** DBに登録されている出荷番号 新たに登録したものはnull* */
      private Long shippingDetailNo;

      private List<CodeAttribute> shippingDetailSkuList = new ArrayList<CodeAttribute>();

      private HashMap<String, ShippingSku> shippingDetailSkuMap = new HashMap<String, ShippingSku>();

      @Required
      @Length(24)
      @AlphaNum2
      @Metadata(name = "SKUコード", order = 1)
      private String skuCode;

      private List<GiftAttribute> shippingDetailGiftList = new ArrayList<GiftAttribute>();

      @Length(16)
      @AlphaNum2
      @Metadata(name = "ギフト", order = 2)
      private String giftCode;

      private String orgGiftCode;

      @Required
      @Digit
      @Length(4)
      @Range(min = 1, max = 9999)
      @Metadata(name = "数量", order = 3)
      private String shippingDetailCommodityAmount;

      //soukai add 2012/01/09 ob start
      private BigDecimal weigth;
      //soukai add 2012/01/09 ob end
      private Long orgShippingDetailCommodityAmount;

      /** 商品情報判定用主キー */
      private String detailNo;

      private boolean reserve;

      private OrderDetail orderDetailCommodityInfo;
      
      //2012/11/20 促销活动 ob add start
      //促销活动编号
      @Length(16)
      @AlphaNum2
      private String campaignCode;
      //促销活动名称
      private String campaignName;
      //折扣类型
      private String discountType;
      //折扣值
      private BigDecimal discountValue;
      //商品区分
      private Long commodityType;
      //套餐商品区分
      private Long setCommodityFlg;
      //赠品信息
      private List<GiftInfo> giftCommodityList = new ArrayList<GiftInfo>();
      //套餐明细
      private List<SetCommpositionInfo> compositionList = new ArrayList<SetCommpositionInfo>();
      
      private boolean displayCampaignFlg = false;
      
      private boolean isDiscountCommodity = false;

      //获得套餐原价
      public BigDecimal getSetTotalPrice() {
    	  BigDecimal totalPrice = BigDecimal.ZERO;
    	  for (SetCommpositionInfo info : compositionList) {
    	    for (CommodityDetail commodityDetail:info.getCommodityDetailList()) {
    	      if (info.getRepresentSkuCode().equals(commodityDetail.getSkuCode())) {
    	        totalPrice=totalPrice.add(commodityDetail.getUnitPrice());
    	        break;
    	      }
    	    }
    	  }
    	  return totalPrice;
      }
      //获得套餐优惠价格
      public BigDecimal getSetDiscountPrice() {
    	  return getSetTotalPrice().subtract(this.getOrderDetailCommodityInfo().getRetailPrice());
      }
      
      public String getSetCommodityName() {
    	  String setCommodityName = "";
    	  for (SetCommpositionInfo info : compositionList) {
    		if (StringUtil.hasValue(setCommodityName))  {
    			setCommodityName = setCommodityName + " + ";
    		}
    		setCommodityName= setCommodityName + getSetCompositionName(info.getComposition());
    	  }
    	  return setCommodityName;
      }
      private String getSetCompositionName(ShippingDetailComposition composition) {
    	  String compositionName = "";
    	  compositionName = composition.getChildSkuCode() + composition.getCommodityName();
    	  if (StringUtil.hasValue(composition.getStandardDetail1Name()) && StringUtil.hasValue(composition.getStandardDetail2Name())) {
    		  compositionName = compositionName + "(" + composition.getStandardDetail1Name() + "/" + composition.getStandardDetail2Name() + ")";
		    } else if (StringUtil.hasValue(composition.getStandardDetail1Name()) && StringUtil.isNullOrEmpty(composition.getStandardDetail2Name())) {
		    	compositionName = compositionName + "(" + composition.getStandardDetail1Name() + ")";
		    } 
    	  return compositionName;
      }
      public String getSetCommoditySkuCode(){
    	  String skuCode = this.getSkuCode();
    	  for (SetCommpositionInfo info : compositionList) {
    		  skuCode = skuCode + "_" + info.getSkuCode();
    	  }
    	  return skuCode;
      }
      //2012/11/20 促销活动 ob add end

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

      /**
       * 新しいインスタンスを生成します。
       */
      public ShippingDetailBean() {

      }

      /**
       * 新しいインスタンスを生成します。
       * 
       * @param detailNo
       */
      public ShippingDetailBean(String detailNo) {
        this.detailNo = detailNo;
      }

      /**
       * shippingDetailCommodityAmountを取得します。
       * 
       * @return shippingDetailCommodityAmount
       */
      public String getShippingDetailCommodityAmount() {
        return shippingDetailCommodityAmount;
      }

      /**
       * shippingDetailCommodityAmountを設定します。
       * 
       * @param shippingDetailCommodityAmount
       *          shippingDetailCommodityAmount
       */
      public void setShippingDetailCommodityAmount(String shippingDetailCommodityAmount) {
        this.shippingDetailCommodityAmount = shippingDetailCommodityAmount;
      }

      /**
       * shippingDetailGiftListを取得します。
       * 
       * @return shippingDetailGiftList
       */
      public List<GiftAttribute> getShippingDetailGiftList() {
        return shippingDetailGiftList;
      }

      /**
       * shippingDetailSkuListを取得します。
       * 
       * @return shippingDetailSkuList
       */
      public List<CodeAttribute> getShippingDetailSkuList() {
        return shippingDetailSkuList;
      }

      /**
       * shippingDetailGiftListを設定します。
       * 
       * @param shippingDetailGiftList
       *          shippingDetailGiftList
       */
      public void setShippingDetailGiftList(List<GiftAttribute> shippingDetailGiftList) {
        this.shippingDetailGiftList = shippingDetailGiftList;
      }

      /**
       * shippingDetailSkuListを設定します。
       * 
       * @param shippingDetailSkuList
       *          shippingDetailSkuList
       */
      public void setShippingDetailSkuList(List<CodeAttribute> shippingDetailSkuList) {
        this.shippingDetailSkuList = shippingDetailSkuList;
      }

      /**
       * detailNoを取得します。
       * 
       * @return detailNo
       */
      public String getDetailNo() {
        return detailNo;
      }

      /**
       * detailNoを設定します。
       * 
       * @param detailNo
       *          detailNo
       */
      public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
      }

      /**
       * orgShippingDetailCommodityAmountを取得します。
       * 
       * @return orgShippingDetailCommodityAmount
       */
      public Long getOrgShippingDetailCommodityAmount() {
        return orgShippingDetailCommodityAmount;
      }

      /**
       * orgShippingDetailCommodityAmountを設定します。
       * 
       * @param orgShippingDetailCommodityAmount
       *          orgShippingDetailCommodityAmount
       */
      public void setOrgShippingDetailCommodityAmount(Long orgShippingDetailCommodityAmount) {
        this.orgShippingDetailCommodityAmount = orgShippingDetailCommodityAmount;
      }

      /**
       * orderDetailCommodityInfoを取得します。
       * 
       * @return orderDetailCommodityInfo
       */
      public OrderDetail getOrderDetailCommodityInfo() {
        return orderDetailCommodityInfo;
      }

      /**
       * orderDetailCommodityInfoを設定します。
       * 
       * @param orderDetailCommodityInfo
       *          orderDetailCommodityInfo
       */
      public void setOrderDetailCommodityInfo(OrderDetail orderDetailCommodityInfo) {
        this.orderDetailCommodityInfo = orderDetailCommodityInfo;
      }

      /**
       * shippingDetailNoを取得します。
       * 
       * @return shippingDetailNo
       */
      public Long getShippingDetailNo() {
        return shippingDetailNo;
      }

      /**
       * shippingDetailNoを設定します。
       * 
       * @param shippingDetailNo
       *          shippingDetailNo
       */
      public void setShippingDetailNo(Long shippingDetailNo) {
        this.shippingDetailNo = shippingDetailNo;
      }

      /**
       * orgGiftCodeを取得します。
       * 
       * @return orgGiftCode
       */
      public String getOrgGiftCode() {
        return orgGiftCode;
      }

      /**
       * orgGiftCodeを設定します。
       * 
       * @param orgGiftCode
       *          orgGiftCode
       */
      public void setOrgGiftCode(String orgGiftCode) {
        this.orgGiftCode = orgGiftCode;
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
       * shippingDetailSkuMapを取得します。
       * 
       * @return shippingDetailSkuMap
       */
      public HashMap<String, ShippingSku> getShippingDetailSkuMap() {
        return shippingDetailSkuMap;
      }

      /**
       * shippingDetailSkuMapを設定します。
       * 
       * @param shippingDetailSkuMap
       *          shippingDetailSkuMap
       */
      public void setShippingDetailSkuMap(HashMap<String, ShippingSku> shippingDetailSkuMap) {
        CollectionUtil.copyAll(this.shippingDetailSkuMap, shippingDetailSkuMap);
      }

	/**
	 * @return the weigth
	 */
	public BigDecimal getWeigth() {
		return weigth;
	}

	/**
	 * @param weigth the weigth to set
	 */
	public void setWeigth(BigDecimal weigth) {
		this.weigth = weigth;
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

  
  public String getDiscountType() {
    return discountType;
  }

  
  public void setDiscountType(String discountType) {
    this.discountType = discountType;
  }

  
  public BigDecimal getDiscountValue() {
    return discountValue;
  }

  
  public void setDiscountValue(BigDecimal discountValue) {
    this.discountValue = discountValue;
  }

  
  public Long getCommodityType() {
    return commodityType;
  }

  
  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  
  public Long getSetCommodityFlg() {
    return setCommodityFlg;
  }

  
  public void setSetCommodityFlg(Long setCommodityType) {
    this.setCommodityFlg = setCommodityType;
  }

  
  public List<GiftInfo> getGiftCommodityList() {
    return giftCommodityList;
  }

  
  public void setGiftCommodityList(List<GiftInfo> giftCommodityList) {
    this.giftCommodityList = giftCommodityList;
  }

  
  public List<SetCommpositionInfo> getCompositionList() {
    return compositionList;
  }

  
  public void setCompositionList(List<SetCommpositionInfo> compositionList) {
    this.compositionList = compositionList;
  }
public boolean isDisplayCampaignFlg() {
	return displayCampaignFlg;
}
public void setDisplayCampaignFlg(boolean displayCampaignFlg) {
	this.displayCampaignFlg = displayCampaignFlg;
}

/**
 * @return the isDiscountCommodity
 */
public boolean isDiscountCommodity() {
  return isDiscountCommodity;
}

/**
 * @param isDiscountCommodity the isDiscountCommodity to set
 */
public void setDiscountCommodity(boolean isDiscountCommodity) {
  this.isDiscountCommodity = isDiscountCommodity;
}

    }

    /**
     * shippingAddressNoを取得します。
     * 
     * @return shippingAddressNo
     */
    public Long getShippingAddressNo() {
      return shippingAddressNo;
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
     * shippingAddressNoを設定します。
     * 
     * @param shippingAddressNo
     *          shippingAddressNo
     */
    public void setShippingAddressNo(Long shippingAddressNo) {
      this.shippingAddressNo = shippingAddressNo;
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
     * @return the deliveryRemark
     */
    public String getDeliveryRemark() {
      return deliveryRemark;
    }

    /**
     * deliveryRemarkを設定します。
     * 
     * @param deliveryRemark
     *          the deliveryRemark to set
     */
    public void setDeliveryRemark(String deliveryRemark) {
      this.deliveryRemark = deliveryRemark;
    }

    /**
     * deliveryAppointedEndTimeを取得します。
     * 
     * @return the deliveryAppointedEndTime
     */
    public String getDeliveryAppointedEndTime() {
      return deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedEndTimeを設定します。
     * 
     * @param deliveryAppointedEndTime
     *          the deliveryAppointedEndTime to set
     */
    public void setDeliveryAppointedEndTime(String deliveryAppointedEndTime) {
      this.deliveryAppointedEndTime = deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedStartTimeを取得します。
     * 
     * @return the deliveryAppointedStartTime
     */
    public String getDeliveryAppointedStartTime() {
      return deliveryAppointedStartTime;
    }

    /**
     * deliveryAppointedStartTimeを設定します。
     * 
     * @param deliveryAppointedStartTime
     *          the deliveryAppointedStartTime to set
     */
    public void setDeliveryAppointedStartTime(String deliveryAppointedStartTime) {
      this.deliveryAppointedStartTime = deliveryAppointedStartTime;
    }

    /**
     * shippingChargeTaxTypeNameを取得します。
     * 
     * @return the shippingChargeTaxTypeName
     */
    public String getShippingChargeTaxTypeName() {
      return shippingChargeTaxTypeName;
    }

    /**
     * shippingChargeTaxTypeNameを設定します。
     * 
     * @param shippingChargeTaxTypeName
     *          the shippingChargeTaxTypeName to set
     */
    public void setShippingChargeTaxTypeName(String shippingChargeTaxTypeName) {
      this.shippingChargeTaxTypeName = shippingChargeTaxTypeName;
    }

    /**
     * addressを取得します。
     * 
     * @return address
     */
    public ShippingAddress getAddress() {
      return address;
    }

    /**
     * addressを設定します。
     * 
     * @param address
     *          address
     */
    public void setAddress(ShippingAddress address) {
      this.address = address;
    }

    /**
     * /** displayDeliveryAppointedDateを取得します。
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
     * deletableを取得します。
     * 
     * @return deletable
     */
    public boolean isDeletable() {
      return deletable;
    }

    /**
     * deletableを設定します。
     * 
     * @param deletable
     *          deletable
     */
    public void setDeletable(boolean deletable) {
      this.deletable = deletable;
    }

    /**
     * modifiedを取得します。
     * 
     * @return modified
     */
    public boolean isModified() {
      return modified;
    }

    /**
     * modifiedを設定します。
     * 
     * @param modified
     *          modified
     */
    public void setModified(boolean modified) {
      this.modified = modified;
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

    
    public String getShippingDirectDate() {
      return shippingDirectDate;
    }

    
    public void setShippingDirectDate(String shippingDirectDate) {
      this.shippingDirectDate = shippingDirectDate;
    }

	/**
	 * @return the deliveryAppointedDateList
	 */
	public List<CodeAttribute> getDeliveryAppointedDateList() {
		return deliveryAppointedDateList;
	}

	/**
	 * @param deliveryAppointedDateList the deliveryAppointedDateList to set
	 */
	public void setDeliveryAppointedDateList(
			List<CodeAttribute> deliveryAppointedDateList) {
		this.deliveryAppointedDateList = deliveryAppointedDateList;
	}

	/**
	 * @return the deliveryAppointedTimeList
	 */
	public List<CodeAttribute> getDeliveryAppointedTimeList() {
		return deliveryAppointedTimeList;
	}

	/**
	 * @param deliveryAppointedTimeList the deliveryAppointedTimeList to set
	 */
	public void setDeliveryAppointedTimeList(
			List<CodeAttribute> deliveryAppointedTimeList) {
		this.deliveryAppointedTimeList = deliveryAppointedTimeList;
	}

	/**
	 * @return the deliveryAppointedTime
	 */
	public String getDeliveryAppointedTime() {
		return deliveryAppointedTime;
	}

	/**
	 * @param deliveryAppointedTime the deliveryAppointedTime to set
	 */
	public void setDeliveryAppointedTime(String deliveryAppointedTime) {
		this.deliveryAppointedTime = deliveryAppointedTime;
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

  /**
   * U1020230:受注修正のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class TotalPrice implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private BigDecimal commodityPrice;

    private BigDecimal giftPrice;

    private BigDecimal shippingCharge;

    private BigDecimal allPrice;

    private BigDecimal paymentCommission;

    private BigDecimal grandAllPrice;
    
    private BigDecimal allPointPaymentPoint;
    
    private BigDecimal outCardUsePrice = BigDecimal.ZERO;

    private boolean isNotPointInFull;

    //soukai add 2012/01/10 ob start
    private BigDecimal discountPrice = BigDecimal.ZERO;
    //soukai add 2012/01/10 ob end
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

    /**
     * paymentCommissionを取得します。
     * 
     * @return paymentCommission
     */
    public BigDecimal getPaymentCommission() {
      return paymentCommission;
    }

    /**
     * paymentCommissionを設定します。
     * 
     * @param paymentCommission
     *          paymentCommission
     */
    public void setPaymentCommission(BigDecimal paymentCommission) {
      this.paymentCommission = paymentCommission;
    }

    /**
     * allPriceを取得します。
     * 
     * @return allPrice
     */
    public BigDecimal getAllPrice() {
      return allPrice;
    }

    /**
     * allPriceを設定します。
     * 
     * @param allPrice
     *          allPrice
     */
    public void setAllPrice(BigDecimal allPrice) {
      this.allPrice = allPrice;
    }

    /**
     * commodityPriceを取得します。
     * 
     * @return commodityPrice
     */
    public BigDecimal getCommodityPrice() {
      return commodityPrice;
    }

    /**
     * commodityPriceを設定します。
     * 
     * @param commodityPrice
     *          commodityPrice
     */
    public void setCommodityPrice(BigDecimal commodityPrice) {
      this.commodityPrice = commodityPrice;
    }

    /**
     * giftPriceを取得します。
     * 
     * @return giftPrice
     */
    public BigDecimal getGiftPrice() {
      return giftPrice;
    }

    /**
     * giftPriceを設定します。
     * 
     * @param giftPrice
     *          giftPrice
     */
    public void setGiftPrice(BigDecimal giftPrice) {
      this.giftPrice = giftPrice;
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
     * grandAllPriceを取得します。
     * 
     * @return grandAllPrice
     */
    public BigDecimal getGrandAllPrice() {
      return grandAllPrice;
    }

    /**
     * grandAllPriceを設定します。
     * 
     * @param grandAllPrice
     *          grandAllPrice
     */
    public void setGrandAllPrice(BigDecimal grandAllPrice) {
      this.grandAllPrice = grandAllPrice;
    }

    
    public BigDecimal getAllPointPaymentPoint() {
      return allPointPaymentPoint;
    }

    
    public void setAllPointPaymentPoint(BigDecimal allPointPaymentPoint) {
      this.allPointPaymentPoint = allPointPaymentPoint;
    }

	/**
	 * @return the discountPrice
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @param discountPrice the discountPrice to set
	 */
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

  
  /**
   * @return the outCardUsePrice
   */
  public BigDecimal getOutCardUsePrice() {
    return outCardUsePrice;
  }

  
  /**
   * @param outCardUsePrice the outCardUsePrice to set
   */
  public void setOutCardUsePrice(BigDecimal outCardUsePrice) {
    this.outCardUsePrice = outCardUsePrice;
  }
  }

  // add by V10-CH start
  public static class CouponInfoBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    public BigDecimal beforeCouponPrice;

    public BigDecimal afterCouponPrice;
    
    private List<CouponBean> afterCouponList = new ArrayList<CouponBean>();

    private List<CouponBean> beforeCouponList = new ArrayList<CouponBean>();

    public List<CouponBean> getAfterCouponList() {
      return afterCouponList;
    }

    public void setAfterCouponList(List<CouponBean> afterCouponList) {
      this.afterCouponList = afterCouponList;
    }

    public List<CouponBean> getBeforeCouponList() {
      return beforeCouponList;
    }

    public void setBeforeCouponList(List<CouponBean> beforeCouponList) {
      this.beforeCouponList = beforeCouponList;
    }

    public BigDecimal getAfterCouponPrice() {
      return afterCouponPrice;
    }

    public void setAfterCouponPrice(BigDecimal afterCouponPrice) {
      this.afterCouponPrice = afterCouponPrice;
    }

    public BigDecimal getBeforeCouponPrice() {
      return beforeCouponPrice;
    }

    public void setBeforeCouponPrice(BigDecimal beforeCouponPrice) {
      this.beforeCouponPrice = beforeCouponPrice;
    }

    public void clearAfterCouponSelect() {
      for (CouponBean cb : afterCouponList) {
        cb.setSelectCouponId("");
      }
    }
  }

  // add by V10-CH end

  /**
   * U1020230:受注修正のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PaymentAddress implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(20)
    @Metadata(name = "氏名(姓)", order = 1)
    private String lastName;

    @Required
    @Length(20)
    @Metadata(name = "氏名(名)", order = 2)
    private String firstName;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(姓)", order = 3)
    private String lastNameKana;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(名)", order = 4)
    private String firstNameKana;

    @Required
    @Length(256)
    @Email
    @Metadata(name = "メールアドレス", order = 5)
    private String customerEmail;

    @Required
    @Length(7)
    @PostalCode
    @Metadata(name = "郵便番号", order = 6)
    private String postalCode;

    @Required
    @Length(2)
    @AlphaNum2
    @Metadata(name = "都道府県", order = 7)
    private String prefectureCode;

    @Metadata(name = "都道府県", order = 8)
    private String address1;

    /** 市コード */
    @Required
    @Length(3)
    @Metadata(name = "市コード", order = 9)
    private String cityCode;

    // @Required
    @Length(50)
    @Metadata(name = "市区町村", order = 17)
    private String address2;

    
    @Length(50)
    @Metadata(name = "町名・番地", order = 10)
    private String address3;

    @Required
    @Length(100)
    @Metadata(name = "アパート・マンション", order = 11)
    private String address4;

    @Length(4)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号1", order = 12)
    private String phoneNumber1;

    //@Length(8)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号2", order = 13)
    private String phoneNumber2;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号3", order = 14)
    private String phoneNumber3;

    @Length(20)
    @Phone
    @Metadata(name = "電話番号", order = 15)
    private String phoneNumber;

    // Add by V10-CH start
    @MobileNumber
    @Metadata(name = "手机号码", order = 16)
    private String mobileNumber;

    // Add by V10-CH end

    private String recentEmail;

    @Phone
    private String recentPhoneNumber;

    // Add by V10-CH start
    @MobileNumber
    private String recentMobileNumber;

    // Add by V10-CH end

    // modify by V10-CH 170 start
    private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();

    // modify by V10-CH 170 end

    //soukai add 2012/01/09 ob start
    private String areaCode;
    
    private List<CodeAttribute> addressPrefectureList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> addressCityList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> addressAreaList = new ArrayList<CodeAttribute>();
    //soukai add 2012/01/09 ob end
    
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
     * address2を取得します。
     * 
     * @return address2
     */
    public String getAddress2() {
      return address2;
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
     * address3を取得します。
     * 
     * @return address3
     */
    public String getAddress3() {
      return address3;
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
     * address4を取得します。
     * 
     * @return address4
     */
    public String getAddress4() {
      return address4;
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
     * customerEmailを取得します。
     * 
     * @return customerEmail
     */
    public String getCustomerEmail() {
      return customerEmail;
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

    public List<CodeAttribute> getCityList() {
      return cityList;
    }

    public void setCityList(List<CodeAttribute> cityList) {
      this.cityList = cityList;
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
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the addressPrefectureList
	 */
	public List<CodeAttribute> getAddressPrefectureList() {
		return addressPrefectureList;
	}

	/**
	 * @param addressPrefectureList the addressPrefectureList to set
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
	 * @param addressCityList the addressCityList to set
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
	 * @param addressAreaList the addressAreaList to set
	 */
	public void setAddressAreaList(List<CodeAttribute> addressAreaList) {
		this.addressAreaList = addressAreaList;
	}

  }

  /**
   * U1020230:受注修正のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShippingAddress implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(20)
    @Metadata(name = "氏名(姓)", order = 1)
    private String lastName;

    @Required
    @Length(20)
    @Metadata(name = "氏名(名)", order = 2)
    private String firstName;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(姓)", order = 3)
    private String lastNameKana;

    @Required
    @Length(40)
    @Kana
    @Metadata(name = "氏名カナ(名)", order = 4)
    private String firstNameKana;

    @Required
    @Length(7)
    @PostalCode
    @Metadata(name = "郵便番号", order = 6)
    private String postalCode;

    @Required
    @Length(2)
    @AlphaNum2
    @Metadata(name = "都道府県", order = 7)
    private String prefectureCode;

    @Metadata(name = "都道府県", order = 8)
    private String address1;

    // @Required
    @Length(50)
    @Metadata(name = "市区町村", order = 9)
    private String address2;

    
    @Length(50)
    @Metadata(name = "町名・番地", order = 10)
    private String address3;

    @Required
    @Length(100)
    @Metadata(name = "アパート・マンション", order = 11)
    private String address4;

    @Length(4)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号1", order = 12)
    private String phoneNumber1;

    @Length(8)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号2", order = 13)
    private String phoneNumber2;

    @Length(6)
    // @Digit(allowNegative = false)
    @Metadata(name = "電話番号3", order = 14)
    private String phoneNumber3;

    // modify by V10-CH 170 start
    private List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();

    /** 市コード */
    @Required
    @Length(3)
    @Metadata(name = "市コード", order = 18)
    private String cityCode;

    // modify by V10-CH 170 end

    @Length(20)
    @Phone
    @Metadata(name = "電話番号", order = 16)
    private String phoneNumber;

    // Add by V10-CH start
    @Length(11)
    @MobileNumber
    @Metadata(name = "手机号码", order = 17)
    private String mobileNumber;

    private String recentMobileNumber;

    // Add by V10-CH end
    private String recentEmail;

    private String recentPhoneNumber;

    //soukai add 2012/01/12 ob start
    private String areaCode;
    
    private List<CodeAttribute> addressPrefectureList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> addressCityList = new ArrayList<CodeAttribute>();

    private List<CodeAttribute> addressAreaList = new ArrayList<CodeAttribute>();
    //soukai add 2012/01/12 ob end
    
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
     * address2を取得します。
     * 
     * @return address2
     */
    public String getAddress2() {
      return address2;
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
     * address3を取得します。
     * 
     * @return address3
     */
    public String getAddress3() {
      return address3;
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
     * address4を取得します。
     * 
     * @return address4
     */
    public String getAddress4() {
      return address4;
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

    // /**
    // * phoneNumber2を取得します。

    // *
    // * @return phoneNumber2
    // */
    public String getPhoneNumber2() {
      return phoneNumber2;
    }

    //
    // /**
    // * phoneNumber2を設定します。

    // *
    // * @param phoneNumber2
    // * phoneNumber2
    // */
    public void setPhoneNumber2(String phoneNumber2) {
      this.phoneNumber2 = phoneNumber2;
    }

    //
    // /**
    // * phoneNumber3を取得します。

    // *
    // * @return phoneNumber3
    // */
    public String getPhoneNumber3() {
      return phoneNumber3;
    }

    //
    // /**
    // * phoneNumber3を設定します。

    // *
    // * @param phoneNumber3
    // * phoneNumber3
    // */
    public void setPhoneNumber3(String phoneNumber3) {
      this.phoneNumber3 = phoneNumber3;
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

    public List<CodeAttribute> getCityList() {
      return cityList;
    }

    public void setCityList(List<CodeAttribute> cityList) {
      this.cityList = cityList;
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
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the addressPrefectureList
	 */
	public List<CodeAttribute> getAddressPrefectureList() {
		return addressPrefectureList;
	}

	/**
	 * @param addressPrefectureList the addressPrefectureList to set
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
	 * @param addressCityList the addressCityList to set
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
	 * @param addressAreaList the addressAreaList to set
	 */
	public void setAddressAreaList(List<CodeAttribute> addressAreaList) {
		this.addressAreaList = addressAreaList;
	}

  }

  /**
   * U1020230:受注修正のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PointBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String restPoint;

    private BigDecimal beforeUsePoint;

    @Required
    // @Precision(precision = 10, scale = 2)
    @Point
    @Metadata(name = "今回利用ポイント", order = 1)
    private String usePoint;

    /** ポイント差し引き額 */
    private BigDecimal totalPrice;

    /**
     * beforeUsePointを取得します。
     * 
     * @return beforeUsePoint
     */
    public BigDecimal getBeforeUsePoint() {
      return beforeUsePoint;
    }

    /**
     * beforeUsePointを設定します。
     * 
     * @param beforeUsePoint
     *          beforeUsePoint
     */
    public void setBeforeUsePoint(BigDecimal beforeUsePoint) {
      this.beforeUsePoint = beforeUsePoint;
    }

    /**
     * restPointを取得します。
     * 
     * @return restPoint
     */
    public String getRestPoint() {
      return restPoint;
    }

    /**
     * restPointを設定します。
     * 
     * @param restPoint
     *          restPoint
     */
    public void setRestPoint(String restPoint) {
      this.restPoint = restPoint;
    }

    /**
     * totalPriceを取得します。
     * 
     * @return totalPrice
     */
    public BigDecimal getTotalPrice() {
      return totalPrice;
    }

    /**
     * totalPriceを設定します。
     * 
     * @param totalPrice
     *          totalPrice
     */
    public void setTotalPrice(BigDecimal totalPrice) {
      this.totalPrice = totalPrice;
    }

    /**
     * usePointを取得します。
     * 
     * @return usePoint
     */
    public String getUsePoint() {
      return usePoint;
    }

    /**
     * usePointを設定します。
     * 
     * @param usePoint
     *          usePoint
     */
    public void setUsePoint(String usePoint) {
      this.usePoint = usePoint;
    }

    /**
     * 受注修正時にポイント利用額が不足しているかどうかを返します。
     * 
     * @return 今回使用分が「残高＋前回使用分(受注修正時に取り消され、戻ってくる)」より多ければtrue(不足している)を返します。
     */
    public boolean isShort() {
      BigDecimal useThisTime = NumUtil.parse(this.getUsePoint()); // 今回使用
      BigDecimal useLastTime = NumUtil.coalesce(this.getBeforeUsePoint(), BigDecimal.ZERO); // 前回使用
      BigDecimal rest = NumUtil.parse(this.getRestPoint()); // 現在の残高

      return ValidatorUtil.moreThan(useThisTime, rest.add(useLastTime));
    }
  }

  /**
   * addCommodityEditを取得します。
   * 
   * @return addCommodityEdit
   */
  public AddCommodityBean getAddCommodityEdit() {
    return addCommodityEdit;
  }

  /**
   * addCommodityEditを設定します。
   * 
   * @param addCommodityEdit
   *          addCommodityEdit
   */
  public void setAddCommodityEdit(AddCommodityBean addCommodityEdit) {
    this.addCommodityEdit = addCommodityEdit;
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
   * orderDataTransportFlgを取得します。
   * 
   * @return orderDataTransportFlg
   */
  public String getOrderDataTransportFlg() {
    return orderDataTransportFlg;
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
   * shippingHeaderListを取得します。
   * 
   * @return shippingHeaderList
   */
  public List<ShippingHeaderBean> getShippingHeaderList() {
    return shippingHeaderList;
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
   * orderNoを設定します。
   * 
   * @param orderNo
   *          orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * shippingHeaderListを設定します。
   * 
   * @param shippingHeaderList
   *          shippingHeaderList
   */
  public void setShippingHeaderList(List<ShippingHeaderBean> shippingHeaderList) {
    this.shippingHeaderList = shippingHeaderList;
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
   * displayUpdateButtonを取得します。
   * 
   * @return displayUpdateButton
   */
  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
  }

  /**
   * displayUpdateButtonを設定します。
   * 
   * @param displayUpdateButton
   *          displayUpdateButton
   */
  public void setDisplayUpdateButton(boolean displayUpdateButton) {
    this.displayUpdateButton = displayUpdateButton;
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
   * operationModeを取得します。
   * 
   * @return operationMode
   */
  public String getOperationMode() {
    return operationMode;
  }

  /**
   * operationModeを設定します。
   * 
   * @param operationMode
   *          operationMode
   */
  public void setOperationMode(String operationMode) {
    this.operationMode = operationMode;
  }

  /**
   * afterTotalPriceを取得します。
   * 
   * @return afterTotalPrice
   */
  public TotalPrice getAfterTotalPrice() {
    return afterTotalPrice;
  }

  /**
   * afterTotalPriceを設定します。
   * 
   * @param afterTotalPrice
   *          afterTotalPrice
   */
  public void setAfterTotalPrice(TotalPrice afterTotalPrice) {
    this.afterTotalPrice = afterTotalPrice;
  }

  /**
   * beforeTotalPriceを取得します。
   * 
   * @return beforeTotalPrice
   */
  public TotalPrice getBeforeTotalPrice() {
    return beforeTotalPrice;
  }

  /**
   * beforeTotalPriceを設定します。
   * 
   * @param beforeTotalPrice
   *          beforeTotalPrice
   */
  public void setBeforeTotalPrice(TotalPrice beforeTotalPrice) {
    this.beforeTotalPrice = beforeTotalPrice;
  }

  /**
   * pointInfoを取得します。
   * 
   * @return pointInfo
   */
  public PointBean getPointInfo() {
    return pointInfo;
  }

  /**
   * pointInfoを設定します。
   * 
   * @param pointInfo
   *          pointInfo
   */
  public void setPointInfo(PointBean pointInfo) {
    this.pointInfo = pointInfo;
  }

  /**
   * orderPaymentを取得します。
   * 
   * @return orderPayment
   */
  public PaymentMethodBean getOrderPayment() {
    return orderPayment;
  }

  /**
   * orderPaymentを設定します。
   * 
   * @param orderPayment
   *          orderPayment
   */
  public void setOrderPayment(PaymentMethodBean orderPayment) {
    this.orderPayment = orderPayment;
  }

  /**
   * orderStatusを取得します。
   * 
   * @return orderStatus
   */
  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  /**
   * orderStatusを設定します。
   * 
   * @param orderStatus
   *          orderStatus
   */
  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * pointPartDisplayFlgを取得します。
   * 
   * @return pointPartDisplayFlg
   */
  public boolean isPointPartDisplayFlg() {
    return pointPartDisplayFlg;
  }

  /**
   * pointPartDisplayFlgを設定します。
   * 
   * @param pointPartDisplayFlg
   *          pointPartDisplayFlg
   */
  public void setPointPartDisplayFlg(boolean pointPartDisplayFlg) {
    this.pointPartDisplayFlg = pointPartDisplayFlg;
  }

  /**
   * reserveFlgを取得します。
   * 
   * @return reserveFlg
   */
  public boolean isReserveFlg() {
    return reserveFlg;
  }

  /**
   * reserveFlgを設定します。
   * 
   * @param reserveFlg
   *          reserveFlg
   */
  public void setReserveFlg(boolean reserveFlg) {
    this.reserveFlg = reserveFlg;
  }

  //M17N 10361 追加 ここから
  /**
   * phantomOrderを取得します。
   * 
   * @return phantomOrder
   */
  public boolean isPhantomOrder() {
    return phantomOrder;
  }

  /**
   * phantomOrderを設定します。
   * 
   * @param phantomOrder
   *          phantomOrder
   */
  public void setPhantomOrder(boolean phantomOrder) {
    this.phantomOrder = phantomOrder;
  }

  /**
   * phantomReservationを取得します。
   * 
   * @return phantomReservation
   */
  public boolean isPhantomReservation() {
    return phantomReservation;
  }

  /**
   * phantomReservationを設定します。
   * 
   * @param phantomReservation
   *          phantomReservation
   */
  public void setPhantomReservation(boolean phantomReservation) {
    this.phantomReservation = phantomReservation;
  }
  // M17N 10361 追加 ここまで
  
  /**
   * oldOrderContainerを取得します。
   * 
   * @return oldOrderContainer
   */
  public OrderContainer getOldOrderContainer() {
    return oldOrderContainer;
  }

  /**
   * oldOrderContainerを設定します。
   * 
   * @param oldOrderContainer
   *          oldOrderContainer
   */
  public void setOldOrderContainer(OrderContainer oldOrderContainer) {
    this.oldOrderContainer = oldOrderContainer;
  }

  public static class GiftAttribute implements CodeAttribute, Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    /** ギフトの税込価格 */
    private BigDecimal giftPrice;

    private Long giftTaxType;

    private BigDecimal giftTax;

    private Long giftTaxRate;

    private String giftName;

    private boolean updatableGift;

    /** ドロップダウン表示用名前 */
    private String name;

    /** ギフトコード */
    private String value;

    public GiftAttribute() {

    }

    public GiftAttribute(String name, String value) {
      this.name = name;
      this.value = value;
    }

    /**
     * giftPriceを取得します。
     * 
     * @return giftPrice
     */
    public BigDecimal getGiftPrice() {
      return giftPrice;
    }

    /**
     * giftPriceを設定します。
     * 
     * @param giftPrice
     *          giftPrice
     */
    public void setGiftPrice(BigDecimal giftPrice) {
      this.giftPrice = giftPrice;
    }

    /**
     * nameを取得します。
     * 
     * @return name
     */
    public String getName() {
      return name;
    }

    /**
     * nameを設定します。
     * 
     * @param name
     *          name
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * updatableGiftを取得します。
     * 
     * @return updatableGift
     */
    public boolean isUpdatableGift() {
      return updatableGift;
    }

    /**
     * updatableGiftを設定します。
     * 
     * @param updatableGift
     *          updatableGift
     */
    public void setUpdatableGift(boolean updatableGift) {
      this.updatableGift = updatableGift;
    }

    /**
     * valueを取得します。
     * 
     * @return value
     */
    public String getValue() {
      return value;
    }

    /**
     * valueを設定します。
     * 
     * @param value
     *          value
     */
    public void setValue(String value) {
      this.value = value;
    }

    /**
     * giftNameを取得します。
     * 
     * @return giftName
     */
    public String getGiftName() {
      return giftName;
    }

    /**
     * giftNameを設定します。
     * 
     * @param giftName
     *          giftName
     */
    public void setGiftName(String giftName) {
      this.giftName = giftName;
    }

    /**
     * giftTaxTypeを取得します。
     * 
     * @return giftTaxType
     */
    public Long getGiftTaxType() {
      return giftTaxType;
    }

    /**
     * giftTaxTypeを設定します。
     * 
     * @param giftTaxType
     *          giftTaxType
     */
    public void setGiftTaxType(Long giftTaxType) {
      this.giftTaxType = giftTaxType;
    }

    /**
     * giftTaxを取得します。
     * 
     * @return giftTax
     */
    public BigDecimal getGiftTax() {
      return giftTax;
    }

    /**
     * giftTaxを設定します。
     * 
     * @param giftTax
     *          giftTax
     */
    public void setGiftTax(BigDecimal giftTax) {
      this.giftTax = giftTax;
    }

    /**
     * giftTaxRateを取得します。
     * 
     * @return giftTaxRate
     */
    public Long getGiftTaxRate() {
      return giftTaxRate;
    }

    /**
     * giftTaxRateを設定します。
     * 
     * @param giftTaxRate
     *          giftTaxRate
     */
    public void setGiftTaxRate(Long giftTaxRate) {
      this.giftTaxRate = giftTaxRate;
    }

  }

  public static class ShippingSku implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String skuCode;

    private String commodityName;

    private BigDecimal price;

    public ShippingSku() {
      this.skuCode = "";
      this.commodityName = "";
      this.price = BigDecimal.ZERO;
    }

    public ShippingSku(String skuCode, String commodityName, BigDecimal price) {
      this.skuCode = skuCode;
      this.commodityName = commodityName;
      this.price = price;
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
     * priceを取得します。
     * 
     * @return price
     */
    public BigDecimal getPrice() {
      return price;
    }

    /**
     * priceを設定します。
     * 
     * @param price
     *          price
     */
    public void setPrice(BigDecimal price) {
      this.price = price;
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

  public String getCouponFunctionEnabledFlg() {
    return couponFunctionEnabledFlg;
  }

  public void setCouponFunctionEnabledFlg(String couponFunctionEnabledFlg) {
    this.couponFunctionEnabledFlg = couponFunctionEnabledFlg;
  }

  // add by v10-ch start
  public static class CouponBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerCouponId;

    private String selectCouponId;

    private String couponName;

    private String couponPrice;

    private String useEndDate;

    public String getCouponName() {
      return couponName;
    }

    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    public String getCouponPrice() {
      return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
      this.couponPrice = couponPrice;
    }

    public String getCustomerCouponId() {
      return customerCouponId;
    }

    public void setCustomerCouponId(String customerCouponId) {
      this.customerCouponId = customerCouponId;
    }

    public String getSelectCouponId() {
      return selectCouponId;
    }

    public void setSelectCouponId(String selectCouponId) {
      this.selectCouponId = selectCouponId;
    }

    public String getUseEndDate() {
      return useEndDate;
    }

    public void setUseEndDate(String useEndDate) {
      this.useEndDate = useEndDate;
    }

  }

  // add by v10-ch end

  public List<String> getUseCouponList() {
    return useCouponList;
  }

  public void setUseCouponList(List<String> useCouponList) {
    this.useCouponList = useCouponList;
  }

  public List<CustomerCoupon> getCustomerCoupon() {
    return customerCoupon;
  }

  public void setCustomerCoupon(List<CustomerCoupon> customerCoupon) {
    this.customerCoupon = customerCoupon;
  }

  public CouponInfoBean getCouponInfoBean() {
    return couponInfoBean;
  }

  public void setCouponInfoBean(CouponInfoBean couponInfoBean) {
    this.couponInfoBean = couponInfoBean;
  }

/**
 * @return the addressScript
 */
public String getAddressScript() {
	return addressScript;
}

/**
 * @param addressScript the addressScript to set
 */
public void setAddressScript(String addressScript) {
	this.addressScript = addressScript;
}
//soukai add 2012/01/03 ob start
/**
 * 发票信息。
 * 
 * @author OB.
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

  private String invoiceTypeName;
  
  @Required
  @Length(20)
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
  @Length(60)
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
    if (StringUtil.isNullOrEmpty(invoiceFlg)) {
  	  return InvoiceFlg.NO_NEED.getValue();
    }
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
    if (StringUtil.isNullOrEmpty(invoiceType)) {
  	  return InvoiceType.USUAL.getValue();
    }
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

	/**
	 * @return the invoiceTypeName
	 */
	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	/**
	 * @param invoiceTypeName the invoiceTypeName to set
	 */
	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

}
//soukai add 2012/01/03 ob end

/**
 * @return the orderInvoice
 */
public InvoiceBean getOrderInvoice() {
	return orderInvoice;
}

/**
 * @param orderInvoice the orderInvoice to set
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
 * @param invoiceCommodityNameList the invoiceCommodityNameList to set
 */
public void setInvoiceCommodityNameList(
		List<CodeAttribute> invoiceCommodityNameList) {
	this.invoiceCommodityNameList = invoiceCommodityNameList;
}

/**
 * @return the orgCashierDiscount
 */
public CashierDiscount getOrgCashierDiscount() {
	return orgCashierDiscount;
}

/**
 * @param orgCashierDiscount the orgCashierDiscount to set
 */
public void setOrgCashierDiscount(CashierDiscount orgCashierDiscount) {
	this.orgCashierDiscount = orgCashierDiscount;
}

/**
 * @return the newCashierDiscount
 */
public CashierDiscount getNewCashierDiscount() {
	return newCashierDiscount;
}

/**
 * @param newCashierDiscount the newCashierDiscount to set
 */
public void setNewCashierDiscount(CashierDiscount newCashierDiscount) {
	this.newCashierDiscount = newCashierDiscount;
}

/**
 * @return the discountTypeList
 */
public List<CodeAttribute> getDiscountTypeList() {
	return discountTypeList;
}

/**
 * @param discountTypeList the discountTypeList to set
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
 * @param personalCouponList the personalCouponList to set
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
 * @param selDiscountType the selDiscountType to set
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
 * @param selPersonalCouponCode the selPersonalCouponCode to set
 */
public void setSelPersonalCouponCode(String selPersonalCouponCode) {
	this.selPersonalCouponCode = selPersonalCouponCode;
}

/**
 * @return the publicCouponCode
 */
public String getPublicCouponCode() {
	return publicCouponCode;
}

/**
 * @param publicCouponCode the publicCouponCode to set
 */
public void setPublicCouponCode(String publicCouponCode) {
	this.publicCouponCode = publicCouponCode;
}

/**
 * @return the useOrgDiscount
 */
public String getUseOrgDiscount() {
	return useOrgDiscount;
}

/**
 * @param useOrgDiscount the useOrgDiscount to set
 */
public void setUseOrgDiscount(String useOrgDiscount) {
	this.useOrgDiscount = useOrgDiscount;
}

/**
 * @return the discountPrice
 */
public String getDiscountPrice() {
	return discountPrice;
}

/**
 * @param discountPrice the discountPrice to set
 */
public void setDiscountPrice(String discountPrice) {
	this.discountPrice = discountPrice;
}

/**
 * @return the shippingCharge
 */
public String getShippingCharge() {
	return shippingCharge;
}

/**
 * @param shippingCharge the shippingCharge to set
 */
public void setShippingCharge(String shippingCharge) {
	this.shippingCharge = shippingCharge;
}

/**
 * @return the upadatePaymentFlg
 */
public boolean isUpadatePaymentFlg() {
	return upadatePaymentFlg;
}

/**
 * @param upadatePaymentFlg the upadatePaymentFlg to set
 */
public void setUpadatePaymentFlg(boolean upadatePaymentFlg) {
	this.upadatePaymentFlg = upadatePaymentFlg;
}

public String getOrderFlg() {
  return orderFlg;
}

public void setOrderFlg(String orderFlg) {
  this.orderFlg = orderFlg;
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


public String getAdvertCode() {
  return advertCode;
}


public void setAdvertCode(String advertCode) {
  this.advertCode = advertCode;
}

public List<ShippingDetailBean> getGiftList() {
	return giftList;
}

public void setGiftList(List<ShippingDetailBean> giftList) {
	this.giftList = giftList;
}

public ShippingDetailBean getSetCommodityInfo() {
	return setCommodityInfo;
}

public void setSetCommodityInfo(ShippingDetailBean setCommodityInfo) {
	this.setCommodityInfo = setCommodityInfo;
}

public Map<String, Long> getOrgStockMap() {
	return orgStockMap;
}

public void setOrgStockMap(Map<String, Long> orgStockMap) {
	this.orgStockMap = orgStockMap;
}

// 2012/11/28 ob add start
public static class SetCommpositionInfo implements Serializable {
	/** serial version uid */
	  private static final long serialVersionUID = 1L;
	  
	  private String commodityCode;
	  
	  private String representSkuCode;
	  
	  private String skuCode;
	  
	  private List<CodeAttribute> shippingDetailSkuList = new ArrayList<CodeAttribute>();
	  
	  private List<CommodityDetail> commodityDetailList = new ArrayList<CommodityDetail>();
	  
	  private ShippingDetailComposition composition = new ShippingDetailComposition();

	  private BigDecimal retailPrice;
	  
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public List<CodeAttribute> getShippingDetailSkuList() {
		return shippingDetailSkuList;
	}

	public void setShippingDetailSkuList(List<CodeAttribute> shippingDetailSkuList) {
		this.shippingDetailSkuList = shippingDetailSkuList;
	}

	public List<CommodityDetail> getCommodityDetailList() {
		return commodityDetailList;
	}

	public void setCommodityDetailList(List<CommodityDetail> commodityDetailList) {
		this.commodityDetailList = commodityDetailList;
	}

	public ShippingDetailComposition getComposition() {
		return composition;
	}

	public void setComposition(ShippingDetailComposition composition) {
		this.composition = composition;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

  /**
   * @return the representSkuCode
   */
  public String getRepresentSkuCode() {
    return representSkuCode;
  }

  /**
   * @param representSkuCode the representSkuCode to set
   */
  public void setRepresentSkuCode(String representSkuCode) {
    this.representSkuCode = representSkuCode;
  }
}
// 2012/11/28 ob add end


/**
 * @return the mobileComputerType
 */
public Long getMobileComputerType() {
  return mobileComputerType;
}


/**
 * @param mobileComputerType the mobileComputerType to set
 */
public void setMobileComputerType(Long mobileComputerType) {
  this.mobileComputerType = mobileComputerType;
}





/**
 * @return the orderClientType
 */
public String getOrderClientType() {
  return orderClientType;
}


/**
 * @param orderClientType the orderClientType to set
 */
public void setOrderClientType(String orderClientType) {
  this.orderClientType = orderClientType;
}


/**
 * @return the giftCardUsePrice
 */
public BigDecimal getGiftCardUsePrice() {
  return giftCardUsePrice;
}


/**
 * @param giftCardUsePrice the giftCardUsePrice to set
 */
public void setGiftCardUsePrice(BigDecimal giftCardUsePrice) {
  this.giftCardUsePrice = giftCardUsePrice;
}


/**
 * @return the useAgentStr
 */
public String getUseAgentStr() {
  return useAgentStr;
}


/**
 * @param useAgentStr the useAgentStr to set
 */
public void setUseAgentStr(String useAgentStr) {
  this.useAgentStr = useAgentStr;
}

}
