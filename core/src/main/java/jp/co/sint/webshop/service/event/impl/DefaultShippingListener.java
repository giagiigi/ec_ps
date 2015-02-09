package jp.co.sint.webshop.service.event.impl;

import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
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
import jp.co.sint.webshop.service.event.ShippingEvent;
import jp.co.sint.webshop.service.event.ShippingListener;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.NumUtil;

import org.apache.log4j.Logger;

public class DefaultShippingListener implements ShippingListener {

  private static final long serialVersionUID = 1L;

  public DefaultShippingListener() {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultShippingListener.0"));
  }

  public void shippingArranged(ShippingEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultShippingListener.1"));
  }

  public void shippingFulfilled(ShippingEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultShippingListener.2"));

    if (event.isSendFlg()) {
      try {
        if(event.getType().equals("mail")){
          MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
          OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
          ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());

          ShippingHeader shippingHeader = orderService.getShippingHeader(event.getShippingNo());
          OrderContainer orderContainer = orderService.getOrder(shippingHeader.getOrderNo(), event.getShippingNo());
          OrderContainer allContainer = orderService.getOrder(shippingHeader.getOrderNo());
          Shop shop = shopService.getShop(shippingHeader.getShopCode());
          CashierPaymentTypeBase base = new CashierPaymentTypeBase();
          base.setPaymentCommission(orderContainer.getOrderHeader().getPaymentCommission());
          base.setPaymentMethodCode(NumUtil.toString(orderContainer.getOrderHeader().getPaymentMethodNo()));
          base.setPaymentMethodName(orderContainer.getOrderHeader().getPaymentMethodName());
          base.setPaymentMethodType(orderContainer.getOrderHeader().getPaymentMethodType());

          CashierPayment payment = new CashierPayment();
          payment.setSelectPayment(base);

          ServiceResult mailResult = mailingService.sendShippingReceivedMail(orderContainer, allContainer, shop, payment, event
              .getShippingNo());
          for (ServiceErrorContent error : mailResult.getServiceErrorList()) {
            logger.error(error);
          }
        }else if(event.getType().equals("sms")){

          SmsingService smsingService = ServiceLocator.getSmsingService(ServiceLoginInfo.getInstance());
          OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
          ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());

          ShippingHeader shippingHeader = orderService.getShippingHeader(event.getShippingNo());
          OrderContainer orderContainer = orderService.getOrder(shippingHeader.getOrderNo(), event.getShippingNo());
          OrderContainer allContainer = orderService.getOrder(shippingHeader.getOrderNo());
          Shop shop = shopService.getShop(shippingHeader.getShopCode());
          CashierPaymentTypeBase base = new CashierPaymentTypeBase();
          base.setPaymentCommission(orderContainer.getOrderHeader().getPaymentCommission());
          base.setPaymentMethodCode(NumUtil.toString(orderContainer.getOrderHeader().getPaymentMethodNo()));
          base.setPaymentMethodName(orderContainer.getOrderHeader().getPaymentMethodName());
          base.setPaymentMethodType(orderContainer.getOrderHeader().getPaymentMethodType());

          CashierPayment payment = new CashierPayment();
          payment.setSelectPayment(base);

          ServiceResult smsResult = smsingService.sendShippingReceivedSms(orderContainer, allContainer, shop, payment, event
              .getShippingNo());
          for (ServiceErrorContent error : smsResult.getServiceErrorList()) {
            logger.error(error);
          }
        }
      } catch (RuntimeException e) {
        logger.error(e);
        throw new ServiceException(e);
      }
    }
  }
}
