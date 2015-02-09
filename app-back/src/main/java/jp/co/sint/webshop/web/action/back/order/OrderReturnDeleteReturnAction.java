package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020260:受注返品のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderReturnDeleteReturnAction extends WebBackAction<OrderReturnBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    boolean auth = false;
    if (config.getOperatingMode() == OperatingMode.MALL) {
      auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.SHOP) {
      auth = Permission.ORDER_MODIFY_SHOP.isGranted(login) || Permission.ORDER_MODIFY_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.ONE) {
      auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
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
    boolean valid = StringUtil.hasValue(getPathInfo(0));
    if (!valid) {
      // ShippingNoがURLに設定されていなければログイン画面に遷移
      setNextUrl("/app/common/login");
    }
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String shippingNo = getPathInfo(0);

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    ShippingHeader shippingHeader = service.getShippingHeader(shippingNo);

    if (shippingHeader == null) {
      setNextUrl("/app/order/order_return/init/" + getBean().getOrderNo() + "/" + WebConstantCode.COMPLETE_DELETE);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 顧客退会チェック
    if (CustomerConstant.isCustomer(shippingHeader.getCustomerCode())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      if (customerService.isWithdrawed(shippingHeader.getCustomerCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, shippingHeader.getOrderNo()));
        return BackActionResult.SERVICE_ERROR;
      }
    }

    ServiceResult result = service.deleteReturnItemData(shippingNo);

    if (result.hasError()) {
      for (ServiceErrorContent errors : result.getServiceErrorList()) {
        if (errors.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/order/order_return/init/" + shippingHeader.getOrderNo() + "/" + WebConstantCode.COMPLETE_DELETE);
    return BackActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] argsTmp = getRequestParameter().getPathArgs();
    if (argsTmp.length > index) {
      return argsTmp[index];
    }
    return "";
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderReturnDeleteReturnAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102026003";
  }

}
