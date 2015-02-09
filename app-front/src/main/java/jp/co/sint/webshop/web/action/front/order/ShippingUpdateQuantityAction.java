package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShippingUpdateQuantityAction extends ShippingChangeAddressAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;

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

    // 在庫・予約上限数チェック
    valid = shippingValidation();
    if (valid) {
      try {
        copyBeanToCashier();
      } catch (Exception e) {
        valid = false;
      } finally {
        if (!valid) {
          return false;
        }
      }
    }
    valid &= numberLimitValidation();

    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    isCashOnDeliveryOnly();

    setRequestBean(createShippingBeanFromCashier());
    recastCart();
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
