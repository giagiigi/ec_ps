package jp.co.sint.webshop.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.MobileDomain;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.PointInsertProcedure;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.StoredProceduedResultType;
import jp.co.sint.webshop.data.StoredProcedureResult;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.CartHistoryDao;
import jp.co.sint.webshop.data.dao.CommodityDetailDao;
import jp.co.sint.webshop.data.dao.CompanyCustomerDao;
import jp.co.sint.webshop.data.dao.CustomerAddressDao;
import jp.co.sint.webshop.data.dao.CustomerAttributeChoiceDao;
import jp.co.sint.webshop.data.dao.CustomerAttributeDao;
import jp.co.sint.webshop.data.dao.CustomerCouponDao;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.CustomerGroupDao;
import jp.co.sint.webshop.data.dao.FavoriteCommodityDao;
import jp.co.sint.webshop.data.dao.InquiryDetailDao;
import jp.co.sint.webshop.data.dao.InquiryHeaderDao;
import jp.co.sint.webshop.data.dao.NewCouponHistoryDao;
import jp.co.sint.webshop.data.dao.ReminderDao;
import jp.co.sint.webshop.data.dao.WebDiagnosisHeaderDao;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.domain.UseStatus;
import jp.co.sint.webshop.data.dto.CartHistory;
import jp.co.sint.webshop.data.dto.CompanyCustomer;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerMessage;
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.data.dto.MobileAuth;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.data.dto.WebDiagnosisHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.campain.CampainQuery;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchQuery;
import jp.co.sint.webshop.service.customer.CartHistoryQuery;
import jp.co.sint.webshop.service.customer.CouponStatusAllInfo;
import jp.co.sint.webshop.service.customer.CouponStatusAllQuery;
import jp.co.sint.webshop.service.customer.CouponStatusListSearchCondition;
import jp.co.sint.webshop.service.customer.CouponStatusQuery;
import jp.co.sint.webshop.service.customer.CouponStatusSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerAddressQuery;
import jp.co.sint.webshop.service.customer.CustomerAttributeQuery;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerCouponInfo;
import jp.co.sint.webshop.service.customer.CustomerCouponQuery;
import jp.co.sint.webshop.service.customer.CustomerGroupCount;
import jp.co.sint.webshop.service.customer.CustomerGroupQuery;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.customer.CustomerPointInfo;
import jp.co.sint.webshop.service.customer.CustomerRecommendSummaryProcedure;
import jp.co.sint.webshop.service.customer.CustomerRegisterCountSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerRegisterCountSearchQuery;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.service.customer.CustomerSearchQuery;
import jp.co.sint.webshop.service.customer.CustomerWithdrawalQuery;
import jp.co.sint.webshop.service.customer.DeleteExpiredPointProcedure;
import jp.co.sint.webshop.service.customer.DeliveryHistoryInfo;
import jp.co.sint.webshop.service.customer.DeliveryHistoryQuery;
import jp.co.sint.webshop.service.customer.DeliveryHistorySearchCondition;
import jp.co.sint.webshop.service.customer.InquiryCountQuery;
import jp.co.sint.webshop.service.customer.InquiryDetailQuery;
import jp.co.sint.webshop.service.customer.InquiryInfo;
import jp.co.sint.webshop.service.customer.InquirySearchCondition;
import jp.co.sint.webshop.service.customer.InquirySearchInfo;
import jp.co.sint.webshop.service.customer.InquirySearchQuery;
import jp.co.sint.webshop.service.customer.MemberCouponHistory;
import jp.co.sint.webshop.service.customer.MemberCouponHistoryQuery;
import jp.co.sint.webshop.service.customer.MemberInquiryHistory;
import jp.co.sint.webshop.service.customer.MemberInquiryHistoryQuery;
import jp.co.sint.webshop.service.customer.MemberSearchCondition;
import jp.co.sint.webshop.service.customer.MemberSearchInfo;
import jp.co.sint.webshop.service.customer.MemberSearchQuery;
import jp.co.sint.webshop.service.customer.MemberShippingHistory;
import jp.co.sint.webshop.service.customer.MemberShippingHistoryQuery;
import jp.co.sint.webshop.service.customer.OverdueCouponProcedure;
import jp.co.sint.webshop.service.customer.OwnerCardDetail;
import jp.co.sint.webshop.service.customer.PasswordReminderQuery;
import jp.co.sint.webshop.service.customer.PointHistoryQuery;
import jp.co.sint.webshop.service.customer.PointStatusAllQuery;
import jp.co.sint.webshop.service.customer.PointStatusAllSearchInfo;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.service.customer.PointStatusShopQuery;
import jp.co.sint.webshop.service.customer.PointStatusShopSearchInfo;
import jp.co.sint.webshop.service.event.CustomerEvent;
import jp.co.sint.webshop.service.event.CustomerEventType;
import jp.co.sint.webshop.service.event.CustomerListener;
import jp.co.sint.webshop.service.event.ServiceEventHandler;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

/**
 * SI Web Shopping 10 顧客サービス(CustomerService)
 * 
 * @author System Integrator Corp.
 */
public class CustomerServiceImpl extends AbstractServiceImpl implements CustomerService {

  private static final long serialVersionUID = 1L;

  private List<CustomerListener> customerListeners;

  public void setCustomerListeners(List<CustomerListener> listeners) {
    this.customerListeners = listeners;
  }

  public ServiceResult deleteCustomerAddress(String customerCode, Long addressNo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 顧客存在チェック
    if (isWithdrawedCustomer(customerCode)) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CustomerAddressDao addrDao = DIContainer.getDao(CustomerAddressDao.class);
    CustomerAddress address = addrDao.load(customerCode, addressNo);

    // 削除対象データが存在しない場合はエラー

    if (address == null) {
      serviceResult.addServiceError(CustomerServiceErrorContent.ADDRESS_DELETED_ERROR);
      return serviceResult;
    }
    addrDao.delete(customerCode, addressNo);

    return serviceResult;
  }

