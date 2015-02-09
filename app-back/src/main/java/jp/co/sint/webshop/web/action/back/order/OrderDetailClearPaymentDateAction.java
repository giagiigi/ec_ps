package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
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
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020220:受注管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailClearPaymentDateAction extends OrderDetailBaseAction {

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

    OrderHeader orderHeaderInfo = service.getOrderHeader(bean.getOrderNo());

    // データ存在チェック
    if (orderHeaderInfo == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.order.OrderDetailClearPaymentDateAction.0")));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 顧客退会チェック
    if (CustomerConstant.isCustomer(orderHeaderInfo.getCustomerCode())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      if (customerService.isWithdrawed(orderHeaderInfo.getCustomerCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeaderInfo.getOrderNo()));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    // 決済方法チェック(クレジットは不可)
    if (orderHeaderInfo.getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.SET_PAYMENT_CREDIT, orderHeaderInfo.getOrderNo(),
          Messages.getString("web.action.back.order.OrderDetailClearPaymentDateAction.1")));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // データ連携済チェック
    if (DataTransportStatus.fromValue(orderHeaderInfo.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    OrderIdentifier orderIdentifier = new OrderIdentifier();
    orderIdentifier.setOrderNo(bean.getOrderNo());
    orderIdentifier.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());

    ServiceResult result = service.clearPaymentDate(orderIdentifier);

    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (content == OrderServiceErrorContent.PAYMENT_DATE_SET_ERROR) {
          setNextUrl(null);
          setRequestBean(bean);
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_SET_CLEAR_PAYMENT_DATE, bean.getOrderNo()));
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
    return Messages.getString("web.action.back.order.OrderDetailClearPaymentDateAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102022002";
  }

}
