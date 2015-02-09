package jp.co.sint.webshop.web.action.front.info;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.ComplianceBean;

/**
 * U2050710:特定商取引法に基づく表記のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ComplianceInitAction extends WebFrontAction<ComplianceBean> {

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
    ComplianceBean bean = new ComplianceBean();
    setRequestBean(bean);
    setNextUrl(null);
    return FrontActionResult.RESULT_SUCCESS;
  }

}
