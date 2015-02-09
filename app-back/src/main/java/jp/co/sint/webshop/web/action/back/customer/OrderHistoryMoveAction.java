package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.OrderHistoryBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1030150:注文履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryMoveAction extends WebBackAction<OrderHistoryBean> {

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

    String customerCode = "";
    String orderNo = "";

    // parameter[0] : 顧客コード , parameter[1] : 受注番号
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      customerCode = StringUtil.coalesce(parameter[0], "");
      if (parameter.length > 1) {
        orderNo = StringUtil.coalesce(parameter[1], "");
      }
    }

    //顧客の存在チェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (StringUtil.isNullOrEmpty(customerCode) || service.isNotFound(customerCode) || service.isWithdrawed(customerCode)) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.OrderHistoryMoveAction.0")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }
    // 受注の存在チェック
    if (StringUtil.isNullOrEmpty(orderNo)) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.OrderHistoryMoveAction.1")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    } else {
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      OrderContainer orderContainer = orderService.getOrder(orderNo);

      if (orderContainer == null || orderContainer.getOrderHeader() == null || orderContainer.getOrderDetails() == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.customer.OrderHistoryMoveAction.1")));
        setRequestBean(getBean());

        return BackActionResult.RESULT_SUCCESS;
      }
    }
    setNextUrl("/app/order/order_detail/init/" + orderNo);

    // 前画面情報設定
    DisplayTransition.add(null, "/app/customer/order_history/init_back/" + customerCode, getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = true;

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String customerCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(customerCode)) {
        return false;
      }

      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      authorization &= service.isShopCustomer(customerCode, getLoginInfo().getShopCode());
    }

    return authorization;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.OrderHistoryMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103015003";
  }

}
