package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.code.CodeAttribute;

/**
 * SI Web Shopping 10 メールテンプレートで使用するタグ情報
 * 
 * @author System Integrator Corp.
 */
public interface MailTemplateTag extends CodeAttribute {

  /** 到着予定日時タグ */
  String ARRIVAL = "ARRIVAL";

  /** 受注ヘッダタグ */
  String ORDER_HEADER = "ORDER_HEADER";

  /** 受注明細タグ */
  String ORDER_DETAIL = "ORDER_DETAIL";

  /** 出荷報告タグ */
  String SHIPPING = "SHIPPING";

  /** 顧客タグ */
  String CUSTOMER = "CUSTOMER";

  /** 顧客ヘッダタグ */
  String CUSTOMER_HEADER = "CUSTOMER_HEADER";

  /** 顧客明細タグ */
  String CUSTOMER_DETAIL = "CUSTOMER_DETAIL";

  /** 顧客アドレスタグ */
  String CUSTOMER_ADDRESS = "CUSTOMER_ADDRESS";

  /** 支払情報タグ */
  String PAYMENT = "PAYMENT";

  /** 支払情報明細タグ */
  String PAYMENT_DETAIL = "PAYMENT_DETAIL";

  /** 配送先情報タグ */
  String SHIPPING_HEADER = "SHIPPING_HEADER";

  /** 予約配送先情報タグ */
  String RESERVATION_SHIPPING = "RESERVATION_SHIPPING";

  /** 配送先明細情報タグ */
  String SHIPPING_DETAIL = "SHIPPING_DETAIL";

  /** パスワード再登録タグ */
  String REISSUE_PASSWORD = "REISSUE_PASSWORD";

  /** パスワード変更確認タグ */
  String PASSWORD_CONFIRMATION = "PASSWORD_CONFIRMATION";

  /** ショップ情報タグ */
  String SHOP_INFO = "SHOP_INFO";

  /** ポイント失効情報タグ */
  String POINT_EXPIRED = "POINT_EXPIRED";

  /** 誕生日情報タグ */
  String BIRTHDAY = "BIRTHDAY";

  /** 支払情報確認タグ */
  String PAYMENT_CONFIRM = "PAYMENT_CONFIRM";

  // Add by V10-CH start
  /** グループ変更タグ */
  String GROUP_CHANGE = "GROUP_CHANGE";
  /** グループ変更タグ */
  String GROUP_CHANGE_TO_LOWER = "GROUP_CHANGE_TO_LOWER";
  // Add by V10-CH end
  
  // saikou 2011/12/28 ob start
  String COUPON_START = "COUPON_START";
  
  String COUPON_END = "COUPON_END";
  // saikou 2011/12/28 ob end

  /**
   * プレビュー時のダミー情報を取得します。
   * 
   * @return プレビュー時のダミー情報
   */
  String getDummyData();

  /**
   * タグ名を取得します。
   * 
   * @return タグ名
   */
  String getName();

  /**
   * 実際に設定されるタグを取得します。
   * 
   * @return 実際に設定されるタグ
   */
  String getValue();

  /**
   * タグ分類名を取得します。
   * 
   * @return タグ分類名
   */
  String getTagDiv();

  /**
   * 必須タグがどうかの真偽値を返します。
   * 
   * @return 真偽値
   */
  boolean isRequired();

}
