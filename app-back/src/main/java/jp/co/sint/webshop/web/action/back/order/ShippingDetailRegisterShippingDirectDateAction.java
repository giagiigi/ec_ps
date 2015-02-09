package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.InputShippingReport;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.ShippingDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingDetailRegisterShippingDirectDateAction extends WebBackAction<ShippingDetailBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth;
    BackLoginInfo login = getLoginInfo();
    if (Permission.SHIPPING_UPDATE_SITE.isGranted(login)) {
      auth = true;
    } else if (Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
      auth = true;
    } else {
      auth = false;
    }
    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ShippingDetailBean reqBean = getBean();

    return validateItems(reqBean.getDeliveryHeader(), "shippingDirectDate");
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ShippingDetailBean reqBean = getBean();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    ServiceResult serviceResult = null;

    String shippingNo = reqBean.getDeliveryHeader().getShippingNo();
    ShippingHeader shippingHeader = service.getShippingHeader(shippingNo);

    if (shippingHeader == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingDirectDateAction.0"), shippingNo)));
    }

    if (NumUtil.toString(shippingHeader.getShippingStatus()).equals(ShippingStatus.CANCELLED.getValue())) {
      // 出荷ステータスが「キャンセル」の場合は出荷指示日設定不可
      addErrorMessage(WebMessage.get(OrderErrorMessage.CANCELLED_SHIPPING_WITH_NO, shippingHeader.getShippingNo(),
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingDirectDateAction.1")));
    }

    // 顧客退会チェック
    if (CustomerConstant.isCustomer(shippingHeader.getCustomerCode())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      if (customerService.isWithdrawed(shippingHeader.getCustomerCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, shippingHeader.getOrderNo()));
      }
    }

    if (NumUtil.toString(shippingHeader.getFixedSalesStatus()).equals(FixedSalesStatus.FIXED.getValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可
      addErrorMessage(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO, shippingHeader.getOrderNo()));
    }

    InputShippingReport shippedResult = new InputShippingReport();
    shippedResult.setShippingNo(shippingNo);
    shippedResult.setShippingDirectDate(DateUtil.fromString(reqBean.getDeliveryHeader().getShippingDirectDate()));
    shippedResult.setOrderUpdatedDatetime(reqBean.getOrderHeader().getOrderHeaderUpdatedDatetime());
    shippedResult.setUpdatedDatetime(reqBean.getDeliveryHeader().getShippingHeaderUpdatedDatetime());
    shippedResult.setUpdatedUser(getLoginInfo().getLoginId());

    OrderHeader orderHeader = service.getOrderHeader(shippingHeader.getOrderNo());

    if (orderHeader == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingDirectDateAction.2"),
          shippingHeader.getOrderNo())));
    }

    if (NumUtil.toString(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED.getValue())) {
      // データ連携済みステータスが「連携済み」の場合はステータスの変更不可
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_DATA_TRANSPORTED_WITH_NO, shippingHeader.getShippingNo()));
    }

    if (!NumUtil.toString(shippingHeader.getShippingStatus()).equals(ShippingStatus.READY.getValue())) {
      if (reqBean.isUpdateWithShipping()) {
        // 「出荷可能」の場合のみ出荷指示可能
        addErrorMessage(WebMessage.get(OrderErrorMessage.IMPOSSIBILITY_SHIPPING_DIRECT, shippingHeader.getShippingNo()));
      } else {
        addErrorMessage(WebMessage.get(OrderErrorMessage.IMPOSSIBILITY_SHIPPING_DIRECT_DATE, shippingHeader.getShippingNo()));
      }
    }
    
    // M17N 10361 追加 ここから
    OrderStatus status = OrderStatus.fromValue(orderHeader.getOrderStatus());
    if (status == OrderStatus.PHANTOM_ORDER || status == OrderStatus.PHANTOM_RESERVATION) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_IS_PHANTOM, shippingHeader.getShippingNo()));
    }
    // M17N 10361 追加 ここから

    if (reqBean.isUpdateWithShipping()) {
      // 出荷手配してしまう場合のみチェックする項目
      if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_IS_RESERVED));
      }
    }

    if (shippedResult.getShippingDirectDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
      // 出荷指示日が受注日よりも前の日付の場合はエラー
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_INPUT_ORDERDATE_OR_LATER_WITH_NO, shippingHeader.getShippingNo(),
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingDirectDateAction.3")));
    }

    if (super.getDisplayMessage().getErrors().size() > 0) {
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;

    }

    serviceResult = service.registerShippingDirectionDate(shippedResult, reqBean.isUpdateWithShipping());
    if (serviceResult != null) {
      if (serviceResult.hasError()) {
        for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
          if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
                Messages.getString("web.action.back.order.ShippingDetailRegisterShippingDirectDateAction.0"),
                shippingHeader.getShippingNo())));
          } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          } else if (errorContent.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.FIXED_SHIPPING_STATUS_WITH_NO, shippingHeader.getShippingNo()));
          } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            return BackActionResult.SERVICE_ERROR;
          }
        }
      }
    }

    setNextUrl("/app/order/shipping_detail/init/"
        + reqBean.getDeliveryHeader().getShippingNo() + "/update_shipping_direct_date");

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingDetailRegisterShippingDirectDateAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102042005";
  }

}
