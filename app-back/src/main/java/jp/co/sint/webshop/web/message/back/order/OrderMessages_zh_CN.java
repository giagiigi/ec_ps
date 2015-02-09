package jp.co.sint.webshop.web.message.back.order;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderMessages_zh_CN extends ListResourceBundle {

  private static final Object[][] ORDER_MESSAGES_MAIN1 = {
      {
          "point_in_full", "使用全额积分或优惠券支付。"
      }, {
          "return_orver", "退货数量超过了购入商品的数量。"
      }, {
          "still_paid", "已付款的订单信息。"
      }, {
          "paid_order_with_no", "订单编号:{0}已付款。"
      }, {
          "not_paid_order_with_no", "订单编号:{0}未付款。"
      }, {
          "still_shipping_order", "发货安排中或是已发货的订单信息。"
      }, {
          "delivery_appointed_date", "发送指定日不是有效日期。"
      }, {
          "past_delivery_appointed_date", "发送指定日的过去或是当日的日期。"
      }, {
          "delivery_appointed_time_both", "发送指定时间是开始时间或结束时间的其中一个时间没有被填入。"
      }, {
          "add_commodity_shipping_not_found", "配送地・发送店铺・发送种别・订单状况的不同商品的追加请登陆新规订单。"
      }, {
          "change_same_delivery", "不能变更成同一个送货地址。"
      }, {
          "no_shipping", "配送地信息不存在。"
      }, {
          "over_usable_point", "超过了可以使用的积分。"
      }, {
          "order_data_transported_with_no", "订单编号:{0}因连接了数据，所以不能更新。"
      }, {
          "shipping_data_transported_with_no", "订单编号:{0}因连接了数据，所以不能更新。"
      }, {
          "delete_shipping_data", "该当的发货信息不能删除。"
      }, {
          "delete_all_commodity", "商品里最少需要１个商品。"
      }, {
          "delete_all_delivery", "配送地里最少需要１个商品。"
      }, {
          "fixed_data", "该当的发货信息是已确认的销售额。"
      }, {
          "fixed_data_with_no", "发货编号:{0}是已确认的销售额。"
      }, {
          "change_prefecture_shipping", "发货编号:{0}的省市县被变更了。"
      }, {
          "shipping_data_sales_status_not_fixed", "发货编号:{0}发货没有确认到。"
      }, {
          "clear_payment_date_failed_with_no", "订单编号:{0}的付款日清空失败了。"
      }, {
          "fixed_shipping_status_with_no", "发货编号:{0}的发货等级不能变更。"
      }, {
          "clear_shipping_failed_with_no", "出荷番号:{0}的发货实际不能取消。"
      }, {
          "shipping_direct_failed_with_no", "发货编号:{0}的发货指示失败了。"
      }, {
          "shipped_result_failed_with_no", "发货编号:{0}的发货实际不能登陆。"
      }, {
          "no_status_suitable_for_shipping", "发货编号:{0}因发货不可能，所以无法做指示。"
      }, {
          "no_status_shipping_arrange", "发货编号:{0}因发货安排中，所以发货安排无法取消。"
      }, {
          "no_status_shipped", "发货编号:{0}因还未发货，所以发货实际无法取消。"
      }, {
          "no_pay_and_payment_advance_with_no", "发货编号:{0}因未付款或因先付款，所以无法作指示。"
      }, {
          "shop_not_found", "该当的店铺不存在。"
      }
  };

  private static final Object[][] ORDER_MESSAGES_MAIN2 = new Object[][] {
      {
          "impossibility_shipping_direct_cause_date", "发货编号:{0}因发货指示日没有被设定，所以不能安排。"
      }, {
          "impossibility_shipping_direct", "发货编号:{0}因发货安排中或是以发货，所以不能做发货安排。"
      }, {
          "impossibility_shipping_direct_date", "发货编号:{0}因发货安排中或是以发货，无法更新发货指示日。"
      }, {
          "impossibility_shipping_direct_date_cause_list", "发货编号:{0}因已经设定了发货指示日，所以从一览不能设定。"
      }, {
          "impossibility_shipping_report", "发货编号:{0}因没有在发货安排中，无法发货登录。"
      }, {
          "impossibility_appointed_date_cause_direct_past", "发送指定日被设定为无法发送日期。"
      }, {
          "impossibility_clear_shipping_direct_date", "发货编号:{0}因以发货，所以不能取消发货指定日。"
      }, {
          "impossibility_clear_shipping_direct_date_inprocess", "发货编号:{0}因发货安排中，所以无法取消发货指定日。"
      }, {
          "impossibility_clear_shipping_direct_date_not_set", "发货编号:{0}的发货指定日还未设定，发货指定日不能取消。"
      }, {
          "will_shipping_report_date", "发货日是请填入现在日期以前的日期。"
      }, {
          "direct_without_date", "发货编号:{0}是因没有指定发货指示日，所以只执行了发货安排。"
      }, {
          "shipping_direct_input_orderdate_or_later_with_no", "发货编号:{0}的发货日请填入发货指定日以后的日期。"
      }, {
          "shipping_input_orderdate_or_later_with_no", "发货编号:{0}的{1}请填入订单日以后的日期。"
      }, {
          "no_customer_to_send_shipping_mail_with_no", "发货编号:{0}的顾客不存在。"
      }, {
          "shipped_and_payment_advance_with_no", "订单编号:{0}是因以发货或先付费，所以付款无法取消。"
      }, {
          "delivery_appoint_orderdate_or_later_with_no", "发货编号:{0}的发送指定日时请填入订单日时以后的日期。"
      }, {
          "payment_orderdate_or_later_with_no", "订单编号:{0}的付款日请填入订单日后的日期。"
      }, {
          "not_set_clear_payment_date", "订单编号:{0}入宽日不能取消。"
      }, {
          "already_set_clear_payment_date", "订单编号:{0}以付款。"
      }, {
          "order_changed_error", "订单信息被变更。"
      }, {
          "credit_cancel_error", "信用卡公司的处理失败了。请再次按下取消。"
      }, {
          "stock_shortage", "SKU编码:{0}是合计到{1}个可以购入。"
      }, {
          "no_available_stock_sku", "SKU编码:{0}断货。"
      // 10.1.6 10277 追加 ここから
      }, {
          "stock_allocate_default_error", "{0}没有库存。"
      // 10.1.6 10277 追加 ここまで
      // 10.1.2 10110 追加 ここから
      }, {
          "stock_concurrency_control_error", "现在系统繁忙，请稍后再一次订购。"
      // 10.1.2 10110 追加 ここまで
      }, {
          "out_of_period", "SKU编码:{0}是因贩卖条件变更，所以请再次将商品放入购物车。"
      }, {
          "set_payment_date_canceled_order", "取消订单后不能设定付款日。"
      }, {
          "set_payment_date_canceled_order_with_no", "订单编号:{0}取消订单理由不能是{1}。"
      }, {
          "set_payment_credit", "订单编号:{0}支付方法是信用卡结算的订单时不能是{1}。"
      }, {
          "update_returned_shipping", "这个发货信息是因以退货，所以无法更新。"
      }, {
          "cancelled_shipping_with_no", "发货编号:{0}是因取消发货，所以不能{1}。"
      }, {
          "return_item_loss_money_length_over", "退货损失金额的合计请填入{0}位以内。"
      }, {
          "register_payment_data_transported", "订单编号:{0}是因数据连带中，所以不能设定付款日。"
      }, {
          "clear_payment_data_transported", "订单编号:{0}是因数据连带中，所以不能删除付款日。"
      }, {
          "settelment_incomplete_invalid_parameter", "{0}不能被认证。"
      }, {
          "withdrawal_customer", "订单编号:{0}是以退会的顾客。"
      }, {
          "cash_on_delivery_select_error", "这个店铺不能同时购入，购入者本人以外的送货地址和相不同商品同时发送不同商品。"
      }, {
          "cash_on_delivery_select_and_no_point_error", "不能支付支付金额的全部积分时不能同时购入，购入者本人以外的送货地址和相不同商品同时发送不同商品。"
      }, {
          "input_negative_value_error", "请填入{0}是0以上的值。"
      }, {
          "wrong_additional_delivery", "请选择追加送货地址。"
      }, {
          "total_amount_over", "支付合计金额是{0}的支付上限金额，因超过{1}所以不能购入。 请变更支付方法或订单内容。"
      // M17N 10361 追加 ここから
      }, {
          "shipping_is_phantom", "发货编号:{0}是仮订单或者是仮预约，所以不能发货"
      }, {
          "set_payment_date_phantom_order_with_no", "订单编号:{0}是仮订单或者是仮预约，所以不能{1}。"
      // M17N 10361 追加 ここまで
      }
      // soukai add 2012/02/04 ob start
   // 20110725 shiseido add start
      ,{
      "order_cancel_disabled", "已支付完成的订单不可取消。"
  }
      // soukai add 2012/02/04 ob end
      //2012/11/22 促销对应 新建订单_商品选择  ob add start
      ,{
      	"multiple_gift_buy_error", "您要参加的活动已过期或者您的订单已不再满足活动条件。"
      },
      {
      	"same_campaign_multiple_gift_buy_error", "单次购买同一活动的同一商品只可领取一次。"
      },
      {
        "total_stock_shortage_error", "库存数量不足，{0}的当前库存数只允许购买{1}个。"
      },
      {
      	"set_commodity_composition_not_exist_error", "套餐商品{0}不存在或者已被删除。"
      },
      {
        "discount_code_null_error", "折扣券编号不能为空。"
      },
      {
        "discount_not_exist","折扣券不存在或已过期。"
      },
      {
        "discount_use_only", "优惠券和折扣券不可同时使用。"
      },
      {
        "discount_max_commodity_error", "购买{0}件及以上才能使用{1}折扣券。"
      },
      {
        "discount_max_used_error", "折扣券({0})超过了最大使用次数。"
      },
      {
        "discount_cannt_used_error", "选择的优惠券不可使用。"
      }
      //2012/11/22 促销对应 新建订单_商品选择  ob add end
  };

  @Override
  protected Object[][] getContents() {

    List<Object[]> list = new ArrayList<Object[]>();
    for (Object[] items : ORDER_MESSAGES_MAIN1) {
      list.add(items);
    }
    for (Object[] items : ORDER_MESSAGES_MAIN2) {
      list.add(items);
    }
    for (Object[] items : ORDER_MODIFY_MESSAGE) {
      list.add(items);
    }
    for (Object[] items : ORDER_MAIL_MESSAGE) {
      list.add(items);
    }
    for (Object[] items : ORDER_RESERVATION_MESSAGE) {
      list.add(items);
    }
    Object[][] obj = new Object[list.size()][2];
    for (int i = 0; i < list.size(); i++) {
      Object[] tmp = list.get(i);
      obj[i] = tmp;
    }
    return obj;
  }

  private static final Object[][] ORDER_MAIL_MESSAGE = new Object[][] {
      {
          "send_shipping_mail_failed_with_no", "发货编号:{0}的邮件送信失败了。"
      }, {
          "send_order_mail_failed_with_no", "订单编号:{0}的邮件送信失败了。"
      }, {
          "set_payment_canceled_order_with_no", "订单编号:{0}是因取消订单，所以{1}的邮件不能送出。"
      }, {
          "send_mail_data_transported", "订单编号:{0}是因数据连带中，所以{1}的邮件不能送出。"
      }, {
          "send_shipping_mail_data_transported_with_no", "发货编号:{0}是因数据连带中，所以发货邮件不能送出。"
      }, {
          "send_reminder_mail_to_not_pay", "订单编号:{0}是因不用支付，所以付款督促邮件不能送出。"
      }
  };

  private static final Object[][] ORDER_RESERVATION_MESSAGE = new Object[][] {
      {
          "shipping_is_reserved", "预约商品不能发货。"
      }, {
          "oneshot_reservation_over", "SKU编码:{0}不能超过各订单的预约上限数。"
      }, {
          "add_reserve_plural_shipping_error", "预约商品不能指定复数送货地址。"
      }, {
          "not_available", "SKU编码:{0}不能预约。"
      }, {
          "reservation_over", "SKU编码:{0}合计可以到{1}个预约。"
      }, {
          "not_reservation_change", "因预约期间结束，所以订单内容不能变更。"
      }, {
          "point_small_not_error", "使用积分必须设为{0}的倍数。"
      }
  };

  private static final Object[][] ORDER_MODIFY_MESSAGE = new Object[][] {
      {
          "data_transported", "数据以连带，无法更新。"
      }, {
          "order_modify_add_reserve", "预约商品不能追加。"
      }, {
          "change_master_payment", "支付方法的手续费被更新了。请再次选择支付方法。"
      }, {
          "update_payment_use_master", "支付方法的手续费是被更新到最新的手续费了。"
      }, {
          "change_master_shipping", "发送种别的运送费被更新了。请再次选择送货地址。"
      }, {
          "change_master_commodity", "商品的贩卖价格被更新了。请再度选择商品。"
      }, {
          "change_master_gift", "礼品信息被更新了。请再度选择礼品。"
      }, {
          "cannot_change_gift_amount", "礼品:{0}是被变更到不能使用状态，所以不能增加数量。"
      }, {
          "no_payment_method_error", "指定后的支付方法是被删除了或是无法利用状态。"
      }, {
          "add_reserve_payment_not_found_shop", "不同店铺的商品或是追加预约商品时，请清空一次购物车。"
      }, {
          "add_reserve_payment_not_found", "追加预约商品时，请清空一次购物车。"
      }, {
          "add_sales_payment_not_found_shop", "不同店铺的商品或是追加预约商品时，请清空一次购物车。"
      }, {
          "add_sales_payment_not_found", "追加预约商品时，请清空一次购物车。"
      }, {
          "modify_credit_error", "支付方法是信用卡支付的订单不能修正。"
      // M17N 10361 追加 ここから
      }, {
          "modify_external_payment_error", "支付方法是{0}的订单不能修正。"
      // M17N 10361 追加 ここまで
      }, {
          "not_exist", "SKU编码:{0}是因不存在或贩卖期间外，所以不能购入。"
      }, {
          "not_exist_addition", "SKU编码:{0}是因不存在或出售期间外，数量不能追加。"
      // M17N-0003 追加 ここから
      // 注文毎注文上限数オーバーエラー
      }, {
          "oneshot_order_over", "购物车的数量，{0}最多只能购买{1}个。"
      // 輸出禁止国オーバーエラー
      }, {
          "embargoed_commodity", "{0}不能配送到{1}这个国家。"
      // M17N-0003 追加 ここまで
      }, {
          "add_commodity_amount_over", "商品追加后的数量是{0}位时不能追加。"
      }, {
          "unusable_gift", "礼品:{0}是因更新到不能使用状态，所以不能使用。"
      }, {
          "max_useable_coupon_count", "一个订单最多只能用{0}张优惠券。"
      }, {
          "select_useable_coupon", "请选择优惠券。"
      }
  };
}
