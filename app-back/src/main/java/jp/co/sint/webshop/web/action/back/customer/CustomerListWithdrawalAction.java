package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1030110:顧客マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerListWithdrawalAction extends CustomerListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 削除権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_DELETE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 選択された顧客コードを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // チェックボックスが選択されているか
    if (!StringUtil.hasValueAllOf(values)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.customer.CustomerListWithdrawalAction.0")));
      return false;
    }

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
    Logger logger = Logger.getLogger(this.getClass());
    CustomerListBean bean = getBean();

    // 選択された顧客コードを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    List<String> errorCustomerCode = new ArrayList<String>();
    bean.setSeccessWithdrawal(false);

    ServiceResult serviceResult = null;
    for (String customerCode : values) {
      CustomerInfo info = service.getCustomer(customerCode);

      serviceResult = service.withdrawalFromMembership(customerCode, info.getCustomer().getUpdatedDatetime());
      if (serviceResult.hasError()) {
        for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
          if (result.equals(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR)) {
            bean.setSeccessWithdrawal(true);
          } else if (result.equals(CustomerServiceErrorContent.EXIST_ACTIVE_ORDER_ERROR)) {
            errorCustomerCode.add(customerCode);
          } else {
            return BackActionResult.SERVICE_ERROR;
          }
        }
        logger.error("failed");
      } else {
        bean.setSeccessWithdrawal(true);
      }
    }
    bean.setErrorCustomerCode(errorCustomerCode);

    setNextUrl("/app/customer/customer_list/search/withdrawal");
    logger.info("succeed");

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerListWithdrawalAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103011006";
  }

}
