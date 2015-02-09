package jp.co.sint.webshop.web.message.back;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum CompleteMessage implements MessageType {

  /** 登録完了メッセージ */
  REGISTER_COMPLETE("register_complete"),

  /** 更新完了メッセージ */
  UPDATE_COMPLETE("update_complete"),

  /** 削除完了メッセージ */
  DELETE_COMPLETE("delete_complete"),

  /** キャンセル完了メッセージ */
  CANCEL_COMPLETE("cancel_complete"),

  /** アップロード完了メッセージ */
  UPLOAD_COMPLETE("upload_complete"),

  /** CSV出力完了メッセージ */
  CSV_EXPORT_COMPLETE("csv_export_complete"),

  /** CSV取込完了メッセージ */
  CSV_IMPORT_COMPLETE("csv_import_complete"),

  /** メールキュー登録完了メッセージ */
  REGISTER_MAILQUE_COMPLETE("register_mailque_complete"),

  /** 入金確認メール送信完了メッセージ */
  SEND_CONFIRM_MAIL_COMPLETE("send_confirm_mail_complete"),

  /** 入金督促メール送信完了メッセージ */
  SEND_REMIND_MAIL_COMPLETE("send_remind_mail_complete"),

  /** 出荷報告メール送信完了メッセージ */
  SEND_SHIPPING_MAIL_COMPLETE("send_shipping_mail_complete"),

  /** 出荷報告メール送信完了メッセージ(番号無し) */
  SEND_SHIPPING_MAIL_WITHOUT_NO("send_shipping_mail_without_no"),
  
  //Add by V10-CH start
  /** 出荷報告SMS送信完了メッセージ */
  SEND_SHIPPING_SMS_COMPLETE("send_shipping_sms_complete"),

  /** 出荷報告SMS送信完了メッセージ(番号無し) */
  SEND_SHIPPING_SMS_WITHOUT_NO("send_shipping_sms_without_no"), 
  //Add by V10-CH end
  
  /** 顧客退会処理完了メッセージ */
  CUSTOMER_WITHDRAWED_COMPLETE("customer_withdrawed_complete"),

  /** ショップ登録完了メッセージ */
  SHOP_REGISTER_COMPLETE("shop_register_complete"),

  /** 受注修正メール送信完了メッセージ */
  SEND_MODIFY_MAIL_COMPLETE("send_modify_mail_complete"),

  //Add by V10-CH start
  /** メールキュー登録完了メッセージ */
  REGISTER_SMSQUE_COMPLETE("register_smsque_complete"),

  /** 入金確認メール送信完了メッセージ */
  SEND_CONFIRM_SMS_COMPLETE("send_confirm_sms_complete"),

  /** 入金督促メール送信完了メッセージ */
  SEND_REMIND_SMS_COMPLETE("send_remind_sms_complete"),

  SEND_MODIFY_SMS_COMPLETE("send_modify_sms_complete"),
  
  COMMODITY_CYNCRO_COMPLETE("commodity_cyncro_complete");
  
  //Add by V10-CH end
  private String messagePropertyId;

  private CompleteMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.back.Messages";
  }

}
