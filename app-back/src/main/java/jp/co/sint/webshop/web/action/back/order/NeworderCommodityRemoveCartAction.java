package jp.co.sint.webshop.web.action.back.order;

import java.util.List;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.CartCommodityDetailListBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020110:新規受注(商品選択)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityRemoveCartAction extends NeworderCommodityBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    String shopCode = getPathInfo(0);
    String skuCode = getPathInfo(1);
    auth &= StringUtil.hasValue(shopCode) && StringUtil.hasValue(skuCode);
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
    String shopCode = getPathInfo(0);
    String skuCode = getPathInfo(1);
    String giftCode = getPathInfo(2);
    String isDiscount = getPathInfo(3);
    if (giftCode.equals("false") || giftCode.equals("true")){
      isDiscount = giftCode;
    }
    List<CartCommodityDetailListBean> cartList = getBean().getCartCommodityList();
    int index1 = -1;
    int index2 = -1;
      for (int i = 0 ;i < cartList.size(); i++) {
        if(cartList.get(i).getSkuCode().equals(skuCode) 
            && cartList.get(i).getIsDiscountCommodity().equals(isDiscount)){
          if (isDiscount.equals("true")){
            index1 = i;
            if (i != cartList.size()-1){
              if(cartList.get(i).getSkuCode().equals(cartList.get(i+1).getSkuCode())){
                index2 = i+1;
              }
            }
          } else {
            index2 = i;
            if (i != 0){
              if(cartList.get(i).getSkuCode().equals(cartList.get(i-1).getSkuCode())){
                index1 = i-1;
              }
            }
          }
        }
      }
    if (index1 != -1 && index2 != -1){
      if (StringUtil.isNullOrEmpty(cartList.get(index1).getPurchasingAmount())){
        cartList.get(index1).setPurchasingAmount("0");
      }
      int discountAmout1 =Integer.parseInt(cartList.get(index1).getPurchasingAmount()); 
      if (StringUtil.isNullOrEmpty(cartList.get(index2).getPurchasingAmount())){
        cartList.get(index2).setPurchasingAmount("0");
      }
      int discountAmout2 =Integer.parseInt(cartList.get(index2).getPurchasingAmount());
      if (isDiscount.equals("true")){
        getCart().updateQuantity(shopCode, skuCode, true, discountAmout2);
      } else {
        getCart().updateQuantity(shopCode, skuCode, true, discountAmout1);
      }
    } else {
      getCart().remove(shopCode, skuCode, giftCode);
    }

    getBean().setCartCommodityList(createBeanFromCart());
    // 2012/11/22 促销对应 新建订单_商品选择  ob add start
    // 把购物车的可领取赠品显示到画面购物车list中
    getBean().setCartOtherGiftCommodityList(createBeanFromCartOfGift());
    // 设置可领取商品List
    multipleGiftProcess(getBean(), false, true);
    // 2012/11/22 促销对应 新建订单_商品选择  ob add end
    setRequestBean(getBean());

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
    return Messages.getString("web.action.back.order.NeworderCommodityRemoveCartAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102011006";
  }

}
