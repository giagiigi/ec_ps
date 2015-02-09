package jp.co.sint.webshop.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchQuery;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.dao.AreaDao;
import jp.co.sint.webshop.data.dao.BankDao;
import jp.co.sint.webshop.data.dao.CommissionDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CouponIssueDao;
import jp.co.sint.webshop.data.dao.DeliveryAppointedTimeDao;
import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.dao.DeliveryLocationDao;
import jp.co.sint.webshop.data.dao.DeliveryRegionChargeDao;
import jp.co.sint.webshop.data.dao.DeliveryRegionDao;
import jp.co.sint.webshop.data.dao.DeliveryRelatedInfoDao;
import jp.co.sint.webshop.data.dao.DeliveryTypeDao;
import jp.co.sint.webshop.data.dao.JdDeliveryLocationDao;
import jp.co.sint.webshop.data.dao.JdDeliveryRegionDao;
import jp.co.sint.webshop.data.dao.JdDeliveryRelatedInfoDao;
import jp.co.sint.webshop.data.dao.JdPrefectureDao;
import jp.co.sint.webshop.data.dao.MailTemplateDetailDao;
import jp.co.sint.webshop.data.dao.MailTemplateHeaderDao;
import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.dao.PostPaymentDao;
import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.dao.RegionBlockDao;
import jp.co.sint.webshop.data.dao.RegionBlockLocationDao;
import jp.co.sint.webshop.data.dao.ShippingChargeDao;
import jp.co.sint.webshop.data.dao.ShippingHeaderDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.dao.SmsTemplateDetailDao;
import jp.co.sint.webshop.data.dao.TmallDeliveryLocationDao;
import jp.co.sint.webshop.data.dao.TmallDeliveryRegionDao;
import jp.co.sint.webshop.data.dao.TmallDeliveryRelatedInfoDao;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.data.domain.DefaultFlg;
import jp.co.sint.webshop.data.domain.DeleteFlg;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.MailContentType;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodDisplayType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.domain.ShippingChargeFlg;
import jp.co.sint.webshop.data.domain.ShippingChargeFreeFlg;
import jp.co.sint.webshop.data.domain.SmsType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryLocation;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.data.dto.DeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.data.dto.JdDeliveryLocation;
import jp.co.sint.webshop.data.dto.JdDeliveryRegion;
import jp.co.sint.webshop.data.dto.JdDeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.JdPrefecture;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.PostPayment;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.data.dto.RegionBlockLocation;
import jp.co.sint.webshop.data.dto.ShippingCharge;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.data.dto.TmallDeliveryLocation;
import jp.co.sint.webshop.data.dto.TmallDeliveryRegion;
import jp.co.sint.webshop.data.dto.TmallDeliveryRelatedInfo;
import jp.co.sint.webshop.mail.MailComposition;
import jp.co.sint.webshop.mail.MailCompositionLocator;
import jp.co.sint.webshop.mail.MailTemplateTag;
import jp.co.sint.webshop.mail.MailTemplateUtil;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceException;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.catalog.CommodityLayoutQuery;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.mail.DefaultMailTextCreater;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.result.ShopManagementServiceErrorContent;
import jp.co.sint.webshop.service.shop.CouponQuery;
import jp.co.sint.webshop.service.shop.CouponResearch;
import jp.co.sint.webshop.service.shop.CouponResearchInfo;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeCondition;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeInfo;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeQuery;
import jp.co.sint.webshop.service.shop.MailTemplateSuite;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.service.shop.ShippingChargeSuite;
import jp.co.sint.webshop.service.shop.ShopDeleteQuery;
import jp.co.sint.webshop.service.shop.ShopListSearchCondition;
import jp.co.sint.webshop.service.shop.ShopManagementServiceQuery;
import jp.co.sint.webshop.service.shop.ShopManagementSimpleSql;
import jp.co.sint.webshop.service.shop.ShopSearchQuery;
import jp.co.sint.webshop.service.shop.SmsTemplateSuite;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class ShopManagementServiceImpl extends AbstractServiceImpl implements ShopManagementService {

	private static final long serialVersionUID = 1L;

	/**
	 * shopCodeに関連付いている全てのデータを削除する
	 */
	public ServiceResult deleteShop(String shopCode) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		TransactionManager manager = DIContainer.getTransactionManager();

		// shopCodeに関連付いているデータの削除
		try {
			manager.begin(getLoginInfo());
			for (String deleteQuery : ShopDeleteQuery.getDeleteQuery()) {
				manager.executeUpdate(deleteQuery, shopCode);
			}

			// 10.1.2 10098 削除 ここから
			// // 支払い方法テーブルは削除フラグを1にする事で論理削除を行う
			// manager.executeUpdate(ShopManagementSimpleSql.UPDATE_PAYMENT_TO_DELETE,
			// this.getLoginInfo().getRecordingFormat(),
			// DatabaseUtil.getSystemDatetime(), shopCode);
			// 10.1.2 10098 削除 ここまで

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

	public int countUndeletableOrders(String shopCode) {

		Long unDeletableShopCount = NumUtil.toLong(DatabaseUtil.executeScalar(
				new SimpleQuery(
						ShopManagementSimpleSql.COUNT_UNDELETABLE_ORDERS,
						shopCode, FixedSalesStatus.NOT_FIXED.getValue(),
						PaymentStatus.NOT_PAID.getValue(),
						OrderStatus.CANCELLED.getValue())).toString());

		return unDeletableShopCount.intValue();
	}

	public ServiceResult deleteBank(String shopCode, Long paymentMethodNo,
			String bankCode, String bankBranchCode, String accountNo) {
		ServiceResultImpl result = new ServiceResultImpl();

		// 登録銀行が2件以上の場合削除可能とする。
		if (getBankList(shopCode, paymentMethodNo).size() > 1) {

			BankDao dao = DIContainer.getDao(BankDao.class);

			dao.delete(shopCode, paymentMethodNo, bankCode, bankBranchCode,
					accountNo);
		} else {
			result
					.addServiceError(ShopManagementServiceErrorContent.DELETE_ALL_BANK);
		}

		return result;
	}

	// 05-20 Add start
	public ServiceResult deletePost(String shopCode, Long paymentMethodNo,
			String postAccountNo) {
		// String shopCode, Long paymentMethodNo,String postAccountNo
		ServiceResultImpl result = new ServiceResultImpl();
		if (getPostList(shopCode, paymentMethodNo).size() > 1) {
			PostPaymentDao dao = DIContainer.getDao(PostPaymentDao.class);
			dao.delete(shopCode, paymentMethodNo, postAccountNo);
		} else {
			result
					.addServiceError(ShopManagementServiceErrorContent.DELETE_ALL_POST);
		}
		return result;
	}

	// 05-20 Add end

	public ServiceResult deleteCommission(String shopCode,
			Long paymentMethodNo, BigDecimal paymentPriceThreshold) {
		ServiceResultImpl result = new ServiceResultImpl();
		CommissionDao dao = DIContainer.getDao(CommissionDao.class);

		dao.delete(shopCode, paymentMethodNo, paymentPriceThreshold);

		return result;
	}

	public ServiceResult deleteDeliveryAppointedTime(String shopCode,
			Long deliveryTypeNo, String deliveryAppointedTimeCode) {
		ServiceResultImpl result = new ServiceResultImpl();

		DeliveryAppointedTimeDao dao = DIContainer
				.getDao(DeliveryAppointedTimeDao.class);

		DeliveryAppointedTime orgTime = dao.load(shopCode, deliveryTypeNo,
				deliveryAppointedTimeCode);

		if (orgTime == null) {
			return result;
		} else {
			dao.delete(orgTime, getLoginInfo());
		}

		return result;
	}

	public ServiceResult deleteDeliveryType(String shopCode, Long deliveryTypeNo) {
		TransactionManager manager = DIContainer.getTransactionManager();
		ServiceResultImpl serviceResult = new ServiceResultImpl();

		DeliveryTypeDao dao = DIContainer.getDao(DeliveryTypeDao.class);

		DeliveryType orgType = dao.load(shopCode, deliveryTypeNo);

		if (orgType == null) {
			return serviceResult;
		}

		// 商品チェック
		CommodityHeaderDao commodityDao = DIContainer
				.getDao(CommodityHeaderDao.class);
		Query query = new SimpleQuery(
				ShopManagementSimpleSql.COUNT_COMMODITY_WITH_DELIVERY_TYPE_NO,
				shopCode, deliveryTypeNo);
		List<CommodityHeader> commodityList = commodityDao.findByQuery(query);

		if (commodityList.size() > 0) {
			serviceResult
					.addServiceError(ShopManagementServiceErrorContent.DELIVERY_TYPE_STILL_USE_ERROR);
			return serviceResult;
		}

		// 出荷チェック
		ShippingHeaderDao shippingDao = DIContainer
				.getDao(ShippingHeaderDao.class);
		Query shippingQuery = new SimpleQuery(
				ShopManagementSimpleSql.COUNT_SHIPPING_WITH_DELIVERY_TYPE_NO,
				shopCode, deliveryTypeNo);
		List<ShippingHeader> shippingList = shippingDao
				.findByQuery(shippingQuery);

		if (shippingList.size() > 0
				|| getDeliveryTypeList(shopCode).size() <= 1) {
			serviceResult
					.addServiceError(ShopManagementServiceErrorContent.USED_DELIVERY_TYPE);
			return serviceResult;
		}

		// shopCodeに関連付いているデータの削除
		try {
			manager.begin(getLoginInfo());

			manager.delete(orgType);

			manager.executeUpdate(
					ShopManagementSimpleSql.DELETE_SHIPPING_CHARGE, shopCode,
					deliveryTypeNo);

			manager.executeUpdate(
					ShopManagementSimpleSql.DELETE_DELIVERY_APPOINTED,
					shopCode, deliveryTypeNo);

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

	public ServiceResult deleteMailTemplate(MailTemplateSuite template) {
		ServiceResultImpl result = new ServiceResultImpl();
		MailTemplateHeaderDao headerDao = DIContainer
				.getDao(MailTemplateHeaderDao.class);
		MailTemplateHeader header = template.getMailTemplateHeader();
		MailTemplateHeader deleteHeader = headerDao.load(header.getShopCode(),
				header.getMailType(), header.getMailTemplateNo());

		MailTemplateDetailDao detailDao = DIContainer
				.getDao(MailTemplateDetailDao.class);
		List<MailTemplateDetail> deleteDetailList = new ArrayList<MailTemplateDetail>();
		for (MailTemplateDetail detail : template.getMailTemplateDetail()) {
			MailTemplateDetail org = detailDao.load(detail.getShopCode(),
					detail.getMailType(), detail.getMailTemplateNo(), detail
							.getMailTemplateBranchNo());
			if (org != null) {
				deleteDetailList.add(org);
			}
		}

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());

			if (deleteHeader != null) {
				manager.delete(deleteHeader);
			}

			for (MailTemplateDetail detail : deleteDetailList) {
				manager.delete(detail);
			}
			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	public ServiceResult deletePaymentMethod(String shopCode,
			Long paymentMethodNo) {
		ServiceResultImpl result = new ServiceResultImpl();

		PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);
		Query query = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_PAYMENT_METHOD_LIST, shopCode);
		List<PaymentMethod> paymentMethodList = dao.findByQuery(query);
		List<PaymentMethod> displayPaymentList = new ArrayList<PaymentMethod>();
		for (PaymentMethod paymentMethod : paymentMethodList) {
			if (!paymentMethod.getPaymentMethodType().equals(
					PaymentMethodType.NO_PAYMENT.getValue())
					&& !paymentMethod.getPaymentMethodType().equals(
							PaymentMethodType.POINT_IN_FULL.getValue())) {
				displayPaymentList.add(paymentMethod);
			}
		}

		if (displayPaymentList.size() <= 1) {
			result
					.addServiceError(ShopManagementServiceErrorContent.NOT_DELETE_PAYMENT);
			return result;
		}

		// 10.1.1 10027 修正 ここから
		// dao.delete(shopCode, paymentMethodNo);
		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());
			PaymentMethod paymentMethod = dao.load(shopCode, paymentMethodNo);
			if (paymentMethod != null) {
				manager.delete(paymentMethod);
			}
			for (Bank bank : getBankList(shopCode, paymentMethodNo)) {
				manager.delete(bank);
			}
			// Add by V10-CH start
			for (PostPayment post : getPostList(shopCode, paymentMethodNo)) {
				manager.delete(post);
			}
			// Add by V10-CH end
			// 10.1.2 10097 追加 ここから
			for (Commission commission : getCommissionList(shopCode,
					paymentMethodNo)) {
				manager.delete(commission);
			}
			// 10.1.2 10097 追加 ここまで
			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}
		// 10.1.1 10027 修正 ここまで
		return result;
	}

	public ServiceResult deleteRegionBlockList(String shopCode,
			Long regionBlockId) {
		ServiceResultImpl result = new ServiceResultImpl();

		RegionBlockDao dao = DIContainer.getDao(RegionBlockDao.class);
		dao.delete(shopCode, regionBlockId);

		return result;
	}

	public List<Bank> getBankList(String shopCode, Long paymentMethodNo) {
		BankDao dao = DIContainer.getDao(BankDao.class);
		Query query = new SimpleQuery(ShopManagementSimpleSql.LOAD_BANK_LIST,
				shopCode, paymentMethodNo);
		List<Bank> bankList = dao.findByQuery(query);
		return bankList;
	}

	// 05-20 Add start
	public List<PostPayment> getPostList(String shopCode, Long paymentMethodNo) {
		PostPaymentDao dao = DIContainer.getDao(PostPaymentDao.class);
		Query query = new SimpleQuery(ShopManagementSimpleSql.LOAD_POST_LIST,
				shopCode, paymentMethodNo);
		List<PostPayment> postList = dao.findByQuery(query);
		return postList;
	}

	// 05-20 Add end

	public List<DeliveryAppointedTime> getDeliveryAppointedTimeList(
			String shopCode, Long deliveryTypeNo) {
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_DELIVERY_APPOINTED_TIME_LIST,
				shopCode, deliveryTypeNo);
		List<DeliveryAppointedTime> timeList = DatabaseUtil.loadAsBeanList(q,
				DeliveryAppointedTime.class);

		return timeList;
	}

	public List<DeliveryAppointedTime> getDeliveryAppointedTimeList(
			String shopCode, Long deliveryTypeNo,
			Long deliveryAppointedStartTime, Long deliveryAppointedEndTime) {
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_DELIVERY_APPOINTED_TIME_LIST
						+ ShopManagementSimpleSql.LOAD_DELIVERY_APPOINTED_TIME_LIST_ADDITION_TIME,
				shopCode, deliveryTypeNo, deliveryAppointedStartTime,
				deliveryAppointedEndTime);

		List<DeliveryAppointedTime> timeList = DatabaseUtil.loadAsBeanList(q,
				DeliveryAppointedTime.class);

		return timeList;
	}

	public DeliveryType getDeliveryType(String shopCode, Long deliveryNo) {
		DeliveryTypeDao dao = DIContainer.getDao(DeliveryTypeDao.class);

		return dao.load(shopCode, deliveryNo);
	}

	public List<DeliveryType> getDeliveryTypeList(String shopCode) {
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_DELIVERY_TYPE_LIST, shopCode);
		List<DeliveryType> deliveryTypeList = DatabaseUtil.loadAsBeanList(q,
				DeliveryType.class);

		return deliveryTypeList;
	}

	public List<Holiday> getHoliday(String shopCode, Date date) {
		String dateFrom = DateUtil.getYYYY(date) + "/" + DateUtil.getMM(date)
				+ "/01";
		String dateTo = DateUtil.getYYYY(date) + "/" + DateUtil.getMM(date)
				+ "/" + DateUtil.getEndDay(date);
		Query q = new SimpleQuery(ShopManagementSimpleSql.LOAD_HOLIDAY,
				shopCode, dateFrom, dateTo);
		List<Holiday> holidayList = DatabaseUtil.loadAsBeanList(q,
				Holiday.class);

		return holidayList;
	}

	public MailTemplateSuite getMailTemplateConfig(String shopCode,
			String mailType, Long templateNo) {
		MailTemplateSuite suite = new MailTemplateSuite();
		MailTemplateHeaderDao dao = DIContainer
				.getDao(MailTemplateHeaderDao.class);
		MailTemplateHeader header = dao.load(shopCode, mailType, templateNo);
		suite.setMailTemplateHeader(header);

		Query query = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_MAIL_TEMPLATE_DETAIL, shopCode,
				mailType, templateNo);
		List<MailTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(
				query, MailTemplateDetail.class);
		suite.setMailTemplateDetail(detailList);

		return suite;
	}

	// Add by V10-CH start
	public SmsTemplateSuite getSmsTemplateConfig(String smsType) {
		SmsTemplateSuite suite = new SmsTemplateSuite();
		Query query = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL, smsType);
		List<SmsTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(query,
				SmsTemplateDetail.class);
		suite.setSmsTemplateDetail(detailList);
		return suite;
	}

	// Add by V10-CH end

	public List<CodeAttribute> getInformationMailTypeList() {
		List<CodeAttribute> informationMailTypeList = new ArrayList<CodeAttribute>();
		Query q = new SimpleQuery(ShopManagementSimpleSql.LOAD_INFORMATION);
		List<MailTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(q,
				MailTemplateDetail.class);
		for (MailTemplateDetail detail : detailList) {
			NameValue value = new NameValue();
			value.setName(detail.getMailCompositionName());
			value.setValue(Long.toString(detail.getMailTemplateNo()));

			informationMailTypeList.add(value);
		}
		return informationMailTypeList;
	}

	// Add by V10-CH start
	public List<CodeAttribute> getInformationSmsTypeList() {
		List<CodeAttribute> informationSmsTypeList = new ArrayList<CodeAttribute>();
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_SMS_TEMPLATE_DETAIL, "06");
		List<SmsTemplateDetail> detailList = DatabaseUtil.loadAsBeanList(q,
				SmsTemplateDetail.class);
		for (SmsTemplateDetail detail : detailList) {
			NameValue value = new NameValue();
			value.setValue(Long.toString(detail.getSmsTemplateNo()));
			informationSmsTypeList.add(value);
		}
		return informationSmsTypeList;
	}

	// Add by V10-CH end
	public PaymentMethodSuite getPaymentMethod(String shopCode,
			Long paymentMethodNo) {
		PaymentMethodDao paymentMethodDao = DIContainer
				.getDao(PaymentMethodDao.class);
		CommissionDao commissionDao = DIContainer.getDao(CommissionDao.class);

		Query commissionQuery = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_COMMISSION_LIST, shopCode,
				paymentMethodNo);
		List<Commission> commissionList = commissionDao
				.findByQuery(commissionQuery);

		PaymentMethod method = paymentMethodDao.load(shopCode, paymentMethodNo);

		PaymentMethodSuite methodSuite = new PaymentMethodSuite();

		if (method != null) {
			methodSuite.setPaymentMethod(method);
			methodSuite.setCommissionList(commissionList);
		}
		return methodSuite;
	}

	public boolean isDeletableDelivery(String shopCode, Long deliveryTypeNo) {
		boolean deletableDelivery = true;
		// 10.1.6 10275 修正 ここから
		//
		// // 商品チェック
		// CommodityHeaderDao commodityDao =
		// DIContainer.getDao(CommodityHeaderDao.class);
		// Query query = new
		// SimpleQuery(ShopManagementSimpleSql.COUNT_COMMODITY_WITH_DELIVERY_TYPE_NO,
		// shopCode, deliveryTypeNo);
		// List<CommodityHeader> commodityList =
		// commodityDao.findByQuery(query);
		//
		// if (commodityList.size() > 0) {
		// return false;
		// }
		//
		// // 出荷チェック
		// ShippingHeaderDao shippingDao =
		// DIContainer.getDao(ShippingHeaderDao.class);
		// Query shippingQuery = new
		// SimpleQuery(ShopManagementSimpleSql.COUNT_SHIPPING_WITH_DELIVERY_TYPE_NO,
		// shopCode, deliveryTypeNo);
		// List<ShippingHeader> shippingList =
		// shippingDao.findByQuery(shippingQuery);
		//
		// if (shippingList.size() > 0) {
		// return false;
		// }
		// 商品ヘッダ、出荷ヘッダに外部キーとして保存されてる配送種別は削除不可
		for (String tableName : new String[] { "COMMODITY_HEADER",
				"SHIPPING_HEADER" }) {
			Object associated = DatabaseUtil
					.executeScalar(new SimpleQuery(ShopManagementSimpleSql
							.getDeliveryTypeFindQuery(tableName), shopCode,
							deliveryTypeNo));
			if (associated != null) {
				return false;
			}
		}
		// 10.1.6 10275 修正 ここまで
		return deletableDelivery;
	}

	public boolean isDeletablePayment(String deleteShopCode,
			Long deletePaymentMethodNo) {
		// 10.1.7 10317 修正 ここから
		// boolean deletablePayment = true;
		//
		// Long modifiableCount = NumUtil.toLong(DatabaseUtil.executeScalar(
		// new SimpleQuery(ShopManagementSimpleSql.LOAD_MODIFIABLE_ORDER_COUNT,
		// deleteShopCode, deletePaymentMethodNo,
		// PaymentStatus.NOT_PAID.getValue(),
		// FixedSalesStatus.NOT_FIXED.getValue())).toString());
		// if (modifiableCount > 0) {
		// return false;
		// }
		//
		// return deletablePayment;
		Long result = NumUtil.toLong(DatabaseUtil.executeScalar(
				new SimpleQuery(
						ShopManagementSimpleSql.EXISTS_DELETABLE_PAYMENT_QUERY,
						deleteShopCode, deletePaymentMethodNo,
						PaymentStatus.NOT_PAID.getValue(),
						FixedSalesStatus.NOT_FIXED.getValue())).toString());
		return (result == null || result.equals(BigDecimal.ZERO));
		// 10.1.7 10317 修正 ここまで
	}

	public List<PaymentMethod> getPaymentMethodList(String shopCode) {
		PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);

		// 支払方法一覧の取得
		List<PaymentMethod> paymentMethodList = dao.findByQuery(
				ShopManagementSimpleSql.LOAD_PAYMENT_METHOD_LIST, shopCode);

		return paymentMethodList;
	}

	public List<PaymentMethod> getAllPaymentMethodList(String shopCode) {
		PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);

		// 支払方法一覧の取得
		List<PaymentMethod> paymentMethodList = dao.findByQuery(
				ShopManagementSimpleSql.LOAD_ALL_PAYMENT_METHOD_LIST, shopCode);

		return paymentMethodList;
	}

	public List<Commission> getCommissionList(String shopCode,
			Long paymentMethodNo) {
		CommissionDao dao = DIContainer.getDao(CommissionDao.class);

		List<Commission> commissionList = dao.findByQuery(
				ShopManagementSimpleSql.LOAD_COMMISSION_LIST, shopCode,
				paymentMethodNo);
		return commissionList;
	}

	public Commission getCommission(String shopCode, Long paymentMethodNo,
			BigDecimal price) {
		// modify by V10-CH 170 start
		if (BigDecimalUtil.isAbove(price, getMaxPaymentPriceThreshold())) {
			// if (price > 99999999) {
			// price = 99999999L;
			// }
		}
		// modify by V10-CH 170 end
		Query commissionQuery = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_COMMISSION, shopCode,
				paymentMethodNo, shopCode, paymentMethodNo, price);
		Commission commission = DatabaseUtil.loadAsBean(commissionQuery,
				Commission.class);
		return commission;
	}

	// modified by zhanghaibin start 2010-05-19
	private BigDecimal getMaxPaymentPriceThreshold() {
		Precision precision = BeanUtil.getAnnotation(Commission.class,
				"paymentPriceThreshold", Precision.class);
		return NumUtil.getActualMaximum(precision.precision(), precision
				.scale());
	}

	// modified by zhanghaibin end 2010-05-19

	public List<RegionBlockLocation> getRegionBlockLocationList(String shopCode) {
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_REGION_BLOCK_LOCATION_LIST,
				shopCode);

		List<RegionBlockLocation> prefList = DatabaseUtil.loadAsBeanList(q,
				RegionBlockLocation.class);

		return prefList;
	}

	public List<RegionBlock> getRegionBlockList(Shop shop) {
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_REGION_BLOCK_LIST, shop
						.getShopCode());

		List<RegionBlock> blockList = DatabaseUtil.loadAsBeanList(q,
				RegionBlock.class);
		return blockList;
	}

	public List<ShippingChargeSuite> getShippingChargeList(String shopCode,
			Long deliveryTypeNo) {
		Shop shop = new Shop();
		shop.setShopCode(shopCode);
		List<RegionBlock> blockList = getRegionBlockList(shop);
		Query q = new SimpleQuery(ShopManagementSimpleSql.LOAD_SHIPPING_CHARGE,
				shopCode, deliveryTypeNo);
		Map<Long, String> regionMap = new HashMap<Long, String>();
		for (RegionBlock block : blockList) {
			regionMap.put(block.getRegionBlockId(), block.getRegionBlockName());
		}

		List<ShippingCharge> chargeList = DatabaseUtil.loadAsBeanList(q,
				ShippingCharge.class);

		List<ShippingChargeSuite> list = new ArrayList<ShippingChargeSuite>();
		for (ShippingCharge charge : chargeList) {
			ShippingChargeSuite suite = new ShippingChargeSuite();
			suite.setRegionBlockName(regionMap.get(charge.getRegionBlockId()));
			suite.setShippingCharge(charge);

			list.add(suite);
		}

		return list;
	}

	public ShippingCharge getShippingCharge(String shopCode,
			Long deliveryTypeNo, String prefectureCode) {
		List<RegionBlockLocation> regionBlockLocationList = getRegionBlockLocationList(shopCode);
		Long regionBlockId = null;
		for (RegionBlockLocation regionBlockLocation : regionBlockLocationList) {
			if (regionBlockLocation.getPrefectureCode().equals(prefectureCode)) {
				regionBlockId = regionBlockLocation.getRegionBlockId();
			}
		}
		ShippingCharge shippingCharge = null;
		if (regionBlockId != null) {
			ShippingChargeDao dao = DIContainer.getDao(ShippingChargeDao.class);
			shippingCharge = dao.load(shopCode, deliveryTypeNo, regionBlockId);
		}
		return shippingCharge;
	}

	public Shop getShop(String shopCode) {
		ShopDao shopDao = DIContainer.getDao(ShopDao.class);
		return shopDao.load(shopCode);
	}

	public SearchResult<Shop> getShopList(ShopListSearchCondition condition) {

		SearchQuery<Shop> query = new ShopSearchQuery(condition);

		SearchResult<Shop> result = DatabaseUtil.executeSearch(query);
		return result;

	}

	public List<Shop> getShopAll() {
		Query query = new SimpleQuery(ShopManagementSimpleSql.LOAD_SHOP_LIST);
		List<Shop> shopList = DatabaseUtil.loadAsBeanList(query, Shop.class);

		return shopList;
	}

	public ServiceResult insertCommission(Commission commission) {
		ServiceResultImpl result = new ServiceResultImpl();
		CommissionDao dao = DIContainer.getDao(CommissionDao.class);

		// 重複チェック
		Commission checkCommission = dao.load(commission.getShopCode(),
				commission.getPaymentMethodNo(), commission
						.getPaymentPriceThreshold());

		if (checkCommission != null) {
			result
					.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
			return result;
		}

		setUserStatus(commission);

		ValidationSummary resultList = BeanValidator.validate(commission);
		if (resultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
		} else {
			dao.insert(commission, getLoginInfo());
		}

		return result;
	}

	public ServiceResult insertDeliveryAppointedTime(
			DeliveryAppointedTime deliveryTimeSlot) {
		ServiceResultImpl result = new ServiceResultImpl();

		// 重複チェック
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_DELIVERY_APPOINTED_TIME_LIST
						+ ShopManagementSimpleSql.LOAD_DELIVERY_APPOINTED_TIME_LIST_ADDITION_CODE,
				deliveryTimeSlot.getShopCode(), deliveryTimeSlot
						.getDeliveryTypeNo(), deliveryTimeSlot
						.getDeliveryAppointedTimeCode());

		List<DeliveryAppointedTime> timeList = DatabaseUtil.loadAsBeanList(q,
				DeliveryAppointedTime.class);

		if (timeList.size() > 0) {
			result
					.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
			return result;
		}

		setUserStatus(deliveryTimeSlot);

		ValidationSummary resultList = BeanValidator.validate(deliveryTimeSlot);
		if (resultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
		} else {

			DeliveryAppointedTimeDao dao = DIContainer
					.getDao(DeliveryAppointedTimeDao.class);
			dao.insert(deliveryTimeSlot, getLoginInfo());
		}

		return result;
	}

	public ServiceResult insertDeliveryType(DeliveryType deliveryType,
			List<ShippingCharge> chargeList) {
		Logger logger = Logger.getLogger(this.getClass());
		ServiceResultImpl serviceResult = new ServiceResultImpl();

		// 重複チェック
		DeliveryType type = getDeliveryType(deliveryType.getShopCode(),
				deliveryType.getDeliveryTypeNo());
		if (type != null) {
			serviceResult
					.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
			return serviceResult;
		}

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());

			setUserStatus(deliveryType);
			ValidationSummary resultList = BeanValidator.validate(deliveryType);
			if (resultList.hasError()) {
				serviceResult
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				return serviceResult;
			} else {
				manager.insert(deliveryType);
			}

			for (ShippingCharge shippingCharge : chargeList) {
				setUserStatus(shippingCharge);
				resultList = BeanValidator.validate(shippingCharge);
				if (resultList.hasError()) {
					serviceResult
							.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
					for (String rs : resultList.getErrorMessages()) {
						logger.debug(rs);
					}
					manager.rollback();
					return serviceResult;
				} else {
					manager.insert(shippingCharge);
				}
			}

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

	public ServiceResult updateMailTemplateDetail(MailTemplateSuite template) {
		Logger logger = Logger.getLogger(this.getClass());
		ServiceResultImpl result = new ServiceResultImpl();

		MailTemplateHeaderDao headerDao = DIContainer
				.getDao(MailTemplateHeaderDao.class);
		MailTemplateHeader header = template.getMailTemplateHeader();
		MailTemplateHeader orgHeader = headerDao.load(header.getShopCode(),
				header.getMailType(), header.getMailTemplateNo());
		if (orgHeader == null) {
			result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return result;
		} else {
			header.setCreatedUser(orgHeader.getCreatedUser());
			header.setCreatedDatetime(orgHeader.getCreatedDatetime());
			header.setOrmRowid(orgHeader.getOrmRowid());
			setUserStatus(header);
		}
		ValidationSummary valid = BeanValidator.validate(header);
		if (valid.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			for (String rs : valid.getErrorMessages()) {
				logger.debug(rs);
			}
		}

		MailTemplateDetailDao detailDao = DIContainer
				.getDao(MailTemplateDetailDao.class);
		List<MailTemplateDetail> detailList = template.getMailTemplateDetail();
		for (MailTemplateDetail detail : detailList) {
			MailTemplateDetail org = detailDao.load(detail.getShopCode(),
					detail.getMailType(), detail.getMailTemplateNo(), detail
							.getMailTemplateBranchNo());
			if (org == null) {
				result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
				return result;
			} else {
				detail.setCreatedUser(org.getCreatedUser());
				detail.setCreatedDatetime(org.getCreatedDatetime());
				detail.setOrmRowid(org.getOrmRowid());
				setUserStatus(detail);
			}
			valid = BeanValidator.validate(detail);
			if (valid.hasError()) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				for (String rs : valid.getErrorMessages()) {
					logger.debug(rs);
				}
			}
			// 本文の項目タグ必須チェック
			String text = detail.getMailText();
			MailType mailType = MailType.fromValue(detail.getMailType());
			MailComposition composition = MailComposition.fromMailTypeAndTag(
					mailType, detail.getSubstitutionTag());
			boolean hasError = false;
			for (MailTemplateTag tag : composition.getUseableTagList()) {
				if (tag.isRequired() && !text.contains(tag.getValue())) {
					// 必須かつ本文にタグがない場合はエラーとする
					hasError = true;
					/*
					 * logger.debug("【テンプレート】" + mailType.getName() + "【構造タグ】" +
					 * detail.getMailCompositionName() + ":" + "項目タグ" +
					 * tag.getValue() + "は必須です");
					 */
					logger.debug(MessageFormat.format(Messages
							.log("service.impl.ShopManagementServiceImpl.0"),
							mailType.getName(),
							detail.getMailCompositionName(), tag.getValue()));
				}
			}
			for (MailComposition c : MailComposition.fromMailType(mailType)) {
				if (c.getParentCompostion().getSubstitutionTag().equals(
						detail.getSubstitutionTag())
						&& !text.contains(c.getSubstitutionTag())) {
					// 子構造タグがない場合はエラーとする
					hasError = true;
					/*
					 * logger.debug("【テンプレート】" + mailType.getName() + "【構造タグ】" +
					 * detail.getMailCompositionName() + ":" + "構造タグ" +
					 * c.getSubstitutionTag() + "は必須です");
					 */
					logger.debug(MessageFormat.format(Messages
							.log("service.impl.ShopManagementServiceImpl.1"),
							mailType.getName(),
							detail.getMailCompositionName(), c
									.getSubstitutionTag()));
				}
			}
			if (hasError) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			}
		}
		if (result.hasError()) {
			return result;
		}

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());

			manager.update(header);

			for (MailTemplateDetail detail : detailList) {
				manager.update(detail);
			}

			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	// Add by V10-CH start
	public ServiceResult updateSmsTemplateDetail(SmsTemplateSuite template) {
		ServiceResultImpl result = new ServiceResultImpl();

		SmsTemplateDetailDao detailDao = DIContainer
				.getDao(SmsTemplateDetailDao.class);
		List<SmsTemplateDetail> detailList = template.getSmsTemplateDetail();
		for (SmsTemplateDetail detail : detailList) {
			SmsTemplateDetail org = detailDao.load(detail.getShopCode(), detail
					.getSmsType(), detail.getSmsTemplateNo());
			if (org == null) {
				result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
				return result;
			} else {
				detail.setCreatedUser(org.getCreatedUser());
				detail.setCreatedDatetime(org.getCreatedDatetime());
				detail.setOrmRowid(org.getOrmRowid());
				setUserStatus(detail);
			}
			// 本文の項目タグ必須チェック
			boolean hasError = false;
			if (hasError) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			}
		}
		if (result.hasError()) {
			return result;
		}

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());

			for (SmsTemplateDetail detail : detailList) {
				manager.update(detail);
			}

			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	// Add by V10-CH end

	public ServiceResult insertInformationMail(String informationName) {
		Logger logger = Logger.getLogger(this.getClass());

		ServiceResultImpl result = new ServiceResultImpl();

		// テンプレート番号の最大値を取得
		Long templateNo = 0L;
		List<CodeAttribute> informationList = getInformationMailTypeList();
		if (informationList.size() > 0) {
			for (CodeAttribute c : informationList) {
				Long tmpNo = Long.parseLong(c.getValue());
				if (tmpNo > templateNo) {
					templateNo = tmpNo;
				}
			}
			templateNo++;
		} else {
			templateNo = 0L;
		}

		TransactionManager manager = DIContainer.getTransactionManager();

		WebshopConfig config = DIContainer.getWebshopConfig();

		try {
			manager.begin(getLoginInfo());

			MailTemplateHeader header = new MailTemplateHeader();
			header.setShopCode(config.getSiteShopCode());

			header.setMailType(MailType.INFORMATION.getValue());
			header.setMailTemplateNo(templateNo);
			header.setMailSubject("");
			header.setMailComposition(MailComposition.INFORMATION_MAIL_MAIN
					.getSubstitutionTag()
					+ MailComposition.INFORMATION_MAIL_SIGN
							.getSubstitutionTag());
			header.setDisplayOrder(0L);
			header.setMailContentType(Long.parseLong(MailContentType.TEXT
					.getValue()));
			Shop shop = getShop(config.getSiteShopCode());
			header.setFromAddress(shop.getEmail());
			setUserStatus(header);
			ValidationSummary validateHeader = BeanValidator.validate(header);
			if (validateHeader.hasError()) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				for (String rs : validateHeader.getErrorMessages()) {
					logger.debug(rs);
				}
				return result;
			} else {
				manager.insert(header);
			}

			MailTemplateDetail detail = new MailTemplateDetail();
			detail.setShopCode(config.getSiteShopCode());
			detail.setMailType(MailType.INFORMATION.getValue());
			detail.setMailTemplateNo(templateNo);
			detail.setMailTemplateBranchNo(1L);
			detail.setParentMailTemplateBranchNo(0L);
			detail.setMailTemplateDepth(1L);
			detail.setMailText("");
			detail.setMailCompositionName(informationName);
			detail.setSubstitutionTag(MailComposition.INFORMATION_MAIL_MAIN
					.getSubstitutionTag());

			setUserStatus(detail);
			ValidationSummary validateDetail = BeanValidator.validate(detail);
			if (validateDetail.hasError()) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				for (String rs : validateDetail.getErrorMessages()) {
					logger.debug(rs);
				}
				return result;
			} else {
				manager.insert(detail);
			}

			MailTemplateDetail signDetail = new MailTemplateDetail();
			signDetail.setShopCode(config.getSiteShopCode());
			signDetail.setMailType(MailType.INFORMATION.getValue());
			signDetail.setMailTemplateNo(templateNo);
			signDetail.setMailTemplateBranchNo(2L);
			signDetail.setParentMailTemplateBranchNo(0L);
			signDetail.setMailTemplateDepth(1L);
			signDetail.setMailText(DefaultMailTextCreater.getSignature());
			signDetail
					.setMailCompositionName(MailComposition.INFORMATION_MAIL_SIGN
							.getName());
			signDetail.setSubstitutionTag(MailComposition.INFORMATION_MAIL_SIGN
					.getSubstitutionTag());

			setUserStatus(signDetail);
			ValidationSummary validateSign = BeanValidator.validate(signDetail);
			if (validateSign.hasError()) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				for (String rs : validateSign.getErrorMessages()) {
					logger.debug(rs);
				}
				return result;
			} else {
				manager.insert(signDetail);
			}

			manager.commit();

		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	// Add by V10-CH start
	public ServiceResult insertInformationSms(String informationName) {
		Logger logger = Logger.getLogger(this.getClass());

		ServiceResultImpl result = new ServiceResultImpl();

		// テンプレート番号の最大値を取得
		Long templateNo = 0L;
		List<CodeAttribute> informationList = getInformationSmsTypeList();
		if (informationList.size() > 0) {
			for (CodeAttribute c : informationList) {
				Long tmpNo = Long.parseLong(c.getValue());
				if (tmpNo > templateNo) {
					templateNo = tmpNo;
				}
			}
			templateNo++;
		} else {
			templateNo = 0L;
		}

		TransactionManager manager = DIContainer.getTransactionManager();

		WebshopConfig config = DIContainer.getWebshopConfig();

		try {
			manager.begin(getLoginInfo());

			SmsTemplateDetail detail = new SmsTemplateDetail();
			detail.setShopCode(config.getSiteShopCode());
			detail.setSmsType(SmsType.INFORMATION.getValue());
			detail.setSmsTemplateNo(templateNo);
			detail.setSmsText("");

			setUserStatus(detail);
			ValidationSummary validateDetail = BeanValidator.validate(detail);
			if (validateDetail.hasError()) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				for (String rs : validateDetail.getErrorMessages()) {
					logger.debug(rs);
				}
				return result;
			} else {
				manager.insert(detail);
			}

			SmsTemplateDetail signDetail = new SmsTemplateDetail();
			signDetail.setShopCode(config.getSiteShopCode());
			signDetail.setSmsType(MailType.INFORMATION.getValue());
			signDetail.setSmsTemplateNo(templateNo);
			signDetail.setSmsText(DefaultMailTextCreater.getSignature());

			setUserStatus(signDetail);
			ValidationSummary validateSign = BeanValidator.validate(signDetail);
			if (validateSign.hasError()) {
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				for (String rs : validateSign.getErrorMessages()) {
					logger.debug(rs);
				}
				return result;
			} else {
				manager.insert(signDetail);
			}

			manager.commit();

		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	// Add by V10-CH end

	// v10-ch-pg modify start
	// public ServiceResult insertPaymentMethod(PaymentMethodSuite
	// paymentMethod,
	// Bank bank) {
	public ServiceResult insertPaymentMethod(PaymentMethodSuite paymentMethod,
			Bank bank, PostPayment postPayment) {
		// v10-ch-pg modify end
		ServiceResultImpl result = new ServiceResultImpl();
		Logger logger = Logger.getLogger(this.getClass());

		PaymentMethod insertPaymentMethod = paymentMethod.getPaymentMethod();
		Commission insertCommission = paymentMethod.getCommissionList().get(0);

		Long nextVal = DatabaseUtil
				.generateSequence(SequenceType.PAYMENT_METHOD_NO);

		// 支払方法が代引以外の場合は、手数料・税区分・税率を固定で設定する
		if (!paymentMethod.getPaymentMethod().getPaymentMethodType().equals(
				PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
			insertPaymentMethod.setPaymentCommissionTaxType(TaxType.INCLUDED
					.longValue());
			TaxUtil util = DIContainer.get("TaxUtil");
			insertPaymentMethod.setPaymentCommissionTaxRate(util.getTaxRate());
			insertCommission.setPaymentCommission(BigDecimal.ZERO);
		}

		// 支払方法設定・Validationチェック
		insertPaymentMethod.setPaymentMethodNo(nextVal);
		insertPaymentMethod.setDeleteFlg(DeleteFlg.NOT_DELETED.longValue());
		setUserStatus(insertPaymentMethod);
		ValidationSummary methodResultList = BeanValidator
				.validate(insertPaymentMethod);

		if (methodResultList.hasError()) {
			for (String rs : methodResultList.getErrorMessages()) {
				logger.debug(rs);
			}

			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			return result;
		}

		// 金融機関設定・チェック(bankがnullの場合は登録を行わない)
		if (bank != null) {
			bank.setPaymentMethodNo(nextVal);
			setUserStatus(bank);
			ValidationSummary bankResultList = BeanValidator.validate(bank);
			if (bankResultList.hasError()) {
				for (String rs : bankResultList.getErrorMessages()) {
					logger.debug(rs);
				}
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				return result;
			}
			// v10-ch-pg add start
		} else if (postPayment != null) {
			postPayment.setPaymentMethodNo(nextVal);
			setUserStatus(postPayment);
			ValidationSummary postResultList = BeanValidator
					.validate(postPayment);
			if (postResultList.hasError()) {
				for (String rs : postResultList.getErrorMessages()) {
					logger.debug(rs);
				}
				result
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				return result;
			}
		}
		// v10-ch-pg add end

		// 手数料設定・チェック
		insertCommission.setPaymentMethodNo(nextVal);
		setUserStatus(insertCommission);
		ValidationSummary commissionResultList = BeanValidator
				.validate(insertCommission);
		if (commissionResultList.hasError()) {
			for (String rs : commissionResultList.getErrorMessages()) {
				logger.debug(rs);
			}
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			return result;
		}

		TransactionManager manager = DIContainer.getTransactionManager();

		try {
			manager.begin(getLoginInfo());

			// 支払方法登録
			manager.insert(insertPaymentMethod);

			// 金融機関登録(bankがnullの場合は登録を行わない)
			if (bank != null) {

				manager.insert(bank);
				// v10-ch-pg add start
			} else if (postPayment != null) {
				manager.insert(postPayment);
			}
			// v10-ch-pg add end

			// 手数料登録
			manager.insert(insertCommission);

			manager.commit();

		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	public ServiceResult insertRegionBlockList(RegionBlock regionBlock) {
		ServiceResultImpl result = new ServiceResultImpl();

		Long regionBlockId = DatabaseUtil
				.generateSequence(SequenceType.REGION_BLOCK_ID);

		regionBlock.setRegionBlockId(regionBlockId);
		setUserStatus(regionBlock);

		ValidationSummary resultList = BeanValidator.validate(regionBlock);

		if (resultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
			return result;
		}

		WebshopConfig wsConfig = DIContainer.getWebshopConfig();
		TransactionManager manager = DIContainer.getTransactionManager();

		try {
			manager.begin(getLoginInfo());
			manager.insert(regionBlock);

			for (DeliveryType delivery : getDeliveryTypeList(regionBlock
					.getShopCode())) {
				ShippingCharge shippingCharge = new ShippingCharge();
				shippingCharge.setShopCode(regionBlock.getShopCode());
				shippingCharge.setRegionBlockId(regionBlock.getRegionBlockId());
				// デフォルト送料・デフォルトリードタイムをWebShopConfigから取得
				shippingCharge.setShippingCharge(wsConfig
						.getDefaultShippingCharge());
				shippingCharge.setLeadTime(Long.valueOf(wsConfig
						.getMinimalLeadTime()));
				shippingCharge.setDeliveryTypeNo(delivery.getDeliveryTypeNo());
				setUserStatus(shippingCharge);
				manager.insert(shippingCharge);
			}
			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	/**
	 * ショップ情報を登録する
	 */
	public ServiceResult insertShop(Shop shop) {

		ServiceResultImpl serviceResult = new ServiceResultImpl();

		if (shop.getCloseDatetime() == null) {
			shop.setCloseDatetime(DateUtil.truncateDate(DateUtil.getMax()));
		}

		setUserStatus(shop);

		ValidationSummary resultList = BeanValidator.validate(shop);

		// Validationチェック
		if (resultList.hasError()) {
			serviceResult
					.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
			return serviceResult;
		}

		ShopDao shopDao = DIContainer.getDao(ShopDao.class);

		// 該当データの存在確認
		Shop orgShop = shopDao.load(shop.getShopCode());
		if (orgShop != null) {
			serviceResult
					.addServiceError(ShopManagementServiceErrorContent.SHOP_REGISTERED_ERROR);
			return serviceResult;
		}

		TransactionManager manager = DIContainer.getTransactionManager();

		try {
			manager.begin(getLoginInfo());

			// ショップマスタ登録
			setUserStatus(shop);
			manager.insert(shop);

			// メールテンプレートヘッダーと明細を登録
			List<MailTemplateSuite> mailTemplateSuiteList = createDefaultMailtemplateData(shop);
			for (MailTemplateSuite mailTemplateSuite : mailTemplateSuiteList) {

				// Validationチェック
				ValidationSummary mailHeaderResult = BeanValidator
						.validate(mailTemplateSuite.getMailTemplateHeader());
				if (mailHeaderResult.hasError()) {
					serviceResult
							.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
					Logger logger = Logger.getLogger(this.getClass());
					for (String rs : mailHeaderResult.getErrorMessages()) {
						logger.debug(rs);
					}
					return serviceResult;
				}
				setUserStatus(mailTemplateSuite.getMailTemplateHeader());
				manager.insert(mailTemplateSuite.getMailTemplateHeader());
				for (MailTemplateDetail mailTemplateDetail : mailTemplateSuite
						.getMailTemplateDetail()) {
					// Validationチェック
					ValidationSummary mailDetailResult = BeanValidator
							.validate(mailTemplateDetail);
					if (mailDetailResult.hasError()) {
						serviceResult
								.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
						Logger logger = Logger.getLogger(this.getClass());
						for (String rs : mailDetailResult.getErrorMessages()) {
							logger.debug(rs);
						}
						return serviceResult;
					}
					setUserStatus(mailTemplateDetail);
					manager.insert(mailTemplateDetail);
				}
			}

			// デフォルト(全額ポイントと支払不要と代引)の支払方法を支払方法マスタに登録する(ショップ個別決済時のみ)
			if (DIContainer.getWebshopConfig().getOperatingMode().equals(
					OperatingMode.SHOP)) {
				PaymentMethodSuite noPayment = createDefaultNoPayment(shop);
				manager.insert(noPayment.getPaymentMethod());
				manager.insert(noPayment.getCommissionList().get(0));

				PaymentMethodSuite pointInFullPayment = createDefaultPointInFullPayment(shop);
				manager.insert(pointInFullPayment.getPaymentMethod());
				manager.insert(pointInFullPayment.getCommissionList().get(0));

				PaymentMethodSuite cashOnDelivery = createDefaultCashOnDelivery(shop);
				manager.insert(cashOnDelivery.getPaymentMethod());
				manager.insert(cashOnDelivery.getCommissionList().get(0));
			}

			// 地域ブロック
			RegionBlock regionBlock = createRegionBlock(shop);
			manager.insert(regionBlock);

			// 地域ブロック配置
			List<RegionBlockLocation> locationList = createRegionBlockLocation(
					shop.getShopCode(), regionBlock.getRegionBlockId());
			for (RegionBlockLocation rb : locationList) {
				manager.insert(rb);
			}

			// デフォルト配送種別登録
			DeliveryType deliveryType = getDefaultDeliveryType(shop);
			manager.insert(deliveryType);
			ShippingCharge shippingCharge = getDefaultShippingCharge(shop,
					deliveryType, regionBlock);
			manager.insert(shippingCharge);

			// 10.1.2 10094 追加 ここから
			// 商品詳細レイアウト
			List<CommodityLayout> layoutList = CommonLogic
					.createCommodityLayout(shop.getShopCode());
			manager
					.executeUpdate(
							CommodityLayoutQuery.GET_DELETE_COMMODITY_LAYOUT_SHOP_QUERY,
							shop.getShopCode());
			for (CommodityLayout cl : layoutList) {
				setUserStatus(cl);
				manager.insert(cl);
			}
			// 10.1.2 10094 追加 ここまで

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

	private ShippingCharge getDefaultShippingCharge(Shop shop,
			DeliveryType deliveryType, RegionBlock regionBlock) {
		ShippingCharge shippingCharge = new ShippingCharge();

		shippingCharge.setShopCode(shop.getShopCode());
		shippingCharge.setDeliveryTypeNo(deliveryType.getDeliveryTypeNo());
		shippingCharge.setRegionBlockId(regionBlock.getRegionBlockId());
		WebshopConfig config = DIContainer.getWebshopConfig();
		shippingCharge.setShippingCharge(config.getDefaultShippingCharge());
		shippingCharge.setLeadTime(Long.valueOf(config.getMinimalLeadTime()));
		setUserStatus(shippingCharge);

		return shippingCharge;
	}

	private DeliveryType getDefaultDeliveryType(Shop shop) {
		DeliveryType deliveryType = new DeliveryType();

		deliveryType.setShopCode(shop.getShopCode());
		deliveryType.setDeliveryTypeNo(0L);
		deliveryType.setDeliveryTypeName(Messages
				.getString("service.impl.ShopManagementServiceImpl.2"));
		deliveryType
				.setDeliverySpecificationType(DeliverySpecificationType.NONE
						.longValue());
		deliveryType.setDisplayFlg(DisplayFlg.VISIBLE.longValue());
		deliveryType.setShippingChargeTaxType(TaxType.NO_TAX.longValue());
		deliveryType.setShippingChargeFreeFlg(ShippingChargeFreeFlg.CHARGE_FREE
				.longValue());
		deliveryType.setShippingChargeFreeThreshold(BigDecimal.ZERO);
		deliveryType.setShippingChargeFlg(ShippingChargeFlg.EACH.longValue());
		deliveryType.setShippingChargeThreshold(BigDecimal.ZERO);
		setUserStatus(deliveryType);

		return deliveryType;

	}

	private PaymentMethodSuite createDefaultCashOnDelivery(Shop shop) {
		PaymentMethodSuite paymentMethodSuite = new PaymentMethodSuite();
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setShopCode(shop.getShopCode());
		Long paymentMethodNo = DatabaseUtil
				.generateSequence(SequenceType.PAYMENT_METHOD_NO);

		paymentMethod.setPaymentMethodNo(paymentMethodNo);
		paymentMethod.setPaymentMethodName(PaymentMethodType.CASH_ON_DELIVERY
				.getName());
		paymentMethod.setPaymentMethodDisplayType(PaymentMethodDisplayType.ALL
				.longValue());
		paymentMethod.setPaymentMethodType(PaymentMethodType.CASH_ON_DELIVERY
				.getValue());
		paymentMethod.setAdvanceLaterFlg(AdvanceLaterFlg.LATER.longValue());
		paymentMethod.setPaymentCommissionTaxType(TaxType.NO_TAX.longValue());
		paymentMethod.setDeleteFlg(DeleteFlg.NOT_DELETED.longValue());

		setUserStatus(paymentMethod);

		paymentMethodSuite.setPaymentMethod(paymentMethod);

		Commission commission = new Commission();
		commission.setShopCode(shop.getShopCode());
		commission.setPaymentMethodNo(paymentMethodNo);
		commission.setPaymentCommission(BigDecimal.ZERO);

		// String length = WebUtil.buildMaxlength(Commission.class,
		// "paymentPriceThreshold", null);

		// String paymentPriceThreshold = "";
		// for (Long i = 0L; i < NumUtil.toLong(length); i++) {
		// paymentPriceThreshold += "9";
		// }
		// commission.setPaymentPriceThreshold(NumUtil.parse(paymentPriceThreshold));
		Precision precision = BeanUtil.getAnnotation(Commission.class,
				"paymentPriceThreshold", Precision.class);
		BigDecimal paymentPriceThreshold = NumUtil.getActualMaximum(precision
				.precision(), precision.scale());
		commission.setPaymentPriceThreshold(paymentPriceThreshold);

		setUserStatus(commission);

		List<Commission> commissionList = new ArrayList<Commission>();
		commissionList.add(commission);

		paymentMethodSuite.setCommissionList(commissionList);

		return paymentMethodSuite;
	}

	private PaymentMethodSuite createDefaultNoPayment(Shop shop) {
		PaymentMethodSuite paymentMethodSuite = new PaymentMethodSuite();
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setShopCode(shop.getShopCode());
		Long paymentMethodNo = DatabaseUtil
				.generateSequence(SequenceType.PAYMENT_METHOD_NO);

		paymentMethod.setPaymentMethodNo(paymentMethodNo);
		paymentMethod.setPaymentMethodName(PaymentMethodType.NO_PAYMENT
				.getName());
		paymentMethod.setPaymentMethodDisplayType(PaymentMethodDisplayType.ALL
				.longValue());
		paymentMethod.setPaymentMethodType(PaymentMethodType.NO_PAYMENT
				.getValue());
		paymentMethod.setAdvanceLaterFlg(AdvanceLaterFlg.ADVANCE.longValue());
		paymentMethod.setPaymentCommissionTaxType(TaxType.NO_TAX.longValue());
		paymentMethod.setDeleteFlg(DeleteFlg.NOT_DELETED.longValue());

		setUserStatus(paymentMethod);

		paymentMethodSuite.setPaymentMethod(paymentMethod);

		Commission commission = new Commission();
		commission.setShopCode(shop.getShopCode());
		commission.setPaymentMethodNo(paymentMethodNo);
		commission.setPaymentCommission(BigDecimal.ZERO);

		// String length = WebUtil.buildMaxlength(Commission.class,
		// "paymentPriceThreshold", null);
		//
		// String paymentPriceThreshold = "";
		// for (Long i = 0L; i < NumUtil.toLong(length); i++) {
		// paymentPriceThreshold += "9";
		// }
		// commission.setPaymentPriceThreshold(NumUtil.parse(paymentPriceThreshold));
		Precision precision = BeanUtil.getAnnotation(Commission.class,
				"paymentPriceThreshold", Precision.class);
		BigDecimal paymentPriceThreshold = NumUtil.getActualMaximum(precision
				.precision(), precision.scale());
		commission.setPaymentPriceThreshold(paymentPriceThreshold);

		setUserStatus(commission);

		List<Commission> commissionList = new ArrayList<Commission>();
		commissionList.add(commission);

		paymentMethodSuite.setCommissionList(commissionList);

		return paymentMethodSuite;
	}

	private PaymentMethodSuite createDefaultPointInFullPayment(Shop shop) {
		PaymentMethodSuite paymentMethodSuite = new PaymentMethodSuite();
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setShopCode(shop.getShopCode());
		Long paymentMethodNo = DatabaseUtil
				.generateSequence(SequenceType.PAYMENT_METHOD_NO);

		paymentMethod.setPaymentMethodNo(paymentMethodNo);
		paymentMethod.setPaymentMethodName(PaymentMethodType.POINT_IN_FULL
				.getName());
		paymentMethod.setPaymentMethodDisplayType(PaymentMethodDisplayType.ALL
				.longValue());
		paymentMethod.setPaymentMethodType(PaymentMethodType.POINT_IN_FULL
				.getValue());
		paymentMethod.setAdvanceLaterFlg(AdvanceLaterFlg.ADVANCE.longValue());
		paymentMethod.setPaymentCommissionTaxType(TaxType.NO_TAX.longValue());
		paymentMethod.setDeleteFlg(DeleteFlg.NOT_DELETED.longValue());

		setUserStatus(paymentMethod);

		paymentMethodSuite.setPaymentMethod(paymentMethod);

		Commission commission = new Commission();
		commission.setShopCode(shop.getShopCode());
		commission.setPaymentMethodNo(paymentMethodNo);
		commission.setPaymentCommission(BigDecimal.ZERO);

		// String length = WebUtil.buildMaxlength(Commission.class,
		// "paymentPriceThreshold", null);
		//
		// String paymentPriceThreshold = "";
		// for (Long i = 0L; i < NumUtil.toLong(length); i++) {
		// paymentPriceThreshold += "9";
		// }
		// commission.setPaymentPriceThreshold(NumUtil.parse(paymentPriceThreshold));
		Precision precision = BeanUtil.getAnnotation(Commission.class,
				"paymentPriceThreshold", Precision.class);
		BigDecimal paymentPriceThreshold = NumUtil.getActualMaximum(precision
				.precision(), precision.scale());
		commission.setPaymentPriceThreshold(paymentPriceThreshold);

		setUserStatus(commission);

		List<Commission> commissionList = new ArrayList<Commission>();
		commissionList.add(commission);

		paymentMethodSuite.setCommissionList(commissionList);

		return paymentMethodSuite;
	}

	private RegionBlock createRegionBlock(Shop shop) {
		RegionBlock regionBlock = new RegionBlock();
		regionBlock.setShopCode(shop.getShopCode());
		regionBlock.setRegionBlockId(DatabaseUtil
				.generateSequence(SequenceType.REGION_BLOCK_ID));
		regionBlock.setRegionBlockName(Messages
				.getString("service.impl.ShopManagementServiceImpl.3"));
		setUserStatus(regionBlock);

		return regionBlock;
	}

	private List<RegionBlockLocation> createRegionBlockLocation(
			String shopCode, Long regionBlockId) {
		List<RegionBlockLocation> regionBlockLocationList = new ArrayList<RegionBlockLocation>();

		for (PrefectureCode pf : PrefectureCode.values()) {
			RegionBlockLocation regionBlockLocation = new RegionBlockLocation();
			regionBlockLocation.setPrefectureCode(pf.getValue());
			regionBlockLocation.setRegionBlockId(regionBlockId);
			regionBlockLocation.setShopCode(shopCode);
			setUserStatus(regionBlockLocation);
			regionBlockLocationList.add(regionBlockLocation);
		}

		return regionBlockLocationList;
	}

	/**
	 * ショップ初期登録時に使用する、メールテンプレートの一覧を取得する
	 * 
	 * @param shop
	 * @return List<MailTemplateSuite>
	 */
	public List<MailTemplateSuite> createDefaultMailtemplateData(Shop shop) {
		List<MailTemplateSuite> mailTemplateSuiteList = new ArrayList<MailTemplateSuite>();

		WebshopConfig config = DIContainer.getWebshopConfig();

		// メールテンプレート登録開始
		OperatingMode operatingMode = config.getOperatingMode();
		boolean siteUser = shop.getShopCode().equals(config.getSiteShopCode());

		// ポイントルール使用可否設定
		PointRule point = CommonLogic.getPointRule(getLoginInfo());
		boolean usablePoint = (PointFunctionEnabledFlg.ENABLED.longValue()
				.equals(point.getPointFunctionEnabledFlg()));

		// 使用可能なメールタイプ一覧を取得
		List<MailType> mailTypeList = MailTemplateUtil.getUsableMailTypeList(
				operatingMode, siteUser, false);

		// メールタイプごとにMailTemplateSuiteを作成し、リストを返す
		for (MailType mailType : mailTypeList) {
			MailTemplateHeader header = new MailTemplateHeader();
			setUserStatus(header);
			String headerText = "";

			// ヘッダー情報生成
			header.setShopCode(shop.getShopCode());
			header.setMailType(mailType.getValue());
			header.setMailTemplateNo(0L);
			header.setMailSubject(DefaultMailTextCreater.getSubject(mailType,
					usablePoint));
			header.setDisplayOrder(0L);
			header.setMailSenderName(shop.getShopName());
			header.setFromAddress(shop.getEmail());
			header.setBccAddress("");
			header.setMailContentType(MailContentType.TEXT.longValue());

			// 明細情報生成
			List<MailTemplateDetail> mailTemplateDetailList = new ArrayList<MailTemplateDetail>();
			List<MailComposition> mailCompositionList = MailCompositionLocator
					.fromMailType(mailType);

			Long branchNo = 1L;

			// ParentBranchNoを設定する為の、親構造とBranchNoのMapを作成する
			HashMap<MailComposition, Long> mailCompositionBranchNoMap = new HashMap<MailComposition, Long>();

			// メールタイプに関連付いている構造タグごとにメールテンプレート明細情報を生成する
			for (MailComposition composition : mailCompositionList) {
				MailTemplateDetail detail = new MailTemplateDetail();
				setUserStatus(detail);

				mailCompositionBranchNoMap.put(
						MailComposition.COMMON_ROOT_COMPOSITION, 0L);
				mailCompositionBranchNoMap.put(composition, branchNo);

				detail.setShopCode(shop.getShopCode());
				detail.setMailType(mailType.getValue());
				detail.setMailTemplateNo(0L);
				detail.setMailTemplateBranchNo(branchNo);

				if (branchNo.equals(1L)) {
					detail.setParentMailTemplateBranchNo(0L);
				} else {
					detail
							.setParentMailTemplateBranchNo(mailCompositionBranchNoMap
									.get(composition.getParentCompostion()));
				}

				detail
						.setMailTemplateDepth(getMailTemplateDepthNo(composition));
				detail.setMailCompositionName(composition.getName());
				detail.setSubstitutionTag(composition.getSubstitutionTag());
				detail.setMailText(DefaultMailTextCreater.getText(composition,
						usablePoint));

				if (composition.getParentCompostion().getName().equals("")) {
					headerText += detail.getSubstitutionTag() + "\n";
				}

				branchNo += 1;
				mailTemplateDetailList.add(detail);
			}
			header.setMailComposition(headerText);

			MailTemplateSuite mailTemplateSuite = new MailTemplateSuite();
			mailTemplateSuite.setMailTemplateHeader(header);
			mailTemplateSuite.setMailTemplateDetail(mailTemplateDetailList);
			mailTemplateSuiteList.add(mailTemplateSuite);
		}

		return mailTemplateSuiteList;
	}

	/**
	 * 引数のMailComposition情報を元に、メールの階層の値を返す。<br>
	 * トップレベルは1
	 * 
	 * @param composition
	 * @return Long depthNo
	 */
	private Long getMailTemplateDepthNo(MailComposition composition) {
		Long depthNo = 1L;

		// Enumの性質上無限ループはありえない
		while (composition.getParentCompostion() != MailComposition.COMMON_ROOT_COMPOSITION) {
			depthNo += 1;
			composition = composition.getParentCompostion();
		}
		return depthNo;
	}

	public ServiceResult insertBank(Bank bank) {
		ServiceResultImpl result = new ServiceResultImpl();

		BankDao dao = DIContainer.getDao(BankDao.class);

		Bank orgBank = dao.load(bank.getShopCode(), bank.getPaymentMethodNo(),
				bank.getBankCode(), bank.getBankBranchCode(), bank
						.getAccountNo());

		if (orgBank != null) {
			result
					.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
			return result;
		}

		setUserStatus(bank);
		ValidationSummary resultValidate = BeanValidator.validate(bank);
		if (resultValidate.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			return result;
		} else {
			dao.insert(bank, getLoginInfo());
		}

		return result;
	}

	// 05-20 Add start
	public ServiceResult insertPost(PostPayment post) {
		ServiceResultImpl result = new ServiceResultImpl();

		PostPaymentDao dao = DIContainer.getDao(PostPaymentDao.class);

		PostPayment orgpost = dao.load(post.getShopCode(), post
				.getPaymentMethodNo(), post.getPostAccountNo());

		if (orgpost != null) {
			result
					.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
			return result;
		}

		setUserStatus(post);
		ValidationSummary resultValidate = BeanValidator.validate(post);
		if (resultValidate.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			return result;
		} else {
			dao.insert(post, getLoginInfo());
		}

		return result;
	}

	// 05-20 Add end
	public ServiceResult updateBank(Bank bank) {
		ServiceResultImpl result = new ServiceResultImpl();

		BankDao dao = DIContainer.getDao(BankDao.class);

		Bank orgBank = dao.load(bank.getShopCode(), bank.getPaymentMethodNo(),
				bank.getBankCode(), bank.getBankBranchCode(), bank
						.getAccountNo());

		if (orgBank == null) {
			result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return result;
		}

		bank.setOrmRowid(orgBank.getOrmRowid());
		bank.setCreatedDatetime(orgBank.getCreatedDatetime());
		bank.setCreatedUser(orgBank.getCreatedUser());
		bank.setUpdatedUser(orgBank.getUpdatedUser());
		ValidationSummary resultValidate = BeanValidator.validate(bank);
		if (resultValidate.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			return result;
		} else {
			dao.update(bank, getLoginInfo());
		}

		return result;
	}

	// 05-20 Add start
	public ServiceResult updatePost(PostPayment post) {
		ServiceResultImpl result = new ServiceResultImpl();

		PostPaymentDao dao = DIContainer.getDao(PostPaymentDao.class);

		PostPayment orgPost = dao.load(post.getShopCode(), post
				.getPaymentMethodNo(), post.getPostAccountNo());
		// load()参数 String shopCode, Long paymentMethodNo, String postAccountNo

		if (orgPost == null) {
			result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return result;
		}

		post.setOrmRowid(orgPost.getOrmRowid());
		post.setCreatedDatetime(orgPost.getCreatedDatetime());
		post.setCreatedUser(orgPost.getCreatedUser());
		post.setUpdatedUser(orgPost.getUpdatedUser());
		ValidationSummary resultValidate = BeanValidator.validate(post);
		if (resultValidate.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			return result;
		} else {
			dao.update(post, getLoginInfo());
		}

		return result;
	}

	// 05-20 Add end
	public ServiceResult registerRegionBlockLocationList(
			List<RegionBlockLocation> regionBlockLocationList, String shopCode) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		TransactionManager manager = DIContainer.getTransactionManager();

		try {
			manager.begin(getLoginInfo());

			List<RegionBlockLocation> orgRegionBlockLocationList = getRegionBlockLocationList(shopCode);
			if (orgRegionBlockLocationList.size() == regionBlockLocationList
					.size()) {
				Map<String, RegionBlockLocation> map = new HashMap<String, RegionBlockLocation>();
				for (RegionBlockLocation regionBlockLocation : regionBlockLocationList) {
					map.put(regionBlockLocation.getPrefectureCode(),
							regionBlockLocation);
				}
				for (RegionBlockLocation orgRegion : orgRegionBlockLocationList) {
					RegionBlockLocation updRegion = map.get(orgRegion
							.getPrefectureCode());
					if (updRegion == null) {
						manager.rollback();
						serviceResult
								.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
						return serviceResult;
					} else {
						updRegion.setCreatedUser(orgRegion.getCreatedUser());
						updRegion.setCreatedDatetime(orgRegion
								.getCreatedDatetime());
						updRegion.setOrmRowid(orgRegion.getOrmRowid());
						setUserStatus(updRegion);

						ValidationSummary result = BeanValidator
								.validate(updRegion);
						if (result.hasError()) {
							manager.rollback();
							serviceResult
									.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
							return serviceResult;
						} else {
							manager.update(updRegion);
						}
					}
				}
			} else {
				manager
						.executeUpdate(
								ShopManagementSimpleSql.DELETE_ALL_REGION_BLOCK_LOCATION,
								shopCode);
				for (RegionBlockLocation p : regionBlockLocationList) {
					setUserStatus(p);
					ValidationSummary result = BeanValidator.validate(p);
					if (result.hasError()) {
						manager.rollback();
						serviceResult
								.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
						return serviceResult;
					} else {
						manager.insert(p);
					}
				}
			}
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

	public ServiceResult updateCommission(Commission commission) {
		ServiceResultImpl result = new ServiceResultImpl();
		CommissionDao dao = DIContainer.getDao(CommissionDao.class);
		Commission orgCommission = dao.load(commission.getShopCode(),
				commission.getPaymentMethodNo(), commission
						.getPaymentPriceThreshold());

		if (orgCommission == null) {
			result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return result;
		}

		commission.setOrmRowid(orgCommission.getOrmRowid());
		commission.setUpdatedUser(orgCommission.getUpdatedUser());
		commission.setCreatedDatetime(orgCommission.getCreatedDatetime());
		commission.setCreatedUser(orgCommission.getCreatedUser());

		ValidationSummary resultList = BeanValidator.validate(commission);

		if (resultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
		} else {
			dao.update(commission, getLoginInfo());
		}

		return result;
	}

	public ServiceResult updateDeliveryAppointedTime(
			DeliveryAppointedTime deliveryTimeSlot) {
		ServiceResultImpl result = new ServiceResultImpl();
		DeliveryAppointedTimeDao dao = DIContainer
				.getDao(DeliveryAppointedTimeDao.class);

		DeliveryAppointedTime orgTime = dao.load(
				deliveryTimeSlot.getShopCode(), deliveryTimeSlot
						.getDeliveryTypeNo(), deliveryTimeSlot
						.getDeliveryAppointedTimeCode());

		if (orgTime == null) {
			result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return result;
		}

		deliveryTimeSlot.setUpdatedUser(orgTime.getUpdatedUser());
		deliveryTimeSlot.setCreatedUser(orgTime.getCreatedUser());
		deliveryTimeSlot.setCreatedDatetime(orgTime.getCreatedDatetime());
		deliveryTimeSlot.setOrmRowid(orgTime.getOrmRowid());
		setUserStatus(deliveryTimeSlot);

		ValidationSummary resultList = BeanValidator.validate(deliveryTimeSlot);
		if (resultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
		} else {
			dao.update(deliveryTimeSlot, getLoginInfo());
		}

		return result;
	}

	public ServiceResult updateDeliveryType(DeliveryType deliveryType,
			List<ShippingCharge> chargeList) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();

		// 削除済みチェック
		DeliveryType type = getDeliveryType(deliveryType.getShopCode(),
				deliveryType.getDeliveryTypeNo());
		if (type == null) {
			serviceResult
					.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return serviceResult;
		}

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());

			deliveryType.setCreatedUser(type.getCreatedUser());
			deliveryType.setCreatedDatetime(type.getCreatedDatetime());
			deliveryType.setOrmRowid(type.getOrmRowid());

			setUserStatus(deliveryType);
			ValidationSummary resultList = BeanValidator.validate(deliveryType);
			if (resultList.hasError()) {
				serviceResult
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				return serviceResult;
			} else {
				manager.update(deliveryType);
			}

			// 送料ルールは数が変わる可能性が高い為DeleteInsertとする
			manager.executeUpdate(
					ShopManagementSimpleSql.DELETE_SHIPPING_CHARGE,
					deliveryType.getShopCode(), deliveryType
							.getDeliveryTypeNo());

			for (ShippingCharge shippingCharge : chargeList) {
				setUserStatus(shippingCharge);
				resultList = BeanValidator.validate(shippingCharge);
				if (resultList.hasError()) {
					serviceResult
							.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
					manager.rollback();
					return serviceResult;
				} else {
					manager.insert(shippingCharge);
				}
			}

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

	public ServiceResult updateHoliday(String shopCode, String year,
			String month, List<String> date) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		TransactionManager manager = DIContainer.getTransactionManager();

		Date deleteFrom = DateUtil.fromString(year + "/" + month + "/01");
		Date deleteTo = DateUtil.fromString(year + "/" + month + "/"
				+ DateUtil.getEndDay(deleteFrom));

		try {
			manager.begin(getLoginInfo());
			// shopCodeに関連付いているデータの削除
			manager.executeUpdate(ShopManagementSimpleSql.DELETE_HOLIDAY,
					shopCode, DateUtil.toDateString(deleteFrom), DateUtil
							.toDateString(deleteTo));

			for (String d : date) {
				Long holidyId = DatabaseUtil
						.generateSequence(SequenceType.HOLIDAY_ID);

				Holiday holiday = new Holiday();
				holiday.setHolidayId(holidyId);
				holiday.setShopCode(shopCode);
				holiday.setHoliday(DateUtil.fromString(year + "/" + month + "/"
						+ d));
				setUserStatus(holiday);

				// Validationチェック
				ValidationSummary resultList = BeanValidator.validate(holiday);

				if (resultList.hasError()) {
					serviceResult
							.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
					Logger logger = Logger.getLogger(this.getClass());
					for (String rs : resultList.getErrorMessages()) {
						logger.debug(rs);
					}
					manager.rollback();
					return serviceResult;
				} else {
					manager.insert(holiday);
				}
			}
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

	public ServiceResult updatePaymentMethod(
			PaymentMethodSuite paymentMethodSuite) {
		ServiceResultImpl result = new ServiceResultImpl();
		PaymentMethodDao paymentDao = DIContainer
				.getDao(PaymentMethodDao.class);
		CommissionDao commissionDao = DIContainer.getDao(CommissionDao.class);

		PaymentMethod updateMethod = paymentMethodSuite.getPaymentMethod();
		Commission updateCommission = paymentMethodSuite.getCommissionList()
				.get(0);
		PaymentMethod orgMethod = paymentDao.load(updateMethod.getShopCode(),
				updateMethod.getPaymentMethodNo());

		// String length = WebUtil.buildMaxlength(Commission.class,
		// "paymentPriceThreshold", null);
		//
		// String paymentPriceThreshold = "";
		// for (Long i = 0L; i < NumUtil.toLong(length); i++) {
		// paymentPriceThreshold += "9";
		// }
		// Commission orgCommission =
		// commissionDao.load(updateMethod.getShopCode(),
		// updateMethod.getPaymentMethodNo(), NumUtil
		// .parse(paymentPriceThreshold));
		Precision precision = BeanUtil.getAnnotation(Commission.class,
				"paymentPriceThreshold", Precision.class);
		BigDecimal paymentPriceThreshold = NumUtil.getActualMaximum(precision
				.precision(), precision.scale());
		Commission orgCommission = commissionDao.load(updateMethod
				.getShopCode(), updateMethod.getPaymentMethodNo(),
				paymentPriceThreshold);

		if (orgMethod == null || orgCommission == null) {
			result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return result;
		}

		updateMethod.setOrmRowid(orgMethod.getOrmRowid());
		updateMethod.setCreatedDatetime(orgMethod.getCreatedDatetime());
		updateMethod.setCreatedUser(orgMethod.getCreatedUser());
		updateMethod.setUpdatedUser(orgMethod.getUpdatedUser());

		ValidationSummary paymentResultList = BeanValidator
				.validate(updateMethod);
		if (paymentResultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : paymentResultList.getErrorMessages()) {
				logger.debug(rs);
			}
			return result;
		}

		updateCommission.setOrmRowid(orgCommission.getOrmRowid());
		updateCommission.setCreatedDatetime(orgCommission.getCreatedDatetime());
		updateCommission.setCreatedUser(orgCommission.getCreatedUser());
		updateCommission.setUpdatedUser(orgCommission.getUpdatedUser());
		updateCommission.setUpdatedDatetime(orgCommission.getUpdatedDatetime());

		ValidationSummary commissionResultList = BeanValidator
				.validate(updateMethod);
		if (commissionResultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : commissionResultList.getErrorMessages()) {
				logger.debug(rs);
			}
			return result;
		}

		TransactionManager manager = DIContainer.getTransactionManager();

		try {
			manager.begin(getLoginInfo());
			manager.update(updateMethod);
			manager.update(updateCommission);
			manager.commit();

		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}

		return result;
	}

	public ServiceResult updateRegionBlockList(RegionBlock regionBlock) {
		ServiceResultImpl result = new ServiceResultImpl();

		RegionBlockDao dao = DIContainer.getDao(RegionBlockDao.class);

		RegionBlock orgBlock = dao.load(regionBlock.getShopCode(), regionBlock
				.getRegionBlockId());

		if (orgBlock == null) {
			result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return result;
		}

		regionBlock.setOrmRowid(orgBlock.getOrmRowid());
		regionBlock.setCreatedDatetime(orgBlock.getCreatedDatetime());
		regionBlock.setCreatedUser(orgBlock.getCreatedUser());
		setUserStatus(regionBlock);

		ValidationSummary resultList = BeanValidator.validate(regionBlock);
		if (resultList.hasError()) {
			result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
		} else {
			dao.update(regionBlock, getLoginInfo());
		}

		return result;
	}

	/**
	 * shop情報を更新する
	 */
	public ServiceResult updateShop(Shop shop) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();

		ShopDao shopDao = DIContainer.getDao(ShopDao.class);

		Shop orgShop = getShop(shop.getShopCode());

		// ショップ未登録時エラー
		if (orgShop == null) {
			serviceResult
					.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
			return serviceResult;
		}

		// データのコピーを行う
		shop.setOrmRowid(orgShop.getOrmRowid());
		shop.setCreatedDatetime(orgShop.getCreatedDatetime());
		shop.setCreatedUser(orgShop.getCreatedUser());
		shop.setUpdatedUser(orgShop.getUpdatedUser());

		if (shop.getCloseDatetime() == null) {
			shop.setCloseDatetime(DateUtil.truncateDate(DateUtil.getMax()));
		}

		// Validationチェック
		ValidationSummary resultList = BeanValidator.validate(shop);

		if (resultList.hasError()) {
			serviceResult
					.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
			Logger logger = Logger.getLogger(this.getClass());
			for (String rs : resultList.getErrorMessages()) {
				logger.debug(rs);
			}
			return serviceResult;
		}

		shopDao.update(shop, getLoginInfo());

		return serviceResult;
	}

	public Long getOpenShopCount() {
		return NumUtil.toLong(DatabaseUtil.executeScalar(
				new SimpleQuery(ShopManagementSimpleSql.LOAD_OPEN_SHOP_COUNT))
				.toString());
	}

	public BigDecimal calculateShippingCharge(String shopCode,
			Long deliveryTypeNo, String prefectureCode, int itemCount,
			BigDecimal total) {
		Logger logger = Logger.getLogger(this.getClass());

		if (shopCode == null || deliveryTypeNo == null
				|| prefectureCode == null) {
			throw new IllegalArgumentException();
		}
		// delete by V10-CH 170 start
		// if (!PrefectureCode.isValid(prefectureCode)) {
		// throw new IllegalArgumentException();
		// }
		// delete by V10-CH 170 start

		DeliveryTypeDao dtDao = DIContainer.getDao(DeliveryTypeDao.class);
		DeliveryType dt = dtDao.load(shopCode, deliveryTypeNo);

		RegionBlockLocationDao regionDao = DIContainer
				.getDao(RegionBlockLocationDao.class);
		List<RegionBlockLocation> regionBlockLocationList = regionDao
				.findByQuery(new SimpleQuery(
						ShopManagementServiceQuery.LOAD_REGION_BLOCK_LOCATION,
						shopCode, prefectureCode));

		// 存在しないショップや配送種別が指定された場合
		if (regionBlockLocationList == null
				|| regionBlockLocationList.size() < 1) {
			throw new ServiceException(Messages
					.getString("service.impl.ShopManagementServiceImpl.4"));
		}
		RegionBlockLocation regionBlockLocation = regionBlockLocationList
				.get(0);
		Long regionBlockId = regionBlockLocation.getRegionBlockId();

		ShippingChargeDao scDao = DIContainer.getDao(ShippingChargeDao.class);
		ShippingCharge sc = scDao.load(shopCode, deliveryTypeNo, regionBlockId);

		if (sc == null) {
			// logger.error("shipping charge is null. deliveryTypeNo = " +
			// deliveryTypeNo + " regionBlockId = " + regionBlockId);
			// throw new ServiceException("ShippingCharge is null");
			logger.error(MessageFormat.format(Messages
					.log("service.impl.ShopManagementServiceImpl.5"),
					deliveryTypeNo, regionBlockId));
			throw new ServiceException(Messages
					.getString("service.impl.ShopManagementServiceImpl.6"));
		}

		BigDecimal charge = sc.getShippingCharge();

		if (dt.getShippingChargeFreeFlg().equals(
				ShippingChargeFreeFlg.ACCOUNTING.longValue())
				// && dt.getShippingChargeFreeThreshold() <= total) {
				&& BigDecimalUtil.isBelowOrEquals(dt
						.getShippingChargeFreeThreshold(), total)) {
			// 無料フラグが1かつ、購入金額が閾値を超えている場合無料
			charge = BigDecimal.ZERO;
		} else {
			// 無料フラグが0もしくは、購入金額が閾値以下の場合
			if (itemCount > 1) {
				// 複数購入時の送料計算
				if (dt.getShippingChargeFlg().equals(
						ShippingChargeFlg.EACH.longValue())) {
					// 送料フラグが0なら商品1つずつ課金
					charge = BigDecimalUtil.multiply(charge, itemCount);
				} else {
					// 最も高い送料(?)1点＋(送料課金閾値 * 商品個数)
					// charge += (dt.getShippingChargeThreshold() * (itemCount -
					// 1));
					charge = charge.add(BigDecimalUtil.multiply(dt
							.getShippingChargeThreshold(), itemCount - 1));

				}
			}
			// 単一購入の場合は地域ブロックの送料そのまま
		}

		Long taxType = dt.getShippingChargeTaxType();
		TaxUtil util = DIContainer.get("TaxUtil");
		Long taxRate = util.getTaxRate();
		charge = Price.getPriceIncludingTax(charge, taxRate, taxType + "");

		return charge;
	}

	public List<CouponIssue> getCouponIssueList(String shopCode) {
		CouponIssueDao dao = DIContainer.getDao(CouponIssueDao.class);
		List<CouponIssue> couponIssueList = dao.findByQuery(
				ShopManagementSimpleSql.LOAD_COUPON_ISSUE_LIST, shopCode);
		return couponIssueList;
	}

	public CouponIssue getCouponIssue(String shopCode, Long couponIssueNo) {
		CouponIssueDao dao = DIContainer.getDao(CouponIssueDao.class);
		CouponIssue couponIssue = dao.load(shopCode, couponIssueNo);
		return couponIssue;
	}

	public ServiceResult insertCouponIssue(CouponIssue couponIssue) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		Long nextVal = DatabaseUtil
				.generateSequence(SequenceType.COUPON_ISSUE_NO);
		couponIssue.setCouponIssueNo(nextVal);
		// 重複チェック
		CouponIssue couponIssueOrg = getCouponIssue(couponIssue.getShopCode(),
				couponIssue.getCouponIssueNo());
		if (couponIssueOrg != null) {
			serviceResult
					.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
			return serviceResult;
		}

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());

			setUserStatus(couponIssue);
			ValidationSummary resultList = BeanValidator.validate(couponIssue);
			if (resultList.hasError()) {
				serviceResult
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				return serviceResult;
			} else {
				manager.insert(couponIssue);
			}

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

	public ServiceResult updateCouponIssue(CouponIssue couponIssue) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();

		// 削除済みチェック
		CouponIssue couponIssueOrg = getCouponIssue(couponIssue.getShopCode(),
				couponIssue.getCouponIssueNo());
		if (couponIssue == null) {
			serviceResult
					.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return serviceResult;
		}

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());

			couponIssue.setCreatedUser(couponIssueOrg.getCreatedUser());
			couponIssue.setCreatedDatetime(couponIssueOrg.getCreatedDatetime());
			couponIssue.setOrmRowid(couponIssueOrg.getOrmRowid());

			setUserStatus(couponIssue);
			ValidationSummary resultList = BeanValidator.validate(couponIssue);
			if (resultList.hasError()) {
				serviceResult
						.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
				manager.rollback();
				return serviceResult;
			} else {
				manager.update(couponIssue);
			}

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

	public ServiceResult deleteCouponIssue(String shopCode, Long couponIssueNo) {
		ServiceResultImpl result = new ServiceResultImpl();

		CouponIssueDao dao = DIContainer.getDao(CouponIssueDao.class);

		TransactionManager manager = DIContainer.getTransactionManager();
		try {
			manager.begin(getLoginInfo());
			CouponIssue couponIssue = dao.load(shopCode, couponIssueNo);
			if (couponIssue != null) {
				manager.delete(couponIssue);
			}

			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			result
					.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}
		return result;
	}

	public List<CouponResearch> getCouponResearch(String shopCode,
			String couponCode) {
		Query query = new SimpleQuery(CouponQuery.getLoadCouponResearchQuery(),
				NumUtil.toLong(couponCode), shopCode);
		return DatabaseUtil.loadAsBeanList(query, CouponResearch.class);
	}

	public CouponResearchInfo getCouponResearchInfo(String shopCode,
			String couponCode) {
		Query query = new SimpleQuery(CouponQuery
				.getloadCouponResearchDateQuery(), CouponUsedFlg.USED
				.longValue(), CouponUsedFlg.ENABLED.longValue(),
				CouponUsedFlg.DISABLED.longValue(), CouponUsedFlg.OVERDUE
						.longValue(), CouponUsedFlg.PHANTOM_COUPON.longValue(),
				NumUtil.toLong(couponCode));
		return DatabaseUtil.loadAsBean(query, CouponResearchInfo.class);
	}
	

	/**
	 * 取得配送公司信息集合
	 * 
	 * @param shopCode
	 *            店铺编号
	 * @return 配送公司信息集合
	 */
	public List<DeliveryCompany> getDeliveryCompanyList(String shopCode) {
		Query q = new SimpleQuery(
				ShopManagementSimpleSql.LOAD_DELIVERY_COMPANY_LIST, shopCode);
		List<DeliveryCompany> deliveryCompanyList = DatabaseUtil.loadAsBeanList(q, DeliveryCompany.class);

		return deliveryCompanyList;
	}

	/**
	 * 根据(店铺编号)查询配送会社
	 * 
	 * @param shopCode
	 *            店铺编号
	 * @return 指定的配送会社信息
	 */
	public DeliveryCompany getDeliveryCompany(String shopCode, String deliveryCompanyNo) {
		DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);

		return dao.load(shopCode, deliveryCompanyNo);
	}

	/**
	 * 根据(店铺编号)删除配送会社
	 * 
	 * @param shopCode
	 *            店铺编号
	 * @return 指定的配送会社信息
	 */
	public ServiceResult deleteDeliveryCompany(String shopCode, String deliveryCompanyNo) {
		TransactionManager manager = DIContainer.getTransactionManager();
		
		ServiceResultImpl serviceResult = new ServiceResultImpl();

		DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);

		DeliveryCompany company = dao.load(shopCode, deliveryCompanyNo);

		if (company == null) {
			serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
			return serviceResult;
		}else if (DefaultFlg.DEFAULT.longValue().equals(company.getDefaultFlg())) {
			serviceResult.addServiceError(ShopManagementServiceErrorContent.DEFAULT_DELETE_ERROR);
			return serviceResult;
		}

		// shopCodeに関連付いているデータの削除
		try {
			manager.begin(getLoginInfo());

			for (int i =0;i<ShopManagementSimpleSql.DELETE_DELIVERY_COMPANY_INFO.length;i++) {
				manager.executeUpdate(ShopManagementSimpleSql.DELETE_DELIVERY_COMPANY_INFO[i], deliveryCompanyNo);
			}

			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}
		return serviceResult;
		
	}

	/**
	 * 取得地域区分全部信息
	 * 
	 * @param shopCode
	 * @return 配送公司编号
	 */	
	public List<ShippingChargeSuite> getShippingChargeList(String shopCode, String deliveryCompanyNo) {
		Shop shop = new Shop();
		shop.setShopCode(shopCode);
		List<RegionBlock> blockList = getRegionBlockList(shop);
		Query q = new SimpleQuery(ShopManagementSimpleSql.LOAD_SHIPPING_COMPANY_CHARGE,
				shopCode, deliveryCompanyNo);
		Map<Long, String> regionMap = new HashMap<Long, String>();
		for (RegionBlock block : blockList) {
			regionMap.put(block.getRegionBlockId(), block.getRegionBlockName());
		}

		List<ShippingCharge> chargeList = DatabaseUtil.loadAsBeanList(q, ShippingCharge.class);

		List<ShippingChargeSuite> list = new ArrayList<ShippingChargeSuite>();
		for (ShippingCharge charge : chargeList) {
			ShippingChargeSuite suite = new ShippingChargeSuite();
			suite.setRegionBlockName(regionMap.get(charge.getRegionBlockId()));
			suite.setShippingCharge(charge);

			list.add(suite);
		}

		return list;
	}

	// soukai update 2011/12/19 ob start
	/**
	 * 追加配送公司信息
	 * 
	 * @param shopCode
	 * @param deliveryCompanyNo
	 * @return 配送公司信息
	 */	
	public ServiceResult insertDeliveryCompany(DeliveryCompany company) {
	    
	    ServiceResultImpl result = new ServiceResultImpl();
	    Logger logger = Logger.getLogger(this.getClass());
	    DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);

	    // 重複エラーチェック
	    if (dao.load(company.getShopCode(), company.getDeliveryCompanyNo()) != null) {
	      result.addServiceError(CommunicationServiceErrorContent.DUPLICATED_CODE_ERROR);
	    }

	    // validationチェック
	    List<ValidationResult> resultList = BeanValidator.validate(company).getErrors();
	    if (resultList.size() > 0) {
	      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
	      for (ValidationResult rs : resultList) {
	        logger.debug(rs.getFormedMessage());
	      }
	    }

	    if (result.hasError()) {
	      return result;
	    }

	    dao.insert(company, getLoginInfo());

	    return result;
	}

	/**
	 * 更新配送公司信息
	 * 
	 * @param company
	 * @return 配送公司信息
	 */			
	public ServiceResult updateDeliveryCompany(DeliveryCompany company) {
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
	    if (company == null) {
	      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
	    }
	    try {
	    	setUserStatus(company);
	    	dao.update(company, getLoginInfo());
		} catch (Exception e) {
			serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		}
	    return serviceResult;
	}
	// soukai update 2011/12/19 ob end

	// soukai add 2011/12/19 ob start
	/**
	 * 配送公司编号不能重复
	 * 
	 * @param deliveryCompanyNo
	 * @return 配送公司信息
	 */	
	public DeliveryCompany getDeliveryCompanyByNo(String deliveryCompanyNo) {
		DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
		return dao.load(deliveryCompanyNo);
	}
	// soukai add 2011/12/19 ob end
	@Override
	public SearchResult<DeliveryRegionChargeInfo> getDeliveryRegionCharge(DeliveryRegionChargeCondition condition) {
		SearchQuery<DeliveryRegionChargeInfo> query = new DeliveryRegionChargeQuery(condition);
		return DatabaseUtil.executeSearch(query);
	}
	/**
	 * 执行运费的更新功能
	 */
	public ServiceResult updateDeliveryRegionCharge(DeliveryRegionCharge deliveryRegionCharge) {
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    DeliveryRegionChargeDao deliveryRegionChargeDao = DIContainer.getDao(DeliveryRegionChargeDao.class);
	    List<DeliveryRegionCharge> list = new ArrayList<DeliveryRegionCharge>();
	    if (StringUtil.hasValue(deliveryRegionCharge.getPrefectureCode())) {
	    	DeliveryRegionCharge orgInfo = deliveryRegionChargeDao.load(deliveryRegionCharge.getPrefectureCode(), deliveryRegionCharge.getDeliveryCompanyNo());
		    list.add(orgInfo);
	    } else {
	    	list = deliveryRegionChargeDao.loadAll();
	    }
	    try {
	    	for (DeliveryRegionCharge info : list) {
	    	  info.setDeliveryCompanyNo(deliveryRegionCharge.getDeliveryCompanyNo());
	    		info.setAddCharge(deliveryRegionCharge.getAddCharge());
	    		info.setAddWeight(deliveryRegionCharge.getAddWeight());
	    		info.setDeliveryChargeBig(deliveryRegionCharge.getDeliveryChargeBig());
	    		info.setDeliveryChargeSmall(deliveryRegionCharge.getDeliveryChargeSmall());
	    		info.setDeliveryWeight(deliveryRegionCharge.getDeliveryWeight());
	    		info.setFreeOrderAmount(deliveryRegionCharge.getFreeOrderAmount());
	    		info.setFreeWeight(deliveryRegionCharge.getFreeWeight());
	    		info.setLeadTime(deliveryRegionCharge.getLeadTime());
	    		info.setOrderAmount(deliveryRegionCharge.getOrderAmount());
	    		setUserStatus(info);
			    deliveryRegionChargeDao.update(info, getLoginInfo());
	    	}
		} catch (Exception e) {
			serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		}
	    return serviceResult;
	}
	/**
	 * 跟据配送公司编号、店铺号查询运费功能
	 * @param shopCode 店铺号
	 * @param deliveryCompanyNo 配送公司编号
	 * @param regionBlockId  配送地域编号
	 * @return DeliveryRegionCharge
	 */
	public DeliveryRegionCharge getDeliveryRegionCharge(String shopCode,String deliveryCompanyNo,Long regionBlockId) {
	    DeliveryRegionChargeDao deliveryRegionChargeDao = DIContainer.getDao(DeliveryRegionChargeDao.class);
		Query query = new SimpleQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION_CHARGE_BY_COMPANYCODE, shopCode,deliveryCompanyNo, regionBlockId);
		List<DeliveryRegionCharge> list= deliveryRegionChargeDao.findByQuery(query);
		if(list.size()>0)
		{
			return list.get(0);
		}
	    return null;
	}

	/**
	 * 跟据配送公司编号、店铺号查询运费集合功能
	 * @param shopCode 店铺号
	 * @param deliveryCompanyNo 配送公司编号
	 * @param regionBlockId  配送地域编号
	 * @return List<DeliveryRegionCharge>
	 */
	public List<DeliveryRegionCharge> getDeliveryRegionChargeList(String shopCode,String deliveryCompanyNo) {
	    DeliveryRegionChargeDao deliveryRegionChargeDao = DIContainer.getDao(DeliveryRegionChargeDao.class);
	    List<DeliveryRegionCharge> list=deliveryRegionChargeDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION_CHARGE_LIST_BY_COMPANYCODE,shopCode,deliveryCompanyNo);
	    return list;
	}


	/**
	 * 根据店铺号、地域名、货到付款区分、配送希望日指定区分和公司Id查询所有的配送信息的信息
	 * @param shopCode shopCode
	 * @param deliveryComputerNo 公司编号
	 * @param prefectureCode 地域Id
	 * @return List<DeliveryRelatedInfo>
	 */
	public List<DeliveryRelatedInfo> getDeliveryCompanyAppointedTimeListByCod(String shopCode,String deliveryCompanyNo,String prefectureCode,Long codType,Long deliveryDateType) {
		DeliveryRelatedInfoDao deliveryRegionAppointedTimeDao = DIContainer.getDao(DeliveryRelatedInfoDao.class);
	    return deliveryRegionAppointedTimeDao.findByQuery(ShopManagementSimpleSql.LOAD_COMPANY_TIME_ALL_BY_COD_TYPE,shopCode,deliveryCompanyNo,prefectureCode,codType,deliveryDateType);
	}
	
	/**
	 * 根据店铺号、地域名、货到付款区分、配送希望日指定区分、公司Id和配送时间ID查询所有的配送信息的信息
	 * @param shopCode shopCode
	 * @param deliveryComputerNo 公司编号
	 * @param prefectureCode 地域Id
	 * @param timeCode 配送时间ID
	 * @return DeliveryRegionAppointedTime
	 */
	public DeliveryRelatedInfo getDeliveryCompanyAppointedTimeListByTimeCode(String shopCode,String deliveryCompanyNo,String prefectureCode,Long codType,Long deliveryDateType,String timeCode) {
		DeliveryRelatedInfoDao deliveryRegionAppointedTimeDao = DIContainer.getDao(DeliveryRelatedInfoDao.class);
	    List<DeliveryRelatedInfo> list=deliveryRegionAppointedTimeDao.findByQuery(ShopManagementSimpleSql.LOAD_COMPANY_TIME_ALL_BY_TIME_CODE,shopCode,deliveryCompanyNo,prefectureCode,codType,deliveryDateType,timeCode);
	    if(list.size()>0)
	    {
	    	return list.get(0);
	    }
	    return null;
	}

	/**
	 * 根据查询配送信息的组别信息
	 * @param shopCode shopCode
	 * @param deliveryComputerNo 公司编号
	 * @param prefectureCode 地域Id
	 * @return List<DeliveryRelatedInfo>
	 */
	public List<DeliveryRelatedInfo> getDeliveryRelatedInfo(String shopCode,String deliveryCompanyNo,String prefectureCode) {
		DeliveryRelatedInfoDao deliveryRegionAppointedTimeDao = DIContainer.getDao(DeliveryRelatedInfoDao.class);
	    return deliveryRegionAppointedTimeDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_RELATED_TIME,shopCode,deliveryCompanyNo,prefectureCode);
	}
	// soukai add ob 2011/12/19 start
	/**
	 * 取得配送公司关联情报集合
	 * @return List<DeliveryRelatedInfo>
	 */
	public List<DeliveryRelatedInfo> getDeliveryRelatedInfoList(String shopCode,String deliveryCompanyNo,String prefectureCode) {
		DeliveryRelatedInfoDao deliveryRelatedInfoDao = DIContainer.getDao(DeliveryRelatedInfoDao.class);
		return deliveryRelatedInfoDao.load(shopCode, deliveryCompanyNo, prefectureCode);
	}
	
  public List<JdDeliveryRelatedInfo> getDeliveryRelatedInfoListJd(String shopCode,String deliveryCompanyNo,String prefectureCode) {
    JdDeliveryRelatedInfoDao deliveryRelatedInfoDao = DIContainer.getDao(JdDeliveryRelatedInfoDao.class);
     return deliveryRelatedInfoDao.load(shopCode, deliveryCompanyNo, prefectureCode);
  }
	
	public List<TmallDeliveryRelatedInfo> getDeliveryRelatedInfoListTmall(String shopCode,String deliveryCompanyNo,String prefectureCode) {
	  TmallDeliveryRelatedInfoDao deliveryRelatedInfoDao = DIContainer.getDao(TmallDeliveryRelatedInfoDao.class);
	  return deliveryRelatedInfoDao.load(shopCode, deliveryCompanyNo, prefectureCode);
	}

	/**
	 * 执行取得配送公司关联情报插入功能
	 * @param DeliveryRelatedInfo 配送公司关联情报Bean
	 * @return  ServiceResult
	 */
	public ServiceResult insertDeliveryRelatedInfo(DeliveryRelatedInfo deliveryRelatedInfo) {
		TransactionManager manager = DIContainer.getTransactionManager();
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		Long nextVal = DatabaseUtil.generateSequence(SequenceType.DELIVERY_RELATED_INFO_NO);
		deliveryRelatedInfo.setDeliveryRelatedInfoNo(nextVal.toString());
		setUserStatus(deliveryRelatedInfo);
		try {
			manager.begin(getLoginInfo());
			manager.insert(deliveryRelatedInfo);
			manager.commit();
		} catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}
		return serviceResult;
	}
	
	 public ServiceResult insertDeliveryRelatedInfoJd(JdDeliveryRelatedInfo deliveryRelatedInfo) {
	    TransactionManager manager = DIContainer.getTransactionManager();
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    Long nextVal = DatabaseUtil.generateSequence(SequenceType.DELIVERY_RELATED_INFO_NO);
	    deliveryRelatedInfo.setDeliveryRelatedInfoNo(nextVal.toString());
	    setUserStatus(deliveryRelatedInfo);
	    try {
	      manager.begin(getLoginInfo());
	      manager.insert(deliveryRelatedInfo);
	      manager.commit();
	    } catch (ConcurrencyFailureException e) {
	      manager.rollback();
	      throw e;
	    } catch (RuntimeException e) {
	      manager.rollback();
	      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
	    } finally {
	      manager.dispose();
	    }
	    return serviceResult;
	  }
	
	public ServiceResult insertDeliveryRelatedInfoTmall(TmallDeliveryRelatedInfo deliveryRelatedInfo) {
	    TransactionManager manager = DIContainer.getTransactionManager();
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    Long nextVal = DatabaseUtil.generateSequence(SequenceType.TMALL_DELIVERY_RELATED_INFO_NO);
	    deliveryRelatedInfo.setDeliveryRelatedInfoNo(nextVal.toString());
	    setUserStatus(deliveryRelatedInfo);
	    try {
	      manager.begin(getLoginInfo());
	      manager.insert(deliveryRelatedInfo);
	      manager.commit();
	    } catch (ConcurrencyFailureException e) {
	      manager.rollback();
	      throw e;
	    } catch (RuntimeException e) {
	      manager.rollback();
	      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
	    } finally {
	      manager.dispose();
	    }
	    return serviceResult;
	  }

	/**
	 * 执行取得配送公司关联情报更新功能
	 * @return ServiceResult
	 */
	public ServiceResult updateDeliveryRelatedInfo(DeliveryRelatedInfo deliveryRelatedInfo) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		DeliveryRelatedInfoDao deliveryRelatedInfoDao = DIContainer.getDao(DeliveryRelatedInfoDao.class);
	    if (deliveryRelatedInfo == null) {
	      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
	    }
	    try {
	    	setUserStatus(deliveryRelatedInfo);
	    	deliveryRelatedInfoDao.update(deliveryRelatedInfo, getLoginInfo());
		} catch (Exception e) {
			serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		}
	    return serviceResult;
	}

	/**
	 * 执行取得配送公司关联情报删除功能
	 * @return ServiceResult
	 */
	public ServiceResult deleteDeliveryRelatedInfo(DeliveryRelatedInfo deliveryRelatedInfo) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();
		DeliveryRelatedInfoDao deliveryRelatedInfoDao = DIContainer.getDao(DeliveryRelatedInfoDao.class);
	    if (deliveryRelatedInfo == null) {
	      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
	    }
	    try {
	    	deliveryRelatedInfoDao.delete(deliveryRelatedInfo, getLoginInfo());
		} catch (Exception e) {
			serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		}
	    return serviceResult;
	}
	
  public ServiceResult deleteDeliveryRelatedInfoJd(JdDeliveryRelatedInfo deliveryRelatedInfo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    JdDeliveryRelatedInfoDao deliveryRelatedInfoDao = DIContainer.getDao(JdDeliveryRelatedInfoDao.class);
      if (deliveryRelatedInfo == null) {
        serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      }
      try {
        deliveryRelatedInfoDao.delete(deliveryRelatedInfo, getLoginInfo());
    } catch (Exception e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
      return serviceResult;
  }
	
  public ServiceResult deleteDeliveryRelatedInfoTmall(TmallDeliveryRelatedInfo deliveryRelatedInfo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallDeliveryRelatedInfoDao deliveryRelatedInfoDao = DIContainer.getDao(TmallDeliveryRelatedInfoDao.class);
      if (deliveryRelatedInfo == null) {
        serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      }
      try {
        deliveryRelatedInfoDao.delete(deliveryRelatedInfo, getLoginInfo());
    } catch (Exception e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
      return serviceResult;
  }
	/**
	 * 设置默认配送公司
	 * @param deliveryCompanyNo 配送公司编号
	 * @return  ServiceResult
	 */
	public ServiceResult setDefaultDeliveryCompany(String deliveryCompanyNo) {
		ServiceResultImpl serviceResult = new ServiceResultImpl();
	    TransactionManager manager = DIContainer.getTransactionManager();
	    try {
	    	manager.begin(getLoginInfo());
	    	int i = manager.executeUpdate(ShopManagementServiceQuery.UPDATE_DEFAULT_DELIVERY_COMPANY, getLoginInfo().getRecordingFormat(),DateUtil.getSysdate(), deliveryCompanyNo);
	    	if (i==0) {
	    		serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
	    		manager.rollback();
	    		return serviceResult;
	    	}
	    	manager.executeUpdate(ShopManagementServiceQuery.UPDATE_DEFAULT_DELIVERY_COMPANY2, getLoginInfo().getRecordingFormat(),DateUtil.getSysdate(), deliveryCompanyNo);
	    	manager.commit();
	    }  catch (ConcurrencyFailureException e) {
			manager.rollback();
			throw e;
		} catch (RuntimeException e) {
			manager.rollback();
			serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
		} finally {
			manager.dispose();
		}
	    return serviceResult;
	}
	//soukai add 2011/12/19 ob end
	
  //soukai add 2011/12/20 ob shb begin
  
  /**
   * 根据根据店铺号、地域名、公司Id查询配送公司的地域手续费信息
   * @param shopCode shopCode
   * @param deliveryComputerNo 公司编号
   * @param prefectureCode 地域Id
   * @return List<DeliveryRegion>
   */
  public List<DeliveryRegion> getDeliveryRegionListByDeliveryCompanyNo(String shopCode, String deliveryCompanyNo) {
    DeliveryRegionDao deliveryRegionDao = DIContainer.getDao(DeliveryRegionDao.class);
    return deliveryRegionDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION_LIST_BY_DELIVERY_COMPANYNO, shopCode, deliveryCompanyNo);
  }
  
  public List<TmallDeliveryRegion> getDeliveryRegionListByDeliveryCompanyNoTmall(String shopCode, String deliveryCompanyNo) {
    TmallDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(TmallDeliveryRegionDao.class);
    return deliveryRegionDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION_LIST_BY_DELIVERY_COMPANYNO_TMALL, shopCode, deliveryCompanyNo);
  }
  
  public List<JdDeliveryRegion> getDeliveryRegionListByDeliveryCompanyNoJd(String shopCode, String deliveryCompanyNo) {
    JdDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(JdDeliveryRegionDao.class);
    return deliveryRegionDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION_LIST_BY_DELIVERY_COMPANYNO_JD, shopCode, deliveryCompanyNo);
  }
  
