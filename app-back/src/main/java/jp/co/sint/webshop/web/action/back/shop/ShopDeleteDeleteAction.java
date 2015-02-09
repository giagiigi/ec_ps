package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopDeleteBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1050240:ショップマスタ削除確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopDeleteDeleteAction extends WebBackAction<ShopDeleteBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(loginInfo)) {
      authorization = true;
    } else {
      authorization = false;
    }

    return authorization;
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
    ShopDeleteBean bean = getBean();

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    int count = service.countUndeletableOrders(bean.getShopCode());

    if (count > 0) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PAYMENT_OR_NOT_FIXED_ERROR));
      setNextUrl(null);
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.deleteShop(bean.getShopCode());

    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setRequestBean(bean);
      setNextUrl("/app/shop/shop_delete/init/delete");
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopDeleteDeleteAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105024002";
  }

}