  public ServiceResult deleteCustomerGroup(String customerGroupCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    CustomerGroupDao dao = DIContainer.getDao(CustomerGroupDao.class);
    // デフォルトの顧客グループは削除不可

    if (customerGroupCode.equals("0")) {
      serviceResult.addServiceError(CustomerServiceErrorContent.DEFAULT_CUSTOMERGROUP_DELETED_ERROR);
      return serviceResult;
    }

    // 削除対象データが存在しない場合はエラー

    CustomerGroupCount list = getCustomerGroup(customerGroupCode);
    if (list == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // 顧客グループに対して会員が登録されている場合はエラーとする

    if (list.getMemberShip() > 0) {
      serviceResult.addServiceError(CustomerServiceErrorContent.REGISTERD_CUSTOMERGROUP_DELETED_ERROR);
      return serviceResult;
    }

    dao.delete(customerGroupCode);
    return serviceResult;
  }

  public ServiceResult deleteIneffectivePointHistory(String customerCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 削除対象顧客が存在しない場合はエラー
    CustomerInfo info = getCustomer(customerCode);

    if (info.getCustomer() == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // ポイントシステム使用チェック
    PointRule pointRule = CommonLogic.getPointRule(getLoginInfo());
    if (pointRule.getPointFunctionEnabledFlg().equals(NumUtil.toLong(PointFunctionEnabledFlg.DISABLED.getValue()))) {
      serviceResult.addServiceError(CustomerServiceErrorContent.POINT_SYSTEM_DISABLED_ERROR);
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(PointHistoryQuery.DELETE_POINT_HISTORY_QUERY, customerCode);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (Exception e) {
      txMgr.rollback();
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public SearchResult<CustomerSearchInfo> findCustomer(CustomerSearchCondition condition) {

    CustomerSearchQuery query = new CustomerSearchQuery(condition, CustomerSearchQuery.LOAD_CUSTOMER_QUERY);

    return DatabaseUtil.executeSearch(query);
  }

  public List<CustomerAttribute> getAttributeList() {
    Query query = new SimpleQuery(CustomerAttributeQuery.LOAD_ATTRIBUTE_ALL_QUERY);
    return DatabaseUtil.loadAsBeanList(query, CustomerAttribute.class);
  }

  public CustomerInfo getCustomer(String customerCode) {
    // 10.1.4 10170 修正 ここから
    // CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    // CustomerAddressDao addrDao = DIContainer.getDao(CustomerAddressDao.class);
    //
    // Customer cust = dao.load(customerCode);
    // CustomerAddress addr = addrDao.load(customerCode, 0L);
    //
    // CustomerInfo info = new CustomerInfo();
    // info.setCustomer(cust);
    // info.setAddress(addr);
    //
    // return info;
    return getCustomer(customerCode, false);
    // 10.1.4 10170 修正 ここまで
  }
  // 20120112 ysy add start
	public String calculateOrederNo(String customerCode, String commodityCode) {
		Query query = new SimpleQuery(CustomerSearchQuery.LOAD_COUNT_ORDER_NO_QUERY, customerCode,
				commodityCode);
		Long calculat = DatabaseUtil.executeScalar(query, Long.class);
		return NumUtil.toString(calculat);
	}
  // 20120112 ysy add end 
  // 10.1.4 10170 追加 ここから
  public CustomerInfo getCustomer(String customerCode, boolean isPointRecalculate) {
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    CustomerAddressDao addrDao = DIContainer.getDao(CustomerAddressDao.class);

    Customer cust = dao.load(customerCode);
    CustomerAddress addr = addrDao.load(customerCode, 0L);

    // 顧客のポイントがオーバーフローしている可能性があるとき、ポイント履歴から

    // 実際のポイントを計算してセットする

    if (isPointRecalculate) {
      //modify by V10-CH 170 start
      //if (cust.getRestPoint() == 99999999L) {
      if (cust.getRestPoint() != null && BigDecimalUtil.equals(cust.getRestPoint() , new BigDecimal(99999999.99))) {
      //modify by V10-CH 170 END
        Query query = new SimpleQuery(PointHistoryQuery.LOAD_ISSUED_POINT_SUMMARY,
            customerCode, PointIssueStatus.ENABLED.getValue());
        //modify by V10-CH 170 start
//        Long sumOfPointHistory = DatabaseUtil.executeScalar(query, Long.class);
        //cust.setRestPoint(sumOfPointHistory);
        BigDecimal sumOfPointHistory = DatabaseUtil.executeScalar(query, BigDecimal.class);
        cust.setRestPoint(sumOfPointHistory);
        //modify by V10-CH 170 end
      }
    }
    CustomerInfo info = new CustomerInfo();
    info.setCustomer(cust);
    info.setAddress(addr);

    return info;
  }
  // 10.1.4 10170 追加 ここまで

  public CustomerAddress getCustomerAddress(String customerCode, Long addressNo) {
    CustomerAddressDao addrDao = DIContainer.getDao(CustomerAddressDao.class);
    CustomerAddress address = addrDao.load(customerCode, addressNo);

    return address;
  }

  public List<CustomerAddress> getCustomerAddressList(String customerCode) {
    CustomerAddressDao addrDao = DIContainer.getDao(CustomerAddressDao.class);

    return addrDao.findByQuery(CustomerAddressQuery.LOAD_ALL_QUERY, customerCode);
  }

  public SearchResult<CustomerAddress> getCustomerAddressList(CustomerSearchCondition condition) {
    CustomerAddressQuery query = new CustomerAddressQuery(condition);

    return DatabaseUtil.executeSearch(query);
  }

  public List<CustomerAttributeAnswer> getCustomerAttributeAnswer(CustomerSearchCondition condition) {
    Query query = new SimpleQuery(CustomerAttributeQuery.LOAD_CUSTOMER_ATTRIBUTE_ANSWER_ALL_QUERY, condition
        .getCustomerAttributeNo(), condition.getCustomerCode());
    return DatabaseUtil.loadAsBeanList(query, CustomerAttributeAnswer.class);
  }

  public List<CustomerGroupCount> getCustomerGroup() {
    Query query = new SimpleQuery(CustomerGroupQuery.LOAD_ALL_QUERY);
    return DatabaseUtil.loadAsBeanList(query, CustomerGroupCount.class);
  }

  public ServiceResult insertCustomer(CustomerInfo customerInfo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 顧客属性存在チェック

    List<CustomerAttribute> getAttributeList = getAttributeList();

    for (CustomerAttributeAnswer answer : customerInfo.getAnswerList()) {
      boolean exitResult = false;
      for (CustomerAttribute ca : getAttributeList) {
        List<CustomerAttributeChoice> getChoiceList = getAttributeChoiceList(Long.toString(ca.getCustomerAttributeNo()));
        for (CustomerAttributeChoice cac : getChoiceList) {
          if (answer.getCustomerAttributeNo().equals(cac.getCustomerAttributeNo())
              && answer.getCustomerAttributeChoicesNo().equals(cac.getCustomerAttributeChoicesNo())) {
            exitResult = true;
          }
        }
      }
      if (!exitResult) {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
    }

    // メールアドレス重複エラー
    if (!isAvailableEmailInsert(customerInfo.getCustomer().getEmail())) {
      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR);
      return serviceResult;
    }
    customerInfo.getCustomer().setLoginId(customerInfo.getCustomer().getEmail());

    // ログインID重複エラー：将来的にメールアドレスとログインIDを別々に使用する可能性もあるので、ログインID重複チェックを別に実行

    if (!isAvailableLoginIdInsert(customerInfo.getCustomer().getLoginId())) {
      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR);
      return serviceResult;
    }

    // メール区分を設定
    customerInfo.getCustomer().setClientMailType(getClientMailType(customerInfo.getCustomer().getEmail()));
    setUserStatus(customerInfo.getCustomer());
    setUserStatus(customerInfo.getAddress());

    // パスワードをハッシュ化

    customerInfo.getCustomer().setPassword(PasswordUtil.getDigest(customerInfo.getCustomer().getPassword()));

    // 顧客属性回答が存在する場合システム日付を設定

    if (customerInfo.getAnswerList().size() > 0) {
      customerInfo.getCustomer().setCustomerAttributeReplyDate(DateUtil.fromString(DateUtil.getSysdateString()));
    }
    // 顧客コード取得

    customerInfo.getCustomer().setCustomerCode(CommonLogic.generateCustomerCode());
    customerInfo.getAddress().setCustomerCode(customerInfo.getCustomer().getCustomerCode());

    // Validationチェック
    ValidationSummary validateCustomer = BeanValidator.validate(customerInfo.getCustomer());
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }
    ValidationSummary validateAddress = BeanValidator.validate(customerInfo.getAddress());
    if (validateAddress.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateAddress.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    // 顧客属性

    List<CustomerAttributeAnswer> answers = customerInfo.getAnswerList();
    for (CustomerAttributeAnswer as : answers) {
      as.setCustomerCode(customerInfo.getCustomer().getCustomerCode());
      setUserStatus(as);
      ValidationSummary validateAnswer = BeanValidator.validate(as);
      if (validateAnswer.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : validateAnswer.getErrorMessages()) {
          logger.debug(rs);
        }
        return serviceResult;
      }
    }

    // ポイント履歴
    PointRule pointRule = CommonLogic.getPointRule(getLoginInfo());
    PointHistory point = new PointHistory();

    long usePoint = Long.parseLong(PointFunctionEnabledFlg.ENABLED.getValue());
    if (pointRule.getPointFunctionEnabledFlg().equals(usePoint) 
        && ValidatorUtil.moreThan(pointRule.getCustomerRegisterPoint(), BigDecimal.ZERO)) {
      //modify by V10-CH start
      //point.setIssuedPoint(pointRule.getCustomerRegisterPoint());
      point.setIssuedPoint(pointRule.getCustomerRegisterPoint());
      //modify by V10-CH end
      point.setCustomerCode(customerInfo.getCustomer().getCustomerCode());
      point.setPointIssueDatetime(DateUtil.fromString(DateUtil.getSysdateString()));
      point.setPointHistoryId((DatabaseUtil.generateSequence(SequenceType.POINT_HISTORY_ID)));
      point.setDescription(CustomerConstant.POINT_DESCRIPTION);
      point.setPointIssueStatus(Long.parseLong(PointIssueStatus.ENABLED.getValue()));
      point.setPointIssueType(Long.parseLong(PointIssueType.MEMBER.getValue()));
      point.setShopCode(DIContainer.getWebshopConfig().getSiteShopCode());
      setUserStatus(point);

      // Validationチェック
      ValidationSummary validatePoint = BeanValidator.validate(point);
      if (validatePoint.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : validatePoint.getErrorMessages()) {
          logger.debug(rs);
        }
        return serviceResult;
      }
    } else {
      customerInfo.getCustomer().setRestPoint(BigDecimal.ZERO);
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 顧客情報登録
      txMgr.insert(customerInfo.getCustomer());
      // アドレス帳登録

      txMgr.insert(customerInfo.getAddress());
      // 顧客属性回答登録

      for (CustomerAttributeAnswer insert : answers) {
        txMgr.insert(insert);
      }
      // ポイント履歴
      if (pointRule.getPointFunctionEnabledFlg().equals(usePoint) 
          && ValidatorUtil.moreThan(pointRule.getCustomerRegisterPoint(), BigDecimal.ZERO)) {
        PointInsertProcedure pointInsert = new PointInsertProcedure(point);
        txMgr.executeProcedure(pointInsert);
      }
      txMgr.commit();
      // 顧客情報を再取得(最終ポイント獲得日取得)
      performCustomerEvent(new CustomerEvent(getCustomer(customerInfo.getCustomer().getCustomerCode()).getCustomer()),
          CustomerEventType.ADDED);
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  /*
   *  
   *  add by os012 20111213 start
   */
  public ServiceResult insertCustomers(CustomerInfo customerInfo) {
	    ServiceResultImpl serviceResult = new ServiceResultImpl();

	    
	    // メールアドレス重複エラー
	    if (!isAvailableEmailInsert(customerInfo.getCustomer().getEmail())) {
	      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR);
	      return serviceResult;
	    }
	    customerInfo.getCustomer().setLoginId(customerInfo.getCustomer().getEmail());

	    // ログインID重複エラー：将来的にメールアドレスとログインIDを別々に使用する可能性もあるので、ログインID重複チェックを別に実行

	    if (!isAvailableLoginIdInsert(customerInfo.getCustomer().getLoginId())) {
	      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR);
	      return serviceResult;
	    }

	    // メール区分を設定
	    customerInfo.getCustomer().setClientMailType(getClientMailType(customerInfo.getCustomer().getEmail()));
	    setUserStatus(customerInfo.getCustomer()); 

	    // パスワードをハッシュ化

	    customerInfo.getCustomer().setPassword(PasswordUtil.getDigest(customerInfo.getCustomer().getPassword()));

	    // 顧客属性回答が存在する場合システム日付を設定

	    if (customerInfo.getAnswerList().size() > 0) {
	      customerInfo.getCustomer().setCustomerAttributeReplyDate(DateUtil.fromString(DateUtil.getSysdateString()));
	    }
	    // 顧客コード取得

	    customerInfo.getCustomer().setCustomerCode(CommonLogic.generateCustomerCode());
	 
	    // Validationチェック
	    ValidationSummary validateCustomer = BeanValidator.validate(customerInfo.getCustomer());
	    if (validateCustomer.hasError()) {
	      Logger logger = Logger.getLogger(this.getClass());
	      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
	      for (String rs : validateCustomer.getErrorMessages()) {
	        logger.debug(rs);
	      }
	      return serviceResult;
	    } 
	    TransactionManager txMgr = DIContainer.getTransactionManager();
	    try {
	      txMgr.begin(getLoginInfo());
	      // 顧客情報登録
	      txMgr.insert(customerInfo.getCustomer());
	    
	      txMgr.commit();
	      // 顧客情報を再取得(最終ポイント獲得日取得)
	      performCustomerEvent(new CustomerEvent(getCustomer(customerInfo.getCustomer().getCustomerCode()).getCustomer()),
	          CustomerEventType.ADDED);
	    } catch (ConcurrencyFailureException e) {
	      txMgr.rollback();
	      throw e;
	    } catch (RuntimeException e) {
	      txMgr.rollback();
	      Logger logger = Logger.getLogger(this.getClass());
	      logger.error(e);
	    } finally {
	      txMgr.dispose();
	    }
	    return serviceResult;
	  } 
  //--add by os012 20111213 end
  
  public ServiceResult insertCustomerAddress(CustomerAddress address) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 顧客存在チェック
    if (noAliveCustomer(address.getCustomerCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CustomerAddressDao addrDao = DIContainer.getDao(CustomerAddressDao.class);

    // アドレス帳番号取得
    //20120116 added by wjw  start
    if (addrDao.exists(address.getCustomerCode(),0L)) {
      Long seq = DatabaseUtil.generateSequence(SequenceType.ADDRESS_NO);
      address.setAddressNo(seq);
    } else {
      address.setAddressNo(CustomerConstant.SELF_ADDRESS_NO);
    }
    //20120116 added by wjw  end
    setUserStatus(address);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(address).getErrors();

    if (resultList.size() > 0) {
      // ServiceResultにValidationチェックエラーが発生した情報を設定

      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        // 詳細なエラー内容はログにDebugで出力

        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }
    addrDao.insert(address, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult insertCustomerGroup(CustomerGroup customerGroup) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    CustomerGroupDao dao = (CustomerGroupDao) DIContainer.getDao(CustomerGroupDao.class);

    setUserStatus(customerGroup);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(customerGroup).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 登録済みデータの場合はエラーとする。

    if (getCustomerGroup(customerGroup.getCustomerGroupCode()) != null) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMERGROUP_REGISTERED_ERROR);
      return serviceResult;
    }
    dao.insert(customerGroup, getLoginInfo());
    return serviceResult;
  }

  public ServiceResult insertPointHistory(PointHistory history, Date updatedDatetime) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // ポイントシステム使用チェック
    PointRule pointRule = CommonLogic.getPointRule(getLoginInfo());
    if (pointRule.getPointFunctionEnabledFlg().equals(NumUtil.toLong(PointFunctionEnabledFlg.DISABLED.getValue()))) {
      serviceResult.addServiceError(CustomerServiceErrorContent.POINT_SYSTEM_DISABLED_ERROR);
      return serviceResult;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();

    history.setPointHistoryId((DatabaseUtil.generateSequence(SequenceType.POINT_HISTORY_ID)));

    setUserStatus(history);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(history).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 現在保有ポイントがマイナスになる場合はエラーにする

    CustomerInfo info = getCustomer(history.getCustomerCode());
    //modify by V10-CH 170 start
//    if (info.getCustomer().getRestPoint() + history.getIssuedPoint() < 0) {
    //modify by os012 20111213 start
    BigDecimal pointSum  =history.getIssuedPoint();
     if(info.getCustomer().getRestPoint()!=null)
     {
    	   pointSum = BigDecimalUtil.add(info.getCustomer().getRestPoint(), history.getIssuedPoint());
     }
   //modify by os012 20111213 end
    if (ValidatorUtil.lessThan(pointSum, BigDecimal.ZERO)) { 
    //modify by V10-CH 170 end
      serviceResult.addServiceError(CustomerServiceErrorContent.REST_POINT_MINUS_ERROR);
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());
      // ポイント更新
      PointInsertProcedure pointInsert = new PointInsertProcedure(history);
      txMgr.executeProcedure(pointInsert);

      // ポイントのオーバーフロー時処理

      if (pointInsert.getResult() == CustomerConstant.POINT_OVERFLOW_CODE) {
        txMgr.rollback();
        serviceResult.addServiceError(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR);
        return serviceResult;
      }

      txMgr.commit();
      CommonLogic.verifyPointDifference(history.getCustomerCode(), PointIssueStatus.ENABLED); // 10.1.3 10174 追加
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CustomerServiceErrorContent.POINT_INSERT_FAILURE_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  public ServiceResult updateCustomer(CustomerInfo customerInfo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // メールアドレス重複エラー
    if (!isAvailableEmailUpdate(customerInfo.getCustomer().getCustomerCode(), customerInfo.getCustomer().getEmail())) {
      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR);
      return serviceResult;
    }
    customerInfo.getCustomer().setLoginId(customerInfo.getCustomer().getEmail());
    // ログインID重複エラー

    // 将来的にメールアドレスとログインIDを別々に使用する可能性もあるので、ログインID重複チェックを別に実行

    if (!isAvailableLoginIdUpdate(customerInfo.getCustomer().getCustomerCode(), customerInfo.getCustomer().getLoginId())) {
      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR);
      return serviceResult;
    }

    // 顧客削除済みエラー

    if (isWithdrawedCustomer(customerInfo.getCustomer().getCustomerCode())) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
      return serviceResult;
    }

    CustomerInfo orgInfo = getCustomer(customerInfo.getCustomer().getCustomerCode());
    // アドレス帳削除済みエラー
    if (orgInfo.getAddress() == null) {
      serviceResult.addServiceError(CustomerServiceErrorContent.ADDRESS_DELETED_ERROR);
      return serviceResult;
    }

    // 顧客属性存在チェック

    List<CustomerAttribute> getAttributeList = getAttributeList();

    for (CustomerAttributeAnswer answer : customerInfo.getAnswerList()) {
      boolean exitResult = false;
      for (CustomerAttribute ca : getAttributeList) {
        List<CustomerAttributeChoice> getChoiceList = getAttributeChoiceList(Long.toString(ca.getCustomerAttributeNo()));
        for (CustomerAttributeChoice cac : getChoiceList) {
          if (answer.getCustomerAttributeNo().equals(cac.getCustomerAttributeNo())
              && answer.getCustomerAttributeChoicesNo().equals(cac.getCustomerAttributeChoicesNo())) {
            exitResult = true;
          }
        }
      }
      if (!exitResult) {
        serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return serviceResult;
      }
    }

    // メール区分を設定
    customerInfo.getCustomer().setClientMailType(getClientMailType(customerInfo.getCustomer().getEmail()));
    // 退会依頼データを設定

    if (customerInfo.getCustomer().getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue())) {
      customerInfo.getCustomer().setWithdrawalRequestDate(DateUtil.fromString(DateUtil.getSysdateString()));
    } else if (customerInfo.getCustomer().getCustomerStatus().equals(CustomerStatus.MEMBER.longValue())) {
      customerInfo.getCustomer().setWithdrawalRequestDate(null);
    }

