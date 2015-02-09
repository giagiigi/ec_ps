package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.data.domain.DeliveryDateType;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ConfirmBean;

/**
 * U2020140:注文内容確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ConfirmInitAction extends ConfirmBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    Cashier cashier = getBean().getCashier();
    // お支払い情報が無い場合TOP画面に遷移
    if (cashier == null || cashier.getPayment() == null) {
      setNextUrl("/app/common/index");
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

    // session中赠品重置 20140725 hdh
    giftReset(getCart(), "00000000");

    ConfirmBean bean = getBean();
    Cashier cashier = bean.getCashier();
    // 10.1.4 K00171 追加 ここから
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    Set<Sku> skuSet = new HashSet<Sku>();
    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        Sku sku = new Sku(commodity.getShopCode(), commodity.getSkuCode());
        if (!skuSet.contains(sku)) {
          skuSet.add(sku);
        }

        if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg()) && commodity.getSkuCode().contains(":")) {
          commodity.setSkuCodeOfSet(commodity.getSkuCode().split(":")[0]);
        }
      }
    }
    Map<Sku, Boolean> returnPolicyMap = catalogService.getCommodityReturnPolicies(skuSet, false);
    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        Boolean hasReturnPolicy = returnPolicyMap.get(new Sku(commodity.getShopCode(), commodity.getSkuCode()));
        hasReturnPolicy = BeanUtil.coalesce(hasReturnPolicy, Boolean.FALSE);
        if (hasReturnPolicy) {
          CommodityHeader header = catalogService.getCommodityHeader(commodity.getShopCode(), commodity.getCommodityCode());
          commodity.setReturnPolicy(header.getCommodityDescriptionPc());
        }
      }
    }
    // 10.1.4 K00171 追加 ここまで
    OrderContainer container = CartUtil.createOrderContainer(cashier);

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    service.setAcquiredPoint(container);

    // 獲得ポイントの取得
    BigDecimal acquiredPoint = BigDecimal.ZERO;
    for (ShippingContainer shipping : container.getShippings()) {
      acquiredPoint = BigDecimalUtil.add(acquiredPoint, shipping.getShippingHeader().getAcquiredPoint());
    }
    bean.setTotalAcquiredPoint(acquiredPoint);
    bean.setDisplayPoint(NumUtil.parse(bean.getCashier().getUsePoint()));

    container.getOrderHeader().setOrderFlg(OrderFlg.CHECKED.longValue());
    int orderHeadOff = DIContainer.getWebshopConfig().getOrderHeadOff();
    if (cashier.getPayment().getSelectPayment().getOrderPriceThreshold() != null
        && BigDecimalUtil.isAboveOrEquals(cashier.getPaymentTotalPrice(), new BigDecimal(orderHeadOff))) {
      container.getOrderHeader().setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
    }
    if (!StringUtil.isNullOrEmpty(container.getOrderHeader().getCaution())) {
      container.getOrderHeader().setOrderFlg(OrderFlg.NOT_CHECKED.longValue());
    }

    bean.setOrder(container);

    if (container.getOrderHeader().getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
      bean.setDisplayGrandTotalPrice(cashier.getGrandTotalPrice().add(
          cashier.getPayment().getSelectPayment().getPaymentCommission()));
      bean.setPaymentPrice(PointUtil.getTotalPyamentPrice(
          BigDecimalUtil.addAll(bean.getCashier().getTotalCommodityPrice(), bean.getCashier().getTotalGiftPrice(), bean
              .getCashier().getTotalShippingCharge(), cashier.getPayment().getSelectPayment().getPaymentCommission()),
          NumUtil.parse(bean.getCashier().getUsePoint())).subtract(NumUtil.parse(bean.getCashier().getUseCoupon())));
    } else {
      bean.setDisplayGrandTotalPrice(cashier.getGrandTotalPrice());
      if (BigDecimalUtil.isAbove(BigDecimal.ZERO, bean.getDisplayGrandTotalPrice())) {
        bean.setDisplayGrandTotalPrice(BigDecimal.ZERO);
      }
      bean.setPaymentPrice(PointUtil.getTotalPyamentPrice(BigDecimalUtil.addAll(bean.getCashier().getTotalCommodityPrice(),
          bean.getCashier().getTotalGiftPrice(), bean.getCashier().getTotalShippingCharge()).subtract(
          NumUtil.parse(bean.getCashier().getUseCoupon())), NumUtil.parse(bean.getCashier().getUsePoint())));
    }
    if (container.getOrderHeader().getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
      bean.setNotPointInFull(false);
    } else {
      bean.setNotPointInFull(true);
    }

    bean.setNeedInvoice(cashier.getInvoice().getInvoiceFlg().equals(InvoiceFlg.NEED.getValue()));
    bean.setUseDiscount(BigDecimalUtil.isAbove(cashier.getDiscountPrice(), BigDecimal.ZERO));

    CashierShipping cashierShipping = cashier.getShippingList().get(0);
    if (StringUtil.hasValue(cashierShipping.getDeliveryAppointedDate())
        && !cashierShipping.getDeliveryAppointedDate().equals(DeliveryDateType.UNUSABLE.getValue())) {
      bean.setDisplayDeliveryDate(true);
    } else if (StringUtil.hasValueAnyOf(cashierShipping.getDeliveryAppointedStartTime(), cashierShipping
        .getDeliveryAppointedTimeEnd())) {
      bean.setDisplayDeliveryDate(true);
    } else {
      bean.setDisplayDeliveryDate(false);
    }
    String paymentType = container.getOrderHeader().getPaymentMethodType();
    List<PaymentMethodType> paymentTypeList = Arrays.asList(PaymentMethodType.values());
    for (PaymentMethodType paymentMethodType : paymentTypeList) {
      if (paymentType.equals(paymentMethodType.getValue())) {
        cashier.getPayment().getSelectPayment().setPaymentMethodName(paymentMethodType.getName());
      }
    }
    // 礼品卡支付方法对应
    if (getBean().getCashier().getGiftCardUseAmount() != null
        && BigDecimalUtil.isAbove(getBean().getCashier().getGiftCardUseAmount(), BigDecimal.ZERO)
        && BigDecimalUtil.equals(getBean().getCashier().getPaymentTotalPrice(), BigDecimal.ZERO)) {
      cashier.getPayment().getSelectPayment().setPaymentMethodName(PaymentMethodType.NO_PAYMENT.getName());
    }

    setRequestBean(bean);

    // 赠品重置 20140725 hdh
    // if(getBean().getCashier().get !=null &&
    // getBean().getShippingList().size()>0){
    // for(ShippingHeaderBean headerBean:getBean().getShippingList()){
    // if(headerBean.getShippingList()!=null &&
    // headerBean.getShippingList().size()>0){
    // for(ShippingDetailBean detail:headerBean.getShippingList()){
    // giftReset(detail);
    // }
    //          
    // }
    // }
    // }

    return FrontActionResult.RESULT_SUCCESS;
  }
}
