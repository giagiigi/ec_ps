package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

public class ShippingListSelectShopAction extends ShippingListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    BackLoginInfo login = getLoginInfo();

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      if (Permission.SHIPPING_READ_SITE.isGranted(login)) {
        auth = true;
      }
    } else {
      if (Permission.SHIPPING_READ_SHOP.isGranted(login) || Permission.SHIPPING_READ_SITE.isGranted(login)) {
        auth = true;
      }
    }
    return auth;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShippingListBean bean = getBean();

    if (StringUtil.isNullOrEmpty(bean.getSearchShopCode())) {
      bean.setDeliveryTypeDisplayFlg(false);
      bean.setSearchDeliveryType(new ArrayList<CodeAttribute>());
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());

    bean.setDeliveryTypeDisplayFlg(true);
    bean.setSearchDeliveryType(utilService.getDeliveryTypes(bean.getSearchShopCode(), true));
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListSelectShopAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041012";
  }

}
