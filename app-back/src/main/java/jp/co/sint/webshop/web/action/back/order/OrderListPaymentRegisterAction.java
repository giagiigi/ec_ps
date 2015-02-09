package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderIdentifier;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean.OrderSearchedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderListPaymentRegisterAction extends WebBackAction<OrderListBean> {

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
    boolean validation = true;

    OrderListBean bean = getBean();
    ValidationSummary summary = BeanValidator.validate(bean.getRegisterPaymentDateBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());

    }

    validation &= summary.isValid();

    List<String> orderNoArray = getBean().getCheckedOrderList();

    if (orderNoArray.size() == 1) {
      if (bean.getCheckedOrderList().get(0).equals("")) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
            Messages.getString("web.action.back.order.OrderListPaymentRegisterAction.0")));
        validation &= false;
        return validation;
      }
    }
    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    List<String> orderNoArray = getBean().getCheckedOrderList();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    OrderListBean bean = getBean();

    boolean validation = true;

    for (String orderNo : orderNoArray) {
      OrderHeader orderHeader = service.getOrderHeader(orderNo);

      // 顧客退会チェック
      String customerCode = orderHeader.getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(customerCode)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderNo));
          validation &= false;
        }
      }

      // データ未連携チェック
      if (orderHeader.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.REGISTER_PAYMENT_DATA_TRANSPORTED, orderNo));
        validation &= false;
      }

      // 未入金チェック
      if (!orderHeader.getPaymentStatus().equals(PaymentStatus.NOT_PAID.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.PAID_ORDER_WITH_NO, orderNo));
        validation &= false;
      }

      // キャンセル済みチェック
      if (orderHeader.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
        addErrorMessage(WebMessage
            .get(OrderErrorMessage.SET_PAYMENT_DATE_CANCELED_ORDER_WITH_NO, orderHeader.getOrderNo(),
                Messages.getString("web.action.back.order.OrderListPaymentRegisterAction.1")));
        validation &= false;
      }

      // 決済方法チェック(クレジットは不可)
      if (orderHeader.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
        addErrorMessage(WebMessage
            .get(OrderErrorMessage.SET_PAYMENT_CREDIT, orderHeader.getOrderNo(),
                Messages.getString("web.action.back.order.OrderListPaymentRegisterAction.1")));
        validation &= false;
      }

      if (DateUtil.truncateDate(orderHeader.getOrderDatetime()).after(
          DateUtil.fromString(bean.getRegisterPaymentDateBean().getRegisterPaymentDatetime()))) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.PAYMENT_ORDERDATE_OR_LATER_WITH_NO, orderNo));
        validation &= false;
      }
      // M17N 10361 追加 ここから
      if (orderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_ORDER.longValue())
          || orderHeader.getOrderStatus().equals(OrderStatus.PHANTOM_RESERVATION.longValue())) {
        addErrorMessage(WebMessage
            .get(OrderErrorMessage.SET_PAYMENT_DATE_PHANTOM_ORDER_WITH_NO, orderHeader.getOrderNo(), Messages.getString("web.action.back.order.OrderListPaymentRegisterAction.1")));
        validation &= false;
      }
      // M17N 10361 追加 ここまで
    }

    if (!validation) {
      setNextUrl(null);
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    Date paymentDate = DateUtil.fromString(bean.getRegisterPaymentDateBean().getRegisterPaymentDatetime());

    // serviceの処理結果に一件でもエラーがあるかどうか判別する為のフラグ
    boolean serviceResultErrorFlg = false;

    ServiceResult result = null;

    for (String orderNo : orderNoArray) {
      OrderIdentifier orderIdentifier = new OrderIdentifier();
      orderIdentifier.setOrderNo(orderNo);
      for (OrderSearchedBean orderSearchedBean : getBean().getOrderSearchedList()) {
        if (orderSearchedBean.getOrderNo().equals(orderNo)) {
          orderIdentifier.setUpdatedDatetime(orderSearchedBean.getUpdatedDatetime());
        }
      }
      result = service.setPaymentDate(orderIdentifier, paymentDate);

      if (result.hasError()) {
        serviceResultErrorFlg = true;
        Logger logger = Logger.getLogger(this.getClass());
        logger.warn(MessageFormat.format(
            Messages.log("web.action.back.order.OrderListPaymentRegisterAction.2"), orderNo));
      }
    }

    // サービスの処理結果に1件でもエラーが存在した場合、エラー画面遷移
    if (serviceResultErrorFlg) {
      return BackActionResult.SERVICE_ERROR;
    }

    setRequestBean(bean);
    setNextUrl("/app/order/order_list/search/" + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderListPaymentRegisterAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021004";
  }

}
