package jp.co.sint.webshop.service.event;

/**
 * StockServiceのアクションイベントを受け取るためのリスナーインターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface StockListener extends ServiceEventListener<StockEvent> {

  /**
   * ＥＣ引当数が更新されると呼び出されます。
   * 
   * @param event
   *          在庫イベント
   */
  void changeEcAllocatedQuantity(StockEvent event);

  /**
   * ＴＭＡＬＬ引当数が更新されると呼び出されます。
   * 
   * @param event
   *          在庫イベント
   * @throws Exception 
   */
  void changeTmAllocatedQuantity(StockEvent event);

  /**
   * ＪＤ引当数が更新されると呼び出されます。
   * 
   * @param event
   *          在庫イベント
   */
  void changeJdAllocatedQuantity(StockEvent event);
}