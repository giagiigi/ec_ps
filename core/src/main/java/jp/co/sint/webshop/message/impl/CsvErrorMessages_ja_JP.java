package jp.co.sint.webshop.message.impl;

import java.util.ListResourceBundle;

public class CsvErrorMessages_ja_JP extends ListResourceBundle {

  protected Object[][] getContents() {
    return new Object[][] {
        {
            "digit", "有効な数値を入力してください。"
        }, {
            "date", "yyyy/mm/dd形式で入力してください。"
        }, {
            "datetime", "yyyy/MM/dd hh:mm:ss形式で入力してください。"
        // 10.1.1 10006 追加 ここから
        }, {
            "not_in_range", "{0}は{1}年から{2}年の範囲で入力してください。"
        // 10.1.1 10006 追加 ここまで  
        }, {
            "wrong_value", "不正な値です。"
        // 10.1.1 10019 追加 ここから
        }, {
            "minus_number_error", "{0}は0以上の数値を入力してください。"
        // 10.1.1 10019 追加 ここまで
        }, {
            "csv_not_found", "出力可能なデータはありませんでした。"
        }, {
            "validate_shopcode", "ショップコードが不正です。"
        }, {
            "not_exitst", "{0}が存在しません。"
        }, {
            "fixed_data", "売上確定済みのため、出荷実績を登録できません。"
        }, {
            "shipping_data_transported", "データ連携済みのため、出荷実績を登録できません。"
        }, {
            "shippingdate_before_orderdate", "出荷日が受注日以降の日付でないため、出荷実績を登録できません。"
        }, {
            "shippingdate_before_directdate", "出荷日が出荷指示日以降の日付でないため、出荷実績を登録できません。"
        }, {
            "arrivaldate_before_shippingdate", "到着予定日が出荷日以降の日付でないため、出荷実績を登録できません。"
        }, {
            "shipping_direct_before_orderdate", "出荷指示日が受注日以降の日付でないため、出荷実績を登録できません。"
        }, {
            "arrival_time_both_required", "到着予定時間を入力する場合は、必ず開始時刻と終了時刻を入力してください。"
        }, {
            "arrival_time_comparison_error", "到着予定開始時刻は、到着予定終了時刻よりも前の時刻を入力してください。"
        }, {
            "no_pay_and_payment_advance", "未入金かつ先払いのため、出荷実績を登録できません。"
        }, {
            "shipping_is_reserved", "予約商品のため、出荷実績を登録できません。"
        }, {
            "shipping_is_returned", "返品情報のため、出荷実績を登録できません。"
        }, {
            "rebuild_category_tree_failed", "カテゴリツリー再構築に失敗しました。カテゴリの親子構造が不正です。"
        }, {
            "rebuild_category_tree_succeeded", "カテゴリツリー再構築に成功しました。"
        }, {
            "delete_payment_is_shipped", "先払いかつ出荷済の商品が存在するため、入金取消ができません。"
        }, {
            "payment_is_cancelled", "キャンセル済の受注情報です。"
        }, {
            "fault_standard_name", "規格名称を1つのみ設定する場合、{0}2名称は設定できません。"
        }, {
            "self_address", "本人アドレスは、顧客アドレス取込処理で新規に登録する事はできません。"
        }, {
            "withdrawed_address", "退会済顧客のアドレスは、取込む事ができません。"
        }, {
            "withdrawed", "退会済顧客は、取込む事ができません。"
        }, {
            "prefecture_mismatch", "入力された住所１は、都道府県コードと関連付いていません。"
        }, {
            "duplicated_mail", "既に使用されているメールアドレスです。"
        }, {
            "duplicated_loginid", "既に使用されているログインIDです。"
        }, {
            "birthday", "生年月日は現在日付より100年以内でなければいけません。"
        }, {
            "cancell_or_return", "返品、またはキャンセルされているため宅配便伝票番号を登録する事ができません。"
        }, {
            "shipping_is_cancelled", "キャンセルされているため、出荷実績を登録できません。"
        }, {
            "delivery_slip_no_required", "宅配便伝票番号は必ず入力してください。"
        }, {
            "import_stock_io_failed", "入出庫取込に失敗しました。"
        }, {
            "stock_amount_overflow", "在庫数の合計は{0}以下にしてください。"
        }, {
            "stock_deliver_overflow", "この商品は引当または予約されているため、入出庫数は{0}以下を入力してください。"
        }, {
            "stock_no_changeable", "有効在庫がないため、在庫数を減らせません。"
        }, {
            "stock_quantity_invalid", "入出庫数は1以上を入力してください。"
        }, {
            "delete_commodity_error", "商品コード:{0}の商品は売上確定していない出荷データに含まれるため、削除できません。"
        }, {
            "category_max_depth_over", "カテゴリ階層は{0}階層までとしてください。"
        }, {
            "change_category_own", "親カテゴリを自分自身にすることはできません。"
        }, {
            "set_payment_credit", "クレジットカード決済の受注は入金日を変更する事ができません。"
        }, {
            "set_payment_no_payment", "支払不要の受注は入金日を変更する事ができません。"
        }, {
            "set_payment_point_in_full", "全額ポイント支払いの受注は入金日を変更する事ができません。"
        }, {
            "self_phone_no_required", "本人アドレスを登録する場合、電話番号は必ず入力してください。"
        }, {
          "invalid_country_and_region", "国コード・地域コードの組み合わせが不正です。" //added 50061
        }, {
          "address1_mismatch", "入力された住所１は、国コード・地域コードと関連付いていません。" //added 50061
        }, {
            // M17N-0007 追加 ここから
            "request_item", "{0}は必ず入力してください。"
        }, {
            "key_currency_no_update", "基軸通貨の為替レートを更新することはできません。"
        }, {
            "unsuported_currency_no_update", "対応していない通貨コードが指定されています。"
        }, {
            "exchange_rate_range", "{0}は正の数で指定してください。"
            // M17N-0007 追加 ここまで
        }, {
            "mobile_or_phone_required", "手机号码或者电话号码至少输入一个."
            // 10.1.5 10242 追加 ここから
        }, {
            "out_of_range",  "请输入{0}到{1}范围内的值。"
            // 10.1.5 10242 追加 ここまで
            // 10.1.5 10243 追加 ここから
        }, {
            "invalid_phone_format",  "请输入一个有效的电话号码。"
            // 10.1.5 10243 追加 ここまで
            // 10.1.6 10258 追加 ここから
        }, {
            "overlapped_values", "{0}和{1}请输入不同的数值。"
            // 10.1.6 10258 追加 ここまで
        }
    };
  }

}
