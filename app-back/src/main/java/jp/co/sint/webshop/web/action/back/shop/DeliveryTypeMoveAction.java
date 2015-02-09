package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1050610:配送種別設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeMoveAction extends WebBackAction<DeliveryTypeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo());
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
    if (getMoveDisplayID().equals("delivery_type_edit") && StringUtil.hasValue(getDeliveryTypeNo())) {
      // 配送種別明細への遷移の場合、配送種別の存在チェックを行う
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      DeliveryType delivery = service.getDeliveryType(getLoginInfo().getShopCode(), NumUtil.toLong(getDeliveryTypeNo()));
      if (delivery == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.shop.DeliveryTypeMoveAction.0")));
        setRequestBean(getBean());
        setNextUrl(null);
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    setNextUrl("/app/shop/" + getMoveDisplayID() + "/init/" + getDeliveryTypeNo());
    DisplayTransition.add(getBean(), "/app/shop/delivery_type/init", getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
  }

  private String getMoveDisplayID() {
    String[] tmpList = getRequestParameter().getPathArgs();

    if (tmpList.length > 0) {
      return tmpList[0];
    } else {
      return "";
    }
  }

  private String getDeliveryTypeNo() {
    String[] tmpList = getRequestParameter().getPathArgs();

    if (tmpList.length > 1) {
      return tmpList[1];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.DeliveryTypeMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105061005";
  }

}
