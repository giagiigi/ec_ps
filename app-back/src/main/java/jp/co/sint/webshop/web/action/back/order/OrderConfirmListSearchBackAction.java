package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderConfirmListSearchBackAction extends OrderConfirmListSearchAction {

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected OrderListSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderConfirmListSearchAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021009";
  }

}
