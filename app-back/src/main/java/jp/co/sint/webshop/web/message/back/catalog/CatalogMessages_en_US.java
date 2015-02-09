package jp.co.sint.webshop.web.message.back.catalog;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CatalogMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "code_failed", "Invalid code {0}."
        },
        {
            "root_category_delete_error", "You can not delete the root category."
        },
        {
            "root_category_parent_change_error", "You can not change the parent of root category."
        },
        {
            "change_category_own_child_error", "Unable to move itself to child category."
        },
        {
            "change_category_own_error", "Unable to change the parent category into itself."
        },
        {
            "delete_commodity_error",
            "Unable to delete the item code:{0}. it is included in the shipping data that have not been fixed yet."
        },
        {
            "delete_sku_error",
            "Unable to delete the SKU code:{0}, since it is included in the shipping data that have not been fixed yet."
        },
        {
            "arrival_goods_delete_error", "Could not delete the arrival goods info of SKU code:{0}."
        },
        {
            "init_data_error", "The necessary data have not been registered yet. Initialize the data by clicking the reset button."
        },
        {
            "set_period_error", "Both {0} and {1} setting are necessary."
        },
        {
            "date_period_error", "{0} have to be earlier date than {1}."
        },
        {
            "price_revision_date_error", "The revised price date have to be within the sales period."
        },
        {
            "stock_status_set_error", "Please select DisplayStockStatus if stock control type is [manage stock(display status)]."
        },
        {
            "fault_standard_detail_name_set_error", "Invalid standard detail name."
        },
        {
            "fault_standard_name_set_error",
            "The standard name [{0}] is set in this item standard. Please set the standard detail name on the item SKU page."
        }, {
            "standard_name2_set_error", "Unable to set {0}2 name, if you have set the only one standard name."
        }, {
            "standard_names_set_error", "Unable to set {0}1 name and {0}2 name, if you do not use the standard name."
        }, {
            "register_sku_error", "Please register standard name if you newly register SKU item."
        }, {
            "stock_amount_overflow_error", "The sum of {0} have to be within the range from {1} to {2}."
        }, {
            "stock_absolute_overflow_error", "This item has been allocated or reserved. Please input the stock I/O more than {0}."
        }, {
            "stock_deliver_overflow_error", "This item has been allocated or reserved. Please input the stock I/O less than {0}."
        }, {
            "stock_no_changeable_error", "Unable to lessen the stock quantity, since there is no valid stock."
        }, {
            "no_available_delivery_type_error", "There is no usable delivery type registered. Please register the delivery type."
        }, {
            "stock_quantity_error", "Please input stock I/O quantity more than 1."
        }, {
            "commodity_layout_top_parts_error", "Please set {0} on the top of the display area."
        }, {
            "not_represent_sku_error", "Please input registered SKU code for the representative SKU code."
        }, {
            "update_category_path_error", "Failed in category registration. There si the inconsistency of category tree."
        }, {
            "category_max_depth_over_error", "The depth of category tree have to be below {0}."
        }, {
            "standard_name_duplicated_error", "The standard name is duplicated."
        }, {
            // 10.1.6 10258 追加 ここから
            "overlapped_values", "{0}和{1}请输入不同的值。"
        }, {
            "fault_standard_count_error", "规则数不正确。"
        }, {
            // 10.1.6 10258 追加 ここまで, {
            "stock_io_memo_required_error", "you have to input the reason of stock I/O."
        }
    };
    return obj;
  }

}
