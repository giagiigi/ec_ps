package jp.co.sint.webshop.service.event;

public enum OrderEventType implements ServiceEventType<OrderEvent, OrderListener> {
  /** 受注作成 */
  RECEIVED,
  /** 受注キャンセル */
  CANCELLED,
  /** 受注更新 */
  UPDATED,
  /** 受注返品 */
  RETURNED;

  public void execute(OrderListener listener, OrderEvent event) {
    switch (this) {
      case RECEIVED:
        listener.orderReceived(event);
        break;
      case UPDATED:
        listener.orderUpdated(event);
        break;
      case CANCELLED:
        listener.orderCancelled(event);
        break;
      case RETURNED:
        listener.orderReturned(event);
        break;
      default:
        break;
    }
  }
}
