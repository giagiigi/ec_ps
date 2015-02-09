package jp.co.sint.webshop.web.message.back;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum SearchErrorMessage implements MessageType {

  /** 検索結果なし */
  SEARCH_RESULT_NOT_FOUND("search_result_not_found"),

  /** 検索結果オーバーフロー */
  SEARCH_RESULT_OVERFLOW("search_result_overflow");

  private String messagePropertyId;

  private SearchErrorMessage(String messagePropertyId) {
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