    // 顧客属性回答が存在する場合システム日付を設定

    if (customerInfo.getAnswerList().size() > 0) {
      customerInfo.getCustomer().setCustomerAttributeReplyDate(DateUtil.fromString(DateUtil.getSysdateString()));
    }

    setUserStatus(customerInfo.getCustomer());
    setUserStatus(customerInfo.getAddress());

    // Validationチェック
    ValidationSummary validateCustomer = BeanValidator.validate(customerInfo.getCustomer());
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    ValidationSummary validateAddress = BeanValidator.validate(customerInfo.getAddress());
    if (validateAddress.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateAddress.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    List<CustomerAttributeAnswer> answers = customerInfo.getAnswerList();
    for (CustomerAttributeAnswer insert : answers) {
      setUserStatus(insert);

      ValidationSummary validateAnswer = BeanValidator.validate(insert);
      if (validateAnswer.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : validateAnswer.getErrorMessages()) {
          logger.debug(rs);
        }
        return serviceResult;
      }
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      // 顧客情報登録
      txMgr.update(customerInfo.getCustomer());

      // アドレス帳登録

      txMgr.update(customerInfo.getAddress());

      // 顧客属性回答登録

      Query query = new SimpleQuery(CustomerAttributeQuery.LOAD_CUSTOMER_ATTRIBUTE_ANSWER_CUSTOMER_QUERY, customerInfo
          .getCustomer().getCustomerCode());
      List<CustomerAttributeAnswer> deleteAnswers = DatabaseUtil.loadAsBeanList(query, CustomerAttributeAnswer.class);

      for (CustomerAttributeAnswer delete : deleteAnswers) {
        txMgr.delete(delete); // 削除
      }
      for (CustomerAttributeAnswer insert : answers) {
        txMgr.insert(insert); // 新規登録
      }

      txMgr.commit();
      performCustomerEvent(new CustomerEvent(customerInfo.getCustomer()), CustomerEventType.UPDATED);
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }
  /**
   * add by os012 20111213 start 
   * updateCustomerOnly Method just for update Customer table information
   */
  public ServiceResult updateCustomerOnly(CustomerInfo customerInfo) {
	    ServiceResultImpl serviceResult = new ServiceResultImpl();

	    // メールアドレス重複エラー
	    if (!isAvailableEmailUpdate(customerInfo.getCustomer().getCustomerCode(), customerInfo.getCustomer().getEmail())) {
	      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR);
	      return serviceResult;
	    }
	    customerInfo.getCustomer().setLoginId(customerInfo.getCustomer().getEmail());
	    // ログインID重複エラー

	    // 将来的にメールアドレスとログインIDを別々に使用する可能性もあるので、ログインID重複チェックを別に実行

