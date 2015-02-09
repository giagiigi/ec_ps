package jp.co.sint.webshop.web.message.back.communication;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommunicationMessages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // ページ遷移エラー
        {
            "move_action_error", "不正な{0}が指定されました。"
        },
        // コンテンツ未登録エラー
        {
            "contents_exist_error", "キャンペーンのコンテンツは登録されていません。"
        },
        // 実施中アンケート削除不可エラー
        {
            "delete_enquete_error", "実施中のアンケートは削除できません。"
        },
        // 回答済みアンケート更新不可エラー
        {
            "answered_enquete_error", "回答が存在するアンケートの{0}を{1}することはできません。"
        },
        // キャンペーン削除エラー
        {
            "delete_campaign_error", "受注情報に関連しているキャンペーンは削除できません。({0})"
        },
        // soukai add ob 2011/12/15 start
       
        {
        	/** 利用期间小于系统时间错误 */
            "use_data_system_data_error", "利用期间不能早于当前系统时间。"
        },
        
        {
        	/** 优惠券规则已经被使用，无法删除错误 */
            "coupon_delete_error", "{0}已经被使用，无法删除。"
        },
        {
        	/** 企划已经被使用，无法删除错误 */
            "plan_delete_error", "{0}已经被使用，无法删除。"
        },
        {
          /** 企划已经被使用，无法删除错误 */
            "new_campaign_delete_error", "促销(活动编号：{0})已经被使用，无法删除。"
        },
        {
          /** 促销不存在 */
            "new_campaign_not_exist_error", "促销(活动编号：{0})不存在、已经删除。"
        },
        // 顾客组别不存在错误 
        {
            "customer_group_campaign_no_data_error", "选择的顾客组别已被删除。"
        },
        // 优惠比率超出1~100 错误
        {
            "campaign_number_range_over_error", "优惠比例请在0…100的范围内输入。"
        },
     // 比率不可以为空
        {
            "ratio_null_error", "比例不可以为空。"
        },
        // 金额不可以为空
        {
            "money_null_error", "优惠金额不可以为空。"
        },
        // 比率格式错误
        {
            "ratio_error", "比例输入不正确,请重新输入。"
        },
        // 金额格式错误
        {
            "money_error", "优惠金额输入不正确,请重新输入。"
        },
        // 关联商品不在商品分类下
        {
            "plan_category_commoidty", "关联商品(商品编号：{0})不属于所选商品分类。"
        },
        // 关联商品不在品牌分类下
        {
            "plan_brand_commoidty", "关联商品(商品编号：{0})不属于所选品牌。"
        },
        // soukai add ob 2011/12/15 end
        // 実施中キャンペーン削除エラー
        {
            "delete_effect_campaign_error", "実施中のキャンペーンは削除できません。"
        },
        // 20130730 txw add start
        {
          /** 折扣已经被使用，无法删除错误 */
            "discount_delete_error", "限时限量(折扣编号：{0})已经实施，无法删除。"
        },
        {
          /**重复登录*/
          "duplicated_discount_error", "限时限量(折扣编号：{0})已经登录。"
        },
        {
          /**重复登录*/
          "duplicated_discount_commodity_error", "限时限量(折扣编号：{0},商品编号：{1})已经登录。"
        },
        {
          /** 折扣不存在 */
            "discount_not_exist_error", "限时限量(折扣编号：{0})不存在、已经删除。"
        },
        {
          /** 折扣商品不存在 */
            "discount_commdoity_not_exist_error", "限时限量(折扣编号：{0},商品编号：{1})不存在、已经删除。"
        }
        // 20130730 txw add end
    };
    return obj;
  }

}
