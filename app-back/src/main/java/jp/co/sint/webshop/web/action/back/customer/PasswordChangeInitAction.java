package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.PasswordChangeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030230:パスワード変更のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PasswordChangeInitAction extends WebBackAction<PasswordChangeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
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
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    PasswordChangeBean bean = new PasswordChangeBean();
    bean.setNewPassword("");

    String[] urlParam = getRequestParameter().getPathArgs();
    // 存在チェック
    CustomerInfo info;
    if (urlParam.length > 0) {
      info = cs.getCustomer(urlParam[0]);
      if (cs.isNotFound(urlParam[0]) || cs.isWithdrawed(urlParam[0])) {
        setNextUrl("/app/common/dashboard/init/");
        setRequestBean(getBean());

        return BackActionResult.RESULT_SUCCESS;
      }
      // 10.1.2 10120 追加 ここから
      if (urlParam.length > 1) {
        String complete = "";
        complete = urlParam[1];
        if (WebConstantCode.COMPLETE_UPDATE.equals(complete)) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
              Messages.getString("web.action.back.customer.PasswordChangeInitAction.1")));
        } else {
          setNextUrl("/app/common/dashboard/init/");
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      // 10.1.2 10120 追加 ここまで
    } else {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    bean.setCustomerCode(urlParam[0]);
    bean.setUpdatedDatetime(info.getCustomer().getUpdatedDatetime());

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PasswordChangeInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103023001";
  }

}