	    if (!isAvailableLoginIdUpdate(customerInfo.getCustomer().getCustomerCode(), customerInfo.getCustomer().getLoginId())) {
	      serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR);
	      return serviceResult;
	    }

	    // 顧客削除済みエラー

	    if (isWithdrawedCustomer(customerInfo.getCustomer().getCustomerCode())) {
	      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
	      return serviceResult;
	    }

	    @SuppressWarnings("unused")
		  CustomerInfo orgInfo = getCustomer(customerInfo.getCustomer().getCustomerCode());
	  

	    // メール区分を設定
	    customerInfo.getCustomer().setClientMailType(getClientMailType(customerInfo.getCustomer().getEmail()));
	    // 退会依頼データを設定
	    
	    if (customerInfo.getCustomer().getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue())) {
	      customerInfo.getCustomer().setWithdrawalRequestDate(DateUtil.fromString(DateUtil.getSysdateString()));
	    } else if (customerInfo.getCustomer().getCustomerStatus().equals(CustomerStatus.MEMBER.longValue())) {
	      customerInfo.getCustomer().setWithdrawalRequestDate(null);
	    }

	    // 顧客属性回答が存在する場合システム日付を設定

	    if (customerInfo.getAnswerList().size() > 0) {
	      customerInfo.getCustomer().setCustomerAttributeReplyDate(DateUtil.fromString(DateUtil.getSysdateString()));
	    } 
	    setUserStatus(customerInfo.getCustomer()); 

	    // Validationチェック
	    ValidationSummary validateCustomer = BeanValidator.validate(customerInfo.getCustomer());
	    if (validateCustomer.hasError()) {
	      Logger logger = Logger.getLogger(this.getClass());
	      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
	      for (String rs : validateCustomer.getErrorMessages()) {
	        logger.debug(rs);
	      }
	      return serviceResult;
	    } 
	    TransactionManager txMgr = DIContainer.getTransactionManager();
	    try {
	      txMgr.begin(getLoginInfo());
	      // 顧客情報登録
	      txMgr.update(customerInfo.getCustomer()); 

	      txMgr.commit();
	      performCustomerEvent(new CustomerEvent(customerInfo.getCustomer()), CustomerEventType.UPDATED);
	    } catch (ConcurrencyFailureException e) {
	      txMgr.rollback();
	      throw e;
	    } catch (RuntimeException e) {
	      txMgr.rollback();
	    } finally {
	      txMgr.dispose();
	    }

	    return serviceResult;
	  }

  public ServiceResult updateCustomerAddress(CustomerAddress address) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 顧客存在チェック
    if (noAliveCustomer(address.getCustomerCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // 本人アドレス帳番号チェック

    // 10.1.3 10150 修正 ここから
    // if (address.getAddressNo().equals(CustomerConstant.SELFE_ADDRESS_NO)) {
    //20120116 del by wjw  start
    //if (address.getAddressNo().equals(CustomerConstant.SELF_ADDRESS_NO)) {
    // 10.1.3 10150 修正 ここまで
    //  serviceResult.addServiceError(CustomerServiceErrorContent.SELF_ADDRESS_UPDATE_ERROR);
    //  return serviceResult;
    //}
    //20120116 del by wjw  end

    CustomerAddressDao addrDao = DIContainer.getDao(CustomerAddressDao.class);
    CustomerAddress orgAddress = addrDao.load(address.getCustomerCode(), address.getAddressNo());

    // アドレス帳削除済みエラー
    if (orgAddress == null) {
      serviceResult.addServiceError(CustomerServiceErrorContent.ADDRESS_NO_DEF_FOUND_ERROR);
      return serviceResult;
    } else {
      address.setOrmRowid(orgAddress.getOrmRowid());
      address.setCreatedUser(orgAddress.getCreatedUser());
      address.setCreatedDatetime(orgAddress.getCreatedDatetime());
      setUserStatus(address);
    }

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(address).getErrors();

    if (resultList.size() > 0) {
      // ServiceResultにValidationチェックエラーが発生した情報を設定

      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        // 詳細なエラー内容はログにDebugで出力

        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }
    addrDao.update(address, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult updateCustomerGroup(CustomerGroup customerGroup) {
    CustomerGroupDao dao = DIContainer.getDao(CustomerGroupDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 更新対象データが存在しない場合はエラーとする
    CustomerGroup sResult = getCustomerGroup(customerGroup.getCustomerGroupCode());
    if (sResult == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CustomerGroup group = dao.load(customerGroup.getCustomerGroupCode());
    group.setCustomerGroupCode(customerGroup.getCustomerGroupCode());
    group.setCustomerGroupName(customerGroup.getCustomerGroupName());
    group.setCustomerGroupPointRate(customerGroup.getCustomerGroupPointRate());
    group.setUpdatedDatetime(customerGroup.getUpdatedDatetime());
    // 20120517 shen add start
    group.setCustomerGroupNameEn(customerGroup.getCustomerGroupNameEn());
    group.setCustomerGroupNameJp(customerGroup.getCustomerGroupNameJp());
    // 20120517 shen add end
    setUserStatus(group);

    // Validationチェック
    ValidationSummary validate = BeanValidator.validate(group);
    if (validate.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validate.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    dao.update(group, getLoginInfo());
    return serviceResult;
  }

  public ServiceResult withdrawalFromMembership(String customerCode, Date updatedDatetime) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 有効受注が存在する

    if (hasActiveOrder(customerCode)) {
      serviceResult.addServiceError(CustomerServiceErrorContent.EXIST_ACTIVE_ORDER_ERROR);
      return serviceResult;
    }

    CustomerDao custDao = DIContainer.getDao(CustomerDao.class);
    Customer customer = custDao.load(customerCode);
//  delete by lc 2012-03-14 start
//    Query addQuery = new SimpleQuery(CustomerAddressQuery.LOAD_ALL_QUERY, customerCode);
//    CustomerAddress address = DatabaseUtil.loadAsBean(addQuery, CustomerAddress.class);
//  delete by lc 2012-03-14 end
    
    // 削除対象データが存在しない、または既に退会済みの場合はエラー
//  upd by lc 2012-03-14 start   
//    if (isWithdrawedCustomer(customerCode) || address == null) {
//  upd by lc 2012-03-14 start
      if (isWithdrawedCustomer(customerCode) ) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
      return serviceResult;
    }

    customer.setUpdatedDatetime(updatedDatetime);
    setUserStatus(customer);

    // メール送信の為、姓、名、Emailを退避

    String firstName = customer.getFirstName();
    String lastName = customer.getLastName();
    String firstNameKana = customer.getFirstNameKana();
    String lastNameKana = customer.getLastNameKana();
    String email = customer.getEmail();

    // 個人データをマスク・変更
    customer.setLastName(CustomerConstant.MASK_DATA);
    customer.setFirstName(CustomerConstant.MASK_DATA);
    customer.setLastNameKana(CustomerConstant.MASK_DATA);
    customer.setFirstNameKana(CustomerConstant.MASK_DATA);
    customer.setLoginId(customer.getCustomerCode() + CustomerConstant.MASK_DATA);
    customer.setEmail(customer.getCustomerCode() + CustomerConstant.MASK_DATA);
    customer.setWithdrawalDate(DateUtil.fromString(DateUtil.getSysdateString()));
    customer.setCustomerStatus(Long.parseLong(CustomerStatus.WITHDRAWED.getValue()));
    customer.setRestPoint(BigDecimal.ZERO);
    customer.setTemporaryPoint(BigDecimal.ZERO);

    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try { // customerCodeに関連付いているデータを更新する
      txMgr.begin(getLoginInfo());

      // 顧客情報を更新

      txMgr.update(customer);
      // アドレス帳を更新
      txMgr.executeUpdate(CustomerWithdrawalQuery.ADDRESS_WITHDRAWAL_QUERY, DateUtil.getSysdate(), customerCode);
      // ポイント履歴を更新

      txMgr.executeUpdate(CustomerWithdrawalQuery.POINT_WITHDRAWAL_QUERY, PointIssueStatus.DISABLED.getValue(), DateUtil
          .getSysdate(), customerCode);
      // 受注ヘッダを更新

      txMgr.executeUpdate(CustomerWithdrawalQuery.ORDER_WITHDRAWAL_QUERY, customer.getEmail(), DateUtil.getSysdate(), customerCode);
      // 出荷ヘッダを更新

      txMgr.executeUpdate(CustomerWithdrawalQuery.SHIPPING_WITHDRAWAL_QUERY, DateUtil.getSysdate(), customerCode);
      // お気に入りを削除
      txMgr.executeUpdate(CustomerWithdrawalQuery.FAVORITE_WITHDRAWAL_QUERY, customerCode);
      // おすすめ商品を削除

      txMgr.executeUpdate(CustomerWithdrawalQuery.RECOMMENDED_WITHDRAWAL_QUERY, customerCode);
      // リマインダー

      txMgr.executeUpdate(CustomerWithdrawalQuery.REMINDER_WITHDRAWAL_QUERY, customerCode);
      // 入荷お知らせ
      txMgr.executeUpdate(CustomerWithdrawalQuery.ARRIVAL_GOODS_WITHDRAWAL_QUERY, customerCode);

      txMgr.commit();

      // 退避したデータを戻す

      customer.setFirstName(firstName);
      customer.setFirstNameKana(firstNameKana);
      customer.setLastName(lastName);
      customer.setLastNameKana(lastNameKana);
      customer.setEmail(email);
      performCustomerEvent(new CustomerEvent(customer), CustomerEventType.WITHDRAWED);
      logger.debug("succeed");
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult withdrawalRequest(String customerCode, Date updatedDatetime) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CustomerDao custDao = DIContainer.getDao(CustomerDao.class);
    Customer customer = custDao.load(customerCode);

    // 削除対象データが存在しない、既に退会依頼済み、または未入金受注が存在する場合はエラー
    if (customer == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    } else if (customer.getCustomerStatus().equals(Long.parseLong(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.getValue()))) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_NOTICE_ERROR);
      return serviceResult;
    } else if (customer.getCustomerStatus().equals(Long.parseLong(CustomerStatus.WITHDRAWED.getValue()))) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
      return serviceResult;
    } else if (hasNotPaymentOrder(customerCode)) {
      serviceResult.addServiceError(CustomerServiceErrorContent.EXIST_NOT_PAYMENT_ORDER_ERROR);
      return serviceResult;
    }

    customer.setUpdatedDatetime(updatedDatetime);
    customer.setWithdrawalRequestDate(DateUtil.fromString(DateUtil.getSysdateString()));

    // 退会依頼データを設定

    customer.setCustomerStatus(Long.parseLong(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.getValue()));
    setUserStatus(customer);

    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try { // customerCodeに関連付いているデータを更新する
      txMgr.begin(getLoginInfo());

      // 顧客データを更新
      txMgr.update(customer);

      txMgr.commit();
      performCustomerEvent(new CustomerEvent(customer), CustomerEventType.WITHDRAWAL_REQUESTED);
      logger.debug("succeed");
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.debug("failed");
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public CustomerGroupCount getCustomerGroup(String customerGroupCode) {
    Query query = new SimpleQuery(CustomerGroupQuery.LOAD_QUERY, customerGroupCode);
    return DatabaseUtil.loadAsBean(query, CustomerGroupCount.class);
  }

  public List<CustomerAttributeChoice> getAttributeChoiceList(String attributeNo) {
    Query query = new SimpleQuery(CustomerAttributeQuery.LOAD_ATTRIBUTE_CHOICE_ALL_QUERY, attributeNo);
    return DatabaseUtil.loadAsBeanList(query, CustomerAttributeChoice.class);
  }

  public CustomerAttribute getAttribute(Long attributeNo) {
    CustomerAttributeDao dao = DIContainer.getDao(CustomerAttributeDao.class);
    return dao.load(attributeNo);
  }

  public ServiceResult insertCustomerAttribute(CustomerAttribute attribute, List<CustomerAttributeChoice> attributeChoice) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      // 顧客属性登録

      Long attributeNo = DatabaseUtil.generateSequence(SequenceType.CUSTOMER_ATTRIBUTE);
      attribute.setCustomerAttributeNo(attributeNo);
      setUserStatus(attribute);

      ValidationSummary validateAttribute = BeanValidator.validate(attribute);
      if (validateAttribute.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String rs : validateAttribute.getErrorMessages()) {
          logger.debug(rs);
        }
        return serviceResult;
      }

      txMgr.insert(attribute);

      // 顧客属性選択肢登録
      for (CustomerAttributeChoice ac : attributeChoice) {
        ac.setCustomerAttributeNo(attributeNo);
        ac.setCustomerAttributeChoicesNo(DatabaseUtil.generateSequence(SequenceType.CUSTOMER_ATTRIBUTE_CHOICE));
        setUserStatus(ac);

        ValidationSummary validateChoice = BeanValidator.validate(ac);
        if (validateChoice.hasError()) {
          Logger logger = Logger.getLogger(this.getClass());
          serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          for (String rs : validateChoice.getErrorMessages()) {
            logger.debug(rs);
          }
          return serviceResult;
        }

        txMgr.insert(ac);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public ServiceResult deleteCustomerAttributeAndChoices(Long attributeNo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(CustomerAttributeQuery.DELETE_ATTRIBUTE_QUERY, attributeNo);
      txMgr.executeUpdate(CustomerAttributeQuery.DELETE_ATTRIBUTE_CHOICES_QUERY, attributeNo);
      txMgr.executeUpdate(CustomerAttributeQuery.DELETE_ATTRIBUTE_CUSTOMER_ATTRIBUTE_ANSWER_ALL_QUERY, attributeNo);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  public CustomerAttributeChoice getAttributeChoice(CustomerAttributeChoice attributeCoice) {
    CustomerAttributeChoiceDao dao = DIContainer.getDao(CustomerAttributeChoiceDao.class);
    return dao.load(attributeCoice.getCustomerAttributeNo(), attributeCoice.getCustomerAttributeChoicesNo());
  }

  public CustomerPointInfo getCustomerPointInfo(String customerCode) {
    Query query = new SimpleQuery(PointHistoryQuery.LOAD_CUSTOMER_POINT_QUERY, customerCode);
    return DatabaseUtil.loadAsBean(query, CustomerPointInfo.class);
  }

  public SearchResult<PointStatusAllSearchInfo> findPointStatusInfo(PointStatusListSearchCondition condition) {
    PointStatusAllQuery query = new PointStatusAllQuery(condition, PointStatusAllQuery.LOAD_POINT_STATUS_QUERY);
    return DatabaseUtil.executeSearch(query);
  }

  public SearchResult<PointStatusShopSearchInfo> findPointStatusShopInfo(PointStatusListSearchCondition condition) {
    PointStatusShopQuery query = new PointStatusShopQuery(condition, PointStatusShopQuery.LOAD_POINT_STATUS_SHOP_QUERY);
    return DatabaseUtil.executeSearch(query);
  }

  public boolean isAvailableEmailUpdate(String customerCode, String checkEmail) {
    boolean result = false;

    Query query = new SimpleQuery(CustomerSearchQuery.CHECK_EMAIL_QUERY_UPDATE, customerCode, checkEmail.toLowerCase());
    Customer resultEmail = DatabaseUtil.loadAsBean(query, Customer.class);

    if (resultEmail == null) {
      result = true;
    }

    return result;
  }

  public boolean isAvailableEmailInsert(String checkEmail) {
    boolean result = false;

    Query query = new SimpleQuery(CustomerSearchQuery.CHECK_EMAIL_QUERY_INSERT, checkEmail.toLowerCase());
    Customer resultEmail = DatabaseUtil.loadAsBean(query, Customer.class);

    if (resultEmail == null) {
      result = true;
    }

    return result;
  }

  private boolean isAvailableLoginIdUpdate(String customerCode, String checkLoginId) {
    boolean result = false;

    Query query = new SimpleQuery(CustomerSearchQuery.CHECK_LOGINID_QUERY_UPDATE, customerCode, checkLoginId);
    Customer resultLoginId = DatabaseUtil.loadAsBean(query, Customer.class);

    if (resultLoginId == null) {
      result = true;
    }

    return result;
  }

  private boolean isAvailableLoginIdInsert(String checkLoginId) {
    boolean result = false;

    Query query = new SimpleQuery(CustomerSearchQuery.CHECK_LOGINID_QUERY_INSERT, checkLoginId);
    Customer resultLoginId = DatabaseUtil.loadAsBean(query, Customer.class);

    if (resultLoginId == null) {
      result = true;
    }

    return result;
  }

  public SearchResult<PointStatusAllSearchInfo> findPointStatusCustomerInfo(PointStatusListSearchCondition condition) {
    PointStatusAllQuery query = new PointStatusAllQuery(condition, PointStatusAllQuery.LOAD_POINT_STATUS_CUSTOMER_QUERY);
    return DatabaseUtil.executeSearch(query);
  }

  public ServiceResult updatePassword(Customer customer) {
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 更新対象データが存在しない/既に退会済み/退会依頼中の顧客の場合はエラーとする

    Customer customerData = dao.load(customer.getCustomerCode());
    if (isWithdrawedCustomer(customer.getCustomerCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // Validationチェック
    ValidationSummary validateCustomer = BeanValidator.partialValidate(customer, "password");
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    // パスワードポリシーチェックを行う
    PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
    if (!policy.isValidPassword(customer.getPassword())) {
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    // パスワードをハッシュ化して格納する

    customerData.setPassword(PasswordUtil.getDigest(customer.getPassword()));
    customerData.setUpdatedDatetime(customer.getUpdatedDatetime());

    dao.update(customerData, getLoginInfo());
    performCustomerEvent(new CustomerEvent(customerData), CustomerEventType.PASSWORD_UPDATED);

    return serviceResult;
  }

  public Reminder getReminderInfo(String token) {
    Query query = new SimpleQuery(PasswordReminderQuery.LOAD_REMINDER_QUERY, token, DIContainer.getWebshopConfig()
        .getReminderTimeLimit());
    return DatabaseUtil.loadAsBean(query, Reminder.class);
  }

  public ServiceResult insertReminder(Reminder reminder) {
    ReminderDao dao = DIContainer.getDao(ReminderDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(reminder);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(reminder).getErrors();

    if (resultList.size() > 0) {
      // ServiceResultにValidationチェックエラーが発生した情報を設定

      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        // 詳細なエラー内容はログにDebugで出力

        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 存在チェックエラー

    if (dao.exists(reminder.getReissuanceKey())) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    // 新規登録
    dao.insert(reminder, getLoginInfo());

    return serviceResult;
  }

  public SearchResult<DeliveryHistoryInfo> findDeliveryHistoryInfo(DeliveryHistorySearchCondition condition) {
    DeliveryHistoryQuery query = new DeliveryHistoryQuery(condition, DeliveryHistoryQuery.LOAD_DELIVERY_HISTORY_QUERY);
    return DatabaseUtil.executeSearch(query);
  }

  public ServiceResult deleteFavoriteCommodity(String customerCode, String shopCode, String skuCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 顧客存在チェック
    if (noAliveCustomer(customerCode)) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    FavoriteCommodityDao dao = DIContainer.getDao(FavoriteCommodityDao.class);
    dao.delete(customerCode, shopCode, skuCode);
    return serviceResult;
  }

  public ServiceResult insertFavoriteCommodity(String customerCode, String shopCode, String skuCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 顧客存在チェック
    if (noAliveCustomer(customerCode)) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
      return serviceResult;
    }

    FavoriteCommodityDao dao = DIContainer.getDao(FavoriteCommodityDao.class);

    FavoriteCommodity favoriteCommodity = new FavoriteCommodity();
    favoriteCommodity.setShopCode(shopCode);
    favoriteCommodity.setSkuCode(skuCode);
    favoriteCommodity.setCustomerCode(customerCode);
    favoriteCommodity.setFavoriteRegisterDate(DateUtil.getSysdate());
    setUserStatus(favoriteCommodity);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(favoriteCommodity).getErrors();

    if (resultList.size() > 0) {
      // ServiceResultにValidationチェックエラーが発生した情報を設定

      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        // 詳細なエラー内容はログにDebugで出力

        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    CommodityDetailDao skuDao = DIContainer.getDao(CommodityDetailDao.class);
    if (!skuDao.exists(shopCode, skuCode)) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    if (dao.exists(customerCode, shopCode, skuCode)) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
    } else {
      dao.insert(favoriteCommodity, getLoginInfo());
    }

    return serviceResult;
  }

  public ServiceResult customerRecommendSummary(String month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    try {
      StoredProcedureResult result = DatabaseUtil.executeProcedure(new CustomerRecommendSummaryProcedure(month, this.getLoginInfo()
          .getRecordingFormat()));

      if (result.getParameters().get("r_result").toString().equals(StoredProceduedResultType.FAILED.getValue())) {
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      }
    } catch (DataAccessException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  public Long getCustomerRegisterCount(CustomerRegisterCountSearchCondition condition) {
    return NumUtil.toLong(DatabaseUtil.executeScalar(new CustomerRegisterCountSearchQuery(condition)).toString());
  }

  public Customer getCustomerByLoginId(String loginId) {
    Query query = new SimpleQuery(CustomerSearchQuery.LOAD_CUSTOMER_QUERY_BY_LOGIN_ID, loginId.toLowerCase(),loginId.toLowerCase());
    return DatabaseUtil.loadAsBean(query, Customer.class);
  }

  public ServiceResult deleteExpiredPoint() {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    try {
      StoredProcedureResult result = DatabaseUtil.executeProcedure(new DeleteExpiredPointProcedure(this.getLoginInfo()
          .getRecordingFormat()));

      if (result.getParameters().get("r_result").toString().equals(StoredProceduedResultType.FAILED.getValue())) {
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      }
    } catch (DataAccessException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  public ServiceResult updateCustomerAttribute(CustomerAttribute attribute) {
    CustomerAttributeDao dao = DIContainer.getDao(CustomerAttributeDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 更新対象データが存在しない場合はエラーとする
    CustomerAttribute sResult = dao.load(attribute.getCustomerAttributeNo());
    if (sResult == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    } else {
      attribute.setOrmRowid(sResult.getOrmRowid());
      attribute.setCreatedUser(sResult.getCreatedUser());
      attribute.setCreatedDatetime(sResult.getCreatedDatetime());
    }

    setUserStatus(attribute);
    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(attribute).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(attribute, getLoginInfo());
    return serviceResult;
  }

  public ServiceResult updateCustomerAttributeChoice(CustomerAttributeChoice attributeChoice) {
    CustomerAttributeChoiceDao dao = DIContainer.getDao(CustomerAttributeChoiceDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 更新対象データが存在しない場合はエラーとする
    CustomerAttributeChoice sResult = getAttributeChoice(attributeChoice);
    if (sResult == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    } else {
      attributeChoice.setOrmRowid(sResult.getOrmRowid());
      attributeChoice.setUpdatedDatetime(sResult.getUpdatedDatetime());
      attributeChoice.setCreatedDatetime(sResult.getCreatedDatetime());
      attributeChoice.setCreatedUser(sResult.getCreatedUser());
    }

    setUserStatus(attributeChoice);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(attributeChoice).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(attributeChoice, getLoginInfo());
    return serviceResult;
  }

  public ServiceResult deleteCustomerAttributeChoice(Long customerAttributeNo, Long customerAttributeChoiceNo) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CustomerAttributeChoiceDao dao = DIContainer.getDao(CustomerAttributeChoiceDao.class);

    // 削除対象データが存在しない場合はエラーとする
    CustomerAttributeChoice sResult = dao.load(customerAttributeNo, customerAttributeChoiceNo);
    if (sResult == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    dao.delete(customerAttributeNo, customerAttributeChoiceNo);

    return serviceResult;
  }

  public ServiceResult insertCustomerAttributeChoice(CustomerAttributeChoice attributeChoice) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    CustomerAttributeChoiceDao dao = DIContainer.getDao(CustomerAttributeChoiceDao.class);
    setUserStatus(attributeChoice);

    // Validationチェック
    attributeChoice.setCustomerAttributeChoicesNo(DatabaseUtil.generateSequence(SequenceType.CUSTOMER_ATTRIBUTE_CHOICE));
    List<ValidationResult> resultList = BeanValidator.validate(attributeChoice).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.insert(attributeChoice, getLoginInfo());
    return serviceResult;
  }

  public boolean isShopCustomer(String customerCode, String shopCode) {
    boolean result = true;

    Query query = new SimpleQuery(CustomerSearchQuery.CHECK_SHOP_CUSTOMER_QUERY, customerCode, shopCode);
    Customer resultCustomerCode = DatabaseUtil.loadAsBean(query, Customer.class);

    if (resultCustomerCode == null) {
      result = false;
    }

    return result;
  }

  public String getCustomerCodeToEmail(String checkEmail) {
    Query query = new SimpleQuery(CustomerSearchQuery.CHECK_EMAIL_QUERY_INSERT, checkEmail);
    Customer resultEmail = DatabaseUtil.loadAsBean(query, Customer.class);

    String customerCode = "";
    if (resultEmail != null) {
      customerCode = resultEmail.getCustomerCode();
    }

    return customerCode;
  }

  public ServiceResult initPassword(Customer customer, String token) {
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 更新対象データが存在しない/既に退会済み/退会依頼中の顧客の場合はエラーとする

    if (noAliveCustomer(customer.getCustomerCode())) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
      return serviceResult;
    }

    // URLパラメータのTOKENが有効かチェック
    if (getReminderInfo(token) == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // Validationチェック
    ValidationSummary validateCustomer = BeanValidator.partialValidate(customer, "password");
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    // パスワードポリシーチェックを行う
    PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
    if (!policy.isValidPassword(customer.getPassword())) {
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    // パスワードをハッシュ化して格納する

    Customer customerData = dao.load(customer.getCustomerCode());
    customerData.setPassword(PasswordUtil.getDigest(customer.getPassword()));
    customerData.setUpdatedDatetime(customer.getUpdatedDatetime());

    setUserStatus(customerData);

    // パスワード更新

    dao.update(customerData, getLoginInfo());

    // リマインダー削除

    ReminderDao reminder = DIContainer.getDao(ReminderDao.class);
    reminder.delete(token);

    performCustomerEvent(new CustomerEvent(customerData), CustomerEventType.PASSWORD_UPDATED);

    return serviceResult;
  }
  
  public ServiceResult initPaymentPassword(Customer customer, String token) {
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 更新対象データが存在しない/既に退会済み/退会依頼中の顧客の場合はエラーとする

    if (noAliveCustomer(customer.getCustomerCode())) {
      serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
      return serviceResult;
    }

    // URLパラメータのTOKENが有効かチェック
    if (getReminderInfo(token) == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // Validationチェック
    ValidationSummary validateCustomer = BeanValidator.partialValidate(customer, "paymentPassword");
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      return serviceResult;
    }

    // パスワードポリシーチェックを行う
    PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
    if (!policy.isValidPassword(customer.getPaymentPassword())) {
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    // パスワードをハッシュ化して格納する

    Customer customerData = dao.load(customer.getCustomerCode());
    customerData.setPaymentPassword(customer.getPaymentPassword());
    customerData.setUpdatedDatetime(customer.getUpdatedDatetime());

    setUserStatus(customerData);

    // パスワード更新

    dao.update(customerData, getLoginInfo());

    // リマインダー削除

    ReminderDao reminder = DIContainer.getDao(ReminderDao.class);
    reminder.delete(token);

    return serviceResult;
  }

  private boolean noAliveCustomer(String customerCode) {
    // 顧客存在チェック
    CustomerInfo customer = getCustomer(customerCode);
    if (customer.getCustomer() == null || customer.getCustomer().getCustomerStatus().equals(CustomerStatus.WITHDRAWED.longValue())
        || customer.getCustomer().getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue())) {
      return true;
    }
    return false;
  }

  private boolean isWithdrawedCustomer(String customerCode) {
    // 顧客存在チェック
    CustomerInfo customer = getCustomer(customerCode);
    boolean customerIsNull = (customer.getCustomer() == null);
    boolean customerIsWithdrawed = !customerIsNull
        && customer.getCustomer().getCustomerStatus().equals(CustomerStatus.WITHDRAWED.longValue());
    if (customerIsNull || customerIsWithdrawed) {
      return true;
    }
    return false;
  }

  public boolean isNotFound(String customerCode) {
    CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);

    return customerDao.load(customerCode) == null;
  }

  public boolean isInactive(String customerCode) {
    CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
    Customer customer = customerDao.load(customerCode);

    if (customer != null
        && (customer.getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue()) || customer
            .getCustomerStatus().equals(CustomerStatus.WITHDRAWED.longValue()))) {
      return true;
    }
    return false;
  }

  public boolean isWithdrawed(String customerCode) {
    CustomerDao customerDao = DIContainer.getDao(CustomerDao.class);
    Customer customer = customerDao.load(customerCode);

    if (customer != null && customer.getCustomerStatus().equals(CustomerStatus.WITHDRAWED.longValue())) {
      return true;
    }
    return false;
  }

  public boolean hasActiveOrder(String customerCode) {
    boolean result = true;

    // 20120320 shen update start
    // Query query = new SimpleQuery(CustomerWithdrawalQuery.ACTIVE_ORDER_QUERY, OrderStatus.CANCELLED.getValue(),
    //     PaymentStatus.NOT_PAID.getValue(), FixedSalesStatus.NOT_FIXED.getValue(), customerCode);
    Query query = new SimpleQuery(CustomerWithdrawalQuery.ACTIVE_ORDER_QUERY, OrderStatus.CANCELLED.getValue(),
        ShippingStatusSummary.SHIPPED_ALL.getValue(), ShippingStatusSummary.CANCELLED.getValue(), customerCode);
    // 20120320 shen update end
    List<OrderHeader> resultOrderNo = DatabaseUtil.loadAsBeanList(query, OrderHeader.class);

    if (resultOrderNo == null || resultOrderNo.size() == 0) {
      result = false;
    }

    return result;
  }

  private Long getClientMailType(String email) {
    MobileDomain domain = DIContainer.get("MobileDomain");
    domain.getClientMailType(email);

    return Long.parseLong(Integer.toString(domain.getClientMailType(email)));
  }

  private void performCustomerEvent(CustomerEvent event, CustomerEventType type) {
    ServiceEventHandler.execute(this.customerListeners, event, type);
  }

  public boolean hasNotPaymentOrder(String customerCode) {
    boolean result = true;

    Query query = new SimpleQuery(CustomerWithdrawalQuery.NOT_PAYMENT_ORDER_QUERY, OrderStatus.CANCELLED.getValue(),
        PaymentStatus.NOT_PAID.getValue(), customerCode);
    List<OrderHeader> resultOrderNo = DatabaseUtil.loadAsBeanList(query, OrderHeader.class);

    if (resultOrderNo == null || resultOrderNo.size() == 0) {
      result = false;
    }

    return result;
  }

  public List<CustomerMessage> getCustomerMessageList(Date dateStart,Date dateEnd,String customerCode){
    Query query = new SimpleQuery(CustomerWithdrawalQuery.DAILY_CUSTOMER_MESSAGE, dateStart,dateEnd,customerCode);
    return DatabaseUtil.loadAsBeanList(query, CustomerMessage.class);
  }
  
  
  public List<CustomerCoupon> getCustomerCouponList(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_CUSTOMER_COUPON_QUERY, customerCode, CouponUsedFlg.ENABLED.longValue(),
        CouponUsedFlg.USED.longValue(), CouponUsedFlg.OVERDUE.longValue());
    return DatabaseUtil.loadAsBeanList(query, CustomerCoupon.class);
  }

  public SearchResult<CouponStatusAllInfo> findCouponStatusDetailInfo(CouponStatusSearchCondition condition) {
    CouponStatusQuery query = new CouponStatusQuery(condition,
        CouponStatusQuery.LOAD_COUPON_STATUS_QUERY);
    return DatabaseUtil.executeSearch(query);
  }

  public CustomerCoupon getCustomerCoupon(String customerCouponId) {
    CustomerCouponDao customerCouponDao = DIContainer.getDao(CustomerCouponDao.class);
    return customerCouponDao.load(NumUtil.toLong(customerCouponId));    
  }
  
  public GiftCardIssueDetail getGiftCardIssueDetailByPassWord(String password) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_GIFT_CARD_ISSUE_DETAIL_BY_PASSWORD, password.toUpperCase());
    return DatabaseUtil.loadAsBean(query, GiftCardIssueDetail.class);
  }
  
  public CustomerCardInfo getCustomerCardInfoByCustomerCode(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_CUSTOMER_CARD_TOTAL_AMOUNT, customerCode);
    return DatabaseUtil.loadAsBean(query, CustomerCardInfo.class);
  }
  
  public CustomerCardInfo getCustomerCardInfoByCustomerCodeUnable(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_CUSTOMER_CARD_TOTAL_AMOUNT_UNABLE, customerCode);
    return DatabaseUtil.loadAsBean(query, CustomerCardInfo.class);
  }
  
  public GiftCardReturnConfirm getGiftCardReturnConfirm(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_GIFT_CARD_RETURN_AMOUNT, customerCode);
    return DatabaseUtil.loadAsBean(query, GiftCardReturnConfirm.class);
  }
  
  public List<OwnerCardDetail> getCustomerCardInfo(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_CUSTOMER_CARD_DETAIL, customerCode);
    return DatabaseUtil.loadAsBeanList(query, OwnerCardDetail.class);
  }
  
  public CustomerCardUseInfo getCustomerCardUseInfoBycustomerCode(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_CUSTOMER_CARD_TOTAL_USE_AMOUNT, customerCode);
    return DatabaseUtil.loadAsBean(query, CustomerCardUseInfo.class);
  }
  
  public List<CustomerCardInfo> getCustomerCardInfoList(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_CUSTOMER_CARD_INFO_LIST, customerCode);
    return DatabaseUtil.loadAsBeanList(query, CustomerCardInfo.class);
  }
  
  public ServiceResult giftCardActivateProcess(Customer customer, GiftCardIssueDetail cardDetail , GiftCardRule rule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    CustomerCardInfo cardInfo = new CustomerCardInfo();
    cardInfo.setCustomerCode(customer.getCustomerCode());
    cardInfo.setCardId(cardDetail.getCardId());
    cardInfo.setDenomination(cardDetail.getDenomination());
    cardInfo.setRechargeDate(DateUtil.getSysdate());
    cardInfo.setCardCode(cardDetail.getCardCode());
    cardInfo.setCardName(cardDetail.getCardName());
    cardInfo.setCardEndDate(DateUtil.addMonth(cardInfo.getRechargeDate(), Integer.parseInt(( rule.getEffectiveYears()*12) + "" ) ));
    cardInfo.setCardStatus(0L);
    
    try {
      txMgr.begin(this.getLoginInfo());
      
      cardDetail.setCardStatus(1L);
      setUserStatus(cardDetail);
      txMgr.update(cardDetail);
      
      customer.setLockFlg(0L);
      customer.setErrorTimes(0L);
      setUserStatus(customer);
      txMgr.update(customer);

      setUserStatus(cardInfo);
      txMgr.insert(cardInfo);
      
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }
  
  
  
  
  
  public List<CustomerCouponInfo> getCustomerCouponInfoBack(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_CUSTOMER_COUPON_BACK_QUERY, customerCode);
    return DatabaseUtil.loadAsBeanList(query, CustomerCouponInfo.class);
  }
  
  public SearchResult<CustomerCoupon> findCouponStatusCustomerInfo(CouponStatusListSearchCondition condition) {
    CouponStatusAllQuery query = new CouponStatusAllQuery(condition, CouponStatusAllQuery.LOAD_COUPON_STATUS_QUERY);
    return DatabaseUtil.executeSearch(query);
  }
  
  public ServiceResult overdueCoupon() {
    
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    
    try {
      StoredProcedureResult result = DatabaseUtil.executeProcedure(new OverdueCouponProcedure(CouponUsedFlg.OVERDUE.getValue(),
          this.getLoginInfo().getRecordingFormat()));

      if (result.getParameters().get("r_result").toString().equals(StoredProceduedResultType.FAILED.getValue())) {
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      }
    } catch (DataAccessException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }
  
  public ServiceResult insertCustomerCoupon(CustomerCoupon cc) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CustomerCouponDao customerCouponDao = DIContainer.getDao(CustomerCouponDao.class);

    // アドレス帳番号取得

    Long seq = DatabaseUtil.generateSequence(SequenceType.CUSTOMER_COUPON_ID);
    cc.setCustomerCouponId(seq);
    setUserStatus(cc);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(cc).getErrors();

    if (resultList.size() > 0) {
      // ServiceResultにValidationチェックエラーが発生した情報を設定

      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        // 詳細なエラー内容はログにDebugで出力

        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }
    customerCouponDao.insert(cc, getLoginInfo());

    return serviceResult;
  }
  //add by yl 20121209 start
  /*
   * 咨询详细
   */
  	public SearchResult<InquiryDetail> getInquiryDetailList(InquirySearchCondition condition) {
	    InquiryDetailQuery query = new InquiryDetailQuery(condition);
	    return DatabaseUtil.executeSearch(query);
	  } 
	  
	  public InquirySearchInfo getInquiryInfo(String inquiryHeaderNo) {
	    Query query = new SimpleQuery(InquiryDetailQuery.LOAD_INQUIRY_HEADER_QUERY, inquiryHeaderNo);
	    return DatabaseUtil.loadAsBean(query, InquirySearchInfo.class);
	  }
	  
	  public InquiryHeader getInquiryHeader(String inquiryHeaderNo) {
	    InquiryHeaderDao dao = DIContainer.getDao(InquiryHeaderDao.class);
	    return dao.load(inquiryHeaderNo);
	  }
	  
	  public ServiceResult insertInquiryDetail(InquiryDetail inquiryDetail) {
	    
	    ServiceResultImpl serviceResult = new ServiceResultImpl();

	    Long seq = DatabaseUtil.generateSequence(SequenceType.INQUIRY_DETAIL_NO);
	     
	    inquiryDetail.setInquiryDetailNo(seq);
	    inquiryDetail.setAcceptDatetime(DateUtil.getSysdate());
	    inquiryDetail.setPersonInChargeNo(getLoginInfo().getLoginId());
	    inquiryDetail.setPersonInChargeName(getLoginInfo().getName());
	    setUserStatus(inquiryDetail);
	    
	    // Validationチェック
	    List<ValidationResult> resultList = BeanValidator.validate(inquiryDetail).getErrors();
	    if (resultList.size() > 0) {
	      // ServiceResultにValidationチェックエラーが発生した情報を設定
	      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
	      Logger logger = Logger.getLogger(this.getClass());
	      for (ValidationResult rs : resultList) {
	        // 詳細なエラー内容はログにDebugで出力
	        logger.debug(rs.getFormedMessage());
	      }
	      return serviceResult;
	    }
	    
	    TransactionManager txMgr = DIContainer.getTransactionManager();
	    try {
	      txMgr.begin(getLoginInfo());
	      // 咨询详细
	      txMgr.insert(inquiryDetail);
	      
	      txMgr.commit();
	    } catch (ConcurrencyFailureException e) {
	      txMgr.rollback();
	      throw e;
	    } catch (RuntimeException e) {
	      txMgr.rollback();
	      Logger logger = Logger.getLogger(this.getClass());
	      logger.error(e);
	    } finally {
	      txMgr.dispose();
	    }

	    return serviceResult;
	  }
	  
	  public ServiceResult deleteInquiryDetail(String inquiryHeaderNo, String inquiryDetailNo) {
	    
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    
	    InquiryDetailDao dao = DIContainer.getDao(InquiryDetailDao.class);
	    InquiryDetail inquiryDetail = dao.load(inquiryHeaderNo, inquiryDetailNo);
	    if (inquiryDetail == null) {
	      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
	      return serviceResult;
	    }
	    
	    TransactionManager txMgr = DIContainer.getTransactionManager();
	    try {
	      txMgr.begin(getLoginInfo());
	    
	      // 咨询详细
	      txMgr.delete(inquiryDetail);
	      
	      txMgr.commit();
	    } catch (ConcurrencyFailureException e) {
	      txMgr.rollback();
	      throw e;
	    } catch (RuntimeException e) {
	      txMgr.rollback();
	      Logger logger = Logger.getLogger(this.getClass());
	      logger.error(e);
	    } finally {
	      txMgr.dispose();
	    }
	    
	    return serviceResult;
	  }
	  
	  public SearchResult<InquirySearchInfo> getInquiryCountList(InquirySearchCondition condition) {
	    InquiryCountQuery query = new InquiryCountQuery(condition);
	    return DatabaseUtil.executeSearch(query);
	  }
	  
	  public ServiceResult insertInquiry(InquiryInfo inquiryInfo) {
		    ServiceResultImpl serviceResult = new ServiceResultImpl();

		    Long headerSeq = DatabaseUtil.generateSequence(SequenceType.INQUIRY_HEADER_NO);
		    Long detailSeq = DatabaseUtil.generateSequence(SequenceType.INQUIRY_DETAIL_NO);
		    
		    // Header
		    inquiryInfo.getInquiryHeader().setInquiryHeaderNo(headerSeq);
		    inquiryInfo.getInquiryHeader().setAcceptDatetime(DateUtil.getSysdate());
		    setUserStatus(inquiryInfo.getInquiryHeader());
		    
		    // Detail
		    inquiryInfo.getInquiryDetail().setInquiryHeaderNo(headerSeq);
		    inquiryInfo.getInquiryDetail().setInquiryDetailNo(detailSeq);
		    inquiryInfo.getInquiryDetail().setAcceptDatetime(DateUtil.getSysdate());
		    inquiryInfo.getInquiryDetail().setPersonInChargeNo(getLoginInfo().getLoginId());
		    inquiryInfo.getInquiryDetail().setPersonInChargeName(getLoginInfo().getName());
		    setUserStatus(inquiryInfo.getInquiryDetail());
		    
		    // Validationチェック
		    List<ValidationResult> resultList = BeanValidator.validate(inquiryInfo.getInquiryHeader()).getErrors();
		    if (resultList.size() > 0) {
		      // ServiceResultにValidationチェックエラーが発生した情報を設定
		      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
		      Logger logger = Logger.getLogger(this.getClass());
		      for (ValidationResult rs : resultList) {
		        // 詳細なエラー内容はログにDebugで出力
		        logger.debug(rs.getFormedMessage());
		      }
		      return serviceResult;
		    }
		    
		    resultList = BeanValidator.validate(inquiryInfo.getInquiryDetail()).getErrors();
		    if (resultList.size() > 0) {
		      // ServiceResultにValidationチェックエラーが発生した情報を設定
		      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
		      Logger logger = Logger.getLogger(this.getClass());
		      for (ValidationResult rs : resultList) {
		        // 詳細なエラー内容はログにDebugで出力
		        logger.debug(rs.getFormedMessage());
		      }
		      return serviceResult;
		    }
		    
		    TransactionManager txMgr = DIContainer.getTransactionManager();
		    try {
		      txMgr.begin(getLoginInfo());
		      // 咨询Header
		      txMgr.insert(inquiryInfo.getInquiryHeader());
		      // 咨询详细
		      txMgr.insert(inquiryInfo.getInquiryDetail());
		      
		      txMgr.commit();
		    } catch (ConcurrencyFailureException e) {
		      txMgr.rollback();
		      throw e;
		    } catch (RuntimeException e) {
		      txMgr.rollback();
		      Logger logger = Logger.getLogger(this.getClass());
		      logger.error(e);
		    } finally {
		      txMgr.dispose();
		    }

		    return serviceResult;
		  } 
	  public SearchResult<InquirySearchInfo> getInquiryList(InquirySearchCondition condition) {
		    InquirySearchQuery query = new InquirySearchQuery(condition);
		    return DatabaseUtil.executeSearch(query);
		  }
		  
	  public ServiceResult deleteInquiry(List<String> inquiryHeaderNoList) {
		    ServiceResultImpl serviceResult = new ServiceResultImpl();
		    
		    InquiryHeaderDao headerDao = DIContainer.getDao(InquiryHeaderDao.class);
		    
		    TransactionManager txMgr = DIContainer.getTransactionManager();
		    try {
		      txMgr.begin(getLoginInfo());
		      
		      for (String inquiryHeaderNo : inquiryHeaderNoList) {
		        InquiryHeader inquiryHeader = headerDao.load(inquiryHeaderNo);
		        if (inquiryHeader != null) {
		          // 咨询Header
		          txMgr.delete(inquiryHeader);
		          // 咨询详细
		          txMgr.executeUpdate(InquirySearchQuery.DELETE_INQUIRY_QUERY, inquiryHeader.getInquiryHeaderNo().toString());
		        }
		      }
		      
		      txMgr.commit();
		    } catch (ConcurrencyFailureException e) {
		      txMgr.rollback();
		      throw e;
		    } catch (RuntimeException e) {
		      txMgr.rollback();
		      Logger logger = Logger.getLogger(this.getClass());
		      logger.error(e);
		    } finally {
		      txMgr.dispose();
		    }
		    
		    return serviceResult;
		  }
		  
	 
  //add by yl 20121209  end 
	//20111209 lirong add start
	  /**
	   * 根据肌肤诊断编号获得肌肤诊断header
	   * @param webDiagnosisHeaderNo 肌肤诊断编号
	   * @return
	   */
	  public WebDiagnosisHeader getWebDiagnosisHeader(String webDiagnosisHeaderNo) {
	    WebDiagnosisHeaderDao dao = DIContainer.getDao(WebDiagnosisHeaderDao.class);
	    return dao.load(webDiagnosisHeaderNo);
	  }
	//20111209 lirong add end
	  
	  // 20111214 shen add start
	  public ServiceResult insertCartHistory(CartHistory cartHistory) {
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    CartHistoryDao dao = DIContainer.getDao(CartHistoryDao.class);
	    
	    setUserStatus(cartHistory);
	    
	    // Validationチェック
      List<ValidationResult> resultList = BeanValidator.validate(cartHistory).getErrors();
      if (resultList.size() > 0) {
        // ServiceResultにValidationチェックエラーが発生した情報を設定
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        Logger logger = Logger.getLogger(this.getClass());
        for (ValidationResult rs : resultList) {
          // 詳細なエラー内容はログにDebugで出力
          logger.debug(rs.getFormedMessage());
        }
        return serviceResult;
      }
	    
	    TransactionManager txMgr = DIContainer.getTransactionManager();
      try {
        txMgr.begin(getLoginInfo());

        // 删除存在的购物车商品履历
        if (dao.exists(cartHistory.getCustomerCode(), cartHistory.getShopCode(), cartHistory.getSkuCode())) {
          dao.delete(cartHistory.getCustomerCode(), cartHistory.getShopCode(), cartHistory.getSkuCode());
        }
        
        // 登录购物车商品履历
        txMgr.insert(cartHistory);
        
        txMgr.commit();
      } catch (ConcurrencyFailureException e) {
        txMgr.rollback();
        throw e;
      } catch (RuntimeException e) {
        txMgr.rollback();
        Logger logger = Logger.getLogger(this.getClass());
        logger.error(e);
      } finally {
        txMgr.dispose();
      }
	    
	    return serviceResult;
	  }
	  
	  public ServiceResult deleteCartHistory(String customerCode, String shopCode, String skuCode) {
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    
	    if (StringUtil.isNullOrEmptyAnyOf(customerCode, skuCode)) {
	      return serviceResult;
	    }
	    
	    CartHistoryDao dao = DIContainer.getDao(CartHistoryDao.class);
	    dao.delete(customerCode, shopCode, skuCode);
	    
	    return serviceResult;
	  }
	  
	  public ServiceResult deleteCartHistory(String customerCode) {
	    ServiceResultImpl serviceResult = new ServiceResultImpl();
	    
	    if (StringUtil.isNullOrEmpty(customerCode)) {
	      return serviceResult;
	    }
      
      TransactionManager txMgr = DIContainer.getTransactionManager();
      try {
        txMgr.begin(getLoginInfo());

        // 清空购物车履历
        txMgr.executeUpdate(CartHistoryQuery.DELETE_CART_HISTORY_QUERY, customerCode);
        
        txMgr.commit();
      } catch (ConcurrencyFailureException e) {
        txMgr.rollback();
        throw e;
      } catch (RuntimeException e) {
        txMgr.rollback();
        Logger logger = Logger.getLogger(this.getClass());
        logger.error(e);
      } finally {
        txMgr.dispose();
      }
      
      return serviceResult;
    }
	  
	  public List<CartHistory> getCartHistoryList(String customerCode) {
	    Query query = new SimpleQuery(CartHistoryQuery.SELECT_CART_HISTORY_BY_CUSTOMER_CODE_QUERY, customerCode);
	    return DatabaseUtil.loadAsBeanList(query, CartHistory.class);
	  }
    // 20111214 shen add end
	  // Add by V10-CH start
	  public ServiceResult changeCustomerGroup(Customer customer) {
	    Logger logger = Logger.getLogger(this.getClass());
	    ServiceResultImpl result = new ServiceResultImpl();
	    TransactionManager txMgr = DIContainer.getTransactionManager();
	    try {
	      txMgr.begin(this.getLoginInfo());
	      setUserStatus(customer);
	      txMgr.update(customer);
	      txMgr.commit();
	    } catch (ConcurrencyFailureException e) {
	      logger.error(e);
	      txMgr.rollback();
	      throw e;
	    } catch (RuntimeException e) {
	      logger.debug(e);
	      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
	      txMgr.rollback();
	    } finally {
	      txMgr.dispose();
	    }

	    return result;
	  }
	  // Add by V10-CH end
	   public SearchResult<MemberSearchInfo> getMemberList(MemberSearchCondition condition) {
	      MemberSearchQuery query = new MemberSearchQuery(condition);
	      return DatabaseUtil.executeSearch(query);
	    }
	//20111213 lirong add start
	    /**
	     *顾客新增
	     */
	    @Override
	   public ServiceResult insertCustomer1(Customer customer,String companyCode) {
	      
	      
	      ServiceResultImpl serviceResult = new ServiceResultImpl();
	      if (StringUtil.isNullOrEmpty(customer.getCustomerCode())) {
	    	// 顧客コード取得
			  customer.setCustomerCode(CommonLogic.generateCustomerCode());  
	      }
	      // パスワードをハッシュ化
	      customer.setPassword(PasswordUtil.getDigest(customer.getPassword()));
	      // メールアドレス重複エラー
	      if (!isAvailableEmailInsert(customer.getEmail())) {
	        serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR);
	        return serviceResult;
	      }
	      customer.setLoginId(customer.getEmail());
	      // ログインID重複エラー：将来的にメールアドレスとログインIDを別々に使用する可能性もあるので、ログインID重複チェックを別に実行

	      if (!isAvailableLoginIdInsert(customer.getLoginId())) {
	        serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR);
	        return serviceResult;
	      }
	      // メール区分を設定
	      customer.setClientMailType(getClientMailType(customer.getEmail()));
	      
	      setUserStatus(customer);
	      TransactionManager txMgr = DIContainer.getTransactionManager();
	      customer.setRestPoint(BigDecimal.ZERO);
	      try {
	        txMgr.begin(getLoginInfo());
	        // 顧客情報登録
	        txMgr.insert(customer);
	        if (StringUtil.hasValue(companyCode)) {
	          CompanyCustomerDao ccDao = DIContainer.getDao(CompanyCustomerDao.class);
	          CompanyCustomer cc = ccDao.load(companyCode);
	          if (cc != null && StringUtil.isNullOrEmpty(cc.getCustomerCode())) {
	            cc.setCustomerCode(customer.getCustomerCode());
	            txMgr.update(cc);
	          } else {
	            txMgr.rollback();
	          }
	        }
	        txMgr.commit();
	        //支付宝快捷登录会员不发邮件
	        if(!customer.getCustomerKbn().equals(1L)){
	        // 顧客情報を再取得(最終ポイント獲得日取得)
	        performCustomerEvent(new CustomerEvent(customer),CustomerEventType.ADDED);
	        }
	      } catch (ConcurrencyFailureException e) {
	        txMgr.rollback();
	        throw e;
	      } catch (RuntimeException e) {
	        txMgr.rollback();
	        Logger logger = Logger.getLogger(this.getClass());
	        logger.error(e);
	      } finally {
	        txMgr.dispose();
	      }
	      return serviceResult;
	    }

	    /***
	     * 顾客更新
	     */
	    @Override
	    public ServiceResult updateCustomer1(Customer customer) {
	      ServiceResultImpl serviceResult = new ServiceResultImpl();

	      // メールアドレス重複エラー
	      if (!isAvailableEmailUpdate(customer.getCustomerCode(), customer.getEmail())) {
	        serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR);
	        return serviceResult;
	      }
	      
	      customer.setLoginId(customer.getEmail());
	     
	      // ログインID重複エラー

	      // 将来的にメールアドレスとログインIDを別々に使用する可能性もあるので、ログインID重複チェックを別に実行

	      if (!isAvailableLoginIdUpdate(customer.getCustomerCode(), customer.getLoginId())) {
	        serviceResult.addServiceError(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR);
	        return serviceResult;
	      }
	     
	      // 顧客削除済みエラー

	      if (isWithdrawedCustomer(customer.getCustomerCode())) {
	        serviceResult.addServiceError(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR);
	        return serviceResult;
	      }
	      customer.setClientMailType(getClientMailType(customer.getEmail()));
	      // 退会依頼データを設定

	      if (customer.getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue())) {
	        customer.setWithdrawalRequestDate(DateUtil.fromString(DateUtil.getSysdateString()));
	      } else if (customer.getCustomerStatus().equals(CustomerStatus.MEMBER.longValue())) {
	        customer.setWithdrawalRequestDate(null);
	      } 

	      setUserStatus(customer);

	   // Validationチェック
	      ValidationSummary validateCustomer = BeanValidator.validate(customer);
	      if (validateCustomer.hasError()) {
	        Logger logger = Logger.getLogger(this.getClass());
	        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
	        for (String rs : validateCustomer.getErrorMessages()) {
	          logger.debug(rs);
	        }
	        return serviceResult;
	      }
	    TransactionManager txMgr = DIContainer.getTransactionManager();
	      try {
	        txMgr.begin(getLoginInfo());
	        // 顧客情報登録
	        txMgr.update(customer);
	        txMgr.commit();
	        // del by lc 2012-09-06 start 修改用户信息时，不发送邮件 
	       //performCustomerEvent(new CustomerEvent(customer), CustomerEventType.UPDATED);
	        // del by lc 2012-09-06 end
	      } catch (ConcurrencyFailureException e) {
	        txMgr.rollback();
	        throw e;
	      } catch (RuntimeException e) {
	        txMgr.rollback();
	      } finally {
	        txMgr.dispose();
	      }

	      return serviceResult;
	    }
	    //20111213 lirong add end
  
  // 20111223 shen add start
  public CustomerGroupCampaign getCustomerGroupCampaign(String shopCode, String customerGroupCode) {
    Query query = new SimpleQuery(CustomerGroupQuery.LOAD_CUSTOMER_GROUP_CAMPAIGN_BY_GROUP_CODE_QUERY, shopCode, customerGroupCode);
    return DatabaseUtil.loadAsBean(query, CustomerGroupCampaign.class);
  }
  
  public List<NewCouponHistory> getUnusedPersonalCouponList(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_UNUSED_PERSONAL_COUPON_LIST_QUERY, UseStatus.UNUSED.getValue(), customerCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponHistory.class);
  }
  
  public NewCouponHistory getUnusedPersonalCoupon(String customerCode, String couponIssueNo) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_UNUSED_PERSONAL_COUPON_QUERY, UseStatus.UNUSED.getValue(), customerCode, couponIssueNo);
    return DatabaseUtil.loadAsBean(query, NewCouponHistory.class);
  }
  
  public NewCouponRule getPublicCoupon(String couponCode) {
    // 20131022 txw update start
    if (StringUtil.hasValue(couponCode)) {
      Query query = new SimpleQuery(CustomerCouponQuery.LOAD_PUBLIC_COUPON_QUERY, CouponType.COMMON_DISTRIBUTION.getValue(),
          StringUtil.toHalfWidth(couponCode));
      return DatabaseUtil.loadAsBean(query, NewCouponRule.class);
    }
    return null;
    // 20131022 txw update end
  }
  
  public NewCouponRule getPersonalBirthdayCoupon(String couponCode) {
    // 20131022 txw update start
    if (StringUtil.hasValue(couponCode)) {
      Query query = new SimpleQuery(CustomerCouponQuery.LOAD_PERSONNAL_COUPON_QUERY, CouponType.BIRTHDAY_DISTRIBUTION.getValue(),
          StringUtil.toHalfWidth(couponCode));
      return DatabaseUtil.loadAsBean(query, NewCouponRule.class);
    }
    return null;
    // 20131022 txw update end
  }
  
  public NewCouponRule getSpecialMemberDistribution(String couponCode) {
    // 20131022 txw update start
    if (StringUtil.hasValue(couponCode)) {
      Query query = new SimpleQuery(CustomerCouponQuery.LOAD_PERSONNAL_COUPON_QUERY, CouponType.SPECIAL_MEMBER_DISTRIBUTION.getValue(),
          StringUtil.toHalfWidth(couponCode));
      return DatabaseUtil.loadAsBean(query, NewCouponRule.class);
    }
    return null;
    // 20131022 txw update end
  }
  
  // 20111223 shen add end
  //20111225 os013 add start
  public Customer getCustomerCode(String tmallUserId,String customerStatus) {
    Query query = new SimpleQuery(CustomerSearchQuery.LOAD_CUSTOMER_TMALLUSERID_QUERY, tmallUserId,customerStatus);
    return DatabaseUtil.loadAsBean(query, Customer.class);
  }
  //20111225 os013 add end
  
  //20111229 ob add start
  public List<NewCouponHistory> getMailNoticeForStartCoupon(String useStartDatetime) {
	Query query = new SimpleQuery(PrivateCouponListSearchQuery.FIND_NEW_COUPON_USE_START_FOR_BATCH, useStartDatetime);
	return DatabaseUtil.loadAsBeanList(query, NewCouponHistory.class);
  }
  
  public List<NewCouponHistory> getMailNoticeForEndCoupon(String useEndDatetime) {
	Query query = new SimpleQuery(PrivateCouponListSearchQuery.FIND_NEW_COUPON_USE_END_FOR_BATCH, useEndDatetime);
	return DatabaseUtil.loadAsBeanList(query, NewCouponHistory.class);
  }
  //20111229 ob end start
  
  // 20120104 shen add start
  public ServiceResultImpl insertCustomerSelfAddress(CustomerAddress address) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 顧客存在チェック
    if (noAliveCustomer(address.getCustomerCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CustomerAddressDao dao = DIContainer.getDao(CustomerAddressDao.class);
    
    CustomerAddress customerAddress = dao.load(address.getCustomerCode(), address.getAddressNo());
    if (customerAddress != null) {
      // アドレス帳番号取得
      Long seq = DatabaseUtil.generateSequence(SequenceType.ADDRESS_NO);
      address.setAddressNo(seq);
    }
    setUserStatus(address);

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(address).getErrors();

    if (resultList.size() > 0) {
      // ServiceResultにValidationチェックエラーが発生した情報を設定

      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        // 詳細なエラー内容はログにDebugで出力

        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }
    dao.insert(address, getLoginInfo());

    return serviceResult;
  }
  // 20120104 shen add end
  // 20120110 os013 add start
  public ServiceResult settleFastpay(String tmallUserId, String realName, String email) {
    ServiceResult serviceResult = new ServiceResultImpl();
    Customer customerCode = new Customer();
    // 查询是否有支付宝用户号
    customerCode = getCustomerCode(tmallUserId, CustomerStatus.WITHDRAWED.getValue());

    // 支付宝第一次登录，进入顾客注册画面
    if (customerCode == null) {
      // 顧客情報
      Customer customer = new Customer();
      // 顧客コード
      customer.setCustomerCode(CommonLogic.generateCustomerCode());
      // 氏名(名)
      customer.setFirstName(" ");
      // 氏名(姓)
      customer.setLastName(realName);
      // 氏名カナ(名)
      customer.setFirstNameKana(" ");
      // 氏名カナ(姓)
      customer.setLastNameKana(" ");
      // メールアドレス
      customer.setEmail(tmallUserId + "@alipay.com");
      // ログインID
      customer.setLoginId(tmallUserId + "@alipay.com");
      // パスワード
      customer.setPassword(customer.getCustomerCode() + tmallUserId);
      // 生年月日
      customer.setBirthDate(DateUtil.fromString("1900/01/01"));
      // 性別
      customer.setSex(0L);
      // 情報メール
      customer.setRequestMailType(0L);
      // ログイン失敗回数
      customer.setLoginErrorCount(0L);
      // 顧客グループコード
      customer.setCustomerGroupCode(CustomerConstant.DEFAULT_GROUP_CODE);
      // ログインロックフラグ
      customer.setLoginLockedFlg(LoginLockedFlg.UNLOCKED.longValue());
      // 顧客ステータス
      customer.setCustomerStatus(CustomerStatus.MEMBER.longValue());
      // 支付宝用户编号
      customer.setTmallUserId(Long.parseLong(tmallUserId));
      // 会员区分
      customer.setCustomerKbn(1L);
      //add by cs_yuli 20120619 start
      //会员语言
      customer.setLanguageCode(DIContainer.getLocaleContext().getCurrentLanguageCode());
      //add by cs_yuli 20120619 end
      serviceResult = insertCustomer1(customer,"");
    }
    return serviceResult;
  }
  // 20120110 os013 add end
  //20120111 os013 add start
  //我的所有优惠券查询
  public List<NewCouponHistory> getCouponHistoryList(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_COUPON_HISTORY_LIST_QUERY, customerCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponHistory.class);
  }
  //20120111 os013 add end

  public SearchResult<MemberShippingHistory> getMemberShippingHistoryList(MemberSearchCondition condition) {
    MemberShippingHistoryQuery query = new MemberShippingHistoryQuery(condition, MemberShippingHistoryQuery.LOAD_ORDER_QUERY);
    return DatabaseUtil.executeSearch(query);
  } 

  public SearchResult<MemberInquiryHistory> getMemberInquiryHistoryList(MemberSearchCondition condition) {
    MemberInquiryHistoryQuery query = new MemberInquiryHistoryQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }

  public SearchResult<MemberCouponHistory> getMemberCouponHistoryList(MemberSearchCondition condition) {
    MemberCouponHistoryQuery query = new MemberCouponHistoryQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }
 
  public ServiceResult updateCouponUserStatus(NewCouponHistory history) {
	ServiceResultImpl result = new ServiceResultImpl();

	NewCouponHistoryDao dao = DIContainer.getDao(NewCouponHistoryDao.class);

	NewCouponHistory couponHistory = dao.load(history.getCouponIssueNo());

	if (couponHistory == null) {
		result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
		return result;
	}

	history.setOrmRowid(couponHistory.getOrmRowid());
	history.setCreatedDatetime(couponHistory.getCreatedDatetime());
	history.setCreatedUser(couponHistory.getCreatedUser());
	history.setUpdatedUser(couponHistory.getUpdatedUser());
	ValidationSummary resultValidate = BeanValidator.validate(history);
	if (resultValidate.hasError()) {
		result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
		return result;
	} else {
		dao.update(history, getLoginInfo());
	}

	return result;
  }
  // 
  public List<NewCouponHistory> getCouponHistoryValidList(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.LOAD_COUPON_HISTORY_LIST, customerCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponHistory.class);
  }
  
  // 2013/04/01 优惠券对应 ob add start
  /**
   * 携帯電話検証コードを取得する
   * @return MobileAuthを返します。
   */
  public MobileAuth getAuthInfo(String authCode, String mobileNumber) {

    if (StringUtil.isNullOrEmpty(authCode) ||StringUtil.isNullOrEmpty(mobileNumber)) {
      return null;
    }

    Object[] params = new Object[] {
        mobileNumber,authCode, DateUtil.toDateTimeString(DateUtil.getSysdate(), DateUtil.DEFAULT_TIMESTAMP_FORMAT)
    };

    final String queryCheck = CustomerSearchQuery.GET_MOBILE_AUTH;
    Query query = new SimpleQuery(queryCheck, params);
    MobileAuth authResult = DatabaseUtil.loadAsBean(query, MobileAuth.class);

    if (authResult != null) {
      if (StringUtil.isNullOrEmpty(authResult.getAuthCode())) {
        return null;
      } else {
        return authResult;
      }
    } else {
      return null;
    }
  }
  // 2013/04/01 优惠券对应 ob add end
  
	/**
	 * 查询判断是否有效
	 */
	@Override
	public Long getCount(String number, String veri) {
		String queryCheck = "select count(*) from mobile_auth where  mobile_number = ? and auth_code=? and (? between start_datetime and end_datetime)";
		Query query = new SimpleQuery(queryCheck, number, veri,DateUtil.toDateTimeString(DateUtil.getSysdate(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
		Long count = DatabaseUtil.executeScalar(query, Long.class);
		if(count != null){
			return count;
		}else{
			return 0L;
		}
	}

	/**
	 * 查询短信验证手机
	 */
	@Override
	public String getMobileNumber(String code) {
		String queryCheck = "select mobile_number from customer where customer_code=? ";
		Query query = new SimpleQuery(queryCheck, code);
		String count = DatabaseUtil.executeScalar(query, String.class);
		return count;
	}

  @Override
  public NewCouponHistory getNewCouponHistory(String loginId) {
    Query query = new SimpleQuery("select * from new_coupon_history where customer_code=? and coupon_status=1 and use_status=0 AND USE_END_DATETIME  >= "+SqlDialect.getDefault().getCurrentDatetime(),loginId);
    NewCouponHistory op = DatabaseUtil.loadAsBean(query, NewCouponHistory.class); 
    return op;
  }
  public BigDecimal getCouponType(String couponCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.GET_COUPON_TYPE, couponCode);
    return DatabaseUtil.executeScalar(query, BigDecimal.class);
  }
  
  public String getCouponCodeByCouponIssueNo(String couponIssueCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.GET_COUPON_CODE_BY_COUPON_ISSUE_NO, couponIssueCode);
    return DatabaseUtil.executeScalar(query, String.class);
  }
  
  public List<NewCouponRuleUseInfo> getNewCouponRuleUseInfo(String couponCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.GET_NEW_COUPON_RULE_USE_INFO, couponCode);
    return DatabaseUtil.loadAsBeanList(query, NewCouponRuleUseInfo.class);
  }
  
  @Override
  public Long getAvaliableGiftCardCount(String customerCode) {
    Query query = new SimpleQuery(CustomerCouponQuery.GET_AVALIABLE_GIFT_CARD_COUNT, customerCode);
    return NumUtil.parseLong(DatabaseUtil.executeScalar(query));
  }
  
  
}
