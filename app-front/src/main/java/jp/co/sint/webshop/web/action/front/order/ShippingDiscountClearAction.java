package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author Kousen.
 */
public class ShippingDiscountClearAction extends ShippingBaseAction {

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

    ShippingBean bean = getBean();
    // cashierに画面情報を設定
    Cashier cashier = bean.getCashier();
    
    bean.setSelDiscountType("");
    bean.setSelPersonalCouponCode("");
    bean.setPublicCouponCode("");
    cashier.setDiscount(new CashierDiscount());
    createOutCardPrice();
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
