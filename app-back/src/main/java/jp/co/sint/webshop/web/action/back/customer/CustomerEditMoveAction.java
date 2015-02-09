package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditMoveAction extends CustomerEditBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean authorize() {
    // 遷移のみなので常にture
    return true;
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
    String displayMode = "";

    // parameter[0] : 次画面パラメータ , parameter[1] : 顧客コード
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      displayMode = StringUtil.coalesce(parameter[0], "");
      if (parameter.length > 1) {
        customerCode = StringUtil.coalesce(parameter[1], "");
      }
    }

    // 顧客存在チェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    boolean hasCustomer = true;
    if (StringUtil.hasValue(customerCode)) {
      if (service.isNotFound(customerCode) || service.isWithdrawed(customerCode)) {
        hasCustomer = false;
      }
      // ショップ管理者
      if (Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo())) {
        hasCustomer &= service.isShopCustomer(customerCode, getLoginInfo().getShopCode());
      }
    } else {
      hasCustomer = false;
    }
    if (!hasCustomer) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.CustomerEditMoveAction.0")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    if (displayMode.equals("address")) {
      // アドレス帳一覧存在チェック
      //delete by os012 20111215 start
//      CustomerSearchCondition condition = new CustomerSearchCondition();
//      condition.setCustomerCode(customerCode);
//      if (service.getCustomerAddressList(condition).getRowCount() < 1) {
//        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
//            Messages.getString("web.action.back.customer.CustomerEditMoveAction.1")));
//        setRequestBean(getBean());
//
//        return BackActionResult.RESULT_SUCCESS;
//      }
     // delete by os012 20111215 end
    }

    // 次画面のURLをセット
    setNextUrl(customerCode, displayMode);

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/customer/customer_edit/select/" + customerCode, getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 次画面のURLをセットします。
   * 
   * @param String
   *          displayMode
   * @param String
   *          customerCode
   */
  private void setNextUrl(String customerCode, String displayMode) {
    String nextPage = "";
    if (displayMode.equals("address")) {
      nextPage = "address_list";
    } else if (displayMode.equals("order")) {
      nextPage = "order_history";
    } else if (displayMode.equals("point")) {
      nextPage = "point_history";
    } else if (displayMode.equals("password")) {
      nextPage = "password_change";
    } else if (displayMode.equals("coupon")) {
      nextPage = "coupon_list";
    } else {
      nextPage = "customer_edit";
    }
    setNextUrl("/app/customer/" + nextPage + "/init/" + customerCode);
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
    return Messages.getString("web.action.back.customer.CustomerEditMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012004";
  }

}
