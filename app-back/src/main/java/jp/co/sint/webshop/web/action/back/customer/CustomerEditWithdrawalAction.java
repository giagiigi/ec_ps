package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditWithdrawalAction extends CustomerEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    // 権限チェック
    BackLoginInfo login = getLoginInfo();
    if (Permission.CUSTOMER_DELETE.isGranted(login)) {
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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerEditBean bean = getBean();
    String customerCode = bean.getCustomerCode();

    // 顧客コードが存在しない場合
    if (customerCode.equals("")) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.CustomerEditWithdrawalAction.0")));
      setRequestBean(bean);

      return BackActionResult.RESULT_SUCCESS;
    }

    // 顧客退会処理
    ServiceResult serviceResult = service.withdrawalFromMembership(bean.getCustomerCode(), bean.getUpdatedDatetimeCustomer());

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR)) {
          setNextUrl("/app/customer/customer_edit/select/" + bean.getCustomerCode() + "/withdrawal");
        } else if (result.equals(CustomerServiceErrorContent.EXIST_ACTIVE_ORDER_ERROR)) {
          addErrorMessage(WebMessage.get(CustomerErrorMessage.DELETE_CUSTOMER_ORDER_ERROR));
          this.setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/customer/customer_edit/select/" + bean.getCustomerCode() + "/withdrawal");
    }

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerEditWithdrawalAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012007";
  }

}
