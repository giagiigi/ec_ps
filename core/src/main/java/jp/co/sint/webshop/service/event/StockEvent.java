package jp.co.sint.webshop.service.event;

/**
 * コンポーネント内でStockServiceのイベントが発生したことを示すイベントです。
 * 
 * @author System Integrator Corp.
 */
public class StockEvent implements ServiceEvent {

  private static final long serialVersionUID = 1L;

  private String orderNo;

  public StockEvent() {
  }

  public StockEvent(String orderNo) {
    setOrderNo(orderNo);
  }

  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

}
