package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.communication.CustomerGroupCampaignSearchCondition;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060510:顾客组别优惠规则管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignListSearchBackAction extends CustomerGroupCampaignListSearchAction {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected CustomerGroupCampaignSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客组别优惠规则管理返回处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106051005";
  }

}
