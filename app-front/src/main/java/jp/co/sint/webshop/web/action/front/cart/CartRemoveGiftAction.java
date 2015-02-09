package jp.co.sint.webshop.web.action.front.cart;

import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author OB
 */
public class CartRemoveGiftAction extends CartBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String shopCode = getPathInfo(0);
    String skuCode = getPathInfo(1);
    String campaignCode = getPathInfo(2);
    getBean().setShopCode(shopCode);
    getBean().setSkuCode(skuCode);
    getBean().setCampaignCode(campaignCode);
    if (StringUtil.isNullOrEmptyAnyOf(shopCode, skuCode, campaignCode)) {
      throw new URLNotFoundException();
    }
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Cart cart = getCart();
    CartBean bean = getBean();

    cart.remove(bean.getShopCode(), bean.getCampaignCode() + "~" + bean.getSkuCode(), "", getLoginInfo().getCustomerCode());

    setRequestBean(createBeanFromCart());
    
    setNextUrl("/cart/cart/init");
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
