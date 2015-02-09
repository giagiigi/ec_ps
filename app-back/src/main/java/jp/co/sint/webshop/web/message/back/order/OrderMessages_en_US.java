package jp.co.sint.webshop.web.message.back.order;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderMessages_en_US extends ListResourceBundle {

  private static final Object[][] ORDER_MESSAGES_MAIN1 = {
      {
          "point_in_full", "Payment by points in full"
      },
      {
          "return_orver", "The return quantity has exceeded The purchase quantity."
      },
      {
          "still_paid", "This is paid order info."
      },
      {
          "paid_order_with_no", "The order no:{0} has already been paid."
      },
      {
          "not_paid_order_with_no", "The order no:{0} has not been paid yet."
      },
      {
          "still_shipping_order", "This is [in process] or [shipped] order."
      },
      {
          "delivery_appointed_date", "The delivery appointed date is not valid date."
      },
      {
          "past_delivery_appointed_date", "The delivery appointed date is past or the same date."
      },
      {
          "delivery_appointed_time_both", "The delivery appointed time have to be input both start date and end date."
      },
      {
          "add_commodity_shipping_not_found",
          "When adding item which has any differences with Delivary address/Delivary shop/Delivary type/Delivary status, "
              + "Please register it as a new order."
      },
      {
          "change_same_delivery", "Unable to change into the same delivery address."
      },
      {
          "no_shipping", "There is no delivary address anfo."
      },
      {
          "over_usable_point", "It has been exceeded usable points."
      },
      {
          "order_data_transported_with_no", "Unable to update order no.:{0}, Since the data has been transported."
      },
      {
          "shipping_data_transported_with_no", "Unable to update shipping no.:{0}, Since the data has been transported."
      },
      {
          "delete_shipping_data", "Unable to delete the shipping info."
      },
      {
          "delete_all_commodity", "Unable to delete all the item."
      },
      {
          "delete_all_delivery", "Unable to delete all the delivary address."
      },
      {
          "fixed_data", "The shipping info is sales-fixed data"
      },
      {
          "fixed_data_with_no", "Shipping no.:{0} is sales-fixed data."
      },
      {
          "change_prefecture_shipping", "The prefecture of Shipping no.:{0} has been changed."
      },
      {
          "shipping_data_sales_status_not_fixed", "Shipping no.:{0} is not fixed."
      },
      {
          "clear_payment_date_failed_with_no", "Failed in clear the payment date of order no.:{0}."
      },
      {
          "fixed_shipping_status_with_no", "The shipping status of shipping no.:{0} can not change."
      },
      {
          "clear_shipping_failed_with_no", "Unable to cancel the shipping record of shipping no.:{0}."
      },
      {
          "shipping_direct_failed_with_no", "Failed in shipping order of shipping no.:{0}."
      },
      {
          "shipped_result_failed_with_no", "Unable to register the shipping record of shipping no.:{0}."
      },
      {
          "no_status_suitable_for_shipping", "shipping no.:{0} is not ready for shipping. Shipping order is not available."
      },
      {
          "no_status_shipping_arrange", "Shipping no.:{0} is not [in process]. unable to cancel the shipping arrangement."
      },
      {
          "no_status_shipped", "Shipping no.:{0} is not [shipped]. unable to cancel the shipping recoed."
      },
      {
          "no_pay_and_payment_advance_with_no",
          "Shipping no.:{0} is [not paid] and [advance payment]. Shipping order is not available."
      }, {
          "shop_not_found", "There is no such shop exist."
      }
  };

  private static final Object[][] ORDER_MESSAGES_MAIN2 = new Object[][] {
      {
          "impossibility_shipping_direct_cause_date",
          "Shipping no.:{0} is not set the shipping appointed date. Shipping arrangement is not available."
      },
      {
          "impossibility_shipping_direct", "Shipping no.:{0} is [in process] or [shipped]. Shipping arrangement is not available."
      },
      {
          "impossibility_shipping_direct_date",
          "Shipping no.:{0} is [in process] or [shipped]. Unable to update the shipping appointed date."
      },
      {
          "impossibility_shipping_direct_date_cause_list",
          "The shipping appointed date of shipping no.:{0} has already been set. Unable to set from the list."
      },
      {
          "impossibility_shipping_report", "Shipping no.:{0} is not [In process]. Unable to register the shipping."
      },
      {
          "impossibility_appointed_date_cause_direct_past",
          "The invalid delivery date has been set in the delivery appointed date."
      },
      {
          "impossibility_clear_shipping_direct_date",
          "Shipping no.:{0} is [Shipped]. Unable to cancel the shipping appointed date."
      },
      {
          "impossibility_clear_shipping_direct_date_inprocess",
          "Shipping no.:{0} is [In process]. Unable to cancel the shipping appointed date."
      },
      {
          "impossibility_clear_shipping_direct_date_not_set",
          "The shipping appointed date of shipping no.:{0} has not been set. Unable to cancel the shipping appointed date."
      },
      {
          "will_shipping_report_date", "The shipping date have to be input the earlier date than today."
      },
      {
          "direct_without_date", "The shipping appointed date of shipping no.:{0} is set. Executed only the shipping arrangement."
      },
      {
          "shipping_direct_input_orderdate_or_later_with_no",
          "The shipping date of shipping no.:{0} have to be later date than the shipping order date."
      },
      {
          "shipping_input_orderdate_or_later_with_no", "{1} of Shipping no.:{0} have to be later date than the order date."
      },
      {
          "no_customer_to_send_shipping_mail_with_no", "Shipping no.{0}: There is no customer."
      },
      {
          "shipped_and_payment_advance_with_no",
          "Order no.:{0} is [Shipped] and [Advance payment]. Unable to cancel the payment date."
      },
      {
          "delivery_appoint_orderdate_or_later_with_no",
          "The delivery appointed date of Shipping no.:{0} have to be later date than the order date."
      },
      {
          "payment_orderdate_or_later_with_no", "The payment date of order no.:{0} have to be later date than the order date."
      },
      {
          "not_set_clear_payment_date", "Unable to cancel the payment date of order no.:{0}."
      },
      {
          "already_set_clear_payment_date", "Order no.:{0} has already been paid."
      },
      {
          "order_changed_error", "The order info has been modified."
      },
      {
          "credit_cancel_error", "Failed in processing the card company. Please cancel it again."
      },
      {
          "stock_shortage", "SKU code:{0} can be purchased up to {1} in total."
      },
      {
          "no_available_stock_sku", "SKU code:{0} is out of stock."
      // 10.1.6 10277 追加 ここから
      },
      {
          "stock_allocate_default_error", "{0}没有库存。"
      // 10.1.6 10277 追加 ここまで
      // 10.1.2 10110 追加 ここから
      },
      {
          "stock_concurrency_control_error",
          "It is very crowded just now. Please order again at a certain intervals when it is a few. "
      // 10.1.2 10110 追加 ここまで
      },
      {
          "out_of_period", "The sales condition of SKU code:{0} has been changed. Please add the item to cart again."
      },
      {
          "set_payment_date_canceled_order", "Unable to set the payment date of the canceled order."
      },
      {
          "set_payment_date_canceled_order_with_no", "Order no.:{0}. Unable to {1} The canceled item."
      },
      {
          "set_payment_credit", "Order no.:{0}. Unable to {1} the order paid by credit card. "
      },
      {
          "update_returned_shipping", "This shipping info is [returned]. Unable to update."
      },
      {
          "cancelled_shipping_with_no", "Shipping no.:{0} is canceled shipping. Unable to {1}."
      },
      {
          "return_item_loss_money_length_over", "The length of the total return goods loss have to be within {0} figures."
      },
      {
          "register_payment_data_transported", "Order no.:{0} is being data transported. Unable to set the payment date."
      },
      {
          "clear_payment_data_transported", "Order no.:{0} is being data transported. Unable to delete the payment date."
      },
      {
          "settelment_incomplete_invalid_parameter", "{0} has not been certified."
      },
      {
          "withdrawal_customer", "The customer of order no.:{0} has already withdrawed."
      },
      {
          "cash_on_delivery_select_error",
          "You can not purchase the items of delivery to the someone who did not purchase,"
              + " and of multi deliver methods at one order."
      },
      {
          "cash_on_delivery_select_and_no_point_error",
          "You can not purchase the items of delivery to the someone who did not purchase,"
              + " and of multi deliver methods at one order, If You can not pay the total price with points."
      },
      {
          "input_negative_value_error", "{0} have to be larger value than 0."
      },
      {
          "wrong_additional_delivery", "Please select the addtional delivery address."
      },
      {
          "total_amount_over",
          "Unable to purchase this, since the total payment price {0} has exceeded payment limit {1}."
              + " Please change the payment method or order contents."
      }
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
          "send_shipping_mail_failed_with_no", "Failed in sending E-mail of shipping no.:{0}."
      },
      {
          "send_order_mail_failed_with_no", "Failed in sending E-mail of order no.:{0}."
      },
      {
          "set_payment_canceled_order_with_no", "Order no.:{0} is a canceled order. Unable to send {1} mail."
      },
      {
          "send_mail_data_transported", "Order no.:{0} is being data transported. Unable to send {1} mail."
      },
      {
          "send_shipping_mail_data_transported_with_no",
          "Shipping no.:{0} is being data transported. Unable to send the shipping report mail."
      }, {
          "send_reminder_mail_to_not_pay", "Order no.:{0} is no need to be paid. Unable to send the reminder mail."
      }
  };

  private static final Object[][] ORDER_RESERVATION_MESSAGE = new Object[][] {
      {
          "shipping_is_reserved", "Unable to ship the reserved item."
      }, {
          "oneshot_reservation_over", "SKU code:{0} has exceeded the reservation limit on each order."
      }, {
          "add_reserve_plural_shipping_error", "The reserved item can not be send to several addresses."
      }, {
          "not_available", "SKU code:{0} can not be reserved."
      }, {
          "reservation_over", "SKU code:{0} can be reserved up to {1} in total."
      }, {
          "not_reservation_change", "Unable to change the order contents, since the reservation period is over."
      }, {
          "point_small_not_error", "使用积分必须设为{0}的倍数。"
      }
  };

  private static final Object[][] ORDER_MODIFY_MESSAGE = new Object[][] {
      {
          "data_transported", "Unable to update since it is being data transported."
      },
      {
          "order_modify_add_reserve", "Unable to add the reserved item."
      },
      {
          "change_master_payment",
          "The commission of the payment method have been updated. Please select the payment method again."
      },
      {
          "update_payment_use_master", "The commission of payment method have been updated to the latest fee."
      },
      {
          "change_master_shipping",
          "The delivery charge of delivary type have been updated.Please select the delivary address again."
      },
      {
          "change_master_commodity", "The sales price of the item have been updated.Please select the item again."
      },
      {
          "change_master_gift", "The gift information have been updated. Please select the gift again."
      },
      {
          "cannot_change_gift_amount", "Gift:{0} has been changed to unusable status. You can not increase the amount."
      },
      {
          "no_payment_method_error", "The payment method may be deleted or unusable."
      },
      {
          "add_reserve_payment_not_found_shop",
          "Please clear the cart, when adding the item from different shops, or the reservation item."
      },
      {
          "add_reserve_payment_not_found", "Please clear the cart, when adding the reservation item."
      },
      {
          "add_sales_payment_not_found_shop",
          "Please clear the cart, when adding the item from different shops, or the sales item."
      }, {
          "add_sales_payment_not_found", "Please clear the cart, when adding the sales item."
      }, {
          "modify_credit_error", "The order paid with credit card can not be modified."
      }, {
          "not_exist", "Unable to purchase SKU code:{0}, since it may not exist or not within sales period."
      }, {
          "not_exist_addition", "SUnable to add the amount of SKU code:{0}, since it may not exist or not within sales period."
      // M17N-0003 追加 ここから
      // 注文毎注文上限数オーバーエラー
      }, {
          "oneshot_order_over", "{0}It peels off and , wagon can be orderedto {1} piece together with the amount."
      // 輸出禁止国オーバーエラー
      }, {
          "embargoed_commodity", "{0} , wagon can be orderedto {1} piece together with the amount."
      // M17N-0003 追加 ここまで
      }, {
          "add_commodity_amount_over", "Unable to add the item. The amount after adding item has exceeded {0} figures."
      }, {
          "unusable_gift", "Unable to use Gift:{0}, Since it has been updated to unusable status."
      }, {
          "max_useable_coupon_count", "一个订单最多只能用{0}张优惠券。"
      }, {
          "select_useable_coupon", "请选择优惠券。"
      }
  };
}
