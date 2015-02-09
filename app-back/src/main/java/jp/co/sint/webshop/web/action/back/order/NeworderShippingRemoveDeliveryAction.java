package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020130:新規受注(配送先設定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderShippingRemoveDeliveryAction extends NeworderShippingBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    String addressNo = getPathInfo(0);
    String shopCode = getPathInfo(1);
    String deliveryTypeCode = getPathInfo(2);
    auth &= StringUtil.hasValue(addressNo);
    auth &= StringUtil.hasValue(shopCode);
    auth &= StringUtil.hasValue(deliveryTypeCode);

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 配送先が0件になる削除は不可
    if (getBean().getDeliveryList().size() <= 1) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.DELETE_ALL_DELIVERY));
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
    NeworderShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();

    String addressNo = getPathInfo(0);
    String shopCode = getPathInfo(1);
    String deliveryTypeCode = getPathInfo(2);

    cashier.removeShipping(shopCode, NumUtil.toLong(deliveryTypeCode), NumUtil.toLong(addressNo));

    isCashOnDeliveryOnly();
    setRequestBean(createBeanFromCashier());

    return BackActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderShippingRemoveDeliveryAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013007";
  }

}
