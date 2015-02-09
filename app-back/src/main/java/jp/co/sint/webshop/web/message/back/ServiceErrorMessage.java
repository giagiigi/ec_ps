package jp.co.sint.webshop.web.message.back;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum ServiceErrorMessage implements MessageType {

  /** 重複登録エラーデフォルトメッセージ */
  DUPLICATED_REGISTER_DEFAULT_ERROR("duplicated_register_default_error"),

  /** 重複登録エラーメッセージ */
  DUPLICATED_REGISTER_ERROR("duplicated_register_error"),
  //2013/04/01 优惠券对应 ob add start
  /** 重複登録エラーメッセージ */
  REGISTER_ERROR("register_error"),
  //2013/04/01 优惠券对应 ob add end

  /** 該当データ未存在エラーデフォルトメッセージ */
  NO_DATA_DEFAULT_ERROR("no_data_default_error"),

  /** 該当データ未存在エラーメッセージ */
  NO_DATA_ERROR("no_data_error"),
  


  /** 保有ポイントマイナスエラーメッセージ */
  REST_POINT_MINUS_ERROR("rest_point_minus_error"),

  /** デフォルト顧客グループ削除時メッセージ */
  DELETE_DEFAULT_CUSTOMERGROUP("delete_default_customergroup"),

  /** 会員登録済み顧客グループ削除時メッセージ */
  DELETE_REGISTERD_CUSTOMERGROUP("delete_registered_customergroup"),

  /** 更新失敗エラーメッセージ */
  UPDATE_FAILED("update_failed");

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
    return "jp.co.sint.webshop.web.message.back.Messages";
  }

}
