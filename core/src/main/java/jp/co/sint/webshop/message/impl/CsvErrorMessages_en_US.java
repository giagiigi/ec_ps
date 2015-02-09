package jp.co.sint.webshop.message.impl;

import java.util.ListResourceBundle;

public class CsvErrorMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
        {
            "digit", "Please input the valid numerical value."
        }, {
            "date", "Please input yyyy/mm/dd format."
        }, {
            "datetime", "Please input yyyy/MM/dd hh:mm:ss format."
        }, {
            "not_in_range", "{0} entered is from {1} to {2}." // 10.1.1 10006 追加 
        }, {
            "wrong_value", "invalid value."
        }, {
            "minus_number_error", "{0} entered is above 0."// 10.1.1 10019 追加 ここから
        }, {
            "csv_not_found", "There was no data output."
        }, {
            "validate_shopcode", "the shop code is invalid."
        }, {
            "not_exitst", "{0} does not exist."
        }, {
            "fixed_data", "Unable to register the shipping report because the sales has already been confirmed."
        }, {
            "shipping_data_transported", "Unable to register the shipping report because the data has already been transported."
        }, {
            "shippingdate_before_orderdate",
            "Unable to register the shipping report because the shipping date is not after the order date."
        }, {
            "shippingdate_before_directdate",
            "Unable to register the shipping report because the shipping date is not after the shipping order date."
        }, {
            "arrivaldate_before_shippingdate",
            "Unable to register the shipping report because the arrival date is not after the shipping date."
        }, {
            "shipping_direct_before_orderdate",
            "Unable to register the shipping report because the shipping order date is not after the order date."
        }, {
            "arrival_time_both_required", "Please input both start time and end time when input the estimated arrival time."
        }, {
            "arrival_time_comparison_error",
            "The estimated arrival start time has to be earlier than the estimated arrival end time."
        }, {
            "no_pay_and_payment_advance",
            "Unable to register the shipping report because the order is the advanced payment and has not been paid yet."
        }, {
            "shipping_is_reserved", "Unable to register the shipping report because of the reservation item."
        }, {
            "shipping_is_returned", "Unable to register the shipping report because of the returned information."
        }, {
            "rebuild_category_tree_failed", "Failed in rebuild category tree. Invalid category hierarchy structure."
        }, {
            "rebuild_category_tree_succeeded", "Succeeded in rebuild category tree."
        }, {
            "delete_payment_is_shipped", "Unable to cancel the payment. There is the advenced payment, the shipped item exist."
        }, {
            "payment_is_cancelled", "This is canceled order info."
        }, {
            "fault_standard_name", "Unable to set {0}2 name if the only one standard name set"
        }, {
            "self_address", "Unable to register the self address in the customer address import processing."
        }, {
            "withdrawed_address", "Unable to import the withdrawed customer address."
        }, {
            "withdrawed", "Unable to import the withdrawed customer."
        }, {
            "prefecture_mismatch", "the input address 1 does not relate to the prefectures code."
        }, {
            "duplicated_mail", "the E-mail address is already used."
        }, {
            "duplicated_loginid", "The login ID is already used."
        }, {
            "birthday", "The date of birth has to be within 100 years from the date now."
        }, {
            "cancell_or_return", "Unable to register the delivery slip no. because of being canceled or returned."
        }, {
            "shipping_is_cancelled", "Unable to register the shipping report because the shipping has been canceled."
        }, {
            "delivery_slip_no_required", "The delivery slip no. is required to input."
        }, {
            "import_stock_io_failed", "Failed in importing the stock I/O."
        }, {
            "stock_amount_overflow", "the total stock amount has to be less than {0}."
        }, {
            "stock_deliver_overflow", "This item is reserved or allocated. Please input the stock I/O less amount than {0}."
        }, {
            "stock_no_changeable", "Unable to reduce the stock amount because of no valid stock."
        }, {
            "stock_quantity_invalid", "The stock I/O amount have to be more than 1."
        }, {
            "delete_commodity_error",
            "Unable to delete the item code:{0}, because it is inclueded "
                + "in the shipping data that has not been confirmed sales yet."
        }, {
            "category_max_depth_over", "the depth of category has to be below {0}."
        }, {
            "change_category_own", "Unable to set parent category to the self."
        }, {
            "set_payment_credit", "The order by credit card settlement can not be changed the payment date."
        }, {
            "set_payment_no_payment", "The unnecessary payment order can not be changed the payment date."
        }, {
            "set_payment_point_in_full", "The order of point payment in full can not be changed the payment date."
        }, {
            "self_phone_no_required", "The phone no. is required to input when register the seld address."
        }, {
            "invalid_country_and_region", "Invalid country code/region code." // added 50061
        }, {
            "address1_mismatch", "the input address 1 does not relate to the country/region code." // added 50061
        }, {
            "request_item", "The {0} is required." // M17N-0007 追加 
        }, {
            "key_currency_no_update", "The key currency can not be updated." // M17N-0007 追加 
        }, {
            "unsuported_currency_no_update", "The unsuported currency can not be updated." // M17N-0007 追加 
        }, {
            "exchange_rate_range", "{0} must be non-negative." // M17N-0007 追加 
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
