package jp.co.sint.webshop.web.message.back.common;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum DashboardInformationMessage implements MessageType {

  /** 支払有効期限切れメッセージ */
  PAYMENT_DEADLINE("payment_deadline"),
  /** 退会依頼メッセージ */
  CUSTOMER_WITHDRAWAL("customer_withdrawal"),
  /** 在庫切れSKUメッセージ */
  STOCK_LIMIT("stock_limit"),
  /** ポイントルール運用中メッセージ */
  POINT_ENABLED("point_enabled"),
  /** ポイントルール運用中メッセージ */
  COUPON_ENABLED("coupon_enabled"),
  /** ボーナスポイントメッセージ */
  BONUS_POINT("bonus_point"),
  /** キャンペーン実施中メッセージ */
  CAMPAIGN_UNDERWAY("campaign_underway");

  private String messagePropertyId;

  private DashboardInformationMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.back.common.DashboardMessages";
  }

}
