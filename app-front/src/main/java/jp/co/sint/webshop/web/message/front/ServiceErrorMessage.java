package jp.co.sint.webshop.web.message.front;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum ServiceErrorMessage implements MessageType {

  /** 該当データ未存在エラーメッセージ */
  NO_DATA_ERROR("no_data_error"),
  /** 重複登録エラーメッセージ */
  DUPLICATED_REGISTER_ERROR("duplicated_register_error");

  private String messagePropertyId;

  private ServiceErrorMessage(String messagePropertyId) {
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
