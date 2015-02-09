package jp.co.sint.webshop.service.event;

public enum CustomerEventType implements ServiceEventType<CustomerEvent, CustomerListener> {
  /** 入会 */
  ADDED,
  /** 更新 */
  UPDATED,
  /** 退会 */
  WITHDRAWED,
  /** パスワード変更 */
  PASSWORD_UPDATED,
  /** 退会依頼 */
  WITHDRAWAL_REQUESTED;

  public void execute(CustomerListener listener, CustomerEvent event) {
    switch (this) {
      case ADDED:
        listener.customerAdded(event);
        break;
      case UPDATED:
        listener.customerUpdated(event);
        break;
      case WITHDRAWED:
        listener.customerWithdrawed(event);
        break;
      case PASSWORD_UPDATED:
        listener.customerPasswordUpdated(event);
        break;
      case WITHDRAWAL_REQUESTED:
        listener.customerWithdrawalRequested(event);
        break;
      default:
        break;
    }
  }
}
