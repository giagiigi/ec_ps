package jp.co.sint.webshop.service.order;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceException;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SmsingService;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.event.ShippingDelegate;
import jp.co.sint.webshop.service.event.ShippingEvent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;

import org.apache.log4j.Logger;

public class ShippingMailDelegate implements ShippingDelegate, Serializable {

  private static final long serialVersionUID = 1L;

  public ShippingMailDelegate() {
  }

  public void shippingUpdated(ShippingEvent shippingEvent, String orderType) {
    Logger logger = Logger.getLogger(this.getClass());
    if (shippingEvent.isSendFlg()) {
      try {
        if (shippingEvent.getType().equals("mail")) {
          MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
          ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
          OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());

          if (orderType.equals("TMALL")) {
            TmallShippingHeader tmallShippingHeader = orderService.getTmallShippingHeader(shippingEvent.getShippingNo());
            OrderContainer tmallOrderContainer = orderService.getTmallOrder(tmallShippingHeader.getOrderNo(), tmallShippingHeader
                .getShippingNo());
            OrderContainer tmallAllContainer = orderService.getTmallOrder(tmallShippingHeader.getOrderNo());
            Shop tmallShop = shopService.getShop(tmallShippingHeader.getShopCode());
            CashierPaymentTypeBase tmallBase = new CashierPaymentTypeBase();
            tmallBase.setPaymentCommission(tmallOrderContainer.getTmallOrderHeader().getPaymentCommission());
            tmallBase.setPaymentMethodCode(NumUtil.toString(tmallOrderContainer.getTmallOrderHeader().getPaymentMethodNo()));
            tmallBase.setPaymentMethodName(tmallOrderContainer.getTmallOrderHeader().getPaymentMethodName());
            tmallBase.setPaymentMethodType(tmallOrderContainer.getTmallOrderHeader().getPaymentMethodType());

            CashierPayment tmallPayment = new CashierPayment();
            tmallPayment.setSelectPayment(tmallBase);

            ServiceResult tmallMailResult = mailingService.sendTmallShippingReceivedMail(tmallOrderContainer, tmallAllContainer,
                tmallShop, tmallPayment, shippingEvent.getShippingNo());
            for (ServiceErrorContent error : tmallMailResult.getServiceErrorList()) {
              logger.error(error);
            }
          } else {
            ShippingHeader shippingHeader = orderService.getShippingHeader(shippingEvent.getShippingNo());
            OrderContainer orderContainer = orderService.getOrder(shippingHeader.getOrderNo(), shippingHeader.getShippingNo());
            OrderContainer allContainer = orderService.getOrder(shippingHeader.getOrderNo());
            Shop shop = shopService.getShop(shippingHeader.getShopCode());
            CashierPaymentTypeBase base = new CashierPaymentTypeBase();
            base.setPaymentCommission(orderContainer.getOrderHeader().getPaymentCommission());
            base.setPaymentMethodCode(NumUtil.toString(orderContainer.getOrderHeader().getPaymentMethodNo()));
            base.setPaymentMethodName(orderContainer.getOrderHeader().getPaymentMethodName());
            base.setPaymentMethodType(orderContainer.getOrderHeader().getPaymentMethodType());

            CashierPayment payment = new CashierPayment();
            payment.setSelectPayment(base);

            ServiceResult mailResult = mailingService.sendShippingReceivedMail(orderContainer, allContainer, shop, payment,
                shippingEvent.getShippingNo());
            for (ServiceErrorContent error : mailResult.getServiceErrorList()) {
              logger.error(error);
            }
          }
        } else if (shippingEvent.getType().equals("sms")) {
          SmsingService smsingService = ServiceLocator.getSmsingService(ServiceLoginInfo.getInstance());
          ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
          OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());

          if (orderType.equals("TMALL")) {
            TmallShippingHeader tmallShippingHeader = orderService.getTmallShippingHeader(shippingEvent.getShippingNo());
            OrderContainer tmallOrderContainer = orderService.getTmallOrder(tmallShippingHeader.getOrderNo(), tmallShippingHeader
                .getShippingNo());
            OrderContainer tmallAllContainer = orderService.getTmallOrder(tmallShippingHeader.getOrderNo());
            Shop tmallShop = shopService.getShop(tmallShippingHeader.getShopCode());
            CashierPaymentTypeBase tmallBase = new CashierPaymentTypeBase();
            tmallBase.setPaymentCommission(tmallOrderContainer.getTmallOrderHeader().getPaymentCommission());
            tmallBase.setPaymentMethodCode(NumUtil.toString(tmallOrderContainer.getTmallOrderHeader().getPaymentMethodNo()));
            tmallBase.setPaymentMethodName(tmallOrderContainer.getTmallOrderHeader().getPaymentMethodName());
            tmallBase.setPaymentMethodType(tmallOrderContainer.getTmallOrderHeader().getPaymentMethodType());

            CashierPayment tmallPayment = new CashierPayment();
            tmallPayment.setSelectPayment(tmallBase);

            ServiceResult tmallSmsResult = smsingService.sendTmallShippingReceivedSms(tmallOrderContainer, tmallAllContainer, tmallShop,
                tmallPayment, shippingEvent.getShippingNo());
            for (ServiceErrorContent error : tmallSmsResult.getServiceErrorList()) {
              logger.error(error);
            }
          } else {
            ShippingHeader shippingHeader = orderService.getShippingHeader(shippingEvent.getShippingNo());
            OrderContainer orderContainer = orderService.getOrder(shippingHeader.getOrderNo(), shippingHeader.getShippingNo());
            OrderContainer allContainer = orderService.getOrder(shippingHeader.getOrderNo());
            Shop shop = shopService.getShop(shippingHeader.getShopCode());
            CashierPaymentTypeBase base = new CashierPaymentTypeBase();
            base.setPaymentCommission(orderContainer.getOrderHeader().getPaymentCommission());
            base.setPaymentMethodCode(NumUtil.toString(orderContainer.getOrderHeader().getPaymentMethodNo()));
            base.setPaymentMethodName(orderContainer.getOrderHeader().getPaymentMethodName());
            base.setPaymentMethodType(orderContainer.getOrderHeader().getPaymentMethodType());

            CashierPayment payment = new CashierPayment();
            payment.setSelectPayment(base);

            ServiceResult smsResult = smsingService.sendShippingReceivedSms(orderContainer, allContainer, shop, payment,
                shippingEvent.getShippingNo());
            for (ServiceErrorContent error : smsResult.getServiceErrorList()) {
              logger.error(error);
            }
          }
        }
      } catch (RuntimeException e) {
        logger.error(e);
        throw new ServiceException(e);
      }
    }
  }
}
