package jp.co.sint.webshop.web.action.front.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.ApplicableObject;
import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.data.domain.CodType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.UseFlagObject;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OptionalCommodity;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDelivery;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.service.cart.CashierInvoice;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.cart.CashierInvoice.CashierInvoiceBase;
import jp.co.sint.webshop.service.cart.impl.CartItemImpl;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.service.communication.FreePostageListSearchCondition;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.AddAddressBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.AddCommodityBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.Address;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.DeliveryCompanyBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean.CompositionBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean.CouponDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean.GiftBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2020120:お届け先設定のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class ShippingBaseAction extends OrderBaseAction<ShippingBean> {
  
  public void createOutCardPrice() {
    ShippingBean bean = getBean();
    BigDecimal outerCardRate = DIContainer.getWebshopConfig().getOuterCardRate();
    bean.getCashier().setOuterCardUseAmount(BigDecimal.ZERO);
    if (bean.getCashier().getPayment() != null && bean.getCashier().getPayment().getSelectPayment() != null ) {
      if (PaymentMethodType.OUTER_CARD.getValue().equals(bean.getCashier().getPayment().getSelectPayment().getPaymentMethodType())) {
        BigDecimal outPrice = bean.getCashier().getPaymentTotalPrice().divide(outerCardRate,5);
        outPrice = BigDecimalUtil.subtract(outPrice, bean.getCashier().getPaymentTotalPrice());
        bean.getCashier().setOuterCardUseAmount(outPrice);
      }
    }
  }

  /**
   * 注文情報から出荷に関する情報を生成
   * 
   * @return bean
   */
  public ShippingBean createShippingBeanFromCashier() {
    ShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();
    bean.setCustomerCode(cashier.getCustomer().getCustomerCode());
    bean.setPaymentUser(createAddress(cashier.getSelfAddress(), cashier.getCustomer().getEmail()));
    bean.setAddCommodityList(createAddCommodityList(cashier));
    bean.setAddSelectCommodityList(createAddSelectCommodityList(bean.getAddCommodityList()));
    bean.setOrderInvoice(createInvoice(cashier));

    if (bean.getAddAddressCheckList().size() == 0) {
      bean.setAddAddressCheckList(createAddressCheckList(cashier));
    }
    if (bean.getAddAddressSelectList().size() == 0) {
      bean.setAddAddressSelectList(createAddressSelectList(bean.getAddAddressCheckList()));
    }
    bean.setShippingList(createShippingList(cashier));

    BigDecimal totalAllPrice = BigDecimalUtil.add(cashier.getTotalCommodityPrice(), cashier.getTotalShippingCharge());
    BigDecimal couponPrice = BigDecimal.ZERO;

    // お支払い方法関連情報の取得
    bean.setOrderPayment(PaymentSupporterFactory.createPaymentSuppoerter().createPaymentMethodBean(cashier.getPayment(),
        totalAllPrice, BigDecimal.ZERO, false, couponPrice, cashier.getShippingList().get(0).getAddress().getAreaCode()));
    for (PaymentTypeBase paybase : bean.getOrderPayment().getDisplayPaymentList()) {
      if (PaymentMethodType.ALIPAY.getValue().equals(paybase.getPaymentMethodType())) {
        paybase.setPaymentMethodName(PaymentMethodType.ALIPAY.getName());
      }
      if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(paybase.getPaymentMethodType())) {
        paybase.setPaymentMethodName(PaymentMethodType.CASH_ON_DELIVERY.getName());
      }
      if (PaymentMethodType.CHINA_UNIONPAY.getValue().equals(paybase.getPaymentMethodType())) {
        paybase.setPaymentMethodName(PaymentMethodType.CHINA_UNIONPAY.getName());
      }
      if (PaymentMethodType.POSTRAL.getValue().equals(paybase.getPaymentMethodType())) {
        paybase.setPaymentMethodName(PaymentMethodType.POSTRAL.getName());
      }
      if (PaymentMethodType.OUTER_CARD.getValue().equals(paybase.getPaymentMethodType())) {
        paybase.setPaymentMethodName(PaymentMethodType.OUTER_CARD.getName());
      }
      if (PaymentMethodType.INNER_CARD.getValue().equals(paybase.getPaymentMethodType())) {
        paybase.setPaymentMethodName(PaymentMethodType.INNER_CARD.getName());
      }
    }


    bean.setDiscountTypeList(createDiscountTypeList(cashier));

    // 个人优惠券
    bean.setPersonalCouponList(createPersonalCouponList(cashier));
    bean.setSelPrefectureCode(cashier.getShippingList().get(0).getAddress().getPrefectureCode());
    bean.setSelCityCode(cashier.getShippingList().get(0).getAddress().getCityCode());
    bean.setSelAreaCode(cashier.getShippingList().get(0).getAddress().getAreaCode());

    if (bean.getDiscountTypeList().size() == 1 && bean.getPersonalCouponList().size() == 1) {
      bean.setDisplayCouponButton(false);
    } else {
      bean.setDisplayCouponButton(true);
    }

    // 公共优惠券存在验证
    bean.setDisplayPublicCouponButton(false);
    bean.setDisplayPublicCouponButton(true);
    createDeliveryToBean(bean);

    // 20131016 txw add start
    if (BigDecimalUtil.isAbove(bean.getCashier().getShippingList().get(0).getShippingCharge(), BigDecimal.ZERO)) {
      // 免邮活动验证
      String mediaCode = "";

      CommonSessionContainer commonSession = (CommonSessionContainer) getSessionContainer();
      if (commonSession.getSession().getAttribute(WebFrameworkConstants.ATTRIBUTE_FREE_MEDIA) != null) {
        mediaCode = commonSession.getSession().getAttribute(WebFrameworkConstants.ATTRIBUTE_FREE_MEDIA).toString();
      }

      if (StringUtil.hasValue(mediaCode)) {
        if (isFree(bean, mediaCode)) {
          bean.getShippingList().get(0).getShippingList().get(0).setShippingCharge(BigDecimal.ZERO);
          bean.getCashier().getShippingList().get(0).setShippingCharge(BigDecimal.ZERO);
          // 配送公司运费设置为0
          for (DeliveryCompanyBean dcb : bean.getDeliveryCompanyList()) {
            dcb.setDeliveryCharge(BigDecimal.ZERO);
          }
        }
      }
    } else {
      // 配送公司运费设置为0
      for (DeliveryCompanyBean dcb : bean.getDeliveryCompanyList()) {
        dcb.setDeliveryCharge(BigDecimal.ZERO);
      }
    }
    // 20131016 txw add end
    return bean;
  }

  /**
   * 住所生成
   * 
   * @param cashier
   * @return address
   */
  private Address createAddress(CustomerAddress customerAddress, String email) {
    Address address = new Address();
    address.setAddress1(customerAddress.getAddress1());
    address.setAddress2(customerAddress.getAddress2());
    address.setAddress3(customerAddress.getAddress3());
    address.setAddress4(customerAddress.getAddress4());
    address.setAddressNo(customerAddress.getAddressNo());
    address.setEmail(email);
    address.setFirstName(customerAddress.getAddressFirstName());
    address.setFirstNameKana(customerAddress.getAddressFirstNameKana());
    address.setLastName(customerAddress.getAddressLastName());
    address.setLastNameKana(customerAddress.getAddressLastNameKana());
    address.setPhoneNumber(customerAddress.getPhoneNumber());
    // Add by V10-CH start
    address.setMobileNumber(customerAddress.getMobileNumber());
    // Add by V10-CH end
    address.setPostCode(customerAddress.getPostalCode());
    // modify by V10-CH 170 start
    address.setCityCode(customerAddress.getCityCode());
    // modify by V10-CH 170 end
    return address;
  }

  /**
   * 商品追加情報生成
   * 
   * @param cashier
   * @return commodityList
   */
  private List<AddCommodityBean> createAddCommodityList(Cashier cashier) {
    List<AddCommodityBean> commodityList = new ArrayList<AddCommodityBean>();

    Long i = 0L;
    for (CartCommodityInfo useableCommodity : cashier.getUsableCommodity()) {
      AddCommodityBean commodity = new AddCommodityBean();
      commodity.setCommodityKey(NumUtil.toString(i));
      commodity.setShopCode(useableCommodity.getShopCode());
      commodity.setSkuCode(useableCommodity.getSkuCode());
      commodity.setDisplayName(getDisplayName(useableCommodity.getCommodityName(), useableCommodity.getStandardDetail1Name(),
          useableCommodity.getStandardDetail2Name()));
      commodityList.add(commodity);
      i++;
    }

    return commodityList;
  }

  /**
   * 商品追加情報生成(選択用)
   * 
   * @param addCommodityBean
   * @return commoditySelectList
   */
  private List<CodeAttribute> createAddSelectCommodityList(List<AddCommodityBean> addCommodityBean) {
    List<CodeAttribute> commoditySelectList = new ArrayList<CodeAttribute>();

    for (AddCommodityBean useableCommodity : addCommodityBean) {
      NameValue value = new NameValue(useableCommodity.getDisplayName(), useableCommodity.getCommodityKey());
      commoditySelectList.add(value);
    }

    return commoditySelectList;
  }

  /**
   * アドレス一覧生成(リスト用)
   * 
   * @param customerCode
   * @return addressList
   */
  public List<CodeAttribute> createAddressSelectList(List<AddAddressBean> addAddressList) {
    List<CodeAttribute> addressList = new ArrayList<CodeAttribute>();

    for (AddAddressBean address : addAddressList) {
      NameValue value = new NameValue(address.getAddressAlias(), address.getAddressNo());
      addressList.add(value);
    }

    return addressList;
  }

  /**
   * アドレス一覧生成(チェック用)
   * 
   * @param cashier
   * @return addressList
   */
  public List<AddAddressBean> createAddressCheckList(Cashier cashier) {
    List<AddAddressBean> addressList = new ArrayList<AddAddressBean>();

    if (getLoginInfo().isCustomer()) {
      String customerCode = cashier.getCustomer().getCustomerCode();
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

      for (CustomerAddress address : service.getCustomerAddressList(customerCode)) {
        AddAddressBean addAddress = new AddAddressBean();
        addAddress.setAddressNo(Long.toString(address.getAddressNo()));
        addAddress.setAddressAlias(address.getAddressAlias());
        addAddress.setAddressFirstName(address.getAddressFirstName());
        addAddress.setAddressFirstNameKana(address.getAddressFirstNameKana());
        addAddress.setAddressLastName(address.getAddressLastName());
        addAddress.setAddressLastNameKana(address.getAddressLastNameKana());
        addAddress.setPostalCode1(address.getPostalCode());
        addAddress.setPostalCode2("");

        addAddress.setPrefectureCode(address.getPrefectureCode());
        if (StringUtil.hasValue(address.getPostalCode())) {
          addAddress.setPostalCode1(address.getPostalCode());
        }
        addAddress.setAddress1(address.getAddress1());
        addAddress.setCityCode(address.getCityCode());
        addAddress.setAddress2(address.getAddress2());
        addAddress.setAddress3(address.getAddress3());
        // addAddress.setAddress4(address.getAddress4());
        addAddress.setAddress4(StringUtil.getHeadline(address.getAddress4(), 40));
        addAddress.setPhoneNumber(address.getPhoneNumber());
        addAddress.setMobileNumber(address.getMobileNumber());
        addAddress.setAreaCode(address.getAreaCode());
        addressList.add(addAddress);
      }
    }

    return addressList;

  }

  private List<ShippingHeaderBean> createShippingList(Cashier cashier) {
    List<ShippingHeaderBean> shippingList = new ArrayList<ShippingHeaderBean>();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    ShippingHeaderBean shippingBean = new ShippingHeaderBean();
    // 出荷情報をアドレス・ショップコードにソートする

    List<CashierShipping> shippings = new ArrayList<CashierShipping>();
    shippings.addAll(cashier.getShippingList());
    Collections.sort(shippings, new CashierShippingComparator());

    Long prevAddressNo = -1L;
    List<ShippingDetailBean> shippingDetailBeanList = new ArrayList<ShippingDetailBean>();
    for (CashierShipping shipping : shippings) {
      if (!prevAddressNo.equals(shipping.getAddress().getAddressNo())) {
        if (!prevAddressNo.equals(-1L)) {
          shippingList.add(shippingBean);
        }

        shippingBean = new ShippingHeaderBean();
        shippingDetailBeanList = new ArrayList<ShippingDetailBean>();
        shippingBean.setAddress(createAddress(shipping.getAddress(), ""));
        shippingBean.setNewAddressNo(NumUtil.toString(shipping.getAddress().getAddressNo()));
        prevAddressNo = shipping.getAddress().getAddressNo();
      }
      ShippingDetailBean detailBean = new ShippingDetailBean();
      DeliveryType deliveryType = shipping.getDeliveryType();
      detailBean.setDeliveryTypeCode(NumUtil.toString(deliveryType.getDeliveryTypeNo()));
      detailBean.setDeliveryTypeName(deliveryType.getDeliveryTypeName());
      detailBean.setDeliveryRemark(shipping.getDeliveryRemark());
      detailBean.setShippingCharge(shipping.getShippingCharge());
      detailBean.setShopCode(shipping.getShopCode());
      detailBean.setShopName(shipping.getShopName());

      detailBean.setDeliveryAppointedDate(shipping.getDeliveryAppointedDate());
      if (StringUtil.hasValueAnyOf(shipping.getDeliveryAppointedStartTime(), shipping.getDeliveryAppointedTimeEnd())) {
        detailBean.setDeliveryAppointedTimeZone(shipping.getDeliveryAppointedStartTime() + "-"
            + shipping.getDeliveryAppointedTimeEnd());
      }

      if (StringUtil.hasValue(cashier.getPayment().getPaymentMethodCode())) {
        if (cashier.getPayment().getSelectPayment().isCashOnDelivery()) {
          detailBean.setCodType(CodType.OHTER.getValue());
        } else {
          detailBean.setCodType(CodType.COD.getValue());
        }
        String dcCode = "";
        // 获得配送公司
        // 2013/04/21 优惠券对应 ob update start
        List<DeliveryCompany> dc = utilService.getDeliveryCompanys(shipping.getShopCode(), shipping.getAddress()
            .getPrefectureCode(), shipping.getAddress().getCityCode(), shipping.getAddress().getAreaCode(), cashier.getPayment()
            .getSelectPayment().isCashOnDelivery(), cashier.getTotalWeight().toString());
        // 2013/04/21 优惠券对应 ob update end

        // 封装List<DeliveryCompanyBean>
        List<DeliveryCompanyBean> lsDcb = new ArrayList<DeliveryCompanyBean>();
        DeliveryCompanyBean cacheDCBean = new DeliveryCompanyBean();
        cacheDCBean.setDeliveryCompanyNo("D000");
        for (DeliveryCompany deliveryCompany : dc) {
          if (!cacheDCBean.getDeliveryCompanyNo().equals(deliveryCompany.getDeliveryCompanyNo())) {
            DeliveryCompanyBean dcb = new DeliveryCompanyBean();
            dcb.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
            dcb.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());

            lsDcb.add(dcb);
          }
          cacheDCBean.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
        }

        if (lsDcb.size() == 1) {
          dcCode = dc.get(0).getDeliveryCompanyNo();
        } else if (lsDcb.size() > 1) {
          if (cashier.getDelivery() != null) {
            dcCode = cashier.getDelivery().getDeliveryCompanyCode();
          }
        }
        List<CodeAttribute> deliveryDateList = utilService.getDeliveryDateList(shipping.getShopCode(), shipping.getAddress()
            .getPrefectureCode(), cashier.getPayment().getSelectPayment().isCashOnDelivery(), shipping.getCommodityInfoList(),
            cashier.getTotalWeight().toString(), dcCode);
        detailBean.setDeliveryAppointedDateList(deliveryDateList);

        if (deliveryDateList.size() > 0) {
          String deliveryAppointedDate = shipping.getDeliveryAppointedDate();
          if (StringUtil.isNullOrEmpty(deliveryAppointedDate)) {
            deliveryAppointedDate = deliveryDateList.get(0).getValue();
          }
          List<CodeAttribute> deliveryTimeZoneList = utilService.getDeliveryTimeList(shipping.getShopCode(), shipping.getAddress()
              .getPrefectureCode(), cashier.getPayment().getSelectPayment().isCashOnDelivery(), deliveryAppointedDate, shipping
              .getAddress().getAreaCode(), cashier.getTotalWeight().toString(), dcCode);
          detailBean.setDeliveryAppointedTimeZoneList(deliveryTimeZoneList);
        }
      }

      List<ShippingCommodityBean> commodityList = new ArrayList<ShippingCommodityBean>();

      // 2012/11/26 促销对应 ob update start
      List<ShippingCommodityBean> acceptedGiftList = new ArrayList<ShippingCommodityBean>();

      // 当前适用的优惠券促销活动信息取得
      CommunicationService comService = ServiceLocator.getCommunicationService(getLoginInfo());
      List<CampaignInfo> couponList = comService.getCouponCampaignInfo(DateUtil.getSysdate());
      // 2012/11/26 促销对应 ob update end

      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        ShippingCommodityBean commodityBean = new ShippingCommodityBean();
        // 2012/11/26 促销对应 ob update start
        if (CommodityType.GIFT.longValue().equals(commodity.getCommodityType())) {
          commodityBean = createAcceptedGift(commodity);
          commodityBean.setGift(true);
          acceptedGiftList.add(commodityBean);
        } else {
          commodityBean = createCommodity(commodity, cashier, couponList);
          commodityBean.setGift(false);
          commodityList.add(commodityBean);
        }
        // 2012/11/26 促销对应 ob update end
      }

      detailBean.setCommodityList(commodityList);
      // 2012/11/26 促销对应 ob add start
      detailBean.setAcceptedGiftList(acceptedGiftList);
      // 2012/11/26 促销对应 ob add end
      shippingDetailBeanList.add(detailBean);
      shippingBean.setShippingList(shippingDetailBeanList);

    }
    // 削除
    shippingList.add(shippingBean);

    // 商品カウント
    for (ShippingHeaderBean header : shippingList) {
      // 他に配送先が無ければ削除不可

      header.setDisplayDeleteButton(shippingList.size() != 1);
      int commoidityCount = 0;
      for (ShippingDetailBean detail : header.getShippingList()) {
        commoidityCount += detail.getCommodityList().size();
      }
      for (ShippingDetailBean detail : header.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          // 他に商品が無ければ削除不可

          commodity.setDisplayDeleteButton(commoidityCount != 1);
        }
      }
    }

    return shippingList;
  }
  
  /**
   * Cashierアイテムを元に1商品情報を生成します。
   * 
   * @param item
   * @return commodity
   */
  private ShippingCommodityBean createCommodity(CartCommodityInfo commodity, Cashier cashier, List<CampaignInfo> couponList) {
    ShippingCommodityBean commodityBean = new ShippingCommodityBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(commodity.getCommodityCode());
    if (dc != null ) {
      commodityBean.setTimeDiscount(true);
    } else {
      commodityBean.setTimeDiscount(false);
    }
    int commodityAmount = commodity.getQuantity();
    commodityBean.setCommodityAmount(Integer.toString(commodityAmount));
    commodityBean.setCommodityCode(commodity.getCommodityCode());
    commodityBean.setCommodityDetail1Name(commodity.getStandardDetail1Name());
    commodityBean.setCommodityDetail2Name(commodity.getStandardDetail2Name());
    commodityBean.setCommodityName(commodity.getCommodityName());
    commodityBean.setGiftCode(commodity.getGiftCode());
    commodityBean.setRetailPrice(commodity.getRetailPrice());
    commodityBean.setUsedCouponCode(commodity.getCampaignCouponCode());
    commodityBean.setUsedCouponName(commodity.getCampaignCouponName());
    commodityBean.setUsedCouponType(commodity.getCampaignCouponType());
    commodityBean.setIsDiscountCommodity(commodity.getIsDiscountCommodity());
    if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
      commodityBean.setOriginalCommodityCode(commodity.getOriginalCommodityCode());
      commodityBean.setCombinationAmount(commodity.getCombinationAmount());
    }
    if (commodity.getCampaignCouponPrice() != null && commodity.getRetailPrice() != null) {
      if (BigDecimalUtil.isAbove(commodity.getCampaignCouponPrice(), commodity.getRetailPrice())) {
        commodityBean.setUsedCouponPrice(commodity.getRetailPrice());
      } else {
        commodityBean.setUsedCouponPrice(commodity.getCampaignCouponPrice());
      }
    } else {
      commodityBean.setUsedCouponPrice(BigDecimal.ZERO);
    }

    if (commodityBean.getUsedCouponPrice() != null) {
      if (BigDecimalUtil.isAbove(commodityBean.getUsedCouponPrice(), commodityBean.getRetailPrice())) {
        commodityBean.setRetailPriceExceptCoupon(BigDecimal.ZERO);
      } else {
        commodityBean.setRetailPriceExceptCoupon(BigDecimalUtil.subtract(commodityBean.getRetailPrice(), commodityBean
            .getUsedCouponPrice()));
      }
    } else {
      commodityBean.setRetailPriceExceptCoupon(commodity.getRetailPrice());
    }

    commodityBean.setSkuCode(commodity.getSkuCode());

    commodityBean.setSubTotalPrice(BigDecimalUtil.multiply(commodity.getRetailPrice().add(commodity.getGiftPrice()), commodity
        .getQuantity()));
    if (commodity.getCampaignCouponPrice() != null) {
      commodityBean.setSubTotalPrice(BigDecimalUtil.multiply(BigDecimalUtil.subtract(commodity.getRetailPrice(),
          commodity.getCampaignCouponPrice()).add(commodity.getGiftPrice()), commodity.getQuantity()));
    }

    commodityBean.setUnitPrice(commodity.getUnitPrice());
    commodityBean.setDiscount(!commodity.getUnitPrice().equals(commodity.getRetailPrice()));
    commodityBean.setReserve(cashier.isReserve());
    commodityBean.setWeight(commodity.getWeight());
    commodityBean.setSetCommodity(false);

    // 套餐商品时
    if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
      List<CompositionBean> compositionList = new ArrayList<CompositionBean>();
      commodityBean.setSetCommodity(true);
      if (commodity.getSkuCode() != null && commodity.getSkuCode().contains(":")) {
        commodityBean.setSkuCodeOfSet(commodity.getSkuCode().split(":")[0]);
      }
      for (CompositionItem compositionItem : commodity.getCompositionList()) {
        CompositionBean composition = new CompositionBean();
        composition.setShopCode(compositionItem.getShopCode());
        composition.setCommodityCode(compositionItem.getCommodityCode());
        composition.setCommodityName(compositionItem.getCommodityName());
        composition.setSkuCode(compositionItem.getSkuCode());
        composition.setStandardDetail1Name(compositionItem.getStandardDetail1Name());
        composition.setStandardDetail2Name(compositionItem.getStandardDetail2Name());
        compositionList.add(composition);
      }

      commodityBean.setCompositionList(compositionList);
    }

    // 存在赠品时
    if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
      List<GiftBean> giftList = new ArrayList<GiftBean>();
      for (GiftItem giftItem : commodity.getGiftList()) {
        GiftBean gift = new GiftBean();
        
        gift.setShopCode(giftItem.getShopCode());
        gift.setGiftCode(giftItem.getGiftSkuCode());
        gift.setGiftName(giftItem.getGiftName());
        gift.setCampaignCode(giftItem.getCampaignCode());
        gift.setCampaignName(giftItem.getCampaignName());
        gift.setStandardDetail1Name(giftItem.getStandardDetail1Name());
        gift.setStandardDetail2Name(giftItem.getStandardDetail2Name());
        gift.setGiftSalesPrice(giftItem.getRetailPrice());
        gift.setGiftAmount(giftItem.getQuantity()+"");
        gift.setSubTotal(BigDecimalUtil.multiply(giftItem.getRetailPrice(), giftItem.getQuantity()));
        gift.setWeight(NumUtil.parseBigDecimalWithoutZero(BigDecimalUtil.multiply(giftItem.getWeight(), giftItem.getQuantity())));

        giftList.add(gift);
      }
      commodityBean.setCampaignGiftList(giftList);
    }

    // 优惠券设定
    if (!SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {

      if (couponList != null && couponList.size() > 0) {
        List<CouponDetailBean> coupon = new ArrayList<CouponDetailBean>();
        for (CampaignInfo info : couponList) {
          if (info.getConditionList() != null && info.getConditionList().get(0) != null) {
            CampaignCondition condition = info.getConditionList().get(0);

            String[] attributes = condition.getAttributrValue().split(",");
            Set<String> attributeSet = new HashSet<String>();
            for (int i = 0; i < attributes.length; i++) {
              if (StringUtil.hasValue(attributes[i])) {
                attributeSet.add(attributes[i]);
              }
            }

            // 该商品存在可使用的优惠券时，将优惠券信息保存到商品信息中
            String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
            if (attributeSet.contains(commodity.getCommodityCode())
                && (condition.getMaxCommodityNum() == null || commodityAmount >= condition.getMaxCommodityNum())) {
              CouponDetailBean couponDetail = new CouponDetailBean();
              couponDetail.setCampaignCode(info.getCampaignMain().getCampaignCode());
              if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
                couponDetail.setCampaignName(info.getCampaignMain().getCampaignName());
              } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
                couponDetail.setCampaignName(info.getCampaignMain().getCampaignNameJp());
              } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
                couponDetail.setCampaignName(info.getCampaignMain().getCampaignNameEn());
              }
              couponDetail.setDiscountType(condition.getDiscountType());
              couponDetail.setCouponValue(info.getCampaignDoings().getAttributrValue());

              coupon.add(couponDetail);
            }
          }
        }

        commodityBean.setCouponList(coupon);
      }

      if (commodityBean.getCouponList() == null || commodityBean.getCouponList().size() < 1) {

        if (StringUtil.hasValue(commodityBean.getUsedCouponCode())) {
          commodityBean.setDisplayCouponMode("2"); // 已使用折扣券
        } else {
          commodityBean.setDisplayCouponMode("0"); // 无可使用的折扣券
        }
      } else {
        if (StringUtil.isNullOrEmpty(commodityBean.getUsedCouponCode())) {
          commodityBean.setDisplayCouponMode("1"); // 有可使用的折扣券，但未使用
        } else {
          commodityBean.setDisplayCouponMode("2"); // 已使用折扣券
        }
      }
    } else {
      commodityBean.setDisplayCouponMode("0"); // 不可使用折扣券
    }

    return commodityBean;
  }

  /**
   * 已领取多重促销活动赠品信息生成
   */
  private ShippingCommodityBean createAcceptedGift(CartCommodityInfo commodity) {
    ShippingCommodityBean acceptedGift = new ShippingCommodityBean();
    acceptedGift.setCommodityAmount(Integer.toString(commodity.getQuantity()));
    acceptedGift.setCommodityCode(commodity.getCommodityCode());
    acceptedGift.setCommodityName(commodity.getCommodityName());
    acceptedGift.setMultipleCampaignCode(commodity.getMultipleCampaignCode());
    acceptedGift.setMultipleCampaignName(commodity.getMultipleCampaignName());
    acceptedGift.setRetailPrice(commodity.getRetailPrice());
    acceptedGift.setSkuCode(commodity.getSkuCode());
    acceptedGift.setCommodityDetail1Name(commodity.getStandardDetail1Name());
    acceptedGift.setCommodityDetail2Name(commodity.getStandardDetail2Name());
    acceptedGift.setSubTotalPrice(BigDecimalUtil.multiply(commodity.getRetailPrice(), commodity.getQuantity()));
    acceptedGift.setWeight(NumUtil.parseBigDecimalWithoutZero(BigDecimalUtil.multiply(commodity.getWeight(), commodity
        .getQuantity())));

    return acceptedGift;
  }

  // 2012/11/26 促销对应 ob add end

  /**
   * 注文情報を生成
   */
  public void copyBeanToCashier() {
    ShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();

    cashier.setMessage(bean.getMessage());

    for (ShippingHeaderBean shipping : bean.getShippingList()) {
      for (ShippingDetailBean detailBean : shipping.getShippingList()) {
        CashierShipping cashierShipping = cashier.getShipping(detailBean.getShopCode(), NumUtil.toLong(detailBean
            .getDeliveryTypeCode()), shipping.getAddress().getAddressNo());
        cashierShipping.setDeliveryRemark(detailBean.getDeliveryRemark());
        // 20111228 shen update start
        cashierShipping.setDeliveryAppointedDate(detailBean.getDeliveryAppointedDate());
        cashierShipping.setDeliveryAppointedTimeZone(detailBean.getDeliveryAppointedTimeZone());
        if (StringUtil.isNullOrEmpty(detailBean.getDeliveryAppointedTimeZone())
            || detailBean.getDeliveryAppointedTimeZone().equals(AppointedTimeType.NOT.getValue())) {
          cashierShipping.setDeliveryAppointedStartTime("");
          cashierShipping.setDeliveryAppointedTimeEnd("");
        } else {
          String[] timeZone = detailBean.getDeliveryAppointedTimeZone().split("-");
          if (timeZone.length > 1) {
            cashierShipping.setDeliveryAppointedStartTime(timeZone[0]);
            cashierShipping.setDeliveryAppointedTimeEnd(timeZone[1]);
          } else if (detailBean.getDeliveryAppointedTimeZone().endsWith("-")) {
            cashierShipping.setDeliveryAppointedStartTime(detailBean.getDeliveryAppointedTimeZone().replace("-", ""));
            cashierShipping.setDeliveryAppointedTimeEnd("");
          } else {
            cashierShipping.setDeliveryAppointedStartTime("");
            cashierShipping.setDeliveryAppointedTimeEnd(detailBean.getDeliveryAppointedTimeZone().replace("-", ""));
          }
        }
        // 20111228 shen update end
        // 商品情報コピー

        for (ShippingCommodityBean commodity : detailBean.getCommodityList()) {
          List<CartCommodityInfo> cashierCommodityList = cashierShipping.getCommodityInfoList();
          for (CartCommodityInfo cashierCommodity : cashierCommodityList) {
            if (commodity.getSkuCode().equals(cashierCommodity.getSkuCode())
                && cashierCommodity.getIsDiscountCommodity().equals(commodity.getIsDiscountCommodity())) {
              cashierCommodity.setGiftCode("");
              cashierCommodity.setGiftName("");
              cashierCommodity.setGiftPrice(BigDecimal.ZERO);
              cashierCommodity.setGiftTaxCharge(BigDecimal.ZERO);
              cashierCommodity.setGiftTaxType(null);
              cashierCommodity.setQuantity(Integer.parseInt(commodity.getCommodityAmount()));

              cashierCommodity.setCampaignCouponCode(commodity.getUsedCouponCode());
              cashierCommodity.setCampaignCouponName(commodity.getUsedCouponName());
              cashierCommodity.setCampaignCouponPrice(commodity.getUsedCouponPrice());
              cashierCommodity.setCampaignCouponType(commodity.getUsedCouponType());
            }
          }
        }
      }
    }

    if (!StringUtil.isNullOrEmpty(getBean().getSelectedDeliveryCompany().getDeliveryCompanyNo())) {
      CashierDelivery cdBean = new CashierDelivery();
      cdBean.setDeliveryCompanyCode(getBean().getSelectedDeliveryCompany().getDeliveryCompanyNo());
      cashier.setDelivery(cdBean);
    }
    cashier.recomputeShippingCharge();
    if (BigDecimalUtil.isAbove(bean.getCashier().getShippingList().get(0).getShippingCharge(), BigDecimal.ZERO)) {
      // 免邮活动验证
      String mediaCode = "";

      CommonSessionContainer commonSession = (CommonSessionContainer) getSessionContainer();
      if (commonSession.getSession().getAttribute(WebFrameworkConstants.ATTRIBUTE_FREE_MEDIA) != null) {
        mediaCode = commonSession.getSession().getAttribute(WebFrameworkConstants.ATTRIBUTE_FREE_MEDIA).toString();
      }

      if (StringUtil.hasValue(mediaCode)) {
        if (isFree(bean, mediaCode)) {
          bean.getShippingList().get(0).getShippingList().get(0).setShippingCharge(BigDecimal.ZERO);
          bean.getCashier().getShippingList().get(0).setShippingCharge(BigDecimal.ZERO);
        }
      }
    }
    // 积分默认值设定
    cashier.setUsePoint("0");
  }

  /**
   * 注文商品の合計数を生成
   * 
   * @param shopCode
   * @param skuCode
   * @return totalAmount
   */
  public Long getTotalAmount(String shopCode, String skuCode) {
    Long totalAmount = 0L;

    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        if (!detail.getShopCode().equals(shopCode)) {
          continue;
        }
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          if (commodity.getSkuCode().equals(skuCode)) {
            totalAmount += NumUtil.toLong(commodity.getCommodityAmount());
          }
        }
      }
    }

    return totalAmount;
  }


  /**
   * 在庫・予約上限数チェック
   * 
   * @return boolean
   */
  public boolean shippingValidation() {
    boolean valid = true;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    Map<Sku, Long> amountMap = new HashMap<Sku, Long>();
    CommodityAvailability commodityAvailability = null;

    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        valid &= validateBean(shipping);

        // 统计通常商品/套餐商品
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          List<String> errorMesssageList = BeanValidator.validate(commodity).getErrorMessages();
          String displayName = getDisplayName(commodity.getCommodityName(), commodity.getCommodityDetail1Name(), commodity
              .getCommodityDetail2Name());
          if (errorMesssageList.size() > 0) {
            for (String s : errorMesssageList) {
              addErrorMessage(MessageFormat.format(Messages.getString("web.action.front.order.ShippingBaseAction.1"), displayName,
                  s));
            }
            valid &= false;
          }

          String shopCode = detail.getShopCode();

          if (commodity.getCompositionList() != null && commodity.getCompositionList().size() > 0) {
            // 套餐商品时

            for (CompositionBean composition : commodity.getCompositionList()) {
              String compositionSkuCode = composition.getSkuCode();
              Sku compositionSku = getSku(amountMap, shopCode, compositionSkuCode);
              Long compositionAmount = NumUtil.toLong(commodity.getCommodityAmount());
              if (compositionSku == null) {
                compositionSku = new Sku(shopCode, compositionSkuCode, CommodityType.GENERALGOODS.longValue());
                compositionSku.setDisplayName(getDisplayName(composition.getCommodityName(), composition.getStandardDetail1Name(),
                    composition.getStandardDetail2Name()));
              } else {
                compositionAmount += amountMap.get(compositionSku);
              }
              amountMap.put(compositionSku, compositionAmount);
            }
          } else {
            // 通常商品时

            String skuCode = commodity.getSkuCode();
            Sku sku = getSku(amountMap, shopCode, skuCode);
            Long amount = NumUtil.toLong(commodity.getCommodityAmount());
            if (sku == null) {
              sku = new Sku(shopCode, skuCode, CommodityType.GENERALGOODS.longValue());
              sku.setDisplayName(getDisplayName(commodity.getCommodityName(), commodity.getCommodityDetail1Name(), commodity
                  .getCommodityDetail2Name()));
            } else {
              amount += amountMap.get(sku);
            }
            amountMap.put(sku, amount);
          }

          // 统计赠品促销活动的赠品
          if (commodity.getCampaignGiftList() != null && commodity.getCampaignGiftList().size() > 0) {
            CampaignMainDao camDao = DIContainer.getDao(CampaignMainDao.class);
            for (GiftBean gift : commodity.getCampaignGiftList()) {
              String giftShopCode = detail.getShopCode();
              String giftCode = gift.getGiftCode();
              Sku giftSku = getSku(amountMap, giftShopCode, giftCode);
              CampaignMain camMain = camDao.load(gift.getCampaignCode());
              Long giftAmount = 1L;
              if (camMain.getGiftAmount() != null ) {
                giftAmount = camMain.getGiftAmount();
              }
              if (giftSku == null) {
                giftSku = new Sku(shopCode, giftCode, CommodityType.GIFT.longValue());
                giftSku.setDisplayName(getDisplayName(gift.getGiftName(), gift.getStandardDetail1Name(), gift
                    .getStandardDetail2Name()));
              } else {
                giftAmount += amountMap.get(giftSku);
              }
              amountMap.put(giftSku, giftAmount);
            }
          }
        }

        // 已领取的多重促销活动的赠品
        for (ShippingCommodityBean acceptGift : detail.getAcceptedGiftList()) {
          String shopCode = detail.getShopCode();
          String skuCode = acceptGift.getSkuCode();
          Sku sku = getSku(amountMap, shopCode, skuCode);
          Long amount = NumUtil.toLong(acceptGift.getCommodityAmount());
          if (sku == null) {
            sku = new Sku(shopCode, skuCode, CommodityType.GIFT.longValue());
            sku.setDisplayName(getDisplayName(acceptGift.getCommodityName(), acceptGift.getCommodityDetail1Name(), acceptGift
                .getCommodityDetail2Name()));
          } else {
            amount += amountMap.get(sku);
          }
          amountMap.put(sku, amount);
        }
      }
    }

    boolean isPluralShipping = getBean().getShippingList().size() > 1;
    boolean isPluralShippingDetail = !isPluralShipping && getBean().getShippingList().get(0).getShippingList().size() > 1;
    boolean isPluralCommodity = !isPluralShipping && !isPluralShippingDetail
        && getBean().getShippingList().get(0).getShippingList().get(0).getCommodityList().size() > 1;
    boolean isReserve = false;
    CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        
