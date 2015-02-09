package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AccountListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1050910:管理ユーザマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountListMoveAction extends WebBackAction<AccountListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().isAdmin();
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    String nextUrl = "";
    if (tmpArgs.length > 0) {
      nextUrl = "/app/shop/" + tmpArgs[0] + "/init";
      for (int i = 1; i < tmpArgs.length; i++) {
        nextUrl += "/" + tmpArgs[i];
      }

      // 管理ユーザ明細に遷移する場合は管理ユーザの存在チェックを行う。
      if (tmpArgs[0].equals("account_edit")) {
        String userCode = "";
        if (tmpArgs.length > 1) {
          userCode = tmpArgs[1];
        }
        SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
        UserAccount account = service.getUserAccount(NumUtil.toLong(userCode));
        if (account == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.shop.AccountListMoveAction.0")));
          setNextUrl(null);
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
    } else {
      nextUrl = null;
    }

    DisplayTransition.add(getBean(), "/app/shop/account_list/init/move",
        getSessionContainer());

    setNextUrl(nextUrl);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountListMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105091003";
  }

}
