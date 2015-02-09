package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030610:ご注文履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryInitBackAction extends OrderHistoryInitAction {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected OrderListSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }
}
