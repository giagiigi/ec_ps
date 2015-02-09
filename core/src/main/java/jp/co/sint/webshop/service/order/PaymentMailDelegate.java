package jp.co.sint.webshop.service.order;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceException;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.event.PaymentDelegate;
import jp.co.sint.webshop.service.event.PaymentEvent;

public class PaymentMailDelegate implements PaymentDelegate, Serializable {

  private static final long serialVersionUID = 1L;

  public PaymentMailDelegate() {
  }

  public void paymentDateUpdated(PaymentEvent paymentEvent) {
    try {
      MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
      OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
      ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());

      OrderContainer orderContainer = orderService.getOrder(paymentEvent.getOrderNo());
      OrderSummary orderSummary = orderService.getOrderSummary(paymentEvent.getOrderNo());
      Shop shop = shopService.getShop(orderContainer.getOrderHeader().getShopCode());
      mailingService.sendPaymentReceivedMail(orderContainer, orderSummary, shop);

    } catch (RuntimeException e) {
      throw new ServiceException(e);
    }
  }
}
