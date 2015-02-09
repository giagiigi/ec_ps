package jp.co.sint.webshop.web.message.front;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum ActionErrorMessage implements MessageType {

  /** 検索条件不正エラーメッセージ */
  SEARCHCONDITION_ERROR("searchCondition_error"),

  /** データ未選択エラーメッセージ */
  NO_CHECKED("no_checked"),

  /** 該当データ未存在エラーメッセージ */
  NO_DATA_ERROR("no_data_error"),

  /** 大小関係不正エラー */
  COMPARISON_ERROR("comparison_error"),

  /** URL不正エラーメッセージ */
  BAD_URL("bad_url"),
  
  /**  */
  NO_NUMBER("no_number"),
  
  /**  */
  TRUE_NUMBER("true_number"),
  
  /** 文字数超過エラーメッセージ */
  STRING_OVER("string_over")
  // 2013/04/01 优惠券对应 ob add start
  /**验证码无效或已超出有效期间*/
  ,MOBILE_AUTH_CODE_OVER("mobile_auth_code_over");
  // 2013/04/01 优惠券对应 ob add en

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
    return "jp.co.sint.webshop.web.message.front.Messages";
  }

}
