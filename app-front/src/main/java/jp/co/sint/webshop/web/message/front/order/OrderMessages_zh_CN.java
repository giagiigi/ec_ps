package jp.co.sint.webshop.web.message.front.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class OrderMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 全額ポイント払いメッセージ

        {
            "point_in_full", "使用全额积分或优惠券支付。"
        },
        // URLパラメータ不正エラーメッセージ

        {
            "wrong_url_parameter", "URL参数不正确。"
        },
        // ポイント不足エラーメッセージ
        {
            "absence_point", "积分不足"
        },
        // 追加商品必須エラーメッセージ
        {
            "required_additional_commodity", "必须输入追加商品"
        },
        // 追加お届け先不正エラーメッセージ
        {
            "wrong_additional_delivery", "请选择追加的投送地点。"
        },
        // 同一お届け先変更エラーメッセージ
        {
            "change_same_delivery", "对同样投送地点的变更不能。"
        },
        // 全商品削除エラーメッセージ

        {
            "delete_all_commodity", "商品至少必要1个。"
        },
        // 全商品削除エラーメッセージ

        {
            "delete_all_delivery", "投送地点至少必要1件。"
        },
        // アンケート登録失敗エラーメッセージ

        {
            "enquete_register_failed", "问卷调查的登录失败了。"
        },
        // 商品購入エラー時のリカバリメッセージ
        {
            "restart_cashier", "请从开始重做商品购买。"
        },
        // 与信失敗（カードによる）エラーメッセージ
        {
            "card_authorization_incomplete_error", "顾客的信用卡不能使用。"
        },
        // カート商品追加エラー
        {
            "add_cart", "对购物车不能追加商品。"
        },
        // 決済情報認証失敗エラー

        {
            "settelment_incomplete_invalid_parameter", "{0}不被认证。"
        },
        // 予約上限数オーバーエラー
        {
            "reservation_over", "{0}能到合计{1}个预约。"
        },
        // 合計予約上限数オーバーエラー
        {
            "total_reservation_over", "{0}能与购物车的数量合起到{1}个预约。"
        },
        // 予約不可エラー

        {
            "not_available", "{0}因为超越了最大预约接待数，不能受理预约。"
        },
        // 期間外エラー(商品名指定)
        {
            "out_of_period_with_name", "因为{0}的销售条件变化了，请再一次把商品重新放入购物车。"
        },
        // 商品未存在エラー
        {
            "not_exist", "{0}已售罄。"
        },
        
        // 2012/11/23 促销对应 ob add start
        // 同一活动同一商品重复领取错误
        {
            "multiple_accept_error", "单次购买同一活动的同一商品只可领取一次。"
        },
        {
            "multiple_gift_accept_error", "您要参加的活动已过期或者您的订单已不再满足活动条件。"
        },
        {
            "multiple_gift_stock_error", "您要领取的商品现在没有库存。"
        },
        {
            "commodity_coupon_error", "您输入的折扣券无效。"
        },
        {
            "commodity_coupon_detail_error", "您输入的折扣券:{0}不存在或者已过期。"
        },
        {
            "commodity_coupon_use_limit_error", "您输入的折扣券{0}适用商品的最小购物数为{1}个。"
        },
        {
            "commodity_coupon_max_commodity_num_error", "您输入的折扣券：{0}已超出使用次数限制，不可使用。"
        },
        {
            "commodity_coupon_and_discount_duplicate_error", "媒体礼品券和折扣券不可同时使用。"
        },
        // 2012/11/23 促销对应 ob add end
        // 2013/04/07 优惠券对应 ob add start
        {
            "discount_duplicate_error", "一张订单只能使用一张优惠券。"
        },
        {
            "use_coupon_of_issue_by_self_error", "您不能使用自己发行的优惠券。"
        },
        // 2013/04/07 优惠券对应 ob add end
        
        // 在庫引当エラーメッセージ(在庫切れ)
        {
            "stock_allocate_default_error", "{0}没有库存"
        },
        // 10.1.2 10110 追加 ここから
        // 在庫引当エラーメッセージ(排他制御の衝突)
        {
          "stock_concurrency_control_error", "现在系统正忙，请稍候再次下订单。"
        },
        // 10.1.2 10110 追加 ここまで
        // 在庫引当エラーメッセージ(在庫不足)
        {
            "stock_shortage_error", "{0}能到合计{1}个购买。"
        },
        // 合計数在庫引当エラー(在庫不足)
        {
            "total_stock_shortage_error", "库存数量不足，{0}的当前库存数只允许购买{1}个。"
        },
        // 表示可能なお支払い方法が代金引換のみの場合に、自宅以外のお届け先を選択したときのエラー

        {
            "cash_on_delivery_select_error", "在这个店铺不能发送给订单买主本人以外的顾客，并且不对应复数商品使用不同发送方法(快递、EMS、…)。"
        },
        // 表示可能なお支払い方法が代金引換のみかつ、お支払い金額のすべてをポイントでお支払いできない場合に、自宅以外のお届け先を選択したときのエラー

        {
            "cash_on_delivery_select_and_no_point_error", "积分不能支付支付金额的全部的情况，同时订单给买主本人先生以外的报告，和复数发送方法(通常便、特別便、…)商品。"
        },
        // お支払い方法の存在エラー
        {
            "no_payment_method_error", "指定的支付方式，不能利用。"
        },
        // 予約商品複数配送先追加エラー

        {
            "add_reserve_plural_shipping_error", "预约商品不能指定复数的投送地点。"
        },
        // 注文合計金額上限エラー
        // M17N-0003 追加 ここから
        // 注文毎注文上限数オーバーエラー
        {
            "oneshot_order_over", "购物车的数量，{0}最多只能购买{1}个。"
        },
        // 輸出禁止国オーバーエラー
        {
            "embargoed_commodity", "{0}不能配送到{1}这个国家。"
        },
        // 輸出禁止国オーバーエラー
        {
            "embargoed_commodity_detail", "这个商品不能配送到{0}这些国家。"
        },
        // M17N-0003 追加 ここまで
        {
            "total_amount_over", "因为支付共计金额超过{0}的支付上限金额{1}不能购买。 请变更支付方式或者订单内容。"
        },
        //add by zhanghaibin start 2010-05-18     
        // 使用积分小于积分最小使用单位

        {
            "point_small_not_error", "{0}请设定为{1}的倍数。"
        },
        //add by zhanghaibin end   2010-05-18     
        // ゲスト別配送先指定エラー
        {
            "guest_other_delivery_error", "如果不能发送方法不相同的商品的同时进行购买，指定给买主本人先生以外的报告。"
        },
        {
          "amount_required_error", "请输入No.{0}的数量。"
        },
        {
          "max_useable_coupon_count", "一个订单最多只能用{0}张优惠券。"
        },
        {
          "is_used_coupon", "该礼金券不可使用。"
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
