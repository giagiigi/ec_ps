package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.sint.webshop.data.dao.NewCouponRuleDao;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class NeworderShippingDiscountConfirmAction extends NeworderShippingBaseAction {

  public boolean validate() {
	  	String[] tmp = getRequestParameter().getPathArgs();
	  	if (tmp!=null && tmp.length>0 && "clear".equals(tmp[0])){
	  		return true;
	  	}
      if (StringUtil.isNullOrEmpty(getBean().getSelDiscountType())) {
        addErrorMessage("请选择折扣类型。");
        return false;
      }
	  	
	  	if ( DiscountType.COUPON.getValue().equals(getBean().getSelDiscountType()) &&StringUtil.isNullOrEmptyAnyOf(getBean().getCashier().getCustomer().getMobileNumber(), getBean().getCashier().getCustomer().getAuthCode())) {
	  	  addErrorMessage("如果需要使用优惠券或礼品券，请先进行手机验证。");
        return false;
	  	}
	  	

	    if (getBean().isDiscountCouponUsedFlg()) {
	      addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_USE_ONLY,
	          Messages.getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
	      return false;
	    }
	    // 优惠券折扣验证
	    if (getBean().getSelDiscountType().equals(DiscountType.COUPON.getValue())) {
	      if (StringUtil.hasValueAllOf(getBean().getSelPersonalCouponCode(), getBean().getPublicCouponCode())) {
	        addErrorMessage("私有和公有优惠券不能同时使用。");
	        return false;
	      } else if (StringUtil.hasValue(getBean().getSelPersonalCouponCode())) {
	        
        // 已选择个人优惠券信息取得
        MyCouponInfo aviableCouponSelected = getAviableCouponSelected();
        if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
          addErrorMessage("选择的个人优惠券已使用或已过期。");
          getBean().setPersonalCouponList(createPersonalCouponList(getBean().getCashier()));
          getBean().getCashier().setDiscount(new CashierDiscount());
          return false;
        }

        // 2013/04/17 优惠券对应 ob update end

      } else if (StringUtil.hasValue(getBean().getPublicCouponCode())) {
	        // 公共优惠券存在验证
	        NewCouponRule newCouponRule = getPublicCoupon(getBean().getPublicCouponCode());
	        if (newCouponRule != null) {
	          if (newCouponRule.getUseType() != null) {
	             // 优惠券使用订购金额限制
	            if(StringUtil.isNullOrEmpty(newCouponRule.getObjectBrand()) && StringUtil.isNullOrEmpty(newCouponRule.getObjectCategory())
	                && (newCouponRule.getUseType().equals(2L) || (newCouponRule.getUseType().equals(3L)))) {
	              addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.9")
	                  + newCouponRule.getMinUseOrderAmount().toString()
	                  + Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.10"));
	              getBean().getCashier().setDiscount(new CashierDiscount());
	              return false;
	            }
	          }

	            if (BigDecimalUtil.isBelow(getTotalRelatePrice(newCouponRule), newCouponRule.getMinUseOrderAmount())) {
	              addErrorMessage("指定商品订购未满"+newCouponRule.getMinUseOrderAmount().toString()+"元不能使用");
	              getBean().getCashier().setDiscount(new CashierDiscount());
	              return false;
	            }
	            
	          OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
	          // 优惠券使用次数网站限制
	          Long siteCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), null);
	          if (newCouponRule.getSiteUseLimit().longValue()>0L && siteCount >= newCouponRule.getSiteUseLimit().longValue()) {
	            addErrorMessage("已经超出了使用次数上限，因此不能使用了。");
	            getBean().getCashier().setDiscount(new CashierDiscount());
	            return false;
	          }
	          // 优惠券使用次数个人限制
	          Long customerCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), getBean().getCustomerCode());
	          if (newCouponRule.getPersonalUseLimit().longValue()>0L && customerCount >= newCouponRule.getPersonalUseLimit().longValue()) {
	            addErrorMessage("已经超出了个人使用次数上限，因此不能使用了。");
	            getBean().getCashier().setDiscount(new CashierDiscount());
	            return false;
	          }
	          // 20121102 yyq add start
	          if (newCouponRule.getApplicableObjects() == 1) {
	            // 如果有初次购买限制
	            Long orderCount = orderService.countUsedCouponFirstOrder(getBean().getCustomerCode());
	            if (orderCount >= 1) {
	              addErrorMessage("很抱歉，该优惠券只允许初次在品店购买商品的用户使用。");
	              getBean().getCashier().setDiscount(new CashierDiscount());
	              return false;
	            }
	          }

	          if (!StringUtil.isNullOrEmpty(newCouponRule.getApplicableArea())) {
	            // 限制适用区域
	            boolean flag = true;
	            String areaUse = newCouponRule.getApplicableArea();
	            String address1 = getBean().getDeliveryList().get(0).getPrefectureCode();
	            String[] temp = areaUse.split(";");
	            for (int i = 0; i < temp.length; i++) {
	              if (address1.equals(temp[i])) {
	                flag = false;
	              }
	            }
	            if(flag){
	              addErrorMessage("很抱歉，您选择的配送区域无法使用优惠券。");
	              getBean().getCashier().setDiscount(new CashierDiscount());
	              return false;
	            }
	          }
	        } else {
	          addErrorMessage("输入的优惠券编号不存在或已过期。");
	          getBean().getCashier().setDiscount(new CashierDiscount());
	          return false;
	        }
	      } else {
	        addErrorMessage("请选择或输入优惠券。");
	        return false;
	      }
	    } else {
	      // 会员折扣存在验证
	      CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign(getBean().getCashier());
	      if (customerGroupCampaign == null) {
	        addErrorMessage("会员折扣不存在。");
	        getBean().setDiscountTypeList(createDiscountTypeList(getBean().getCashier()));
	        getBean().getCashier().setDiscount(new CashierDiscount());
	        return false;
	      }
	      // 20140311 hdh add start
	      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
	      Long useCount = orderService.countCustomerGroupCampaignOrder(customerGroupCampaign.getCampaignCode(), getBean().getCustomerCode());
	      //会员折扣最大使用次数
	      if(useCount>=customerGroupCampaign.getPersonalUseLimit().longValue()){
	        addErrorMessage("会员折扣已经超出了使用次数上限，因此不能使用了。");
	        return false;
	      }
	      // 20140311 hdh add end
	    }
	    return true;
  }

  @Override
  public WebActionResult callService() {
	   NeworderShippingBean bean = getBean();
	   // cashierに画面情報を設定
	    Cashier cashier = bean.getCashier();
	    CashierDiscount cashierDiscount = new CashierDiscount();
	    String[] tmp = getRequestParameter().getPathArgs();
	  	if (tmp!=null && tmp.length>0 && "clear".equals(tmp[0])){
	  		bean.setSelDiscountType(null);
	  		bean.setSelPersonalCouponCode(null);
	  		bean.setPublicCouponCode(null);
	  		cashier.setDiscount(cashierDiscount);
	  		bean.setDiscountUsedFlg(false);
	      createOutCardPrice();
	  		setRequestBean(bean);
		    return BackActionResult.RESULT_SUCCESS;
	  	}
	  	
	  	// 20140312 hdh add start
	  	getBean().setHiddenSelDiscountType(getBean().getSelDiscountType());
	  	// 20140312 hdh add end;
	    // 优惠券折扣
	    if (getBean().getSelDiscountType().equals(DiscountType.COUPON.getValue())) {
	      // 个人优惠券
	      if (StringUtil.hasValue(getBean().getSelPersonalCouponCode())) {
	        // 2013/04/17 优惠券对应 ob update start
	        // 已选择个人优惠券信息取得
	        MyCouponInfo aviableCouponSelected = getAviableCouponSelected();
	        if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
	          addErrorMessage("选择的个人优惠券已使用或已过期。");
            bean.setPersonalCouponList(createPersonalCouponList(cashier));
            cashier.setDiscount(new CashierDiscount());
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
	        }
	        cashierDiscount.setDiscountCode(aviableCouponSelected.getCouponCode());
	        cashierDiscount.setDiscountName(aviableCouponSelected.getCouponName());
	        cashierDiscount.setDiscountDetailCode(aviableCouponSelected.getCouponIssueNo());
	        cashierDiscount.setDiscountType(DiscountType.COUPON.getValue());
	        cashierDiscount.setDiscountMode(aviableCouponSelected.getCouponIssueType().toString());
	        NewCouponRuleDao dao = DIContainer.getDao(NewCouponRuleDao.class);
	        NewCouponRule dto = dao.load(aviableCouponSelected.getCouponCode());
	        if (dto != null) {
            cashierDiscount.setCouponType(CouponType.PURCHASE_DISTRIBUTION.getValue());
	        } else {
	          cashierDiscount.setCouponType("");
	        }
	        

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
          // 2013/04/17 优惠券对应 ob update end
	        
	      } else {
	        // 公共优惠券
	        NewCouponRule newCouponRule = getPublicCoupon(bean.getPublicCouponCode(), cashier.getTotalCommodityPrice());
	        if (newCouponRule == null) {
	          addErrorMessage("输入的优惠券编号不存在或已过期。");
	          cashier.setDiscount(new CashierDiscount());
	          setRequestBean(bean);
	          return BackActionResult.RESULT_SUCCESS;
	        }

	        // 折扣信息设定
	        cashierDiscount.setDiscountCode(newCouponRule.getCouponCode());
	        cashierDiscount.setDiscountName(newCouponRule.getCouponName());
	        cashierDiscount.setDiscountType(DiscountType.COUPON.getValue());
	        cashierDiscount.setDiscountMode(newCouponRule.getCouponIssueType().toString());
	        cashierDiscount.setCouponType(CouponType.COMMON_DISTRIBUTION.getValue());
	        BigDecimal objectTotalPriceTemp = getTotalRelatePrice(newCouponRule);
	        if (newCouponRule.getCouponIssueType().equals(CouponIssueType.FIXED.longValue())) {
	          cashierDiscount.setDiscountPrice(newCouponRule.getCouponAmount());
	        } else {
	          
	          BigDecimal discountPrice;
	          if (StringUtil.isNullOrEmpty(newCouponRule.getObjectCommodities())
	              && StringUtil.isNullOrEmpty(newCouponRule.getObjectBrand())
	              && StringUtil.isNullOrEmpty(newCouponRule.getObjectCategory())) {
	              if(newCouponRule.getMaxUseOrderAmount()!=null ){
	                  if(cashier.getTotalCommodityPrice().compareTo(newCouponRule.getMaxUseOrderAmount()) > 0){
	                    discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(newCouponRule.getMaxUseOrderAmount(), newCouponRule
	                        .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	                  }else {
	                    discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(cashier.getTotalCommodityPrice(), newCouponRule
	                        .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	                }
	              } else {
	                  discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(cashier.getTotalCommodityPrice(), newCouponRule
	                      .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
	              }
	          } else {
	              BigDecimal commodityTotalPrice = objectTotalPriceTemp;
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
	      CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign(getBean().getCashier());
	      if (customerGroupCampaign == null) {
	        addErrorMessage("会员折扣不存在。");
	        bean.setDiscountTypeList(createDiscountTypeList(cashier));
	        cashier.setDiscount(new CashierDiscount());
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
	        BigDecimal discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(cashier.getTotalCommodityPrice(),
	            customerGroupCampaign.getCampaignProportion()), 100, 2, RoundingMode.HALF_UP);
	        cashierDiscount.setDiscountPrice(discountPrice);
	        cashierDiscount.setDiscountRate(customerGroupCampaign.getCampaignProportion());
	      }
	    }
	    cashier.setDiscount(cashierDiscount);
	    bean.setDiscountUsedFlg(true);
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
    return "新接受订货登录(发送处设定)优惠设定";
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
