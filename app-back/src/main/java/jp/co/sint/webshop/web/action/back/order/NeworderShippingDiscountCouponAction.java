package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dao.CampaignConditionDao;
import jp.co.sint.webshop.data.dao.CampaignDoingsDao;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean.OrderCommodityBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class NeworderShippingDiscountCouponAction extends NeworderShippingBaseAction {

  public boolean validate() {
    String[] tmp = getRequestParameter().getPathArgs();
    if (null == tmp || tmp.length < 1){
      addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_CODE_NULL_ERROR,
          Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
      return false;
    }
    
    if ("clear".equals(tmp[0])) {
      return true;
    }
    
    if (getBean().isDiscountUsedFlg()) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_USE_ONLY,
          Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
      return false;
    }
    
    String commodityCode = tmp[1];
    
    boolean valid = discountCouponUseValidation(commodityCode);
    if (valid) {
      valid &= minStockValidation();
    }
    
    return valid;
  }

  @Override
  public WebActionResult callService() {
    NeworderShippingBean bean = getBean();
    
    //从参数中取出折扣券编号以及使用的商品编号
    String[] tmp = getRequestParameter().getPathArgs();
    
    String commodityCode = tmp[1];
    List<DeliveryBean> deliveryList = bean.getDeliveryList();
    
    boolean findFlg = false;
    for (int i=0; i<deliveryList.size(); ++i) {
      List<OrderCommodityBean> orderCommodityList = deliveryList.get(i).getOrderCommodityList();
      for (int j=0; j<orderCommodityList.size(); ++j) {
        if (commodityCode.equals(orderCommodityList.get(j).getSkuCode())) {
          findFlg = true;
          String campaignCode = orderCommodityList.get(j).getDiscountCouponCode();
          CampaignConditionDao conditionDao = DIContainer.getDao(CampaignConditionDao.class);
          CampaignCondition condition = conditionDao.load(campaignCode);
          CampaignDoingsDao doingDao = DIContainer.getDao(CampaignDoingsDao.class);
          CampaignDoings doing = doingDao.load(campaignCode);
          BigDecimal discountPrice = BigDecimal.ZERO;
          BigDecimal totalDiscountPrice = BigDecimal.ZERO;
          
          //固定金额
          if (CouponIssueType.FIXED.longValue().equals(condition.getDiscountType())) {
            discountPrice = new BigDecimal(doing.getAttributrValue());
          } else { //比例
            discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(orderCommodityList.get(j).getRetailPrice(),
                new BigDecimal(doing.getAttributrValue())), 100, 2, RoundingMode.HALF_UP);
          }
          // 如果优惠金额比商品贩卖金额大,最多只能优惠商品的贩卖金额
          if (BigDecimalUtil.isAbove(discountPrice, orderCommodityList.get(j).getRetailPrice())) {
            discountPrice = orderCommodityList.get(j).getRetailPrice();
          }
          
          totalDiscountPrice = BigDecimalUtil.multiply(discountPrice, new BigDecimal(orderCommodityList.get(j).getPurchasingAmount()));
          if (BigDecimalUtil.isAbove(totalDiscountPrice, bean.getCashier().getTotalCommodityPrice())) {
            totalDiscountPrice = bean.getCashier().getTotalCommodityPrice();
          }
          
          Map<String, Long> discountCouponUsedMap = bean.getDiscountCouponUsedMap();
          if ("clear".equals(tmp[0])) {
            orderCommodityList.get(j).setDiscountCouponCode("");
            orderCommodityList.get(j).setDiscountCouponUsedFlg(false);
            
            orderCommodityList.get(j).setDiscountPrice(BigDecimal.ZERO);
            orderCommodityList.get(j).setTotalDiscountPrice(BigDecimal.ZERO);
            bean.setDiscountCouponUsedFlg(false);
            if (discountCouponUsedMap.containsKey(campaignCode)) {
              Long count = discountCouponUsedMap.get(campaignCode) - 1;
              discountCouponUsedMap.remove(campaignCode);
              if (count > 0) {
                discountCouponUsedMap.put(campaignCode, count);
              }
            }
            break;
          } else {
            orderCommodityList.get(j).setDiscountCouponUsedFlg(true);
            orderCommodityList.get(j).setDiscountPrice(discountPrice);
            orderCommodityList.get(j).setTotalDiscountPrice(totalDiscountPrice);
            
            bean.setDiscountCouponUsedFlg(true);
            
            if (discountCouponUsedMap.containsKey(campaignCode)) {
              Long count = discountCouponUsedMap.get(campaignCode) + 1;
              discountCouponUsedMap.remove(campaignCode);
              discountCouponUsedMap.put(campaignCode, count);
            } else {
              discountCouponUsedMap.put(campaignCode, 1L);
            }
            
            bean.setDiscountCouponUsedMap(discountCouponUsedMap);
          }
        }
        if (findFlg) {
          break;
        }
      }
    }
    copyBeanToCashier();
    
    setRequestBean(createBeanFromCashier());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013009";
  }
}
