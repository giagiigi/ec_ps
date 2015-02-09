package jp.co.sint.webshop.service.event;

/**
 * @author System Integrator Corp.
 */
public interface ShippingListener extends ServiceEventListener<ShippingEvent> {

  /**
   * 出荷データのステータスが「手配中」になったときに実行されます。
   * 
   * @param event
   *          出荷イベント
   */
  void shippingArranged(ShippingEvent event);

  /**
   * 出荷データのステータスが「出荷済み」になったときに実行されます。
   * 
   * @param event
   *          出荷イベント
   */
  void shippingFulfilled(ShippingEvent event);

}
