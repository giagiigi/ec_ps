package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderIdentifier;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.PaymentBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020220:受注管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailSetPaymentDateAction extends OrderDetailBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)) {
      authorization = Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      authorization = Permission.ORDER_UPDATE_SHOP.isGranted(getLoginInfo())
          || Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
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
    boolean validation = true;
    PaymentBean bean = getBean().getPaymentEdit();
    validation = validateBean(bean);

    if (!StringUtil.hasValue(getBean().getPaymentEdit().getPaymentDate())) {
      validation = false;
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR,
          Messages.getString("web.action.back.order.OrderDetailSetPaymentDateAction.0")));
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
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderDetailBean bean = getBean();

    OrderHeader orderHeaderInfo = service.getOrderHeader(bean.getOrderNo());

    // データ存在チェック
    if (orderHeaderInfo == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.order.OrderDetailSetPaymentDateAction.1")));
    }
    // データ連携済チェック
    if (DataTransportStatus.fromValue(orderHeaderInfo.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
    }
    // 入金日＞受注日チェック
    if (DateUtil.truncateDate(orderHeaderInfo.getOrderDatetime())
        .after(DateUtil.fromString(bean.getPaymentEdit().getPaymentDate()))) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.PAYMENT_ORDERDATE_OR_LATER_WITH_NO, bean.getOrderNo()));
    }
    // キャンセル済みチェック
    if (orderHeaderInfo.getOrderStatus().equals(OrderStatus.CANCELLED.longValue())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.SET_PAYMENT_DATE_CANCELED_ORDER));
    }

    // 決済方法チェック(クレジットは不可)
    if (orderHeaderInfo.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.SET_PAYMENT_CREDIT, orderHeaderInfo.getOrderNo(),
          Messages.getString("web.action.back.order.OrderDetailSetPaymentDateAction.2")));
    }
    // 顧客退会済みチェック
    if (CustomerConstant.isCustomer(orderHeaderInfo.getCustomerCode())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      if (customerService.isWithdrawed(orderHeaderInfo.getCustomerCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeaderInfo.getOrderNo()));
      }
    }

    if (getDisplayMessage().getErrors().size() > 0) {
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    OrderIdentifier orderIdentifier = new OrderIdentifier();
    orderIdentifier.setOrderNo(bean.getOrderNo());
    orderIdentifier.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());

    ServiceResult result = service.setPaymentDate(orderIdentifier, DateUtil.fromString(bean.getPaymentEdit().getPaymentDate()));

    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (content == OrderServiceErrorContent.ALREADY_SET_CLEAR_PAYMENT_DATE) {
          setNextUrl(null);
          setRequestBean(bean);
          addErrorMessage(WebMessage.get(OrderErrorMessage.ALREADY_SET_CLEAR_PAYMENT_DATE, bean.getOrderNo()));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    setNextUrl("/app/order/order_detail/init/" + bean.getOrderNo() + "/" + WebConstantCode.COMPLETE_UPDATE);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderDetailSetPaymentDateAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102022006";
  }

}
