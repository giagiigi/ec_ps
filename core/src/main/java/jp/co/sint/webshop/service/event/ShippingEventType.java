package jp.co.sint.webshop.service.event;

public enum ShippingEventType implements ServiceEventType<ShippingEvent, ShippingListener> {
  /** 出荷手配 */
  ARRANGED,

  /** 出荷実績登録 */
  FULFILLED;


  public void execute(ShippingListener listener, ShippingEvent event) {
    switch (this) {
      case ARRANGED:
        listener.shippingArranged(event);
        break;
      case FULFILLED:
        listener.shippingFulfilled(event);
        break;
      default:
        break;
    }
  }
}
