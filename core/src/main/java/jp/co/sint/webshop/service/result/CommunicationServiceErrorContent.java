package jp.co.sint.webshop.service.result;

/**
 * CustomerListService内で発生するエラーEnum
 * 
 * @author System Integrator Corp.
 */
public enum CommunicationServiceErrorContent implements ServiceErrorContent {
  /** アンケートコード登録済みエラー */
  DUPLICATED_CODE_ERROR,
  /** アンケート期間重複エラー */
  DUPLICATED_PERIOD_ERROR,
  //soukai add ob 2011/12/26 start
  /**优惠券信息已被使用 */
  NEWCOUPONRULE_USE_ERROR,
  /**企划已被使用 */
  PLAN_USE_ERROR,
  //soukai add ob 2011/12/26 end
  /** キャンペーン削除エラー */
  DELETE_CAMPAIGN_ERROR,
  // 20130730 txw add start
  DELETE_DISCOUNT_ERROR;
  // 20130730 txw add end

}
