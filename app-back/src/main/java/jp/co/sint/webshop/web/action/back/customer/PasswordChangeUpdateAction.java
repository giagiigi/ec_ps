package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.PasswordChangeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030230:パスワード変更のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PasswordChangeUpdateAction extends WebBackAction<PasswordChangeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_UPDATE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    PasswordChangeBean bean = getBean();

    boolean result = validateBean(getBean());

    // パスワードチェックエラー
    PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
    if (!policy.isValidPassword(bean.getNewPassword())) {
      addErrorMessage(WebMessage.get(CustomerErrorMessage.PASSWORD_POLICY_ERROR));
      result = false;
    }
    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    Customer cust = new Customer();
    PasswordChangeBean bean = getBean();

    // データベース登録処理
    String[] urlParam = getRequestParameter().getPathArgs();

    cust.setCustomerCode(bean.getCustomerCode());
    cust.setPassword(bean.getNewPassword());
    cust.setUpdatedDatetime(bean.getUpdatedDatetime());

    ServiceResult sResult = cs.updatePassword(cust);

    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.customer.PasswordChangeUpdateAction.0")));
        } else if (result.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
    } else {
      // 10.1.2 10120 修正 ここから
      // setNextUrl("/app/customer/customer_edit/select/" + urlParam[0]);
      setNextUrl("/app/customer/password_change/init/" + urlParam[0] + "/" + WebConstantCode.COMPLETE_UPDATE);
      // 10.1.2 10120 修正 ここまで
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
    return Messages.getString("web.action.back.customer.PasswordChangeUpdateAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103023002";
  }

}
