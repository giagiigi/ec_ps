package jp.co.sint.webshop.web.message.front.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        //      全額ポイント払いメッセージ

        {
            "point_in_full", "使用全額積分支付。"
        },
        // URLパラメータ不正エラーメッセージ

        {
            "wrong_url_parameter", "URL參數不正確。"
        },
        // ポイント不足エラーメッセージ
        {
            "absence_point", "積分不足"
        },
        // 追加商品必須エラーメッセージ
        {
            "required_additional_commodity", "必須輸入追加商品"
        },
        // 追加お屆け先不正エラーメッセージ
        {
            "wrong_additional_delivery", "請選擇追加的投送地點。"
        },
        // 同一お屆け先変更エラーメッセージ
        {
            "change_same_delivery", "對同樣投送地點的變更不能。"
        },
        // 全商品削除エラーメッセージ

        {
            "delete_all_commodity", "商品至少必要1個。"
        },
        // 全商品削除エラーメッセージ

        {
            "delete_all_delivery", "投送地點至少必要1件。"
        },
        // アンケート登録失敗エラーメッセージ

        {
            "enquete_register_failed", "問卷調查的登錄失敗了。"
        },
        // 商品購入エラー時のリカバリメッセージ
        {
            "restart_cashier", "請從開始重做商品購買。"
        },
        // 與信失敗（カードによる）エラーメッセージ
        {
            "card_authorization_incomplete_error", "顧客的信用卡不能使用。"
        },
        // カート商品追加エラー
        {
            "add_cart", "對購物車不能追加商品。"
        },
        // 決済情報認証失敗エラー

        {
            "settelment_incomplete_invalid_parameter", "{0}不被認證。"
        },
        // 予約上限數オーバーエラー
        {
            "reservation_over", "{0}能到合計{1}個預約。"
        },
        // 合計予約上限數オーバーエラー
        {
            "total_reservation_over", "{0}能與購物車的數量合起到{1}個預約。"
        },
        // 予約不可エラー

        {
            "not_available", "{0}因為超越了最大預約接待數，不能受理預約。"
        },
        // 期間外エラー(商品名指定)
        {
            "out_of_period_with_name", "因為{0}的銷售條件變化了，請再一次把商品重新放入購物車。"
        },
        // 商品未存在エラー
        {
            "not_exist", "{0}已售罄。"
        },
        // 2012/11/23 促销对应 ob add start
        // 同一活动同一商品重复领取错误
        {
            "multiple_accept_error", "單次購買同一活動的同一商品只可領取一次。"
        },
        {
            "multiple_gift_accept_error", "您要參加的活動已過期或者您的訂單已不再滿足活動條件。"
        },
        {
            "multiple_gift_stock_error", "您要領取的商品現在沒有庫存。"
        },
        {
            "commodity_coupon_error", "您输入的折扣券无效。"
        },
        {
            "commodity_coupon_detail_error", "您輸入的折扣券:{0}不存在或者已過期。"
        },
        {
            "commodity_coupon_use_limit_error", "您輸入的折扣券{0}適用商品的最小購物數爲{1}個。"
        },
        {
            "commodity_coupon_max_commodity_num_error", "您輸入的折扣券：{0}已超出使用次數限制，不可使用。"
        },
        {
            "commodity_coupon_and_discount_duplicate_error", "媒體禮品券和折扣券不可同時使用。"
        },
        // 2012/11/23 促销对应 ob add end
        // 在庫引當エラーメッセージ(在庫切れ)
        {
            "stock_allocate_default_error", "{0}沒有庫存"
        },
        // 10.1.2 10110 追加 ここから
        // 在庫引當エラーメッセージ(排他制禦の衝突)
        {
          "stock_concurrency_control_error", "現在系統正忙，請稍候再次下訂單。"
        },
        // 10.1.2 10110 追加 ここまで
        // 在庫引當エラーメッセージ(在庫不足)
        {
            "stock_shortage_error", "{0}能到合計{1}個購買。"
        },
        // 合計數在庫引當エラー(在庫不足)
        {
            "total_stock_shortage_error", "{0}能與購物車的數量合起到{1}個購買。"
        },
        // 表示可能なお支払い方法が代金引換のみの場合に、自宅以外のお屆け先を選択したときのエラー

        {
            "cash_on_delivery_select_error", "在這個店鋪不能發送給訂單買主本人以外的顧客，並且不對應復數商品使用不同發送方法(通常便、特別便、…)。"
        },
        // 表示可能なお支払い方法が代金引換のみかつ、お支払い金額のすべてをポイントでお支払いできない場合に、自宅以外のお屆け先を選択したときのエラー

        {
            "cash_on_delivery_select_and_no_point_error", "積分不能支付支付金額的全部的情況，同時訂單給買主本人先生以外的報告，和復數發送方法(通常便、特別便、…)商品。"
        },
        // お支払い方法の存在エラー
        {
            "no_payment_method_error", "指定的支付方式，不能利用。"
        },
        // 予約商品複數配送先追加エラー

        {
            "add_reserve_plural_shipping_error", "預約商品不能指定復數的投送地點。"
        },
        // 註文合計金額上限エラー
        // M17N-0003 追加 ここから
        // 註文毎註文上限數オーバーエラー
        {
            "oneshot_order_over", "購物車的數量，{0}最多只能購買{1}個。"
        },
        // 輸出禁止國オーバーエラー
        {
            "embargoed_commodity", "{0}不能配送到{1}這個國家。"
        },
        // 輸出禁止國オーバーエラー
        {
            "embargoed_commodity_detail", "這個商品不能配送到{0}這些國家。"
        },
        // M17N-0003 追加 ここまで
        {
            "total_amount_over", "因為支付共計金額超過{0}的支付上限金額{1}不能購買。 請變更支付方式或者訂單內容。"
        },
        // ゲスト別配送先指定エラー

        {
            "guest_other_delivery_error", "如果不能發送方法不相同的商品的同時進行購買，指定給買主本人先生以外的報告。"
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
