package jp.co.sint.webshop.web.action.back.order;

import java.util.List;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderListSendPaymentedConfirmAction extends WebBackAction<OrderListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.ORDER_UPDATE_SHOP) || getLoginInfo().hasPermission(Permission.ORDER_UPDATE_SITE);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean authorization = true;
    if (getBean().getCheckedOrderList().size() == 1) {
      if (getBean().getCheckedOrderList().get(0).equals("")) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
            Messages.getString("web.action.back.order.OrderListSendPaymentedConfirmAction.0")));
        authorization = false;
      }
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

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());

    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());

    List<String> orderNoArray = getBean().getCheckedOrderList();

    boolean valid = true;

    for (String orderNo : orderNoArray) {

      OrderContainer orderContainer = orderService.getOrder(orderNo);
      OrderSummary orderSummary = orderService.getOrderSummary(orderNo);

      OrderHeader orderHeader = orderContainer.getOrderHeader();

      // 顧客退会チェック
      String customerCode = orderHeader.getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(customerCode)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderNo));
          valid &= false;
        }
      }

      // キャンセル済みチェック
      if (orderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SET_PAYMENT_CANCELED_ORDER_WITH_NO, orderHeader.getOrderNo(),
            Messages.getString("web.action.back.order.OrderListSendPaymentedConfirmAction.1")));
        valid &= false;
        continue;
      }

      // 未入金チェック
      if (PaymentStatus.fromValue(orderHeader.getPaymentStatus()).equals(PaymentStatus.NOT_PAID)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_PAID_ORDER_WITH_NO, orderNo));
        valid &= false;
        continue;
      }

      // データ未連携チェック
      if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SEND_MAIL_DATA_TRANSPORTED, orderNo,
            Messages.getString("web.action.back.order.OrderListSendPaymentedConfirmAction.1")));
        valid &= false;
        continue;
      }

      Shop shop = shopService.getShop(orderContainer.getOrderHeader().getShopCode());

      MailingService mailingService = ServiceLocator.getMailingService(getLoginInfo());
      mailingService.sendPaymentReceivedMail(orderContainer, orderSummary, shop);
    }

    String nextUrl = null;
    if (valid) {
      nextUrl = "/app/order/order_list/search/mail_confirm";
    }

    setRequestBean(getBean());
    setNextUrl(nextUrl);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderListSendPaymentedConfirmAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021008";
  }

}
