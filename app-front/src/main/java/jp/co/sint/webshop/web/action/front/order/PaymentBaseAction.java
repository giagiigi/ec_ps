package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.CouponBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.PointInfoBean;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

/**
 * U2020130:お支払い方法選択のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class PaymentBaseAction extends OrderBaseAction<PaymentBean> {

  /**
   * ポイント情報を生成します
   * 
   * @return pointInfo
   */
  public PointInfoBean createPointInfoBean() {
    // 使用ポイント表示部情報の取得
    PointInfoBean pointInfo = new PointInfoBean();
    Cashier cashier = getBean().getCashier();

    BigDecimal commodityPrice = cashier.getTotalCommodityPrice();
    BigDecimal giftPrice = cashier.getTotalGiftPrice();
    BigDecimal shippingPrice = cashier.getTotalShippingCharge();
    pointInfo.setTotalCommodityPrice(commodityPrice);
    pointInfo.setTotalGiftPrice(giftPrice);
    pointInfo.setTotalShippingPrice(shippingPrice);

    BigDecimal couponTotalPrice = BigDecimal.ZERO;
    if (cashier.getCustomerCouponId() != null) {
      for (CustomerCoupon str : cashier.getCustomerCouponId()) {
        for (CouponBean couponBean : getBean().getCouponList()) {
          if(couponBean.getCustomerCouponId().equals(NumUtil.toString(str.getCustomerCouponId()))) {
            couponTotalPrice = couponTotalPrice.add(NumUtil.parse(couponBean.getCouponPrice()));
          }
        }
      }
    }
    pointInfo.setCouponTotalPrice(couponTotalPrice);
    cashier.setUseCoupon(NumUtil.toString(couponTotalPrice));
    
    BigDecimal usePoint = null;
    if (cashier.isUsablePoint()) {
      updateCashierPoint(cashier); // 10.1.3 10168 10169 追加
      usePoint = NumUtil.parse(cashier.getUsePoint());
      pointInfo.setRestPoint(cashier.getCustomer().getRestPoint());
    } else {
      // ポイントを使用しない場合ポイントに関する情報全てに0を設定
      usePoint = BigDecimal.ZERO;
      pointInfo.setRestPoint(BigDecimal.ZERO);
    }
    pointInfo.setUsePoint(NumUtil.toString(usePoint));
    // modified by v10-cn start
    pointInfo.setTotalAllPrice(PointUtil.getTotalPyamentPrice(BigDecimalUtil.addAll(commodityPrice, giftPrice, shippingPrice).subtract(couponTotalPrice),
        usePoint));
    
    // modified by v10-cn end
    return pointInfo;
  }

  /**
   * cashierから画面情報を設定します
   */
  public void createCashierFromDisplay() {
    PaymentBean bean = getBean();

    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();

    // cashierに画面情報を設定
    Cashier cashier = bean.getCashier();
    cashier.setUsePoint(bean.getPointInfo().getUsePoint());
    cashier.setPayment(supporter.createCashierPayment(bean.getOrderPayment()));
    
    // cashierから画面情報を設定
    bean.setPointInfo(createPointInfoBean());
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
    // bean.setOrderPayment(supporter.createPaymentMethodBean(cashier.getPayment(), bean.getPointInfo().getTotalAllPrice(), NumUtil
    //     .parse(bean.getPointInfo().getUsePoint()), hasOtherShipping, bean.getPointInfo().getCouponTotalPrice()));
    bean.setOrderPayment(supporter.createPaymentMethodBean(cashier.getPayment(), bean.getPointInfo().getTotalAllPrice(), NumUtil
        .parse(bean.getPointInfo().getUsePoint()), hasOtherShipping, bean.getPointInfo().getCouponTotalPrice(), ""));
    // 20120113 shen update end
  }

  // 10.1.3 10168 10169 追加 ここから
  private void updateCashierPoint(Cashier cashier) {
    if (cashier != null) {
      BigDecimal total = BigDecimalUtil.addAll(cashier.getTotalCommodityPrice(), cashier.getTotalGiftPrice(), cashier
          .getTotalShippingCharge());
      if (BigDecimalUtil.isBelowOrEquals(BigDecimalUtil.multiply(DIContainer.getWebshopConfig().getRmbPointRate(), total.setScale(
          0, BigDecimal.ROUND_UP)), NumUtil.parse(cashier.getUsePoint()))) {
      }
    }
  }
  // 10.1.3 10168 10169 追加 ここまで
}
