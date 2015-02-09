package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerCouponInfo;
import jp.co.sint.webshop.service.impl.CommonLogic;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeCashOnDelivery;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeCashOnDelivery.PriceCommission;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.CouponBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.PointInfoBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2020130:お支払い方法選択のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentInitAction extends PaymentBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    Cashier cashier = getBean().getCashier();
    // 10.1.4 10199 修正 ここから
    // if (cashier == null) {
    if (cashier == null || cashier.getPayment() == null) {
      // 10.1.4 10199 修正 ここまで
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
    PaymentBean bean = getBean();
    Cashier cashier = bean.getCashier();

    // add by V10-CH start
    SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(ServiceLoginInfo.getInstance());
    CouponRule cr = siteManagementService.getCouponRule();
    Long couponEnabledFlg = cr.getCouponFunctionEnabledFlg();
    cashier.setUsableCoupon(false);
    if (couponEnabledFlg == 1 && BigDecimalUtil.isBelowOrEquals(cr.getCouponInvestPurchasePrice(), cashier.getTotalCommodityPrice())) {
      OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
      FrontLoginInfo loginInfo = this.getLoginInfo();
      List<CustomerCouponInfo> couponInfo = orderService.getPaymentCouponList(loginInfo.getCustomerCode());
      List<CouponBean> cbl = new ArrayList<CouponBean>();
      for (CustomerCouponInfo cci : couponInfo) {
        CouponBean cb = new CouponBean();
        cb.setCouponName(cci.getCouponName());
        cb.setCouponPrice(cci.getCouponPrice());      
        cb.setUseEndDate(DateUtil.toDateString(cci.getUseCouponEndDate()));
        cb.setCustomerCouponId(NumUtil.toString(cci.getCustomerCouponId()));
        if (cashier.getCustomerCouponId() != null) {
          for (CustomerCoupon customerCouponId : cashier.getCustomerCouponId()) {
            if (StringUtil.hasValueAllOf(NumUtil.toString(cci.getCustomerCouponId()), NumUtil.toString(customerCouponId.getCustomerCouponId()))
                && NumUtil.toString(cci.getCustomerCouponId()).equals(NumUtil.toString(customerCouponId.getCustomerCouponId()))) {
              cb.setSelectCouponId(NumUtil.toString(customerCouponId.getCustomerCouponId()));
            }
          }
        }
        cbl.add(cb);
        cashier.setUsableCoupon(true);
        bean.setCouponFunctionEnabledFlg(NumUtil.toString(couponEnabledFlg));
      }
      bean.setCouponList(cbl);
    }
    // add by V10-CH end

    bean.setPointInfo(createPointInfoBean());

    // お支払い方法関連情報の取得
    boolean hasOtherShipping = false;
    if (cashier.getShippingList().size() > 1
        || (cashier.getShippingList().size() == 1 && !cashier.getShippingList().get(0).getAddress().getAddressNo().equals(
        // 10.1.3 10150 修正 ここから
            // CustomerConstant.SELFE_ADDRESS_NO))) {
            CustomerConstant.SELF_ADDRESS_NO))) {
      // 10.1.3 10150 修正 ここまで
      hasOtherShipping = true;
    }
    // 20120113 shen update start
    // bean.setOrderPayment(PaymentSupporterFactory.createPaymentSuppoerter().createPaymentMethodBean(cashier.getPayment(),
    //     bean.getPointInfo().getTotalAllPrice(), NumUtil.parse(bean.getPointInfo().getUsePoint()), hasOtherShipping, bean.getPointInfo().getCouponTotalPrice()));
    bean.setOrderPayment(PaymentSupporterFactory.createPaymentSuppoerter().createPaymentMethodBean(cashier.getPayment(),
        bean.getPointInfo().getTotalAllPrice(), NumUtil.parse(bean.getPointInfo().getUsePoint()), hasOtherShipping, bean.getPointInfo().getCouponTotalPrice(), ""));
    // 20120113 shen update end
    
    boolean hasCard = false;

    // 代引の場合、手数料一覧に表示される手数料に、TaxTypeに応じた値をセット
    for (PaymentTypeBase base : bean.getOrderPayment().getDisplayPaymentList()) {
      if (base.getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
        for (PriceCommission priceCommission : ((PaymentTypeCashOnDelivery) base).getCommissionList()) {
          TaxUtil tax = DIContainer.get("TaxUtil");
          Long taxRate = tax.getTaxRate();
          BigDecimal commission = NumUtil.parse(priceCommission.getCommission());

          BigDecimal commissionTax = Price.getPriceIncludingTax(commission, taxRate, String.valueOf(base
              .getPaymentCommissionTaxType()));
          priceCommission.setCommission(NumUtil.toString(commissionTax));
        }
      }
      if (base.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
        hasCard = true;
      }
    }

    if (hasCard) {
      int yyyy = Integer.parseInt(DateUtil.getYYYY(DateUtil.getSysdate()));
      List<NameValue> list = new ArrayList<NameValue>();
      list.add(new NameValue("----", ""));
      for (int i = 0; i < 11; i++) {
        list.add(new NameValue(Integer.toString(yyyy + i), Integer.toString(yyyy + i).substring(2)));
      }
      bean.setCardExpirationYearList(list);
    }

    if (bean.getOrderPayment().getDisplayPaymentList().size() < 1) {
      // 全額ポイント払いが可能かどうかを検証
      PointRule pointRule = CommonLogic.getPointRule(getLoginInfo());
      if (pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())) {
        // if (getBean().getCashier().getCustomer().getRestPoint() >=
        // getBean().getCashier().getGrandTotalPrice()) {
        if (BigDecimalUtil.isAboveOrEquals(getBean().getCashier().getCustomer().getRestPoint(), getBean().getCashier()
            .getGrandTotalPrice())) {
          PointInfoBean pointInfo = getBean().getPointInfo();
          // modify by V10-CH start
          // pointInfo.setUsePoint(NumUtil.toString(pointInfo.getTotalCommodityPrice()
          // + pointInfo.getTotalGiftPrice()
          // + pointInfo.getTotalShippingPrice()));
          pointInfo.setUsePoint(NumUtil.toString(BigDecimalUtil.addAll(pointInfo.getTotalCommodityPrice(), pointInfo
              .getTotalGiftPrice(), pointInfo.getTotalShippingPrice())));
          // modify by V10-CH end
          addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
          bean.setDisplayPointButton(false);
          bean.setDisplayPointEditMode(WebConstantCode.DISPLAY_HIDDEN);

          createCashierFromDisplay();

          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        }
      }
      addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_ERROR));
    } else if (bean.getOrderPayment().getDisplayPaymentList().size() == 1) {

      // 10.1.3 10168 10169 追加 ここから
      if (bean.getOrderPayment().getDisplayPaymentList().get(0).getPaymentMethodType().equals(
          PaymentMethodType.POINT_IN_FULL.getValue())) {
        addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
      }
      // 10.1.3 10168 10169 追加 ここまで

      if (bean.getOrderPayment().getDisplayPaymentList().get(0).getPaymentMethodType().equals(
          PaymentMethodType.NO_PAYMENT.getValue())) {
        bean.setDisplayPointButton(false);
        bean.setDisplayPointEditMode(WebConstantCode.DISPLAY_HIDDEN);

        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }
    }

    bean.setDisplayPointButton(true);
    bean.setDisplayPointEditMode(WebConstantCode.DISPLAY_EDIT);

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    PaymentBean reqBean = (PaymentBean) getRequestBean();
    for (String error : reqBean.getErrorMessages()) {
      addErrorMessage(error);
    }
    reqBean.getErrorMessages().clear();
    setRequestBean(reqBean);
  }

}
