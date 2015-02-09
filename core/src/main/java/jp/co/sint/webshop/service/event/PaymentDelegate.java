package jp.co.sint.webshop.service.event;


public interface PaymentDelegate {

  void paymentDateUpdated(PaymentEvent event);

}
