//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはコード定義ドキュメントから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.code;

import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「連番区分」のコード定義を表す列挙クラスです。
 * 
 * @author System Integrator Corp.
 */
public enum SequenceType implements CodeAttribute {

  /** 「アドレス帳番号」を表す値です。 */
  ADDRESS_NO("アドレス帳番号", "ADDRESS_NO_SEQ"),

  /** 「在庫状況番号」を表す値です。 */
  STOCK_STATUS_NO("在庫状況番号", "STOCK_STATUS_NO_SEQ"),
  /** 「临时库存番号」を表す値です。 */
  STOCK_TEMP_NO("临时库存番号", "STOCK_TEMP_SEQ"),
  /** 「消費税番号」を表す値です。 */
  TAX_NO("消費税番号", "TAX_NO_SEQ"),

  /** 「顧客属性番号」を表す値です。 */
  CUSTOMER_ATTRIBUTE("顧客属性番号", "CUSTOMER_ATTR_NO_SEQ"),

  /** 「顧客属性選択肢番号」を表す値です。 */
  CUSTOMER_ATTRIBUTE_CHOICE("顧客属性選択肢番号", "CUSTOMER_ATTR_CHOICES_NO_SEQ"),

  /** 「ポイント履歴ID」を表す値です。 */
  POINT_HISTORY_ID("ポイント履歴ID", "POINT_HISTORY_ID_SEQ"),

  /** 「顧客コード」を表す値です。 */
  CUSTOMER_CODE("顧客コード", "CUSTOMER_CODE_SEQ"),

  /** 「ゲストコード」を表す値です。 */
  GUEST_CODE("ゲストコード", "GUEST_CODE_SEQ"),

  /** 「地域ブロックID」を表す値です。 */
  REGION_BLOCK_ID("地域ブロックID", "REGION_BLOCK_ID_SEQ"),

  /** 「カテゴリ属性番号」を表す値です。 */
  CATEGORY_ATTRIBUTE_NO("カテゴリ属性番号", "CATEGORY_ATTRIBUTE_NO_SEQ"),

  /** 「アンケート設問番号」を表す値です。 */
  ENQUETE_QUESTION_NO("アンケート設問番号", "ENQUETE_QUESTION_NO_SEQ"),

  /** 「アンケート選択肢番号」を表す値です。 */
  ENQUETE_CHOICES_NO("アンケート選択肢番号", "ENQUETE_CHOICES_NO_SEQ"),

  /** 「お知らせ番号」を表す値です。 */
  INFORMATION_NO("お知らせ番号", "INFORMATION_NO_SEQ"),
  
  /** 「库存履历编号」を表す値です。 */
  STOCK_HISTORY_ID("库存履历编号", "STOCK_HISTORY_ID_SEQ"),

  /** 「ユーザコード」を表す値です。 */
  USER_CODE("ユーザコード", "USER_CODE_SEQ"),

  /** 「支払方法番号」を表す値です。 */
  PAYMENT_METHOD_NO("支払方法番号", "PAYMENT_METHOD_NO_SEQ"),

  /** 「管理側アクセスログ」を表す値です。 */
  USER_ACCESS_LOG_ID("管理側アクセスログ", "USER_ACCESS_LOG_ID_SEQ"),

  /** 「受注番号」を表す値です。 */
  ORDER_NO("受注番号", "ORDER_NO_SEQ"),
  /** 「发票抬头编号」を表す値です。 */
  ORDER_INVOICE_NO("发票抬头编号", "ORDER_INVOICE_SEQ"),
  /** 「出荷番号」を表す値です。 */
  SHIPPING_NO("出荷番号", "SHIPPING_NO_SEQ"),

  /** 「メールキューID」を表す値です。 */
  MAIL_QUEUE_ID("メールキューID", "MAIL_QUEUE_ID_SEQ"),

  // Add by v10-CH start
  SMS_QUEUE_ID("SMSID", "SMS_QUEUE_ID_SEQ"),
  // Add by V10-CH end

  /** 「レビューID」を表す値です。 */
  REVIEW_ID("レビューID", "REVIEW_ID_SEQ"),