//        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
//          long quantity = Long.parseLong(commodity.getCommodityAmount());
//          Long availableStock = 0L;
//          Long tmpStockAmount = 0L; // 20130710 yamanaka add
//          String shopCode = detail.getShopCode();
//          String skuCode = commodity.getSkuCode();
//          String commodityName = getDisplayName(commodity.getCommodityName(), commodity.getCommodityDetail1Name(), commodity
//              .getCommodityDetail2Name());
          
//          // 最小购买数check
//          if (commodity.isSetCommodity()) {
//            // 套餐商品
//            if (commodity.getSkuCode().contains(":")) {
//              skuCode = commodity.getSkuCode().split(":")[0];
//            }
//            List<String> compositionSkuCodeList = new ArrayList<String>();
//            List<String> giftSkuCodeList = new ArrayList<String>();
//            for (CompositionBean composition : commodity.getCompositionList()) {
//              if (StringUtil.hasValue(composition.getSkuCode())) {
//                compositionSkuCodeList.add(composition.getSkuCode());
//              }
//            }
//
//            if (commodity.getCampaignGiftList() != null && commodity.getCampaignGiftList().size() > 0) {
//              for (GiftBean gift : commodity.getCampaignGiftList()) {
//                if (StringUtil.hasValue(gift.getGiftCode())) {
//                  giftSkuCodeList.add(gift.getGiftCode());
//                }
//              }
//            }
//
//            availableStock = catalogService.getAvailableStock(shopCode, skuCode, true, compositionSkuCodeList, giftSkuCodeList);
//            if (availableStock < NumUtil.toLong(commodity.getCommodityAmount(), 0L)) {
//              addErrorMessage(WebMessage
//                  .get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
//              valid &= false;
//            }
//          } else {
//            // 通常商品
//            List<String> giftSkuCodeList = new ArrayList<String>();
//
//            if (commodity.getCampaignGiftList() != null && commodity.getCampaignGiftList().size() > 0) {
//              for (GiftBean gift : commodity.getCampaignGiftList()) {
//                if (StringUtil.hasValue(gift.getGiftCode())) {
//                  giftSkuCodeList.add(gift.getGiftCode());
//                }
//              }
//            }
//
//            CommodityHeader ch = chDao.load(shopCode, skuCode);
//
//            // 20130710 yamanaka mod start
//            if (StringUtil.hasValue(ch.getOriginalCommodityCode())) {
//              availableStock = catalogService.getAvailableStock(shopCode, ch.getOriginalCommodityCode(), false, null,
//                  giftSkuCodeList);
//              tmpStockAmount = availableStock / ch.getCombinationAmount();
//              if (tmpStockAmount < NumUtil.toLong(commodity.getCommodityAmount(), 0L)) {
//                addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, tmpStockAmount
//                    .toString()));
//                valid &= false;
//              }
//            } else {
//              availableStock = catalogService.getAvailableStock(shopCode, skuCode, false, null, giftSkuCodeList);
//            }
//          }

