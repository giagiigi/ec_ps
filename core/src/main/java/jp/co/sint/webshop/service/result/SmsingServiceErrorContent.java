package jp.co.sint.webshop.service.result;

/**
 * ShopManagementService内で発生するエラーEnum
 * 
 * @author System Integrator Corp.
 */
public enum SmsingServiceErrorContent implements ServiceErrorContent {
  /** メール送信先顧客情報非存在エラー */
  NO_CUSTOMER_TO_SEND_SMS,

  /** メールテンプレート非存在エラー */
  NO_SMS_TEMPLATE_ERROR;
}
