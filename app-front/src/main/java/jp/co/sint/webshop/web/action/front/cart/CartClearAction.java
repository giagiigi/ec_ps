package jp.co.sint.webshop.web.action.front.cart;

import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CartClearAction extends CartBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Cart cart = getCart();
    cart.clear();

    // 20111214 shen add start
    // 购物车履历清空
    if (getLoginInfo().isLogin()) {
      cart.clearCartHistroy(getLoginInfo().getCustomerCode());
    }
    // 20111214 shen add end

    setRequestBean(createBeanFromCart());
    setNextUrl("/cart/cart/init");
    return FrontActionResult.RESULT_SUCCESS;
  }

}
