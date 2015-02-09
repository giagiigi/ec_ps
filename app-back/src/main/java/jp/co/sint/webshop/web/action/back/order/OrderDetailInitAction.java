package jp.co.sint.webshop.web.action.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingDetailComposition;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeCvs;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.PaymentProviderManager;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.OrderHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.PaymentBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1020220:受注管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailInitAction extends OrderDetailBaseAction {

  private Map<String, String> shopNameMap = new HashMap<String, String>();

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
      authorization = Permission.ORDER_READ_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      authorization = Permission.ORDER_READ_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_READ_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.ORDER_READ_SITE.isGranted(getLoginInfo());
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] pathArgs = getRequestParameter().getPathArgs();
    if (pathArgs.length > 0) {
      return true;
    }
    setNextUrl("/app/order/order_list");
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String orderNo = getRequestParameter().getPathArgs()[0];

    // 受注タイプを取得する
    String orderType = orderNo.substring(0, 1);

    OrderDetailBean bean = new OrderDetailBean();

    // ECの場合
    if ((!"T".equals(orderType)) && (!"D".equals(orderType))) {
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      OrderContainer orderContainer = orderService.getOrder(orderNo);
      OrderHeader orderHeader = orderContainer.getOrderHeader();
      // 存在チェック
      if (orderContainer.getOrderHeader() == null) {
        setNextUrl("/app/order/order_list");
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      // ショップ個別決済時、他ショップ情報の場合はログイン画面へ
      if (getConfig().getOperatingMode() == OperatingMode.SHOP && getLoginInfo().isShop()) {
        if (!getLoginInfo().getShopCode().equals(orderHeader.getShopCode())) {
          setNextUrl("/app/common/login");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      bean.setOrderNo(orderNo);
      bean.setOrderType(OrderType.EC.getValue());
      bean.setOrderFlg(orderHeader.getOrderFlg().toString());
      bean.setOrderEditMessage(null);
      bean.setOrderStatus(NumUtil.toString(orderHeader.getOrderStatus()));
      bean.setOrderDataTransportFlg(NumUtil.toString(orderHeader.getDataTransportStatus()));
      bean.setOrderDatetime(DateUtil.toDateTimeString(orderHeader.getOrderDatetime()));

      // 注文金額部の値を設定
      OrderSummary orderSummary = orderService.getOrderSummary(orderNo, OrderType.EC.getValue());
      bean.setTotalCommodityPrice(StringUtil.coalesce(String.valueOf(orderSummary.getRetailPrice()), ""));
      bean.setTotalGiftPrice(StringUtil.coalesce(orderSummary.getGiftPrice().toString()));
      bean.setTotalShippingCharge(StringUtil.coalesce(orderSummary.getShippingCharge().toString(), ""));

      BigDecimal totalOrderPrice = orderSummary.getTotalAmount();
      totalOrderPrice = totalOrderPrice.add(orderSummary.getPaymentCommission());
      totalOrderPrice = totalOrderPrice.add( orderHeader.getOuterCardPrice()!= null ? orderHeader.getOuterCardPrice() : BigDecimal.ZERO);
      bean.setTotalOrderPrice(StringUtil.coalesce(NumUtil.toString(totalOrderPrice), ""));
      bean.setPaymentCommission(NumUtil.toString(orderHeader.getPaymentCommission()));
      bean.setUsedPoint(StringUtil.coalesce(orderSummary.getUsedPoint().toString()));
      bean.setUsedCouponPrice(NumUtil.toString(orderHeader.getDiscountPrice()));
      if (orderHeader.getDiscountPrice() == null) {
        orderHeader.setDiscountPrice(BigDecimal.ZERO);
      }
      BigDecimal paymentPrice = PointUtil.getTotalPyamentPrice(BigDecimalUtil.subtract(totalOrderPrice, orderHeader
          .getDiscountPrice()), orderSummary.getUsedPoint());
      String giftCardUsePrice = NumUtil.toString(BigDecimal.ZERO);
      String outerCardUsePrice = NumUtil.toString(BigDecimal.ZERO);
      if (orderHeader.getOuterCardPrice() != null && BigDecimalUtil.isAbove(orderHeader.getOuterCardPrice(), BigDecimal.ZERO)) {
        outerCardUsePrice = orderHeader.getOuterCardPrice().toString();
      }
      bean.setOuterCardUsePrice(outerCardUsePrice);
      if (BigDecimalUtil.isAbove(BigDecimal.ZERO, paymentPrice)) {
        bean.setAllPaymentPrice(NumUtil.toString(BigDecimal.ZERO));
      } else {
        if (orderHeader.getGiftCardUsePrice() != null && BigDecimalUtil.isAbove(orderHeader.getGiftCardUsePrice(), BigDecimal.ZERO)) {
          giftCardUsePrice = NumUtil.toString(orderHeader.getGiftCardUsePrice());
          bean.setOrderEditButtonFlg(false);
          bean.setAllPaymentPrice(NumUtil.toString(paymentPrice.subtract(orderHeader.getGiftCardUsePrice())));
        } else {
          bean.setAllPaymentPrice(NumUtil.toString(paymentPrice));
        }
      }
      bean.setGiftCardUsePrice(giftCardUsePrice);
      if (orderContainer.getOrderHeader().getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        bean.setNotPointInFull(false);
      } else {
        bean.setNotPointInFull(true);
      }
      // 支払情報部の値を設定
      PaymentBean paymentEdit = new PaymentBean();
      paymentEdit.setPaymentMethodCode(NumUtil.toString(orderHeader.getPaymentMethodNo()));
      paymentEdit.setPaymentMethodName(orderHeader.getPaymentMethodName());
      paymentEdit.setAdvanceLaterType(NumUtil.toString(orderHeader.getAdvanceLaterFlg()));
      paymentEdit.setPaymentCommission(NumUtil.toString(orderHeader.getPaymentCommission()));
      if (orderHeader.getPaymentLimitDate() != null) {
        paymentEdit.setPaymentLimitDate(DateUtil.toDateString(orderHeader.getPaymentLimitDate()));
      }
      paymentEdit.setPaymentDate(DateUtil.toDateString(orderHeader.getPaymentDate()));
      paymentEdit.setCvsReceiptNo(orderHeader.getPaymentReceiptNo());
      paymentEdit.setCvsReceiptUrl(orderHeader.getPaymentReceiptUrl());
      if (StringUtil.hasValue(orderHeader.getCvsCode())) {
        CashierPaymentTypeBase cashier = new CashierPaymentTypeCvs();
        List<CodeAttribute> cvsList = PaymentProviderManager.getCodeList(cashier);
        String cvsName = "";
        for (CodeAttribute cvs : cvsList) {
          if (cvs.getValue().equals(orderHeader.getCvsCode())) {
            cvsName = cvs.getName();
          }
        }
        paymentEdit.setCvsName(cvsName);
      }
      if (orderHeader.getPaymentOrderId() != null && orderHeader.getPaymentOrderId() > 0) {
        paymentEdit.setTradeID(NumUtil.toString(orderHeader.getPaymentOrderId()));
      }
      paymentEdit.setUpdateDatetime(orderHeader.getUpdatedDatetime());

      bean.setPaymentEdit(paymentEdit);

      // 受注ヘッダ部の値を設定
      OrderHeaderBean orderHeaderEdit = new OrderHeaderBean();
      orderHeaderEdit.setCustomerCode(orderContainer.getOrderHeader().getCustomerCode());
      orderHeaderEdit.setCustomerFirstName(orderContainer.getOrderHeader().getFirstName());
      orderHeaderEdit.setCustomerLastName(orderContainer.getOrderHeader().getLastName());
      orderHeaderEdit.setCustomerFirstNameKana(orderContainer.getOrderHeader().getFirstNameKana());
      orderHeaderEdit.setCustomerLastNameKana(orderContainer.getOrderHeader().getLastNameKana());
      orderHeaderEdit.setGuestFlg(NumUtil.toString(orderContainer.getOrderHeader().getGuestFlg()));
      if (StringUtil.hasValue(orderContainer.getOrderHeader().getPhoneNumber())) {
        String[] phoneNumber = orderContainer.getOrderHeader().getPhoneNumber().split("-");
        if (phoneNumber.length == 2) {
          orderHeaderEdit.setCustomerTel1(phoneNumber[0]);
          orderHeaderEdit.setCustomerTel2(phoneNumber[1]);
        } else if (phoneNumber.length == 3) {
          orderHeaderEdit.setCustomerTel1(phoneNumber[0]);
          orderHeaderEdit.setCustomerTel2(phoneNumber[1]);
          orderHeaderEdit.setCustomerTel3(phoneNumber[2]);
        }
      } else {
        orderContainer.getOrderHeader().setPhoneNumber("");
        orderHeaderEdit.setCustomerTel1("");
        orderHeaderEdit.setCustomerTel2("");
        orderHeaderEdit.setCustomerTel3("");
      }
      orderHeaderEdit.setCustomerMobile(orderContainer.getOrderHeader().getMobileNumber());
      UtilService s = ServiceLocator.getUtilService(getLoginInfo());
      orderHeaderEdit.setPrefectureCode(orderContainer.getOrderHeader().getPrefectureCode());
      orderHeaderEdit.setCityList(s.getCityNames(orderHeaderEdit.getPrefectureCode()));
      String recentEmail = "";
      String recentPhoneNumber = "";
      String recentMobileNumber = "";
      String customerCode = orderContainer.getOrderHeader().getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        CustomerInfo orderCustomer = customerService.getCustomer(customerCode);
        if (orderCustomer != null) {
          if (!orderCustomer.getCustomer().getEmail().equals(orderContainer.getOrderHeader().getEmail())) {
            recentEmail = orderCustomer.getCustomer().getEmail();
          }
          if (StringUtil.isNullOrEmpty(orderCustomer.getAddress().getPhoneNumber())) {
            orderCustomer.getAddress().setPhoneNumber("");
          }
          if (!orderCustomer.getAddress().getPhoneNumber().equals(orderContainer.getOrderHeader().getPhoneNumber())) {
            recentPhoneNumber = orderCustomer.getAddress().getPhoneNumber();
          }
          if (StringUtil.isNullOrEmpty(orderCustomer.getAddress().getMobileNumber())) {
            orderCustomer.getAddress().setMobileNumber("");
          }
          if (!orderCustomer.getAddress().getMobileNumber().equals(orderContainer.getOrderHeader().getMobileNumber())) {
            recentMobileNumber = orderCustomer.getAddress().getMobileNumber();
          }
        }
      }
      orderHeaderEdit.setRecentPhoneNumber(recentPhoneNumber);
      orderHeaderEdit.setRecentMobileNumber(recentMobileNumber);
      orderHeaderEdit.setRecentEmail(recentEmail);
      orderHeaderEdit.setCustomerEmail(orderContainer.getOrderHeader().getEmail());
      orderHeaderEdit.setPostalCode(orderContainer.getOrderHeader().getPostalCode());
      orderHeaderEdit.setPrefectureCode(orderContainer.getOrderHeader().getPrefectureCode());
      orderHeaderEdit.setAddress1(orderContainer.getOrderHeader().getAddress1());
      orderHeaderEdit.setAddress2(orderContainer.getOrderHeader().getAddress2());
      orderHeaderEdit.setAddress3(orderContainer.getOrderHeader().getAddress3());
      orderHeaderEdit.setAddress4(orderContainer.getOrderHeader().getAddress4());
      orderHeaderEdit.setAreaCode(orderContainer.getOrderHeader().getAreaCode());
      orderHeaderEdit.setMessage(orderContainer.getOrderHeader().getMessage());
      orderHeaderEdit.setCaution(orderContainer.getOrderHeader().getCaution());
      orderHeaderEdit.setUpdateDatetime(orderContainer.getOrderHeader().getUpdatedDatetime());
      orderHeaderEdit.setCityCode(orderContainer.getOrderHeader().getCityCode());
      bean.setOrderHeaderEdit(orderHeaderEdit);
      // 出荷情報部の値を設定
      List<ShippingHeaderBean> shippingList = new ArrayList<ShippingHeaderBean>();
      List<ShippingHeaderBean> returnShippingList = new ArrayList<ShippingHeaderBean>();
      setShippingData(shippingList, returnShippingList, orderContainer);
      bean.setShippingList(shippingList);
      // 出荷返品情報部の値を設定
      bean.setReturnShippingList(returnShippingList);

      // 订单发票情报设定
      bean.setOrderInvoice(getOrderInvoice(orderNo, bean.getOrderHeaderEdit().getCustomerCode(), orderService));
      // 商品规格List查询
      List<CodeAttribute> commodityNameList = new ArrayList<CodeAttribute>();
      commodityNameList.add(new NameValue("请选择", ""));
      if (StringUtil.isNullOrEmpty(bean.getOrderInvoice().getInvoiceCommodityName())) {
        for (NameValue invoiceName : DIContainer.getInvoiceValue().getInvoiceCommodityNameList()) {
          commodityNameList.add(new NameValue(invoiceName.getName(), invoiceName.getValue()));
        }
      } else {
        boolean bl = false;
        for (List<NameValue> nameValueList : DIContainer.getInvoiceValue().getInvoiceCommodityNameByLanguageList()) {
          if (bl) {
            break;
          }
          for (NameValue invoiceCommodityName : nameValueList) {
            if (invoiceCommodityName.getValue().equals(bean.getOrderInvoice().getInvoiceCommodityName().replace("-电器", ""))) {
              commodityNameList.addAll(nameValueList);
              bl = true;
              break;
            }

          }
        }
      }
      bean.setInvoiceCommodityNameList(commodityNameList);
      // 实收金额
      bean.setPaidPrice(orderContainer.getOrderHeader().getPaidPrice());
      UtilService serv = ServiceLocator.getUtilService(getLoginInfo());
      bean.setAddressScript(serv.createAddressScript());
      bean.setAddressPrefectureList(serv.createPrefectureList());
      bean.setAddressCityList(serv.createCityList(bean.getOrderHeaderEdit().getPrefectureCode()));
      bean.setAddressAreaList(serv.createAreaList(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit().getCityCode()));

      // 计算订单包含商品总重量
      BigDecimal totalWeight = new BigDecimal(0);
      List<OrderDetail> commodityWeightList = orderService.getOrderDetailCommodityListWeight(orderHeader.getOrderNo(), DIContainer.getWebshopConfig().getSiteShopCode());
      for(OrderDetail orderDetail : commodityWeightList) {
        if(orderDetail.getCommodityWeight() != null) {
          totalWeight = totalWeight.add(orderDetail.getCommodityWeight());
        }
      }
      bean.setTotalWeight(totalWeight.toString());
      
      
      // TMALL受注の場合
    } else if("T".equals(orderType)){
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      TmallService tmallService = ServiceLocator.getTmallService(getLoginInfo());
      OrderContainer orderContainer = orderService.getTmallOrder(orderNo);
      TmallOrderHeader tmallOrderHeader = orderContainer.getTmallOrderHeader();
      // 存在チェック
      if (tmallOrderHeader == null) {
        setNextUrl("/app/order/order_list");
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      // ショップ個別決済時、他ショップ情報の場合はログイン画面へ
      if (getConfig().getOperatingMode() == OperatingMode.SHOP && getLoginInfo().isShop()) {
        if (!getLoginInfo().getShopCode().equals(tmallOrderHeader.getShopCode())) {
          setNextUrl("/app/common/login");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      bean.setOrderNo(orderNo);
      // 淘宝订单交易号
      bean.setTmallTid(tmallOrderHeader.getTmallTid());
      bean.setOrderType(OrderType.TMALL.getValue());
      bean.setOrderFlg(tmallOrderHeader.getOrderFlg().toString());
      bean.setOrderEditMessage(null);
      bean.setOrderStatus(NumUtil.toString(tmallOrderHeader.getOrderStatus()));
      bean.setOrderDataTransportFlg(NumUtil.toString(tmallOrderHeader.getDataTransportStatus()));
      bean.setOrderDatetime(DateUtil.toDateTimeString(tmallOrderHeader.getOrderDatetime()));

      // 注文金額部の値を設定
      OrderSummary orderSummary = orderService.getOrderSummary(orderNo, OrderType.TMALL.getValue());
      bean.setTotalCommodityPrice(StringUtil.coalesce(String.valueOf(orderSummary.getRetailPrice()), ""));
      bean.setTotalGiftPrice(StringUtil.coalesce(orderSummary.getGiftPrice().toString()));
      bean.setTotalShippingCharge(StringUtil.coalesce(orderSummary.getShippingCharge().toString(), ""));

      BigDecimal totalOrderPrice = orderSummary.getTotalAmount();
      totalOrderPrice = totalOrderPrice.add(orderSummary.getPaymentCommission());

      bean.setTotalOrderPrice(StringUtil.coalesce(NumUtil.toString(totalOrderPrice), ""));
      bean.setPaymentCommission(NumUtil.toString(tmallOrderHeader.getPaymentCommission()));
      bean.setUsedPoint(StringUtil.coalesce(orderSummary.getUsedPoint().toString()));
      if (tmallOrderHeader.getDiscountPrice() == null) {
        tmallOrderHeader.setDiscountPrice(BigDecimal.ZERO);
      }

      if (tmallOrderHeader.getAdjustFee() == null) {
        tmallOrderHeader.setAdjustFee(BigDecimal.ZERO);
      }

      if (tmallOrderHeader.getTmallDiscountPrice() == null) {
        tmallOrderHeader.setTmallDiscountPrice(BigDecimal.ZERO);
      }

      if (tmallOrderHeader.getMjsDiscount() == null) {
        tmallOrderHeader.setMjsDiscount(BigDecimal.ZERO);
      }

      bean.setUsedCouponPrice(NumUtil.toString(tmallOrderHeader.getDiscountPrice().add(tmallOrderHeader.getAdjustFee())));
      bean.setTmallDiscountPrice(NumUtil.toString(tmallOrderHeader.getTmallDiscountPrice()));
      bean.setPointConvertPrice(NumUtil.toString(tmallOrderHeader.getPointConvertPrice()));
      bean.setMjsDiscount(NumUtil.toString(tmallOrderHeader.getMjsDiscount()));
      BigDecimal paymentPrice = PointUtil.getTmallTotalPyamentPrice(BigDecimalUtil.subtract(BigDecimalUtil.subtract(
          totalOrderPrice, BigDecimalUtil.add(tmallOrderHeader.getDiscountPrice(), tmallOrderHeader.getAdjustFee())),
          BigDecimalUtil.add(tmallOrderHeader.getTmallDiscountPrice(), tmallOrderHeader.getMjsDiscount())), orderSummary
          .getUsedPoint());
      if (BigDecimalUtil.isAbove(BigDecimal.ZERO, paymentPrice)) {
        bean.setAllPaymentPrice(NumUtil.toString(BigDecimal.ZERO));
      } else {
        bean.setAllPaymentPrice(NumUtil.toString(paymentPrice));
      }
      if (tmallOrderHeader.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        bean.setNotPointInFull(false);
      } else {
        bean.setNotPointInFull(true);
      }

      // 支払情報部の値を設定
      PaymentBean paymentEdit = new PaymentBean();
      paymentEdit.setPaymentMethodCode(NumUtil.toString(tmallOrderHeader.getPaymentMethodNo()));
      paymentEdit.setPaymentMethodName(tmallOrderHeader.getPaymentMethodName());
      paymentEdit.setAdvanceLaterType(NumUtil.toString(tmallOrderHeader.getAdvanceLaterFlg()));
      paymentEdit.setPaymentCommission(NumUtil.toString(tmallOrderHeader.getPaymentCommission()));
      if (tmallOrderHeader.getPaymentLimitDate() != null) {
        paymentEdit.setPaymentLimitDate(DateUtil.toDateString(tmallOrderHeader.getPaymentLimitDate()));
      }
      paymentEdit.setPaymentDate(DateUtil.toDateString(tmallOrderHeader.getPaymentDate()));
      paymentEdit.setCvsReceiptNo(tmallOrderHeader.getPaymentReceiptNo());
      paymentEdit.setCvsReceiptUrl(tmallOrderHeader.getPaymentReceiptUrl());
      if (StringUtil.hasValue(tmallOrderHeader.getCvsCode())) {
        CashierPaymentTypeBase cashier = new CashierPaymentTypeCvs();
        List<CodeAttribute> cvsList = PaymentProviderManager.getCodeList(cashier);
        String cvsName = "";
        for (CodeAttribute cvs : cvsList) {
          if (cvs.getValue().equals(tmallOrderHeader.getCvsCode())) {
            cvsName = cvs.getName();
          }
        }
        paymentEdit.setCvsName(cvsName);
      }
      if (tmallOrderHeader.getPaymentOrderId() != null && tmallOrderHeader.getPaymentOrderId() > 0) {
        paymentEdit.setTradeID(NumUtil.toString(tmallOrderHeader.getPaymentOrderId()));
      }
      paymentEdit.setUpdateDatetime(tmallOrderHeader.getUpdatedDatetime());

      bean.setPaymentEdit(paymentEdit);

      // 受注ヘッダ部の値を設定
      OrderHeaderBean orderHeaderEdit = new OrderHeaderBean();
      orderHeaderEdit.setCustomerCode(tmallOrderHeader.getCustomerCode());
      orderHeaderEdit.setCustomerFirstName(tmallOrderHeader.getFirstName());
      orderHeaderEdit.setCustomerLastName(tmallOrderHeader.getLastName());
      orderHeaderEdit.setCustomerFirstNameKana(tmallOrderHeader.getFirstNameKana());
      orderHeaderEdit.setCustomerLastNameKana(tmallOrderHeader.getLastNameKana());
      orderHeaderEdit.setGuestFlg(NumUtil.toString(tmallOrderHeader.getGuestFlg()));
      if (StringUtil.hasValue(tmallOrderHeader.getPhoneNumber())) {
        String[] phoneNumber = tmallOrderHeader.getPhoneNumber().split("-");
        if (phoneNumber.length == 2) {
          orderHeaderEdit.setCustomerTel1(phoneNumber[0]);
          orderHeaderEdit.setCustomerTel2(phoneNumber[1]);
        } else if (phoneNumber.length == 3) {
          orderHeaderEdit.setCustomerTel1(phoneNumber[0]);
          orderHeaderEdit.setCustomerTel2(phoneNumber[1]);
          orderHeaderEdit.setCustomerTel3(phoneNumber[2]);
        }
      } else {
        tmallOrderHeader.setPhoneNumber("");
        orderHeaderEdit.setCustomerTel1("");
        orderHeaderEdit.setCustomerTel2("");
        orderHeaderEdit.setCustomerTel3("");
      }

      orderHeaderEdit.setCustomerMobile(tmallOrderHeader.getMobileNumber());

      UtilService s = ServiceLocator.getUtilService(getLoginInfo());
      orderHeaderEdit.setPrefectureCode(tmallOrderHeader.getPrefectureCode());
      orderHeaderEdit.setCityList(s.getCityNames(orderHeaderEdit.getPrefectureCode()));

      String recentEmail = "";
      String recentPhoneNumber = "";
      String recentMobileNumber = "";
      String customerCode = tmallOrderHeader.getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        CustomerInfo orderCustomer = customerService.getCustomer(customerCode);
        if (orderCustomer != null) {
          if (!orderCustomer.getCustomer().getEmail().equals(tmallOrderHeader.getEmail())) {
            recentEmail = orderCustomer.getCustomer().getEmail();
          }
          if (StringUtil.isNullOrEmpty(orderCustomer.getAddress().getPhoneNumber())) {
            orderCustomer.getAddress().setPhoneNumber("");
          }
          if (!orderCustomer.getAddress().getPhoneNumber().equals(tmallOrderHeader.getPhoneNumber())) {
            recentPhoneNumber = orderCustomer.getAddress().getPhoneNumber();
          }
          if (StringUtil.isNullOrEmpty(orderCustomer.getAddress().getMobileNumber())) {
            orderCustomer.getAddress().setMobileNumber("");
          }
          if (!orderCustomer.getAddress().getMobileNumber().equals(tmallOrderHeader.getMobileNumber())) {
            recentMobileNumber = orderCustomer.getAddress().getMobileNumber();
          }
        }
      }
      orderHeaderEdit.setRecentPhoneNumber(recentPhoneNumber);
      orderHeaderEdit.setRecentMobileNumber(recentMobileNumber);
      orderHeaderEdit.setRecentEmail(recentEmail);
      orderHeaderEdit.setCustomerEmail(tmallOrderHeader.getEmail());
      orderHeaderEdit.setPostalCode(tmallOrderHeader.getPostalCode());
      orderHeaderEdit.setPrefectureCode(tmallOrderHeader.getPrefectureCode());
      orderHeaderEdit.setAddress1(tmallOrderHeader.getAddress1());
      orderHeaderEdit.setAddress2(tmallOrderHeader.getAddress2());
      orderHeaderEdit.setAddress3(tmallOrderHeader.getAddress3());
      orderHeaderEdit.setAddress4(tmallOrderHeader.getAddress4());
      orderHeaderEdit.setAreaCode(tmallOrderHeader.getAreaCode());
      orderHeaderEdit.setMessage(tmallOrderHeader.getMessage());
      orderHeaderEdit.setCaution(tmallOrderHeader.getCaution());
      orderHeaderEdit.setUpdateDatetime(tmallOrderHeader.getUpdatedDatetime());
      orderHeaderEdit.setCityCode(tmallOrderHeader.getCityCode());
      bean.setOrderHeaderEdit(orderHeaderEdit);

      // 出荷情報部の値を設定
      List<ShippingHeaderBean> shippingList = new ArrayList<ShippingHeaderBean>();
      List<ShippingHeaderBean> returnShippingList = new ArrayList<ShippingHeaderBean>();
      setTmallShippingData(shippingList, returnShippingList, orderContainer);
      bean.setShippingList(shippingList);

      // 配送希望日时指定
      ShippingContainer shippingContainer = orderContainer.getShippings().get(0);
      boolean codFlg = false;
      if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(tmallOrderHeader.getPaymentMethodType())) {
        codFlg = true;
      }

      List<CartCommodityInfo> commodityList = new ArrayList<CartCommodityInfo>();
      for (TmallOrderDetail orderDetail : orderContainer.getTmallIOrderDetails()) {
        CartCommodityInfo cartCommodityInfo = new CartCommodityInfo();
        CommodityInfo commodityInfo = catalogService.getSkuInfo(orderDetail.getShopCode(), orderDetail.getSkuCode());
        if (commodityInfo != null && commodityInfo.getHeader() != null) {
          cartCommodityInfo.setStockManagementType(commodityInfo.getHeader().getStockManagementType().toString());
          commodityList.add(cartCommodityInfo);
        }
      }

      List<CodeAttribute> deliveryAppointedDateList = s.getTmallDeliveryDateList(shippingContainer.getTmallShippingHeader()
          .getShopCode(), shippingContainer.getTmallShippingHeader().getPrefectureCode(), codFlg, commodityList);
      bean.setDeliveryAppointedDateList(deliveryAppointedDateList);
      String deliveryDate = shippingContainer.getTmallShippingHeader().getDeliveryAppointedDate();
      boolean exitsFlg = false;
      if (StringUtil.hasValue(deliveryDate)) {
        for (CodeAttribute info : deliveryAppointedDateList) {
          if (info.getValue().equals(deliveryDate)) {
            exitsFlg = true;
          }
        }
      }
      if (!exitsFlg) {
        deliveryDate = null;
      }
      if (StringUtil.isNullOrEmpty(deliveryDate) && deliveryAppointedDateList.size() > 0) {
        deliveryDate = deliveryAppointedDateList.get(0).getValue();
      }

      List<CodeAttribute> deliveryAppointedTimeList = s.getTmallDeliveryTimeList(shippingContainer.getTmallShippingHeader()
          .getShopCode(), shippingContainer.getTmallShippingHeader().getPrefectureCode(), codFlg, deliveryDate, shippingContainer
          .getTmallShippingHeader().getAreaCode());
      bean.setDeliveryAppointedTimeList(deliveryAppointedTimeList);

      bean.setTmallOrderFlg(true);
      // 出荷返品情報部の値を設定
      bean.setReturnShippingList(returnShippingList);

      // 订单发票情报设定
      bean.setOrderInvoice(getOrderInvoice(orderNo, bean.getOrderHeaderEdit().getCustomerCode(), orderService));
      // 商品规格List查询
      List<CodeAttribute> commodityNameList = new ArrayList<CodeAttribute>();
      commodityNameList.add(new NameValue("请选择", ""));
      if (StringUtil.isNullOrEmpty(bean.getOrderInvoice().getInvoiceCommodityName())) {
        for (NameValue invoiceName : DIContainer.getInvoiceValue().getInvoiceCommodityNameList()) {
          commodityNameList.add(new NameValue(invoiceName.getName(), invoiceName.getValue()));
        }
      } else {
        boolean bl = false;
        for (List<NameValue> nameValueList : DIContainer.getInvoiceValue().getInvoiceCommodityNameByLanguageList()) {
          if (bl) {
            break;
          }
          for (NameValue invoiceCommodityName : nameValueList) {
            if (invoiceCommodityName.getValue().equals(bean.getOrderInvoice().getInvoiceCommodityName())) {
              commodityNameList.addAll(nameValueList);
              bl = true;
              break;
            }

          }
        }
      }
      bean.setInvoiceCommodityNameList(commodityNameList);
      // 实收金额
      bean.setPaidPrice(orderContainer.getTmallOrderHeader().getPaidPrice());
      UtilService serv = ServiceLocator.getUtilService(getLoginInfo());
      bean.setAddressScript(serv.createAddressScript());
      bean.setAddressPrefectureList(serv.createPrefectureList());
      bean.setAddressCityList(serv.createCityList(bean.getOrderHeaderEdit().getPrefectureCode()));
      bean.setAddressAreaList(serv.createAreaList(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit().getCityCode()));
      
      // 计算订单包含的商品的总重量
      BigDecimal totalWeight = new BigDecimal(0);
      List<TmallOrderDetail> tmallCommodityList = tmallService.getTmallOrderDetailCommodityList(tmallOrderHeader.getOrderNo(), DIContainer.getWebshopConfig().getSiteShopCode());
      for(TmallOrderDetail tmallOrderDetail : tmallCommodityList) {
        CommodityDetail commodityDetail = orderService.getCommodityDetail(tmallOrderDetail.getCommodityCode(), DIContainer.getWebshopConfig().getSiteShopCode());
        if(commodityDetail != null && commodityDetail.getWeight() != null) {
          totalWeight = totalWeight.add(commodityDetail.getWeight());        
        }
      }
      bean.setTotalWeight(totalWeight.toString());
      
    // JD受注の場合
    } else {
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      JdService jdService = ServiceLocator.getJdService(getLoginInfo());
      OrderContainer orderContainer = orderService.getJdOrder(orderNo);
      JdOrderHeader jdOrderHeader = orderContainer.getJdOrderHeader();
      // 存在チェック
      if (jdOrderHeader == null) {
        setNextUrl("/app/order/order_list");
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      // ショップ個別決済時、他ショップ情報の場合はログイン画面へ
      if (getConfig().getOperatingMode() == OperatingMode.SHOP && getLoginInfo().isShop()) {
        if (!getLoginInfo().getShopCode().equals(jdOrderHeader.getShopCode())) {
          setNextUrl("/app/common/login");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      bean.setOrderNo(orderNo);
      // 京东订单交易号
      bean.setJdOrderNo(jdOrderHeader.getJdOrderNo());
      // 京东默认不显示editButton
      bean.setOrderEditButtonFlg(false);
      bean.setOrderType(OrderType.JD.getValue());
      bean.setOrderFlg(jdOrderHeader.getOrderFlg().toString());
      bean.setOrderEditMessage(null);
      bean.setOrderStatus(NumUtil.toString(jdOrderHeader.getOrderStatus()));
      bean.setOrderDataTransportFlg(NumUtil.toString(jdOrderHeader.getDataTransportStatus()));
      bean.setOrderDatetime(DateUtil.toDateTimeString(jdOrderHeader.getOrderDatetime()));

      // 注文金額部の値を設定
      OrderSummary orderSummary = orderService.getOrderSummary2(orderNo, OrderType.JD.getValue());
      bean.setTotalCommodityPrice(StringUtil.coalesce(String.valueOf(orderSummary.getRetailPrice()), ""));
      bean.setTotalGiftPrice(StringUtil.coalesce(orderSummary.getGiftPrice().toString()));
      bean.setTotalShippingCharge(StringUtil.coalesce(orderSummary.getShippingCharge().toString(), ""));

      BigDecimal totalOrderPrice = orderSummary.getTotalAmount();
      totalOrderPrice = totalOrderPrice.add(orderSummary.getPaymentCommission());

      bean.setTotalOrderPrice(StringUtil.coalesce(NumUtil.toString(totalOrderPrice), ""));
      bean.setPaymentCommission(NumUtil.toString(jdOrderHeader.getPaymentCommission()));
      bean.setUsedPoint(StringUtil.coalesce(orderSummary.getUsedPoint().toString()));
      if (jdOrderHeader.getDiscountPrice() == null) {
        jdOrderHeader.setDiscountPrice(BigDecimal.ZERO);
      }

      //京东没adjustfee和mjsDiscount和PointConvertPrice
//      if (jdOrderHeader.getAdjustFee() == null) {
//        jdOrderHeader.setAdjustFee(BigDecimal.ZERO);
//      }

      if (jdOrderHeader.getDiscountPrice() == null) {
        jdOrderHeader.setDiscountPrice(BigDecimal.ZERO);
      }

//      if (jdOrderHeader.getMjsDiscount() == null) {
//        jdOrderHeader.setMjsDiscount(BigDecimal.ZERO);
//      }

//      bean.setUsedCouponPrice(NumUtil.toString(jdOrderHeader.getDiscountPrice().add(jdOrderHeader.getAdjustFee())));
      bean.setJdDiscountPrice(NumUtil.toString(jdOrderHeader.getDiscountPrice()));
//      bean.setPointConvertPrice(NumUtil.toString(jdOrderHeader.getPointConvertPrice()));
//      bean.setMjsDiscount(NumUtil.toString(jdOrderHeader.getMjsDiscount()));
//      BigDecimal paymentPrice = PointUtil.getTmallTotalPyamentPrice(BigDecimalUtil.subtract(BigDecimalUtil.subtract(
//          totalOrderPrice, BigDecimalUtil.add(jdOrderHeader.getDiscountPrice(), jdOrderHeader.getAdjustFee())),
//          BigDecimalUtil.add(jdOrderHeader.getTmallDiscountPrice(), jdOrderHeader.getMjsDiscount())), orderSummary
//          .getUsedPoint());
//      if (BigDecimalUtil.isAbove(BigDecimal.ZERO, paymentPrice)) {
//        bean.setAllPaymentPrice(NumUtil.toString(BigDecimal.ZERO));
//      } else {
//        bean.setAllPaymentPrice(NumUtil.toString(paymentPrice));
//      }

      bean.setUsedCouponPrice(NumUtil.toString(jdOrderHeader.getDiscountPrice()));
      bean.setJdDiscountPrice(NumUtil.toString(jdOrderHeader.getDiscountPrice()));
      BigDecimal paymentPrice = BigDecimalUtil.subtract(totalOrderPrice, jdOrderHeader.getDiscountPrice());
      if (BigDecimalUtil.isAbove(BigDecimal.ZERO, paymentPrice)) {
        bean.setAllPaymentPrice(NumUtil.toString(BigDecimal.ZERO));
      } else {
        bean.setAllPaymentPrice(NumUtil.toString(paymentPrice));
      }


      if (jdOrderHeader.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        bean.setNotPointInFull(false);
      } else {
        bean.setNotPointInFull(true);
      }

      // 支払情報部の値を設定
      PaymentBean paymentEdit = new PaymentBean();
      paymentEdit.setPaymentMethodCode(NumUtil.toString(jdOrderHeader.getPaymentMethodNo()));
      paymentEdit.setPaymentMethodName(jdOrderHeader.getPaymentMethodName());
      paymentEdit.setAdvanceLaterType(NumUtil.toString(jdOrderHeader.getAdvanceLaterFlg()));
      paymentEdit.setPaymentCommission(NumUtil.toString(jdOrderHeader.getPaymentCommission()));
      if (jdOrderHeader.getPaymentLimitDate() != null) {
        paymentEdit.setPaymentLimitDate(DateUtil.toDateString(jdOrderHeader.getPaymentLimitDate()));
      }
      paymentEdit.setPaymentDate(DateUtil.toDateString(jdOrderHeader.getPaymentDate()));
      paymentEdit.setCvsReceiptNo(jdOrderHeader.getPaymentReceiptNo());
      paymentEdit.setCvsReceiptUrl(jdOrderHeader.getPaymentReceiptUrl());
      if (StringUtil.hasValue(jdOrderHeader.getCvsCode())) {
        CashierPaymentTypeBase cashier = new CashierPaymentTypeCvs();
        List<CodeAttribute> cvsList = PaymentProviderManager.getCodeList(cashier);
        String cvsName = "";
        for (CodeAttribute cvs : cvsList) {
          if (cvs.getValue().equals(jdOrderHeader.getCvsCode())) {
            cvsName = cvs.getName();
          }
        }
        paymentEdit.setCvsName(cvsName);
      }
      if (jdOrderHeader.getPaymentOrderId() != null && jdOrderHeader.getPaymentOrderId() > 0) {
        paymentEdit.setTradeID(NumUtil.toString(jdOrderHeader.getPaymentOrderId()));
      }
      paymentEdit.setUpdateDatetime(jdOrderHeader.getUpdatedDatetime());

      bean.setPaymentEdit(paymentEdit);

      // 受注ヘッダ部の値を設定
      OrderHeaderBean orderHeaderEdit = new OrderHeaderBean();
      orderHeaderEdit.setCustomerCode(jdOrderHeader.getCustomerCode());
      orderHeaderEdit.setCustomerFirstName(jdOrderHeader.getFirstName());
      orderHeaderEdit.setCustomerLastName(jdOrderHeader.getLastName());
      orderHeaderEdit.setCustomerFirstNameKana(jdOrderHeader.getFirstNameKana());
      orderHeaderEdit.setCustomerLastNameKana(jdOrderHeader.getLastNameKana());
      orderHeaderEdit.setGuestFlg(NumUtil.toString(jdOrderHeader.getGuestFlg()));
      if (StringUtil.hasValue(jdOrderHeader.getPhoneNumber())) {
        String[] phoneNumber = jdOrderHeader.getPhoneNumber().split("-");
        if (phoneNumber.length == 2) {
          orderHeaderEdit.setCustomerTel1(phoneNumber[0]);
          orderHeaderEdit.setCustomerTel2(phoneNumber[1]);
        } else if (phoneNumber.length == 3) {
          orderHeaderEdit.setCustomerTel1(phoneNumber[0]);
          orderHeaderEdit.setCustomerTel2(phoneNumber[1]);
          orderHeaderEdit.setCustomerTel3(phoneNumber[2]);
        }
      } else {
        jdOrderHeader.setPhoneNumber("");
        orderHeaderEdit.setCustomerTel1("");
        orderHeaderEdit.setCustomerTel2("");
        orderHeaderEdit.setCustomerTel3("");
      }

      orderHeaderEdit.setCustomerMobile(jdOrderHeader.getMobileNumber());

      UtilService s = ServiceLocator.getUtilService(getLoginInfo());
      orderHeaderEdit.setPrefectureCode(jdOrderHeader.getPrefectureCode());
      orderHeaderEdit.setCityList(s.getJdCityNames(orderHeaderEdit.getPrefectureCode()));

      String recentEmail = "";
      String recentPhoneNumber = "";
      String recentMobileNumber = "";
      String customerCode = jdOrderHeader.getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        CustomerInfo orderCustomer = customerService.getCustomer(customerCode);
        if (orderCustomer != null) {
          if (!orderCustomer.getCustomer().getEmail().equals(jdOrderHeader.getEmail())) {
            recentEmail = orderCustomer.getCustomer().getEmail();
          }
          if (StringUtil.isNullOrEmpty(orderCustomer.getAddress().getPhoneNumber())) {
            orderCustomer.getAddress().setPhoneNumber("");
          }
          if (!orderCustomer.getAddress().getPhoneNumber().equals(jdOrderHeader.getPhoneNumber())) {
            recentPhoneNumber = orderCustomer.getAddress().getPhoneNumber();
          }
          if (StringUtil.isNullOrEmpty(orderCustomer.getAddress().getMobileNumber())) {
            orderCustomer.getAddress().setMobileNumber("");
          }
          if (!orderCustomer.getAddress().getMobileNumber().equals(jdOrderHeader.getMobileNumber())) {
            recentMobileNumber = orderCustomer.getAddress().getMobileNumber();
          }
        }
      }
      orderHeaderEdit.setRecentPhoneNumber(recentPhoneNumber);
      orderHeaderEdit.setRecentMobileNumber(recentMobileNumber);
      orderHeaderEdit.setRecentEmail(recentEmail);
      orderHeaderEdit.setCustomerEmail(jdOrderHeader.getEmail());
      orderHeaderEdit.setPostalCode(jdOrderHeader.getPostalCode());
      orderHeaderEdit.setPrefectureCode(jdOrderHeader.getPrefectureCode());
      orderHeaderEdit.setAddress1(jdOrderHeader.getAddress1());
      orderHeaderEdit.setAddress2(jdOrderHeader.getAddress2());
      orderHeaderEdit.setAddress3(jdOrderHeader.getAddress3());
      orderHeaderEdit.setAddress4(jdOrderHeader.getAddress4());
      orderHeaderEdit.setAreaCode(jdOrderHeader.getAreaCode());
      orderHeaderEdit.setMessage(jdOrderHeader.getMessage());
      orderHeaderEdit.setCaution(jdOrderHeader.getCaution());
      orderHeaderEdit.setUpdateDatetime(jdOrderHeader.getUpdatedDatetime());
      orderHeaderEdit.setCityCode(jdOrderHeader.getCityCode());
      bean.setOrderHeaderEdit(orderHeaderEdit);

      // 出荷情報部の値を設定
      List<ShippingHeaderBean> shippingList = new ArrayList<ShippingHeaderBean>();
      List<ShippingHeaderBean> returnShippingList = new ArrayList<ShippingHeaderBean>();
      setJdShippingData(shippingList, returnShippingList, orderContainer);
      bean.setShippingList(shippingList);

      // 配送希望日时指定
      ShippingContainer shippingContainer = orderContainer.getShippings().get(0);
      boolean codFlg = false;
      if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(jdOrderHeader.getPaymentMethodType())) {
        codFlg = true;
      }

      List<CartCommodityInfo> commodityList = new ArrayList<CartCommodityInfo>();
      for (JdOrderDetail orderDetail : orderContainer.getJdOrderDetails()) {
        CartCommodityInfo cartCommodityInfo = new CartCommodityInfo();
        CommodityInfo commodityInfo = catalogService.getSkuInfo(orderDetail.getShopCode(), orderDetail.getSkuCode());
        if (commodityInfo != null && commodityInfo.getHeader() != null) {
          cartCommodityInfo.setStockManagementType(commodityInfo.getHeader().getStockManagementType().toString());
          commodityList.add(cartCommodityInfo);
        }
      }

      List<CodeAttribute> deliveryAppointedDateList = s.getJdDeliveryDateList(shippingContainer.getJdShippingHeader()
          .getShopCode(), shippingContainer.getJdShippingHeader().getPrefectureCode(), codFlg, commodityList);
      bean.setDeliveryAppointedDateList(deliveryAppointedDateList);
      String deliveryDate = shippingContainer.getJdShippingHeader().getDeliveryAppointedDate();
      boolean exitsFlg = false;
      if (StringUtil.hasValue(deliveryDate)) {
        for (CodeAttribute info : deliveryAppointedDateList) {
          if (info.getValue().equals(deliveryDate)) {
            exitsFlg = true;
          }
        }
      }
      if (!exitsFlg) {
        deliveryDate = null;
      }
      if (StringUtil.isNullOrEmpty(deliveryDate) && deliveryAppointedDateList.size() > 0) {
        deliveryDate = deliveryAppointedDateList.get(0).getValue();
      }

      List<CodeAttribute> deliveryAppointedTimeList = s.getJdDeliveryTimeList(shippingContainer.getJdShippingHeader()
          .getShopCode(), shippingContainer.getJdShippingHeader().getPrefectureCode(), codFlg, deliveryDate, shippingContainer
          .getJdShippingHeader().getAreaCode());
      bean.setDeliveryAppointedTimeList(deliveryAppointedTimeList);

      bean.setJdOrderFlg(true);
      // 出荷返品情報部の値を設定
      bean.setReturnShippingList(returnShippingList);

      // 订单发票情报设定
      bean.setOrderInvoice(getOrderInvoice(orderNo, bean.getOrderHeaderEdit().getCustomerCode(), orderService));
      // 商品规格List查询
      List<CodeAttribute> commodityNameList = new ArrayList<CodeAttribute>();
      commodityNameList.add(new NameValue("请选择", ""));
      if (StringUtil.isNullOrEmpty(bean.getOrderInvoice().getInvoiceCommodityName())) {
        for (NameValue invoiceName : DIContainer.getInvoiceValue().getInvoiceCommodityNameList()) {
          commodityNameList.add(new NameValue(invoiceName.getName(), invoiceName.getValue()));
        }
      } else {
        boolean bl = false;
        for (List<NameValue> nameValueList : DIContainer.getInvoiceValue().getInvoiceCommodityNameByLanguageList()) {
          if (bl) {
            break;
          }
          for (NameValue invoiceCommodityName : nameValueList) {
            if (invoiceCommodityName.getValue().equals(bean.getOrderInvoice().getInvoiceCommodityName())) {
              commodityNameList.addAll(nameValueList);
              bl = true;
              break;
            }

          }
        }
      }
      bean.setInvoiceCommodityNameList(commodityNameList);
      // 实收金额
      bean.setPaidPrice(orderContainer.getJdOrderHeader().getPaidPrice());
      UtilService serv = ServiceLocator.getUtilService(getLoginInfo());
      bean.setAddressScript(serv.createJdAddressScript());
      bean.setAddressPrefectureList(serv.createJdPrefectureList());
      bean.setAddressCityList(serv.createJdCityList(bean.getOrderHeaderEdit().getPrefectureCode()));
      bean.setAddressAreaList(serv.createJdAreaList(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit().getCityCode()));

      // 计算订单包含的商品的总重量
      BigDecimal totalWeight = new BigDecimal(0);
      List<JdOrderDetail> jdCommodityList = jdService.getJdOrderDetailCommodityList(jdOrderHeader.getOrderNo(), DIContainer.getWebshopConfig().getSiteShopCode());
      for(JdOrderDetail jdOrderDetail : jdCommodityList) {
        CommodityDetail commodityDetail = orderService.getCommodityDetail(jdOrderDetail.getCommodityCode(), DIContainer.getWebshopConfig().getSiteShopCode());
        if(commodityDetail != null && commodityDetail.getWeight() != null) {
          totalWeight = totalWeight.add(commodityDetail.getWeight());        
        }
      }
      bean.setTotalWeight(totalWeight.toString());
    }
    
    
    
    setRequestBean(bean);
    // 処理完了系メッセージの表示
    if (getRequestParameter().getPathArgs().length > 1) {
      // urlパラメータの一つ目には受注番号が入る為、処理完了情報は二つ目に存在する
      String completeParam = getRequestParameter().getPathArgs()[1];
      if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.OrderDetailInitAction.0")));
      } else if (completeParam.equals(WebConstantCode.COMPLETE_CANCEL)) {
        addInformationMessage(WebMessage.get(CompleteMessage.CANCEL_COMPLETE, Messages
            .getString("web.action.back.order.OrderDetailInitAction.0")));
      } else if (completeParam.equals("cancel_error")) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.ORDER_CANCEL_DISABLED));
      }
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  private void initPrerender(OrderDetailBean bean) {
    bean.setPaymentDateClearButtonFlg(false);
    bean.setPaymentDateSetButtonFlg(false);
    bean.setModifyButtonFlg(false);
    bean.setUpdateButtonFlg(false);
    bean.setCancelButtonFlg(false);
    bean.setReturnButtonFlg(false);
    bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    bean.setShippingInformationDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    bean.setOrderFlgDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    if (StringUtil.hasValue(getNextUrl())) {
      return;
    }
    OrderDetailBean bean = (OrderDetailBean) getRequestBean();
    // 初期化
    initPrerender(bean);
    setButtonDisplayWithAuth(bean);
    // ステータスによるボタン表示・非表示の設定
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderSummary orderSummary = service.getOrderSummary2(bean.getOrderNo(), bean.getOrderType());
    CommodityInfo commodityInfo = null;
    // 返品ボタン
    boolean displayReturnButton = false;

    // ＥＣの場合
    if (OrderType.EC.getValue().equals(bean.getOrderType())) {
      List<ShippingContainer> shippingList = service.getShippingList(bean.getOrderNo());

      OrderHeader orderHeader = service.getOrderHeader(bean.getOrderNo());
      if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
        for (ShippingContainer sc : shippingList) {
          for (ShippingDetail sd : sc.getShippingDetails()) {
            commodityInfo = catalogService.getSkuInfo(sd.getShopCode(), sd.getSkuCode());
          }
        }
      }
      // 受注修正・キャンセルボタン
      if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())
          && (commodityInfo == null || !DateUtil.isPeriodDate(commodityInfo.getHeader().getReservationStartDatetime(),
              commodityInfo.getHeader().getReservationEndDatetime()))) {
        bean.setModifyButtonFlg(false);
        bean.setCancelButtonFlg(bean.isCancelButtonFlg());
      } else if (!OrderStatus.CANCELLED.equals(orderHeader.getOrderStatus())
          && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
        
            bean.setModifyButtonFlg(bean.isModifyButtonFlg());
            bean.setCancelButtonFlg(bean.isCancelButtonFlg());
            
      } else {
        bean.setModifyButtonFlg(false);
        bean.setCancelButtonFlg(false);
      }

      // カード決済の場合
      if (orderHeader.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
        bean.setPaymentDateSetButtonFlg(false);
        bean.setPaymentDateClearButtonFlg(false);
        bean.setModifyButtonFlg(false);
      }
      if (orderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        bean.setPaymentDateSetButtonFlg(false);
        bean.setPaymentDateClearButtonFlg(false);
      }

      // 顧客退会時の表示制御
      if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(orderHeader.getCustomerCode())) {
          bean.setPaymentDateSetButtonFlg(false);
          bean.setPaymentDateClearButtonFlg(false);
          bean.setUpdateButtonFlg(false);
          bean.setModifyButtonFlg(false);
          bean.setCancelButtonFlg(false);
          bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_READONLY);
        }
      }

      for (ShippingContainer shipping : shippingList) {
        ShippingHeader header = shipping.getShippingHeader();
        if (header.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())
            && header.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
          displayReturnButton = true;
        }
        if (header.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())) {
          bean.setUpdateButtonFlg(false);
        }
      }

      for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
        ShippingHeader header = service.getShippingHeader(shippingHeader.getShippingNo());
        ShippingStatus status = ShippingStatus.fromValue(header.getShippingStatus());
        // 出荷手配・出荷済み・キャンセルの場合出荷情報の変更は不可
        if (status.equals(ShippingStatus.SHIPPED) || status.equals(ShippingStatus.IN_PROCESSING)
            || status.equals(ShippingStatus.CANCELLED)) {
          shippingHeader.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
          bean.setOrderFlgDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
        } else {
          shippingHeader.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
          bean.setOrderFlgDisplayMode(WebConstantCode.DISPLAY_EDIT);
        }
      }

      // TMALLの場合
    } else if (OrderType.TMALL.getValue().equals(bean.getOrderType())) {
      List<ShippingContainer> tmallShippingList = service.getTmallShippingList(bean.getOrderNo());
      TmallOrderHeader tmallOrderHeader = service.getTmallOrderHeader(bean.getOrderNo());
      if (tmallOrderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
        for (ShippingContainer sc : tmallShippingList) {
          for (TmallShippingDetail sd : sc.getTmallShippingDetails()) {
            commodityInfo = catalogService.getSkuInfo(sd.getShopCode(), sd.getSkuCode());
          }
        }
      }
      for (ShippingContainer shipping : tmallShippingList) {
        TmallShippingHeader tmallShippingHeader = shipping.getTmallShippingHeader();
        if (tmallShippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())
            && tmallShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
          displayReturnButton = true;
        }
        if (tmallShippingHeader.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())) {
          bean.setUpdateButtonFlg(false);
        }
      }
      // 受注修正・キャンセルボタン
      if (tmallOrderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())
          && (commodityInfo == null || !DateUtil.isPeriodDate(commodityInfo.getHeader().getReservationStartDatetime(),
              commodityInfo.getHeader().getReservationEndDatetime()))) {
        bean.setModifyButtonFlg(false);
        bean.setCancelButtonFlg(false);
      } else if (!OrderStatus.CANCELLED.equals(tmallOrderHeader.getOrderStatus())
          && tmallOrderHeader.getPaymentStatus().equals(PaymentStatus.PAID.longValue())
          && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
        bean.setModifyButtonFlg(bean.isModifyButtonFlg());
        bean.setCancelButtonFlg(bean.isCancelButtonFlg());
      } else {
        bean.setModifyButtonFlg(false);
        bean.setCancelButtonFlg(false);
      }
      if (tmallOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
        if (orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
          bean.setUpdateButtonFlg(true);
        } else {
          bean.setUpdateButtonFlg(false);
        }
      } else {
        if (tmallOrderHeader.getPaymentStatus().equals(PaymentStatus.PAID.longValue()) && tmallOrderHeader.getPaymentDate() != null
            && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
          bean.setUpdateButtonFlg(true);
        } else {
          bean.setUpdateButtonFlg(false);
        }
      }

      // カード決済の場合
      if (tmallOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
        bean.setPaymentDateSetButtonFlg(false);
        bean.setPaymentDateClearButtonFlg(false);
        bean.setModifyButtonFlg(false);
      }
      // M17N 10361 追加 ここから
      if (tmallOrderHeader.getPaymentMethodType().equals(PaymentMethodType.ALIPAY.getValue())
          || tmallOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())) {
        bean.setPaymentDateSetButtonFlg(true);
        bean.setPaymentDateClearButtonFlg(true);
        bean.setModifyButtonFlg(false);
      }
      // M17N 10361 追加 ここまで
      // 受注ステータスキャンセル済チェック
      if (tmallOrderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        bean.setPaymentDateSetButtonFlg(false);
        bean.setPaymentDateClearButtonFlg(false);
      }

      // 顧客退会時の表示制御
      if (CustomerConstant.isCustomer(tmallOrderHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(tmallOrderHeader.getCustomerCode())) {
          bean.setPaymentDateSetButtonFlg(false);
          bean.setPaymentDateClearButtonFlg(false);
          bean.setUpdateButtonFlg(false);
          bean.setModifyButtonFlg(false);
          bean.setCancelButtonFlg(false);
          bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_READONLY);
        }
      }
      for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
        TmallShippingHeader tmallHeader = service.getTmallShippingHeader(shippingHeader.getShippingNo());
        ShippingStatus status = ShippingStatus.fromValue(tmallHeader.getShippingStatus());
        // 出荷手配・出荷済み・キャンセルの場合出荷情報の変更は不可
        if (status.equals(ShippingStatus.SHIPPED) || status.equals(ShippingStatus.IN_PROCESSING)
            || status.equals(ShippingStatus.CANCELLED)) {
          shippingHeader.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
          shippingHeader.setDisplayMode2(WebConstantCode.DISPLAY_HIDDEN);
          bean.setOrderFlgDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
        } else {
          shippingHeader.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
          shippingHeader.setDisplayMode2(WebConstantCode.DISPLAY_EDIT);
          bean.setOrderFlgDisplayMode(WebConstantCode.DISPLAY_EDIT);
        }
      }
      // JDの場合
    } else if (OrderType.JD.getValue().equals(bean.getOrderType())) {
      List<ShippingContainer> jdShippingList = service.getJdShippingList(bean.getOrderNo());
      JdOrderHeader jdOrderHeader = service.getJdOrderHeader(bean.getOrderNo());
      if (jdOrderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
        for (ShippingContainer sc : jdShippingList) {
          for (JdShippingDetail sd : sc.getJdShippingDetails()) {
            commodityInfo = catalogService.getSkuInfo(sd.getShopCode(), sd.getSkuCode());
          }
        }
      }
      for (ShippingContainer shipping : jdShippingList) {
        JdShippingHeader jdShippingHeader = shipping.getJdShippingHeader();
        if (jdShippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())
            && jdShippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
          displayReturnButton = true;
        }
        if (jdShippingHeader.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())) {
          bean.setUpdateButtonFlg(false);
        }
      }
      // 受注修正・キャンセルボタン
      if (jdOrderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())
          && (commodityInfo == null || !DateUtil.isPeriodDate(commodityInfo.getHeader().getReservationStartDatetime(),
              commodityInfo.getHeader().getReservationEndDatetime()))) {
        bean.setModifyButtonFlg(false);
        bean.setCancelButtonFlg(false);
      } else if (!OrderStatus.CANCELLED.equals(jdOrderHeader.getOrderStatus())
          && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
        bean.setModifyButtonFlg(bean.isModifyButtonFlg());
        bean.setCancelButtonFlg(bean.isCancelButtonFlg());
      } else {
        bean.setModifyButtonFlg(false);
        bean.setCancelButtonFlg(false);
      }
      if (jdOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
        if (orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
          bean.setUpdateButtonFlg(true);
        } else {
          bean.setUpdateButtonFlg(false);
        }
      } else {
        if (jdOrderHeader.getPaymentStatus().equals(PaymentStatus.PAID.longValue()) && jdOrderHeader.getPaymentDate() != null
            && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())) {
          bean.setUpdateButtonFlg(true);
        } else {
          bean.setUpdateButtonFlg(false);
        }
      }

      // カード決済の場合
      if (jdOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
        bean.setPaymentDateSetButtonFlg(false);
        bean.setPaymentDateClearButtonFlg(false);
        bean.setModifyButtonFlg(false);
      }
      // M17N 10361 追加 ここから
      if (jdOrderHeader.getPaymentMethodType().equals(PaymentMethodType.ALIPAY.getValue())
          || jdOrderHeader.getPaymentMethodType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())) {
        bean.setPaymentDateSetButtonFlg(true);
        bean.setPaymentDateClearButtonFlg(true);
        bean.setModifyButtonFlg(false);
      }
      // M17N 10361 追加 ここまで
      // 受注ステータスキャンセル済チェック
      if (jdOrderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        bean.setPaymentDateSetButtonFlg(false);
        bean.setPaymentDateClearButtonFlg(false);
      }

      // 顧客退会時の表示制御
      if (CustomerConstant.isCustomer(jdOrderHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(jdOrderHeader.getCustomerCode())) {
          bean.setPaymentDateSetButtonFlg(false);
          bean.setPaymentDateClearButtonFlg(false);
          bean.setUpdateButtonFlg(false);
          bean.setModifyButtonFlg(false);
          bean.setCancelButtonFlg(false);
          bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_READONLY);
        }
      }
      for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
        JdShippingHeader jdHeader = service.getJdShippingHeader(shippingHeader.getShippingNo());
        ShippingStatus status = ShippingStatus.fromValue(jdHeader.getShippingStatus());
        // 出荷手配・出荷済み・キャンセルの場合出荷情報の変更は不可
        if (status.equals(ShippingStatus.SHIPPED) || status.equals(ShippingStatus.IN_PROCESSING)
            || status.equals(ShippingStatus.CANCELLED)) {
          shippingHeader.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
          shippingHeader.setDisplayMode2(WebConstantCode.DISPLAY_HIDDEN);
          bean.setOrderFlgDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
        } else {
          shippingHeader.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
          shippingHeader.setDisplayMode2(WebConstantCode.DISPLAY_EDIT);
          bean.setOrderFlgDisplayMode(WebConstantCode.DISPLAY_EDIT);
        }
      }
    }

    if (displayReturnButton) {
      bean.setReturnButtonFlg(bean.isReturnButtonFlg());
    } else {
      bean.setReturnButtonFlg(false);
    }

    // 更新不可能なときは、連絡事項・注意事項（管理側のみ参照）をreadonlyにする
    if (bean.isUpdateButtonFlg()) {
      bean.setMessagesEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setMessagesEditMode(WebConstantCode.DISPLAY_READONLY);
    }
    // 受注状況・返品状況チェック
    bean.setOrderStatusName(OrderStatus.fromValue(NumUtil.toLong(bean.getOrderStatus())).getName());
    if (orderSummary.getReturnStatusSummary().equals(ReturnStatusSummary.PARTIAL_RETURNED.longValue())
        || orderSummary.getReturnStatusSummary().equals(ReturnStatusSummary.RETURNED_ALL.longValue())) {
      bean.setHasReturn(Messages.getString("web.action.back.order.OrderDetailInitAction.1"));
    }
    // M17N 10361 追加 ここから
    if (bean.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.getValue())
        || bean.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.getValue())) {
      bean.setPaymentDateSetButtonFlg(false);
      bean.setPaymentDateClearButtonFlg(false);
    }
    // M17N 10361 追加 こまで

    // TMALLの場合、受注修正不可の制御を追加する
    if (OrderType.TMALL.getValue().equals(bean.getOrderType())) {
      bean.setModifyButtonFlg(false);
      //bean.setCancelButtonFlg(false);
    }
    // JDの場合、受注修正不可の制御を追加する
    if (OrderType.JD.getValue().equals(bean.getOrderType())) {
      bean.setModifyButtonFlg(false);
      //bean.setCancelButtonFlg(false);
    }

    if (bean.getOrderFlg().equals("2")) {
      if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
        bean.setAuthority(getLoginInfo().hasPermission(Permission.CONFIRM_OBJECT_IO_SITE));
      } else {
        bean.setAuthority(getLoginInfo().hasPermission(Permission.CONFIRM_OBJECT_IO_SHOP)
            || getLoginInfo().hasPermission(Permission.CONFIRM_OBJECT_IO_SITE));
      }
    }

    setRequestBean(bean);
  }

  private void setButtonDisplayWithAuth(OrderDetailBean bean) {
    // モード別ボタン表示・非表示の設定
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
      bean.setOrderAndPaymentPartDisplayFlg(getLoginInfo().isSite());
      if (Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo())) {
        bean.setPaymentDateClearButtonFlg(true);
        bean.setPaymentDateSetButtonFlg(true);
        bean.setUpdateButtonFlg(true);
        bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
        bean.setShippingInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
      }
      if (Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo())) {
        bean.setModifyButtonFlg(true);
        bean.setCancelButtonFlg(true);
        bean.setReturnButtonFlg(true);
        bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
        bean.setShippingInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
      }
      if (Permission.ORDER_READ_SITE.isGranted(getLoginInfo())) {
        bean.setReturnButtonFlg(true);
      }
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      bean.setOrderAndPaymentPartDisplayFlg(true);
      if (Permission.ORDER_UPDATE_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo())) {
        bean.setPaymentDateClearButtonFlg(true);
        bean.setPaymentDateSetButtonFlg(true);
        bean.setUpdateButtonFlg(true);
        bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
        bean.setShippingInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
      }
      if (Permission.ORDER_MODIFY_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo())) {
        bean.setModifyButtonFlg(true);
        bean.setCancelButtonFlg(true);
        bean.setReturnButtonFlg(true);
        bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
        bean.setShippingInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
      }
      if (Permission.ORDER_READ_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_READ_SITE.isGranted(getLoginInfo())) {
        bean.setReturnButtonFlg(true);
      }
    } else if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      bean.setOrderAndPaymentPartDisplayFlg(true);
      if (Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo())) {
        bean.setPaymentDateClearButtonFlg(true);
        bean.setPaymentDateSetButtonFlg(true);
        bean.setUpdateButtonFlg(true);
        bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
        bean.setShippingInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
      }
      if (Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo())) {
        bean.setModifyButtonFlg(true);
        bean.setCancelButtonFlg(true);
        bean.setReturnButtonFlg(true);
        bean.setOrderInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
        bean.setShippingInformationDisplayMode(WebConstantCode.DISPLAY_EDIT);
      }
      if (Permission.ORDER_READ_SITE.isGranted(getLoginInfo())) {
        bean.setReturnButtonFlg(true);
      }
    }
  }

  // soukai add 2011/12/27 ob start

  /**
   * 出荷情報をnextBeanに設定する
   * 
   * @param shippingList
   * @param orderContainer
   */
  private void setTmallShippingData(List<ShippingHeaderBean> shippingList, List<ShippingHeaderBean> returnShippingList,
      OrderContainer orderContainer) {
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    List<ShippingContainer> shippingContainerList = orderContainer.getShippings();
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    Collections.sort(shippingContainerList, new TmallShippingComparator());
    for (ShippingContainer shippingContainer : shippingContainerList) {
      // 返品データの場合、次の出荷情報へ
      if (ReturnItemType.RETURNED.longValue().equals(shippingContainer.getTmallShippingHeader().getReturnItemType())) {
        ShippingHeaderBean shippingHeader = new ShippingHeaderBean();
        // 出荷情報明細部の値を設定
        List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();

        for (TmallShippingDetail shippingDetailInfo : shippingContainer.getTmallShippingDetails()) {

          ShippingDetailBean shippingDetail = new ShippingDetailBean();

          shippingDetail.setShippingDetailSkuCode(shippingDetailInfo.getSkuCode());

          // 商品関連情報は受注管理明細より取得
          TmallOrderDetail orderDetail = getTmallOrderDetail(orderContainer.getTmallIOrderDetails(), shippingDetailInfo
              .getShopCode(), shippingDetailInfo.getSkuCode());

          TmallShippingDetailComposition sdc = null;
          if (!StringUtil.isNullOrEmpty(shippingContainer.getTmallShippingHeader().getOriginalShippingNo())) {
            sdc = orderService.getTmallShippingDetailCompositionBean(shippingContainer.getTmallShippingHeader()
                .getOriginalShippingNo().toString(), shippingDetailInfo.getSkuCode());
          }

          // 出荷明細に関連付いている受注管理明細が存在しなかった場合エラー

          if (orderDetail == null && sdc == null) {
            Logger logger = Logger.getLogger(this.getClass());
            logger.error(Messages.log("web.action.back.order.OrderDetailInitAction.2") + shippingDetailInfo.getShopCode()
                + Messages.log("web.action.back.order.OrderDetailInitAction.3") + shippingDetailInfo.getSkuCode()
                + Messages.log("web.action.back.order.OrderDetailInitAction.4"));
            throw new RuntimeException(Messages.getString("web.action.back.order.OrderDetailInitAction.5"));
          }

          if (sdc != null) {
            shippingDetail.setShippingDetailCommodityName(sdc.getCommodityName());
            shippingDetail.setShippingDetailStandardDetail1Name(sdc.getStandardDetail1Name());
            shippingDetail.setShippingDetailStandardDetail2Name(sdc.getStandardDetail2Name());
            shippingDetail.setShippingDetailPurchasingAmount(shippingDetailInfo.getPurchasingAmount().toString());
            shippingDetail.setShippingDetailSalesPrice(sdc.getRetailPrice().toString());
            shippingDetail.setShippingDetailCommodityTaxType(sdc.getCommodityTaxType().toString());
          } else {
            shippingDetail.setShippingDetailCommodityName(orderDetail.getCommodityName());
            shippingDetail.setShippingDetailStandardDetail1Name(orderDetail.getStandardDetail1Name());
            shippingDetail.setShippingDetailStandardDetail2Name(orderDetail.getStandardDetail2Name());
            shippingDetail.setShippingDetailPurchasingAmount(shippingDetailInfo.getPurchasingAmount().toString());
            shippingDetail.setShippingDetailSalesPrice(shippingDetailInfo.getRetailPrice().toString());
            shippingDetail.setShippingDetailCommodityTaxType(orderDetail.getCommodityTaxType().toString());
          }

          shippingDetail.setShippingDetailGiftCode(shippingDetailInfo.getGiftCode());
          if (StringUtil.isNullOrEmpty(shippingDetailInfo.getGiftName())) {
            shippingDetail.setShippingDetailGiftName(Messages.log("web.action.back.order.OrderDetailInitAction.7"));
          } else {
            shippingDetail.setShippingDetailGiftName(shippingDetailInfo.getGiftName());
          }
          shippingDetail.setShippingDetailGiftPrice(shippingDetailInfo.getGiftPrice().toString());
          shippingDetail.setShippingDetailGiftTaxType(shippingDetailInfo.getGiftTaxType().toString());
          shippingDetail.setShippingDetailTotalPrice(String.valueOf(BigDecimalUtil.multiply(BigDecimalUtil.add(shippingDetailInfo
              .getRetailPrice(), shippingDetailInfo.getGiftPrice()), Long.parseLong(shippingDetail
              .getShippingDetailPurchasingAmount()))));

          shippingDetailList.add(shippingDetail);
        }

        shippingHeader.setShippingDetailList(shippingDetailList);

        returnShippingList.add(shippingHeader);

        continue;
      }
      // 出荷情報ヘッダ部の設定
      TmallShippingHeader tmallShippingInfo = shippingContainer.getTmallShippingHeader();
      ShippingHeaderBean shippingHeader = new ShippingHeaderBean();

      DeliveryType delivery = shopService.getDeliveryType(tmallShippingInfo.getShopCode(), tmallShippingInfo.getDeliveryTypeNo());

      // 配送指定日時の設定
      DeliverySpecificationType type = DeliverySpecificationType.fromValue(delivery.getDeliverySpecificationType());
      boolean displayDeliveryAppointedDate = false;
      if (orderContainer.getTmallOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        displayDeliveryAppointedDate = false;
      } else {
        if (StringUtil.hasValue(tmallShippingInfo.getDeliveryAppointedDate())) {
          shippingHeader.setDeliveryAppointedDate(tmallShippingInfo.getDeliveryAppointedDate());
          displayDeliveryAppointedDate = true;
        } else {
          shippingHeader.setDeliveryAppointedDate("");
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.DATE_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedDate = true;
        }
      }
      shippingHeader.setDisplayDeliveryAppointedDate(displayDeliveryAppointedDate);

      // 配送希望時間帯
      shippingHeader.setDeliveryAppointedStartTimeList(createTimeList());
      shippingHeader.setDeliveryAppointedEndTimeList(createTimeList());
      String start = NumUtil.toString(tmallShippingInfo.getDeliveryAppointedTimeStart());
      String end = NumUtil.toString(tmallShippingInfo.getDeliveryAppointedTimeEnd());
      boolean displayDeliveryAppointedTime = false;
      if (orderContainer.getTmallOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        displayDeliveryAppointedTime = false;
      } else {
        if (StringUtil.isNullOrEmptyAnyOf(start, end)) {
          shippingHeader.setDeliveryAppointedStartTime("");
          shippingHeader.setDeliveryAppointedEndTime("");
        } else {
          shippingHeader.setDeliveryAppointedStartTime(start);
          shippingHeader.setDeliveryAppointedEndTime(end);
          displayDeliveryAppointedTime = true;
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.TIME_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedTime = true;
        }
      }
      shippingHeader.setDisplayDeliveryAppointedTime(displayDeliveryAppointedTime);
      if (StringUtil.hasValueAnyOf(start, end)) {
        if (StringUtil.hasValueAnyOf(start, end)) {
          shippingHeader.setDeliveryDateTime(start + "-" + end);
          shippingHeader.setDeliveryDateTimeName(start + "时-" + end + "时");
        } else if (StringUtil.hasValue(start)) {
          shippingHeader.setDeliveryDateTime(start + "-");
          shippingHeader.setDeliveryDateTimeName(start + "时以后");
        } else {
          shippingHeader.setDeliveryDateTime("-" + end);
          shippingHeader.setDeliveryDateTimeName(end + "时以前");
        }
      }
      shippingHeader.setShippingNo(tmallShippingInfo.getShippingNo());
      shippingHeader.setShippingFirstName(tmallShippingInfo.getAddressFirstName());
      shippingHeader.setShippingLastName(tmallShippingInfo.getAddressLastName());
      shippingHeader.setShippingFirstNameKana(tmallShippingInfo.getAddressFirstNameKana());
      shippingHeader.setShippingLastNameKana(tmallShippingInfo.getAddressLastNameKana());
      if (StringUtil.hasValue(tmallShippingInfo.getPhoneNumber())) {
        String[] shippingPhoneNumber = tmallShippingInfo.getPhoneNumber().split("-");
        if (shippingPhoneNumber.length == 2) {
          shippingHeader.setShippingTel1(shippingPhoneNumber[0]);
          shippingHeader.setShippingTel2(shippingPhoneNumber[1]);
        } else if (shippingPhoneNumber.length == 3) {
          shippingHeader.setShippingTel1(shippingPhoneNumber[0]);
          shippingHeader.setShippingTel2(shippingPhoneNumber[1]);
          shippingHeader.setShippingTel3(shippingPhoneNumber[2]);
        }
      } else {
        tmallShippingInfo.setPhoneNumber("");
      }
      shippingHeader.setMobileTel(tmallShippingInfo.getMobileNumber());
      shippingHeader.setShippingCityCode(tmallShippingInfo.getCityCode());
      shippingHeader.setShippingPostalCode(tmallShippingInfo.getPostalCode());
      shippingHeader.setShippingPrefectureCode(tmallShippingInfo.getPrefectureCode());
      shippingHeader.setOrgShippingPrefectureCode(tmallShippingInfo.getPrefectureCode());
      shippingHeader.setShippingAddress1(tmallShippingInfo.getAddress1());
      shippingHeader.setShippingAddress2(tmallShippingInfo.getAddress2());
      shippingHeader.setShippingAddress3(tmallShippingInfo.getAddress3());
      shippingHeader.setShippingAddress4(tmallShippingInfo.getAddress4());
      shippingHeader.setAreaCode(tmallShippingInfo.getAreaCode());
      shippingHeader.setDeliveryCompanyName(tmallShippingInfo.getDeliveryCompanyName());
      shippingHeader.setShippingShopCode(tmallShippingInfo.getShopCode());
      shippingHeader.setShippingDirectDate(DateUtil.toDateString(tmallShippingInfo.getShippingDirectDate()));
      shippingHeader.setShippingDate(DateUtil.toDateString(tmallShippingInfo.getShippingDate()));
      shippingHeader.setShippingShopName(getShopName(tmallShippingInfo.getShopCode()));
      shippingHeader.setShippingTypeCode(NumUtil.toString(tmallShippingInfo.getDeliveryTypeNo()));
      shippingHeader.setShippingTypeName(tmallShippingInfo.getDeliveryTypeName());
      shippingHeader.setUpdateDatetime(tmallShippingInfo.getUpdatedDatetime());
      shippingHeader.setDeliveryRemark(tmallShippingInfo.getDeliveryRemark());
      shippingHeader.setShippingCharge(StringUtil.coalesce(tmallShippingInfo.getShippingCharge().toString(), ""));
      shippingHeader.setShippingChargeTaxType(StringUtil.coalesce(tmallShippingInfo.getShippingChargeTaxType().toString(), ""));
      // 出荷情報明細部の値を設定
      List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();
      for (TmallShippingDetail tmallShippingDetailInfo : shippingContainer.getTmallShippingDetails()) {
        ShippingDetailBean shippingDetail = new ShippingDetailBean();
        shippingDetail.setShippingDetailSkuCode(tmallShippingDetailInfo.getSkuCode());
        // 商品関連情報は受注管理明細より取得
        TmallOrderDetail tmallOrderDetail = getTmallOrderDetail(orderContainer.getTmallIOrderDetails(), tmallShippingDetailInfo
            .getShopCode(), tmallShippingDetailInfo.getSkuCode());
        // 出荷明細に関連付いている受注管理明細が存在しなかった場合エラー
        if (tmallOrderDetail == null) {
          Logger logger = Logger.getLogger(this.getClass());
          logger.error(Messages.log("web.action.back.order.OrderDetailInitAction.2") + tmallShippingDetailInfo.getShopCode()
              + Messages.log("web.action.back.order.OrderDetailInitAction.3") + tmallShippingDetailInfo.getSkuCode()
              + Messages.log("web.action.back.order.OrderDetailInitAction.4"));
          throw new RuntimeException(Messages.getString("web.action.back.order.OrderDetailInitAction.5"));
        }

        shippingDetail.setShippingDetailCommodityName(tmallOrderDetail.getCommodityName());
        shippingDetail.setShippingDetailStandardDetail1Name(tmallOrderDetail.getStandardDetail1Name());
        shippingDetail.setShippingDetailStandardDetail2Name(tmallOrderDetail.getStandardDetail2Name());
        shippingDetail.setShippingDetailPurchasingAmount(tmallShippingDetailInfo.getPurchasingAmount().toString());
        shippingDetail.setShippingDetailSalesPrice(tmallOrderDetail.getRetailPrice().toString());
        shippingDetail.setShippingDetailCommodityTaxType(tmallOrderDetail.getCommodityTaxType().toString());

        shippingDetail.setShippingDetailGiftCode(tmallShippingDetailInfo.getGiftCode());
        if (StringUtil.isNullOrEmpty(tmallShippingDetailInfo.getGiftName())) {
          shippingDetail.setShippingDetailGiftName(Messages.log("web.action.back.order.OrderDetailInitAction.7"));
        } else {
          shippingDetail.setShippingDetailGiftName(tmallShippingDetailInfo.getGiftName());
        }
        shippingDetail.setShippingDetailGiftPrice(tmallShippingDetailInfo.getGiftPrice().toString());
        shippingDetail.setShippingDetailGiftTaxType(tmallShippingDetailInfo.getGiftTaxType().toString());
        shippingDetail.setShippingDetailTotalPrice(String.valueOf(BigDecimalUtil.multiply(BigDecimalUtil.add(tmallOrderDetail
            .getRetailPrice(), tmallShippingDetailInfo.getGiftPrice()), Long.parseLong(shippingDetail
            .getShippingDetailPurchasingAmount()))));

        shippingDetailList.add(shippingDetail);
      }
      shippingHeader.setShippingDetailList(shippingDetailList);
      shippingList.add(shippingHeader);
    }
  }

  private void setJdShippingData(List<ShippingHeaderBean> shippingList, List<ShippingHeaderBean> returnShippingList,
      OrderContainer orderContainer) {
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    List<ShippingContainer> shippingContainerList = orderContainer.getShippings();
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    Collections.sort(shippingContainerList, new JdShippingComparator());
    for (ShippingContainer shippingContainer : shippingContainerList) {
      // 返品データの場合、次の出荷情報へ
      if (ReturnItemType.RETURNED.longValue().equals(shippingContainer.getJdShippingHeader().getReturnItemType())) {
        ShippingHeaderBean shippingHeader = new ShippingHeaderBean();
        // 出荷情報明細部の値を設定
        List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();

        for (JdShippingDetail shippingDetailInfo : shippingContainer.getJdShippingDetails()) {

          ShippingDetailBean shippingDetail = new ShippingDetailBean();

          shippingDetail.setShippingDetailSkuCode(shippingDetailInfo.getSkuCode());

          // 商品関連情報は受注管理明細より取得
          JdOrderDetail orderDetail = getJdOrderDetail(orderContainer.getJdOrderDetails(), shippingDetailInfo
              .getShopCode(), shippingDetailInfo.getSkuCode());

          JdShippingDetailComposition sdc = null;
          if (!StringUtil.isNullOrEmpty(shippingContainer.getJdShippingHeader().getOriginalShippingNo())) {
            sdc = orderService.getJdShippingDetailCompositionBean(shippingContainer.getJdShippingHeader()
                .getOriginalShippingNo().toString(), shippingDetailInfo.getSkuCode());
          }

          // 出荷明細に関連付いている受注管理明細が存在しなかった場合エラー

          if (orderDetail == null && sdc == null) {
          //if (orderDetail == null) {
            Logger logger = Logger.getLogger(this.getClass());
            logger.error(Messages.log("web.action.back.order.OrderDetailInitAction.2") + shippingDetailInfo.getShopCode()
                + Messages.log("web.action.back.order.OrderDetailInitAction.3") + shippingDetailInfo.getSkuCode()
                + Messages.log("web.action.back.order.OrderDetailInitAction.4"));
            throw new RuntimeException(Messages.getString("web.action.back.order.OrderDetailInitAction.5"));
          }

          if (sdc != null) {
            shippingDetail.setShippingDetailCommodityName(sdc.getCommodityName());
            shippingDetail.setShippingDetailStandardDetail1Name(sdc.getStandardDetail1Name());
            shippingDetail.setShippingDetailStandardDetail2Name(sdc.getStandardDetail2Name());
            shippingDetail.setShippingDetailPurchasingAmount(shippingDetailInfo.getPurchasingAmount().toString());
            shippingDetail.setShippingDetailSalesPrice(sdc.getRetailPrice().toString());
            shippingDetail.setShippingDetailCommodityTaxType(sdc.getCommodityTaxType().toString());
          } else {
            shippingDetail.setShippingDetailCommodityName(orderDetail.getCommodityName());
            shippingDetail.setShippingDetailStandardDetail1Name(orderDetail.getStandardDetail1Name());
            shippingDetail.setShippingDetailStandardDetail2Name(orderDetail.getStandardDetail2Name());
            shippingDetail.setShippingDetailPurchasingAmount(shippingDetailInfo.getPurchasingAmount().toString());
            shippingDetail.setShippingDetailSalesPrice(shippingDetailInfo.getRetailPrice().toString());
            shippingDetail.setShippingDetailCommodityTaxType(orderDetail.getCommodityTaxType().toString());
          }

          shippingDetail.setShippingDetailGiftCode(shippingDetailInfo.getGiftCode());
          if (StringUtil.isNullOrEmpty(shippingDetailInfo.getGiftName())) {
            shippingDetail.setShippingDetailGiftName(Messages.log("web.action.back.order.OrderDetailInitAction.7"));
          } else {
            shippingDetail.setShippingDetailGiftName(shippingDetailInfo.getGiftName());
          }
          shippingDetail.setShippingDetailGiftPrice(shippingDetailInfo.getGiftPrice().toString());
          shippingDetail.setShippingDetailGiftTaxType(shippingDetailInfo.getGiftTaxType().toString());
          shippingDetail.setShippingDetailTotalPrice(String.valueOf(BigDecimalUtil.multiply(BigDecimalUtil.add(shippingDetailInfo
              .getRetailPrice(), shippingDetailInfo.getGiftPrice()), Long.parseLong(shippingDetail
              .getShippingDetailPurchasingAmount()))));

          shippingDetailList.add(shippingDetail);
        }

        shippingHeader.setShippingDetailList(shippingDetailList);

        returnShippingList.add(shippingHeader);

        continue;
      }
      // 出荷情報ヘッダ部の設定
      JdShippingHeader jdShippingInfo = shippingContainer.getJdShippingHeader();
      ShippingHeaderBean shippingHeader = new ShippingHeaderBean();

      DeliveryType delivery = shopService.getDeliveryType(jdShippingInfo.getShopCode(), jdShippingInfo.getDeliveryTypeNo());

      // 配送指定日時の設定
      DeliverySpecificationType type = DeliverySpecificationType.fromValue(delivery.getDeliverySpecificationType());
      boolean displayDeliveryAppointedDate = false;
      if (orderContainer.getJdOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        displayDeliveryAppointedDate = false;
      } else {
        if (StringUtil.hasValue(jdShippingInfo.getDeliveryAppointedDate())) {
          shippingHeader.setDeliveryAppointedDate(jdShippingInfo.getDeliveryAppointedDate());
          displayDeliveryAppointedDate = true;
        } else {
          shippingHeader.setDeliveryAppointedDate("");
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.DATE_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedDate = true;
        }
      }
      shippingHeader.setDisplayDeliveryAppointedDate(displayDeliveryAppointedDate);

      // 配送希望時間帯
      shippingHeader.setDeliveryAppointedStartTimeList(createTimeList());
      shippingHeader.setDeliveryAppointedEndTimeList(createTimeList());
      String start = NumUtil.toString(jdShippingInfo.getDeliveryAppointedTimeStart());
      String end = NumUtil.toString(jdShippingInfo.getDeliveryAppointedTimeEnd());
      boolean displayDeliveryAppointedTime = false;
      if (orderContainer.getJdOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        displayDeliveryAppointedTime = false;
      } else {
        if (StringUtil.isNullOrEmptyAnyOf(start, end)) {
          shippingHeader.setDeliveryAppointedStartTime("");
          shippingHeader.setDeliveryAppointedEndTime("");
        } else {
          shippingHeader.setDeliveryAppointedStartTime(start);
          shippingHeader.setDeliveryAppointedEndTime(end);
          displayDeliveryAppointedTime = true;
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.TIME_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedTime = true;
        }
      }
      shippingHeader.setDisplayDeliveryAppointedTime(displayDeliveryAppointedTime);
      if (StringUtil.hasValueAnyOf(start, end)) {
        if (StringUtil.hasValueAnyOf(start, end)) {
          shippingHeader.setDeliveryDateTime(start + "-" + end);
          shippingHeader.setDeliveryDateTimeName(start + "时-" + end + "时");
        } else if (StringUtil.hasValue(start)) {
          shippingHeader.setDeliveryDateTime(start + "-");
          shippingHeader.setDeliveryDateTimeName(start + "时以后");
        } else {
          shippingHeader.setDeliveryDateTime("-" + end);
          shippingHeader.setDeliveryDateTimeName(end + "时以前");
        }
      }
      shippingHeader.setShippingNo(jdShippingInfo.getShippingNo());
      shippingHeader.setShippingFirstName(jdShippingInfo.getAddressFirstName());
      shippingHeader.setShippingLastName(jdShippingInfo.getAddressLastName());
      shippingHeader.setShippingFirstNameKana(jdShippingInfo.getAddressFirstNameKana());
      shippingHeader.setShippingLastNameKana(jdShippingInfo.getAddressLastNameKana());
      if (StringUtil.hasValue(jdShippingInfo.getPhoneNumber())) {
        String[] shippingPhoneNumber = jdShippingInfo.getPhoneNumber().split("-");
        if (shippingPhoneNumber.length == 2) {
          shippingHeader.setShippingTel1(shippingPhoneNumber[0]);
          shippingHeader.setShippingTel2(shippingPhoneNumber[1]);
        } else if (shippingPhoneNumber.length == 3) {
          shippingHeader.setShippingTel1(shippingPhoneNumber[0]);
          shippingHeader.setShippingTel2(shippingPhoneNumber[1]);
          shippingHeader.setShippingTel3(shippingPhoneNumber[2]);
        }
      } else {
        jdShippingInfo.setPhoneNumber("");
      }
      shippingHeader.setMobileTel(jdShippingInfo.getMobileNumber());
      shippingHeader.setShippingCityCode(jdShippingInfo.getCityCode());
      shippingHeader.setShippingPostalCode(jdShippingInfo.getPostalCode());
      shippingHeader.setShippingPrefectureCode(jdShippingInfo.getPrefectureCode());
      shippingHeader.setOrgShippingPrefectureCode(jdShippingInfo.getPrefectureCode());
      shippingHeader.setShippingAddress1(jdShippingInfo.getAddress1());
      shippingHeader.setShippingAddress2(jdShippingInfo.getAddress2());
      shippingHeader.setShippingAddress3(jdShippingInfo.getAddress3());
      shippingHeader.setShippingAddress4(jdShippingInfo.getAddress4());
      shippingHeader.setAreaCode(jdShippingInfo.getAreaCode());
      shippingHeader.setDeliveryCompanyName(jdShippingInfo.getDeliveryCompanyName());
      shippingHeader.setShippingShopCode(jdShippingInfo.getShopCode());
      shippingHeader.setShippingDirectDate(DateUtil.toDateString(jdShippingInfo.getShippingDirectDate()));
      shippingHeader.setShippingDate(DateUtil.toDateString(jdShippingInfo.getShippingDate()));
      shippingHeader.setShippingShopName(getShopName(jdShippingInfo.getShopCode()));
      shippingHeader.setShippingTypeCode(NumUtil.toString(jdShippingInfo.getDeliveryTypeNo()));
      shippingHeader.setShippingTypeName(jdShippingInfo.getDeliveryTypeName());
      shippingHeader.setUpdateDatetime(jdShippingInfo.getUpdatedDatetime());
      shippingHeader.setDeliveryRemark(jdShippingInfo.getDeliveryRemark());
      shippingHeader.setShippingCharge(StringUtil.coalesce(jdShippingInfo.getShippingCharge().toString(), ""));
      shippingHeader.setShippingChargeTaxType(StringUtil.coalesce(jdShippingInfo.getShippingChargeTaxType().toString(), ""));
      // 出荷情報明細部の値を設定
      List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();
      for (JdShippingDetail jdShippingDetailInfo : shippingContainer.getJdShippingDetails()) {
        ShippingDetailBean shippingDetail = new ShippingDetailBean();
        shippingDetail.setShippingDetailSkuCode(jdShippingDetailInfo.getSkuCode());
        // 商品関連情報は受注管理明細より取得
        JdOrderDetail jdOrderDetail = getJdOrderDetail(orderContainer.getJdOrderDetails(), jdShippingDetailInfo.getShopCode(), jdShippingDetailInfo.getSkuCode());
        // 出荷明細に関連付いている受注管理明細が存在しなかった場合エラー
        if (jdOrderDetail == null) {
          Logger logger = Logger.getLogger(this.getClass());
          logger.error(Messages.log("web.action.back.order.OrderDetailInitAction.2") + jdShippingDetailInfo.getShopCode()
              + Messages.log("web.action.back.order.OrderDetailInitAction.3") + jdShippingDetailInfo.getSkuCode()
              + Messages.log("web.action.back.order.OrderDetailInitAction.4"));
          throw new RuntimeException(Messages.getString("web.action.back.order.OrderDetailInitAction.5"));
        }

        shippingDetail.setShippingDetailCommodityName(jdOrderDetail.getCommodityName());
        shippingDetail.setShippingDetailStandardDetail1Name(jdOrderDetail.getStandardDetail1Name());
        shippingDetail.setShippingDetailStandardDetail2Name(jdOrderDetail.getStandardDetail2Name());
        shippingDetail.setShippingDetailPurchasingAmount(jdShippingDetailInfo.getPurchasingAmount().toString());
        shippingDetail.setShippingDetailSalesPrice(jdOrderDetail.getRetailPrice().toString());
        shippingDetail.setShippingDetailCommodityTaxType(jdOrderDetail.getCommodityTaxType().toString());

        shippingDetail.setShippingDetailGiftCode(jdShippingDetailInfo.getGiftCode());
        if (StringUtil.isNullOrEmpty(jdShippingDetailInfo.getGiftName())) {
          shippingDetail.setShippingDetailGiftName(Messages.log("web.action.back.order.OrderDetailInitAction.7"));
        } else {
          shippingDetail.setShippingDetailGiftName(jdShippingDetailInfo.getGiftName());
        }
        shippingDetail.setShippingDetailGiftPrice(jdShippingDetailInfo.getGiftPrice().toString());
        shippingDetail.setShippingDetailGiftTaxType(jdShippingDetailInfo.getGiftTaxType().toString());
        shippingDetail.setShippingDetailTotalPrice(String.valueOf(BigDecimalUtil.multiply(BigDecimalUtil.add(jdOrderDetail
            .getRetailPrice(), jdShippingDetailInfo.getGiftPrice()), Long.parseLong(shippingDetail
            .getShippingDetailPurchasingAmount()))));

        shippingDetailList.add(shippingDetail);
      }
      shippingHeader.setShippingDetailList(shippingDetailList);
      shippingList.add(shippingHeader);
    }
  }
  
  
  /**
   * ショップコードとSKUコードが一致する受注管理明細情報を返す
   * 
   * @param detailList
   * @param shopCode
   * @param skuCode
   * @return Tmall受注管理明細
   */
  private TmallOrderDetail getTmallOrderDetail(List<TmallOrderDetail> detailList, String shopCode, String skuCode) {
    for (TmallOrderDetail detail : detailList) {
      if (detail != null && detail.getShopCode().equals(shopCode) && detail.getSkuCode().equals(skuCode)) {
        return detail;
      }
    }
    return null;
  }

  private JdOrderDetail getJdOrderDetail(List<JdOrderDetail> detailList, String shopCode, String skuCode) {
    for (JdOrderDetail detail : detailList) {
      if (detail != null && detail.getShopCode().equals(shopCode) && detail.getSkuCode().equals(skuCode)) {
        return detail;
      }
    }
    return null;
  }
  
  /**
   * 订单发票情报设定 return InvoiceBean
   */
  private InvoiceBean getOrderInvoice(String orderNo, String customerOrder, OrderService orderService) {
    InvoiceBean invoiceBean = new InvoiceBean();
    // 订单发票情报查询
    OrderInvoice orderInvoice = orderService.getOrderInvoice(orderNo);

    invoiceBean.setInvoiceType(InvoiceType.USUAL.getValue());// 默认通常
    if (orderInvoice != null) {
      // 有订单发票时，【领取发票】设置为被选中
      if (StringUtil.isNotNull(orderInvoice.getCustomerName()) || StringUtil.isNotNull(orderInvoice.getCompanyName())) {
        invoiceBean.setInvoiceFlg("1");
      }

      invoiceBean.setInvoiceCommodityName(orderInvoice.getCommodityName().replace("-电器", ""));// 商品规格
      invoiceBean.setInvoiceType(NumUtil.formatNumber(orderInvoice.getInvoiceType()));// 发票类型
      if (InvoiceType.USUAL.getValue().equals(NumUtil.formatNumber(orderInvoice.getInvoiceType()))) {
        // 发票类型是通常发票
        invoiceBean.setInvoiceCustomerName(orderInvoice.getCustomerName());// 顾客名称
      } else {
        // 发票类型是增值税发票
        invoiceBean.setInvoiceCompanyName(orderInvoice.getCompanyName());// 公司名称
        invoiceBean.setInvoiceTaxpayerCode(orderInvoice.getTaxpayerCode());// 纳税人识别号
        invoiceBean.setInvoiceAddress(orderInvoice.getAddress());// 住所
        invoiceBean.setInvoiceTel(orderInvoice.getTel());// 电话号码
        invoiceBean.setInvoiceBankName(orderInvoice.getBankName());// 银行名称
        invoiceBean.setInvoiceBankNo(orderInvoice.getBankNo());// 银行支行编号
      }
    } else {
      CustomerVatInvoice customerVatInvoice = orderService.getCustomerVatInvoice(customerOrder);
      if (customerVatInvoice != null) {
        invoiceBean.setInvoiceCompanyName(customerVatInvoice.getCompanyName());
        invoiceBean.setInvoiceTaxpayerCode(customerVatInvoice.getTaxpayerCode());
        invoiceBean.setInvoiceAddress(customerVatInvoice.getAddress());
        invoiceBean.setInvoiceTel(customerVatInvoice.getTel());
        invoiceBean.setInvoiceBankName(customerVatInvoice.getBankName());
        invoiceBean.setInvoiceBankNo(customerVatInvoice.getBankNo());
      }
    }
    return invoiceBean;
  }

  // soukai add 2011/12/27 ob end
  /**
   * 出荷情報をnextBeanに設定する
   * 
   * @param shippingList
   * @param orderContainer
   */
  private void setShippingData(List<ShippingHeaderBean> shippingList, List<ShippingHeaderBean> returnShippingList,
      OrderContainer orderContainer) {
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    Collections.sort(orderContainer.getShippings(), new ShippingComparator());
    for (ShippingContainer shippingContainer : orderContainer.getShippings()) {

      // 返品データの場合、次の出荷情報へ
      if (shippingContainer.getShippingHeader().getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
        ShippingHeaderBean shippingHeader = new ShippingHeaderBean();
        // 出荷情報明細部の値を設定
        List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();

        for (ShippingDetail shippingDetailInfo : shippingContainer.getShippingDetails()) {

          ShippingDetailBean shippingDetail = new ShippingDetailBean();

          shippingDetail.setShippingDetailSkuCode(shippingDetailInfo.getSkuCode());

          // 商品関連情報は受注管理明細より取得
          OrderDetail orderDetail = getOrderDetail(orderContainer.getOrderDetails(), shippingDetailInfo.getShopCode(),
              shippingDetailInfo.getSkuCode());

          ShippingDetailComposition sdc = null;
          if (!StringUtil.isNullOrEmpty(shippingContainer.getShippingHeader().getOriginalShippingNo())) {
            sdc = orderService.getShippingDetailCompositionBean(shippingContainer.getShippingHeader().getOriginalShippingNo()
                .toString(), shippingDetailInfo.getSkuCode());
          }

          // 出荷明細に関連付いている受注管理明細が存在しなかった場合エラー

          if (orderDetail == null && sdc == null) {

            Logger logger = Logger.getLogger(this.getClass());
            logger.error(Messages.log("web.action.back.order.OrderDetailInitAction.2") + shippingDetailInfo.getShopCode()
                + Messages.log("web.action.back.order.OrderDetailInitAction.3") + shippingDetailInfo.getSkuCode()
                + Messages.log("web.action.back.order.OrderDetailInitAction.4"));
            throw new RuntimeException(Messages.getString("web.action.back.order.OrderDetailInitAction.5"));
          }

          if (sdc != null) {
            shippingDetail.setShippingDetailCommodityName(sdc.getCommodityName());
            shippingDetail.setShippingDetailStandardDetail1Name(sdc.getStandardDetail1Name());
            shippingDetail.setShippingDetailStandardDetail2Name(sdc.getStandardDetail2Name());
            shippingDetail.setShippingDetailPurchasingAmount(shippingDetailInfo.getPurchasingAmount().toString());
            shippingDetail.setShippingDetailSalesPrice(sdc.getRetailPrice().toString());
            shippingDetail.setShippingDetailCommodityTaxType(sdc.getCommodityTaxType().toString());
          } else {
            shippingDetail.setShippingDetailCommodityName(orderDetail.getCommodityName());
            shippingDetail.setShippingDetailStandardDetail1Name(orderDetail.getStandardDetail1Name());
            shippingDetail.setShippingDetailStandardDetail2Name(orderDetail.getStandardDetail2Name());
            shippingDetail.setShippingDetailPurchasingAmount(shippingDetailInfo.getPurchasingAmount().toString());
            shippingDetail.setShippingDetailSalesPrice(shippingDetailInfo.getRetailPrice().toString());
            shippingDetail.setShippingDetailCommodityTaxType(orderDetail.getCommodityTaxType().toString());
          }

          shippingDetail.setShippingDetailGiftCode(shippingDetailInfo.getGiftCode());
          if (StringUtil.isNullOrEmpty(shippingDetailInfo.getGiftName())) {
            shippingDetail.setShippingDetailGiftName(Messages.log("web.action.back.order.OrderDetailInitAction.7"));
          } else {
            shippingDetail.setShippingDetailGiftName(shippingDetailInfo.getGiftName());
          }
          shippingDetail.setShippingDetailGiftPrice(shippingDetailInfo.getGiftPrice().toString());
          shippingDetail.setShippingDetailGiftTaxType(shippingDetailInfo.getGiftTaxType().toString());
          if (sdc != null) {
            shippingDetail.setShippingDetailTotalPrice(String.valueOf(BigDecimalUtil.multiply(BigDecimalUtil.add(sdc
                .getRetailPrice(), shippingDetailInfo.getGiftPrice()), Long.parseLong(shippingDetail
                .getShippingDetailPurchasingAmount()))));

          } else {
            shippingDetail.setShippingDetailTotalPrice(String.valueOf(BigDecimalUtil.multiply(BigDecimalUtil.add(shippingDetailInfo
                .getRetailPrice(), shippingDetailInfo.getGiftPrice()), Long.parseLong(shippingDetail
                .getShippingDetailPurchasingAmount()))));
          }

          shippingDetailList.add(shippingDetail);
        }

        shippingHeader.setShippingDetailList(shippingDetailList);

        returnShippingList.add(shippingHeader);

        continue;
      }
      // 出荷情報ヘッダ部の設定
      ShippingHeader shippingInfo = shippingContainer.getShippingHeader();
      ShippingHeaderBean shippingHeader = new ShippingHeaderBean();
      DeliveryType delivery = shopService.getDeliveryType(shippingInfo.getShopCode(), shippingInfo.getDeliveryTypeNo());

      // 配送指定日時の設定
      DeliverySpecificationType type = DeliverySpecificationType.fromValue(delivery.getDeliverySpecificationType());
      boolean displayDeliveryAppointedDate = false;
      if (orderContainer.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        displayDeliveryAppointedDate = false;
      } else {
        if (StringUtil.hasValue(shippingInfo.getDeliveryAppointedDate())) {
          shippingHeader.setDeliveryAppointedDate(shippingInfo.getDeliveryAppointedDate());
          displayDeliveryAppointedDate = true;
        } else {
          shippingHeader.setDeliveryAppointedDate("");
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.DATE_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedDate = true;
        }
      }
      shippingHeader.setDisplayDeliveryAppointedDate(displayDeliveryAppointedDate);

      // 配送希望時間帯
      shippingHeader.setDeliveryAppointedStartTimeList(createTimeList());
      shippingHeader.setDeliveryAppointedEndTimeList(createTimeList());
      String start = NumUtil.toString(shippingInfo.getDeliveryAppointedTimeStart());
      String end = NumUtil.toString(shippingInfo.getDeliveryAppointedTimeEnd());
      boolean displayDeliveryAppointedTime = false;
      if (orderContainer.getOrderHeader().getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        displayDeliveryAppointedTime = false;
      } else {
        if (StringUtil.isNullOrEmptyAnyOf(start, end)) {
          shippingHeader.setDeliveryAppointedStartTime("");
          shippingHeader.setDeliveryAppointedEndTime("");
        } else {
          shippingHeader.setDeliveryAppointedStartTime(start);
          shippingHeader.setDeliveryAppointedEndTime(end);
          displayDeliveryAppointedTime = true;
        }
        if (type != null
            && (type.equals(DeliverySpecificationType.TIME_ONLY) || type.equals(DeliverySpecificationType.DATE_AND_TIME))) {
          displayDeliveryAppointedTime = true;
        }
      }
      shippingHeader.setDisplayDeliveryAppointedTime(displayDeliveryAppointedTime);

      shippingHeader.setShippingNo(shippingInfo.getShippingNo());
      shippingHeader.setShippingFirstName(shippingInfo.getAddressFirstName());
      shippingHeader.setShippingLastName(shippingInfo.getAddressLastName());
      shippingHeader.setShippingFirstNameKana(shippingInfo.getAddressFirstNameKana());
      shippingHeader.setShippingLastNameKana(shippingInfo.getAddressLastNameKana());
      // modify by V10-CH start
      if (StringUtil.hasValue(shippingInfo.getPhoneNumber())) {
        String[] shippingPhoneNumber = shippingInfo.getPhoneNumber().split("-");
        if (shippingPhoneNumber.length == 2) {
          shippingHeader.setShippingTel1(shippingPhoneNumber[0]);
          shippingHeader.setShippingTel2(shippingPhoneNumber[1]);
        } else if (shippingPhoneNumber.length == 3) {
          shippingHeader.setShippingTel1(shippingPhoneNumber[0]);
          shippingHeader.setShippingTel2(shippingPhoneNumber[1]);
          shippingHeader.setShippingTel3(shippingPhoneNumber[2]);
        }
      } else {
        shippingInfo.setPhoneNumber("");
      }
      shippingHeader.setMobileTel(shippingInfo.getMobileNumber());
      shippingHeader.setShippingCityCode(shippingInfo.getCityCode());
      shippingHeader.setShippingPostalCode(shippingInfo.getPostalCode());
      shippingHeader.setShippingPrefectureCode(shippingInfo.getPrefectureCode());
      shippingHeader.setOrgShippingPrefectureCode(shippingInfo.getPrefectureCode());
      shippingHeader.setShippingAddress1(shippingInfo.getAddress1());
      shippingHeader.setShippingAddress2(shippingInfo.getAddress2());
      shippingHeader.setShippingAddress3(shippingInfo.getAddress3());
      shippingHeader.setShippingAddress4(shippingInfo.getAddress4());
      shippingHeader.setAreaCode(shippingInfo.getAreaCode());
      shippingHeader.setShippingShopCode(shippingInfo.getShopCode());
      shippingHeader.setShippingDirectDate(DateUtil.toDateString(shippingInfo.getShippingDirectDate()));
      shippingHeader.setShippingDate(DateUtil.toDateString(shippingInfo.getShippingDate()));
      shippingHeader.setShippingShopName(getShopName(shippingInfo.getShopCode()));
      shippingHeader.setShippingTypeCode(NumUtil.toString(shippingInfo.getDeliveryTypeNo()));
      shippingHeader.setShippingTypeName(shippingInfo.getDeliveryTypeName());
      shippingHeader.setUpdateDatetime(shippingInfo.getUpdatedDatetime());

      shippingHeader.setDeliveryRemark(shippingInfo.getDeliveryRemark());
      shippingHeader.setShippingCharge(StringUtil.coalesce(shippingInfo.getShippingCharge().toString(), ""));
      shippingHeader.setShippingChargeTaxType(StringUtil.coalesce(shippingInfo.getShippingChargeTaxType().toString(), ""));

      shippingHeader.setDeliveryCompanyName(shippingInfo.getDeliveryCompanyName());
      shippingHeader.setDeliveryCompanyNo(shippingInfo.getDeliveryCompanyNo());
      // 出荷情報明細部の値を設定
      List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();

      for (ShippingDetail shippingDetailInfo : shippingContainer.getShippingDetails()) {

        ShippingDetailBean shippingDetail = new ShippingDetailBean();

        shippingDetail.setShippingDetailSkuCode(shippingDetailInfo.getSkuCode());

        // 商品関連情報は受注管理明細より取得
        OrderDetail orderDetail = getOrderDetail(orderContainer.getOrderDetails(), shippingDetailInfo.getShopCode(),
            shippingDetailInfo.getSkuCode());

        // 出荷明細に関連付いている受注管理明細が存在しなかった場合エラー

        if (orderDetail == null) {
          Logger logger = Logger.getLogger(this.getClass());
          logger.error(Messages.log("web.action.back.order.OrderDetailInitAction.2") + shippingDetailInfo.getShopCode()
              + Messages.log("web.action.back.order.OrderDetailInitAction.3") + shippingDetailInfo.getSkuCode()
              + Messages.log("web.action.back.order.OrderDetailInitAction.4"));
          throw new RuntimeException(Messages.getString("web.action.back.order.OrderDetailInitAction.5"));
        }

        shippingDetail.setShippingDetailCommodityName(orderDetail.getCommodityName());
        shippingDetail.setShippingDetailStandardDetail1Name(orderDetail.getStandardDetail1Name());
        shippingDetail.setShippingDetailStandardDetail2Name(orderDetail.getStandardDetail2Name());
        shippingDetail.setShippingDetailPurchasingAmount(shippingDetailInfo.getPurchasingAmount().toString());
        shippingDetail.setShippingDetailSalesPrice(shippingDetailInfo.getRetailPrice().toString());
        shippingDetail.setShippingDetailCommodityTaxType(orderDetail.getCommodityTaxType().toString());

        shippingDetail.setShippingDetailGiftCode(shippingDetailInfo.getGiftCode());
        if (StringUtil.isNullOrEmpty(shippingDetailInfo.getGiftName())) {
          shippingDetail.setShippingDetailGiftName(Messages.log("web.action.back.order.OrderDetailInitAction.7"));
        } else {
          shippingDetail.setShippingDetailGiftName(shippingDetailInfo.getGiftName());
        }
        shippingDetail.setShippingDetailGiftPrice(shippingDetailInfo.getGiftPrice().toString());
        shippingDetail.setShippingDetailGiftTaxType(shippingDetailInfo.getGiftTaxType().toString());
        shippingDetail.setShippingDetailTotalPrice(String.valueOf(BigDecimalUtil.multiply(BigDecimalUtil.add(shippingDetailInfo
            .getRetailPrice(), shippingDetailInfo.getGiftPrice()), Long.parseLong(shippingDetail
            .getShippingDetailPurchasingAmount()))));

        shippingDetailList.add(shippingDetail);
      }

      shippingHeader.setShippingDetailList(shippingDetailList);

      shippingList.add(shippingHeader);
    }
  }

  private List<CodeAttribute> createTimeList() {
    List<CodeAttribute> values = new ArrayList<CodeAttribute>();
    values.add(new NameValue("--", ""));
    for (int i = 0; i <= 23; i++) {
      String year = Integer.toString(i);
      String dispYear = year;
      if (year.length() == 1) {
        dispYear = "0" + year;
      }
      values.add(new NameValue(dispYear, year));
    }
    return values;
  }

  /**
   * ショップコードからショップ名を取得する<BR>
   * 取得したショップ名はマップに入れておきDBへの連続アクセスを回避する<BR>
   * 
   * @param shopCode
   * @return ショップ名
   */
  private String getShopName(String shopCode) {
    String shopName = shopNameMap.get(shopCode);
    if (StringUtil.isNullOrEmpty(shopName)) {
      ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
      Shop shop = shopService.getShop(shopCode);
      if (shop != null) {
        shopName = shop.getShopName();
      } else {
        shopName = "";
      }
      shopNameMap.put(shopCode, shopName);
    }
    return shopName;
  }

  /**
   * ショップコードとSKUコードが一致する受注管理明細情報を返す
   * 
   * @param detailList
   * @param shopCode
   * @param skuCode
   * @return 受注管理明細
   */
  private OrderDetail getOrderDetail(List<OrderDetail> detailList, String shopCode, String skuCode) {
    for (OrderDetail detail : detailList) {
      if (detail != null && detail.getShopCode().equals(shopCode) && detail.getSkuCode().equals(skuCode)) {
        return detail;
      }
    }
    return null;
  }

  private static class TmallShippingComparator implements Comparator<ShippingContainer>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(ShippingContainer o1, ShippingContainer o2) {
      return o1.getTmallShippingNo().compareTo(o2.getTmallShippingNo());
    }
  }
  
  private static class JdShippingComparator implements Comparator<ShippingContainer>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(ShippingContainer o1, ShippingContainer o2) {
      return o1.getJdShippingNo().compareTo(o2.getJdShippingNo());
    }
  }

  private static class ShippingComparator implements Comparator<ShippingContainer>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(ShippingContainer o1, ShippingContainer o2) {
      return o1.getShippingNo().compareTo(o2.getShippingNo());
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderDetailInitAction.6");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102022003";
  }

}
