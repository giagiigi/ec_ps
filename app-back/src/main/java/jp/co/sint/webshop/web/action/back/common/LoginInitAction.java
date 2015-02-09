package jp.co.sint.webshop.web.action.back.common;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.common.LoginBean;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U3010110:ログインのアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class LoginInitAction extends WebBackAction<LoginBean> {

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
    Logger logger = Logger.getLogger(this.getClass());
    LoginBean bean = new LoginBean();

    // ログイン済みなら強制的にsession.invalidateする
    if (getLoginInfo().isLogin()) {
      getSessionContainer().logout();
      logger.debug(Messages.log("web.action.back.common.LoginInitAction.0"));
    }

    setRequestBean(bean);
    setNextUrl(null);

    return BackActionResult.RESULT_SUCCESS;
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

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    LoginBean bean = (LoginBean) getRequestBean();

    if (getConfig().isMall() || getConfig().isShop()) {
      bean.setOperatingModeIsMall(true);
    } else if (getConfig().isOne()) {
      bean.setOperatingModeIsMall(false);
    }

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.common.LoginInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "0101011002";
  }

}