  /** 「顧客嗜好ID」を表す値です。 */
  CUSTOMER_PREFERENCE_ID("顧客嗜好ID", "CUSTOMER_PREFERENCE_ID_SEQ"),

  /** 「個別配信メールキューID」を表す値です。 */
  RESPECTIVE_MAIL_QUEUE_ID("個別配信メールキューID", "RESPECTIVE_MAIL_QUEUE_ID_SEQ"),

  // Add by V10-CH start
  /** SMS */
  RESPECTIVE_SMS_QUEUE_ID("個別配信SMSID", "RESPECTIVE_SMS_QUEUE_ID_SEQ"),
  // Add by V10-CH end

  /** 「人気ランキング集計ID」を表す値です。 */
  POPULAR_RANKING_COUNT_ID("人気ランキング集計ID", "POPULAR_RANKING_COUNT_ID_SEQ"),

  /** 「アクセスログID」を表す値です。 */
  ACCESS_LOG_ID("アクセスログID", "ACCESS_LOG_ID_SEQ"),

  /** 「リファラーID」を表す値です。 */
  REFERER_ID("リファラーID", "REFERER_ID_SEQ"),

  /** 「商品別アクセスログID」を表す値です。 */
  COMMODITY_ACCESS_LOG_ID("商品別アクセスログID", "COMMODITY_ACCESS_LOG_ID_SEQ"),

  /** 「検索キーワードログID」を表す値です。 */
  SEARCH_KEYWORD_LOG_ID("検索キーワードログID", "SEARCH_KEYWORD_LOG_ID_SEQ"),

  /** 「入出庫行ID」を表す値です。 */
  STOCK_IO_ID("入出庫行ID", "STOCK_IO_ID_SEQ"),

  /** 「休日ID」を表す値です。 */
  HOLIDAY_ID("休日ID", "HOLIDAY_ID_SEQ"),

  /** 「決済サービス取引ID」を表す値です。 */
  PAYMENT_ORDER_ID("決済サービス取引ID", "PAYMENT_ORDER_ID_SEQ"),

  /** 「受注メール送信履歴ID」を表す値です。 */
  ORDER_MAIL_HISTORY_ID("受注メール送信履歴ID", "ORDER_MAIL_HISTORY_ID_SEQ"),

  // Add by V10-CH start
  ORDER_SMS_HISTORY_ID("受注SMS送信履歴ID", "ORDER_SMS_HISTORY_ID_SEQ"), ONLINE_SERVICE_NO("在线编号", "ONLINE_SERVICE_SEQ"), COUPON_ISSUE_NO(
      "クーポン発行", "COUPON_ISSUE_NO_SEQ"), CUSTOMER_COUPON_ID("ユーザクーポン関連", "CUSTOMER_COUPON_ID_SEQ"),
  // Add by V10-CH end
  // soukai add ob 2011/12/13 start
  COUPON_CODE_ID("优惠券规则编号", "NEW_COUPON_RULE_SEQ"),
  // soukai add ob 2011/12/13 end
  // 2013/04/10 优惠券对应 ob add start
  /** 「使用履历id」を表す値です。 */
  USE_HISTORY_ID("使用履历id", "USE_HISTORY_ID_SEQ"),
  // 2013/04/10 优惠券对应 ob add end
  // soukai add ob 2011/12/20 start
  DELIVERY_RELATED_INFO_NO("配送关联信息编号", "DELIVERY_RELATED_INFO_NO_SEQ"),
  // soukai add ob 2011/12/20 end

  TMALL_DELIVERY_RELATED_INFO_NO("配送关联信息编号", "TMALL_DELIVERY_RELATED_INFO_NO_SEQ"),
  // 20111209 yl add start
  // 20111227 os013 add start
  shipping_detail_list_no("发货明细实际编号", "SHIPPING_DETAIL_LIST_SEQ"),
  // 20111227 os013 add end
  /**
   * 咨询编号
   */
  INQUIRY_HEADER_NO("咨询编号", "INQUIRY_HEADER_NO_SEQ"),
  /**
   * 咨询明细编号
   */
  INQUIRY_DETAIL_NO("咨询明细编号", "INQUIRY_DETAIL_NO_SEQ"),
  // add by os012 20120103 start
  /** 「淘宝订单号」を表す値です。 */
  TMALL_ORDER_NO_SEQ("淘宝订单号", "TMALL_ORDER_NO_SEQ"),
  /** 「TMALL出荷番号」を表す値です。 */
  TMALL_SHIPPING_NO("TMALL出荷番号", "TMALL_SHIPPING_NO_SEQ"),

