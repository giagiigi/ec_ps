package jp.co.sint.webshop.service.event.impl;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SmsingService;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.event.OrderEvent;
import jp.co.sint.webshop.service.event.OrderListener;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderMailType;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;

public class DefaultOrderListener implements OrderListener {

  private static final long serialVersionUID = 1L;

  public DefaultOrderListener() {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultOrderListener.0"));
  }

  public void orderReturned(OrderEvent ev) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultOrderListener.1"));
  }

  public void orderCancelled(OrderEvent ev) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultOrderListener.2"));

    MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());

    OrderContainer order = orderService.getOrder(ev.getOrderNo());
    CashierPayment payment = ev.getCashierPayment();
    Shop shop = shopService.getShop(order.getOrderHeader().getShopCode());

    ServiceResult result = mailingService.sendCancelOrderMail(order, payment, shop);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        logger.error(error.toString());
      }
    }
  }

  public void orderReceived(OrderEvent ev) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultOrderListener.3"));

    MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    //add by V10-CH start
    SmsingService smsingService = ServiceLocator.getSmsingService(ServiceLoginInfo.getInstance());
    //add by V10-CH end
    OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());

    OrderContainer order = orderService.getOrder(ev.getOrderNo());
    CashierPayment payment = ev.getCashierPayment();
    Shop shop = shopService.getShop(order.getOrderHeader().getShopCode());

    ServiceResult result = null;
    if (ev.getOrderMailType() == null) {
      logger.error(Messages.log("service.event.impl.DefaultOrderListener.4"));
    } else if (ev.getOrderMailType().equals(OrderMailType.PC)) {
      result = mailingService.sendNewOrderMailPc(order, payment, shop);
    } else if (ev.getOrderMailType().equals(OrderMailType.MOBILE)) {
      result = mailingService.sendNewOrderMailMobile(order, payment, shop);
    } else {
      logger.error(Messages.log("service.event.impl.DefaultOrderListener.5"));
    }
    
    //Add by V10-CH start
    ServiceResult smsresult = null;
    if (ev.getOrderSmsType() == null) {
      //logger.error(Messages.log("service.event.impl.DefaultOrderListener.4"));
    } else{
      smsresult = smsingService.sendNewOrderSmsPc(order, payment, shop);
      if (smsresult.hasError()) {
        for (ServiceErrorContent error : smsresult.getServiceErrorList()) {
          logger.error(error.toString());
        }
      }
    }
    //Add by V10-Ch end

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        logger.error(error.toString());
      }
    }


  }

  public void orderUpdated(OrderEvent ev) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultOrderListener.6"));

    MailingService mailService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());

    OrderContainer order = orderService.getOrder(ev.getOrderNo());
    CashierPayment payment = ev.getCashierPayment();
    Shop shop = shopService.getShop(order.getOrderHeader().getShopCode());

    ServiceResult result = mailService.sendUpdateOrderMail(order, payment, shop);
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        logger.error(error.toString());
      }
    }

  }

}
