package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CustomerChangepasswordBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030420:パスワード変更のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerChangepasswordUpdateAction extends WebFrontAction<CustomerChangepasswordBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = validateBean(getBean());

    CustomerChangepasswordBean bean = getBean();

    if (StringUtil.hasValue(bean.getOldPassword())) {
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      CustomerInfo customerInfo = service.getCustomer(getLoginInfo().getCustomerCode());
      if (!PasswordUtil.getDigest(bean.getOldPassword()).equalsIgnoreCase(customerInfo.getCustomer().getPassword())) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.NOT_MATCH_PASSWORD_ERROR));
        result = false;
      }
    }

    if (StringUtil.hasValue(bean.getNewPassword())) {
      // パスワードポリシーチェックエラー
      PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
      if (!policy.isValidPassword(bean.getNewPassword())) {
        addErrorMessage(WebMessage.get(MypageErrorMessage.PASSWORD_POLICY_ERROR));
        result = false;
      }

     if(result){ 
       String validateCode = this.getSessionContainer().getVerifyCode();
       if(StringUtil.hasValue(validateCode)){
         validateCode = validateCode.toLowerCase();
       }
       
       if(!bean.getVerificationCode().equalsIgnoreCase(validateCode)){
         addErrorMessage( Messages.getString("web.action.front.mypage.CustomerChangepasswordUpdateAction.1"));
         result = false;
       }
     }
      
      // パスワード不一致エラー
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

    CustomerChangepasswordBean bean = getBean();
    
    bean.setCustomerCode(getLoginInfo().getCustomerCode());

    // 顧客コードが存在しない場合
    if (StringUtil.isNullOrEmpty(bean.getCustomerCode())) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 顧客存在チェック
    if (service.isNotFound(bean.getCustomerCode()) || service.isInactive(bean.getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.CustomerChangepasswordUpdateAction.0")));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    Customer customer = new Customer();

    customer.setCustomerCode(bean.getCustomerCode());
    customer.setPassword(bean.getNewPassword());
    customer.setUpdatedDatetime(bean.getUpdatedDatetime());

    // DB更新
    ServiceResult serviceResult = service.updatePassword(customer);

    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.CustomerChangepasswordUpdateAction.0")));
        } else if (result.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
    } else {
      setNextUrl("/app/mypage/customer_changepassword/init/update");
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