//2013/04/15 配送公司设定对应 ob add start
  /**
   * 根据根据店铺号、公司Id查询配送公司的配送市县信息
   * @param shopCode shopCode
   * @param deliveryComputerNo 公司编号
   * @return List<DeliveryLocation>
   */
  public List<DeliveryLocation> getLoad(String shopCode, String deliveryCompanyNo){
    DeliveryLocationDao deliveryLocationDao = DIContainer.getDao(DeliveryLocationDao.class);
    return deliveryLocationDao.findByQuery(ShopManagementSimpleSql.LOAD_COMPANYNO, shopCode, deliveryCompanyNo);
  }
  
  public List<JdDeliveryLocation> getLoadJd(String shopCode, String deliveryCompanyNo){
    JdDeliveryLocationDao deliveryLocationDao = DIContainer.getDao(JdDeliveryLocationDao.class);
    return deliveryLocationDao.findByQuery(ShopManagementSimpleSql.LOAD_COMPANYNO_JD, shopCode, deliveryCompanyNo);
  }
  
  public List<TmallDeliveryLocation> getLoadTmall(String shopCode, String deliveryCompanyNo){
    TmallDeliveryLocationDao deliveryLocationDao = DIContainer.getDao(TmallDeliveryLocationDao.class);
    return deliveryLocationDao.findByQuery(ShopManagementSimpleSql.LOAD_COMPANYNO_TMALL, shopCode, deliveryCompanyNo);
  }
