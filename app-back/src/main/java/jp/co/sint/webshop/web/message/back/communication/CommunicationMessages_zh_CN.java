package jp.co.sint.webshop.web.message.back.communication;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommunicationMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // ページ遷移エラー
        {
            "move_action_error", "不正确的{0}被指定了。"
        },
        // コンテンツ未登録エラー
        {
            "contents_exist_error", "宣传活动的内容没有被登录。"
        },
        // 実施中アンケート削除不可エラー
        {
            "delete_enquete_error", "实施中的问卷调查不能被删除。"
        },
        // 回答済みアンケート更新不可エラー
        {
            "answered_enquete_error", "回答中不能存在问卷调查的{0}的{1}。"
        },
        // キャンペーン削除エラー
        {
            "delete_campaign_error", "关联订购信息不能删除宣传活动。({0})"
        },
        // 実施中キャンペーン削除エラー
        {
            "delete_effect_campaign_error", "实施中的宣传活动不能删除。"
        },
        //20121119 促销对应 ob add start
        {
        	"duplicated_register_error","已经登录过的活动编号。"
        },
        {
        	"register_failed_error","{0}登录失败。"
        },
        {
        	"register_success_info","{0}登录成功。"
        },
        {
        	"choose_no_error","请选择活动类型。"
        },
        {
        	"choose_campaign_type_error","请选择对象类型。"
        },
        {
        	"update_failed_error","{0}更新失败。"
        },
        {
        	"update_success_info","{0}更新成功。"
        },
        {
        	"commodity_no_choose","请选择{0}。"
        },
        {
        	"condition_type_no_choose","请选择对象类型。"
        },  
        {
        	"no_common_commodity","该商品不是通常商品。"
        },
        {
        	"no_gift_commodity","该商品不是赠送商品。"
        },
        {
        	"duplicated_commodity_error","已经登录过的{0}商品。"
        },{
        	"out_range_error","已超过最大登录可能数。"
        },
        {
        	"no_special_many_error","赠品[{0}]：相同活动期间内，不允许关联多个赠品促销活动。"
        },
        {
        	"delete_failed_error","{0}删除失败。"
        },
        {
          "delete_success_info","{0}删除成功。"
        },
        {
          "discount_not_null","{0}必须输入。"
        },
        {
          "set_commodity_error","只能关联普通商品（非套餐商品）。"
        }
       //20121119 促销对应 ob add end
        // 20130730 txw add start
        ,{
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
