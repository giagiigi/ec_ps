package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.PointHistoryBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030140:ポイント履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointHistoryDeleteAction extends PointHistoryBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_POINT_DELETE.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    PointHistoryBean bean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();

    // 存在チェック
    if (urlParam.length > 0) {
      if (cs.isNotFound(urlParam[0]) || cs.isWithdrawed(urlParam[0])) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.customer.PointHistoryDeleteAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }
    } else {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.PointHistoryDeleteAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    // ポイントシステム使用チェック
    if (!bean.getUsablePointSystem()) {
      addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_SYSTEM_DISABLED_DELETE));
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    CustomerInfo info = cs.getCustomer(urlParam[0]);
    ServiceResult sResult = cs.deleteIneffectivePointHistory(info.getCustomer().getCustomerCode());

    // サービス実行結果処理
    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR));
        } else if (result.equals(CustomerServiceErrorContent.POINT_SYSTEM_DISABLED_ERROR)) {
          addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_SYSTEM_DISABLED_DELETE));
        }
      }
    } else {
      setNextUrl("/app/customer/point_history/complete/" + urlParam[0] + "/delete");
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
    return Messages.getString("web.action.back.customer.PointHistoryDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103014002";
  }

}
