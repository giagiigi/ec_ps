package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050610:配送種別設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeDeleteTimeAction extends WebBackAction<DeliveryTypeBean> {

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
    boolean valid = validateBean(getBean());

    if (getBean().getDeleteTimeCode().length() <= 0) {
      addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.shop.DeliveryTypeDeleteTimeAction.0"))); //$NON-NLS-1$
      return false;
    }

    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    ServiceResult result = service.deleteDeliveryAppointedTime(getLoginInfo().getShopCode(), NumUtil.toLong(getBean()
        .getSelectDeliveryTypeNo()), getBean().getDeleteTimeCode());

    if (result.hasError()) {
      setNextUrl(null);
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/delivery_type/open_time/" + getBean().getSelectDeliveryTypeNo()
        + "/" + WebConstantCode.COMPLETE_DELETE);

    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.DeliveryTypeDeleteTimeAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105061002";
  }

}
