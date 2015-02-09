package jp.co.sint.webshop.web.message.back.analysis;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum AnalysisErrorMessage implements MessageType {
  /** 表示モードエラー */
  DISPLAY_MODE_ERROR("display_mode_error"),

  /** 検索条件範囲エラー */
  SEARCH_RANGE_ERROR("search_range_error"),

  /** 権限エラー */
  PERMISSION_ERROR("permission_error"),

  /** 集計データ未存在エラー */
  NO_SUMMARY_DATA_ERROR("no_summary_data_error");

  private String messagePropertyId;

  private AnalysisErrorMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.back.analysis.AnalysisMessages";
  }

}
