package jp.co.sint.webshop.mail;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.domain.OperatingMode;

public final class MailTemplateUtil {

  private MailTemplateUtil() {

  }

  /**
   * 使用できるメールテンプレートタイプの一覧を取得します。
   * 
   * @param mode
   * 
   * @param siteUser
   * 
   * @param information
   *          情報メールの有無 true - 含む false - 含まない
   * @return 使用できるメールテンプレートタイプの一覧
   */
  public static List<MailType> getUsableMailTypeList(OperatingMode mode,
      boolean siteUser, boolean information) {
    List<MailType> mailTypeList = new ArrayList<MailType>();

    if (siteUser) {
      if (information) {
        mailTypeList.add(MailType.INFORMATION);
      }
      // 全モードでサイトに関連付いているテンプレート
      mailTypeList.add(MailType.CUSTOMER_REGISTERED);
      mailTypeList.add(MailType.RECEIVED_WITHDRAWAL_NOTICE);
      mailTypeList.add(MailType.COMPLETED_WITHDRAWAL);
      mailTypeList.add(MailType.PASSWORD_CONFIRMATION);
      mailTypeList.add(MailType.BIRTHDAY);
      //2012-01-30 lc del start
      //mailTypeList.add(MailType.POINT_EXPIRED);
      //2012-01-30 lc del end
      mailTypeList.add(MailType.REISSUE_PASSWORD);
      // Add by V10-CH start
      mailTypeList.add(MailType.GROUP_CHANGE);
      mailTypeList.add(MailType.GROUP_CHANGE_INFORMATION);
      // Add by V10-CH end

      if (mode == OperatingMode.MALL) {
        // モール一括決済でサイトに関連付いているテンプレート
        mailTypeList.add(MailType.ORDER_DETAILS_PC);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.ORDER_DETAILS_MOBILE);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.CANCELLED_ORDER);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.PAYMENT_REMINDER);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.RECEIVED_PAYMENT);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.RESERVATION_DETAIL);
        //mailTypeList.add(MailType.CANCELLED_RESERVATION);
        //2012-01-30 lc del end
      } else if (mode == OperatingMode.ONE) {
        // 一店舗版でサイトに関連付いているテンプレート
        mailTypeList.add(MailType.ORDER_DETAILS_PC);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.ORDER_DETAILS_MOBILE);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.CANCELLED_ORDER);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.PAYMENT_REMINDER);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.RECEIVED_PAYMENT);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.RESERVATION_DETAIL);
        //mailTypeList.add(MailType.CANCELLED_RESERVATION);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.SHIPPING_INFORMATION);
        //2012-04-01 yyq add start
        mailTypeList.add(MailType.SHIPPING_INFORMATION_TM);
        //2012-04-01 yyq add end
        mailTypeList.add(MailType.ARRIVAL_INFORMATION);
        // saikou 2011/12/28 ob start
        mailTypeList.add(MailType.COUPON_START);
        mailTypeList.add(MailType.COUPON_END);
        // saikou 2011/12/28 ob end
        mailTypeList.add(MailType.GROUP_CHANGE_TO_LOWER);
      }
    } else {
      if (mode == OperatingMode.MALL) {
        // モール一括決済でショップに関連付いているテンプレート
        mailTypeList.add(MailType.SHIPPING_INFORMATION);
        mailTypeList.add(MailType.ARRIVAL_INFORMATION);
      } else if (mode == OperatingMode.SHOP) {
        // ショップ個別決済でショップに関連付いているテンプレート
        mailTypeList.add(MailType.ORDER_DETAILS_PC);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.ORDER_DETAILS_MOBILE);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.CANCELLED_ORDER);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.PAYMENT_REMINDER);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.RECEIVED_PAYMENT);
        //2012-01-30 lc del start
        //mailTypeList.add(MailType.RESERVATION_DETAIL);
        //mailTypeList.add(MailType.CANCELLED_RESERVATION);
        //2012-01-30 lc del end
        mailTypeList.add(MailType.SHIPPING_INFORMATION);
        mailTypeList.add(MailType.ARRIVAL_INFORMATION);
      }
    }

    return mailTypeList;
  }

  /**
   * 各メール構造で使用できるタグの一覧を取得します。
   * 
   * @param type
   *          メールタイプ
   * @param substitutionTag
   *          構造タグ
   * @return タグの一覧
   */
  public static List<MailTemplateTag> getUsableTagList(MailType type,
      String substitutionTag) {
    List<MailTemplateTag> tagList = new ArrayList<MailTemplateTag>();
    MailComposition composition = MailComposition.fromMailTypeAndTag(type,
        substitutionTag);
    if (composition != null) {
      tagList.addAll(composition.getUseableTagList());
    }
    return tagList;
  }

}
