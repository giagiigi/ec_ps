package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.CustomerGroupCampaignDao;
import jp.co.sint.webshop.data.dao.NewCouponRuleDao;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierInvoice;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderServiceQuery;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderBaseBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerRegisterBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderBaseBean.InvoiceBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 新規受注の基底クラスです。
 * 
 * @param <T>
 *          UIMainBean
 * @author System Integrator Corp.
 */
public abstract class NeworderBaseAction<T extends UIMainBean> extends WebBackAction<T> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * 新規顧客登録共通バリデーション
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean customerValidate() {

    NeworderCustomerRegisterBean bean = (NeworderCustomerRegisterBean) getBean();
    boolean result = validateBean(getBean());
    // soukai delete 2012/1/2 ob start
    // Add by V10-CH start
    /*
     * if (StringUtil.isNullOrEmpty(bean.getPhoneNumber1()) &&
     * StringUtil.isNullOrEmpty(bean.getPhoneNumber2()) &&
     * StringUtil.isNullOrEmpty(bean.getPhoneNumber3()) &&
     * StringUtil.isNullOrEmpty(bean.getMobileNumber())) {
     * addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER)); result =
     * false; }else{ PhoneValidator phoneValidator = new PhoneValidator();
     * boolean phoneResult = false; phoneResult =
     * phoneValidator.isValid(StringUtil.joint('-', bean.getPhoneNumber1(),
     * bean.getPhoneNumber2(), bean .getPhoneNumber3())); if (!phoneResult) {
     * addErrorMessage(phoneValidator.getMessage()); } result &= phoneResult; }
     */
    // soukai delete 2012/1/2 ob end
    // else{
    // if(StringUtil.hasValue(bean.getPhoneNumber1()) ||
    // StringUtil.hasValue(bean.getPhoneNumber2()) ||
    // StringUtil.hasValue(bean.getPhoneNumber3())){
    // if(!(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2())
    // && bean.getPhoneNumber1().length()>1 &&
    // bean.getPhoneNumber2().length()>5)){
    // addErrorMessage(WebMessage.get(ValidationMessage.TRUE_NUMBER));
    // result = false;
    // }
    // }
    // }
    // Add by V10-CH end
    // 必須入力エラー
    // soukai add 2012/1/2 ob start
    if (StringUtil.hasValueAllOf(bean.getEmail(), bean.getEmailConfirm())) {
      if (!bean.getEmail().equals(bean.getEmailConfirm())) {
        addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_EMAIL));
      }
    }
    // soukai add 2012/1/2 ob end
    if (StringUtil.isNullOrEmpty(bean.getPassword())) {
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
          .getString("web.action.back.order.NeworderBaseAction.0")));
      result = false;
    }
    if (StringUtil.isNullOrEmpty(bean.getPasswordCon())) {
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
          .getString("web.action.back.order.NeworderBaseAction.1")));
      result = false;
    }
    if (StringUtil.hasValue(bean.getPassword())) {
      // パスワードチェックエラー
      PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
      if (!policy.isValidPassword(bean.getPassword())) {
        addErrorMessage(WebMessage.get(CustomerErrorMessage.PASSWORD_POLICY_ERROR));
        result = false;
      }

      // 不一致エラー
      if (StringUtil.hasValue(bean.getPasswordCon()) && !bean.getPassword().equals(bean.getPasswordCon())) {
        addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_PASSWORD));
        result = false;
      }
    }

    // add by lc 2012-03-08 start
    if (StringUtil.getLength(bean.getLastName()) > 28) {
      addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
          .getString("web.action.back.order.OrderDetailUpdateAction.customerName")));
      result = false;
    }
    // add by lc 2012-03-08 end
    return result;

  }

  public boolean numberLimitValidation(NeworderBaseBean bean) {
    OrderContainer order = createOrderContainerFromCashier(bean);
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

  public OrderContainer createOrderContainerFromCashier(NeworderBaseBean bean) {
    Cashier cashier = bean.getCashier();
    OrderContainer order = new OrderContainer();
    OrderHeader orderHeader = new OrderHeader();
    if (StringUtil.hasValue(cashier.getPayment().getPaymentMethodCode())) {
      orderHeader.setPaymentCommission(cashier.getPayment().getSelectPayment().getPaymentCommission());
    }

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
        // Long discountAmount = commodity.getUnitPrice() -
        // commodity.getRetailPrice();
        BigDecimal discountAmount = commodity.getUnitPrice().subtract(commodity.getRetailPrice());
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
        // 2012-11-27 促销对应 ob add start
        if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
          for (GiftItem gift : commodity.getGiftList()) {
            ShippingDetail giftDetail = new ShippingDetail();
            giftDetail.setUnitPrice(gift.getUnitPrice());
            if (gift.isDiscount()) {
              giftDetail.setDiscountPrice(gift.getRetailPrice());
            }
            BigDecimal giftDiscountAmount = gift.getUnitPrice().subtract(gift.getRetailPrice());
            giftDetail.setDiscountAmount(giftDiscountAmount);
            giftDetail.setRetailPrice(gift.getRetailPrice());

            giftDetail.setGiftPrice(BigDecimal.ZERO);

            giftDetail.setPurchasingAmount(Integer.valueOf(gift.getQuantity()).longValue());
            giftDetail.setRetailTax(gift.getUnitTaxCharge());
            shippingDetails.add(giftDetail);
          }
        }
        // 2012-11-27 促销对应 ob add end

        shippingDetails.add(shippingDetail);
      }
      shippingContainer.setShippingDetails(shippingDetails);
      shippings.add(shippingContainer);
    }

    order.setShippings(shippings);
    return order;
  }

  // soukai add 2012/01/07 ob start
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
    if (StringUtil.hasValue(invoiceBean.getInvoiceType())) {
      invoiceBean.setInvoiceTypeName(InvoiceType.fromValue(invoiceBean.getInvoiceType()).getName());
    }
    return invoiceBean;
  }

  // soukai add 2012/01/07 ob end

  // 2013/04/17 优惠券对应 ob add start
  public boolean checkDiscountInfo(Cashier cashier) {
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());

    if (CouponType.CUSTOMER_GROUP.getValue().equals(cashier.getDiscount().getCouponType())) {
      // 顾客组别优惠时，顾客组别的存在check
      CustomerGroupCampaignDao dao = DIContainer.getDao(CustomerGroupCampaignDao.class);
      List<CustomerGroupCampaign> campaign = dao.findByQuery(OrderServiceQuery.GET_CUSTOMER_GOURP_CAMPAIGN, cashier.getDiscount()
          .getDiscountCode());
      if (campaign == null || campaign.size() == 0) {
        addErrorMessage("选择的优惠已被删除或者利用期间外。");
        return false;
      }
      if (DateUtil.getSysdate().after(campaign.get(0).getCampaignEndDatetime())
          || DateUtil.getSysdate().before(campaign.get(0).getCampaignStartDatetime())) {
        addErrorMessage("选择的优惠已被删除或者利用期间外。");
        return false;
      }
      // 顾客组别优惠的最小购买金额check
      if (cashier.getTotalCommodityPrice().compareTo(campaign.get(0).getMinOrderAmount()) < 0) {
        addErrorMessage("商品合计金额小于使用优惠的最小购买金额。");
        return false;
      }

    } else if (CouponType.COMMON_DISTRIBUTION.getValue().equals(cashier.getDiscount().getCouponType())) {
      // 公共优惠券时，优惠券的存在check
      NewCouponRuleDao couponDao = DIContainer.getDao(NewCouponRuleDao.class);
      NewCouponRule coupon = couponDao.load(cashier.getDiscount().getDiscountCode());
      if (coupon == null) {
        addErrorMessage("选择的优惠已被删除或者利用期间外。");
        return false;
      }
      // 公共优惠券的使用回数check
      if (DateUtil.getSysdate().after(coupon.getMinUseEndDatetime())
          || DateUtil.getSysdate().before(coupon.getMinUseStartDatetime())) {
        addErrorMessage("选择的优惠已被删除或者利用期间外。");
        return false;
      }

      if (NumUtil.toLong(coupon.getSiteUseLimit().toString()) > 0L) {
        Long siteUseCount = orderService.countUsedCouponOrder(coupon.getCouponCode(), null);
        if (NumUtil.toLong(coupon.getSiteUseLimit().toString()) <= siteUseCount) {
          addErrorMessage("选择的优惠已被删除或者利用期间外。");
          return false;
        }
      }
      if (NumUtil.toLong(coupon.getPersonalUseLimit().toString()) > 0L) {
        Long customerUseCount = orderService.countUsedCouponOrder(coupon.getCouponCode(), cashier.getCustomer().getCustomerCode());
        if (NumUtil.toLong(coupon.getPersonalUseLimit().toString()) <= customerUseCount) {
          addErrorMessage("选择的优惠已超过最大利用可能回数。");
          return false;
        }
      }

      // 公共优惠券时，最小购买金额check
      if (cashier.getTotalCommodityPrice().compareTo(coupon.getMinUseOrderAmount()) < 0) {
        addErrorMessage("商品合计金额小于使用优惠的最小购买金额。");
        return false;
      }

      // 自己发行的Public优惠券不可使用
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      if (communicationService.issuedBySelf(cashier.getDiscount().getDiscountCode(), cashier.getCustomer().getCustomerCode())) {
        addErrorMessage("不能使用顾客本人发行的优惠券。");
        return false;
      }
    } else if (CouponType.PURCHASE_DISTRIBUTION.getValue().equals(cashier.getDiscount().getCouponType())
        || CouponType.SPECIAL_MEMBER_DISTRIBUTION.getValue().equals(cashier.getDiscount().getCouponType())) {
      // 使用个人优惠券验证

      // 已选择个人优惠券信息取得
      MyCouponInfo aviableCouponSelected = getAviableCouponInfo(cashier);

      if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
        addErrorMessage("选择的优惠已被删除或者利用期间外。");
        return false;
      }
    }

    return true;
  }

  private MyCouponInfo getAviableCouponInfo(Cashier cashier) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    MyCouponInfo newCouponHistory = service.getMyCoupon(cashier.getCustomer().getCustomerCode(), cashier.getDiscount()
        .getDiscountDetailCode());

    MyCouponInfo result = new MyCouponInfo();

    List<String> objectCommodity = new ArrayList<String>();

    BigDecimal objectTotalPrice = BigDecimal.ZERO;
    BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;

    List<NewCouponHistoryUseInfo> useCommodityList = service.getUseCommodityList(newCouponHistory.getCouponIssueNo());
    List<NewCouponHistoryUseInfo> brandList = service.getBrandCodeList(newCouponHistory.getCouponIssueNo());
    List<NewCouponHistoryUseInfo> importCommodityTypeList = service.getImportCommodityTypeList(newCouponHistory.getCouponIssueNo());
    // 20131010 txw add start
    List<NewCouponHistoryUseInfo> categoryList = service.getCategoryCodeList(newCouponHistory.getCouponIssueNo());
    // 20131010 txw add end
    boolean objectFlg = false;
    if (useCommodityList != null && useCommodityList.size() > 0) {
      objectFlg = true;
    }
    if (brandList != null && brandList.size() > 0) {
      objectFlg = true;
    }
    // 20131010 txw add start
    if (categoryList != null && categoryList.size() > 0) {
      objectFlg = true;
    }
    // 20131010 txw add end
    if (importCommodityTypeList != null && importCommodityTypeList.size() > 0) {
      objectFlg = true;
    }
    if (objectFlg) {
      objectCommodity = getObjectCommodity("00000000", useCommodityList, brandList, importCommodityTypeList, cashier, categoryList);
    }
    // 指定商品的购买金额取得
    if (objectCommodity.size() > 0) {
      for (String commodityCode : objectCommodity) {
        for (CashierShipping shipping : cashier.getShippingList()) {
          for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
            if (commodityCode.equals(commodity.getCommodityCode())) {
              objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(), commodity
                  .getQuantity()));
            }
          }
        }
      }
    }

    if (newCouponHistory.getUseType().equals(1L)) {
      if (objectFlg) {
        objectTotalPriceTemp = cashier.getTotalCommodityPrice().subtract(objectTotalPrice);
      } else {
        objectTotalPriceTemp = cashier.getTotalCommodityPrice();
      }
    } else {
      if (objectFlg) {
        objectTotalPriceTemp = objectTotalPrice;
      } else {
        objectTotalPriceTemp = cashier.getTotalCommodityPrice();
      }
    }

    // 优惠前后标志
    // if
    // (BeforeAfterDiscountType.AFTERDISCOUNT.longValue().equals(newCouponHistory.getBeforeAfterDiscountType()))
    // {
    // BigDecimal couponAmount = BigDecimal.ZERO;
    // if
    // (CouponIssueType.FIXED.longValue().equals(newCouponHistory.getCouponIssueType()))
    // {
    // if (newCouponHistory.getCouponAmount() != null) {
    // couponAmount = newCouponHistory.getCouponAmount();
    // }
    // } else {
    // couponAmount =
    // BigDecimalUtil.divide(BigDecimalUtil.multiply(objectTotalPrice,
    // BigDecimalUtil.tempFormatLong(
    // newCouponHistory.getCouponProportion(), BigDecimal.ZERO)), 100, 2,
    // RoundingMode.HALF_UP);
    // }
    // objectTotalPrice = BigDecimalUtil.subtract(objectTotalPrice,
    // couponAmount);
    // }

    // 优惠券利用最小购买金额
    if (BigDecimalUtil.isAboveOrEquals(objectTotalPriceTemp, newCouponHistory.getMinUseOrderAmount())) {
      newCouponHistory.setObjectTotalPrice(objectTotalPriceTemp);
      result = newCouponHistory;
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
  private List<String> getObjectCommodity(String shopCode, List<NewCouponHistoryUseInfo> useCommodityList,
      List<NewCouponHistoryUseInfo> brandList, List<NewCouponHistoryUseInfo> importCommodityTypeList, Cashier cashier,
      List<NewCouponHistoryUseInfo> categoryList) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();

    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        commodityListOfCart.add(cs.getCommodityHeader(cashier.getPaymentShopCode(), commodity.getCommodityCode()));
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

    // 20131010 txw add start
    List<String> categoryCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo category : categoryList) {
      categoryCodeList.add(category.getCategoryCode());
    }
    // 20131010 txw add end

    List<Long> importCommodityTypeCodeList = new ArrayList<Long>();
    for (NewCouponHistoryUseInfo importCommodityType : importCommodityTypeList) {
      importCommodityTypeCodeList.add(importCommodityType.getImportCommodityType());
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

      // 20131010 txw add start
      // 使用关联信息(分类编号)
      if (categoryCodeList != null && categoryCodeList.size() > 0) {
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
          if (categoryCodeList.contains(categoryCode)) {
            existCategory = true;
            break;
          }
        }
        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
      // 20131010 txw add end

    }

    return objectCommodity;
  }
  // 2013/04/17 优惠券对应 ob add end
}
