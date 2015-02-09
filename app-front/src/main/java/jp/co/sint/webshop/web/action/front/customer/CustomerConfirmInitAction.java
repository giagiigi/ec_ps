package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;

/**
 * U2030230:お客様情報登録確認のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerConfirmInitAction extends CustomerConfirmBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getBean() == null || getBean().isComplete()) {
      setNextUrl("/app/common/index");
      if (!getBean().isComplete()) {
        getSessionContainer().logout();
      }
      return false;
    }

    boolean validate = validateBean(getBean());
    if (!validate) {
      setNextUrl("/app/common/index");
      getSessionContainer().logout();
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerConfirmBean bean = getBean();

    if (bean == null || bean.isFailedTransitionFlg()) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    bean.setFailedTransitionFlg(true);
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
