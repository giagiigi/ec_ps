package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
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
public class CustomerEditUpdateAction extends CustomerEditBaseAction {

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
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = super.validate();

    // 顧客コードチェック
    CustomerEditBean bean = getBean();
    if (StringUtil.isNullOrEmpty(bean.getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.customer.CustomerEditUpdateAction.0")));
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

    CustomerEditBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = service.getCustomer(bean.getCustomerCode());

    // 顧客情報
    Customer customer = info.getCustomer();

    setCustomerData(customer, bean);
    customer.setCustomerCode(bean.getCustomerCode());
    customer.setUpdatedDatetime(bean.getUpdatedDatetimeCustomer());

    // アドレス情報
    CustomerAddress address = info.getAddress();
    //modify by os012 20111213 start  
	  
	 //address 为空异常处理
    if (address!=null)
    {
    	setAddressData(address, bean);
  	    address.setCustomerCode(bean.getCustomerCode()); 
  	    address.setUpdatedDatetime(bean.getUpdatedDatetimeAddress());
	    address.setAddressNo(address.getAddressNo());
	    address.setAddressAlias(address.getAddressAlias());
    }
  //modify by os012 20111213 end
    // 顧客属性
    List<CustomerAttributeAnswer> answerList = new ArrayList<CustomerAttributeAnswer>();
    setCustomerAttributeAnswers(bean, answerList);

    info.setCustomer(customer);
    info.setAddress(address);
    info.setAnswerList(answerList);

    // データベース更新処理
    //modify by os012 20111213 start
//    ServiceResult srCustomer = service.updateCustomer(info);
    ServiceResult srCustomer = service.updateCustomerOnly(info);
    //modify by os012 20111213 end
    // DBエラーの有無によって次画面のURLを制御する
    if (srCustomer.hasError()) {
      for (ServiceErrorContent result : srCustomer.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.customer.CustomerEditUpdateAction.0")));
          setNextUrl(null);
        } else if (result.equals(CustomerServiceErrorContent.ADDRESS_DELETED_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.customer.CustomerEditUpdateAction.1")));
          setNextUrl(null);
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.customer.CustomerEditUpdateAction.2")));
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.customer.CustomerEditUpdateAction.3")));
        } else {
          return BackActionResult.SERVICE_ERROR;
        }

        // 入力テキストモード、表示ボタンフラグを設定
        bean.setNextButtonDisplayFlg(true);
        bean.setUpdateButtonDisplayFlg(false);
        bean.setBackButtonDisplayFlg(false);
        bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
      }
    } else {
    	
      setNextUrl("/app/customer/customer_edit/select/" + customer.getCustomerCode() + "/update");
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerEditUpdateAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012006";
  }

}
