package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.order.CommodityOfCartInfo;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeCvs;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.PointBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.TotalPrice;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020230:受注修正のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class OrderModifyMoveConfirmAction extends OrderModifyBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    PointBean pointInfo = getBean().getPointInfo();

    if (!isChangeAbleOrder()) {
      return false;
    }

    boolean valid = validateBean(pointInfo);

    // 利用ポイントが使用可能ポイントより多い場合エラー
    if (pointInfo.isShort()) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.OVER_USABLE_POINT));
      valid = false;
    }
    valid &= validateBean(getBean().getOrderHeaderEdit());

    List<String> errors = PaymentSupporterFactory.createPaymentSuppoerter().validatePayment(getBean().getOrderPayment());
    for (String error : errors) {
      addErrorMessage(error);
      valid = false;
    }
    // コンビニ決済だった場合、受取人名カナがあわせて15文字以上だった場合エラー
    for (PaymentTypeBase payment : getBean().getOrderPayment().getDisplayPaymentList()) {
      if (payment.isCvsPayment()) {
        PaymentTypeCvs cvsPayment = (PaymentTypeCvs) payment;
        if ((cvsPayment.getFirstNameKana() + cvsPayment.getLastNameKana()).length() > 15) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.STRING_OVER, Messages
              .getString("web.action.back.order.OrderModifyMoveConfirmAction.0"), "15"));
          valid = false;
        }
      }
      // 各支払方法の上限金額を超えていないかどうかチェック


      if (payment.getPaymentMethodCode().equals(getBean().getOrderPayment().getPaymentMethodCode())) {
        if (!payment.canAccept(getBean().getPointInfo().getTotalPrice())) {
          String max = NumUtil.formatCurrency(payment.getMaximum());
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_AMOUNT_OVER, payment.getPaymentMethodName(), max));
          valid = false;
        }
      }
    }
    // soukai add 2012/01/10 ob start
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
    
    if (StringUtil.getLength(invoiceBean.getInvoiceCustomerName()) > 40) {
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
    
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().getOrderHeaderEdit().getAddress().setCityList(
        s.getCityNames(getBean().getOrderHeaderEdit().getAddress().getPrefectureCode()));
    if (StringUtil.isNullOrEmpty(getBean().getShippingHeaderList().get(0).getDeliveryCompanyNo())) {
      addErrorMessage("请选择配送公司");
      valid &= false;
    }
    return valid & validateItems(getBean(), "shippingCharge", "discountPrice");
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderModifyBean bean = getBean();

    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    ServiceResult result =null;
    
    if (bean.getNewCashierDiscount()!=null && StringUtil.hasValue(bean.getNewCashierDiscount().getDiscountCode())){
      
      CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
      List<CommodityOfCartInfo> commodityListOfCart = new ArrayList<CommodityOfCartInfo>();
      
      for (ShippingHeaderBean shipping : getBean().getShippingHeaderList()) {
        for (ShippingDetailBean detail : shipping.getShippingDetailList()) {
          CommodityOfCartInfo commodityOfCart = new CommodityOfCartInfo();
          OrderDetail orderDetailCommodityInfo = detail.getOrderDetailCommodityInfo();
          commodityOfCart.setCommodityCode(orderDetailCommodityInfo.getCommodityCode());
          commodityOfCart.setCommodityName(orderDetailCommodityInfo.getCommodityName());
          commodityOfCart.setRetailPrice(orderDetailCommodityInfo.getRetailPrice());
          commodityOfCart.setPurchasingAmount(orderDetailCommodityInfo.getPurchasingAmount());
          commodityOfCart.setBrandCode(orderDetailCommodityInfo.getBrandCode());
          CommodityHeader ch = chDao.load(orderDetailCommodityInfo.getShopCode(), orderDetailCommodityInfo.getCommodityCode());
          commodityOfCart.setImportCommodityType(ch.getImportCommodityType());
          commodityListOfCart.add(commodityOfCart);
        }
      }
      
      result = orderService.checkDiscountInfo(bean.getOrderHeaderEdit().getCustomerCode(),
          bean.getNewCashierDiscount().getCouponType(),bean.getNewCashierDiscount().getDiscountCode(),
          bean.getNewCashierDiscount().getDiscountDetailCode(),bean.getAfterTotalPrice().getCommodityPrice(), commodityListOfCart);
    }
    
    if (result !=null && result.hasError()) {
    	for (ServiceErrorContent error : result.getServiceErrorList()) {
    		if (error == OrderServiceErrorContent.DISCOUNT_INFO_NOT_EXITS_ERROR) {
          addErrorMessage("选择的优惠已被删除或者利用期间外。");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
        if (error == OrderServiceErrorContent.DISCOUNT_COUNT_OVER_ERROR) {
          addErrorMessage("选择的优惠已超过最大利用可能回数。");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
        if (error == OrderServiceErrorContent.DISCOUNT_COUPON_USED_ERROR) {
          addErrorMessage("选择的优惠券已使用。");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
        if (error == OrderServiceErrorContent.DISCOUNT_MIN_ORDER_PRICE_ERROR) {
          addErrorMessage("本次商品购买金额小于使用优惠的最小购买金额。");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
    		
    		if (error == OrderServiceErrorContent.DISCOUNT_COUNT_ISSUE_BY_MYSELF_ERROR) {
    		  addErrorMessage("不能使用顾客本人发行的优惠券。");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
    		}
    	}
    } 
    if (result !=null && !result.hasError()) {
    	for (ShippingHeaderBean headerBean : getBean().getShippingHeaderList()) {
    		for (ShippingDetailBean detailBean : headerBean.getShippingDetailList()) {
    			if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())
    					&& StringUtil.hasValue(detailBean.getCampaignName())) {
    				addErrorMessage("折扣券和优惠券不可同时使用。");
      	          	setRequestBean(bean);
      	          	return BackActionResult.RESULT_SUCCESS;
    			}
    		}
    	}
    }
    this.recomputeGift(bean, true);
    recomputePoint(bean);

    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();
    PaymentTypeBase selectPayment = supporter.getSelectPaymentType(bean.getOrderPayment());
    List<String> errors = PaymentSupporterFactory.createPaymentSuppoerter().validatePayment(getBean().getOrderPayment());
    if (errors.size() > 0) {
      for (String error : errors) {
        addErrorMessage(error);
      }
      setRequestBean(bean);

      return BackActionResult.RESULT_SUCCESS;
    }
    if (numberLimitValidation(createOrderContainer(bean))) {
      bean.setOperationMode("confirm");
      bean.setDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      bean.setDisplayUpdateButton(false);
      addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));
    }

    TotalPrice afterTotalPrice = bean.getAfterTotalPrice();

    if (bean.getOrderPayment().getPaymentMethodCode().equals(selectPayment.getPaymentMethodCode())) {
      if (selectPayment.getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
      } else {
        afterTotalPrice.setAllPrice(afterTotalPrice.getAllPrice());
      }
      afterTotalPrice.setPaymentCommission(selectPayment.getPaymentCommission());
      if (selectPayment.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        afterTotalPrice.setNotPointInFull(false);
      } else {
        afterTotalPrice.setNotPointInFull(true);
      }
    }
    bean.setDiscountTypeList(createDiscountTypeList());
    bean.setPersonalCouponList(createPersonalCouponList());
    recomputePaymentCommission(bean);
    
    
    if (StringUtil.isNullOrEmpty(bean.getShippingCharge())) {
    	
    	if (bean.getAfterTotalPrice().getShippingCharge()!=null){
        	bean.setShippingCharge(bean.getAfterTotalPrice().getShippingCharge().toString());
        } else {
        	bean.setShippingCharge("0");
        }
    } else {
    	for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
    		header.setShippingCharge(bean.getShippingCharge());
    	}
    }
    
    recomputePrice(bean);
    
    if (StringUtil.isNullOrEmpty(bean.getDiscountPrice())){
    	if (bean.getAfterTotalPrice()!=null) {
        	bean.setDiscountPrice(bean.getAfterTotalPrice().getDiscountPrice().toString());
        } else {
        	bean.setDiscountPrice("0");
        }
    } else {
    	bean.getAfterTotalPrice().setDiscountPrice(new BigDecimal(bean.getDiscountPrice()));
    }
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
    return Messages.getString("web.action.back.order.OrderModifyMoveConfirmAction.1");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023006";
  }

}
