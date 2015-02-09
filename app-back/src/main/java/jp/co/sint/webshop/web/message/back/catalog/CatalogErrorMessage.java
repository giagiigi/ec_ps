package jp.co.sint.webshop.web.message.back.catalog;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum CatalogErrorMessage implements MessageType {

  /** SKUコードエラー */
  CODE_FAILED("code_failed"),

  /** ルートカテゴリ削除不可エラー */
  ROOT_CATEGORY_DELETE_ERROR("root_category_delete_error"),

  /** ルートカテゴリの親カテゴリ変更不可エラー */
  ROOT_CATEGORY_PARENT_CHANGE_ERROR("root_category_parent_change_error"),

  /** 自身の子カテゴリへの移動不可エラー */
  CHANGE_CATEGORY_OWN_CHILD_ERROR("change_category_own_child_error"),

  /** 親カテゴリの自分自身への変更不可エラー */
  CHANGE_CATEGORY_OWN_ERROR("change_category_own_error"),

  /** 商品削除エラー */
  DELETE_COMMODITY_ERROR("delete_commodity_error"),

  /** 商品削除エラー(SKU) */
  DELETE_SKU_ERROR("delete_sku_error"),
  // 10.1.7 10327 追加 ここから
  /** 規格数変更エラー */
  CHANGE_STANDARD_COUNT_ERROR("change_standard_count_error"),
  // 10.1.7 10327 追加 ここまで
  /** 入荷お知らせ削除失敗エラー */
  ARRIVAL_GOODS_DELETE_ERROR("arrival_goods_delete_error"),

  /** 初期化データ存在エラー */
  INIT_DATA_ERROR("init_data_error"),

  /** 日時大小不正エラー */
  DATE_RANGE_ERROR("date_period_error"),

  /** 価格改定日時エラー */
  PRICE_REVISION_DATE_ERROR("price_revision_date_error"),

  /** 在庫管理区分未設定エラー */
  STOCK_STATUS_SET_ERROR("stock_status_set_error"),

  /** 期間未設定エラー */
  SET_PERIOD_ERROR("set_period_error"),

  /** 不正規格詳細名称設定エラー */
  FAULT_STANDARD_DETAIL_NAME_SET_ERROR("fault_standard_detail_name_set_error"),

  /** 不正規格名称設定エラー */
  FAULT_STANDARD_NAME_SET_ERROR("fault_standard_name_set_error"),

  /** 新規SKU登録エラー */
  REGISTER_SKU_ERROR("register_sku_error"),

  /** 規格名称2設定エラー */
  STANDARD_NAME2_SET_ERROR("standard_name2_set_error"),

  /** 規格名称未設定エラー */
  STANDARD_NAMES_SET_ERROR("standard_names_set_error"),

  /** 配送種別未登録エラー */
  NO_AVAILABLE_DELIVERY_TYPE_ERROR("no_available_delivery_type_error"),

  /** 在庫数上限値オーバーエラー */
  STOCK_AMOUNT_OVERFLOW_ERROR("stock_amount_overflow_error"),

  /** 在庫変更可能値オーバーエラー(在庫) */
  STOCK_ABSOLUTE_OVERFLOW_ERROR("stock_absolute_overflow_error"),

  /** 在庫変更可能値オーバーエラー(出庫) */
  STOCK_DELIVER_OVERFLOW_ERROR("stock_deliver_overflow_error"),

  /** 在庫変更可能値オーバーエラー(出庫) */
  STOCK_NO_CHANGEABLE_ERROR("stock_no_changeable_error"),

  /** 入出庫数0エラー */
  STOCK_QUANTITY_ERROR("stock_quantity_error"),

  /** 商品詳細カート最上位表示エラー */
  COMMODITY_LAYOUT_TOP_PARTS_ERROR("commodity_layout_top_parts_error"),

  /** 代表SKU未存在エラー */
  NOT_REPRESENT_SKU_ERROR("not_represent_sku_error"),

  /** カテゴリパス更新エラー */
  UPDATE_CATEGORY_PATH_ERROR("update_category_path_error"),

  /** カテゴリ階層最大値オーバーエラー */
  CATEGORY_MAX_DEPTH_OVER_ERROR("category_max_depth_over_error"),

  /** 規格名称重複エラー */
  STANDARD_NAME_DUPLICATED_ERROR("standard_name_duplicated_error"),
  
  // 10.1.6 10258 追加 ここから
  /** 重複エラー */
  OVERLAPPED_VALUES("overlapped_values"),
  
  /** 規格数不正エラー */
  FAULT_STANDARD_COUNT_ERROR("fault_standard_count_error"),
  // 10.1.6 10258 追加 ここまで

  /** 入出庫事由未入力エラー */
  STOCK_IO_MEMO_REQUIRED_ERROR("stock_io_memo_required_error"),
  //20120105 os013 add start
  /**T-Mall的引当数大于库存数 */
  STOCK_ALLOCATEDTMALL_STOCKTMALL("stock_allocatedTmall_stockTmall"),
  /** 安全库存数量+EC引当数+Tmall引当数 */
  STOCK_STOCKTHRESHOLD_ALLOCATEDQUANTITY_ALLOCATEDTMALL("stock_stockthreshold_allocatedquantity_allocatedTmall"),
  /**库存在分配未成功*/
  STOCK_THE_ALLOCATION_WAS_NOT_SUCCESSFUL("stock_the_allocation_was_not_successful"),
  /**SKU编号未选择*/
  NOT_STOCK_SKU_SELECTED("not_stock_sku_selected"),
  /**订单下载中*/
  ORDER_DOWNLOADING("order_downloading"),
  //20120116 os013 add start
  SHARE_RATIO_NUMERICAL_SIZE("share_ratio_numerical_size"),
  //20120116 os013 add end
  //20120113 os011 add start
  /**订单下载失败*/
  TMALL_DOWNLOADING_ERROR("tmall_downloading_error"),
  /**上传淘宝失败**/
  TMALL_SKUUP_ERROR("tmall_skuup_error"),
  /**淘宝下载时间不能超过一天**/
  ORDER_DOWN_TIME_UP_ONE_DAY("tmall_download_time_error"),
//20120113 os011 add start
  //20120105 os013 add end,
  /**淘宝品牌不存在**/
  TMALL_BRAND_ERROR("tmall_brand_error"),
  /**淘宝分类不存在**/
  // 2012/11/21 促销对应 ob add start
  /**商品类型错误**/
  COMMODITY_TYPE_ERROR("commodity_type_error"),
  /**套餐明细件数错误**/
  NUMBER_OUT_ERROR("number_out_error"),
  /**套餐商品价格超出最大值**/
  PRICE_NUMBER_OUT_ERROR("price_number_out_error"),
  // 2012/11/21 促销对应 ob add end
  TMALL_CATEGORY_ERROR("tmall_category_error"),
  //单价不能小于最小单价 add by os014 2012-02-08
  SKU_DATE_COMPARABLE_ERROR_LESS("sku_date_comparable_error_less"),
  //价格必须等于希望售价 add by os014 2012-02-08
  SKU_DATE_COMPARABLE_ERROR_EQULSE("sku_date_comparable_error_equlse"),
  //必须大于 add by os014 2012-02-08
  SKU_DATE_COMPARABLE_ERROR_MORE("sku_date_comparable_error_more"),
  //必须大于 add by os014 2012-02-08
  // 20130614 txw add start
  TMALL_SCALE_ERROR("tmall_scale_error"),
  // 20130614 txw add end
  // 2014/06/06 库存更新对应 ob_卢 add start
  // 京东比例设定错误
  JD_SCALE_ERROR("jd_scale_error"),
  // 库存同期化失败邮件未发送成功
  SEND_MAIL_ERROR("send_mail_error"),
  // 2014/06/06 库存更新对应 ob_卢 add end
  SKU_DATE_MUSTEMPTY_ERROR("sku_date_mustempty_error");

  
  private String messagePropertyId;

  private CatalogErrorMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.back.catalog.CatalogMessages";
  }

}
