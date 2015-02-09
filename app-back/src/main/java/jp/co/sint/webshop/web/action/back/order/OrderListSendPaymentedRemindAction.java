package jp.co.sint.webshop.web.action.back.order;

import java.util.List;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderListSendPaymentedRemindAction extends WebBackAction<OrderListBean> {

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
            Messages.getString("web.action.back.order.OrderListSendPaymentedRemindAction.0")));
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

    // 入金督促メールを送信する
    // まず、パラメータで渡された受注番号から支払方法を取得する。
    // 取得した支払方法別に必要な情報を支払方法情報タグに詰め込み、メールを送信する。

    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());

    List<String> orderNoArray = getBean().getCheckedOrderList();

    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();

    boolean valid = true;

    for (String orderNo : orderNoArray) {
      OrderContainer orderContainer = orderService.getOrder(orderNo);
      OrderHeader orderHeader = orderContainer.getOrderHeader();
      OrderSummary orderSummary = orderService.getOrderSummary(orderNo);

      // 顧客退会チェック
      String customerCode = orderHeader.getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(customerCode)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderNo));
          valid &= false;
          continue;
        }
      }

      // データ入金済みチェック
      if (PaymentStatus.fromValue(orderHeader.getPaymentStatus()).equals(PaymentStatus.PAID)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.PAID_ORDER_WITH_NO, orderNo));
        valid &= false;
        continue;
      }

      //データ未連携チェック
      if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SEND_MAIL_DATA_TRANSPORTED, orderNo,
            Messages.getString("web.action.back.order.OrderListSendPaymentedRemindAction.1"))); //$NON-NLS-1$
        valid &= false;
        continue;
      }

      // キャンセル済みチェック
      if (orderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SET_PAYMENT_CANCELED_ORDER_WITH_NO, orderHeader.getOrderNo(),
            Messages.getString("web.action.back.order.OrderListSendPaymentedRemindAction.1"))); //$NON-NLS-1$
        valid &= false;
        continue;
      }

      Shop shop = shopService.getShop(orderHeader.getShopCode());
//      PaymentMethodBean paymentMethod = supporter.createPaymentMethodBean(orderHeader.getShopCode(), orderSummary.getTotalAmount()
//          - orderSummary.getUsedPoint(), orderSummary.getUsedPoint());
      PaymentMethodBean paymentMethod = supporter.createPaymentMethodBean(orderHeader.getShopCode(), BigDecimalUtil.subtract(orderSummary.getTotalAmount(),
          orderSummary.getUsedPoint()), orderSummary.getUsedPoint(), orderSummary.getCouponPrice());
      paymentMethod.setPaymentMethodCode(NumUtil.toString(orderHeader.getPaymentMethodNo()));
      CashierPayment cashierPayment = supporter.createCashierPayment(paymentMethod);

      // 支払方法チェック
      CashierPaymentTypeBase selectPayment = cashierPayment.getSelectPayment();
      if (selectPayment != null) {
        // 支払方法が削除済みの場合は全額ポイント払い等ではないと判断する。（支払不要全額ポイント払いは削除不可のため）
        String paymentMethodType = selectPayment.getPaymentMethodType();
        if (paymentMethodType.equals(PaymentMethodType.NO_PAYMENT.getValue())
            || paymentMethodType.equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
          // 支払不要 or 全額ポイント払いの場合、入金督促メール送信不可
          addErrorMessage(WebMessage.get(OrderErrorMessage.SEND_REMINDER_MAIL_TO_NOT_PAY, orderHeader.getOrderNo()));
          valid &= false;
          continue;
        }
      }

      MailingService mailingService = ServiceLocator.getMailingService(getLoginInfo());
      ServiceResult result = mailingService.sendPaymentReminderMail(orderContainer, orderSummary, shop, cashierPayment);
      if (result.hasError()) {
        for (ServiceErrorContent content : result.getServiceErrorList()) {
          if (content == OrderServiceErrorContent.PAYMENT_METHOD_NOT_FOUND_ERROR) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.SEND_ORDER_MAIL_FAILED_WITH_NO, orderHeader.getOrderNo()));
            valid &= false;
            continue;
          }
        }
      }

    }

    String nextUrl = null;
    if (valid) {
      nextUrl = "/app/order/order_list/search/mail_remind";
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
    return Messages.getString("web.action.back.order.OrderListSendPaymentedRemindAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021009";
  }

}
