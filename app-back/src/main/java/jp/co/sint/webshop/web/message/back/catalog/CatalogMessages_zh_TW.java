package jp.co.sint.webshop.web.message.back.catalog;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CatalogMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "code_failed", "{0}编号不正确。"
        }, {
            "root_category_delete_error", "分类根不能删除。"
        }, {
            "root_category_parent_change_error", "分类根的父级分类不能变更。"
        }, {
            "change_category_own_child_error", "不能移动到自身的子级分类里。"
        }, {
            "change_category_own_error", "不能改变自身的父级分类。"
        }, {
            "delete_commodity_error", "商品编码:{0}的商品因是含有没有确定销售额的出货数据，所以不能删除。"
        }, {
            "delete_sku_error", "SKU编码:{0}是含有没有确定销售额的出货数据，所以不能删除。"
        }, {
            "arrival_goods_delete_error", "SKU编码:{0}的入货通知不能删除。"
        }, {
            "init_data_error", "没有登录所需数据。请点击重置按钮初始化数据。"
        }, {
            "set_period_error", "要做{0}设定的话必须要先做{1}的设置。"
        }, {
            "date_period_error", "必须要{0}比{1}之前设置。"
        }, {
            "price_revision_date_error", "请设定价格改定日时设定在贩卖期间内。"
        }, {
            "stock_status_set_error", "库存管理区分「库存管理(状况显示)」被设定的情况下，请选择库存状况显示。"
        }, {
            "fault_standard_detail_name_set_error", "详细规格名称不正确。"
        }, {
            "fault_standard_name_set_error", "这个商品的规格的规格名称是被设置成「{0}」。请在商品SKU画面里设定详细规格名称。"
        }, {
            "standard_name2_set_error", "规格名称只设定１个的时候，不能{0}设定２个名称。"
        }, {
            "standard_names_set_error", "规格名称不使用时，{0}1名称，{0}２名称不能设定。"
        }, {
            "register_sku_error", "SKU商品新规登录时，请登录规格名称。"
        }, {
            "stock_amount_overflow_error", "{0}的合计是从{1}到{2}的范围内。"
        }, {
            "stock_absolute_overflow_error", "这个商品是被抽中或是被预约了，请输入进出货数量是{0}以上的数值。"
        }, {
            "stock_deliver_overflow_error", "这个商品是被抽中或是被预约了，　请输入进出货数量是{0}以下的数值。"
        }, {
            "stock_no_changeable_error", "没有有效的在库，所以在库数量没有减少。"
        }, {
            "no_available_delivery_type_error", "没有登录有效的利用可能的发送种别。请登录发送种别。"
        }, {
            "stock_quantity_error", "进出库数请出入输入１以上的数值。"
        }, {
            "commodity_layout_top_parts_error", "请把{0}配置到显示区域的最上面。"
        }, {
            "not_represent_sku_error", "代表SKU编码请出入以登录的SKU编码。"
        }, {
            "update_category_path_error", "分类登陆・更新处理失败了。分类树里有不整合。"
        }, {
            "category_max_depth_over_error", "分类阶层请通过{0}到阶层。"
        }, {
            "standard_name_duplicated_error", "规格名称有重复。"
        }, {
            // 10.1.6 10258 追加 ここから
            "overlapped_values", "{0}和{1}请输入不同的值。"
        }, {
            "fault_standard_count_error", "规则数不正确。"
        }, {
            // 10.1.6 10258 追加 ここまで, {, {
            "stock_io_memo_required_error", "进出库理由去请必须填入。"
        }
    };
    return obj;
  }

}
