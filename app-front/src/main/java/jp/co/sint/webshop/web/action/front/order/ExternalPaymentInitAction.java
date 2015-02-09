package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.ExternalPaymentBean;
import jp.co.sint.webshop.web.text.front.Messages;

import org.apache.log4j.Logger;

/**
 * @author System Integrator Corp.
 */
public class ExternalPaymentInitAction extends WebFrontAction<ExternalPaymentBean> {

  public boolean validate() {
    if (StringUtil.isNullOrEmpty(orderNo())) {
      setNextUrl("/app/common/index"); //$NON-NLS-1$
      return false;
    }
    return true;
  }

  public WebActionResult callService() {

    WebActionResult result = FrontActionResult.RESULT_SUCCESS;

    try {
      ExternalPaymentBean bean = getBean();
      Logger logger = Logger.getLogger(this.getClass());
      bean.setOrderNo(orderNo());
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      OrderContainer container = service.getOrder(bean.getOrderNo());
      OrderHeader oh = container.getOrderHeader();
      if (oh == null) {
        setNextUrl("/app/common/index"); //$NON-NLS-1$
        logger.info(MessageFormat.format(
            Messages.getString("web.action.front.order.ExternalPaymentInitAction.0"), bean.getOrderNo())); //$NON-NLS-1$
        return result;
      }

      if (oh.getGuestFlg().equals(0L) && !oh.getCustomerCode().equals(getLoginInfo().getCustomerCode())) {
        setNextUrl("/app/common/index"); //$NON-NLS-1$
        logger.info(MessageFormat.format(
            Messages.getString("web.action.front.order.ExternalPaymentInitAction.1"), oh.getCustomerCode(), //$NON-NLS-1$
            getLoginInfo().getCustomerCode()));
        return result;
      }

      // M17N 10361 修正 ここから
      // PKG仮预约、仮订单逻辑删除 开始
      // if (!oh.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue()))
      // {
      // if (!oh.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue())
      // &&
      // !oh.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue()))
      // {
      // // M17N 10361 修正 ここから
      //        setNextUrl("/app/common/index"); //$NON-NLS-1$
      //        logger.info(Messages.getString("web.action.front.order.ExternalPaymentInitAction.2")); //$NON-NLS-1$
      // return result;
      // }
      // PKG仮预约、仮订单逻辑删除 结束

      if (oh.getPaymentStatus().equals(PaymentStatus.PAID.longValue())) {
        setNextUrl("/app/common/index"); //$NON-NLS-1$
        logger.info(Messages.getString("web.action.front.order.ExternalPaymentInitAction.3")); //$NON-NLS-1$
        return result;
      }
      // M17N 10361 追加 ここから
      if (oh.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        setNextUrl("/app/common/index"); //$NON-NLS-1$
        logger.info(Messages.getString("web.action.front.order.ExternalPaymentInitAction.4")); //$NON-NLS-1$
        return result;
      }
      // M17N 10361 追加 ここから

      ShopManagementService sms = ServiceLocator.getShopManagementService(getLoginInfo());
      PaymentMethodSuite pms = sms.getPaymentMethod(oh.getShopCode(), oh.getPaymentMethodNo());
      bean.setPaymentMethodName(pms.getPaymentMethod().getPaymentMethodName());
      
      List<PaymentMethodType> paymentTypeList =  Arrays.asList(PaymentMethodType.values());
      for(PaymentMethodType paymentMethodType:paymentTypeList){
        if(oh.getPaymentMethodType().equals(paymentMethodType.getValue())){
          bean.setPaymentMethodName(paymentMethodType.getName());
        }
      }
      BigDecimal totalAmount = BigDecimalUtil.add(container.getTotalAmount(), container.getOrderHeader().getPaymentCommission());
      BigDecimal discountPrice = NumUtil.coalesce(oh.getDiscountPrice(), BigDecimal.ZERO);
      BigDecimal giftCardUsePrice = NumUtil.coalesce(oh.getGiftCardUsePrice(), BigDecimal.ZERO);
      BigDecimal outCardUsePrice = NumUtil.coalesce(oh.getOuterCardPrice(), BigDecimal.ZERO);
      totalAmount = BigDecimalUtil.subtract(totalAmount, discountPrice);
      totalAmount = BigDecimalUtil.subtract(totalAmount, giftCardUsePrice);
      totalAmount = BigDecimalUtil.add(totalAmount, outCardUsePrice);
      bean.setPaymentTotalPrice(totalAmount.toString());
      bean.setOrderContainer(container);
      bean.setPaymentFormObject(getPaymentFormObject(container));
      bean.setDisplayAlipayInfo(false);
      bean.setDisplayChinapayInfo(false);
      if (PaymentMethodType.ALIPAY.getValue().equals(container.getOrderHeader().getPaymentMethodType())) {
        bean.setDisplayAlipayInfo(true);
      } else if (PaymentMethodType.CHINA_UNIONPAY.getValue().equals(container.getOrderHeader().getPaymentMethodType()) || PaymentMethodType.OUTER_CARD.getValue().equals(container.getOrderHeader().getPaymentMethodType())
          || PaymentMethodType.INNER_CARD.getValue().equals(container.getOrderHeader().getPaymentMethodType())) {
        bean.setDisplayChinapayInfo(true);
      }
      // 遷移履歴情報を削除
      getSessionContainer().clearBackTransitionInfoList();
      setRequestBean(bean);
    } catch (Exception ex) {
      result = FrontActionResult.SERVICE_ERROR;
    }
    return result;
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

  /**
   * 从URL取得订单编号。
   * 
   * @return orderNo
   */
  // 20111205 yl update start
  private String orderNo() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  // 20111205 yl update start
}
