package jp.co.sint.webshop.web.action.front.order;

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
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author Kousen.
 */
public class ShippingDiscountConfirmAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    refreshItems(false, false);

    boolean hasMsg = true;
    if (StringUtil.hasValue(getPathInfo(0))) {
      getBean().setSelDiscountType(getPathInfo(0));
      hasMsg = false;
    }

    if (StringUtil.isNullOrEmpty(getBean().getSelDiscountType())) {
      if (hasMsg) {
        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.0"));
      }
      return false;
    }

    // 2012/11/28 促销对应 ob add start
    // 购物车内的商品已使用折扣券时
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()) {
        for (ShippingCommodityBean commodity : detail.getCommodityList()) {
          if (StringUtil.hasValue(commodity.getUsedCouponCode())) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_AND_DISCOUNT_DUPLICATE_ERROR));
            return false;
          }
        }
      }
    }
    // 2012/11/28 促销对应 ob add end

    CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign(getBean().getCashier());

    // 优惠券折扣验证
    if (getBean().getSelDiscountType().equals(DiscountType.COUPON.getValue())) {
      if (  StringUtil.hasValueAllOf(getBean().getSelPersonalCouponCode(), getBean().getPublicCouponCode())){
        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.1"));
        return false;
      } else if (StringUtil.hasValue(getBean().getSelPersonalCouponCode())) {

        // 已选择个人优惠券信息取得
        MyCouponInfo aviableCouponSelected = getAviableCouponSelected();

        if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
          addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.2"));
          getBean().setPersonalCouponList(createPersonalCouponList(getBean().getCashier()));
          getBean().getCashier().setDiscount(new CashierDiscount());
          return false;
        }
        
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        String couponCode = customerService.getCouponCodeByCouponIssueNo(getBean().getSelPersonalCouponCode());
        List<NewCouponRuleUseInfo> newCouponRuleUseInfoList = customerService.getNewCouponRuleUseInfo(couponCode);
        
        // 取得个人优惠券的类型
        BigDecimal couponType = customerService.getCouponType(couponCode);
        
        // 如果为生日优惠券，则订单相关商品订购金额大于规则最小订购金额才能通过验证
        if (couponType != null && CouponType.BIRTHDAY_DISTRIBUTION.getValue().equals(couponType.toString())) {
          NewCouponRule newCouponRule = customerService.getPersonalBirthdayCoupon(couponCode);
          
          if (BigDecimalUtil.isBelow(getTotalRelatePrice(newCouponRule, newCouponRuleUseInfoList), newCouponRule.getMinUseOrderAmount())) {
            addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.9")
                + newCouponRule.getMinUseOrderAmount().toString()
                + Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.10"));
            getBean().getCashier().setDiscount(new CashierDiscount());
            return false;
          }
        }
        
        // 如果为特别会员发行优惠券，则订单相关商品订购金额大于规则最小订购金额才能通过验证
        if (couponType != null && CouponType.SPECIAL_MEMBER_DISTRIBUTION.getValue().equals(couponType.toString())) {
          NewCouponRule newCouponRule = customerService.getSpecialMemberDistribution(couponCode);
          
          if (BigDecimalUtil.isBelow(getTotalRelatePrice(newCouponRule, newCouponRuleUseInfoList), newCouponRule.getMinUseOrderAmount())) {
            addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.9")
                + newCouponRule.getMinUseOrderAmount().toString()
                + Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.10"));
            getBean().getCashier().setDiscount(new CashierDiscount());
            return false;
          }
        }
        
      } else if (StringUtil.hasValue(getBean().getPublicCouponCode())) {
        // 公共优惠券存在验证

        // 2013/04/07 优惠券对应 ob add start
        // 自己发行的Public优惠券不可使用
        CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
        if (communicationService.issuedBySelf(getBean().getPublicCouponCode(), getBean().getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.USE_COUPON_OF_ISSUE_BY_SELF_ERROR));
          return false;
        }
        // 2013/04/07 优惠券对应 ob add end

        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        NewCouponRule newCouponRule = customerService.getPublicCoupon(getBean().getPublicCouponCode());
        if (newCouponRule == null) {
          addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.5"));
          getBean().getCashier().setDiscount(new CashierDiscount());
          return false;
        } else {
          if (newCouponRule.getUseType() != null) {
            if(StringUtil.isNullOrEmpty(newCouponRule.getObjectBrand()) && StringUtil.isNullOrEmpty(newCouponRule.getObjectCategory())
                && (newCouponRule.getUseType().equals(2L) || (newCouponRule.getUseType().equals(3L)))) {
              addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.9")
                  + newCouponRule.getMinUseOrderAmount().toString()
                  + Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.10"));
              getBean().getCashier().setDiscount(new CashierDiscount());
              return false;
            }
          }

          // 优惠券使用订购金额限制
          if (BigDecimalUtil.isBelow(getTotalRelatePrice(newCouponRule), newCouponRule.getMinUseOrderAmount())) {
            addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.9")
                + newCouponRule.getMinUseOrderAmount().toString()
                + Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.10"));
            getBean().getCashier().setDiscount(new CashierDiscount());
            return false;
          }
          OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
          // 优惠券使用次数网站限制
          Long siteCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), null);
          if (siteCount >= newCouponRule.getSiteUseLimit().longValue()) {
            addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.3"));
            getBean().getCashier().setDiscount(new CashierDiscount());
            return false;
          }
          // 优惠券使用次数个人限制
          Long customerCount = orderService.countUsedCouponOrder(newCouponRule.getCouponCode(), getBean().getCustomerCode());
          if (customerCount >= newCouponRule.getPersonalUseLimit().longValue()) {
            addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.4"));
            getBean().getCashier().setDiscount(new CashierDiscount());
            return false;
          }

          // 20121102 yyq add start
          if (newCouponRule.getApplicableObjects() == 1L) {
            // 如果有初次购买限制
            Long orderCount = orderService.countUsedCouponFirstOrder(getBean().getCustomerCode());
            if (orderCount >= 1L) {
              addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.11"));
              getBean().getCashier().setDiscount(new CashierDiscount());
              return false;
            }
          }

          if (!StringUtil.isNullOrEmpty(newCouponRule.getApplicableArea())) {
            // 限制适用区域
            boolean flag = true;
            String areaUse = newCouponRule.getApplicableArea();
            String address1 = getBean().getSelPrefectureCode();
            String[] temp = areaUse.split(";");
            for (int i = 0; i < temp.length; i++) {
              if (address1.equals(temp[i])) {
                flag = false;
              }
            }
            if (flag) {
              addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.12"));
              getBean().getCashier().setDiscount(new CashierDiscount());
              return false;
            }
          }
          // 20121102 yyq add end

          // 2012-08-31 add by yyq start
          String mobileNumber = "";
          String phoneNumber = "";
          String nameAddress = "";
          for (ShippingHeaderBean shipping : getBean().getShippingList()) {

            mobileNumber = shipping.getAddress().getMobileNumber();
            phoneNumber = shipping.getAddress().getPhoneNumber();
            if (StringUtil.isNullOrEmpty(mobileNumber)) {
              mobileNumber = null;
            }
            if (StringUtil.isNullOrEmpty(phoneNumber)) {
              phoneNumber = null;
            }
            if (shipping.getAddress().getAddress3() != null) {
              nameAddress = shipping.getAddress().getLastName() + shipping.getAddress().getAddress1()
                  + shipping.getAddress().getAddress2() + shipping.getAddress().getAddress3() + shipping.getAddress().getAddress4();
            } else {
              nameAddress = shipping.getAddress().getLastName() + shipping.getAddress().getAddress1()
                  + shipping.getAddress().getAddress2() + shipping.getAddress().getAddress4();
            }
          }
          List<NewCouponRule> newCouponRuleList = orderService.getCouponLimitNewOrderCheck(newCouponRule.getCouponCode(),
              mobileNumber, phoneNumber, StringUtil.replaceEmpty(nameAddress));

          if (newCouponRuleList != null && newCouponRuleList.size() > 0) {
            int size = newCouponRuleList.size();
            NewCouponRule ncr = (NewCouponRule) newCouponRuleList.get(0);
            if (ncr.getPersonalUseLimit().intValue() <= size) {
              addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.4"));
              getBean().getCashier().setDiscount(new CashierDiscount());
              return false;
            }
          }
          // 2012-08-31 add by yyq end

        }
      } else {
        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.6"));
        return false;
      }
    } else if (getBean().getSelDiscountType().equals(DiscountType.CUSTOMER.getValue())) {
      // 会员折扣存在验证
      // 20140226 hdh add start
//      if(StringUtil.hasValue(getBean().getSelPersonalCouponCode())||StringUtil.hasValue(getBean().getPublicCouponCode())){
//        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.1"));
//        return false;
//      }
      // 20140226 hdh add end
      if (customerGroupCampaign == null) {
        if (hasMsg) {
          addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.7"));
        }
        getBean().setDiscountTypeList(createDiscountTypeList(getBean().getCashier()));
        getBean().getCashier().setDiscount(new CashierDiscount());
        return false;
      }   
      // 20140311 hdh add start
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      Long useCount = orderService.countCustomerGroupCampaignOrder(customerGroupCampaign.getCampaignCode(), getBean().getCustomerCode());
      
      //会员折扣最大使用次数
      if(useCount>=customerGroupCampaign.getPersonalUseLimit().longValue()){
        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.13"));
        return false;
      }
      // 20140311 hdh add end
      
    } else {
      if (hasMsg) {
        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.0"));
      }
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

    ShippingBean bean = getBean();

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // cashierに画面情報を設定
    Cashier cashier = bean.getCashier();
    CashierDiscount cashierDiscount = new CashierDiscount();
    // 20140312 hdh add start
    getBean().setHiddenSelDiscountType(getBean().getSelDiscountType());
    // 20140312 hdh add end
    // 优惠券折扣
    if (getBean().getSelDiscountType().equals(DiscountType.COUPON.getValue())) {
      // 个人优惠券
      if (StringUtil.hasValue(getBean().getSelPersonalCouponCode())) {
        // 2013/04/16 优惠券对应 ob update start

        // 已选择个人优惠券信息取得
        MyCouponInfo aviableCouponSelected = getAviableCouponSelected();
        if (aviableCouponSelected == null || StringUtil.isNullOrEmpty(aviableCouponSelected.getCouponIssueNo())) {
          addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.2"));
          bean.setPersonalCouponList(createPersonalCouponList(cashier));
          cashier.setDiscount(new CashierDiscount());
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        }
        cashierDiscount.setDiscountCode(aviableCouponSelected.getCouponCode());
        cashierDiscount.setDiscountName(utilService.getNameByLanguage(aviableCouponSelected.getCouponName(), aviableCouponSelected
            .getCouponNameEn(), aviableCouponSelected.getCouponNameJp()));
        cashierDiscount.setDiscountDetailCode(aviableCouponSelected.getCouponIssueNo());
        cashierDiscount.setDiscountType(DiscountType.COUPON.getValue());
        cashierDiscount.setDiscountMode(aviableCouponSelected.getCouponIssueType().toString());

        // 区分：个人优惠券/公共优惠券
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

          discountPrice = BigDecimalUtil.divide(BigDecimalUtil
              .multiply(totalPriceTemp, aviableCouponSelected.getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
          cashierDiscount.setDiscountPrice(discountPrice);
          cashierDiscount.setDiscountRate(aviableCouponSelected.getCouponProportion());
        }
        // 2013/04/16 优惠券对应 ob update end
      } else {
        // 公共优惠券
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        NewCouponRule newCouponRule = customerService.getPublicCoupon(getBean().getPublicCouponCode());
        if (newCouponRule == null) {
          addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.5"));
          cashier.setDiscount(new CashierDiscount());
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        }

        // 折扣信息设定
        cashierDiscount.setDiscountCode(newCouponRule.getCouponCode());
        cashierDiscount.setDiscountName(utilService.getNameByLanguage(newCouponRule.getCouponName(), newCouponRule
            .getCouponNameEn(), newCouponRule.getCouponNameJp()));
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
            if (newCouponRule.getMaxUseOrderAmount() != null) {
              if (cashier.getTotalCommodityPrice().compareTo(newCouponRule.getMaxUseOrderAmount()) > 0) {
                discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(newCouponRule.getMaxUseOrderAmount(), newCouponRule
                    .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
              } else {
                discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(cashier.getTotalCommodityPrice(), newCouponRule
                    .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
              }
            } else {
              discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(cashier.getTotalCommodityPrice(), newCouponRule
                  .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
            }
          } else {
            BigDecimal commodityTotalPrice = objectTotalPriceTemp;

            if (newCouponRule.getMaxUseOrderAmount() == null) {
              discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(commodityTotalPrice, newCouponRule
                  .getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
            } else {
              if (commodityTotalPrice.compareTo(newCouponRule.getMaxUseOrderAmount()) > 0) {
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
        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.7"));
        bean.setDiscountTypeList(createDiscountTypeList(cashier));
        cashier.setDiscount(new CashierDiscount());
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }
      // 折扣信息设定
      String campaignName = utilService.getNameByLanguage(customerGroupCampaign.getCampaignName(), customerGroupCampaign
          .getCampaignNameEn(), customerGroupCampaign.getCampaignNameJp());
      cashierDiscount.setDiscountCode(customerGroupCampaign.getCampaignCode());
      cashierDiscount.setDiscountName(campaignName);
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
      //清空优惠券信息
      bean.setSelPersonalCouponCode("");
      bean.setPublicCouponCode("");
    }

    if (BigDecimalUtil.isAbove(cashierDiscount.getDiscountPrice(), cashier.getTotalCommodityPrice())) {
      cashierDiscount.setDiscountPrice(cashier.getTotalCommodityPrice());
    }
    cashier.setDiscount(cashierDiscount);

    // 重新设置礼品卡金额
    bean.getCashier().setGiftCardUseAmount(BigDecimal.ZERO);
    if (BigDecimalUtil.isAbove(new BigDecimal(bean.getUseAmountRight()), bean.getCashier().getPaymentTotalPrice())) {
      bean.setUseAmountRight(bean.getCashier().getPaymentTotalPrice().toString());
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.17"));
    }
    bean.getCashier().setGiftCardUseAmount(new BigDecimal(bean.getUseAmountRight()));
    createOutCardPrice();
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
