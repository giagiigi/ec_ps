package jp.co.sint.webshop.web.action.front.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerResultBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030230:お客様情報登録確認のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerConfirmUpdateAction extends CustomerConfirmBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (service.isNotFound(getBean().getCustomerCode()) || service.isInactive(getBean().getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.front.customer.CustomerConfirmUpdateAction.0")));
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

    CustomerConfirmBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 顧客情報
    CustomerInfo info = service.getCustomer(bean.getCustomerCode());
    Customer customer = info.getCustomer();
    customer.setUpdatedDatetime(bean.getUpdatedDatetimeCustomer());
    // アドレス情報
    CustomerAddress address = info.getAddress();
    address.setUpdatedDatetime(bean.getUpdatedDatetimeAddress());

    // 登録データを設定
    setEditData(bean, customer, address);

    // 顧客属性
    List<CustomerAttributeAnswer> answerList = new ArrayList<CustomerAttributeAnswer>();

    setCustomerAttributeAnswers(bean, answerList);

    info.setCustomer(customer);
    info.setAddress(address);
    info.setAnswerList(answerList);

    // データベース更新処理
    ServiceResult customerResult = service.updateCustomer(info);

    // DBエラーの有無によって次画面のURLを制御する
    if (customerResult.hasError()) {
      for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
      setRequestBean(bean);

      return FrontActionResult.RESULT_SUCCESS;
    }

    bean.setComplete(true);

    // ログイン情報をセッションに格納し、セッションを再作成する
    CustomerInfo loginCustomer = service.getCustomer(bean.getCustomerCode());
    LoginInfo login = WebLoginManager.createFrontLoginInfo(loginCustomer.getCustomer());
    getSessionContainer().login(login);

    setNextUrl("/app/customer/customer_result");

    CustomerResultBean nextBean = new CustomerResultBean();
    nextBean.setUpdateFlg(true);
    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
