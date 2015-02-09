package jp.co.sint.webshop.web.message.front;

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
  /** メール送信完了メッセージ */
  SENDMAIL_COMPLETE("sendmail_complete"),
  /** 問い合わせ送信完了メッセージ */
  INQUIRY_COMPLETE("inquiry_complete"),
  /**  */
  SENDSMS_COMPLETE("sendsms_complete");

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
    return "jp.co.sint.webshop.web.message.front.Messages";
  }

}
