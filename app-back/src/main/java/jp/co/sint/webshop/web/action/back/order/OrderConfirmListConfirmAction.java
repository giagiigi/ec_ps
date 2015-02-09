package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderConfirmListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderConfirmListConfirmAction extends WebBackAction<OrderConfirmListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.ORDER_UPDATE_SHOP) || getLoginInfo().hasPermission(Permission.ORDER_UPDATE_SITE);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean validation = true;

    OrderConfirmListBean bean = getBean();
    List<String> orderNoArray = getBean().getCheckedOrderList();

    if (orderNoArray.size() == 1) {
      if (bean.getCheckedOrderList().get(0).equals("")) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
            Messages.getString("web.action.back.order.OrderConfirmListConfirmAction.0")));
        validation &= false;
        return validation;
      }
    }
    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    List<String> orderNoArray = getBean().getCheckedOrderList();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    OrderConfirmListBean bean = getBean();

    // serviceの処理結果に一件でもエラーがあるかどうか判別する為のフラグ
    boolean serviceResultErrorFlg = false;
    ServiceResult result = null;
    for (String orderNo : orderNoArray) {
      result = service.setOrderFlg(orderNo);

      if (result.hasError()) {
        serviceResultErrorFlg = true;
        Logger logger = Logger.getLogger(this.getClass());
        logger.warn(MessageFormat.format(
            Messages.log("web.action.back.order.OrderConfirmListConfirmAction.2"), orderNo));
      }
      
    }

    // サービスの処理結果に1件でもエラーが存在した場合、エラー画面遷移
    if (serviceResultErrorFlg) {
      return BackActionResult.SERVICE_ERROR;
    }

    setRequestBean(bean);
    setNextUrl("/app/order/order_confirm_list/search/" + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderConfirmListConfirmAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021007";
  }

}
