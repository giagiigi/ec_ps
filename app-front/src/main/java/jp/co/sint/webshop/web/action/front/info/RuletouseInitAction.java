package jp.co.sint.webshop.web.action.front.info;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.RuletouseBean;

/**
 * U2050610:利用規約のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RuletouseInitAction extends WebFrontAction<RuletouseBean> {

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
    RuletouseBean bean = new RuletouseBean();
    setRequestBean(bean);
    setNextUrl(null);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
