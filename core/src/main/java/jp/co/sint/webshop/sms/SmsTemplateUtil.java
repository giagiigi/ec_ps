package jp.co.sint.webshop.sms;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.SmsType;

public final class SmsTemplateUtil {

  private SmsTemplateUtil() {
    
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
  public static List<SmsType> getUsableSmsTypeList(OperatingMode mode,
      boolean siteUser, boolean information) {
    List<SmsType> smsTypeList = new ArrayList<SmsType>();

    if (siteUser) {
      if (information) {
        smsTypeList.add(SmsType.INFORMATION);
      }
      // 全モードでサイトに関連付いているテンプレート
//      smsTypeList.add(SmsType.CUSTOMER_REGISTERED);
//      smsTypeList.add(SmsType.RECEIVED_WITHDRAWAL_NOTICE);
//      smsTypeList.add(SmsType.COMPLETED_WITHDRAWAL);
//      smsTypeList.add(SmsType.PASSWORD_CONFIRMATION);
//      smsTypeList.add(SmsType.BIRTHDAY);
//      smsTypeList.add(SmsType.POINT_EXPIRED);
//      smsTypeList.add(SmsType.REISSUE_PASSWORD);

      if (mode == OperatingMode.MALL) {
//        // モール一括決済でサイトに関連付いているテンプレート
//        smsTypeList.add(SmsType.ORDER_DETAILS_PC);
        smsTypeList.add(SmsType.ORDER_DETAILS_PC);
//        smsTypeList.add(SmsType.CANCELLED_ORDER);
//        smsTypeList.add(SmsType.PAYMENT_REMINDER);
//        smsTypeList.add(SmsType.RECEIVED_PAYMENT);
//        smsTypeList.add(SmsType.RESERVATION_DETAIL);
//        smsTypeList.add(SmsType.CANCELLED_RESERVATION);
      } else if (mode == OperatingMode.ONE) {
        // 一店舗版でサイトに関連付いているテンプレート
//        smsTypeList.add(SmsType.ORDER_DETAILS_PC);
        smsTypeList.add(SmsType.ORDER_DETAILS_PC);
//        smsTypeList.add(SmsType.CANCELLED_ORDER);
//        smsTypeList.add(SmsType.PAYMENT_REMINDER);
//        smsTypeList.add(SmsType.RECEIVED_PAYMENT);
//        smsTypeList.add(SmsType.RESERVATION_DETAIL);
//        smsTypeList.add(SmsType.CANCELLED_RESERVATION);
          smsTypeList.add(SmsType.SHIPPING_INFORMATION);
//        smsTypeList.add(SmsType.ARRIVAL_INFORMATION);
          
          // saikou 2011/12/28 ob add start
          smsTypeList.add(SmsType.COUPON_START);
          smsTypeList.add(SmsType.COUPON_END);
          // saikou 2011/12/28 ob add end
      }
    } else {
      if (mode == OperatingMode.MALL) {
        // モール一括決済でショップに関連付いているテンプレート
        smsTypeList.add(SmsType.SHIPPING_INFORMATION);
        smsTypeList.add(SmsType.ARRIVAL_INFORMATION);
      } else if (mode == OperatingMode.SHOP) {
        // ショップ個別決済でショップに関連付いているテンプレート
//        smsTypeList.add(SmsType.ORDER_DETAILS_PC);
        smsTypeList.add(SmsType.ORDER_DETAILS_PC);
//        smsTypeList.add(SmsType.CANCELLED_ORDER);
//        smsTypeList.add(SmsType.PAYMENT_REMINDER);
//        smsTypeList.add(SmsType.RECEIVED_PAYMENT);
//        smsTypeList.add(SmsType.RESERVATION_DETAIL);
//        smsTypeList.add(SmsType.CANCELLED_RESERVATION);
        smsTypeList.add(SmsType.SHIPPING_INFORMATION);
//        smsTypeList.add(SmsType.ARRIVAL_INFORMATION);
      }
    }

    return smsTypeList;
  }
  
  public static List<SmsTemplateTag> getUsableTagList(SmsType type, String substitutionTag) {
    List<SmsTemplateTag> tagList = new ArrayList<SmsTemplateTag>();
    SmsComposition composition = SmsComposition.fromSmsTypeAndTag(type, substitutionTag);
    if (composition != null) {
      tagList.addAll(composition.getUseableTagList());
    }
    return tagList;
  }

}
