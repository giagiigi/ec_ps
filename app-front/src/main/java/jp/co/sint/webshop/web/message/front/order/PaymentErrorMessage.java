package jp.co.sint.webshop.web.message.front.order;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum PaymentErrorMessage implements MessageType {

  /** カード情報不正エラー */
  INVALID_CARD_INFORMATION_ERROR("invalid_card_information_error"),

  /** 顧客名不正エラー */
  INVALID_CUSTOMER_NAME_ERROR("invalid_customer_name_error"),

  /** 顧客名(カナ)不正エラー */
  INVALID_CUSTOMER_NAME_KANA_ERROR("invalid_customer_name_kana_error"),

  /** メールアドレス不正エラー */
  INVALID_EMAIL_ERROR("invalid_email_error"),

  /** その他決済不正エラー */
  INVALID_OTHER_ERROR("invalid_other_error");

  private String messagePropertyId;

  private PaymentErrorMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.front.order.PaymentMessages";
  }
}