//2013/04/15 配送公司设定对应 ob add end 
  /**
   * 获取指定的  DeliveryRegion
   * @param shopCode           
   * @param deliveryCompanyNo  公司编号
   * @param prefectureCode     地域Id
   * @return
   */
  public DeliveryRegion getDeliveryRegion(String shopCode, String deliveryCompanyNo, String prefectureCode) {
    DeliveryRegionDao deliveryRegionDao = DIContainer.getDao(DeliveryRegionDao.class);
    List<DeliveryRegion> deliveryRegionList = deliveryRegionDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION, shopCode, deliveryCompanyNo, prefectureCode);
    
    if (deliveryRegionList.size() > 0) {
      return deliveryRegionList.get(0);
    } else {
      return null;
    }
  }
  
  public JdDeliveryRegion getDeliveryRegionJd(String shopCode, String deliveryCompanyNo, String prefectureCode) {
    JdDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(JdDeliveryRegionDao.class);
    List<JdDeliveryRegion> deliveryRegionList = deliveryRegionDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION_JD, shopCode, deliveryCompanyNo, prefectureCode);
    
    if (deliveryRegionList.size() > 0) {
      return deliveryRegionList.get(0);
    } else {
      return null;
    }
  }
  
  public TmallDeliveryRegion getDeliveryRegionTmall(String shopCode, String deliveryCompanyNo, String prefectureCode) {
    TmallDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(TmallDeliveryRegionDao.class);
    List<TmallDeliveryRegion> deliveryRegionList = deliveryRegionDao.findByQuery(ShopManagementSimpleSql.LOAD_DELIVERY_REGION_TMALL, shopCode, deliveryCompanyNo, prefectureCode);
    
    if (deliveryRegionList.size() > 0) {
      return deliveryRegionList.get(0);
    } else {
      return null;
    }
  }
  
  /**
   * 插入DeliveryRegion信息列表
   * @param deliveryRegionList
   * @return 插入结果
   */
  public ServiceResult insertDeliveryRegionList(List<DeliveryRegion> deliveryRegionList) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    DeliveryRegion tmp = null;
    if (deliveryRegionList.size() > 0) {
      tmp = deliveryRegionList.get(0);
    } else {
      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }
    //取得delivery_region中的所有DeliveryRegion
    List<DeliveryRegion> foundList = getDeliveryRegionListByDeliveryCompanyNo(tmp.getShopCode(), tmp.getDeliveryCompanyNo());
    /*
     * 遍历数据库中的记录集,与页面传送过来的用户重新选择的记录集进行比较
     * 如果数据库中的记录集已经不存在与用户重新选择的记录集中,表示该配送公司的这个地域信息不再需要,直接删除掉
     */
    boolean bFound = false;
    for(DeliveryRegion foundObj : foundList) {
      for(DeliveryRegion insertObj : deliveryRegionList) {
        if (foundObj.getPrefectureCode().equals(insertObj.getPrefectureCode())) {
          bFound = true;
          break;
        }
      }
      
      if (!bFound) {
        this.deleteDeliveryRegion(foundObj);
      }
      bFound = false;
    }
    //再将不存在的记录集插入到数据库
    DeliveryRegionDao deliveryRegionDao = DIContainer.getDao(DeliveryRegionDao.class);    
    for (DeliveryRegion deliveryRegion : deliveryRegionList) {
      if (!deliveryRegionDao.exists(deliveryRegion.getShopCode(), deliveryRegion.getDeliveryCompanyNo(), deliveryRegion.getPrefectureCode())) {
        serviceResult = (ServiceResultImpl) insertDeliveryRegion(deliveryRegion);
      }
    }
    
    return serviceResult;
  }
  
  public ServiceResult insertDeliveryRegionListJd(List<JdDeliveryRegion> deliveryRegionList) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    JdDeliveryRegion tmp = null;
    if (deliveryRegionList.size() > 0) {
      tmp = deliveryRegionList.get(0);
    } else {
      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }
    //取得delivery_region中的所有DeliveryRegion
    List<JdDeliveryRegion> foundList = getDeliveryRegionListByDeliveryCompanyNoJd(tmp.getShopCode(), tmp.getDeliveryCompanyNo());
    /*
     * 遍历数据库中的记录集,与页面传送过来的用户重新选择的记录集进行比较
     * 如果数据库中的记录集已经不存在与用户重新选择的记录集中,表示该配送公司的这个地域信息不再需要,直接删除掉
     */
    boolean bFound = false;
    for(JdDeliveryRegion foundObj : foundList) {
      for(JdDeliveryRegion insertObj : deliveryRegionList) {
        if (foundObj.getPrefectureCode().equals(insertObj.getPrefectureCode())) {
          bFound = true;
          break;
        }
      }
      
      if (!bFound) {
        this.deleteDeliveryRegionJd(foundObj);
      }
      bFound = false;
    }
    //再将不存在的记录集插入到数据库
    JdDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(JdDeliveryRegionDao.class);    
    for (JdDeliveryRegion deliveryRegion : deliveryRegionList) {
      if (!deliveryRegionDao.exists(deliveryRegion.getShopCode(), deliveryRegion.getDeliveryCompanyNo(), deliveryRegion.getPrefectureCode())) {
        serviceResult = (ServiceResultImpl) insertDeliveryRegionJd(deliveryRegion);
      }
    }
    
    return serviceResult;
  }
  
  
  public ServiceResult insertDeliveryRegionListTmall(List<TmallDeliveryRegion> deliveryRegionList) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallDeliveryRegion tmp = null;
    if (deliveryRegionList.size() > 0) {
      tmp = deliveryRegionList.get(0);
    } else {
      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }
    //取得delivery_region中的所有DeliveryRegion
    List<TmallDeliveryRegion> foundList = getDeliveryRegionListByDeliveryCompanyNoTmall(tmp.getShopCode(), tmp.getDeliveryCompanyNo());
    /*
     * 遍历数据库中的记录集,与页面传送过来的用户重新选择的记录集进行比较
     * 如果数据库中的记录集已经不存在与用户重新选择的记录集中,表示该配送公司的这个地域信息不再需要,直接删除掉
     */
    boolean bFound = false;
    for(TmallDeliveryRegion foundObj : foundList) {
      for(TmallDeliveryRegion insertObj : deliveryRegionList) {
        if (foundObj.getPrefectureCode().equals(insertObj.getPrefectureCode())) {
          bFound = true;
          break;
        }
      }
      
      if (!bFound) {
        this.deleteDeliveryRegionTmall(foundObj);
      }
      bFound = false;
    }
    //再将不存在的记录集插入到数据库
    TmallDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(TmallDeliveryRegionDao.class);    
    for (TmallDeliveryRegion deliveryRegion : deliveryRegionList) {
      if (!deliveryRegionDao.exists(deliveryRegion.getShopCode(), deliveryRegion.getDeliveryCompanyNo(), deliveryRegion.getPrefectureCode())) {
        serviceResult = (ServiceResultImpl) insertDeliveryRegionTmall(deliveryRegion);
      }
    }
    
    return serviceResult;
  }
  
  
  
