package jp.co.sint.webshop.web.message.back.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DashboardMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 支払有効期限切れメッセージ
        {
            "payment_deadline", "有支付有效期限过期的订购。"
        },

        // 退会依頼メッセージ
        {
            "customer_withdrawal", "有退会申请中的会员。"
        },
        // 在庫切れSKU件数メッセージ
        {
            "stock_limit", "有库存过期的商品。"
        },
        // ポイントルール運用中メッセージ
        {
            "point_enabled", "现在积分规则运用中。"
        },
        {
          "coupon_enabled", "现在优惠券规则运用中。"
        },
        // ボーナスポイントメッセージ
        {
            "bonus_point", "今日是{0}%的奖金积分期间中。"
        },
        // キャンペーンメッセージ
        {
            "campaign_underway", "到{0}『{1}』实施中 {2}%减价。"
        },

    };
    return obj;
  }

}
