package jp.co.sint.webshop.web.action.front.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.AuthorizationService; //10.1.4 10154 追加
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.TransactionTokenAction; // 10.1.4 10195 追加
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.login.WebLoginManager;

/**
 * U2030230:お客様情報登録確認のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
// 10.1.4 10195 修正 ここから
// public class CustomerConfirmRegisterAction extends CustomerConfirmBaseAction {
public class CustomerConfirmRegisterAction extends CustomerConfirmBaseAction implements TransactionTokenAction {
// 10.1.4 10195 修正 ここまで

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerConfirmBean bean = getBean();

    CustomerService customerSv = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = new CustomerInfo();

    // 顧客情報
    Customer customer = new Customer();
    customer.setCustomerGroupCode(CustomerConstant.DEFAULT_GROUP_CODE);
    customer.setPassword(bean.getPassword());
    customer.setLoginErrorCount(0L);
    customer.setLoginLockedFlg(Long.parseLong(LoginLockedFlg.UNLOCKED.getValue()));
    customer.setCustomerStatus(Long.parseLong(CustomerStatus.MEMBER.getValue()));

    // アドレス情報
    CustomerAddress address = new CustomerAddress();

    // 登録データを設定
    setEditData(bean, customer, address);
    customer.setLoginDatetime(customer.getCreatedDatetime());

    // 顧客属性
    List<CustomerAttributeAnswer> answerList = new ArrayList<CustomerAttributeAnswer>();
    setCustomerAttributeAnswers(bean, answerList);

    info.setCustomer(customer);
    info.setAddress(address);
    info.setAnswerList(answerList);
    String originalPassword = customer.getPassword(); //10.1.4 10154 追加

    // データベース更新処理
    ServiceResult customerResult = customerSv.insertCustomer(info);

    // 顧客情報更新時エラー
    if (customerResult.hasError()) {
      for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl("/app/customer/customer_edit1/init");

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // ログイン情報をセッションに格納し、セッションを再作成する
    CustomerInfo loginCustomer = customerSv.getCustomer(customerSv.getCustomerCodeToEmail(bean.getEmail()));
    //10.1.4 10154 追加 ここから
    AuthorizationService authService = ServiceLocator.getAuthorizationService(getLoginInfo());
    ServiceResult authResult = authService.authorizeCustomer(loginCustomer.getCustomer().getLoginId(), originalPassword);
    //登録成功した情報で認証し、失敗する（＝登録できていない）場合は
    //明らかにエラー
    if (authResult.hasError()) {
        return FrontActionResult.SERVICE_ERROR;
    }
    //10.1.4 10154 追加 ここまで
    LoginInfo login = WebLoginManager.createFrontLoginInfo(loginCustomer.getCustomer());
    getSessionContainer().login(login);

    bean.setComplete(true);
    setNextUrl("/app/customer/customer_result");
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
  // 10.1.8 10394 追加 ここから
  /**
   * Actionの処理が終了後にSessionをrenewするかどうかを返します。<BR>
   * 
   * @return true-セッションをrenewする false-セッションをrenewしない
   */
  @Override
  public boolean isSessionRenew() {
    return true;
  }
  // 10.1.8 10394 追加 ここまで
}
