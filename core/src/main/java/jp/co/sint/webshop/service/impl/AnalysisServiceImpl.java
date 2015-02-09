package jp.co.sint.webshop.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.StoredProcedure;
import jp.co.sint.webshop.data.StoredProcedureResult;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.dto.AccessLog;
import jp.co.sint.webshop.data.dto.CommodityAccessLog;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.analysis.AccessLogData;
import jp.co.sint.webshop.service.analysis.AnalysisServiceQuery;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogDeleteProcedure;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogSearchCondition;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogSearchQuery;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogSummary;
import jp.co.sint.webshop.service.analysis.Conversion;
import jp.co.sint.webshop.service.analysis.ConversionQuery;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.analysis.CustomerAccessLogDeleteProcedure;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewList;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewSearchCondition;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewSearchQuery;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceProcedure;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSearchCondition;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSearchQuery;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSummary;
import jp.co.sint.webshop.service.analysis.CustomerRegisterLogGraphQuery;
import jp.co.sint.webshop.service.analysis.CustomerStatisticsProcedure;
import jp.co.sint.webshop.service.analysis.CustomerStatisticsSummary;
import jp.co.sint.webshop.service.analysis.FmAnalysisQuery;
import jp.co.sint.webshop.service.analysis.FmAnalysisSummary;
import jp.co.sint.webshop.service.analysis.GiftCardUseLogListSearchQuery;
import jp.co.sint.webshop.service.analysis.GiftCardUseLogSearchCondition;
import jp.co.sint.webshop.service.analysis.GiftCardUseLogSummary;
import jp.co.sint.webshop.service.analysis.NewPublicCouponQuery;
import jp.co.sint.webshop.service.analysis.NewPublicCouponSearchCondition;
import jp.co.sint.webshop.service.analysis.NewPublicCouponSummary;
import jp.co.sint.webshop.service.analysis.OrderCountLogGraphQuery;
import jp.co.sint.webshop.service.analysis.PageView;
import jp.co.sint.webshop.service.analysis.PageViewLogGraphQuery;
import jp.co.sint.webshop.service.analysis.PageViewQuery;
import jp.co.sint.webshop.service.analysis.PlanSummaryViewInfo;
import jp.co.sint.webshop.service.analysis.PlanSummaryViewSearchCondition;
import jp.co.sint.webshop.service.analysis.PlanSummaryViewSearchQuery;
import jp.co.sint.webshop.service.analysis.PostgresDatabaseAnalysisQuery;
import jp.co.sint.webshop.service.analysis.RefererDeleteProcedure;
import jp.co.sint.webshop.service.analysis.RefererSearchCondition;
import jp.co.sint.webshop.service.analysis.RefererSearchQuery;
import jp.co.sint.webshop.service.analysis.RefererSummary;
import jp.co.sint.webshop.service.analysis.RfAnalysisQuery;
import jp.co.sint.webshop.service.analysis.RfAnalysisSummary;
import jp.co.sint.webshop.service.analysis.RfmAnalysisData;
import jp.co.sint.webshop.service.analysis.RfmAnalysisSearchCondition;
import jp.co.sint.webshop.service.analysis.RfmGenerateProcedure;
import jp.co.sint.webshop.service.analysis.RmAnalysisQuery;
import jp.co.sint.webshop.service.analysis.RmAnalysisSummary;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopGenerateProcedure;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopQuery;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopSearchCondition;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopSummary;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuGenerateProcedure;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSearchCondition;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSearchQuery;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSummary;
import jp.co.sint.webshop.service.analysis.SalesAmountQuery;
import jp.co.sint.webshop.service.analysis.SalesAmountSummary;
import jp.co.sint.webshop.service.analysis.SearchKeySummary;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogDeleteProcedure;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogSearchCondition;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogSearchQuery;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogSummary;
import jp.co.sint.webshop.service.analysis.UserAccessLogDeleteProcedure;
import jp.co.sint.webshop.service.analysis.WeekGraphData;
import jp.co.sint.webshop.service.analysis.WeekGraphSummary;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.IOUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class AnalysisServiceImpl extends AbstractServiceImpl implements AnalysisService {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 検索条件に問題がないかどうか判定します。 */
  private boolean hasConditionError(Object condition) {
    Logger logger = Logger.getLogger(this.getClass());
    ValidationSummary summary = BeanValidator.validate(condition);

    for (String s : summary.getErrorMessages()) {
      logger.error(s);
    }

    return summary.hasError();
  }

  /** 日付の範囲が正しいかどうか判定します。 */
  private boolean isValidDateRange(DateRange range) {
    if (range == null) {
      return false;
    }
    if (range.getStart() == null || range.getEnd() == null) {
      return false;
    }
    return range.isCorrect();
  }

  /** プロシージャを実行します */
  private ServiceResult executeProcedure(StoredProcedure procedure) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    StoredProcedureResult result = null;
    try {
      result = DatabaseUtil.executeProcedure(procedure);
    } catch (DataAccessException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    Map<String, Object> resultMap = result.getParameters();
    // 戻り値が0でない場合はエラー
    boolean succeed = true;
    for (Map.Entry<String, Object> e : resultMap.entrySet()) {
      succeed &= e.getValue().equals(0);
    }
    if (!succeed) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  /**
   * 与えられた検索条件に合致する商品アクセスログの集計結果を取得します
   * 
   * @param condition
   *          検索条件
   * @return アクセスログの集計結果
   */
  public SearchResult<CommodityAccessLogSummary> getCommodityAccessLog(CommodityAccessLogSearchCondition condition) {
    if (hasConditionError(condition)) {
      return new SearchResult<CommodityAccessLogSummary>();
    }

    CommodityAccessLogSearchQuery query = new CommodityAccessLogSearchQuery(condition);

    return DatabaseUtil.executeSearch(query);
  }

  public SearchResult<RefererSummary> getReferer(RefererSearchCondition condition) {
    if (hasConditionError(condition)) {
      return new SearchResult<RefererSummary>();
    }

    RefererSearchQuery query = new RefererSearchQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }

  public ServiceResult generateRfm() {
    return generateRfm(new DateRange(DateUtil.getMin(), DateUtil.getMax()));
  }

  public ServiceResult generateRfm(Date targetDay) {
    return generateRfm(new DateRange(targetDay, targetDay));
  }

  public ServiceResult generateRfm(DateRange period) {
    if (!isValidDateRange(period)) {
      ServiceResultImpl result = new ServiceResultImpl();
      result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);

      return result;
    }
    StoredProcedure procedure = new RfmGenerateProcedure(period.getStart(), period.getEnd());
    return executeProcedure(procedure);
  }

  public ServiceResult deleteCustomerAccessLog(int keepingMonth) {
    if (keepingMonth < 0) {
      ServiceResultImpl serviceResult = new ServiceResultImpl();
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }
    StoredProcedure procedure = new CustomerAccessLogDeleteProcedure(keepingMonth);

    return executeProcedure(procedure);
  }

  public ServiceResult deleteSearchKeywordLog(int keepingMonth) {
    if (keepingMonth < 0) {
      ServiceResultImpl serviceResult = new ServiceResultImpl();
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }
    StoredProcedure procedure = new SearchKeywordLogDeleteProcedure(keepingMonth);

    return executeProcedure(procedure);
  }

  public ServiceResult deleteUserAccessLog(int keepingMonth) {
    if (keepingMonth < 0) {
      ServiceResultImpl serviceResult = new ServiceResultImpl();
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }
    StoredProcedure procedure = new UserAccessLogDeleteProcedure(keepingMonth);

    return executeProcedure(procedure);
  }

  public ServiceResult deleteCommodityAccessLog(int keepingMonth) {
    if (keepingMonth < 0) {
      ServiceResultImpl serviceResult = new ServiceResultImpl();
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }
    StoredProcedure procedure = new CommodityAccessLogDeleteProcedure(keepingMonth);

    return executeProcedure(procedure);
  }

  public ServiceResult deleteReferer(int keepingMonth) {
    if (keepingMonth < 0) {
      ServiceResultImpl serviceResult = new ServiceResultImpl();
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }
    StoredProcedure procedure = new RefererDeleteProcedure(keepingMonth);

    return executeProcedure(procedure);
  }

  public List<CustomerStatisticsSummary> getCustomerStatistics() {
    SimpleQuery customerStatisticsQuery = new SimpleQuery(AnalysisServiceQuery.SELECT_CUSTOMER_STATISTICS, new Object[0]);

    return DatabaseUtil.loadAsBeanList(customerStatisticsQuery, CustomerStatisticsSummary.class);

  }

  private WeekGraphData getWeeklyGraphData(Query query) {
    WeekGraphData data = new WeekGraphData();
    data.setWeeklyList(DatabaseUtil.loadAsBeanList(query, WeekGraphSummary.class));

    return data;
  }

  public WeekGraphData getCustomerRegisterLogWeek() {
    return getWeeklyGraphData(new CustomerRegisterLogGraphQuery());
  }

  public WeekGraphData getOrderCountLogWeek(String shopCode) {
    return getWeeklyGraphData(new OrderCountLogGraphQuery(shopCode));
  }

  public WeekGraphData getPageViewLogWeek() {
    return getWeeklyGraphData(new PageViewLogGraphQuery());
  }

  public SearchResult<SearchKeywordLogSummary> getSearchKeywordLog(SearchKeywordLogSearchCondition condition) {
    if (hasConditionError(condition)) {
      return new SearchResult<SearchKeywordLogSummary>();
    }

    Logger logger = Logger.getLogger(this.getClass());
    if (condition.getSearchStartDate() != null && condition.getSearchEndDate() != null) {
      if (condition.getSearchEndDate().before(condition.getSearchStartDate())) {
        logger.error(Messages.log("service.impl.AnalysisServiceImpl.0"));
        return new SearchResult<SearchKeywordLogSummary>();
      }
    }

    return DatabaseUtil.executeSearch(new SearchKeywordLogSearchQuery(condition));
  }

  public ServiceResult generateCustomerPreference(int month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    if (month <= 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);

      return serviceResult;
    }

    StoredProcedure procedure = new CustomerPreferenceProcedure(month);
    return executeProcedure(procedure);
  }

  public ServiceResult generateCustomerStatistics() {
    StoredProcedure procedure = new CustomerStatisticsProcedure();

    return executeProcedure(procedure);
  }

  public ServiceResult generateSalesAmountByShop(Date countedDate) {
    return generateSalesAmountByShop(new DateRange(countedDate, countedDate));
  }

  public ServiceResult generateSalesAmountByShop(DateRange period) {
    if (!isValidDateRange(period)) {
      ServiceResultImpl result = new ServiceResultImpl();
      result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
    }

    StoredProcedure procedure = new SalesAmountByShopGenerateProcedure(period.getStart(), period.getEnd());

    return executeProcedure(procedure);
  }

  public ServiceResult generateSalesAmountBySku(DateRange period) {
    if (!isValidDateRange(period)) {
      ServiceResultImpl result = new ServiceResultImpl();
      result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
    }

    StoredProcedure procedure = new SalesAmountBySkuGenerateProcedure(period.getStart(), period.getEnd());

    return executeProcedure(procedure);
  }

  public ServiceResult generateSalesAmountByShop() {
    return generateSalesAmountByShop(new DateRange(DateUtil.getMin(), DateUtil.fromString(DateUtil.getSysdateString())));
  }

  public ServiceResult generateSalesAmountBySku(Date countedDate) {
    return generateSalesAmountBySku(new DateRange(countedDate, countedDate));
  }

  public ServiceResult generateSalesAmountBySku() {
    return generateSalesAmountBySku(new DateRange(DateUtil.getMin(), DateUtil.fromString(DateUtil.getSysdateString())));
  }

  public SearchResult<CustomerPreferenceSummary> getCustomerPreference(CustomerPreferenceSearchCondition condition) {
    if (hasConditionError(condition)) {
      return new SearchResult<CustomerPreferenceSummary>();
    }

    CustomerPreferenceSearchQuery query = new CustomerPreferenceSearchQuery(condition);

    return DatabaseUtil.executeSearch(query);
  }

  public RfmAnalysisData getRfmAnalysis(RfmAnalysisSearchCondition condition) {
    if (hasConditionError(condition)) {
      return new RfmAnalysisData();
    }
    List<RfAnalysisSummary> rfList = DatabaseUtil.loadAsBeanList(new RfAnalysisQuery(condition), RfAnalysisSummary.class);
    List<RmAnalysisSummary> rmList = DatabaseUtil.loadAsBeanList(new RmAnalysisQuery(condition), RmAnalysisSummary.class);
    List<FmAnalysisSummary> fmList = DatabaseUtil.loadAsBeanList(new FmAnalysisQuery(condition), FmAnalysisSummary.class);

    RfmAnalysisData data = new RfmAnalysisData();
    data.setRfAnalysisResult(rfList);
    data.setRmAnalysisResult(rmList);
    data.setFmAnalysisResult(fmList);

    return data;
  }

  public List<SalesAmountByShopSummary> getSalesAmountByShop(SalesAmountByShopSearchCondition condition) {
    return DatabaseUtil.loadAsBeanList(new SalesAmountByShopQuery(condition), SalesAmountByShopSummary.class);
  }

  public List<String> getSearchKey() {
    List<SearchKeySummary> searchKeyList = DatabaseUtil.loadAsBeanList(new SimpleQuery(AnalysisServiceQuery.SELECT_SEARCH_KEY,
        new Object[0]), SearchKeySummary.class);

    List<String> list = new ArrayList<String>();
    for (SearchKeySummary s : searchKeyList) {
      list.add(s.getSearchKey());
    }

    return list;
  }

  // Readerを受け取り、BufferedReaderでラップして返します
  private BufferedReader getBufferedReader(Reader reader) {
    BufferedReader bufReader = null;
    if (reader instanceof BufferedReader) {
      bufReader = (BufferedReader) reader;
    } else {
      bufReader = new BufferedReader(reader);
    }
    return bufReader;
  }

  public ServiceResult importAccessLog(Reader reader) {
    Logger logger = Logger.getLogger(this.getClass());

    BufferedReader bufRead = null;
    UserAgentManager manager = DIContainer.getUserAgentManager();
    ServiceResultImpl result = new ServiceResultImpl();
    String line;

    Map<String, Long> pageViewMap = new HashMap<String, Long>();
    Map<String, Set<String>> visitMap = new HashMap<String, Set<String>>();
    try {
      bufRead = getBufferedReader(reader);
      while ((line = bufRead.readLine()) != null) {
        String[] tokens = line.split(",", -1);

        if (tokens.length != 4) {
          logger.error(line);
          continue;
        }

        String accessDate = tokens[0];
        String accessTime = tokens[1];
        String sessionId = tokens[2];
        UserAgent agent = manager.identifyAgent(tokens[3]);

        if (accessDate == null || !DateUtil.isCorrect(accessDate) || accessDate.length() != 10) {
          continue;
        }

        if (!NumUtil.isNum(accessTime)) {
          continue;
        }

        Long time = NumUtil.toLong(accessTime);
        if (time < 0L || 23L < time) {
          continue;
        }

        // アクセス日、アクセス時刻,ユーザーエージェントを","でつないだものをキーにする
        String key = accessDate + "," + accessTime + "," + agent.getClientGroup();
        if (pageViewMap.containsKey(key)) {
          pageViewMap.put(key, pageViewMap.get(key) + 1);
        } else {
          pageViewMap.put(key, 1L);
        }

        if (visitMap.containsKey(key)) {
          visitMap.get(key).add(sessionId);
        } else {
          Set<String> s = new HashSet<String>();
          s.add(sessionId);
          visitMap.put(key, s);
        }
      }
    } catch (IOException e) {
      logger.error(e);
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      return result;
    } finally {
      IOUtil.close(bufRead);
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(this.getLoginInfo());
      int i = 0;
      for (Map.Entry<String, Long> e : pageViewMap.entrySet()) {
        String[] tokens = e.getKey().split(",", -1);

        Date accessDate = DateUtil.fromString(tokens[0]);
        if (accessDate == null) {
          continue;
        }
        Long accessTime = NumUtil.toLong(tokens[1]);
        String clientGroup = tokens[2];

        if (i == 0) {
          // 10.1.4 10083 修正 ここから
          // txMgr.executeUpdate(AnalysisServiceQuery.DELETE_ACCESS_LOG, accessDate);
          txMgr.executeUpdate(AnalysisServiceQuery.DELETE_ACCESS_LOG, accessDate, accessTime);
          // 10.1.4 10083 修正 ここまで
          i++;
        }

        AccessLog accessLog = new AccessLog();
        accessLog.setAccessDate(accessDate);
        accessLog.setAccessTime(accessTime);
        accessLog.setClientGroup(clientGroup);

        List<ValidationResult> validResultList = BeanValidator
            .partialValidate(accessLog, "accessDate", "accessTime", "clientGroup").getErrors();
        if (validResultList.size() > 0) {
          continue;
        }

        Object[] insertParams = new Object[] {
            DatabaseUtil.generateSequence(SequenceType.ACCESS_LOG_ID), accessDate, accessTime, clientGroup, e.getValue(), 0L, 0L,
            getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), getLoginInfo().getRecordingFormat(),
            DatabaseUtil.getSystemDatetime()
        };
        txMgr.executeUpdate(AnalysisServiceQuery.INSERT_ACCESS_LOG, insertParams);
      }

      for (Map.Entry<String, Set<String>> e : visitMap.entrySet()) {
        String[] tokens = e.getKey().split(",", -1);

        Date accessDate = DateUtil.fromString(tokens[0]);
        if (accessDate == null) {
          continue;
        }
        Long accessTime = NumUtil.toLong(tokens[1]);
        String clientGroup = tokens[2];

        Long visitCount = Long.valueOf((long) e.getValue().size());

        Date countStartDate = DateUtil.addHour(accessDate, accessTime.intValue());
        Date countEndDate = DateUtil.addHour(accessDate, accessTime.intValue() + 1);
        SimpleQuery query = new SimpleQuery(AnalysisServiceQuery.COUNT_PURCHASER, countStartDate, countEndDate, clientGroup);
        Long purchaserCount = Long.valueOf(DatabaseUtil.executeScalar(query).toString());

        Object[] params = new Object[] {
            visitCount, purchaserCount, this.getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), accessDate,
            accessTime, clientGroup
        };

        txMgr.executeUpdate(AnalysisServiceQuery.UPDATE_ACCESS_LOG, params);
      }
      txMgr.commit();
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
      throw e;
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult importReferer(Reader reader) {
    BufferedReader bufRead = null;
    ServiceResultImpl result = new ServiceResultImpl();

    Map<String, Long> refererMap = new HashMap<String, Long>();
    try {
      bufRead = getBufferedReader(reader);
      UserAgentManager manager = DIContainer.getUserAgentManager();
      String line;

      while ((line = bufRead.readLine()) != null) {
        String[] tokens = line.split(",", -1);

        if (tokens.length != 3) {
          continue;
        }

        String accessDate = tokens[0];
        String refererUrl = tokens[1];
        UserAgent agent = manager.identifyAgent(tokens[2]);

        if (accessDate == null || !DateUtil.isCorrect(accessDate) || accessDate.length() != 10) {
          continue;
        }

        // アクセス日、リファラURL、クライアントグループを","つなぎにしたものをマップのキーとする
        String key = accessDate + "," + refererUrl + "," + agent.getClientGroup();

        if (refererMap.containsKey(key)) {
          refererMap.put(key, refererMap.get(key) + 1);
        } else {
          refererMap.put(key, 1L);
        }
      }
    } catch (IOException e) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      return result;
    } finally {
      IOUtil.close(bufRead);
    }

    TransactionManager txManager = DIContainer.getTransactionManager();
    try {
      txManager.begin(this.getLoginInfo());
      int i = 0;
      for (Map.Entry<String, Long> e : refererMap.entrySet()) {
        String[] tokens = e.getKey().split(",");
        Date accessDate = DateUtil.fromString(tokens[0]);
        if (accessDate == null) {
          continue;
        }
        String refererUrl = tokens[1];
        String clientGroup = tokens[2];

        if (i == 0) {
          txManager.executeUpdate(AnalysisServiceQuery.DELETE_REFERER, accessDate);
          i++;
        }

        Object[] insertParams = new Object[] {
            DatabaseUtil.generateSequence(SequenceType.REFERER_ID), accessDate, clientGroup, refererUrl, e.getValue(),
            getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime(), getLoginInfo().getRecordingFormat(),
            DatabaseUtil.getSystemDatetime()
        };

        txManager.executeUpdate(AnalysisServiceQuery.INSERT_REFERER, insertParams);
      }
      txManager.commit();
    } catch (RuntimeException e) {
      txManager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txManager.dispose();
    }

    return result;
  }

  public ServiceResult importCommodityAccessLog(Reader reader) {
    BufferedReader bufRead = null;
    ServiceResultImpl result = new ServiceResultImpl();

    Map<String, Long> commodityAccessLogMap = new HashMap<String, Long>();

    try {
      bufRead = getBufferedReader(reader);
      UserAgentManager manager = DIContainer.getUserAgentManager();
      String line;

      while ((line = bufRead.readLine()) != null) {
        String[] tokens = line.split(",", -1);

        if (tokens.length != 4) {
          continue;
        }

        String accessDate = tokens[0];
        String shopCode = tokens[1];
        String commodityCode = tokens[2];
        UserAgent agent = manager.identifyAgent(tokens[3]);

        if (accessDate == null || !DateUtil.isCorrect(accessDate) || accessDate.length() != 10) {
          continue;
        }
        // 10.1.3 10165 修正 ここから
        // System.out.println(accessDate);
        Logger logger = Logger.getLogger(this.getClass());
        logger.debug(accessDate);
        // 10.1.3 10165 修正 ここまで
        CommodityAccessLog accessLog = new CommodityAccessLog();
        accessLog.setAccessDate(DateUtil.fromString(tokens[0]));
        accessLog.setShopCode(shopCode);
        accessLog.setCommodityCode(commodityCode);
        accessLog.setClientGroup(agent.getClientGroup());

        List<ValidationResult> validResultList = BeanValidator.partialValidate(accessLog, "accessDate", "shopCode",
            "commodityCode", "clientGroup").getErrors();
        if (validResultList.size() > 0) {
          continue;
        }

        // アクセス日、ショップコード、商品コード、クライアントグループを","つなぎにしたものをマップのキーとする
        String key = accessDate + "," + shopCode + "," + commodityCode + "," + agent.getClientGroup();
        if (commodityAccessLogMap.containsKey(key)) {
          commodityAccessLogMap.put(key, commodityAccessLogMap.get(key) + 1);
        } else {
          commodityAccessLogMap.put(key, 1L);
        }
      }
    } catch (IOException e) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      return result;
    } finally {
      IOUtil.close(bufRead);
    }

    try {
      ShopDao shopDao = DIContainer.getDao(ShopDao.class);
      CommodityHeaderDao commodityDao = DIContainer.getDao(CommodityHeaderDao.class);

      TransactionManager txManager = DIContainer.getTransactionManager();
      try {
        txManager.begin(this.getLoginInfo());

        int i = 0;
        for (Map.Entry<String, Long> e : commodityAccessLogMap.entrySet()) {

          String[] tokens = e.getKey().split(",");
          Date accessDate = DateUtil.fromString(tokens[0]);
          if (accessDate == null) {
            continue;
          }

          if (i == 0) {
            txManager.executeUpdate(AnalysisServiceQuery.DELETE_COMMODITY_ACCESS_LOG, accessDate);
            i++;
          }

          if (tokens.length != 4) {
            continue;
          }

          String shopCode = tokens[1];
          String commodityCode = tokens[2];
          String clientGroup = tokens[3];

          Shop shop = shopDao.load(shopCode);
          CommodityHeader commodity = commodityDao.load(shopCode, commodityCode);
          if (shop == null || commodity == null) {
            continue;
          }
          String shopName = shop.getShopName();
          String commodityName = commodity.getCommodityName();

          Object[] insertParams = new Object[] {
              DatabaseUtil.generateSequence(SequenceType.COMMODITY_ACCESS_LOG_ID), accessDate, shopCode, commodityCode,
              clientGroup, shopName, commodityName, e.getValue(), getLoginInfo().getRecordingFormat(),
              DatabaseUtil.getSystemDatetime(), getLoginInfo().getRecordingFormat(), DatabaseUtil.getSystemDatetime()
          };

          txManager.executeUpdate(AnalysisServiceQuery.INSERT_COMMODITY_ACCSES_LOG, insertParams);

        }
        txManager.commit();
      } catch (RuntimeException e) {
        txManager.rollback();
        result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
        throw e;
      } finally {
        txManager.dispose();
      }
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }

    return result;
  }

  public AccessLogData getAccessLog(CountType type, int year, int month, int day, String clientGroup) {
    List<PageView> pageViewList = DatabaseUtil.loadAsBeanList(new PageViewQuery(type, year, month, day, clientGroup),
        PageView.class);
    List<Conversion> conversionList = DatabaseUtil.loadAsBeanList(new ConversionQuery(type, year, month, day, clientGroup),
        Conversion.class);

    AccessLogData data = new AccessLogData();
    data.setPageViewList(pageViewList);
    data.setConversionList(conversionList);

    return data;
  }

  public SearchResult<SalesAmountBySkuSummary> getSalesAmountBySku(SalesAmountBySkuSearchCondition condition) {
    if (hasConditionError(condition)) {
      return new SearchResult<SalesAmountBySkuSummary>();
    }

    return DatabaseUtil.executeSearch(new SalesAmountBySkuSearchQuery(condition));
  }
  
  public SearchResult<GiftCardUseLogSummary> getGiftCardUseLogList(GiftCardUseLogSearchCondition condition) {
    if (hasConditionError(condition)) {
      return new SearchResult<GiftCardUseLogSummary>();
    }

    return DatabaseUtil.executeSearch(new GiftCardUseLogListSearchQuery(condition));
  }

  public SalesAmountSummary getSalesAmountSite(DateRange period) {
    return DatabaseUtil.loadAsBean(new SalesAmountQuery(period, true), SalesAmountSummary.class);
  }

  public List<SalesAmountSummary> getSalesAmountShop(DateRange range) {
    return DatabaseUtil.loadAsBeanList(new SalesAmountQuery(range, false), SalesAmountSummary.class);
  }

  public Date getLatestAccessLogDate() {
    List<Map<String, String>> targetList = DatabaseUtil.loadAsMapList(new SimpleQuery(
        AnalysisServiceQuery.SELECT_LATEST_ACCESS_LOG_DATE));

    if (targetList.isEmpty()) {
      return null;
    }
    Map<String, String> target = targetList.get(0);
    Date result = DateUtil.fromString(target.get("ACCESS_DATE"));
    return DateUtil.addHour(result, NumUtil.toLong(target.get("ACCESS_TIME")).intValue());
  }

  public Date getLatestCommodityAccessLogDate() {
    return (Date) DatabaseUtil.executeScalar(new SimpleQuery(AnalysisServiceQuery.SELECT_LATEST_COMMODITY_ACCESS_LOG_DATE));
  }

  public Date getLatestRefererLogDate() {
    return (Date) DatabaseUtil.executeScalar(new SimpleQuery(AnalysisServiceQuery.SELECT_LATEST_REFERER_DATE));
  }
  
  public void databaseAnalysis() {
    ArrayList<String> tableNames = DatabaseUtil.executeScalarForTableAnalysis(new SimpleQuery(PostgresDatabaseAnalysisQuery.GET_ALL_TABLE));    
    for (int i = 0;i < tableNames.size();i++) {
      DatabaseUtil.executeAnalysisTable(new PostgresDatabaseAnalysisQuery(PostgresDatabaseAnalysisQuery.VACUUM_TABLE + tableNames.get(i) + ";"));
    }
  }

  /**
   * 根据查询条件取得促销企划分析信息
   * @param condition 查询条件
   * @return 
   */
public SearchResult<PlanSummaryViewInfo> getPlanSummaryViewList(PlanSummaryViewSearchCondition condition) {
	  return DatabaseUtil.executeSearch(new PlanSummaryViewSearchQuery(condition));
}

/**
 * 根据查询条件取得顾客组别优惠活动信息
 * 
 * @param condition 查询条件
 * 
 * @return 顾客组别优惠活动信息
 */
public SearchResult<CustomerGroupCampaignSummaryViewList> getCustomerGroupCampaignSummaryViewList(CustomerGroupCampaignSummaryViewSearchCondition condition) {
	    return DatabaseUtil.executeSearch(new CustomerGroupCampaignSummaryViewSearchQuery(condition));
}

@Override
public List<NewPublicCouponSummary> getNewPublicCoupon(NewPublicCouponSearchCondition condition) {
  
  return DatabaseUtil.loadAsBeanList(new NewPublicCouponQuery(condition), NewPublicCouponSummary.class);

}

}
