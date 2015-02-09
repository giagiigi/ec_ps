package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.UIBean;
import jp.co.sint.webshop.web.bean.front.order.GuestBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;

/**
 * U2020140:注文内容確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ConfirmMoveAction extends ConfirmBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // URL不正チェック
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length < 1) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
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
    String move = getPathInfo(0);
    // 20111220 shen update start
    // if (move.equals("payment")) {
    if (move.equals("shipping")) {
      // // PaymentBeanに対してcasheirを設定する
      // PaymentBean paymentBean = new PaymentBean();
      // paymentBean.setCashier(getBean().getCashier());
      // setRequestBean(paymentBean);
      // setNextUrl("/app/order/payment");

      // 20120109 shen add start
      // 清空手续费
      getBean().getCashier().getPayment().getSelectPayment().setPaymentCommission(BigDecimal.ZERO);
      getBean().getCashier().getShippingList().get(0).setDeliveryDateCommssion(BigDecimal.ZERO);
      // 20120109 shen add end
      
      // ShippingBeanに対してcasheirを設定する
      ShippingBean bean = new ShippingBean();
      bean.setCashier(getBean().getCashier());
      setRequestBean(bean);
      setNextUrl("/app/order/shipping");
      // 20111220 shen update end
    } else if (move.equals("guest")) {
      if (getLoginInfo().isGuest()) {
        UIBean tmpBean = getSessionContainer().getTempBean();
        GuestBean bean = null;
        if (tmpBean instanceof GuestBean) {
          bean = (GuestBean) tmpBean;
        } else {
          bean = new GuestBean();
        }
        bean.setCashier(getBean().getCashier());
        getSessionContainer().setTempBean(bean);
        setNextUrl("/app/order/guest/init/" + getBean().getCashier().getPaymentShopCode() + "/"
            + bean.getSelectReserveSkuSet().getShopCode() + "/" + bean.getSelectReserveSkuSet().getSkuCode());
      }
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }
}
