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
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030410:顧客グループマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupRegisterAction extends WebBackAction<CustomerGroupBean> {

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
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
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
    CustomerGroupBean cBean = getBean();
    setNextUrl(null);
    return validateBean(cBean.getEdit());
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

    cBean.getEdit().setDisplayMode(WebConstantCode.DISPLAY_EDIT);

    if (cs.getCustomerGroup((cBean.getEdit().getCustomerGroupCode())) != null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
          Messages.getString("web.action.back.customer.CustomerGroupRegisterAction.0")));
      this.setRequestBean(cBean);

      return BackActionResult.RESULT_SUCCESS;
    }

    // データベース登録処理
    group.setCustomerGroupCode((cBean.getEdit().getCustomerGroupCode()));
    group.setCustomerGroupName(cBean.getEdit().getCustomerGroupName());
    group.setCustomerGroupPointRate(Long.parseLong(cBean.getEdit().getCustomerGroupPointRate()));
    // 20120517 shen add start
    group.setCustomerGroupNameEn(cBean.getEdit().getCustomerGroupNameEn());
    group.setCustomerGroupNameJp(cBean.getEdit().getCustomerGroupNameJp());
    // 20120517 shen add end

    ServiceResult sResult = cs.insertCustomerGroup(group);
    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CustomerServiceErrorContent.CUSTOMERGROUP_REGISTERED_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.customer.CustomerGroupRegisterAction.0")));
        } else if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/customer/customer_group/init/register");
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
    return Messages.getString("web.action.back.customer.CustomerGroupRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103041003";
  }

}
