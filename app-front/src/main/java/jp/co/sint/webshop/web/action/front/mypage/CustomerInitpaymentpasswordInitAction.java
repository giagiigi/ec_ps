package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CustomerInitpaymentpasswordBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030430:パスワード再発行のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerInitpaymentpasswordInitAction extends WebFrontAction<CustomerInitpaymentpasswordBean> {

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
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerInitpaymentpasswordBean bean = new CustomerInitpaymentpasswordBean();

    String[] param = getRequestParameter().getPathArgs();
    String token = "";

    if (param.length > 0) {
      token = param[0];
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    // URLパラメータのTOKENが有効かチェック
    Reminder reminderInfo = cs.getReminderInfo(token);
    if (reminderInfo == null) {
      bean.setDisplayFlg(false);
      addErrorMessage(WebMessage.get(MypageErrorMessage.NOT_USED_REMINDER));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    // 顧客存在/削除済みチェック
    if (cs.isNotFound(reminderInfo.getCustomerCode()) || cs.isInactive(reminderInfo.getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.customer.CustomerInitpasswordInitAction.0")));
 
      bean.setDisplayFlg(false);
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    bean.setToken(token);
    bean.setDisplayFlg(true);
    bean.setNewPassword("");
    bean.setNewPasswordConfirm("");
    bean.setUpdateCustomerCode(reminderInfo.getCustomerCode());
    CustomerInfo info = cs.getCustomer(reminderInfo.getCustomerCode());
    bean.setUpdatedDatetime(info.getCustomer().getUpdatedDatetime());

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
