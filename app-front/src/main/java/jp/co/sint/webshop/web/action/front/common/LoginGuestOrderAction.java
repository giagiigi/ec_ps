package jp.co.sint.webshop.web.action.front.common;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.LoginBean;

/**
 * U2010110:ログインのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class LoginGuestOrderAction extends WebFrontAction<LoginBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    if (getCart().getItemCount() == 0) {
      setNextUrl("/app/common/index");
      return FrontActionResult.RESULT_SUCCESS;
    }

    String[] tmpArgs = getRequestParameter().getPathArgs();

    String url = "/app/order/guest/init";

    for (String str : tmpArgs) {
      url += "/" + str;
    }
    setNextUrl(url);
    return FrontActionResult.RESULT_SUCCESS;

  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }
}
