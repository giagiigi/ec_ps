package jp.co.sint.webshop.web.message.back;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum ActionErrorMessage implements MessageType {

  /** デフォルトデータ削除エラーメッセージ */
  DEFAULT_DELETE("default_delete"),

  /** データ削除エラーメッセージ */
  DELETE_ERROR("delete_error"),

  /** データ未選択エラーメッセージ */
  NO_CHECKED("no_checked"),
  
  /** データ未選択エラーメッセージ */
  NO_CHECKED_NEW("no_checked_new"),

  /** 大小関係不正エラー */
  COMPARISON_ERROR("comparison_error"),

  /** 検索条件不正エラーメッセージ */
  SEARCHCONDITION_ERROR("searchCondition_error"),

  /** 指定期間不正エラーメッセージ */
  PERIOD_ERROR("period_error"),

  /** 指定期間不正エラーメッセージ(パラメータ有り) */
  PERIOD_ERROR_WITH_PARAM("period_error_with_param"),

  /** 未入金・売上未確定データ有 */
  PAYMENT_OR_NOT_FIXED_ERROR("payment_or_not_fixed_error"),

  /** ショップコード非存在エラー */
  NO_SHOP_CODE_ERROR("no_shop_code_error"),

  /** 0件削除エラー */
  ZERO_DATA_DELETE_ERROR("zero_data_delete_error"),

  /** 手数料最大値削除エラー */
  DELETE_MAX_COMMISSION_ERROR("delete_max_commission_error"),

  /** 検索結果なしメッセージ */
  SEARCH_RESULT_NOT_FOUND("search_result_not_found"),

  /** 検索結果オーバーフローメッセージ */
  SEARCH_RESULT_OVERFLOW("search_result_overflow"),

  // 2012/12/14 促销对应 ob add start
  /** データ登録失敗メッセージ */
  REGISTER_FAILED_ERROR("register_failed_error"),
  
  /** データ登録成功メッセージ */
  REGISTER_SECCESS("register_seccess"),
  // 2012/12/14 促销对应 ob add end
  
  /** URLパラメータ不正エラー */
  BAD_URL("bad_url"),

  /** CSV取込失敗エラーメッセージ */
  CSV_IMPORT_FAILED("csv_import_failed"),

  /** CSV取込一部失敗エラーメッセージ */
  CSV_IMPORT_PARTIAL("csv_import_partial"),

  /** ファイルアップロード失敗エラーメッセージ */
  UPLOAD_FAILED("upload_failed"),

  /** メールキュー登録失敗エラーメッセージ */
  REGISTER_MAILQUEUE_FAILED("register_mailqueue_failed"),
  
  //Add by V10-CH start
  REGISTER_SMSQUEUE_FAILED("register_smsqueue_failed"),
  //Add by V10-CH end

  /** 入出力対象なしエラーメッセージ */
  NO_IO_SUBJECT("no_io_subject"),

  /** CSV入出力権限なしエラーメッセージ */
  CSV_IO_NO_AUTH("csv_io_no_auth"),

  /** 該当CSVデータなしエラーメッセージ */
  CSV_OUTPUT_NO_DATA("csv_output_no_data"),

  /** 文字数超過エラーメッセージ */
  STRING_OVER("string_over"),

  /** 書式エラーメッセージ */
  FORMAT_ERROR("format_error"),

  /** コンテンツアップロード時キャンペーン未存在エラーメッセージ */
  NO_CAMPAIGN_ERROR("no_campaign_error"),
  
  /** 在线客服使用时，SCRIPT内容（定位代码）必须输入 */
  SCRIPT_TEXT1("Script_text1"),
  
  /** 在线客服使用时，SCRIPT内容（图标代码）必须输入 */
  SCRIPT_TEXT2("Script_text2"),

  /**在线客服使用时，Script内容（功能代码）必须输入 */
  SCRIPT_TEXT3("Script_text3"),
  
  /**Google Analysis使用时，Script内容（功能代码）必须输入 */
  SCRIPT_TEXT("Script_text"),

  /**使用积分必须设为RMB兑换率的倍数 */  
  POINT_SMALL_NOT_ERROR("point_small_not_error"),
  /**商品同期化多选框非空判断*/
  COMMODITY_SYNCRO_NULL_ERROR("commodity_syncro_null_error"),
  //add by os014 2012-01-31 商品编辑页面手动输入品牌编号错误 begin
  /**输入品牌编号错误*/
  C_COMMODITY_HEADER_EDIT_BRANDCODE_ERROR("c_commodity_header_edit_brandcode_error"),
  //20120204 ob shb add start
  IMG_ERR("img_err"),
  IMG_UPLOAD_OVER("img_upload_over"),
  IMG_UPLOAD_SUCCESS("img_upload_success"),
  IMG_UPLOAD_FINAL("img_upload_final"),
  //20120204 ob shb add end
  //add by os014 end
  // soukai add 2012/01/31 ob start
  /** 检索结果超过上限时提示错误信息 */
  SEARCH_RESULT_BEYOND_CAP("search_result_beyond_cap");
  // soukai add 2012/01/31 ob end
  private String messagePropertyId;

  private ActionErrorMessage(String messagePropertyId) {
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
