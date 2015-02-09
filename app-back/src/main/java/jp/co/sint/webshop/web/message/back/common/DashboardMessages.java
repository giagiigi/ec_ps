package jp.co.sint.webshop.web.message.back.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DashboardMessages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 支払有効期限切れメッセージ
        {
            "payment_deadline", "支払有効期限切れの注文があります。"
        },

        // 退会依頼メッセージ
        {
            "customer_withdrawal", "退会依頼中の会員がいます。"
        },
        // 在庫切れSKU件数メッセージ
        {
            "stock_limit", "在庫切れ商品があります。"
        },
        // ポイントルール運用中メッセージ
        {
            "point_enabled", "現在、ポイントルール運用中です。"
        },
        {
          "coupon_enabled", "现在优惠券规则运用中。"
        },
        // ボーナスポイントメッセージ
        {
            "bonus_point", "本日は、{0}%のボーナスポイント期間中です。"
        },
        // キャンペーンメッセージ
        {
            "campaign_underway", "{0}まで、『{1}』実施中 {2}%値引です。"
        },

    };
    return obj;
  }

}
