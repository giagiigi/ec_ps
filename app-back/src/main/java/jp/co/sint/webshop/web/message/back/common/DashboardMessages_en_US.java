package jp.co.sint.webshop.web.message.back.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DashboardMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 支払有効期限切れメッセージ
        {
            "payment_deadline", "There is the overdued order."
        },

        // 退会依頼メッセージ
        {
            "customer_withdrawal", "There is the member requesting withdrawal."
        },
        // 在庫切れSKU件数メッセージ
        {
            "stock_limit", "There is the item with no stock left."
        },
        // ポイントルール運用中メッセージ
        {
            "point_enabled", "The point rule is applied now."
        },
        {
          "coupon_enabled", "现在优惠券规则运用中。"
        },
        // ボーナスポイントメッセージ
        {
            "bonus_point", "Today is {0}% bonus point day."
        },
        // キャンペーンメッセージ
        {
            "campaign_underway", "\"{1}\" is underway, {2}% discount, until {0}"
        },

    };
    return obj;
  }

}
