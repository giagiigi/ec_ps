package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.AddressEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030220:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressEditUpdateAction extends AddressEditBaseAction {

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
    if (StringUtil.isNullOrEmpty(getBean().getAddressNo())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.AddressEditUpdateAction.0")));
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

    AddressEditBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerAddress address = setAddressData(bean);
    address.setAddressNo(Long.parseLong(bean.getAddressNo()));
    address.setUpdatedDatetime(bean.getUpdatedDatetime());

    // データベース更新処理
    ServiceResult serviceResult = service.updateCustomerAddress(address);

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.customer.AddressEditUpdateAction.1")));
        } else if (result.equals(CustomerServiceErrorContent.ADDRESS_NO_DEF_FOUND_ERROR)
            || result.equals(CustomerServiceErrorContent.SELF_ADDRESS_UPDATE_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.customer.AddressEditUpdateAction.0")));
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);

    } else {
      setNextUrl("/app/customer/address_edit/init/"
          + address.getCustomerCode() + "/" + address.getAddressNo() + "/update");
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
    return Messages.getString("web.action.back.customer.AddressEditUpdateAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103022005";
  }

}
