package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1050920:管理ユーザマスタ明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountEditUpdateAction extends AccountEditRegisterBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    return baseCallService(REGISTER_TYPE_UPDATE);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountEditUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105092005";
  }

}
