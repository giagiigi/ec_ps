package jp.co.sint.webshop.web.action.front.cart;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CartRemoveAction extends CartBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String shopCode = getPathInfo(0);
    String skuCode = getPathInfo(1);
    String giftCode = getPathInfo(2);
    getBean().setShopCode(shopCode);
    getBean().setSkuCode(skuCode);
    getBean().setGiftCode(giftCode);
    
    // 2012/11/23 促销对应 ob update start
    // return validateBean(getBean());
    boolean result = validateBean(getBean());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    
    if (result) {
      if (skuCode.contains(":")) {
        skuCode = skuCode.split(":")[0];
      }

      CommodityInfo commodityInfo = catalogService.getSkuInfo(shopCode, skuCode);
      if (commodityInfo == null || commodityInfo.getHeader() == null || CommodityType.GIFT.longValue().equals(commodityInfo.getHeader().getCommodityType())) {
        throw new URLNotFoundException();
      }
    }
    // 2012/11/23 促销对应 ob update end
    return result;
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
    // 20111214 shen add start
    String shopCode = getPathInfo(0);
    String skuCode = getPathInfo(1);
    String isDiscount = getPathInfo(3);
    int index1 = -1;
    int index2 = -1;
    for (ShopCartBean shopBean : bean.getShopCartBean()) {
      for (int i = 0; i < shopBean.getCommodityCartBean().size(); i++) {
        if(shopBean.getCommodityCartBean().get(i).getSkuCode().equals(skuCode) 
            && shopBean.getCommodityCartBean().get(i).getIsDiscountCommodity().equals(isDiscount)){
          if (isDiscount.equals("true")){
            index1 = i;
            if (i != shopBean.getCommodityCartBean().size()-1){
              if(shopBean.getCommodityCartBean().get(i).getSkuCode().equals(shopBean.getCommodityCartBean().get(i+1).getSkuCode())){
                index2 = i+1;
              }
            }
          } else {
            index2 = i;
            if (i != 0){
              if(shopBean.getCommodityCartBean().get(i).getSkuCode().equals(shopBean.getCommodityCartBean().get(i-1).getSkuCode())){
                index1 = i-1;
              }
            }
          }
        }
      }
    }
    if (index1 != -1 && index2 != -1){
      int discountAmout1 =Integer.parseInt(bean.getShopCartBean().get(0).getCommodityCartBean().get(index1).getCommodityAmount()); 
      int discountAmout2 =Integer.parseInt(bean.getShopCartBean().get(0).getCommodityCartBean().get(index2).getCommodityAmount());
      if (isDiscount.equals("true")){
        cart.updateQuantity(shopCode, skuCode, StringUtil.isNullOrEmpty(bean.getShopCartBean().get(0)
            .getReserveSkuCode()), discountAmout2, getLoginInfo().getCustomerCode());
      } else {
        cart.updateQuantity(shopCode, skuCode, StringUtil.isNullOrEmpty(bean.getShopCartBean().get(0)
            .getReserveSkuCode()), discountAmout1, getLoginInfo().getCustomerCode());
      }
    } else {
      cart.remove(bean.getShopCode(), bean.getSkuCode(), bean.getGiftCode(), getLoginInfo().getCustomerCode());
    }

    // 20111214 shen add end
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