  // add by os012 20120103 end

  // ADD BY OS014 2012-01-19 BEGIN
  TMALL_PROPERTY_VALUE_NO("", "TMALL_PROPERTY_VALUE_SEQ"),
  // ADD BY OS014 2012-01-19 END
  /**
   * 商品同期化履历编号
   */
  COMMODITY_CYNCHRO_HISTORY_SEQ("商品同期化履历编号", "C_SYNCHISTORY_SEQ"),
  // 20111209 yl add end
  // 20120215 os013 add start
  /**
   * 商品拡張情報编号
   */
  C_COMMODITY_EXT_SEQ("商品拡張情報", "C_COMMODITY_EXT_SEQ"),
  // 20120215 os013 add end
  AUTH_ID("手机验证ID", "MOBILE_AUTH_ID_SEQ"),

  FRIEND_COUPON_RULE_SEQ("朋友推荐优惠券序列号", "FRIEND_COUPON_RULE_SEQ"),

  TMALL_STOCK_ALLOCATION_SEQ("TMALL库存分配", "TMALL_STOCK_ALLOCATION_SEQ"),

  // 20131018 txw add start
  FREE_POSTAGE_RULE_SEQ("免邮促销", "FREE_POSTAGE_RULE_SEQ"),

  ORDER_MEDIA_SEQ("订单媒体", "ORDER_MEDIA_SEQ"),

  CUSTOMER_CARD_INFO_CARD_ID_SEQ("卡号", "CUSTOMER_CARD_INFO_CARD_ID_SEQ"),
  // 20131018 txw add end

  CANDIDATE_WORD_SEQ("候補ワード", "CANDIDATE_WORD_SEQ"),

  // 20131018 wz add start
  CARD_ID("礼品卡ID", "gift_card_issue_detail_card_id_seq"),
  // 20131018 wz add end

  // 20140514 hdh add start

  /** 「JD订单号」を表す値です。 */
  JD_ORDER_NO_SEQ("JD订单号", "JD_ORDER_NO_SEQ"),
  /** 「JD出荷番号」を表す値です。 */
  JD_SHIPPING_NO_SEQ("JD出荷番号", "JD_SHIPPING_NO_SEQ"),

  JD_COUPON_DETAIL_NO_SEQ("京东优惠明细编号", "JD_COUPON_DETAIL_NO_SEQ"),


  COMMODITY_PRICE_CHANGE_HISTORY_SEQ("价格变动历史表", "COMMODITY_PRICE_CHANGE_HISTORY_SEQ"),
  //210140514 hdh add end
  
  //2014-12-31 zzy add start
  UNTRUE_ORDER_WORD_CODE_SEQ("虚假订单关键词编号","UNTRUE_ORDER_WORD_CODE_SEQ");
  //2014-12-31 zzy add end
  private String name;

  private String value;

  private SequenceType(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * コード名称を返します。
   * 
   * @return コード名称
   */
  public String getName() {
    return name;
  }

  /**
   * コード値を返します。
   * 
   * @return コード値
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Long型のコード値を返します。
   * 
   * @return コード値
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * 指定されたコード名を持つ連番区分を返します。
   * 
   * @param name
   *          コード名
   * @return 連番区分
   */
  public static SequenceType fromName(String name) {
    for (SequenceType p : SequenceType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ連番区分を返します。
   * 
   * @param value
   *          コード値
   * @return 連番区分
   */
  public static SequenceType fromValue(String value) {
    for (SequenceType p : SequenceType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ連番区分を返します。
   * 
   * @param value
   *          コード値
   * @return 連番区分
   */
  public static SequenceType fromValue(Long value) {
    return fromValue(Long.toString(value));
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param value
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String value) {
    if (StringUtil.hasValue(value)) {
      for (SequenceType p : SequenceType.values()) {
        if (p.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param value
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(Long value) {
    return isValid(Long.toString(value));
  }
}
