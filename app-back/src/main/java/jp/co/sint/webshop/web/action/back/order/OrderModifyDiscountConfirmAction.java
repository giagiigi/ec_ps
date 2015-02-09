package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;

public class OrderModifyDiscountConfirmAction extends OrderModifyBaseAction {

  public boolean validate() {
	  	String[] tmp = getRequestParameter().getPathArgs();
	  	getBean().getAfterTotalPrice().setDiscountPrice(BigDecimal.ZERO);
	  	
	  	if (tmp!=null && tmp.length>0 && "clear".equals(tmp[0])){
	  		return true;
	  	}
	    if (StringUtil.isNullOrEmpty(getBean().getSelDiscountType())) {
	      addErrorMessage("请选择折扣类型。");
	      return false;
	    }
	    // 优惠券折扣验证
	    if (getBean().getSelDiscountType().equals(DiscountType.COUPON.getValue())) {
	      if (StringUtil.hasValueAllOf(getBean().getSelPersonalCouponCode(), getBean().getPublicCouponCode())) {
	        addErrorMessage("私有和公有优惠券不能同时使用。");
	        return false;
	      } else if (StringUtil.hasValue(getBean().getSelPersonalCouponCode())) {
	        NewCouponHistory newCouponHistory = getPersonalCoupon(getBean().getOrderHeaderEdit().getCustomerCode()
	        		, getBean().getSelPersonalCouponCode(),getBean().getAfterTotalPrice().getCommodityPrice());
	        if (newCouponHistory == null) {
	          addErrorMessage("选择的个人优惠券已使用或已过期。");
	          getBean().setPersonalCouponList(createPersonalCouponList());
	          getBean().setNewCashierDiscount(new CashierDiscount());
	          return false;
	        }
	      } else if (StringUtil.hasValue(getBean().getPublicCouponCode())) {
	        // 公共优惠券存在验证
	        NewCouponRule newCouponRule = getPublicCoupon(getBean().getPublicCouponCode(), getBean().getAfterTotalPrice().getCommodityPrice());
	        if (newCouponRule != null) {
	          OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
	          // 优惠券使用次数网站限制
	          Long siteCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), null);
	          if (newCouponRule.getSiteUseLimit().longValue()>0L&&siteCount >= newCouponRule.getSiteUseLimit().longValue()) {
	            addErrorMessage("已经超出了使用次数上限，因此不能使用了。");
	            getBean().setNewCashierDiscount(new CashierDiscount());
	            return false;
	          }
	          
	          // 优惠券使用订购金额限制
	          // if (BigDecimalUtil.isBelow(getBean().getCashier().getTotalCommodityPrice(), newCouponRule.getMinUseOrderAmount())) {
	          if (BigDecimalUtil.isBelow(getTotalCouponCommodityPrice(newCouponRule.getObjectCommodities()), newCouponRule.getMinUseOrderAmount())) {
	            addErrorMessage("指定商品订购未满"
	                + newCouponRule.getMinUseOrderAmount().toString()
	                + "元不能使用");
	            return false;
	          }
	          // 优惠券使用次数个人限制
	          Long customerCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), getBean().getOrderHeaderEdit().getCustomerCode());
	          if (newCouponRule.getPersonalUseLimit().longValue()>0L&&customerCount >= newCouponRule.getPersonalUseLimit().longValue()) {
	            addErrorMessage("已经超出了个人使用次数上限，因此不能使用了。");
	            getBean().setNewCashierDiscount(new CashierDiscount());
	            return false;
	          }
	          
	          // 20121102 yyq add start
	          if (newCouponRule.getApplicableObjects() == 1) {
	            // 如果有初次购买限制
	            Long orderCount = orderService.countUsedCouponFirstOrder(getBean().getOrderHeaderEdit().getCustomerCode());
	            if (orderCount >= 1) {
	              addErrorMessage("很抱歉，该优惠券只允许初次在品店购买商品的用户使用。");
	              getBean().setNewCashierDiscount(new CashierDiscount());
	              return false;
	            }
	          }

