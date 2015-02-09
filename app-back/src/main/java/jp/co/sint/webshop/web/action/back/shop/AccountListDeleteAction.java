package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AccountListBean;
import jp.co.sint.webshop.web.bean.back.shop.AccountListBean.AccountListDetail;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050910:管理ユーザマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AccountListDeleteAction extends WebBackAction<AccountListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    if (getLoginInfo().isAdmin()) {
      if (getLoginInfo().isShop()) {
        auth = true;
      } else {
        auth = getLoginInfo().isSite();
      }
    } else {
      auth = false;
    }
    return auth;
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
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    UserAccount account = new UserAccount();
    AccountListDetail deleteUser = getBean().getDeleteUser();

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = deleteUser.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    account.setShopCode(shopCode);
    account.setUserCode(Long.parseLong(deleteUser.getUserCode()));

    ServiceResult result = service.deleteUserAccount(account);
    if (result.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/account_list/init/" + WebConstantCode.COMPLETE_DELETE);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.AccountListDeleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105091001";
  }

}
