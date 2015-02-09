package jp.co.sint.webshop.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.MobileDomain;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.BankDao;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.PointRuleDao;
import jp.co.sint.webshop.data.domain.AccountType;
import jp.co.sint.webshop.data.domain.ClientSmsType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.MailSendStatus;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.SmsSendStatus;
import jp.co.sint.webshop.data.domain.SmsType;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.MobileAuth;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderSmsHistory;
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.data.dto.RespectiveSmsqueue;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SmsingService;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeCvs;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeDigitalCash;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.service.order.PaymentProviderManager;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.result.SmsingServiceErrorContent;
import jp.co.sint.webshop.service.shop.ShopManagementSimpleSql;
import jp.co.sint.webshop.service.shop.SmsTemplateSuite;
import jp.co.sint.webshop.service.sms.ClientSmsTypeCondition;
import jp.co.sint.webshop.sms.ArrivalTag;
import jp.co.sint.webshop.sms.CouponEndTag;
import jp.co.sint.webshop.sms.OrderHeaderTag;
import jp.co.sint.webshop.sms.PaymentTag;
import jp.co.sint.webshop.sms.ShippingDetailTag;
import jp.co.sint.webshop.sms.ShippingHeaderTag;
import jp.co.sint.webshop.sms.ShippingTag;
import jp.co.sint.webshop.sms.ShopInfoTag;
import jp.co.sint.webshop.sms.SmsComposition;
import jp.co.sint.webshop.sms.SmsInfo;
import jp.co.sint.webshop.sms.SmsSender;
import jp.co.sint.webshop.sms.SmsTagDataList;
import jp.co.sint.webshop.sms.SmsTemplateExpander;
import jp.co.sint.webshop.sms.SmsTemplateTag;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.ExPrintWriter;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.VerifyCode;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

