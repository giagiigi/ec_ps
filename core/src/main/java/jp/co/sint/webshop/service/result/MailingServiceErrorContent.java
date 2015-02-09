package jp.co.sint.webshop.service.result;

/**
 * ShopManagementService内で発生するエラーEnum
 * 
 * @author System Integrator Corp.
 */
public enum MailingServiceErrorContent implements ServiceErrorContent {
  /** メール送信先顧客情報非存在エラー */
  NO_CUSTOMER_TO_SEND_MAIL,

  /** メールテンプレート非存在エラー */
  NO_MAIL_TEMPLATE_ERROR;
}
