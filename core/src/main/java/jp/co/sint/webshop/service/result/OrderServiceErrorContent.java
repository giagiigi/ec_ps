package jp.co.sint.webshop.service.result;

/**
 * OrderService内で発生するエラーEnum
 */
public enum OrderServiceErrorContent implements ServiceErrorContent {
  /** 入力値妥当性エラー */
  INPUT_ADEQUATE_ERROR,
  /** 使用ポイントエラー */
  USEABLE_POINT_ERROR,
  // 10.1.4 10201 追加 ここから
  /** 使用可能ポイント数エラー */
  OVER_USABLE_POINT_ERROR,
  // 10.1.4 10201 追加 ここまで
  /** 在庫引当エラー */
  STOCK_ALLOCATE_ERROR,
  /** 受注データ削除エラー */
  SHIPPING_DATA_DELETE_ERROR,
  /** 入金日設定エラー */
  PAYMENT_DATE_SET_ERROR,
  /** 入金日入金済エラー */
  ALREADY_SET_CLEAR_PAYMENT_DATE,
  /**订单下载中*/
  ORDER_DOWNLOADING,
  /**订单下载接口失败*/
  ORDER_DOWN_INTERFACE_FAILED,
  /**订单下载接收数据为空*/
  ORDER_DOWN_INTERFACE_DATA_NULL,
  /**订单下载接口时间设置不能超出一天*/ 
  ORDER_DOWN_TIME_UP_ONE_DAY,
  /**订单下载起始时间不能超过执行时间*/ 
  ORDER_DOWN_STARTTIME_UP_ENDTIME,
  /**库存再分配失败*/
  STOCK_ALLOCATE_FAILED, 
  /**淘宝SKU上传失败*/
  TAMLL_SKU_UPLOAD_FAILED, 
  /** 与信失敗エラー */
  AUTHORIZATION_INCOMPLETE_ERROR,
  /** 決済失敗エラー */
  CARD_SETTLEMENT_IMCOMPLETE_ERROR,
  /** 受注キャンセルエラー */
  ORDER_CANCEL_ERROR,
  /** 該当受注データキャンセル済エラー */
  ALREADY_CANCELED_ERROR,
  /** キャンペーン未存在エラー */
  CAMPAIGN_FAILED,
  /** 売上確定済みエラー */
  FIXED_DATA_ERROR,
  /** データ連携済エラー */
  DATA_TRANSPORT_ERROR,
  /** 出荷日エラー(受注日以前) */
  SHIPPING_DATE_BEFORE_ORDERDATE_ERROR,
  /** 出荷日エラー(出荷指示日以前) */
  SHIPPING_DATE_BEFORE_SHIPPING_DIRECT_ERROR,
  /** 先払いかつ未入金エラー */
  NO_PAY_AND_PAYMENT_ADVANCE_ERROR,
  /** 出荷データ非存在エラー */
  NO_SHIPPING_DATA_ERROR,
  /** 受注データ非存在エラー */
  NO_ORDER_DATA_ERROR,
  /** 出荷データ予約エラー */
  SHIPPING_IS_RESERVED_ERROR,
  /** 未出荷エラー */
  NOT_SHIPPED_ERROR,
  /** 商品無し返品Validationエラー */
  NO_ITEM_RETURN_VALIDATION_ERROR,
  /** 支払方法削除済みエラー */
  PAYMENT_METHOD_NOT_FOUND_ERROR,
  // soukai add 2012/01/09 ob start
  /** 淘宝连携失败 */
  TMALL_ACCESS_ERROR,
  /** 优惠信息不存在 */
  DISCOUNT_INFO_NOT_EXITS_ERROR,
  /** 已使用 */
  DISCOUNT_COUPON_USED_ERROR,
  /** 已超过最大回数*/
  DISCOUNT_COUNT_OVER_ERROR,
  // 2013/04/18 优惠券对应 ob add start
  /** 顾客本人发行*/
  DISCOUNT_COUNT_ISSUE_BY_MYSELF_ERROR,
  // 2013/04/18 优惠券对应 ob add end
  /** 最小金额错误 */
  DISCOUNT_MIN_ORDER_PRICE_ERROR,
  /** 订单取消不可 */
  ORDER_CANCEL_DISABLED,
  // soukai add 2012/01/09 ob end
  /** 予約注文変更エラー */
  NOT_RESERVATION_CHANGEABLE;
  
}
