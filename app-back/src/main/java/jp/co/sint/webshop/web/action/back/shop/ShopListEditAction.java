package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1050210:ショップマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopListEditAction extends WebBackAction<ShopListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    boolean authorization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = false;
    } else {
      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(loginInfo);
    }

    return authorization;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String editShopCode = getRequestParameter().getPathArgs()[0];

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = service.getShop(editShopCode);

    if (shop == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.ShopListEditAction.0")));
      setNextUrl(null);
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    setNextUrl("/app/shop/shop_edit/select/" + editShopCode);
    DisplayTransition.add(getBean(), "/app/shop/shop_list/search_back/",
        getSessionContainer());

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
    return Messages.getString("web.action.back.shop.ShopListEditAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105021001";
  }

}
