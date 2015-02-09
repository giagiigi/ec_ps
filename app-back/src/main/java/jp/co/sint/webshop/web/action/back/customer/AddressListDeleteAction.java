package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.AddressListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030210:アドレス帳一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressListDeleteAction extends WebBackAction<AddressListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 参照権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_DELETE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    AddressListBean bean = getBean();
    if (StringUtil.isNullOrEmpty(bean.getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.AddressListDeleteAction.0")));
      return false;
    }

    // parameter[0] : アドレス番号
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      bean.setDeleteAddressNo(parameter[0]);
    } else {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.AddressListDeleteAction.1")));
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

    AddressListBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    String customerCode = bean.getCustomerCode();

    Long addressNo = Long.parseLong(bean.getDeleteAddressNo());

    // 本人削除エラー
    //  10.1.3 10150 修正 ここから
    // if (addressNo.equals(CustomerConstant.SELFE_ADDRESS_NO)) {
    if (addressNo.equals(CustomerConstant.SELF_ADDRESS_NO)) {
    // 10.1.3 10150 修正 ここまで
      addErrorMessage(WebMessage.get(CustomerErrorMessage.DELETE_SELF_ADDRESS_ERROR));
      setRequestBean(bean);
      setNextUrl(null);

      return BackActionResult.RESULT_SUCCESS;
    }
    // データベース更新処理
    ServiceResult serviceResult = service.deleteCustomerAddress(customerCode, addressNo);

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.customer.AddressListDeleteAction.0")));
        } else if (result.equals(CustomerServiceErrorContent.ADDRESS_DELETED_ERROR)) {
          setNextUrl("/app/customer/address_list/init/" + customerCode + "/delete");

          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/customer/address_list/init/" + customerCode + "/delete");
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
    return Messages.getString("web.action.back.customer.AddressListDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103021001";
  }

}
