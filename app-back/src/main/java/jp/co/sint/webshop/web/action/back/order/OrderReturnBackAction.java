package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020260:受注返品のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderReturnBackAction extends OrderReturnConfirmAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderReturnBean bean = getBean();
    bean.setDispStatus(WebConstantCode.DISPLAY_EDIT);
    bean.setConfirmButtonDisplay(true);
    bean.setRegisterButtonDisplay(false);
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderReturnBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102026001";
  }

}
