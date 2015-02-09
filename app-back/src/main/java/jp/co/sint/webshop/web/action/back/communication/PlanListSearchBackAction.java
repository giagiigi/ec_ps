package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.service.communication.PlanSearchCondition;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030740:企划一览のアクションクラスです
 * 
 * @author OB.
 */
public class PlanListSearchBackAction extends PlanListSearchAction {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected PlanSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
	if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "促销企划管理画面返回处理";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划管理画面返回处理";
	}
    return "";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "5106081005";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106091005";
	}
    return "";
   }
}
