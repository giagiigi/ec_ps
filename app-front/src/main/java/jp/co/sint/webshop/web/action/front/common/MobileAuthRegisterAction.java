package jp.co.sint.webshop.web.action.front.common;

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
import jp.co.sint.webshop.web.bean.front.common.MobileAuthBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.common.AuthorizationErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * @author System Integrator Corp.
 */
public class MobileAuthRegisterAction extends WebFrontAction<MobileAuthBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    MobileAuthBean bean = getBean();
    // 手机验证码重复验证
    if (StringUtil.hasValue(bean.getMobileNumber())) {
      SmsingService smsSv = ServiceLocator.getSmsingService(getLoginInfo());
      boolean tempFlg = smsSv.isExist(bean.getMobileNumber());
      if (tempFlg) {
        addErrorMessage(WebMessage.get(AuthorizationErrorMessage.MOBILE_DOUBLE_ERROR));
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }
    }

    // 有效性验证
    if (!StringUtil.hasValueAllOf(bean.getAuthCode(), bean.getMobileNumber())) {
      return FrontActionResult.SERVICE_ERROR;
    } else {
      String authCode = bean.getMobileNumber() + bean.getAuthCode();
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
    customer.setMobileNumber(bean.getMobileNumber());
    ServiceResult customerResult = service.updateCustomer1(customer);
    if (customerResult.hasError()) {
      for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.front.common.MobileAuthRegisterAction.1")));
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.front.common.MobileAuthRegisterAction.2")));
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
      if (StringUtil.hasValue(bean.getUrl()) && bean.getUrl().equals("mypage")) {
        setNextUrl("/app/mypage/new_my_coupon/init");
      } else if (StringUtil.hasValue(bean.getUrl()) && bean.getUrl().equals("new_gift_card")) {
        setNextUrl("/app/mypage/new_gift_card/init");
      } else {
        setNextUrl("/app/customer/customer_edit1/init/complete");
      }
    }
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getLoginInfo() == null || getLoginInfo().isNotLogin()) {
      setNextUrl("/app/common/index");
      return false;
    }
    boolean result = true;
    MobileAuthBean bean = getBean();
    result = validateBean(bean);
    if (!result) {
      return result;
    }
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    // 判断手机对应验证码是否存在有效（有效时间内）
    Long numFlg = service.getCount(bean.getMobileNumber(), bean.getAuthCode());
    if (numFlg == 0L) {
      this.addErrorMessage(Messages.getString("web.action.front.common.MobileAuthRegisterAction.3"));
      return false;
    }
    return true;
  }
}
