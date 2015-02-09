package jp.co.sint.webshop.web.message.back.order;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum OrderErrorMessage implements MessageType {

  /** 全額ポイント払いメッセージ */
  POINT_IN_FULL("point_in_full"),
  /** 返品数＞購入個数エラー */
  RETURN_OVER("return_orver"),
  /** 入金済受注情報 */
  STILL_PAID("still_paid"),
  /** 入金済受注情報(受注番号表示) */
  PAID_ORDER_WITH_NO("paid_order_with_no"),
  /** 出荷手配済・出荷済受注情報 */
  STILL_SHIPPING_ORDER("still_shipping_order"),
  /** 配送指定日が日付として正しくない */
  DELIVERY_APPOINTED_DATE("delivery_appointed_date"),
  /** 配送指定日が過去日付エラー */
  PAST_DELIVERY_APPOINTED_DATE("past_delivery_appointed_date"),
  /** 配送指定時間が片方しか入力されていない */
  DELIVERY_APPOINTED_TIME_BOTH("delivery_appointed_time_both"),
  /** 商品追加先配送未存在エラー */
  ADD_COMMODITY_SHIPPING_NOT_FOUND("add_commodity_shipping_not_found"),
  /** 配送先未設定時エラー */
  NO_SHIPPING("no_shipping"),
  /** データ連携済エラー */
  DATA_TRANSPORTED("data_transported"),
  /** 受注データ連携済エラー */
  ORDER_DATA_TRANSPORTED_WITH_NO("order_data_transported_with_no"),
  /** 出荷データ連携済エラー */
  SHIPPING_DATA_TRANSPORTED_WITH_NO("shipping_data_transported_with_no"),
  /** 使用可能ポイント数エラー */
  OVER_USABLE_POINT("over_usable_point"),
  /** 全配送先削除エラーメッセージ */
  DELETE_ALL_DELIVERY("delete_all_delivery"),
  /** 全商品削除エラーメッセージ */
  DELETE_ALL_COMMODITY("delete_all_commodity"),
  /** 出荷情報削除エラー */
  DELETE_SHIPPING_DATA("delete_shipping_data"),
  /** 同一お届け先変更エラーメッセージ */
  CHANGE_SAME_DELIVERY("change_same_delivery"),
  /** 予約商品複数配送先追加エラー */
  ADD_RESERVE_PLURAL_SHIPPING_ERROR("add_reserve_plural_shipping_error"),
  /** 売上確定済みエラー */
  FIXED_DATA("fixed_data"),
  /** 売上確定済みエラー(出荷番号表示) */
  FIXED_DATA_WITH_NO("fixed_data_with_no"),
  /** 別決済同時注文エラー（ショップ個別決済・予約商品） */
  ADD_RESERVE_PAYMENT_NOT_FOUND_SHOP("add_reserve_payment_not_found_shop"),
  /** 別決済同時注文エラー（ショップ個別決済以外・予約商品） */
  ADD_RESERVE_PAYMENT_NOT_FOUND("add_reserve_payment_not_found"),
  /** 別決済同時注文エラー（ショップ個別決済・販売商品） */
  ADD_SALES_PAYMENT_NOT_FOUND_SHOP("add_sales_payment_not_found_shop"),
  /** 別決済同時注文エラー（ショップ個別決済以外・販売商品） */
  ADD_SALES_PAYMENT_NOT_FOUND("add_sales_payment_not_found"),
  /** 配送先都道府県変更エラー */
  CHANGE_PREFECTURE_SHIPPING("change_prefecture_shipping"),
  /** 受注ステータス未確定エラー */
  SHIPPING_DATA_SALES_STATUS_NOT_FIXED("shipping_data_sales_status_not_fixed"),
  /** 入金日クリア失敗エラー */
  CLEAR_PAYMENT_DATE_FAILED_WITH_NO("clear_payment_date_failed_with_no"),
  /** 入金日取消エラー */
  NOT_SET_CLEAR_PAYMENT_DATE("not_set_clear_payment_date"),
  /** 入金日入金済エラー */
  ALREADY_SET_CLEAR_PAYMENT_DATE("already_set_clear_payment_date"),
  /** 受注データ未入金エラー */
  NOT_PAID_ORDER_WITH_NO("not_paid_order_with_no"),
  /** 出荷ステータス変更不可エラー */
  FIXED_SHIPPING_STATUS_WITH_NO("fixed_shipping_status_with_no"),
  /** 出荷取り消し失敗エラー */
  CLEAR_SHIPPING_FAILED_WITH_NO("clear_shipping_failed_with_no"),
  // 10.1.4 10188 追加 ここから
  /** 出荷取り消し失敗エラー(売上送信済み) */
  CLEAR_SHIPPING_FAILED_FOR_TRANSPORTED("clear_shipping_failed_for_transported"),
  // 10.1.4 10188 追加 ここまで
  /** 出荷指示失敗エラー */
  SHIPPING_DIRECT_FAILED_WITH_NO("shipping_direct_failed_with_no"),
  /** 出荷実績登録失敗エラー */
  SHIPPED_RESULT_FAILED_WITH_NO("shipped_result_failed_with_no"),
  /** 出荷指示不可エラー(出荷ステータス不正：NOT出荷可能） */
  IMPOSSIBILITY_SHIPPING_DIRECT("impossibility_shipping_direct"),
  /** 出荷指示日設定不可エラー(出荷ステータス不正：NOT出荷可能） */
  IMPOSSIBILITY_SHIPPING_DIRECT_DATE("impossibility_shipping_direct_date"),
  /** 出荷指示不可エラー(出荷指示日空） */
  IMPOSSIBILITY_SHIPPING_DIRECT_CAUSE_DATE("impossibility_shipping_direct_cause_date"),
  /** 出荷指示日更新エラー(出荷指示日一覧更新) */
  IMPOSSIBILITY_SHIPPING_DIRECT_DATE_CAUSE_LIST("impossibility_shipping_direct_date_cause_list"),
  /** 出荷登録不可エラー（出荷状況不正） */
  IMPOSSIBILITY_SHIPPING_REPORT("impossibility_shipping_report"),
  /** 配送指定日設定エラー(出荷指示日＜受注日） */
  IMPOSSIBILITY_APPOINTED_DATE_CAUSE_DIRECT_PAST("impossibility_appointed_date_cause_direct_past"),
  /** 出荷指示日クリアエラー(出荷済み) */
  IMPOSSIBILITY_CLEAR_SHIPPING_DIRECT_DATE("impossibility_clear_shipping_direct_date"),
  /** 出荷指示日クリアエラー(出荷手配済み) */
  IMPOSSIBILITY_CLEAR_SHIPPING_DIRECT_DATE_INPROCESS("impossibility_clear_shipping_direct_date_inprocess"),
  /** 出荷指示日クリアエラー(出荷指示日未設定) */
  IMPOSSIBILITY_CLEAR_SHIPPING_DIRECT_DATE_NOT_SET("impossibility_clear_shipping_direct_date_not_set"),
  /** 出荷日未来日付エラー */
  WILL_SHIPPING_REPORT_DATE("will_shipping_report_date"),
  /** 出荷指示時に出荷指示日が指定されているため、ステータスのみ更新 */
  DIRECT_WITHOUT_DATE("direct_without_date"),
  /** 出荷手配中ステータスエラー */
  NO_STATUS_SHIPPING_ARRANGE("no_status_shipping_arrange"),
  /** 出荷済ステータスエラー */
  NO_STATUS_SHIPPED("no_status_shipped"),
  /** 未入金エラー */
  NO_PAY_AND_PAYMENT_ADVANCE_WITH_NO("no_pay_and_payment_advance_with_no"),
  /** 出荷日不正エラー(受注日以前) */
  SHIPPING_DIRECT_INPUT_ORDERDATE_OR_LATER_WITH_NO("shipping_direct_input_orderdate_or_later_with_no"),
  /** 出荷日不正エラー(出荷指示日) */
  SHIPPING_INPUT_ORDERDATE_OR_LATER_WITH_NO("shipping_input_orderdate_or_later_with_no"),
  /** 出荷済みかつ先払いのため入金取り消しできませんエラー */
  SHIPPED_AND_PAYMENT_ADVANCE_WITH_NO("shipped_and_payment_advance_with_no"),
  /** 出荷メール送信先顧客が存在しないエラー */
  NO_CUSTOMER_TO_SEND_SHIPPING_MAIL_WITH_NO("no_customer_to_send_shipping_mail_with_no"),
  /** 受注メール送信失敗エラー */
  SEND_ORDER_MAIL_FAILED_WITH_NO("send_order_mail_failed_with_no"),
  /** 出荷メール送信に失敗しましたエラー */
  SEND_SHIPPING_MAIL_FAILED_WITH_NO("send_shipping_mail_failed_with_no"),
  /** 入金督促メール送信エラー（支払不要/全額ポイント払い） */
  SEND_REMINDER_MAIL_TO_NOT_PAY("send_reminder_mail_to_not_pay"),
  // Add by V10-CH start
  NO_CUSTOMER_TO_SEND_SHIPPING_SMS_WITH_NO("no_customer_to_send_shipping_sms_with_no"), SEND_ORDER_SMS_FAILED_WITH_NO(
      "send_order_sms_failed_with_no"), SEND_SHIPPING_SMS_FAILED_WITH_NO("send_shipping_sms_failed_with_no"), SEND_REMINDER_SMS_TO_NOT_PAY(
      "send_reminder_sms_to_not_pay"), SEND_SHIPPING_SMS_DATA_TRANSPORTED_WITH_NO("send_shipping_sms_data_transported_with_no"), SEND_SMS_DATA_TRANSPORTED(
      "send_sms_data_transported"),
  // Add by V10-CH end
  /** 出荷キャンセル済エラー */
  CANCELLED_SHIPPING_WITH_NO("cancelled_shipping_with_no"),
  /** 配送指定日時不正エラー */
  DELIVERY_APPOINT_ORDERDATE_OR_LATER_WITH_NO("delivery_appoint_orderdate_or_later_with_no"),
  /** 入金日不正エラー */
  PAYMENT_ORDERDATE_OR_LATER_WITH_NO("payment_orderdate_or_later_with_no"),
  /** 受注修正時支払方法がクレジットカードの場合のエラー */
  MODIFY_CREDIT_ERROR("modify_credit_error"),
  // M17N 10361 追加 ここから
  /** 受注修正時支払方法が外部決済の場合のエラー */
  MODIFY_EXTERNAL_PAYMENT_ERROR("modify_external_payment_error"),
  // M17N 10361 追加 ここまで
  /** 受注情報変更エラー */
  ORDER_CHANGED_ERROR("order_changed_error"),
  /** クレジットカードキャンセルエラー */
  CREDIT_CANCEL_ERROR("credit_cancel_error"),
  /** 商品未存在エラー(購入不可商品の場合も含む) */
  NOT_EXIST("not_exist"),
  /** 商品未存在エラー(購入不可商品の場合も含む) */
  NOT_EXIST_ADDITION("not_exist_addition"),
  // 10.1.6 10277 追加 ここから
  /** 在庫引当エラー(在庫切れ) */
  STOCK_ALLOCATE_DEFAULT_ERROR("stock_allocate_default_error"),
  // 10.1.6 10277 追加 ここまで
  /** 有効在庫数不足エラー */
  STOCK_SHORTAGE("stock_shortage"),
  /** 有効在庫数0エラー(SKUコード) */
  NO_AVAILABLE_STOCK_SKU("no_available_stock_sku"),
  // 10.1.2 10110 追加 ここから
  /** 在庫引当エラー(排他制御の衝突) */
  STOCK_CONCURRENCY_CONTROL_ERROR("stock_concurrency_control_error"),
  // 10.1.2 10110 追加 ここまで
  /** 出荷情報予約エラー */
  SHIPPING_IS_RESERVED("shipping_is_reserved"),
  /** 予約上限数オーバーエラー */
  RESERVATION_OVER("reservation_over"),
  /** 注文毎予約上限数オーバーエラー */
  ONESHOT_RESERVATION_OVER("oneshot_reservation_over"),
  /** 予約不可エラー */
  NOT_AVAILABLE("not_available"),
  /** 予約商品追加エラー(受注修正) */
  ORDER_MODIFY_ADD_RESERVE("order_modify_add_reserve"),
  /** 販売期間外エラー */
  OUT_OF_PERIOD("out_of_period"),
  /** キャンセル済み受注入金日設定エラー */
  SET_PAYMENT_DATE_CANCELED_ORDER("set_payment_date_canceled_order"),
  /** キャンセル済み受注入金日設定エラー(受注番号表示) */
  SET_PAYMENT_DATE_CANCELED_ORDER_WITH_NO("set_payment_date_canceled_order_with_no"),
  /** キャンセル済み受注エラー(受注番号表示) */
  SET_PAYMENT_CANCELED_ORDER_WITH_NO("set_payment_canceled_order_with_no"),
  /** クレジット決済受注エラー(受注番号表示) */
  SET_PAYMENT_CREDIT("set_payment_credit"),
  /** 返品済み出荷情報更新エラー */
  UPDATE_RETURNED_SHIPPING("update_returned_shipping"),
  /** データ連携中のためメールが送信できないエラー */
  SEND_SHIPPING_MAIL_DATA_TRANSPORTED_WITH_NO("send_shipping_mail_data_transported_with_no"),
  /** 返品損金額桁数エラー */
  RETURN_ITEM_LOSS_MONEY_LENGTH_OVER("return_item_loss_money_length_over"),
  /** データ連携済み入金日設定エラー */
  REGISTER_PAYMENT_DATA_TRANSPORTED("register_payment_data_transported"),
  /** データ連携済み入金日削除エラー */
  CLEAR_PAYMENT_DATA_TRANSPORTED("clear_payment_data_transported"),
  /** データ連携済みメール送信エラー */
  SEND_MAIL_DATA_TRANSPORTED("send_mail_data_transported"),
  /** 決済情報認証失敗エラー */
  SETTELMENT_INCOMPLETE_INVALID_PARAMETER("settelment_incomplete_invalid_parameter"),
  /** 退会済顧客エラー */
  WITHDRAWAL_CUSTOMER("withdrawal_customer"),
  /** 表示可能な支払方法が代金引換のみの場合に、自宅以外の配送先を選択したときのエラー */
  CASH_ON_DELIVERY_SELECT_ERROR("cash_on_delivery_select_error"),
  /** 表示可能な支払方法が代金引換のみかつ、支払金額のすべてをポイントで支払できない場合に、自宅以外のお届け先を選択したときのエラー */
  CASH_ON_DELIVERY_SELECT_AND_NO_POINT_ERROR("cash_on_delivery_select_and_no_point_error"),
  /** 支払方法の存在エラー */
  NO_PAYMENT_METHOD_ERROR("no_payment_method_error"),
  /** 支払方法更新エラー */
  CHANGE_MASTER_PAYMENT("change_master_payment"),
  /** 送料更新エラー */
  CHANGE_MASTER_SHIPPING("change_master_shipping"),
  /** 販売価格更新エラー */
  CHANGE_MASTER_COMMODITY("change_master_commodity"),
  /** ギフト更新エラー */
  CHANGE_MASTER_GIFT("change_master_gift"),
  /** 最新の支払手数料で画面情報を更新 */
  UPDATE_PAYMENT_USE_MASTER("update_payment_use_master"),
  /** ギフト数量変更エラー */
  CANNOT_CHANGE_GIFT_AMOUNT("cannot_change_gift_amount"),
  /** 追加商品桁数オーバー */
  ADD_COMMODITY_AMOUNT_OVER("add_commodity_amount_over"),
  /** ギフト削除済みエラー */
  UNUSABLE_GIFT("unusable_gift"),
  /** 負数入力エラー */
  INPUT_NEGATIVE_VALUE_ERROR("input_negative_value_error"),
  /** 追加お届け先不正エラーメッセージ */
  WRONG_ADDITIONAL_DELIVERY("wrong_additional_delivery"),
  /** 注文合計金額上限エラー */
  TOTAL_AMOUNT_OVER("total_amount_over"),
  // M17N 10361 追加 ここから
  /** 仮受注出荷エラー */
  SHIPPING_IS_PHANTOM("shipping_is_phantom"),
  /** 仮受注入金日設定エラー(受注番号表示) */
  SET_PAYMENT_DATE_PHANTOM_ORDER_WITH_NO("set_payment_date_phantom_order_with_no"),
  // M17N 10361 追加 ここまで
  /** ショップ非存在エラー */
  SHOP_NOT_FOUND("shop_not_found"),
  /** 予約注文変更エラー */
  NOT_RESERVATION_CHANGE("not_reservation_change"),

  MAX_USEABLE_COUPON_COUNT("max_useable_coupon_count"),

  POINT_SMALL_NOT_ERROR("point_small_not_error"),
  // soukai add 2012/02/04 ob start
  /** 已付款订单取消不可 */
  ORDER_CANCEL_DISABLED("order_cancel_disabled"),
  //soukai add 2012/02/04 ob end
  //2012/11/22 促销对应 新建订单_商品选择  ob add start
  /** 您要参加的活动已过期或者您的订单已不再满足活动条件*/
  MULTIPLE_GIFT_BUY_ERROR("multiple_gift_buy_error"),
  /** 单次购买同一活动的同一商品只可领取一次*/
  SAME_CAMPAIGN_MULTIPLE_GIFT_BUY_ERROR("same_campaign_multiple_gift_buy_error"),
  /** 库存数量不足，{0}的当前库存数只允许购买{1}个*/
  TOTAL_STOCK_SHORTAGE_ERROR("total_stock_shortage_error"),
  /** 套餐商品{0}不存在或者已被删除。*/
  SET_COMMODITY_COMPOSITION_NOT_EXIST_ERROR("set_commodity_composition_not_exist_error"),
  /** 折扣券编号为空 */
  DISCOUNT_CODE_NULL_ERROR("discount_code_null_error"),
  /** 折扣券不存在或已过期 */
  DISCOUNT_NOT_EXIST("discount_not_exist"),
  /** 折扣券与订单礼品券只能使用一种 */
  DISCOUNT_USE_ONLY("discount_use_only"),
  /** 折扣券_对象商品限定件数*/
  DISCOUNT_MAX_COMMODITY_ERROR("discount_max_commodity_error"),
  /** 折扣券最大使用回数*/
  DISCOUNT_MAX_USED_ERROR("discount_max_used_error"),
  /** 选择的折扣券无法使用*/
  DISCOUNT_CANNT_USED_ERROR("discount_cannt_used_error"),
  //2012/11/22 促销对应 新建订单_商品选择  ob add end
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
    return "jp.co.sint.webshop.web.message.back.order.OrderMessages";
  }

}
