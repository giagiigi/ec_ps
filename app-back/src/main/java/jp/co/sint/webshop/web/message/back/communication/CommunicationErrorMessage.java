package jp.co.sint.webshop.web.message.back.communication;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum CommunicationErrorMessage implements MessageType {

  /** ページ遷移エラー */
  MOVE_ACTION_ERROR("move_action_error"),
  /** ページ遷移エラー */
  CONTENTS_EXIST_ERROR("contents_exist_error"),
  /** 実施中アンケート削除不可エラー */
  DELETE_ENQUETE_ERROR("delete_enquete_error"),
  /** 回答済みアンケート更新不可エラー */
  ANSWERED_ENQUETE_ERROR("answered_enquete_error"),
  /** キャンペーン削除エラー */
  DELETE_CAMPAIGN_ERROR("delete_campaign_error"),
  // soukai add ob 2011/12/15 start
  /** 顾客组别不存在错误 */
  CUSTOMER_GROUP_CAMPAIGN_NO_DATA_ERROR("customer_group_campaign_no_data_error"),
  /** 优惠比率超出0~100 错误 */
  CAMPAIGN_NUMBER_RANGE_OVER_ERROR("campaign_number_range_over_error"),
  /** 利用期间小于系统时间错误 */
  USE_DATE_SYSTEM_DATA_ERROR("use_data_system_data_error"),
  /** 比率不可以为空错误 */
  RATIO_NULL_ERROR("ratio_null_error"),
  /** 金额不可以为空错误 */
  MONEY_NULL_ERROR("money_null_error"),
  /** 比率格式错误 */
  RATIO_ERROR("ratio_error"),
  /** 金额格式错误 */
  MONEY_ERROR("money_error"),
  /** 关联商品不在商品分类下 */
  PLAN_CATEGORY_COMMODITY("plan_category_commoidty"),
  /** 关联商品不在品牌下 */
  PLAN_BARND_COMMODITY("plan_brand_commoidty"),
  /** 优惠券规则已经被使用，无法删除错误  */
  COUPON_DELETE_ERROR("coupon_delete_error"),
  /** 企划已经被使用，无法删除错误  */
  PLAN_DELETE_ERROR("plan_delete_error"),
  // 2012/12/13 促销对应 ob add start
  /** 促销活动已经被使用，无法删除错误  */
  NEW_CAMPAIGN_DELETE_ERROR("new_campaign_delete_error"),
  /** 促销活动不存在错误  */
  NEW_CAMPAIGN_NOT_EXIST_ERROR("new_campaign_not_exist_error"),
  // 2012/12/13 促销对应 ob add end
  // soukai add ob 2011/12/15 end
  /** 実施中キャンペーン削除エラー */
  DELETE_EFFECT_CAMPAIGN_ERROR("delete_effect_campaign_error"),
  
  /** 実施中キャンペーン削除エラー */
 //20121119 促销对应 ob add start
  /**重复登录*/
  DUPLICATED_REGISTER_ERROR("duplicated_register_error"),
  /**登录失败*/
  REGISTER_FAILED_ERROR("register_failed_error"),
  /**登录成功*/
  REGISTER_SUCCESS_INFO("register_success_info"),
  /**选择活动类型*/
  CHOOSE_NO_ERROR("choose_no_error"),
  CHOOSE_CAMPAIGN_TYPE_ERROR("choose_campaign_type_error"),
  UPDATE_FAILED_ERROR("update_failed_error"),
  DELETE_FAILED_ERROR("delete_failed_error"),
  DELETE_SUCCESS_INFO("delete_success_info"),
  UPDATE_SUCCESS_INFO("update_success_info"),
  /**关联商品*/
  COMMODITY_NO_CHOOSE("commodity_no_choose"),
  CONDITION_TYPE_NO_CHOOSE("condition_type_no_choose"),
  
  NO_COMMON_COMMODITY("no_common_commodity"),
  NO_GIFT_COMMODITY("no_gift_commodity"),
  
  DUPLICATED_COMMODITY_ERROR("duplicated_commodity_error"),
  OUT_RANGE_ERROR("out_range_error"),
  NO_SPECIAL_MANY_ERROR("no_special_many_error"),
  DISCOUNT_NOT_NULL("discount_not_null"),
  SET_COMMODITY_ERROR("set_commodity_error"),
  //20121119 促销对应 ob add end
  
  // 20130730 txw add start
  /** 折扣已经被使用，无法删除错误  */
  DISCOUNT_DELETE_ERROR("discount_delete_error"),
  /**重复登录*/
  DUPLICATED_DISCOUNT_ERROR("duplicated_discount_error"),
  /**重复登录*/
  DUPLICATED_DISCOUNT_COMMODITY_ERROR("duplicated_discount_commodity_error"),
  /** 折扣不存在错误  */
  DISCOUNT_NOT_EXIST_ERROR("discount_not_exist_error"),
  /** 折扣商品不存在错误  */
  DISCOUNT_COMMODITY_NOT_EXIST_ERROR("discount_commdoity_not_exist_error"),
  // 20130730 txw add end
  
  // zhangfeng 2014/4/9 add begin
  /** 折扣已经被使用，无法删除错误  */
  MESSAGE_DELETE_ERROR("discount_delete_error"),
  /** 折扣不存在错误  */
  MESSAGE_NOT_EXIST_ERROR("discount_not_exist_error");
  // zhangfeng 2014/4/9 add end
  
  
  private String messagePropertyId;

  private CommunicationErrorMessage(String messagePropertyId) {
    this.messagePropertyId = messagePropertyId;
  }

  /**
   * メッセージプロパティIDを取得します。
   * 
   * @return メッセージプロパティID
   */
  public String getMessagePropertyId() {
    return this.messagePropertyId;
  }

  /**
   * メッセージリソースを取得します。
   * 
   * @return メッセージリソース
   */
  public String getMessageResource() {
    return "jp.co.sint.webshop.web.message.back.communication.CommunicationMessages";
  }

}
