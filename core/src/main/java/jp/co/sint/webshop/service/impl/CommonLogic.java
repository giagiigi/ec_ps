package jp.co.sint.webshop.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.dao.CommissionDao;
import jp.co.sint.webshop.data.dao.CouponRuleDao;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.GoogleAnalysisDao;
import jp.co.sint.webshop.data.dao.OnlineServiceDao;
import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.dao.PointRuleDao;
import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.PartsCode;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.GoogleAnalysis;
import jp.co.sint.webshop.data.dto.OnlineService;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.customer.PointHistoryQuery;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.service.shop.ShopManagementSimpleSql;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.IpUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public final class CommonLogic {

  private CommonLogic() {

  }

  public static PointRule getPointRule(LoginInfo loginInfo) {
    PointRuleDao dao = DIContainer.getDao(PointRuleDao.class);
    List<PointRule> pointRuleList = dao.loadAll();
    if (pointRuleList.size() > 0) {
      return pointRuleList.get(0);
    }
    return new PointRule();
  }

  // add by shikui start 2010/04/28
  public static OnlineService getOnlineService(String shopCode) {
    OnlineServiceDao dao = DIContainer.getDao(OnlineServiceDao.class);
    // modify by V10-CH start
    // List<OnlineService> OnlineServiceList = dao.loadAll();
    String sqlString = "SELECT * FROM ONLINE_SERVICE WHERE SHOP_CODE = ?";
    List<OnlineService> OnlineServiceList = dao.findByQuery(sqlString, shopCode);
    // modify by V10-CH end
    if (OnlineServiceList.size() > 0) {
      return OnlineServiceList.get(0);
    } else {
      OnlineService onlineservice = new OnlineService();
      onlineservice.setEnabledFlg(Long.parseLong("1"));
      onlineservice.setShopCode(shopCode);
      return onlineservice;
    }
  }

  // add by shikui end 2010/04/28

  // add by ytw start 2010/06/10
  public static GoogleAnalysis getGoogleAnalysis(LoginInfo loginInfo) {
    GoogleAnalysisDao dao = DIContainer.getDao(GoogleAnalysisDao.class);
    List<GoogleAnalysis> GoogleAnalysisList = dao.loadAll();
    if (GoogleAnalysisList.size() > 0) {
      return GoogleAnalysisList.get(0);
    }
    return new GoogleAnalysis();
  }

  // add by ytw end 2010/06/10

  public static PaymentMethodSuite getPaymentMethod(String shopCode, Long paymentMethodNo, LoginInfo loginInfo) {
    PaymentMethodDao paymentMethodDao = DIContainer.getDao(PaymentMethodDao.class);
    CommissionDao commissionDao = DIContainer.getDao(CommissionDao.class);

    Query commissionQuery = new SimpleQuery(ShopManagementSimpleSql.LOAD_COMMISSION_LIST, shopCode, paymentMethodNo);
    List<Commission> commissionList = commissionDao.findByQuery(commissionQuery);

    PaymentMethod method = paymentMethodDao.load(shopCode, paymentMethodNo);

    PaymentMethodSuite methodSuite = new PaymentMethodSuite();
    methodSuite.setPaymentMethod(method);
    methodSuite.setCommissionList(commissionList);

    return methodSuite;

  }

  public static boolean checkShopIsOpen(String shopCode) {
    ShopDao dao = DIContainer.getDao(ShopDao.class);
    Shop s = dao.load(shopCode);
    if (s == null) {
      return false;
    }
    Date openDate = BeanUtil.coalesce(s.getOpenDatetime(), DateUtil.getMin());
    Date closeDate = BeanUtil.coalesce(s.getCloseDatetime(), DateUtil.getMax());
    DateRange openSpan = new DateRange(openDate, closeDate);
    return openSpan.includes(DateUtil.getSysdate());
  }

  public static boolean checkCustomerIsValid(String customerCode) {
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    Customer c = dao.load(customerCode);
    if (c == null) {
      return false;
    }
    return !c.getCustomerStatus().equals(CustomerStatus.WITHDRAWED.longValue());
  }

  public static String generateCustomerCode() {
    Long value = DatabaseUtil.generateSequence(SequenceType.CUSTOMER_CODE);
    NumberFormat nf = new DecimalFormat("0000000000000000");
    return nf.format(value);
  }

  public static String generateGuestCode() {
    Long value = DatabaseUtil.generateSequence(SequenceType.GUEST_CODE);
    NumberFormat nf = new DecimalFormat("1000000000000000");
    return nf.format(value);
  }

  public static String getCategorySearchPath(String path, String categoryCode) {
    if (StringUtil.hasValueAllOf(path, categoryCode)) {
      return path + "~" + categoryCode;
    } else {
      return null;
    }
  }

  public static String getCategorySearchPath(String categoryCode) {
    String result = null;
    if (StringUtil.hasValue(categoryCode)) {
      CategoryDao catDao = DIContainer.getDao(CategoryDao.class);
      Category cat = catDao.load(categoryCode);
      if (cat != null) {
        result = getCategorySearchPath(cat.getPath(), categoryCode);
      }
    }
    return result;
  }

  // 10.1.2 10094 追加 ここから
  public static List<CommodityLayout> createCommodityLayout(String shopCode) {
    List<CommodityLayout> layoutList = new ArrayList<CommodityLayout>();
    long index = 0;
    for (PartsCode pc : PartsCode.values()) {
      CommodityLayout layout = new CommodityLayout();
      layout.setShopCode(shopCode);
      layout.setPartsCode(pc.getValue());
      layout.setDisplayFlg(NumUtil.toLong(DisplayFlg.VISIBLE.getValue()));
      layout.setDisplayOrder(index);
      layoutList.add(layout);
      index++;
    }
    return layoutList;
  }

  // 10.1.2 10094 追加 ここまで

  // 10.1.3 10174 追加 ここから
  public static void verifyPointDifference(String customerCode, PointIssueStatus pointIssueStatus) {
    PointRuleDao ruleDao = DIContainer.getDao(PointRuleDao.class);
    List<PointRule> pointRule = ruleDao.loadAll();
    if (pointRule.size() == 0 || pointRule.get(0).getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.DISABLED.longValue())) {
      return;
    }
    CustomerDao custDao = DIContainer.getDao(CustomerDao.class);
    Customer customer = custDao.load(customerCode);
    if (customer == null) {
      return;
    }
    Query query = new SimpleQuery(PointHistoryQuery.LOAD_ISSUED_POINT_SUMMARY, customerCode, pointIssueStatus.getValue());
    Long sumOfPointHistory = NumUtil.coalesce(DatabaseUtil.executeScalar(query, Long.class), 0L);
    // 10.1.4 K00168 修正 ここから
    // Long customerPoint = 0L;
    BigDecimal customerPoint = null;
    // 10.1.4 K00168 修正 ここまで
    if (pointIssueStatus.equals(PointIssueStatus.ENABLED)) {
      customerPoint = NumUtil.coalesce(customer.getRestPoint(), BigDecimal.ZERO);
    } else if (pointIssueStatus.equals(PointIssueStatus.PROVISIONAL)) {
      customerPoint = NumUtil.coalesce(customer.getTemporaryPoint(), BigDecimal.ZERO);
    } else {
      return;
    }
    if (!customerPoint.equals(sumOfPointHistory)) {
      Logger logger = Logger.getLogger(CommonLogic.class);
      logger.info(MessageFormat.format("顧客マスタの{0}ポイントが、ポイント履歴の{0}ポイント合計と異なります。[顧客コード:{1} 顧客マスタのポイント:{2} ポイント履歴のポイント合計:{3}]",
          pointIssueStatus.getName(), customerCode, customerPoint, sumOfPointHistory));
    }
  }

  // 10.1.3 10174 追加 ここまで

  public static CouponRule getCouponRule(LoginInfo loginInfo) {
    CouponRuleDao dao = DIContainer.getDao(CouponRuleDao.class);
    List<CouponRule> couponRuleList = dao.loadAll();
    if (couponRuleList.size() > 0) {
      return couponRuleList.get(0);
    }
    return new CouponRule();
  }

  // 20111220 shen add start
  /**
   * 根据IP地址取得省市编号
   */
  public static String getPrefectureCodeByIPAddress(String ipAddress) {
    String prefectureCode = "";
    String addressName = IpUtil.getCountryString(ipAddress);
    if (StringUtil.hasValue(addressName) && addressName.equals(Messages.getString("service.impl.CommonLogic.0"))) {
      addressName = "";
    }
    if (StringUtil.hasValue(addressName)) {
      PrefectureDao prefectureDao = DIContainer.getDao(PrefectureDao.class);
      List<Prefecture> prefectureList = prefectureDao.loadAll();
      for (Prefecture refecture : prefectureList) {
        if (addressName.startsWith(refecture.getPrefectureName())) {
          prefectureCode = refecture.getPrefectureCode();
          break;
        }
      }
    }

    return prefectureCode;
  }
  // 20111220 shen add end

}
