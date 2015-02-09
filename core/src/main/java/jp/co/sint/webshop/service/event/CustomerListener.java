package jp.co.sint.webshop.service.event;

/**
 * CustomerServiceのアクションイベントを受け取るためのリスナーインターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface CustomerListener extends ServiceEventListener<CustomerEvent> {

  /**
   * 顧客が追加されると呼び出されます。
   * 
   * @param event
   *          顧客イベント
   */
  void customerAdded(CustomerEvent event);

  /**
   * 顧客が更新されると呼び出されます。
   * 
   * @param event
   *          顧客イベント
   */
  void customerUpdated(CustomerEvent event);

  /**
   * 顧客が退会すると呼び出されます。
   * 
   * @param event
   *          顧客イベント
   */
  void customerWithdrawed(CustomerEvent event);

  /**
   * 顧客がパスワードを変更すると呼び出されます。
   * 
   * @param event
   *          顧客イベント
   */
  void customerPasswordUpdated(CustomerEvent event);

  /**
   * 顧客が退会依頼をすると呼び出されます。
   * 
   * @param event
   *          顧客イベント
   */
  void customerWithdrawalRequested(CustomerEvent event);
}
