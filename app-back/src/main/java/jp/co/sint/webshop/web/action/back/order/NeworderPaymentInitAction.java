package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerCouponInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean.CouponBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean.UsePointBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020150:新規受注(決済方法指定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderPaymentInitAction extends NeworderPaymentBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NeworderPaymentBean bean = createPaymentBean();
    Cashier cashier = bean.getCashier();

    // add by V10-CH start
    SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(ServiceLoginInfo.getInstance());
    CouponRule cr = siteManagementService.getCouponRule();
    Long couponEnabledFlg = siteManagementService.getCouponRule().getCouponFunctionEnabledFlg();
    BigDecimal couponTotalPrice = BigDecimal.ZERO;
    if (couponEnabledFlg == 1
        && BigDecimalUtil.isBelowOrEquals(cr.getCouponInvestPurchasePrice(), cashier.getTotalCommodityPrice())) {
      OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
      List<CustomerCouponInfo> couponInfo = orderService.getPaymentCouponList(cashier.getCustomer().getCustomerCode());
      List<CouponBean> cbl = new ArrayList<CouponBean>();
      for (CustomerCouponInfo cci : couponInfo) {
        CouponBean cb = new CouponBean();
        cb.setCouponName(cci.getCouponName());
        cb.setCouponPrice(cci.getCouponPrice());
        cb.setUseEndDate(DateUtil.toDateString(cci.getUseCouponEndDate()));
        cb.setCustomerCouponId(NumUtil.toString(cci.getCustomerCouponId()));
        if (cashier.getCustomerCouponId() != null) {
          for (CustomerCoupon customerCouponId : cashier.getCustomerCouponId()) {
            if (StringUtil.hasValueAllOf(NumUtil.toString(cci.getCustomerCouponId()), NumUtil.toString(customerCouponId
                .getCustomerCouponId()))
                && NumUtil.toString(cci.getCustomerCouponId()).equals(NumUtil.toString(customerCouponId.getCustomerCouponId()))) {
              cb.setSelectCouponId(NumUtil.toString(customerCouponId.getCustomerCouponId()));
              couponTotalPrice = couponTotalPrice.add(NumUtil.parse(cci.getCouponPrice()));
            }
          }
        }
        cbl.add(cb);
        bean.setCouponFunctionEnabledFlg(NumUtil.toString(couponEnabledFlg));
        cashier.setUsableCoupon(true);
      }
      bean.setCouponList(cbl);
      bean.setCouponTotalPrice(NumUtil.toString(couponTotalPrice));
    } else {
      cashier.setUsableCoupon(false);
    }
    bean.setPaymentPrice(PointUtil.getTotalPyamentPrice(BigDecimalUtil.subtract(bean.getTotalPrice(), NumUtil.parse(bean
        .getCouponTotalPrice())), NumUtil.parse(bean.getCashier().getUsePoint())));
    // add by V10-CH end

    // 支払方法関連情報の取得
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
    // bean.setPayment(PaymentSupporterFactory.createPaymentSuppoerter().createPaymentMethodBean(cashier.getPayment(),
    // bean.getPaymentPrice(),
    // NumUtil.parse(bean.getUsePointBean().getUsePoint()), hasOtherShipping,
    // NumUtil.parse(bean.getCouponTotalPrice())));
    bean.setPayment(PaymentSupporterFactory.createPaymentSuppoerter().createPaymentMethodBean(cashier.getPayment(),
        bean.getPaymentPrice(), NumUtil.parse(bean.getUsePointBean().getUsePoint()), hasOtherShipping,
        NumUtil.parse(bean.getCouponTotalPrice()), ""));
    // 20120113 shen update end

    if (bean.getPayment().getDisplayPaymentList().size() < 1) {
      // 全額ポイント払いが可能かどうかを検証
      SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
      PointRule pointRule = service.getPointRule();
      if (pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())) {
        // if (bean.getCashier().getCustomer().getRestPoint() >=
        // bean.getCashier().getGrandTotalPrice()) {
        // modify by V10-CH start
        if (BigDecimalUtil.isAboveOrEquals(getBean().getCashier().getCustomer().getRestPoint(), getBean().getCashier()
            .getGrandTotalPrice())) {
          // modify by V10-CH end
          UsePointBean pointInfo = getBean().getUsePointBean();
          pointInfo.setUsePoint(NumUtil.toString(getBean().getTotalPrice()));
          addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
          bean.setDisplayPointButton(false);
          bean.setDisplayPointEditMode(WebConstantCode.DISPLAY_HIDDEN);

          createCashierFromDisplay();

          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      addErrorMessage(WebMessage.get(OrderErrorMessage.NO_PAYMENT_METHOD_ERROR));
    } else if (bean.getPayment().getDisplayPaymentList().size() == 1) {
      if (bean.getPayment().getDisplayPaymentList().get(0).getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())) {
        bean.setDisplayPointButton(false);
        bean.setDisplayPointEditMode(WebConstantCode.DISPLAY_HIDDEN);

        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    bean.setDisplayPointButton(true);
    bean.setDisplayPointEditMode(WebConstantCode.DISPLAY_EDIT);

    bean.setMessage(cashier.getMessage());
    bean.setCaution(cashier.getCaution());

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderPaymentInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102015002";
  }

}
