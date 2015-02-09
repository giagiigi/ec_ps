package jp.co.sint.webshop.web.action.front.info;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.PrivacypolicyBean;

/**
 * U2050310:個人情報保護方針のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PrivacypolicyInitAction extends WebFrontAction<PrivacypolicyBean> {

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
    PrivacypolicyBean bean = new PrivacypolicyBean();
    setRequestBean(bean);
    setNextUrl(null);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
