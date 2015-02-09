package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopDeleteBean;
import jp.co.sint.webshop.web.bean.back.shop.ShopEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1050220:ショップマスタ登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopEditDeleteAction extends WebBackAction<ShopEditBean> {

  @Override
  public void init() {
    if (getLoginInfo().isShop()) {
      getBean().setShopCode(getLoginInfo().getShopCode());
    }
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authlization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (Permission.SITE_MANAGER.isGranted(loginInfo)) {
      authlization = true;
    } else if (Permission.SHOP_MANAGER.isGranted(loginInfo)) {
      authlization = true;
    } else if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(loginInfo)
        || Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(loginInfo)) {
      authlization = true;
    } else {
      authlization = false;
    }

    return authlization;
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
    ShopEditBean shopEditBean = getBean();

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    int count = service.countUndeletableOrders(shopEditBean.getShopCode());
    if (count > 0) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PAYMENT_OR_NOT_FIXED_ERROR));
      setNextUrl(null);
      setRequestBean(shopEditBean);
      return BackActionResult.RESULT_SUCCESS;
    } else {
      ShopDeleteBean bean = new ShopDeleteBean();
      bean.setShopCode(shopEditBean.getShopCode());
      setRequestBean(bean);
      setNextUrl("/app/shop/shop_delete/");

    }

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopEditDeleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105022003";
  }

}
