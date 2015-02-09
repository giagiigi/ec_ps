package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean.CouponBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean.UsePointBean;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

/**
 * U1020150:新規受注(決済方法指定)の基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class NeworderPaymentBaseAction extends NeworderBaseAction<NeworderPaymentBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    if (getBean().getCashier() == null) {
      auth = false;
    }
    return auth;
  }

  /**
   * 新規受注(決済方法指定)のbeanを作成します。
   * 
   * @return 新規受注(決済方法指定)のbean
   */
  public NeworderPaymentBean createPaymentBean() {
    // 使用ポイント表示部情報の取得
    NeworderPaymentBean bean = getBean();
    UsePointBean pointInfo = bean.getUsePointBean();
    Cashier cashier = getBean().getCashier();

    BigDecimal commodityPrice = cashier.getTotalCommodityPrice();
    BigDecimal giftPrice = cashier.getTotalGiftPrice();
    BigDecimal shippingPrice = cashier.getTotalShippingCharge();
    bean.setCommodityTotalPrice(commodityPrice);
    bean.setGiftTotalPrice(giftPrice);
    bean.setShippingTotalPrice(shippingPrice);
    BigDecimal usePoint;
    if (cashier.isUsablePoint()) {
      usePoint = NumUtil.parse(cashier.getUsePoint());
      pointInfo.setRestPoint(cashier.getCustomer().getRestPoint());
    } else {
      // ポイントを使用しない場合ポイントに関する情報全てに0を設定
      usePoint = BigDecimal.ZERO;
      pointInfo.setRestPoint(BigDecimal.ZERO);
    }
    pointInfo.setUsePoint(NumUtil.toString(usePoint));
    BigDecimal totalAllPrice = BigDecimalUtil.addAll(BigDecimalUtil.addAll(commodityPrice, giftPrice), shippingPrice);
    bean.setTotalPrice(totalAllPrice);
    // modified by v10-cn start
    bean.setPaymentPrice(PointUtil.getTotalPyamentPrice(BigDecimalUtil.subtract(totalAllPrice, NumUtil.parse(bean
        .getCouponTotalPrice())), usePoint));

    if (BigDecimalUtil.isAbove(BigDecimal.ZERO, bean.getPaymentPrice())) {
      bean.setPaymentPrice(BigDecimal.ZERO);
    }

    BigDecimal couponTotalPrice = BigDecimal.ZERO;
    if (cashier.getCustomerCouponId() != null) {
      for (CustomerCoupon str : cashier.getCustomerCouponId()) {
        for (CouponBean couponBean : getBean().getCouponList()) {
          if (couponBean.getCustomerCouponId().equals(NumUtil.toString(str.getCustomerCouponId()))) {
            couponTotalPrice = couponTotalPrice.add(NumUtil.parse(couponBean.getCouponPrice()));
          }
        }
      }
    }
    bean.setCouponTotalPrice(NumUtil.toString(couponTotalPrice));
    cashier.setUseCoupon(NumUtil.toString(couponTotalPrice));

    BigDecimal rmbPointRate = DIContainer.getWebshopConfig().getRmbPointRate();
    bean.setAllPointPayment(BigDecimalUtil.multiply(bean.getTotalPrice(), rmbPointRate).setScale(PointUtil.getAcquiredPointScale(),
        RoundingMode.CEILING).toString());
    // modified by v10-cn end
    return bean;
  }

  /**
   * 新規受注(決済方法指定)のbeanに画面情報を設定します。
   */
  public void createCashierFromDisplay() {
    NeworderPaymentBean bean = getBean();

    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();

    // cashierに画面情報を設定
    Cashier cashier = bean.getCashier();
    cashier.setUsePoint(bean.getUsePointBean().getUsePoint());
    cashier.setPayment(supporter.createCashierPayment(bean.getPayment()));
    cashier.setMessage(bean.getMessage());
    cashier.setCaution(bean.getCaution());

    // cashierから画面情報を設定
    bean = createPaymentBean();
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
    // bean.setPayment(supporter.createPaymentMethodBean(cashier.getPayment(),
    // bean.getPaymentPrice(), NumUtil.parse(bean
    // .getUsePointBean().getUsePoint()), hasOtherShipping,
    // NumUtil.parse(bean.getCouponTotalPrice())));
    bean.setPayment(supporter.createPaymentMethodBean(cashier.getPayment(), bean.getPaymentPrice(), NumUtil.parse(bean
        .getUsePointBean().getUsePoint()), hasOtherShipping, NumUtil.parse(bean.getCouponTotalPrice()), ""));
    // 20120113 shen update end
  }

}
