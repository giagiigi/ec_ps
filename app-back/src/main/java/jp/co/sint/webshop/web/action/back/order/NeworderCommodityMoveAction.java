package jp.co.sint.webshop.web.action.back.order;


import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;


/**
 * U1020110:新規受注(商品選択)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityMoveAction extends NeworderCommodityBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // カート情報再計算
    calculate();
    if (getDisplayMessage().getErrors().size() > 0) {
      return BackActionResult.RESULT_SUCCESS;
    }
    // 計算後カートに入っている商品チェック
    if (getCart().getItemCount() < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.NeworderCommodityMoveAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    setNextUrl("/app/order/neworder_customer/init/");
    DisplayTransition.add(getBean(), "/app/order/neworder_commodity/init/back", getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCommodityMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102011005";
  }

}
