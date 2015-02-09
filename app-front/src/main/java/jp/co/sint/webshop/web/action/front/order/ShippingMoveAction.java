package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.front.order.ConfirmBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShippingMoveAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;

    String move = getPathInfo(0);
    if (move.equals("cart") || move.equals("top")) {
      return true;
    }
    refreshItems(true, false);

    // 在庫・予約上限数チェック
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
    if (getBean().getMessage().length() > 350) {
      addErrorMessage(Messages.getString("web.action.front.order.ShippingMessage.01"));
      return false;
    }
    
    valid &= numberLimitValidation();

    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length < 1) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    if (move.equals("confirm")) {
      List<String> errorList = PaymentSupporterFactory.createPaymentSuppoerter().validatePayment(getBean().getOrderPayment());
      for (String error : errorList) {
        addErrorMessage(error);
        valid = false;
      }
      if (errorList.size() == 0) {// 选择支付方式后，验证配送公司
        if (getBean().getCashier().getDelivery() == null
            || (getBean().getCashier().getDelivery() != null && StringUtil.isNullOrEmpty(getBean().getCashier().getDelivery()
                .getDeliveryCompanyCode()))) {
          // 配送公司为空，或未选择时
          addErrorMessage(Messages.getString("web.action.front.order.ShippingMoveConfirmAction.0"));
          valid = false;
        }
      }
      // 各お支払い方法の上限金額を超えていないかどうかチェック
      BigDecimal totalAllPrice = BigDecimalUtil.add(getBean().getCashier().getTotalCommodityPrice(), getBean().getCashier()
          .getTotalShippingCharge());
      for (PaymentTypeBase payment : getBean().getOrderPayment().getDisplayPaymentList()) {
        if (payment.getPaymentMethodCode().equals(getBean().getOrderPayment().getPaymentMethodCode())) {
          if (!payment.canAccept(totalAllPrice)) {
            String max = NumUtil.formatCurrency(payment.getMaximum());
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_AMOUNT_OVER, payment.getPaymentMethodName(), max));
            valid = false;
          }
        }
      }

      // 发票验证
      InvoiceBean invoiceBean = getBean().getOrderInvoice();
      valid &= validateItems(invoiceBean, "invoiceFlg");
      
      if (valid && invoiceBean.getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
        if (StringUtil.getLength(invoiceBean.getInvoiceCustomerName()) > 50) {
          addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
              .getString("web.action.front.customer.CustomerEdit1NextAction.9")));
          valid &= false;
        }
        valid &= validateItems(invoiceBean, "invoiceType");
        if (valid && invoiceBean.getInvoiceType().equals(InvoiceType.VAT.getValue())) {
          valid &= validateItems(invoiceBean, "invoiceCommodityName", "invoiceCompanyName", "invoiceTaxpayerCode",
              "invoiceAddress", "invoiceTel", "invoiceBankName", "invoiceBankNo");

          // 2013-02-19 add by yyq 验证纳税人识别号必须是输入15或18或20位 start
          if (StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 15
              && StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 18
              && StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 20) {
            addErrorMessage(WebMessage.get(ValidationMessage.INVOICE_TAXPAYER_CODE_LENGTH_ERR, Messages
                .getString("web.action.front.order.ShippingMoveAction.3")));
            valid &= false;
          }
          // 2013-02-19 add by yyq 验证纳税人识别号必须是输入15或18或20位 end

          if (StringUtil.getLength(invoiceBean.getInvoiceAddress()) > 200) {
            addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, Messages
                .getString("web.action.front.order.ShippingMoveAction.0")));
            valid &= false;
          }
          if (StringUtil.getLength(invoiceBean.getInvoiceCompanyName()) > 70) {
            addErrorMessage(WebMessage.get(ValidationMessage.COMPANYNAME_LENGTH_ERR, Messages
                .getString("web.action.front.order.ShippingMoveAction.1")));
            valid &= false;
          }
          if (StringUtil.getLength(invoiceBean.getInvoiceBankName()) > 70) {
            addErrorMessage(WebMessage.get(ValidationMessage.BANKNAME_LENGTH_ERR, Messages
                .getString("web.action.front.order.ShippingMoveAction.2")));
            valid &= false;
          }
        } else if (valid) {
          valid &= validateItems(invoiceBean, "invoiceCommodityName", "invoiceCustomerName");
        }
      }
    }
    String mobileNumber = "";
    String phoneNumber = "";
    String nameAddress = "";
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    for (ShippingHeaderBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingList()){
        for(ShippingCommodityBean commodity : detail.getCommodityList()){
          if (commodity.getIsDiscountCommodity().equals("true")){
            //历史所有客户购买总数
            Long siteTotalAmount = catalogService.getHistoryBuyAmountTotal(commodity.getSkuCode());
            if (siteTotalAmount == null){
              siteTotalAmount = 0L;
            }
            String customerCode = getLoginInfo().getCustomerCode();
            if (StringUtil.hasValue(customerCode) && !customerCode.equals("guest")) {
              DiscountCommodity dcBean = catalogService.getDiscountCommodityByCommodityCode(commodity.getSkuCode());
              //限购商品剩余可购买数量
              Long avalibleAmountTotal = dcBean.getSiteMaxTotalNum() - siteTotalAmount;
              if (avalibleAmountTotal <= 0L){
                addErrorMessage(commodity.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.0") );
                return false;
              }
              Long historyNum = catalogService.getHistoryBuyAmount(commodity.getSkuCode(), customerCode);
              if (historyNum == null){
                historyNum = 0L;
              }
              if (dcBean.getCustomerMaxTotalNum() > historyNum){
                Long num = dcBean.getCustomerMaxTotalNum() - historyNum;
                if (num > avalibleAmountTotal){
                  num = avalibleAmountTotal;
                }
                if (Long.parseLong(commodity.getCommodityAmount()) > num ){
                  addErrorMessage(commodity.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.1") + num + Messages.getString("web.action.front.order.DiscountCommodity.2") );
                  return false;
                }
              } else {
                addErrorMessage(commodity.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.3"));
                return false ;
              }
            }
          }
        }
      }
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
        return false;
      }
    }
//    BigDecimal outerCardTop = DIContainer.getWebshopConfig().getOuterCardTop();
//    if (PaymentMethodType.OUTER_CARD.getValue().equals(getBean().getCashier().getPayment().getSelectPayment().getPaymentMethodType())) {
//      if(BigDecimalUtil.isAbove(getBean().getCashier().getPaymentTotalPrice(), outerCardTop)) {
//        addErrorMessage("外卡支付金额不能超过1000。");
//        return false;
//      }
//    }
    valid &= couponValidation();
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String move = getPathInfo(0);
    if (move.equals("new_address")) {
      DisplayTransition.add(getBean(), "/app/order/shipping", getSessionContainer());
      setNextUrl("/app/mypage/address_edit/init");
    } else if (move.equals("cart")) {
      setNextUrl("/app/cart/cart");
    } else if (move.equals("top")) {
      setNextUrl("/app/common/index");
    } else if (move.equals("confirm")) {


      // 画面のポイント情報を設定
      createCashierFromDisplay();

      // confirmに対してcasheirを設定する
      ConfirmBean bean = new ConfirmBean();
      bean.setCashier(getBean().getCashier());
      setRequestBean(bean);
      setNextUrl("/app/order/confirm");
    }
    recastCart();

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
