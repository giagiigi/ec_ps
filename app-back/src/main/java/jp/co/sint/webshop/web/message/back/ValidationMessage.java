package jp.co.sint.webshop.web.message.back;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum ValidationMessage implements MessageType {
  /** 必須入力エラー */
  REQUIRED_ERROR("required_error"),
  /** パスワード不一致エラー */
  NOT_SAME_PASSWORD("not_same_password"),
  
  NOT_SAME_EMAIL("not_same_email"),
  
  // add by lc 2012-03-08 strat
  NAME_LENGTH_ERR("name_length_err"),
  ADDRESS_LENGTH_ERR("address_length_err"),
  INVOICE_LENGTH_ERR("invoce_length_err"),
  //add by lc 2012-03-08 end
  
  //Add by V10-CH start
  /** 电话号码与手机号码二选一 */
  NO_NUMBER("no_number"),
  /** 特殊情况 */
  TRUE_NUMBER("true_number"),
  //Add by V10-CH end
  /** 最小値エラー */
  LESS_THAN_PASSWORD("less_than_password");

  private String messagePropertyId;

  private ValidationMessage(String messagePropertyId) {
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
