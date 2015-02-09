package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerGroupBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030410:顧客グループマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupDeleteAction extends WebBackAction<CustomerGroupBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerGroup group = new CustomerGroup();
    CustomerGroupBean cBean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    // 存在チェック
    if (urlParam.length > 0) {
      if (cs.getCustomerGroup((urlParam[0])) == null) {
        setNextUrl("/app/customer/customer_group/init/delete");
        return BackActionResult.RESULT_SUCCESS;
      }
    } else {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.customer.CustomerGroupDeleteAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    group.setCustomerGroupCode((urlParam[0]));

    ServiceResult sResult = cs.deleteCustomerGroup(group.getCustomerGroupCode());

    // サービス実行結果処理
    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CustomerServiceErrorContent.DEFAULT_CUSTOMERGROUP_DELETED_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DELETE_DEFAULT_CUSTOMERGROUP));
        } else if (result.equals(CustomerServiceErrorContent.REGISTERD_CUSTOMERGROUP_DELETED_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DELETE_REGISTERD_CUSTOMERGROUP));
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          setNextUrl("/app/customer/customer_group/init/delete");
        }
      }
    } else {
      setNextUrl("/app/customer/customer_group/init/delete");
    }

    this.setRequestBean(cBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerGroupDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103041001";
  }

}
