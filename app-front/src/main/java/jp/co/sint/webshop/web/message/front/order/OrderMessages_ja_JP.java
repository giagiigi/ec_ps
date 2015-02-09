package jp.co.sint.webshop.web.message.front.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderMessages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 全額ポイント払いメッセージ
        {
            "point_in_full", "全額ポイントでのお支払いになります。"
        },
        // URLパラメータ不正エラーメッセージ
        {
            "wrong_url_parameter", "URLパラメータが不正です。"
        },
        // ポイント不足エラーメッセージ
        {
            "absence_point", "ポイントが不足しています。"
        },
        // 追加商品必須エラーメッセージ
        {
            "required_additional_commodity", "追加商品は必須入力です。"
        },
        // 追加お届け先不正エラーメッセージ
        {
            "wrong_additional_delivery", "追加するお届け先を選択してください。"
        },
        // 同一お届け先変更エラーメッセージ
        {
            "change_same_delivery", "同一お届け先への変更はできません。"
        },
        // 全商品削除エラーメッセージ
        {
            "delete_all_commodity", "商品は少なくとも１つ必要です。"
        },
        // 全商品削除エラーメッセージ
        {
            "delete_all_delivery", "お届け先は少なくとも１件必要です。"
        },
        // アンケート登録失敗エラーメッセージ
        {
            "enquete_register_failed", "アンケートの登録に失敗しました。"
        },
        // 商品購入エラー時のリカバリメッセージ
        {
            "restart_cashier", "はじめから商品購入をやり直してください。"
        },
        // 与信失敗（カードによる）エラーメッセージ
        {
            "card_authorization_incomplete_error", "お客様のカードはご利用になれません。"
        },
        // カート商品追加エラー
        {
            "add_cart", "カートに商品を追加できませんでした。"
        },
        // 決済情報認証失敗エラー
        {
            "settelment_incomplete_invalid_parameter", "{0}が認証されませんでした。"
        },
        // 予約上限数オーバーエラー
        {
            "reservation_over", "{0}は、合計{1}個まで予約することができます。"
        },
        // 合計予約上限数オーバーエラー
        {
            "total_reservation_over", "{0}は、カートの数量と合わせて{1}個まで予約することができます。"
        },
        // 予約不可エラー
        {
            "not_available", "{0}は、最大予約受け付け数を超えたため、予約を受け付けられません。"
        },
        // 期間外エラー(商品名指定)
        {
            "out_of_period_with_name", "{0}の販売条件が変わったため、もう一度カートに商品を入れ直してください。"
        },
        // 商品未存在エラー
        {
            "not_exist", "{0}商品は現在在庫がございません。"
        },
        // 2012/11/23 促销对应 ob add start
        // 同一活动同一商品重复领取错误
        {
            "multiple_accept_error", "同じキャンペーンに同一商品の受領は一回のみとなります。"
        },
        {
            "multiple_gift_accept_error", "ご希望のキャンペーンは期限が過ぎているかまたはご注文内容はキャンペーン条件を満たしていないです。"
        },
        {
            "multiple_gift_stock_error", "ご希望の商品は在庫切れです。"
        },
        {
            "commodity_coupon_error", "このクーポン券コードは無効です。"
        },
        {
            "commodity_coupon_detail_error", "ご利用のクーポン券{0}は存在しないかまたは期限が切れています。"
        },
        {
            "commodity_coupon_use_limit_error", "この商品は{1}コ以上をお買上げの場合、{0}ギフト券をご利用いただけます。"
        },
        {
            "commodity_coupon_max_commodity_num_error", "ご利用のクーポン券{0}はご利用回数制限を超えており、ご利用できません。"
        },
        {
            "commodity_coupon_and_discount_duplicate_error", "ご利用のクーポン券はほかのギフト券との併用はできません。"
        },
        // 2012/11/23 促销对应 ob add end
        // 2013/04/07 优惠券对应 ob add start
        {
            "discount_duplicate_error", "ご利用のクーポン券はほかのギフト券との併用はできません。"
        },
        {
           "use_coupon_of_issue_by_self_error", "您不能使用自己发行的优惠券。"
        },
        // 2013/04/07 优惠券对应 ob add end
        // 在庫引当エラーメッセージ(在庫切れ)
        {
            "stock_allocate_default_error", "{0}は在庫がありません。"
        },
        // 10.1.2 10110 追加 ここから
        // 在庫引当エラーメッセージ(排他制御の衝突)
        {
          "stock_concurrency_control_error", "ただいま大変混雑しております。少し時間をおいてもう一度注文してください。"
        },
        // 10.1.2 10110 追加 ここまで
        // 在庫引当エラーメッセージ(在庫不足)
        {
            "stock_shortage_error", "{0}は、合計{1}個まで購入することができます。"
        },
        // 合計数在庫引当エラー(在庫不足)
        {
            "total_stock_shortage_error", "商品[{0}]が短時間に注文が集中したため在庫不足となり、{1}個以上のご購入になれません。大変申し訳御座いません。"
        },
        // 表示可能なお支払い方法が代金引換のみの場合に、自宅以外のお届け先を選択したときのエラー
        {
            "cash_on_delivery_select_error", "このショップでは、ご購入者本人様以外へのお届け、および複数配送方法（通常便、特別便、…）の商品を同時に注文することはできません。"
        },
        // 表示可能なお支払い方法が代金引換のみかつ、お支払い金額のすべてをポイントでお支払いできない場合に、自宅以外のお届け先を選択したときのエラー
        {
            "cash_on_delivery_select_and_no_point_error",
            "お支払金額のすべてをポイントでお支払いできない場合、ご購入者本人様以外へのお届け、および複数配送方法（通常便、特別便、…）の商品を同時に注文することはできません。"
        },
        // お支払い方法の存在エラー
        {
            "no_payment_method_error", "ご指定のお支払い方法は、ご利用できません。"
        },
        // 予約商品複数配送先追加エラー
        {
            "add_reserve_plural_shipping_error", "予約商品は複数のお届け先を指定することはできません。"
        },
        // M17N-0003 追加 ここから
        // 注文毎注文上限数オーバーエラー
        {
            "oneshot_order_over", "{0}は、カートの数量と合わせて{1}個まで注文することができます。"
        },
        // 輸出禁止国オーバーエラー
        {
            "embargoed_commodity", "{0}は、{1}へお届けすることができません。"
        },
        // 輸出禁止国オーバーエラー
        {
            "embargoed_commodity_detail", "この商品は{0}にはお届けすることができません。"
        },
        // M17N-0003 追加 ここまで
        // 注文合計金額上限エラー
        {
            "total_amount_over", "お支払い合計金額が{0}のお支払い上限金額{1}を超えているため購入できません。 お支払い方法か注文内容を変更してください。"
        },
        // ゲスト別配送先指定エラー
        {
            "guest_other_delivery_error", "配送方法が異なる商品の同時購入を行う場合、ご購入者本人様以外へのお届けを指定することはできません。"
        },
        {
          "amount_required_error", "No.{0}の数量は必ず入力してください。"
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
