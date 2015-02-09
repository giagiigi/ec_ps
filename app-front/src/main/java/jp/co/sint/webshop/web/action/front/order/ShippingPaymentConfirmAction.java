package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author Kousen.
 */
public class ShippingPaymentConfirmAction extends ShippingBaseAction {

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

    
    
    //session中赠品重置  20140725 hdh
    giftReset(getCart(), "00000000");
    
    
    ShippingBean bean = getBean();
    if (getBean().getAddAddressCheckList()!= null && getBean().getAddAddressCheckList().size()>0){
      bean.getVerificationBean().setMobileNumber(bean.getAddAddressCheckList().get(0).getMobileNumber());
      bean.getVerificationBean().setPhoneNumber(bean.getAddAddressCheckList().get(0).getPhoneNumber());
    }
    
    Cashier cashier = bean.getCashier();
    //配送时间设置
    bean.getShippingList().get(0).getShippingList().get(0).setDeliveryAppointedDate(cashier.getShippingList().get(0).getDeliveryAppointedDate());
    bean.getShippingList().get(0).getShippingList().get(0).setDeliveryAppointedTimeZone(cashier.getShippingList().get(0).getDeliveryAppointedTimeZone());
    
    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();

    // 支付方式设定
    cashier.setPayment(supporter.createCashierPayment(bean.getOrderPayment()));

    refreshItems(false, false);

    if (StringUtil.hasValue(getPathInfo(0)) && getPathInfo(0).equals("nextDiscount")) {
      setNextUrl("/app/order/shipping/discount_confirm/" + DiscountType.CUSTOMER.getValue());
    }
    super.copyBeanToCashier();
    
    bean = createShippingBeanFromCashier();
    
    // 重新设置礼品卡金额
    if (StringUtil.hasValue(bean.getUseAmountRight())) {
      bean.getCashier().setGiftCardUseAmount(BigDecimal.ZERO);
      if (BigDecimalUtil.isAbove(new BigDecimal(bean.getUseAmountRight()), bean.getCashier().getPaymentTotalPrice())) {
        bean.setUseAmountRight(bean.getCashier().getPaymentTotalPrice().toString());
        addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.17"));
      }
      bean.getCashier().setGiftCardUseAmount(new BigDecimal(bean.getUseAmountRight()));
    }
    createOutCardPrice();
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

}
