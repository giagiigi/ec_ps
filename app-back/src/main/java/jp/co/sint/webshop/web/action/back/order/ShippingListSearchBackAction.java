package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.service.order.ShippingListSearchCondition;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListSearchBackAction extends ShippingListSearchAction {

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected ShippingListSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListSearchBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041011";
  }

}
