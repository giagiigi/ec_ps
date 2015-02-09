package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.UntrueOrderWordBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
/**
 * 虚假订单关键词
 */
public class UntrueOrderWordInitAction extends WebBackAction<UntrueOrderWordBean> {
  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    UntrueOrderWordBean bean = new UntrueOrderWordBean();
    setBean(bean);
  }

  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.UNTRUE_ORDER_WORD.isGranted(login);
  }
  
  @Override
  public boolean validate() {
    return true;
  }
  
  @Override
  public WebActionResult callService() {
    UntrueOrderWordBean bean = getBean();
    setRequestBean(bean);
    String parameter = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }
    if (parameter.equals("add")) {
      addInformationMessage(MessageFormat.format(Messages
          .getString("web.action.back.order.UntrueOrderWordRegisterAction.2"),true));
    }
    if (parameter.equals("delete")) {
      addInformationMessage(MessageFormat.format(Messages
          .getString("web.action.back.order.UntrueOrderWordDeleteAction.1"),true));
    }
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    UntrueOrderWordBean bean = (UntrueOrderWordBean) getRequestBean();
    setRequestBean(bean);
  }
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
 return Messages.getString("web.action.back.order.UntrueOrderWordInitAction.0");
  }
  
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102051002";
  }


}
