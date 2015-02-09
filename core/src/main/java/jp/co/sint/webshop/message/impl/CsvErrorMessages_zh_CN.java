package jp.co.sint.webshop.message.impl;

import java.util.ListResourceBundle;

public class CsvErrorMessages_zh_CN extends ListResourceBundle {

  protected Object[][] getContents() {
    return new Object[][] {
        {
            "digit", "请输入有效的数值。"
        }, {
            "date", "请以yyyy/mm/dd形式输入。"
        }, {
            "datetime", "请以yyyy/MM/dd hh:mm:ss形式输入。"
        // 10.1.1 10006 追加 ここから
        }, {
            "not_in_range", "{0}请在{1}年到{2}年的范围内输入。"
        // 10.1.1 10006 追加 ここまで       
        }, {
            "wrong_value", "不正确的值。"
        // 10.1.1 10019 追加 ここから
        }, {
            "minus_number_error", "{0}请输入0以上的数值。"
        // 10.1.1 10019 追加 ここまで
        }, {
            "csv_not_found", "没有能输出的数据。"
        }, {
            "validate_shopcode", "店铺编号不正确。"
        }, {
            "not_exitst", "{0}不存在。"
        },{
            "request_pair","{0} 和 {1} 必须成对出现。"
        //20120309 os013 add start
        },{
          "request_greater_than","{0}必须大于等于最小单价：{1}。"
        },{
          "request_equal","{0}必须等于{1}。"
        //20120309 os013 add end
        //20120206 os013 add start
        }, {
          "commodity_code_not_exitst", "商品编号: {0}不存在。"
        },{
          "sku_code_not_exitst", "SKU编号：{0}不存在。"
        },{
          "category_attribute_not_exitst", "商品编号{0}中的カテゴリ属性番号:{1}不存在。"
        },{
          "category_attribute_no_not_exitst","カテゴリコード{0}中不存在カテゴリ属性番号{1}"
        },{
          "represent_sku_code_and_sku_code","商品编号为{0}的代表SKUコード和SKUコード必须一致。"
        //20120206 os013 add end
        //20120210 os013 add start
        }, {
          "commodity_code_exitst", "商品编号: {0}已存在。"
        }, {
          "sku_code_exitst", "SKU编号: {0}已存在。"
        //20120210 os013 add end
        }, {
            "fixed_data", "因为销售额确定完毕，不能登录发货实绩。"
        }, {
            "shipping_data_transported", "因为数据联合完毕，不能登录发货实绩。"
        }, {
            "shippingdate_before_orderdate", "因为发货日是订单日以后的日期，不能登录发货实绩。"
        }, {
            "shippingdate_before_directdate", "因为发货日是发货指示日以后的日期，不能登录发货实绩。"
        }, {
            "arrivaldate_before_shippingdate", "因为到达预定日是发货日以后的日期，不能登录发货实绩。"
        }, {
            "shipping_direct_before_orderdate", "因为发货指示日是订单日以后的日期，不能登录发货实绩。"
        }, {
            "arrival_time_both_required", "输入到达预定时间的场合，请必定输入开始时刻和结束时刻。"
        }, {
            "arrival_time_comparison_error", "到达预定开始时刻清输入到达预定时刻之前的时刻。"
        }, {
            "no_pay_and_payment_advance", "因为领受人未付款，不能登录发货实绩。"
        }, {
            "shipping_is_reserved", "因为预约商品，不能登录发货实绩。"
        }, {
            "shipping_is_returned", "因为退货信息，不能登录发货实绩。"
        }, {
            "rebuild_category_tree_failed", "分类树再构筑失败了。分类的父子构造不正确。"
        }, {
            "rebuild_category_tree_succeeded", "分类树再构筑成功了。"
        }, {
            "delete_payment_is_shipped", "因存在先付款和以发货的商品，所以无法取消付款。"
        }, {
            "payment_is_cancelled", "是取消完毕的接受订货信息。"
        }, {
            "fault_standard_name", "如果只1个设定规格名称，{0}2名称不能设定。"
        }, {
            "self_address", "本人地址，不能用顾客地址取包含在内处理新登录。"
        }, {
            "withdrawed_address", "已退会顾客的地址，不能取入。"
        }, {
            "withdrawed", "已退会顾客，不能取入。"
        }, {
            "prefecture_mismatch", "被输入了的地址1，与都道府县编码没有关联。"
        }, {
            "duplicated_mail", "是已经被使用的邮件地址。"
        }, {
            "duplicated_loginid", "是已经被使用的登录ID。"
        }, {
            "birthday", "出生年月日现在比起日期必须100年以内。"
        }, {
            "cancell_or_return", "因退货或被取消，所以送货公司票据编号不能登录。"
        }, {
            "shipping_is_cancelled", "因为被取消，不能登录发货实绩。"
        }, {
            "delivery_slip_no_required", "送货公司票据编号必须填入。"
        }, {
            "import_stock_io_failed", "进发货读入失败。"
        }, {
            "stock_amount_overflow", "库存数的合计请在{0}以下。"
        }, {
            "stock_deliver_overflow", "因为这个商品引正当再被预约，出入库数请输入{0}以下。"
        }, {
            "stock_no_changeable", "因为没有有效库存，不能减少库存数。"
        }, {
            "stock_quantity_invalid", "出入库数请输入以上。"
        }, {
            "delete_commodity_error", "商品编号:{0}的商品含销售没有确定的发货数据、不能删除。"
        }, {
            "category_max_depth_over", "分类阶层请到{0}阶层为止。"
        }, {
            "change_category_own", "父级分类不能是自己自身的目录。"
        }, {
            "set_payment_credit", "使用信用卡结算的订购不能更改付款日。"
        }, {
            "set_payment_no_payment", "不用支付的订购不能更改付款日。"
        }, {
            "set_payment_point_in_full", "全额积分支付的订购不能更改付款日。"
           //20120217 os013 add start
        },{
          "import_flag","商品编号为{0}的更新区分不能为1或0以外的值"
        },{
          "commodity_description2_length_request_greater","商品编号为{0}的商品说明2长度必须大于5。"
           //20120217 os013 add end
        }, {
            "self_phone_no_required", "本人地址登录的场合，电话号码必须输入。"
        }, {
          "invalid_country_and_region", "国编号和地域编号的组合不正当。" //added 50061
        }, {
          "address1_mismatch", "被输入了的地址1，与国编号和地域编号码没有关联。" //added 50061
        }, {
          //20120209 os013 add start
          "no_request_item", "{0}不可输入。"
        },{
          "category_code_request_item","カテゴリコード必须至少输入一个。"
        }, {
          //20120209 os013 add end
            // M17N-0007 追加 ここから
            "request_item", "{0}必须输入。"
        }, {
            "key_currency_no_update", "基准货币不能被更新。"
        }, {
            "unsuported_currency_no_update", "不支持的货币不能被更新。"
        }, {
            "exchange_rate_range", "{0}必须为大于0的数值。"
            // M17N-0007 追加 ここまで
        // M17N 10361 追加 ここから
        }, {
            "shipping_is_phantom", "发货编号:{0}是仮订单或者是仮预约，所以不能发货"
        }, {
            "set_payment_date_phantom_order_with_no", "是仮订单或者是仮预约，所以不能更改付款日。"
        // M17N 10361 追加 ここまで
        },{
          "no_number","电话号码与手机号码至少输入一项。"
        },{
          "false_phone","电话号码不正确。"
        },{
          "false_mobile","手机号码不正确。"
          // 10.1.5 10242 追加 ここから
        }, {
            "out_of_range",  "{0}请输入{1}到{2}范围内的值。"
            // 10.1.5 10242 追加 ここまで
            // 10.1.5 10243 追加 ここから
        }, {
            "invalid_phone_format",  "请输入一个有效的电话号码。"
            // 10.1.5 10243 追加 ここまで
            // 10.1.6 10258 追加 ここから
        }, {
            "overlapped_values", "{0}和{1}请输入不同的数值。"
            // 10.1.6 10258 追加 ここまで
        }, {
          "datetimeErr", "{0}请以yyyy/MM/dd hh:mm:ss形式输入。"
        }
    };
  }

}