//2013/04/15 配送公司设定对应 ob add start
  /**
   * 插入DeliveryLocation信息列表
   * @param deliveryLocationList
   * @return 插入结果
   */
  public ServiceResult insertDeliveryLocationList(List<DeliveryLocation> deliveryLocationList){
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    DeliveryLocation tmp = null;
    if (deliveryLocationList.size() > 0) {
      tmp = deliveryLocationList.get(0);
    } else {
      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }
    //取得此店铺、此配送公司所有已存在数据删除处理
    List<DeliveryLocation> foundList = getLoad(tmp.getShopCode(), tmp.getDeliveryCompanyNo());
    try {
      txMgr.begin(getLoginInfo());

      // 配送公司配送区域信息删除
      for(DeliveryLocation deliveryLocation : foundList){
        txMgr.delete(deliveryLocation);
      }
      for(DeliveryLocation deliveryLocation : deliveryLocationList){
        setUserStatus(deliveryLocation);
        txMgr.insert(deliveryLocation);
      }
      
      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }
  
  public ServiceResult insertDeliveryLocationListJd(List<JdDeliveryLocation> deliveryLocationList){
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    JdDeliveryLocation tmp = null;
    if (deliveryLocationList.size() > 0) {
      tmp = deliveryLocationList.get(0);
    } else {
      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }
    //取得此店铺、此配送公司所有已存在数据删除处理
    List<JdDeliveryLocation> foundList = getLoadJd(tmp.getShopCode(), tmp.getDeliveryCompanyNo());
    try {
      txMgr.begin(getLoginInfo());

      // 配送公司配送区域信息删除
      for(JdDeliveryLocation deliveryLocation : foundList){
        txMgr.delete(deliveryLocation);
      }
      for(JdDeliveryLocation deliveryLocation : deliveryLocationList){
        setUserStatus(deliveryLocation);
        txMgr.insert(deliveryLocation);
      }
      
      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }
  
  
  
  public ServiceResult insertDeliveryLocationListTmall(List<TmallDeliveryLocation> deliveryLocationListTmall){
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    TmallDeliveryLocation tmp = null;
    if (deliveryLocationListTmall.size() > 0) {
      tmp = deliveryLocationListTmall.get(0);
    } else {
      serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }
    //取得此店铺、此配送公司所有已存在数据删除处理
    List<TmallDeliveryLocation> foundList = getLoadTmall(tmp.getShopCode(), tmp.getDeliveryCompanyNo());
    try {
      txMgr.begin(getLoginInfo());

      // 配送公司配送区域信息删除
      for(TmallDeliveryLocation deliveryLocation : foundList){
        txMgr.delete(deliveryLocation);
      }
      for(TmallDeliveryLocation deliveryLocation : deliveryLocationListTmall){
        setUserStatus(deliveryLocation);
        txMgr.insert(deliveryLocation);
      }
      
      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }
//2013/04/15 配送公司设定对应 ob add end 
  /**
   * 插入DeliveryRegion信息
   * @param deliveryRegion
   * @return 插入结果
   */
  public ServiceResult insertDeliveryRegion(DeliveryRegion deliveryRegion) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    DeliveryRegionDao deliveryRegionDao = DIContainer.getDao(DeliveryRegionDao.class);
      if (deliveryRegionDao == null) {
        serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      }
      try {
        deliveryRegionDao.insert(deliveryRegion, getLoginInfo());
    } catch (Exception e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
      return serviceResult;
  }
  
  public ServiceResult insertDeliveryRegionJd(JdDeliveryRegion deliveryRegion) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    JdDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(JdDeliveryRegionDao.class);
      if (deliveryRegionDao == null) {
        serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      }
      try {
        deliveryRegionDao.insert(deliveryRegion, getLoginInfo());
    } catch (Exception e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
      return serviceResult;
  }
  
  public ServiceResult insertDeliveryRegionTmall(TmallDeliveryRegion deliveryRegion) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallDeliveryRegionDao deliveryRegionDao = DIContainer.getDao(TmallDeliveryRegionDao.class);
      if (deliveryRegionDao == null) {
        serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      }
      try {
        deliveryRegionDao.insert(deliveryRegion, getLoginInfo());
    } catch (Exception e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
      return serviceResult;
  }
  
  /**
   * 更新DeliveryRegion信息
   * @param deliveryRegion
   * @return 更新结果
   */
  public ServiceResult updateDeliveryRegion(DeliveryRegion deliveryRegion) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    DeliveryRegionDao deliveryRegionDao = DIContainer.getDao(DeliveryRegionDao.class);
      if (deliveryRegionDao == null) {
        serviceResult.addServiceError(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR);
      }
      try {
        setUserStatus(deliveryRegion);
        deliveryRegionDao.update(deliveryRegion, getLoginInfo());
    } catch (Exception e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
      return serviceResult;
  }
  
  /**
   * 删除指定的DeliveryRegion信息
   * @param deliveryRegion
   * @return 删除结果
   */
  public ServiceResult deleteDeliveryRegionTmall(TmallDeliveryRegion deliveryRegion) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(getLoginInfo());
      /*
       * 根据店号,配送公司编号,地域编号
       * 循环删除delivery_region表,delivery_region_appointed_time表,delivery_region_charge表中相关联的记录
       */
      for (String deleteQuery : ShopDeleteQuery.getDeliveryRegionDeleteQueryTmall()) {
        manager.executeUpdate(deleteQuery, deliveryRegion.getShopCode(), deliveryRegion.getDeliveryCompanyNo(), deliveryRegion.getPrefectureCode());
      }
      
      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return serviceResult;
  }
  
  
  public ServiceResult deleteDeliveryRegion(DeliveryRegion deliveryRegion) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(getLoginInfo());
      /*
       * 根据店号,配送公司编号,地域编号
       * 循环删除delivery_region表,delivery_region_appointed_time表,delivery_region_charge表中相关联的记录
       */
      for (String deleteQuery : ShopDeleteQuery.getDeliveryRegionDeleteQuery()) {
        manager.executeUpdate(deleteQuery, deliveryRegion.getShopCode(), deliveryRegion.getDeliveryCompanyNo(), deliveryRegion.getPrefectureCode());
      }
      
      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return serviceResult;
  }
  
  public ServiceResult deleteDeliveryRegionJd(JdDeliveryRegion deliveryRegion) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(getLoginInfo());
      /*
       * 根据店号,配送公司编号,地域编号
       * 循环删除jd_delivery_region表,delivery_region_appointed_time表,delivery_region_charge表中相关联的记录
       */
      for (String deleteQuery : ShopDeleteQuery.getDeliveryRegionDeleteQueryJd()) {
        manager.executeUpdate(deleteQuery, deliveryRegion.getShopCode(), deliveryRegion.getDeliveryCompanyNo(), deliveryRegion.getPrefectureCode());
      }
      
      manager.commit();
    } catch (ConcurrencyFailureException e) {
      manager.rollback();
      throw e;
    } catch (RuntimeException e) {
      manager.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return serviceResult;
  }

  
  public Prefecture getPrefectureInfo(String prefectureCode) {
    PrefectureDao prefectureDao = DIContainer.getDao(PrefectureDao.class);
    Prefecture prefecture = prefectureDao.load(prefectureCode);
    return prefecture;
  }
  
  public JdPrefecture getPrefectureInfoJd(String prefectureCode) {
    JdPrefectureDao prefectureDao = DIContainer.getDao(JdPrefectureDao.class);
    JdPrefecture prefecture = prefectureDao.load(prefectureCode);
    return prefecture;
  }
  
  public DeliveryRelatedInfo getDeliveryRelatedInfo(String deliveryRelatedInfoNo) {
    DeliveryRelatedInfoDao dao = DIContainer.getDao(DeliveryRelatedInfoDao.class);
    DeliveryRelatedInfo info = dao.load(deliveryRelatedInfoNo);
    return info;
  }
  
  public JdDeliveryRelatedInfo getDeliveryRelatedInfoJd(String deliveryRelatedInfoNo) {
    JdDeliveryRelatedInfoDao dao = DIContainer.getDao(JdDeliveryRelatedInfoDao.class);
    JdDeliveryRelatedInfo info = dao.load(deliveryRelatedInfoNo);
    return info;
  }
  
  public TmallDeliveryRelatedInfo getDeliveryRelatedInfoTmall(String deliveryRelatedInfoNo) {
    TmallDeliveryRelatedInfoDao dao = DIContainer.getDao(TmallDeliveryRelatedInfoDao.class);
    TmallDeliveryRelatedInfo info = dao.load(deliveryRelatedInfoNo);
    return info;
  }