//          // 各套餐的可购买性check
//          if (valid && commodity.isSetCommodity()) {
//            commodityAvailability = catalogService.isAvailable(shopCode, skuCode, Integer.parseInt(commodity.getCommodityAmount()),
//                commodity.isReserve(), true);
//            if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
//              addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, commodityName));
//              valid &= false;
//            } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
//              addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, commodityName));
//              valid &= false;
//            } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
//              valid &= true;
//            }
//          }

//          // 组合商品库存判断时候 要将购物车里面的所有商品都进行循环判断 add by twh start
//          if (valid) {
//            Long allCount = 0L;
//            if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
//              allCount = quantity * commodity.getCombinationAmount();
//            } else {
//              allCount = quantity * 1L;
//            }
//
//            for (ShippingCommodityBean scb : detail.getCommodityList()) {
//              if (!scb.getCommodityCode().equals(skuCode)) {
//                boolean rel = false;
//                if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
//                  rel = (commodity.getOriginalCommodityCode().equals(scb.getCommodityCode()) || (StringUtil.hasValue(scb
//                      .getOriginalCommodityCode()) && commodity.getOriginalCommodityCode().equals(scb.getOriginalCommodityCode())));
//                } else {
//                  rel = (StringUtil.hasValue(scb.getOriginalCommodityCode()) && commodity.getCommodityCode().equals(
//                      scb.getOriginalCommodityCode()));
//                }
//
//                if (rel) {
//                  if (StringUtil.hasValue(scb.getOriginalCommodityCode())) {
//                    allCount = allCount + Long.parseLong(scb.getCommodityAmount()) * scb.getCombinationAmount();
//                  } else {
//                    allCount = allCount + Long.parseLong(scb.getCommodityAmount());
//                  }
//                }
//              }
//            }
//            if (allCount > availableStock) {
//              addErrorMessage(WebMessage
//                  .get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
//              valid &= false;
//            }
//          }
//          // 组合商品库存判断时候 要将购物车里面的所有商品都进行循环判断 add by twh end
//        }
        
        // 套装，单品，组合，库存综合判断
        List<ShippingCommodityBean> suitList = new ArrayList<ShippingCommodityBean>();
        List<ShippingCommodityBean> combineList = new ArrayList<ShippingCommodityBean>();
        List<ShippingCommodityBean> simpleListAll = new ArrayList<ShippingCommodityBean>();
        List<ShippingCommodityBean> simpleList = new ArrayList<ShippingCommodityBean>();
        List<ShippingCommodityBean> discountList = new ArrayList<ShippingCommodityBean>();
        Map<String,Long> notSimpleMap = new HashMap<String,Long>();
        for (ShippingCommodityBean commodityBean : detail.getCommodityList()) {
          if (StringUtil.hasValue(commodityBean.getOriginalCommodityCode())) {
            combineList.add(commodityBean);
          } else if (commodityBean.isSetCommodity()) {
            suitList.add(commodityBean);
          } else {
            simpleListAll.add(commodityBean);
          }
        }
        for( ShippingCommodityBean commodityBean : simpleListAll) {
          if(commodityBean.getIsDiscountCommodity().equals("true")) {
            discountList.add(commodityBean);
          } else {
            simpleList.add(commodityBean);
          }
        }
        
        // 限时限量折扣商品
        for (ShippingCommodityBean commodityBean : discountList) {
          if (notSimpleMap.get(commodityBean.getCommodityCode()) == null ) {
            notSimpleMap.put(commodityBean.getCommodityCode(), Long.parseLong(commodityBean.getCommodityAmount()));
          } else {
            Long num = notSimpleMap.get(commodityBean.getCommodityCode());
            num += Long.parseLong(commodityBean.getCommodityAmount());
            notSimpleMap.remove(commodityBean.getCommodityCode());
            notSimpleMap.put(commodityBean.getCommodityCode(), num);
          }
        }
        
        // 套餐商品
        for (ShippingCommodityBean commodityBean : suitList) {
          String commodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getCommodityDetail1Name(), commodityBean.getCommodityDetail2Name());
          List<String> compositionSkuCodeList = new ArrayList<String>();
          List<String> giftSkuCodeList = new ArrayList<String>();
          String skuCode = commodityBean.getSkuCodeOfSet();
          for (CompositionBean composition : commodityBean.getCompositionList()) {
            if (StringUtil.hasValue(composition.getSkuCode())) {
              compositionSkuCodeList.add(composition.getSkuCode());
              if (notSimpleMap.get(composition.getSkuCode()) == null ) {
                notSimpleMap.put(composition.getSkuCode(), Long.parseLong(commodityBean.getCommodityAmount()));
              } else {
                Long num = notSimpleMap.get(composition.getSkuCode());
                num += Long.parseLong(commodityBean.getCommodityAmount());
                notSimpleMap.remove(composition.getSkuCode());
                notSimpleMap.put(composition.getSkuCode(), num);
              }
            }
          }
          if (commodityBean.getCampaignGiftList() != null && commodityBean.getCampaignGiftList().size() > 0) {
            for (GiftBean gift : commodityBean.getCampaignGiftList()) {
              if (StringUtil.hasValue(gift.getGiftCode())) {
                giftSkuCodeList.add(gift.getGiftCode());
              }
            }
          }
          Long availableStock = catalogService.getAvailableStock("00000000", skuCode, true, compositionSkuCodeList, giftSkuCodeList);
          if (availableStock < NumUtil.toLong(commodityBean.getCommodityAmount(), 0L)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
            valid &= false;
          }
        }
        
        // 组合商品
        for (ShippingCommodityBean commodityBean : combineList) {
          String commodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getCommodityDetail1Name(), commodityBean.getCommodityDetail2Name());
          List<String> giftSkuCodeList = new ArrayList<String>();
          if (commodityBean.getCampaignGiftList() != null && commodityBean.getCampaignGiftList().size() > 0) {
            for (GiftBean gift : commodityBean.getCampaignGiftList()) {
              if (StringUtil.hasValue(gift.getGiftCode())) {
                giftSkuCodeList.add(gift.getGiftCode());
              }
            }
          }
          CommodityHeader ch = chDao.load("00000000", commodityBean.getCommodityCode());
          Long availableStock = catalogService.getAvailableStock("00000000", ch.getOriginalCommodityCode());
          availableStock = availableStock/ch.getCombinationAmount();
          
          if (availableStock < NumUtil.toLong(commodityBean.getCommodityAmount(), 0L)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
            valid &= false;
          }
          if (notSimpleMap.get(ch.getOriginalCommodityCode()) == null ) {
            notSimpleMap.put(ch.getOriginalCommodityCode(), Long.parseLong(commodityBean.getCommodityAmount())*ch.getCombinationAmount() );
          } else {
            Long num = notSimpleMap.get(ch.getOriginalCommodityCode());
            num += Long.parseLong(commodityBean.getCommodityAmount())*ch.getCombinationAmount();
            notSimpleMap.remove(ch.getOriginalCommodityCode());
            notSimpleMap.put(ch.getOriginalCommodityCode(), num);
          }
        }
        
        // 通常商品
        for (ShippingCommodityBean commodityBean : simpleList) {
          String commodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getCommodityDetail1Name(), commodityBean.getCommodityDetail2Name());
          Long num = notSimpleMap.get(commodityBean.getCommodityCode());
          Long totalNum = Long.parseLong(commodityBean.getCommodityAmount());
          if (num == null) {
            num = 0L;
          }
          totalNum += num;
          
          List<String> giftSkuCodeList = new ArrayList<String>();
          if (commodityBean.getCampaignGiftList() != null && commodityBean.getCampaignGiftList().size() > 0) {
            for (GiftBean gift : commodityBean.getCampaignGiftList()) {
              if (StringUtil.hasValue(gift.getGiftCode())) {
                giftSkuCodeList.add(gift.getGiftCode());
              }
            }
          }
          Long availableStock = catalogService.getAvailableStock("00000000", commodityBean.getCommodityCode());
          if (availableStock < totalNum ) {
             addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
             valid &= false;
          }
        }
        
      }
    }

    if (valid) {
      for (Entry<Sku, Long> entry : amountMap.entrySet()) {
        String shopCode = entry.getKey().getShopCode();
        String skuCode = entry.getKey().getSkuCode();
        Long commodityType = entry.getKey().getCommodityType();
        Long amount = entry.getValue();
        String displayName = entry.getKey().getDisplayName();

        isReserve = getSessionContainer().getCart().getReserve(shopCode, skuCode) != null;
        // 予約商品複数配送先追加エラー
        if (isReserve) {
          if (isPluralShipping || isPluralShippingDetail || isPluralCommodity) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_RESERVE_PLURAL_SHIPPING_ERROR));
            return false;
          }
        }

        if (CommodityType.GENERALGOODS.longValue().equals(commodityType)) {
          // 各通常商品可购买性check
          commodityAvailability = catalogService.isAvailable(shopCode, skuCode, amount.intValue(), isReserve);
        } else {
          // 各赠品可购买性check
          commodityAvailability = catalogService.isAvailableGift(shopCode, skuCode, amount.intValue());
        }

        if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE_ERROR, displayName, String.valueOf(catalogService
              .getAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, displayName, NumUtil.toString(catalogService
              .getReservationAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
          valid &= true;
        }
      }
    }

    for (Entry<Sku, Long> entry : amountMap.entrySet()) {
      String shopCode = entry.getKey().getShopCode();
      String skuCode = entry.getKey().getSkuCode();

      isReserve = getSessionContainer().getCart().getReserve(shopCode, skuCode) != null;
      // 予約商品複数配送先追加エラー
      if (isReserve) {
        if (isPluralShipping || isPluralShippingDetail || isPluralCommodity) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_RESERVE_PLURAL_SHIPPING_ERROR));
          return false;
        }
      }
    }

    return valid;
  }

  /**
   * 折扣券的使用可能验证
   * 
   * @return boolean
   */
  public boolean couponValidation() {
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    CommunicationService comService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 统计各通常商品/套餐商品的数量
    Map<Sku, Long> amountMap = getAmountMap();

    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {

          // 商品优惠券活动的使用性check
          if (StringUtil.hasValue(commodity.getUsedCouponCode())) {
            List<CampaignCondition> conditionList = comService.getCampaignConditionByCouponCode(commodity.getUsedCouponCode());
            if (conditionList != null && conditionList.size() > 0) {
              String[] attributeValues = conditionList.get(0).getAttributrValue().split(",");
              boolean isExit = false;
              for (int i = 0; i < attributeValues.length; i++) {
                if (attributeValues[i].equals(commodity.getCommodityCode())) {
                  isExit = true;

                  // 使用商品SKU的合计数量>=条件的对象商品限定件数
                  String shopCode = detail.getShopCode();
                  String skuCode = commodity.getSkuCode();
                  if (conditionList.get(0).getMaxCommodityNum() != null && !amountMap.isEmpty()) {
                    if (conditionList.get(0).getMaxCommodityNum() > amountMap.get(getSku(amountMap, shopCode, skuCode))) {
                      addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_USE_LIMIT_ERROR, commodity
                          .getUsedCouponCode(), conditionList.get(0).getMaxCommodityNum().toString()));
                      return false;
                    }
                  }

                  // 以前使用限制次数
                  Long couponUsedCounts = orderService.getCampaignDiscountUsedCount(commodity.getUsedCouponCode(), "");
                  // 本次使用限制次数
                  Long couponCounts = getCouponCounts(commodity.getUsedCouponCode());
                  // 使用回数check
                  if (couponUsedCounts != null
                      && conditionList.get(0).getUseLimit() != null
                      && !amountMap.isEmpty()
                      && BigDecimalUtil.isAbove(BigDecimalUtil.add(couponUsedCounts, couponCounts), new BigDecimal(conditionList
                          .get(0).getUseLimit()))) {
                    addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_MAX_COMMODITY_NUM_ERROR, commodity
                        .getUsedCouponCode()));
                    return false;
                  }
                  break;
                }
              }

              if (!isExit) {
                addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_DETAIL_ERROR, commodity.getUsedCouponCode()));
                return false;
              }

            } else {
              addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_DETAIL_ERROR, commodity.getUsedCouponCode()));
              return false;
            }
          }
        }
      }
    }

    return true;
  }

  private Long getCouponCounts(String couponUsedCounts) {
    Long result = 0L;
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          if (couponUsedCounts.equals(commodity.getUsedCouponCode())) {
            result = result + 1L;
          }
        }
      }
    }

    return result;
  }

  /**
   * 统计各通常商品/套餐商品的数量
   * 
   * @return 各通常商品/套餐商品的数量
   */
  public Map<Sku, Long> getAmountMap() {
    Map<Sku, Long> amountMap = new HashMap<Sku, Long>();

    // 统计各通常商品/套餐商品的数量
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          String shopCode = detail.getShopCode();
          String skuCode = commodity.getSkuCode();
          Sku sku = getSku(amountMap, shopCode, skuCode);
          Long amount = NumUtil.toLong(commodity.getCommodityAmount());
          if (sku == null) {
            sku = new Sku(shopCode, skuCode);
            sku.setDisplayName(getDisplayName(commodity.getCommodityName(), commodity.getCommodityDetail1Name(), commodity
                .getCommodityDetail2Name()));
          } else {
            amount += amountMap.get(sku);
          }
          amountMap.put(sku, amount);
        }
      }
    }

    return amountMap;
  }

  /**
   * 取得指定折扣券本次订单的使用次数
   * 
   * @param couponCode
   *          折扣券编号
   * @return 指定折扣券本次订单的使用次数
   */
  public Long getCouponUseCountFromBean(String couponCode) {
    Long result = 0L;

    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          if (StringUtil.hasValue(commodity.getUsedCouponCode()) && commodity.getUsedCouponCode().equals(couponCode)) {
            result = result + 1L;
          }
        }
      }
    }

    return result;
  }

  /**
   * Cashier情報に保持している情報をOrderContainerに変換します。<br>
   * 変換する情報は配送手数料・ギフト料・販売価格・商品数量とそれに紐付く金額情報です。
   * 
   * @return
   */
  private OrderContainer createOrderContainerFromCashier() {
    ShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();
    OrderContainer order = new OrderContainer();
    OrderHeader orderHeader = new OrderHeader();
    order.setOrderHeader(orderHeader);

    List<ShippingContainer> shippings = new ArrayList<ShippingContainer>();
    TaxUtil u = DIContainer.get("TaxUtil");
    for (CashierShipping shipping : cashier.getShippingList()) {
      ShippingContainer shippingContainer = new ShippingContainer();

      ShippingHeader shippingHeader = new ShippingHeader();
      shippingHeader.setShippingCharge(shipping.getShippingCharge());
      shippingHeader.setShippingChargeTaxType(shipping.getDeliveryType().getShippingChargeTaxType());
      shippingHeader.setShippingChargeTaxRate(u.getTaxRate());
      shippingHeader.setShippingChargeTax(shipping.getShippingChargeTax());

      shippingContainer.setShippingHeader(shippingHeader);

      List<ShippingDetail> shippingDetails = new ArrayList<ShippingDetail>();
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        ShippingDetail shippingDetail = new ShippingDetail();
        shippingDetail.setUnitPrice(commodity.getUnitPrice());
        if (commodity.isDiscount()) {
          shippingDetail.setDiscountPrice(commodity.getRetailPrice());
        }
        BigDecimal discountAmount = BigDecimalUtil.subtract(commodity.getUnitPrice(), commodity.getRetailPrice());
        shippingDetail.setDiscountAmount(discountAmount);
        shippingDetail.setRetailPrice(commodity.getRetailPrice());
        shippingDetail.setPurchasingAmount(Integer.valueOf(commodity.getQuantity()).longValue());
        shippingDetail.setGiftCode(commodity.getGiftCode());
        shippingDetail.setGiftName(commodity.getGiftName());
        shippingDetail.setGiftPrice(commodity.getGiftPrice());
        shippingDetail.setGiftTaxRate(u.getTaxRate());
        shippingDetail.setGiftTax(commodity.getGiftTaxCharge());
        shippingDetail.setGiftTaxType(commodity.getGiftTaxType());
        shippingDetail.setRetailTax(commodity.getCommodityTaxCharge());

        shippingDetails.add(shippingDetail);
      }
      shippingContainer.setShippingDetails(shippingDetails);
      shippings.add(shippingContainer);
    }

    order.setShippings(shippings);
    return order;
  }

  /**
   * 限界値のValitionチェックを行います。<br>
   */
  public boolean numberLimitValidation() {
    OrderContainer order = createOrderContainerFromCashier();
    ValidationSummary summary = DIContainer.getNumberLimitPolicy().isCorrect(order);
    if (summary.hasError()) {
      for (String error : summary.getErrorMessages()) {
        addErrorMessage(error);
      }
      return false;
    } else {
      return true;
    }

  }

  private Sku getSku(Map<Sku, Long> map, String shopCode, String skuCode) {
    for (Entry<Sku, Long> entry : map.entrySet()) {
      Sku mapSku = entry.getKey();
      if (mapSku.getShopCode().equals(shopCode) && mapSku.getSkuCode().equals(skuCode)) {
        return entry.getKey();
      }
    }
    return null;
  }

  private static class CashierShippingComparator implements Comparator<CashierShipping>, Serializable {

    private static final long serialVersionUID = 1L;

    public int compare(CashierShipping o1, CashierShipping o2) {
      String o1AddressNo = o1.getAddress().getAddressNo().toString();
      String o2AddressNo = o2.getAddress().getAddressNo().toString();
      if (o1AddressNo.equals(o2AddressNo)) {
        String o1ShopCode = o1.getShopCode();
        String o2ShopCode = o2.getShopCode();
        return o1ShopCode.compareTo(o2ShopCode);
      } else {
        return o1AddressNo.compareTo(o2AddressNo);
      }
    }
  }

  /**
   * お支払い方法が代金引換のみの場合に、Cashier内の配送先が複数あるかどうかを検証します。
   * 
   * @return 合計金額が0円、または残ポイントが合計金額を上回る:false<br>
   *         配送先が複数存在する:true
   */
  public boolean isCashOnDeliveryOnly() {

    if (BigDecimalUtil.isBelow(getBean().getCashier().getGrandTotalPrice(), BigDecimal.ONE)) {
      return false;
    }
    SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = siteService.getPointRule();
    BigDecimal restPoint = getBean().getCashier().getCustomer().getRestPoint();
    // ポイント残高がnull(ゲストの場合)の時はポイント残高にありえない値(-1)を設定

    if (restPoint == null) {
      getBean().getCashier().getCustomer().setRestPoint(BigDecimal.ONE.negate());
    }
    boolean hasNoPoint = false;
    if (getLoginInfo().isGuest()) {
      hasNoPoint = true;
    } else if (pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())) {
      if (BigDecimalUtil.isAboveOrEquals(getBean().getCashier().getCustomer().getRestPoint(), getBean().getCashier()
          .getGrandTotalPrice())) {
        return false;
      } else {
        hasNoPoint = true;
      }
    }
    PaymentSupporter paymentSupporter = new PaymentSupporter(LoginType.FRONT);
    PaymentMethodBean paymentMethodBean = paymentSupporter.createPaymentMethodBean(getBean().getCashier().getPayment()
        .getShopCode());

    if (paymentMethodBean.getDisplayPaymentList().size() == 1
        && paymentMethodBean.getDisplayPaymentList().get(0).getPaymentMethodType().equals(
            PaymentMethodType.CASH_ON_DELIVERY.getValue())) {

      if (getBean().getCashier().getShippingList().size() > 1) {
        if (hasNoPoint) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_AND_NO_POINT_ERROR));
        } else {
          addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_ERROR));
        }
        return true;
      }

      for (CashierShipping cs : getBean().getCashier().getShippingList()) {
        if (!cs.getAddress().getAddressNo().equals(0L)) {
          if (hasNoPoint) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_AND_NO_POINT_ERROR));
          } else {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_ERROR));
          }
          return true;
        }
      }
    }
    return false;
  }

  public void recastCart() {
    for (CartCommodityInfo commodity : getBean().getCashier().getUsableCommodity()) {
      Long quantity = getTotalAmount(commodity.getShopCode(), commodity.getSkuCode());
      if (quantity.equals(0L)) {
        getCart().remove(commodity.getShopCode(), commodity.getSkuCode(), "");
      } else {
        boolean isOrder = !getBean().getCashier().isReserve();
        getCart().updateQuantity(commodity.getShopCode(), commodity.getSkuCode(), isOrder, quantity.intValue());
      }
    }
  }

  public CustomerAddress copyCustomerAddress(Cashier cashier, AddAddressBean addAddress) {
    CustomerAddress address = new CustomerAddress();
    address.setCustomerCode(cashier.getCustomer().getCustomerCode());
    address.setAddressNo(NumUtil.toLong(addAddress.getAddressNo()));
    address.setAddressAlias(addAddress.getAddressAlias());
    address.setAddressLastName(addAddress.getAddressLastName());
    address.setAddressFirstName(addAddress.getAddressFirstName());
    address.setAddressLastNameKana(addAddress.getAddressLastNameKana());
    address.setAddressFirstNameKana(addAddress.getAddressFirstNameKana());
    address.setPostalCode(addAddress.getPostalCode1() + addAddress.getPostalCode2());
    address.setPrefectureCode(addAddress.getPrefectureCode());
    address.setAddress1(addAddress.getAddress1());
    address.setAddress2(addAddress.getAddress2());
    address.setAddress3(addAddress.getAddress3());
    address.setAddress4(addAddress.getAddress4());
    address.setPhoneNumber(addAddress.getPhoneNumber());
    address.setMobileNumber(addAddress.getMobileNumber());
    address.setCityCode(addAddress.getCityCode());
    address.setAreaCode(addAddress.getAreaCode());

    return address;
  }

  /**
   * cashierから画面情報を設定します
   */
  public void createCashierFromDisplay() {
    ShippingBean bean = getBean();

    Cashier cashier = bean.getCashier();
    // 发票信息设定
    CashierInvoice invoice = new CashierInvoice();
    if (StringUtil.hasValue(bean.getOrderInvoice().getInvoiceFlg())
        && bean.getOrderInvoice().getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
      invoice.setInvoiceFlg(InvoiceFlg.NEED.getValue());
      CashierInvoiceBase invoiceInfo = new CashierInvoiceBase();
      invoiceInfo.setCommodityName(bean.getOrderInvoice().getInvoiceCommodityName());
      invoiceInfo.setInvoiceType(bean.getOrderInvoice().getInvoiceType());
      if (bean.getOrderInvoice().getInvoiceType().equals(InvoiceType.VAT.getValue())) {
        invoiceInfo.setInvoiceType(InvoiceType.VAT.getValue());
        invoiceInfo.setCompanyName(bean.getOrderInvoice().getInvoiceCompanyName());
        invoiceInfo.setTaxpayerCode(bean.getOrderInvoice().getInvoiceTaxpayerCode());
        invoiceInfo.setAddress(bean.getOrderInvoice().getInvoiceAddress());
        invoiceInfo.setTel(bean.getOrderInvoice().getInvoiceTel());
        invoiceInfo.setBankName(bean.getOrderInvoice().getInvoiceBankName());
        invoiceInfo.setBankNo(bean.getOrderInvoice().getInvoiceBankNo());
        invoiceInfo.setInvoiceSaveFlg(bean.getOrderInvoice().getInvoiceSaveFlg());
      } else {
        invoiceInfo.setInvoiceType(InvoiceType.USUAL.getValue());
        invoiceInfo.setCustomerName(bean.getOrderInvoice().getInvoiceCustomerName());
      }
      invoice.setInvoiceInfo(invoiceInfo);
    } else {
      invoice.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
    }
    cashier.setInvoice(invoice);

    // 手续费初始设定
    cashier.getPayment().getSelectPayment().setPaymentCommission(BigDecimal.ZERO);
  }

  public InvoiceBean createInvoice(Cashier cashier) {
    InvoiceBean invoiceBean = new InvoiceBean();
    CashierInvoice cashierInvoice = cashier.getInvoice();
    if (cashierInvoice != null) {
      if (cashierInvoice.getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
        invoiceBean.setInvoiceFlg(cashierInvoice.getInvoiceFlg());
        invoiceBean.setInvoiceCommodityName(cashierInvoice.getInvoiceInfo().getCommodityName());
        invoiceBean.setInvoiceType(cashierInvoice.getInvoiceInfo().getInvoiceType());
        if (invoiceBean.getInvoiceType().equals(InvoiceType.USUAL.getValue())) {
          invoiceBean.setInvoiceCustomerName(cashierInvoice.getInvoiceInfo().getCustomerName());
        } else {
          invoiceBean.setInvoiceCompanyName(cashierInvoice.getInvoiceInfo().getCompanyName());
          invoiceBean.setInvoiceTaxpayerCode(cashierInvoice.getInvoiceInfo().getTaxpayerCode());
          invoiceBean.setInvoiceAddress(cashierInvoice.getInvoiceInfo().getAddress());
          invoiceBean.setInvoiceTel(cashierInvoice.getInvoiceInfo().getTel());
          invoiceBean.setInvoiceBankName(cashierInvoice.getInvoiceInfo().getBankName());
          invoiceBean.setInvoiceBankNo(cashierInvoice.getInvoiceInfo().getBankNo());
          invoiceBean.setInvoiceSaveFlg(cashierInvoice.getInvoiceInfo().getInvoiceSaveFlg());
        }
      } else {
        invoiceBean.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
        invoiceBean.setInvoiceType(InvoiceType.USUAL.getValue());
      }
    } else {
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      CustomerVatInvoice customerVatInvoice = service.getCustomerVatInvoice(cashier.getCustomer().getCustomerCode());
      if (customerVatInvoice != null) {
        invoiceBean.setInvoiceCompanyName(customerVatInvoice.getCompanyName());
        invoiceBean.setInvoiceTaxpayerCode(customerVatInvoice.getTaxpayerCode());
        invoiceBean.setInvoiceAddress(customerVatInvoice.getAddress());
        invoiceBean.setInvoiceTel(customerVatInvoice.getTel());
        invoiceBean.setInvoiceBankName(customerVatInvoice.getBankName());
        invoiceBean.setInvoiceBankNo(customerVatInvoice.getBankNo());
      }
      invoiceBean.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
      invoiceBean.setInvoiceType(InvoiceType.USUAL.getValue());
    }

    return invoiceBean;
  }

  public List<CodeAttribute> createDiscountTypeList(Cashier cashier) {
    List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();
    CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign(cashier);
    if (customerGroupCampaign != null) {
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      String campaignName = utilService.getNameByLanguage(customerGroupCampaign.getCampaignName(), customerGroupCampaign
          .getCampaignNameEn(), customerGroupCampaign.getCampaignNameJp());
      discountTypeList.add(new NameValue(campaignName, DiscountType.CUSTOMER.getValue()));
    }
    discountTypeList.add(new NameValue("", DiscountType.COUPON.getValue()));
    return discountTypeList;
  }

  public CustomerGroupCampaign getCustomerGroupCampaign(Cashier cashier) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());

    String shopCode = cashier.getPayment().getShopCode();
    String customerGroupCode = cashier.getCustomer().getCustomerGroupCode();
    CustomerGroupCampaign customerGroupCampaign = customerService.getCustomerGroupCampaign(shopCode, customerGroupCode);
    if (customerGroupCampaign != null) {
      if (BigDecimalUtil.isBelow(cashier.getTotalCommodityPrice(), customerGroupCampaign.getMinOrderAmount())) {
        customerGroupCampaign = null;
      }
    }

    return customerGroupCampaign;
  }

  public void createDiscount(Cashier cashier) {
    ShippingBean bean = getBean();
    CashierDiscount cashierDiscount = cashier.getDiscount();
    if (StringUtil.hasValue(cashierDiscount.getDiscountType())) {
      if (cashierDiscount.getDiscountType().equals(DiscountType.CUSTOMER.getValue())) {
        bean.setSelDiscountType(DiscountType.CUSTOMER.getValue());
      } else if (cashierDiscount.getDiscountType().equals(DiscountType.COUPON.getValue())) {
        bean.setSelDiscountType(DiscountType.COUPON.getValue());
        if (cashierDiscount.getCouponType().equals(CouponType.COMMON_DISTRIBUTION.getValue())) {
          bean.setPublicCouponCode(cashierDiscount.getDiscountCode());
        } else {
          bean.setSelPersonalCouponCode(cashierDiscount.getDiscountDetailCode());
        }
      }
    }
  }

  public List<CodeAttribute> createPersonalCouponList(Cashier cashier) {
    List<CodeAttribute> personalCouponList = new ArrayList<CodeAttribute>();
    personalCouponList.add(new NameValue(Messages.getString("web.action.front.order.ShippingBaseAction.2"), ""));


    String shopCode = cashier.getPayment().getShopCode();
    List<MyCouponInfo> aviableCouponList = getAviableCouponList(shopCode);

    for (MyCouponInfo newCouponHistory : aviableCouponList) {
      if (BigDecimalUtil.isAboveOrEquals(cashier.getTotalCommodityPrice(), newCouponHistory.getMinUseOrderAmount())) {
        String msg = "";
        if (newCouponHistory.getCouponIssueType().equals(CouponIssueType.FIXED.longValue())) {
          msg = " (￥" + newCouponHistory.getCouponAmount() + ")";
        } else if (newCouponHistory.getCouponIssueType().equals(CouponIssueType.PROPORTION.longValue())) {
          msg = " (" + newCouponHistory.getCouponProportion() + "%"
              + Messages.getString("web.action.front.order.ShippingBaseAction.3") + ")";
        }

        String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
        if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
          personalCouponList.add(new NameValue(newCouponHistory.getCouponName() + msg, newCouponHistory.getCouponIssueNo()));
        } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
          personalCouponList.add(new NameValue(newCouponHistory.getCouponNameJp() + msg, newCouponHistory.getCouponIssueNo()));
        } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
          personalCouponList.add(new NameValue(newCouponHistory.getCouponNameEn() + msg, newCouponHistory.getCouponIssueNo()));
        } else {
          personalCouponList.add(new NameValue(newCouponHistory.getCouponName() + msg, newCouponHistory.getCouponIssueNo()));
        }

      }
    }
    return personalCouponList;
  }

  public MyCouponInfo getAviableCouponSelected() {
    MyCouponInfo aviableCouponSelected = new MyCouponInfo();
    List<MyCouponInfo> aviableCouponList = getAviableCouponList(getBean().getCashier().getPaymentShopCode());

    if (aviableCouponList == null || aviableCouponList.size() < 1) {
      return new MyCouponInfo();
    }

    for (MyCouponInfo aviableCoupon : aviableCouponList) {
      if (aviableCoupon.getCouponIssueNo().equals(getBean().getSelPersonalCouponCode())) {
        aviableCouponSelected = aviableCoupon;
        break;
      }
    }

    return aviableCouponSelected;

  }

  /**
   * 可以使用的优惠券一览取得
   * 
   * @param shopCode
   * @return 可以使用的优惠券一览
   */
  public List<MyCouponInfo> getAviableCouponList(String shopCode) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    List<MyCouponInfo> newCouponHistoryList = service.getMyCoupon(getLoginInfo().getCustomerCode());
    List<MyCouponInfo> result = new ArrayList<MyCouponInfo>();

    List<String> objectCommodity = new ArrayList<String>();
    for (MyCouponInfo newCouponHistory : newCouponHistoryList) {

      BigDecimal objectTotalPrice = BigDecimal.ZERO;
      BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;

      // 朋友推荐 优惠券时没有USE_TYPE字段默认可使用
      if (StringUtil.isNullOrEmpty(newCouponHistory.getUseType())) {
        newCouponHistory.setUseType(0L);
      }

      List<NewCouponHistoryUseInfo> useCommodityList = service.getUseCommodityList(newCouponHistory.getCouponIssueNo());
      List<NewCouponHistoryUseInfo> brandList = service.getBrandCodeList(newCouponHistory.getCouponIssueNo());
      List<NewCouponHistoryUseInfo> importCommodityTypeList = service.getImportCommodityTypeList(newCouponHistory
          .getCouponIssueNo());
      List<NewCouponHistoryUseInfo> categoryList = service.getCategoryCodeList(newCouponHistory.getCouponIssueNo());
      boolean objectFlg = false;
      if (useCommodityList != null && useCommodityList.size() > 0) {
        objectFlg = true;
      }
      if (brandList != null && brandList.size() > 0) {
        objectFlg = true;
      }
      if (importCommodityTypeList != null && importCommodityTypeList.size() > 0) {
        objectFlg = true;
      }
      if (categoryList != null && categoryList.size() > 0) {
        objectFlg = true;
      }
      if (objectFlg) {
        objectCommodity = getObjectCommodity(shopCode, newCouponHistory.getCouponIssueNo(), useCommodityList, brandList,
            importCommodityTypeList, categoryList);
      }
      
      // 指定商品的购买金额取得
      Map<String, OptionalCommodity> optionalCommodityMap = getBean().getCashier().getOptionalCommodityMap();
      if (objectCommodity.size() > 0) {
        for (String commodityCode : objectCommodity) {
          for (ShippingHeaderBean shipping : getBean().getShippingList()) {
            for (ShippingDetailBean detail : shipping.getShippingList()) {
              for (ShippingCommodityBean commodity : detail.getCommodityList()) {
                if (commodityCode.equals(commodity.getCommodityCode())) {
                  Long amount =  NumUtil.toLong(commodity.getCommodityAmount());
                  Long ocNum = 0L;
                  Long eachNum = 0L;
                  for (String s : optionalCommodityMap.keySet()) {
                    if (commodity.getCommodityCode().equals(s.split(":")[0]) && !"true".equals(commodity.getIsDiscountCommodity())) {
                      OptionalCommodity oc = optionalCommodityMap.get(s);
                      BigDecimal cheapPrice = new BigDecimal(s.split(":")[1]);
                      ocNum += oc.getCommodityAmount();
                      eachNum = oc.getCommodityAmount();
                      BigDecimal realPrice = oc.getRealPrice().subtract(cheapPrice);
                      objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(realPrice,eachNum));
                    }
                  }
                  objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(),amount-ocNum));
                }
              }
            }
          }
        }
      }

      // 不使用
      if (newCouponHistory.getUseType().equals(1L)) {
        if (objectFlg) {
          objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice().subtract(objectTotalPrice);
        } else {
          objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
        }
        // 使用
      } else {
        if (objectFlg) {
          objectTotalPriceTemp = objectTotalPrice;
        } else {
          objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
        }
      }


      // 优惠券利用最小购买金额
      if (BigDecimalUtil.isAboveOrEquals(objectTotalPriceTemp, newCouponHistory.getMinUseOrderAmount())) {
        newCouponHistory.setObjectTotalPrice(objectTotalPriceTemp);
        result.add(newCouponHistory);
      }
    }

    return result;
  }

  /**
   * 购物车中满足优惠券使用规则（商品编号或品牌编号或商品区分品）的商品一览设定
   * 
   * @param shopCode
   * @param newCouponHistory
   *          优惠券发行履历信息
   * @param objectCommodity
   *          购物车中满足优惠券使用规则的商品一览
   */
  private List<String> getObjectCommodity(String shopCode, String couponIssueCode, List<NewCouponHistoryUseInfo> useCommodityList,
      List<NewCouponHistoryUseInfo> brandList, List<NewCouponHistoryUseInfo> importCommodityTypeList,
      List<NewCouponHistoryUseInfo> categoryList) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();

    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          commodityListOfCart.add(cs.getCommodityHeader(shopCode, commodity.getCommodityCode()));
        }
      }
    }
    List<String> useCommodityCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo useCommodity : useCommodityList) {
      useCommodityCodeList.add(useCommodity.getCommodityCode());
    }

    List<String> brandCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo brand : brandList) {
      brandCodeList.add(brand.getBrandCode());
    }

    List<Long> importCommodityTypeCodeList = new ArrayList<Long>();
    for (NewCouponHistoryUseInfo importCommodityType : importCommodityTypeList) {
      importCommodityTypeCodeList.add(importCommodityType.getImportCommodityType());
    }

    List<String> caregoryCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo category : categoryList) {
      caregoryCodeList.add(category.getCategoryCode());
    }

    for (CommodityHeader ch : commodityListOfCart) {

      // 使用关联信息(商品编号)
      if (useCommodityCodeList != null && useCommodityCodeList.size() > 0 && useCommodityCodeList.contains(ch.getCommodityCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(品牌编号)
      if (brandCodeList != null && brandCodeList.size() > 0 && brandCodeList.contains(ch.getBrandCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(商品区分品)
      if (importCommodityTypeCodeList != null && importCommodityTypeCodeList.size() > 0
          && importCommodityTypeCodeList.contains(ch.getImportCommodityType())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(分类编号)
      if (caregoryCodeList != null && caregoryCodeList.size() > 0) {
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categorys = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          existCategory = false;
        } else {
          existCategory = true;
        }
        for (String categoryCode : categorys) {
          if (caregoryCodeList.contains(categoryCode)) {
            existCategory = true;
            break;
          }
        }
        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
    }
    return objectCommodity;
  }

  // 指定商品的购买金额取得
  public BigDecimal getTotalRelatePrice(NewCouponRule newCouponRule) {
    BigDecimal objectTotalPrice = BigDecimal.ZERO;
    BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;

    // 朋友推荐 优惠券时没有USE_TYPE字段默认可使用
    if (StringUtil.isNullOrEmpty(newCouponRule.getUseType())) {
      newCouponRule.setUseType(0L);
    }

    if (newCouponRule.getObjectCommodities() == null) {
      newCouponRule.setObjectCommodities("");
    }
    String[] commoditys = newCouponRule.getObjectCommodities().split(";");

    if (newCouponRule.getObjectBrand() == null) {
      newCouponRule.setObjectBrand("");
    }
    String[] brands = newCouponRule.getObjectBrand().split(";");

    if (newCouponRule.getObjectCategory() == null) {
      newCouponRule.setObjectCategory("");
    }
    String[] categorys = newCouponRule.getObjectCategory().split(";");

    boolean objectFlg = false;
    boolean partailFlg = false;
    if (commoditys != null && commoditys.length > 0 && StringUtil.hasValue(commoditys[0])) {
      objectFlg = true;
    }
    if (brands != null && brands.length > 0 && StringUtil.hasValue(brands[0])) {
      objectFlg = true;
      partailFlg = true;
    }
    if (categorys != null && categorys.length > 0 && StringUtil.hasValue(categorys[0])) {
      objectFlg = true;
      partailFlg = true;
    }
    List<String> objectCommodity = new ArrayList<String>();
    //部分使用对应
    if ( partailFlg &&  (newCouponRule.getUseType().equals(2L) || newCouponRule.getUseType().equals(3L))) {
      objectTotalPrice = getCommodityLimitObjectPrice(newCouponRule, brands, categorys);
      return objectTotalPrice;
    } else if (objectFlg) {
      objectCommodity = getPublicObjectCommodity("00000000", newCouponRule, commoditys, brands, categorys);
    }
    
    // 指定商品的购买金额取得
    if (objectCommodity.size() > 0 ) {
      for (String commodityCode : objectCommodity) {
        for (ShippingHeaderBean shipping : getBean().getShippingList()) {
          for (ShippingDetailBean detail : shipping.getShippingList()) {
            for (ShippingCommodityBean commodity : detail.getCommodityList()) {
              if (commodityCode.equals(commodity.getCommodityCode())) {
                  objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(), NumUtil
                      .toLong(commodity.getCommodityAmount())));
              }
            }
          }
        }
      }
    }
    // 不使用
    if (newCouponRule.getUseType().equals(1L)) {
      if (objectFlg) {
        objectTotalPriceTemp = getBean().getCashier().getNotOptionalCommodityPrice().subtract(objectTotalPrice);
      } else {
        objectTotalPriceTemp = getBean().getCashier().getNotOptionalCommodityPrice();
      }
      // 使用
    } else{
      if (objectFlg) {
        objectTotalPriceTemp = objectTotalPrice;
      } else {
        objectTotalPriceTemp = getBean().getCashier().getNotOptionalCommodityPrice();
      }
    }

    return objectTotalPriceTemp;
  }
  
  
  /*
   * 个人优惠券的相关联商品信息存储在new_coupon_rule_use_info表中，所以要传入此参数
   */
  public BigDecimal getTotalRelatePrice(NewCouponRule newCouponRule, List<NewCouponRuleUseInfo> newCouponRuleUseInfoList) {
    BigDecimal objectTotalPrice = BigDecimal.ZERO;
    BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;
    String relatedCommodity = "";
    String relatedBrand = "";
    String relatedCategory = "";
    
    // 由于存入new_coupon_rule_use_info时是add一个商品，品牌，分类就新增一条记录，所以把list中的记录存入string中以分号分割。
    for(NewCouponRuleUseInfo newCouponRuleUseInfo : newCouponRuleUseInfoList) {
      if(StringUtil.hasValue(newCouponRuleUseInfo.getCommodityCode())) {
        relatedCommodity += newCouponRuleUseInfo.getCommodityCode() + ";";
      }
      if(StringUtil.hasValue(newCouponRuleUseInfo.getBrandCode())) {
        relatedBrand += newCouponRuleUseInfo.getBrandCode() + ";";
      }
      if(StringUtil.hasValue(newCouponRuleUseInfo.getCategoryCode())) {
        relatedCategory += newCouponRuleUseInfo.getCategoryCode() + ";";
      }
    }
    
    
    
    
    // 朋友推荐 优惠券时没有USE_TYPE字段默认可使用
    if (StringUtil.isNullOrEmpty(newCouponRule.getUseType())) {
      newCouponRule.setUseType(0L);
    }

    String[] commoditys = relatedCommodity.split(";");

    String[] brands = relatedBrand.split(";");

    String[] categorys = relatedCategory.split(";");

    boolean objectFlg = false;
    boolean partailFlg = false;
    if (commoditys != null && commoditys.length > 0 && StringUtil.hasValue(commoditys[0])) {
      objectFlg = true;
    }
    if (brands != null && brands.length > 0 && StringUtil.hasValue(brands[0])) {
      objectFlg = true;
      partailFlg = true;
    }
    if (categorys != null && categorys.length > 0 && StringUtil.hasValue(categorys[0])) {
      objectFlg = true;
      partailFlg = true;
    }
    List<String> objectCommodity = new ArrayList<String>();
    //部分使用对应
    if ( partailFlg &&  (newCouponRule.getUseType().equals(2L) || newCouponRule.getUseType().equals(3L))) {
      objectTotalPrice = getCommodityLimitObjectPrice(newCouponRule, brands, categorys);
      return objectTotalPrice;
    } else if (objectFlg) {
      objectCommodity = getPersonnalObjectCommodity("00000000", commoditys, brands, categorys);
    }
    
    // 指定商品的购买金额取得
    if (objectCommodity.size() > 0 ) {
      for (String commodityCode : objectCommodity) {
        for (ShippingHeaderBean shipping : getBean().getShippingList()) {
          for (ShippingDetailBean detail : shipping.getShippingList()) {
            for (ShippingCommodityBean commodity : detail.getCommodityList()) {
              if (commodityCode.equals(commodity.getCommodityCode())) {
                  objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(), NumUtil
                      .toLong(commodity.getCommodityAmount())));
              }
            }
          }
        }
      }
    }
    // 不使用
    if (newCouponRule.getUseType().equals(1L)) {
      if (objectFlg) {
        objectTotalPriceTemp = getBean().getCashier().getNotOptionalCommodityPrice().subtract(objectTotalPrice);
      } else {
        objectTotalPriceTemp = getBean().getCashier().getNotOptionalCommodityPrice();
      }
      // 使用
    } else{
      if (objectFlg) {
        objectTotalPriceTemp = objectTotalPrice;
      } else {
        objectTotalPriceTemp = getBean().getCashier().getNotOptionalCommodityPrice();
      }
    }

    return objectTotalPriceTemp;
  }
  
  
  public BigDecimal getCommodityLimitObjectPrice(NewCouponRule rule,String[] brands,String[] categorys) {
    
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();
    
    Map<String,List<ShippingCommodityBean>> objectMap = new HashMap<String,List<ShippingCommodityBean>>();
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          // A件B元对应  不能利用在公公优惠券
          if (getBean().getCashier().getOpCommodityCodeList().contains(commodity.getCommodityCode()) 
              && !"true".equals(commodity.getIsDiscountCommodity())) {
            continue;
          }
          
          CommodityHeader ch = cs.getCommodityHeader("00000000", commodity.getCommodityCode());
          
          
          
          //品牌对应
          for (int i = 0; i < brands.length; i++) {
            if (!objectCommodity.contains(ch.getCommodityCode()) && brands[i].split(":")[0].equals(ch.getBrandCode())) {
              objectCommodity.add(ch.getCommodityCode());
              if (objectMap.get(brands[i]) == null) {
                List<ShippingCommodityBean> list = new ArrayList<ShippingCommodityBean>();
                list.add(commodity);
                objectMap.put(brands[i], list);
              } else {
                objectMap.get(brands[i]).add(commodity);
              }
              break;
            }
          }
      
          // 分类对应
          String categoryPath = ch.getCategoryPath();
          categoryPath = categoryPath.replaceAll("/", "");
          categoryPath = categoryPath.replaceAll("#", "");
          String[] categoryDb = categoryPath.split("~");
          String keyCategory = "";
          boolean existCategory = false;
          if (StringUtil.hasValue(categoryPath)) {
            for (int i = 0; i < categorys.length; i++) {
              for (int j = 0; j < categoryDb.length; j++) {
                if (StringUtil.hasValue(categoryDb[j]) &&  categorys[i].split(":")[0].equals(categoryDb[j]) ) {
                  existCategory = true;
                  keyCategory = categorys[i];
                  break;
                }
              }
              if (existCategory) {
                break;
              }
            }
          } else {
            existCategory = false;
          }
    
          if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
            objectCommodity.add(ch.getCommodityCode());
            if (objectMap.get(keyCategory) == null) {
              List<ShippingCommodityBean> list = new ArrayList<ShippingCommodityBean>();
              list.add(commodity);
              objectMap.put(keyCategory, list);
            } else {
              objectMap.get(keyCategory).add(commodity);
            }
          }
        }
      }
    }
    
    // 不限制必须购买分类或者品牌对应
    if (rule.getUseType().equals(3L)) {
      if (objectMap.keySet() == null) {
        return new BigDecimal("-100");
      } else if (objectMap.keySet().size() == 0) {
        return new BigDecimal("-100");
      }
    }

    
    BigDecimal partialObjectPrice = BigDecimal.ZERO;
    BigDecimal allObjectPrice = BigDecimal.ZERO; 
    //拆开
    for (String s : objectMap.keySet()) {
      List<ShippingCommodityBean> changeOneList = new ArrayList<ShippingCommodityBean>();
      //把商品拆成单个数量加入集合
      for (ShippingCommodityBean commodity : objectMap.get(s)) {
        for (int i = 0 ; i< Integer.parseInt(commodity.getCommodityAmount()) ;i++) {
          allObjectPrice = BigDecimalUtil.add(allObjectPrice, commodity.getRetailPrice());
          changeOneList.add(commodity);
        }
      }
      //按照价格排序
      ShippingCommodityBean scb = new ShippingCommodityBean();
      for (int i=0 ; i < changeOneList.size() ; i++) {
        for (int j=i+1 ; j < changeOneList.size() ; j++) {
          if (BigDecimalUtil.isBelow(changeOneList.get(i).getRetailPrice(), changeOneList.get(j).getRetailPrice())){
            scb = changeOneList.get(i);
            changeOneList.set(i, changeOneList.get(j)) ;
            changeOneList.set(j, scb);
          }
        }
      }
      //计算符合条件部分商品总金额
      int objectNum = 0;
      if(s.split(":").length > 1) {
        objectNum = Integer.parseInt(s.split(":")[1]);
      } else {
        objectNum = 9999;
      }

      int bigNum = objectNum > changeOneList.size() ? changeOneList.size() : objectNum;
      
      for (int i = 0 ; i < bigNum ; i++) {
        partialObjectPrice = BigDecimalUtil.add(partialObjectPrice, changeOneList.get(i).getRetailPrice());
      }
      //objectMap.put(s, changeOneList);
    }
    
    if (rule.getUseType().equals(3L)) {
      partialObjectPrice = BigDecimalUtil.subtract(getBean().getCashier().getTotalCommodityPrice(), allObjectPrice.subtract(partialObjectPrice));
    }
    return partialObjectPrice;
  }
  
  
  