public class SmsingServiceImpl extends AbstractServiceImpl implements
		SmsingService {

	private static final long serialVersionUID = 1L;

	private ServiceResult sendOrderSms(OrderContainer orderContainer,
			CashierPayment payment, Shop shop, boolean newOrderFlg,
			ClientSmsType clientSmsType) {

		ServiceResultImpl result = new ServiceResultImpl();

		OrderHeader orderHeader = orderContainer.getOrderHeader();

		String toMobile = orderHeader.getMobileNumber();

		// 顧客の場合、顧客テーブルからメールアドレスと取得する

		if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
			if (orderHeader.getCustomerCode() == null) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				return result;
			}
			CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
			Customer customer = customerDao.load(orderHeader.getCustomerCode());
			if (customer == null) {
				result
						.addServiceError(SmsingServiceErrorContent.NO_CUSTOMER_TO_SEND_SMS);
				return result;
			}
		}
		// delete by V10-CH start
		// orderContainer.getOrderHeader().setMobileNumber(toMobile);
		// delete by V10-CH end

		SmsType smsType;

		if (orderContainer.getOrderHeader().getOrderStatus().equals(
				OrderStatus.RESERVED.longValue())) {
			smsType = SmsType.RESERVATION_DETAIL;
		} else if (clientSmsType == ClientSmsType.PC) {
			smsType = SmsType.ORDER_DETAILS_PC;
		} else {
			smsType = SmsType.ORDER_DETAILS_PC;
		}

		if (noSetSmsTemplate(shop.getShopCode(), smsType.getValue(), 0L)) {
			result
					.addServiceError(SmsingServiceErrorContent.NO_SMS_TEMPLATE_ERROR);
			return result;
		}

		SmsTemplateSuite smsTemplateSuite = createSmsTemplate(shop
				.getShopCode(), smsType.getValue(), 0L);

		SmsComposition mainComposition = null;
		// SmsComposition modifyMessageComposition = null;
		SmsComposition shippingHeaderComposition = null;
		SmsComposition shippingDetailComposition = null;
		SmsComposition paymentComposition = null;
		SmsComposition signComposition = null;

		mainComposition = SmsComposition.ORDER_DETAILS_PC_MAIN;
		// modifyMessageComposition = SmsComposition.ORDER_DETAILS_MOBILE_MAIN;
		shippingHeaderComposition = SmsComposition.ORDER_DETAILS_PC_MAIN;
		shippingDetailComposition = SmsComposition.ORDER_DETAILS_PC_MAIN;
		paymentComposition = SmsComposition.ORDER_DETAILS_PC_MAIN;
		signComposition = SmsComposition.ORDER_DETAILS_PC_MAIN;

		// 税込価格表示を追記

		setTaxIncludedPriceDisplay(smsTemplateSuite);

		SmsTemplateExpander expander = new SmsTemplateExpander(smsTemplateSuite);

		setOrderHeaderTag(expander, mainComposition, orderContainer);

		if (newOrderFlg
				&& !orderContainer.getOrderHeader().getOrderStatus().equals(
						OrderStatus.RESERVED.longValue())) {
			// for (SmsTemplateDetail detail :
			// smsTemplateSuite.getSmsTemplateDetail()) {
			// if
			// (detail.getSmsCompositionName().equals(modifyMessageComposition.getName()))
			// {
			// detail.setSmsText("");
			// }
			// }
		}
		if (orderContainer.getOrderHeader().getOrderStatus().equals(
				OrderStatus.RESERVED.longValue())) {
			setReservationShippingTag(expander, shippingHeaderComposition,
					orderContainer, shippingDetailComposition);
		} else {
			setShippingHeaderTag(expander, shippingHeaderComposition,
					orderContainer, shippingDetailComposition);
		}
		setPaymentTag(expander, paymentComposition, orderHeader, payment);
		setShopInfoTag(expander, signComposition, shop);

		RespectiveSmsqueue queue = createSmsQueue(smsTemplateSuite, toMobile,
				SmsSendStatus.NOT_SENT.longValue(), expander.expandTemplate());

		ServiceResult sendSmsResult = sendRespectiveSms(queue);

		if (sendSmsResult.hasError()) {
			for (ServiceErrorContent error : sendSmsResult
					.getServiceErrorList()) {
				result.addServiceError(error);
			}
		}
		return result;
	}

	private SmsTemplateSuite createSmsTemplate(String shopCode, String smsType,
			Long smsTemplateNo) {
		SmsTemplateSuite suite = new SmsTemplateSuite();

		Query query = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL, smsType);
		List<SmsTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(query,
				SmsTemplateDetail.class);
		suite.setSmsTemplateDetail(detailList);

		return suite;
	}

  //2013/05/05 优惠券对应 ob add start
	/**
	 * 
	 */
  private SmsTemplateSuite createSmsTemplate(String shopCode, String smsType,
      Long smsTemplateNo,String customerCode) {
    Query queryCustomer = null;
    List<SmsTemplateDetail> detailList = null;
    queryCustomer = new SimpleQuery("SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?", customerCode);
    Customer customerBean = DatabaseUtil.loadAsBean(queryCustomer, Customer.class);
    SmsTemplateSuite suite = new SmsTemplateSuite();
    
    if (customerBean != null) {
      if (customerBean.getLanguageCode().equals(LanguageCode.En_Us.getValue())) {
        Query query = new SimpleQuery(
        ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL_EN, smsType);
        detailList = DatabaseUtil.loadAsBeanList(query,SmsTemplateDetail.class);        
      } else if (customerBean.getLanguageCode().equals(LanguageCode.Ja_Jp.getValue())) {
        Query query = new SimpleQuery(
        ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL_JP, smsType);
        detailList = DatabaseUtil.loadAsBeanList(query,SmsTemplateDetail.class);
      } else {
        Query query = new SimpleQuery(
            ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL_CN, smsType);
        detailList = DatabaseUtil.loadAsBeanList(query,SmsTemplateDetail.class);
      }
    } else {
      Query query = new SimpleQuery(
          ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL_CN, smsType);
      detailList = DatabaseUtil.loadAsBeanList(query, SmsTemplateDetail.class);
    }
    suite.setSmsTemplateDetail(detailList);

    return suite;
  }
  //2013/05/05 优惠券对应 ob add end
	private void setTaxIncludedPriceDisplay(SmsTemplateSuite smsTemplateSuite) {
		// String lineFeed = "";
		// lineFeed = "\r\n";//只有TEXT一种
		// for (SmsTemplateDetail detail :
		// smsTemplateSuite.getSmsTemplateDetail())
		// {
		// detail.setSmsText(detail.getSmsText() + lineFeed +
		// Messages.getString("service.impl.SmsingServiceImpl.4"));
		// }
	}

	private void setOrderHeaderTag(SmsTemplateExpander expander,
			SmsComposition composition, OrderContainer orderContainer) {

		SmsTagDataList orderHeaderTagList = new SmsTagDataList();
		orderHeaderTagList.add(OrderHeaderTag.ORDER_NO, orderContainer
				.getOrderHeader().getOrderNo());
		orderHeaderTagList.add(OrderHeaderTag.ORDER_DATETIME, DateUtil
				.toDateTimeString(orderContainer.getOrderHeader()
						.getOrderDatetime()));
		orderHeaderTagList.add(OrderHeaderTag.LAST_NAME, orderContainer
				.getOrderHeader().getLastName());
		orderHeaderTagList.add(OrderHeaderTag.EMAIL, orderContainer
				.getOrderHeader().getEmail());
		orderHeaderTagList.add(OrderHeaderTag.POSTAL_CODE, WebUtil
				.convertPostalCode(orderContainer.getOrderHeader()
						.getPostalCode()));
		orderHeaderTagList.add(OrderHeaderTag.ADDRESS1, orderContainer
				.getOrderHeader().getAddress1());
		orderHeaderTagList.add(OrderHeaderTag.ADDRESS2, orderContainer
				.getOrderHeader().getAddress2());
		orderHeaderTagList.add(OrderHeaderTag.ADDRESS3, orderContainer
				.getOrderHeader().getAddress3());
		orderHeaderTagList.add(OrderHeaderTag.PHONE_NUMBER, orderContainer
				.getOrderHeader().getPhoneNumber());
		orderHeaderTagList.add(OrderHeaderTag.MOBILE_NUMBER, orderContainer
				.getOrderHeader().getMobileNumber());
		orderHeaderTagList.add(OrderHeaderTag.MESSAGE, orderContainer
				.getOrderHeader().getMessage());
		// ご注文合計金額（全配送先小計＋支払い手数料）
		BigDecimal orderTotalPrice = BigDecimal.ZERO;
		BigDecimal orderTotalAcquiredPoint = BigDecimal.ZERO;
		BigDecimal orderTotalCommodityprice = BigDecimal.ZERO;
		BigDecimal orderTotalShippingCharge = BigDecimal.ZERO;
		BigDecimal orderTotalGiftPrice = BigDecimal.ZERO;
		for (ShippingContainer shipping : orderContainer.getShippings()) {
			ShippingHeader header = shipping.getShippingHeader();
			for (ShippingDetail detail : shipping.getShippingDetails()) {
				BigDecimal retailPrice = detail.getRetailPrice();
				BigDecimal giftPrice = detail.getGiftPrice();
				orderTotalPrice = orderTotalPrice.add(BigDecimalUtil.multiply(
						retailPrice.add(giftPrice), detail
								.getPurchasingAmount()));
				orderTotalCommodityprice = orderTotalCommodityprice
						.add(BigDecimalUtil.multiply(retailPrice, detail
								.getPurchasingAmount()));
				orderTotalGiftPrice = orderTotalGiftPrice.add(BigDecimalUtil
						.multiply(giftPrice, detail.getPurchasingAmount()));
			}
			orderTotalPrice = orderTotalPrice.add(header.getShippingCharge());
			orderTotalAcquiredPoint = BigDecimalUtil.add(
					orderTotalAcquiredPoint, header.getAcquiredPoint());
			orderTotalShippingCharge = orderTotalShippingCharge.add(header
					.getShippingCharge());
		}
		orderTotalPrice = orderTotalPrice.add(orderContainer.getOrderHeader()
				.getPaymentCommission());
		// お支払い合計金額
		BigDecimal paymentPrice = PointUtil
				.getTotalPyamentPrice(orderTotalPrice, orderContainer
						.getOrderHeader().getUsedPoint());

		PointRuleDao dao = DIContainer.getDao(PointRuleDao.class);

		if (dao.loadAll().get(0).getPointFunctionEnabledFlg().equals(
				PointFunctionEnabledFlg.ENABLED.longValue())) {
			StringWriter s = new StringWriter();
			PrintWriter out = new ExPrintWriter(s);
			out.println(Messages.getString("service.impl.SmsingServiceImpl.21")
					+ NumUtil.formatNumber(orderContainer.getOrderHeader()
							.getUsedPoint())
					+ Messages.getString("service.impl.SmsingServiceImpl.22"));
			out.println(Messages.getString("service.impl.SmsingServiceImpl.23")
					+ NumUtil.formatNumber(orderTotalAcquiredPoint)
					+ Messages.getString("service.impl.SmsingServiceImpl.24"));
			out.println(Messages.getString("service.impl.SmsingServiceImpl.25")
					+ StringUtil.coalesce(Price.getFormatPrice(paymentPrice),
							""));
			orderHeaderTagList.add(OrderHeaderTag.PAYMENT_TOTAL_PRICE, s
					.toString());
		} else {
			StringWriter s = new StringWriter();
			PrintWriter out = new PrintWriter(s);
			if (ValidatorUtil.moreThan(orderContainer.getOrderHeader()
					.getUsedPoint(), BigDecimal.ZERO)) {
				// if (orderContainer.getOrderHeader().getUsedPoint() > 0) {
				out
						.println(Messages
								.getString("service.impl.SmsingServiceImpl.26")
								+ NumUtil.formatNumber(orderContainer
										.getOrderHeader().getUsedPoint())
								+ Messages
										.getString("service.impl.SmsingServiceImpl.27"));
			}
			out.println(Messages.getString("service.impl.SmsingServiceImpl.28")
					+ StringUtil.coalesce(Price.getFormatPrice(paymentPrice),
							""));
			orderHeaderTagList.add(OrderHeaderTag.PAYMENT_TOTAL_PRICE, s
					.toString());
		}

		orderHeaderTagList.add(OrderHeaderTag.COMMODITY_TOTAL_PRICE, StringUtil
				.coalesce(Price.getFormatPrice(orderTotalCommodityprice)));
		orderHeaderTagList.add(OrderHeaderTag.SHIPPING_CHARGE_TOTAL_PRICE,
				StringUtil.coalesce(Price
						.getFormatPrice(orderTotalShippingCharge)));
		orderHeaderTagList.add(OrderHeaderTag.GIFT_TOTAL_PRICE, StringUtil
				.coalesce(Price.getFormatPrice(orderTotalGiftPrice)));
		orderHeaderTagList.add(OrderHeaderTag.COMMISSION_PRICE, StringUtil
				.coalesce(Price.getFormatPrice(orderContainer.getOrderHeader()
						.getPaymentCommission())));

		expander.addTagDataList(composition.getType().getValue(),
				orderHeaderTagList);

	}

	private void setShippingHeaderTag(SmsTemplateExpander expander,
			SmsComposition headerComposition, OrderContainer orderContainer,
			SmsComposition detailComposition) {

		String detailValue = detailComposition.getSubstitutionTag().replace(
				"#", "@");

		for (ShippingContainer shipping : orderContainer.getShippings()) {
			ShippingHeader shippingHeader = shipping.getShippingHeader();
			SmsTagDataList shippingHeaderTagList = new SmsTagDataList();

			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_LAST_NAME,
					shippingHeader.getAddressLastName());
			shippingHeaderTagList.add(ShippingHeaderTag.POSTAL_CODE, WebUtil
					.convertPostalCode(shippingHeader.getPostalCode()));
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS1,
					shippingHeader.getAddress1());
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS2,
					shippingHeader.getAddress2());
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS3,
					shippingHeader.getAddress3());
			shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_CHARGE,
					StringUtil.coalesce(Price.getFormatPrice(shippingHeader
							.getShippingCharge())));
			shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_TYPE_NAME,
					StringUtil.coalesce(shippingHeader.getDeliveryTypeName(),
							""));
			shippingHeaderTagList.add(ShippingHeaderTag.PHONE_NUMBER,
					shippingHeader.getPhoneNumber());
			shippingHeaderTagList.add(ShippingHeaderTag.MOBILE_NUMBER,
					shippingHeader.getMobileNumber());
			String deliveryAppointedDate = Messages
					.getString("service.impl.SmsingServiceImpl.5");
			// 20111230 shen update start
			// if
			// (StringUtil.hasValue(DateUtil.toDateString(shippingHeader.getDeliveryAppointedDate())))
			// {
			// deliveryAppointedDate =
			// DateUtil.toDateString(shippingHeader.getDeliveryAppointedDate());
			// }
			if (StringUtil.hasValue(shippingHeader.getDeliveryAppointedDate())) {
				deliveryAppointedDate = shippingHeader
						.getDeliveryAppointedDate();
			}
			// 20111230 shen update end
			shippingHeaderTagList.add(
					ShippingHeaderTag.DELIVERY_APPOINTED_DATE,
					deliveryAppointedDate);
			if (StringUtil.isNullOrEmptyAnyOf(NumUtil.toString(shippingHeader
					.getDeliveryAppointedTimeStart()), NumUtil
					.toString(shippingHeader.getDeliveryAppointedTimeEnd()))) {
				shippingHeaderTagList.add(
						ShippingHeaderTag.DELIVERY_APPOINTED_TIME, Messages
								.getString("service.impl.SmsingServiceImpl.6"));
			} else {
				shippingHeaderTagList
						.add(
								ShippingHeaderTag.DELIVERY_APPOINTED_TIME,
								NumUtil.toString(shippingHeader
										.getDeliveryAppointedTimeStart())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.7")
										+ NumUtil.toString(shippingHeader
												.getDeliveryAppointedTimeEnd())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.8"));
			}
			String arrivalDate = Messages
					.getString("service.impl.SmsingServiceImpl.9");
			if (StringUtil.hasValue(DateUtil.toDateString(shippingHeader
					.getArrivalDate()))) {
				arrivalDate = DateUtil.toDateString(shippingHeader
						.getArrivalDate());
			}
			shippingHeaderTagList.add(ArrivalTag.ARRIVAL_DATE, arrivalDate);
			if (StringUtil.isNullOrEmptyAnyOf(NumUtil.toString(shippingHeader
					.getArrivalTimeStart()), NumUtil.toString(shippingHeader
					.getArrivalTimeEnd()))) {
				shippingHeaderTagList.add(ArrivalTag.ARRIVAL_TIME, Messages
						.getString("service.impl.SmsingServiceImpl.10"));
			} else {
				shippingHeaderTagList
						.add(
								ArrivalTag.ARRIVAL_TIME,
								NumUtil.toString(shippingHeader
										.getArrivalTimeStart())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.11")
										+ NumUtil.toString(shippingHeader
												.getArrivalTimeEnd())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.12"));
			}
			shippingHeaderTagList
					.add(ShippingHeaderTag.DELIVERY_SLIP_NO, StringUtil
							.coalesce(shippingHeader.getDeliverySlipNo(), ""));

			// 配送先小計を取得（ （（販売価格＋ギフト価格）×商品数量）×配送先）＋配送手数料）

			BigDecimal shippingSumPrice = BigDecimal.ZERO;
			BigDecimal shippingTotalCommodity = BigDecimal.ZERO;
			BigDecimal shippingTotalGift = BigDecimal.ZERO;
			for (ShippingDetail detail : shipping.getShippingDetails()) {
				shippingSumPrice = shippingSumPrice.add(BigDecimalUtil
						.multiply(detail.getRetailPrice().add(
								detail.getGiftPrice()), detail
								.getPurchasingAmount()));
				shippingTotalCommodity = shippingTotalCommodity
						.add(BigDecimalUtil.multiply(detail.getRetailPrice(),
								detail.getPurchasingAmount()));
				shippingTotalGift = shippingTotalGift.add(BigDecimalUtil
						.multiply(detail.getGiftPrice(), detail
								.getPurchasingAmount()));
			}
			shippingSumPrice = shippingSumPrice.add(shippingHeader
					.getShippingCharge());
			shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_SUM_PRICE,
					StringUtil.coalesce(Price.getFormatPrice(shippingSumPrice),
							""));
			shippingHeaderTagList.add(
					ShippingHeaderTag.SHIPPING_COMMODITY_SUM_PRICE, StringUtil
							.coalesce(Price
									.getFormatPrice(shippingTotalCommodity)));
			shippingHeaderTagList.add(
					ShippingHeaderTag.SHIPPING_GIFT_SUM_PRICE, StringUtil
							.coalesce(Price.getFormatPrice(shippingTotalGift)));

			// 出荷明細情報は展開した情報を出荷ヘッダ部の項目タグとして処理する

			String shippingDetailText = setShippingDetailTag(expander,
					detailComposition, shipping.getShippingDetails(),
					orderContainer.getOrderDetails());
			SmsTemplateTag shippingDetailTag = new ShippingDetailCustomTag(
					detailComposition.getName(), detailValue,
					shippingDetailText);
			shippingHeaderTagList.add(shippingDetailTag, shippingDetailText);

			expander.addTagDataList(headerComposition.getType().getValue(),
					shippingHeaderTagList);
		}
		// 出荷ヘッダ部の出荷明細情報構造タグ部を項目タグとして設定する
		SmsTemplateDetail template = expander
				.getSmsTemplateDetail(headerComposition.getType().getValue());
		String headerText = template.getSmsText().replace(
				detailComposition.getType().getValue(), detailValue);
		template.setSmsText(headerText);
		expander.setSmsTemplateDetail(template);

	}

	// 2012-02-06 yyq add start desc:tmall发送短信
	private void setTmallShippingHeaderTag(SmsTemplateExpander expander,
			SmsComposition headerComposition, OrderContainer orderContainer,
			SmsComposition detailComposition) {

		String detailValue = detailComposition.getSubstitutionTag().replace(
				"#", "@");

		for (ShippingContainer shipping : orderContainer.getShippings()) {
			TmallShippingHeader tmallShippingHeader = shipping
					.getTmallShippingHeader();
			SmsTagDataList shippingHeaderTagList = new SmsTagDataList();

			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_LAST_NAME,
					tmallShippingHeader.getAddressLastName());
			shippingHeaderTagList.add(ShippingHeaderTag.POSTAL_CODE, WebUtil
					.convertPostalCode(tmallShippingHeader.getPostalCode()));
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS1,
					tmallShippingHeader.getAddress1());
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS2,
					tmallShippingHeader.getAddress2());
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS3,
					tmallShippingHeader.getAddress3());
			shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_CHARGE,
					StringUtil.coalesce(Price
							.getFormatPrice(tmallShippingHeader
									.getShippingCharge())));
			shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_TYPE_NAME,
					StringUtil.coalesce(tmallShippingHeader
							.getDeliveryTypeName(), ""));
			shippingHeaderTagList.add(ShippingHeaderTag.PHONE_NUMBER,
					tmallShippingHeader.getPhoneNumber());
			shippingHeaderTagList.add(ShippingHeaderTag.MOBILE_NUMBER,
					tmallShippingHeader.getMobileNumber());
			String deliveryAppointedDate = Messages
					.getString("service.impl.SmsingServiceImpl.5");
			if (StringUtil.hasValue(tmallShippingHeader
					.getDeliveryAppointedDate())) {
				deliveryAppointedDate = tmallShippingHeader
						.getDeliveryAppointedDate();
			}
			shippingHeaderTagList.add(
					ShippingHeaderTag.DELIVERY_APPOINTED_DATE,
					deliveryAppointedDate);
			if (StringUtil.isNullOrEmptyAnyOf(NumUtil
					.toString(tmallShippingHeader
							.getDeliveryAppointedTimeStart()),
					NumUtil.toString(tmallShippingHeader
							.getDeliveryAppointedTimeEnd()))) {
				shippingHeaderTagList.add(
						ShippingHeaderTag.DELIVERY_APPOINTED_TIME, Messages
								.getString("service.impl.SmsingServiceImpl.6"));
			} else {
				shippingHeaderTagList
						.add(
								ShippingHeaderTag.DELIVERY_APPOINTED_TIME,
								NumUtil.toString(tmallShippingHeader
										.getDeliveryAppointedTimeStart())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.7")
										+ NumUtil.toString(tmallShippingHeader
												.getDeliveryAppointedTimeEnd())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.8"));
			}
			String arrivalDate = Messages
					.getString("service.impl.SmsingServiceImpl.9");
			if (StringUtil.hasValue(DateUtil.toDateString(tmallShippingHeader
					.getArrivalDate()))) {
				arrivalDate = DateUtil.toDateString(tmallShippingHeader
						.getArrivalDate());
			}
			shippingHeaderTagList.add(ArrivalTag.ARRIVAL_DATE, arrivalDate);
			if (StringUtil.isNullOrEmptyAnyOf(NumUtil
					.toString(tmallShippingHeader.getArrivalTimeStart()),
					NumUtil.toString(tmallShippingHeader.getArrivalTimeEnd()))) {
				shippingHeaderTagList.add(ArrivalTag.ARRIVAL_TIME, Messages
						.getString("service.impl.SmsingServiceImpl.10"));
			} else {
				shippingHeaderTagList
						.add(
								ArrivalTag.ARRIVAL_TIME,
								NumUtil.toString(tmallShippingHeader
										.getArrivalTimeStart())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.11")
										+ NumUtil.toString(tmallShippingHeader
												.getArrivalTimeEnd())
										+ Messages
												.getString("service.impl.SmsingServiceImpl.12"));
			}
			shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_SLIP_NO,
					StringUtil.coalesce(
							tmallShippingHeader.getDeliverySlipNo(), ""));

			BigDecimal shippingSumPrice = BigDecimal.ZERO;
			BigDecimal shippingTotalCommodity = BigDecimal.ZERO;
			BigDecimal shippingTotalGift = BigDecimal.ZERO;
			for (ShippingDetail detail : shipping.getShippingDetails()) {
				shippingSumPrice = shippingSumPrice.add(BigDecimalUtil
						.multiply(detail.getRetailPrice().add(
								detail.getGiftPrice()), detail
								.getPurchasingAmount()));
				shippingTotalCommodity = shippingTotalCommodity
						.add(BigDecimalUtil.multiply(detail.getRetailPrice(),
								detail.getPurchasingAmount()));
				shippingTotalGift = shippingTotalGift.add(BigDecimalUtil
						.multiply(detail.getGiftPrice(), detail
								.getPurchasingAmount()));
			}
			shippingSumPrice = shippingSumPrice.add(tmallShippingHeader
					.getShippingCharge());
			shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_SUM_PRICE,
					StringUtil.coalesce(Price.getFormatPrice(shippingSumPrice),
							""));
			shippingHeaderTagList.add(
					ShippingHeaderTag.SHIPPING_COMMODITY_SUM_PRICE, StringUtil
							.coalesce(Price
									.getFormatPrice(shippingTotalCommodity)));
			shippingHeaderTagList.add(
					ShippingHeaderTag.SHIPPING_GIFT_SUM_PRICE, StringUtil
							.coalesce(Price.getFormatPrice(shippingTotalGift)));

			String shippingDetailText = setTmallShippingDetailTag(expander,
					detailComposition, shipping.getTmallShippingDetails(),
					orderContainer.getTmallIOrderDetails());
			SmsTemplateTag shippingDetailTag = new ShippingDetailCustomTag(
					detailComposition.getName(), detailValue,
					shippingDetailText);
			shippingHeaderTagList.add(shippingDetailTag, shippingDetailText);

			expander.addTagDataList(headerComposition.getType().getValue(),
					shippingHeaderTagList);
		}
		// 出荷ヘッダ部の出荷明細情報構造タグ部を項目タグとして設定する
		SmsTemplateDetail template = expander
				.getSmsTemplateDetail(headerComposition.getType().getValue());
		String headerText = template.getSmsText().replace(
				detailComposition.getType().getValue(), detailValue);
		template.setSmsText(headerText);
		expander.setSmsTemplateDetail(template);
	}

	// 2012-02-06 yyq add end desc:tmall发送短信

	private void setPaymentTag(SmsTemplateExpander expander,
			SmsComposition composition, OrderHeader header, CashierPayment pay) {

		SmsTagDataList paymentTagList = new SmsTagDataList();
		paymentTagList.add(PaymentTag.PAYMENT_METHOD_NAME, header
				.getPaymentMethodName());
		paymentTagList.add(PaymentTag.PAYMENT_COMMISSION, StringUtil
				.coalesce(Price.getFormatPrice(header.getPaymentCommission())));
		paymentTagList.add(PaymentTag.PAYMENT_DETAIL, getPaymentDetailText(
				composition, pay, header));
		expander.addTagDataList(composition.getType().getValue(),
				paymentTagList);

	}

	private String getPaymentDetailText(SmsComposition composition,
			CashierPayment payment, OrderHeader header) {
		StringWriter s = new StringWriter();
		PrintWriter out = new ExPrintWriter(s);

		if (payment.getSelectPayment().isBanking()) {
			BankDao bankDao = DIContainer.getDao(BankDao.class);
			List<Bank> bankList = bankDao.findByQuery(new SimpleQuery(
					ShopManagementSimpleSql.LOAD_BANK_LIST, payment
							.getShopCode(), payment.getPaymentMethodCode()));

			int bankSize = bankList.size();

			out
					.println(Messages
							.getString("service.impl.SmsingServiceImpl.29"));

			int i = 1;
			for (Bank bank : bankList) {
				if (bankSize <= 1) {
					out.println(Messages
							.getString("service.impl.SmsingServiceImpl.30"));
				} else {
					out.println(Messages
							.getString("service.impl.SmsingServiceImpl.31")
							+ Integer.valueOf(i).toString());
				}
				out.println(Messages
						.getString("service.impl.SmsingServiceImpl.32")
						+ StringUtil.coalesce(bank.getBankName(), ""));
				out.println(Messages
						.getString("service.impl.SmsingServiceImpl.33")
						+ StringUtil.coalesce(bank.getBankBranchName(), ""));
				out.println(MessageFormat.format(Messages
						.getString("service.impl.SmsingServiceImpl.37"),
						StringUtil.coalesce(AccountType.fromValue(
								bank.getAccountType()).getName(), ""),
						StringUtil.coalesce(bank.getAccountNo(), "")));
				out.println();
				i += 1;
			}
			if (header.getPaymentLimitDate() != null) {
				if (header.getPaymentLimitDate().before(
						DateUtil.fromString(DateUtil.getSysdateString()))) {
					// 支払期限が切れている場合
					out.println(Messages
							.getString("service.impl.SmsingServiceImpl.39"));
				} else {
					out
							.println(MessageFormat
									.format(
											Messages
													.getString("service.impl.SmsingServiceImpl.40"),
											DateUtil.toDateString(header
													.getPaymentLimitDate())));
				}
			}
		} else if (payment.getSelectPayment().isCreditcard()) {
			// クレジットカードの場合、支払方法名だけの表示だけでよいので追記せず
			out.println("");

		} else if (payment.getSelectPayment().isCvsPayment()) {
			String cvsName = "";
			CashierPaymentTypeBase cashier = new CashierPaymentTypeCvs();
			List<CodeAttribute> cvsList = PaymentProviderManager
					.getCodeList(cashier);
			for (CodeAttribute cvs : cvsList) {
				if (cvs.getValue().equals(header.getCvsCode())) {
					cvsName = cvs.getName();
				}
			}
			out
					.println(Messages
							.getString("service.impl.SmsingServiceImpl.41"));
			out.println(MessageFormat.format(Messages
					.getString("service.impl.SmsingServiceImpl.42"), StringUtil
					.coalesce(cvsName, "")));
			out.println(MessageFormat.format(Messages
					.getString("service.impl.SmsingServiceImpl.43"), StringUtil
					.coalesce(header.getPaymentReceiptNo(), "")));
			if (StringUtil.hasValue(header.getPaymentReceiptUrl())) {
				out
						.println(MessageFormat
								.format(
										Messages
												.getString("service.impl.SmsingServiceImpl.44"),
										StringUtil.coalesce(header
												.getPaymentReceiptUrl(), "")));
			}
			if (header.getPaymentLimitDate().before(
					DateUtil.fromString(DateUtil.getSysdateString()))) {
				// 支払期限が切れている場合
				out.println(Messages
						.getString("service.impl.SmsingServiceImpl.45"));
			} else {
				out.println(MessageFormat.format(Messages
						.getString("service.impl.SmsingServiceImpl.46"),
						DateUtil.toDateString(header.getPaymentLimitDate())));
			}

		} else if (payment.getSelectPayment().isDigitalCash()) {
			String digitalName = "";
			CashierPaymentTypeBase cashier = new CashierPaymentTypeDigitalCash();
			List<CodeAttribute> digitalList = PaymentProviderManager
					.getCodeList(cashier);
			for (CodeAttribute digital : digitalList) {
				if (digital.getValue().equals(header.getDigitalCashType())) {
					digitalName = digital.getName();
				}
			}
			out
					.println(Messages
							.getString("service.impl.SmsingServiceImpl.47"));
			out.println(MessageFormat.format(Messages
					.getString("service.impl.SmsingServiceImpl.48"), StringUtil
					.coalesce(digitalName, "")));
			if (header.getPaymentLimitDate().before(
					DateUtil.fromString(DateUtil.getSysdateString()))) {
				// 支払期限が切れている場合
				out.println(Messages
						.getString("service.impl.SmsingServiceImpl.49"));
			} else {
				out.println(MessageFormat.format(Messages
						.getString("service.impl.SmsingServiceImpl.50"),
						DateUtil.toDateString(header.getPaymentLimitDate())));
			}

		} else if (payment.getSelectPayment().isCashOnDelivery()) {

			// 代引の場合、支払方法名だけの表示だけでよいので追記せず
			out.println("");
		} else if (payment.getSelectPayment().isPointInFull()) {

			// 全額ポイント払いの場合、支払方法名だけの表示だけでよいので追記せず
			out.println("");
		} else if (payment.getSelectPayment().isNoPayment()) {

			// 支払不要の場合、支払方法名だけの表示だけでよいので追記せず
			out.println("");
		}

		return s.toString();
	}

	private void setShopInfoTag(SmsTemplateExpander expander,
			SmsComposition composition, Shop shop) {
		SmsTagDataList shopInfoTagList = new SmsTagDataList();

		shopInfoTagList.add(ShopInfoTag.SHOP_NAME, shop.getShopName());
		String shopIntroducedUrl = "";
		WebshopConfig webshopConfig = DIContainer.getWebshopConfig();
		if (shop.getShopCode().equals(webshopConfig.getSiteShopCode())) {
			shopIntroducedUrl = webshopConfig.getTopPageUrl()
					+ "/app/common/index";
		} else {
			shopIntroducedUrl = shop.getShopIntroducedUrl();
		}
		shopInfoTagList.add(ShopInfoTag.SHOP_INTRODUCED_URL, shopIntroducedUrl);
		shopInfoTagList.add(ShopInfoTag.EMAIL, shop.getEmail());
		shopInfoTagList.add(ShopInfoTag.POSTAL_CODE, WebUtil
				.convertPostalCode(shop.getPostalCode()));
		shopInfoTagList.add(ShopInfoTag.ADDRESS1, shop.getAddress1());
		shopInfoTagList.add(ShopInfoTag.ADDRESS2, shop.getAddress2());
		shopInfoTagList.add(ShopInfoTag.ADDRESS3, shop.getAddress3());
		shopInfoTagList.add(ShopInfoTag.PHONE_NUMBER, shop.getPhoneNumber());
		shopInfoTagList.add(ShopInfoTag.MOBILE_NUMBER, shop.getMobileNumber());
		shopInfoTagList.add(ShopInfoTag.PERSON_IN_CHARGE, shop
				.getPersonInCharge());
		expander.addTagDataList(composition.getType().getValue(),
				shopInfoTagList);

	}

	private RespectiveSmsqueue createSmsQueue(
			SmsTemplateSuite smsTemplateSuite, String toMobile,
			Long smsSendStatus, String smsText) {
		RespectiveSmsqueue queue = new RespectiveSmsqueue();
		queue.setSmsType(smsTemplateSuite.getSmsTemplateDetail().get(0)
				.getSmsType());
		queue.setToMobile(toMobile);
		queue.setSmsSendStatus(smsSendStatus);
		queue.setSmsText(smsText);
		queue.setCreatedDatetime(smsTemplateSuite.getSmsTemplateDetail().get(0)
				.getCreatedDatetime());
		queue.setCreatedUser(smsTemplateSuite.getSmsTemplateDetail().get(0)
				.getCreatedUser());
		queue.setUpdatedDatetime(smsTemplateSuite.getSmsTemplateDetail().get(0)
				.getUpdatedDatetime());
		queue.setUpdatedUser(smsTemplateSuite.getSmsTemplateDetail().get(0)
				.getUpdatedUser());

		return queue;
	}

	public ServiceResult sendRespectiveSms(RespectiveSmsqueue smsQueue) {
		return sendRespectiveSms(smsQueue, null);
	}

	private ServiceResult sendRespectiveSms(RespectiveSmsqueue smsQueue,
			OrderSmsHistory history) {
		Logger logger = Logger.getLogger(this.getClass());
		ServiceResultImpl serviceResult = new ServiceResultImpl();

		Long smsQueueSeq = DatabaseUtil
				.generateSequence(SequenceType.RESPECTIVE_SMS_QUEUE_ID);
		smsQueue.setSmsQueueId(smsQueueSeq);

		TransactionManager manager = DIContainer.getTransactionManager();
		SmsSender sender = DIContainer.getSmsSender();
		try {
			manager.begin(this.getLoginInfo());

			SmsInfo info = new SmsInfo();

			info.addToList(smsQueue.getToMobile());

			info.setText(smsQueue.getSmsText());
			info.setMobileNumber(smsQueue.getToMobile());
			try {
				if (StringUtil.hasValue(smsQueue.getToMobile())) {
					String[] mobiles = new String[] { smsQueue.getToMobile() };
					boolean result = sender.sendSms(mobiles, info.getText());// 发送SMS
					if (result) {
						smsQueue.setSmsSendStatus(Long
								.parseLong(SmsSendStatus.SENT_ALL.getValue()));// SMS发送成功
						logger.info(Messages
								.log("service.impl.SmsingServiceImpl.1"));
					} else {
						smsQueue.setSmsSendStatus(Long
								.parseLong(SmsSendStatus.NOT_SENT.getValue()));// SMS发送失败
						logger.info(Messages
								.log("service.impl.SmsingServiceImpl.2"));
					}
					smsQueue.setSmsSentDatetime(DatabaseUtil
							.getSystemDatetime());
				}
			} catch (MessagingException e) {
				logger.error(Messages.log("service.impl.SmsingServiceImpl.2"));
				smsQueue.setSmsSendStatus(Long
						.parseLong(SmsSendStatus.FAILED_ALL.getValue()));
			} catch (UnsupportedEncodingException e) {
				logger.error(Messages.log("service.impl.SmsingServiceImpl.3"));
				smsQueue.setSmsSendStatus(Long
						.parseLong(SmsSendStatus.FAILED_ALL.getValue()));
			}

			setUserStatus(smsQueue);
			// Validateチェック
			ValidationSummary validResult = BeanValidator.validate(smsQueue);
			if (validResult.hasError()) {
				serviceResult
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				for (String message : validResult.getErrorMessages()) {
					logger.error(message);
				}
				return serviceResult;
			}

			manager.insert(smsQueue);
			// delete by V10-CH start
			// if (history != null) {
			// history.setSmsQueueId(smsQueue.getSmsQueueId());
			// manager.insert(history);
			// }
			// delete by V10-CH end
			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			serviceResult
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return serviceResult;
	}

	private boolean noSetSmsTemplate(String shopCode, String smsType,
			Long smsTemplateNo) {

		Query query = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL, smsType);
		List<SmsTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(query,
				SmsTemplateDetail.class);
		boolean checkSms = true;
		if (detailList != null) {
			checkSms = false;
		}
		return checkSms;
	}

	private void setReservationShippingTag(SmsTemplateExpander expander,
			SmsComposition headerComposition, OrderContainer orderContainer,
			SmsComposition detailComposition) {

		String detailValue = detailComposition.getSubstitutionTag().replace(
				"#", "@");

		for (ShippingContainer shipping : orderContainer.getShippings()) {
			ShippingHeader shippingHeader = shipping.getShippingHeader();
			SmsTagDataList shippingHeaderTagList = new SmsTagDataList();

			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS_LAST_NAME,
					shippingHeader.getAddressLastName());
			shippingHeaderTagList.add(ShippingHeaderTag.POSTAL_CODE, WebUtil
					.convertPostalCode(shippingHeader.getPostalCode()));
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS1,
					shippingHeader.getAddress1());
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS2,
					shippingHeader.getAddress2());
			shippingHeaderTagList.add(ShippingHeaderTag.ADDRESS3,
					shippingHeader.getAddress3());
			shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_CHARGE,
					StringUtil.coalesce(Price.getFormatPrice(shippingHeader
							.getShippingCharge())));
			shippingHeaderTagList.add(ShippingHeaderTag.DELIVERY_TYPE_NAME,
					StringUtil.coalesce(shippingHeader.getDeliveryTypeName(),
							""));
			shippingHeaderTagList.add(ShippingHeaderTag.PHONE_NUMBER,
					shippingHeader.getPhoneNumber());
			shippingHeaderTagList.add(ShippingHeaderTag.MOBILE_NUMBER,
					shippingHeader.getMobileNumber());

			BigDecimal shippingSumPrice = BigDecimal.ZERO;
			BigDecimal shippingTotalCommodity = BigDecimal.ZERO;
			BigDecimal shippingTotalGift = BigDecimal.ZERO;
			for (ShippingDetail detail : shipping.getShippingDetails()) {
				shippingSumPrice = shippingSumPrice.add(BigDecimalUtil
						.multiply(detail.getRetailPrice().add(
								detail.getGiftPrice()), detail
								.getPurchasingAmount()));
				shippingTotalCommodity = shippingTotalCommodity
						.add(BigDecimalUtil.multiply(detail.getRetailPrice(),
								detail.getPurchasingAmount()));
				shippingTotalGift = shippingTotalGift.add(BigDecimalUtil
						.multiply(detail.getGiftPrice(), detail
								.getPurchasingAmount()));
			}
			shippingSumPrice = shippingSumPrice.add(shippingHeader
					.getShippingCharge());
			shippingHeaderTagList.add(ShippingHeaderTag.SHIPPING_SUM_PRICE,
					StringUtil.coalesce(Price.getFormatPrice(shippingSumPrice),
							""));
			shippingHeaderTagList.add(
					ShippingHeaderTag.SHIPPING_COMMODITY_SUM_PRICE, StringUtil
							.coalesce(Price
									.getFormatPrice(shippingTotalCommodity)));
			shippingHeaderTagList.add(
					ShippingHeaderTag.SHIPPING_GIFT_SUM_PRICE, StringUtil
							.coalesce(Price.getFormatPrice(shippingTotalGift)));

			// 出荷明細情報は展開した情報を出荷ヘッダ部の項目タグとして処理する

			String shippingDetailText = setShippingDetailTag(expander,
					detailComposition, shipping.getShippingDetails(),
					orderContainer.getOrderDetails());
			SmsTemplateTag shippingDetailTag = new ShippingDetailCustomTag(
					detailComposition.getName(), detailValue,
					shippingDetailText);
			shippingHeaderTagList.add(shippingDetailTag, shippingDetailText);
			expander.addTagDataList(headerComposition.getType().getValue(),
					shippingHeaderTagList);
		}
		// 出荷ヘッダ部の出荷明細情報構造タグ部を項目タグとして設定する
		SmsTemplateDetail template = expander
				.getSmsTemplateDetail(headerComposition.getSubstitutionTag());
		String headerText = template.getSmsText().replace(
				detailComposition.getSubstitutionTag(), detailValue);
		template.setSmsText(headerText);
		expander.setSmsTemplateDetail(template);

	}

	private String setShippingDetailTag(SmsTemplateExpander expander,
			SmsComposition composition, List<ShippingDetail> shippingDetails,
			List<OrderDetail> orderDetails) {

		SmsTemplateDetail detail = expander.getSmsTemplateDetail(composition
				.getType().getValue());
		String shippingDetailText = "";

		for (ShippingDetail shippingDetail : shippingDetails) {

			// 受注明細から商品名を取得
			String commodityName = "";
			for (OrderDetail orderDetail : orderDetails) {
				if (shippingDetail.getShopCode().equals(
						orderDetail.getShopCode())
						&& shippingDetail.getSkuCode().equals(
								orderDetail.getSkuCode())) {
					commodityName = orderDetail.getCommodityName();
					String standard1Name = orderDetail.getStandardDetail1Name();
					String standard2Name = orderDetail.getStandardDetail2Name();
					if (StringUtil.hasValueAllOf(standard1Name, standard2Name)) {
						commodityName += "(" + standard1Name + "/"
								+ standard2Name + ")";
					} else if (StringUtil.hasValue(standard1Name)) {
						commodityName += "(" + standard1Name + ")";
					}
					break;
				}
			}

			SmsTagDataList shippingDetailTagList = new SmsTagDataList();
			shippingDetailTagList.add(ShippingDetailTag.UNIT_NAME, StringUtil
					.coalesce(commodityName, ""));
			shippingDetailTagList.add(ShippingDetailTag.PURCHASING_AMOUNT,
					shippingDetail.getPurchasingAmount().toString());
			shippingDetailTagList.add(ShippingDetailTag.RETAIL_PRICE,
					StringUtil.coalesce(Price.getFormatPrice(shippingDetail
							.getRetailPrice())));
			shippingDetailTagList.add(ShippingDetailTag.GIFT_NAME, StringUtil
					.coalesce(shippingDetail.getGiftName(), ""));
			shippingDetailTagList.add(ShippingDetailTag.GIFT_PRICE, StringUtil
					.coalesce(Price.getFormatPrice(shippingDetail
							.getGiftPrice())));

			expander.addTagDataList(composition.getType().getValue(),
					shippingDetailTagList);
			shippingDetailText += expander.expandItemTags(detail.getSmsText(),
					shippingDetailTagList);
		}

		return shippingDetailText;

	}

	// Tmall新增
	private String setTmallShippingDetailTag(SmsTemplateExpander expander,
			SmsComposition composition,
			List<TmallShippingDetail> shippingDetails,
			List<TmallOrderDetail> orderDetails) {

		SmsTemplateDetail detail = expander.getSmsTemplateDetail(composition
				.getType().getValue());
		String shippingDetailText = "";

		for (TmallShippingDetail shippingDetail : shippingDetails) {

			// 受注明細から商品名を取得
			String commodityName = "";
			for (TmallOrderDetail orderDetail : orderDetails) {
				if (shippingDetail.getShopCode().equals(
						orderDetail.getShopCode())
						&& shippingDetail.getSkuCode().equals(
								orderDetail.getSkuCode())) {
					commodityName = orderDetail.getCommodityName();
					String standard1Name = orderDetail.getStandardDetail1Name();
					String standard2Name = orderDetail.getStandardDetail2Name();
					if (StringUtil.hasValueAllOf(standard1Name, standard2Name)) {
						commodityName += "(" + standard1Name + "/"
								+ standard2Name + ")";
					} else if (StringUtil.hasValue(standard1Name)) {
						commodityName += "(" + standard1Name + ")";
					}
					break;
				}
			}

			SmsTagDataList shippingDetailTagList = new SmsTagDataList();
			shippingDetailTagList.add(ShippingDetailTag.UNIT_NAME, StringUtil
					.coalesce(commodityName, ""));
			shippingDetailTagList.add(ShippingDetailTag.PURCHASING_AMOUNT,
					shippingDetail.getPurchasingAmount().toString());
			shippingDetailTagList.add(ShippingDetailTag.RETAIL_PRICE,
					StringUtil.coalesce(Price.getFormatPrice(shippingDetail
							.getRetailPrice())));
			shippingDetailTagList.add(ShippingDetailTag.GIFT_NAME, StringUtil
					.coalesce(shippingDetail.getGiftName(), ""));
			shippingDetailTagList.add(ShippingDetailTag.GIFT_PRICE, StringUtil
					.coalesce(Price.getFormatPrice(shippingDetail
							.getGiftPrice())));

			expander.addTagDataList(composition.getType().getValue(),
					shippingDetailTagList);
			shippingDetailText += expander.expandItemTags(detail.getSmsText(),
					shippingDetailTagList);
		}

		return shippingDetailText;

	}

	public ServiceResult sendNewOrderSmsMobile(OrderContainer order,
			CashierPayment payment, Shop shop) {
		return sendOrderSms(order, payment, shop, true, ClientSmsType.MOBILE);
	}

	public ServiceResult sendNewOrderSmsPc(OrderContainer order,
			CashierPayment payment, Shop shop) {
		return sendOrderSms(order, payment, shop, true, ClientSmsType.PC);
	}

	public ServiceResult sendUpdateOrderSms(OrderContainer order,
			CashierPayment payment, Shop shop) {
		ClientSmsTypeCondition condition = new ClientSmsTypeCondition();
		condition.setSmsAddress(order.getOrderHeader().getEmail());
		return sendOrderSms(order, payment, shop, false,
				getClientSmsType(condition));
	}

	private ClientSmsType getClientSmsType(ClientSmsTypeCondition condition) {
		if (condition.getClientSmsType() != null) {
			return condition.getClientSmsType();
		}

		MobileDomain domain = DIContainer.get("MobileDomain");
		String smsType = Integer.toString(domain.getClientSmsType(condition
				.getSmsAddress()));
		ClientSmsType clientSmsType = ClientSmsType.fromValue(smsType);
		if (clientSmsType != null) {
			return clientSmsType;
		}
		return ClientSmsType.MOBILE;
	}

	public ServiceResult sendShippingReceivedSms(OrderContainer orderContainer,
			OrderContainer allContainer, Shop shop, CashierPayment payment,
			String shippingNo) {
		ServiceResultImpl result = new ServiceResultImpl();

		OrderHeader orderHeader = orderContainer.getOrderHeader();

		String toMobile = orderHeader.getMobileNumber();

		// 会員購入の場合、顧客テーブルからメールアドレスと取得する

		if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
			if (orderHeader.getCustomerCode() == null) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				return result;
			}
			CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
			Customer customer = customerDao.load(orderHeader.getCustomerCode());
			if (customer == null) {
				result
						.addServiceError(SmsingServiceErrorContent.NO_CUSTOMER_TO_SEND_SMS);
				return result;
			}
		}
		// delete by V10-CH start
		// orderContainer.getOrderHeader().setMobileNumber(toMobile);
		// delete by V10-CH end

		SmsTemplateSuite smsTemplateSuite = createSmsTemplate(shop
				.getShopCode(), SmsType.SHIPPING_INFORMATION.getValue(), 0L);
		setTaxIncludedPriceDisplay(smsTemplateSuite);

		if (noSetSmsTemplate(shop.getShopCode(), SmsType.SHIPPING_INFORMATION
				.getValue(), 0L)) {
			result
					.addServiceError(SmsingServiceErrorContent.NO_SMS_TEMPLATE_ERROR);
			return result;
		}

		SmsTemplateExpander expander = new SmsTemplateExpander(smsTemplateSuite);

		setShippingTag(expander, SmsComposition.SHIPPING_REPORT_MAIN,
				orderContainer, allContainer);
		setShippingHeaderTag(expander, SmsComposition.SHIPPING_REPORT_MAIN,
				orderContainer, SmsComposition.SHIPPING_REPORT_MAIN);
		setShopInfoTag(expander, SmsComposition.SHIPPING_REPORT_MAIN, shop);

		RespectiveSmsqueue queue = createSmsQueue(smsTemplateSuite, toMobile,
				SmsSendStatus.NOT_SENT.longValue(), expander.expandTemplate());

		OrderSmsHistory history = new OrderSmsHistory();
		history.setOrderNo(orderHeader.getOrderNo());
		history.setShippingNo(shippingNo);
		// history.setOrderSmsHistoryId(DatabaseUtil.generateSequence(SequenceType.ORDER_SMS_HISTORY_ID));

		ServiceResult sendSmsResult = sendRespectiveSms(queue, history);

		if (sendSmsResult.hasError()) {
			for (ServiceErrorContent error : sendSmsResult
					.getServiceErrorList()) {
				result.addServiceError(error);
			}
		}
		return result;
	}

	// 2012-02-06 yyq add start desc:tmall发送短信
	public ServiceResult sendTmallShippingReceivedSms(
			OrderContainer orderContainer, OrderContainer allContainer,
			Shop shop, CashierPayment payment, String shippingNo) {
		ServiceResultImpl result = new ServiceResultImpl();

		TmallOrderHeader tmallOrderHeader = orderContainer
				.getTmallOrderHeader();

		String toMobile = tmallOrderHeader.getMobileNumber();

		if (CustomerConstant.isCustomer(tmallOrderHeader.getCustomerCode())) {
			if (tmallOrderHeader.getCustomerCode() == null) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				return result;
			}
			CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
			Customer customer = customerDao.load(tmallOrderHeader
					.getCustomerCode());
			if (customer == null) {
				result
						.addServiceError(SmsingServiceErrorContent.NO_CUSTOMER_TO_SEND_SMS);
				return result;
			}
		}

		SmsTemplateSuite smsTemplateSuite = createSmsTemplate(shop
				.getShopCode(), SmsType.SHIPPING_INFORMATION.getValue(), 0L);
		setTaxIncludedPriceDisplay(smsTemplateSuite);

		if (noSetSmsTemplate(shop.getShopCode(), SmsType.SHIPPING_INFORMATION
				.getValue(), 0L)) {
			result
					.addServiceError(SmsingServiceErrorContent.NO_SMS_TEMPLATE_ERROR);
			return result;
		}

		SmsTemplateExpander expander = new SmsTemplateExpander(smsTemplateSuite);

		setTmallShippingTag(expander, SmsComposition.SHIPPING_REPORT_MAIN,
				orderContainer, allContainer);
		setTmallShippingHeaderTag(expander,
				SmsComposition.SHIPPING_REPORT_MAIN, orderContainer,
				SmsComposition.SHIPPING_REPORT_MAIN);
		setShopInfoTag(expander, SmsComposition.SHIPPING_REPORT_MAIN, shop);

		RespectiveSmsqueue queue = createSmsQueue(smsTemplateSuite, toMobile,
				SmsSendStatus.NOT_SENT.longValue(), expander.expandTemplate());

		OrderSmsHistory history = new OrderSmsHistory();
		history.setOrderNo(tmallOrderHeader.getOrderNo());
		history.setShippingNo(shippingNo);

		ServiceResult sendSmsResult = sendRespectiveSms(queue, history);

		if (sendSmsResult.hasError()) {
			for (ServiceErrorContent error : sendSmsResult
					.getServiceErrorList()) {
				result.addServiceError(error);
			}
		}
		return result;
	}

	// 2012-02-06 yyq add end desc:tmall发送短信

	private void setShippingTag(SmsTemplateExpander expander,
			SmsComposition composition, OrderContainer orderContainer,
			OrderContainer allContainer) {

		SmsTagDataList shippingTagList = new SmsTagDataList();
		shippingTagList.add(ShippingTag.ORDER_NO, orderContainer
				.getOrderHeader().getOrderNo());
		shippingTagList.add(ShippingTag.ORDER_DATETIME, DateUtil
				.toDateTimeString(orderContainer.getOrderHeader()
						.getOrderDatetime()));
		shippingTagList.add(ShippingTag.LAST_NAME, orderContainer
				.getOrderHeader().getLastName());
		shippingTagList.add(ShippingTag.LAST_NAME, orderContainer
				.getOrderHeader().getLastName());
		shippingTagList.add(ShippingTag.EMAIL, orderContainer.getOrderHeader()
				.getEmail());
		shippingTagList.add(ShippingTag.LAST_NAME, orderContainer
				.getOrderHeader().getLastName());
		shippingTagList.add(ShippingTag.POSTAL_CODE, WebUtil
				.convertPostalCode(orderContainer.getOrderHeader()
						.getPostalCode()));
		shippingTagList.add(ShippingTag.ADDRESS1, orderContainer
				.getOrderHeader().getAddress1());
		shippingTagList.add(ShippingTag.ADDRESS2, orderContainer
				.getOrderHeader().getAddress2());
		shippingTagList.add(ShippingTag.ADDRESS3, orderContainer
				.getOrderHeader().getAddress3());
		shippingTagList.add(ShippingTag.PHONE_NUMBER, orderContainer
				.getOrderHeader().getPhoneNumber());
		shippingTagList.add(ShippingTag.MOBILE_NUMBER, orderContainer
				.getOrderHeader().getMobileNumber());
		shippingTagList.add(ShippingTag.MESSAGE, orderContainer
				.getOrderHeader().getMessage());
		shippingTagList.add(ShippingTag.COMMISSION_PRICE, StringUtil
				.coalesce(Price.getFormatPrice(orderContainer.getOrderHeader()
						.getPaymentCommission())));
		// ご注文合計金額（全配送先小計＋支払い手数料）
		BigDecimal orderTotalPrice = BigDecimal.ZERO;
		for (ShippingContainer shipping : allContainer.getShippings()) {
			ShippingHeader header = shipping.getShippingHeader();
			for (ShippingDetail detail : shipping.getShippingDetails()) {
				BigDecimal retailPrice = detail.getRetailPrice();
				BigDecimal giftPrice = detail.getGiftPrice();
				orderTotalPrice = orderTotalPrice.add(BigDecimalUtil.multiply(
						retailPrice.add(giftPrice), detail
								.getPurchasingAmount()));
			}
			orderTotalPrice = orderTotalPrice.add(header.getShippingCharge());
		}
		orderTotalPrice = orderTotalPrice.add(orderContainer.getOrderHeader()
				.getPaymentCommission());
		// お支払い合計金額
		BigDecimal paymentPrice = PointUtil
				.getTotalPyamentPrice(orderTotalPrice, orderContainer
						.getOrderHeader().getUsedPoint());
		shippingTagList.add(ShippingTag.PAYMENT_TOTAL_PRICE, StringUtil
				.coalesce(Price.getFormatPrice(paymentPrice), ""));

		expander.addTagDataList(composition.getType().getValue(),
				shippingTagList);
	}

	// 2012-02-06 yyq add start desc:发送短信
	private void setTmallShippingTag(SmsTemplateExpander expander,
			SmsComposition composition, OrderContainer orderContainer,
			OrderContainer allContainer) {

		SmsTagDataList shippingTagList = new SmsTagDataList();
		shippingTagList.add(ShippingTag.ORDER_NO, orderContainer
				.getTmallOrderHeader().getOrderNo());
		shippingTagList.add(ShippingTag.ORDER_DATETIME, DateUtil
				.toDateTimeString(orderContainer.getTmallOrderHeader()
						.getOrderDatetime()));
		shippingTagList.add(ShippingTag.LAST_NAME, orderContainer
				.getTmallOrderHeader().getLastName());
		shippingTagList.add(ShippingTag.LAST_NAME, orderContainer
				.getTmallOrderHeader().getLastName());
		shippingTagList.add(ShippingTag.EMAIL, orderContainer
				.getTmallOrderHeader().getEmail());
		shippingTagList.add(ShippingTag.LAST_NAME, orderContainer
				.getTmallOrderHeader().getLastName());
		shippingTagList.add(ShippingTag.POSTAL_CODE, WebUtil
				.convertPostalCode(orderContainer.getTmallOrderHeader()
						.getPostalCode()));
		shippingTagList.add(ShippingTag.ADDRESS1, orderContainer
				.getTmallOrderHeader().getAddress1());
		shippingTagList.add(ShippingTag.ADDRESS2, orderContainer
				.getTmallOrderHeader().getAddress2());
		shippingTagList.add(ShippingTag.ADDRESS3, orderContainer
				.getTmallOrderHeader().getAddress3());
		shippingTagList.add(ShippingTag.PHONE_NUMBER, orderContainer
				.getTmallOrderHeader().getPhoneNumber());
		shippingTagList.add(ShippingTag.MOBILE_NUMBER, orderContainer
				.getTmallOrderHeader().getMobileNumber());
		shippingTagList.add(ShippingTag.MESSAGE, orderContainer
				.getTmallOrderHeader().getMessage());
		shippingTagList.add(ShippingTag.COMMISSION_PRICE, StringUtil
				.coalesce(Price.getFormatPrice(orderContainer
						.getTmallOrderHeader().getPaymentCommission())));
		BigDecimal orderTotalPrice = BigDecimal.ZERO;
		for (ShippingContainer shipping : allContainer.getShippings()) {
			TmallShippingHeader header = shipping.getTmallShippingHeader();
			for (TmallShippingDetail detail : shipping
					.getTmallShippingDetails()) {
				BigDecimal retailPrice = detail.getRetailPrice();
				BigDecimal giftPrice = detail.getGiftPrice();
				orderTotalPrice = orderTotalPrice.add(BigDecimalUtil.multiply(
						retailPrice.add(giftPrice), detail
								.getPurchasingAmount()));
			}
			orderTotalPrice = orderTotalPrice.add(header.getShippingCharge());
		}
		orderTotalPrice = orderTotalPrice.add(orderContainer
				.getTmallOrderHeader().getPaymentCommission());
		// お支払い合計金額
		BigDecimal paymentPrice = PointUtil.getTotalPyamentPrice(
				orderTotalPrice, orderContainer.getTmallOrderHeader()
						.getUsedPoint());
		shippingTagList.add(ShippingTag.PAYMENT_TOTAL_PRICE, StringUtil
				.coalesce(Price.getFormatPrice(paymentPrice), ""));

		expander.addTagDataList(composition.getType().getValue(),
				shippingTagList);
	}

	// 2012-02-06 yyq add end desc:发送短信

	private static class ShippingDetailCustomTag implements SmsTemplateTag {

		private String name;

		private String value;

		private String dummyData;

		private boolean required;

		public ShippingDetailCustomTag(String name, String value,
				String dummyData) {
			this.name = name;
			this.value = value;
			this.dummyData = dummyData;
			this.required = false;
		}

		public String getDummyData() {
			return dummyData;
		}

		public String getName() {
			return name;
		}

		public String getTagDiv() {
			return SHIPPING_HEADER;
		}

		public String getValue() {
			return value;
		}

		public boolean isRequired() {
			return required;
		}
	}

	public ServiceResult sendArrivalInformationSms(
			CommodityHeader commodityHeader, CommodityDetail commodityDetail,
			Shop shop, List<Customer> customerList) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendBirthdaySms(int beforeDays, Shop shop) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendCancelOrderSms(OrderContainer orderContainer,
			CashierPayment payment, Shop shop) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendCustomerRegisterdSms(Customer customer, Shop shop) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendImmediate(SmsInfo sms) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendInformationSms(CustomerSearchCondition condition,
			SmsTemplateSuite smsTemplateSuite) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendPasswordChangeSms(Customer customer, Shop shop) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendPasswordSendSms(Customer customer,
			Reminder reminder, Shop shop, String contextPath) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendPaymentReceivedSms(OrderContainer orderContainer,
			OrderSummary orderSummary, Shop shop) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendPaymentReminderSms(OrderContainer orderContainer,
			OrderSummary orderSummary, Shop shop, CashierPayment cashierPayment) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendPointExpiredSms(int beforeDays) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendWithdrawalRequestSms(Customer customer, Shop shop) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResult sendWithdrawalSms(Customer customer, Shop shop) {
		// TODO Auto-generated method stub
		return null;
	}

	// 20111230 ob add start
	@Override
	public ServiceResult sendCouponStartBeforeSms(
			NewCouponHistory newCouponHistory, Shop shop, Customer customer,
			String mobileNo) {
		ServiceResultImpl result = new ServiceResultImpl();
		 //2013/05/05 优惠券对应 ob modify start
//		SmsTemplateSuite smsTemplateSuite = createSmsTemplate(shop
//				.getShopCode(), SmsType.COUPON_START.getValue(), 0L);
    SmsTemplateSuite smsTemplateSuite = createSmsTemplate(shop
        .getShopCode(), SmsType.COUPON_START.getValue(), 0L, customer.getCustomerCode());
		 //2013/05/05 优惠券对应 ob modify end
		if (noSetSmsTemplate(shop.getShopCode(), MailType.COUPON_START
				.getValue(), 0L)) {
			result
					.addServiceError(SmsingServiceErrorContent.NO_SMS_TEMPLATE_ERROR);
			return result;
		}

		SmsTemplateExpander expander = new SmsTemplateExpander(smsTemplateSuite);
		setCouponStartTag(expander, SmsComposition.COUPON_START_MAIN,
				newCouponHistory, customer);

		RespectiveSmsqueue queue = createSmsQueue(smsTemplateSuite, mobileNo,
				MailSendStatus.NOT_SENT.longValue(), expander.expandTemplate());
		ServiceResult sendSmsResult = sendRespectiveSms(queue);

		if (sendSmsResult.hasError()) {
			for (ServiceErrorContent error : sendSmsResult
					.getServiceErrorList()) {
				result.addServiceError(error);
			}
		}

		return result;
	}

	private void setCouponStartTag(SmsTemplateExpander expander,
			SmsComposition composition, NewCouponHistory newCouponHistory,
			Customer customer) {
		SmsTagDataList couponStartTagList = new SmsTagDataList();
		couponStartTagList.add(CouponEndTag.LAST_NAME, customer.getLastName());
		couponStartTagList.add(CouponEndTag.COUPON_CODE, newCouponHistory
				.getCouponIssueNo());

    //2013/04/15 优惠券对应 ob add start
    //优惠劵名称
    String lang = getLanguageCode(customer.getCustomerCode());
    if (!StringUtil.isNullOrEmpty(lang)) {
      if (lang.equals("zh-cn")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
      } else if (lang.equals("ja-jp")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameJp());
      } else {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameEn());
      }
    } else {
      couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
    }
    //2013/04/15 优惠券对应 ob add end
		if (StringUtil.hasValue(newCouponHistory.getIssueReason())) {
			couponStartTagList.add(CouponEndTag.REASON, newCouponHistory
					.getIssueReason());
		} else {
			couponStartTagList.add(CouponEndTag.REASON, Messages
					.getString("service.impl.UtilServiceImpl.8"));
		}
		couponStartTagList.add(CouponEndTag.MIN_AMOUNT, NumUtil
				.toString(newCouponHistory.getMinUseOrderAmount()));
		if (CouponIssueType.FIXED.longValue().equals(
				newCouponHistory.getCouponIssueType())) {
			couponStartTagList.add(CouponEndTag.AMOUNT, NumUtil
					.toString(newCouponHistory.getCouponAmount())
					+ Messages.getString("numUtil.formatCurrency.0"));
		} else {
			couponStartTagList.add(CouponEndTag.AMOUNT, NumUtil
					.toString(newCouponHistory.getCouponProportion())
					+ "%");
		}
		couponStartTagList.add(CouponEndTag.USE_START_DATE, DateUtil
				.toDateTimeString(newCouponHistory.getUseStartDatetime()));
		couponStartTagList.add(CouponEndTag.USE_END_DATE, DateUtil
				.toDateTimeString(newCouponHistory.getUseEndDatetime()));
		expander.addTagDataList(composition.getType().getValue(),
				couponStartTagList);
	}

	private void setCouponEndTag(SmsTemplateExpander expander,
			SmsComposition composition, NewCouponHistory newCouponHistory,
			Customer customer) {
		SmsTagDataList couponStartTagList = new SmsTagDataList();
		couponStartTagList.add(CouponEndTag.LAST_NAME, customer.getLastName());
		couponStartTagList.add(CouponEndTag.COUPON_CODE, newCouponHistory
				.getCouponIssueNo());
		
    //2013/04/15 优惠券对应 ob add start
    //优惠劵名称
    String lang = getLanguageCode(customer.getCustomerCode());
    if (!StringUtil.isNullOrEmpty(lang)) {
      if (lang.equals("zh-cn")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
      } else if (lang.equals("ja-jp")) {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameJp());
      } else {
        couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponNameEn());
      }
    } else {
      couponStartTagList.add(CouponEndTag.COUPON_NAME, newCouponHistory.getCouponName());
    }
    //2013/04/15 优惠券对应 ob add end
		if (StringUtil.hasValue(newCouponHistory.getIssueReason())) {
			couponStartTagList.add(CouponEndTag.REASON, newCouponHistory
					.getIssueReason());
		} else {
			couponStartTagList.add(CouponEndTag.REASON, Messages
					.getString("service.impl.UtilServiceImpl.8"));
		}
		couponStartTagList.add(CouponEndTag.MIN_AMOUNT, NumUtil
				.toString(newCouponHistory.getMinUseOrderAmount()));
		if (CouponIssueType.FIXED.longValue().equals(
				newCouponHistory.getCouponIssueType())) {
			couponStartTagList.add(CouponEndTag.AMOUNT, NumUtil
					.toString(newCouponHistory.getCouponAmount())
					+ Messages.getString("numUtil.formatCurrency.0"));
		} else {
			couponStartTagList.add(CouponEndTag.AMOUNT, NumUtil
					.toString(newCouponHistory.getCouponProportion())
					+ "%");
		}
		couponStartTagList.add(CouponEndTag.USE_START_DATE, DateUtil
				.toDateTimeString(newCouponHistory.getUseStartDatetime()));
		couponStartTagList.add(CouponEndTag.USE_END_DATE, DateUtil
				.toDateTimeString(newCouponHistory.getUseEndDatetime()));
		expander.addTagDataList(composition.getType().getValue(),
				couponStartTagList);
	}

	public ServiceResult sendCouponEndBeforeSms(
			NewCouponHistory newCouponHistory, Shop shop, Customer customer,
			String mobileNo) {
		ServiceResultImpl result = new ServiceResultImpl();
	  //2013/04/15 优惠券对应 ob modify start
//		SmsTemplateSuite smsTemplateSuite = createSmsTemplate(shop
//				.getShopCode(), SmsType.COUPON_END.getValue(), 0L);
    SmsTemplateSuite smsTemplateSuite = createSmsTemplate(shop
        .getShopCode(), SmsType.COUPON_END.getValue(), 0L, customer.getCustomerCode());
	  //2013/04/15 优惠券对应 ob modify start
		if (noSetSmsTemplate(shop.getShopCode(),
				MailType.COUPON_END.getValue(), 0L)) {
			result
					.addServiceError(SmsingServiceErrorContent.NO_SMS_TEMPLATE_ERROR);
			return result;
		}

		SmsTemplateExpander expander = new SmsTemplateExpander(smsTemplateSuite);
		setCouponEndTag(expander, SmsComposition.COUPON_END_MAIN,
				newCouponHistory, customer);

		RespectiveSmsqueue queue = createSmsQueue(smsTemplateSuite, mobileNo,
				MailSendStatus.NOT_SENT.longValue(), expander.expandTemplate());
		ServiceResult sendSmsResult = sendRespectiveSms(queue);

		if (sendSmsResult.hasError()) {
			for (ServiceErrorContent error : sendSmsResult
					.getServiceErrorList()) {
				result.addServiceError(error);
			}
		}
		return result;
	}

	// 20111230 ob add end

	// 20130327 add by yyq start

	// 20130327 add by yyq end

	/**
	 * 重写方法查询手机号码是否被绑定
	 */
	@Override
	public boolean isExist(String mobileNumber) {
		// TODO Auto-generated method stub
		String queryCheck = "SELECT mobile_number FROM customer WHERE mobile_number = ?";
		Query query = new SimpleQuery(queryCheck, mobileNumber);
		String count = DatabaseUtil.executeScalar(query, String.class);
		return !StringUtil.isNullOrEmpty(count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jp.co.sint.webshop.service.SmsingService#sendSms(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean sendSms(String[] mobile, String msg) {
		// TODO Auto-generated method stub
		boolean flag = false;
		Logger logger = Logger.getLogger(this.getClass());
		SmsSender sender = DIContainer.getSmsSender();

		try {
			flag = sender.sendSms(mobile, msg);
		} catch (MessagingException e) {
			logger.error(Messages.log("service.impl.SmsingServiceImpl.2"));
		} catch (UnsupportedEncodingException e) {
			logger.error(Messages.log("service.impl.SmsingServiceImpl.3"));
		}
		return flag;
	}

	/**
	 * 重写验证码短信方法
	 */
	@Override
	public ServiceResult sendMobileAuthCode(MobileAuth mobileAuth,String languageCode) {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger(this.getClass());
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		WebshopConfig config = DIContainer.getWebshopConfig();

		Long authId = DatabaseUtil.generateSequence(SequenceType.AUTH_ID);
		mobileAuth.setMobileAuthId(authId.toString());
		Date nowDate = DateUtil.getSysdate();
		mobileAuth.setStartDatetime(nowDate);
		mobileAuth.setEndDatetime(DateUtil.addMinute(nowDate, config
				.getAuthcodeTimeout()));
		// 获得验证码
		String authCode = VerifyCode.genRamonCode(6);
		mobileAuth.setAuthCode(authCode);
		setUserStatus(mobileAuth);
		ValidationSummary validResult = BeanValidator.validate(mobileAuth);
		if (validResult.hasError()) {
			serviceResult
					.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			for (String message : validResult.getErrorMessages()) {
				logger.error(message);
			}
			return serviceResult;
		}
    // 封装短信消息
    StringBuilder sb = new StringBuilder();
    if (StringUtil.isNullOrEmpty(languageCode)) {
      languageCode = "zh-cn";
    }
    if (languageCode.equals("zh-cn")) {
      sb.append("【品店】您所需的验证码为 ");
      sb.append(authCode);
      sb.append("。");
      sb.append("请在 " + config.getAuthcodeTimeout() + "分钟内使用。退订回复TD。");
    } else if (languageCode.equals("ja-jp")) {
      sb.append("【品店】品店より認証番号をお送りします。コード:");
      sb.append(authCode);
      sb.append(",");
      sb.append(config.getAuthcodeTimeout() + "分以内に入力して下さい。受信したくない方はTDを返信して下さい。");
    } else if (languageCode.equals("en-us")) {
      sb.append("【Pinstore】Auth-code(");
      sb.append(authCode);
      sb.append(") ");
      sb.append(config.getAuthcodeTimeout() + "mins validation.Reply TD to cancel.");
    }
		TransactionManager txMgr = DIContainer.getTransactionManager();
		try {
			txMgr.begin(getLoginInfo());

			txMgr.insert(mobileAuth);

			// 发送SMS
			String[] mobiles = new String[] { mobileAuth.getMobileNumber() };
			boolean flag = sendSms(mobiles, sb.toString());
			if(!flag){
				serviceResult
				.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
			}
			txMgr.commit();
		} catch (ConcurrencyFailureException e) {
			txMgr.rollback();
			throw e;
		} catch (RuntimeException e) {
			txMgr.rollback();
			serviceResult
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			txMgr.dispose();
		}
		return serviceResult;
	}
	 
	//2013/04/15 优惠券对应 ob add start
	/***
	 * 取得用户语言
	 * @param customerCode 顾客编号
	 */ 
	 public String getLanguageCode(String customerCode) {
	    Query queryCustomer = null;
	    String resStr = "zh-cn";
	    queryCustomer = new SimpleQuery("SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?", customerCode);
	    Customer customerBean = DatabaseUtil.loadAsBean(queryCustomer, Customer.class);

	    if (customerBean != null) {
	      if (customerBean.getLanguageCode().equals(LanguageCode.Zh_Cn.getValue())) {
	        resStr = "zh-cn";
	      } else if (customerBean.getLanguageCode().equals(LanguageCode.Ja_Jp.getValue())) {
	        resStr = "ja-jp";
	      } else {
	        resStr = "en-us";
	      }
	    }
	    return resStr;
	  }
	 //2013/04/15 优惠券对应 ob add end

}
