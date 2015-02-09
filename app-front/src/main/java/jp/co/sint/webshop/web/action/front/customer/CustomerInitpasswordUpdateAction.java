package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.LoginBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerInitpasswordBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030430:パスワード再発行のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerInitpasswordUpdateAction extends WebFrontAction<CustomerInitpasswordBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;

    CustomerInitpasswordBean bean = getBean();

    result = validateBean(getBean());

    if (StringUtil.hasValue(bean.getNewPassword())) {
      // パスワードチェックエラー
      PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
      if (!policy.isValidPassword(bean.getNewPassword())) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.PASSWORD_POLICY_ERROR));
        result = false;
      }

      // 不一致エラー
      if (StringUtil.hasValue(bean.getNewPasswordConfirm()) && !bean.getNewPassword().equals(bean.getNewPasswordConfirm())) {
        addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_PASSWORD));
        result = false;
      }
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
    CustomerInitpasswordBean bean = getBean();

    // データベース登録処理
    cust.setCustomerCode(bean.getUpdateCustomerCode());
    cust.setPassword(bean.getNewPassword());
    cust.setUpdatedDatetime(bean.getUpdatedDatetime());

    ServiceResult sResult = cs.initPassword(cust, bean.getToken());

    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR)) {
          bean.setDisplayFlg(false);
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.front.customer.CustomerInitpasswordUpdateAction.0")));
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          bean.setDisplayFlg(false);
          addErrorMessage(WebMessage.get(MypageErrorMessage.NOT_USED_REMINDER));
        } else if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      this.setRequestBean(bean);
    } else {
      // ログイン情報を作成する
      LoginBean loginBean = new LoginBean();

      CustomerInfo info = cs.getCustomer(bean.getUpdateCustomerCode());
      loginBean.setLoginId(info.getCustomer().getLoginId());
      loginBean.setPassword(bean.getNewPassword());

      setNextUrl("/app/customer/customer_initpassword_done/init/");
      setRequestBean(loginBean);
    }

    return FrontActionResult.RESULT_SUCCESS;
  }
}
