package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030510:顧客属性のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeDeleteAction extends WebBackAction<CustomerAttributeBean> {

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
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.customer.CustomerAttributeDeleteAction.0")));
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
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerAttributeBean cBean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    // 存在チェック
    if (cs.getAttribute(Long.parseLong((urlParam[0]))) == null) {
      setNextUrl("/app/customer/customer_attribute/init/deleteattribute");
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult sResult = cs.deleteCustomerAttributeAndChoices(Long.parseLong((urlParam[0])));

    // サービス実行結果処理
    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          setNextUrl("/app/customer/customer_attribute/init/deleteattribute");
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/customer/customer_attribute/init/deleteattribute");
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
    return Messages.getString("web.action.back.customer.CustomerAttributeDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051001";
  }

}
