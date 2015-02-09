package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.MailingServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListSendShippingMailAction extends ShippingListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());

    ShippingListBean reqBean = getBean();
    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    if (StringUtil.hasValue(reqBean.getSearchShopCode())) {
      reqBean.setSearchDeliveryType(utilService.getDeliveryTypes(reqBean.getSearchShopCode(), true));
    }

    MailingService mailService = ServiceLocator.getMailingService(getLoginInfo());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    for (String shippingNo : reqBean.getShippingCheck()) {
      ShippingHeader shippingHeader = service.getShippingHeader(shippingNo);

      OrderContainer container = service.getOrder(shippingHeader.getOrderNo(), shippingNo);
      OrderContainer allContainer = service.getOrder(shippingHeader.getOrderNo());

      if (NumUtil.toString(shippingHeader.getShippingStatus()).equals(ShippingStatus.CANCELLED.getValue())) {
        // 出荷ステータスが「キャンセル」の場合は出荷実績登録不可
        addErrorMessage(WebMessage.get(OrderErrorMessage.CANCELLED_SHIPPING_WITH_NO, shippingHeader.getShippingNo(),
            Messages.getString("web.action.back.order.ShippingListSendShippingMailAction.0")));
        continue;
      }

      if (!shippingHeader.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_DATA_SALES_STATUS_NOT_FIXED, shippingNo));
        continue;
      }

      // 顧客が退会済みの場合はメールを送信しない
      if (CustomerConstant.isCustomer(shippingHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(shippingHeader.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, shippingHeader.getOrderNo()));
          continue;
        }
      }

      // データ連携中だったらメールを送信しない
      if (DataTransportStatus.fromValue(container.getOrderHeader().getDataTransportStatus())
          .equals(DataTransportStatus.TRANSPORTED)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SEND_SHIPPING_MAIL_DATA_TRANSPORTED_WITH_NO, shippingNo));
        continue;
      }

      Shop shop = shopService.getShop(shippingHeader.getShopCode());      

      CashierPaymentTypeBase base = new CashierPaymentTypeBase();
      base.setPaymentCommission(container.getOrderHeader().getPaymentCommission());
      base.setPaymentMethodCode(NumUtil.toString(container.getOrderHeader().getPaymentMethodNo()));
      base.setPaymentMethodName(container.getOrderHeader().getPaymentMethodName());
      base.setPaymentMethodType(container.getOrderHeader().getPaymentMethodType());

      CashierPayment payment = new CashierPayment();
      payment.setSelectPayment(base);
      payment.setShopCode(container.getOrderHeader().getShopCode());

      ServiceResult serviceResult = mailService.sendShippingReceivedMail(container,
          allContainer,
          shop,
          payment,
          shippingNo);

      if (serviceResult.hasError()) {
        for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
          if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            addErrorMessage(WebMessage.get(ActionErrorMessage.REGISTER_MAILQUEUE_FAILED,
                Messages.getString("web.action.back.order.ShippingListSendShippingMailAction.1") + shippingNo));
          } else if (errorContent.equals(MailingServiceErrorContent.NO_MAIL_TEMPLATE_ERROR)) {
            return BackActionResult.SERVICE_ERROR;
          } else if (errorContent.equals(MailingServiceErrorContent.NO_CUSTOMER_TO_SEND_MAIL)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NO_CUSTOMER_TO_SEND_SHIPPING_MAIL_WITH_NO, shippingNo));
          } else {
            addErrorMessage(WebMessage.get(OrderErrorMessage.SEND_SHIPPING_MAIL_FAILED_WITH_NO, shippingNo));
          }
        }
      } else {
        addInformationMessage(WebMessage.get(CompleteMessage.SEND_SHIPPING_MAIL_COMPLETE, shippingNo));
      }
    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    ShippingListBean reqBean = getBean();

    if (reqBean.getShippingCheck().isEmpty()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.ShippingListSendShippingMailAction.2")));
      result = false;
    }

    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListSendShippingMailAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041013";
  }

}
