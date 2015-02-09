package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.AddAddressBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShippingChangeAddressAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    
    refreshItems(false, false);

    String addressNo = getPathInfo(0);
    if (!StringUtil.hasValue(addressNo)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.WRONG_URL_PARAMETER));
      return false;
    }
    ShippingHeaderBean selectShipping = getSelectShipping();
    if (selectShipping == null) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.WRONG_URL_PARAMETER));
      return false;
    }

    // 在庫?予約上限数チェック
    valid = shippingValidation();
    if (valid) {
      try {
        copyBeanToCashier();
      } catch (Exception e) {
        valid = false;
      } finally {
        if (!valid) {
          return false;
        }
      }
    }
    valid &= numberLimitValidation();

    if (valid) {
      // 同一出荷先への変更チェック
      // 配送先を変更してなければTrue
      if (NumUtil.toString(selectShipping.getAddress().getAddressNo()).equals(selectShipping.getNewAddressNo())) {
        getBean().getCashier().setDiscount(new CashierDiscount());
        getBean().setPublicCouponCode("");
        return true;
      }
      for (ShippingHeaderBean shipping : getBean().getShippingList()) {
        if (selectShipping.getNewAddressNo().equals(NumUtil.toString(shipping.getAddress().getAddressNo()))) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.CHANGE_SAME_DELIVERY));
          valid = false;
        }
      }
    }

    // 2012-11-13 update by yyq start
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
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    List<NewCouponRule> newCouponRuleList = orderService.getCouponLimitNewOrderCheck(getBean().getPublicCouponCode(), mobileNumber,
        phoneNumber, StringUtil.replaceEmpty(nameAddress));

    if (newCouponRuleList != null && newCouponRuleList.size() > 0) {
      int size = newCouponRuleList.size();
      NewCouponRule ncr = (NewCouponRule) newCouponRuleList.get(0);
      if (ncr.getPersonalUseLimit().intValue() <= size) {
        addErrorMessage(Messages.getString("web.action.front.order.ShippingDiscountConfirmAction.4"));
        getBean().getCashier().setDiscount(new CashierDiscount());
      }
    }

    getBean().getCashier().setDiscount(new CashierDiscount());
    getBean().setPublicCouponCode("");
    // 2012-11-13 update by yyq end
    
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    
    Cashier cashier = getBean().getCashier();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    CashierDiscount cashierDiscount = new CashierDiscount();
    
    // 优惠券折扣
    if (getBean().getHiddenSelDiscountType().equals(DiscountType.COUPON.getValue())) {
      
      // 个人优惠券
      if (StringUtil.hasValue(getBean().getSelPersonalCouponCode())) {
        
        // 已选择个人优惠券信息取得
        MyCouponInfo aviableCouponSelected = getAviableCouponSelected();
        if (aviableCouponSelected != null && StringUtil.hasValue(aviableCouponSelected.getCouponIssueNo())) {
          cashierDiscount.setDiscountCode(aviableCouponSelected.getCouponCode());
          cashierDiscount.setDiscountName(utilService.getNameByLanguage(aviableCouponSelected.getCouponName(),
              aviableCouponSelected.getCouponNameEn(), aviableCouponSelected.getCouponNameJp()));
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
            BigDecimal usePrice;
            if (aviableCouponSelected.getObjectTotalPrice().compareTo(aviableCouponSelected.getMaxUseOrderAmount()) > 0) {
              usePrice = aviableCouponSelected.getMaxUseOrderAmount();
            } else {
              usePrice = aviableCouponSelected.getObjectTotalPrice();
            }
            discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(usePrice,
                aviableCouponSelected.getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
            if (aviableCouponSelected.getMaxUseOrderAmount() != null
                && discountPrice.compareTo(aviableCouponSelected.getMaxUseOrderAmount()) > 0) {
              discountPrice = aviableCouponSelected.getMaxUseOrderAmount();
            }
            cashierDiscount.setDiscountPrice(discountPrice);
            cashierDiscount.setDiscountRate(aviableCouponSelected.getCouponProportion());
          }
        }
        // 2013/04/16 优惠券对应 ob update end
        
      } else {
        // 公共优惠券
        if (!StringUtil.isNullOrEmpty(getBean().getPublicCouponCode())) {
          CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
          NewCouponRule newCouponRule = customerService.getPublicCoupon(getBean().getPublicCouponCode());
          if (newCouponRule != null) {
            // 折扣信息设定
            cashierDiscount.setDiscountCode(newCouponRule.getCouponCode());
            cashierDiscount.setDiscountName(utilService.getNameByLanguage(newCouponRule.getCouponName(), newCouponRule
                .getCouponNameEn(), newCouponRule.getCouponNameJp()));
            cashierDiscount.setDiscountType(DiscountType.COUPON.getValue());
            cashierDiscount.setDiscountMode(newCouponRule.getCouponIssueType().toString());
            cashierDiscount.setCouponType(CouponType.COMMON_DISTRIBUTION.getValue());
            if (newCouponRule.getCouponIssueType().equals(CouponIssueType.FIXED.longValue())) {
              cashierDiscount.setDiscountPrice(newCouponRule.getCouponAmount());
            } else {
              BigDecimal discountPrice = BigDecimalUtil.divide(BigDecimalUtil.multiply(cashier.getTotalCommodityPrice(),
                  newCouponRule.getCouponProportion()), 100, 2, RoundingMode.HALF_UP);
              cashierDiscount.setDiscountPrice(discountPrice);
              cashierDiscount.setDiscountRate(newCouponRule.getCouponProportion());
            }
          }
        }
      }
    } else {
      // 会员折扣
      CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign(getBean().getCashier());
      if (customerGroupCampaign != null) {
        // 折扣信息设定
        String campaignName = utilService.getNameByLanguage(customerGroupCampaign.getCampaignName(),
            customerGroupCampaign.getCampaignNameEn(), customerGroupCampaign.getCampaignNameJp());
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
      }
    }
    
    // 20120412 shen add start
    if (BigDecimalUtil.isAbove(cashierDiscount.getDiscountPrice(), cashier.getTotalCommodityPrice())) {
      cashierDiscount.setDiscountPrice(cashier.getTotalCommodityPrice());
    }
    // 20120412 shen add end

    cashier.setDiscount(cashierDiscount);
    
    ShippingHeaderBean selectShipping = getSelectShipping();
    // アドレスを変更している場合
    if (!NumUtil.toString(selectShipping.getAddress().getAddressNo()).equals(selectShipping.getNewAddressNo())) {
      for (ShippingDetailBean detail : selectShipping.getShippingList()) {
        // cashierからshippingを取得
        CashierShipping cashierShipping = cashier.getShipping(detail.getShopCode(), NumUtil.toLong(detail
            .getDeliveryTypeCode()), selectShipping.getAddress().getAddressNo());
        // cashierShippingの情報をもとに新たな配送先へ商品の追加
        CustomerAddress address = getCustomerAddress(cashier, selectShipping.getNewAddressNo());
        if (address == null) {
          throw new RuntimeException();
        }
        cashier.addCashierItem(address, cashierShipping.getDeliveryType(), cashierShipping.getCommodityInfoList());

        // 古い配送情報を削除
        cashier.removeShipping(cashierShipping);
      }
    }

    // 运费设定
    cashier.recomputeShippingCharge();

    ShippingBean bean = createShippingBeanFromCashier();

    // 重新设置礼品卡金额
    if (StringUtil.hasValue(bean.getUseAmountRight())) {
      bean.getCashier().setGiftCardUseAmount(BigDecimal.ZERO);
      if (BigDecimalUtil.isAbove(new BigDecimal(bean.getUseAmountRight()), bean.getCashier().getPaymentTotalPrice())) {
        bean.setUseAmountRight(bean.getCashier().getPaymentTotalPrice().toString());
        addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.17"));
      }
      bean.getCashier().setGiftCardUseAmount(new BigDecimal(bean.getUseAmountRight()));
    }
    createOutCardPrice();
    // 临时清空优惠券信息
    getBean().getCashier().setDiscount(new CashierDiscount());
    getBean().setPublicCouponCode("");
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /***
   * 変更後のアドレス情報を取得する
   * 
   * @param cashier
   * @param changeAddressNo
   * @return
   */
  private CustomerAddress getCustomerAddress(Cashier cashier, String changeAddressNo) {
    AddAddressBean addAddress = null;

    for (AddAddressBean usableAddress : getBean().getAddAddressCheckList()) {
      if (usableAddress.getAddressNo().equals(changeAddressNo)) {
        addAddress = usableAddress;
      }
    }
    return copyCustomerAddress(cashier, addAddress);
  }

  /**
   * 配送先情報を設定
   * 
   * @return shipping or null
   */
  public ShippingHeaderBean getSelectShipping() {
    String addressNo = getPathInfo(0);
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      if (NumUtil.toString(shipping.getAddress().getAddressNo()).equals(addressNo)) {
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

}
