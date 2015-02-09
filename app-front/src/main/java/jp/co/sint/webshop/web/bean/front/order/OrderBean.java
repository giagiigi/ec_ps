package jp.co.sint.webshop.web.bean.front.order;

import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;

/**
 * UIFrontBeanを拡張した抽象クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class OrderBean extends UIFrontBean {

  private static final long serialVersionUID = 1L; // 10.1.4 K00149 追加

  private Cashier cashier;

  /**
   * cashierを取得します。
   * 
   * @return cashier
   */
  public Cashier getCashier() {
    return cashier;
  }

  /**
   * cashierを設定します。
   * 
   * @param cashier
   *          cashier
   */
  public void setCashier(Cashier cashier) {
    this.cashier = cashier;
  }

}
