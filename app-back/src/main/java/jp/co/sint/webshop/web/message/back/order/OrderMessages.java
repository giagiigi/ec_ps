package jp.co.sint.webshop.web.message.back.order;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderMessages extends ListResourceBundle {

  private static final Object[][] ORDER_MESSAGES_MAIN1 = {
      {
          "point_in_full", "全額ポイントでの支払になります。"
      }, {
          "return_orver", "返品数が商品購入数を超えています。"
      }, {
          "still_paid", "入金済みの受注情報です。"
      }, {
          "paid_order_with_no", "受注番号:{0}は入金済みです。"
      }, {
          "not_paid_order_with_no", "受注番号:{0}は未入金です。"
      }, {
          "still_shipping_order", "出荷手配中もしくは出荷済みの受注情報です。"
      }, {
          "delivery_appointed_date", "配送指定日は有効な日付ではありません。"
      }, {
          "past_delivery_appointed_date", "配送指定日が過去もしくは当日日付です。"
      }, {
          "delivery_appointed_time_both", "配送指定時間が開始時間か終了時間の片方しか入力されていません。"
      }, {
          "add_commodity_shipping_not_found", "配送先・配送ショップ・配送種別・受注状況の異なる商品の追加は新規受注として登録してください。"
      }, {
          "change_same_delivery", "同一お届け先への変更はできません。"
      }, {
          "no_shipping", "配送先情報が存在しません。"
      }, {
          // 10.1.4 10201 修正 ここから
          // "over_usable_point", "使用可能ポイントを超えています。"
          "over_usable_point", "入力された使用ポイントが、ポイント残高を超えています。使用ポイントの再設定を行ってください。"
      // 10.1.4 10201 修正 ここまで
      }, {
          "order_data_transported_with_no", "受注番号:{0}はデータ連携済みのため、更新できません。"
      }, {
          "shipping_data_transported_with_no", "出荷番号:{0}はデータ連携済みのため、更新できません。"
      }, {
          "delete_shipping_data", "該当の出荷情報は削除できません。"
      }, {
          "delete_all_commodity", "商品は少なくとも１つ必要です。"
      }, {
          "delete_all_delivery", "配送先は少なくとも１件必要です。"
      }, {
          "fixed_data", "該当の出荷情報は売上確定済みです。"
      }, {
          "fixed_data_with_no", "出荷番号:{0}は売上確定済みです。"
      }, {
          "change_prefecture_shipping", "出荷番号:{0}の都道府県が変更されています。"
      }, {
          "shipping_data_sales_status_not_fixed", "出荷番号:{0}は出荷が確定していません。"
      }, {
          "clear_payment_date_failed_with_no", "受注番号:{0}の入金日クリアに失敗しました。"
      }, {
          "fixed_shipping_status_with_no", "出荷番号:{0}は出荷ステータスを変更できません。"
      }, {
          "clear_shipping_failed_with_no", "出荷番号:{0}は出荷実績の取消ができません。"
      // 10.1.4 10188 追加 ここから
      }, {
          "clear_shipping_failed_for_transported", "出荷番号:{0}は売上送信済みのため、出荷実績の取消ができません。"
      // 10.1.4 10188 追加 ここまで
      }, {
          "shipping_direct_failed_with_no", "出荷番号:{0}の出荷指示に失敗しました。"
      }, {
          "shipped_result_failed_with_no", "出荷番号:{0}は出荷実績を登録できません。"
      }, {
          "no_status_suitable_for_shipping", "出荷番号:{0}は出荷可能でないため、出荷指示できません。"
      }, {
          "no_status_shipping_arrange", "出荷番号:{0}は出荷手配中でないため、出荷手配の取消ができません。"
      }, {
          "no_status_shipped", "出荷番号:{0}は出荷済みでないため、出荷実績の取消ができません。"
      }, {
          "no_pay_and_payment_advance_with_no", "出荷番号:{0}は未入金かつ先払いのため、出荷指示できません。"
      }, {
          "shop_not_found", "該当のショップが存在しません。"
      }
  };

  private static final Object[][] ORDER_MESSAGES_MAIN2 = new Object[][] {
      {
          "impossibility_shipping_direct_cause_date", "出荷番号:{0}は出荷指示日が設定されていないため、出荷手配できません。"
      }, {
          "impossibility_shipping_direct", "出荷番号:{0}は出荷手配中もしくは出荷済みのため、出荷手配できません。"
      }, {
          "impossibility_shipping_direct_date", "出荷番号:{0}は出荷手配中もしくは出荷済みのため、出荷指示日を更新できません。"
      }, {
          "impossibility_shipping_direct_date_cause_list", "出荷番号:{0}は出荷指示日が既に設定されているめ、一覧から設定できません。"
      }, {
          "impossibility_shipping_report", "出荷番号:{0}は出荷手配中でないため、出荷登録できません。"
      }, {
          "impossibility_appointed_date_cause_direct_past", "配送できない日付が配送指定日に設定されています。"
      }, {
          "impossibility_clear_shipping_direct_date", "出荷番号:{0}は出荷済みのため出荷指示日の取消はできません。"
      }, {
          "impossibility_clear_shipping_direct_date_inprocess", "出荷番号:{0}は出荷手配中のため出荷指示日の取消はできません。"
      }, {
          "impossibility_clear_shipping_direct_date_not_set", "出荷番号:{0}の出荷指示日は未設定のため出荷指示日の取消はできません。"
      }, {
          "will_shipping_report_date", "出荷日は、現在日付以前の日付を入力してください。"
      }, {
          "direct_without_date", "出荷番号:{0}は出荷指示日が指定されているため、出荷手配のみ実行しました。"
      }, {
          "shipping_direct_input_orderdate_or_later_with_no", "出荷番号:{0}の出荷日は、出荷指示日以降の日付を入力してください。"
      }, {
          "shipping_input_orderdate_or_later_with_no", "出荷番号:{0}の{1}は、受注日以降の日付を入力してください。"
      }, {
          "no_customer_to_send_shipping_mail_with_no", "出荷番号:{0}の顧客は存在しません。"
      },
      // Add by V10-CH start
      {
          "no_customer_to_send_shipping_sms_with_no", "出荷番号:{0}の顧客は存在しません。"
      },
      // Add by V10-CH end
      {
          "shipped_and_payment_advance_with_no", "受注番号:{0}は出荷済みかつ先払いのため、入金取消できません。"
      }, {
          "delivery_appoint_orderdate_or_later_with_no", "出荷番号:{0}の配送指定日時は、受注日時以降の日時を入力してください。"
      }, {
          "payment_orderdate_or_later_with_no", "受注番号:{0}の入金日は、受注日時以降の日時を入力してください。"
      }, {
          "not_set_clear_payment_date", "受注番号:{0}は入金日取消できません。"
      }, {
          "already_set_clear_payment_date", "受注番号:{0}は入金済みです。"
      }, {
          "order_changed_error", "受注情報が変更されています。"
      }, {
          "credit_cancel_error", "カード会社の処理に失敗しました。もう一度キャンセルして下さい。"
      }, {
          "stock_shortage", "SKUコード:{0}は、合計{1}個まで購入することができます。"
      }, {
          "no_available_stock_sku", "SKUコード:{0}は在庫切れです。"
      // 10.1.6 10277 追加 ここから
      }, {
          "stock_allocate_default_error", "{0}は在庫がありません。"
      // 10.1.6 10277 追加 ここまで
      // 10.1.2 10110 追加 ここから
      }, {
          "stock_concurrency_control_error", "ただいま大変混雑しております。少し時間をおいてもう一度注文してください。"
      // 10.1.2 10110 追加 ここまで
      }, {
          "out_of_period", "SKUコード:{0}は販売条件が変わったため、もう一度カートに商品を入れなおして下さい。"
      }, {
          "set_payment_date_canceled_order", "キャンセルした受注は入金日設定できません。"
      }, {
          "set_payment_date_canceled_order_with_no", "受注番号:{0}キャンセルした受注は{1}できません。"
      }, {
          "set_payment_credit", "受注番号:{0} 支払方法がクレジットカード決済の受注は{1}できません。"
      }, {
          "update_returned_shipping", "この出荷情報は返品済みのため、更新できません。"
      }, {
          "cancelled_shipping_with_no", "出荷番号:{0}はキャンセルした出荷のため、{1}できません。"
      }, {
          "return_item_loss_money_length_over", "返品損金額の合計は{0}桁以内になるように入力してください。"
      }, {
          "register_payment_data_transported", "受注番号:{0}はデータ連携中のため、入金日を設定できません。"
      }, {
          "clear_payment_data_transported", "受注番号:{0}はデータ連携中のため、入金日を削除できません。"
      }, {
          "settelment_incomplete_invalid_parameter", "{0}が認証されませんでした。"
      }, {
          "withdrawal_customer", "受注番号:{0}は顧客が退会済みです。"
      }, {
          "cash_on_delivery_select_error", "このショップでは、ご購入者本人様以外へのお届け、および配送方法が異なる商品の同時購入はできません。"
      }, {
          "cash_on_delivery_select_and_no_point_error", "支払金額のすべてをポイントで支払できない場合、ご購入者本人様以外へのお届け、および配送方法が異なる商品の同時購入はできません。"
      }, {
          "input_negative_value_error", "{0}は0以上の値を入力してください。"
      }, {
          "wrong_additional_delivery", "追加するお届け先を選択してください。"
      }, {
          "total_amount_over", "支払合計金額が{0}の支払上限金額{1}を超えているため購入できません。 支払方法か注文内容を変更してください。"
      }
  };

  @Override
  protected Object[][] getContents() {

    List<Object[]> list = new ArrayList<Object[]>();
    for (Object[] items : ORDER_MESSAGES_MAIN1) {
      list.add(items);
    }
    for (Object[] items : ORDER_MESSAGES_MAIN2) {
      list.add(items);
    }
    for (Object[] items : ORDER_MODIFY_MESSAGE) {
      list.add(items);
    }
    for (Object[] items : ORDER_MAIL_MESSAGE) {
      list.add(items);
    }
    // Add by V10-CH start
    for (Object[] items : ORDER_SMS_MESSAGE) {
      list.add(items);
    }
    // Add by V10-CH end
    for (Object[] items : ORDER_RESERVATION_MESSAGE) {
      list.add(items);
    }
    Object[][] obj = new Object[list.size()][2];
    for (int i = 0; i < list.size(); i++) {
      Object[] tmp = list.get(i);
      obj[i] = tmp;
    }
    return obj;
  }

  private static final Object[][] ORDER_MAIL_MESSAGE = new Object[][] {
      {
          "send_shipping_mail_failed_with_no", "出荷番号:{0}のメール送信に失敗しました。"
      }, {
          "send_order_mail_failed_with_no", "受注番号:{0}のメール送信に失敗しました。"
      }, {
          "set_payment_canceled_order_with_no", "受注番号:{0}はキャンセルした受注のため、{1}メールを送信できません。"
      }, {
          "send_mail_data_transported", "受注番号:{0}はデータ連携中のため、{1}メールを送信できません。"
      }, {
          "send_shipping_mail_data_transported_with_no", "出荷番号:{0}はデータ連携中のため、出荷報告メールを送信できません。"
      }, {
          "send_reminder_mail_to_not_pay", "受注番号:{0}は支払が不要のため、入金督促メールを送信できません。"
      }
  };

  private static Object[][] ORDER_SMS_MESSAGE = new Object[][] {
      {
          "send_shipping_sms_failed_with_no", "出荷番号:{0}のメール送信に失敗しました。"
      }, {
          "send_order_sms_failed_with_no", "受注番号:{0}のメール送信に失敗しました。"
      }, {
          "send_shipping_sms_data_transported_with_no", "出荷番号:{0}はデータ連携中のため、出荷報告メールを送信できません。"
      }
  };

  private static final Object[][] ORDER_RESERVATION_MESSAGE = new Object[][] {
      {
          "shipping_is_reserved", "予約商品は出荷できません。"
      }, {
          "oneshot_reservation_over", "SKUコード:{0}は注文ごとの予約上限数を超えています。"
      }, {
          "add_reserve_plural_shipping_error", "予約商品は複数のお届け先を指定することはできません。"
      }, {
          "not_available", "SKUコード:{0}は予約できません。"
      }, {
          "reservation_over", "SKUコード:{0}は、合計{1}個まで予約することができます。"
      }, {
          "not_reservation_change", "予約期間が終了しているため、注文内容を変更することはできません。"
      }
  };

  private static final Object[][] ORDER_MODIFY_MESSAGE = new Object[][] {
      {
          "data_transported", "データ連携済みのため、更新できません。"
      }, {
          "order_modify_add_reserve", "予約商品は追加できません。"
      }, {
          "change_master_payment", "支払方法の手数料が更新されました。再度支払方法を選択し直してください。"
      }, {
          "update_payment_use_master", "支払方法の手数料が最新の手数料に更新されました。"
      }, {
          "change_master_shipping", "配送種別の送料が更新されました。再度配送先を選択し直してください。"
      }, {
          "change_master_commodity", "商品の販売価格が更新されました。再度商品を選択し直してください。"
      }, {
          "change_master_gift", "ギフト情報が更新されました。再度ギフトを選択し直してください。"
      }, {
          "cannot_change_gift_amount", "ギフト:{0}は使用できない状態に変更されたため、数量を増やすことはできません。"
      }, {
          "no_payment_method_error", "指定した支払方法は、削除されたか利用できない状態になっています。"
      }, {
          "add_reserve_payment_not_found_shop", "ショップが異なる商品、または予約商品を追加する場合は、一度カートをクリアしてください。"
      }, {
          "add_reserve_payment_not_found", "予約商品を追加する場合は、一度カートをクリアしてください。"
      }, {
          "add_sales_payment_not_found_shop", "ショップが異なる商品、または販売商品を追加する場合は、一度カートをクリアしてください。"
      }, {
          "add_sales_payment_not_found", "販売商品を追加する場合は、一度カートをクリアしてください。"
      }, {
          "modify_credit_error", "支払方法がクレジットカード払いの受注は修正できません。"
      }, {
          "not_exist", "SKUコード:{0}は、存在しないか販売期間外のため購入することができません。"
      }, {
          "not_exist_addition", "SKUコード:{0}は、存在しないか販売期間外のため数量を追加することができません。"
      }, {
          "add_commodity_amount_over", "商品追加後の数量が{0}桁を超えるため追加できません。"
      }, {
          "unusable_gift", "ギフト:{0}は使用できない状態に更新されたため、使用することはできません。"
      }, {
          "max_useable_coupon_count", "一个订单最多只能用{0}张优惠券。"
      }
  };
}
