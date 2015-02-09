package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CustomerChangepasswordBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030420:パスワード変更のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerChangepasswordInitAction extends WebFrontAction<CustomerChangepasswordBean> {

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

    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = service.getCustomer(customerCode);

    // 顧客が存在しない場合
    if (service.isNotFound(customerCode) || service.isInactive(customerCode)) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    // URLから取得したパラメータをセット
    // parameter[0] : 処理パラメータ
    String[] parameter = getRequestParameter().getPathArgs();

    CustomerChangepasswordBean bean = new CustomerChangepasswordBean();

    if (parameter.length > 0) {
      setCompleteMessage(parameter[0]);
      bean.setUpdated(true);
    } else {
      bean.setUpdated(false);
    }

    bean.setCustomerCode(customerCode);
    bean.setUpdatedDatetime(info.getCustomer().getUpdatedDatetime());

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage(String completeParam) {
    if (completeParam.equals("update")) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
          Messages.getString("web.action.front.mypage.CustomerChangepasswordInitAction.0")));
    }
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
