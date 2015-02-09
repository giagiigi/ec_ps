package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SmsingService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.FriendCouponBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.common.AuthorizationErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

public class FriendCouponVerificationAction extends WebFrontAction<FriendCouponBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public WebActionResult callService() {
    FriendCouponBean bean = getBean();
    // 手机验证码重复验证
    if (StringUtil.hasValue(bean.getTell())) {
      SmsingService smsSv = ServiceLocator.getSmsingService(getLoginInfo());
      boolean tempFlg = smsSv.isExist(bean.getTell());
      if (tempFlg) {
        addErrorMessage(WebMessage.get(AuthorizationErrorMessage.MOBILE_DOUBLE_ERROR));
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }
    }

    // 有效性验证
    if (!StringUtil.hasValueAllOf(bean.getAuthCode(), bean.getTell())) {
      return FrontActionResult.SERVICE_ERROR;
    } else {
      String authCode = bean.getTell() + bean.getAuthCode();
      if (authCode.equals(getSessionContainer().getMobileAuthCode())) {
        getSessionContainer().setMobileAuthCode(null);
      } else {
        return FrontActionResult.SERVICE_ERROR;
      }
    }

    // 顧客携帯情報を更新する
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    Customer customer = service.getCustomerByLoginId(getLoginInfo().getLoginId());
    customer.setAuthCode(bean.getAuthCode());
    customer.setMobileNumber(bean.getTell());
    ServiceResult customerResult = service.updateCustomer1(customer);
    if (customerResult.hasError()) {
      for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, 
              Messages.getString("web.action.front.common.MobileAuthRegisterAction.1")));
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, 
              Messages.getString("web.action.front.common.MobileAuthRegisterAction.2")));
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
    } else {
      // ログイン情報をセッションに格納し、セッションを再作成する
      FrontLoginInfo login = getLoginInfo();
      login.setMobileCustomerFlg(Boolean.TRUE);
      login.setMobileNumber(customer.getMobileNumber());
      getSessionContainer().login(login);
      addInformationMessage(WebMessage.get(AuthorizationErrorMessage.MOBILE_COMPLETE));
    }
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
