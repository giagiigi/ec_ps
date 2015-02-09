package jp.co.sint.webshop.web.message.front.common;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum AuthorizationErrorMessage implements MessageType {

  /** 認証エラー*/
  AUTHORIZATION_ERROR("authorization_error"),
  
  /** 携帯検証を成功する*/
  MOBILE_COMPLETE("mobile_complete"),
  
  /** 携帯検証を失敗する*/
  MOBILE_COMPLETE_ERROR("mobile_complete_error"),
  
  /** 携帯重複エラー*/
  MOBILE_DOUBLE_ERROR("mobile_double_error");
  
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
    return "jp.co.sint.webshop.web.message.front.common.AuthorizationMessages";
  }

}
