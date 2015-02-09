package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerGroupBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerGroupBean.CustomerGroupBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030410:顧客グループマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupSelectAction extends WebBackAction<CustomerGroupBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    // 権限チェック
    BackLoginInfo login = getLoginInfo();
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
      auth = true;
    } else {
      auth = false;
    }

    return auth;
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
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    String[] urlParam = getRequestParameter().getPathArgs();
    CustomerGroupBean cBean = getBean();
    CustomerGroupBeanDetail edit = cBean.getEdit();

    CustomerGroup group = cs.getCustomerGroup((urlParam[0]));
    if (group == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR));
      this.setRequestBean(cBean);
      return BackActionResult.RESULT_SUCCESS;
    }
    edit.setCustomerGroupCode(group.getCustomerGroupCode());
    edit.setCustomerGroupName(group.getCustomerGroupName());
    edit.setCustomerGroupPointRate(Long.toString(group.getCustomerGroupPointRate()));
    edit.setUpdatedDatetime(group.getUpdatedDatetime());
    edit.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    // 20120517 shen add start
    edit.setCustomerGroupNameEn(group.getCustomerGroupNameEn());
    edit.setCustomerGroupNameJp(group.getCustomerGroupNameJp());
    // 20120517 shen add end
    cBean.setEdit(edit);

    this.setRequestBean(cBean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerGroupSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103041004";
  }

}
