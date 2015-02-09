package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MyInfoBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MyInfoUpdateAction extends WebFrontAction<MyInfoBean> {

  // 操作成功提示
  // private static final String COMPLETE = "complete";

  @Override
  public boolean validate() {
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    MyInfoBean bean = getBean();

    FrontLoginInfo login = getLoginInfo();

    String customerCode = login.getCustomerCode();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = service.getCustomer(customerCode);

    // 更新処理の場合データ取得
    Customer customer = info.getCustomer();

    if (StringUtil.hasValue(bean.getBirthDate())) {
      customer.setBirthDate(DateUtil.fromString(bean.getBirthDate()));
    }
    customer.setLanguageCode(bean.getLanguageCode());
    customer.setRequestMailType(NumUtil.toLong(bean.getRequestMailType(), null));
    customer.setSex(NumUtil.toLong(bean.getSex(), null));
    customer.setLastName(bean.getLastName());
    customer.setEmail(bean.getEmail());
    // customer.setUpdatedDatetime(DateUtil.getSysdate());
    // customer.setUpdatedUser(customer.getLastName());

    ServiceResult serviceResult = service.updateCustomer1(customer);
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
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
      setNextUrl("/app/mypage/my_info/init/suc");
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

}
