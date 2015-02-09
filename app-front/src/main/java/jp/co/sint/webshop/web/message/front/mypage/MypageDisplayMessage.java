package jp.co.sint.webshop.web.message.front.mypage;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum MypageDisplayMessage implements MessageType {

  /** メールマガジン配信停止完了メッセージ */
  MAILMAGAZINE_CANCEL_COMPLETE("mailmagazine_cancel_complete"),
  /** メールマガジン申し込み用メール送信完了メッセージ */
  MAILMAGAZINE_SENDMAIL_COMPLETE("mailmagazine_sendmail_complete"),

  CUSTOMER_GROUP_UPGRADES_REMIND_ONE("customer_group_upgrades_remind_one"),

  CUSTOMER_GROUP_UPGRADES_REMIND_TWO("customer_group_upgrades_remind_two"),

  CUSTOMER_GROUP_UPGRADES_REMIND_THREE("customer_group_upgrades_remind_three"),

  SEARCH_RESULT_OVERFLOW("search_result_overflow");

  private String messagePropertyId;

  private MypageDisplayMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.front.mypage.MypageMessages";
  }

}
