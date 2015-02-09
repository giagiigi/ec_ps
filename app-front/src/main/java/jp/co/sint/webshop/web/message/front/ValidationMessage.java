package jp.co.sint.webshop.web.message.front;

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
  /*20111212 lirong add start*/
  /** メールアドレス不一致エラー */
  NOT_SAME_EMAIL("not_same_email"),
  /*20111212 lirong add end*/
  
  /*20111215 lirong add start*/
  /** 验证码不一致エラー */
  NOT_SAME_VERIFY_CODE("not_same_verify"),
  /*20111215 lirong add end*/
  NAME_LENGTH_ERR("name_length_err"),
  ADDRESS_LENGTH_ERR("address_length_err"),
  INVOICE_TAXPAYER_CODE_LENGTH_ERR("invoice_taxpayer_code_length_err"),
  COMPANYNAME_LENGTH_ERR("companyname_length_err"),
  BANKNAME_LENGTH_ERR("bankname_length_err"),
  /** 最小値エラー */
  LESS_THAN_PASSWORD("less_than_password"),
  //Add by V10-CH start
  TRUE_NUMBER("true_number"),
  NO_NUMBER("no_number");
  //Add by V10-CH end

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
    return "jp.co.sint.webshop.web.message.front.Messages";
  }
}
