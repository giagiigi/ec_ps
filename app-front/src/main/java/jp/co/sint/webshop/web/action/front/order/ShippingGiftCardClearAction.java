package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author Kousen.
 */
public class ShippingGiftCardClearAction extends ShippingBaseAction {

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

    bean.getCashier().setGiftCardUseAmount(BigDecimal.ZERO);
    createOutCardPrice();
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

}
