package jp.co.sint.webshop.service.event;

public interface PaymentListener extends ServiceEventListener<PaymentEvent> {

  void paymentDateUpdated(PaymentEvent event);

}
