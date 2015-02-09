package jp.co.sint.webshop.web.action.front.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CartAddCartAction extends CartBaseAction {

  @Override
  public void init() {
	  Cart cart = getCart();
	  if (cart.getItemCountExceptGift() > 0) {
	    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
      for (CartItem item : cart.get()) {
        Long CampaignAvailability = catalogSvc.campaignAvailability(item.getCommodityCode(), item.getShopCode(), null);
        if (CampaignAvailability == null || CampaignAvailability == 0L) {
          cart.remove(item.getShopCode(), item.getSkuCode(), item.getCommodityCode());
        } 
      }
	  }
  }

  
  
  // 2012/12/11 ob add end
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    Cart cart = getCart();
    if (cart.getItemCountExceptGift() == 0) {
      cart.clearAcceptedGift();
      addErrorMessage(WebMessage.get(CartDisplayMessage.NO_CART_ITEM));
    }

    String shopName = "";

    // 限界値チェック
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
    if (getConfig().getOperatingMode() == OperatingMode.MALL || getConfig().getOperatingMode() == OperatingMode.ONE) {
      SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
      Shop site = service.getSite();
      shopName = site.getShopName();

      BigDecimal totalCurrency = BigDecimal.ZERO;
      int totalQuantity = 0;

      for (CartItem item : cart.get()) {
        totalCurrency = totalCurrency.add(BigDecimalUtil.multiply(item.getRetailPrice().add(item.getGiftPrice()), item
            .getQuantity()));
        totalQuantity = totalQuantity + item.getQuantity();
      }
      ValidationResult resultCurrency = numberPolicy.validTotalCurrency(totalCurrency);
      if (resultCurrency.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultCurrency.getFormedMessage()));
      }
      ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(totalQuantity));
      if (resultAmount.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultAmount.getFormedMessage()));
      }
    } else if (getConfig().getOperatingMode() == OperatingMode.SHOP) {
      for (String shopCode : cart.getShopCodeList()) {
        BigDecimal totalCurrency = BigDecimal.ZERO;
        int totalQuantity = 0;
        String itemShopName = "";
        for (CartItem item : cart.get(shopCode)) {
          itemShopName = item.getShopName();
          totalCurrency = totalCurrency.add(BigDecimalUtil.multiply((item.getRetailPrice().add(item.getGiftPrice())), item
              .getQuantity()));
          totalQuantity = totalQuantity + item.getQuantity();
        }
        ValidationResult resultCurrency = numberPolicy.validTotalCurrency(totalCurrency);
        if (resultCurrency.isError()) {
          addErrorMessage(getLimitErrorMessageOfEachMode(itemShopName, resultCurrency.getFormedMessage()));
        }
        ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(totalQuantity));
        if (resultAmount.isError()) {
          addErrorMessage(getLimitErrorMessageOfEachMode(itemShopName, resultAmount.getFormedMessage()));
        }
      }
    }
    // 予約品限界値チェック
    List<CartItem> reserveList = cart.getReserve();
    for (CartItem item : reserveList) {
      if (getConfig().getOperatingMode() == OperatingMode.SHOP) {
        shopName = item.getShopName();
      }
      ValidationResult resultCurrency = numberPolicy.validTotalCurrency(BigDecimalUtil.multiply((item.getRetailPrice().add(item
          .getGiftPrice())), item.getQuantity()));
      if (resultCurrency.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultCurrency.getFormedMessage()));
      }
      ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(item.getQuantity()));
      if (resultAmount.isError()) {
        addErrorMessage(getLimitErrorMessageOfEachMode(shopName, resultAmount.getFormedMessage()));
      }
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
    String skuCode = getPathInfo(0);
    String shopCode = "00000000";
    
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    boolean existFlg = false;
    CommodityInfo info = service.getSkuInfo(shopCode, skuCode);
    if (info != null) {
      existFlg = true;
    }
    if (existFlg ) {
      Long quantity = 1L;
      CartBean nextBean = new CartBean();
      List<String> errorMessageList = new ArrayList<String>();

      int cartQuantity = quantity.intValue();
      CartItem cartItem = getCartItem(shopCode, skuCode);

      if (cartItem != null) {
        cartQuantity = cartItem.getQuantity() + quantity.intValue();
      }

      if (!getCart().set(shopCode, skuCode, "",cartQuantity, getLoginInfo().getCustomerCode(), false)) {
        errorMessageList.add(WebMessage.get(OrderErrorMessage.ADD_CART));
      }
      
      nextBean.setErrorMessageList(errorMessageList);
      setRequestBean(nextBean);

      setNextUrl("/app/cart/cart/init");
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

  private CartItem getCartItem(String shopCode, String skuCode) {
    CartItem cartItem = getCart().get(shopCode, skuCode);
    if (cartItem == null) {
      cartItem = getCart().getReserve(shopCode, skuCode);
    }

    return cartItem;
  }
  
}
