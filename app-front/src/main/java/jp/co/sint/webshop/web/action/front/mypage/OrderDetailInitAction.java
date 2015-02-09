package jp.co.sint.webshop.web.action.front.mypage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CustomerCancelableFlg;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeCvs;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.PaymentProviderManager;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean.OrderDetailCustomerBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean.OrderDetailHistoryCommodityBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean.OrderDetailHistoryDeliveryBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean.OrderDetailInvoiceBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean.OrderDetailPaymentBean;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean.ReturnCommodityBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

import org.apache.log4j.Logger;

/**
 * U2030620:注文内容のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailInitAction extends WebFrontAction<OrderDetailBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length < 1) {
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    FrontLoginInfo login = getLoginInfo();
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    // 顧客存在チェック
    if (customerService.isNotFound(login.getCustomerCode()) || customerService.isInactive(login.getCustomerCode())) {
      setNextUrl("/app/common/index");
      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    String orderNo = getRequestParameter().getPathArgs()[0];
    // 受注番号チェック
    getBean().setOrderNo(orderNo);
    ValidationSummary validateCustomer = BeanValidator.partialValidate(getBean(), "orderNo");
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.front.mypage.OrderDetailInitAction.0")));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }

    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    OrderContainer orderContainer = orderService.getOrder(orderNo);

    if (orderContainer.getOrderHeader() == null
        || !login.getCustomerCode().equals(orderContainer.getOrderHeader().getCustomerCode())) {
      setNextUrl("/app/common/index");
      setRequestBean(getBean());
      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }
    OrderSummary orderSummary = orderService.getOrderSummary(orderNo);
    OrderDetailBean bean = new OrderDetailBean();
    // お客様情報セット
    OrderDetailCustomerBean customer = new OrderDetailCustomerBean();
    // 20120201 ysy add start
    OrderDetailInvoiceBean invoice = new OrderDetailInvoiceBean();
    if(orderContainer.getOrderInvoice() != null){
    	 invoice.setAddress(orderContainer.getOrderInvoice().getAddress());
    	 invoice.setBankName(orderContainer.getOrderInvoice().getBankName());
    	 invoice.setCustomerName(orderContainer.getOrderInvoice().getCustomerName());
    	 invoice.setBankNo(orderContainer.getOrderInvoice().getBankNo());
    	 invoice.setCommodityName(orderContainer.getOrderInvoice().getCommodityName());
    	 invoice.setCompanyName(orderContainer.getOrderInvoice().getCompanyName());
    	 invoice.setInvoiceType(orderContainer.getOrderInvoice().getInvoiceType());
    	 invoice.setTaxpayerCode(orderContainer.getOrderInvoice().getTaxpayerCode());
    	 invoice.setTel(orderContainer.getOrderInvoice().getTel());
    }
    // 20120201 ysy add end
    CustomerInfo cust = customerService.getCustomer(orderContainer.getOrderHeader().getCustomerCode());
    customer.setPostalCode(orderContainer.getOrderHeader().getPostalCode());
    customer.setAddress1(orderContainer.getOrderHeader().getAddress1());
    customer.setAddress2(orderContainer.getOrderHeader().getAddress2());
    customer.setAddress3(orderContainer.getOrderHeader().getAddress3());
    customer.setAddress4(orderContainer.getOrderHeader().getAddress4());
    customer.setFirstName(orderContainer.getOrderHeader().getFirstName());
    customer.setLastName(orderContainer.getOrderHeader().getLastName());
    customer.setEmail(cust.getCustomer().getEmail());
    customer.setPhoneNumber(orderContainer.getOrderHeader().getPhoneNumber());
    customer.setMobileNumber(orderContainer.getOrderHeader().getMobileNumber());
    bean.setInvoice(invoice);
    bean.setCustomer(customer);
    bean.setMessage(orderContainer.getOrderHeader().getMessage());
    bean.setOrderDatetime(DateUtil.toDateString(orderContainer.getOrderHeader().getOrderDatetime()));
    bean.setOrderStatus(OrderStatus.fromValue(orderContainer.getOrderHeader().getOrderStatus()).getName());
    // 受注情報セット
    bean.setTotoalCouponPrice(NumUtil.toString(orderContainer.getOrderHeader().getCouponPrice()));
    bean.setGiftCardUsePrice(orderContainer.getOrderHeader().getGiftCardUsePrice().toString());
    if (orderContainer.getOrderHeader().getOuterCardPrice() == null ) {
      bean.setOuterCardUsePrice(BigDecimal.ZERO.toString());
    } else {
      bean.setOuterCardUsePrice(orderContainer.getOrderHeader().getOuterCardPrice().toString());
    }
    bean.setOrderNo(orderContainer.getOrderHeader().getOrderNo());
    bean.setCommodityTotalPrice(NumUtil.toString(orderSummary.getRetailPrice()));
    bean.setShippingChargeSum(NumUtil.toString(orderSummary.getShippingCharge()));
    bean.setDiscountPrice(NumUtil.toString(orderSummary.getDiscountPrice())); 
    bean.setPaymentCommissionSum(NumUtil.toString(orderContainer.getOrderHeader().getPaymentCommission()));
    bean.setUsedPointSum(NumUtil.toString(orderSummary.getUsedPoint()));
    bean.setPaymentDate(DateUtil.toDateString(orderContainer.getOrderHeader().getPaymentDate()));
    // お支払い情報のセット
    OrderDetailPaymentBean payment = new OrderDetailPaymentBean();
    payment.setPaymentMethodNo(orderContainer.getOrderHeader().getPaymentMethodType());
    payment.setPaymentMethodName(orderContainer.getOrderHeader().getPaymentMethodName());
    if (StringUtil.hasValue(orderContainer.getOrderHeader().getCvsCode())) {
      CashierPaymentTypeBase cashier = new CashierPaymentTypeCvs();
      List<CodeAttribute> cvsList = PaymentProviderManager.getCodeList(cashier);
      String cvsName = "";
      for (CodeAttribute cvs : cvsList) {
        if (cvs.getValue().equals(orderContainer.getOrderHeader().getCvsCode())) {
          cvsName = cvs.getName();
        }
      }
      payment.setCvsName(cvsName);
    }
    // 未取消 && 未支付  && alipay とnetbank
    if(orderSummary.getOrderStatus()!= Long.parseLong(OrderStatus.CANCELLED.getValue()) &&
        orderSummary.getPaymentStatus() == Long.parseLong(PaymentStatus.NOT_PAID.getValue()) && 
        (orderSummary.getPaymentMethodType().equals(PaymentMethodType.ALIPAY.getValue()) 
        || orderSummary.getPaymentMethodType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())
        || orderSummary.getPaymentMethodType().equals(PaymentMethodType.OUTER_CARD.getValue())
        || orderSummary.getPaymentMethodType().equals(PaymentMethodType.INNER_CARD.getValue()))) {
      payment.setPaymentFlg(true);
    } else {
      payment.setPaymentFlg(false);
    }
    // add by lc 2012-08-07 start 直接支付跳转Bean准备
    bean.setPaymentFormObject(getPaymentFormObject(orderContainer));
    if (PaymentMethodType.ALIPAY.getValue().equals(orderContainer.getOrderHeader().getPaymentMethodType())) {
      bean.setDisplayAlipayInfo(true);
    } else if (PaymentMethodType.CHINA_UNIONPAY.getValue().equals(orderContainer.getOrderHeader().getPaymentMethodType()) 
        || PaymentMethodType.OUTER_CARD.getValue().equals(orderContainer.getOrderHeader().getPaymentMethodType())
        || PaymentMethodType.INNER_CARD.getValue().equals(orderContainer.getOrderHeader().getPaymentMethodType())) {
      bean.setDisplayChinapayInfo(true);
    }
    // add by lc 2012-08-07 end    
    //    M17N 10361 追加 ここまで
    payment.setReceiptNo(orderContainer.getOrderHeader().getPaymentReceiptNo());
    payment.setReceiptUrl(orderContainer.getOrderHeader().getPaymentReceiptUrl());
    payment.setPaymentLimitDate(DateUtil.toDateString(orderContainer.getOrderHeader().getPaymentLimitDate()));
    payment.setPaymentCommission(NumUtil.toString(orderContainer.getOrderHeader().getPaymentCommission()));
    bean.setPayment(payment);
    // 出荷情報セット

    List<ShippingContainer> shippingContainerList = orderContainer.getShippings();
    Collections.sort(shippingContainerList, new ShippingComparator());
    List<OrderDetailHistoryDeliveryBean> deliveryList = new ArrayList<OrderDetailHistoryDeliveryBean>();
    List<ReturnCommodityBean> returnCommodityList = new ArrayList<ReturnCommodityBean>();
    
    for (ShippingContainer shippingContainer : shippingContainerList) {
      if (shippingContainer.getShippingDetails() == null || shippingContainer.getShippingDetails().size() <= 0) {
        // 出荷明細が存在しない場合は表示しない
        continue;
      }
      ShippingHeader shippingHeader = shippingContainer.getShippingHeader();
      if (shippingHeader.getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
        // 返品の場合
        for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
          ReturnCommodityBean commodity = new ReturnCommodityBean();
          // 出荷明細に関連付いている受注明細情報を取得

          OrderDetail orderDetail = orderService.getOrderDetail(orderNo, shippingDetail.getShopCode(), shippingDetail.getSkuCode());
          
          ShippingDetailComposition sdc = null;
          if (!StringUtil.isNullOrEmpty(shippingContainer.getShippingHeader().getOriginalShippingNo())) {
            sdc = orderService.getShippingDetailCompositionBean(shippingContainer.getShippingHeader().getOriginalShippingNo()
                .toString(), shippingDetail.getSkuCode());
          }

          // 出荷明細に関連付いている受注管理明細が存在しなかった場合エラー

          if (sdc != null) {
            commodity.setShopCode(sdc.getShopCode());
            commodity.setCommodityCode(sdc.getChildSkuCode());
            commodity.setCommodityName(sdc.getCommodityName());
            commodity.setStandardDetail1Name(sdc.getStandardDetail1Name());
            commodity.setStandardDetail2Name(sdc.getStandardDetail2Name());
          }else{
            commodity.setShopCode(orderDetail.getShopCode());
            commodity.setCommodityCode(orderDetail.getCommodityCode());
            commodity.setCommodityName(orderDetail.getCommodityName());
            commodity.setStandardDetail1Name(orderDetail.getStandardDetail1Name());
            commodity.setStandardDetail2Name(orderDetail.getStandardDetail2Name());
          }
         
          commodity.setSalePrice(NumUtil.toString(shippingDetail.getRetailPrice()));
          if (StringUtil.isNullOrEmpty(shippingDetail.getGiftName())) {
            commodity.setCommodityGiftName(Messages.getString("web.action.front.mypage.OrderDetailInitAction.2"));
          } else {
            commodity.setCommodityGiftName(shippingDetail.getGiftName());
          }
          commodity.setCommodityGiftFee(NumUtil.toString(shippingDetail.getGiftPrice()));
          commodity.setCommodityAmount(NumUtil.toString(shippingDetail.getPurchasingAmount()));
          if (sdc != null) {
            commodity.setIntermediateTotal(NumUtil.toString(BigDecimalUtil.multiply(shippingDetail.getRetailPrice().add(
                shippingDetail.getGiftPrice()), shippingDetail.getPurchasingAmount())));
            commodity.setSkuCode(sdc.getChildSkuCode());
          }else{
            commodity.setIntermediateTotal(NumUtil.toString(BigDecimalUtil.multiply(shippingDetail.getRetailPrice().add(
                shippingDetail.getGiftPrice()), shippingDetail.getPurchasingAmount())));
            commodity.setSkuCode(orderDetail.getSkuCode());
          }

          returnCommodityList.add(commodity);
        }
        
        continue;
      }
      OrderDetailHistoryDeliveryBean delivery = new OrderDetailHistoryDeliveryBean();
      setOrderDetailHistoryDeliveryProperties(shippingHeader, delivery);
      // アドレス帳情報の取得
      CustomerAddress address = customerService.getCustomerAddress(login.getCustomerCode(), shippingContainer.getShippingHeader()
          .getAddressNo());
      String addressAlias = "";
      if (address == null) {
        addressAlias = MessageFormat.format(Messages.getString("web.action.front.mypage.OrderDetailInitAction.1"), shippingHeader
            .getAddressLastName(), shippingHeader.getAddressFirstName());
        delivery.setAddressLink(false);
      } else {
        addressAlias = address.getAddressAlias();
        delivery.setAddressLink(true);
      }
      delivery.setAddressAlias(addressAlias);
      // 出荷明細情報セット

      List<OrderDetailHistoryCommodityBean> commodityList = new ArrayList<OrderDetailHistoryCommodityBean>();
      for (ShippingDetail shippingDetail : shippingContainer.getShippingDetails()) {
        if(!shippingDetail.getCommodityType().equals(CommodityType.PROPAGANDA.longValue())) {
          OrderDetailHistoryCommodityBean commodity = new OrderDetailHistoryCommodityBean();
          OrderDetail orderDetail = orderService.getOrderDetail(orderNo, shippingDetail.getShopCode(), shippingDetail.getSkuCode());
          
          commodity.setShopCode(orderDetail.getShopCode());
          commodity.setCommodityCode(orderDetail.getCommodityCode());
          commodity.setCommodityName(orderDetail.getCommodityName());
          commodity.setStandardDetail1Name(orderDetail.getStandardDetail1Name());
          commodity.setStandardDetail2Name(orderDetail.getStandardDetail2Name());
          commodity.setSalePrice(NumUtil.toString(shippingDetail.getRetailPrice()));
          if (StringUtil.isNullOrEmpty(shippingDetail.getGiftName())) {
            commodity.setCommodityGiftName(Messages.getString("web.action.front.mypage.OrderDetailInitAction.2"));
          } else {
            commodity.setCommodityGiftName(shippingDetail.getGiftName());
          }
          commodity.setCommoditySet(displaySetButton(orderDetail.getShopCode(), orderDetail.getCommodityCode(),orderDetail.getSetCommodityFlg()));
          commodity.setCommodityGiftFee(NumUtil.toString(shippingDetail.getGiftPrice()));
          commodity.setCommodityAmount(NumUtil.toString(shippingDetail.getPurchasingAmount()));
          commodity.setIntermediateTotal(NumUtil.toString(BigDecimalUtil.multiply(shippingDetail.getRetailPrice().add(
              shippingDetail.getGiftPrice()), shippingDetail.getPurchasingAmount())));
          commodity.setSkuCode(orderDetail.getSkuCode());
          commodity.setReturnItemType(shippingHeader.getReturnItemType());
          commodity.setGiftFlg(CommodityType.GIFT.longValue().equals(orderDetail.getCommodityType()));
          commodityList.add(commodity);
        }
      }
      delivery.setCommodityList(commodityList);
      deliveryList.add(delivery);

    }
    BigDecimal grandTotalPrice = BigDecimalUtil.addAll(orderSummary.getRetailPrice(), orderSummary.getShippingCharge(),
        orderSummary.getGiftPrice());
    grandTotalPrice = grandTotalPrice.add(NumUtil.parse(bean.getPayment().getPaymentCommission()));
    grandTotalPrice = grandTotalPrice.add(NumUtil.parse(bean.getOuterCardUsePrice()));
    bean.setGrandTotalPrice(NumUtil.toString(grandTotalPrice));
    if (BigDecimalUtil.isAbove(BigDecimal.ZERO, NumUtil.parse(bean.getGrandTotalPrice()))) {
      bean.setGrandTotalPrice(NumUtil.toString(BigDecimal.ZERO));
    }
    if (BigDecimalUtil.isAbove(NumUtil.parse(bean.getGrandTotalPrice()), orderSummary.getUsedPoint().divide(
        DIContainer.getWebshopConfig().getRmbPointRate(), PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR))) {
      bean.setOrderTotalPrice(NumUtil.toString(BigDecimalUtil.subtract(BigDecimalUtil.subtract(NumUtil.parse(bean.getGrandTotalPrice()), BigDecimalUtil
          .divide(orderSummary.getUsedPoint(),DIContainer.getWebshopConfig().getRmbPointRate(), PointUtil.getAcquiredPointScale(),  
              RoundingMode.FLOOR)),NumUtil.parse(bean.getTotoalCouponPrice()))));
    }
    if (BigDecimalUtil.isAbove(NumUtil.parse(bean.getDiscountPrice()),BigDecimal.ZERO)) {
        bean.setOrderTotalPrice(NumUtil.toString(BigDecimalUtil.subtract(NumUtil.parse(bean.getOrderTotalPrice()),
        		NumUtil.parse(bean.getDiscountPrice()))));
      }
    if (BigDecimalUtil.isAbove(BigDecimal.ZERO, NumUtil.parse(bean.getOrderTotalPrice()))) {
      bean.setOrderTotalPrice(BigDecimal.ZERO.toString());
    }
    bean.setOrderTotalPrice(NumUtil.toString(BigDecimalUtil.subtract(NumUtil.parse(bean.getOrderTotalPrice()),
        NumUtil.parse(bean.getGiftCardUsePrice()))));
    bean.setDeliverylist(deliveryList);
    bean.setReturnCommodityList(returnCommodityList);
    bean.setTotalGiftPrice(NumUtil.toString(orderSummary.getGiftPrice()));
    bean.setAcquiredPointSum(NumUtil.toString(orderSummary.getAcquiredPoint()));
    bean.setUpdatedDatetime(orderContainer.getOrderHeader().getUpdatedDatetime());
    if (!BigDecimalUtil.equals(orderSummary.getUsedPoint(), BigDecimal.ZERO)) {
      bean.setUsedPointSumDisplayFlg(true);
    }
    if (!BigDecimalUtil.equals(orderSummary.getAcquiredPoint(), BigDecimal.ZERO)) {
      bean.setAcquiredPointSumDisplayFlg(true);
    }
    if (!BigDecimalUtil.equals(orderSummary.getAcquiredPoint(), BigDecimal.ZERO)) {
      bean.setAcquiredPointSumDisplayFlg(true);
    }
    if (!BigDecimalUtil.equals(orderSummary.getCouponPrice(), BigDecimal.ZERO)) {
      bean.setUsedCouponSumDisplayFlg(true);
    }
    if (orderContainer.getOrderHeader().getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
      bean.setNotPointInFull(false);
    } else {
      bean.setNotPointInFull(true);
    }
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  private void setOrderDetailHistoryDeliveryProperties(ShippingHeader shippingHeader, OrderDetailHistoryDeliveryBean delivery) {
	OrderService order = ServiceLocator.getOrderService(getLoginInfo());
    delivery.setShippingNo(shippingHeader.getShippingNo());
    delivery.setAddressNo(NumUtil.toString(shippingHeader.getAddressNo()));
    delivery.setAddressLastName(shippingHeader.getAddressLastName());
    delivery.setAddressFirstName(shippingHeader.getAddressFirstName());
    delivery.setDeliveryTypeName(shippingHeader.getDeliveryTypeName());

    delivery.setPostalCode(shippingHeader.getPostalCode());
    delivery.setAddress1(shippingHeader.getAddress1());
    delivery.setAddress2(shippingHeader.getAddress2());
    delivery.setAddress3(shippingHeader.getAddress3());
    delivery.setAddress4(shippingHeader.getAddress4());
    delivery.setDeliveryAppointedDate(shippingHeader.getDeliveryAppointedDate());
    delivery.setDeliveryAppointedStartTime(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeStart()));
    delivery.setDeliveryAppointedEndTime(NumUtil.toString(shippingHeader.getDeliveryAppointedTimeEnd()));
    delivery.setDeliveryRemark(shippingHeader.getDeliveryRemark());
    String deliveryShipNoArray = order.getDeliveryShipNoArray(shippingHeader.getShippingNo());
    if (deliveryShipNoArray == null){
    	delivery.setDeliverySlipNo("");
    } else {
    	delivery.setDeliverySlipNo(deliveryShipNoArray);
    }
    delivery.setShippingCharge(NumUtil.toString(shippingHeader.getShippingCharge()));
    delivery.setPhoneNumber(shippingHeader.getPhoneNumber());
    delivery.setMobileNumber(shippingHeader.getMobileNumber());
    delivery.setShippingDate(DateUtil.toDateString(shippingHeader.getShippingDate()));

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = shopService.getShop(shippingHeader.getShopCode());
    delivery.setShopName(shop.getShopName());

    DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
    DeliveryCompany deliveryCompany = dao.load(shippingHeader.getDeliveryCompanyNo());
    
    if(deliveryCompany != null){
      delivery.setDeliverySlipName(deliveryCompany.getDeliveryCompanyName());
      delivery.setDeliveryPercelUrl(deliveryCompany.getDeliveryCompanyUrl());
    }else{
      delivery.setDeliverySlipName(shippingHeader.getDeliveryCompanyName());
    }
    
    
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderDetailBean bean = (OrderDetailBean) getRequestBean();
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    if (StringUtil.hasValue(bean.getOrderNo())) {
      OrderSummary orderSummary = service.getOrderSummary(bean.getOrderNo());
      OrderHeader orderHeader = service.getOrderHeader(bean.getOrderNo());
      if (orderSummary == null || orderHeader == null) {
        return;
      }

      // キャンセルボタンの表示

      ShopManagementService shopSv = ServiceLocator.getShopManagementService(getLoginInfo());
      Shop shop = shopSv.getShop(orderHeader.getShopCode());
      if (shop.getCustomerCancelableFlg().equals(CustomerCancelableFlg.ENABLED.longValue())
          && orderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue())
          && orderSummary.getShippingStatusSummary().equals(ShippingStatusSummary.NOT_SHIPPED.longValue())
          && DataTransportStatus.fromValue(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.NOT_TRANSPORTED)) {
        bean.setCancelButtonDisplayFlg(true);
      } else {
        bean.setCancelButtonDisplayFlg(false);
      }
    }

    // キャンセル完了メッセージの表示

    if (getRequestParameter().getPathArgs().length > 1) {
      if (getRequestParameter().getPathArgs()[1].equals("cancel")) {
        addInformationMessage(WebMessage.get(CompleteMessage.CANCEL_COMPLETE, Messages
            .getString("web.action.front.mypage.OrderDetailInitAction.0")));
      }
    }

    setRequestBean(bean);
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
  
  private Object getPaymentFormObject(OrderContainer container) {
    Object result = null;
    OrderHeader oh = container.getOrderHeader();
    PaymentMethodType pmt = PaymentMethodType.fromValue(oh.getPaymentMethodType());
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    if (pmt == PaymentMethodType.ALIPAY) {
      result = service.getAlipayBean(container);

    } else if (pmt == PaymentMethodType.CHINA_UNIONPAY || pmt == PaymentMethodType.OUTER_CARD || pmt == PaymentMethodType.INNER_CARD) {
      result = service.getChinapayBean(container);
    }
    return result;
  }
}
