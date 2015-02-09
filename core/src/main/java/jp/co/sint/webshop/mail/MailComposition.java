package jp.co.sint.webshop.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * メール構造の初期設定情報を保持するEnum。
 * 
 * @author System Integrator Corp.
 */
public enum MailComposition {

  // Composition情報は構造の順番どおり記述しなければならない
  /** 共通用ルート構造 */
  COMMON_ROOT_COMPOSITION("", null, "", null),
  /** 情報メール-本文 */
  INFORMATION_MAIL_MAIN("", MailType.INFORMATION, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CustomerBaseTag.values(), CustomerTag
      .values()),
  /** 情報メール-署名 */
  INFORMATION_MAIL_SIGN("署名", MailType.INFORMATION, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 受注確認（PC）-本文 */
  ORDER_DETAILS_PC_MAIN("本文", MailType.ORDER_DETAILS_PC, "[#MAIN#]", COMMON_ROOT_COMPOSITION, OrderHeaderTag.values()),
  // del by lc 2012-04-12 start
  //  /** 受注確認（PC）-受注修正メッセージ */
  //  ORDER_DETAILS_PC_MODIFY_MESSAGE("受注修正メッセージ", MailType.ORDER_DETAILS_PC, "[#ORDER_DETAILS_MODIFY" + "_MESSAGE#]",
  //      ORDER_DETAILS_PC_MAIN),
  // del by lc 2012-04-12 end
  /** 受注確認（PC）-配送先情報 */
  ORDER_DETAILS_PC_SHIPPING_HEADER("配送先情報", MailType.ORDER_DETAILS_PC, "[#SHIPPING_HEADER#]", ORDER_DETAILS_PC_MAIN,
      ShippingHeaderTag.values()),
  /** 受注確認（PC）-配送先明細情報 */
  ORDER_DETAILS_PC_SHIPPING_DETAIL("配送先明細情報", MailType.ORDER_DETAILS_PC, "[#SHIPPING_DETAIL#]", ORDER_DETAILS_PC_SHIPPING_HEADER,
      ShippingDetailTag.values()),
  /** 受注確認（PC）-支払先情報 */
  ORDER_DETAILS_PC_PAYMENT("支払先情報", MailType.ORDER_DETAILS_PC, "[#PAYMENT#]", ORDER_DETAILS_PC_MAIN, PaymentTag.values()),
  /** 受注確認（PC）-署名 */
  ORDER_DETAILS_PC_SIGN("署名", MailType.ORDER_DETAILS_PC, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 受注キャンセル-本文 */
  CANCELLED_ORDER_MAIN("本文", MailType.CANCELLED_ORDER, "[#MAIN#]", COMMON_ROOT_COMPOSITION, OrderHeaderTag.values()),
  /** 受注キャンセル-配送先情報 */
  CANCELLED_ORDER_SHIPPING_HEADER("配送先情報", MailType.CANCELLED_ORDER, "[#SHIPPING_HEADER#]", CANCELLED_ORDER_MAIN, ShippingHeaderTag
      .values()),
  /** 受注キャンセル-配送先明細情報 */
  CANCELLED_ORDER_SHIPPING_DETAIL("配送先明細情報", MailType.CANCELLED_ORDER, "[#SHIPPING_DETAIL#]", CANCELLED_ORDER_SHIPPING_HEADER,
      ShippingDetailTag.values()),
  /** 受注キャンセル-支払先情報 */
  CANCELLED_ORDER_PAYMENT("支払先情報", MailType.CANCELLED_ORDER, "[#PAYMENT#]", CANCELLED_ORDER_MAIN, PaymentTag.values()),
  /** 受注キャンセル-署名 */
  CANCELLED_ORDER_SIGN("署名", MailType.CANCELLED_ORDER, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 受注確認（携帯）-本文 */
  ORDER_DETAILS_MOBILE_MAIN("本文", MailType.ORDER_DETAILS_MOBILE, "[#MAIN#]", COMMON_ROOT_COMPOSITION, OrderHeaderTag.values()),
  /** 受注確認（携帯）-受注修正メッセージ */
  ORDER_DETAILS_MOBILE_MODIFY_MESSAGE("受注修正メッセージ", MailType.ORDER_DETAILS_MOBILE, "[#ORDER_DETAILS_MODIFY_MESSAGE#]",
      ORDER_DETAILS_MOBILE_MAIN),
  /** 受注確認（携帯）-配送先情報 */
  ORDER_DETAILS_MOBILE_SHIPPING_HEADER("配送先情報", MailType.ORDER_DETAILS_MOBILE, "[#SHIPPING_HEADER#]", ORDER_DETAILS_MOBILE_MAIN,
      ShippingHeaderTag.values()),
  /** 受注確認（携帯）-配送先明細情報 */
  ORDER_DETAILS_MOBILE_SHIPPING_DETAIL("配送先明細情報", MailType.ORDER_DETAILS_MOBILE, "[#SHIPPING_DETAIL#]",
      ORDER_DETAILS_MOBILE_SHIPPING_HEADER, ShippingDetailTag.values()),
  /** 受注確認（携帯）-支払先情報 */
  ORDER_DETAILS_MOBILE_PAYMENT("支払先情報", MailType.ORDER_DETAILS_MOBILE, "[#PAYMENT#]", ORDER_DETAILS_MOBILE_MAIN, PaymentTag
      .values()),
  /** 受注確認（携帯）-署名 */
  ORDER_DETAILS_MOBILE_SIGN("署名", MailType.ORDER_DETAILS_MOBILE, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** パスワード再登録（PC）-本文 */
  REISSUE_PASSWORD_MAIN("本文", MailType.REISSUE_PASSWORD, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CustomerBaseTag.values(),
      ReissuePasswordTag.values()),
  /** パスワード再登録（PC）-署名 */
  REISSUE_PASSWORD_SIGN("署名", MailType.REISSUE_PASSWORD, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** パスワード変更確認（PC）-本文 */
  PASSWORD_CONFIRMATION_MAIN("本文", MailType.PASSWORD_CONFIRMATION, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CustomerBaseTag.values()),
  /** パスワード変更確認（PC）-署名 */
  PASSWORD_CONFIRMATION_SIGN("署名", MailType.PASSWORD_CONFIRMATION, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 入金確認 -本文 */
  RECEIVED_PAYMENT_MAIN("本文", MailType.RECEIVED_PAYMENT, "[#MAIN#]", COMMON_ROOT_COMPOSITION, PaymentConfirmTag.values()),
  /** 入金確認 -配送先 */
  RECEIVED_PAYMENT_SHIPPING("配送先", MailType.RECEIVED_PAYMENT, "[#SHIPPING#]", RECEIVED_PAYMENT_MAIN, ShippingHeaderTag.values()),
  /** 入金確認 -配送先商品情報 */
  RECEIVED_PAYMENT_SHIPPING_COMMODITY("配送先商品情報", MailType.RECEIVED_PAYMENT, "[#COMMODITY#]", RECEIVED_PAYMENT_SHIPPING,
      ShippingDetailTag.values()),
  /** 入金確認 -署名 */
  RECEIVED_PAYMENT_SIGN("署名", MailType.RECEIVED_PAYMENT, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 入金督促 -本文 */
  PAYMENT_REMINDER_MAIN("本文", MailType.PAYMENT_REMINDER, "[#MAIN#]", COMMON_ROOT_COMPOSITION, PaymentReminderTag.values()),
  /** 入金督促 -支払先 */
  PAYMENT_REMINDER_PAYMENT_INFO("支払先", MailType.PAYMENT_REMINDER, "[#PAYMENT_INFO#]", PAYMENT_REMINDER_MAIN, PaymentTag.values()),
  /** 入金督促 -配送先 */
  PAYMENT_REMINDER_SHIPPING("配送先", MailType.PAYMENT_REMINDER, "[#SHIPPING#]", PAYMENT_REMINDER_MAIN, ShippingHeaderTag.values()),
  /** 入金督促 -配送先商品情報 */
  PAYMENT_REMINDER_SHIPPING_COMMODITY("配送先商品情報", MailType.PAYMENT_REMINDER, "[#COMMODITY#]", PAYMENT_REMINDER_SHIPPING,
      ShippingDetailTag.values()),
  /** 入金督促 -署名 */
  PAYMENT_REMINDER_SIGN("署名", MailType.PAYMENT_REMINDER, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 出荷報告 -本文 */
  SHIPPING_REPORT_MAIN("本文", MailType.SHIPPING_INFORMATION, "[#MAIN#]", COMMON_ROOT_COMPOSITION, ShippingTag.values()),
  /** 出荷報告 -配送先情報 */
  SHIPPING_REPORT_HEADER("配送先情報", MailType.SHIPPING_INFORMATION, "[#SHIPPING_HEADER#]", SHIPPING_REPORT_MAIN, ShippingHeaderTag
      .values(), ArrivalTag.values()),
  /** 出荷報告 -配送先明細情報 */
  SHIPPING_REPORT_DETAIL("配送先明細情報", MailType.SHIPPING_INFORMATION, "[#SHIPPING_DETAIL#]", SHIPPING_REPORT_HEADER, ShippingDetailTag
      .values()),
  // /** 出荷報告 -支払方法情報 */
  // SHIPPING_REPORT_PAYMENT("支払方法情報", MailType.SHIPPING_INFORMATION,
  // "[#PAYMENT_HEADER#]", SHIPPING_REPORT_MAIN, PaymentTag.values()),
  /** 出荷報告 -署名 */
  SHIPPING_REPORT_SIGN("署名", MailType.SHIPPING_INFORMATION, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 予約確認-本文 */
  RESERVATION_DETAIL_MAIN("本文", MailType.RESERVATION_DETAIL, "[#MAIN#]", COMMON_ROOT_COMPOSITION, OrderHeaderTag.values()),
  /** 予約確認-配送先情報 */
  RESERVATION_DETAIL_SHIPPING_HEADER("配送先情報", MailType.RESERVATION_DETAIL, "[#SHIPPING_HEADER#]", RESERVATION_DETAIL_MAIN,
      ReservationShippingTag.values()),
  /** 予約確認-配送先明細情報 */
  RESERVATION_DETAIL_SHIPPING_DETAIL("配送先明細情報", MailType.RESERVATION_DETAIL, "[#SHIPPING_DETAIL#]",
      RESERVATION_DETAIL_SHIPPING_HEADER, ShippingDetailTag.values()),
  /** 予約確認-支払先情報 */
  RESERVATION_DETAIL_PAYMENT("支払先情報", MailType.RESERVATION_DETAIL, "[#PAYMENT#]", RESERVATION_DETAIL_MAIN, PaymentTag.values()),
  /** 予約確認-署名 */
  RESERVATION_DETAIL_SIGN("署名", MailType.RESERVATION_DETAIL, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 予約キャンセル-本文 */
  CANCELLED_RESERVATION_MAIN("本文", MailType.CANCELLED_RESERVATION, "[#MAIN#]", COMMON_ROOT_COMPOSITION, OrderHeaderTag.values()),
  /** 予約キャンセル-配送先情報 */
  CANCELLED_RESERVATION_SHIPPING_HEADER("配送先情報", MailType.CANCELLED_RESERVATION, "[#SHIPPING_HEADER#]", CANCELLED_RESERVATION_MAIN,
      ReservationShippingTag.values()),
  /** 予約キャンセル-配送先明細情報 */
  CANCELLED_RESERVATION_SHIPPING_DETAIL("配送先明細情報", MailType.CANCELLED_RESERVATION, "[#SHIPPING_DETAIL#]",
      CANCELLED_RESERVATION_SHIPPING_HEADER, ShippingDetailTag.values()),
  /** 予約キャンセル-支払先情報 */
  CANCELLED_RESERVATION_PAYMENT("支払先情報", MailType.CANCELLED_RESERVATION, "[#PAYMENT#]", CANCELLED_RESERVATION_MAIN, PaymentTag
      .values()),
  /** 予約キャンセル-署名 */
  CANCELLED_RESERVATION_SIGN("署名", MailType.CANCELLED_RESERVATION, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 入荷お知らせ -本文 */
  ARRIVAL_GOODS_MAIN("本文", MailType.ARRIVAL_INFORMATION, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CommodityHeaderTag.values(),
      CommodityDetailTag.values()),
  /** 入荷お知らせ -署名 */
  ARRIVAL_GOODS_SIGN("署名", MailType.ARRIVAL_INFORMATION, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 顧客登録（PC）-本文 */
  CUSTOMER_REGISTERED_MAIN("本文", MailType.CUSTOMER_REGISTERED, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CustomerBaseTag.values(),
      CustomerTag.values()),
  /** 顧客登録（PC）-署名 */
  CUSTOMER_REGISTERED_SIGN("署名", MailType.CUSTOMER_REGISTERED, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  /** 顧客退会依頼（PC）-本文 */
  RECEIVED_WITHDRAWAL_NOTICE_MAIN("本文", MailType.RECEIVED_WITHDRAWAL_NOTICE, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CustomerBaseTag
      .values(), CustomerWithdrawalRequestTag.values()),
  /** 顧客退会依頼（PC）-署名 */
  RECEIVED_WITHDRAWAL_NOTICE_SIGN("署名", MailType.RECEIVED_WITHDRAWAL_NOTICE, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag
      .values()),
  /** 顧客退会（PC）-本文 */
  COMPLETED_WITHDRAWAL_MAIN("本文", MailType.COMPLETED_WITHDRAWAL, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CustomerWithdrawalTag
      .values()),
  /** 顧客退会（PC）-署名 */
  COMPLETED_WITHDRAWAL_SIGN("署名", MailType.COMPLETED_WITHDRAWAL, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),

  /** 誕生日 -本文 */
  BIRTHDAY_MAIN("本文", MailType.BIRTHDAY, "[#MAIN#]", COMMON_ROOT_COMPOSITION, BirthDayTag.values()),
  /** 誕生日 -署名 */
  BIRTHDAY_SIGN("署名", MailType.BIRTHDAY, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),

  /** ポイント失効 -本文 */
  POINT_EXPIRED_MAIN("本文", MailType.POINT_EXPIRED, "[#MAIN#]", COMMON_ROOT_COMPOSITION, PointExpiredTag.values()),
  /** ポイント失効 -署名 */
  POINT_EXPIRED_SIGN("署名", MailType.POINT_EXPIRED, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),

  // Add by V10-CH start
  /** 顧客グループ変更 -本文 */
  GROUP_CHANGE_MAIN("本文", MailType.GROUP_CHANGE, "[#MAIN#]", COMMON_ROOT_COMPOSITION, GroupChangeTag.values()),
  /** 顧客グループ変更 -署名 */
  GROUP_CHANGE_SIGN("署名", MailType.GROUP_CHANGE, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),

  /** 顧客グループ変更 -本文 */
  GROUP_CHANGE_MAIN_TO_LOWER("本文", MailType.GROUP_CHANGE_TO_LOWER, "[#MAIN#]", COMMON_ROOT_COMPOSITION, GroupChangeTag.values()),
  /** 顧客グループ変更 -署名 */
  GROUP_CHANGE_SIGN_TO_LOWER("署名", MailType.GROUP_CHANGE_TO_LOWER, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  
  /** 顧客グループ変更 -本文 */
  PAYMENT_PASSWORD_CHANGED_MAIN("本文", MailType.PAYMENT_PASSWORD_CHANGED, "[#MAIN#]", COMMON_ROOT_COMPOSITION, GroupChangeTag.values()),
  /** 顧客グループ変更 -署名 */
  PAYMENT_PASSWORD_CHANGED_SIGN("署名", MailType.PAYMENT_PASSWORD_CHANGED, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  
  
  /** 顧客グループ変更お知らせ -本文 */
  GROUP_CHANGE_INFORMATION_MAIN("本文", MailType.GROUP_CHANGE_INFORMATION, "[#MAIN#]", COMMON_ROOT_COMPOSITION, GroupChangeTag.values()),
  /** 顧客グループ変更お知らせ -署名 */
  GROUP_CHANGE_INFORMATION_SIGN("署名", MailType.GROUP_CHANGE_INFORMATION, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  // Add by V10-CH end
  //saikou 2011/12/28 ob start
  COUPON_START_INFORMATION_MAIN("本文", MailType.COUPON_START, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CouponStartTag.values()),
  
  COUPON_START_INFORMATION_SIGN("署名", MailType.COUPON_START, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  
  COUPON_END_INFORMATION_MAIN("本文", MailType.COUPON_END, "[#MAIN#]", COMMON_ROOT_COMPOSITION, CouponEndTag.values()),
  
  COUPON_END_INFORMATION_SIGN("署名", MailType.COUPON_END, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values()),
  // saikou 2011/12/28 ob start
  
  /** 出荷報告 -本文 */
  TM_SHIPPING_REPORT_MAIN("本文", MailType.SHIPPING_INFORMATION_TM, "[#MAIN#]", COMMON_ROOT_COMPOSITION, TmallShippingTag.values()),
  /** 出荷報告 -配送先情報 */
  TM_SHIPPING_REPORT_HEADER("配送先情報", MailType.SHIPPING_INFORMATION_TM, "[#SHIPPING_HEADER#]", SHIPPING_REPORT_MAIN, TmallShippingHeaderTag
      .values(), ArrivalTag.values()),
  /** 出荷報告 -配送先明細情報 */
  TM_SHIPPING_REPORT_DETAIL("配送先明細情報", MailType.SHIPPING_INFORMATION_TM, "[#SHIPPING_DETAIL#]", SHIPPING_REPORT_HEADER, TmallShippingDetailTag
      .values()),
  /** 出荷報告 -署名 */
   TM_SHIPPING_REPORT_SIGN("署名", MailType.SHIPPING_INFORMATION_TM, "[#SIGN#]", COMMON_ROOT_COMPOSITION, ShopInfoTag.values());

  private String name;

  private String substitutionTag;

  private MailType type;

  private MailComposition parentCompostion;

  private List<MailTemplateTag> useableTagList = new ArrayList<MailTemplateTag>();

  private MailComposition(String name, MailType type, String substitutionTag, MailComposition parentComposition,
      MailTemplateTag[]... usableTag) {
    this.name = name;
    this.substitutionTag = substitutionTag;
    this.type = type;
    this.parentCompostion = parentComposition;
    for (MailTemplateTag[] tag : usableTag) {
      this.useableTagList.addAll(Arrays.asList(tag));
    }
  }

  /**
   * nameを取得します。
   * 
   * @return the name
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * compostionTagを取得します。
   * 
   * @return the compostionTag
   */
  public String getSubstitutionTag() {
    return substitutionTag;
  }

  /**
   * typeを取得します。
   * 
   * @return the type
   */
  public MailType getType() {
    return type;
  }

  /**
   * useableTagListを取得します。
   * 
   * @return the useableTagList
   */
  public List<MailTemplateTag> getUseableTagList() {
    return useableTagList;
  }

  /**
   * メールタイプから構造情報を取得する。
   * 
   * @param type
   * @return メールタイプから得られる構造情報
   */
  public static List<MailComposition> fromMailType(MailType type) {
    List<MailComposition> compostionList = new ArrayList<MailComposition>();
    for (MailComposition c : MailComposition.values()) {
      if (c.getType() == type) {
        compostionList.add(c);
      }
    }
    return compostionList;
  }

  /**
   * メールタイプと構造タグから構造情報を取得する。
   * 
   * @param type
   * @param substitutionTag
   * @return メールタイプと構造タグから得られる構造情報
   */
  public static MailComposition fromMailTypeAndTag(MailType type, String substitutionTag) {
    for (MailComposition c : MailComposition.values()) {
      if (c.getType() == type && c.getSubstitutionTag().equals(substitutionTag)) {
        return c;
      }
    }
    return null;
  }

  public MailComposition getParentCompostion() {
    return parentCompostion;
  }

}