//  public Map<String, String> getCommodityLimitNum(String shopCode, NewCouponRule rule) {
//    Map<String, String> result = new HashMap<String, String>();
//    if (!StringUtil.hasValueAnyOf(rule.getObjectBrand(), rule.getObjectCategory())) {
//      return result;
//    }
//
//    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
//    Map<String,ShippingCommodityBean> cartMap = new HashMap<String,ShippingCommodityBean>();
//    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
//
//    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
//      for (ShippingDetailBean detail : shipping.getShippingList()) {
//        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
//          commodityListOfCart.add(cs.getCommodityHeader(shopCode, commodity.getCommodityCode()));
//          cartMap.put(commodity.getCommodityCode(), commodity);
//        }
//      }
//    }
//
//    String[] brands = rule.getObjectBrand().split(";");
//    for (CommodityHeader ch : commodityListOfCart) {
//
//      // 使用关联信息(品牌编号)
//
//      for (int i = 0; i < brands.length; i++) {
//
//        String[] brandInfo = brands[i].split(":");
//        if (!result.containsKey(ch.getCommodityCode()) && brandInfo[0].equals(ch.getBrandCode())) {
//          String limitNum = "";
//          if (brandInfo.length > 1) {
//            limitNum = brandInfo[1];
//          }
//          result.put(ch.getCommodityCode(), limitNum);
//          break;
//        }
//      }
//
//      // 使用关联信息(分类编号)
//      String[] categorys = rule.getObjectCategory().split(";");
//      String categoryPath = ch.getCategoryPath();
//      categoryPath = categoryPath.replaceAll("/", "");
//      categoryPath = categoryPath.replaceAll("#", "");
//      String[] categoryDb = categoryPath.split("~");
//      boolean existCategory = false;
//      String limitNum = "";
//      if (StringUtil.hasValue(categoryPath)) {
//        for (int i = 0; i < categorys.length; i++) {
//          String[] categoryInfo = categorys[i].split(":");
//          for (int j = 0; j < categoryDb.length; j++) {
//            if (categoryInfo[0].equals(categoryDb[j]) && StringUtil.hasValue(categoryDb[j])) {
//              existCategory = true;
//              if (categoryInfo.length > 1) {
//                limitNum = categoryInfo[1];
//              }
//              break;
//            }
//          }
//          if (existCategory) {
//            break;
//          }
//        }
//      } else {
//        existCategory = true;
//        limitNum = "1";
//      }
//
//      if (!result.containsKey(ch.getCommodityCode()) && existCategory) {
//        result.put(ch.getCommodityCode(), limitNum);
//      }
//    }
//    return result;
//  }
//
//    
  
  
  
  public List<String> getPublicObjectCommodity(String shopCode, NewCouponRule useCommodity, String[] commoditys, String[] brands,
      String[] categorys) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();

    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          // A件B元对应  不能利用在公公优惠券
          if (!getBean().getCashier().getOpCommodityCodeList().contains(commodity.getCommodityCode())
              || "true".equals(commodity.getIsDiscountCommodity())) {
            commodityListOfCart.add(cs.getCommodityHeader(shopCode, commodity.getCommodityCode()));
          }
        }
      }
    }
    if (!StringUtil.hasValueAnyOf(useCommodity.getObjectCommodities(), useCommodity.getObjectBrand(), useCommodity
        .getObjectCategory())) {
      for (CommodityHeader ch : commodityListOfCart) {
        objectCommodity.add(ch.getCommodityCode());
      }
      return objectCommodity;
    }

    for (CommodityHeader ch : commodityListOfCart) {

      if (commoditys != null && commoditys[0] != null) {
        // 使用关联信息(商品编号)
        for (int i = 0; i < commoditys.length; i++) {
          if (!objectCommodity.contains(ch.getCommodityCode()) && commoditys[i].equals(ch.getCommodityCode())) {
            objectCommodity.add(ch.getCommodityCode());
            break;
          }
        }

        // 使用关联信息(品牌编号)
        for (int i = 0; i < brands.length; i++) {
          if (!objectCommodity.contains(ch.getCommodityCode()) && brands[i].split(":")[0].equals(ch.getBrandCode())) {
            objectCommodity.add(ch.getCommodityCode());
            break;
          }
        }

        // 使用关联信息(分类编号)
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categoryDb = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          for (int i = 0; i < categorys.length; i++) {
            for (int j = 0; j < categoryDb.length; j++) {
              if (categorys[i].split(":")[0].equals(categoryDb[j]) && StringUtil.hasValue(categoryDb[j])) {
                existCategory = true;
                break;
              }
            }
            if (existCategory) {
              break;
            }
          }
        } else {
          existCategory = true;
        }

        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
    }

    return objectCommodity;
  }
  
  public List<String> getPersonnalObjectCommodity(String shopCode, String[] commoditys, String[] brands, String[] categorys) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();

    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          // A件B元对应  不能利用在公公优惠券
          if (!getBean().getCashier().getOpCommodityCodeList().contains(commodity.getCommodityCode())
              || "true".equals(commodity.getIsDiscountCommodity())) {
            commodityListOfCart.add(cs.getCommodityHeader(shopCode, commodity.getCommodityCode()));
          }
        }
      }
    }
    if (commoditys.length == 0 && brands.length == 0 && categorys.length == 0) {
      for (CommodityHeader ch : commodityListOfCart) {
        objectCommodity.add(ch.getCommodityCode());
      }
      return objectCommodity;
    }

    for (CommodityHeader ch : commodityListOfCart) {

      if (commoditys != null && commoditys[0] != null) {
        // 使用关联信息(商品编号)
        for (int i = 0; i < commoditys.length; i++) {
          if (!objectCommodity.contains(ch.getCommodityCode()) && commoditys[i].equals(ch.getCommodityCode())) {
            objectCommodity.add(ch.getCommodityCode());
            break;
          }
        }

        // 使用关联信息(品牌编号)
        for (int i = 0; i < brands.length; i++) {
          if (!objectCommodity.contains(ch.getCommodityCode()) && brands[i].split(":")[0].equals(ch.getBrandCode())) {
            objectCommodity.add(ch.getCommodityCode());
            break;
          }
        }

        // 使用关联信息(分类编号)
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categoryDb = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          for (int i = 0; i < categorys.length; i++) {
            for (int j = 0; j < categoryDb.length; j++) {
              if (categorys[i].split(":")[0].equals(categoryDb[j]) && StringUtil.hasValue(categoryDb[j])) {
                existCategory = true;
                break;
              }
            }
            if (existCategory) {
              break;
            }
          }
        } else {
          existCategory = true;
        }

        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
    }

    return objectCommodity;
  }

  public NewCouponHistory getPersonalCoupon(String customerCode, String personalCouponCode, BigDecimal totalCommodityPrice) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    NewCouponHistory newCouponHistory = customerService.getUnusedPersonalCoupon(customerCode, personalCouponCode);
    if (newCouponHistory != null) {
      if (BigDecimalUtil.isBelow(totalCommodityPrice, newCouponHistory.getMinUseOrderAmount())) {
        newCouponHistory = null;
      }
    }

    return newCouponHistory;
  }

  private void setDeliveryAppointedTimeZoneList() {
    if (StringUtil.hasValue(getBean().getCashier().getPayment().getPaymentMethodCode())) {
      CashierShipping shipping = getBean().getCashier().getShippingList().get(0);
      ShippingDetailBean shippingDetailBean = getBean().getShippingList().get(0).getShippingList().get(0);
      if (shippingDetailBean.getDeliveryAppointedDateList().size() > 0) {
        String deliveryAppointedDate = shippingDetailBean.getDeliveryAppointedDate();
        UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
        List<CodeAttribute> deliveryTimeZoneList = utilService.getDeliveryTimeList(shipping.getShopCode(), shipping.getAddress()
            .getPrefectureCode(), getBean().getCashier().getPayment().getSelectPayment().isCashOnDelivery(), deliveryAppointedDate,
            shipping.getAddress().getAreaCode(), getBean().getCashier().getTotalWeight().toString(), getBean()
                .getSelectedDeliveryCompany().getDeliveryCompanyNo());
        shippingDetailBean.setDeliveryAppointedTimeZoneList(deliveryTimeZoneList);
      }
    }
  }


  private void setAddressList() {
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // 2012-09-06 被LC提取到Init方法中，用以防止重复加载导致访问过慢
    getBean().getAdditionalAddressEdit().setAdditionalPrefectureList(s.createPrefectureList());
    getBean().getAdditionalAddressEdit().setAdditionalCityList(
        s.createCityList(getBean().getAdditionalAddressEdit().getAdditionalPrefectureCode()));
    getBean().getAdditionalAddressEdit().setAdditionalAreaList(
        s.createAreaList(getBean().getAdditionalAddressEdit().getAdditionalPrefectureCode(), getBean().getAdditionalAddressEdit()
            .getAdditionalCityCode()));
  }

  public void refreshItems(boolean deliveryAppointedTimeZone, boolean additionalBlock) {
    // 地址列表
    setAddressList();
    // 配送指定日期
    if (deliveryAppointedTimeZone) {
      setDeliveryAppointedTimeZoneList();
    }
    // 收货地址追加显示
    getBean().setAdditionalBlock(additionalBlock == true ? WebConstantCode.DISPLAY_BLOCK : WebConstantCode.DISPLAY_NONE);
  }


  public void createDeliveryToBean(ShippingBean bean) {
    UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    ShippingDetailBean sdBean = bean.getShippingList().get(0).getShippingList().get(0);
    String shopCode = sdBean.getShopCode();
    String prefectureCode = bean.getSelPrefectureCode();
    // 获得支付类型
    String paymentMethodType = "";
    if (StringUtil.isNullOrEmpty(bean.getOrderPayment().getPaymentMethodCode())) {
      // 如果支付方式未选择，则不表示配送公司列表，退出本方法
      return;
    } else {
      for (PaymentTypeBase ptb : bean.getOrderPayment().getDisplayPaymentList()) {
        if (ptb.getPaymentMethodCode().equals(bean.getOrderPayment().getPaymentMethodCode())) {
          paymentMethodType = ptb.getPaymentMethodType();
          break;
        }
      }
    }
    // 是否货到付款
    Boolean codFlg = paymentMethodType.equals(PaymentMethodType.CASH_ON_DELIVERY.getValue()) ? true : false;

    // 订单重量计算
    BigDecimal subTotalWeight = bean.getCashier().getTotalWeight();
    BigDecimal subTotalPrice = bean.getCashier().getTotalCommodityPrice();

    // 获得配送公司
    // 2013/04/21 优惠券对应 ob update start
    String cityCode = bean.getSelCityCode();
    String areaCode = bean.getSelAreaCode();
    List<DeliveryCompany> dc = utilService.getDeliveryCompanys(shopCode, prefectureCode, cityCode, areaCode, codFlg, subTotalWeight
        .toString());
    // 2013/04/21 优惠券对应 ob update end

    // 封装List<DeliveryCompanyBean>
    List<DeliveryCompanyBean> lsDcb = new ArrayList<DeliveryCompanyBean>();
    DeliveryCompanyBean cacheDCBean = new DeliveryCompanyBean();
    cacheDCBean.setDeliveryCompanyNo("D000");
    boolean isFree = isFreeShippingCharge(bean.getCashier());
    for (DeliveryCompany deliveryCompany : dc) {
      if (!cacheDCBean.getDeliveryCompanyNo().equals(deliveryCompany.getDeliveryCompanyNo())) {
        DeliveryCompanyBean dcb = new DeliveryCompanyBean();
        dcb.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
        dcb.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
        if (!isFree) { // 如果不满足活动条件
          dcb.setDeliveryCharge(shopService.getShippingCharge(prefectureCode, subTotalPrice, subTotalWeight, deliveryCompany
              .getDeliveryCompanyNo()));
        } else {
          dcb.setDeliveryCharge(BigDecimal.ZERO);
        }
        lsDcb.add(dcb);
      }
      cacheDCBean.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
    }
    // 设置Bean可以指定的配送公司列表
    bean.setDeliveryCompanyList(lsDcb);

    DeliveryCompanyBean dcBean = new DeliveryCompanyBean();
    if (bean.getCashier().getDelivery() != null) {// 配送公司选择时，验证选中的配送公司是否在列表中
      boolean flg = false;

      for (DeliveryCompanyBean dcb : lsDcb) {
        if (bean.getCashier().getDelivery().getDeliveryCompanyCode().equals(dcb.getDeliveryCompanyNo())) {
          flg = true;
          break;
        }
      }

      if (flg) {
        dcBean.setDeliveryCompanyNo(bean.getCashier().getDelivery().getDeliveryCompanyCode());
      } else {
        String strDeliveryCompanyNo = "";
        String strDeliveryCompanyNoYmd = "";
        BigDecimal deliveryCharge = BigDecimal.ZERO;
        for (DeliveryCompanyBean dcb : lsDcb) {
          BigDecimal cacheDeliveryCharge = BigDecimal.ZERO;
          cacheDeliveryCharge = shopService.getShippingCharge(prefectureCode, subTotalPrice, subTotalWeight, dcb
              .getDeliveryCompanyNo());
          // 循环第一轮时，为运费deliveryCharge赋值
          if (BigDecimalUtil.equals(deliveryCharge, BigDecimal.ZERO)) {
            deliveryCharge = cacheDeliveryCharge;
          }
          // 两条以上的配送公司，默认选择运费最低的
          if (BigDecimalUtil.isBelowOrEquals(cacheDeliveryCharge, deliveryCharge)) {
            deliveryCharge = cacheDeliveryCharge;
            strDeliveryCompanyNo = dcb.getDeliveryCompanyNo();
          }
          if (!StringUtil.isNullOrEmpty(dcb.getDeliveryCompanyNo()) && dcb.getDeliveryCompanyNo().equals("D006")) {
            strDeliveryCompanyNoYmd = dcb.getDeliveryCompanyNo();
          }
        }
        if (!StringUtil.isNullOrEmpty(strDeliveryCompanyNoYmd)) {
          BigDecimal cacheDeliveryCharge = shopService.getShippingCharge(prefectureCode, subTotalPrice, subTotalWeight,
              strDeliveryCompanyNoYmd);
          bean.getShippingList().get(0).getShippingList().get(0).setShippingCharge(cacheDeliveryCharge);
          bean.getCashier().getShippingList().get(0).setShippingCharge(cacheDeliveryCharge);
          dcBean.setDeliveryCompanyNo(strDeliveryCompanyNoYmd);
        } else {
          dcBean.setDeliveryCompanyNo(strDeliveryCompanyNo);
        }
      }
      bean.setSelectedDeliveryCompany(dcBean);
    } else { // 未选择配送公司时
      if (lsDcb.size() > 1) {
        String strDeliveryCompanyNo = "";
        String strDeliveryCompanyNoYmd = "";
        BigDecimal deliveryCharge = BigDecimal.ZERO;
        for (DeliveryCompanyBean dcb : lsDcb) {
          BigDecimal cacheDeliveryCharge = BigDecimal.ZERO;
          cacheDeliveryCharge = shopService.getShippingCharge(prefectureCode, subTotalPrice, subTotalWeight, dcb
              .getDeliveryCompanyNo());
          // 循环第一轮时，为运费deliveryCharge赋值
          if (BigDecimalUtil.equals(deliveryCharge, BigDecimal.ZERO)) {
            deliveryCharge = cacheDeliveryCharge;
          }
          // 两条以上的配送公司，默认选择运费最低的
          if (BigDecimalUtil.isBelowOrEquals(cacheDeliveryCharge, deliveryCharge)) {
            deliveryCharge = cacheDeliveryCharge;
            strDeliveryCompanyNo = dcb.getDeliveryCompanyNo();
          }
          if (!StringUtil.isNullOrEmpty(dcb.getDeliveryCompanyNo()) && dcb.getDeliveryCompanyNo().equals("D006")) {
            strDeliveryCompanyNoYmd = dcb.getDeliveryCompanyNo();
          }
        }
        if (!StringUtil.isNullOrEmpty(strDeliveryCompanyNoYmd)) {
          BigDecimal cacheDeliveryCharge = shopService.getShippingCharge(prefectureCode, subTotalPrice, subTotalWeight,
              strDeliveryCompanyNoYmd);
          bean.getShippingList().get(0).getShippingList().get(0).setShippingCharge(cacheDeliveryCharge);
          bean.getCashier().getShippingList().get(0).setShippingCharge(cacheDeliveryCharge);
          dcBean.setDeliveryCompanyNo(strDeliveryCompanyNoYmd);
        } else {
          dcBean.setDeliveryCompanyNo(strDeliveryCompanyNo);
        }

        bean.setSelectedDeliveryCompany(dcBean);
      }
    }
    if (isFree) {
      bean.getShippingList().get(0).getShippingList().get(0).setShippingCharge(BigDecimal.ZERO);
      bean.getCashier().getShippingList().get(0).setShippingCharge(BigDecimal.ZERO);
    }
    List<CashierShipping> shippings = new ArrayList<CashierShipping>();
    shippings.addAll(bean.getCashier().getShippingList());
    List<CodeAttribute> deliveryDateList = utilService.getDeliveryDateList(shippings.get(0).getShopCode(), shippings.get(0)
        .getAddress().getPrefectureCode(), bean.getCashier().getPayment().getSelectPayment().isCashOnDelivery(), shippings.get(0)
        .getCommodityInfoList(), bean.getCashier().getTotalWeight().toString(), bean.getSelectedDeliveryCompany()
        .getDeliveryCompanyNo());
    sdBean.setDeliveryAppointedDateList(deliveryDateList);
    if (deliveryDateList.size() > 0) {
      String deliveryAppointedDate = shippings.get(0).getDeliveryAppointedDate();
      if (StringUtil.isNullOrEmpty(deliveryAppointedDate)) {
        deliveryAppointedDate = deliveryDateList.get(0).getValue();
      }
      List<CodeAttribute> deliveryTimeZoneList = utilService.getDeliveryTimeList(shippings.get(0).getShopCode(), shippings.get(0)
          .getAddress().getPrefectureCode(), bean.getCashier().getPayment().getSelectPayment().isCashOnDelivery(),
          deliveryAppointedDate, shippings.get(0).getAddress().getAreaCode(), bean.getCashier().getTotalWeight().toString(), bean
              .getSelectedDeliveryCompany().getDeliveryCompanyNo());
      sdBean.setDeliveryAppointedTimeZoneList(deliveryTimeZoneList);
    }

  }

  // add by lc 2012-08-28 end

  /**
   * 根据ShippingHeaderBean查看本次发货是否是免邮费
   * 
   * @param bean
   * @return
   */
  private boolean isFreeShippingCharge(Cashier bean) {
    boolean result = false;

    CampainFilter cf = new CampainFilter();
    CampaignInfo campaignI = new CampaignInfo();
    campaignI.setMobileNumber(bean.getShippingList().get(0).getAddress().getMobileNumber());
    campaignI.setPhoneNumber(bean.getShippingList().get(0).getAddress().getPhoneNumber());
    campaignI.setLastName(bean.getShippingList().get(0).getAddress().getAddressLastName());
    campaignI.setAddress1(bean.getShippingList().get(0).getAddress().getAddress1());
    campaignI.setAddress2(bean.getShippingList().get(0).getAddress().getAddress2());
    campaignI.setAddress3(bean.getShippingList().get(0).getAddress().getAddress3());
    campaignI.setAddress4(bean.getShippingList().get(0).getAddress().getAddress4());
    campaignI.setCustomerCode(bean.getShippingList().get(0).getAddress().getCustomerCode());
    campaignI.setPrefectureCode(bean.getShippingList().get(0).getAddress().getPrefectureCode());
    List<OrderDetail> commodityList = new ArrayList<OrderDetail>();

    for (CartCommodityInfo detail : bean.getShippingList().get(0).getCommodityInfoList()) {
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        OrderDetail orderD = new OrderDetail();
        orderD.setCommodityCode(detail.getCommodityCode());
        orderD.setPurchasingAmount(NumUtil.toLong(detail.getQuantity() + ""));
        commodityList.add(orderD);
      }
    }
    campaignI.setCommodityList(commodityList);
    result = cf.isFreeShippingCharge(campaignI);
    return result;
  }

  /**
   * 根据mediaCode查看本次发货是否是免邮费
   * 
   * @param bean
   * @param mediaCode
   * @return
   */
  private boolean isFree(ShippingBean bean, String mediaCode) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());

    boolean freeFlg = false;
    FreePostageListSearchCondition condition = new FreePostageListSearchCondition();
    condition.setSearchStatus(ActivityStatus.IN_PROGRESS.getValue());
    List<FreePostageRule> resultList = service.getFreePostageList(condition).getRows();
    for (FreePostageRule rule : resultList) {
      boolean successFlg = true;
      BigDecimal objectTotalPrice = BigDecimal.ZERO;
      BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;
      BigDecimal objectTotalWeightTemp = BigDecimal.ZERO;

      if (StringUtil.isNullOrEmpty(rule.getUseType())) {
        rule.setUseType(UseFlagObject.ABLE.longValue());
      }

      // 适用区域限制
      boolean areaFlg = false;
      if (StringUtil.hasValue(rule.getApplicableArea())) {
        String[] areas = rule.getApplicableArea().split(";");
        for (int i = 0; i < areas.length; i++) {
          if (bean.getSelPrefectureCode().equals(areas[i])) {
            areaFlg = true;
            break;
          }
        }
        successFlg = areaFlg;
      }

      // 会员限定
      if (successFlg) {
        if (rule.getApplicableObject().equals(ApplicableObject.FIRST.longValue())) {
          Long orderCount = orderService.countUsedCouponFirstOrder(bean.getCustomerCode());
          if (orderCount > 0L) {
            successFlg = false;
          }
        }
      }

      // 发布媒体限定
      if (successFlg) {
        String[] mediaCodes = new String[] {};

        if (StringUtil.hasValue(rule.getObjectIssueCode())) {
          mediaCodes = rule.getObjectIssueCode().split(";");
          boolean mediaFlg = false;
          for (String mc : mediaCodes) {
            if (mc.equals(mediaCode)) {
              mediaFlg = true;
              break;
            }
          }
          successFlg = mediaFlg;
        } else {
          successFlg = false;
        }
      }

      // 重量限定
      objectTotalWeightTemp = getBean().getCashier().getTotalWeight();
      if (rule.getWeightLimit() != null && BigDecimalUtil.isAbove(objectTotalWeightTemp, rule.getWeightLimit())) {
        successFlg = false;
      }

      if (successFlg) {
        String[] commoditys = new String[] {};
        String[] brands = new String[] {};
        String[] categorys = new String[] {};

        if (StringUtil.hasValue(rule.getObjectCommodity())) {
          commoditys = rule.getObjectCommodity().split(";");
        }
        if (StringUtil.hasValue(rule.getObjectBrand())) {
          brands = rule.getObjectBrand().split(";");
        }
        if (StringUtil.hasValue(rule.getObjectCategory())) {
          categorys = rule.getObjectCategory().split(";");
        }

        boolean objectFlg = false;
        if (commoditys.length > 0 || brands.length > 0 || categorys.length > 0) {
          objectFlg = true;
        }

        List<String> objectCommodity = new ArrayList<String>();
        if (objectFlg) {
          objectCommodity = getFreeObjectCommodity(bean, commoditys, brands, categorys);
        }

        // 指定商品的购买金额/重量取得
        if (objectCommodity.size() > 0) {
          for (String commodityCode : objectCommodity) {
            for (ShippingHeaderBean shipping : bean.getShippingList()) {
              for (ShippingDetailBean detail : shipping.getShippingList()) {
                for (ShippingCommodityBean commodity : detail.getCommodityList()) {
                  if (commodityCode.equals(commodity.getCommodityCode())) {
                    objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(),
                        NumUtil.toLong(commodity.getCommodityAmount())));
                  }
                }
              }
            }
          }
        }

        // 不使用
        if (rule.getUseType().equals(UseFlagObject.UNABLE.longValue())) {
          if (objectFlg) {
            objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice().subtract(objectTotalPrice);
            // 购买的商品必须全部不满足活动
            if (objectCommodity.size() > 0) {
              successFlg = false;
            }
          } else {
            objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
          }
          // 使用
        } else {
          if (objectFlg) {
            objectTotalPriceTemp = objectTotalPrice;
            if (objectCommodity.size() < 1) {
              successFlg = false;
            } else {
              // 购买的商品必须全部满足活动
              for (ShippingHeaderBean shipping : bean.getShippingList()) {
                for (ShippingDetailBean detail : shipping.getShippingList()) {
                  for (ShippingCommodityBean commodity : detail.getCommodityList()) {
                    if (!commodity.isGift() && !objectCommodity.contains(commodity.getCommodityCode())) {
                      successFlg = false;
                      break;
                    }
                  }
                }
              }
            }
          } else {
            objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
          }
        }

        // 最小购买金额限定
        if (rule.getMinUseOrderAmount() != null && BigDecimalUtil.isBelow(objectTotalPriceTemp, rule.getMinUseOrderAmount())) {
          successFlg = false;
        }

      }
      if (successFlg) {
        freeFlg = true;
        break;
      }
    }
    return freeFlg;
  }

  /**
   * 免邮促销活动得到满足条件的商品
   */
  private List<String> getFreeObjectCommodity(ShippingBean bean, String[] commoditys, String[] brands, String[] categorys) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();

    for (ShippingHeaderBean shipping : bean.getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          commodityListOfCart.add(cs.getCommodityHeader(getConfig().getSiteShopCode(), commodity.getCommodityCode()));
        }
      }
    }
    List<String> commodityCodeList = new ArrayList<String>();
    for (String commodityCode : commoditys) {
      commodityCodeList.add(commodityCode);
    }

    List<String> brandCodeList = new ArrayList<String>();
    for (String brandCode : brands) {
      brandCodeList.add(brandCode);
    }

    List<String> caregoryCodeList = new ArrayList<String>();
    for (String categoryCode : categorys) {
      caregoryCodeList.add(categoryCode);
    }

    for (CommodityHeader ch : commodityListOfCart) {

      // 使用关联信息(商品编号)
      if (commodityCodeList.size() > 0 && commodityCodeList.contains(ch.getCommodityCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(品牌编号)
      if (brandCodeList.size() > 0 && brandCodeList.contains(ch.getBrandCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(分类编号)
      if (caregoryCodeList != null && caregoryCodeList.size() > 0) {
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categoryCodes = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          existCategory = false;
        } else {
          existCategory = true;
        }
        for (String categoryCode : categoryCodes) {
          if (caregoryCodeList.contains(categoryCode)) {
            existCategory = true;
            break;
          }
        }
        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
    }
    return objectCommodity;
  }

  
  
  /** 赠品重新计算 **/
  public void giftReset(ShippingDetailBean shopBean) {
    
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    
    List<ShippingCommodityBean> items = shopBean.getCommodityList();
    
    // 购物车中商品符合赠品促销的活动编号
    List<String> compaignCodes = new ArrayList<String>();
    for (ShippingCommodityBean item : items) {
      if (item.getCampaignGiftList() != null && item.getCampaignGiftList().size() > 0) {
        String compaignCode = item.getCampaignGiftList().get(0).getCampaignCode();
        if (StringUtil.hasValue(compaignCode) && !compaignCodes.contains(compaignCode)) {
          compaignCodes.add(compaignCode);
        }
      }
    }
    
    for (String code : compaignCodes) {
      // 赠品活动中的关联商品
      CampaignLine campaignLine = communicationService.loadCampaignLine(code);
      if (campaignLine == null) {
        continue;
      }
      List<CampaignCondition> cList = campaignLine.getConditionList();
      String commodityStr = ""; // 关联商品编号接连串
      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          CampaignCondition condition = cList.get(i);
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
          if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
        }
      }
      if (StringUtil.isNullOrEmpty(commodityStr)) {
        continue;
      }
      
      String firstCommdityCode = null; //取符合活动的第一个商品编号
      int totalCommodityNum = 0;   //符合当前活动的商品总数量
      for (ShippingCommodityBean item : items) {
        if (item.getCampaignGiftList() == null || item.getCampaignGiftList().size() == 0
            || !code.equals(item.getCampaignGiftList().get(0).getCampaignCode())) {
          continue;
        }
        // 只取第一个商品

        String commodiyCode = item.getCommodityCode();
        String[] commodityCodes = commodityStr.split(",");
        for (String curCode : commodityCodes) {
          if (StringUtil.isNullOrEmpty(curCode)) {
            continue;
          }
          if (curCode.equals(commodiyCode)) {
            totalCommodityNum += NumUtil.toLong(item.getCommodityAmount());

            if (firstCommdityCode == null) {
              firstCommdityCode = item.getCommodityCode();
            } else {
              // 清空后面的商品赠品信息
              item.setGiftList(null);
              item.setCampaignGiftList(null);
              item.setCampaignName(null);
              item.setCampaignCode(null);
              // item.setGiftName(null);
              // item.setGiftOptionCode("");
            }
          }
        }
      }
      Long giftNum   = 0L;
      if("0".equals(NumUtil.toString(campaignLine.getCampaignMain().getMinCommodityNum()))){
        giftNum = campaignLine.getCampaignMain().getGiftAmount();
      }else{
        giftNum = (totalCommodityNum / campaignLine.getCampaignMain().getMinCommodityNum())
        * campaignLine.getCampaignMain().getGiftAmount();
      }
      
      //重置赠品数量
      
      for(ShippingCommodityBean item : items){
        if(StringUtil.hasValue(firstCommdityCode) && firstCommdityCode.equals(item.getCommodityCode())){
          
          if(giftNum.intValue() !=0 && item.getCampaignGiftList()!=null){
            for(GiftBean gift:item.getCampaignGiftList()){
              gift.setGiftAmount(NumUtil.toString(giftNum));
            }           
          }else{ //如果赠品为0，清空第一个商品中的赠品
            item.setGiftList(null);
            item.setCampaignGiftList(null);
            item.setCampaignName(null);
            item.setCampaignCode(null);
          }

          break;
        }
 
      }
      

    }
    
  }
  
  /** 赠品重新计算 **/
  public void giftReset(Cart cart, String shopCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    List<CartItem> items = cart.get();

    // 购物车中商品符合赠品促销的活动编号
    List<String> compaignCodes = new ArrayList<String>();
    for (CartItem item : items) {
      if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
        String compaignCode = item.getCommodityInfo().getGiftList().get(0).getCampaignCode();
        if (StringUtil.hasValue(compaignCode) && !compaignCodes.contains(compaignCode)) {
          compaignCodes.add(compaignCode);
        }
      }
    }

    for (String code : compaignCodes) {
      // 赠品活动中的关联商品
      CampaignLine campaignLine = communicationService.loadCampaignLine(code);
      if (campaignLine == null) {
        continue;
      }
      List<CampaignCondition> cList = campaignLine.getConditionList();
      String commodityStr = ""; // 关联商品编号接连串
      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          CampaignCondition condition = cList.get(i);
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
          if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
        }
      }
      if (StringUtil.isNullOrEmpty(commodityStr)) {
        continue;
      }

      String firstCommdityCode = null; // 取符合活动的第一个商品编号
      int totalCommodityNum = 0; // 符合当前活动的商品总数量
      for (CartItem item : items) {

        // 只取第一个商品
        String commodiyCode = item.getCommodityCode();
        String[] commodityCodes = commodityStr.split(",");
        for (String curCode : commodityCodes) {
          if (StringUtil.isNullOrEmpty(curCode)) {
            continue;
          }
          if (curCode.equals(commodiyCode)) {
            totalCommodityNum += item.getQuantity();

            if (firstCommdityCode == null) {
              firstCommdityCode = item.getCommodityCode();
            } else {
              // 清空后面的商品赠品信息
              CartItemImpl impl = (CartItemImpl) item;
              impl.getCommodityInfo().setGiftList(null);
              // impl.getCommodityInfo().setGiftCode(null);
              // impl.setGiftCode(null);
              // impl.setGiftName(null);
            }
          }

        }
      }
      Long giftNum   = 0L;
      //如果最小购买数为0则赠品最多只送活动中的赠品数量
      if("0".equals(NumUtil.toString(campaignLine.getCampaignMain().getMinCommodityNum()))){
        giftNum = campaignLine.getCampaignMain().getGiftAmount();
      }else{
        giftNum = (totalCommodityNum / campaignLine.getCampaignMain().getMinCommodityNum())
        * campaignLine.getCampaignMain().getGiftAmount();
      }
      // 重置赠品数量
      if (giftNum.intValue() == 0) {
        cart.get(shopCode, firstCommdityCode).getCommodityInfo().setGiftList(null);
        return;
      }
      CartItem cartItem = cart.get(shopCode, firstCommdityCode);
      if (cartItem != null && cartItem.getCommodityInfo() != null && cartItem.getCommodityInfo().getGiftList() != null
          && cartItem.getCommodityInfo().getGiftList().size() > 0) {
        for (GiftItem gift : cart.get(shopCode, firstCommdityCode).getCommodityInfo().getGiftList()) {
          gift.setQuantity(giftNum.intValue());
        }
      }
    }

  }


}