	          if (!StringUtil.isNullOrEmpty(newCouponRule.getApplicableArea())) {
	            // 限制适用区域
	            boolean flag = true;
	            String areaUse = newCouponRule.getApplicableArea();
	            String address1 = getBean().getShippingHeaderList().get(0).getAddress().getPrefectureCode();
	            String[] temp = areaUse.split(";");
	            for (int i = 0; i < temp.length; i++) {
	              if (address1.equals(temp[i])) {
	                flag = false;
	              }
	            }
	            if(flag){
	              addErrorMessage("很抱歉，您选择的配送区域无法使用优惠券。");
	              getBean().setNewCashierDiscount(new CashierDiscount());
	              return false;
	            }
	          }
	          // 20121102 yyq add end
	          
	        } else {
	          addErrorMessage("输入的优惠券编号不存在或已过期。");
	          getBean().setNewCashierDiscount(new CashierDiscount());
	          return false;
	        }
	      } else {
	        addErrorMessage("请选择或输入优惠券。");
	        return false;
	      }
	    } else {
	      // 会员折扣存在验证
	      CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign();
	      if (customerGroupCampaign == null) {
	        addErrorMessage("会员折扣不存在。");
	        getBean().setDiscountTypeList(createDiscountTypeList());
	        getBean().setNewCashierDiscount(new CashierDiscount());
	        return false;
	      }
	    }
	    return true;
  }

  @Override
  public WebActionResult callService() {
	   OrderModifyBean bean = getBean();
	   recomputePrice(bean);
	   recomputePoint(bean);
	    if (StringUtil.hasValue(bean.getOrderPayment().getPaymentMethodCode())) {
	    	recomputePaymentCommission(bean);
	    }
	    recomputePrice(bean);
	   // cashierに画面情報を設定
	   CashierDiscount cashierDiscount = new CashierDiscount();
	    String[] tmp = getRequestParameter().getPathArgs();
	  	if (tmp!=null && tmp.length>0 && "clear".equals(tmp[0])){
	  		bean.setSelDiscountType(null);
	  		bean.setSelPersonalCouponCode(null);
	  		bean.setPublicCouponCode(null);
	  		bean.setNewCashierDiscount(cashierDiscount);
	  		bean.setDiscountPrice("0");
	  		 if ("1".equals(bean.getUseOrgDiscount())) {
	  			 if (bean.getOrgCashierDiscount().getDiscountPrice()!=null) {
	  				bean.setDiscountPrice(bean.getAfterTotalPrice().getDiscountPrice().toString());
	  			 }
	  		 }
	  		setRequestBean(bean);
		    return BackActionResult.RESULT_SUCCESS;
	  	}
	    // 优惠券折扣
	    if (getBean().getSelDiscountType().equals(DiscountType.COUPON.getValue())) {
	      // 个人优惠券
	      if (StringUtil.hasValue(getBean().getSelPersonalCouponCode())) {
	        
	        // 已选择个人优惠券信息取得
	        List<MyCouponInfo> myCouponInfoList = getAviableCouponList(getBean().getShopCode());
	        MyCouponInfo aviableCouponSelected = new MyCouponInfo();
	        
	        for (MyCouponInfo myCouponInfo : myCouponInfoList) {
	          if (getBean().getSelPersonalCouponCode().equals(myCouponInfo.getCouponIssueNo())) {
	            aviableCouponSelected = myCouponInfo;
	          }
	        }
	        
	        if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
	          addErrorMessage("选择的个人优惠券已使用或已过期。");
            bean.setNewCashierDiscount(cashierDiscount);
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
	        }
	        
	        cashierDiscount.setDiscountCode(aviableCouponSelected.getCouponCode());
	        cashierDiscount.setDiscountName(aviableCouponSelected.getCouponName());
	        cashierDiscount.setDiscountDetailCode(aviableCouponSelected.getCouponIssueNo());
	        cashierDiscount.setDiscountType(DiscountType.COUPON.getValue());
	        cashierDiscount.setDiscountMode(aviableCouponSelected.getCouponIssueType().toString());
          cashierDiscount.setCouponType(CouponType.PURCHASE_DISTRIBUTION.getValue());

	        if (aviableCouponSelected.getCouponIssueType().equals(CouponIssueType.FIXED.longValue())) {
	          cashierDiscount.setDiscountPrice(aviableCouponSelected.getCouponAmount());
	          if (aviableCouponSelected.getCouponAmount().compareTo(aviableCouponSelected.getMaxUseOrderAmount()) > 0) {
	            cashierDiscount.setDiscountPrice(aviableCouponSelected.getMaxUseOrderAmount());
	          }
	        } else {
	          BigDecimal discountPrice;
	          BigDecimal totalPriceTemp = aviableCouponSelected.getObjectTotalPrice();
	          
	          if (aviableCouponSelected.getMaxUseOrderAmount() != null
	              && totalPriceTemp.compareTo(aviableCouponSelected.getMaxUseOrderAmount()) > 0) {
	            totalPriceTemp = aviableCouponSelected.getMaxUseOrderAmount();
	          }
	          
	          discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(totalPriceTemp,
	              aviableCouponSelected.getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	          cashierDiscount.setDiscountPrice(discountPrice);
	          cashierDiscount.setDiscountRate(aviableCouponSelected.getCouponProportion());
	        }
          // 2013/04/18 优惠券对应 ob update end
	        
	      } else {
	        // 公共优惠券
	        NewCouponRule newCouponRule = getPublicCoupon(bean.getPublicCouponCode(), bean.getAfterTotalPrice().getCommodityPrice());
	        if (newCouponRule == null) {
	          addErrorMessage("输入的优惠券编号不存在或已过期。");
	          bean.setNewCashierDiscount(cashierDiscount);
	          setRequestBean(bean);
	          return BackActionResult.RESULT_SUCCESS;
	        }

	        // 折扣信息设定
	        cashierDiscount.setDiscountCode(newCouponRule.getCouponCode());
	        cashierDiscount.setDiscountName(newCouponRule.getCouponName());
	        cashierDiscount.setDiscountType(DiscountType.COUPON.getValue());
	        cashierDiscount.setDiscountMode(newCouponRule.getCouponIssueType().toString());
	        cashierDiscount.setCouponType(CouponType.COMMON_DISTRIBUTION.getValue());
	        if (newCouponRule.getCouponIssueType().equals(CouponIssueType.FIXED.longValue())) {
	          cashierDiscount.setDiscountPrice(newCouponRule.getCouponAmount());
	        } else {
	          BigDecimal discountPrice;
	          if(StringUtil.isNullOrEmpty(newCouponRule.getObjectCommodities())){
	              if(newCouponRule.getMaxUseOrderAmount()!=null ){
	                  if(bean.getAfterTotalPrice().getCommodityPrice().compareTo(newCouponRule.getMaxUseOrderAmount()) > 0){
	                    discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(newCouponRule.getMaxUseOrderAmount(), newCouponRule
	                        .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	                  }else {
	                    discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(bean.getAfterTotalPrice().getCommodityPrice(), newCouponRule
	                        .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	                }
	              } else {
	                  discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(bean.getAfterTotalPrice().getCommodityPrice(), newCouponRule
	                      .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	              }
	          } else {
	         // 取得优惠券包含对象商品list
	            String[] commodityList = newCouponRule.getObjectCommodities().split(";");
	            BigDecimal result = BigDecimal.ZERO;
	            for (String commodityCode : commodityList) {
	              for (ShippingHeaderBean headerBean : bean.getShippingHeaderList()) {
	                for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
	                  if (commodityCode.equals(detailBean.getSkuCode())) {
	                    result = result.add(BigDecimalUtil.multiply(detailBean.getOrderDetailCommodityInfo().getRetailPrice(), Long.valueOf(detailBean.getShippingDetailCommodityAmount())));
	                }
	                }
	              }
	            }
	            BigDecimal commodityTotalPrice = result;
	              if(newCouponRule.getMaxUseOrderAmount()==null ){
	                discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(commodityTotalPrice, newCouponRule
	                    .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	              } else {
	                  if(commodityTotalPrice.compareTo(newCouponRule.getMaxUseOrderAmount()) > 0){
	                    discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(newCouponRule.getMaxUseOrderAmount(), newCouponRule
	                        .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	                  } else {
	                    discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(commodityTotalPrice, newCouponRule
	                        .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	                  }
	              }
	          }
	          cashierDiscount.setDiscountPrice(discountPrice);
	          cashierDiscount.setDiscountRate(newCouponRule.getCouponProportion());
	        }
	      }
	    } else {
	      // 会员折扣
	      CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign();
	      if (customerGroupCampaign == null) {
	        addErrorMessage("会员折扣不存在。");
	        bean.setDiscountTypeList(createDiscountTypeList());
	        bean.setNewCashierDiscount(cashierDiscount);
	        setRequestBean(bean);
	        return BackActionResult.RESULT_SUCCESS;
	      }
	      // 折扣信息设定
	      cashierDiscount.setDiscountCode(customerGroupCampaign.getCampaignCode());
	      cashierDiscount.setDiscountName(customerGroupCampaign.getCampaignName());
	      cashierDiscount.setDiscountType(DiscountType.CUSTOMER.getValue());
	      cashierDiscount.setDiscountMode(customerGroupCampaign.getCampaignType().toString());
	      cashierDiscount.setCouponType(CouponType.CUSTOMER_GROUP.getValue());
	      if (customerGroupCampaign.getCampaignType().equals(CampaignType.FIXED.longValue())) {
	        cashierDiscount.setDiscountPrice(customerGroupCampaign.getCampaignAmount());
	      } else {
	        BigDecimal discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(bean.getAfterTotalPrice().getCommodityPrice(),
	            customerGroupCampaign.getCampaignProportion()), 100, 2, RoundingMode.HALF_UP);
	        cashierDiscount.setDiscountPrice(discountPrice);
	        cashierDiscount.setDiscountRate(customerGroupCampaign.getCampaignProportion());
	      }
	    }

	    bean.setNewCashierDiscount(cashierDiscount);
	    bean.getAfterTotalPrice().setDiscountPrice(bean.getNewCashierDiscount().getDiscountPrice());
	    bean.setDiscountPrice(bean.getNewCashierDiscount().getDiscountPrice().toString());
	    createOutCardPrice();
	    setRequestBean(bean);
	    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   *
   * @return Action名
   */
  public String getActionName() {
    return "接受订货修正优惠设定处理";
  }

  /**
   * オペレーションコードの取得
   *
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023011";
  }
}
