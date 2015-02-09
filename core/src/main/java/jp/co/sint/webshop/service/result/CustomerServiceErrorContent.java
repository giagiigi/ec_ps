package jp.co.sint.webshop.service.result;

/**
 * CustomerListService内で発生するエラーEnum
 * 
 * @author System Integrator Corp.
 */
public enum CustomerServiceErrorContent implements ServiceErrorContent {
  /** アドレス帳削除済みエラー */
  ADDRESS_DELETED_ERROR,
  /** アドレス帳登録済みエラー */
  ADDRESS_REGISTERED_ERROR,
  /** アドレス帳未登録エラー */
  ADDRESS_NO_DEF_FOUND_ERROR,
  /** 顧客グループ登録済みエラー */
  CUSTOMERGROUP_REGISTERED_ERROR,
  
  // soukai add ob 2011/12/14 start
  /** 顾客组别已被删除错误 */
  CUSTOMERGROUP_DELETED_ERROR,
  // soukai add ob 2011/12/14 end
  
  /** デフォルト顧客グループ削除エラー */
  DEFAULT_CUSTOMERGROUP_DELETED_ERROR,
  /** 会員登録済み顧客グループ削除エラー */
  REGISTERD_CUSTOMERGROUP_DELETED_ERROR,
  /** 顧客削除済みエラー */
  CUSTOMER_DELETED_ERROR,
  /** 顧客退会依頼済みエラー */
  CUSTOMER_DELETED_NOTICE_ERROR,
  /** 顧客登録済みエラー */
  CUSTOMER_REGISTERED_ERROR,
  /** 保有ポイントマイナスエラー */
  REST_POINT_MINUS_ERROR,
  /** メールアドレス重複エラー */
  DUPLICATED_EMAIL_ERROR,
  /** ログインID重複エラー */
  DUPLICATED_LOGINID_ERROR,
  /** 有効受注存在エラー */
  EXIST_ACTIVE_ORDER_ERROR,
  /** 未入金受注存在エラー */
  EXIST_NOT_PAYMENT_ORDER_ERROR,
  /** 本人アドレス帳更新エラー */
  SELF_ADDRESS_UPDATE_ERROR,
  /** ポイント追加失敗エラー */
  POINT_INSERT_FAILURE_ERROR,
  /** ポイントオーバーフローエラー */
  POINT_OVERFLOW_ERROR,
  /** ポイントシステム停止中エラ */
  POINT_SYSTEM_DISABLED_ERROR,
  // 20120109 shen add start
  /** 礼金券不可使用错误 */
  COUPON_NOT_USE_ERROR
  // 20120109 shen add end
}
