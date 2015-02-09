package jp.co.sint.webshop.web.message.back.shop;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum ShopErrorMessage implements MessageType {

  /** 配送種別コードエラー */
  CODE_FAILED("code_failed"),
  /** 時間エラー(開始時間＞終了時間) */
  DELIVERY_TYPE_START_END_ERROR("delivery_type_start_end_error"),
  /** ポイント付与率マイナスエラー */
  POINT_RATE_MINUS_ERROR("point_rate_minus_error"),
  /** ボーナス時ポイント付与率マイナスエラー */
  BONUS_POINT_RATE_MINUS_ERROR("bonus_point_rate_minus_error"),
  /** サイト未登録エラー */
  NO_SITE_ERROR("no_site_error"),
  /** 支払方法非存在エラー */
  NO_PAYMENT_ERROR("no_payment_error"),
  /** 削除対象配送種別使用中エラー */
  DELIVERY_TYPE_STILL_USE_ERROR("delivery_type_still_use_error"),
  /** 削除対象配送種別削除不可エラー */
  USED_DELIVERY_TYPE("used_delivery_type"),
  /** 配送リードタイム最小値エラー */
  LEAD_TIME_MINIMAL_ERROR("lead_time_minimal_error"),
  /** 支払方法削除不可エラー */
  UNDELETABLE_PAYMENT("undeletable_payment"),
  /** メールテンプレート項目必須エラー */
  REQUIRED_TAG("required_tag"),
  /** 表示可能な支払方法エラー */
  PAYMENT_UNAVAILABLE("payment_unavailable"),
  /** 銀行全削除エラー */
  DELETE_ALL_BANK("delete_all_bank"),
  /** 邮局全削除エラー */
  DELETE_ALL_POST("delete_all_post"),
  // soukai add 2011/12/21 ob start
  /** 配送时间段未指定错误 */
  APPOINTED_TIME_ERROR("appointed_time_error"),
  /** 配送时间段大小错误 */
  APPOINTED_TIME_START_END_ERROR("appointed_time_start_end_error"),
  /** COD区分指定错误 */
  COD_TYPE_ERROR("cod_type_error"),
  /** COD区分指定错误 */
  COD_TYPE_ALL_ERROR("cod_type_all_error"),
  // soukai add 2011/12/21 ob end
  /** 手数料重複エラー */
  REGISTERED_COMMISSION("registered_commission"),
  /** 商品重量最小值大于最大值错误 */
  WEIGHT_START_END_ERROR("weight_start_end_error");

  private String messagePropertyId;

  private ShopErrorMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.back.shop.ShopMessages";
  }

}
