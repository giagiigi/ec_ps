package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.AddressListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1030210:アドレス帳一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressListMoveAction extends WebBackAction<AddressListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = true;

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String customerCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(customerCode)) {
        return false;
      }

      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      authorization &= service.isShopCustomer(customerCode, getLoginInfo().getShopCode());
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String customerCode = "";
    String addressNo = "";

    // parameter[0] : 顧客コード , parameter[1] : アドレス帳番号
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      customerCode = StringUtil.coalesce(parameter[0], "");
      if (parameter.length > 1) {
        addressNo = StringUtil.coalesce(parameter[1], "");
      }
    }

    // 顧客、アドレスの存在チェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    setRequestBean(getBean());

    if (StringUtil.isNullOrEmpty(customerCode) || service.isNotFound(customerCode) || service.isWithdrawed(customerCode)) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR,
          Messages.getString("web.action.back.customer.AddressListMoveAction.0")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }
    if (StringUtil.hasValue(addressNo)) {
      // 10.1.3 10150 修正 ここから
      // if (addressNo.equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO))) {
      //20120116 del by wjw  start
//      if (addressNo.equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO))) {
//      // 10.1.3 10150 修正 ここまで
//        addErrorMessage(WebMessage.get(CustomerErrorMessage.EDIT_SELF_ADDRESS_ERROR));
//        setRequestBean(getBean());
//
//        return BackActionResult.RESULT_SUCCESS;
//      }
      //20120116 del by wjw  end
      CustomerAddress address = service.getCustomerAddress(customerCode, Long.parseLong(addressNo));
      if (address == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR,
            Messages.getString("web.action.back.customer.AddressListMoveAction.1")));
        setRequestBean(getBean());

        return BackActionResult.RESULT_SUCCESS;
      }
    }

    setNextUrl("/app/customer/address_edit/init/" + customerCode + "/" + addressNo);

    // 前画面情報設定
    DisplayTransition.add(null, "/app/customer/address_list/init_back/" + customerCode, getSessionContainer());

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
    return Messages.getString("web.action.back.customer.AddressListMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103021004";
  }

}
