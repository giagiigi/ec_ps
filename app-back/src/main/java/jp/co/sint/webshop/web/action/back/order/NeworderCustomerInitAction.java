package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UIBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020120:新規受注（顧客選択）のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerInitAction extends NeworderCustomerBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 計算後カートに入っている商品チェック
    if (getCart().getItemCount() < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.NeworderCustomerInitAction.0")));
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
    NeworderCustomerBean bean = new NeworderCustomerBean();
    UIBean tmpBean = getSessionContainer().getTempBean();
    if (tmpBean != null && tmpBean instanceof NeworderCustomerBean) {
      bean = (NeworderCustomerBean) tmpBean;
    }

    UtilService utilSvc = ServiceLocator.getUtilService(getLoginInfo());
    bean.setSearchCustomerGroupList(utilSvc.getCustomerGroupNames());
    bean.setCustomerPermission(Permission.CUSTOMER_UPDATE.isGranted(getLoginInfo()));
    setRequestBean(bean);
    getSessionContainer().setTempBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    String[] tmp = getRequestParameter().getPathArgs();
    return (tmp.length > 0 && tmp[0].equals("back"));
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCustomerInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102012001";
  }

}
