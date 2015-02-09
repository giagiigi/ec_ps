package jp.co.sint.webshop.message.impl;

import java.util.ListResourceBundle;

public class CsvErrorMessages_zh_TW extends ListResourceBundle {

  protected Object[][] getContents() {
    return new Object[][] {
        {
            "digit", "請輸入有效的數值。"
        }, {
            "date", "請以yyyy/mm/dd形式輸入。"
        }, {
            "datetime", "請以yyyy/MM/dd hh:mm:ss形式輸入。"
        // 10.1.1 10006 追加 ここから
        }, {
            "not_in_range", "{0}請在{1}年到{2}年的範圍內輸入。"
        // 10.1.1 10006 追加 ここまで       
        }, {
            "wrong_value", "不正確的值。"
        // 10.1.1 10019 追加 ここから
        }, {
            "minus_number_error", "{0}請輸入0以上的數值。"
        // 10.1.1 10019 追加 ここまで
        }, {
            "csv_not_found", "沒有能輸出的數據。"
        }, {
            "validate_shopcode", "店鋪編號不正確。"
        }, {
            "not_exitst", "{0}不存在。"
        }, {
            "fixed_data", "因為銷售額確定完畢，不能登錄發貨實績。"
        }, {
            "shipping_data_transported", "因為數據聯合完畢，不能登錄發貨實績。"
        }, {
            "shippingdate_before_orderdate", "因為發貨日是訂單日以後的日期，不能登錄發貨實績。"
        }, {
            "shippingdate_before_directdate", "因為發貨日是發貨指示日以後的日期，不能登錄發貨實績。"
        }, {
            "arrivaldate_before_shippingdate", "因為到達預定日是發貨日以後的日期，不能登錄發貨實績。"
        }, {
            "shipping_direct_before_orderdate", "因為發貨指示日是訂單日以後的日期，不能登錄發貨實績。"
        }, {
            "arrival_time_both_required", "輸入到達預定時間的場合，請必定輸入開始時刻和結束時刻。"
        }, {
            "arrival_time_comparison_error", "到達預定開始時刻清輸入到達預定時刻之前的時刻。"
        }, {
            "no_pay_and_payment_advance", "因為領受人未付款，不能登錄發貨實績。"
        }, {
            "shipping_is_reserved", "因為預約商品，不能登錄發貨實績。"
        }, {
            "shipping_is_returned", "因為退貨信息，不能登錄發貨實績。"
        }, {
            "rebuild_category_tree_failed", "分類樹再構築失敗了。分類的父子構造不正確。"
        }, {
            "rebuild_category_tree_succeeded", "分類樹再構築成功了。"
        }, {
            "delete_payment_is_shipped", "因存在先付款和以發貨的商品，所以無法取消付款。"
        }, {
            "payment_is_cancelled", "是取消完畢的接受訂貨信息。"
        }, {
            "fault_standard_name", "如果只1個設定規格名稱，{0}2名稱不能設定。"
        }, {
            "self_address", "本人地址，不能用顧客地址取包含在內處理新登錄。"
        }, {
            "withdrawed_address", "已退會顧客的地址，不能取入。"
        }, {
            "withdrawed", "已退會顧客，不能取入。"
        }, {
            "prefecture_mismatch", "被輸入了的地址1，與都道府縣編碼沒有關聯。"
        }, {
            "duplicated_mail", "是已經被使用的郵件地址。"
        }, {
            "duplicated_loginid", "是已經被使用的登陸ID。"
        }, {
            "birthday", "出生年月日現在比起日期必須100年以內。"
        }, {
            "cancell_or_return", "因退貨或被取消，所以送貨公司票據編號不能登錄。"
        }, {
            "shipping_is_cancelled", "因為被取消，不能登錄發貨實績。"
        }, {
            "delivery_slip_no_required", "送貨公司票據編號必須填入。"
        }, {
            "import_stock_io_failed", "進發貨讀入失敗。"
        }, {
            "stock_amount_overflow", "庫存數的合計請在{0}以下。"
        }, {
            "stock_deliver_overflow", "因為這個商品引正當再被預約，出入庫數請輸入{0}以下。"
        }, {
            "stock_no_changeable", "因為沒有有效庫存，不能減少庫存數。"
        }, {
            "stock_quantity_invalid", "出入庫數請輸入以上。"
        }, {
            "delete_commodity_error", "商品編號:{0}的商品含銷售沒有確定的發貨數據、不能刪除。"
        }, {
            "category_max_depth_over", "分類階層請到{0}階層為止。"
        }, {
            "change_category_own", "父級分類不能是自己自身的目錄。"
        }, {
            "set_payment_credit", "使用信用卡結算的訂購不能更改付款日。"
        }, {
            "set_payment_no_payment", "不用支付的訂購不能更改付款日。"
        }, {
            "set_payment_point_in_full", "全額積分支付的訂購不能更改付款日。"
        }, {
            "self_phone_no_required", "本人地址登錄的場合，電話號碼必須輸入。"
        }, {
            "invalid_country_and_region", "國編號和地域編號的組合不正當。" //added 50061
        }, {
            "address1_mismatch", "被輸入了的地址1，與國編號和地域編號碼沒有關聯。" //added 50061
        }, {
            // M17N-0007 追加 ここから
            "request_item", "{0}必須輸入。"
        }, {
            "key_currency_no_update", "基準貨幣不能被更新。"
        }, {
            "unsuported_currency_no_update", "不支持的貨幣不能被更新。"
        }, {
            "exchange_rate_range", "{0}必須為大於0的數值。"
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
