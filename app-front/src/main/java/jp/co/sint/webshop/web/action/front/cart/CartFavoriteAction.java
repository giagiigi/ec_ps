package jp.co.sint.webshop.web.action.front.cart;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CartFavoriteAction extends CartBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().isLogin();
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String shopCode = getPathInfo(0);
    String skuCode = getPathInfo(1);
    getBean().setShopCode(shopCode);
    getBean().setSkuCode(skuCode);
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();
    String shopCode = getPathInfo(0);
    String skuCode = getPathInfo(1);

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = catalogService.getSkuInfo(shopCode, skuCode);

    if (commodity == null) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.cart.CartFavoriteAction.0")));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.insertFavoriteCommodity(customerCode, shopCode, skuCode);
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (content.equals(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.cart.CartFavoriteAction.1")));
          setRequestBean(getBean());
          return FrontActionResult.RESULT_SUCCESS;
        } else if (content.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.cart.CartFavoriteAction.2")));
          setRequestBean(getBean());
        } else if (content == CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR) {
          addErrorMessage(WebMessage.get(CartDisplayMessage.DUPLICATED_FAVORITE));
          setRequestBean(getBean());
          return FrontActionResult.RESULT_SUCCESS;
        }
      }
      return FrontActionResult.SERVICE_ERROR;
    }
    setRequestBean(getBean());

    addInformationMessage(WebMessage.get(CartDisplayMessage.COMPLETE_ADD_FAVORITE));
    return FrontActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
