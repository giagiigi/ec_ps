package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.OrderBean;

/**
 * 受注アクションの基底クラスです
 * 
 * @author System Integrator Corp.
 * @param <T> 
 */
public abstract class OrderBaseAction<T extends OrderBean> extends WebFrontAction<T> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    Cashier cashier = getBean().getCashier();
    if (cashier == null) {
      return false;
    }
    if (getLoginInfo().isNotLogin() && cashier.getCustomer() == null) {
      return false;
    }
    return true;
  }

}
