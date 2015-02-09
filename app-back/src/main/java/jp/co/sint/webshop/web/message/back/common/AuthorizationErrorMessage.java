package jp.co.sint.webshop.web.message.back.common;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum AuthorizationErrorMessage implements MessageType {

  /** 認証エラー */
  AUTHORIZATION_ERROR("authorization_error");

  private String messagePropertyId;

  private AuthorizationErrorMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.back.common.AuthorizationMessages";
  }

}
