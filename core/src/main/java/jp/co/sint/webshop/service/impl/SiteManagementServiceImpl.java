package jp.co.sint.webshop.service.impl;

import static jp.co.sint.webshop.service.shop.SiteInfoQuery.SITE_LOAD_QUERY;

import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchQuery;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.AdvertDao;
import jp.co.sint.webshop.data.dao.CouponRuleDao;
import jp.co.sint.webshop.data.dao.GoogleAnalysisDao;
import jp.co.sint.webshop.data.dao.InformationDao;
import jp.co.sint.webshop.data.dao.OnlineServiceDao;
import jp.co.sint.webshop.data.dao.PointRuleDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.dao.TaxDao;
import jp.co.sint.webshop.data.dao.UserAccessLogDao;
import jp.co.sint.webshop.data.dao.UserAccountDao;
import jp.co.sint.webshop.data.dao.UserPermissionDao;
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.GoogleAnalysis;
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.data.dto.OnlineService;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.data.dto.UserAccessLog;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.data.dto.UserPermission;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.result.SiteManagementServiceErrorContent;
import jp.co.sint.webshop.service.shop.AdvertInfoQuery;
import jp.co.sint.webshop.service.shop.InformationCountSearchCondition;
import jp.co.sint.webshop.service.shop.InformationCountSearchQuery;
import jp.co.sint.webshop.service.shop.SiteManagementServiceQuery;
import jp.co.sint.webshop.service.shop.UserAccessLogSearchCondition;
import jp.co.sint.webshop.service.shop.UserAccessLogSearchQuery;
import jp.co.sint.webshop.service.shop.UserAccountSearchCondition;
import jp.co.sint.webshop.service.shop.UserAccountSearchQuery;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class SiteManagementServiceImpl extends AbstractServiceImpl implements SiteManagementService {

  private static final long serialVersionUID = 1L;

  public ServiceResult deleteInformation(Long informationNo) {
    ServiceResultImpl result = new ServiceResultImpl();
    InformationDao dao = DIContainer.getDao(InformationDao.class);

    dao.delete(informationNo);

    return result;
  }

  public ServiceResult deleteTax(Long taxNo) {
    ServiceResultImpl result = new ServiceResultImpl();
    TaxDao dao = DIContainer.getDao(TaxDao.class);
    dao.delete(taxNo);

    return result;
  }

  public ServiceResult deleteUserAccount(UserAccount account) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    UserAccount orgAccount = getUserAccount(account.getUserCode());
    if (orgAccount == null) {
      return serviceResult;
    }

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(getLoginInfo());

      manager.delete(orgAccount);

      manager.executeUpdate(SiteManagementServiceQuery.DELETE_USER_PERMISSION_LIST, orgAccount.getUserCode());

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

  public Tax getCurrentTax() {
    List<Tax> taxList = getTaxList();
    Date sysdate = DateUtil.getSysdate();
    Tax currentTax = null;
    for (Tax tax : taxList) {
      Date appliedDate = tax.getAppliedStartDate();
      // 当日日付より以前に適用されている消費税のうち一番新しいものを取得する

      if (appliedDate.before(sysdate) && (currentTax == null || appliedDate.after(currentTax.getAppliedStartDate()))) {
        currentTax = tax;
      }
    }
    return currentTax;
  }

  public List<Information> getInformationList() {
    InformationDao dao = DIContainer.getDao(InformationDao.class);
    Query query = new SimpleQuery(SiteManagementServiceQuery.LOAD_INFORMATION_LIST);
    return dao.findByQuery(query);
  }

  public PointRule getPointRule() {
    return CommonLogic.getPointRule(getLoginInfo());
  }

  public Shop getSite() {
    Query q = new SimpleQuery(SITE_LOAD_QUERY);
    List<Shop> shopList = DatabaseUtil.loadAsBeanList(q, Shop.class);

    if (shopList.size() > 0) {
      return shopList.get(0);
    } else {
      return null;
    }
  }

  public ServiceResult updateSite(Shop shop) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    ShopDao dao = DIContainer.getDao(ShopDao.class);

    Shop orgShop = getSite();

    // サイト未登録時エラー
    if (orgShop == null || orgShop.getShopCode() == null) {
      serviceResult.addServiceError(SiteManagementServiceErrorContent.SITE_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }

    // データのコピー


    shop.setOrmRowid(orgShop.getOrmRowid());
    shop.setCreatedDatetime(orgShop.getCreatedDatetime());
    shop.setCreatedUser(orgShop.getCreatedUser());

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(shop).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 更新処理実行
    dao.update(shop, getLoginInfo());

    return serviceResult;
  }

  public List<Tax> getTaxList() {
    TaxDao taxDao = DIContainer.getDao(TaxDao.class);
    return taxDao.findByQuery(SiteManagementServiceQuery.LOAD_TAX_LIST);
  }

  public UserAccount getUserAccount(Long userCode) {
    UserAccountDao dao = DIContainer.getDao(UserAccountDao.class);
    return dao.load(userCode);
  }

  public SearchResult<UserAccount> getUserAccountList(SearchCondition condition) {

    if (condition instanceof UserAccountSearchCondition) {
      SearchQuery<UserAccount> query = new UserAccountSearchQuery((UserAccountSearchCondition) condition);

      SearchResult<UserAccount> result = DatabaseUtil.executeSearch(query);
      return result;

    } else {
      return new SearchResult<UserAccount>();
    }

  }

  public List<UserPermission> getUserPermissionList(Long userCode) {

    UserPermissionDao dao = DIContainer.getDao(UserPermissionDao.class);

    return dao.findByQuery(SiteManagementServiceQuery.LOAD_USER_PERMISSION_LIST, userCode);
  }

  public ServiceResult insertInformation(Information information) {
    InformationDao dao = DIContainer.getDao(InformationDao.class);

    // お知らせ情報のSEQを取得

    Long nextVal = DatabaseUtil.generateSequence(SequenceType.INFORMATION_NO);
    information.setInformationNo(nextVal);
    setUserStatus(information);

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    List<ValidationResult> resultList = BeanValidator.validate(information).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.insert(information, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult insertTax(Tax tax) {
    TaxDao taxDao = DIContainer.getDao(TaxDao.class);

    // 消費税番号のSEQを取得

    Long nextVal = DatabaseUtil.generateSequence(SequenceType.TAX_NO);

    // 取得した消費税番号を登録用DTOにセット
    tax.setTaxNo(nextVal);

    setUserStatus(tax);

    // Validationチェック
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    List<Tax> taxList = getTaxList();

    for (Tax orgTax : taxList) {
      if (orgTax.getAppliedStartDate().equals(tax.getAppliedStartDate())) {
        serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
        return serviceResult;
      }
    }

    List<ValidationResult> resultList = BeanValidator.validate(tax).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 登録処理実行
    taxDao.insert(tax, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult insertUserAccount(UserAccount account, List<UserPermission> permissionList) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    UserAccount a = getUserAccountByLoginId(account.getShopCode(), account.getUserLoginId());
    if (a != null) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    Long userCode = DatabaseUtil.generateSequence(SequenceType.USER_CODE);
    // ユーザの登録
    account.setUserCode(userCode);
    // パスワードの暗号化

    account.setPassword(PasswordUtil.getDigest(account.getPassword()));

    setUserStatus(account);

    ValidationSummary summary = BeanValidator.validate(account);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (String s : summary.getErrorMessages()) {
        logger.debug(s);
      }
    }

    // 権限の設定

    for (UserPermission p : permissionList) {
      p.setUserCode(userCode);
      setUserStatus(p);
      summary = BeanValidator.validate(p);
      if (summary.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        Logger logger = Logger.getLogger(this.getClass());
        for (String s : summary.getErrorMessages()) {
          logger.debug(s);
        }
      }
    }

    if (serviceResult.hasError()) {
      return serviceResult;
    }

    TransactionManager manager = DIContainer.getTransactionManager();

    try {
      manager.begin(getLoginInfo());

      manager.insert(account);

      // 権限の設定

      for (UserPermission p : permissionList) {
        manager.insert(p);
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

  public ServiceResult updateInformation(Information information) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    InformationDao dao = DIContainer.getDao(InformationDao.class);
    Information orgInformation = dao.load(information.getInformationNo());

    // お知らせ情報未登録エラー
    if (orgInformation == null) {
      serviceResult.addServiceError(SiteManagementServiceErrorContent.NO_INFORMATION_DATE_ERROR);
      return serviceResult;
    }

    // データのコピーを行う
    information.setOrmRowid(orgInformation.getOrmRowid());
    information.setCreatedDatetime(orgInformation.getCreatedDatetime());
    information.setCreatedUser(orgInformation.getCreatedUser());
    information.setUpdatedUser(orgInformation.getUpdatedUser());

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(information).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(information, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult updatePointRule(PointRule rule) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    PointRuleDao dao = DIContainer.getDao(PointRuleDao.class);
    List<PointRule> orgPointRuleList = dao.loadAll();

    if (orgPointRuleList.size() <= 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    PointRule orgPointRule = orgPointRuleList.get(0);

    rule.setOrmRowid(orgPointRule.getOrmRowid());
    rule.setCreatedDatetime(orgPointRule.getCreatedDatetime());
    rule.setCreatedUser(orgPointRule.getCreatedUser());
    rule.setUpdatedUser(orgPointRule.getUpdatedUser());

    Logger logger = Logger.getLogger(this.getClass());
    List<ValidationResult> resultList = BeanValidator.validate(rule).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(rule, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult updateUserAccount(UserAccount account) {

    Logger logger = Logger.getLogger(this.getClass());

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    UserAccountDao dao = DIContainer.getDao(UserAccountDao.class);
    UserAccount userAccount = dao.load(account.getUserCode());

    if (userAccount == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug(Messages.log("service.impl.SiteManagementServiceImpl.0"));
      return serviceResult;
    }

    account.setCreatedDatetime(userAccount.getCreatedDatetime());
    account.setCreatedUser(userAccount.getCreatedUser());
    setUserStatus(account);

    ValidationSummary summary = BeanValidator.validate(account);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String s : summary.getErrorMessages()) {
        logger.debug(s);
      }
      return serviceResult;
    }

    dao.update(account, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult updateUserAccount(UserAccount account, List<UserPermission> permissionList) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    UserAccountDao dao = DIContainer.getDao(UserAccountDao.class);
    UserAccount orgAccount = dao.load(account.getUserCode());
    if (orgAccount == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(getLoginInfo());

      // パスワードの暗号化

      if (StringUtil.isNullOrEmpty(account.getPassword()) || orgAccount.getPassword().equals(account.getPassword())) {
        account.setPassword(orgAccount.getPassword());
      } else {
        account.setPassword(PasswordUtil.getDigest(account.getPassword()));
      }
      account.setOrmRowid(orgAccount.getOrmRowid());
      account.setCreatedUser(orgAccount.getCreatedUser());
      account.setCreatedDatetime(orgAccount.getCreatedDatetime());
      setUserStatus(account);

      ValidationSummary summary = BeanValidator.validate(account);
      if (summary.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        Logger logger = Logger.getLogger(this.getClass());
        for (String s : summary.getErrorMessages()) {
          logger.debug(s);
        }
        return serviceResult;
      }

      manager.update(account);

      // 権限の設定(DeleteInsert)
      manager.executeUpdate(SiteManagementServiceQuery.DELETE_USER_PERMISSION_LIST, account.getUserCode());

      for (UserPermission p : permissionList) {
        p.setUserCode(account.getUserCode());
        setUserStatus(p);
        manager.insert(p);
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

  public SearchResult<UserAccessLog> getUserAccessLog(SearchCondition condition) {
    if (condition instanceof UserAccessLogSearchCondition) {
      UserAccessLogSearchQuery query = new UserAccessLogSearchQuery((UserAccessLogSearchCondition) condition);

      SearchResult<UserAccessLog> result = DatabaseUtil.executeSearch(query);
      return result;

    } else {
      return new SearchResult<UserAccessLog>();
    }
  }

  public ServiceResult insertUserAccessLog(UserAccessLog accessLog) {
    ServiceResultImpl result = new ServiceResultImpl();

    Long nextVal = DatabaseUtil.generateSequence(SequenceType.USER_ACCESS_LOG_ID);
    accessLog.setUserAccessLogId(nextVal);

    setUserStatus(accessLog);
    ValidationSummary validate = BeanValidator.validate(accessLog);
    if (validate.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (String s : validate.getErrorMessages()) {
        logger.debug(s);
      }
      return result;
    }

    UserAccessLogDao dao = DIContainer.getDao(UserAccessLogDao.class);
    dao.insert(accessLog, getLoginInfo());

    return result;
  }

  public UserAccount getUserAccountByLoginId(String shopcode, String loginid) {
    Query query = new SimpleQuery(SiteManagementServiceQuery.LOAD_USER_ACCOUNT_BY_LOGIN_ID, shopcode, loginid);
    return DatabaseUtil.loadAsBean(query, UserAccount.class);
  }

  public String getInformationCount(InformationCountSearchCondition condition) {
    return DatabaseUtil.executeScalar(new InformationCountSearchQuery(condition)).toString();
  }

  // add by shikui start 2010/04/28
  public OnlineService getOnlineService(String shopCode) {
    return CommonLogic.getOnlineService(shopCode);
  }

  public ServiceResult updateOnlineService(OnlineService onlineService) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    OnlineServiceDao dao = DIContainer.getDao(OnlineServiceDao.class);
    List<OnlineService> orgOnlineServiceList = dao.loadAll();

    if (orgOnlineServiceList.size() <= 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    // modify by V10-CH start
    OnlineService orgOnlineService = new OnlineService();
    for (OnlineService lst : orgOnlineServiceList) {
      if (lst.getOnlineServiceNo().equals(onlineService.getOnlineServiceNo())) {
        orgOnlineService = lst;
      }
    }
    // modify by V10-CH end
    onlineService.setOrmRowid(orgOnlineService.getOrmRowid());
    onlineService.setCreatedDatetime(orgOnlineService.getCreatedDatetime());
    onlineService.setCreatedUser(orgOnlineService.getCreatedUser());
    onlineService.setUpdatedUser(orgOnlineService.getUpdatedUser());

    Logger logger = Logger.getLogger(this.getClass());
    List<ValidationResult> resultList = BeanValidator.validate(onlineService).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(onlineService, getLoginInfo());

    return serviceResult;
  }

  // Add by V10-CH start
  public Boolean isHaveOnline(String shopCode) {
    Boolean isHaveOnline = false;
    OnlineServiceDao dao = DIContainer.getDao(OnlineServiceDao.class);
    isHaveOnline = dao.isHaveOnline(shopCode);
    return isHaveOnline;
  }

  // Add by V10-CH end

  // Add by V10-CH start
  public ServiceResult insertOnlineService(OnlineService onlineService) {
    Long nextVal = DatabaseUtil.generateSequence(SequenceType.ONLINE_SERVICE_NO);
    onlineService.setOnlineServiceNo(nextVal);
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    OnlineServiceDao dao = DIContainer.getDao(OnlineServiceDao.class);
    setUserStatus(onlineService);
    dao.insert(onlineService, getLoginInfo());
    return serviceResult;
  }

  // Add by V10-CH end

  public GoogleAnalysis getGoogleAnalysis() {
    return CommonLogic.getGoogleAnalysis(getLoginInfo());
  }

  public ServiceResult updateGoogleAnalysis(GoogleAnalysis googleAnalysis) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    GoogleAnalysisDao dao = DIContainer.getDao(GoogleAnalysisDao.class);
    List<GoogleAnalysis> orgGoogleAnalysisDaoList = dao.loadAll();

    if (orgGoogleAnalysisDaoList.size() <= 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    GoogleAnalysis orgGoogleAnalysis = orgGoogleAnalysisDaoList.get(0);

    googleAnalysis.setOrmRowid(orgGoogleAnalysis.getOrmRowid());
    googleAnalysis.setCreatedDatetime(orgGoogleAnalysis.getCreatedDatetime());
    googleAnalysis.setCreatedUser(orgGoogleAnalysis.getCreatedUser());
    googleAnalysis.setUpdatedUser(orgGoogleAnalysis.getUpdatedUser());

    Logger logger = Logger.getLogger(this.getClass());
    List<ValidationResult> resultList = BeanValidator.validate(googleAnalysis).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    dao.update(googleAnalysis, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult insertGoogleAnalysis(GoogleAnalysis googleAnalysis) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    GoogleAnalysisDao dao = DIContainer.getDao(GoogleAnalysisDao.class);
    // googleA
    setUserStatus(googleAnalysis);
    dao.insert(googleAnalysis, getLoginInfo());

    return serviceResult;
  }

  public CouponRule getCouponRule() {
    return CommonLogic.getCouponRule(getLoginInfo());
  }

  public ServiceResult updateCouponRule(CouponRule couponRule) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CouponRuleDao dao = DIContainer.getDao(CouponRuleDao.class);

    CouponRule orgCouponRule = getCouponRule();

    // サイト未登録時エラー
    // if (orgCouponRule == null) {
    // serviceResult.addServiceError(SiteManagementServiceErrorContent.SITE_NO_DEF_FOUND_ERROR);
    // return serviceResult;
    // }

    // データのコピー

    couponRule.setOrmRowid(orgCouponRule.getOrmRowid());
    couponRule.setCreatedDatetime(orgCouponRule.getCreatedDatetime());
    couponRule.setCreatedUser(orgCouponRule.getCreatedUser());
    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(couponRule).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }
    // 更新処理実行
    dao.update(couponRule, getLoginInfo());

    return serviceResult;
  }
  // add by ytw end 2010/06/10

  public Advert getAdvert(Long AdvertNo) {
    AdvertDao ad = DIContainer.getDao(AdvertDao.class);
    return ad.load(AdvertNo);
  }
  
  public ServiceResult updateAdvert(Advert advert) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    AdvertDao dao = DIContainer.getDao(AdvertDao.class);

    Advert orgAdvert = getAdvert(advert.getAdvertNo());

    // サイト未登録時エラー
    if (orgAdvert == null) {
      serviceResult.addServiceError(SiteManagementServiceErrorContent.SITE_NO_DEF_FOUND_ERROR);
      return serviceResult;
    }

    // データのコピー


    advert.setOrmRowid(orgAdvert.getOrmRowid());
    advert.setCreatedDatetime(orgAdvert.getCreatedDatetime());
    advert.setCreatedUser(orgAdvert.getCreatedUser());

    // Validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(advert).getErrors();

    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      Logger logger = Logger.getLogger(this.getClass());
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    // 更新処理実行
    dao.update(advert, getLoginInfo());

    return serviceResult;
  }
  
  public List<Advert> getEnabledAdvert(String advertType) {
    Query q = new SimpleQuery(AdvertInfoQuery.LOAD_ENABLED_ADVERT_QUERY, advertType);
    List<Advert> advertList = DatabaseUtil.loadAsBeanList(q, Advert.class);

    return advertList;
  }
  
  public List<Advert> getAdvertByType(String advertType) {
    Query q = new SimpleQuery(AdvertInfoQuery.LOAD_ADVERT_QUERY, advertType);
    List<Advert> advertList = DatabaseUtil.loadAsBeanList(q, Advert.class);

    return advertList;
  }
}
