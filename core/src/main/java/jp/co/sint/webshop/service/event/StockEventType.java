package jp.co.sint.webshop.service.event;

public enum StockEventType implements ServiceEventType<StockEvent, StockListener> {
  /** EC */
  EC,
  /** TMALL */
  TM,
  /** JD */
  JD;

  public void execute(StockListener listener, StockEvent event) {
    switch (this) {
      case EC:
        listener.changeEcAllocatedQuantity(event);
        break;
      case TM:
        listener.changeTmAllocatedQuantity(event);
        break;
      case JD:
        listener.changeJdAllocatedQuantity(event);
        break;
      default:
        break;
    }
  }
}
