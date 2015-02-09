package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderConfirmBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020160:新規受注(確認)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderConfirmMoveAction extends NeworderBaseAction<NeworderConfirmBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    auth &= StringUtil.hasValue(getPathInfo(0));
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
    //String move = getPathInfo(0);
    //soukai update 2012/01/07 ob start
    /*if (move.equals("payment")) {
      // PaymentBeanに対してcasheirを設定する
      NeworderPaymentBean paymentBean = new NeworderPaymentBean();
      paymentBean.setCashier(getBean().getCashier());
      setRequestBean(paymentBean);

      setNextUrl("/app/order/neworder_payment");
    } else if (move.equals("shipping")) {
      // ShippingBeanに対してcasheirを設定する
      NeworderShippingBean shippingBean = new NeworderShippingBean();
      shippingBean.setCashier(getBean().getCashier());
      setRequestBean(shippingBean);

      setNextUrl("/app/order/neworder_shipping");
    }*/
    NeworderShippingBean shippingBean = new NeworderShippingBean();
    shippingBean.setCashier(getBean().getCashier());
    setRequestBean(shippingBean);

    setNextUrl("/app/order/neworder_shipping");
    //soukai update 2012/01/07 ob end
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
    return Messages.getString("web.action.back.order.NeworderConfirmMoveAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102016002";
  }

}
