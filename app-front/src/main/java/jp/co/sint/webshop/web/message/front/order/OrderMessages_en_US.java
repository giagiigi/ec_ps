package jp.co.sint.webshop.web.message.front.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class OrderMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 全額ポイント払いメッセージ
        {
            "point_in_full", "Payment by points in full."
        },
        // URLパラメータ不正エラーメッセージ
        {
            "wrong_url_parameter", "Incorrect URL parameter."
        },
        // ポイント不足エラーメッセージ
        {
            "absence_point", "Tere is not enough points left."
        },
        // 追加商品必須エラーメッセージ
        {
            "required_additional_commodity", "Addtional item is required to input."
        },
        // 追加お届け先不正エラーメッセージ
        {
            "wrong_additional_delivery", "Please select addtional delivery address."
        },
        // 同一お届け先変更エラーメッセージ
        {
            "change_same_delivery", "You can not modify the same delivery address."
        },
        // 全商品削除エラーメッセージ
        {
            "delete_all_commodity", "At least one item is needed."
        },
        // 全商品削除エラーメッセージ
        {
            "delete_all_delivery", "At least one delivery address is needed."
        },
        // アンケート登録失敗エラーメッセージ
        {
            "enquete_register_failed", "Failed in registering the enquete"
        },
        // 商品購入エラー時のリカバリメッセージ
        {
            "restart_cashier", "Please retry the purchase item from the beggining."
        },
        // 与信失敗（カードによる）エラーメッセージ
        {
            "card_authorization_incomplete_error", "Your card can not be used."
        },
        // カート商品追加エラー
        {
            "add_cart", "Couldn't add the item to the cart."
        },
        // 決済情報認証失敗エラー
        {
            "settelment_incomplete_invalid_parameter", "{0} was not authorized."
        },
        // 予約上限数オーバーエラー
        {
            "reservation_over", "{0} can be reserved {1} pieces max"
        },
        // 合計予約上限数オーバーエラー
        {
            "total_reservation_over", "{0} can be reserved {1} pieces in all (with the quantity in the cart)."
        },
        // 予約不可エラー
        {
            "not_available", "{0}: Unable to accept the reservation. the maximum reservation limit has exceeded. "
        },
        // 期間外エラー(商品名指定)
        {
            "out_of_period_with_name", "The sales condition of {0} has been changed. Please add the item to the cart."
        },
        // 商品未存在エラー
        {
            "not_exist", "The product {0} is out of stock."
        },
        // 2012/11/23 促销对应 ob add start
        // 同一活动同一商品重复领取错误
        {
            "multiple_accept_error", "Same item can be received only once at the same campain."
        },
        {
            "multiple_gift_accept_error", "This campain is expired or your order does not meet the requirementｓ for this campain."
        },
        {
            "multiple_gift_stock_error", "This item is out of stock at the moment."
        },
        {
            "commodity_coupon_error", "This Coupon code is not valid."
        },
        {
            "commodity_coupon_detail_error", "This Coupon code{0}does not exist or has expired."
        },
        {
            "commodity_coupon_use_limit_error", "{0} Coupon can be used only when you purchase more than {1} of this item."
        },
        {
            "commodity_coupon_max_commodity_num_error", "This coupon {0} can not be used because it is over the number of times."
        },
        {
            "commodity_coupon_and_discount_duplicate_error", "This coupon can not be used with other gift voucher together."
        },
        // 2012/11/23 促销对应 ob add end
        // 2013/04/07 优惠券对应 ob add start
        {
            "discount_duplicate_error", "This coupon can not be used with other gift voucher together."
        },
        {
            "use_coupon_of_issue_by_self_error", "您不能使用自己发行的优惠券。"
        },
        // 2013/04/07 优惠券对应 ob add end
        // 在庫引当エラーメッセージ(在庫切れ)
        {
            "stock_allocate_default_error", "{0} is out of stock."
        },
        // 10.1.2 10110 追加 ここから
        // 在庫引当エラーメッセージ(排他制御の衝突)
        {
          "stock_concurrency_control_error",
          "It is very crowded just now. Please order again at a certain intervals when it is a few. "
        },
        // 10.1.2 10110 追加 ここまで
        // 在庫引当エラーメッセージ(在庫不足)
        {
            "stock_shortage_error", "{0} can be purchased {1} pieces max."
        },
        // 合計数在庫引当エラー(在庫不足)
        {
            "total_stock_shortage_error", "{0} can be purchased {1} pieces in all (with the quantity in the cart)."
        },
        // 表示可能なお支払い方法が代金引換のみの場合に、自宅以外のお届け先を選択したときのエラー

        {
            "cash_on_delivery_select_error",
            "You can not order the item of delivery to the someone who is not purchaser, "
            + "and of multi deliver methods(normal delivery, special delivery ...) at one order."
        },
        // 表示可能なお支払い方法が代金引換のみかつ、お支払い金額のすべてをポイントでお支払いできない場合に、自宅以外のお届け先を選択したときのエラー
        {
            "cash_on_delivery_select_and_no_point_error",
            "If you can not pay all the payment with the points, "
            + "You can not order the item of delivery to the someone who did not purchase, "
            + "and of multi deliver method(normal delivery, special delivery ....)."
        },
        // お支払い方法の存在エラー
        {
            "no_payment_method_error", "Unable to use the payment method."
        },
        // 予約商品複数配送先追加エラー
        {
            "add_reserve_plural_shipping_error", "The reserved item does not appliy to the multi delivery addresses."
        },
        // 注文合計金額上限エラー
        {
            "total_amount_over",
            "The total payment price has exceeded the payment limit:{1} of {0}. "
            + "Please change the payment method or the order contents."
        },
        // M17N-0003 追加 ここから
        // 注文毎注文上限数オーバーエラー
        {
            "oneshot_order_over", "{0}It peels off and , wagon can be orderedto {1} piece together with the amount."
        },
        // 輸出禁止国オーバーエラー
        {
            "embargoed_commodity", "{0} , wagon can be orderedto {1} piece together with the amount."
        },
        // 輸出禁止国オーバーエラー
        {
            "embargoed_commodity_detail", "This commodity cannot be delivered to {0}."
        },
        // M17N-0003 追加 ここまで
        // ゲスト別配送先指定エラー
        {
            "guest_other_delivery_error",
            "Unable to appoint the delivery to the someone who did not purchase, "
            + "when purchasing the two different delivery method items at the same time."
        },
        {
          "amount_required_error", "请输入No.{0}的数量。"
        },
        {
          "max_useable_coupon_count", "一个订单最多只能用{0}张优惠券。"
        },
        {
        "is_used_coupon", "优惠券{0}已被使用。"
        },
        {
          "out_of_time_coupon", "优惠券{0}不在可使用期间内。"
        }, 
        {
          "select_useable_coupon", "请选择优惠券。"
        }
    };
    return obj;
  }
}
