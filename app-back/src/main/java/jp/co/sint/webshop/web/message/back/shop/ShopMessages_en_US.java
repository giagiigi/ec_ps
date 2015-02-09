package jp.co.sint.webshop.web.message.back.shop;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShopMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 配送種別コードエラーメッセージ
        {
            "code_failed", "Invalid code {0}"
        },
        // 配送種別時間帯設定、開始終了エラー
        {
            "delivery_type_start_end_error", "The end time have to be later than start time."
        },
        // ポイント付与率マイナスエラー
        {
            "point_rate_minus_error", "Invest rate have to be more than 0."
        },
        // ボーナス時ポイント付与率マイナスエラー
        {
            "bonus_point_rate_minus_error", "Bonus point invest rate have to be more than 0."
        },
        // サイト未登録エラー
        {
            "no_site_error", "Site information is not registered."
        },
        // 支払方法非存在エラー
        {
            "no_payment_error", "The payment method may be deleted or not exist."
        },
        // 削除対象配送種別使用中エラー
        {
            "delivery_type_still_use_error", "Unable to delete the delivery type in which item has been registered."
        },
        // 削除対象配送種別削除不可エラー
        {
            "used_delivery_type", "Unable to delete the delivery type in which order info has been registered."
        },
        {
            "lead_time_minimal_error",
            "Delivery lead time have to be more than {0} days, if the Delivery appointed date/time"
                + " is set only [date only] or [both date and time]."
        },
        // 支払方法削除不可エラー
        {
            "undeletable_payment", "Unable to delete the payment method."
        },
        // 支払表示区分エラー
        {
            "invalid_payment_display_type", "{0} is not available on management site."
        },
        // メールテンプレート項目必須エラー
        {
            "required_tag", "Please input {1} in {0}."
        },
        // 表示可能な支払方法が存在しないエラー
        {
            "payment_unavailable", "The payment method have to be displayed one at least."
        },
        // 銀行全削除エラー
        {
            "delete_all_bank", "The bank info have to be registered one at least."
        },
        // 手数料重複エラー
        {
            "registered_commission", "Please input the purchase price that has not been registered in purchase price(to)."
        },
        //邮局机关消除
        {
            "delete_all_post" , "The post info have to be registered one at least."
        }
    };
    return obj;
  }
}
