package jp.co.sint.webshop.web.action.front.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.impl.CommonLogic;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.cart.CartMsgBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author Kousen.
 */
public class CartMsgInitAction extends WebFrontAction<CartMsgBean> {

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
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CartMsgBean nextBean = getBean();
    if (nextBean == null) {
      nextBean = new CartMsgBean();
    }
    
    Cart cart = getCart();
    if (cart == null) {
      nextBean = new CartMsgBean();
      setRequestBean(nextBean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    
    String shopCode = DIContainer.getWebshopConfig().getSiteShopCode();
    //获取控制提示语句显示的阀值
    int aboveValue = DIContainer.getWebshopConfig().getAboveCartTip();
    
    BigDecimal claimTotal = BigDecimal.ZERO;
    BigDecimal weightTotal = BigDecimal.ZERO;
    for (CartItem item : cart.get()) {
      //claimTotal = claimTotal.add(BigDecimalUtil.multiply(item.getRetailPrice(), item.getQuantity()));
      weightTotal = weightTotal.add(BigDecimalUtil.multiply(item.getWeight(), item.getQuantity()));
      // 2012/12/06 促销对应 ob add start
      if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
        for (GiftItem gift : item.getCommodityInfo().getGiftList()) {
          claimTotal = claimTotal.add(BigDecimalUtil.multiply(gift.getRetailPrice(), gift.getQuantity()));
          weightTotal = weightTotal.add(BigDecimalUtil.multiply(gift.getWeight(), gift.getQuantity()));
        }
      }
      // 限时限购
      DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(item.getCommodityCode());
      int totalNum = item.getQuantity();
      boolean havedTimeDisccountFlg = false;  //是否存在限时限购
      if (dc != null ){
        // 20140430 hdh add start
        // 限时限量折扣
        //历史所有客户购买总数
        Long siteTotalAmount = 0L;
        siteTotalAmount= service.getHistoryBuyAmountTotal(item.getCommodityCode());
        if (siteTotalAmount == null){
            siteTotalAmount = 0L;
        }
        
        Long historyNum = 0L;
        String customerCode = getLoginInfo().getCustomerCode();
        if (StringUtil.hasValue(customerCode)) {
          //当前登录客户历史购买数量
          historyNum = service.getHistoryBuyAmount(item.getCommodityCode(), customerCode);
          if (historyNum == null){
            historyNum = 0L;
          }
        }
        if ( dc.getSiteMaxTotalNum() > siteTotalAmount){
          Long curQuantity = 0L;
          //限购商品剩余可购买数量
          Long avalibleAmount = dc.getSiteMaxTotalNum() - siteTotalAmount;
          if(avalibleAmount < 0L ){
            avalibleAmount = 0L;
          }
          if (dc.getCustomerMaxTotalNum()!=null){
            //当前客户可购买限购商品数
            curQuantity = dc.getCustomerMaxTotalNum() - historyNum;
            if(curQuantity < 0L ){
              curQuantity = 0L;
            }
            if(curQuantity > avalibleAmount){
              curQuantity = avalibleAmount;
            }
          }
          if (totalNum <= curQuantity){
            claimTotal = claimTotal.add(BigDecimalUtil.multiply(dc.getDiscountPrice(),totalNum));
            havedTimeDisccountFlg = true;
          } else {
                if (curQuantity != 0L) {
                  claimTotal = claimTotal.add(BigDecimalUtil.multiply(dc.getDiscountPrice(), curQuantity.intValue()));
                }
                claimTotal = claimTotal.add(BigDecimalUtil.multiply(item.getRetailPrice(), totalNum-curQuantity.intValue()));
                havedTimeDisccountFlg = true;
          }
        } 
        
      }
      if(!havedTimeDisccountFlg){
        claimTotal = claimTotal.add(BigDecimalUtil.multiply(item.getRetailPrice(), item.getQuantity()));
      }
      claimTotal = claimTotal.subtract(cart.getOptionalCheapPrice());
      // 2012/12/06 促销对应 ob add end
    }

    // IP地址获取省份
    if (StringUtil.isNullOrEmpty(nextBean.getSelPrefectureCode())) {
      if (StringUtil.hasValue(cart.getPrefectureCode())) {
        nextBean.setSelPrefectureCode(cart.getPrefectureCode());
      } else {
        // String prefectureCode =
        // CommonLogic.getPrefectureCodeByIPAddress(getSessionContainer().getIpAddress());
        // if (StringUtil.isNullOrEmpty(prefectureCode)) {
        // // 默认上海
        // prefectureCode = "09";
        // }
        // nextBean.setSelPrefectureCode(prefectureCode);
        nextBean.setSelPrefectureCode("09");
      }
    }
    cart.setPrefectureCode(nextBean.getSelPrefectureCode());

    ShopManagementService shop = ServiceLocator.getShopManagementService(getLoginInfo());
    // 运费关联信息
    DeliveryRegionCharge deliveryRegionCharge = shop.getDeliveryRegionCharge(shopCode, nextBean.getSelPrefectureCode(), "D000");
    if (deliveryRegionCharge != null) {
      BigDecimal freePrice = deliveryRegionCharge.getFreeOrderAmount();
      BigDecimal freeWeight = deliveryRegionCharge.getFreeWeight();

      // add by lc 2012-10-25 start for 根据当前购物车商品情况，查看所选地址是否免邮
      BigDecimal shippingCharge = BigDecimal.ZERO;
      if (!isFreeShippingCharge(cart)) {
        shippingCharge = shop.getShippingCharge(nextBean.getSelPrefectureCode(), claimTotal, weightTotal, "D000");
      }
      // add by lc 2012-10-25 end
      
      nextBean.setShippingCharge(shippingCharge);
      // 提示免运费语句的前提条件
      // 不免运费，免运费重量不能为0，并且免运费和商品总价的差值不能大于规定值
      if (BigDecimalUtil.isAbove(shippingCharge, BigDecimal.ZERO) && !BigDecimalUtil.equals(freeWeight, BigDecimal.ZERO)
          && BigDecimalUtil.isAbove(BigDecimal.valueOf(aboveValue), BigDecimalUtil.subtract(freePrice, claimTotal))) {
          int count = 1;
          BigDecimal tipWeight = BigDecimal.ZERO;
          BigDecimal tipDiscountPrice = BigDecimal.ZERO;
          // case1,免运费价格大于商品总价，且商品总重量大于免运费重量
          if ( BigDecimalUtil.isAbove(freePrice, claimTotal) && BigDecimalUtil.isBelowOrEquals(freeWeight, weightTotal)){
            while(BigDecimalUtil.isAbove(weightTotal, freeWeight)){
              freeWeight = freeWeight.add(freeWeight);
              count++;
            }
            tipWeight = BigDecimalUtil.subtract(freeWeight, weightTotal);
            tipDiscountPrice = BigDecimalUtil.subtract( BigDecimalUtil.multiply(freePrice, count), claimTotal);
            //免运费价格大于商品总价，且免运费重量大于商品中重量
          } else if (BigDecimalUtil.isAbove(freePrice, claimTotal) && BigDecimalUtil.isAbove(freeWeight, weightTotal)) {
            tipWeight = BigDecimalUtil.subtract(freeWeight, weightTotal);
            tipDiscountPrice = BigDecimalUtil.subtract( freePrice, claimTotal);
         // case2,商品总价大于免运费价格，且商品总重量大于免运费重量
          }else if (BigDecimalUtil.isBelowOrEquals(freePrice, claimTotal) && BigDecimalUtil.isBelowOrEquals(freeWeight, weightTotal)) {
            /*if (BigDecimalUtil.equals(freeWeight, weightTotal) && BigDecimalUtil.equals(freePrice, claimTotal)){
              tipDiscountPrice = BigDecimal.ONE;
            }else{*/
              while(BigDecimalUtil.isAbove(weightTotal, freeWeight) && BigDecimalUtil.isAbove(claimTotal, freePrice)){
                freeWeight = freeWeight.add(freeWeight);
                freePrice = freePrice.add(freePrice);
                count++;
              }
              tipWeight = BigDecimalUtil.subtract( freeWeight, weightTotal);
              tipDiscountPrice = BigDecimalUtil.subtract( freePrice, claimTotal);
//            }
           // case3,商品总价大于免运费价格，且免运费重量大于商品总重量
          }else if(BigDecimalUtil.isBelowOrEquals(freePrice, claimTotal) && BigDecimalUtil.isAbove(freeWeight, weightTotal)) {
            while(BigDecimalUtil.isAbove(claimTotal, freePrice)){
              freePrice = freePrice.add(freePrice);
              count++;
            }
            tipDiscountPrice = BigDecimalUtil.subtract(freePrice, claimTotal);
            tipWeight = BigDecimalUtil.subtract( BigDecimalUtil.multiply(freeWeight, count), weightTotal);
          }else{
            tipWeight = null;
            tipDiscountPrice = null;
          }
          
          if (tipDiscountPrice != null && BigDecimalUtil.isAbove(BigDecimal.valueOf(aboveValue), tipDiscountPrice)
              && BigDecimalUtil.isAbove(tipWeight,BigDecimal.ZERO) && BigDecimalUtil.isAbove(tipDiscountPrice,BigDecimal.ZERO)) {
            nextBean.setCartPriceForCharge(tipDiscountPrice);
            nextBean.setCartWeightForCharge(NumUtil.parseBigDecimalWithoutZero(tipWeight));
          }else{
            nextBean.setCartPriceForCharge(null);
            nextBean.setCartWeightForCharge(null);
          }
          
      }else{
        nextBean.setCartPriceForCharge(null);
        nextBean.setCartWeightForCharge(null);
      }
      /*
      if (BigDecimalUtil.isAbove(shippingCharge, BigDecimal.ZERO) && BigDecimalUtil.isAbove(freeWeight, BigDecimal.ZERO)) {
        // 不到免运费金额
        if (BigDecimalUtil.isAbove(freePrice, claimTotal)) {
          //不到免运费金额，并且差值小于显示提示语句的阀值
          if (BigDecimalUtil.isAbove(BigDecimal.valueOf(aboveValue), BigDecimalUtil.subtract(freePrice, claimTotal))) {
            nextBean.setCartPriceForCharge(BigDecimalUtil.subtract(freePrice, claimTotal));
            nextBean.setCartWeightForCharge(freeWeight);
          } else {
            nextBean.setCartPriceForCharge(null);
            nextBean.setCartWeightForCharge(null);
          }
          // 等于免运费金额
        } else if (BigDecimalUtil.equals(freePrice, claimTotal)) {
          nextBean.setCartPriceForCharge(BigDecimal.ONE);
          nextBean.setCartWeightForCharge(freeWeight);
        } else {
          // 超过免运费金额
          if (BigDecimalUtil.isAbove(freePrice, BigDecimal.ZERO)) {
            BigDecimal proportion = BigDecimalUtil.divide(claimTotal, freePrice, 0, RoundingMode.UP);
            nextBean.setCartPriceForCharge(BigDecimalUtil.subtract(BigDecimalUtil.multiply(freePrice, proportion), claimTotal));
            nextBean.setCartWeightForCharge(BigDecimalUtil.multiply(freeWeight, proportion));
          } else {
            nextBean.setCartPriceForCharge(null);
            nextBean.setCartWeightForCharge(freeWeight);
          }
        }
      } else {
        nextBean.setCartPriceForCharge(null);
        nextBean.setCartWeightForCharge(null);
      }*/
    }
 // 金券关联信息
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();
    CommunicationService communication = ServiceLocator.getCommunicationService(getLoginInfo());
    NewCouponRule newCouponRule = communication.getNewCouponRule(CouponType.PURCHASE_DISTRIBUTION.getValue());
    if (newCouponRule != null && BigDecimalUtil.isAbove(newCouponRule.getMinIssueOrderAmount(), claimTotal) && StringUtil.hasValue(customerCode)) {
      Long orderCount = 0L;
      if (newCouponRule.getApplicableObjects() == 1L && StringUtil.hasValue(customerCode)) {
        // 判断用户是否是初次购买
        orderCount = orderService.countUsedCouponFirstOrder(customerCode);
      }
      if (orderCount >= 1) {
        nextBean.setCartPriceForCoupon(null);
        nextBean.setCouponPrice(null);
        nextBean.setCouponProportion(null);
      } else {
        nextBean.setCartPriceForCoupon(BigDecimalUtil.subtract(newCouponRule.getMinIssueOrderAmount(), claimTotal));
        if (newCouponRule.getCouponIssueType().equals(CouponIssueType.FIXED.longValue())) {
          nextBean.setCouponPrice(newCouponRule.getCouponAmount());
          nextBean.setCouponProportion(null);
        } else {
          nextBean.setCouponPrice(null);
          nextBean.setCouponProportion(newCouponRule.getCouponProportion());
        }
      }
    } else {
      nextBean.setCartPriceForCoupon(null);
      nextBean.setCouponPrice(null);
      nextBean.setCouponProportion(null);
    }

    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.setPrefectureList(s.createPrefectureList(false));

    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 根据Cart查看本次发货是否是免邮费
   * @param bean
   * @return
   */
  private boolean isFreeShippingCharge(Cart bean){
    boolean result = false;
    
    CampainFilter cf = new CampainFilter();
    CampaignInfo campaignI = new CampaignInfo();
    campaignI.setPrefectureCode(bean.getPrefectureCode());
    List<OrderDetail> commodityList = new ArrayList<OrderDetail>();
    
    for (CartItem detail : bean.get()) {
      // 2012/12/07 促销对应 ob add start
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
      // 2012/12/07 促销对应 ob add end  
        OrderDetail orderD = new OrderDetail();
        orderD.setCommodityCode(detail.getCommodityCode());
        orderD.setPurchasingAmount(NumUtil.toLong(detail.getQuantity() + ""));
        commodityList.add(orderD);
      // 2012/12/07 促销对应 ob add start
      }
      // 2012/12/07 促销对应 ob add end
    }
    campaignI.setCommodityList(commodityList);
    result = cf.isFreeShippingCharge(campaignI);
    return result;
  }
}
