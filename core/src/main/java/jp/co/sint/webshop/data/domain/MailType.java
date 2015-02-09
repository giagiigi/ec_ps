//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはコード定義ドキュメントから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「メールタイプ」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum MailType implements CodeAttribute {

  /** 「情報メール」を表す値です。 */
  INFORMATION("情報メール", "00"),

  /** 「顧客登録」を表す値です。 */
  CUSTOMER_REGISTERED("顧客登録", "01"),

  /** 「顧客退会依頼」を表す値です。 */
  RECEIVED_WITHDRAWAL_NOTICE("顧客退会依頼", "02"),

  /** 「顧客退会」を表す値です。 */
  COMPLETED_WITHDRAWAL("顧客退会", "03"),

  /** 「パスワード変更確認」を表す値です。 */
  PASSWORD_CONFIRMATION("パスワード変更確認", "04"),

  /** 「受注確認(PC)」を表す値です。 */
  ORDER_DETAILS_PC("受注確認(PC)", "05"),

  /** 「受注確認(携帯)」を表す値です。 */
  ORDER_DETAILS_MOBILE("受注確認(携帯)", "06"),

  /** 「受注キャンセル」を表す値です。 */
  CANCELLED_ORDER("受注キャンセル", "07"),

  /** 「返品確認」を表す値です。 */
  RETURNED_ITEM("返品確認", "08"),

  /** 「入金督促」を表す値です。 */
  PAYMENT_REMINDER("入金督促", "09"),

  /** 「入金確認」を表す値です。 */
  RECEIVED_PAYMENT("入金確認", "10"),

  /** 「EC出荷連絡」を表す値です。 */
  SHIPPING_INFORMATION("EC出荷連絡", "11"),

  /** 「予約確認」を表す値です。 */
  RESERVATION_DETAIL("予約確認", "12"),

  /** 「予約キャンセル」を表す値です。 */
  CANCELLED_RESERVATION("予約キャンセル", "13"),

  /** 「入荷お知らせ」を表す値です。 */
  ARRIVAL_INFORMATION("入荷お知らせ", "14"),

  /** 「誕生日」を表す値です。 */
  BIRTHDAY("誕生日", "15"),

  /** 「ポイント失効」を表す値です。 */
  POINT_EXPIRED("ポイント失効", "16"),

  /** 「パスワード再登録」を表す値です。 */
  REISSUE_PASSWORD("パスワード再登録", "17"),

  // Add by V10-CH start
  /** 「顧客グループ変更」を表す値です。 */
  GROUP_CHANGE("顧客グループ変更", "18"),
  
  /** 「顧客グループ変更お知らせ」を表す値です。 */
  GROUP_CHANGE_INFORMATION("顧客グループ変更お知らせ", "19"),
  
  // Add by V10-CH end
  
  // saikou 2011/12/28 ob start
  COUPON_START("优惠劵利用开始通知", "20"),
  
  COUPON_END("优惠劵利用结束通知", "21"),
  // saikou 2011/12/28 ob start
  
  /** 「EC出荷連絡」を表す値です。 */
  SHIPPING_INFORMATION_TM("淘宝发货联络", "22"),
  
  /** 「会员降级」を表す値です。 */
  GROUP_CHANGE_TO_LOWER("会员降级联络", "23"),
  
  PAYMENT_PASSWORD_CHANGED("支付密码修改方法", "24");

  private String name;

  private String value;

  private MailType(String name, String value) {
    this.name = name;
    this.value = value;
  }
  /**
   * コード名称を返します。
   * @return コード名称
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * コード値を返します。
   * @return コード値
   */
  public String getValue() {
    return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
  }

  /**
   * Long型のコード値を返します。
   * @return コード値
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * 指定されたコード名を持つメールタイプを返します。
   *
   * @param name コード名
   * @return メールタイプ
   */
  public static MailType fromName(String name) {
    for (MailType p : MailType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つメールタイプを返します。
   *
   * @param value コード値
   * @return メールタイプ
   */
  public static MailType fromValue(String value) {
    for (MailType p : MailType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つメールタイプを返します。
   *
   * @param value コード値
   * @return メールタイプ
   */
  public static MailType fromValue(Long value) {
    return fromValue(Long.toString(value));
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   *
   * @param value コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String value) {
    if (StringUtil.hasValue(value)) {
      for (MailType p : MailType.values()) {
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
   * @param value コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(Long value) {
    return isValid(Long.toString(value));
  }
}