/* (non-Javadoc)
 * @see jp.co.sint.webshop.service.ShopManagementService#getShippingCharge(java.lang.String, java.math.BigDecimal, java.math.BigDecimal)
 */
@Override
public BigDecimal getShippingCharge(String prefectureCode,
		BigDecimal totalOrderPrice, BigDecimal totalCommodityWeight, String deliveryCompanyNo) {
	  DeliveryRegionChargeDao dao = DIContainer.getDao(DeliveryRegionChargeDao.class);
    DeliveryRegionCharge charge = dao.load(prefectureCode, deliveryCompanyNo);
    BigDecimal shippingCharege = BigDecimal.ZERO;
    BigDecimal oldTotalOrderPrice = totalOrderPrice;
    BigDecimal oldTotalCommodityWeight = totalCommodityWeight;
    if (charge == null) {
  	  return BigDecimal.ZERO;
    } else {
      // 不管如何购买,都免运费
      if ( charge.getFreeOrderAmount().compareTo(BigDecimal.ZERO) == 0 
            && charge.getFreeWeight().compareTo(BigDecimal.ZERO) != 0 ) {
        return BigDecimal.ZERO;
      }
      
      if (!(charge.getFreeOrderAmount().compareTo(BigDecimal.ZERO) == 0)
          && !(charge.getFreeWeight().compareTo(BigDecimal.ZERO) == 0)) {
        BigDecimal freeOrderAmount = charge.getFreeOrderAmount();
        BigDecimal freeWeight = charge.getFreeWeight();

        while (totalOrderPrice.compareTo(freeOrderAmount) >= 0) {
          totalCommodityWeight = totalCommodityWeight.subtract(freeWeight);
          totalOrderPrice = totalOrderPrice.subtract(freeOrderAmount);
        }
        if (totalCommodityWeight.compareTo(BigDecimal.ZERO) <= 0) {
          return BigDecimal.ZERO;
        } else {
          totalOrderPrice = oldTotalOrderPrice;
          totalCommodityWeight = oldTotalCommodityWeight;
        }
      }
      // 如果免运费的订单金额为0，运费为0
      //if (charge.getFreeOrderAmount().compareTo(BigDecimal.ZERO) == 0) {
      //return BigDecimal.ZERO;
      //}
      if (totalOrderPrice.compareTo(charge.getOrderAmount()) < 0) {
        shippingCharege = charge.getDeliveryChargeSmall();
      } else {
        shippingCharege = charge.getDeliveryChargeBig();
      }
      
      // 运费逻辑修正start
      // 判断购买商品金额是否大于免运费金额，如果大于计算出倍数
      // 倍数
      int countInt;
      if (charge.getFreeOrderAmount().compareTo(BigDecimal.ZERO) == 0) {
        countInt = 0;
      } else {
        BigDecimal countDecimal = totalOrderPrice.divide(charge.getFreeOrderAmount());
        countInt = countDecimal.intValue();
      }
      // 如果倍数<=0按照正常续重收取运费
      if (countInt <= 0) {
        if (totalCommodityWeight.compareTo(charge.getDeliveryWeight()) > 0 && charge.getAddWeight().compareTo(BigDecimal.ZERO) > 0) {
          BigDecimal count = totalCommodityWeight.subtract(charge.getDeliveryWeight()).divide(charge.getAddWeight());
          int count1;
          count1 = count.intValue();
          if (count.subtract(new BigDecimal(count1)).compareTo(BigDecimal.ZERO) > 0) {
            count1 = count1 + 1;
          }
          shippingCharege = shippingCharege.add(charge.getAddCharge().multiply(new BigDecimal(count1)));
        }
      } else {
        // 计算出可以免收运费的商品重量
        BigDecimal countCharge = charge.getFreeWeight().multiply(new BigDecimal(countInt));
        // 减去免收运费的商品重量再根据正常续重收取运费
        if (totalCommodityWeight.subtract(countCharge).compareTo(BigDecimal.ZERO) > 0
            && charge.getAddWeight().compareTo(BigDecimal.ZERO) > 0) {
          BigDecimal count = totalCommodityWeight.subtract(countCharge).divide(charge.getAddWeight());
          int count1;
          count1 = count.intValue();
          if (count.subtract(new BigDecimal(count1)).compareTo(BigDecimal.ZERO) > 0) {
            count1 = count1 + 1;
          }
          shippingCharege = charge.getAddCharge().multiply(new BigDecimal(count1));
        }
      }
      // 运费逻辑修正end
    }
    return shippingCharege;
}
  
  //soukai add 2011/12/20 ob shb end

  // 20120113 shen add start
  public DeliveryRegionCharge getDeliveryRegionCharge(String shopCode, String prefectureCode, String deliveryCompanyNo) {
    DeliveryRegionChargeDao dao = DIContainer.getDao(DeliveryRegionChargeDao.class);
    return dao.load(prefectureCode, deliveryCompanyNo);
  }
  
  public Area getArea(String areaCode) {
    AreaDao dao = DIContainer.getDao(AreaDao.class);
    return dao.load(areaCode);
  }
  // 20120113 shen add end
}
