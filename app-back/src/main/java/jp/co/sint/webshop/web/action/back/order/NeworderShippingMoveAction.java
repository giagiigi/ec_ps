package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.back.order.NeworderConfirmBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderBaseBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryCompanyBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean.OrderCommodityBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020130:新規受注(配送先設定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderShippingMoveAction extends NeworderShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    for (DeliveryBean shipping : getBean().getDeliveryList()) {
      valid &= validateBean(shipping);
      for (OrderCommodityBean commodity : shipping.getOrderCommodityList()) {
        valid &= validateBean(commodity);
        valid &= discountCouponUseValidation(commodity.getDiscountCouponCode());
        CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
        if (commodity.getIsDiscountCommodity().equals("true")){
          //历史所有客户购买总数
          Long siteTotalAmount = catalogService.getHistoryBuyAmountTotal(commodity.getSkuCode());
          if (siteTotalAmount == null){
            siteTotalAmount = 0L;
          }
          String customerCode = getBean().getCustomerCode();
          if (StringUtil.hasValue(customerCode)) {
            DiscountCommodity dcBean = catalogService.getDiscountCommodityByCommodityCode(commodity.getSkuCode());
            //限购商品剩余可购买数量
            Long avalibleAmountTotal = dcBean.getSiteMaxTotalNum() - siteTotalAmount;
            if (avalibleAmountTotal <= 0L){
              addErrorMessage(commodity.getCommodityName() + "限购活动可购买数量为0。" );
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
              if (Long.parseLong(commodity.getPurchasingAmount()) > num ){
                addErrorMessage(commodity.getCommodityName() + "限购活动最多可购买数" + num + "个。" );
                return false;
              }
            } else {
              addErrorMessage(commodity.getCommodityName() + "限购活动 已购买过个人最大数量 " + dcBean.getCustomerMaxTotalNum() + "个。");
              return false ;
            }
          }
        }
      }
    }

    if (valid) {
      valid &= minStockValidation();
      if (valid) {
        valid &= shippingValidation();
        copyBeanToCashier();
        valid &= numberLimitValidation(getBean());  
      }
      
      if (valid) {
        recreateOtherGiftList();
        valid &= allCommoditysStockValidation();
        valid &= discountCouponLimtedValidation();
      }
    }

      List<String> errorList = PaymentSupporterFactory.createPaymentSuppoerter().validatePayment(getBean().getOrderPayment());
      for (String error : errorList) {
        addErrorMessage(error);
        valid = false;
      }

      if (valid){//选择支付方式后，验证配送公司
        if(getBean().getCashier().getDelivery() == null || 
            (getBean().getCashier().getDelivery() != null && StringUtil.isNullOrEmpty(getBean().getCashier().getDelivery().getDeliveryCompanyCode()))){
          //配送公司为空，或未选择时
          addErrorMessage(Messages.getString("web.action.back.order.ShippingMoveConfirmAction.0"));
          valid = false;
        }
        
        if (getBean().getCashier().getDelivery() != null && !StringUtil.isNullOrEmpty(getBean().getCashier().getDelivery().getDeliveryCompanyCode())) {
          List<DeliveryCompanyBean> dcList = new ArrayList<DeliveryCompanyBean>();
          dcList = getBean().getDeliveryCompanyList();
          int tmpI = 0;
          for (DeliveryCompanyBean deliveryCompanyBean : dcList) {
            if(deliveryCompanyBean.getDeliveryCompanyNo().equals(getBean().getCashier().getDelivery().getDeliveryCompanyCode())){
              tmpI++;
              continue;
            }
          }
          if (tmpI == 0){
            addErrorMessage(Messages.getString("web.action.back.order.ShippingMoveConfirmAction.0"));
            valid = false;
          }
        }
      }
      
      NewCouponRule newCouponRule = getPublicCoupon(getBean().getPublicCouponCode());
      if (newCouponRule != null) {
          // 优惠券使用订购金额限制
          if (BigDecimalUtil.isBelow(getBean().getCashier().getTotalCouponCommodityPrice(newCouponRule.getObjectCommodities()), newCouponRule.getMinUseOrderAmount())) {
            addErrorMessage("指定商品订购未满"+newCouponRule.getMinUseOrderAmount().toString()+"元不能使用");
            getBean().getCashier().setDiscount(new CashierDiscount());
            return false;
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
      if (invoiceBean.getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
        if (invoiceBean.getInvoiceType().equals(InvoiceType.VAT.getValue())) {
          valid &= validateItems(invoiceBean, "invoiceCommodityName", "invoiceCompanyName", "invoiceTaxpayerCode",
              "invoiceAddress", "invoiceTel", "invoiceBankName", "invoiceBankNo");
          // 2013-02-19 add by yyq 验证纳税人识别号必须是输入15或18或20位 start
          if (StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 15
              && StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 18
              && StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 20) {
            addErrorMessage("纳税人识别号位数不正确，有效长度为15、18、20位");
            valid &= false;
          }
          // 2013-02-19 add by yyq 验证纳税人识别号必须是输入15或18或20位 end
        } else {
          valid &= validateItems(invoiceBean, "invoiceCommodityName", "invoiceType", "invoiceCustomerName");
        }
      }

      if (StringUtil.getLength(invoiceBean.getInvoiceCustomerName()) > 50) {
          addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, "发票内容(发票抬头)"));
          valid &= false;
        }
      
      if (StringUtil.getLength(invoiceBean.getInvoiceCompanyName()) > 70) {
        addErrorMessage(WebMessage.get(ValidationMessage.INVOICE_LENGTH_ERR, "发票内容(公司名称)"));
        valid &= false;
      }
      
      if (StringUtil.getLength(invoiceBean.getInvoiceAddress()) > 200) {
        addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, "发票内容(住所)"));
        valid &= false;
      }

      if (StringUtil.getLength(invoiceBean.getInvoiceBankName()) > 70) {
        addErrorMessage(WebMessage.get(ValidationMessage.INVOICE_LENGTH_ERR, "发票内容(银行名称)"));
        valid &= false;
      }

    // if
    // (PaymentMethodType.OUTER_CARD.getValue().equals(getBean().getCashier().getPayment().getSelectPayment().getPaymentMethodType()))
    // {
    // if(BigDecimalUtil.isAbove(getBean().getCashier().getPaymentTotalPrice(),
    // new BigDecimal("1000"))) {
    // addErrorMessage("外卡支付金额不能超过1000。");
    // valid &= false;
    // }
    // }
      
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	    
	  if (getBean().getCashier().getDiscount() != null && StringUtil.hasValue(getBean().getCashier().getDiscount().getDiscountCode())) {
      if (!checkDiscountInfo(getBean().getCashier())) {
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    // 2013/04/17 优惠券对应 ob add end

    if (isCashOnDeliveryOnly()) {
      NeworderShippingBean newOrderShippingBean = getBean();
      newOrderShippingBean.setAvailableAddDelivery(false);
      setRequestBean(newOrderShippingBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    super.createCashierFromDisplay();
    super.copyBeanToCashier();
    setNextUrl("/app/order/neworder_confirm/init/");
    NeworderConfirmBean nextBean = new NeworderConfirmBean();
    nextBean.setCashier(getBean().getCashier());
    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderShippingMoveAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013005";
  }

}
