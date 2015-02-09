package jp.co.sint.webshop.service.order;

public enum PaymentResultType {

  /** 未実施 */
  NONE,

  /** 成功 */
  COMPLETED,

  /** 失敗 */
  FAILED,

  /** 中断 */
  SUSPENDED,

  /** タイムアウト */
  TIMED_OUT;
}
