package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ShopManagementServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050620:配送種別設定明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeEditDeleteAction extends WebBackAction<DeliveryTypeEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo());
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
    String typeCode = getBean().getDeliveryTypeNo();
    if (typeCode == null || typeCode.equals("")) {
      addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.shop.DeliveryTypeEditDeleteAction.0"))); //$NON-NLS-1$
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    ServiceResult result = service.deleteDeliveryType(getLoginInfo().getShopCode(), NumUtil.toLong(getBean().getDeliveryTypeNo()));

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(ShopManagementServiceErrorContent.DELIVERY_TYPE_STILL_USE_ERROR)) {
          setRequestBean(getBean());
          setNextUrl(null);
          addErrorMessage(WebMessage.get(ShopErrorMessage.DELIVERY_TYPE_STILL_USE_ERROR));
          return BackActionResult.RESULT_SUCCESS;
        }
        if (error.equals(ShopManagementServiceErrorContent.USED_DELIVERY_TYPE)) {
          setRequestBean(getBean());
          setNextUrl(null);
          addErrorMessage(WebMessage.get(ShopErrorMessage.USED_DELIVERY_TYPE));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/delivery_type_edit/init/" + getBean().getDeliveryTypeNo() + "/" + WebConstantCode.COMPLETE_DELETE);

    getBean().setUpdateMode(false);
    getBean().setInsertMode(false);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.DeliveryTypeEditDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105062001";
  }

}
