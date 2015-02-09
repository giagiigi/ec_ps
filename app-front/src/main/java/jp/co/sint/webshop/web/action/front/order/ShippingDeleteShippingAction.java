package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShippingDeleteShippingAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String addressNo = getPathInfo(0);
    if (!StringUtil.hasValue(addressNo)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.WRONG_URL_PARAMETER));
      return false;
    }
    ShippingHeaderBean selectShipping = getSelectShipping();
    if (selectShipping == null) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.WRONG_URL_PARAMETER));
      return false;
    }
    // 配送先が0件になる削除は不可
    if (getBean().getShippingList().size() <= 1) {
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
    ShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();

    ShippingHeaderBean deleteBean = getSelectShipping();
    for (ShippingDetailBean detail : deleteBean.getShippingList()) {
      cashier.removeShipping(detail.getShopCode(), NumUtil.toLong(detail.getDeliveryTypeCode()), deleteBean.getAddress()
          .getAddressNo());
    }

    isCashOnDeliveryOnly();
    setRequestBean(createShippingBeanFromCashier());

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 配送先情報を取得します。
   * 
   * @return shipping
   */
  public ShippingHeaderBean getSelectShipping() {
    String addressNo = getPathInfo(0);
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      if (NumUtil.toString(shipping.getAddress().getAddressNo()).equals(addressNo)) {
        return shipping;
      }
    }
    return null;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

}
