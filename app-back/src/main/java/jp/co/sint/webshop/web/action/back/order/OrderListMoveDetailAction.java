package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderListMoveDetailAction extends WebBackAction<OrderListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    return getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP) || getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    
    String[] para = getRequestParameter().getPathArgs();
    String giftCard = "";
    if (para.length == 2) {
      giftCard = para[1];
    }

    String orderNo = para[0];

    if (StringUtil.hasValue(giftCard)) {
      DisplayTransition.add(getBean(), "/app/analysis/gift_card_use_log/search_back", getSessionContainer());

    } else {
      DisplayTransition.add(getBean(), "/app/order/order_list/search_back", getSessionContainer());
    }
    setNextUrl("/app/order/order_detail/init/" + orderNo);

    
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderListMoveDetailAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021010";
  }

}
