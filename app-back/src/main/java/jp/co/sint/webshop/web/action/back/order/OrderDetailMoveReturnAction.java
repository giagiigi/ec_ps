package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1020220:受注管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailMoveReturnAction extends OrderDetailBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
      authorization = Permission.ORDER_READ_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      authorization = Permission.ORDER_READ_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_READ_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.ORDER_READ_SITE.isGranted(getLoginInfo());
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
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderDetailBean bean = getBean();

    OrderHeader orderHeader = service.getOrderHeader(bean.getOrderNo());

    // データ存在チェック
    if (orderHeader == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.order.OrderDetailMoveReturnAction.0")));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // データ連携済チェック
    if (DataTransportStatus.fromValue(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    setNextUrl("/app/order/order_return/init/" + bean.getOrderNo());

    DisplayTransition.add(getBean(), "/app/order/order_detail/init/" + bean.getOrderNo(), getSessionContainer());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderDetailMoveReturnAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102022005";
  }

}
