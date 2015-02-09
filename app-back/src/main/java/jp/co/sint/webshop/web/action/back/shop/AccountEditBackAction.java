package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.AccountEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050920:管理ユーザマスタ明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountEditBackAction extends AccountEditConfirmAction {

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
    AccountEditBean bean = getBean();

    bean.setDisplayConfirmButton(true);
    // 新規登録の場合
    if (getLoginInfo().isAdmin() && bean.getProcess().equals("insert")) {
      bean.setUpdateMode(WebConstantCode.DISPLAY_EDIT);
      bean.setConfirmMode(WebConstantCode.DISPLAY_EDIT);
      bean.setAdministratorMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setUpdateMode(WebConstantCode.DISPLAY_HIDDEN);
      bean.setConfirmMode(WebConstantCode.DISPLAY_EDIT);
      if (getLoginInfo().isAdmin()) {
        bean.setAdministratorMode(WebConstantCode.DISPLAY_EDIT);
      } else {
        bean.setAdministratorMode(WebConstantCode.DISPLAY_HIDDEN);
      }

    }
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountEditBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105092001";
  }

}
