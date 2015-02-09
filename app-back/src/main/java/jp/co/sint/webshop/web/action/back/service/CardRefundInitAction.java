package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.service.CardRefundBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class CardRefundInitAction extends CardRefundBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.MEMBER_INFO_REFUND);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(getOrderNo())) {
      String[] tmpArgs = getRequestParameter().getPathArgs();
      if (tmpArgs.length > 1) {
        if (WebConstantCode.COMPLETE_REGISTER.equals(tmpArgs[1])) {
          addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "礼品卡退款"));
        } else if (WebConstantCode.COMPLETE_DELETE.equals(tmpArgs[1])) {
          addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "礼品卡退款"));
        } else {
          addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
          return false;
        }
      }

      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CardRefundBean bean = new CardRefundBean();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderContainer orderContainer = service.getOrder(getOrderNo());

    if (orderContainer == null || orderContainer.getOrderHeader() == null) {
      setNextUrl("/app/service/member_info/init");
      return BackActionResult.RESULT_SUCCESS;
    }

    setDbToBean(orderContainer, bean);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 订单号取得。<BR>
   * 
   * @return customerCode
   */
  private String getOrderNo() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.CardRefundInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109051001";
  }
}
