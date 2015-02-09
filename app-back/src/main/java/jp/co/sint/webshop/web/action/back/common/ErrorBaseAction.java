package jp.co.sint.webshop.web.action.back.common;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.action.common.ErrorBean;

/**
 * U1xxxxxx:エラーページの基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class ErrorBaseAction extends WebBackAction<ErrorBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ErrorBean bean = getBean();
    if (bean != null) {
      Throwable t = bean.getThrowable();

      // URL間違いと純粋エラーを区別
      if (t instanceof RuntimeException) {
        throw (RuntimeException) t;
      } else {
        throw new RuntimeException(t);
      }
    } else {
      throw new RuntimeException("session was timeout.");
    }
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
