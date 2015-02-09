package jp.co.sint.webshop.web.action.front.order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author Kousen.
 */
public class ShippingCouponClearAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String skuCodeSeleted = getPathInfo(0);
    getBean().setSkuCodeSeleted(skuCodeSeleted);
    if (StringUtil.isNullOrEmpty(skuCodeSeleted)) {
      throw new URLNotFoundException();
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
    
    Cashier cashier = bean.getCashier();
    
    CommunicationService comService = ServiceLocator.getCommunicationService(getLoginInfo());
    List<CampaignInfo> couponList = comService.getCouponCampaignInfo(DateUtil.getSysdate());
    
    // beanに画面情報を設定
    for (ShippingHeaderBean shipping : bean.getShippingList()) {
      for (ShippingDetailBean detailBean : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detailBean.getCommodityList()) {
          if (bean.getSkuCodeSeleted().equals(commodity.getSkuCode())) {
            commodity.setUsedCouponCode("");
            commodity.setUsedCouponName("");
            commodity.setUsedCouponPrice(null);
            commodity.setUsedCouponType(null);
            commodity.setRetailPriceExceptCoupon(commodity.getRetailPrice());
            commodity.setSubTotalPrice(BigDecimalUtil.multiply(commodity.getRetailPrice(), Integer.parseInt(commodity.getCommodityAmount())));
            commodity.setDisplayCouponMode("0"); // 无可使用的折扣券
            
            if (!commodity.isSetCommodity()) {
              if (couponList != null && couponList.size() > 0) {
                for (CampaignInfo info : couponList) {
                  if (info.getConditionList() != null && info.getConditionList().get(0) != null) {
                    CampaignCondition condition = info.getConditionList().get(0);

                    String[] attributes = condition.getAttributrValue().split(",");
                    Set<String> attributeSet = new HashSet<String>();
                    for (int i = 0; i < attributes.length; i++) {
                      if (StringUtil.hasValue(attributes[i])) {
                        attributeSet.add(attributes[i]);
                      }
                    }

                    if (attributeSet.contains(commodity.getCommodityCode()) 
                        && (condition.getMaxCommodityNum() == null 
                            || NumUtil.toLong(commodity.getCommodityAmount(), 0L) >= condition.getMaxCommodityNum())) {
                      commodity.setDisplayCouponMode("1"); // 有可使用的折扣券，但未使用
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    // cashierに画面情報を設定
    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        if (bean.getSkuCodeSeleted().equals(commodity.getSkuCode())) {
          commodity.setCampaignCouponCode("");
          commodity.setCampaignCouponName("");
          commodity.setCampaignCouponPrice(null);
          commodity.setCampaignCouponType(null);
          break;
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
