package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.CartCommodityDetailListBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean.OrderCommodityBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020130:新規受注(配送先設定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderShippingRemoveCommodityAction extends NeworderShippingBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    DeliveryBean selectShipping = getSelectShipping();
    if (selectShipping == null) {
      return false;
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    DeliveryBean selectShipping = getSelectShipping();
    
    //2012-11-29 促销对应 ob add start
    String skuCode = getPathInfo(3);
    if (skuCode != null) {
      for (OrderCommodityBean gift : getBean().getOtherGiftList()) {
        if (gift.getSkuCode().equals(skuCode)) {
          return true;
        }
      }
    }
    //2012-11-29 促销对应 ob add end

    // 商品がなくなる削除は不可
    if (selectShipping.getOrderCommodityList().size() <= 1) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.DELETE_ALL_COMMODITY));
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
    NeworderShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();
    String[] tmp = getRequestParameter().getPathArgs();
    String addressNo = tmp[0];
    String shopCode =  tmp[1];
    String deliveryTypeCode =  tmp[2];
    String skuCode =  tmp[3];
    String multipleCampaignCode =  tmp[4];
    if (multipleCampaignCode.equals("true") || multipleCampaignCode.equals("false")){
      multipleCampaignCode = "";
    }
    String isDiscount = tmp[tmp.length-1];
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    //2012-11-29 促销对应 ob update start
    if (StringUtil.hasValue(multipleCampaignCode)) {
      for (OrderCommodityBean gift : bean.getOtherGiftList()) {
        if (gift.getSkuCode().equals(skuCode) && gift.getMultipleCampaignCode().equals(multipleCampaignCode)) {
          cashier.removeOtherGift(gift.getMultipleCampaignCode(), skuCode);
        }
      }
    } else {
      cashier.removeCashierItem(shopCode, NumUtil.toLong(deliveryTypeCode), NumUtil.toLong(addressNo), skuCode ,isDiscount);
      recreateOtherGiftList();
    }

    for (DeliveryBean delvery : bean.getDeliveryList()) {
      for (OrderCommodityBean orderCommodity : delvery.getOrderCommodityList()) {
        if (orderCommodity.getSkuCode().equals(skuCode) && StringUtil.hasValue(orderCommodity.getDiscountCouponCode())) {
          String discountCouponCode = orderCommodity.getDiscountCouponCode();
          Map<String, Long> discountCouponUsedMap = getBean().getDiscountCouponUsedMap();
          Long count = 0L;
          if (!discountCouponUsedMap.isEmpty()) {
            if (discountCouponUsedMap.containsKey(discountCouponCode)) {
              count = discountCouponUsedMap.get(discountCouponCode);
              discountCouponUsedMap.remove(discountCouponCode);
              if (count > 1) {
                discountCouponUsedMap.put(discountCouponCode, --count);
              }
            }
          }
        }
      }
    }
    
    //2012-11-29 促销对应 ob update end
    
    cashier.recomputeShippingCharge();
    isCashOnDeliveryOnly();
    
    //2012-11-23 促销对应 ob update start
    NeworderShippingBean newBean = createBeanFromCashier();
    
    super.createCashierFromDisplay();
    
    setRequestBean(newBean);
    //2012-11-23 促销对应 ob update end

    return BackActionResult.RESULT_SUCCESS;
  }

  private DeliveryBean getSelectShipping() {
    String addressNo = getPathInfo(0);
    String shopCode = getPathInfo(1);
    String deliveryTypeCode = getPathInfo(2);
    for (DeliveryBean shipping : getBean().getDeliveryList()) {
      if (shipping.getShopCode().equals(shopCode) && shipping.getDeliveryCode().equals(deliveryTypeCode)
          && NumUtil.toString(shipping.getAddressNo()).equals(addressNo)) {
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

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderShippingRemoveCommodityAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013006";
  }

}
