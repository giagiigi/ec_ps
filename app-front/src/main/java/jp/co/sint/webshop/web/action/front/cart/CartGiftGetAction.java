package jp.co.sint.webshop.web.action.front.cart;

import java.math.BigDecimal;
import java.util.Map;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author KS.
 */
public class CartGiftGetAction extends CartBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String shopCode = getPathInfo(0);
    String commodityCode = getPathInfo(1);
    String campaignCode = getPathInfo(2);
    String skuCode = getPathInfo(3);
    
    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode, campaignCode, skuCode)) {
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
    Cart cart = getCart();
    if (cart.getItemCountExceptGift() == 0) {
      addErrorMessage(WebMessage.get(CartDisplayMessage.NO_CART_ITEM));
    }
    
    if (couponCheck()) {
      cart.add(getPathInfo(0), getPathInfo(2) + "~" + getPathInfo(3), BigDecimal.ONE.intValue());
      
      setRequestBean(createBeanFromCart());

      setNextUrl("/cart/cart/init");
    } else {   
      setRequestBean(createBeanFromCart());
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

  private boolean couponCheck() {
    String shopCode = getPathInfo(0);
    String commodityCode = getPathInfo(1);
    String campaignCode = getPathInfo(2);
    String skuCode = getPathInfo(3);
    
    // 领取商品的活动有效性验证
    if (!checkGiftAcceptable(campaignCode, commodityCode)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.MULTIPLE_GIFT_ACCEPT_ERROR));
      return false;
    }
    
    // 同一活动同一商品只可领取一次
    if (getCart().get(shopCode, campaignCode + "~" + commodityCode) != null && getCart().get(shopCode, campaignCode + "~" + commodityCode).getQuantity() > 0) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.MULTIPLE_ACCEPT_ERROR));
      return false;
    }
    
    // 商品的有效在库数验证
    Map<String, Long> cartGiftTotalAmount = getGiftTotalAmountFromBean(); // 购物车中各赠品的数量信息
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    int giftAmount = 1;
    if (cartGiftTotalAmount.containsKey(commodityCode)) {
      giftAmount = new Long(cartGiftTotalAmount.get(commodityCode) + 1L).intValue();
    }
    if (!catalogService.isAvailableGift(shopCode, skuCode, giftAmount).equals(CommodityAvailability.AVAILABLE)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.MULTIPLE_GIFT_STOCK_ERROR));
      return false;
    }
    
    return true;
  }

}
