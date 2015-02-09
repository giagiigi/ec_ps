package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.Coupon;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author OB.
 */
public class ShippingCouponConfirmAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String skuCode = getPathInfo(0);
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    CommunicationService comService = ServiceLocator.getCommunicationService(getLoginInfo());
    
    getBean().setSkuCodeSeleted(skuCode);
    if (StringUtil.isNullOrEmptyAnyOf(skuCode)) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    
    // 当前已使用媒体礼品券时
    if (getBean().getCashier().getDiscount() != null && StringUtil.hasValue(getBean().getCashier().getDiscount().getDiscountCode())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_AND_DISCOUNT_DUPLICATE_ERROR));
      return false;
    }

    // 折扣券编号的合法性check
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detailBean : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detailBean.getCommodityList()) {

          if (getBean().getSkuCodeSeleted().equals(commodity.getSkuCode())) {
            String inputCouponCode = this.getRequestParameter().get("usedCouponCode_" + commodity.getSkuCode());
            List<CampaignCondition> conditionList = comService.getCampaignConditionByCouponCode(inputCouponCode);
            
            if (conditionList != null && conditionList.size() > 0) {
              if (StringUtil.isNullOrEmpty(conditionList.get(0).getAttributrValue().replace(",", "").trim())) {
                commodity.setDisplayCouponMode("3"); // 使用折扣券出错
                addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_DETAIL_ERROR, inputCouponCode));
                return false;
              }
              
              if (conditionList.get(0).getMaxCommodityNum() != null && NumUtil.toLong(commodity.getCommodityAmount(), 0L) < conditionList.get(0).getMaxCommodityNum()) {
                commodity.setDisplayCouponMode("3"); // 使用折扣券出错
                addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_USE_LIMIT_ERROR, inputCouponCode, conditionList.get(0).getMaxCommodityNum().toString()));
                return false;
              }

            } else {
              commodity.setDisplayCouponMode("3"); // 使用折扣券出错
              addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_ERROR));
              return false;
            }

            // 取得本次使用的回数
            Long useCount = getCouponUseCountFromBean(inputCouponCode) + 1L;

            // 使用限制次数check
            Long couponUsedCounts = orderService.getCampaignDiscountUsedCount(inputCouponCode, "");
            if (couponUsedCounts != null
                && conditionList.get(0).getUseLimit() != null
                && BigDecimalUtil.isAbove(BigDecimalUtil.add(couponUsedCounts, useCount), new BigDecimal(conditionList.get(0).getUseLimit()))) {
              commodity.setDisplayCouponMode("3"); // 使用折扣券出错
              addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_MAX_COMMODITY_NUM_ERROR, inputCouponCode));
              return false;
            }
            
            commodity.setUsedCouponCode(inputCouponCode);
          }
        }
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

    ShippingBean bean = getBean();

    // cashierに画面情報を設定
    Cashier cashier = bean.getCashier();
    List<Coupon> couponTempList = new ArrayList<Coupon>(); // 商品折扣券信息
    CommunicationService comService = ServiceLocator.getCommunicationService(getLoginInfo());
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    
    // 重置bean中的商品价格
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detailBean : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detailBean.getCommodityList()) {
          if (getBean().getSkuCodeSeleted().equals(commodity.getSkuCode())) {
            
            CampaignInfo campaignInfo = comService.getCampaignInfo(commodity.getUsedCouponCode());
            CampaignCondition condition = campaignInfo.getConditionList().get(0);
            
            commodity.setUsedCouponType(condition.getDiscountType());
            commodity.setUsedCouponName(campaignInfo.getCampaignMain().getCampaignName());
            
            if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
              commodity.setUsedCouponName(campaignInfo.getCampaignMain().getCampaignName());
            } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
              commodity.setUsedCouponName(campaignInfo.getCampaignMain().getCampaignNameJp());
            } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
              commodity.setUsedCouponName(campaignInfo.getCampaignMain().getCampaignNameEn());
            }
            
            BigDecimal couponValue = new BigDecimal(BigDecimal.ZERO.toString());
            if (StringUtil.hasValue(campaignInfo.getCampaignDoings().getAttributrValue())) {
              couponValue = new BigDecimal(campaignInfo.getCampaignDoings().getAttributrValue());
            }
            
            if (CouponIssueType.PROPORTION.longValue().equals(condition.getDiscountType())) {
              
              BigDecimal couponPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(commodity.getRetailPrice(), couponValue), 100, 2, RoundingMode.HALF_UP);
              
              if (BigDecimalUtil.isAbove(couponPrice, commodity.getRetailPrice())) {
                couponPrice = commodity.getRetailPrice();
              }
              
              commodity.setUsedCouponPrice(couponPrice);
            } else {
              
              if (BigDecimalUtil.isAbove(couponValue, commodity.getRetailPrice())) {
                couponValue = commodity.getRetailPrice();
              }
              commodity.setUsedCouponPrice(couponValue);
            }
            
            if (commodity.getUsedCouponPrice() != null) {
              commodity.setRetailPriceExceptCoupon(BigDecimalUtil.subtract(commodity.getRetailPrice(), commodity.getUsedCouponPrice()));
            } else {
              commodity.setRetailPriceExceptCoupon(commodity.getRetailPrice());
            }
            
            commodity.setSubTotalPrice(BigDecimalUtil.multiply(commodity.getRetailPriceExceptCoupon(), NumUtil.toLong(commodity.getCommodityAmount(), 0L)));
            
            commodity.setDisplayCouponMode("2"); // 已使用折扣券
            Coupon coupon = new Coupon(commodity.getSkuCode(), commodity.getUsedCouponCode(), commodity.getUsedCouponType(), commodity.getUsedCouponPrice(), commodity.getUsedCouponName());
            couponTempList.add(coupon);
          }
        }
      }
    }
    
    // 将使用折扣券后的金额保存到cashier中
    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        if (getBean().getSkuCodeSeleted().equals(commodity.getSkuCode())) {
          
          for (Coupon coupon : couponTempList) {
            Coupon couponTemp = new Coupon(coupon.getSkuCode(), coupon.getCouponCode(), coupon.getCouponType(), coupon.getCouponPrice(), coupon.getCouponName());
            if (couponTemp.equals(coupon)) {
              commodity.setCampaignCouponCode(couponTemp.getCouponCode());
              commodity.setCampaignCouponName(couponTemp.getCouponName());
              commodity.setCampaignCouponPrice(couponTemp.getCouponPrice());
              commodity.setCampaignCouponType(couponTemp.getCouponType());
            }
          }
        }
      }
    }

    setRequestBean(bean);

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
