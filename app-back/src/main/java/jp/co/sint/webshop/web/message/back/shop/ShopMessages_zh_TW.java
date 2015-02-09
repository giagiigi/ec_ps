package jp.co.sint.webshop.web.message.back.shop;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShopMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 配送種別コードエラーメッセージ
        {
            "code_failed", "{0}编码不正确。"
        },
        // 配送種別時間帯設定、開始終了エラー
        {
            "delivery_type_start_end_error", "请设定结束时间在开始时间之后。"
        },
        // ポイント付与率マイナスエラー
        {
            "point_rate_minus_error", "请填入给与率是０以上的数字。"
        },
        // ボーナス時ポイント付与率マイナスエラー
        {
            "bonus_point_rate_minus_error", "请填入奖金时的积分给与率是０以上的数字。"
        },
        // サイト未登録エラー
        {
            "no_site_error", "网站信息还未登录。"
        },
        // 支払方法非存在エラー
        {
            "no_payment_error", "该当的支付方法不存在或被删除了。"
        },
        // 削除対象配送種別使用中エラー
        {
            "delivery_type_still_use_error", "商品被登录后发送种别不能删除。"
        },
        // 削除対象配送種別削除不可エラー
        {
            "used_delivery_type", "登录的订购情报发送种别不能删除。"
        }, {
            "lead_time_minimal_error", "发送日时指定「只有日」或「日时两项」时，发送交货时间请填入{0}日以上。"
        },
        // 支払方法削除不可エラー
        {
            "undeletable_payment", "该当的支付方法不能删除。"
        },
        // 支払表示区分エラー
        {
          "invalid_payment_display_type", "{0}在管理网站不能利用。"
        },
        // メールテンプレート項目必須エラー
        {
            "required_tag", "{0}里必须填入{1}。"
        },
        // 表示可能な支払方法が存在しないエラー
        {
            "payment_unavailable", "支付方法请最低显示１个。"
        },
        // 銀行全削除エラー
        {
            "delete_all_bank", "金融机关请最低登录１个。"
        },
        // 手数料重複エラー
        {
            "registered_commission", "购入金额里没有登录到购入金额(To)，请填入购入金额。"
        },
        //邮局机关消除
        {
            "delete_all_post" , "邮局机关请最低登录1个。"
        }
    };
    return obj;
  }
}
