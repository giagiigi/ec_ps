package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.EnqueteResultBean;

/**
 * U2060120:アンケート完了のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteResultMoveAction extends WebFrontAction<EnqueteResultBean> {

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
    setNextUrl("/app/common/index");

    return FrontActionResult.RESULT_SUCCESS;
  }
}
