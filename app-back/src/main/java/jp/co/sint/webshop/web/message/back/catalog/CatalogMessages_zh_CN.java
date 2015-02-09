package jp.co.sint.webshop.web.message.back.catalog;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CatalogMessages_zh_CN extends ListResourceBundle {

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
          //20111208 lirong add start
          "change_standard_count_error", "SKU含有未销售确定的数据，规格数不能变更。"
        }, {
          //20111208 lirong add end
            "arrival_goods_delete_error", "SKU编码:{0}的入货通知不能删除。"
        }, {
            "init_data_error", "没有登录所需数据。请点击重置按钮初始化数据。"
        }, {
            "set_period_error", "要做{0}设定的话必须要先做{1}的设置。"
        }, {
            "date_period_error", "{0}必须在{1}之前。"
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
            // 10.1.6 10258 追加 ここまで, {
            "stock_io_memo_required_error", "进出库理由去请必须填入。"
        }, {
            //20120105 os013 add start, {
            "stock_stockthreshold_allocatedquantity_allocatedTmall", "安全库存数量+EC引当数+Tmall引当数大于总库存数。"
        }, {
            "stock_allocatedTmall_stockTmall", "T-Mall的引当数大于库存数。"
        }, {
            "stock_the_allocation_was_not_successful", "库存在分配未成功。"
        }, {
            "not_stock_sku_selected", "没有选中的SKU编号。"
        }, {
            //20120116 os013 add start
          // 2014/06/10 库存更新对应 ob_卢 update start
          //"share_ratio_numerical_size", "EC在库割合(0-100)数值只能在100以内。"
          "share_ratio_numerical_size", "库存比例数值合计只能为100。"
          // 2014/06/10 库存更新对应 ob_卢 update end
            
        }, {
            //20120116 os013 add end
            "order_downloading", "淘宝订单已有人在下载，请稍后在试。"
          //20120113 os011 add start
        }, {
            "tmall_downloading_error", "淘宝订单下载失败。"
        }, {
            "tmall_skuup_error", "SKU编号：{0}上传淘宝更新失败。"
        }, {
            "tmall_download_time_error", "淘宝订单下载起始时间不能超过一天。"
        }
        //20120113 os011 add end
        	//20120105 os013 add end  share_ratio_numerical_size
        , {
          "tmall_brand_error", "输入的淘宝品牌不存在。"
        }, {
          "tmall_category_error", "输入的淘宝分类不存在。"
        }, {//add by os014 begin
            "sku_date_comparable_error_less","{0}必须小于{1}"
        }, {
            "sku_date_comparable_error_equlse","选择定价销售后{0}必须等于{1}"
        }, {
            "sku_date_comparable_error_more","{0}必须大于{1}"
            //add by os014 end
        // 2012/11/20 促销对应 ob add start
        }, {
            "number_out_error", "商品登录失败，套餐明细商品最多可登录15件。"
        }, {
            "commodity_type_error", "套餐明细商品只能登录普通商品。"
        }, {
          "price_number_out_error", "套餐明细商品的合计销售价格最大为99999999.99。"
	      // 2012/11/20 促销对应 ob add end
          // 20130614 txw add start
        }, {
          "tmall_scale_error", "比例设定错误。"
          // 20130614 txw add end
        // 2014/06/06 库存更新对应 ob_卢 add start
        }, {
          "jd_scale_error", "京东比例设定错误。"
        }, {
          "send_mail_error", "库存同期化失败邮件未发送成功。"
        // 2014/06/06 库存更新对应 ob_卢 add end
        }, {
            "sku_date_mustempty_error","选择定价销售后{0}必须为空"
        }
    };
    return obj;
  }

}
