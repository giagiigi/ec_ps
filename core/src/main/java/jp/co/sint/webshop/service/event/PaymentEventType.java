package jp.co.sint.webshop.service.event;

public enum PaymentEventType implements ServiceEventType<PaymentEvent, PaymentListener> {
  UPDATED;

  public void execute(PaymentListener listener, PaymentEvent event) {
    switch (this) {
      case UPDATED:
        listener.paymentDateUpdated(event);
        break;
      default:
        break;
    }    
  }
}
