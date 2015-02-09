package jp.co.sint.webshop.web.message.front.order;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum OrderErrorMessage implements MessageType {

  /** 全額ポイント払いメッセージ */
  POINT_IN_FULL("point_in_full"),
  /** URLパラメータ不正エラーメッセージ */
  WRONG_URL_PARAMETER("wrong_url_parameter"),
  /** ポイント不足エラーメッセージ */
  ABSENCE_POINT("absence_point"),
  // 10.1.4 10201 追加 ここから
  /** 使用可能ポイント数エラー */
  OVER_USABLE_POINT("over_usable_point"),
  // 10.1.4 10201 追加 ここまで
  /** 追加商品必須エラーメッセージ */
  REQUIRED_ADDITIONAL_COMMODITY("required_additional_commodity"),
  /** 追加お届け先不正エラーメッセージ */
  WRONG_ADDITIONAL_DELIVERY("wrong_additional_delivery"),
  /** 同一お届け先変更エラーメッセージ */
  CHANGE_SAME_DELIVERY("change_same_delivery"),
  /** 全商品削除エラーメッセージ */
  DELETE_ALL_COMMODITY("delete_all_commodity"),
  /** 全お届け先削除エラーメッセージ */
  DELETE_ALL_DELIVERY("delete_all_delivery"),
  /** 在庫引当エラー(在庫切れ) */
  STOCK_ALLOCATE_DEFAULT_ERROR("stock_allocate_default_error"),
  /** 在庫引当エラー(在庫不足) */
  STOCK_SHORTAGE_ERROR("stock_shortage_error"),
  // 10.1.2 10110 追加 ここから
  /** 在庫引当エラー(排他制御の衝突) */
  STOCK_CONCURRENCY_CONTROL_ERROR("stock_concurrency_control_error"),
  // 10.1.2 10110 追加 ここまで
  /** 合計数在庫引当エラー(在庫不足) */
  TOTAL_STOCK_SHORTAGE_ERROR("total_stock_shortage_error"),
  /** 予約不可エラー */
  NOT_AVAILABLE("not_available"),
  /** アンケート登録失敗エラーメッセージ */
  ENQUETE_REGISTER_FAILED("enquete_register_failed"),
  /** 与信失敗（カードによる）エラーメッセージ */
  CARD_AUTHORIZATION_INCOMPLETE_ERROR("card_authorization_incomplete_error"),
  /** 商品購入エラー時のリカバリメッセージ */
  RESTART_CASHIER("restart_cashier"),
  /** 予約上限数オーバーエラー */
  RESERVATION_OVER("reservation_over"),
  /** 合計予約上限数オーバーエラー */
  TOTAL_RESERVATION_OVER("total_reservation_over"),
  /** カート商品追加エラー */
  ADD_CART("add_cart"),
  // 10.1.4 10177 追加 ここから
  /** まとめてカート数量チェックエラー */
  AMOUNT_REQUIRED_ERROR("amount_required_error"),
  // 10.1.4 10177 追加 ここまで
  /** 決済情報認証失敗エラー */
  SETTELMENT_INCOMPLETE_INVALID_PARAMETER("settelment_incomplete_invalid_parameter"),
  /** 期間外エラー(商品名指定) */
  OUT_OF_PERIOD_WITH_NAME("out_of_period_with_name"),
  /** 商品未存在エラー */
  NOT_EXIST("not_exist"),
  // 2012/11/23 促销对应 ob add start
  MULTIPLE_ACCEPT_ERROR("multiple_accept_error"),
  MULTIPLE_GIFT_ACCEPT_ERROR("multiple_gift_accept_error"),
  MULTIPLE_GIFT_STOCK_ERROR("multiple_gift_stock_error"),
  COMMODITY_COUPON_ERROR("commodity_coupon_error"),
  COMMODITY_COUPON_DETAIL_ERROR("commodity_coupon_detail_error"),
  COMMODITY_COUPON_USE_LIMIT_ERROR("commodity_coupon_use_limit_error"),
  COMMODITY_COUPON_MAX_COMMODITY_NUM_ERROR("commodity_coupon_max_commodity_num_error"),
  COMMODITY_COUPON_AND_DISCOUNT_DUPLICATE_ERROR("commodity_coupon_and_discount_duplicate_error"),
  // 2012/11/23 促销对应 ob add end
  // 2013/04/07 优惠券对应 ob add start
  DISCOUNT_DUPLICATE_ERROR("discount_duplicate_error"),
  USE_COUPON_OF_ISSUE_BY_SELF_ERROR("use_coupon_of_issue_by_self_error"),
  // 2013/04/07 优惠券对应 ob add end
  /** 表示可能なお支払い方法が代金引換のみの場合に、自宅以外のお届け先を選択したときのエラー */
  CASH_ON_DELIVERY_SELECT_ERROR("cash_on_delivery_select_error"),
  /** 表示可能なお支払い方法が代金引換のみかつ、お支払い金額のすべてをポイントでお支払いできない場合に、自宅以外のお届け先を選択したときのエラー */
  CASH_ON_DELIVERY_SELECT_AND_NO_POINT_ERROR("cash_on_delivery_select_and_no_point_error"),
  /** お支払い方法の存在エラー */
  NO_PAYMENT_METHOD_ERROR("no_payment_method_error"),
  /** 予約商品複数配送先追加エラー */
  ADD_RESERVE_PLURAL_SHIPPING_ERROR("add_reserve_plural_shipping_error"),
  /** 注文合計金額上限エラー */
  TOTAL_AMOUNT_OVER("total_amount_over"),
  /** ゲスト別配送先指定エラー */
  //add by zhanghaibin start 2010-5-18
  /** 使用积分小于积分最小使用单位 */
  POINT_SMALL_NOT_ERROR("point_small_not_error"),
  //add by zhanghaibin end   2010-5-18
  GUEST_OTHER_DELIVERY_ERROR("guest_other_delivery_error"),
  
  IS_USED_COUPON("is_used_coupon"),
  
  OUT_OF_TIME_COUPON("out_of_time_coupon"),
  
  MAX_USEABLE_COUPON_COUNT("max_useable_coupon_count"),
  
  SELECT_USEABLE_COUPON("select_useable_coupon");

  private String messagePropertyId;

  private OrderErrorMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.front.order.OrderMessages";
  }

}
