package jp.co.sint.webshop.service.result;

/**
 * 決済処理で発生したエラーの種別を判断するEnum<BR>
 * 
 * @author System Integrator Corp.
 */
public enum PaymentErrorContent implements ServiceErrorContent {

  /** カード情報不正エラー */
  INVALID_CARD_INFORMATION_ERROR,

  /** 顧客名不正エラー */
  INVALID_CUSTOMER_NAME_ERROR,

  /** 顧客名(カナ)不正エラー */
  INVALID_CUSTOMER_NAME_KANA_ERROR,

  /** メールアドレス不正エラー */
  INVALID_EMAIL_ERROR,

  /** 支払期限不正エラー */
  INVALID_PAYMENT_LIMIT_DATE_ERROR,

  /** その他パラメータ不正エラー */
  INVALID_OTHER_PARAMETER_ERROR,

  /** その他決済不正エラー */
  OTHER_PAYMENT_ERROR;

}
