package jp.co.sint.webshop.web.action.back.common;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebMainAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1xxxxxx:ユーティリティのアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class UtilBackAction extends WebMainAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    BackTransitionInfo backTransitionInfo = DisplayTransition.getBackTransitionInfo(getSessionContainer());

    setNextUrl(backTransitionInfo.getUrl());
    setRequestBean(backTransitionInfo.getBean());

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
   * ログインユーザ情報を取得します。
   * 
   * @return ログインユーザ情報
   */
  @Override
  public LoginInfo getLoginInfo() {
    return null;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }


}
