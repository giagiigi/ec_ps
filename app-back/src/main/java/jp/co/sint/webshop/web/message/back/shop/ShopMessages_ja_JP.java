package jp.co.sint.webshop.web.message.back.shop;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShopMessages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 配送種別コードエラーメッセージ
        {
            "code_failed", "{0}コードが正しくありません。"
        },
        // 配送種別時間帯設定、開始終了エラー
        {
            "delivery_type_start_end_error", "終了時間は、開始時間より後の時間を設定して下さい。"
        },
        // ポイント付与率マイナスエラー
        {
            "point_rate_minus_error", "付与率は0以上を入力して下さい。"
        },
        // ボーナス時ポイント付与率マイナスエラー
        {
            "bonus_point_rate_minus_error", "ボーナス時ポイント付与率は0以上を入力して下さい。"
        },
        // サイト未登録エラー
        {
            "no_site_error", "サイト情報が未登録です。"
        },
        // 支払方法非存在エラー
        {
            "no_payment_error", "該当の支払方法は存在しないか削除されました。"
        },
        // 削除対象配送種別使用中エラー
        {
            "delivery_type_still_use_error", "商品が登録されている配送種別は削除できません。"
        },
        // 削除対象配送種別削除不可エラー
        {
            "used_delivery_type", "受注情報が登録されている配送種別は削除できません。"
        }, {
            "lead_time_minimal_error", "配送日時指定が「日のみ」か「日時両方」の場合、配送リードタイムは{0}日以上を入力してください。"
        },
        // 支払方法削除不可エラー
        {
            "undeletable_payment", "該当の支払方法は削除できません。"
        },
        // メールテンプレート項目必須エラー
        {
            "required_tag", "{0}には、必ず{1}を入力して下さい。"
        },
        // 表示可能な支払方法が存在しないエラー
        {
            "payment_unavailable", "支払方法は、最低1つは表示されるようにしてください。"
        },
        // 支払表示区分エラー
        {
          "invalid_payment_display_type", "{0}は管理側で使用することはできません。"
        },
        // 銀行全削除エラー
        {
            "delete_all_bank", "金融機関は、最低1つは登録されているようにしてください。"
        },
        // 手数料重複エラー
        {
            "registered_commission", "購入金額には、購入金額(To)に登録されていない金額を入力してください。"
        },
        //邮局机关消除
        {
            "delete_all_post" , "邮便局(ゆうびんきょく)机関に登录されていてください最低1個。"
        }
    };
    return obj;
  }
}
