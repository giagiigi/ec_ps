package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
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
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.AddAddressBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class NeworderShippingChangeAddressAction extends NeworderShippingBaseAction {

  @Override
  public boolean validate() {

    String addressNo = getPathInfo(0);
    if (!StringUtil.hasValue(addressNo)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    getBean().getCashier().setDiscount(new CashierDiscount());
    getBean().setPublicCouponCode("");
    DeliveryBean selectShipping = getSelectShipping();
    if (selectShipping == null) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    List<AddAddressBean> list = getBean().getAddAddressCheckList();
    if (list == null || list.size() == 0) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    } else {
      for (AddAddressBean info : list) {
        if (info.getAddressNo().equals(getBean().getShippingAddress())) {
          return true;
        }
      }
      return false;
    }
  }

  @Override
  public WebActionResult callService() {
//    Cashier cashier = getBean().getCashier();
//    DeliveryBean selectShipping = getSelectShipping();
    Cashier cashier = getBean().getCashier();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    CashierDiscount cashierDiscount = new CashierDiscount();
    
    // 优惠券折扣
    if (StringUtil.hasValue(getBean().getHiddenSelDiscountType())) {
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
    }
   
    
    // 20120412 shen add start
    if (BigDecimalUtil.isAbove(cashierDiscount.getDiscountPrice(), cashier.getTotalCommodityPrice())) {
      cashierDiscount.setDiscountPrice(cashier.getTotalCommodityPrice());
    }
    // 20120412 shen add end

    cashier.setDiscount(cashierDiscount);
    
    DeliveryBean selectShipping = getSelectShipping();
    // アドレスを変更している場合
    if (!NumUtil.toString(selectShipping.getAddressNo()).equals(getBean().getShippingAddress())) {
      CashierShipping cashierShipping = cashier.getShipping(selectShipping.getShopCode(), NumUtil.toLong(selectShipping
          .getDeliveryCode()), selectShipping.getAddressNo());
      CustomerAddress address = getCustomerAddress(cashier, getBean().getShippingAddress());
      if (address == null) {
        throw new RuntimeException();
      }
      cashier.addCashierItem(address, cashierShipping.getDeliveryType(), cashierShipping.getCommodityInfoList());
      cashier.removeShipping(cashierShipping);
      cashier.recomputeShippingCharge();
    }

    Collections.sort(cashier.getShippingList(), new ShippingComparator());
    super.createCashierFromDisplay();
    isCashOnDeliveryOnly();
    NeworderShippingBean bean = super.createBeanFromCashier();
    createOutCardPrice();
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
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
  public DeliveryBean getSelectShipping() {
    String addressNo = getPathInfo(0);
    for (DeliveryBean shipping : getBean().getDeliveryList()) {
      if (NumUtil.toString(shipping.getAddressNo()).equals(addressNo)) {
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
    return Messages.getString("web.action.back.order.NeworderShippingChangeAddressAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013008";
  }
}
