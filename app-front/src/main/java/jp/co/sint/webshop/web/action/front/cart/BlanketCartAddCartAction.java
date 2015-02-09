package jp.co.sint.webshop.web.action.front.cart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.bean.front.cart.BlanketCartBean.BlanketCartDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020310:まとめてカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BlanketCartAddCartAction extends BlanketCartBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // まとめてカートを使用しない場合は404画面へ
    super.validate();

    boolean valid = true;
    String shopCode = null;
    String skuCode = null;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    // 10.1.6 10259 追加

    boolean isEmpty = true;
    for (BlanketCartDetailBean detail : getBean().getDetailList()) {
      if (detail == null) {
        continue;
      } else {
        isEmpty &= false;
      }

      // 10.1.4 10177 修正 ここから
      // if (!validateBlanketCartDetailBean(detail)) {
      if (!validateBlanketCartDetailBean(detail, true)) {
        // 10.1.4 10177 修正 ここまで
        valid &= false;
        continue;
      }

      if (StringUtil.hasValueAllOf(detail.getShopCode(), detail.getSkuCode(), detail.getAmount())) {
        shopCode = detail.getShopCode();
        skuCode = detail.getSkuCode();
        int quantity = Integer.parseInt(detail.getAmount());
        CartItem cartItem = getCartItem(shopCode, skuCode);
        if (cartItem != null) {
          quantity = cartItem.getQuantity() + quantity;
        }

        boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);
        CommodityAvailability commodityAvailability = catalogService.isAvailable(shopCode, skuCode, quantity, isReserve);
        String blanketNo = MessageFormat.format(Messages.getString("web.action.front.cart.BlanketCartAddCartAction.0"), detail
            .getNo());
        // 10.1.6 10259 追加 ここから
        Shop shop = shopService.getShop(shopCode);
        if (shop == null) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, blanketNo));
          valid &= false;
          continue;
        }
        // 10.1.6 10259 追加 ここまで
        if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, blanketNo));
          valid &= false;
        //  clearSkuInfo(detail); // 10.1.5 10211 追加
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, blanketNo));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, blanketNo));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, blanketNo, String.valueOf(catalogService
              .getAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_RESERVATION_OVER, blanketNo, NumUtil.toString(catalogService
              .getReservationAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, blanketNo));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
          valid &= true;
        }
      }
    }
    if (isEmpty) {
      addErrorMessage(WebMessage.get(CartDisplayMessage.NO_ITEM_ADDED));
      valid &= false;
    }
    getBean().setFirstDisplay(false);
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Cart cart = getCart();

    CartBean nextBean = new CartBean();
    List<String> errorMessageList = new ArrayList<String>();

    for (BlanketCartDetailBean detail : getBean().getDetailList()) {
      if (detail == null) {
        continue;
      }
      int cartQuantity = NumUtil.toLong(detail.getAmount()).intValue();
      CartItem cartItem = getCartItem(detail.getShopCode(), detail.getSkuCode());

      if (cartItem != null) {
        cartQuantity += cartItem.getQuantity();
      }
      // 20111214 shen update start
      // boolean success = cart.set(detail.getShopCode(), detail.getSkuCode(), cartQuantity);
      boolean success = cart.set(detail.getShopCode(), detail.getSkuCode(), cartQuantity, getLoginInfo().getCustomerCode());
      // 20111214 shen update end
      if (success) {
        CartItem item = cart.get(detail.getShopCode(), detail.getSkuCode());
        if (item != null) {
          detail.setCommodityName(item.getCommodityName());
          detail.setStandardDetail1Name(item.getStandardDetail1Name());
          detail.setStandardDetail2Name(item.getStandardDetail2Name());
        }
      } else {
        errorMessageList.add(WebMessage.get(OrderErrorMessage.ADD_CART)
            + MessageFormat.format(Messages.getString("web.action.front.cart.BlanketCartAddCartAction.1"), detail.getSkuCode()));
      }
    }

    nextBean.setErrorMessageList(errorMessageList);
    setRequestBean(nextBean);

    setNextUrl("/app/cart/cart");

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * カート内の商品情報を取得
   * 
   * @param shopCode
   * @param skuCode
   * @return CartItem
   */
  private CartItem getCartItem(String shopCode, String skuCode) {
    CartItem cartItem = getCart().get(shopCode, skuCode);
    if (cartItem == null) {
      cartItem = getCart().getReserve(shopCode, skuCode);
    }

    return cartItem;
  }
}
