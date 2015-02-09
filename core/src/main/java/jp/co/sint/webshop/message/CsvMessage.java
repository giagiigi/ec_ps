package jp.co.sint.webshop.message;

public enum CsvMessage implements MessageType {

  /** 数値不正エラー */
  DIGIT("digit"),

  /** 日付不正エラー */
  DATE("date"),

  /** 日付不正エラー */
  DATETIME("datetime"),

  /** 日付不正エラー */
  DATETIMEERR("datetimeErr"),

  // 10.1.1 10006 追加 ここから
  /** 日付不正エラー */
  NOT_IN_RANGE("not_in_range"),  
  // 10.1.1 10006 追加 ここまで

  /** CSV項目値不正エラー */
  WRONG_VALUE("wrong_value"),

  // 10.1.1 10019 追加 ここから
  /** マイナス値入力エラー */
  MINUS_NUMBER_ERROR("minus_number_error"),  
  // 10.1.1 10019 追加 ここまで
  //20120209 os013 add start
  /**必须成对*/
  REQUEST_PAIR("request_pair"),
  /**必须输入*/
  REQUEST_ITEM("request_item"),
  /**カテゴリコード必须至少输入一个*/
  CATEGORY_CODE_REQUEST_ITEM("category_code_request_item"),
  /**不可输入*/
  NO_REQUEST_ITEM("no_request_item"),
  /**代表SKUコード与SKUコード必须一致*/
  REPRESENT_SKU_CODE_AND_SKU_CODE("represent_sku_code_and_sku_code"),
  /**商品说明2长度必须大于5*/
  COMMODITY_DESCRIPTION2_LENGTH_REQUEST_GREATER("commodity_description2_length_request_greater"),
  //20120209 os013 add end
  // 10.1.3 10062 追加 ここから
  /** マイナス値入力エラー */
  NOT_INPUT_RANGE("not_input_range"),  
  // 10.1.3 10062 追加 ここまで
  //20120217 os013 add start
  /**导入csv文件时更新区分不能为1或0以外的字符*/
  IMPORT_FLAG("import_flag"),
  //20120217 os013 add end
  /** 出力CSV非存在エラー */
  CSV_NOT_FOUND("csv_not_found"),

  /** ショップコードエラー */
  VALIDATE_SHOPCODE("validate_shopcode"),

  /** 宅配便伝票番号未入力エラー */
  DELIVERY_SLIP_NO_REQUIRED("delivery_slip_no_required"),

  /** 本人電話番号未入力エラー */
  SELF_PHONE_NO_REQUIRED("self_phone_no_required"),
  
  //Add by V10-CH start
  /** 本人手機番号未入力エラー */
  SELF_MOBILE_NO_REQUIRED("self_mobile_no_required"),
  //Add by V10-CH end
  //20120309 os013 add start
  //必须大于等于某数
  REQUEST_GREATER_THAN("request_greater_than"),
  //必须等于
  REQUEST_EQUAL("request_equal"),
  //20120309 os013 add end
  /** 売上確定済みエラー(出荷番号表示) */
  FIXED_DATA("fixed_data"),

  /** 出荷データ連携済エラー */
  SHIPPING_DATA_TRANSPORTED("shipping_data_transported"),

  /** 出荷日不正エラー(受注日以前) */
  SHIPPINGDATE_BEFORE_ORDERDATE("shippingdate_before_orderdate"),

  /** 出荷日不正エラー(出荷指示日以前) */
  SHIPPINGDATE_BEFORE_DIRECTDATE("shippingdate_before_directdate"),

  /** 出荷日不正エラー(到着予定日以後) */
  ARRIVALDATE_BEFORE_SHIPPINGDATE("arrivaldate_before_shippingdate"),

  /** 出荷指示日不正エラー(受注日以前) */
  SHIPPING_DIRECT_BEFORE_ORDERDATE("shipping_direct_before_orderdate"),

  /** 到着予定時間未入力エラー(片方のみ入力されている場合) */
  ARRIVAL_TIME_BOTH_REQUIRED("arrival_time_both_required"),

  /** 到着予定時間大小関係不正エラー */
  ARRIVAL_TIME_COMPARISON_ERROR("arrival_time_comparison_error"),

  /** 未入金エラー */
  NO_PAY_AND_PAYMENT_ADVANCE("no_pay_and_payment_advance"),

  /** 返品またはキャンセルエラー */
  CANCELL_OR_RETURN("cancell_or_return"),

  /** 出荷情報予約エラー */
  SHIPPING_IS_RESERVED("shipping_is_reserved"),

  /** 出荷情報返品エラー */
  SHIPPING_IS_RETURNED("shipping_is_returned"),

  /** 入金情報削除エラー(先払い・出荷済) */
  DELETE_PAYMENT_IS_SHIPPED("delete_payment_is_shipped"),

  /** 入金情報キャンセル済エラー */
  PAYMENT_IS_CANCELLED("payment_is_cancelled"),

  /** 受注情報キャンセル済エラー */
  SHIPPING_IS_CANCELLED("shipping_is_cancelled"),
  // 10.1.4 10183 追加 ここから 
  /** 出荷情報未手配エラー */
  SHIPPING_IS_NOT_IN_PROCESSING("shipping_is_not_in_processing"),
  // 10.1.4 10183 追加 ここまで 

