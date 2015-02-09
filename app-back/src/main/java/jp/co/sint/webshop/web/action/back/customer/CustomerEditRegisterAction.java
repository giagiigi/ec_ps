package jp.co.sint.webshop.web.action.back.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditRegisterAction extends CustomerEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 更新権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_UPDATE.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerEditBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = new CustomerInfo();

    // 顧客情報
    Customer customer = new Customer();
    customer.setPassword(bean.getPassword());
    customer.setRestPoint(BigDecimal.ZERO);

    setCustomerData(customer, bean);

    // アドレス情報
    CustomerAddress address = new CustomerAddress();

    setAddressData(address, bean);
    // 10.1.3 10150 修正 ここから
    // address.setAddressNo(CustomerConstant.SELFE_ADDRESS_NO);
    // address.setAddressAlias(CustomerConstant.SELFE_ADDRESS_AIES);
    address.setAddressNo(CustomerConstant.SELF_ADDRESS_NO);
    address.setAddressAlias(CustomerConstant.SELF_ADDRESS_ALIAS);
    // 10.1.3 10150 修正 ここまで
    // 顧客属性
    List<CustomerAttributeAnswer> answerList = new ArrayList<CustomerAttributeAnswer>();
    setCustomerAttributeAnswers(bean, answerList);
    
    info.setCustomer(customer);
    info.setAddress(address);
    info.setAnswerList(answerList); 
    // データベース更新処理
    //modify by os012 20111213 start
    //    ServiceResult customerResult = service.insertCustomer(info); 
    ServiceResult customerResult = service.insertCustomers(info);
    //modify by os012 20111213 end
    // 顧客情報更新時エラー
    if (customerResult.hasError()) {
      for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.customer.CustomerEditRegisterAction.0")));
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.customer.CustomerEditRegisterAction.1")));
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      } 
      // 入力テキストモード、表示ボタンフラグを設定
      bean.setNextButtonDisplayFlg(true);
      bean.setRegisterButtonDisplayFlg(false);
      bean.setBackButtonDisplayFlg(false);
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT); 
      setNextUrl(null);
    } else {
      setNextUrl("/app/customer/customer_edit/select/" + customer.getCustomerCode() + "/register");
    }
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerEditRegisterAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012005";
  }

}
