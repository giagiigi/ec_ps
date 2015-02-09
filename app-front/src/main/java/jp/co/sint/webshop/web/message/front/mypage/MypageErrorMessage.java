package jp.co.sint.webshop.web.message.front.mypage;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum MypageErrorMessage implements MessageType {

  /** 本人アドレス削除エラーメッセージ */
  DELETE_SELF_ADDRESS_ERROR("delete_self_address_error"),

  /** パスワード再発行画面利用不可メッセージ */
  NOT_USED_REMINDER("not_used_reminder"),

  /** お問い合わせ送信エラーメッセージ */
  INQUIRY_ERROR("inquiry_error"),

  /** 注文キャンセル不可メッセージ */
  DISAPPROVE_CANCEL_ORDER("disapprove_cancel_order"),

  /** 退会依頼済みメッセージ */
  REQUESTED_WITHDRAWAL("requested_withdrawal"),

  /** メールマガジン登録済みメッセージ */
  MAILMAGAZINE_DUPLICATED_ERROR("mailmagazine_duplicated_error"),

  /** メールマガジン未登録メッセージ */
  MAILMAGAZINE_CANCELED_ERROR("mailmagazine_canceled_error"),

  /** 受注情報変更エラー */
  ORDER_CHANGED_ERROR("order_changed_error"),

  /** クレジットキャンセルエラー */
  CREDIT_CANCEL_ERROR("credit_cancel_error"),

  /** 現パスワード不一致エラー */
  NOT_MATCH_PASSWORD_ERROR("not_match_password_error"),

  /** パスワードポリシーエラー */
  PASSWORD_POLICY_ERROR("password_policy_error"),

  /** メールマガジン配信登録/停止完了エラー */
  EXPIRED_URL_ERROR("expired_url_error"),

  /** メールマガジン申し込み用メール送信エラー */
  MAILMAGAZINE_SENDMAIL_ERROR("mailmagazine_sendmail_error"),

  /** メールマガジン大量送信エラー */
  MASSIVE_SEND_ERROR("massive_send_error"),

  /** ポイントオーバーフローエラー */
  POINT_OVERFLOW_ERROR("point_overflow_error"),

  /** 受注未入金顧客削除エラーメッセージ */
  REQUESTED_WITHDRAWAL_CUSTOMER_NOT_PAYMENT_ORDER_ERROR("requested_withdrawal_customer_not_payment_order_error"),
  
  /**2013/04/01 优惠券对应 ob add start*/
  /** 手机验证码发送错误 */
  SMS_SEND_ERROR("sms_send_error"),
  
  /** 手机验证码重复发送错误 */
  SMS_DOUBLE_SEND_ERROR("sms_double_send_error"),
  
  /** 手机验证码重复发送错误 */
  MERGER_ALREADY("merger_already"),
  
  /** 检索的数量超过了上限 */
  SEARCH_RESULT_OVERFLOW("search_result_overflow"),
  
  /**没有发行履历*/
  COUPON_HISTORY_NO_DATE_ERROR("coupon_history_no_date_error"),
  
  /**优惠劵兑换成功*/
  COUPON_EXCHANGE_SUCCESS("coupon_exchange_success"),
  
  /**没有朋友使用优惠劵*/
  NOT_FRIENT_USE_COUPON("not_frient_use_coupon"),

  /** 友達 */
  URL_OUT_DATE("url_out_date"),
  
  /**优惠劵兑换失败*/
  COUPON_EXCHANGE_UNSUCCESS("coupon_exchange_unsuccess");
  
  /**2013/04/01 优惠券对应 ob add end*/
  
  private String messagePropertyId;

  private MypageErrorMessage(String messagePropertyId) {
    this.messagePropertyId = messagePropertyId;
  }

  /**
   * メッセージプロパティIDを取得します。
   * 
   * @return メッセージプロパティID
   */
  public String getMessagePropertyId() {
    return this.messagePropertyId;
  }

  /**
   * メッセージリソースを取得します。
   * 
   * @return メッセージリソース
   */
  public String getMessageResource() {
    return "jp.co.sint.webshop.web.message.front.mypage.MypageMessages";
  }

}
