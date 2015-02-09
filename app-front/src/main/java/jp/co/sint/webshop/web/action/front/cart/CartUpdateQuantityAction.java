package jp.co.sint.webshop.web.action.front.cart;

import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CartUpdateQuantityAction extends CartBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    
    //session中赠品重置  20140725 hdh
    giftReset(getCart(), "00000000");
    
    
    Cart cart = getCart();
    if (cart.getItemCount() == 0) {
      addErrorMessage(WebMessage.get(CartDisplayMessage.NO_CART_ITEM));
    }
    copyBeanToCart();
    setRequestBean(createBeanFromCart());
    
    //赠品重置
    //giftReset(getCart(), "00000000");
//    if(getBean().getShopCartBean() !=null &&getBean().getShopCartBean().size()>0){
//      for(ShopCartBean shopBean:getBean().getShopCartBean()){
//        giftReset(shopBean);
//      }
//    }
    // 10.1.4 10028 追加 ここから
    setNextUrl("/cart/cart/init");
    // 10.1.4 10028 追加 ここまで
    return FrontActionResult.RESULT_SUCCESS;
  }
  
  // 10.1.6 10259 追加 ここから
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CartBean bean = getBean();
    
    // 数量変更アクション時にURLパラメータ不正があったか
//    if (bean.isUpdateQuantityActionError()) {
//      addErrorMessage(Messages.getString("web.action.front.cart.CartUpdateQuantityAction.0"));
//      return false;
//    }
    
    // 2012/11/27 促销对应 ob add start
//    for (ShopCartBean shopBean : bean.getShopCartBean()) {
//      for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
//        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
//          for (GiftBean gift : commodityBean.getGiftList()) {
//            gift.setGiftAmount(commodityBean.getCommodityAmount());
//            gift.setSubTotal(BigDecimalUtil.multiply(gift.getGiftSalesPrice(), NumUtil.toLong(commodityBean.getCommodityAmount(), 0L)));
//            gift.setSubTotalWeight(NumUtil.parseBigDecimalWithoutZero(BigDecimalUtil.multiply(gift.getWeight(), NumUtil.toLong(commodityBean.getCommodityAmount(), 0L))));
//          }
//        }
//      }
//      
//    }
    // 2012/11/27 促销对应 ob add end
    return super.validate();
  }
  // 10.1.6 10259 追加 ここまで

}