  /** 本人アドレス登録不可エラー */
  SELF_ADDRESS("self_address"),

  /** 退会済顧客登録不可エラー */
  WITHDRAWED("withdrawed"),

  /** 退会済顧客アドレス登録不可エラー */
  WITHDRAWED_ADDRESS("withdrawed_address"),

  /** 都道府県コード不整合エラー */
  PREFECTURE_MISMATCH("prefecture_mismatch"),

  /** メールアドレス重複エラー */
  DUPLICATED_MAIL("duplicated_mail"),

  /** ログインID重複エラー */
  DUPLICATED_LOGINID("duplicated_loginid"),

  /** 生年月日エラー */
  BIRTHDAY("birthday"),

  /** 非存在エラー */
  NOT_EXIST("not_exitst"),
  //20120206 os013 add start
  /** 商品编号非存在エラー */
  COMMODITY_CODE_NOT_EXIST("commodity_code_not_exitst"),

  /** 商品编号存在エラー */
  COMMODITY_CODE_EXIST("commodity_code_exitst"),
  
  /** カテゴリ属性番号非存在エラー*/
  CATEGORY_ATTRIBUTE_NOT_EXIST("category_attribute_not_exitst"),
  
  /** カテゴリ属性番号非存在エラー*/
  CATEGORY_ATTRIBUTE_NO_NOT_EXIST("category_attribute_no_not_exitst"),
  
  /** SKU编号存在エラー*/
  SKU_CODE_EXIST("sku_code_exitst"),
  
  /** SKU编号非存在エラー*/
  SKU_CODE_NOT_EXIST("sku_code_not_exitst"),
  //20120206 os013 add end
  /** カテゴリツリー再構築エラー */
  REBUILDE_CATEGORY_TREE_FAILED("rebuild_category_tree_failed"),

  /** カテゴリツリー再構築成功 */
  REBUILDE_CATEGORY_TREE_SUCCEEDED("rebuild_category_tree_succeeded"),

  /** 規格名称不正エラー */
  FAULT_STANDARD_NAME("fault_standard_name"),

  /** 入出庫取込失敗エラー */
  IMPORT_STOCK_IO_FAILED("import_stock_io_failed"),

  /** 在庫数上限値オーバーエラー */
  STOCK_AMOUNT_OVERFLOW("stock_amount_overflow"),

  /** 在庫変更可能値オーバーエラー(入庫) */
  STOCK_NO_CHANGEABLE("stock_no_changeable"),

  // 10.1.4 10159 削除 ここから
//  /** 在庫変更可能値オーバーエラー(出庫) */
//  STOCK_DELIVER_OVERFLOW("stock_deliver_overflow"),
  // 10.1.4 10159 削除 ここまで

  STOCK_QUANTITY_INVALID("stock_quantity_invalid"),

  /** 商品削除エラー */
  DELETE_COMMODITY_ERROR("delete_commodity_error"),

  /** カテゴリ階層最大値オーバー */
  CATEGORY_MAX_DEPTH_OVER("category_max_depth_over"),

  /** クレジット決済エラー */
  SET_PAYMENT_CREDIT("set_payment_credit"),

  /** 全額ポイント支払いエラー */
  SET_PAYMENT_POINT_IN_FULL("set_payment_point_in_full"),

  /** 支払不要エラー */
  SET_PAYMENT_NO_PAYMENT("set_payment_no_payment"),

  /** 自身のカテゴリコードと親カテゴリの一致 */
  CHANGE_CATEGORY_OWN("change_category_own"),
  
  // M17N 10361 追加 ここから
  /** 仮受注出荷エラー */
  SHIPPING_IS_PHANTOM("shipping_is_phantom"),
  
  /** 仮受注入金日設定エラー(受注番号表示) */
  SET_PAYMENT_DATE_PHANTOM_ORDER_WITH_NO("set_payment_date_phantom_order_with_no"),
  // M17N 10361 追加 ここまで
  
  MOBILE_OR_PHONE_REQUIRED("mobile_or_phone_required"),
  
  /** 电话号码与手机至少填入一项 */
  NO_NUMBER("no_number"),
  
  /** 错误电话号码 */
  FALSE_PHONE("false_phone"),
  
  // 10.1.5 10242 追加 ここから
  /** 範囲外エラー * */
  OUT_OF_RANGE("out_of_range"),
  // 10.1.5 10242 追加 ここまで

  // 10.1.5 10243 追加 ここから
  /** 電話番号形式エラー * */
  INVALID_PHONE_FORMAT("invalid_phone_format"),
  // 10.1.5 10243 追加 ここまで
  
  // 10.1.6 10258 追加 ここから
  /** 重複エラー */
  OVERLAPPED_VALUES("overlapped_values"),
  // 10.1.6 10258 追加 ここまで
  
  /** 错误手机号码 */ 
  FALSE_MOBILE("false_phone");

  private String propertyId;

  private CsvMessage(String propertyId) {
    this.propertyId = propertyId;
  }

  public String getMessagePropertyId() {
    return propertyId;
  }

  public String getMessageResource() {
    return "jp.co.sint.webshop.message.impl.CsvErrorMessages";
  }

}
