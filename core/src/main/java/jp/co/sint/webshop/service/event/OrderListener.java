package jp.co.sint.webshop.service.event;

/**
 * OrderServiceのアクションイベントを受け取るためのリスナーインターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface OrderListener extends ServiceEventListener<OrderEvent> {

  /**
   * 注文データを受け取ると呼び出されます。
   * 
   * @param event
   *          注文イベント
   */
  void orderReceived(OrderEvent event);

  /**
   * 注文データが更新されると呼び出されます。
   * 
   * @param event
   *          注文イベント
   */
  void orderUpdated(OrderEvent event);

  /**
   * 注文データがキャンセルされると呼び出されます。
   * 
   * @param event
   *          注文イベント
   */
  void orderCancelled(OrderEvent event);

  /**
   * 注文データが返品されると呼び出されます。
   * 
   * @param event
   *          注文イベント
   */
  void orderReturned(OrderEvent event);

}
