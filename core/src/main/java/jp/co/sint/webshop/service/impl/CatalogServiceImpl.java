package jp.co.sint.webshop.service.impl;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.TmallSendMailConfig;
import jp.co.sint.webshop.configure.TmallWarnStockSendMailConfig;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.InsertSearchKeywordLogProcedure;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.StockManager;
import jp.co.sint.webshop.data.StockUnit;
import jp.co.sint.webshop.data.StockUtil;
import jp.co.sint.webshop.data.StoredProceduedResultType;
import jp.co.sint.webshop.data.StoredProcedureResult;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.cache.CacheContainer;
import jp.co.sint.webshop.data.cache.CacheKey;
import jp.co.sint.webshop.data.cache.CacheRetriever;
import jp.co.sint.webshop.data.dao.ArrivalGoodsDao;
import jp.co.sint.webshop.data.dao.BrandDao;
import jp.co.sint.webshop.data.dao.CCommodityDetailDao;
import jp.co.sint.webshop.data.dao.CCommodityExtDao;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CSynchistoryDao;
import jp.co.sint.webshop.data.dao.CampaignCommodityDao;
import jp.co.sint.webshop.data.dao.CampaignDao;
import jp.co.sint.webshop.data.dao.CategoryAttributeDao;
import jp.co.sint.webshop.data.dao.CategoryAttributeValueDao;
import jp.co.sint.webshop.data.dao.CategoryCommodityDao;
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.dao.CommodityDescribeDao;
import jp.co.sint.webshop.data.dao.CommodityDetailDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CommodityLayoutDao;
import jp.co.sint.webshop.data.dao.CommodityMasterDao;
import jp.co.sint.webshop.data.dao.CommodityPriceChangeHistoryDao;
import jp.co.sint.webshop.data.dao.CommodityProductionDateDao;
import jp.co.sint.webshop.data.dao.CommoditySkuDao;
import jp.co.sint.webshop.data.dao.DeliveryTypeDao;
import jp.co.sint.webshop.data.dao.GiftCommodityDao;
import jp.co.sint.webshop.data.dao.GiftDao;
import jp.co.sint.webshop.data.dao.ImageUploadHistoryDao;
import jp.co.sint.webshop.data.dao.JdCategoryDao;
import jp.co.sint.webshop.data.dao.JdStockAllocationDao;
import jp.co.sint.webshop.data.dao.RelatedBrandDao;
import jp.co.sint.webshop.data.dao.RelatedCommodityADao;
import jp.co.sint.webshop.data.dao.RelatedSiblingCategoryDao;
import jp.co.sint.webshop.data.dao.ReviewSummaryDao;
import jp.co.sint.webshop.data.dao.SetCommodityCompositionDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.dao.StockRatioDao;
import jp.co.sint.webshop.data.dao.StockStatusDao;
import jp.co.sint.webshop.data.dao.TagCommodityDao;
import jp.co.sint.webshop.data.dao.TagDao;
import jp.co.sint.webshop.data.dao.TmallCategoryDao;
import jp.co.sint.webshop.data.dao.TmallCommodityPropertyDao;
import jp.co.sint.webshop.data.dao.TmallPropertyDao;
import jp.co.sint.webshop.data.dao.TmallPropertyValueDao;
import jp.co.sint.webshop.data.dao.TmallStockAllocationDao;
import jp.co.sint.webshop.data.dao.TmallSuitCommodityDao;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.CommoditySyncFlag;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.IsOrNot;
import jp.co.sint.webshop.data.domain.JdUseFlg;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.OnStockFlg;
import jp.co.sint.webshop.data.domain.OrderDownLoadStatus;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.data.domain.RatioType;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.StockRemainSwitch;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.CCommodityCynchro;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CSynchistory;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CandidateWord;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.data.dto.CategorySel;
import jp.co.sint.webshop.data.dto.CommodityDescribe;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.data.dto.CommodityProductionDate;
import jp.co.sint.webshop.data.dto.CommoditySku;
import jp.co.sint.webshop.data.dto.CustomerCommodity;
import jp.co.sint.webshop.data.dto.CyncroResult;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.data.dto.JdCategory;
import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.JsonUtil;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.PopularRankingDetail;
import jp.co.sint.webshop.data.dto.RelatedBrand;
import jp.co.sint.webshop.data.dto.RelatedCommodityA;
import jp.co.sint.webshop.data.dto.RelatedCommodityB;
import jp.co.sint.webshop.data.dto.RelatedSiblingCategory;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.data.dto.SearchKeywordLog;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.data.dto.StockRatio;
import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.data.dto.StockTemp;
import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.data.dto.TmallBrand;
import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.data.dto.TmallProperty;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.CCommodityHeadline;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CategoryViewUtil;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.CategoryViewUtil.PropertyKey;
import jp.co.sint.webshop.service.CategoryViewUtil.PropertyKeys;
import jp.co.sint.webshop.service.campain.CampaignMain;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSearchCondition;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSearchQuery;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSubscritionCount;
import jp.co.sint.webshop.service.catalog.AttributeData;
import jp.co.sint.webshop.service.catalog.AttributeFrontQuery;
import jp.co.sint.webshop.service.catalog.AutoRecommendSummaryProcedure;
import jp.co.sint.webshop.service.catalog.BrandData;
import jp.co.sint.webshop.service.catalog.BrandFrontQuery;
import jp.co.sint.webshop.service.catalog.BrandSearchCondition;
import jp.co.sint.webshop.service.catalog.BrandSearchQuery;
import jp.co.sint.webshop.service.catalog.CCommodityDeleteQuery;
import jp.co.sint.webshop.service.catalog.CCommodityDetailQuery;
import jp.co.sint.webshop.service.catalog.CCommodityHeaderQuery;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.CategoryAttributeHeader;
import jp.co.sint.webshop.service.catalog.CategoryAttributeHeaderQuery;
import jp.co.sint.webshop.service.catalog.CategoryData;
import jp.co.sint.webshop.service.catalog.CategoryDetail;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.service.catalog.CategoryQuery;
import jp.co.sint.webshop.service.catalog.CategorySummaryProcedure;
import jp.co.sint.webshop.service.catalog.ChildSetCommodityInfo;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.CommodityContainerQuery;
import jp.co.sint.webshop.service.catalog.CommodityCsvExportQuery;
import jp.co.sint.webshop.service.catalog.CommodityDeleteQuery;
import jp.co.sint.webshop.service.catalog.CommodityHistoryQuery;
import jp.co.sint.webshop.service.catalog.CommodityHistorySearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityImageSearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.CommodityKey;
import jp.co.sint.webshop.service.catalog.CommodityKeyBackQuery;
import jp.co.sint.webshop.service.catalog.CommodityKeyDetailRecommendBQuery;
import jp.co.sint.webshop.service.catalog.CommodityKeyFrontQuery;
import jp.co.sint.webshop.service.catalog.CommodityLayoutQuery;
import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityListSearchQuery;
import jp.co.sint.webshop.service.catalog.CommodityMasterEditInfo;
import jp.co.sint.webshop.service.catalog.CommodityMasterResult;
import jp.co.sint.webshop.service.catalog.CommodityMasterSearchCondition;
import jp.co.sint.webshop.service.catalog.CommodityMasterSearchQuery;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryCondition;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryQuery;
import jp.co.sint.webshop.service.catalog.ContainerAddInfo;
import jp.co.sint.webshop.service.catalog.ContainerBackQuery;
import jp.co.sint.webshop.service.catalog.ContainerFrontQuery;
import jp.co.sint.webshop.service.catalog.GiftCount;
import jp.co.sint.webshop.service.catalog.GiftQuery;
import jp.co.sint.webshop.service.catalog.ImageUploadSearchQuery;
import jp.co.sint.webshop.service.catalog.JdStockInfo;
import jp.co.sint.webshop.service.catalog.LeftMenuInfoQuery;
import jp.co.sint.webshop.service.catalog.LeftMenuListBean;
import jp.co.sint.webshop.service.catalog.PlanInfo;
import jp.co.sint.webshop.service.catalog.PlanQuery;
import jp.co.sint.webshop.service.catalog.Planline;
import jp.co.sint.webshop.service.catalog.PriceFrontQuery;
import jp.co.sint.webshop.service.catalog.RankingSearchResult;
import jp.co.sint.webshop.service.catalog.RankingSummaryProcedure;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedCampaign;
import jp.co.sint.webshop.service.catalog.RelatedCategory;
import jp.co.sint.webshop.service.catalog.RelatedCategoryQuery;
import jp.co.sint.webshop.service.catalog.RelatedCategorySearchCondition;
import jp.co.sint.webshop.service.catalog.RelatedGift;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseCommodity;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchQueryBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchQueryCampaign;
import jp.co.sint.webshop.service.catalog.RelatedSearchQueryGift;
import jp.co.sint.webshop.service.catalog.RelatedSearchQueryTag;
import jp.co.sint.webshop.service.catalog.RelatedTag;
import jp.co.sint.webshop.service.catalog.ResultBean;
import jp.co.sint.webshop.service.catalog.ReturnPolicySetQuery;
import jp.co.sint.webshop.service.catalog.ReviewData;
import jp.co.sint.webshop.service.catalog.ReviewSummaryFrontQuery;
import jp.co.sint.webshop.service.catalog.SalesChartsData;
import jp.co.sint.webshop.service.catalog.SearchKey;
import jp.co.sint.webshop.service.catalog.SetCommodityInfo;
import jp.co.sint.webshop.service.catalog.StockIOSearchCondition;
import jp.co.sint.webshop.service.catalog.StockIOSearchQuery;
import jp.co.sint.webshop.service.catalog.StockListQuery;
import jp.co.sint.webshop.service.catalog.StockListSearchCondition;
import jp.co.sint.webshop.service.catalog.StockListSearchInfo;
import jp.co.sint.webshop.service.catalog.StockOperationType;
import jp.co.sint.webshop.service.catalog.StockStatusCount;
import jp.co.sint.webshop.service.catalog.StockStatusQuery;
import jp.co.sint.webshop.service.catalog.StockWarnInfo;
import jp.co.sint.webshop.service.catalog.TagCount;
import jp.co.sint.webshop.service.catalog.TagSearchCondition;
import jp.co.sint.webshop.service.catalog.TagSearchQuery;
import jp.co.sint.webshop.service.catalog.TmallCommodityKeyBackQuery;
import jp.co.sint.webshop.service.catalog.TmallContainerBackQuery;
import jp.co.sint.webshop.service.catalog.TmallStockInfo;
import jp.co.sint.webshop.service.catalog.UpdateCategoryPathProcedure;
import jp.co.sint.webshop.service.catalog.UpdateCommodityPriceChangeHistoryQuery;
import jp.co.sint.webshop.service.catalog.CategoryInfo.CategoryInfoDetail;
import jp.co.sint.webshop.service.customer.CustomerGroupCount;
import jp.co.sint.webshop.service.customer.CustomerGroupQuery;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.stock.StockTempInfo;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallCommodityImg;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallManager;
import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.CommodityDescImageUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.FTPFileUpload;
import jp.co.sint.webshop.utility.FileUtil;
import jp.co.sint.webshop.utility.ImageUploadConfig;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StockQuantityUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.CurrencyValidator;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;

import org.apache.log4j.Logger;

public class CatalogServiceImpl extends AbstractServiceImpl implements CatalogService, Serializable {

  private static final long serialVersionUID = 1L;

  public ServiceResult deleteArrivalGoods(String shopCode, String skuCode, String email) {
    ArrivalGoodsDao dao = DIContainer.getDao(ArrivalGoodsDao.class);
    dao.delete(shopCode, skuCode, email);
    return new ServiceResultImpl();
  }

  public ServiceResult deleteCommodityArrivalGoods(String shopCode, String skuCode) {

    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();

    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(new SimpleQuery(CommodityDeleteQuery.getArrivalGoodsDeleteQuery(), shopCode, skuCode));
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult deleteCategory(List<String> categoryCodeList) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      for (String categoryCode : categoryCodeList) {
        for (String query : CatalogQuery.getDeleteCategoryQuery()) {
          txMgr.executeUpdate(query, categoryCode);
        }
      }

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  public ServiceResult deleteCommodity() {

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(CatalogQuery.DELETE_INDEX_COMMODITY);
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

  /**
   * 指定された商品SKUが削除可能か判定します。<BR>
   * 指定された商品SKUが、売上確定ステータス=未確定の出荷と関連付いている場合は削除不可とし、<BR>
   * falseを返します。
   * 
   * @param shopCode
   * @param skuCode
   * @return
   */
  private boolean isDeletableCommoditySku(String shopCode, String skuCode) {
    SimpleQuery query = new SimpleQuery(CommodityDeleteQuery.getNotFixedSaleCommodityCountQuery());
    query.setParameters(shopCode, skuCode);
    Long count = Long.valueOf(DatabaseUtil.executeScalar(query).toString());
    return count == 0L;
  }

  public ServiceResult deleteCommodity(String shopCode, String commodityCode) {
    ServiceResultImpl result = new ServiceResultImpl();

    // ショップコード、商品コードのnullチェック
    if (!StringUtil.hasValueAllOf(shopCode, commodityCode)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 指定された商品が削除可能かチェック

    List<CommodityDetail> detailList = getCommoditySku(shopCode, commodityCode);
    for (CommodityDetail detail : detailList) {
      if (!isDeletableCommoditySku(detail.getShopCode(), detail.getSkuCode())) {
        result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
        return result;
      }
    }

    // 商品削除トランザクション開始
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      for (CommodityDetail detail : detailList) {
        for (String query : CommodityDeleteQuery.getSkuDeleteQuery()) {
          txMgr.executeUpdate(query, detail.getShopCode(), detail.getSkuCode());
        }
      }
      for (String query : CommodityDeleteQuery.getCommodityDeleteQuery()) {
        txMgr.executeUpdate(query, shopCode, commodityCode);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult deleteCommoditySku(String shopCode, String skuCode) {
    ServiceResultImpl result = new ServiceResultImpl();

    // ショップコード、SKUコードのnullチェック
    if (!StringUtil.hasValueAllOf(shopCode, skuCode)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 指定された商品SKUが削除可能かチェック
    if (!isDeletableCommoditySku(shopCode, skuCode)) {
      // 商品削除エラーを返す
      result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
      return result;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      for (String deleteQuery : CommodityDeleteQuery.getSkuDeleteQuery()) {
        txMgr.executeUpdate(deleteQuery, shopCode, skuCode);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult deleteGift(String shopCode, String giftCode) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    // 関連付き商品数チェック
    List<GiftCount> giftList = getGiftList(shopCode);
    for (GiftCount gift : giftList) {
      if (gift.getGiftCode().equals(giftCode) && gift.getRelatedCount() > 0) {
        // サービスエラーの追加
        logger.debug("related commodity");
        result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        return result;
      }
    }

    // 削除処理
    GiftDao gDao = DIContainer.getDao(GiftDao.class);

    if (gDao.load(shopCode, giftCode) == null) {
      logger.debug(Messages.log("service.impl.CatalogServiceImpl.0"));
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    gDao.delete(shopCode, giftCode);

    return result;
  }

  public ServiceResult deleteStockStatus(String shopCode, Long stockStatusNo) {
    ServiceResultImpl result = new ServiceResultImpl();

    if (StringUtil.isNullOrEmpty(shopCode) || NumUtil.isNull(stockStatusNo)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
    }

    Query query = new SimpleQuery(StockStatusQuery.getStockStatusQuery(), shopCode, stockStatusNo);
    StockStatusCount stockStatusCount = DatabaseUtil.loadAsBean(query, StockStatusCount.class);

    // 存在しない場合は既に削除対象がないので、成功とする
    if (stockStatusCount == null) {
      return result;
    }

    // 関連付いている商品がない場合は削除
    if (NumUtil.isNull(stockStatusCount.getRelatedCount())) {
      StockStatusDao dao = DIContainer.getDao(StockStatusDao.class);
      dao.delete(shopCode, stockStatusNo);
      return result;
    }

    result.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
    return result;
  }

  public ServiceResult deleteTag(String shopCode, String tagCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // 関連付いている商品がある場合は、削除不可とする
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_TAG, shopCode, tagCode);
    List<TagCommodity> tagCommodityList = DatabaseUtil.loadAsBeanList(query, TagCommodity.class);
    if (!tagCommodityList.isEmpty()) {
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    // 存在チェックを実行する

    TagDao dao = DIContainer.getDao(TagDao.class);
    if (dao.exists(shopCode, tagCode)) {
      dao.delete(shopCode, tagCode);
    } else {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
    }

    return serviceResult;
  }

  public List<CategoryInfo> getAllCategory() {
    Query query = new SimpleQuery(CatalogQuery.GET_CATEGORY_DEPTH_LIST);
    List<Category> depthList = DatabaseUtil.loadAsBeanList(query, Category.class);

    // 階層ごとにカテゴリ配列を作成
    List<List<CategoryInfo>> categoryTempList = new ArrayList<List<CategoryInfo>>();

    // 取得したカテゴリのリストを入れる
    for (Category dp : depthList) {

      // 階層ごとにカテゴリのリストを取得する
      Query categoryQuery = new SimpleQuery(CategoryQuery.GET_CATEGORY_LIST_QUERY, NumUtil.toString(dp.getDepth()));
      List<CategoryDetail> categoryDetailList = DatabaseUtil.loadAsBeanList(categoryQuery, CategoryDetail.class);

      // 取得結果の一時受け

      List<CategoryInfo> categoryTemp = new ArrayList<CategoryInfo>();

      String tmpCategoryCode = "";
      CategoryInfo categoryInfo = null;
      for (int i = 0; i < categoryDetailList.size(); i++) {
        CategoryDetail cd = categoryDetailList.get(i);

        if (!tmpCategoryCode.equals(cd.getCategoryCode())) {
          // カテゴリマスタに関する情報を設定
          categoryInfo = new CategoryInfo();
          categoryInfo.setCategoryCode(cd.getCategoryCode());
          categoryInfo.setCategoryNamePc(cd.getCategoryNamePc());
          // 20120514 tuxinwei add start
          categoryInfo.setCategoryNamePcEn(cd.getCategoryNamePcEn());
          categoryInfo.setCategoryNamePcJp(cd.getCategoryNamePcJp());
          // 20120514 tuxinwei add end
          categoryInfo.setCategoryNameMobile(cd.getCategoryNameMobile());
          categoryInfo.setCommodityCountPc(cd.getCommodityCountPc());
          categoryInfo.setCommodityCountMobile(cd.getCommodityCountMobile());
          categoryInfo.setDisplayOrder(cd.getDisplayOrder());
          categoryInfo.setDepth(cd.getDepth());
          categoryInfo.setParentCategoryCode(cd.getParentCategoryCode());
          categoryInfo.setPath(cd.getPath());
          categoryInfo.setOrmRowid(cd.getOrmRowid());
          categoryInfo.setUpdatedDatetime(cd.getUpdatedDatetime());
          // add by os012 20111221 start
          categoryInfo.setCategoryIdTmall(cd.getCategoryIdTmall());
          // add by os012 20111221 end
          // 2014/4/28 京东WBS对应 ob_李 add start
          categoryInfo.setCategoryIdJd(cd.getCategoryIdJd());
          // 2014/4/28 京东WBS对应 ob_李 add end
          categoryInfo.setMetaKeyword(cd.getMetaKeyword());
          categoryInfo.setMetaDescription(cd.getMetaDescription());
          categoryInfo.setKeywordCn1(cd.getKeywordCn1());
          categoryInfo.setKeywordCn2(cd.getKeywordCn2());
          categoryInfo.setKeywordEn1(cd.getKeywordEn1());
          categoryInfo.setKeywordEn2(cd.getKeywordEn2());
          categoryInfo.setKeywordJp1(cd.getKeywordJp1());
          categoryInfo.setKeywordJp2(cd.getKeywordJp2());
          // 20130703 txw add start
          categoryInfo.setTitle(cd.getTitle());
          categoryInfo.setTitleEn(cd.getTitleEn());
          categoryInfo.setTitleJp(cd.getTitleJp());
          categoryInfo.setDescription(cd.getDescription());
          categoryInfo.setDescriptionEn(cd.getDescriptionEn());
          categoryInfo.setDescriptionJp(cd.getDescriptionJp());
          categoryInfo.setKeyword(cd.getKeyword());
          categoryInfo.setKeywordEn(cd.getKeywordEn());
          categoryInfo.setKeywordJp(cd.getKeywordJp());
          // 20130703 txw add end
          categoryTemp.add(categoryInfo);
        }

        // カテゴリ属性に関する情報を設定
        CategoryInfoDetail categoryInfoDetail = new CategoryInfoDetail();
        categoryInfoDetail.setCategoryAttributeNo(cd.getCategoryAttributeNo());
        categoryInfoDetail.setCategoryAttributeName(cd.getCategoryAttributeName());
        // add by cs_yuil 20120607 start
        categoryInfoDetail.setCategoryAttributeNameEn(cd.getCategoryAttributeNameEn());
        categoryInfoDetail.setCategoryAttributeNameJp(cd.getCategoryAttributeNameJp());
        // add by cs_yuil 20120607 end
        categoryInfoDetail.setOrmRowid(cd.getAttributeOrmRowid());
        categoryInfoDetail.setUpdatedDatetime(cd.getAttributeUpdatedDatetime());
        if (categoryInfo != null) {
          categoryInfo.getCategoryInfoDetailList().add(categoryInfoDetail);
        }

        tmpCategoryCode = cd.getCategoryCode();

      }
      categoryTempList.add(categoryTemp);
    }

    // 呼出元に戻すためのカテゴリのリストを生成する
    List<CategoryInfo> categoryResultList = new ArrayList<CategoryInfo>();

    // 階層ごとに分割されたリストを順次処理
    for (int i = 0; i < categoryTempList.size(); i++) {
      // 一時リストから階層ごとのカテゴリリストを取得

      List<CategoryInfo> category = categoryTempList.get(i);

      // 第1階層?第2階層のデータを入れる
      if (i == 0 || i == 1) {
        for (int j = 0; j < category.size(); j++) {
          CategoryInfo categoryResult = new CategoryInfo();
          categoryResult.setCategoryCode(category.get(j).getCategoryCode());
          categoryResult.setCategoryNamePc(category.get(j).getCategoryNamePc());
          // 20120514 tuxinwei add start
          categoryResult.setCategoryNamePcEn(category.get(j).getCategoryNamePcEn());
          categoryResult.setCategoryNamePcJp(category.get(j).getCategoryNamePcJp());
          // 20120514 tuxinwei add end
          categoryResult.setCategoryNameMobile(category.get(j).getCategoryNameMobile());
          categoryResult.setCommodityCountPc(category.get(j).getCommodityCountPc());
          categoryResult.setCommodityCountMobile(category.get(j).getCommodityCountMobile());
          categoryResult.setDepth(category.get(j).getDepth());
          categoryResult.setDisplayOrder(category.get(j).getDisplayOrder());
          categoryResult.setParentCategoryCode(category.get(j).getParentCategoryCode());
          categoryResult.setPath(category.get(j).getPath());
          categoryResult.setOrmRowid(category.get(j).getOrmRowid());
          // add by os012 20111221 start
          categoryResult.setCategoryIdTmall(category.get(j).getCategoryIdTmall());
          // add by os012 20111221 end
          // 2014/4/28 京东WBS对应 ob_李 add start
          categoryResult.setCategoryIdJd(category.get(j).getCategoryIdJd());
          // 2014/4/28 京东WBS对应 ob_李 add end
          categoryResult.setMetaKeyword(category.get(j).getMetaKeyword());
          categoryResult.setMetaDescription(category.get(j).getMetaDescription());
          categoryResult.setUpdatedDatetime(category.get(j).getUpdatedDatetime());
          categoryResult.setKeywordCn1(category.get(j).getKeywordCn1());
          categoryResult.setKeywordCn2(category.get(j).getKeywordCn2());
          categoryResult.setKeywordEn1(category.get(j).getKeywordEn1());
          categoryResult.setKeywordEn2(category.get(j).getKeywordEn2());
          categoryResult.setKeywordJp1(category.get(j).getKeywordCn1());
          categoryResult.setKeywordJp2(category.get(j).getKeywordJp2());
          categoryResult.setCategoryInfoDetailList(category.get(j).getCategoryInfoDetailList());
          // 20130703 txw add start
          categoryResult.setTitle(category.get(j).getTitle());
          categoryResult.setTitleEn(category.get(j).getTitleEn());
          categoryResult.setTitleJp(category.get(j).getTitleJp());
          categoryResult.setDescription(category.get(j).getDescription());
          categoryResult.setDescriptionEn(category.get(j).getDescriptionEn());
          categoryResult.setDescriptionJp(category.get(j).getDescriptionJp());
          categoryResult.setKeyword(category.get(j).getKeyword());
          categoryResult.setKeywordEn(category.get(j).getKeywordEn());
          categoryResult.setKeywordJp(category.get(j).getKeywordJp());
          // 20130703 txw add end
          categoryResultList.add(categoryResult);
        }
      } else {
        // 第3階層以降のデータを入れる
        for (int x = 1; x < categoryResultList.size(); x++) {
          int tmpCount = 1;
          for (int j = 0; j < category.size(); j++) {
            if ((categoryResultList.get(x).getCategoryCode()).equals(category.get(j).getParentCategoryCode())) {
              categoryResultList.add(x + tmpCount, category.get(j));
              category.remove(j);
              j = j - 1;
              tmpCount += 1;
            }
          }
        }
      }
    }
    return categoryResultList;
  }

  public ArrivalGoods getArrivalGoods(String shopCode, String skuCode, String email) {
    ArrivalGoodsDao dao = DIContainer.getDao(ArrivalGoodsDao.class);
    return dao.load(shopCode, skuCode, email);
  }

  public SearchResult<ArrivalGoodsSubscritionCount> getArrivalGoodsSubcriptionCountList(ArrivalGoodsSearchCondition condition) {
    ArrivalGoodsSearchQuery query = new ArrivalGoodsSearchQuery();
    return DatabaseUtil.executeSearch(query.createArrivalGoodsSearchQuery(condition));
  }

  public Category getCategory(String categoryCode) {
    CategoryDao dao = DIContainer.getDao(CategoryDao.class);
    return dao.load(categoryCode);
  }

  public CommodityInfo getSkuInfo(String shopCode, String skuCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(skuCode)) {
      return null;
    }

    CommodityHeaderDao hdao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityDetailDao ddao = DIContainer.getDao(CommodityDetailDao.class);
    StockDao sdao = DIContainer.getDao(StockDao.class);

    CommodityDetail detail = ddao.load(shopCode, skuCode);
    CommodityHeader header;
    Stock stock;

    if (detail != null) {
      header = hdao.load(shopCode, detail.getCommodityCode());
      stock = sdao.load(shopCode, detail.getSkuCode());
    } else {
      return null;
    }

    CommodityInfo commodity = new CommodityInfo();
    if (header != null) {
      commodity.setHeader(header);
      commodity.setDetail(detail);
      commodity.setStock(stock);
    } else {
      return null;
    }

    return commodity;
  }

  public CommodityInfo getCommodityInfo(String shopCode, String commodityCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return null;
    }

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    StockDao sDao = DIContainer.getDao(StockDao.class);

    CommodityInfo commodityInfo = new CommodityInfo();
    CommodityHeader header = hDao.load(shopCode, commodityCode);
    CommodityDetail detail;
    Stock stock;
    if (header != null) {
      detail = dDao.load(shopCode, header.getRepresentSkuCode());
    } else {
      return null;
    }

    if (detail != null) {
      stock = sDao.load(shopCode, detail.getSkuCode());
    } else {
      return null;
    }

    commodityInfo.setHeader(header);
    commodityInfo.setDetail(detail);
    commodityInfo.setStock(stock);

    return commodityInfo;
  }

  public List<CommodityDetail> getCommoditySku(String shopCode, String commodityCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return null;
    }

    String sql = CommodityDeleteQuery.getCommoditySkuQuery();
    CommodityDetailDao dao = DIContainer.getDao(CommodityDetailDao.class);
    Query query = new SimpleQuery(sql, shopCode, commodityCode);

    return dao.findByQuery(query);
  }

  public List<GiftCount> getGiftList(String shopCode) {
    if (StringUtil.isNullOrEmpty(shopCode)) {
      return Collections.emptyList();
    }
    Query query = new SimpleQuery(GiftQuery.getGiftCountQuety(), shopCode, shopCode);
    return DatabaseUtil.loadAsBeanList(query, GiftCount.class);
  }

  public List<Gift> getAvailableGiftList(String shopCode, String commodityCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return null;
    }

    Query query = new SimpleQuery(CatalogQuery.GET_GIFT_COMMODITY_LIST, shopCode, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, Gift.class);
  }

  public Category getRootCategory() {
    Query query = new SimpleQuery(CatalogQuery.GET_ROOT_CATEGORY);
    return DatabaseUtil.loadAsBean(query, Category.class);
  }

  public SearchResult<StockIODetail> getStockIOList(StockIOSearchCondition condition) {
    StockIOSearchQuery query = new StockIOSearchQuery();
    return DatabaseUtil.executeSearch(query.createStockIOSearchQuery(condition));
  }

  // 10.1.4 10036 追加 ここから
  public StockStatus getStockStatus(String shopCode, Long stockStatusNo) {
    if (StringUtil.isNullOrEmpty(shopCode) || NumUtil.isNull(stockStatusNo)) {
      return null;
    }
    StockStatusDao dao = DIContainer.getDao(StockStatusDao.class);
    return dao.load(shopCode, stockStatusNo);
  }

  // 10.1.4 10036 追加 ここまで

  public List<StockStatusCount> getStockStatusList(String shopCode) {
    if (StringUtil.isNullOrEmpty(shopCode)) {
      return Collections.emptyList();
    }
    Query query = new SimpleQuery(StockStatusQuery.getStockStatusListQuery(), shopCode);
    return DatabaseUtil.loadAsBeanList(query, StockStatusCount.class);
  }

  public List<TagCount> getTagList(String shopCode) {
    if (StringUtil.isNullOrEmpty(shopCode)) {
      return null;
    }

    Query q = new SimpleQuery(CatalogQuery.GET_TAG, shopCode, shopCode);
    List<TagCount> sqlResult = DatabaseUtil.loadAsBeanList(q, TagCount.class);
    return sqlResult;
  }

  public Tag getTag(String shopCode, String tagCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(tagCode)) {
      return null;
    }

    TagDao dao = DIContainer.getDao(TagDao.class);
    Tag tag = new Tag();
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(tagCode)) {
      tag = dao.load(shopCode, tagCode);
    }
    return tag;
  }

  public boolean hasStockCommodity(String shopCode, String commodityCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return false;
    }
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_VIEW_LIST_COMMODITY, shopCode, commodityCode);
    List<CommodityHeadline> headlineList = DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);

    if (headlineList.isEmpty()) {
      return false;
    }

    Long stockManagementType = headlineList.get(0).getStockManagementType();

    // 在庫管理しない場合はtrueを返す

    Long stockAmount = 0L;
    if (stockManagementType.equals(StockManagementType.WITH_QUANTITY.longValue())
        || stockManagementType.equals(StockManagementType.WITH_STATUS.longValue())) {

      // 1件でも在庫がある場合はtrueを返す

      for (CommodityHeadline ch : headlineList) {
        // 10.1.7 10309 修正 ここから
        // if (ch.getAvailableStockQuantity() == null) {
        if (ch.getAvailableStockQuantity() == null || ch.getAvailableStockQuantity() == -1) {
          // 10.1.7 10309 修正 ここまで
          return true;
        }
        stockAmount += ch.getAvailableStockQuantity();
      }
      return stockAmount > 0;

    } else if (stockManagementType.equals(StockManagementType.NOSTOCK.longValue())
        || stockManagementType.equals(StockManagementType.NONE.longValue())) {
      return true;
    }

    return stockAmount > 0L;
  }

  public ServiceResult insertArrivalGoods(ArrivalGoods arrivalGoods) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    ArrivalGoodsDao dao = DIContainer.getDao(ArrivalGoodsDao.class);

    setUserStatus(arrivalGoods);

    ValidationSummary summary = BeanValidator.validate(arrivalGoods);
    if (summary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return result;
    }

    if (dao.exists(arrivalGoods.getShopCode(), arrivalGoods.getSkuCode(), arrivalGoods.getEmail())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
    } else {
      dao.insert(arrivalGoods, getLoginInfo());
    }
    return result;
  }

  public ServiceResult insertCategory(CategoryInfo categoryInfo) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    Category insertCategory = new Category();
    insertCategory.setCategoryCode(categoryInfo.getCategoryCode());
    insertCategory.setCategoryNamePc(categoryInfo.getCategoryNamePc());
    // 20120514 tuxinwei add start
    insertCategory.setCategoryNamePcEn(categoryInfo.getCategoryNamePcEn());
    insertCategory.setCategoryNamePcJp(categoryInfo.getCategoryNamePcJp());
    // 20120514 tuxinwei add end
    insertCategory.setCategoryNameMobile(categoryInfo.getCategoryNameMobile());
    insertCategory.setCommodityCountPc(categoryInfo.getCommodityCountPc());
    insertCategory.setCommodityCountMobile(categoryInfo.getCommodityCountMobile());
    insertCategory.setDisplayOrder(categoryInfo.getDisplayOrder());
    insertCategory.setDepth(0L);
    insertCategory.setParentCategoryCode(categoryInfo.getParentCategoryCode());
    insertCategory.setPath("/");
    insertCategory.setCreatedUser(categoryInfo.getCreatedUser());
    insertCategory.setCreatedDatetime(categoryInfo.getCreatedDatetime());
    insertCategory.setUpdatedUser(categoryInfo.getUpdatedUser());
    insertCategory.setUpdatedDatetime(categoryInfo.getUpdatedDatetime());

    ValidationSummary summary = BeanValidator.validate(insertCategory);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    List<CategoryAttribute> categoryAttributeList = new ArrayList<CategoryAttribute>();

    for (CategoryInfoDetail ci : categoryInfo.getCategoryInfoDetailList()) {
      CategoryAttribute categoryAttribute = new CategoryAttribute();
      categoryAttribute.setCategoryAttributeNo(ci.getCategoryAttributeNo());
      categoryAttribute.setCategoryCode(insertCategory.getCategoryCode());
      categoryAttribute.setCategoryAttributeName(ci.getCategoryAttributeName());
      categoryAttribute.setCreatedUser(categoryInfo.getCreatedUser());
      categoryAttribute.setCreatedDatetime(categoryInfo.getCreatedDatetime());
      categoryAttribute.setUpdatedUser(categoryInfo.getUpdatedUser());
      categoryAttribute.setUpdatedDatetime(categoryInfo.getUpdatedDatetime());
      categoryAttributeList.add(categoryAttribute);

      summary = BeanValidator.validate(categoryAttribute);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }

    }

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    // カテゴリに対して重複チェックを実行する

    if (categoryDao.exists(insertCategory.getCategoryCode())) {
      logger.error(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    WebshopConfig config = DIContainer.getWebshopConfig();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());

      // カテゴリへの登録
      txMgr.insert(insertCategory);

      // カテゴリ属性の更新
      // 登録対象のカテゴリ属性番号が存在すれば更新

      // 登録対象のカテゴリ属性番号が存在しなければ登録

      CategoryAttributeDao attribueDao = DIContainer.getDao(CategoryAttributeDao.class);
      for (CategoryAttribute ca : categoryAttributeList) {
        if (attribueDao.exists(ca.getCategoryCode(), ca.getCategoryAttributeNo())) {
          txMgr.executeUpdate(CatalogQuery.UPDATE_CATEGORY_ATTRIBUTE, ca.getCategoryAttributeName(), this.getLoginInfo()
              .getRecordingFormat(), insertCategory.getUpdatedDatetime(), ca.getCategoryCode(), ca.getCategoryAttributeNo());
        } else {
          txMgr.insert(ca);
        }
      }

      // カテゴリパス、階層の計算、更新

      UpdateCategoryPathProcedure updatePathProc = new UpdateCategoryPathProcedure(insertCategory.getCategoryCode(), config
          .getCategoryMaxDepth(), getLoginInfo().getRecordingFormat());
      txMgr.executeProcedure(updatePathProc);

      if (updatePathProc.getResult() == UpdateCategoryPathProcedure.SUCCESS) {
        txMgr.commit();
      } else {
        txMgr.rollback();
        if (updatePathProc.getResult() == UpdateCategoryPathProcedure.MAX_DEPTH_OVER_ERROR) {
          serviceResult.addServiceError(CatalogServiceErrorContent.CATEGORY_MAX_DEPTH_OVER_ERROR);
        } else {
          serviceResult.addServiceError(CatalogServiceErrorContent.UPDATE_CATEGORY_PATH_ERROR);
        }
      }

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult insertCommodityInfo(CommodityInfo commodityInfo) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    CommodityHeader header = commodityInfo.getHeader();
    CommodityDetail detail = commodityInfo.getDetail();
    Stock stock = commodityInfo.getStock();
    // add by twh 2012-12-6 start
    // 套装商品登录时category_commodity表中得插入数据
    CategoryCommodity cCommdity = new CategoryCommodity();
    cCommdity.setCategoryCode("0");
    cCommdity.setCategorySearchPath("/~0~10~10100~10100100");
    cCommdity.setCommodityCode(header.getCommodityCode());
    cCommdity.setShopCode(header.getShopCode());
    cCommdity.setCreatedDatetime(header.getCreatedDatetime());
    cCommdity.setCreatedUser(header.getUpdatedUser());
    cCommdity.setOrmRowid(header.getOrmRowid());
    cCommdity.setUpdatedDatetime(header.getUpdatedDatetime());
    cCommdity.setUpdatedUser(header.getUpdatedUser());
    // add by twh 2012-12-6 end

    // 販売期間の開始/終了日時がnullの場合は、

    // システム最大(or最小)日付を設定する

    if (header.getSaleStartDatetime() == null) {
      // 販売開始日:最小

      header.setSaleStartDatetime(DateUtil.getMin());
    }
    if (header.getSaleEndDatetime() == null) {
      // 販売終了日:最大

      header.setSaleEndDatetime(DateUtil.getMax());
    }

    // 予約期間の開始/終了日時がnullの場合は最小システム日付を設定
    // 予約期間の開始日時がnullの場合は最小システム日付を設定
    if (header.getReservationStartDatetime() == null && header.getReservationEndDatetime() == null) {
      header.setReservationStartDatetime(DateUtil.getMin());
      header.setReservationEndDatetime(DateUtil.getMin());
    } else if (header.getReservationStartDatetime() == null) {
      header.setReservationStartDatetime(DateUtil.getMin());
    }

    // 在庫管理区分が「在庫管理する(状況表示)」以外の場合は、

    // 在庫状況番号にnullを設定し、値をクリアする

    StockManagementType stockManagementType = StockManagementType.fromValue(header.getStockManagementType());
    if (stockManagementType != StockManagementType.WITH_STATUS) {
      header.setStockStatusNo(null);
    }

    // 在庫レコードの生成

    stock.setShopCode(detail.getShopCode());
    stock.setSkuCode(detail.getSkuCode());
    stock.setCommodityCode(detail.getCommodityCode());
    stock.setStockQuantity(0L);
    stock.setAllocatedQuantity(0L);
    stock.setReservedQuantity(0L);

    if (NumUtil.isNull(stock.getStockThreshold())) {
      stock.setStockThreshold(0L);
    }

    setUserStatus(header);
    setUserStatus(detail);
    setUserStatus(stock);

    if (NumUtil.isNull(header.getRecommendCommodityRank())) {
      header.setRecommendCommodityRank(99999999L);
    }
    header.setCommodityPopularRank(99999999L);

    // バリデーションチェック

    ValidationSummary hSummary = BeanValidator.validate(header);
    if (hSummary.hasError()) {
      for (String error : hSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    ValidationSummary dSummay = BeanValidator.validate(detail);
    if (dSummay.hasError()) {
      for (String error : dSummay.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    ValidationSummary sSummary = BeanValidator.validate(stock);
    if (sSummary.hasError()) {
      for (String error : sSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 商品ヘッダ,商品SKU,在庫の重複チェック

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (hDao.exists(header.getShopCode(), header.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    if (dDao.exists(detail.getShopCode(), detail.getSkuCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    StockDao sDao = DIContainer.getDao(StockDao.class);
    if (sDao.exists(stock.getShopCode(), stock.getSkuCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(header);
      txMgr.insert(detail);
      txMgr.insert(stock);
      // add by twh 2012-12-6 start
      txMgr.insert(cCommdity);
      // add by twh 2012-12-6 end
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult insertCommoditySku(CommodityDetail sku, Stock stock) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    // 商品ヘッダの存在チェック

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (!hDao.exists(sku.getShopCode(), sku.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    setUserStatus(sku);
    stock.setStockQuantity(0L);
    stock.setAllocatedQuantity(0L);
    stock.setReservedQuantity(0L);
    if (NumUtil.isNull(stock.getStockThreshold())) {
      stock.setStockThreshold(0L);
    }

    setUserStatus(stock);

    // バリデーションチェック

    ValidationSummary skuSummary = BeanValidator.validate(sku);
    if (skuSummary.hasError()) {
      for (String error : skuSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    ValidationSummary stockSummary = BeanValidator.validate(sku);
    if (stockSummary.hasError()) {
      for (String error : stockSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 商品SKU,在庫の重複チェック

    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    if (dDao.exists(sku.getShopCode(), sku.getSkuCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    StockDao sDao = DIContainer.getDao(StockDao.class);
    if (sDao.exists(stock.getShopCode(), stock.getSkuCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    CommodityHeader header = hDao.load(sku.getShopCode(), sku.getCommodityCode());
    boolean faultStandardName = false;
    if (StringUtil.hasValueAllOf(header.getCommodityStandard1Name(), header.getCommodityStandard2Name())) {
      if (!StringUtil.hasValueAllOf(sku.getStandardDetail1Name(), sku.getStandardDetail2Name())) {
        faultStandardName = true;
      }
    } else if (StringUtil.hasValue(header.getCommodityStandard1Name())) {
      if (StringUtil.isNullOrEmpty(sku.getStandardDetail1Name()) || StringUtil.hasValue(sku.getStandardDetail2Name())) {
        faultStandardName = true;
      }
    } else if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name())
        && StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())) {
      if (StringUtil.hasValue(sku.getStandardDetail1Name()) || StringUtil.hasValue(sku.getStandardDetail2Name())) {
        faultStandardName = true;
      }
    }
    if (faultStandardName) {
      result.addServiceError(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR);
      return result;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(sku);
      txMgr.insert(stock);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult insertGift(Gift gift) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    setUserStatus(gift);

    // バリデーションチェック

    ValidationSummary summary = BeanValidator.validate(gift);
    if (summary.hasError()) {
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 重複チェック
    GiftDao dao = DIContainer.getDao(GiftDao.class);
    if (dao.exists(gift.getShopCode(), gift.getGiftCode())) {
      logger.debug("exists gift.");
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    // 登録処理
    dao.insert(gift, getLoginInfo());

    return result;
  }

  public ServiceResult insertRelatedCommodityA(RelatedCommodityA relatedCommodityA) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(relatedCommodityA);

    ValidationSummary summary = BeanValidator.validate(relatedCommodityA);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    if ((relatedCommodityA.getCommodityCode()).equals(relatedCommodityA.getLinkCommodityCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    RelatedCommodityA oppositeCommodityA = new RelatedCommodityA();
    oppositeCommodityA.setShopCode(relatedCommodityA.getShopCode());
    oppositeCommodityA.setCommodityCode(relatedCommodityA.getLinkCommodityCode());
    oppositeCommodityA.setLinkCommodityCode(relatedCommodityA.getCommodityCode());
    oppositeCommodityA.setDisplayOrder(relatedCommodityA.getDisplayOrder());
    oppositeCommodityA.setCreatedUser(relatedCommodityA.getCreatedUser());
    oppositeCommodityA.setCreatedDatetime(relatedCommodityA.getCreatedDatetime());
    oppositeCommodityA.setUpdatedUser(relatedCommodityA.getUpdatedUser());
    oppositeCommodityA.setUpdatedDatetime(relatedCommodityA.getUpdatedDatetime());

    summary = BeanValidator.validate(oppositeCommodityA);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CommodityHeaderDao commodityDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (!commodityDao.exists(relatedCommodityA.getShopCode(), relatedCommodityA.getCommodityCode())
        || !commodityDao.exists(relatedCommodityA.getShopCode(), relatedCommodityA.getLinkCommodityCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      RelatedCommodityADao dao = DIContainer.getDao(RelatedCommodityADao.class);
      // 順方向の関連付けを行う
      if (!dao.exists(relatedCommodityA.getShopCode(), relatedCommodityA.getCommodityCode(), relatedCommodityA
          .getLinkCommodityCode())) {
        txMgr.insert(relatedCommodityA);
      }

      // 逆方向の関連付けを行う
      if (!dao.exists(oppositeCommodityA.getShopCode(), oppositeCommodityA.getCommodityCode(), oppositeCommodityA
          .getLinkCommodityCode())) {
        txMgr.insert(oppositeCommodityA);
      }
      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  public ServiceResult insertRelatedGift(GiftCommodity giftCommodity) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(giftCommodity);

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(giftCommodity);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CommodityHeaderDao commodityDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (!commodityDao.exists(giftCommodity.getShopCode(), giftCommodity.getCommodityCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    GiftCommodityDao dao = DIContainer.getDao(GiftCommodityDao.class);

    // 重複チェックを実行する

    if (dao.exists(giftCommodity.getShopCode(), giftCommodity.getGiftCode(), giftCommodity.getCommodityCode())) {
      logger.error(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);

    } else {
      dao.insert(giftCommodity, getLoginInfo());
    }
    return serviceResult;
  }

  public ServiceResult insertRelatedTag(TagCommodity tagCommodity) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(tagCommodity);

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(tagCommodity);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CommodityHeaderDao commodityDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (!commodityDao.exists(tagCommodity.getShopCode(), tagCommodity.getCommodityCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    TagCommodityDao dao = DIContainer.getDao(TagCommodityDao.class);

    // 重複チェックを実行する

    if (dao.exists(tagCommodity.getShopCode(), tagCommodity.getTagCode(), tagCommodity.getCommodityCode())) {
      logger.error(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);

    } else {
      dao.insert(tagCommodity, getLoginInfo());
    }
    return serviceResult;
  }

  public ServiceResult insertStockIO(StockIODetail detail) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    StockDao dao = DIContainer.getDao(StockDao.class);
    Stock stock = dao.load(detail.getShopCode(), detail.getSkuCode());

    if (stock == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CommodityDetailDao detailDao = DIContainer.getDao(CommodityDetailDao.class);
    CommodityDetail commodityDetail = detailDao.load(detail.getShopCode(), detail.getSkuCode());
    if (commodityDetail == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CommodityHeaderDao headerDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader commodityHeader = headerDao.load(commodityDetail.getShopCode(), commodityDetail.getCommodityCode());
    if (commodityHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    StockUnit stockUnit = new StockUnit();
    stockUnit.setShopCode(detail.getShopCode());
    stockUnit.setStockIODate(DateUtil.getSysdate());
    stockUnit.setSkuCode(detail.getSkuCode());
    stockUnit.setQuantity(detail.getStockIOQuantity().intValue());
    stockUnit.setLoginInfo(getLoginInfo());
    stockUnit.setMemo(detail.getMemo());

    // 入庫?出庫?在庫の違いにより更新する在庫数を変更
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      boolean result = false;

      txMgr.begin(getLoginInfo());
      StockManager stockManager = txMgr.getStockManager();

      if (detail.getStockIOType().equals(StockOperationType.ENTRY.longValue())) {
        // 入庫の場合

        result = stockManager.entry(stockUnit);

      } else if (detail.getStockIOType().equals(StockOperationType.DELIVER.longValue())) {
        // 出庫の場合

        result = stockManager.deliver(stockUnit);

      } else if (detail.getStockIOType().equals(StockOperationType.ALLOCATE.longValue())) {
        // 引当の場合

        result = stockManager.liquidatedAllocate(stockUnit);

      } else if (detail.getStockIOType().equals(StockOperationType.RESERVING.longValue())) {
        // 予約の場合

        result = stockManager.liquidatedReserving(stockUnit);

      } else if (detail.getStockIOType().equals(StockOperationType.DEALLOCATE.longValue())) {
        // 引当取消の場合

        result = stockManager.deallocate(stockUnit);

      } else if (detail.getStockIOType().equals(StockOperationType.CANCEL_RESERVING.longValue())) {
        // 予約取消の場合

        result = stockManager.cancelReserving(stockUnit);

      } else {
        // 存在しない入出庫区分
        serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
        txMgr.rollback();
        return serviceResult;
      }

      if (!result) {
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
        txMgr.rollback();
        return serviceResult;
      }

      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      logger.error(e);
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult insertStockStatus(StockStatus stockStatus) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 在庫状況番号を生成する

    stockStatus.setStockStatusNo(DatabaseUtil.generateSequence(SequenceType.STOCK_STATUS_NO));
    setUserStatus(stockStatus);

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(stockStatus);
    if (summary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 重複チェックを実行する

    StockStatusDao dao = DIContainer.getDao(StockStatusDao.class);
    if (dao.exists(stockStatus.getShopCode(), stockStatus.getStockStatusNo())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }

    dao.insert(stockStatus, getLoginInfo());

    return result;
  }

  public ServiceResult insertTag(Tag tag) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(tag);

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(tag);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return serviceResult;
    }

    TagDao dao = DIContainer.getDao(TagDao.class);

    // 重複チェックを実行する

    if (dao.exists(tag.getShopCode(), tag.getTagCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    dao.insert(tag, getLoginInfo());

    return serviceResult;
  }

  public ServiceResult updateCategory(CategoryInfo categoryInfo) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    Category category = categoryDao.load(categoryInfo.getCategoryCode());

    if (category == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    category.setParentCategoryCode(categoryInfo.getParentCategoryCode());
    category.setCategoryNamePc(categoryInfo.getCategoryNamePc());
    // 20120514 tuxinwei add start
    category.setCategoryNamePcEn(categoryInfo.getCategoryNamePcEn());
    category.setCategoryNamePcJp(categoryInfo.getCategoryNamePcJp());
    // 20120514 tuxinwei add end
    category.setCategoryNameMobile(categoryInfo.getCategoryNameMobile());
    category.setDisplayOrder(categoryInfo.getDisplayOrder());
    category.setUpdatedDatetime(categoryInfo.getUpdatedDatetime());

    ValidationSummary summary = BeanValidator.validate(category);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    // カテゴリ属性名の更新

    List<CategoryAttribute> categoryAttributeList = new ArrayList<CategoryAttribute>();
    for (CategoryInfoDetail ci : categoryInfo.getCategoryInfoDetailList()) {
      CategoryAttribute categoryAttribute = new CategoryAttribute();
      categoryAttribute.setCategoryAttributeNo(ci.getCategoryAttributeNo());
      categoryAttribute.setCategoryCode(categoryInfo.getCategoryCode());
      categoryAttribute.setCategoryAttributeName(ci.getCategoryAttributeName());
      categoryAttribute.setOrmRowid(ci.getOrmRowid());
      categoryAttribute.setCreatedUser(categoryInfo.getCreatedUser());
      categoryAttribute.setCreatedDatetime(categoryInfo.getCreatedDatetime());
      categoryAttribute.setUpdatedUser(categoryInfo.getUpdatedUser());
      categoryAttribute.setUpdatedDatetime(ci.getUpdatedDatetime());
      summary = BeanValidator.validate(categoryAttribute);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }
      categoryAttributeList.add(categoryAttribute);
    }

    WebshopConfig config = DIContainer.getWebshopConfig();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(category);

      // カテゴリ属性の更新
      // 登録対象のカテゴリ属性番号が存在すれば更新

      logger.debug(Messages.log("service.impl.CatalogServiceImpl.1"));
      CategoryAttributeDao attribueDao = DIContainer.getDao(CategoryAttributeDao.class);
      for (CategoryAttribute ca : categoryAttributeList) {
        if (attribueDao.exists(ca.getCategoryCode(), ca.getCategoryAttributeNo())) {
          if (StringUtil.hasValue(ca.getCategoryAttributeName())) {
            // 10.1.4 10127 修正 ここから
            txMgr.executeUpdate(CatalogQuery.UPDATE_CATEGORY_ATTRIBUTE, ca.getCategoryAttributeName(), this.getLoginInfo()
                .getRecordingFormat(), DateUtil.getSysdate(), ca.getCategoryCode(), ca.getCategoryAttributeNo());
            // 10.1.4 10127 修正 ここまで
          } else {
            txMgr.delete(ca);
          }
        } else {
          if (StringUtil.hasValue(ca.getCategoryAttributeName())) {
            txMgr.insert(ca);
          }
        }
      }

      // カテゴリパス、階層の計算、更新

      UpdateCategoryPathProcedure updatePathProc = new UpdateCategoryPathProcedure(category.getCategoryCode(), config
          .getCategoryMaxDepth(), getLoginInfo().getRecordingFormat());
      txMgr.executeProcedure(updatePathProc);

      if (updatePathProc.getResult() == UpdateCategoryPathProcedure.SUCCESS) {
        txMgr.commit();
      } else {
        txMgr.rollback();
        if (updatePathProc.getResult() == UpdateCategoryPathProcedure.MAX_DEPTH_OVER_ERROR) {
          serviceResult.addServiceError(CatalogServiceErrorContent.CATEGORY_MAX_DEPTH_OVER_ERROR);
        } else {
          serviceResult.addServiceError(CatalogServiceErrorContent.UPDATE_CATEGORY_PATH_ERROR);
        }
      }

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  public ServiceResult updateCommodityInfo(CommodityInfo commodityInfo) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    CommodityHeader header = commodityInfo.getHeader();
    CommodityDetail representSku = commodityInfo.getDetail();
    Stock stock = commodityInfo.getStock();
    // 商品ヘッダの存在チェックを実行する
    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader headerResult = hDao.load(header.getShopCode(), header.getCommodityCode());
    if (headerResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    header.setOrmRowid(headerResult.getOrmRowid());
    header.setCreatedUser(headerResult.getCreatedUser());
    header.setCreatedDatetime(headerResult.getCreatedDatetime());
    // 販売期間の開始/終了日時がnullの場合は、

    // システム最大(or最小)日付を設定する

    if (header.getSaleStartDatetime() == null) {
      header.setSaleStartDatetime(DateUtil.getMin());
    }
    if (header.getSaleEndDatetime() == null) {
      header.setSaleEndDatetime(DateUtil.getMax());
    }
    // 予約期間の開始/終了日時がnullの場合は最小システム日付を設定
    // 予約期間の開始日時がnullの場合は最小システム日付を設定
    if (header.getReservationStartDatetime() == null && header.getReservationEndDatetime() == null) {
      header.setReservationStartDatetime(DateUtil.getMin());
      header.setReservationEndDatetime(DateUtil.getMin());
    } else if (header.getReservationStartDatetime() == null) {
      header.setReservationStartDatetime(DateUtil.getMin());
    }
    // 在庫管理区分が「在庫管理する(状況表示)」以外の場合は、

    // 在庫状況番号にnullを設定し、値をクリアする

    StockManagementType stockManagementType = StockManagementType.fromValue(header.getStockManagementType());
    if (stockManagementType != StockManagementType.WITH_STATUS) {
      header.setStockStatusNo(null);
    }
    setUserStatus(header);
    if (NumUtil.isNull(header.getRecommendCommodityRank())) {
      header.setRecommendCommodityRank(99999999L);
    }
    header.setCommodityPopularRank(headerResult.getCommodityPopularRank());
    // 商品詳細が存在すれば更新
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    CommodityDetail detailResult = dDao.load(representSku.getShopCode(), representSku.getSkuCode());
    if (detailResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    representSku.setOrmRowid(detailResult.getOrmRowid());
    representSku.setCreatedUser(detailResult.getCreatedUser());
    representSku.setCreatedDatetime(detailResult.getCreatedDatetime());
    setUserStatus(representSku);
    // 代表SKU以外のSKUの特別価格、予約価格、改定価格を設定する
    List<CommodityDetail> otherDetails = dDao.findByQuery(CommodityDeleteQuery.getCommoditySkuQuery(), header.getShopCode(), header
        .getCommodityCode());
    List<CommodityDetail> updateDetails = new ArrayList<CommodityDetail>();
    for (CommodityDetail detail : otherDetails) {
      // 代表SKU以外の場合

      if (!detail.getSkuCode().equals(header.getRepresentSkuCode())) {
        // 代表SKUの各価格 == null: 各SKUの該当価格をクリアする

        // 代表SKUの各価格 != null: 各SKUの該当価格が未設定であれば、代表SKUの価格をセットする

        if (NumUtil.isNull(representSku.getDiscountPrice())) {
          detail.setDiscountPrice(null);
        } else {
          detail.setDiscountPrice(NumUtil.coalesce(detail.getDiscountPrice(), representSku.getDiscountPrice()));
        }
        if (NumUtil.isNull(representSku.getReservationPrice())) {
          detail.setReservationPrice(null);
        } else {
          detail.setReservationPrice(NumUtil.coalesce(detail.getReservationPrice(), representSku.getReservationPrice()));
        }
        if (NumUtil.isNull(representSku.getChangeUnitPrice())) {
          detail.setChangeUnitPrice(null);
        } else {
          detail.setChangeUnitPrice(NumUtil.coalesce(detail.getChangeUnitPrice(), representSku.getChangeUnitPrice()));
        }
        updateDetails.add(detail);
      }
    }
    StockDao sDao = DIContainer.getDao(StockDao.class);
    Stock stockResult = sDao.load(stock.getShopCode(), stock.getSkuCode());
    if (stockResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    if (NumUtil.isNull(stock.getStockThreshold())) {
      stock.setStockThreshold(0L);
    }

    stock.setOrmRowid(stockResult.getOrmRowid());
    stock.setCreatedUser(stockResult.getCreatedUser());
    stock.setCreatedDatetime(stockResult.getCreatedDatetime());
    setUserStatus(stock);
    // バリデーションチェックを実行する
    ValidationSummary hSummary = BeanValidator.validate(header);
    if (hSummary.hasError()) {
      for (String error : hSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    ValidationSummary dSummary = BeanValidator.validate(representSku);
    if (dSummary.hasError()) {
      for (String error : dSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    ValidationSummary sSummary = BeanValidator.validate(stock);
    if (sSummary.hasError()) {
      for (String error : sSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(header);
      // 商品詳細が存在すれば更新
      txMgr.update(representSku);
      // 代表SKU以外のSKUを更新

      for (CommodityDetail cd : updateDetails) {
        txMgr.update(cd);
      }
      // 在庫情報が存在すれば更新
      txMgr.executeUpdate(CatalogQuery.UPDATE_RESERVATIOIN_INFO, stock.getOneshotReservationLimit(), stock.getReservationLimit(),
          stock.getStockThreshold(), this.getLoginInfo().getRecordingFormat(), stock.getUpdatedDatetime(), stock.getShopCode(),
          stock.getSkuCode());
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult updateCommoditySku(CommodityDetail sku, Stock stock) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    // 商品ヘッダの存在チェック

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (!hDao.exists(sku.getShopCode(), sku.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    CommodityHeader header = hDao.load(sku.getShopCode(), sku.getCommodityCode());
    if (header.getRepresentSkuCode().equals(sku.getSkuCode())) {
      header.setRepresentSkuUnitPrice(sku.getUnitPrice());
    }

    // 商品SKUの存在チェック

    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    CommodityDetail skuResult = dDao.load(sku.getShopCode(), sku.getSkuCode());
    if (skuResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    StockDao sDao = DIContainer.getDao(StockDao.class);
    Stock stockResult = sDao.load(stock.getShopCode(), stock.getSkuCode());
    if (stockResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 値のコピー

    sku.setOrmRowid(skuResult.getOrmRowid());
    sku.setCreatedUser(skuResult.getCreatedUser());
    sku.setCreatedDatetime(skuResult.getCreatedDatetime());

    setUserStatus(sku);

    // バリデーションチェック

    ValidationSummary skuSummary = BeanValidator.validate(sku);
    if (skuSummary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : skuSummary.getErrorMessages()) {
        logger.error(error);
      }
      return result;
    }
    boolean faultStandardName = false;
    if (StringUtil.hasValueAllOf(header.getCommodityStandard1Name(), header.getCommodityStandard2Name())) {
      if (!StringUtil.hasValueAllOf(sku.getStandardDetail1Name(), sku.getStandardDetail2Name())) {
        faultStandardName = true;
      }
    } else if (StringUtil.hasValue(header.getCommodityStandard1Name())) {
      if (StringUtil.isNullOrEmpty(sku.getStandardDetail1Name()) || StringUtil.hasValue(sku.getStandardDetail2Name())) {
        faultStandardName = true;
      }
    } else if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name())
        && StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())) {
      if (StringUtil.hasValue(sku.getStandardDetail1Name()) || StringUtil.hasValue(sku.getStandardDetail2Name())) {
        faultStandardName = true;
      }
    }
    if (faultStandardName) {
      result.addServiceError(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR);
      return result;
    }

    if (NumUtil.isNull(stock.getStockThreshold())) {
      stock.setStockThreshold(0L);
    }

    stock.setStockQuantity(stockResult.getStockQuantity());
    stock.setAllocatedQuantity(stockResult.getAllocatedQuantity());
    stock.setReservedQuantity(stockResult.getReservedQuantity());
    stock.setOrmRowid(stockResult.getOrmRowid());
    stock.setCreatedUser(stockResult.getCreatedUser());
    stock.setCreatedDatetime(stockResult.getCreatedDatetime());
    stock.setUpdatedDatetime(stockResult.getUpdatedDatetime());

    setUserStatus(stock);

    ValidationSummary stockSummary = BeanValidator.validate(stock);
    if (stockSummary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : stockSummary.getErrorMessages()) {
        logger.error(error);
      }
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 商品SKU更新
    try {
      txMgr.begin(getLoginInfo());

      txMgr.update(header);
      txMgr.update(sku);
      txMgr.executeUpdate(CatalogQuery.UPDATE_RESERVATIOIN_INFO, stock.getOneshotReservationLimit(), stock.getReservationLimit(),
          stock.getStockThreshold(), this.getLoginInfo().getRecordingFormat(), stock.getUpdatedDatetime(), stock.getShopCode(),
          stock.getSkuCode());

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      logger.debug(e.getMessage());
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult updatePriceAll(String shopCode, String commodityCode, CommodityPriceType type, BigDecimal price) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    CurrencyValidator validator = new CurrencyValidator();
    if (!validator.isValid(price)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    List<CommodityDetail> detailList = getCommoditySku(shopCode, commodityCode);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      for (CommodityDetail detail : detailList) {
        switch (type) {
          case UNIT_PRICE:
            detail.setUnitPrice(price);
            break;
          case DISCOUNT_PRICE:
            detail.setDiscountPrice(price);
            break;
          case RESERVATION_PRICE:
            detail.setReservationPrice(price);
            break;
          case CHANGE_UNIT_PRICE:
            detail.setChangeUnitPrice(price);
            break;
          default:
            throw new RuntimeException();
        }
        setUserStatus(detail);
        txMgr.update(detail);
      }

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public ServiceResult updateGift(Gift gift) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    // 存在チェック
    GiftDao dao = DIContainer.getDao(GiftDao.class);
    Gift giftResult = dao.load(gift.getShopCode(), gift.getGiftCode());
    if (giftResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    gift.setOrmRowid(giftResult.getOrmRowid());
    gift.setCreatedUser(giftResult.getCreatedUser());
    gift.setCreatedDatetime(giftResult.getCreatedDatetime());

    setUserStatus(gift);

    // バリデーションチェック

    ValidationSummary summary = BeanValidator.validate(gift);
    if (summary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return result;
    }

    // 更新処理
    dao.update(gift, getLoginInfo());

    return result;
  }

  public ServiceResult updateCommoditySaleType(String shopCode, List<String> commodityCodeList, SaleFlg saleFlg) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    if (StringUtil.isNull(shopCode)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    if (saleFlg == null) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    CommodityHeaderDao dao = DIContainer.getDao(CommodityHeaderDao.class);

    try {
      for (String commodityCode : commodityCodeList) {
        CommodityHeader header = dao.load(shopCode, commodityCode);
        if (header != null) {
          header.setSaleFlg(saleFlg.longValue());
          setUserStatus(header);
          dao.update(header, getLoginInfo());
        }
      }
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      logger.error(e);
    }

    return result;
  }

  public ServiceResult updateStockStatus(StockStatus stockStatus) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    // 存在チェックを実行する

    StockStatusDao dao = DIContainer.getDao(StockStatusDao.class);
    StockStatus stockResult = dao.load(stockStatus.getShopCode(), stockStatus.getStockStatusNo());
    if (stockResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 値のコピーを行う
    stockStatus.setOrmRowid(stockResult.getOrmRowid());
    stockStatus.setCreatedUser(stockResult.getCreatedUser());
    stockStatus.setCreatedDatetime(stockResult.getCreatedDatetime());

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(stockStatus);
    if (summary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return result;

    }

    dao.update(stockStatus, getLoginInfo());

    return result;
  }

  public ServiceResult updateTag(Tag tag) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェックを実行する

    ValidationSummary summary = BeanValidator.validate(tag);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return serviceResult;
    }

    TagDao dao = DIContainer.getDao(TagDao.class);

    // 存在チェックを実行する

    if (!dao.exists(tag.getShopCode(), tag.getTagCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    dao.update(tag, getLoginInfo());

    return serviceResult;
  }

  public CommodityHeader getCommodityHeader(String shopCode, String commodityCode) {
    CommodityHeaderDao dao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader commodityHeader = null;
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(commodityCode)) {
      commodityHeader = dao.load(shopCode, commodityCode);
    }
    return commodityHeader;
  }

  public Long getAllDescribeImgHistory(String commodityCode, String type) {
    Query query = new SimpleQuery(CatalogQuery.GET_ALL_HISTORY_IMG, commodityCode, type);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  public Gift getGift(String shopCode, String giftCode) {
    GiftDao dao = DIContainer.get("GiftDao");
    Gift gift = null;
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(giftCode)) {
      gift = dao.load(shopCode, giftCode);
    }
    return gift;
  }

  public ServiceResult insertRelatedCampaign(CampaignCommodity campaignCommodity) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(campaignCommodity);

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(campaignCommodity);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CommodityHeaderDao commodityDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (!commodityDao.exists(campaignCommodity.getShopCode(), campaignCommodity.getCommodityCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CampaignDao campaignDao = DIContainer.getDao(CampaignDao.class);
    if (!campaignDao.exists(campaignCommodity.getShopCode(), campaignCommodity.getCampaignCode())) {
      logger.error(CommonServiceErrorContent.NO_DATA_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CampaignCommodityDao dao = DIContainer.getDao(CampaignCommodityDao.class);

    // 重複チェックを実行する

    if (dao.exists(campaignCommodity.getShopCode(), campaignCommodity.getCampaignCode(), campaignCommodity.getCommodityCode())) {
      logger.error(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);

    } else {
      dao.insert(campaignCommodity, getLoginInfo());
    }
    return serviceResult;
  }

  public TagCommodity getTagCommodity(String shopCode, String tagCode, String commodityCode) {
    TagCommodityDao dao = DIContainer.getDao(TagCommodityDao.class);
    TagCommodity tagCommodity = new TagCommodity();
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(tagCode) && StringUtil.hasValue(commodityCode)) {
      tagCommodity = dao.load(shopCode, tagCode, commodityCode);
    }
    return tagCommodity;
  }

  public CampaignCommodity getCampaignCommodity(String shopCode, String campaignCode, String commodityCode) {
    CampaignCommodityDao dao = DIContainer.getDao(CampaignCommodityDao.class);
    CampaignCommodity campaignCommodity = null;
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(campaignCode) && StringUtil.hasValue(commodityCode)) {
      campaignCommodity = dao.load(shopCode, campaignCode, commodityCode);
    }
    return campaignCommodity;
  }

  public GiftCommodity getGiftCommodity(String shopCode, String giftCode, String commodityCode) {
    GiftCommodityDao dao = DIContainer.getDao(GiftCommodityDao.class);
    GiftCommodity giftCommodity = null;
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(giftCode) && StringUtil.hasValue(commodityCode)) {
      giftCommodity = dao.load(shopCode, giftCode, commodityCode);
    }
    return giftCommodity;
  }

  public RelatedCommodityA getRelatedCommodityA(String shopCode, String commodityCode, String linkCommodityCode) {
    RelatedCommodityADao dao = DIContainer.getDao(RelatedCommodityADao.class);
    RelatedCommodityA relatedCommodityA = null;
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(commodityCode) && StringUtil.hasValue(linkCommodityCode)) {
      relatedCommodityA = dao.load(shopCode, commodityCode, linkCommodityCode);
    }
    return relatedCommodityA;
  }

  public SearchResult<RelatedBaseEvent> getRelatedTagCommoditySearchBaseEvent(RelatedSearchConditionBaseEvent condition) {
    RelatedSearchQueryBaseEvent query = new RelatedSearchQueryBaseEvent();
    return DatabaseUtil.executeSearch(query.createRelatedTagSearchQueryBaseEvent(condition));
  }

  public SearchResult<RelatedBaseEvent> getRelatedGiftCommoditySearchBaseEvent(RelatedSearchConditionBaseEvent condition) {
    RelatedSearchQueryBaseEvent query = new RelatedSearchQueryBaseEvent();
    return DatabaseUtil.executeSearch(query.createRelatedGiftSearchQueryBaseEvent(condition));
  }

  public SearchResult<RelatedBaseEvent> getRelatedCampaignCommoditySearchBaseEvent(RelatedSearchConditionBaseEvent condition) {
    RelatedSearchQueryBaseEvent query = new RelatedSearchQueryBaseEvent();
    return DatabaseUtil.executeSearch(query.createRelatedCampaignSearchQueryBaseEvent(condition));
  }

  public SearchResult<RelatedBaseEvent> getRelatedCommodityASearchBaseEvent(RelatedSearchConditionBaseEvent condition) {
    RelatedSearchQueryBaseEvent query = new RelatedSearchQueryBaseEvent();
    return DatabaseUtil.executeSearch(query.createRelatedCommodityASearchQueryBaseEvent(condition));
  }

  public SearchResult<RelatedBaseEvent> getRelatedCommodityBSearchBaseEvent(RelatedSearchConditionBaseEvent condition) {
    RelatedSearchQueryBaseEvent query = new RelatedSearchQueryBaseEvent();
    return DatabaseUtil.executeSearch(query.createRelatedCommodityBSearchQueryBaseEvent(condition));
  }

  public ServiceResult deleteRelatedCampaign(String shopCode, String campaignCode, String commodityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    CampaignCommodityDao dao = DIContainer.getDao(CampaignCommodityDao.class);
    dao.delete(shopCode, campaignCode, commodityCode);
    return serviceResult;
  }

  public ServiceResult deleteRelatedGift(String shopCode, String giftCode, String commodityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    GiftCommodityDao dao = DIContainer.getDao(GiftCommodityDao.class);
    dao.delete(shopCode, giftCode, commodityCode);
    return serviceResult;
  }

  public ServiceResult deleteRelatedCommodityA(String shopCode, String commodityCode, String linkCommodityCode,
      boolean interactiveDeleteFlg) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    RelatedCommodityADao dao = DIContainer.getDao(RelatedCommodityADao.class);

    if (interactiveDeleteFlg) {
      // 順方向の関連付けを削除する
      // 関連付けがない場合は削除処理をスキップする
      if (dao.exists(shopCode, commodityCode, linkCommodityCode)) {
        dao.delete(shopCode, commodityCode, linkCommodityCode);
      }
    }

    // 逆方向の関連付けを削除する
    // 関連付けがない場合は削除処理をスキップする
    if (dao.exists(shopCode, linkCommodityCode, commodityCode)) {
      dao.delete(shopCode, linkCommodityCode, commodityCode);
    }

    return serviceResult;
  }

  public ServiceResult deleteRelatedTag(String shopCode, String tagCode, String commodityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TagCommodityDao dao = DIContainer.getDao(TagCommodityDao.class);
    dao.delete(shopCode, tagCode, commodityCode);

    return serviceResult;
  }

  public SearchResult<RelatedCampaign> getRelatedCampaignCommoditySearch(RelatedSearchConditionBaseCommodity condition) {
    RelatedSearchQueryCampaign query = new RelatedSearchQueryCampaign();
    return DatabaseUtil.executeSearch(query.createRelatedCampaignSearchQuery(condition));
  }

  public SearchResult<RelatedGift> getRelatedGiftCommoditySearch(RelatedSearchConditionBaseCommodity condition) {
    RelatedSearchQueryGift query = new RelatedSearchQueryGift();
    return DatabaseUtil.executeSearch(query.createRelatedGiftSearchQuery(condition));
  }

  public SearchResult<RelatedTag> getRelatedTagCommoditySearch(RelatedSearchConditionBaseCommodity condition) {
    RelatedSearchQueryTag query = new RelatedSearchQueryTag();
    return DatabaseUtil.executeSearch(query.createRelatedTagSearchQuery(condition));
  }

  /**
   * 商品をキャンペーンに一括で関連付ける<BR>
   * DB内のデータの有無と画面で入力されたチェックボックスとの関係により、登録?削除を制御する<BR>
   * 【画面のチェックボックスでチェックされている場合】<BR>
   * 1．DB内にデータが登録されている：登録処理をスキップする<BR>
   * 2．DB内にデータが登録されていない：関連付けテーブルにデータを登録する<BR>
   * 【画面のチェックボックスでチェックされていない場合】<BR>
   * 1．DB内にデータが登録されている：該当レコードを削除する<BR>
   * 2．DB内にデータが登録されていない：削除処理をスキップする<BR>
   */
  public ServiceResult registerRelatedCampaign(List<RelatedCampaign> relatedCampaign) {
    Logger logger = Logger.getLogger(this.getClass());

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      CampaignCommodityDao dao = DIContainer.getDao(CampaignCommodityDao.class);
      txMgr.begin(getLoginInfo());
      for (RelatedCampaign cc : relatedCampaign) {
        setUserStatus(cc);
        ValidationSummary summary = BeanValidator.validate(cc);
        if (summary.hasError()) {
          logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
          serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          for (String error : summary.getErrorMessages()) {
            logger.error(error);
          }
          // Validationエラーの場合はロールバックする

          txMgr.rollback();
          return serviceResult;
        }

        if (StringUtil.isNullOrEmpty(cc.getCheckCode())) {
          // チェックボックスが選択されていなくて、DB内にもレコードが存在しない場合は何もしない

          if (dao.exists(cc.getShopCode(), cc.getCampaignCode(), cc.getCommodityCode())) {
            // チェックボックスが選択されていなくて、DB内にレコードが存在する場合はレコードを削除する

            txMgr.executeUpdate(CatalogQuery.DELETE_CAMPAIGN_COMMODITY, cc.getShopCode(), cc.getCampaignCode(), cc
                .getCommodityCode());
          }
        } else {
          // チェックボックスが選択されていて、DB内にレコードが存在する場合は何もしない

          if (!dao.exists(cc.getShopCode(), cc.getCampaignCode(), cc.getCommodityCode())) {
            // チェックボックスが選択されていて、DB内にレコードが存在しない場合はレコードを登録する
            CampaignCommodity campaignCommodity = new CampaignCommodity();
            campaignCommodity.setShopCode(cc.getShopCode());
            campaignCommodity.setCampaignCode(cc.getCampaignCode());
            campaignCommodity.setCommodityCode(cc.getCommodityCode());
            campaignCommodity.setOrmRowid(cc.getOrmRowid());
            campaignCommodity.setCreatedUser(cc.getCreatedUser());
            campaignCommodity.setCreatedDatetime(cc.getCreatedDatetime());
            campaignCommodity.setUpdatedUser(cc.getUpdatedUser());
            campaignCommodity.setUpdatedDatetime(cc.getUpdatedDatetime());
            txMgr.insert(campaignCommodity);
          }
        }
      }
      txMgr.commit();
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

  /**
   * 商品をギフトに一括で関連付ける<BR>
   * DB内のデータの有無と画面で入力されたチェックボックスとの関係により、登録?削除を制御する<BR>
   * 【画面のチェックボックスでチェックされている場合】<BR>
   * 1．DB内にデータが登録されている：登録処理をスキップする<BR>
   * 2．DB内にデータが登録されていない：関連付けテーブルにデータを登録する<BR>
   * 【画面のチェックボックスでチェックされていない場合】<BR>
   * 1．DB内にデータが登録されている：該当レコードを削除する<BR>
   * 2．DB内にデータが登録されていない：削除処理をスキップする<BR>
   */
  public ServiceResult registerRelatedGift(List<RelatedGift> relatedGift) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      GiftCommodityDao dao = DIContainer.getDao(GiftCommodityDao.class);
      txMgr.begin(getLoginInfo());
      for (RelatedGift cc : relatedGift) {
        setUserStatus(cc);
        ValidationSummary summary = BeanValidator.validate(cc);
        if (summary.hasError()) {
          logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
          serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          for (String error : summary.getErrorMessages()) {
            logger.error(error);
          }
          // Validationエラーの場合はロールバックする

          txMgr.rollback();
          return serviceResult;
        }

        if (StringUtil.isNullOrEmpty(cc.getCheckCode())) {
          // チェックボックスが選択されていなくて、DB内にもレコードが存在しない場合は何もしない

          if (dao.exists(cc.getShopCode(), cc.getGiftCode(), cc.getCommodityCode())) {
            // チェックボックスが選択されていなくて、DB内にレコードが存在する場合はレコードを削除する

            txMgr.executeUpdate(CatalogQuery.DELETE_GIFT_COMMODITY, cc.getShopCode(), cc.getGiftCode(), cc.getCommodityCode());
          }

        } else {
          if (!dao.exists(cc.getShopCode(), cc.getGiftCode(), cc.getCommodityCode())) {
            // チェックボックスが選択されていて、DB内にレコードが存在する場合は何もしない

            // チェックボックスが選択されていて、DB内にレコードが存在しない場合はレコードを登録する
            GiftCommodity giftCommodity = new GiftCommodity();
            giftCommodity.setShopCode(cc.getShopCode());
            giftCommodity.setGiftCode(cc.getGiftCode());
            giftCommodity.setCommodityCode(cc.getCommodityCode());
            giftCommodity.setOrmRowid(cc.getOrmRowid());
            giftCommodity.setCreatedUser(cc.getCreatedUser());
            giftCommodity.setCreatedDatetime(cc.getCreatedDatetime());
            giftCommodity.setUpdatedUser(cc.getUpdatedUser());
            giftCommodity.setUpdatedDatetime(cc.getUpdatedDatetime());
            txMgr.insert(giftCommodity);
          }
        }
      }
      txMgr.commit();
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

  /**
   * 商品をタグに一括で関連付ける<BR>
   * DB内のデータの有無と画面で入力されたチェックボックスとの関係により、登録?削除を制御する<BR>
   * 【画面のチェックボックスでチェックされている場合】<BR>
   * 1．DB内にデータが登録されている：登録処理をスキップする<BR>
   * 2．DB内にデータが登録されていない：関連付けテーブルにデータを登録する<BR>
   * 【画面のチェックボックスでチェックされていない場合】<BR>
   * 1．DB内にデータが登録されている：該当レコードを削除する<BR>
   * 2．DB内にデータが登録されていない：削除処理をスキップする<BR>
   */
  public ServiceResult registerRelatedTag(List<RelatedTag> relatedTag) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      TagCommodityDao dao = DIContainer.getDao(TagCommodityDao.class);
      for (RelatedTag cc : relatedTag) {
        setUserStatus(cc);
        ValidationSummary summary = BeanValidator.validate(cc);
        if (summary.hasError()) {
          logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
          serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          for (String error : summary.getErrorMessages()) {
            // 詳細なエラー内容はログにDebugで出力

            logger.error(error);
          }
          // Validationエラーの場合はロールバックする

          txMgr.rollback();
          return serviceResult;
        }

        if (StringUtil.isNullOrEmpty(cc.getCheckCode())) {
          if (dao.exists(cc.getShopCode(), cc.getTagCode(), cc.getCommodityCode())) {
            // チェックボックスが選択されていなくて、DB内にもレコードが存在しない場合は何もしない

            // チェックボックスが選択されていて、DB内にレコードが存在する場合はレコードを削除する

            txMgr.executeUpdate(CatalogQuery.DELETE_TAG_COMMODITY, cc.getShopCode(), cc.getTagCode(), cc.getCommodityCode());
          }

        } else {
          if (!dao.exists(cc.getShopCode(), cc.getTagCode(), cc.getCommodityCode())) {
            // チェックボックスが選択されていて、DB内にレコードが存在する場合は何もしない

            // チェックボックスが選択されていなくて、DB内にレコードが存在しない場合はレコードを登録する
            TagCommodity tagCommodity = new TagCommodity();
            tagCommodity.setShopCode(cc.getShopCode());
            tagCommodity.setTagCode(cc.getTagCode());
            tagCommodity.setCommodityCode(cc.getCommodityCode());
            tagCommodity.setOrmRowid(cc.getOrmRowid());
            tagCommodity.setCreatedUser(cc.getCreatedUser());
            tagCommodity.setCreatedDatetime(cc.getCreatedDatetime());
            tagCommodity.setUpdatedUser(cc.getUpdatedUser());
            tagCommodity.setUpdatedDatetime(cc.getUpdatedDatetime());
            txMgr.insert(tagCommodity);
          }
        }
      }
      txMgr.commit();
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

  public ServiceResult updateRelatedCommodityA(RelatedCommodityA relatedA) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();
    RelatedCommodityADao dao = DIContainer.getDao(RelatedCommodityADao.class);

    // 存在チェックを実行する

    if (dao.exists(relatedA.getShopCode(), relatedA.getCommodityCode(), relatedA.getLinkCommodityCode())) {
      dao.update(relatedA, getLoginInfo());
      return serviceResult;

    } else {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

  }

  public SearchResult<TagCount> getTagSearch(TagSearchCondition condition) {
    TagSearchQuery query = new TagSearchQuery();
    return DatabaseUtil.executeSearch(query.createTagSearchQuery(condition));

  }

  public ServiceResult updateCommodityHeader(CommodityHeader header) {
    ServiceResultImpl result = new ServiceResultImpl();

    // 商品ヘッダの存在チェック

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader headerResult = hDao.load(header.getShopCode(), header.getCommodityCode());
    if (headerResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 代表SKUの存在チェック

    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    if (!dDao.exists(header.getShopCode(), header.getRepresentSkuCode())) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    hDao.update(header, getLoginInfo());

    return result;
  }

  public ServiceResult insertCategoryAttributeValue(CategoryAttributeValue categoryAttributeValue) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェックを実行

    ValidationSummary summary = BeanValidator.validate(categoryAttributeValue);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    if (!categoryDao.exists(categoryAttributeValue.getCategoryCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // データの重複チェックをし、登録対象のレコードが存在しなければ登録
    CategoryAttributeValueDao dao = DIContainer.getDao(CategoryAttributeValueDao.class);
    if (dao.exists(categoryAttributeValue.getShopCode(), categoryAttributeValue.getCategoryCode(), categoryAttributeValue
        .getCategoryAttributeNo(), categoryAttributeValue.getCommodityCode())) {
      logger.error(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    } else {
      dao.insert(categoryAttributeValue, getLoginInfo());
    }

    return serviceResult;
  }

  public ServiceResult updateCategoryAttributeValue(CategoryAttributeValue categoryAttributeValue) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェックを実行

    ValidationSummary summary = BeanValidator.validate(categoryAttributeValue);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    if (!categoryDao.exists(categoryAttributeValue.getCategoryCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    // データの存在チェックをし、対象が存在すれば更新

    CategoryAttributeValueDao dao = DIContainer.getDao(CategoryAttributeValueDao.class);
    if (dao.exists(categoryAttributeValue.getShopCode(), categoryAttributeValue.getCategoryCode(), categoryAttributeValue
        .getCategoryAttributeNo(), categoryAttributeValue.getCommodityCode())) {
      dao.update(categoryAttributeValue, getLoginInfo());
    } else {
      logger.error(CommonServiceErrorContent.NO_DATA_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
    }

    return serviceResult;
  }

  public ServiceResult deleteCategoryAttributeValue(String shopCode, String categoryCode, Long categoryAttributeNo,
      String commodityCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // データの存在チェックをし、対象が存在すれば削除

    CategoryAttributeValueDao dao = DIContainer.getDao(CategoryAttributeValueDao.class);
    dao.delete(shopCode, categoryCode, categoryAttributeNo, commodityCode);

    return serviceResult;
  }

  public ServiceResult registerCategoryCommodity(CategoryCommodity categoryCommodity,
      List<CategoryAttributeValue> categoryAttributeValue) {

    Logger logger = Logger.getLogger(this.getClass());

    CategoryAttributeValueDao valueDao = DIContainer.getDao(CategoryAttributeValueDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // カテゴリ検索パスの取得

    String categorySearchPath = CommonLogic.getCategorySearchPath(categoryCommodity.getCategoryCode());
    categoryCommodity.setCategorySearchPath(categorySearchPath);

    setUserStatus(categoryCommodity);

    // Validationチェック処理を実行する

    ValidationSummary categoryCommoditySummary = BeanValidator.validate(categoryCommodity);
    if (categoryCommoditySummary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : categoryCommoditySummary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    for (CategoryAttributeValue value : categoryAttributeValue) {
      setUserStatus(value);
    }

    ValidationSummary categoryAttributeValueSummary = BeanValidator.validate(categoryAttributeValue);
    if (categoryAttributeValueSummary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : categoryAttributeValueSummary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CCommodityHeaderDao commodityDao = DIContainer.getDao(CCommodityHeaderDao.class);
    if (!commodityDao.exists(categoryCommodity.getShopCode(), categoryCommodity.getCommodityCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CategoryCommodityDao categoryCommodityDao = DIContainer.getDao(CategoryCommodityDao.class);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      if (!categoryCommodityDao.exists(categoryCommodity.getShopCode(), categoryCommodity.getCategoryCode(), categoryCommodity
          .getCommodityCode())) {
        // 重複チェックを実行し存在していなければ登録

        txMgr.insert(categoryCommodity);
      }
      List<CategoryAttributeValue> categoryAttributeValueList = getCategoryAttributeValueList(categoryCommodity.getCommodityCode());
      for (CategoryAttributeValue ca : categoryAttributeValue) {
        // 重複チェックを実行し存在していなければ登録

        if (valueDao.exists(ca.getShopCode(), ca.getCategoryCode(), ca.getCategoryAttributeNo(), ca.getCommodityCode())) {
          txMgr.rollback();
          logger.error(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
          serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
          return serviceResult;
        } else {
          txMgr.insert(ca);

          categoryAttributeValueList.add(ca);
        }
      }
      // 20120220 os013 add start
      List<CategoryCommodity> categoryCommodityList = getCategoryCommodityList(categoryCommodity.getShopCode(), categoryCommodity
          .getCommodityCode());
      categoryCommodityList.add(categoryCommodity);
      updateCategorySearchPath(txMgr, categoryCommodityList, categoryCommodity.getCommodityCode(), categoryCommodity.getShopCode());
      updateCategoryAttributeValue(txMgr, categoryAttributeValueList, categoryCommodity.getCommodityCode(), categoryCommodity
          .getShopCode());
      // 20120220 os013 add end
      txMgr.commit();

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult updateCategoryAttributeValue(List<CategoryAttributeValue> categoryAttributeValue) {
    Logger logger = Logger.getLogger(this.getClass());
    CategoryAttributeValueDao dao = DIContainer.getDao(CategoryAttributeValueDao.class);
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    for (CategoryAttributeValue value : categoryAttributeValue) {
      if (StringUtil.isNullOrEmpty(value.getCreatedUser())) {
        setUserStatus(value);
      }

    }

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(categoryAttributeValue);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    CommodityHeaderDao commodityDao = DIContainer.getDao(CommodityHeaderDao.class);
    if (!commodityDao.exists(categoryAttributeValue.get(0).getShopCode(), categoryAttributeValue.get(0).getCommodityCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    if (!categoryDao.exists(categoryAttributeValue.get(0).getCategoryCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    CategoryCommodityDao categoryCommodityDao = DIContainer.getDao(CategoryCommodityDao.class);
    if (!categoryCommodityDao.exists(categoryAttributeValue.get(0).getShopCode(), categoryAttributeValue.get(0).getCategoryCode(),
        categoryAttributeValue.get(0).getCommodityCode())) {
      CategoryCommodity categoryCommodity = new CategoryCommodity();
      categoryCommodity.setShopCode(categoryAttributeValue.get(0).getShopCode());
      categoryCommodity.setCategoryCode(categoryAttributeValue.get(0).getCategoryCode());
      categoryCommodity.setCommodityCode(categoryAttributeValue.get(0).getCommodityCode());

      registerCategoryCommodity(categoryCommodity, categoryAttributeValue);
      return serviceResult;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {

      txMgr.begin(getLoginInfo());
      for (CategoryAttributeValue ca : categoryAttributeValue) {
        // 存在チェックを実行し存在していなければ登録

        if (dao.exists(ca.getShopCode(), ca.getCategoryCode(), ca.getCategoryAttributeNo(), ca.getCommodityCode())) {
          txMgr.update(ca);
        } else {
          txMgr.insert(ca);
        }
      }
      updateCategoryAttributeValue(txMgr, categoryAttributeValue, categoryAttributeValue.get(0).getCommodityCode(),
          categoryAttributeValue.get(0).getShopCode());

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult deleteCategoryAttributeValue(String categoryCode, String shopCode, String commodityCode) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CategoryCommodityDao dao = DIContainer.getDao(CategoryCommodityDao.class);
    CategoryCommodity categoryCommodity = dao.load(shopCode, categoryCode, commodityCode);
    // 20120220 os013 add start
    // 查询陈列商品表中属于商品编号的所有数据
    List<CategoryCommodity> categoryCommodityList = dao.findByQuery(RelatedCategoryQuery.GET_CATEGORY_COMMODITY_LIST_QUERY,
        shopCode, commodityCode);
    List<CategoryCommodity> categoryCommodityList1 = new ArrayList<CategoryCommodity>();
    for (CategoryCommodity cc : categoryCommodityList) {
      // 排除需要删除的CategoryCode
      if (!cc.getCategoryCode().equals(categoryCommodity.getCategoryCode())) {
        categoryCommodityList1.add(cc);
      }
    }
    // 查询出属性值表中属于商品编号的所有数据
    List<CategoryAttributeValue> categoryAttributeValueList = getCategoryAttributeValueList(commodityCode);
    List<CategoryAttributeValue> categoryAttributeValueList1 = new ArrayList<CategoryAttributeValue>();
    for (CategoryAttributeValue cav : categoryAttributeValueList) {
      // 排除需要删除的CategoryCode
      if (!cav.getCategoryCode().equals(categoryCommodity.getCategoryCode())) {
        categoryAttributeValueList1.add(cav);
      }
    }
    // 20120220 os013 add end
    if (categoryCommodity == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
    }

    if (StringUtil.hasValueAllOf(categoryCode, shopCode, commodityCode)) {

      TransactionManager txMgr = DIContainer.getTransactionManager();
      try {
        txMgr.begin(getLoginInfo());
        txMgr.executeUpdate(RelatedCategoryQuery.GET_DELETE_CATEGORY_ATTRIBUTE_VALUE_QUERY, categoryCode, shopCode, commodityCode);
        txMgr.delete(categoryCommodity);
        // 20120220 os013 add start
        // 修改商品基本表
        updateCategorySearchPath(txMgr, categoryCommodityList1, commodityCode, shopCode);
        updateCategoryAttributeValue(txMgr, categoryAttributeValueList1, commodityCode, shopCode);
        // 20120220 os013 add end
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

    }
    return serviceResult;
  }

  public List<RelatedCategory> getCategoryAttributeValueList(String shopCode, String categoryCode, String commodityCode) {
    List<RelatedCategory> list = null;

    if (StringUtil.hasValueAllOf(shopCode, categoryCode, commodityCode)) {
      Query query = new SimpleQuery(RelatedCategoryQuery.GET_CATEGORY_ATTRIBUTE_VALUE_LIST_QUERY, shopCode, categoryCode,
          commodityCode);
      list = DatabaseUtil.loadAsBeanList(query, RelatedCategory.class);
    }

    return list;
  }

  public SearchResult<CategoryAttributeHeader> getCategoryAttributeValueHeader(RelatedCategorySearchCondition condition) {
    CategoryAttributeHeaderQuery query = new CategoryAttributeHeaderQuery();
    return DatabaseUtil.executeSearch(query.createCategoryAttributeHeaderQuery(condition));
  }

  public List<CategoryAttribute> getCategoryAttributeList(String categoryCode) {

    if (StringUtil.isNullOrEmpty(categoryCode)) {
      return null;
    }

    Query query = new SimpleQuery(RelatedCategoryQuery.GET_CATEGORY_ATTRIBUTE_LIST_QUERY, categoryCode);
    return DatabaseUtil.loadAsBeanList(query, CategoryAttribute.class);
  }

  public CategoryAttributeValue getCategoryAttributeValue(String categoryCode, Long categoryAttributeNo, String shopCode,
      String commodityCode) {

    if (!StringUtil.hasValueAnyOf(categoryCode, shopCode, commodityCode) || NumUtil.isNull(categoryAttributeNo)) {
      return null;
    }

    CategoryAttributeValueDao dao = DIContainer.getDao(CategoryAttributeValueDao.class);
    return dao.load(shopCode, categoryCode, categoryAttributeNo, commodityCode);
  }

  public ServiceResult registerCategoryCommodity(List<CategoryCommodity> categoryCommodity) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェック処理を実行する

    for (CategoryCommodity cc : categoryCommodity) {

      // カテゴリ検索パスの取得

      String categorySearchPath = CommonLogic.getCategorySearchPath(cc.getCategoryCode());
      cc.setCategorySearchPath(categorySearchPath);

      setUserStatus(cc);

      ValidationSummary summary = BeanValidator.validate(cc);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }

    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(RelatedCategoryQuery.GET_DELETE_CATEGORY_COMMODITY_ALL_QUERY, categoryCommodity.get(0).getShopCode(),
          categoryCommodity.get(0).getCommodityCode());
      CategoryDao dao = DIContainer.getDao(CategoryDao.class);
      boolean result = true;
      for (CategoryCommodity cc : categoryCommodity) {
        if (dao.exists(cc.getCategoryCode())) {
          txMgr.insert(cc);
        } else {
          result = false;
          serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          break;
        }
      }
      // 20120216 os013 add start
      updateCategorySearchPath(txMgr, categoryCommodity, categoryCommodity.get(0).getCommodityCode(), categoryCommodity.get(0)
          .getShopCode());
      // 20120216 os013 add end
      if (result) {
        txMgr.commit();
      } else {
        txMgr.rollback();
      }
    } catch (RuntimeException e) {
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public ServiceResult deleteCategoryCommodity(String shopCode, String commodityCode) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    List<CategoryCommodity> categoryCommodityList = DatabaseUtil.loadAsBeanList(new SimpleQuery(
        RelatedCategoryQuery.GET_CATEGORY_COMMODITY_LIST_QUERY, shopCode, commodityCode), CategoryCommodity.class);

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      for (CategoryCommodity cc : categoryCommodityList) {
        txMgr.executeUpdate(RelatedCategoryQuery.GET_DELETE_CATEGORY_ATTRIBUTE_VALUE_QUERY, cc.getCategoryCode(), shopCode,
            commodityCode);
      }
      txMgr.executeUpdate(new SimpleQuery(RelatedCategoryQuery.GET_DELETE_CATEGORY_COMMODITY_ALL_QUERY, shopCode, commodityCode));
      List<CategoryCommodity> categoryCommodity = new ArrayList<CategoryCommodity>();
      updateCategorySearchPath(txMgr, categoryCommodity, commodityCode, shopCode);
      txMgr.commit();
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public List<CategoryCommodity> getCategoryCommodityList(String shopCode, String commodityCode) {

    List<CategoryCommodity> list = null;

    if (StringUtil.hasValueAllOf(shopCode, commodityCode)) {
      Query query = new SimpleQuery(RelatedCategoryQuery.GET_CATEGORY_COMMODITY_LIST_QUERY, shopCode, commodityCode);
      list = DatabaseUtil.loadAsBeanList(query, CategoryCommodity.class);
    }

    return list;
  }

  public List<Category> getCategoryListFromPath(String categoryCode, String categoryPath) {
    List<Category> result = null;

    if (StringUtil.hasValueAllOf(categoryCode, categoryPath)) {
      Query query = new SimpleQuery(CatalogQuery.GET_CATEGORY_LIST_FROM_PATH, categoryCode, SqlDialect.getDefault().escape(
          categoryPath)
          + "%");
      result = DatabaseUtil.loadAsBeanList(query, Category.class);
    }

    return result;
  }

  public ServiceResult deleteCategoryAttributeValue(String categoryCode, Long categoryAttributeNo) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    if (StringUtil.isNullOrEmpty(categoryCode) || NumUtil.isNull(categoryAttributeNo)) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return serviceResult;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(CatalogQuery.DELETE_CATEGORY_ATTRIBUTE, categoryCode, categoryAttributeNo);
      txMgr.executeUpdate(CatalogQuery.DELETE_CATEGORY_ATTRIBUTE_VALUE, categoryCode, categoryAttributeNo);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  public List<CommodityLayout> getCommodityLayoutList(String shopCode) {
    List<CommodityLayout> result = new ArrayList<CommodityLayout>();

    if (StringUtil.hasValue(shopCode)) {
      Query query = new SimpleQuery(CommodityLayoutQuery.GET_COMMODITY_LAYOUT_LIST, shopCode);
      result = DatabaseUtil.loadAsBeanList(query, CommodityLayout.class);
    }

    return result;
  }

  public CommodityLayout getCommodityLayout(String shopCode, String partsCode) {
    CommodityLayout result = null;

    if (StringUtil.hasValueAllOf(shopCode, partsCode)) {
      CommodityLayoutDao dao = DIContainer.getDao(CommodityLayoutDao.class);
      result = dao.load(shopCode, partsCode);
    }

    return result;
  }

  public ServiceResult registerCommodityLayout(List<CommodityLayout> layoutList) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    ValidationSummary summary = null;

    for (CommodityLayout cl : layoutList) {
      summary = BeanValidator.validate(cl);
    }

    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    try {
      txMgr.begin(getLoginInfo());
      for (CommodityLayout cl : layoutList) {
        txMgr.update(cl);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
    } finally {
      txMgr.dispose();
    }
    return serviceResult;

  }

  public Campaign getCampaign(String shopCode, String campaignCode) {
    Campaign result = null;

    if (StringUtil.hasValueAllOf(shopCode, campaignCode)) {
      CampaignDao dao = DIContainer.getDao(CampaignDao.class);
      result = dao.load(shopCode, campaignCode);
    }

    return result;
  }

  public Campaign getAppliedCampaignInfo(String shopCode, String commodityCode) {
    Campaign result = null;

    if (StringUtil.hasValueAllOf(shopCode, commodityCode)) {
      Date appliedDate = DateUtil.truncateDate(DateUtil.getSysdate()); // 10.1.7
      // Query q = new SimpleQuery(CatalogQuery.GET_APPLIED_CAMPAIGN_INFO,
      // appliedDate, appliedDate, shopCode, commodityCode);
      // result = DatabaseUtil.loadAsBean(q, Campaign.class);
      CacheContainer cc = getCacheContainer();
      final Query query = new SimpleQuery(CatalogQuery.GET_APPLIED_CAMPAIGN_INFO, appliedDate, appliedDate, shopCode, commodityCode);
      result = cc.get(CacheKey.create(query), new CacheRetriever<Campaign>() {

        public Campaign retrieve() {
          return DatabaseUtil.loadAsBean(query, Campaign.class);
        }
      });
      return result;
    }

    return result;
  }

  public Campaign getAppliedCampaignBySku(String shopCode, String skuCode) {
    CommodityDetailDao dao = DIContainer.getDao(CommodityDetailDao.class);
    CommodityDetail detail = dao.load(shopCode, skuCode);

    Campaign result = null;
    if (detail == null) {
      return null;
    }

    if (StringUtil.hasValueAllOf(shopCode, detail.getCommodityCode())) {
      Date appliedDate = DateUtil.truncateDate(DateUtil.getSysdate());
      Query q = new SimpleQuery(CatalogQuery.GET_APPLIED_CAMPAIGN_INFO, appliedDate, appliedDate, shopCode, detail
          .getCommodityCode());
      result = DatabaseUtil.loadAsBean(q, Campaign.class);
    }
    return result;
  }

  public Campaign getAppliedCampaignInfo(String shopCode, String commodityCode, String appliedDate) {
    Campaign result = null;
    if (StringUtil.hasValueAllOf(shopCode, commodityCode, appliedDate)) {
      Query q = new SimpleQuery(CatalogQuery.GET_APPLIED_CAMPAIGN_INFO, appliedDate, appliedDate, shopCode, commodityCode);
      result = DatabaseUtil.loadAsBean(q, Campaign.class);
    }
    return result;
  }

  public ServiceResult resetCommodityLayout(String shopCode) {
    ServiceResultImpl result = new ServiceResultImpl();

    if (StringUtil.hasValue(shopCode)) {
      List<CommodityLayout> layoutList = CommonLogic.createCommodityLayout(shopCode);

      TransactionManager txMgr = DIContainer.getTransactionManager();
      try {
        txMgr.begin(getLoginInfo());
        txMgr.executeUpdate(CommodityLayoutQuery.GET_DELETE_COMMODITY_LAYOUT_SHOP_QUERY, shopCode);
        for (CommodityLayout cl : layoutList) {
          setUserStatus(cl);
          txMgr.insert(cl);
        }
        txMgr.commit();
      } catch (RuntimeException e) {
        txMgr.rollback();
        // 10.1.2 10094 追加 ここから
        result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
        // 10.1.2 10094 追加 ここまで
      } finally {
        txMgr.dispose();
      }
    }
    return result;
  }

  public List<Category> getSubCategoryTree(String categoryCode) {
    List<Category> list = new ArrayList<Category>();
    if (StringUtil.hasValue(categoryCode)) {
      Query query = new SimpleQuery(CatalogQuery.GET_SUB_CATEGORY, categoryCode);
      list = DatabaseUtil.loadAsBeanList(query, Category.class);
    }
    return list;
  }

  public ServiceResult generateRankingSummary(int month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    if (month <= 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR);
      return serviceResult;
    }

    try {
      StoredProcedureResult result;
      WebshopConfig config = DIContainer.getWebshopConfig();
      if (config.isSortedOrderAmount()) {
        result = DatabaseUtil.executeProcedure(new RankingSummaryProcedure(month, 0, this.getLoginInfo().getRecordingFormat()));
      } else {
        result = DatabaseUtil.executeProcedure(new RankingSummaryProcedure(month, 1, this.getLoginInfo().getRecordingFormat()));
      }

      if (result.getParameters().get("r_result").toString().equals(StoredProceduedResultType.FAILED.getValue())) {
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      }
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }

    return serviceResult;
  }

  public List<CodeAttribute> getTagCommodityList(String shopCode, String commodityCode) {
    List<CodeAttribute> list = null;
    if (StringUtil.hasValueAllOf(shopCode, commodityCode)) {
      Query query = new SimpleQuery(CatalogQuery.GET_TAG_COMMODITY_LIST, shopCode, shopCode, commodityCode);
      List<Tag> tagCommodityList = DatabaseUtil.loadAsBeanList(query, Tag.class);
      List<NameValue> tagList = new ArrayList<NameValue>();
      for (Tag tt : tagCommodityList) {
        tagList.add(new NameValue(tt.getTagName(), tt.getTagCode()));
      }
      list = new ArrayList<CodeAttribute>(tagList);
    }
    return list;
  }

  public List<RankingSearchResult> getRankingSummaryByOrder(String shopCode) {
    return getRankingSummary(shopCode, true);
  }

  public List<RankingSearchResult> getRankingSummaryByCount(String shopCode) {
    return getRankingSummary(shopCode, false);
  }

  private List<RankingSearchResult> getRankingSummary(String shopCode, boolean isSortedOrderAmount) {

    WebshopConfig config = DIContainer.getWebshopConfig();
    Query query = null;

    if (config.getSiteShopCode().equals(shopCode)) {
      query = new SimpleQuery(CatalogQuery.getRankingQuery(shopCode, isSortedOrderAmount), config.getRankingCommodityMaxCount());
    } else {
      query = new SimpleQuery(CatalogQuery.getRankingQuery(shopCode, isSortedOrderAmount), shopCode, config
          .getRankingCommodityMaxCount());
    }

    return DatabaseUtil.loadAsBeanList(query, RankingSearchResult.class);
  }

  public ServiceResult generateAutoRecommendSummary(String month) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    try {
      StoredProcedureResult result = DatabaseUtil.executeProcedure(new AutoRecommendSummaryProcedure(month, this.getLoginInfo()
          .getRecordingFormat()));
      if (result.getParameters().get("r_result").toString().equals(StoredProceduedResultType.FAILED.getValue())) {
        serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      }
    } catch (RuntimeException e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  public ServiceResult generateCategorySummary() {
    ServiceResultImpl sResult = new ServiceResultImpl();
    try {
      StoredProcedureResult result = DatabaseUtil.executeProcedure(new CategorySummaryProcedure(this.getLoginInfo()
          .getRecordingFormat()));
      String lResult = String.valueOf(result.getParameters().get("r_result").toString());
      if (lResult.equals("8")) {
        sResult.addServiceError(CatalogServiceErrorContent.WORKTABLE_INITIALIZE_ERROR);
      } else if (lResult.equals("9")) {
        sResult.addServiceError(CatalogServiceErrorContent.CATEGORY_SUMMARY_EXECUTE_ERROR);
      }
    } catch (RuntimeException e) {
      sResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return sResult;
  }

  public ServiceResult updateRevisionPricing(String revisionDate) {
    ServiceResultImpl result = new ServiceResultImpl();

    SimpleQuery detailQuery = new SimpleQuery(CatalogQuery.REVISION_PRICING_DETAIL, this.getLoginInfo().getRecordingFormat(),
        revisionDate);

    SimpleQuery headerQuery = new SimpleQuery(CatalogQuery.REVISION_PRICING_HEADER, this.getLoginInfo().getRecordingFormat(),
        revisionDate);

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(detailQuery);
      txMgr.executeUpdate(headerQuery);
      txMgr.commit();
    } catch (RuntimeException e) {
      txMgr.rollback();
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(e);
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public List<ArrivalGoods> getIntendedArrivalGoods() {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_ARRIVAL_GOODS_MAIL_INTENDED_COMMODITY, StockManagementType.WITH_QUANTITY
        .getValue(), StockManagementType.WITH_STATUS.getValue(), StockManagementType.NONE.getValue(), StockManagementType.NOSTOCK
        .getValue(), ArrivalGoodsFlg.ACCEPTABLE.getValue(), DisplayFlg.VISIBLE.getValue(), SaleFlg.FOR_SALE.getValue());

    return DatabaseUtil.loadAsBeanList(query, ArrivalGoods.class);
  }

  public Stock getStock(String shopCode, String skuCode) {
    Stock stock = new Stock();
    if (StringUtil.hasValueAllOf(shopCode, skuCode)) {
      StockDao dao = DIContainer.getDao(StockDao.class);
      stock = dao.load(shopCode, skuCode);
    }
    return stock;
  }

  public List<CommodityDetail> getChangeableReservationToOrderCommodity() {
    Query query = new SimpleQuery(CatalogQuery.GET_CHANGEABLE_RESERVED_TO_ORDER_COMMODITY, DateUtil.getDateTimeString());
    return DatabaseUtil.loadAsBeanList(query, CommodityDetail.class);
  }

  public List<CommodityDetail> getChangeableReservationToOrderCommodity(String availableDate) {
    Query query = new SimpleQuery(CatalogQuery.GET_CHANGEABLE_RESERVED_TO_ORDER_COMMODITY, availableDate);
    return DatabaseUtil.loadAsBeanList(query, CommodityDetail.class);
  }

  public List<ArrivalGoods> getArrivalGoodsList(String shopCode, String skuCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_ARRIVAL_GOODS_MAIL_INTENDED_CUSTOMER, shopCode, skuCode);
    return DatabaseUtil.loadAsBeanList(query, ArrivalGoods.class);
  }

  private void convertCommodity(CommodityHeadline commodityHeadline, Object object) {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      Class<? extends Object> fromRowType = commodityHeadline.getClass();
      Class<? extends Object> toRowType = object.getClass();

      Field[] fields = commodityHeadline.getClass().getDeclaredFields();
      for (Field fi : fields) {
        Field[] dFields = object.getClass().getDeclaredFields();
        for (Field dfi : dFields) {
          if (fi.getName().equals(dfi.getName()) && !fi.getName().equals("serialVersionUID")) {
            StringBuilder builder = new StringBuilder();
            builder.append(dfi.getName().substring(0, 1).toUpperCase(Locale.getDefault()));
            builder.append(dfi.getName().substring(1));

            Class<? extends Object> returnType = fromRowType.getMethod("get" + builder.toString(), new Class[0]).getReturnType();
            Method getMethod = fromRowType.getMethod("get" + builder.toString(), new Class[0]);
            toRowType.getMethod("set" + builder.toString(), returnType).invoke(object,
                getMethod.invoke(commodityHeadline, new Object[0]));
          }
          continue;
        }
      }
    } catch (IllegalAccessException e) {
      logger.error(e);
    } catch (NoSuchMethodException e) {
      logger.error(e);
    } catch (InvocationTargetException e) {
      logger.error(e);
    }
  }

  /**
   * 根据商品keyList的顺序，将商品CommodityContainer排序
   * 
   * @param keyList
   * @param list
   * @return
   * @author lichuang
   */
  private List<CommodityContainer> createCommodityContainer(List<CommodityKey> keyList, List<CommodityHeadline> list) {

    List<CommodityContainer> containerList = new ArrayList<CommodityContainer>();

    for (CommodityKey ck : keyList) {
      CommodityHeadline commodityHeadline = null;
      // 根据keyList的顺序将list排序
      for (CommodityHeadline ch : list) {
        if (ck.getCommodityCode().equals(ch.getCommodityCode())) {
          commodityHeadline = ch;
          break;
        }
      }

      CommodityHeader commodityHeader = new CommodityHeader();
      CommodityDetail commodityDetail = new CommodityDetail();
      Stock stock = new Stock();
      Category category = new Category();
      CampaignCommodity campaignCommodity = new CampaignCommodity();
      TagCommodity tagCommodity = new TagCommodity();
      GiftCommodity giftCommodity = new GiftCommodity();
      RelatedCommodityA relatedCommodityA = new RelatedCommodityA();
      RelatedCommodityB relatedCommodityB = new RelatedCommodityB();
      Campaign campaign = new Campaign();
      Gift gift = new Gift();
      Tag tag = new Tag();
      StockStatus stockStatus = new StockStatus();
      ReviewPost reviewPost = new ReviewPost();
      ReviewSummary reviewSummary = new ReviewSummary();
      PopularRankingDetail popularRankingDetail = new PopularRankingDetail();
      ArrivalGoods arrivalGoods = new ArrivalGoods();
      Shop shop = new Shop();
      ContainerAddInfo containerAddInfo = new ContainerAddInfo();
      CommodityLayout commodityLayout = new CommodityLayout();
      Brand brand = new Brand();

      convertCommodity(commodityHeadline, commodityHeader);
      convertCommodity(commodityHeadline, commodityDetail);
      convertCommodity(commodityHeadline, stock);
      convertCommodity(commodityHeadline, category);
      convertCommodity(commodityHeadline, campaignCommodity);
      convertCommodity(commodityHeadline, tagCommodity);
      convertCommodity(commodityHeadline, giftCommodity);
      convertCommodity(commodityHeadline, relatedCommodityA);
      convertCommodity(commodityHeadline, relatedCommodityB);
      convertCommodity(commodityHeadline, campaign);
      convertCommodity(commodityHeadline, gift);
      convertCommodity(commodityHeadline, tag);
      convertCommodity(commodityHeadline, stockStatus);
      convertCommodity(commodityHeadline, reviewPost);
      convertCommodity(commodityHeadline, reviewSummary);
      convertCommodity(commodityHeadline, popularRankingDetail);
      convertCommodity(commodityHeadline, arrivalGoods);
      convertCommodity(commodityHeadline, shop);
      convertCommodity(commodityHeadline, containerAddInfo);
      convertCommodity(commodityHeadline, commodityLayout);
      convertCommodity(commodityHeadline, brand);

      CommodityContainer container = new CommodityContainer();
      container.setCommodityHeader(commodityHeader);
      container.setCommodityDetail(commodityDetail);
      container.setStock(stock);
      container.setCategory(category);
      container.setCampaignCommodity(campaignCommodity);
      container.setTagCommodity(tagCommodity);
      container.setGiftCommodity(giftCommodity);
      container.setRelatedCommodityA(relatedCommodityA);
      container.setRelatedCommodityB(relatedCommodityB);
      container.setCampaign(campaign);
      container.setGift(gift);
      container.setTag(tag);
      container.setStockStatus(stockStatus);
      container.setReviewPost(reviewPost);
      container.setReviewSummary(reviewSummary);
      container.setPopularRankingDetail(popularRankingDetail);
      container.setArrivalGoods(arrivalGoods);
      container.setShop(shop);
      container.setContainerAddInfo(containerAddInfo);
      container.setCommodityLayout(commodityLayout);
      container.setBrand(brand);
      containerList.add(container);

    }

    return containerList;

  }

  private List<CommodityContainer> createCommodityContainer(List<CommodityHeadline> list) {

    List<CommodityContainer> containerList = new ArrayList<CommodityContainer>();

    for (CommodityHeadline commodityHeadline : list) {
      CommodityHeader commodityHeader = new CommodityHeader();
      CommodityDetail commodityDetail = new CommodityDetail();
      Stock stock = new Stock();
      Category category = new Category();
      CampaignCommodity campaignCommodity = new CampaignCommodity();
      TagCommodity tagCommodity = new TagCommodity();
      GiftCommodity giftCommodity = new GiftCommodity();
      RelatedCommodityA relatedCommodityA = new RelatedCommodityA();
      RelatedCommodityB relatedCommodityB = new RelatedCommodityB();
      Campaign campaign = new Campaign();
      Gift gift = new Gift();
      Tag tag = new Tag();
      StockStatus stockStatus = new StockStatus();
      ReviewPost reviewPost = new ReviewPost();
      ReviewSummary reviewSummary = new ReviewSummary();
      PopularRankingDetail popularRankingDetail = new PopularRankingDetail();
      ArrivalGoods arrivalGoods = new ArrivalGoods();
      Shop shop = new Shop();
      ContainerAddInfo containerAddInfo = new ContainerAddInfo();
      CommodityLayout commodityLayout = new CommodityLayout();
      Brand brand = new Brand();

      convertCommodity(commodityHeadline, commodityHeader);
      convertCommodity(commodityHeadline, commodityDetail);
      convertCommodity(commodityHeadline, stock);
      convertCommodity(commodityHeadline, category);
      convertCommodity(commodityHeadline, campaignCommodity);
      convertCommodity(commodityHeadline, tagCommodity);
      convertCommodity(commodityHeadline, giftCommodity);
      convertCommodity(commodityHeadline, relatedCommodityA);
      convertCommodity(commodityHeadline, relatedCommodityB);
      convertCommodity(commodityHeadline, campaign);
      convertCommodity(commodityHeadline, gift);
      convertCommodity(commodityHeadline, tag);
      convertCommodity(commodityHeadline, stockStatus);
      convertCommodity(commodityHeadline, reviewPost);
      convertCommodity(commodityHeadline, reviewSummary);
      convertCommodity(commodityHeadline, popularRankingDetail);
      convertCommodity(commodityHeadline, arrivalGoods);
      convertCommodity(commodityHeadline, shop);
      convertCommodity(commodityHeadline, containerAddInfo);
      convertCommodity(commodityHeadline, commodityLayout);
      convertCommodity(commodityHeadline, brand);

      CommodityContainer container = new CommodityContainer();
      container.setCommodityHeader(commodityHeader);
      container.setCommodityDetail(commodityDetail);
      container.setStock(stock);
      container.setCategory(category);
      container.setCampaignCommodity(campaignCommodity);
      container.setTagCommodity(tagCommodity);
      container.setGiftCommodity(giftCommodity);
      container.setRelatedCommodityA(relatedCommodityA);
      container.setRelatedCommodityB(relatedCommodityB);
      container.setCampaign(campaign);
      container.setGift(gift);
      container.setTag(tag);
      container.setStockStatus(stockStatus);
      container.setReviewPost(reviewPost);
      container.setReviewSummary(reviewSummary);
      container.setPopularRankingDetail(popularRankingDetail);
      container.setArrivalGoods(arrivalGoods);
      container.setShop(shop);
      container.setContainerAddInfo(containerAddInfo);
      container.setCommodityLayout(commodityLayout);
      container.setBrand(brand);
      container.setUseFlg(commodityHeadline.getUseFlg());
      containerList.add(container);
    }
    return containerList;

  }

  public SearchResult<CommodityContainer> getFavoriteCommodities(CommodityContainerCondition condition) {
    CommodityContainerQuery query = new CommodityContainerQuery();
    SearchResult<CommodityHeadline> result = DatabaseUtil.executeSearch(query.createFavoriteCommodityQuery(condition));

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;

  }

  public SearchResult<CommodityContainer> getRecommendedCommodities(CommodityContainerCondition condition) {
    CommodityContainerQuery query = new CommodityContainerQuery();
    SearchResult<CommodityHeadline> result = DatabaseUtil.executeSearch(query.createRecomendCommodityQuery(condition));
    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();

    if (result.getRowCount() == 0) {
      searchResult.setCurrentPage(result.getCurrentPage());
      searchResult.setMaxFetchSize(result.getMaxFetchSize());
      searchResult.setOverflow(result.isOverflow());
      searchResult.setPageSize(result.getPageSize());
      searchResult.setRowCount(result.getRowCount());
      CommodityContainerCondition tempCondition = new CommodityContainerCondition();
      tempCondition.setDisplayClientType(DisplayClientType.PC.getValue());
      tempCondition.setMaxFetchSize(5);

      SearchResult<CommodityContainer> tempResult = getSalesChartsList(tempCondition);
      searchResult.setRows(tempResult.getRows());

      return searchResult;
    }

    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;

  }

  public SearchResult<CommodityContainer> getCommoditySearch(CommodityListSearchCondition condition) {
    SearchResult<CommodityKey> keyList = findCommodityKey(condition);
    return getCommoditySearch(keyList);
  }

  /**
   * @deprecated 旧ロジック
   */
  public SearchResult<CommodityContainer> getCommoditySearchObsoleted(CommodityListSearchCondition condition) {
    CommodityListSearchQuery query = new CommodityListSearchQuery(condition);
    SearchResult<CommodityHeadline> result = DatabaseUtil.executeSearch(query);

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;
  }

  public SearchResult<CommodityContainer> getCommoditySearch(SearchResult<CommodityKey> result) {

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    List<CommodityKey> keyList = result.getRows();

    if (keyList != null && keyList.size() > 0) {
      ContainerBackQuery query = new ContainerBackQuery(keyList);
      query.setMaxFetchSize(result.getMaxFetchSize());
      query.setPageSize(result.getPageSize());

      SearchResult<CommodityHeadline> rr = DatabaseUtil.executeSearch(query);

      List<CommodityContainer> rows = createCommodityContainer(rr.getRows());
      Collections.sort(rows, new CommodityKey.ContainerComparator(keyList));
      searchResult.setRows(rows);
    }
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());

    return searchResult;
  }

  public SearchResult<CSynchistory> getCynchroHiSearchResult(CommodityHistorySearchCondition contition) {
    CommodityHistoryQuery query = new CommodityHistoryQuery(contition);
    query.setMaxFetchSize(contition.getMaxFetchSize());
    query.setPageNumber(contition.getCurrentPage());
    query.setPageSize(contition.getPageSize());
    SearchResult<CSynchistory> searchResult = DatabaseUtil.executeSearch(query);
    searchResult.setCurrentPage(contition.getCurrentPage());
    searchResult.setMaxFetchSize(contition.getMaxFetchSize());
    searchResult.setPageSize(contition.getPageSize());
    return searchResult;
  }

  private void insertSearchKeywordLog(SearchKey key, String value) {
    if (StringUtil.isNullOrEmpty(value)) {
      return;
    }
    TransactionManager txmgr = DIContainer.getTransactionManager();
    try {
      txmgr.begin(getLoginInfo());
      InsertSearchKeywordLogProcedure proc = new InsertSearchKeywordLogProcedure();
      proc.setSearchType(key.getName());
      proc.setSearchWord(value);
      proc.setUpdatedUser(getLoginInfo().getRecordingFormat());
      txmgr.executeProcedure(proc);
      txmgr.commit();
    } catch (RuntimeException e) {
      txmgr.rollback();
    } finally {
      txmgr.dispose();
    }
  }

  private void insertSearchKeywordLog(CommodityContainerCondition condition) {
    Set<String> wordsSet = new HashSet<String>();
    wordsSet.addAll(StringUtil.getSearchWordStringList(condition.getSearchWord()));
    for (String word : wordsSet) {
      insertSearchKeywordLog(SearchKey.KEYWORD, word);
    }
    insertSearchKeywordLog(SearchKey.COMMODITY_CODE, condition.getSearchCommodityCode());
    insertSearchKeywordLog(SearchKey.CATEGORY, condition.getSearchCategoryCode());
    insertSearchKeywordLog(SearchKey.PRICE, condition.getSearchPriceStart());
    insertSearchKeywordLog(SearchKey.PRICE, condition.getSearchPriceEnd());
    insertSearchKeywordLog(SearchKey.REVIEW_SCORE, condition.getReviewScore());
    WebshopConfig config = DIContainer.getWebshopConfig();
    if (!config.isOne()) {
      // 一店舗版の場合、ショップコードは集計しない
      insertSearchKeywordLog(SearchKey.SHOP_CODE, condition.getSearchShopCode());
    }
  }

  private SearchResult<CommodityKey> findCommodityKey(CommodityContainerCondition condition) {
    // CommodityKeyFrontQuery query = new CommodityKeyFrontQuery(condition);
    // SearchResult<CommodityKey> result = DatabaseUtil.executeSearch(query);
    // return result;
    CacheContainer cc = getCacheContainer();
    final CommodityKeyFrontQuery query = new CommodityKeyFrontQuery(condition);
    SearchResult<CommodityKey> result = cc.get(CacheKey.create(query), new CacheRetriever<SearchResult<CommodityKey>>() {

      public SearchResult<CommodityKey> retrieve() {
        return DatabaseUtil.executeSearch(query);
      }
    });
    return result;
  }

  private SearchResult<CommodityKey> findCommodityKey(CommodityListSearchCondition condition) {
    CommodityKeyBackQuery query = new CommodityKeyBackQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }

  public SearchResult<CommodityContainer> getCommodityContainer(SearchResult<CommodityKey> result, String alignmentSequence,
      boolean planDetailFlag, CommodityContainerCondition condition) {

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    List<CommodityKey> keyList = result.getRows();

    if (keyList != null && keyList.size() > 0) {
      final ContainerFrontQuery query = new ContainerFrontQuery(keyList, alignmentSequence, planDetailFlag, condition);
      query.setMaxFetchSize(result.getMaxFetchSize());
      query.setPageSize(result.getPageSize());

      CacheContainer cc = getCacheContainer();
      SearchResult<CommodityHeadline> rr = cc.get(CacheKey.create(query), new CacheRetriever<SearchResult<CommodityHeadline>>() {

        public SearchResult<CommodityHeadline> retrieve() {
          return DatabaseUtil.executeSearch(query);
        }
      });

      List<CommodityContainer> rows = null;
      if (StringUtil.isNullOrEmpty(condition.getSearchSpecFlag())) {
        rows = createCommodityContainer(rr.getRows());
        searchResult.setRows(rows);
        Collections.sort(rows, new CommodityKey.ContainerComparator(keyList));
      } else {
        rows = createCommodityContainer(rr.getRows());
        searchResult.setRows(rows);
      }

    }

    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());

    return searchResult;
  }

  public SearchResult<CommodityContainer> fastFindCommodityContainer(CommodityContainerCondition condition, boolean planDetailFlag) {
    insertSearchKeywordLog(condition);
    SearchResult<CommodityKey> keyList = findCommodityKey(condition);
    return getCommodityContainer(keyList, condition.getAlignmentSequence(), planDetailFlag, condition);
  }

  // 优化购买了XXX商品的人还买了XXXX add by yyq start 20130417
  public SearchResult<CommodityContainer> fastFindDetailRecommendBContainer(CommodityContainerCondition condition) {
    SearchResult<CommodityKey> keyList = findDetailRecommendBKey(condition);
    return getCommodityContainer(keyList, condition.getAlignmentSequence(), false, condition);
  }

  private SearchResult<CommodityKey> findDetailRecommendBKey(CommodityContainerCondition condition) {
    // CommodityKeyDetailRecommendBQuery query = new
    // CommodityKeyDetailRecommendBQuery(condition);
    // SearchResult<CommodityKey> result = DatabaseUtil.executeSearch(query);
    // return result;
    CacheContainer cc = getCacheContainer();
    final CommodityKeyDetailRecommendBQuery query = new CommodityKeyDetailRecommendBQuery(condition);
    SearchResult<CommodityKey> result = cc.get(CacheKey.create(query), new CacheRetriever<SearchResult<CommodityKey>>() {

      public SearchResult<CommodityKey> retrieve() {
        return DatabaseUtil.executeSearch(query);
      }
    });
    return result;
  }

  // 优化购买了XXX商品的人还买了XXXX add by yyq end 20130417

  /**

   */
  public SearchResult<CommodityContainer> getCommodityContainer(CommodityContainerCondition condition) {
    // 検索カテゴリコードが空の場合は、ルートカテゴリコードを設定
    condition.setSearchCategoryCode(StringUtil.coalesceEmptyValue(condition.getSearchCategoryCode(), getRootCategory()
        .getCategoryCode()));

    CommodityContainerQuery query = new CommodityContainerQuery();
    SearchResult<CommodityHeadline> result = DatabaseUtil.executeSearch(query.createCommodityListQuery(condition));

    insertSearchKeywordLog(condition);

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;
  }

  public SearchResult<CommodityContainer> getCommodityContainerBySku(CommodityContainerCondition condition) {
    condition.setByRepresent(false);
    return getCommodityContainer(condition);
  }

  public SearchResult<CommodityContainer> getRecommendAList(CommodityContainerCondition condition) {
    CommodityContainerQuery query = new CommodityContainerQuery();
    SearchResult<CommodityHeadline> result = DatabaseUtil.executeSearch(query.createRelatedCommodityAList(condition));

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;
  }

  // add by wjw 20120103 start
  public SearchResult<CommodityContainer> getRecommendCList(CommodityContainerCondition condition) {
    // CommodityContainerQuery query = new CommodityContainerQuery();
    // SearchResult<CommodityHeadline> result =
    // DatabaseUtil.executeSearch(query.createRelatedCommodityCList(condition));
    CacheContainer cc = getCacheContainer();
    final CommodityContainerQuery query = new CommodityContainerQuery();
    query.createRelatedCommodityCList(condition);
    SearchResult<CommodityHeadline> result = cc.get(CacheKey.create(query), new CacheRetriever<SearchResult<CommodityHeadline>>() {

      public SearchResult<CommodityHeadline> retrieve() {
        return DatabaseUtil.executeSearch(query);
      }
    });

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;
  }

  // add by wjw 20120103 end

  public SearchResult<CommodityContainer> getRecommendBList(CommodityContainerCondition condition) {
    CommodityContainerQuery query = new CommodityContainerQuery();
    SearchResult<CommodityHeadline> result = DatabaseUtil.executeSearch(query.createRelatedCommodityBList(condition));

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;
  }

  public List<CommodityContainer> getCommoditySkuList(String shopCode, String commodityCode, boolean isForSale,
      DisplayClientType displayClientType) {
    List<CommodityHeadline> result = null;

    CacheContainer cc = getCacheContainer();
    if (DisplayClientType.PC.getValue().equals(displayClientType.getValue())) {
      final SimpleQuery query = new SimpleQuery(CatalogQuery.getCommoditySkuQuery(isForSale));
      query.setParameters(commodityCode, shopCode);

      result = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

        public List<CommodityHeadline> retrieve() {
          return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
        }
      });
    } else {
      final SimpleQuery query = new SimpleQuery(CatalogQuery.GET_MOBILE_COMMODITY_SKU);
      query.setParameters(commodityCode, shopCode);
      result = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

        public List<CommodityHeadline> retrieve() {
          return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
        }
      });
    }

    return createCommodityContainer(result);
  }

  public List<CampaignCommodity> getExceptCampaignCommodityList(Campaign campaign) {

    String shopCode = campaign.getShopCode();
    String campaignCode = campaign.getCampaignCode();
    Long discountRate = campaign.getCampaignDiscountRate();
    Date startDate = campaign.getCampaignStartDate();

    Object[] params = {
        shopCode, shopCode, discountRate, discountRate, startDate, discountRate, startDate, campaignCode
    };

    CampaignCommodityDao dao = DIContainer.getDao(CampaignCommodityDao.class);
    return dao.findByQuery(CatalogQuery.GET_EXCEPT_CAMPAIGN_COMMODITY, params);
  }

  public boolean isSale(String shopCode, String skuCode) {
    CommodityInfo commodity = getSkuInfo(shopCode, skuCode);
    if (commodity == null) {
      return false;
    }

    if (isReserve(shopCode, skuCode)) {
      return false;
    }
    CommodityHeader commodityHeader = commodity.getHeader();
    Date saleStartDatetime = commodityHeader.getSaleStartDatetime();
    Date saleEndDatetime = commodityHeader.getSaleEndDatetime();
    return DateUtil.isPeriodDate(saleStartDatetime, saleEndDatetime, DateUtil.getSysdate());
  }

  public boolean isReserve(String shopCode, String skuCode) {
    CommodityInfo commodity = getSkuInfo(shopCode, skuCode);
    if (commodity == null) {
      return false;
    }

    CommodityHeader commodityHeader = commodity.getHeader();
    Date reservationStartDatetime = commodityHeader.getReservationStartDatetime();
    Date reservationEndDatetime = commodityHeader.getReservationEndDatetime();
    if (reservationStartDatetime == null && reservationEndDatetime == null) {
      return false;
    }
    return DateUtil.isPeriodDate(reservationStartDatetime, reservationEndDatetime, DateUtil.getSysdate());
  }

  public Long getCampaignCommodityCount(String shopCode, String campaignCode, String displayClientType) {
    Object[] values = new Object[] {
        shopCode, campaignCode, shopCode, campaignCode, displayClientType
    };
    Object result = DatabaseUtil.executeScalar(new SimpleQuery(CatalogQuery.GET_CAMPAIGN_COMMODITY_COUNT, values));

    Long count;
    if (result == null) {
      count = 0L;
    } else {
      count = NumUtil.parseLong(result);
    }

    return count;
  }

  private Category getActiveCategory(String shopCode, String commodityCode, String categoryCode) {

    // LIKE検索文の作成
    SqlDialect dialect = SqlDialect.getDefault();
    SqlFragment fragment = dialect.createLikeClause("A.PATH || '~' || A.CATEGORY_CODE", categoryCode,
        LikeClauseOption.PARTIAL_MATCH);
    String likeSql = fragment.getFragment();

    // バインド変数の作成

    Object[] params = new Object[] {
        shopCode, commodityCode, fragment.getParameters()[0]
    };

    // SQLの実行

    Query query = new SimpleQuery(CatalogQuery.getCommodityDetailCategory(likeSql), params);
    return DatabaseUtil.loadAsBean(query, Category.class);
  }

  private List<CategoryData> getSortedCategoryTree(List<CategoryData> categoryList) {
    return getSortedCategoryTree(categoryList, null);
  }

  private List<CategoryData> getSortedCategoryTree(List<CategoryData> categoryList, Category activeCategory) {
    // 取得したカテゴリリストを階層ごとに分類

    List<CategoryData> categoryHangar = new ArrayList<CategoryData>();
    List<List<CategoryData>> depthClassificationList = new ArrayList<List<CategoryData>>();
    int depth = 0;
    for (Iterator<CategoryData> iterator = categoryList.iterator(); iterator.hasNext();) {
      CategoryData category = iterator.next();
      if (depth == category.getDepth()) {
        categoryHangar.add(category);
        if (!iterator.hasNext()) {
          depthClassificationList.add(categoryHangar);
        }
      } else {
        depthClassificationList.add(categoryHangar);
        categoryHangar = new ArrayList<CategoryData>();
        categoryHangar.add(category);
        depth++;
        if (!iterator.hasNext()) {
          depthClassificationList.add(categoryHangar);
        }
      }
    }

    // 分類したカテゴリリストを並び替え
    List<CategoryData> categoryTree = new ArrayList<CategoryData>();
    boolean isRoot = true;
    for (List<CategoryData> classificationList : depthClassificationList) {
      for (CategoryData category : classificationList) {
        if (isRoot) {
          if (activeCategory != null) {
            category.setActive(category.getCategoryCode().equals(activeCategory.getCategoryCode()));
          }
          categoryTree.add(category);
          isRoot = false;
        } else {
          for (int i = 0; i < categoryTree.size(); i++) {
            if (category.getParentCategoryCode().equals(categoryTree.get(i).getCategoryCode())) {
              if (activeCategory != null) {
                category.setActive(category.getCategoryCode().equals(activeCategory.getCategoryCode()));
              }
              categoryTree.add(i + 1, category);
              break;
            }

            if (i == categoryTree.size() - 1) {
              Logger logger = Logger.getLogger(this.getClass());
              // logger.debug("カテゴリコード：" + category.getCategoryCode() +
              // "のデータ構造が不正です。");
              logger.debug(MessageFormat.format(Messages.log("service.impl.CatalogServiceImpl.2"), category.getCategoryCode()));
            }
          }
        }
      }
    }

    return categoryTree;
  }

  private List<CategoryData> getCategoryTree(Category activeCategory) {

    // ルートカテゴリの場合、2階層までのカテゴリツリーを返す

    if (activeCategory.getDepth() == 0) {
      List<CategoryData> categoryTree = getCategoryTree();
      for (CategoryData category : categoryTree) {
        category.setActive(category.getCategoryCode().equals(activeCategory.getCategoryCode()));
      }
      return categoryTree;
    }

    // 検索用カテゴリコードの設定

    String[] splitPath = activeCategory.getPath().split(CatalogQuery.CATEGORY_DELIMITER);
    // wjh modify start
    Object[] searchCategoryCodes = null;
    boolean categoryCodeFlg = false;
    if (splitPath.length <= 2) {
      searchCategoryCodes = new Object[1];
      categoryCodeFlg = false;
    } else {
      searchCategoryCodes = new Object[splitPath.length - 2];
      for (int i = 3; i < splitPath.length; i++) {
        searchCategoryCodes[i - 3] = splitPath[i];
      }
      categoryCodeFlg = true;
    }
    // wjh modify end
    searchCategoryCodes[searchCategoryCodes.length - 1] = activeCategory.getCategoryCode();

    // IN検索文の生成
    SqlDialect dialect = SqlDialect.getDefault();
    SqlFragment fragment = dialect.createInClause("CATEGORY_CODE", searchCategoryCodes);
    String inSql = fragment.getFragment();

    // バインド変数の生成

    List<Object> categoryCodesList = new ArrayList<Object>();
    for (int i = 0; i < searchCategoryCodes.length; i++) {
      categoryCodesList.add(fragment.getParameters()[i]);
    }
    categoryCodesList.add(activeCategory.getCategoryCode());
    // wjh add start
    categoryCodesList.add(activeCategory.getCategoryCode());
    if (categoryCodeFlg) {
      categoryCodesList.add(splitPath[splitPath.length - 1]);
    }
    // wjh add end
    Object[] categoryCodes = new Object[categoryCodesList.size()];
    for (int i = 0; i < categoryCodes.length; i++) {
      categoryCodes[i] = categoryCodesList.get(i);
    }

    // SQLの実行

    Query query = new SimpleQuery(CatalogQuery.getCategoryTreeList(inSql, categoryCodeFlg), categoryCodes);
    List<CategoryData> categoryList = DatabaseUtil.loadAsBeanList(query, CategoryData.class);

    return getSortedCategoryTree(categoryList, activeCategory);
  }

  private List<CategoryData> getCategoryTree() {

    // SQL文の生成?実行

    Query query = new SimpleQuery(CatalogQuery.GET_ROOT_CATEGORY_TREE);
    List<CategoryData> categoryList = DatabaseUtil.loadAsBeanList(query, CategoryData.class);

    return getSortedCategoryTree(categoryList);
  }

  public List<CategoryData> getCategoryTree(String categoryCode) {

    // categoryCodeがnullの場合、2階層までのカテゴリツリーを返す

    if (StringUtil.isNullOrEmpty(categoryCode)) {
      return getCategoryTree();
    }

    // categoryCodeを主キーとするカテゴリの詳細情報を取得

    CategoryDao dao = DIContainer.getDao(CategoryDao.class);
    Category activeCategory = dao.load(categoryCode);

    // categoryCodeが不正の場合、2階層までのカテゴリツリーを返す

    if (activeCategory == null) {
      return getCategoryTree();
    }

    // 取得したカテゴリ情報から生成されるカテゴリツリーを返す

    return getCategoryTree(activeCategory);
  }

  public List<CategoryData> getCategoryTree(String shopCode, String commodityCode) {

    // shopCode、あるいはcommodityCodeがnullだった場合、2階層までのカテゴリツリーを返す

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode)) {
      return getCategoryTree();
    }

    // バインド変数の生成

    Object[] params = new Object[] {
        shopCode, commodityCode
    };

    // shopCodeとcommodityCodeに関連付いている、もっとも階層の深いカテゴリを取得する

    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_DETAIL_CATEGORY_CODE, params);
    Category activeCategory = DatabaseUtil.loadAsBean(query, Category.class);

    // shopCode、あるいはcommodityCodeが不正だった場合、または取得したカテゴリが1階層だった場合、2階層までのカテゴリツリーを返す

    if (activeCategory == null) {
      return getCategoryTree();
    } else if (activeCategory.getDepth() == 0) {
      List<CategoryData> categoryTree = getCategoryTree(activeCategory);
      for (CategoryData category : categoryTree) {
        category.setActive(category.getCategoryCode().equals(activeCategory.getCategoryCode()));
      }

      return categoryTree;
    }

    // 取得したカテゴリ情報から生成されるカテゴリツリーを返す

    return getCategoryTree(activeCategory);
  }

  public List<CategoryData> getCategoryTree(String shopCode, String commodityCode, String categoryCode) {

    // shopCode、あるいはcommodityCodeがnullだった場合、categoryCodeから生成されるカテゴリツリーを返す

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode)) {
      return getCategoryTree(categoryCode);
    }

    // categoryCodeがnullのとき、shopCodeおよびcommodityCodeから生成されるカテゴリツリーを返す

    if (StringUtil.isNullOrEmpty(categoryCode)) {
      return getCategoryTree(shopCode, commodityCode);
    }

    // shopCodeとcommodityCodeとcategoryCodeに関連付いている、もっとも階層の深いカテゴリを取得

    Category activeCategory = getActiveCategory(shopCode, commodityCode, categoryCode);

    // shopCode、commodityCode、categoryCodeのいずれかが不正だった場合、または取得したカテゴリが1階層だった場合、2階層までのカテゴリツリーを返す

    if (activeCategory == null) {
      return getCategoryTree();
    } else if (activeCategory.getDepth() == 0) {
      List<CategoryData> categoryTree = getCategoryTree(activeCategory);
      for (CategoryData category : categoryTree) {
        category.setActive(category.getCategoryCode().equals(activeCategory.getCategoryCode()));
      }

      return categoryTree;
    }

    // 取得したカテゴリ情報から生成されるカテゴリツリーを返す

    return getCategoryTree(activeCategory);
  }

  private List<CodeAttribute> getTopicPath(Category activeCategory) {

    // activeCategoryがnullか0階層（ルートカテゴリ）だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0) {
      return Collections.emptyList();
    }

    // パンくずリストに設定するカテゴリの設定

    String[] splitPath = activeCategory.getPath().split(CatalogQuery.CATEGORY_DELIMITER);
    Object[] categoryCodes = new Object[splitPath.length - 1];
    for (int i = 2; i < splitPath.length; i++) {
      categoryCodes[i - 2] = splitPath[i];
    }
    categoryCodes[categoryCodes.length - 1] = activeCategory.getCategoryCode();

    // IN検索文の生成
    SqlDialect dialect = SqlDialect.getDefault();
    SqlFragment fragment = dialect.createInClause("CATEGORY_CODE", categoryCodes);
    String inSql = fragment.getFragment();

    // SQLの実行

    Query query = new SimpleQuery(CatalogQuery.getTopicPathList(inSql), fragment.getParameters());
    List<Category> categoryList = DatabaseUtil.loadAsBeanList(query, Category.class);

    // 20120525 tuxinwei add start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // 20120525 tuxinwei add end
    // パンくずリストの生成
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    for (Category category : categoryList) {
      // 20120525 tuxinwei add start
      CodeAttribute nameValue = new NameValue(utilService.getNameByLanguage(category.getCategoryNamePc(), category
          .getCategoryNamePcEn(), category.getCategoryNamePcJp()), "/category/C" + category.getCategoryCode());
      // 20120525 tuxinwei add end
      topicPath.add(nameValue);
    }

    return topicPath;
  }

  public List<CodeAttribute> getTopicPath(String categoryCode) {

    // categoryCodeが空の場合、空のパンくずリストを返す

    if (StringUtil.isNullOrEmpty(categoryCode)) {
      return Collections.emptyList();
    }

    // categoryCodeを主キーとするカテゴリの詳細情報を取得

    CategoryDao dao = DIContainer.getDao(CategoryDao.class);
    Category activeCategory = dao.load(categoryCode);

    // categoryCodeが不正、あるいは取得したカテゴリが0階層だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0) {
      return Collections.emptyList();
    }

    // 取得したカテゴリ情報から生成されるパンくずリストを返す

    return getTopicPath(activeCategory);
  }

  public List<CodeAttribute> getTopicPath(String shopCode, String commodityCode) {

    // shopCode、あるいはcommodityCodeがnullだった場合、ルートのパンくずリストを返す

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode)) {
      return Collections.emptyList();
    }

    // バインド変数の生成

    Object[] params = new Object[] {
        shopCode, commodityCode
    };

    // shopCode、およびcommodityCodeと関連付いている、もっとも階層の深いカテゴリを取得

    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_DETAIL_CATEGORY_CODE, params);
    Category activeCategory = DatabaseUtil.loadAsBean(query, Category.class);

    // shopCode、あるいはcommodityCodeが不正だった場合、または取得したカテゴリが1階層だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0) {
      return Collections.emptyList();
    }

    // 取得したカテゴリ情報から生成されるパンくずリストを返す

    return getTopicPath(activeCategory);
  }

  public List<CodeAttribute> getTopicPath(String shopCode, String commodityCode, String categoryCode) {

    // shopCode、あるいはcommodityCodeがnullだった場合、ルートのパンくずリストを返す

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode)) {
      return getTopicPath(categoryCode);
    }

    // categoryCodeがnullのとき、shopCodeおよびcommodityCodeから生成されるパンくずリストを返す

    if (StringUtil.isNullOrEmpty(categoryCode)) {
      return getTopicPath(shopCode, commodityCode);
    }

    // shopCodeとcommodityCodeとcategoryCodeに関連付いている、もっとも階層の深いカテゴリを取得

    Category activeCategory = getActiveCategory(shopCode, commodityCode, categoryCode);

    // shopCode、commodityCode、categoryCodeのいずれかが不正だった場合、または取得したカテゴリが1階層だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0) {
      return Collections.emptyList();
    }

    // 取得したカテゴリ情報から生成されるパンくずリストを返す

    return getTopicPath(activeCategory);
  }

  // 10.1.4 K00175 追加 ここから
  private boolean isShopOpen(String shopCode) {
    ShopDao sDao = DIContainer.getDao(ShopDao.class);
    Shop shop = sDao.load(shopCode);
    Date endDate = shop.getCloseDatetime();
    endDate = DateUtil.setHour(endDate, 23);
    endDate = DateUtil.setMinute(endDate, 59);
    endDate = DateUtil.setSecond(endDate, 59);
    return DateUtil.isPeriodDate(shop.getOpenDatetime(), endDate);
  }

  private boolean isReserveByCommodity(String shopCode, String commodityCode) {
    CommodityHeader header = getCommodityHeader(shopCode, commodityCode);
    if (header == null) {
      return false;
    }
    Date startDatetime = header.getReservationStartDatetime();
    Date endDatetime = header.getReservationEndDatetime();
    boolean hasValue = (startDatetime != null || endDatetime != null);
    return hasValue && DateUtil.isPeriodDate(startDatetime, endDatetime, DateUtil.getSysdate());
  }

  private boolean isSaleByCommodity(String shopCode, String commodityCode) {
    CommodityHeader header = getCommodityHeader(shopCode, commodityCode);
    if (header == null) {
      return false;
    }
    Date startDatetime = header.getSaleStartDatetime();
    Date endDatetime = header.getSaleEndDatetime();
    boolean isReserve = isReserveByCommodity(shopCode, commodityCode);
    return !isReserve && DateUtil.isPeriodDate(startDatetime, endDatetime, DateUtil.getSysdate());
  }

  public Map<Sku, CommodityAvailability> getAvailablilityMap(String shopCode, String commodityCode, int quantity, boolean isReserve) {

    Map<Sku, CommodityAvailability> availabilityMap = Collections.emptyMap();
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    List<CommodityDetail> details = dDao.findByQuery(CatalogQuery.GET_COMMODITY_DETAILS_BY_COMMODITY_CODE, shopCode, commodityCode);

    if (details.size() != 0) {
      availabilityMap = new HashMap<Sku, CommodityAvailability>();
      boolean shopIsClosed = !isShopOpen(shopCode);
      boolean isNotListed = !isListed(shopCode, commodityCode);
      boolean isNotOnSale = !isSaleByCommodity(shopCode, commodityCode);
      boolean isNotAcceptingReservation = !isReserveByCommodity(shopCode, commodityCode);
      for (CommodityDetail detail : details) {

        if (shopIsClosed) {
          // ショップが開いていない場合はすべてNOT_EXIST_SKU
          availabilityMap.put(new Sku(detail.getShopCode(), detail.getSkuCode()), CommodityAvailability.NOT_EXIST_SKU);
        } else if (isNotListed) {
          // 商品が公開されていない場合はすべてNOT_EXIST_SKU
          availabilityMap.put(new Sku(detail.getShopCode(), detail.getSkuCode()), CommodityAvailability.NOT_EXIST_SKU);
        } else if (isNotAcceptingReservation && isNotOnSale) {
          // 予約期間外、販売期間外のときはOUT_OF_PERIOD
          availabilityMap.put(new Sku(detail.getShopCode(), detail.getSkuCode()), CommodityAvailability.OUT_OF_PERIOD);
        } else {
          availabilityMap.put(new Sku(detail.getShopCode(), detail.getSkuCode()), isAvailable0(detail, quantity, isReserve));
        }
      }
    }
    return availabilityMap;
  }

  private CommodityAvailability isAvailable0(CommodityDetail detail, int quantity, boolean isReserve) {
    if (detail == null) {
      return CommodityAvailability.NOT_EXIST_SKU;
    }
    String shopCode = detail.getShopCode();
    String skuCode = detail.getSkuCode();

    StockUnit stockUnit = new StockUnit();
    stockUnit.setShopCode(shopCode);
    stockUnit.setSkuCode(skuCode);
    stockUnit.setQuantity(quantity);
    stockUnit.setStockIODate(DateUtil.getSysdate());
    stockUnit.setLoginInfo(getLoginInfo());

    if (isReserve) {
      if (!StockUtil.hasReservingStock(stockUnit)) {
        if (getReservationAvailableStock(shopCode, skuCode) == 0) {
          // 予約不可かつ、有効在庫が0の場合

          return CommodityAvailability.OUT_OF_RESERVATION_STOCK;
        } else {
          // 予約不可かつ、有効在庫が0以外の場合

          return CommodityAvailability.RESERVATION_LIMIT_OVER;
        }
      }
    } else {
      if (!StockUtil.hasStock(stockUnit)) {
        if (getAvailableStock(shopCode, skuCode) == 0) {
          // 引当不可かつ、有効在庫が0の場合

          return CommodityAvailability.OUT_OF_STOCK;
        } else {
          // 引当不可かつ、有効在庫が0以外の場合

          return CommodityAvailability.STOCK_SHORTAGE;
        }
      }
    }
    return CommodityAvailability.AVAILABLE;
  }

  public CommodityAvailability isAvailable(String shopCode, String skuCode, int quantity, boolean isReserve) {
    return isAvailable(shopCode, skuCode, quantity, isReserve, false);
  }

  public CommodityAvailability isAvailable(String shopCode, String skuCode, int quantity, boolean isReserve, boolean isSet) {
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    // 対象ショップが開店しているかのチェック
    ShopDao shopDao = DIContainer.getDao(ShopDao.class);
    Shop shop = shopDao.load(shopCode);
    // 10.1.6 10259 追加 ここから
    if (shop == null) {
      return CommodityAvailability.NOT_EXIST_SKU;
    }
    // 10.1.6 10259 追加 ここまで
    Date endDate = shop.getCloseDatetime();
    endDate = DateUtil.setHour(endDate, 23);
    endDate = DateUtil.setMinute(endDate, 59);
    endDate = DateUtil.setSecond(endDate, 59);

    if (!DateUtil.isPeriodDate(shop.getOpenDatetime(), endDate)) {
      return CommodityAvailability.NOT_EXIST_SKU;
    }

    // 対象商品が存在しない場合は商品未存在エラー
    CommodityDetail detail = dDao.load(shopCode, skuCode);

    if (detail == null) {
      return CommodityAvailability.NOT_EXIST_SKU;
    }
    CommodityHeader header = getCommodityHeader(shopCode, skuCode);

    // 如果是组合品 以下均判断原商品的库存 origiSkuCode
    String origiSkuCode = "";
    if (StringUtil.hasValue(header.getOriginalCommodityCode())) {
      origiSkuCode = header.getOriginalCommodityCode();
    } else {
      origiSkuCode = skuCode;
    }
    // 対象商品が非公開の場合は、販売期間外エラー
    if (!isListed(detail.getShopCode(), detail.getCommodityCode())) {
      return CommodityAvailability.NOT_EXIST_SKU;
    }

    if (isReserve && !isReserve(shopCode, origiSkuCode)) {
      // 予約商品であり、予約期間外であった場合は販売期間外エラー

      return CommodityAvailability.OUT_OF_PERIOD;

    }

    if (!isReserve && !isSale(shopCode, origiSkuCode)) {
      // 販売商品であり、販売期間外であった場合は販売期間外エラー
      return CommodityAvailability.OUT_OF_PERIOD;
    }

    StockUnit stockUnit = new StockUnit();
    stockUnit.setShopCode(shopCode);
    if (StringUtil.hasValue(header.getOriginalCommodityCode())) {
      stockUnit.setSkuCode(header.getOriginalCommodityCode());
    } else {
      stockUnit.setSkuCode(origiSkuCode);
    }

    if (StringUtil.hasValue(header.getOriginalCommodityCode()) && header.getCombinationAmount() != null) {
      stockUnit.setQuantity(quantity * Integer.parseInt(header.getCombinationAmount().toString()));
    } else {
      stockUnit.setQuantity(quantity);
    }
    stockUnit.setStockIODate(DateUtil.getSysdate());
    stockUnit.setLoginInfo(getLoginInfo());

    if (!isSet) {
      if (isReserve) {
        if (!StockUtil.hasReservingStock(stockUnit)) {
          if (getReservationAvailableStock(shopCode, origiSkuCode) == 0) {
            // 予約不可かつ、有効在庫が0の場合
            return CommodityAvailability.OUT_OF_RESERVATION_STOCK;
          } else {
            // 予約不可かつ、有効在庫が0以外の場合
            return CommodityAvailability.RESERVATION_LIMIT_OVER;
          }
        }
      } else {
        if (!StockUtil.hasStock(stockUnit)) {
          if (getAvailableStock(shopCode, origiSkuCode) == 0) {
            // 引当不可かつ、有効在庫が0の場合
            return CommodityAvailability.OUT_OF_STOCK;
          } else {
            // 引当不可かつ、有効在庫が0以外の場合
            return CommodityAvailability.STOCK_SHORTAGE;
          }
        }
      }
    }

    return CommodityAvailability.AVAILABLE;
  }

  // 2012/11/24 促销对应 ob update end

  // 2012/11/23 促销对应 ob add start
  public CommodityAvailability isAvailableGift(String shopCode, String codeValue, int quantity, boolean isCommodityCode) {
    String skuCode = codeValue;
    CommodityHeader ch = new CommodityHeader();
    if (isCommodityCode) {
      ch = getCommodityHeader(shopCode, codeValue);
      skuCode = ch.getRepresentSkuCode();
    }
    StockUnit stockUnit = new StockUnit();
    stockUnit.setShopCode(shopCode);
    stockUnit.setSkuCode(skuCode);
    stockUnit.setQuantity(quantity);
    stockUnit.setStockIODate(DateUtil.getSysdate());
    stockUnit.setLoginInfo(getLoginInfo());

    if (!StockUtil.hasStock(stockUnit)) {
      Long availableStock = getAvailableStock(shopCode, skuCode);
      if (availableStock == null || getAvailableStock(shopCode, skuCode) == 0L) {
        // 引当不可かつ、有効在庫が0の場合
        return CommodityAvailability.OUT_OF_STOCK;
      } else {
        // 引当不可かつ、有効在庫が0以外の場合
        return CommodityAvailability.STOCK_SHORTAGE;
      }
    }

    return CommodityAvailability.AVAILABLE;
  }

  public CommodityAvailability isAvailableGift(String shopCode, String skuCode, int quantity) {
    return isAvailableGift(shopCode, skuCode, quantity, false);
  }

  // 2012/11/23 促销对应 ob add end

  public boolean isListed(String shopCode, String commodityCode) {
    boolean isListed = true;

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader header = hDao.load(shopCode, commodityCode);

    // 対象ショップが開店しているかのチェック

    ShopDao shopDao = DIContainer.getDao(ShopDao.class);
    Shop shop = shopDao.load(shopCode);
    if (shop == null) {
      return false;
    }
    Date endDate = shop.getCloseDatetime();
    endDate = DateUtil.setHour(endDate, 23);
    endDate = DateUtil.setMinute(endDate, 59);
    endDate = DateUtil.setSecond(endDate, 59);

    if (!DateUtil.isPeriodDate(shop.getOpenDatetime(), endDate)) {
      return false;
    }

    // 商品の存在チェック

    if (header == null) {
      return false;
    } else {
      // 販売フラグ、販売期間、予約期間のチェック
      isListed &= SaleFlg.fromValue(header.getSaleFlg()) == SaleFlg.FOR_SALE;
      if (DateUtil.isPeriodDate(header.getSaleStartDatetime(), header.getSaleEndDatetime(), DateUtil.getSysdate())) {
        // 販売期間内ならtrue
        isListed &= true;
      } else if (header.getReservationEndDatetime() != null) {
        // 予約期間が設定されている(予約期間終了 != null)場合は予約期間内か判定

        isListed &= DateUtil.isPeriodDate(header.getReservationStartDatetime(), header.getReservationEndDatetime(), DateUtil
            .getSysdate());
      } else {
        isListed &= false;
      }
    }

    // カテゴリ関連付けの存在チェック
    List<CategoryCommodity> categoryCommodityList = getCategoryCommodityList(shopCode, commodityCode);
    if (categoryCommodityList.isEmpty()) {
      return false;
    }

    // 配送種別の存在チェック
    DeliveryTypeDao dDao = DIContainer.getDao(DeliveryTypeDao.class);
    DeliveryType deliveryType = dDao.load(header.getShopCode(), header.getDeliveryTypeNo());
    if (deliveryType == null) {
      return false;
    } else {
      // 配送種別の表示フラグチェック

      isListed &= DisplayFlg.fromValue(deliveryType.getDisplayFlg()) == DisplayFlg.VISIBLE;
    }

    return isListed;
  }

  public Long getAvailableStock(String shopCode, String skuCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_AVAILABLE_SKU_QUANTITY, shopCode, skuCode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  public Long getUseSuitStock(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_ALL_USE_QUANTITY, commodityCode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  public Long getUseSuitStockButThis(String commodityCode, String pareCommodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_ALL_USE_QUANTITY_BUT_SELF, commodityCode, pareCommodityCode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  public Long getAvailableStockByCommodityCode(String shopCode, String commodityCode) {
    CommodityHeader ch = getCommodityHeader(shopCode, commodityCode);
    Query query = new SimpleQuery(CatalogQuery.GET_AVAILABLE_SKU_QUANTITY, shopCode, ch.getRepresentSkuCode());
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  public Long getAvailableStock(String shopCode, String skuCode, boolean isSet, List<String> compositionSkuCodeList,
      List<String> giftSkuCodeList) {
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
    Long availableStock = numberPolicy.getMaxTotalAmountNum();

    if (giftSkuCodeList != null && giftSkuCodeList.size() > 0) {
      for (String giftSkuCode : giftSkuCodeList) {
        Long stockTemp = getAvailableStock(shopCode, giftSkuCode);
        if (stockTemp == null) {
          return 0L;
        }

        if (stockTemp == -1L) {
          continue;
        } else {
          availableStock = Math.min(availableStock, stockTemp);
        }
      }
    }

    if (isSet) {
      if (compositionSkuCodeList != null && compositionSkuCodeList.size() > 0) {
        if (compositionSkuCodeList.size() < numberPolicy.getMinSetNum()) {
          availableStock = 0L;
        } else {
          for (String compositionSkuCode : compositionSkuCodeList) {
            Long stockTemp = getAvailableStock(shopCode, compositionSkuCode);
            if (stockTemp == null) {
              return 0L;
            }

            if (stockTemp == -1L) {
              continue;
            } else {
              availableStock = Math.min(availableStock, stockTemp);
            }
          }
        }
      } else {
        availableStock = 0L;
      }

    } else {

      Long stockTemp = getAvailableStock("00000000", skuCode);
      if (stockTemp == null) {
        return 0L;
      }

      if (stockTemp != -1L) {
        availableStock = Math.min(availableStock, stockTemp);
      }

    }

    return availableStock;
  }

  /**
   * 取得最大库存的套餐明细商品的sku编号
   * 
   * @param shopCode
   * @param commodityCode
   * @return
   */
  public String getCompositionDetailSkuOfMaxAvailableStock(String shopCode, String commodityCode) {
    List<String> skuCodeList = new ArrayList<String>();
    Long availableStock = 0L;
    String skuCodeReturn = "";
    List<CommodityDetail> commodityDetailList = getCommoditySku(shopCode, commodityCode);

    if (commodityDetailList != null && commodityDetailList.size() > 0) {
      for (CommodityDetail detail : commodityDetailList) {
        skuCodeList.add(detail.getSkuCode());
      }
    }
    if (skuCodeList != null && skuCodeList.size() > 0) {
      for (String skuCode : skuCodeList) {
        Long stockTemp = getAvailableStock(shopCode, skuCode);
        if (stockTemp == null) {
          return skuCode;
        }

        if (stockTemp == -1L) {
          return skuCode;
        } else {
          if (availableStock <= stockTemp) {
            availableStock = stockTemp;
            skuCodeReturn = skuCode;
          }
        }
      }
    }

    return skuCodeReturn;
  }

  // 2012/11/23 促销对应 ob add end

  public Long getReservationAvailableStock(String shopCode, String skuCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_RESERVATION_AVAILABLE_STOCK, shopCode, skuCode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  // 10.1.7 10327 追加 ここから
  public boolean canModifyStandardCount(String shopCode, String commodityCode) {
    if (DIContainer.getDao(CommodityHeaderDao.class).exists(shopCode, commodityCode)) {
      Query query = new SimpleQuery(CatalogQuery.COUNT_NOT_MODIFIABLE_SKU_QUERY, shopCode, commodityCode);
      Long bd = Long.valueOf(DatabaseUtil.executeScalar(query).toString());
      // 20111208 lirong update start
      if (bd == 0L) {
        return true;
      } else {
        return false;
      }
      // 20111208 lirong update end
    }
    return false;
  }

  // 10.1.7 10327 追加 ここまで

  public ServiceResult updateCommodityStandardName(CommodityHeader header) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader orgHeader = hDao.load(header.getShopCode(), header.getCommodityCode());
    // 商品の存在チェック

    if (orgHeader == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }
    // 規格名称2のみ設定されている場合

    if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name()) && StringUtil.hasValue(header.getCommodityStandard2Name())) {
      serviceResult.addServiceError(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR);
      return serviceResult;
    }

    List<CommodityDetail> details = getCommoditySku(header.getShopCode(), header.getCommodityCode());
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);

    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      if (StringUtil.isNullOrEmpty(header.getCommodityStandard1Name())
          && StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())
          && StringUtil.hasValueAllOf(orgHeader.getCommodityStandard1Name(), orgHeader.getCommodityStandard1Name())) {
        for (CommodityDetail detail : details) {
          if (!orgHeader.getRepresentSkuCode().equals(detail.getSkuCode())) {
            if (isDeletableCommoditySku(detail.getShopCode(), detail.getSkuCode())) {
              for (String deleteQuery : CommodityDeleteQuery.getSkuDeleteQuery()) {
                txMgr.executeUpdate(deleteQuery, detail.getShopCode(), detail.getSkuCode());
              }
            } else {
              txMgr.rollback();
              serviceResult.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
              return serviceResult;
            }
          }
        }
        CommodityDetail commodityDetail = dDao.load(header.getShopCode(), header.getRepresentSkuCode());
        if (StringUtil.hasValue(commodityDetail.getStandardDetail1Name())
            || StringUtil.hasValue(commodityDetail.getStandardDetail2Name())) {
          commodityDetail.setStandardDetail1Name("");
          commodityDetail.setStandardDetail2Name("");
          setUserStatus(commodityDetail);
          txMgr.update(commodityDetail);
        }
      } else if (StringUtil.hasValue(header.getCommodityStandard1Name())
          && StringUtil.isNullOrEmpty(header.getCommodityStandard2Name())
          && StringUtil.hasValueAllOf(orgHeader.getCommodityStandard1Name(), orgHeader.getCommodityStandard1Name())) {
        for (CommodityDetail detail : details) {
          CommodityDetail commodityDetail = dDao.load(detail.getShopCode(), detail.getSkuCode());
          if (StringUtil.hasValue(commodityDetail.getStandardDetail2Name())) {
            commodityDetail.setStandardDetail2Name("");
            setUserStatus(commodityDetail);
            txMgr.update(commodityDetail);
          }
        }
      }
      setUserStatus(header);
      txMgr.update(header);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  // 10.1.4 K00171 追加 ここから
  public Map<Sku, Boolean> getCommodityReturnPolicies(Set<Sku> skuSet, boolean isMobile) {
    Map<Sku, Boolean> result = Collections.emptyMap();
    if (skuSet != null && skuSet.size() > 0) {
      result = new HashMap<Sku, Boolean>();
      for (Sku s : skuSet) {
        result.put(s, Boolean.valueOf(false));
      }
      Query query = new ReturnPolicySetQuery(skuSet, isMobile);
      List<Sku> resultSet = DatabaseUtil.loadAsBeanList(query, Sku.class);
      for (Sku s : resultSet) {
        result.put(s, Boolean.valueOf(true));
      }
    }
    return result;
  }

  // 10.1.4 K00171 追加 ここまで
  /**
   * add by os012 20111219 start 用于品牌管理查询
   */
  public SearchResult<Brand> getBrandSearch(BrandSearchCondition condition) {
    BrandSearchQuery query = new BrandSearchQuery();
    return DatabaseUtil.executeSearch(query.createBrandSearchQuery(condition));
  }

  // add by os012 20111219 end

  /**
   * add by os012 20111219 start 用于品牌登录
   */
  public ServiceResult insertBrand(Brand brand) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    setUserStatus(brand);

    // Validationチェック処理を実行する

    ValidationSummary summary = BeanValidator.validate(brand);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return serviceResult;
    }

    BrandDao dao = DIContainer.getDao(BrandDao.class);

    // 重複チェックを実行する

    if (dao.exists(brand.getShopCode(), brand.getBrandCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    dao.insert(brand, getLoginInfo());

    return serviceResult;
  }

  // add by os012 20111219 end
  /**
   * * add by os012 20111219 start 用于品牌管理更新查询
   */
  public Brand getBrand(String shopCode, String brandCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(brandCode)) {
      return null;
    }

    BrandDao dao = DIContainer.getDao(BrandDao.class);
    Brand brand = new Brand();
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(brandCode)) {
      brand = dao.load(shopCode, brandCode);
    }
    return brand;
  }

  /**
   * add by os012 20111219 start 用于品牌管理更新
   */
  public ServiceResult updateBrand(Brand brand) {

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    // Validationチェックを実行する

    ValidationSummary summary = BeanValidator.validate(brand);
    if (summary.hasError()) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return serviceResult;
    }

    BrandDao dao = DIContainer.getDao(BrandDao.class);

    // 存在チェックを実行する

    if (!dao.exists(brand.getShopCode(), brand.getBrandCode())) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    dao.update(brand, getLoginInfo());

    return serviceResult;
  }

  /**
   * * add by os012 20111219 start 用于品牌管理删除
   */
  public ServiceResult deleteBrand(String shopCode, String brandCode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // 存在チェックを実行する
    BrandDao dao = DIContainer.getDao(BrandDao.class);
    if (dao.exists(shopCode, brandCode)) {
      dao.delete(shopCode, brandCode);
    } else {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
    }

    return serviceResult;
  }

  /***
   * add by os012 20111220 start 用于分类管理登录
   */
  public ServiceResult insertCategoryinfo(CategoryInfo categoryInfo) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    Category insertCategory = new Category();
    insertCategory.setCategoryCode(categoryInfo.getCategoryCode());
    insertCategory.setCategoryNamePc(categoryInfo.getCategoryNamePc());
    // 20120514 tuxinwei add start
    insertCategory.setCategoryNamePcEn(categoryInfo.getCategoryNamePcEn());
    insertCategory.setCategoryNamePcJp(categoryInfo.getCategoryNamePcJp());
    // 20120514 tuxinwei add end
    insertCategory.setCategoryNameMobile(categoryInfo.getCategoryNameMobile());
    insertCategory.setCommodityCountPc(categoryInfo.getCommodityCountPc());
    insertCategory.setCommodityCountMobile(categoryInfo.getCommodityCountMobile());
    insertCategory.setDisplayOrder(categoryInfo.getDisplayOrder());
    insertCategory.setDepth(0L);
    insertCategory.setParentCategoryCode(categoryInfo.getParentCategoryCode());
    insertCategory.setPath("/");
    insertCategory.setCreatedUser(categoryInfo.getCreatedUser());
    insertCategory.setCreatedDatetime(categoryInfo.getCreatedDatetime());
    insertCategory.setUpdatedUser(categoryInfo.getUpdatedUser());
    insertCategory.setUpdatedDatetime(categoryInfo.getUpdatedDatetime());
    insertCategory.setKeywordCn2(categoryInfo.getKeywordCn2());
    insertCategory.setKeywordEn2(categoryInfo.getKeywordEn2());
    insertCategory.setKeywordJp2(categoryInfo.getKeywordJp2());
    // add by os012 20111220 start 英文名
    insertCategory.setCategoryIdTmall(categoryInfo.getCategoryIdTmall());
    // add by os012 20111220 end 英文名
    // 2014/4/28 京东WBS对应 ob_李 add start
    insertCategory.setCategoryIdJd(categoryInfo.getCategoryIdJd());
    // 2014/4/28 京东WBS对应 ob_李 add end
    insertCategory.setMetaKeyword(categoryInfo.getMetaKeyword());
    insertCategory.setMetaDescription(categoryInfo.getMetaDescription());
    // 20130703 txw add start
    insertCategory.setTitle(categoryInfo.getTitle());
    insertCategory.setTitleEn(categoryInfo.getTitleEn());
    insertCategory.setTitleJp(categoryInfo.getTitleJp());
    insertCategory.setDescription(categoryInfo.getDescription());
    insertCategory.setDescriptionEn(categoryInfo.getDescriptionEn());
    insertCategory.setDescriptionJp(categoryInfo.getDescriptionJp());
    insertCategory.setKeyword(categoryInfo.getKeyword());
    insertCategory.setKeywordEn(categoryInfo.getKeywordEn());
    insertCategory.setKeywordJp(categoryInfo.getKeywordJp());
    // 20130703 txw add end
    ValidationSummary summary = BeanValidator.validate(insertCategory);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    List<CategoryAttribute> categoryAttributeList = new ArrayList<CategoryAttribute>();

    for (CategoryInfoDetail ci : categoryInfo.getCategoryInfoDetailList()) {
      CategoryAttribute categoryAttribute = new CategoryAttribute();
      categoryAttribute.setCategoryAttributeNo(ci.getCategoryAttributeNo());
      categoryAttribute.setCategoryCode(insertCategory.getCategoryCode());
      categoryAttribute.setCategoryAttributeName(ci.getCategoryAttributeName());
      categoryAttribute.setCreatedUser(categoryInfo.getCreatedUser());
      categoryAttribute.setCreatedDatetime(categoryInfo.getCreatedDatetime());
      categoryAttribute.setUpdatedUser(categoryInfo.getUpdatedUser());
      categoryAttribute.setUpdatedDatetime(categoryInfo.getUpdatedDatetime());
      categoryAttributeList.add(categoryAttribute);

      summary = BeanValidator.validate(categoryAttribute);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }

    }

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    // カテゴリに対して重複チェックを実行する

    if (categoryDao.exists(insertCategory.getCategoryCode())) {
      logger.error(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return serviceResult;
    }

    WebshopConfig config = DIContainer.getWebshopConfig();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());

      // カテゴリへの登録
      txMgr.insert(insertCategory);

      // カテゴリ属性の更新
      // 登録対象のカテゴリ属性番号が存在すれば更新

      // 登録対象のカテゴリ属性番号が存在しなければ登録

      CategoryAttributeDao attribueDao = DIContainer.getDao(CategoryAttributeDao.class);
      for (CategoryAttribute ca : categoryAttributeList) {
        if (attribueDao.exists(ca.getCategoryCode(), ca.getCategoryAttributeNo())) {
          txMgr.executeUpdate(CatalogQuery.UPDATE_CATEGORY_ATTRIBUTE, ca.getCategoryAttributeName(), this.getLoginInfo()
              .getRecordingFormat(), insertCategory.getUpdatedDatetime(), ca.getCategoryCode(), ca.getCategoryAttributeNo());
        } else {
          txMgr.insert(ca);
        }
      }

      // カテゴリパス、階層の計算、更新

      UpdateCategoryPathProcedure updatePathProc = new UpdateCategoryPathProcedure(insertCategory.getCategoryCode(), config
          .getCategoryMaxDepth(), getLoginInfo().getRecordingFormat());
      txMgr.executeProcedure(updatePathProc);

      if (updatePathProc.getResult() == UpdateCategoryPathProcedure.SUCCESS) {
        txMgr.commit();
      } else {
        txMgr.rollback();
        if (updatePathProc.getResult() == UpdateCategoryPathProcedure.MAX_DEPTH_OVER_ERROR) {
          serviceResult.addServiceError(CatalogServiceErrorContent.CATEGORY_MAX_DEPTH_OVER_ERROR);
        } else {
          serviceResult.addServiceError(CatalogServiceErrorContent.UPDATE_CATEGORY_PATH_ERROR);
        }
      }

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  // 20111222 ob add start
  public List<Brand> getAllBrand() {
    BrandDao dao = DIContainer.getDao(BrandDao.class);
    return dao.loadAll();
  }

  // 20111222 ob add start

  // 20111215 lirong add start
  // 获得顧客グループ
  @Override
  public CustomerGroupCount getCustomerGroup(String customerGroupCode) {
    Query query = new SimpleQuery(CustomerGroupQuery.LOAD_QUERY, customerGroupCode);
    return DatabaseUtil.loadAsBean(query, CustomerGroupCount.class);
  }

  // 20111215 lirong add end

  /**
   * add by os014 查询最后同期化时间 2011.12.26
   */
  @Override
  public String getCychroTimeByType(String type) {
    String queryType = "";
    String tableName = DatabaseUtil.getTableName(CCommodityHeader.class);
    // 根据不同的参数组装查询sql
    if ("0".equals(type)) {
      queryType = "sync_time_ec";
    } else if ("1".equals(type)) {
      queryType = "sync_time_tmall";
    }
    // 2014/05/02 京东WBS对应 ob_姚 add start
    else if ("3".equals(type)) {
      queryType = "sync_time_jd";
    }
    // 2014/05/02 京东WBS对应 ob_姚 add end
    else {
      tableName = DatabaseUtil.getTableName(CSynchistory.class);
      queryType = "sync_endtime";
    }
    DateFormat pattern = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    String sql = "select max(" + queryType + ") from " + tableName;
    Query query = new SimpleQuery(sql);
    Date obj = DatabaseUtil.executeScalar(query, Date.class);
    return obj != null ? pattern.format(obj) : "";
  }

  /**
   * add by os014 2011.12.26 查询EC 同期化信息
   */

  @Override
  public List<CCommodityHeader> getCynchEcInfo() {
    Query query = new SimpleQuery(CatalogQuery.CYNCHRO_EC_INFO_QUERY);
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    List<CCommodityHeader> resultList = dao.findByQuery(query);
    return resultList;
  }

  public List<CCommodityHeader> getCynchEcInfoByCheckBox(String[] commodityCodes) {
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    List<CCommodityHeader> resultList = new ArrayList<CCommodityHeader>();
    for (String customerCode : commodityCodes) {
      if (dao.exists("00000000", customerCode)) {
        SimpleQuery query = new SimpleQuery(CatalogQuery.CYNCHRO_EC_INFOBYCHECKBOX_QUERY);
        query.setParameters(customerCode);
        CCommodityHeader commodity = dao.load("00000000", customerCode);
        resultList.add(commodity);
      }
    }

    return resultList;
  }

  public List<CCommodityCynchro> getCynchEInfo() {
    Query query = new SimpleQuery(CatalogQuery.CC_CYNCHRO_EC_INFO_QUERY);
    return DatabaseUtil.loadAsBeanList(query, CCommodityCynchro.class);
  }

  /**
   * add by os014 2011.12.26 查询同期化履历信息
   */
  @Override
  public List<CSynchistory> getCynchHistoryInfo() {
    CSynchistoryDao dao = DIContainer.getDao(CSynchistoryDao.class);
    List<CSynchistory> resultList = dao.loadAll();
    return resultList;
  }

  /**
   * add by os014 2011.12.26 查询TMALL 同期化信息
   */
  @Override
  public List<CCommodityHeader> getCynchTmallInfo() {
    Query query = new SimpleQuery(CatalogQuery.CYNCHRO_TMALL_INFO_QUERY);
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    List<CCommodityHeader> resultList = dao.findByQuery(query);
    return resultList;
  }

  // 2014/05/02 京东WBS对应 ob_姚 add start
  /**
   * 查询京东同期化信息
   */
  @Override
  public List<CCommodityHeader> getCynchJdInfo() {
    Query query = new SimpleQuery(CatalogQuery.CYNCHRO_JD_INFO_QUERY);
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    List<CCommodityHeader> resultList = dao.findByQuery(query);
    return resultList;
  }

  // 2014/05/02 京东WBS对应 ob_姚 add end

  public List<CCommodityHeader> getCynchTmallInfoByCheckBox(String[] commodityCodes) {
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    List<CCommodityHeader> resultList = new ArrayList<CCommodityHeader>();
    for (String customerCode : commodityCodes) {
      if (dao.exists("00000000", customerCode)) {
        CCommodityHeader commodity = dao.load("00000000", customerCode);
        resultList.add(commodity);
      }
    }

    return resultList;

  }

  /***
   * add by cs_yuli 20120314 start 获取淘宝分类编号列表
   * 
   * @param getTmallCategoryCodeList
   * @return
   */
  public List<Category> getTmallCategoryCodeList(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.CATEGORY_QUERY, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, Category.class);
  }

  /**
   * add by os012 2012.02.13 查询TMALL 同期化信息
   */
  public List<CCommodityCynchro> getCynchTInfo() {
    Query query = new SimpleQuery(CatalogQuery.CC_CYNCHRO_TMALL_INFO_QUERY);
    return DatabaseUtil.loadAsBeanList(query, CCommodityCynchro.class);
  }

  /**
   * add by os014 2011.12.26 执行EC系统同期化
   */
  public CyncroResult executeCynchEc() {
    CyncroResult returnResult = ecCynchro();
    saveToHistory(returnResult);
    // 全部失败
    return returnResult;
  }

  public CyncroResult executeCynchEcByCheckBox(String[] commodityCodes) {
    CyncroResult returnResult = ecCynchroByCheckBox(commodityCodes);
    saveToHistory(returnResult);
    // 全部失败
    return returnResult;
  }

  /**
   * add by os014 2011.12.28 组装CommodityDetail 对象
   */
  public CommodityDetail buildCommodityDetail(CCommodityHeader header, CCommodityDetail value) {
    if (value == null) {
      return null;
    }
    CommodityDetail detail = new CommodityDetail();
    detail.setShopCode(value.getShopCode());
    detail.setSkuCode(value.getSkuCode());
    detail.setCommodityCode(value.getCommodityCode());
    if (!(header.getSetCommodityFlg() != null && header.getSetCommodityFlg() == 1L)) {
      detail.setUnitPrice(value.getUnitPrice());
      detail.setDiscountPrice(value.getDiscountPrice());
    } else {
      detail.setUnitPrice(BigDecimal.ZERO);
      detail.setDiscountPrice(null);
    }
    detail.setStandardDetail1Name(value.getStandardDetail1Name());
    detail.setStandardDetail1NameEn(value.getStandardDetail1NameEn());
    detail.setStandardDetail1NameJp(value.getStandardDetail1NameJp());
    detail.setStandardDetail2Name(value.getStandardDetail2Name());
    detail.setStandardDetail2NameEn(value.getStandardDetail2NameEn());
    detail.setStandardDetail2NameJp(value.getStandardDetail2NameJp());
    detail.setWeight(value.getWeight());
    detail.setUseFlg(value.getUseFlg());
    detail.setInnerQuantity(value.getInnerQuantity());
    setUserStatus(detail);
    return detail;
  }

  /**
   * add by os014 2011.12.26 保存同期化履历表
   */
  public void saveToHistory(CyncroResult result) {
    // 组装同期化履历对象
    CSynchistory history = new CSynchistory();
    if (result.getTotalCount() == 0) {
      return;
    }
    history.setFailureCount(Long.valueOf(result.getFailCount().toString()));
    // 生成主键
    history.setSyncCode(String.valueOf(DatabaseUtil.generateSequence(SequenceType.COMMODITY_CYNCHRO_HISTORY_SEQ)));
    history.setSuccessCount(Long.valueOf(result.getSeccessCount().toString()));
    history.setSyncStarttime(new Date(Long.valueOf(result.getStartTime().toString())));
    history.setSyncEndtime(new Date(Long.valueOf(result.getEndTime().toString())));
    history.setTotalCount(Long.valueOf(result.getTotalCount().toString()));

    // 执行插入
    CSynchistoryDao historyDao = DIContainer.getDao(CSynchistoryDao.class);
    historyDao.insert(history);
  }

  /**
   * add by os014 2011.12.26 根据同期化商品信息组装EC商品信息对象
   */
  private CommodityHeader buildCommodityHeader(CCommodityHeader header) {
    String CategoryAttributeStr = "";
    // add by cs_yuli 20120615 start
    String CategoryAttributeEnStr = "";
    String CategoryAttributeJpStr = "";
    // add by cs_yuli 20120615 end
    Logger logger = Logger.getLogger(this.getClass());
    try {
      CategoryAttributeValueDao cavdao = DIContainer.getDao(CategoryAttributeValueDao.class);
      CategoryAttributeDao caDao = DIContainer.getDao(CategoryAttributeDao.class);
      List<CategoryAttributeValue> cavList = cavdao.findByQuery("SELECT * FROM CATEGORY_ATTRIBUTE_VALUE WHERE COMMODITY_CODE =? ",
          header.getCommodityCode());
      if (cavList != null) {
        // add by cs_yuli 20120615 start
        List<String> category = new ArrayList<String>();
        for (int i = 0; i < cavList.size(); i++) {
          CategoryAttributeValue cav = cavList.get(i);
          String categoryCode = cav.getCategoryCode();
          if (!category.contains(categoryCode)) {
            category.add(categoryCode);
          }
        }
        // 设置不超过5个分类
        int categoryNum;
        if (category.size() > 5) {
          categoryNum = 5;
        } else {
          categoryNum = category.size();
        }
        for (int j = 0; j < categoryNum; j++) {
          // add by cs_yuli 20120615 end
          for (int i = 0; i < cavList.size(); i++) {
            CategoryAttributeValue cav = new CategoryAttributeValue();
            cav = (CategoryAttributeValue) cavList.get(i);
            CategoryAttribute ca = caDao.load(cav.getCategoryCode(), (long) i);

            if (cavList.size() - i == 1) {
              CategoryAttributeStr += ca.getCategoryAttributeName() + "|" + cav.getCategoryAttributeValue();
              // add by cs_yuli 20120615 start
              CategoryAttributeEnStr += ca.getCategoryAttributeNameEn() + "|" + cav.getCategoryAttributeValueEn();
              CategoryAttributeJpStr += ca.getCategoryAttributeNameJp() + "|" + cav.getCategoryAttributeValueJp();
              // add by cs_yuli 20120615 end
            } else {
              CategoryAttributeStr += ca.getCategoryAttributeName() + "|" + cav.getCategoryAttributeValue() + "#";
              // add by cs_yuli 20120615 start
              CategoryAttributeEnStr += ca.getCategoryAttributeNameEn() + "|" + cav.getCategoryAttributeValueEn() + "#";
              CategoryAttributeJpStr += ca.getCategoryAttributeNameJp() + "|" + cav.getCategoryAttributeValueJp() + "#";
              // add by cs_yuli 20120615 end
            }
          }
        }
      }
    } catch (Exception e) {
      logger.error(header.getCommodityCode() + "出现异常\r\n" + e.getMessage());

    }
    CommodityHeader cHeader = new CommodityHeader();
    CommodityDescribeDao describeDao = DIContainer.getDao(CommodityDescribeDao.class);
    CommodityDescribe describe = describeDao.load("00000000", header.getCommodityCode());
    String descriptionPc = "";
    String descriptionPcEn = "";
    String descriptionPcJp = "";
    Boolean brFlag = false;
    if (StringUtil.isNotNull(header.getCommodityDescription1())) {
      brFlag = true;
      descriptionPc = "<font color=\"red\">" + header.getCommodityDescription1() + "</font>";
    }
    if (StringUtil.isNotNull(header.getCommodityDescription2())) {
      if (brFlag) {
        descriptionPc += "<br>";
      }
      descriptionPc += header.getCommodityDescription2();
    }
    if (StringUtil.isNotNull(header.getCommodityDescription3())) {
      if (brFlag) {
        descriptionPc += "<br>";
      }
      descriptionPc += header.getCommodityDescription3();
    }

    if (StringUtil.isNotNull(header.getCommodityDescription1En())) {
      brFlag = true;
      descriptionPcEn = "<font color=\"red\">" + header.getCommodityDescription1En() + "</font>";
    }
    if (StringUtil.isNotNull(header.getCommodityDescription2En())) {
      if (brFlag) {
        descriptionPcEn += "<br>";
      }
      descriptionPcEn += header.getCommodityDescription2En();
    }
    if (StringUtil.isNotNull(header.getCommodityDescription3En())) {
      if (brFlag) {
        descriptionPcEn += "<br>";
      }
      descriptionPcEn += header.getCommodityDescription3En();
    }

    if (StringUtil.isNotNull(header.getCommodityDescription1Jp())) {
      brFlag = true;
      descriptionPcJp = "<font color=\"red\">" + header.getCommodityDescription1Jp() + "</font>";
    }
    if (StringUtil.isNotNull(header.getCommodityDescription2Jp())) {
      if (brFlag) {
        descriptionPcJp += "<br>";
      }
      descriptionPcJp += header.getCommodityDescription2Jp();
    }
    if (StringUtil.isNotNull(header.getCommodityDescription3Jp())) {
      if (brFlag) {
        descriptionPcJp += "<br>";
      }
      descriptionPcJp += header.getCommodityDescription3Jp();
    }

    if (describe != null) {
      if (StringUtil.hasValue(describe.getDecribeCn())) {
        cHeader.setCommodityDescriptionPc(describe.getDecribeCn());
      } else {
        cHeader.setCommodityDescriptionPc(descriptionPc);
      }

      if (StringUtil.hasValue(describe.getDecribeEn())) {
        cHeader.setCommodityDescriptionPcEn(describe.getDecribeEn());
      } else {
        cHeader.setCommodityDescriptionPcEn(descriptionPcEn);
      }

      if (StringUtil.hasValue(describe.getDecribeJp())) {
        cHeader.setCommodityDescriptionPcJp(describe.getDecribeJp());
      } else {
        cHeader.setCommodityDescriptionPcJp(descriptionPcJp);
      }

    } else {
      cHeader.setCommodityDescriptionPc(descriptionPc);
      cHeader.setCommodityDescriptionPcEn(descriptionPcEn);
      cHeader.setCommodityDescriptionPcJp(descriptionPcJp);
    }

    cHeader.setShopCode(header.getShopCode());
    cHeader.setCommodityCode(header.getCommodityCode());
    cHeader.setCommodityName(header.getCommodityName());
    cHeader.setCommodityNameEn(header.getCommodityNameEn());
    cHeader.setCommodityNameJp(header.getCommodityNameJp());
    cHeader.setRepresentSkuCode(header.getRepresentSkuCode());
    cHeader.setRepresentSkuUnitPrice(header.getRepresentSkuUnitPrice());
    cHeader.setCommodityTaxType(TaxType.INCLUDED.longValue());
    cHeader.setCommodityDescriptionMobile(header.getCommodityDescriptionShort());
    cHeader.setCommodityDescriptionMobileEn(header.getCommodityDescriptionShortEn());
    cHeader.setCommodityDescriptionMobileJp(header.getCommodityDescriptionShortJp());
    cHeader.setCommoditySearchWords(header.getCommoditySearchWords());
    cHeader.setSaleStartDatetime(header.getSaleStartDatetime());
    cHeader.setSaleEndDatetime(header.getSaleEndDatetime());
    cHeader.setDiscountPriceStartDatetime(header.getDiscountPriceStartDatetime());
    cHeader.setDiscountPriceEndDatetime(header.getDiscountPriceEndDatetime());
    cHeader.setRecommendCommodityRank(Long.valueOf("99999999"));
    cHeader.setCommodityPopularRank(Long.valueOf("99999999"));
    cHeader.setCommodityStandard1Name(header.getStandard1Name());
    cHeader.setCommodityStandard1NameEn(header.getStandard1NameEn());
    cHeader.setCommodityStandard1NameJp(header.getStandard1NameJp());
    cHeader.setCommodityStandard2Name(header.getStandard2Name());
    cHeader.setCommodityStandard2NameEn(header.getStandard2NameEn());
    cHeader.setCommodityStandard2NameJp(header.getStandard2NameJp());
    cHeader.setSaleFlg(header.getSaleFlgEc());
    cHeader.setDisplayClientType(DisplayClientType.PC.longValue());
    cHeader.setArrivalGoodsFlg(Long.valueOf("0"));
    cHeader.setReturnFlg(header.getReturnFlg());
    cHeader.setWarningFlag(header.getWarningFlag());
    cHeader.setSaleFlag(header.getSaleFlag());
    cHeader.setSpecFlag(header.getSpecFlag());
    cHeader.setBrandCode(header.getBrandCode());
    cHeader.setCategoryPath(header.getCategorySearchPath());
    cHeader.setCategoryAttribute1(CategoryAttributeStr);
    // add by cs_yuli 20120615 start
    cHeader.setCategoryAttribute1En(CategoryAttributeEnStr);
    cHeader.setCategoryAttribute1Jp(CategoryAttributeJpStr);
    // add by cs_yuli 20120615 end
    cHeader.setReservationStartDatetime(DateUtil.getMin());
    cHeader.setReservationEndDatetime(DateUtil.getMin());
    cHeader.setCreatedUser(header.getCreatedUser());
    cHeader.setCreatedDatetime(header.getCreatedDatetime());
    cHeader.setDeliveryTypeNo(Long.valueOf("0"));

    cHeader.setOriginalPlace(header.getOriginalPlace());
    cHeader.setOriginalPlaceEn(header.getOriginalPlaceEn());
    cHeader.setOriginalPlaceJp(header.getOriginalPlaceJp());
    cHeader.setShelfLifeDays(header.getShelfLifeDays());
    cHeader.setShelfLifeFlag(header.getShelfLifeFlag());
    cHeader.setCommodityType(header.getCommodityType());
    cHeader.setImportCommodityType(header.getImportCommodityType());
    cHeader.setClearCommodityType(header.getClearCommodityType());
    cHeader.setReserveCommodityType1(header.getReserveCommodityType1());
    cHeader.setReserveCommodityType2(header.getReserveCommodityType2());
    cHeader.setReserveCommodityType3(header.getReserveCommodityType3());
    cHeader.setNewReserveCommodityType1(header.getNewReserveCommodityType1());
    cHeader.setNewReserveCommodityType2(header.getNewReserveCommodityType2());
    cHeader.setNewReserveCommodityType3(header.getNewReserveCommodityType3());
    cHeader.setNewReserveCommodityType4(header.getNewReserveCommodityType4());
    cHeader.setNewReserveCommodityType5(header.getNewReserveCommodityType5());
    cHeader.setOriginalCode(header.getOriginalCode());
    cHeader.setKeywordCn1(header.getKeywordCn1());
    cHeader.setKeywordCn2(header.getKeywordCn2());
    cHeader.setKeywordEn1(header.getKeywordEn1());
    cHeader.setKeywordEn2(header.getKeywordEn2());
    cHeader.setKeywordJp1(header.getKeywordJp1());
    cHeader.setKeywordJp2(header.getKeywordJp2());
    // txw 20130609 add start
    cHeader.setOriginalCommodityCode(header.getOriginalCommodityCode());
    cHeader.setCombinationAmount(header.getCombinationAmount());
    // txw 20130609 add end
    // txw 20130808 add start
    cHeader.setTitle(header.getTitle());
    cHeader.setTitleEn(header.getTitleEn());
    cHeader.setTitleJp(header.getTitleJp());
    cHeader.setDescription(header.getDescription());
    cHeader.setDescriptionEn(header.getDescriptionEn());
    cHeader.setDescriptionJp(header.getDescriptionJp());
    cHeader.setKeyword(header.getKeyword());
    cHeader.setKeywordEn(header.getKeywordEn());
    cHeader.setKeywordJp(header.getKeywordJp());
    // txw 20130808 add end
    cHeader.setHotFlgEn(header.getHotFlgEn());
    cHeader.setHotFlgJp(header.getHotFlgJp());
    cHeader.setSetCommodityFlg(header.getSetCommodityFlg());
    setUserStatus(cHeader);
    return cHeader;
  }

  /**
   * 查询商品header关键属性 QUERY_TMALL_PROPERTYS
   * 格式：100001:201020；100003:201022(包含非关键属性)
   */
  private String queryHeaderProperty(String commodityCode) {
    SimpleQuery propQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_HEADER_PROPERTYS, commodityCode);
    List<String> propertys = DatabaseUtil.executeScalarForTableAnalysis(propQuery);
    StringBuffer sbBuffer = new StringBuffer("");
    if (propertys != null) {
      for (int i = 0; i < propertys.size(); i++) {
        if (i != (propertys.size() - 1)) {
          sbBuffer.append((String) propertys.get(i)).append(";");
        } else {
          sbBuffer.append((String) propertys.get(i));
        }
      }
    }
    return sbBuffer.toString();
  }

  // 2012/03/15 add by os011 start
  /**
   * 查询商品关键属性100001:201020；100003:201022(不包含非关键属性)
   */
  private String queryProperty(String commodityCode) {
    SimpleQuery propQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_PROPERTYS, commodityCode);
    List<String> propertys = DatabaseUtil.executeScalarForTableAnalysis(propQuery);
    StringBuffer sbBuffer = new StringBuffer("");
    if (propertys != null) {
      for (int i = 0; i < propertys.size(); i++) {
        if (i != (propertys.size() - 1)) {
          sbBuffer.append((String) propertys.get(i)).append(";");
        } else {
          sbBuffer.append((String) propertys.get(i));
        }
      }
    }
    return sbBuffer.toString();
  }

  /**
   * 查询商品非关键属性
   */
  private String queryNoKeyProperty(String commodityCode) {
    SimpleQuery propQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_NO_KEY_PROPERTYS, commodityCode);
    List<String> propertys = DatabaseUtil.executeScalarForTableAnalysis(propQuery);
    StringBuffer sbBuffer = new StringBuffer("");
    if (propertys != null) {
      for (int i = 0; i < propertys.size(); i++) {
        if (i != (propertys.size() - 1)) {
          sbBuffer.append((String) propertys.get(i)).append(";");
        } else {
          sbBuffer.append((String) propertys.get(i));
        }
      }
    }
    return sbBuffer.toString();
  }

  /**
   * 查询商品销售属性
   */
  private String querySaleProperty(String commodityCode) {
    SimpleQuery propQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_SALE_PROPERTYS, commodityCode);
    List<String> propertys = DatabaseUtil.executeScalarForTableAnalysis(propQuery);
    StringBuffer sbBuffer = new StringBuffer("");
    if (propertys != null) {
      for (int i = 0; i < propertys.size(); i++) {
        if (i != (propertys.size() - 1)) {
          sbBuffer.append((String) propertys.get(i)).append(";");
        } else {
          sbBuffer.append((String) propertys.get(i));
        }
      }
    }
    return sbBuffer.toString();
  }

  // 2012/03/15 add by os011 end
  /**
   * 根据skuId,sku属性串，淘宝商品ID调用淘宝API获取淘宝商品SKU信息
   * 
   * @param skuCode
   *          skuId
   * @param tmallCommodityCode
   *          淘宝商品ID
   * @param properties
   *          sku属性串
   * @return
   */
  public TmallCommoditySku getTmallSkuInfo(String skuCode, String tmallCommodityCode) {
    TmallService service = ServiceLocator.getTmallService(getLoginInfo());
    TmallCommoditySku reTcs = new TmallCommoditySku();
    // 传递参数
    reTcs.setSkuId(skuCode);
    reTcs.setNumiid(tmallCommodityCode);
    return service.getSku(reTcs);
  }

  /**
   * 判断sku 属性是否己修改
   * 
   * @param sku
   * @return true:属性被修改 false:没有被修改
   */
  public boolean tmallPropertyIsChange(TmallCommoditySku sku) {
    TmallCommoditySku tmallSku = getTmallSkuInfo(sku.getSkuId(), sku.getNumiid());
    if (tmallSku == null) {
      return true;
    }
    return !sku.getProperties().equals(tmallSku.getProperties());
  }

  /**
   * 插入sku信息到淘宝
   * 
   * @param sku
   * @return 淘宝 numiid
   */
  public String insertSkuToTmall(TmallCommoditySku sku) {
    TmallService manager = ServiceLocator.getTmallService(getLoginInfo());
    return manager.insertOrUpdateCommoditySku(sku, "INSERT");
  }

  /**
   * 删除Tmall Sku
   */
  public String deleteTmallSku(TmallCommoditySku sku) {
    TmallService service = ServiceLocator.getTmallService(getLoginInfo());
    TmallCommoditySku reTcs = new TmallCommoditySku();
    // 传递参数
    reTcs.setProperties(sku.getProperties());
    reTcs.setNumiid(sku.getNumiid());
    String retStr = service.deleteSku(reTcs);
    // 返回结果为numiid
    return retStr;
  }

  // 查询属性和属性值
  private void getPropertyString(Map<String, String> property, StringBuilder str, StringBuilder pids, String brandCode,
      String shopCode) {
    if ("0".equals(property.get("PARENT_PID")) || "20000".equals(property.get("PARENT_PID"))) {
      // 如果父属性ID为20000，查询品牌表，品牌名称为父属性值，pids 为20000
      if ("20000".equals(property.get("PARENT_PID"))) {
        // 查询品牌信息
        BrandDao bDao = DIContainer.getDao(BrandDao.class);
        Brand brand = bDao.load(shopCode, brandCode);
        str.append(brand.getBrandName());
        pids.append("20000");
        return;
      } else {
        // 如果父属性ID为0，返回父属性ID，和父属性名称
        str.append(property.get("PROPERTY_NAME"));
        pids.append(property.get("PROPERTY_ID"));
        return;
      }

    } else {// 查询父属性Id,父属性name,属性值ID，属性值name
      SimpleQuery query = new SimpleQuery();
      query.setSqlString(CatalogQuery.QUERY_PROPERTY_PROPERTYVALUE);
      Object params[] = {
          property.get("PARENT_PID"), property.get("CATEGORY_ID"), property.get("PARENT_VID")
      };
      query.setParameters(params);
      List<Map<String, String>> result = DatabaseUtil.loadAsMapList(query);
      Map<String, String> argm = new HashMap<String, String>();
      if (result != null && result.size() > 0) {
        argm = result.get(0);
        str.append(argm.get("value_name") + "&%&");
        str.append(argm.get("property_name") + "&%&");
        Map<String, String> parems = new HashMap<String, String>();
        parems.put("PARENT_PID", argm.get("parent_pid"));
        parems.put("PROPERTY_NAME", argm.get("property_name"));
        parems.put("PROPERTY_ID", argm.get("property_id"));
        getPropertyString(parems, str, pids, brandCode, shopCode);
      }
    }
  }

  private String proStringHandle(StringBuilder sb) {
    String temp = sb.toString();
    String[] tempArr = temp.split("&%&");
    StringBuilder result = new StringBuilder("");
    for (int i = tempArr.length - 1; i >= 0; i--) {
      result.append(tempArr[i]);
      if (i != 0) {
        result.append(";");
      }
    }
    return result.toString();
  }

  // 查询属性自定义属性
  private Map<String, String> queryPropStrAndPids(String commodityCode, String categoryId, String brandCode, String shopCode) {
    Logger logger = Logger.getLogger(this.getClass());
    Map<String, String> result = null;
    try {
      /**
       * 查询出商品是否有自定义的属性值
       */
      TmallCommodityPropertyDao pdDao = DIContainer.getDao(TmallCommodityPropertyDao.class);
      TmallPropertyDao pDao = DIContainer.getDao(TmallPropertyDao.class);
      String inputStr = "";// 自定义的属性值字符串
      String inputPids = "";// 自定义的属性ID字符串
      List<TmallCommodityProperty> props = pdDao.loadInputProByCommodityCode(commodityCode);
      if (props != null && props.size() > 0) {
        result = new HashMap<String, String>();
        for (int i = 0; i < props.size(); i++) {
          StringBuilder sbStr = new StringBuilder("");
          StringBuilder sbPids = new StringBuilder("");
          TmallCommodityProperty tcp = props.get(i);
          ValidationSummary summary = BeanValidator.validate(tcp);
          if (summary.hasError()) {
            for (int j = 0; j < summary.getErrorMessages().size(); j++)
              logger.error(summary.getErrorMessages().get(j));
            continue;
          }
          TmallProperty p = pDao.load(categoryId, tcp.getPropertyId());
          /**
           * 查询属性值字符串 格式： 父属性名;子属性名;自定义的属性值名称;
           */
          Map<String, String> params = new HashMap<String, String>();
          params.put("PROPERTY_ID", tcp.getPropertyId());
          params.put("PARENT_PID", p.getParentPid());
          params.put("CATEGORY_ID", categoryId);
          params.put("PARENT_VID", p.getParentVid());
          String temp = "";
          if ("20000".equals(p.getParentPid())) {
            BrandDao bDao = DIContainer.getDao(BrandDao.class);
            Brand brand = bDao.load(shopCode, brandCode);
            sbStr.append(brand.getBrandName() + ";" + p.getPropertyName() + ";" + tcp.getValueText());
            sbPids.append("20000");

            temp = sbStr.toString();
          } else if ("0".equals(p.getParentPid())) {
            sbStr.append(tcp.getValueText());
            sbPids.append(p.getPropertyId());
            temp = sbStr.toString();
          } else {
            sbStr.append(p.getPropertyName() + ";" + tcp.getValueText());
            sbStr.append("&%&");
            getPropertyString(params, sbStr, sbPids, brandCode, shopCode);
            temp = proStringHandle(sbStr);
          }

          /**
           * 多个属性之间用','逗号分隔 //单个属性处理完成后，组装属性字符串
           */

          inputStr += temp;
          if (brandInInputStr(sbPids.toString())) {
            if (!brandInInputStr(inputPids)) {
              inputPids += sbPids.toString();
            }
          } else {
            inputPids += sbPids.toString();
          }

          if (i != (props.size() - 1)) {
            inputStr += ",";
            inputPids += ",";
          }
        }
        result.put("str", inputStr);
        result.put("pids", inputPids);
      }
    } catch (Exception e) {
    }
    return result;
  }

  private String getCustReturnFlag(String value) {
    if (!StringUtil.hasValue(value)) {
      return "0";
    }
    String temp = Integer.toBinaryString(Integer.parseInt(value));
    return temp.substring(temp.length() - 1);
  }

  /**
   * 作用于Tmall系统同期化 add by os014 2012-01-04
   * 
   * @return 同期结果（成功条数，失败条数，同期开始，结束时间，同期错误信息）
   */

  /**
   * 取得TMALL商品描述
   * 
   * @param header
   * @return
   */
  private String getTmallCommodityDesc(CCommodityHeader header) {
    CommodityDescribeDao describeDao = DIContainer.getDao(CommodityDescribeDao.class);
    CommodityDescribe describe = describeDao.load("00000000", header.getCommodityCode());
    Boolean brFlag = false;
    String desStr = "";
    if (describe != null && StringUtil.hasValue(describe.getDecribeTmall())) {
      desStr = describe.getDecribeTmall();
    } else {

      if (StringUtil.isNotNull(header.getCommodityDescriptionShort())) {
        brFlag = true;
        desStr = header.getCommodityDescriptionShort();
      }
      if (StringUtil.isNotNull(header.getCommodityDescription1())) {
        if (brFlag) {
          desStr += "<br>";
        }
        desStr += "<font color=\"red\">" + header.getCommodityDescription1() + "</font>";
      }
      if (StringUtil.isNotNull(header.getCommodityDescription2())) {
        if (brFlag) {
          desStr += "<br>";
        }
        desStr += header.getCommodityDescription2();
      }
      if (StringUtil.isNotNull(header.getCommodityDescription3())) {
        if (brFlag) {
          desStr += "<br>";
        }
        desStr += header.getCommodityDescription3();
      }
      // if (desStr.length() > 5) { // 宝贝描述。字数要大于5个字符，小于25000个字符
      // return desStr;
      // }
    }

    return desStr;
  }

  /**
   * 查询sku关键属性
   * 
   * @param header
   * @return 00001:00002 字符串
   */
  public String getSkuProperties(CCommodityHeader header, String sku_Code) {
    Query propSkuQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_SKU_PROPERTYS, sku_Code, header.getCommodityCode());
    String skuPropString = "";
    // 商品详细查询
    CCommodityDetail detail = DatabaseUtil.loadAsBean(propSkuQuery, CCommodityDetail.class);
    // 商品详细为空
    if (detail != null) {
      // 规格1
      if (header.getStandard1Id() != null && detail.getStandardDetail1Id() != null) {
        skuPropString = header.getStandard1Id() + ":" + detail.getStandardDetail1Id();
      }
      // 规格2
      if (header.getStandard2Id() != null && detail.getStandardDetail2Id() != null) {
        skuPropString += ";" + header.getStandard2Id() + ":" + detail.getStandardDetail2Id();
      }
    }
    return skuPropString;
  }

  /**
   * 作用于EC系统同期化 add by os014 2012-01-04
   * 
   * @return 同期结果（成功条数，失败条数，同期开始，结束时间，同期错误信息）
   */
  public CyncroResult ecCynchro() {
    Logger logger = Logger.getLogger(this.getClass());
    CyncroResult resultMap = new CyncroResult();
    logger.info("EC同期化开始.............");
    // 执行开始时间
    long startTime = System.currentTimeMillis();
    // 记录成功条数
    long successCount = 0;
    // 记录失败条数
    long failCount = 0;
    // 获取ec同期化信息
    List<CCommodityHeader> ecList = getCynchEcInfo();
    // 同期错误集合
    if (ecList.size() <= 0) {
      resultMap.addErrorInfo(new CyncroResult.CyncroError(CyncroResult.CyncroError.ERROR_RANK_HIGHEST, "没有同期数据或者数据已被删除"));
      return resultMap;
    }
    CCommodityHeaderDao ccdao = DIContainer.getDao(CCommodityHeaderDao.class);
    CommodityHeaderDao cDao = DIContainer.getDao(CommodityHeaderDao.class);
    CCommodityDetailDao cdDao = DIContainer.getDao(CCommodityDetailDao.class);
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    TransactionManager tManager = DIContainer.getTransactionManager();
    for (int i = 0; i < ecList.size(); i++) {
      tManager.begin(getLoginInfo());
      CCommodityHeader ccheader = ecList.get(i);
      /**
       * 查询ec表中是否以存在记录 return false 插入，true 更新
       */
      boolean isHave = cDao.exists(ccheader.getShopCode(), ccheader.getCommodityCode());
      CommodityHeader cHeader = buildCommodityHeader(ccheader);
      // 查询得到cc_detail表sku数据

      long result = 0;

      if (isHave) {
        try {
          // 开始ec同期流程。

          String sql = CatalogQuery.CYNCHRO_COMMDITYHEADER_BYCYNCHRO_UPDATE;
          // update by os014 2012-03-06 修改退换货标志通过把退货标志转成二进制后取最后一位
          Long custRetrunFlag = NumUtil.toLong(getCustReturnFlag(cHeader.getReturnFlg() != null ? String.valueOf(cHeader
              .getReturnFlg()) : null));
          if (custRetrunFlag == 1L) {
            custRetrunFlag = 0L;
          } else {
            custRetrunFlag = 1L;
          }
          Object param[] = {
              cHeader.getCommodityName(), cHeader.getCommodityNameEn(), cHeader.getRepresentSkuCode(),
              cHeader.getRepresentSkuUnitPrice(), cHeader.getCommodityDescriptionPc(), cHeader.getCommodityDescriptionMobile(),
              cHeader.getCommoditySearchWords(), cHeader.getSaleStartDatetime(), cHeader.getSaleEndDatetime(),
              cHeader.getDiscountPriceStartDatetime(), cHeader.getDiscountPriceEndDatetime(), cHeader.getCommodityStandard1Name(),
              cHeader.getCommodityStandard2Name(), cHeader.getSaleFlg(), custRetrunFlag, cHeader.getWarningFlag(),
              cHeader.getSaleFlag(), cHeader.getSpecFlag(), cHeader.getBrandCode(), cHeader.getCategoryPath(),
              cHeader.getCategoryAttribute1(), cHeader.getCategoryAttribute1En(), cHeader.getCategoryAttribute1Jp(),
              cHeader.getUpdatedUser(), cHeader.getUpdatedDatetime(), cHeader.getCommodityNameJp(),
              cHeader.getCommodityDescriptionPcEn(), cHeader.getCommodityDescriptionPcJp(),
              cHeader.getCommodityDescriptionMobileEn(), cHeader.getCommodityDescriptionMobileJp(),
              cHeader.getCommodityStandard1NameEn(), cHeader.getCommodityStandard1NameJp(), cHeader.getCommodityStandard2NameEn(),
              cHeader.getCommodityStandard2NameJp(), cHeader.getOriginalPlace(), cHeader.getOriginalPlaceEn(),
              cHeader.getOriginalPlaceJp(), cHeader.getShelfLifeDays(), cHeader.getShelfLifeFlag(), cHeader.getCommodityType(),
              cHeader.getImportCommodityType(), cHeader.getClearCommodityType(), cHeader.getReserveCommodityType1(),
              cHeader.getReserveCommodityType2(), cHeader.getReserveCommodityType3(), cHeader.getNewReserveCommodityType1(),
              cHeader.getNewReserveCommodityType2(), cHeader.getNewReserveCommodityType3(), cHeader.getNewReserveCommodityType4(),
              cHeader.getNewReserveCommodityType5(), cHeader.getOriginalCode(), cHeader.getShopCode(), cHeader.getCommodityCode()
          };
          SimpleQuery query = new SimpleQuery(sql, param);
          result = tManager.executeUpdate(query);
          if (result <= 0) {
            failCount++;
            logger.debug("[shopcode is " + cHeader.getShopCode() + "commodityCode is " + cHeader.getCommodityCode()
                + " EC update fail]");
            resultMap.addErrorInfo("2", "商品编号为 : " + cHeader.getCommodityCode() + "的商品EC同期化更新失败");
            continue;
          }
          List<CCommodityDetail> detailList = cdDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), ccheader
              .getShopCode(), ccheader.getCommodityCode());

          // sku 是否更新成功的标志
          boolean detailUpdateSuccess = true;
          for (int j = 0; j < detailList.size(); j++) {
            CCommodityDetail ccDetail = detailList.get(j);
            CommodityDetail detail = buildCommodityDetail(ccheader, ccDetail);

            if (!validateDiscountCommodity(cHeader, detail)) {
              result = -1;
              detailUpdateSuccess = false;
              resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品sku编号为： " + detail.getSkuCode()
                  + "的商品SKU同期化插入失败。失败原因是特价期间输入错误");
              break;
            }

            // 判断sku是否以存在，，true:更新 false:添加
            boolean haved = dDao.exists(detail.getShopCode(), detail.getSkuCode());
            if (haved) {
              Object detailParam[] = {
                  detail.getCommodityCode(), detail.getUnitPrice(), detail.getDiscountPrice(), detail.getStandardDetail1Name(),
                  detail.getStandardDetail1NameEn(), detail.getStandardDetail1NameJp(), detail.getStandardDetail2Name(),
                  detail.getStandardDetail2NameEn(), detail.getStandardDetail2NameJp(), detail.getWeight(), detail.getUseFlg(),
                  detail.getUpdatedUser(), detail.getUpdatedDatetime(), detail.getInnerQuantity(), detail.getShopCode(),
                  detail.getSkuCode()
              };
              query = new SimpleQuery(CatalogQuery.CYNCHRO_COMMODITYDETAIL_UPDATE, detailParam);
              int detailResult = tManager.executeUpdate(query);
              // 如果更新成功将结果置为大于0的值
              if (detailResult > 0) {
                result = 100;
              } else {
                detailUpdateSuccess = false;
                tManager.rollback();
                logger.debug("[shopcode is " + cHeader.getShopCode() + "skucode is " + detail.getSkuCode() + " EC update fail]");
                resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品sku编号为： " + detail.getSkuCode()
                    + "的商品SKU同期化更新失败");
                break;
              }
            } else {
              try {
                tManager.insert(detail);
                result = 100;
              } catch (Exception e) {
                result = -1;
                tManager.rollback();
                detailUpdateSuccess = false;
                logger.debug("商品编号为：" + cHeader.getCommodityCode() + " 商品SKU编号为 : " + detail.getSkuCode() + "的商品SKU同期化插入失败", e);
                resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品sku编号为： " + detail.getSkuCode()
                    + "的商品SKU同期化插入失败");
                break;
              }
            }

          }
          if (!detailUpdateSuccess) {
            failCount++;
            tManager.rollback();
            continue;
          }
          tManager.commit();
        } catch (Exception e) {
          failCount++;
          tManager.rollback();
          logger.error("[shopcode is " + cHeader.getShopCode() + "skucode is " + cHeader.getRepresentSkuCode()
              + "update fail,fail reason is ]" + e.getMessage());
          resultMap.addErrorInfo(new CyncroResult.CyncroError(CyncroResult.CyncroError.ERROR_RANK_GENERAL, "商品编号为："
              + cHeader.getCommodityCode() + "的商品同期化更新失败" + e.getMessage()));
          continue;
        } finally {
          tManager.dispose();
        }
      } else {
        try {
          /**
           * 在库管理区分只能添加不能更新。默认值为2
           */
          CCommodityExt ext = new CCommodityExt();
          CCommodityExtDao extDao = DIContainer.getDao(CCommodityExtDao.class);
          ext = extDao.load(cHeader.getShopCode(), cHeader.getCommodityCode());
          Long stockManagementType = StockManagementType.WITH_QUANTITY.longValue();
          if (ext != null && ext.getOnStockFlag() != null && (ext.getOnStockFlag() == 1 || ext.getOnStockFlag() == 2)) {
            if (1 == ext.getOnStockFlag()) {
              stockManagementType = StockManagementType.WITH_QUANTITY.longValue();
            } else {
              stockManagementType = StockManagementType.NOSTOCK.longValue();
            }
          }
          cHeader.setStockManagementType(stockManagementType);
          setUserStatus(cHeader);
          try {
            tManager.insert(cHeader);
          } catch (Exception e) {
            failCount++;
            tManager.rollback();
            logger.debug("[shopcode is " + cHeader.getShopCode() + "skucode is " + cHeader.getRepresentSkuCode()
                + " EC insert header fail,fail reason is ]" + e.getMessage());
            resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + "的商品同期化插入失败");

            continue;
          }
          // sku插入成功标志
          boolean skuSuccess = true;
          // 查询sku信息
          List<CCommodityDetail> detailList = cdDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), ccheader
              .getShopCode(), ccheader.getCommodityCode());
          for (int j = 0; j < detailList.size(); j++) {
            CCommodityDetail ccDetail = detailList.get(j);
            CommodityDetail detail = buildCommodityDetail(ccheader, ccDetail);

            if (!validateDiscountCommodity(cHeader, detail)) {
              skuSuccess = false;
              resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品SKU编号为：" + detail.getSkuCode()
                  + "的商品 SKU同期化插入失败。失败原因是特价期间输入错误");
              break;
            }

            CommodityDetailDao detailDao = DIContainer.getDao(CommodityDetailDao.class);
            setUserStatus(detail);
            CommodityDetail delDetail = detailDao.load(cHeader.getShopCode(), detail.getSkuCode());
            if (delDetail != null) {
              tManager.delete(delDetail);
            }
            try {
              tManager.insert(detail);
            } catch (Exception e) {
              skuSuccess = false;
              logger.debug("[shopcode is " + cHeader.getShopCode() + " skucode is " + detail.getSkuCode()
                  + " EC insert detail fail,fail reason is ]" + e.getMessage());
              resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品SKU编号为：" + detail.getSkuCode()
                  + "的商品 SKU同期化插入失败");
              break;
            }
          }
          // sku插入失败继续下次同期，本次记录为失败
          if (skuSuccess) {
            result = 10;
          } else {
            failCount++;
            tManager.rollback();
            continue;
          }
          // dDao.insert(detail);
          tManager.commit();

        } catch (Exception e) {
          failCount += 1;
          logger.error("[shopcode is " + cHeader.getShopCode() + "skucode is " + cHeader.getRepresentSkuCode()
              + "insert fail,fail reason is ]" + e.getMessage());
          tManager.rollback();
          resultMap.addErrorInfo(new CyncroResult.CyncroError(CyncroResult.CyncroError.ERROR_RANK_GENERAL, "商品编号为："
              + cHeader.getCommodityCode() + "的商品同期化插入失败" + e.getMessage()));
          continue;
        } finally {
          tManager.dispose();
        }

      }
      // 每条记录执行完成都需要将同步标志修改为2 并更新同期时间
      if (result > 0) {
        successCount += 1;
        ccdao.updateByQuery(CatalogQuery.CYNCHRO_EC_ISCYNCHRO_INFO_UPDATE, CommoditySyncFlag.SYNC_FINISH.longValue(), new Date(
            startTime), ccheader.getShopCode(), ccheader.getCommodityCode());
      } else {
        failCount += 1;
      }

    }
    // 执行结束时间
    long endTime = System.currentTimeMillis();
    logger.info("EC同期化结束.............");
    // 返回同期结果
    resultMap.setFailCount(failCount);
    resultMap.setSeccessCount(successCount);
    resultMap.setTotalCount(new Long(ecList.size()));
    resultMap.setStartTime(startTime);
    resultMap.setEndTime(endTime);
    return resultMap;
  }

  /**
   * 判断特价商品是否包含完整的特价特征（特价期间，特价价格等）
   * 
   * @return
   */
  private boolean validateDiscountCommodity(CommodityHeader header, CommodityDetail detail) {
    boolean result = true;

    if (!StringUtil.isNullOrEmpty(header.getDiscountPriceStartDatetime())
        || !StringUtil.isNullOrEmpty(header.getDiscountPriceEndDatetime())) {
      if (StringUtil.isNullOrEmpty(detail.getDiscountPrice())) {
        return false;
      }
    }
    if (!StringUtil.isNullOrEmpty(detail.getDiscountPrice())) {
      if (StringUtil.isNullOrEmpty(header.getDiscountPriceStartDatetime())) {
        return false;
      }
    }

    if (!StringUtil.isNullOrEmptyAnyOf(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
      if (StringUtil.isCorrectRange(DateUtil.toDateString(header.getDiscountPriceStartDatetime()), DateUtil.toDateString(header
          .getDiscountPriceEndDatetime()))) {
        result = true;
      } else {
        result = false;
      }
    }
    return result;
  }

  public CyncroResult executeCynchTmallByCheckBox(String[] commodityCodes) {
    CyncroResult result = tmallCynchroByCheckBox(commodityCodes);
    // 保存同期化履历信息
    saveToHistory(result);
    return result;
  }

  // 2014/05/02 京东WBS对应 ob_姚 add start
  public CyncroResult executeCynchJdByCheckBox(String[] commodityCodes) {
    JdService jdService = ServiceLocator.getJdService(getLoginInfo());
    CyncroResult result = jdService.jdCommodityCyncro(commodityCodes);
    // 保存同期化履历信息
    saveToHistory(result);
    return result;
  }

  // 2014/05/02 京东WBS对应 ob_姚 add end

  /**
   * add by os014 2011.12.26 EC Tmall 两个系统同时同期化
   */
  public ServiceResult executeCynchAll() {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    return serviceResult;
  }

  public ServiceResult updateCategoryInfo(CategoryInfo categoryInfo) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CategoryDao categoryDao = DIContainer.getDao(CategoryDao.class);
    Category category = categoryDao.load(categoryInfo.getCategoryCode());

    if (category == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    category.setParentCategoryCode(categoryInfo.getParentCategoryCode());
    category.setCategoryNamePc(categoryInfo.getCategoryNamePc());
    // 20120514 tuxinwei add start
    category.setCategoryNamePcEn(categoryInfo.getCategoryNamePcEn());
    category.setCategoryNamePcJp(categoryInfo.getCategoryNamePcJp());
    // 20120514 tuxinwei add start
    category.setCategoryNameMobile(categoryInfo.getCategoryNameMobile());
    category.setDisplayOrder(categoryInfo.getDisplayOrder());
    category.setUpdatedDatetime(categoryInfo.getUpdatedDatetime());
    // add by os012 20111220 start 英文名
    category.setCategoryIdTmall(categoryInfo.getCategoryIdTmall());
    category.setMetaKeyword(categoryInfo.getMetaKeyword());
    category.setMetaDescription(categoryInfo.getMetaDescription());
    category.setKeywordCn2(categoryInfo.getKeywordCn2());
    category.setKeywordJp2(categoryInfo.getKeywordJp2());
    category.setKeywordEn2(categoryInfo.getKeywordEn2());
    // add by os012 20111220 end 英文名
    // 2014/4/28 京东WBS对应 ob_李 add start
    category.setCategoryIdJd(categoryInfo.getCategoryIdJd());
    // 2014/4/28 京东WBS对应 ob_李 add end
    // 20130703 txw add start
    category.setTitle(categoryInfo.getTitle());
    category.setTitleEn(categoryInfo.getTitleEn());
    category.setTitleJp(categoryInfo.getTitleJp());
    category.setDescription(categoryInfo.getDescription());
    category.setDescriptionEn(categoryInfo.getDescriptionEn());
    category.setDescriptionJp(categoryInfo.getDescriptionJp());
    category.setKeyword(categoryInfo.getKeyword());
    category.setKeywordEn(categoryInfo.getKeywordEn());
    category.setKeywordJp(categoryInfo.getKeywordJp());
    // 20130703 txw add end
    ValidationSummary summary = BeanValidator.validate(category);
    if (summary.hasError()) {
      logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return serviceResult;
    }

    // カテゴリ属性名の更新

    List<CategoryAttribute> categoryAttributeList = new ArrayList<CategoryAttribute>();
    for (CategoryInfoDetail ci : categoryInfo.getCategoryInfoDetailList()) {
      CategoryAttribute categoryAttribute = new CategoryAttribute();
      categoryAttribute.setCategoryAttributeNo(ci.getCategoryAttributeNo());
      categoryAttribute.setCategoryCode(categoryInfo.getCategoryCode());
      categoryAttribute.setCategoryAttributeName(ci.getCategoryAttributeName());
      // add by cs_yuli 20120607 start
      categoryAttribute.setCategoryAttributeNameEn(ci.getCategoryAttributeNameEn());
      categoryAttribute.setCategoryAttributeNameJp(ci.getCategoryAttributeNameJp());
      // add by cs_yuli 20120607 end
      categoryAttribute.setOrmRowid(ci.getOrmRowid());
      categoryAttribute.setCreatedUser(categoryInfo.getCreatedUser());
      categoryAttribute.setCreatedDatetime(categoryInfo.getCreatedDatetime());
      categoryAttribute.setUpdatedUser(categoryInfo.getUpdatedUser());
      categoryAttribute.setUpdatedDatetime(ci.getUpdatedDatetime());
      summary = BeanValidator.validate(categoryAttribute);
      if (summary.hasError()) {
        logger.error(CommonServiceErrorContent.VALIDATION_ERROR);
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        for (String error : summary.getErrorMessages()) {
          logger.error(error);
        }
        return serviceResult;
      }
      categoryAttributeList.add(categoryAttribute);
    }

    WebshopConfig config = DIContainer.getWebshopConfig();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(category);

      // カテゴリ属性の更新
      // 登録対象のカテゴリ属性番号が存在すれば更新

      logger.debug(Messages.log("service.impl.CatalogServiceImpl.1"));
      CategoryAttributeDao attribueDao = DIContainer.getDao(CategoryAttributeDao.class);
      for (CategoryAttribute ca : categoryAttributeList) {
        if (attribueDao.exists(ca.getCategoryCode(), ca.getCategoryAttributeNo())) {
          if (StringUtil.hasValue(ca.getCategoryAttributeName())) {
            txMgr.executeUpdate(CatalogQuery.UPDATE_CATEGORY_ATTRIBUTE, ca.getCategoryAttributeName(), ca
                .getCategoryAttributeNameEn(), ca.getCategoryAttributeNameJp(), this.getLoginInfo().getRecordingFormat(), DateUtil
                .getSysdate(), ca.getCategoryCode(), ca.getCategoryAttributeNo());
          } else {
            txMgr.delete(ca);
          }
        } else {
          if (StringUtil.hasValue(ca.getCategoryAttributeName())) {
            txMgr.insert(ca);
          }
        }
      }

      // カテゴリパス、階層の計算、更新

      UpdateCategoryPathProcedure updatePathProc = new UpdateCategoryPathProcedure(category.getCategoryCode(), config
          .getCategoryMaxDepth(), getLoginInfo().getRecordingFormat());
      txMgr.executeProcedure(updatePathProc);

      if (updatePathProc.getResult() == UpdateCategoryPathProcedure.SUCCESS) {
        txMgr.commit();
      } else {
        txMgr.rollback();
        if (updatePathProc.getResult() == UpdateCategoryPathProcedure.MAX_DEPTH_OVER_ERROR) {
          serviceResult.addServiceError(CatalogServiceErrorContent.CATEGORY_MAX_DEPTH_OVER_ERROR);
        } else {
          serviceResult.addServiceError(CatalogServiceErrorContent.UPDATE_CATEGORY_PATH_ERROR);
        }
      }

    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  public List<CategoryInfo> getTallRelateCategory(String category_id_tmall) {
    // 階層ごとにカテゴリのリストを取得する
    Query categoryQuery = new SimpleQuery(CategoryQuery.GET_CATEGORY_LIST_TMALL_QUERY, category_id_tmall);
    List<CategoryDetail> categoryDetailList = DatabaseUtil.loadAsBeanList(categoryQuery, CategoryDetail.class);
    // 取得結果の一時受け
    List<CategoryInfo> categoryTemp = new ArrayList<CategoryInfo>();
    String tmpCategoryCode = "";
    CategoryInfo categoryInfo = null;
    for (int i = 0; i < categoryDetailList.size(); i++) {

      CategoryDetail cd = categoryDetailList.get(i);

      if (!tmpCategoryCode.equals(cd.getCategoryCode())) {
        // カテゴリマスタに関する情報を設定
        categoryInfo = new CategoryInfo();
        categoryInfo.setCategoryCode(cd.getCategoryCode());
        categoryInfo.setCategoryNamePc(cd.getCategoryNamePc());
        // 20120514 tuxinwei add start
        categoryInfo.setCategoryNamePcEn(cd.getCategoryNamePcEn());
        categoryInfo.setCategoryNamePcJp(cd.getCategoryNamePcJp());
        // 20120514 tuxinwei add end
        categoryInfo.setCategoryNameMobile(cd.getCategoryNameMobile());
        categoryInfo.setCommodityCountPc(cd.getCommodityCountPc());
        categoryInfo.setCommodityCountMobile(cd.getCommodityCountMobile());
        categoryInfo.setDisplayOrder(cd.getDisplayOrder());
        categoryInfo.setDepth(cd.getDepth());
        categoryInfo.setParentCategoryCode(cd.getParentCategoryCode());
        categoryInfo.setPath(cd.getPath());
        categoryInfo.setOrmRowid(cd.getOrmRowid());
        categoryInfo.setUpdatedDatetime(cd.getUpdatedDatetime());
        // add by os012 20111221 start
        categoryInfo.setCategoryIdTmall(cd.getCategoryIdTmall());
        // add by os012 20111221 end
        categoryTemp.add(categoryInfo);
      }
    }
    return categoryTemp;
  }

  /**
   * add by os012 20120106 start 淘宝已下订单 库存再分配
   * 
   * @param orderHds
   * @return
   */
  public ServiceResult TmallStockAllocate() {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    txMgr.begin(getLoginInfo());
    try {
      // 获取stocklist信息
      Query query = new SimpleQuery(CatalogQuery.GET_ALL_STOCK_LIST);
      List<Stock> stockList = DatabaseUtil.loadAsBeanList(query, Stock.class);
      // 库存再分配
      for (Stock stock : stockList) {
        // 获取stock信息,参数:shopCode，skuCode
        StockDao stockDao = DIContainer.getDao(StockDao.class);
        Stock bl = stockDao.load(stock.getShopCode(), stock.getSkuCode());
        if (bl != null) {
          int success = calculateStockByBatch(txMgr, stock, true);// 执行库存在分配
          if (success == 1 || success == 0)
            continue;// 成功，继续
          else
            break;// 退出
        } else {
          logger.debug(MessageFormat.format(Messages.log("service.impl.OrderServiceImpl.24"), stock.getSkuCode()));// 找不到在库表SKU
        }
      }
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (NullPointerException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (Exception e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  public SearchResult<CommodityContainer> getCCommoditySearch(CommodityListSearchCondition condition) {
    SearchResult<CommodityKey> keyList = findCCommodityKey(condition);
    return getCCommoditySearch(keyList);
  }

  private SearchResult<CommodityKey> findCCommodityKey(CommodityListSearchCondition condition) {
    TmallCommodityKeyBackQuery query = new TmallCommodityKeyBackQuery(condition);
    return DatabaseUtil.executeSearch(query);
  }

  public SearchResult<CommodityContainer> getCCommoditySearch(SearchResult<CommodityKey> result) {

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    List<CommodityKey> keyList = result.getRows();

    if (keyList != null && keyList.size() > 0) {
      TmallContainerBackQuery query = new TmallContainerBackQuery(keyList);
      query.setMaxFetchSize(result.getMaxFetchSize());
      query.setPageSize(result.getPageSize());

      SearchResult<CommodityHeadline> rr = DatabaseUtil.executeSearch(query);

      List<CommodityContainer> rows = createCommodityContainer(rr.getRows());
      Collections.sort(rows, new CommodityKey.ContainerComparator(keyList));
      searchResult.setRows(rows);
      searchResult.setRowCount(rows.size());
    }
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());

    return searchResult;
  }

  /**
   * 分页查询EC同期化数据
   */
  @Override
  public SearchResult<CCommodityHeader> getCynchEcInfo(CommodityHistorySearchCondition contition) {
    AbstractQuery<CCommodityHeader> query = new AbstractQuery<CCommodityHeader>() {

      private static final long serialVersionUID = 1L;

      @Override
      public Class<CCommodityHeader> getRowType() {
        return CCommodityHeader.class;
      }
    };
    query.setSqlString(CatalogQuery.CYNCHRO_EC_INFO_QUERY);
    query.setMaxFetchSize(contition.getMaxFetchSize());
    query.setPageNumber(contition.getCurrentPage());
    query.setPageSize(contition.getPageSize());
    SearchResult<CCommodityHeader> searchResult = DatabaseUtil.executeSearch(query);
    searchResult.setCurrentPage(contition.getCurrentPage());
    searchResult.setMaxFetchSize(contition.getMaxFetchSize());
    searchResult.setPageSize(contition.getPageSize());
    return searchResult;
  }

  /**
   * 分页查询TMALL同期化数据
   */
  @Override
  public SearchResult<CCommodityHeader> getCynchTmallInfo(CommodityHistorySearchCondition contition) {
    AbstractQuery<CCommodityHeader> query = new AbstractQuery<CCommodityHeader>() {

      private static final long serialVersionUID = 1L;

      @Override
      public Class<CCommodityHeader> getRowType() {
        return CCommodityHeader.class;
      }
    };
    query.setSqlString(CatalogQuery.CYNCHRO_TMALL_INFO_QUERY);
    query.setMaxFetchSize(contition.getMaxFetchSize());
    query.setPageNumber(contition.getCurrentPage());
    query.setPageSize(contition.getPageSize());
    SearchResult<CCommodityHeader> searchResult = DatabaseUtil.executeSearch(query);
    searchResult.setCurrentPage(contition.getCurrentPage());
    searchResult.setMaxFetchSize(contition.getMaxFetchSize());
    searchResult.setPageSize(contition.getPageSize());
    return searchResult;
  }

  // add by os012 20120213 start
  public SearchResult<CCommodityCynchro> getCynchTInfo(CommodityHistorySearchCondition contition) {
    AbstractQuery<CCommodityCynchro> query = new AbstractQuery<CCommodityCynchro>() {

      private static final long serialVersionUID = 1L;

      @Override
      public Class<CCommodityCynchro> getRowType() {
        return CCommodityCynchro.class;
      }
    };
    query.setSqlString(CatalogQuery.CC_CYNCHRO_TMALL_INFO_QUERY);
    query.setMaxFetchSize(contition.getMaxFetchSize());
    query.setPageNumber(contition.getCurrentPage());
    query.setPageSize(contition.getPageSize());
    SearchResult<CCommodityCynchro> searchResult = DatabaseUtil.executeSearch(query);
    searchResult.setCurrentPage(contition.getCurrentPage());
    searchResult.setMaxFetchSize(contition.getMaxFetchSize());
    searchResult.setPageSize(contition.getPageSize());
    return searchResult;
  }

  // 2014/05/02 京东WBS对应 ob_姚 add start
  public SearchResult<CCommodityCynchro> getCynchJdInfo(CommodityHistorySearchCondition contition) {
    AbstractQuery<CCommodityCynchro> query = new AbstractQuery<CCommodityCynchro>() {

      private static final long serialVersionUID = 1L;

      @Override
      public Class<CCommodityCynchro> getRowType() {
        return CCommodityCynchro.class;
      }
    };
    query.setSqlString(CatalogQuery.CC_CYNCHRO_JD_INFO_QUERY);
    query.setMaxFetchSize(contition.getMaxFetchSize());
    query.setPageNumber(contition.getCurrentPage());
    query.setPageSize(contition.getPageSize());
    SearchResult<CCommodityCynchro> searchResult = DatabaseUtil.executeSearch(query);
    searchResult.setCurrentPage(contition.getCurrentPage());
    searchResult.setMaxFetchSize(contition.getMaxFetchSize());
    searchResult.setPageSize(contition.getPageSize());
    return searchResult;
  }

  // 2014/05/02 京东WBS对应 ob_姚 add end

  public SearchResult<CCommodityCynchro> getCynchEInfo(CommodityHistorySearchCondition contition) {
    AbstractQuery<CCommodityCynchro> query = new AbstractQuery<CCommodityCynchro>() {

      private static final long serialVersionUID = 1L;

      @Override
      public Class<CCommodityCynchro> getRowType() {
        return CCommodityCynchro.class;
      }
    };
    query.setSqlString(CatalogQuery.CC_CYNCHRO_EC_INFO_QUERY);
    query.setMaxFetchSize(contition.getMaxFetchSize());
    query.setPageNumber(contition.getCurrentPage());
    query.setPageSize(contition.getPageSize());
    SearchResult<CCommodityCynchro> searchResult = DatabaseUtil.executeSearch(query);
    searchResult.setCurrentPage(contition.getCurrentPage());
    searchResult.setMaxFetchSize(contition.getMaxFetchSize());
    searchResult.setPageSize(contition.getPageSize());
    return searchResult;
  }

  // add by os012 20120213 end
  /**
   * 应用于Tmall商品管理查询 add by os012 20111228 start
   * 
   * @param shopCode
   * @param commodityCode
   * @return
   */
  public CommodityInfo getCCommodityInfo(String shopCode, String commodityCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return null;
    }

    // update by os014 2012-01-09 begin
    CCommodityHeaderDao hDao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityDetailDao dDao = DIContainer.getDao(CCommodityDetailDao.class);
    StockDao sDao = DIContainer.getDao(StockDao.class);

    CommodityInfo commodityInfo = new CommodityInfo();
    CCommodityHeader header = hDao.load(shopCode, commodityCode);
    CCommodityDetail detail = new CCommodityDetail();
    Stock stock;
    if (header != null) {
      detail = dDao.load(shopCode, header.getRepresentSkuCode());
    } else {
      return null;
    }
    if (detail != null) {
      stock = sDao.load(shopCode, detail.getSkuCode());
    } else {
      return null;
    }

    commodityInfo.setCheader(header);
    commodityInfo.setCdetail(detail);
    commodityInfo.setStock(stock);
    return commodityInfo;
  }

  /**
   * * 应用于Tmall商品管理修改 add by os012 20111228 start
   * 
   * @param commodityInfo
   * @return
   */
  public ServiceResult updateCCommodityInfo(CommodityInfo commodityInfo) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    CCommodityHeader header = commodityInfo.getCheader();
    CCommodityDetail representSku = commodityInfo.getCdetail();
    Stock stock = commodityInfo.getStock();
    // 商品ヘッダの存在チェックを実行する
    CCommodityHeaderDao hDao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityHeader headerResult = hDao.load(header.getShopCode(), header.getCommodityCode());
    if (headerResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    header.setOrmRowid(headerResult.getOrmRowid());
    header.setCreatedUser(headerResult.getCreatedUser());
    header.setCreatedDatetime(headerResult.getCreatedDatetime());
    // 販売期間の開始/終了日時がnullの場合は、

    // システム最大(or最小)日付を設定する

    if (header.getSaleStartDatetime() == null) {
      header.setSaleStartDatetime(DateUtil.getMin());
    }
    if (header.getSaleEndDatetime() == null) {
      header.setSaleEndDatetime(DateUtil.getMax());
    }

    // 在庫管理区分が「在庫管理する(状況表示)」以外の場合は、

    // 在庫状況番号にnullを設定し、値をクリアする

    setUserStatus(header);
    // 商品詳細が存在すれば更新
    CCommodityDetailDao dDao = DIContainer.getDao(CCommodityDetailDao.class);
    CCommodityDetail detailResult = dDao.load(representSku.getShopCode(), representSku.getSkuCode());
    if (detailResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    representSku.setOrmRowid(detailResult.getOrmRowid());
    representSku.setCreatedUser(detailResult.getCreatedUser());
    representSku.setCreatedDatetime(detailResult.getCreatedDatetime());
    setUserStatus(representSku);
    // 代表SKU以外のSKUの特別価格、予約価格、改定価格を設定する
    List<CCommodityDetail> otherDetails = dDao.findByQuery(CommodityDeleteQuery.getCommoditySkuQuery(), header.getShopCode(),
        header.getCommodityCode());
    List<CCommodityDetail> updateDetails = new ArrayList<CCommodityDetail>();
    for (CCommodityDetail detail : otherDetails) {
      // 代表SKU以外の場合

      if (!detail.getSkuCode().equals(header.getRepresentSkuCode())) {
        // 代表SKUの各価格 == null: 各SKUの該当価格をクリアする

        // 代表SKUの各価格 != null: 各SKUの該当価格が未設定であれば、代表SKUの価格をセットする

        if (NumUtil.isNull(representSku.getDiscountPrice())) {
          detail.setDiscountPrice(null);
        } else {
          detail.setDiscountPrice(NumUtil.coalesce(detail.getDiscountPrice(), representSku.getDiscountPrice()));
        }
        updateDetails.add(detail);
      }
    }
    StockDao sDao = DIContainer.getDao(StockDao.class);
    Stock stockResult = sDao.load(stock.getShopCode(), stock.getSkuCode());
    if (stockResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    if (NumUtil.isNull(stock.getStockThreshold())) {
      stock.setStockThreshold(0L);
    }

    stock.setOrmRowid(stockResult.getOrmRowid());
    stock.setCreatedUser(stockResult.getCreatedUser());
    stock.setCreatedDatetime(stockResult.getCreatedDatetime());
    setUserStatus(stock);
    // バリデーションチェックを実行する
    ValidationSummary hSummary = BeanValidator.validate(header);
    if (hSummary.hasError()) {
      for (String error : hSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    ValidationSummary dSummary = BeanValidator.validate(representSku);
    if (dSummary.hasError()) {
      for (String error : dSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    ValidationSummary sSummary = BeanValidator.validate(stock);
    if (sSummary.hasError()) {
      for (String error : sSummary.getErrorMessages()) {
        logger.error(error);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(header);
      // 商品詳細が存在すれば更新
      txMgr.update(representSku);
      // 代表SKU以外のSKUを更新

      for (CCommodityDetail cd : updateDetails) {
        txMgr.update(cd);
      }
      // 在庫情報が存在すれば更新
      txMgr.executeUpdate(CatalogQuery.UPDATE_RESERVATIOIN_INFO, stock.getOneshotReservationLimit(), stock.getReservationLimit(),
          stock.getStockThreshold(), this.getLoginInfo().getRecordingFormat(), stock.getUpdatedDatetime(), stock.getShopCode(),
          stock.getSkuCode());
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  /***
   * * 应用于Tmall商品管理商品详细修改 add by os012 20111228 start
   * 
   * @param sku
   * @param stock
   * @return
   */
  public ServiceResult updateCCommoditySku(CCommodityDetail sku, Stock stock) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();

    // 商品ヘッダの存在チェック

    CCommodityHeaderDao hDao = DIContainer.getDao(CCommodityHeaderDao.class);
    if (!hDao.exists(sku.getShopCode(), sku.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    CCommodityHeader header = hDao.load(sku.getShopCode(), sku.getCommodityCode());
    // 将商品的可同期标志标记为 1
    header.setSyncFlagTmall(1L);
    header.setSyncFlagEc(1L);
    // 将商品的ERP/WMS导出标志重置
    header.setExportFlagErp(1L);
    header.setExportFlagWms(1L);

    if (header.getRepresentSkuCode().equals(sku.getSkuCode())) {
      header.setRepresentSkuUnitPrice(sku.getUnitPrice());
      header.setTmallRepresentSkuPrice(sku.getTmallUnitPrice());
    }

    // 商品SKUの存在チェック
    CCommodityDetailDao dDao = DIContainer.getDao(CCommodityDetailDao.class);
    CCommodityDetail skuResult = dDao.load(sku.getShopCode(), sku.getSkuCode());
    if (skuResult == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // 2014/4/28 京东WBS对应 ob_李 add start
    if (JdUseFlg.ENABLED.longValue().equals(sku.getJdUseFlg()) && !NumUtil.isNull(header.getJdCommodityId())) {
      header.setSyncFlagJd(SyncFlagJd.SYNCVISIBLE.longValue());
    }
    // 2014/4/28 京东WBS对应 ob_李 add end

    if (sku.getFixedPriceFlag() == null || "".equals(sku.getFixedPriceFlag())) {
      sku.setFixedPriceFlag(0L);
    }
    // 查询sku属性值名称
    TmallPropertyValueDao valueDao = DIContainer.getDao(TmallPropertyValueDao.class);
    if (StringUtil.hasValueAnyOf(header.getStandard1Id(), sku.getStandardDetail1Id())) {
      TmallPropertyValue value1 = valueDao.loadValue(NumUtil.toString(header.getTmallCategoryId()), header.getStandard1Id(), sku
          .getStandardDetail1Id());
      if (value1 != null) {
        sku.setStandardDetail1Name(value1.getValueName());
      }
    }
    if (StringUtil.hasValueAnyOf(header.getStandard2Id(), sku.getStandardDetail2Id())) {
      TmallPropertyValue value2 = valueDao.loadValue(NumUtil.toString(header.getTmallCategoryId()), header.getStandard2Id(), sku
          .getStandardDetail2Id());
      if (value2 != null) {
        sku.setStandardDetail2Name(value2.getValueName());
      }
    }

    // 値のコピー
    sku.setOrmRowid(skuResult.getOrmRowid());
    sku.setCreatedUser(skuResult.getCreatedUser());
    sku.setCreatedDatetime(skuResult.getCreatedDatetime());
    sku.setUpdatedDatetime(skuResult.getUpdatedDatetime());
    sku.setUpdatedUser(skuResult.getUpdatedUser());
    setUserStatus(sku);

    // バリデーションチェック

    ValidationSummary skuSummary = BeanValidator.validate(sku);
    if (skuSummary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : skuSummary.getErrorMessages()) {
        logger.error(error);
      }
      return result;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 商品SKU更新
    try {
      txMgr.begin(getLoginInfo());

      txMgr.update(header);
      txMgr.update(sku);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      logger.debug(e.getMessage());
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  /***
   * * 应用于Tmall商品管理商品详细获取 add by os012 20111228 start
   * 
   * @param shopCode
   * @param commodityCode
   * @return
   */
  public List<CCommodityDetail> getCCommoditySku(String shopCode, String commodityCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return null;
    }

    String sql = CommodityDeleteQuery.getCCommoditySkuQuery();
    CCommodityDetailDao dao = DIContainer.getDao(CCommodityDetailDao.class);
    Query query = new SimpleQuery(sql, shopCode, commodityCode);

    return dao.findByQuery(query);
  }

  /***
   * * 应用于Tmall商品管理商品删除 add by os012 20111228 start
   * 
   * @param shopCode
   * @param commodityCode
   * @return
   */
  public ServiceResult deleteCCommodity(String shopCode, String commodityCode) {
    ServiceResultImpl result = new ServiceResultImpl();
    // ショップコード、商品コードのnullチェック
    if (!StringUtil.hasValueAllOf(shopCode, commodityCode)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 指定された商品が削除可能かチェック

    List<CCommodityDetail> detailList = this.getSkuListByCommodityCode(commodityCode, shopCode);
    for (CCommodityDetail detail : detailList) {
      if (!isDeletableCommoditySku(detail.getShopCode(), detail.getSkuCode())) {
        result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
        return result;
      }
    }

    // 商品削除トランザクション開始
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      for (CCommodityDetail detail : detailList) {
        for (String query : CCommodityDeleteQuery.getSkuDeleteQuery()) {
          txMgr.executeUpdate(query, detail.getShopCode(), detail.getSkuCode());
        }
      }
      for (String query : CCommodityDeleteQuery.getCommodityDeleteQuery()) {
        txMgr.executeUpdate(query, shopCode, commodityCode);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  /**
   * 应用于Tmall商品管理商品关联SKU信息 add by os012 20111228 start
   * 
   * @param shopCode
   * @param skuCode
   * @return CommodityInfo
   */
  public CommodityInfo getAboutSkuInfo(String shopCode, String skuCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(skuCode)) {
      return null;
    }

    CCommodityHeaderDao hdao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityDetailDao ddao = DIContainer.getDao(CCommodityDetailDao.class);
    StockDao sdao = DIContainer.getDao(StockDao.class);

    CCommodityDetail detail = ddao.load(shopCode, skuCode);
    CCommodityHeader header;
    Stock stock;

    if (detail != null) {
      header = hdao.load(shopCode, detail.getCommodityCode());
      stock = sdao.load(shopCode, detail.getSkuCode());
    } else {
      return null;
    }

    CommodityInfo commodity = new CommodityInfo();
    if (header != null) {
      commodity.setCheader(header);
      commodity.setCdetail(detail);
      commodity.setStock(stock);
    } else {
      return null;
    }

    return commodity;
  }

  // 20111228 os013 add start
  // 库存管理查询
  public List<StockListSearchInfo> getStockListInfo(StockListSearchCondition condition) {
    List<StockListSearchInfo> list = new ArrayList<StockListSearchInfo>();
    if (!StringUtil.isNullOrEmpty(condition.getCommodityLink())) {
      StockListQuery query = new StockListQuery(condition, StockListQuery.COMMODITY_CODE_QUERY);
      // 查询商品
      List<StockListSearchInfo> commodityCodeList = DatabaseUtil.loadAsBeanList(query, StockListSearchInfo.class);
      List<String> searchCommodityCode = new ArrayList<String>();
      for (StockListSearchInfo ss : commodityCodeList) {
        searchCommodityCode.add(ss.getCommodityCode());
      }
      condition.setSearchCommodityCode(searchCommodityCode);
      StockListQuery queryStock = new StockListQuery(condition, StockListQuery.BASE_QUERY);
      // 执行查询
      list = DatabaseUtil.loadAsBeanList(queryStock, StockListSearchInfo.class);

    } else {
      StockListQuery query = new StockListQuery(condition, StockListQuery.BASE_QUERY);
      // 执行查询
      list = DatabaseUtil.loadAsBeanList(query, StockListSearchInfo.class);

    }

    return list;
  }

  // 20111228 os013 add end
  // add by wjw 20120103 start

  private List<CodeAttribute> getTopicPathByDetail(Category activeCategory) {

    // activeCategoryがnullか0階層（ルートカテゴリ）だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0) {
      return Collections.emptyList();
    }

    // パンくずリストに設定するカテゴリの設定

    String[] splitPath = activeCategory.getPath().split(CatalogQuery.CATEGORY_DELIMITER);
    Object[] categoryCodes = new Object[splitPath.length - 1];
    for (int i = 1; i < splitPath.length; i++) {
      categoryCodes[i - 1] = splitPath[i];
    }

    // IN検索文の生成
    SqlDialect dialect = SqlDialect.getDefault();
    SqlFragment fragment = dialect.createInClause("CATEGORY_CODE", categoryCodes);
    String inSql = fragment.getFragment();

    // SQLの実行

    Query query = new SimpleQuery(CatalogQuery.getTopicPathList(inSql), fragment.getParameters());
    List<Category> categoryList = DatabaseUtil.loadAsBeanList(query, Category.class);

    // 20120525 tuxinwei add start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // 20120525 tuxinwei add end
    // パンくずリストの生成
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    for (Category category : categoryList) {
      if (!category.getCategoryCode().equals("0")) {
        // 20120525 tuxinwei add start
        CodeAttribute nameValue = new NameValue(utilService.getNameByLanguage(category.getCategoryNamePc(), category
            .getCategoryNamePcEn(), category.getCategoryNamePcJp()), "/category/C" + category.getCategoryCode());
        // 20120525 tuxinwei add end
        topicPath.add(nameValue);
      }
    }

    return topicPath;
  }

  public List<CodeAttribute> getTopicPathByDetail(String shopCode, String commodityCode) {

    // shopCode、あるいはcommodityCodeがnullだった場合、ルートのパンくずリストを返す

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, commodityCode)) {
      return Collections.emptyList();
    }

    // バインド変数の生成

    Object[] params = new Object[] {
        shopCode, commodityCode
    };

    // shopCode、およびcommodityCodeと関連付いている、もっとも階層の深いカテゴリを取得

    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_DETAIL_CATEGORY_PATH, params);
    Category activeCategory = DatabaseUtil.loadAsBean(query, Category.class);

    // shopCode、あるいはcommodityCodeが不正だった場合、または取得したカテゴリが1階層だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0) {
      return Collections.emptyList();
    }

    // 取得したカテゴリ情報から生成されるパンくずリストを返す

    return getTopicPathByDetail(activeCategory);
  }

  // add by wjw 20120103 end
  // add by wjh 20111230 start
  public SearchResult<CCommodityHeadline> getCommodityExportSearch(String code, String searchExportObject) {
    CommodityCsvExportQuery query = new CommodityCsvExportQuery(code, searchExportObject);
    return DatabaseUtil.executeSearch(query);
  }

  public List<BrandData> getBrandList(CommodityContainerCondition condition) {
    BrandFrontQuery query = new BrandFrontQuery(condition);
    return DatabaseUtil.loadAsBeanList(query, BrandData.class);
  }

  public List<ReviewData> getReviewSummary(CommodityContainerCondition condition) {
    ReviewSummaryFrontQuery query = new ReviewSummaryFrontQuery(condition);
    return DatabaseUtil.loadAsBeanList(query, ReviewData.class);
  }

  public Object getPriceCount(CommodityContainerCondition condition) {
    PriceFrontQuery query = new PriceFrontQuery(condition);
    return DatabaseUtil.executeScalar(query);
  }

  public List<AttributeData> getAttributeList(CommodityContainerCondition condition) {
    AttributeFrontQuery query = new AttributeFrontQuery(condition);
    return DatabaseUtil.loadAsBeanList(query, AttributeData.class);
  }

  public Object getCategoryCount(String categoryPath, String brandCode) {
    Query query = null;
    if (StringUtil.hasValue(brandCode)) {
      query = new SimpleQuery(CatalogQuery.GET_CATEGORY_COUNT1, categoryPath, categoryPath + "%", brandCode);
    } else {
      query = new SimpleQuery(CatalogQuery.GET_CATEGORY_COUNT, categoryPath, categoryPath + "%");
    }
    return DatabaseUtil.executeScalar(query);
  }

  public List<SalesChartsData> getSalesChartsList() {

    // SQL文の生成?実行

    Query query = new SimpleQuery(CatalogQuery.GET_SALES_CHARTS);
    List<SalesChartsData> salesChartsList = DatabaseUtil.loadAsBeanList(query, SalesChartsData.class);

    return salesChartsList;
  }

  public List<SalesChartsData> getSalesChartsList(String categoryCode, String limit) {

    // SQL文の生成?実行

    Query query = new SimpleQuery(CatalogQuery.GET_SALES_CHARTS_B, categoryCode, categoryCode, categoryCode, categoryCode,
        categoryCode, limit);
    List<SalesChartsData> salesChartsList = DatabaseUtil.loadAsBeanList(query, SalesChartsData.class);

    return salesChartsList;
  }

  public List<SalesChartsData> getSalesChartsList(String categoryCode, String tag_no, String limit) {

    // SQL文の生成?実行
    Query query = new SimpleQuery(CatalogQuery.GET_SALES_CHARTS_C, categoryCode, categoryCode, categoryCode, categoryCode,
        categoryCode, tag_no, limit);
    List<SalesChartsData> salesChartsList = DatabaseUtil.loadAsBeanList(query, SalesChartsData.class);

    return salesChartsList;
  }

  // 20130321 add by yyq start 详细页明星产品查询
  public List<SalesChartsData> getSalesStarList(String tag_no, String limit) {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    // SQL文の生成?実行
    // Query query = new SimpleQuery(CatalogQuery.GET_SALES_STAR, tag_no,
    // currentLanguageCode, limit);
    // Query query = new SimpleQuery(CatalogQuery.getSalesStar(tag_no,
    // currentLanguageCode, limit));
    // List<SalesChartsData> salesChartsList =
    // DatabaseUtil.loadAsBeanList(query, SalesChartsData.class);

    // return salesChartsList;
    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.getSalesStar(tag_no, currentLanguageCode, limit));
    List<SalesChartsData> salesChartsList = cc.get(CacheKey.create(query), new CacheRetriever<List<SalesChartsData>>() {

      public List<SalesChartsData> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, SalesChartsData.class);
      }
    });
    return salesChartsList;

  }

  // 20130321 add by yyq end 详细页明星产品查询

  public SearchResult<CommodityContainer> getSalesChartsList(CommodityContainerCondition condition) {
    CommodityContainerQuery query = new CommodityContainerQuery();
    SearchResult<CommodityHeadline> result = DatabaseUtil.executeSearch(query.createSalesChartsCommodityList(condition));

    SearchResult<CommodityContainer> searchResult = new SearchResult<CommodityContainer>();
    searchResult.setCurrentPage(result.getCurrentPage());
    searchResult.setMaxFetchSize(result.getMaxFetchSize());
    searchResult.setOverflow(result.isOverflow());
    searchResult.setPageSize(result.getPageSize());
    searchResult.setRowCount(result.getRowCount());
    searchResult.setRows(createCommodityContainer(result.getRows()));

    return searchResult;
  }

  // add by wjh 20111230 end

  /**
   * 查询detail表中价格项目最小的值。 add by os014 2012-01-06
   */
  @Override
  public Map<String, String> findTmallDetailMinPrice(String commodityCode, String shopCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_COMMODITY_DETAIL_MINPRICE, commodityCode, shopCode);
    List<Map<String, String>> result = DatabaseUtil.loadAsMapList(query);
    if (result == null || result.size() == 0) {
      return null;
    } else {
      return result.get(0);
    }
  }

  /**
   * 查询类目的属性 add by os014 2012-01-06
   */
  @Override
  public List<TmallProperty> loadTmallPropertiesByCategoryId(String CategoryId) {
    TmallPropertyDao propertyDao = DIContainer.get("TmallPropertyDao");
    List<TmallProperty> propertys = propertyDao.loadCProByCategoryId(CategoryId);
    for (TmallProperty property : propertys) {
      List<TmallPropertyValue> values = loadTmallPropertyValuesByPropertyId(property.getPropertyId(), property.getCategoryId());
      property.setPropertyValueContain(values);
    }
    return propertys;
  }

  /**
   * 查询子类别 add by os014 2012-01-06
   */
  @Override
  public List<TmallCategory> loadAllChildCategory() {
    TmallCategoryDao categoryDao = DIContainer.get("TmallCategoryDao");
    return categoryDao.loadAllChild();
  }

  // 2014/4/28 京东WBS对应 ob_李 add start
  /**
   * 查询子类别
   */
  @Override
  public List<JdCategory> loadAllChildJdCategory() {
    JdCategoryDao categoryDao = DIContainer.get("JdCategoryDao");
    return categoryDao.loadAllChild();
  }

  // 2014/4/28 京东WBS对应 ob_李 add end

  /**
   * 全部类目add by os012 2012-02-07
   */
  @Override
  public List<TmallCategory> loadAllCategory() {
    TmallCategoryDao categoryDao = DIContainer.get("TmallCategoryDao");
    return categoryDao.loadAllCategory();
  }

  /**
   * 根据ID查询ID同级别全部类目add by os012 2012-02-07
   */
  @Override
  public List<TmallCategory> loadAllCategory(String categoryId) {
    TmallCategoryDao categoryDao = DIContainer.get("TmallCategoryDao");
    return categoryDao.loadAllCategory(categoryId);
  }

  /**
   * 根据ID查询ID下级全部类目add by os012 2012-02-07
   */
  @Override
  public List<TmallCategory> loadAllChild(String categoryId) {
    TmallCategoryDao categoryDao = DIContainer.get("TmallCategoryDao");
    return categoryDao.loadAllChild(categoryId);
  }

  /**
   * 根据ID查询ID父级别全部类目add by os012 2012-02-07
   */
  @Override
  public List<TmallCategory> loadAllFather(String categoryId) {
    TmallCategoryDao categoryDao = DIContainer.get("TmallCategoryDao");
    return categoryDao.loadAllFather(categoryId);
  }

  /**
   * 第一级add by os012 2012-02-07
   */
  @Override
  public List<TmallCategory> loadAllFather() {
    TmallCategoryDao categoryDao = DIContainer.get("TmallCategoryDao");
    return categoryDao.loadAllFather();
  }

  /**
   * 查询属性下的属性值 add by os014 2012-01-06
   */
  @Override
  public List<TmallPropertyValue> loadTmallPropertyValuesByPropertyId(String propertyId, String categoryId) {
    TmallPropertyValueDao valueDao = DIContainer.get("TmallPropertyValueDao");
    return valueDao.loadByPropertyId(propertyId, categoryId);
  }

  /**
   * 查询商品的属性 add by os014 2012-01-07
   */
  @Override
  public List<TmallCommodityProperty> loadPropertyByCommodityCode(String commodityCode) {
    Query query = new SimpleQuery("select * from tmall_commodity_property where commodity_code=?", commodityCode);
    return DatabaseUtil.loadAsBeanList(query, TmallCommodityProperty.class);
  }

  // 2014/06/10 库存更新对应 ob_卢 add start
  public ServiceResult updateStock(String shopCode, List<String> checkedCode, List<String> tmallApiFailCodeList,
      List<String> jdApiFailCodeList, List<String> stockFailCodeList, List<String> ecCynchroCodeList) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    // TMALL批处理
    JdService service = ServiceLocator.getJdService(getLoginInfo());
    int returnInt = 0;
    returnInt = service.jdOrderDownlaod("", "");
    if (returnInt == 0) {
      logger.debug("京东订单下载批处理成功!");
    } else if (returnInt == OrderDownLoadStatus.DOWNLOADFAILED.longValue().intValue()) {
      logger.debug("京东订单下载过程出现错误!");
      result.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
      return result;
    }

    // 循环获得选中的sku编号以及它的EC無限在庫，TMALL無限在庫， 在庫リーバランスフラグ和 安全在庫的现状
    for (int i = 0; i < checkedCode.size(); i++) {
      TransactionManager txMgr = DIContainer.getTransactionManager();
      StockTempInfo stockTempInfo = new StockTempInfo();
      try {
        txMgr.begin(getLoginInfo());

        String[] checked = checkedCode.get(i).split(",");
        // SKUコード
        String skuCode = checked[0];
        // 安全在庫
        Long stockThreshold = NumUtil.toLong(checked[1]);
        // EC在库割合(0-100)
        String shareRatio = checked[2];
        // 在库品区分
        Long onStockFlg = NumUtil.toLong(checked[3]);
        // 京东在库比例
        String shareRatioJd = StringUtil.EMPTY;
        String shareRatioTmall = StringUtil.EMPTY;
        if (checked.length > 4) {
          shareRatioJd = checked[4];
        }
        if (checked.length > 5) {
          shareRatioTmall = checked[5];
        }

        StockDao sdao = DIContainer.getDao(StockDao.class);
        // 原库存信息取得
        Stock stock = sdao.load(shopCode, skuCode);
        stock.setStockQuantity(stock.getStockQuantity() + stock.getStockThreshold());
        // 在库安全数
        stock.setStockThreshold(stockThreshold);

        setUserStatus(stock);
        txMgr.update(stock);

        // EC商品Header信息
        CommodityHeaderDao headerDao = DIContainer.getDao(CommodityHeaderDao.class);
        CommodityHeader header = headerDao.load(stock.getShopCode(), stock.getCommodityCode());
        if (header == null) {
          ecCynchroCodeList.add(stock.getCommodityCode());
          txMgr.rollback();
          continue;
        } else {
          // 在库管理区分 = 画面在库品区分选中时，更新为2：在库数管理；以外更新为1：在库不管理
          if (OnStockFlg.ONE.longValue().equals(onStockFlg)) {
            header.setStockManagementType(OnStockFlg.TWO.longValue());
          } else {
            header.setStockManagementType(OnStockFlg.ONE.longValue());
          }
          setUserStatus(header);
          txMgr.update(header);
        }

        // 更新共通商品Header信息
        CCommodityHeaderDao cHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
        CCommodityHeader cHeader = cHeaderDao.load(stock.getShopCode(), stock.getCommodityCode());
        cHeader.setExportFlagErp(IsOrNot.IS.longValue());
        cHeader.setExportFlagWms(IsOrNot.IS.longValue());
        setUserStatus(cHeader);
        txMgr.update(cHeader);

        // 在库品区分
        CCommodityExtDao extDao = DIContainer.getDao(CCommodityExtDao.class);
        CCommodityExt extDto = extDao.load(shopCode, stock.getCommodityCode());
        // 更新在库品区分
        // 判断在库表中的Commodity_Code在C_Commodity_Ext表中存在
        if (extDto != null) {
          extDto.setOnStockFlag(onStockFlg);
          setUserStatus(extDto);
          txMgr.update(extDto);
        } else {
          extDto = new CCommodityExt();
          extDto.setOnStockFlag(onStockFlg);
          extDto.setCommodityCode(stock.getCommodityCode());
          extDto.setShopCode(shopCode);
          extDto.setOrmRowid(DatabaseUtil.generateSequence(SequenceType.C_COMMODITY_EXT_SEQ));
          setUserStatus(extDto);
          txMgr.insert(extDto);
        }

        // EC库存比例设定
        StockRatioDao ratioDao = DIContainer.getDao(StockRatioDao.class);
        if (ratioDao.exists(stock.getShopCode(), stock.getCommodityCode(), RatioType.EC.getValue())) {
          txMgr.update(setUpdateStockRatio(stock, RatioType.EC.getValue(), shareRatio));
        } else {
          txMgr.insert(setStockRatio(stock, RatioType.EC.longValue(), shareRatio));
        }

        // 京东库存比例设定
        if (ratioDao.exists(stock.getShopCode(), stock.getCommodityCode(), RatioType.JD.getValue())) {
          txMgr.update(setUpdateStockRatio(stock, RatioType.JD.getValue(), shareRatioJd));
        } else {
          txMgr.insert(setStockRatio(stock, RatioType.JD.longValue(), shareRatioJd));
        }

        // 天猫库存比例设定
        if (ratioDao.exists(stock.getShopCode(), stock.getCommodityCode(), RatioType.TMALL.getValue())) {
          txMgr.update(setUpdateStockRatio(stock, RatioType.TMALL.getValue(), shareRatioTmall));
        } else {
          txMgr.insert(setStockRatio(stock, RatioType.TMALL.longValue(), shareRatioTmall));
        }

        // 库存再分配
        List<String> newTmallApiFailCodeList = new ArrayList<String>();
        List<String> newJdApiFailCodeList = new ArrayList<String>();
        List<String> newStockFailCodeList = new ArrayList<String>();

        StockService stockService = ServiceLocator.getStockService(getLoginInfo());
        result = (ServiceResultImpl) stockService.stockRecalculation(txMgr, stock.getCommodityCode(), newTmallApiFailCodeList,
            newJdApiFailCodeList, newStockFailCodeList, stockTempInfo);
        if (result.hasError()) {
          txMgr.rollback();
          if (newTmallApiFailCodeList.size() > 0) {
            tmallApiFailCodeList.addAll(newTmallApiFailCodeList);
          }
          if (newJdApiFailCodeList.size() > 0) {
            jdApiFailCodeList.addAll(newJdApiFailCodeList);
          }
          if (newStockFailCodeList.size() > 0) {
            stockFailCodeList.addAll(newStockFailCodeList);
          }
        } else {
          // 库存增量登录
          stockService.updateStockTemp(txMgr, stockTempInfo);
          txMgr.commit();
        }

      } catch (RuntimeException e) {
        txMgr.rollback();
        throw e;
      } finally {
        txMgr.dispose();
      }
    }

    return result;
  }

  private StockRatio setUpdateStockRatio(Stock stock, String ratioType, String shareRatio) {
    StockRatioDao ratioDao = DIContainer.getDao(StockRatioDao.class);
    StockRatio ratio = ratioDao.load(stock.getShopCode(), stock.getCommodityCode(), ratioType);
    ratio.setStockRatio(NumUtil.toLong(shareRatio));
    setUserStatus(ratio);
    return ratio;
  }

  private StockRatio setStockRatio(Stock stock, Long ratioType, String shareRatio) {
    StockRatio ratio = new StockRatio();
    ratio.setShopCode(stock.getShopCode());
    ratio.setCommodityCode(stock.getCommodityCode());
    ratio.setRatioType(ratioType);
    ratio.setStockRatio(NumUtil.toLong(shareRatio));

    setUserStatus(ratio);
    return ratio;
  }

  public Long getUseSuitStockJd(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_ALL_USE_QUANTITY_JD, commodityCode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  // 2014/06/10 库存更新对应 ob_卢 add end
  // 20120107 os013 add start
  // 在库更新
  public ServiceResult updateStockInfo(String shopCode, List<String> checkedCode) {
    ServiceResultImpl result = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    // 循环获得选中的sku编号以及它的EC無限在庫，TMALL無限在庫， 在庫リーバランスフラグ和 安全在庫的现状
    try {

      txMgr.begin(getLoginInfo());
      // 订单下载次数控制
      int dlFlg = 0;
      // 既存数据安全库存
      for (int i = 0; i < checkedCode.size(); i++) {
        String[] checked = checkedCode.get(i).toString().split(",");
        // SKUコード
        String skuCode = checked[0];
        // 安全在庫
        Long stockThreshold = NumUtil.toLong(checked[1]);
        // 20120116 os013 add start
        // EC在库割合(0-100)
        String shareRatio = checked[2];
        // 在库品区分
        Long onStockFlg = NumUtil.toLong(checked[3]);
        // 20120116 os013 add end
        StockDao sdao = DIContainer.getDao(StockDao.class);
        // 原库存信息取得
        Stock stock = sdao.load(shopCode, skuCode);

        /*********************** 画面检查开始 **************************/
        // // 既存数据安全库存的值
        Long oldStockThreshold = stock.getStockThreshold();
        // 既存数据分配比例的值
        Long oldShareRatio = stock.getStockThreshold();

        // 在库安全数
        stock.setStockThreshold(stockThreshold);
        // 20120116 os013 add start
        // EC在库割合(0-100)设定
        if (!StringUtil.isNullOrEmpty(shareRatio)) {
          stock.setShareRatio(NumUtil.toLong(shareRatio));
        } else {
          stock.setShareRatio(null);
        }
        // 20120116 os013 add end
        /********************** 画面检查结束 ***************************/
        Long oldOnStockFlg = 0L;
        // 在库品区分检查
        Query query1 = new SimpleQuery(CatalogQuery.ON_STOCK_FLG_CHECK, stock.getSkuCode());
        StockTemp stockFlgCheck = DatabaseUtil.loadAsBean(query1, StockTemp.class);
        // 在库区分不存在时
        if (stockFlgCheck != null) {
          // 非在库品时
          oldOnStockFlg = NumUtil.toLong(stockFlgCheck.getStockFlg());
        }
        // 一次处理只能下载一次
        if (dlFlg == 0) {
          // 淘宝订单下载
          // 订单下载条件：库存变化或者tmall无限大，从1->0
          if (!(oldShareRatio.equals(shareRatio) && oldStockThreshold.equals(stockThreshold) && oldOnStockFlg.equals(onStockFlg))) {
            OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
            // 淘宝订单下载
            int successFlg = orderService.OrderDownLoadCommon("", "", false);

            // -1是下载失败，-2为下载中
            if (successFlg == -1) {
              // 订单下载失败
              result.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
              return result;
            } else if (successFlg == -2) {
              // 订单下载中
              result.addServiceError(OrderServiceErrorContent.ORDER_DOWNLOADING);
              return result;
            }
            // 成功后自加
            dlFlg++;
          }
        }
        // 获取stock和stocktemp信息,参数:shopCode，skuCode
        Query query = new SimpleQuery(CatalogQuery.STOCK_AND_STOCK_TEMP_INFO + " WHERE A.SHOP_CODE=? AND A.SKU_CODE =?", shopCode,
            skuCode);
        StockTemp stockNew = DatabaseUtil.loadAsBean(query, StockTemp.class);
        // EC引当调整
        stock.setAllocatedQuantity(stockNew.getAllocatedQuantity());
        // TMALL引当调整
        stock.setAllocatedTmall(stockNew.getAllocatedTmall());
        // 总库存调整
        if (!NumUtil.isNull(stockNew.getAddStockTotal())) {
          // 总库存+库存增量
          stock.setStockTotal(stock.getStockTotal() + stockNew.getAddStockTotal());
        }

        // 分配条件：库存变化
        if (!(oldShareRatio.equals(shareRatio) && oldStockThreshold.equals(stockThreshold) && oldOnStockFlg.equals(onStockFlg))) {
          // 库存在分配 0:分配成功，1:未分配 -1：分配失败
          int calculateStock = calculateStock(txMgr, stock, oldStockThreshold, onStockFlg);
          if (calculateStock == -1) {
            result.addServiceError(CatalogServiceErrorContent.STOCK_THE_ALLOCATION_WAS_NOT_SUCCESSFUL_ERROR);
            return result;
          }
        }
      }
      txMgr.commit();

      return result;
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  /***
   * 库存再分配批处理 安全库存有变更调用返回：0为分配成功，1为未分配，-1为error
   */
  public int calculateStockByBatch(TransactionManager txMgr, Stock stock, boolean excFlg) {
    // 不需要处理
    if (excFlg == false) {
      return 1;
    }

    int allocatedQuantityRate = 0;
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    WebshopConfig config = DIContainer.getWebshopConfig();
    Long CriticalValue = config.getCriticalValue(); // 临界值
    Long shareRatioRate = null; // 分配比例
    // 非在库品，库存分配比例100%全部给EC。否则，库存分配比例为空，从配置文件中读取
    Query queryOnStock = new SimpleQuery(CatalogQuery.ON_STOCK_FLG_CHECK, stock.getSkuCode());
    StockTemp isOnStockBean = DatabaseUtil.loadAsBean(queryOnStock, StockTemp.class);

    try {
      // 在库数为0， 或者标识分配不可时，退出分配方法
      if (stock.getStockTotal().equals(0L) || isOnStockBean.getStockFlg().equals("2")) {
        return 1;
      }

      Long stockTotal = stock.getStockTotal(); // 总在库数，（Stock表 总库存+StockTemp表
      // 总库存）
      Long stockQuantityEC = stock.getStockQuantity(); // Stock表 EC库存
      Long stockQuantityTmall = stock.getStockTmall(); // Stock表 TMALL库存
      Long allocatedQuantityEc = stock.getAllocatedQuantity(); // Stock表 EC引当数量
      Long allocatedQuantityTmall = stock.getAllocatedTmall(); // Stock表
      // TMALL引当数
      Long stockThreshold = stock.getStockThreshold(); // Stock表安全库存
      Long saleQuantityEc = stockQuantityEC - allocatedQuantityEc; // EC贩卖可能数

      // 总贩卖可能数=总在库数-安全在库数-EC引当-Tmall引当
      Long stockAllNum = stockTotal - stockThreshold - allocatedQuantityEc - allocatedQuantityTmall;

      // EC分配比例公式= EC贩卖可能数*100/(TMall贩卖可能数 +EC贩卖可能数)
      if (0L != stockAllNum) {
        allocatedQuantityRate = (BigDecimal.valueOf(saleQuantityEc * 100L).divide(BigDecimal.valueOf(stockAllNum), 0,
            BigDecimal.ROUND_HALF_UP)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
      } else {
        return 1;
      }
      // 判断是否在临界值内,在临界值内就分配，例：平衡率为40 则 40<=计算的分配率<=100-40
      if ((CriticalValue.intValue() <= allocatedQuantityRate && allocatedQuantityRate <= (100 - CriticalValue.intValue()))) {
        return 1;
      } else {
        if (isOnStockBean.getStockFlg().equals("2")) { // 非在库品，分配比例强制为100
          shareRatioRate = 100L;
        } else {
          if (stock.getShareRatio() == null) {
            shareRatioRate = config.getShareRatioRate();
          } else {
            shareRatioRate = stock.getShareRatio();
          }
        }

        // 重新分配库存 Tmall（总贩卖可能数*分配比例/100L）
        Long calcuInit = stockAllNum * (100 - shareRatioRate) / 100L; // 根据公式计算出初始值
        Long saleTmallNum = null;

        // 按比例分配。如果有小数库存存在，那么将比例小的一方+1，大的一方取整
        Double dblStockAll = Double.valueOf(stockAllNum);
        Double dblShareRatio = Double.valueOf(shareRatioRate);

        if (NumUtil.isDecimal(stockAllNum.toString(), shareRatioRate.toString())) { // 如果分配后的值包含小数
          if (shareRatioRate < 50) { // 当TMALL分配比例高时（100-shareRatioRate >50
            // ）,多余库存分给EC
            saleTmallNum = NumUtil.parseLong(Math.floor(dblStockAll * (100 - dblShareRatio) / 100));
          } else if (shareRatioRate == 50) { // 当分配比例相当时，根据配置文件设定分配
            String assign = config.getRemainAssignSwitch();
            if (assign.equals(StockRemainSwitch.TMALL.getName())) { // 根据配置选择多余的分配对象
              saleTmallNum = NumUtil.parseLong(Math.ceil(dblStockAll * (100 - dblShareRatio) / 100));
            } else {
              saleTmallNum = NumUtil.parseLong(Math.floor(dblStockAll * (100 - dblShareRatio) / 100));
            }
          } else {
            saleTmallNum = NumUtil.parseLong(Math.ceil(dblStockAll * (100 - dblShareRatio) / 100));
          }
        } else {// 没有小数的情况下，按照比例直接分配
          saleTmallNum = calcuInit; // TMALL贩卖可能数
        }

        Long saleEcNum = stockAllNum - saleTmallNum; // EC 贩卖可能数

        // 重新分配后EC在库数=EC引当数量+重新分配后 EC贩卖可能数
        Long stockQuantityEcAgain = allocatedQuantityEc + saleEcNum;
        // 重新分配后TMALL在库数=TMALL引当数+重新分配后 TMALL贩卖可能数
        Long stockQuantityTmallAgain = allocatedQuantityTmall + saleTmallNum;

        // EC库存增量=重新分配后EC在库数-分配前的EC在库数
        Long addECStack = stockQuantityEcAgain - stockQuantityEC;
        // Tmall库存增量=重新分配后Tmall在库数-分配前的Tmall在库数
        Long addTmallStack = stockQuantityTmallAgain - stockQuantityTmall;

        // 判断stocktemp是否有数据
        Query query = new SimpleQuery(CatalogQuery.STOCK_STEMP_INFO, stock.getShopCode(), stock.getSkuCode());
        StockTemp stockTemp = DatabaseUtil.loadAsBean(query, StockTemp.class);

        // 判断是否存在
        if (stockTemp == null) {
          // 如果ec库存没有增量，Tamll库存没有增量的话，不需要插入
          if (addECStack.equals(0L) && addTmallStack.equals(0L)) {
            return 1;
          }
          // del by lc 2012-09-11 start
          /*
           * // 2011/03/14 修改 os011 start //
           * 修改用意：如总库存是12,EC是7淘宝时5，EC引当是7淘宝引当是4,分配比例50，临界值20 //
           * 然后库存调整，ec调整1个，淘宝为0个，此时ec库存为8个，淘宝库存就必须减掉1个 if (addTmallStack == 0L
           * && addECStack != 0L && stock.getStockTmall() > 0L) { addTmallStack
           * = -addECStack; } // 2011/03/14 修改 os011 end
           */
          // del by lc 2012-09-11 start
          // 插入库存临时表中的EC库存增量，Tmall库存增量
          Long orm_rowid = DatabaseUtil.generateSequence(SequenceType.STOCK_TEMP_NO);
          Query executeInsert = new SimpleQuery(CatalogQuery.INSERT_STOCK_TEMP, stock.getShopCode(), stock.getSkuCode(), 0L,
              addECStack, addTmallStack, 0L, orm_rowid, this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), this
                  .getLoginInfo().getRecordingFormat(), DateUtil.getSysdate());
          txMgr.executeUpdate(executeInsert);
        } else {
          // 更新库存临时表中的EC库存增量，Tmall库存增量
          txMgr.executeUpdate(CatalogQuery.UPDATE_STOCK_TEMP, addECStack, addTmallStack, this.getLoginInfo().getRecordingFormat(),
              DateUtil.getSysdate(), stock.getShopCode(), stock.getSkuCode());
        }
        return 0;
      }
    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      return -1;
    }
  }

  /**
   * 安全在库调整用库存分配
   * 
   * @param txMgr
   * @param stock
   * @param old_Stock_Threshold
   * @return
   */
  public int calculateStock(TransactionManager txMgr, Stock stock, Long old_Stock_Threshold, Long onStockFlg) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    WebshopConfig config = DIContainer.getWebshopConfig();
    try {

      // 分配比例
      Long shareRatioRate = stock.getShareRatio();
      if (shareRatioRate == null) {
        shareRatioRate = config.getShareRatioRate();
      }

      // 在库品区分检查，非在库品场合
      if (onStockFlg.equals(2L)) {
        // 分配比例强制为100
        shareRatioRate = 100L;
      }

      Long stockTotal = stock.getStockTotal(); // 总在库数，（Stock表 总库存+StockTemp表
      // 总库存）
      Long stockQuantityEC = stock.getStockQuantity(); // Stock表 EC库存
      Long stockQuantityTmall = stock.getStockTmall(); // Stock表 TMALL库存
      Long allocatedQuantityEc = stock.getAllocatedQuantity(); // Stock表 EC引当数量
      Long allocatedQuantityTmall = stock.getAllocatedTmall(); // Stock表
      // TMALL引当数
      Long stockThreshold = stock.getStockThreshold(); // Stock表安全库存

      // 总贩卖可能数=总在库数-安全在库数-EC引当-Tmall引当
      Long stockAllNum = stockTotal - stock.getStockThreshold() - allocatedQuantityEc - allocatedQuantityTmall;

      // 重新分配库存 Tmall（总贩卖可能数*分配比例/100L）
      Long calcuInit = stockAllNum * (100 - shareRatioRate) / 100L; // 根据公式计算出初始值
      Long saleTmallNum = null;

      // 按比例分配。如果有小数库存存在，那么将比例小的一方+1，大的一方取整
      Double dblStockAll = Double.valueOf(stockAllNum);
      Double dblShareRatio = Double.valueOf(shareRatioRate);

      if (NumUtil.isDecimal(stockAllNum.toString(), shareRatioRate.toString())) { // 如果分配后的值包含小数
        if (shareRatioRate < 50) { // 当TMALL分配比例高时（100-shareRatioRate >50
          // ）,多余库存分给EC
          saleTmallNum = NumUtil.parseLong(Math.floor(dblStockAll * (100 - dblShareRatio) / 100));
        } else if (shareRatioRate == 50) { // 当分配比例相当时，根据配置文件设定分配
          String assign = config.getRemainAssignSwitch();
          if (assign.equals(StockRemainSwitch.TMALL.getName())) { // 根据配置选择多余的分配对象
            saleTmallNum = NumUtil.parseLong(Math.ceil(dblStockAll * (100 - dblShareRatio) / 100));
          } else {
            saleTmallNum = NumUtil.parseLong(Math.floor(dblStockAll * (100 - dblShareRatio) / 100));
          }
        } else {
          saleTmallNum = NumUtil.parseLong(Math.ceil(dblStockAll * (100 - dblShareRatio) / 100));
        }
      } else {// 没有小数的情况下，按照比例直接分配
        saleTmallNum = calcuInit; // TMALL贩卖可能数
      }

      Long saleEcNum = stockAllNum - saleTmallNum; // EC 贩卖可能数

      // 重新分配后EC在库数=EC引当数量+重新分配后 EC贩卖可能数
      Long stockQuantityEcAgain = allocatedQuantityEc + saleEcNum;
      // 重新分配后TMALL在库数=TMALL引当数+重新分配后 TMALL贩卖可能数
      Long stockQuantityTmallAgain = allocatedQuantityTmall + saleTmallNum;

      // EC库存增量=重新分配后EC在库数-分配前的EC在库数
      Long addECStack = stockQuantityEcAgain - stockQuantityEC;
      // Tmall库存增量=重新分配后Tmall在库数-分配前的Tmall在库数
      Long addTmallStack = stockQuantityTmallAgain - stockQuantityTmall;

      // 安全库存增量=重新分配后安全库存-分配前的安全库存
      Long addStockThreshold = stockThreshold - old_Stock_Threshold;

      // 判断stocktemp是否有数据
      Query query = new SimpleQuery(CatalogQuery.STOCK_STEMP_INFO, stock.getShopCode(), stock.getSkuCode());
      StockTemp stockTemp = DatabaseUtil.loadAsBean(query, StockTemp.class);
      // 2011/03/14 修改 os011 start
      // 修改用意：如总库存是12,EC是7淘宝时5，EC引当是7淘宝引当是4,分配比例50，临界值20
      // 然后库存调整，ec调整1个，淘宝为0个，此时ec库存为8个，淘宝库存就必须减掉1个
      // if (addTmallStack == 0L && addECStack != 0L && stock.getStockTmall() >
      // 0L) {
      // addTmallStack = -addECStack;
      // }
      // 2011/03/14 修改 os011 end
      // 判断是否存在
      if (stockTemp == null) {
        // 插入库存临时表中的EC库存增量，Tmall库存增量,安全库存增量
        Long orm_rowid = DatabaseUtil.generateSequence(SequenceType.STOCK_TEMP_NO);
        Query executeInsert = new SimpleQuery(CatalogQuery.INSERT_STOCK_TEMP, stock.getShopCode(), stock.getSkuCode(), 0L,
            addECStack, addTmallStack, addStockThreshold, orm_rowid, this.getLoginInfo().getRecordingFormat(), DateUtil
                .getSysdate(), this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate());
        txMgr.executeUpdate(executeInsert);
      } else {
        // 更新库存临时表中的EC库存增量，Tmall库存增量
        txMgr.executeUpdate(CatalogQuery.UPDATE_STOCK_TEMP_THRESHOLD, addECStack, addTmallStack, addStockThreshold, this
            .getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), stock.getShopCode(), stock.getSkuCode());
      }

      return 0;

    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      return -1;
    }
  }

  // 20120107 os013 add end

  @Override
  public List<CCommodityDetail> getSkuListByCommodityCode(String commodityCode, String shopCode) {
    CCommodityDetailDao cdDao = DIContainer.get(CCommodityDetailDao.class.getSimpleName());
    return cdDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), shopCode, commodityCode);
  }

  /**
   * add by os014 2012-01-09 更新c_commodity_header表
   * 
   * @param CcommodityHeader对象
   * @return 操作结果
   */
  @Override
  public ServiceResult updateCcheader(CCommodityHeader header) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityHeader updateHeader = dao.load(header.getShopCode(), header.getCommodityCode());
    // 商品ヘッダの存在チェック
    if (updateHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    // システム最大(or最小)日付を設定する
    if (header.getSaleStartDatetime() == null) {
      header.setSaleStartDatetime(DateUtil.getMin());
    }
    if (header.getSaleEndDatetime() == null) {
      header.setSaleEndDatetime(DateUtil.getMax());
    }
    header.setSyncFlagEc(1L);
    header.setSyncFlagTmall(1L);
    header.setUpdatedDatetime(updateHeader.getUpdatedDatetime());
    setUserStatus(header);
    try {

      updateHeader.setRepresentSkuCode(header.getRepresentSkuCode());
      updateHeader.setRepresentSkuUnitPrice(header.getRepresentSkuUnitPrice());
      updateHeader.setSyncFlagEc(1L);
      updateHeader.setSyncFlagTmall(1L);
      updateHeader.setBigFlag(header.getBigFlag());
      updateHeader.setBrandCode(header.getBrandCode());
      updateHeader.setBuyerCode(header.getBuyerCode());
      updateHeader.setCategoryAttributeValue(header.getCategoryAttributeValue());
      updateHeader.setCategorySearchPath(header.getCategorySearchPath());
      updateHeader.setCommodityDescription1(header.getCommodityDescription1());
      updateHeader.setCommodityDescription2(header.getCommodityDescription2());
      updateHeader.setCommodityDescription3(header.getCommodityDescription3());

      setUserStatus(updateHeader);
      dao.update(updateHeader);
    } catch (Exception e) {
      logger.error("update c_commodity_header table DB error! commodityCode is " + header.getCommodityCode(), e);
      e.printStackTrace();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
    }
    return result;
  }

  @Override
  public ServiceResult addCcheader(CCommodityHeader header) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    header.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    CCommodityHeaderDao haderDao = DIContainer.get(CCommodityHeaderDao.class.getSimpleName());
    // 商品ヘッダ の重複チェック
    if (haderDao.exists(header.getShopCode(), header.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    setUserStatus(header);
    TransactionManager tmgr = DIContainer.getTransactionManager();
    try {
      tmgr.begin(getLoginInfo());
      tmgr.insert(header);
      tmgr.commit();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      tmgr.rollback();
    } finally {
      tmgr.dispose();
    }
    return result;
  }

  /**
   * ERP,WMS库存导入时，库存再分配用
   */
  public int recalcStock(String fromStr) {
    String sku_Code = "";
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("库存再分配开始");
    TransactionManager transaction = DIContainer.getTransactionManager();
    WebshopConfig config = DIContainer.getWebshopConfig();

    try {
      transaction.begin(getLoginInfo());
      // 获取stock和stocktemp信息,参数:shopCode，skuCode
      Query query = new SimpleQuery(CatalogQuery.STOCK_INFO_LIST);
      List<StockTemp> stockTempList = DatabaseUtil.loadAsBeanList(query, StockTemp.class);

      Long shareRatioRate = null; // 分配比例
      // 循环进行库存平衡
      for (StockTemp stock : stockTempList) {
        sku_Code = stock.getSkuCode();
        // 在库品区分检查
        Query query1 = new SimpleQuery(CatalogQuery.ON_STOCK_FLG_CHECK, sku_Code);
        StockTemp stockFlgCheck = DatabaseUtil.loadAsBean(query1, StockTemp.class);

        if (stockFlgCheck.getStockFlg().equals("2")) { // 非在库品时 分配比例强制为100
          shareRatioRate = 100L;
        } else { // 库存比例为空时，从配置文件中取出
          shareRatioRate = stock.getShareRatio();
          if (shareRatioRate == null) {
            shareRatioRate = config.getShareRatioRate();
          }
        }

        Long stockTotal = stock.getStockTotal(); // 总在库数，（Stock表
        // 总库存+StockTemp表总库存）
        Long stockQuantityEC = stock.getStockQuantity(); // Stock表 EC库存
        Long stockQuantityTmall = stock.getStockTmall(); // Stock表 TMALL库存
        Long allocatedQuantityEc = stock.getAllocatedQuantity(); // Stock表EC引当数量
        Long allocatedQuantityTmall = stock.getAllocatedTmall(); // Stock表TMALL引当数
        Long stockThreshold = stock.getStockThreshold(); // Stock表安全库存

        // 总贩卖可能数=总在库数-安全在库数-EC引当-Tmall引当
        Long saleStockAllNum = stockTotal - stockThreshold - allocatedQuantityEc - allocatedQuantityTmall;
        Long saleTmallNum = null;
        boolean addTotalStockFlg = false; // 更新总库存增量标志
        boolean addThresholdStockFlg = false; // 更新安全库存增量标志
        Long addTotalStock = 0L; // 总库存增量
        Long addThresholdStock = 0L; // 安全库存增量

        if (saleStockAllNum > 0) { // 可销售数 >0 时正常分配

          // 重新分配库存 Tmall（总贩卖可能数*分配比例/100L）
          Long calcuInit = saleStockAllNum * (100 - shareRatioRate) / 100L; // 根据公式计算出初始值

          // 按比例分配。如果有小数库存存在，那么将比例小的一方+1，大的一方取整
          Double dblStockAll = Double.valueOf(saleStockAllNum);
          Double dblShareRatio = Double.valueOf(shareRatioRate);

          if (NumUtil.isDecimal(saleStockAllNum.toString(), shareRatioRate.toString())) { // 如果分配后的值包含小数
            if (shareRatioRate < 50) { // 当TMALL分配比例高时（100-shareRatioRate >50
              // ）,多余库存分给EC

              saleTmallNum = NumUtil.parseLong(Math.floor(dblStockAll * (100 - dblShareRatio) / 100));
            } else if (shareRatioRate == 50) { // 当分配比例相当时，根据配置文件设定分配

              String assign = config.getRemainAssignSwitch();
              if (assign.equals(StockRemainSwitch.TMALL.getName())) { // 根据配置选择多余的分配对象
                saleTmallNum = NumUtil.parseLong(Math.ceil(dblStockAll * (100 - dblShareRatio) / 100));
              } else {
                saleTmallNum = NumUtil.parseLong(Math.floor(dblStockAll * (100 - dblShareRatio) / 100));
              }
            } else {
              saleTmallNum = NumUtil.parseLong(Math.ceil(dblStockAll * (100 - dblShareRatio) / 100));
            }
          } else { // 没有小数的情况下，按照比例直接分配

            saleTmallNum = calcuInit; // TMALL贩卖可能数
          }

        } else { // 可销售数 <= 0

          /*
           * 当销售可能数小于零时，分五种情况进行库存分配 1，总库存（原始库存加增量表的库存增量，下同）等于零并且引当数为零时，保持安全库存不动
           * EC/TMALL库存等于EC/TMALL引当，总库存与读取文件中的库存数相同。
           * 2，总库存等于零并且引当数不为零时，EC/TMALL库存等于EC/TMALL引当，总库存等于引当加安全库存，发送库存错误邮件！
           * 3，总库存大于零并且引当数等于零（通常适用安全库存较高的情况），总库存与读取文件中的库存数相同
           * 4，总库存大于零，引当数大于零且总库存减去引当小于零时，处理方式同2，发送库存错误邮件！
           * 5，总库存大于零，引当数大于零且总库存减去引当大于、等于零时，安全库存清零，总库存与读取文件中的库存数相同。
           */
          if (stockTotal == 0) { // 默认情况1

            if (allocatedQuantityEc > 0 || allocatedQuantityTmall > 0) { // 情况2

              sandErrorMail(sku_Code, fromStr); // 发送错误邮件
              addTotalStockFlg = true;
            }

          } else { // 默认情况3

            if (allocatedQuantityEc > 0 || allocatedQuantityTmall > 0) {

              if (stockTotal - allocatedQuantityEc - allocatedQuantityTmall < 0) {// 情况4

                sandErrorMail(sku_Code, fromStr); // 发送错误邮件
                addTotalStockFlg = true;

              } else { // 情况5

                addThresholdStockFlg = true; // 清空安全库存标志
                addThresholdStock = -stockThreshold; // 做法，更新增量表中安全库存增量，使之等于当前安全库存的负值。
              }
            }
          } // END 总库存>0

          /*
           * 当销售可能数小于或等于零时，将销售可能数设置为零 这样，当通过stockTemp增量表进行计算库存时，可以符合如下公式： EC库存 =
           * EC引当，TMALL库存 = TMALL库存
           */
          saleStockAllNum = 0L;
          saleTmallNum = 0L;
        }

        Long saleEcNum = saleStockAllNum - saleTmallNum; // EC 贩卖可能数

        // 重新分配后EC在库数=EC引当数量+重新分配后 EC贩卖可能数
        Long stockQuantityEcAgain = allocatedQuantityEc + saleEcNum;
        // 重新分配后TMALL在库数=TMALL引当数+重新分配后 TMALL贩卖可能数
        Long stockQuantityTmallAgain = allocatedQuantityTmall + saleTmallNum;

        // EC库存增量=重新分配后EC在库数-分配前的EC在库数
        Long addECStack = stockQuantityEcAgain - stockQuantityEC;
        // Tmall库存增量=重新分配后Tmall在库数-分配前的Tmall在库数
        Long addTmallStack = stockQuantityTmallAgain - stockQuantityTmall;

        if (addTotalStockFlg) {
          addTotalStock = allocatedQuantityEc + allocatedQuantityTmall + stockThreshold - stock.getOriginalStock();
          // 更新库存临时表中的EC库存增量，Tmall库存增量, 总库存增量
          transaction.executeUpdate(CatalogQuery.UPDATE_STOCK_TEMP_STOCK_TOTAL, addECStack, addTmallStack, addTotalStock, this
              .getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), stock.getShopCode(), stock.getSkuCode());
        } else if (addThresholdStockFlg) {
          // 更新库存临时表中的EC库存增量，Tmall库存增量, 安全库存增量
          transaction.executeUpdate(CatalogQuery.UPDATE_STOCK_TEMP_THRESHOLD, addECStack, addTmallStack, addThresholdStock, this
              .getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), stock.getShopCode(), stock.getSkuCode());
        } else {
          // 更新库存临时表中的EC库存增量，Tmall库存增量
          transaction.executeUpdate(CatalogQuery.UPDATE_STOCK_TEMP, addECStack, addTmallStack, this.getLoginInfo()
              .getRecordingFormat(), DateUtil.getSysdate(), stock.getShopCode(), stock.getSkuCode());
        }
      }

      transaction.commit();
      logger.debug("库存再分配结束。");
      return 0;
    } catch (RuntimeException e) {
      logger.debug("库存再分配异常。SKU编号：" + sku_Code);
      transaction.rollback();
      return -1;
    } finally {
      transaction.dispose();
    }

  }

  /**
   * 上传淘宝 返回值0：成功，1为未上传，-1为error modFlg 1:淘宝下载自动分配 2：库存调整，库存导入，库存移动等
   */
  public int TmallSku_Code_UP(String sku_Code, Stock stockNew, Long onStockFlg, String modFlg) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager transaction = null;
    StockDao stockDao = DIContainer.getDao(StockDao.class);
    SetCommodityCompositionDao compositionDao = DIContainer.getDao(SetCommodityCompositionDao.class);
    TmallSuitCommodityDao suitDao = DIContainer.getDao(TmallSuitCommodityDao.class);
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    // 成功标志
    int sucessFlg = 1;
    Query stockQuery = null;
    // 传的SKU为空的时候
    if (StringUtil.isNullOrEmpty(sku_Code)) {
      stockQuery = new SimpleQuery(CatalogQuery.GET_TMALL_UP_SKU_CODE_LIST, DIContainer.getWebshopConfig().getSiteShopCode());
    } else {
      stockQuery = new SimpleQuery(CatalogQuery.GET_TMALL_UP_SKU_CODE_LIST + " AND SKU_CODE=?", DIContainer.getWebshopConfig()
          .getSiteShopCode(), sku_Code);
    }
    // 取得未上传淘宝的SKu信息
    List<StockTemp> stockTempList = DatabaseUtil.loadAsBeanList(stockQuery, StockTemp.class);

    try {

      if (stockTempList.size() > 0) {
        transaction = DIContainer.getTransactionManager();
      }
      for (StockTemp stockTemp : stockTempList) {
        List<SetCommodityComposition> compositionList = compositionDao.loadChild("00000000", stockTemp.getSkuCode());
        transaction.begin(getLoginInfo());
        // 如果淘宝库存增量及EC库存增量没有数据的场合，不更新库存
        if (modFlg.equals("1") && stockTemp.getAddStockEc().equals(0L) && stockTemp.getAddStockTmall().equals(0L)) {
          continue;
        }
        /******** 库存更新及删除库存临时表开始 **********/
        Stock stock = stockDao.load(stockTemp.getShopCode(), stockTemp.getSkuCode()); // Stock信息取得

        Long all_Stock_EC = stock.getStockQuantity() + stockTemp.getAddStockEc(); // EC在库=EC在库+增量
        Long all_Stock_Tmall = stock.getStockTmall() + stockTemp.getAddStockTmall(); // TMALL在库=TMALL在库+增量
        Long all_stock_threshold = stock.getStockThreshold() + stockTemp.getAddStockThreshold(); // 安全在库数
        Long all_Stock = stock.getStockTotal() + stockTemp.getAddStockTotal(); // 总在库=总在库+增量

        // 库存安全值检查
        // 总在库小于0时
        if (all_Stock <= 0L) {
          all_Stock = 0L;
          all_Stock_EC = 0L;
          all_Stock_Tmall = 0L;
        } else {
          // EC在库
          if (all_Stock_EC < 0L) {
            all_Stock_EC = 0L;
          }
          // Tmall在库
          if (all_Stock_Tmall < 0L) {
            all_Stock_Tmall = 0L;
          }
        }

        CCommodityHeader cCH = this.getCCommodityheader(stockTemp.getShopCode(), stockTemp.getSkuCode());
        if (cCH.getTmallCommodityId() != null) {
          TmallManager manager = new TmallManager();
          TmallCommoditySku tcs = new TmallCommoditySku();
          tcs.setSkuId(cCH.getCommodityCode());
          tcs.setNumiid(NumUtil.toString(cCH.getTmallCommodityId()));
          TmallCommoditySku tmallSku = manager.getSku(tcs);
          if (tmallSku != null) {
            // TMALL库存数
            Long tmallQuantity = NumUtil.toLong(tmallSku.getQuantity());
            // EC系统上的TMALL库存数
            Long tmallECQuantity = stock.getStockTmall() - stock.getAllocatedTmall();
            if (tmallECQuantity <= 0L) {
              stockTemp.setAddStockTmall(0L); // 淘宝库存增量
            } else {
              stockTemp.setAddStockTmall(tmallECQuantity - tmallQuantity); // 淘宝库存增量
            }
          }
        }
        // 查询在库品区分bean
        Query query1 = new SimpleQuery(CatalogQuery.ON_STOCK_FLG_CHECK, stock.getSkuCode());
        StockTemp stFlgBean = DatabaseUtil.loadAsBean(query1, StockTemp.class);

        // 如果TMALL库存数 < TMALL引单数
        // TMALL库存不变
        boolean errEMail = false;
        if (stock.getStockTmall() < stock.getAllocatedTmall()) {
          errEMail = true;
        }
        // 如果EC库存数 < EC引单数
        // EC库存不变
        if (stock.getStockQuantity() < stock.getAllocatedQuantity()) {
          errEMail = true;
        }
        if (errEMail) {
          stockTemp.setAddStockTmall(0L); // 淘宝库存
          StringBuffer sb = new StringBuffer();
          if (stFlgBean.getStockFlg().equals("2")) {
            // String subject = "商品引当数大于库存数（非在库商品）";
            sb.append(stock.getCommodityCode());
            // sendEMailInfo(subject, sb.toString());
          } else {
            String subject = "商品引当数大于库存数";
            sb.append(stock.getCommodityCode() + "商品引当数大于库存数，请急速调查。");
            sendEMailInfo(subject, sb.toString());
          }
        }

        stock.setStockTotal(all_Stock); // 总在库
        stock.setStockThreshold(all_stock_threshold); // 安全在库数

        // 在库信息在调整(如果商品是为非在库品，则全部库存放入EC方向)
        if (stFlgBean.getStockFlg().equals("2")) {
          stock.setShareRecalcFlag(0L); // 在庫リーバランスフラグ
          stock.setShareRatio(100L); // EC库存比例
          stock.setStockTmall(0L); // TMALL在库赋值
          stock.setStockQuantity(all_Stock_Tmall + all_Stock_EC);
        } else {
          stock.setStockQuantity(all_Stock_EC); // EC在库
          stock.setStockTmall(all_Stock_Tmall); // Tmall在库
        }
        setUserStatus(stock);
        // 更新库存
        transaction.update(stock);
        // 根据SKu删除临时表
        transaction.executeUpdate(CatalogQuery.DELETE_STOCK_TEMP, stock.getShopCode(), stock.getSkuCode());
        /******** 库存更新及删除库存临时表结束 **********/
        boolean updateFlg = true;
        /******** 淘宝上传开始 **********/
        Long addTmallStock = stockTemp.getAddStockTmall(); // 淘宝库存增量
        Long oldStockEc = stock.getStockQuantity();
        Long oldStockTmall = stock.getStockTmall();

        if (addTmallStock != 0L) {
          // 取得组合商品集合
          Query tmallStockQuery = new SimpleQuery(CatalogQuery.GET_TMALL_STOCK_ALLOCATION, stock.getSkuCode());
          List<TmallStockAllocation> tStockBeanList = DatabaseUtil.loadAsBeanList(tmallStockQuery, TmallStockAllocation.class);
          if (tStockBeanList != null && tStockBeanList.size() > 0) {
            List<Long> nvList = new ArrayList<Long>();
            // 套装商品已存在可销售数
            Long allUseQuantity = catalogService.getUseSuitStock(stock.getCommodityCode());
            // 可销售库存数tmall
            Long hasTrueStockTmall = oldStockTmall - stock.getAllocatedTmall() - allUseQuantity;
            // 组合商品EC库存更新 和 组合商品上传淘宝 START
            for (TmallStockAllocation tStock : tStockBeanList) {
              CCommodityHeader cch = this.getCCommodityheader(tStock.getShopCode(), tStock.getCommodityCode());

              // tmall组合品（多包装）有效库存数= (tmall库存 - tmall引当) * 组合品库存比例 / 100
              Long useStockAmount = 0L;
              useStockAmount = hasTrueStockTmall * tStock.getScaleValue() / 100L;
              // 组合品（多包装）有效倍数（整数）
              Long multipleAmount = 0L;
              multipleAmount = useStockAmount / cch.getCombinationAmount();
              // 倍数不等于0时进行组合品（多包装）库存重分配
              if (multipleAmount != 0L) {
                // 组合品（多包装）有效库存数（整数）
                Long stockNum = multipleAmount * cch.getCombinationAmount();
                // 组合品增减量库存数
                Long addOrSubNum = stockNum - (tStock.getStockQuantity() - tStock.getAllocatedQuantity());
                // 更新组合品表(原有组合品（多包装）库存 + 增减量)
                tStock.setStockQuantity(tStock.getStockQuantity() + addOrSubNum);
                transaction.update(tStock);
                // 组合品（多包装）上传淘宝
                stock.setCommodityCode(tStock.getCommodityCode());
                stock.setSkuCode(tStock.getCommodityCode());
                stock.setStockTmall(addOrSubNum / cch.getCombinationAmount());
                // 增减量更新库存标志
                stock.setOneshotReservationLimit(2L);
                updateFlg = tmallSkuCodeUp(stock);
                nvList.add(stockNum);
              } else {
                // 组合品库存等于引当数
                tStock.setStockQuantity(tStock.getAllocatedQuantity());
                transaction.update(tStock);
                // 组合品（多包装）上传淘宝库存数设置成0
                stock.setCommodityCode(tStock.getCommodityCode());
                stock.setSkuCode(tStock.getCommodityCode());
                // 全量更新库存标志
                stock.setOneshotReservationLimit(1L);
                stock.setStockTmall(0L);
                updateFlg = tmallSkuCodeUp(stock);
              }
            }
            // 组合商品EC库存更新 和 组合商品上传淘宝 END

            // 减去被分配的组商品库存数（计算单包装的可销售数）
            for (Long nv : nvList) {
              hasTrueStockTmall = hasTrueStockTmall - nv;
            }
            // 组合品中的单包装上传淘宝
            stock.setCommodityCode(stockTemp.getSkuCode());
            stock.setSkuCode(stockTemp.getSkuCode());
            stock.setStockTmall(hasTrueStockTmall);
            // 全量更新库存标志
            stock.setOneshotReservationLimit(1L);
            updateFlg = tmallSkuCodeUp(stock);
          } else {
            // 设置淘宝api需要的库存增量（非组合品）
            stock.setStockTmall(addTmallStock);
            updateFlg = tmallSkuCodeUp(stock);
          }
        }

        if (updateFlg == true) {
          // 提交事务
          transaction.commit();
          logger.debug(stock.getSkuCode() + "更新库存成功。");
          logger.debug(stock.getSkuCode() + "删除临时库存成功。");
          sucessFlg = 0;
        } else {
          // 上传淘宝失败 ，TMall库存给EC start
          Long avaFlgNum = oldStockEc - stock.getAllocatedQuantity() + addTmallStock;
          if (avaFlgNum > 0) {
            // 第一次stock表更新结束
            transaction.commit();
            // 第二次stock表更新begin
            transaction.begin(getLoginInfo());
            stock = stockDao.load(stockTemp.getShopCode(), stockTemp.getSkuCode());
            stock.setStockTmall(oldStockTmall - addTmallStock);
            stock.setStockQuantity(oldStockEc + addTmallStock);
            transaction.update(stock);
            // 第二次stock表更新结束
            transaction.commit();
            logger.debug(stock.getCommodityCode() + "更新TMall库存失败。TMall库存增量返回EC");

          } else {// 减量大于EC有效库存时发警报邮件
            transaction.rollback();

            String subject = "【品店】上传淘宝失败警告。";
            StringBuffer sb = new StringBuffer();
            sb.append("以下商品上传淘宝失败警告:<BR><BR>");
            sb.append(stock.getCommodityCode() + "库存上传淘宝失败。");
            sb.append("减量大于EC商品有效库存。");
            sendEMailInfo(subject, sb.toString());
          }
          // 上传淘宝失败 ，TMall库存给EC end
          sucessFlg = 1;
        }

        // 套装商品对应
        if (compositionList != null && compositionList.size() > 0) {
          for (SetCommodityComposition composition : compositionList) {
            // 计算套装分配库存数
            TmallSuitCommodity suitCommodity = suitDao.load(composition.getCommodityCode());
            if (suitCommodity == null) {
              continue;
            }
            long scaleValue = suitCommodity.getScaleValue();
            long suitQuantity = 99999999;
            List<Stock> stockList = new ArrayList<Stock>();
            List<SetCommodityComposition> compositionDetailList = catalogService.getSetCommodityInfo("00000000", composition
                .getCommodityCode());

            for (SetCommodityComposition detail : compositionDetailList) {
              Stock detailStock = stockDao.load("00000000", detail.getChildCommodityCode());
              // 组合品分配库存
              Long stockZuhe = StockQuantityUtil.getOriginalTmallStockQUANTITY(detail.getChildCommodityCode());
              Long allUseQuantity = catalogService.getUseSuitStockButThis(detail.getChildCommodityCode(), suitCommodity
                  .getCommodityCode());
              if (suitQuantity > detailStock.getStockTmall() - detailStock.getAllocatedTmall() - allUseQuantity - stockZuhe) {
                suitQuantity = detailStock.getStockTmall() - detailStock.getAllocatedTmall() - allUseQuantity - stockZuhe;
              }
              detailStock.setStockTmall(detailStock.getStockTmall() - detailStock.getAllocatedTmall() - allUseQuantity - stockZuhe);
              stockList.add(detailStock);
            }
            // 套装品分配到的数量
            long stockNum = 0;

            stockNum = suitQuantity * scaleValue / 100;

            CCommodityHeader cheader = catalogService.getCCommodityheader("00000000", composition.getCommodityCode());
            if (cheader.getTmallCommodityId() == null) {
              continue;
            }

            // 库存上传淘宝
            boolean upTmallFlg = true;
            // 子商品
            for (Stock s : stockList) {
              transaction.begin(getLoginInfo());
              s.setOneshotReservationLimit(1L);
              s.setStockTmall(s.getStockTmall() - stockNum);
              upTmallFlg = catalogService.tmallSkuCodeUp(s);
              if (!upTmallFlg) {
                transaction.rollback();
                logger.debug(stock.getSkuCode() + "套装品明细商品更新库存失败。");
              } else {
                transaction.commit();
                logger.debug(stock.getSkuCode() + "套装品明细商品更新库存成功。");
                logger.debug(stock.getSkuCode() + "删除临时库存成功。");
              }
            }
            transaction.begin(getLoginInfo());
            Stock suitStock = stockDao.load("00000000", composition.getCommodityCode());
            suitStock.setOneshotReservationLimit(1L);
            suitStock.setStockTmall(stockNum);
            upTmallFlg = catalogService.tmallSkuCodeUp(suitStock);
            if (!upTmallFlg) {
              transaction.rollback();
              logger.debug(stock.getSkuCode() + "套装品更新库存失败。");
            } else {
              transaction.commit();
              logger.debug(stock.getSkuCode() + "更新库存成功。");
              logger.debug(stock.getSkuCode() + "删除临时库存成功。");
            }

            suitCommodity.setScaleValue(scaleValue);
            suitCommodity.setStockQuantity(stockNum);
            // 登録処理
            catalogService.insertTmallSuitCommodity(suitCommodity);

          }
        }
        /******** 淘宝上传结束 **********/
      }
    } catch (Exception e) {
      e.printStackTrace();
      transaction.rollback();
      sucessFlg = -1;
    } finally {
      if (transaction != null) {
        transaction.dispose();
      }
    }
    return sucessFlg;
  }

  private void sendEMailInfo(String subject, String textStr) {
    // 发送错误邮件
    MailInfo mailInfo = new MailInfo();
    mailInfo.setText(textStr);
    mailInfo.setSubject(subject);
    mailInfo.setSendDate(DateUtil.getSysdate());

    TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());
    mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
    String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
    String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
    MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    svc.sendImmediate(mailInfo);
  }

  /**
   * 淘宝上传
   * 
   * @param stock
   * @return True:为上传成功，False为上传失败
   */
  public boolean tmallSkuCodeUp(Stock stock) {
    try {
      Logger logger = Logger.getLogger(this.getClass());
      Query query = new SimpleQuery(CatalogQuery.CYNCHRO_TCOMMODITYID_QUERY, stock.getCommodityCode());
      CCommodityHeader header = DatabaseUtil.loadAsBean(query, CCommodityHeader.class);
      boolean skuUpFlg = true;
      // SKU商品在淘宝上是否销售
      query = new SimpleQuery(CatalogQuery.CYNCHRO_TCOMMODITYID_DETAIL_QUERY, stock.getSkuCode());
      CCommodityDetail cCommodityDetail = DatabaseUtil.loadAsBean(query, CCommodityDetail.class);
      // 淘宝贩卖FLG为空或者不贩卖时返回
      if (NumUtil.isNull(cCommodityDetail.getTmallUseFlg()) || cCommodityDetail.getTmallUseFlg().equals(0L)) {
        return true;
      }
      // sku没有上传到淘宝时，返回true
      if (NumUtil.isNull(cCommodityDetail.getTmallSkuId()) || cCommodityDetail.getTmallSkuId().equals("0L")) {
        // 商品同期时间及商品淘宝编号为空时
        if (NumUtil.isNull(header.getTmallCommodityId()) && StringUtil.isNullOrEmpty(header.getSyncUserTmall())) {
          return true;
        }
        skuUpFlg = false;
      }
      // 判断是否已经淘宝同期化了
      if (header != null && header.getTmallCommodityId() != null && header.getCommodityType() != CommodityType.GIFT.longValue()) {
        TmallManager manager = new TmallManager();
        TmallCommoditySku sku = new TmallCommoditySku();

        // sku所属商品型号ID
        sku.setNumiid(header.getTmallCommodityId().toString());

        // Sku的商家外部id
        if (!StringUtil.isNullOrEmpty(stock.getSkuCode())) {
          sku.setOuterId(stock.getSkuCode());
        }
        // 淘宝差分=临时放置在tmall的库存
        sku.setQuantity(NumUtil.toString(stock.getStockTmall()));

        // 更新差分时 1 代表全量更新 2代表增减量更新
        if (stock.getOneshotReservationLimit() != null && stock.getOneshotReservationLimit() == 1L) {
          sku.setUpdateType("1");
        } else {
          sku.setUpdateType("2");
        }

        // 更新淘宝的库存差分
        if (!StringUtil.isNullOrEmpty(manager.updateSkuStock(sku))) {
          logger.info(stock.getSkuCode() + "更新淘宝成功。");
          // 商品上架处理,不管有没有错误
          return true;
        } else {
          logger.info(stock.getSkuCode() + "更新淘宝失败。");
          return false;
        }
      } else {
        logger.info(stock.getSkuCode() + "未更新淘宝。");
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(stock.getCommodityCode());
    }
    return false;
  }

  /**
   * 调用商品上架接口
   */
  private boolean commodityTmallUp(String numiid) {
    try {
      Logger logger = Logger.getLogger(this.getClass());
      TmallManager manager = new TmallManager();
      TmallCommodityHeader commodity = new TmallCommodityHeader();
      WebshopConfig config = DIContainer.getWebshopConfig();
      // sku所属商品型号ID
      commodity.setNumiid(numiid);
      // 商品更新时间
      commodity.setListTime(DateUtil.addMinute(DateUtil.getSysdate(), config.getOnTmallMinute()));
      // 更新淘宝的库存差分
      if (!StringUtil.isNullOrEmpty(manager.insertOrUpdateCommodityHeader(commodity, "UPDATE"))) {
        logger.debug("商品编号：" + numiid + "更新淘宝成功。");
        return true;
      } else {
        logger.debug("商品编号：" + numiid + "更新淘宝失败。");
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 删除库存临时表
   */
  public void deleteStockTemp(String shop_Code, String sku_Code) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager transaction = DIContainer.getTransactionManager();
    try {
      transaction.begin(getLoginInfo());
      // 删除库存临时表信息
      transaction.executeUpdate(CatalogQuery.DELETE_STOCK_TEMP, shop_Code, sku_Code);
      transaction.commit();
      logger.debug("库存临时表删除成功。");
    } catch (RuntimeException e) {
      transaction.rollback();
      logger.debug("库存临时表删除失败。");
    } finally {
      transaction.dispose();
    }
  }

  /**
   * add by os014 2012-01-31 查询销售属性
   */
  @Override
  public List<TmallProperty> loadSkuPropertyByCategoryId(String categoryId) {
    TmallPropertyDao propertyDao = DIContainer.get("TmallPropertyDao");
    List<TmallProperty> propertys = propertyDao.loadSkuProByCategoryId(categoryId);
    return propertys;
  }

  // add by OS011 20120110 END

  /**
   * add by os014 2012-01-17 更新sku信息
   */
  @Override
  public void updateSku(CCommodityDetail detail) throws Exception {
    Logger logger = Logger.getLogger(this.getClass());
    setUserStatus(detail);
    CCommodityDetailDao dao = DIContainer.get("CCommodityDetailDao");
    // 设置shop_code
    if (detail.getShopCode() == null || "".equals(detail.getShopCode())) {
      detail.setShopCode("00000000");
    }
    try {
      dao.updateByQuery(CatalogQuery.UPDATE_DETAIL_BY_QUERY, new Object[] {
          detail.getSkuName(), detail.getSuggestePrice(), detail.getPurchasePrice(), detail.getUnitPrice(),
          detail.getTmallUnitPrice(), detail.getDiscountPrice(), detail.getTmallDiscountPrice(), detail.getStandardDetail1Name(),
          detail.getStandardDetail2Name(), detail.getWeight(), detail.getVolume(), detail.getVolumeUnit(),
          detail.getMinimumOrder(), detail.getMaximumOrder(), detail.getOrderMultiple(), detail.getStockWarning(),
          detail.getUpdatedUser(), detail.getUpdatedDatetime(), detail.getSkuCode(), detail.getCommodityCode(),
          detail.getShopCode()
      });
    } catch (Exception e) {
      logger.error(e);
      throw e;
    } finally {
    }

  }

  /**
   * add by os014 2012-01-17 查询cheader信息
   * 
   * @param shopCode
   *          : 店铺ID
   * @param commodityCode
   *          : 商品ID
   */
  @Override
  public CCommodityHeader getCCommodityheader(String shopCode, String commodityCode) {
    CCommodityHeaderDao dao = DIContainer.get("CCommodityHeaderDao");
    return dao.load(shopCode, commodityCode);
  }

  /**
   * planType : 企划类型（销售企划、特集企划） planDetailType : 企划明细区分（时间限定、期间限定）
   */
  public Plan getPlan(String planType, String planDetailType) {
    // Query query = new SimpleQuery(PlanQuery.GET_PLAN_QUERY, planType,
    // planDetailType);
    // Plan plan = DatabaseUtil.loadAsBean(query, Plan.class);
    // return plan;
    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(PlanQuery.GET_PLAN_QUERY, planType, planDetailType);
    Plan plan = cc.get(CacheKey.create(query), new CacheRetriever<Plan>() {

      public Plan retrieve() {
        return DatabaseUtil.loadAsBean(query, Plan.class);
      }
    });
    return plan;
  }

  public List<PlanInfo> getPlanCommodityInfoList(String planCode) {
    List<Planline> planInfoList = new ArrayList<Planline>();
    if (StringUtil.hasValueAllOf(planCode)) {
      // Query query = new SimpleQuery(PlanQuery.GET_PLAN_INFO_LIST_QUERY_B,
      // planCode);
      // planInfoList = DatabaseUtil.loadAsBeanList(query, Planline.class);
      CacheContainer cc = getCacheContainer();
      final Query query = new SimpleQuery(PlanQuery.GET_PLAN_INFO_LIST_QUERY_B, planCode);
      planInfoList = cc.get(CacheKey.create(query), new CacheRetriever<List<Planline>>() {

        public List<Planline> retrieve() {
          return DatabaseUtil.loadAsBeanList(query, Planline.class);
        }
      });
    }

    return getPlanInfoList(planInfoList);
  }

  public List<PlanInfo> getFeaturedCommodityInfoList(String planCode) {
    List<Planline> planInfoList = new ArrayList<Planline>();
    if (StringUtil.hasValueAllOf(planCode)) {
      // Query query = new SimpleQuery(PlanQuery.GET_FEATURE_INFO_LIST_QUERY,
      // planCode);
      // planInfoList = DatabaseUtil.loadAsBeanList(query, Planline.class);
      //在你的方法第一行加上:
      CacheContainer cc = getCacheContainer();
      final Query query = new SimpleQuery(PlanQuery.GET_FEATURE_INFO_LIST_QUERY, planCode);
      planInfoList = cc.get(CacheKey.create(query), new CacheRetriever<List<Planline>>() {

        public List<Planline> retrieve() {
          return DatabaseUtil.loadAsBeanList(query, Planline.class);
        }
      });
    }



    return getPlanInfoList(planInfoList);

  }

  private List<PlanInfo> getPlanInfoList(List<Planline> list) {
    List<PlanInfo> planInfoList = new ArrayList<PlanInfo>();
    for (Planline planline : list) {
      CommodityHeader commodityHeader = new CommodityHeader();
      CommodityDetail commodityDetail = new CommodityDetail();
      ReviewSummary reviewSummary = new ReviewSummary();
      convertPlanCommodity(planline, commodityHeader);
      convertPlanCommodity(planline, commodityDetail);
      convertPlanCommodity(planline, reviewSummary);
      PlanInfo planInfo = new PlanInfo();
      planInfo.setPlanCode(planline.getPlanCode());
      planInfo.setDetailType(planline.getDetailType());
      planInfo.setDetailCode(planline.getDetailCode());
      planInfo.setDetailName(planline.getDetailName());
      planInfo.setDetailNameEn(planline.getDetailNameEn());
      planInfo.setDetailNameJp(planline.getDetailNameJp());
      planInfo.setDetailUrl(planline.getDetailUrl());
      planInfo.setDetailUrlEn(planline.getDetailUrlEn());
      planInfo.setDetailUrlJp(planline.getDetailUrlJp());
      planInfo.setShowCommodityCount(planline.getShowCommodityCount());
      planInfo.setCommodityCode(planline.getCommodityCode());
      planInfo.setCommodityHeader(commodityHeader);
      planInfo.setCommodityDetail(commodityDetail);
      planInfo.setReviewSummary(reviewSummary);
      planInfo.setUseFlg(planline.getAvaQuantity());
      planInfo.setCampaignName(planline.getCampaignName());
      planInfoList.add(planInfo);
    }
    return planInfoList;
  }

  private void convertPlanCommodity(Planline planline, Object object) {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      Class<? extends Object> fromRowType = planline.getClass();
      Class<? extends Object> toRowType = object.getClass();

      Field[] fields = planline.getClass().getDeclaredFields();
      for (Field fi : fields) {
        Field[] dFields = object.getClass().getDeclaredFields();
        for (Field dfi : dFields) {
          if (fi.getName().equals(dfi.getName()) && !fi.getName().equals("serialVersionUID")) {
            StringBuilder builder = new StringBuilder();
            builder.append(dfi.getName().substring(0, 1).toUpperCase(Locale.getDefault()));
            builder.append(dfi.getName().substring(1));

            Class<? extends Object> returnType = fromRowType.getMethod("get" + builder.toString(), new Class[0]).getReturnType();
            Method getMethod = fromRowType.getMethod("get" + builder.toString(), new Class[0]);
            toRowType.getMethod("set" + builder.toString(), returnType).invoke(object, getMethod.invoke(planline, new Object[0]));
          }
          continue;
        }
      }
    } catch (IllegalAccessException e) {
      logger.error(e);
    } catch (NoSuchMethodException e) {
      logger.error(e);
    } catch (InvocationTargetException e) {
      logger.error(e);
    }
  }

  public List<PlanInfo> getPlanCommodityInfoList() {
    List<Planline> planInfoList = new ArrayList<Planline>();
    Query query = new SimpleQuery(PlanQuery.GET_PLAN_INFO_LIST_QUERY_FOR_INDEX);
    planInfoList = DatabaseUtil.loadAsBeanList(query, Planline.class);

    return getPlanInfoList(planInfoList);
  }

  /**
   * 查询属性值的名称 param categoryId:类目ID keys： 包含属性ID和属性值ID的 PropertyKey 对象集合
   */
  @Override
  public void findValueName(String categoryId, PropertyKeys keys) {
    TmallPropertyValueDao vDao = DIContainer.get(TmallPropertyValueDao.class.getSimpleName());
    for (int i = 0; i < keys.size(); i++) {
      PropertyKey key = keys.get(i);
      List<String> vidsList = key.getVids();
      for (int j = 0; j < vidsList.size(); j++) {
        if (!CategoryViewUtil.MANUAL_ID.equals(vidsList.get(j))) {
          TmallPropertyValue value = vDao.loadValue(categoryId, key.getPId(), vidsList.get(j));
          if (value != null) {
            key.getVNames().set(j, value.getValueName());
          } else {
            key.getVNames().set(j, "error");
          }

        }
      }
    }
  }

  public List<LeftMenuListBean> getLeftMenuInfo(String selected, String categoryCode, String brandCode, String reviewScore,
      String priceType, String priceStart, String priceEnd, String categoryAttribute1, String categoryAttribute2,
      String categoryAttribute3, String searchWord) {
    // LeftMenuInfoQuery query = new LeftMenuInfoQuery(selected,categoryCode,
    // brandCode, reviewScore, priceType, priceStart, priceEnd,
    // categoryAttribute1, categoryAttribute2, categoryAttribute3, searchWord);
    // return DatabaseUtil.loadAsBeanList(query, LeftMenuListBean.class);
    CacheContainer cc = getCacheContainer();
    final LeftMenuInfoQuery query = new LeftMenuInfoQuery(selected, categoryCode, brandCode, reviewScore, priceType, priceStart,
        priceEnd, categoryAttribute1, categoryAttribute2, categoryAttribute3, searchWord);
    List<LeftMenuListBean> result = cc.get(CacheKey.create(query), new CacheRetriever<List<LeftMenuListBean>>() {

      public List<LeftMenuListBean> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, LeftMenuListBean.class);
      }
    });
    return result;

  }

  /**
   * add by os014 2012-01-18 更新商品属性和属性值数据，对应表为：tmall_commodity_property
   * 
   * @param commodityId
   *          商品ID
   * @param shopCode
   *          店铺ID
   * @param keys
   *          属性与属性值集合对象
   * @return 更新结果
   */
  @Override
  public ServiceResult updateCommodityPropertys(String categoryId, String commodityId, String shopCode, PropertyKeys keys) {
    TransactionManager tmgr = DIContainer.getTransactionManager();
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    try {
      tmgr.begin(getLoginInfo());
      /**
       * 删除以前的属性
       */
      tmgr.executeUpdate(CatalogQuery.DELETE_COMMODITY_PROPERTY, commodityId);
      /**
       * 插入属性与属性值
       */
      for (int i = 0; i < keys.size(); i++) {
        PropertyKey key = keys.get(i);
        for (int j = 0; j < key.getVids().size(); j++) {
          TmallCommodityProperty value = new TmallCommodityProperty();
          setUserStatus(value);
          value.setCommodityCode(commodityId);
          value.setPropertyId(key.getPId());
          value.setValueId(key.getVids().get(j));
          value.setValueText(key.getVNames().get(j));

          TmallPropertyValueDao valueDao = DIContainer.getDao(TmallPropertyValueDao.class);
          TmallPropertyValue pValue = valueDao.loadValueByName(categoryId, value.getPropertyId(), value.getValueText());

          // 如果属性是手动输入的,设置属性Id为tmall_property_value_seq
          // 格式为 INPUT+'_'+序列的值 如: INPUT_123456
          if (CategoryViewUtil.MANUAL_ID.equals(key.getVids().get(j))) {

            // 如果手动输入的属性值在表中已存在 将valueId设置到value对象
            if (pValue != null) {
              value.setValueId(pValue.getValueId());
            } else {
              Long seqLong = DatabaseUtil.generateSequence(SequenceType.TMALL_PROPERTY_VALUE_NO);
              value.setValueId(CategoryViewUtil.MANUAL_ID + "_" + NumUtil.toString(seqLong));
            }

          }
          tmgr.insert(value);
          // 如果属性值是手动输入的需要将属性值插入到tmall_property_value表
          if (CategoryViewUtil.MANUAL_ID.equals(key.getVids().get(j))) {
            TmallPropertyValue propertyValue = new TmallPropertyValue();
            propertyValue.setCategoryId(categoryId);
            propertyValue.setPropertyId(value.getPropertyId());
            propertyValue.setValueId(value.getValueId());
            propertyValue.setValueName(value.getValueText());
            setUserStatus(propertyValue);
            propertyValue.setAliasName(value.getValueText());
            propertyValue.setDeleteFlag(0l);
            /**
             * 判断手动输入的记录在tmall_property_value表中是否以存在 不存在时需要将记录插入到属性值表
             */
            if (pValue == null) {
              tmgr.insert(propertyValue);
            }
          }
        }
      }
      tmgr.commit();
    } catch (Exception e) {
      logger.error(e);
      tmgr.rollback();
      e.printStackTrace();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      tmgr.dispose();
    }
    return result;
  }

  /**
   * ショップコード,商品コードに関連付いている商品詳細の指定したタイプの価格の値を一括更新します。
   * <p>
   * <dl>
   * add by os014 2012-01-19
   * <dt><b>処理概要: </b></dt>
   * <dd>ショップコード,商品コードに関連付いている商品詳細の指定したタイプの価格の値を一括更新します。
   * <ol>
   * <li>引数で受け取った価格に対して、Validationチェックを行います。</li>
   * <li>引数で受け取ったショップコードと商品コードをもとに、商品詳細のリストを取得します。</li>
   * <li>以降の処理を、商品詳細のリストの数だけ繰り返します。</li>
   * <li>商品詳細のリストから1件取得します。</li>
   * <li>引数で受け取ったタイプが商品単価の場合は、引数で受け取った価格を、1件取得した商品詳細の商品単価にセットします。<br />
   * 引数で受け取ったタイプが特別価格の場合は、引数で受け取った価格を、1件取得した商品詳細の特別価格にセットします。<br />
   * 引数で受け取ったタイプが予約価格の場合は、引数で受け取った価格を、1件取得した商品詳細の予約価格にセットします。<br />
   * 引数で受け取ったタイプが改定価格の場合は、引数で受け取った価格を、1件取得した商品詳細の改定価格にセットします。<br />
   * </li>
   * <li>1件取得した商品詳細で、商品詳細を更新します。</li>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>shopCodeがnullでないこと</dd>
   * <dd>commodityCodeがnullでないこと</dd>
   * <dd>typeがnullでないこと</dd>
   * <dd>priceがnullでないこと</dd>
   * <dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>引数で受け取ったタイプに応じて、商品単価?特別価格?予約価格?改定価格を更新します。</dd>
   * <dd>※このメソッドは排他制御を考慮していません。これより前の変更を上書きします。</dd>
   * </dl>
   * </p>
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param type
   *          更新対象となる価格タイプ
   * @param price
   *          価格
   * @return サービスの処理結果を返します。
   */
  @Override
  public ServiceResult updateCcommodityPriceAll(String shopCode, String commodityCode, CommodityPriceType type, BigDecimal price) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    CurrencyValidator validator = new CurrencyValidator();
    if (!validator.isValid(price)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    CCommodityHeaderDao headerDao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityHeader header = headerDao.load(shopCode, commodityCode);
    if (header == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    List<CCommodityDetail> detailList = getCCommoditySku(shopCode, commodityCode);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      for (CCommodityDetail detail : detailList) {
        switch (type) {
          case UNIT_PRICE:
            detail.setUnitPrice(price);
            detail.setTmallUnitPrice(price);
            break;
          case DISCOUNT_PRICE:
            detail.setDiscountPrice(price);
            detail.setTmallDiscountPrice(price);
            break;
          case SUGGESTE_PRICE:
            detail.setSuggestePrice(price);
            break;
          case PURCHASE_PRICE:
            detail.setPurchasePrice(price);
            break;
          default:
            throw new RuntimeException();
        }
        setUserStatus(detail);
        txMgr.update(detail);
      }
      // 如果修改的是单价，需要修改商品header表的代表sku单价
      if (CommodityPriceType.UNIT_PRICE == type) {
        header.setRepresentSkuUnitPrice(price);
        setUserStatus(header);
        txMgr.update(header);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      logger.error(e);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  public List<CodeAttribute> getParamPath(String categoryCode, String brandCode, String reviewScore, String priceType,
      String priceStart, String priceEnd, String categoryAttribute1, String categoryAttribute2, String categoryAttribute3,
      String searchWord) {
    List<CodeAttribute> paramPath = new ArrayList<CodeAttribute>();
    // 20120529 tuxinwei add start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // 20120529 tuxinwei add end
    if (StringUtil.hasValue(searchWord)) {
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        CodeAttribute nameValue = new NameValue(
            Messages.getString("service.impl.CatalogServiceImpl.4") + " \"" + searchWord + "\"", "/category/searchWord_"
                + searchWord);
        paramPath.add(nameValue);
      } else {
        CodeAttribute nameValue = new NameValue(searchWord + Messages.getString("service.impl.CatalogServiceImpl.4"),
            "/category/searchWord_" + searchWord);
        paramPath.add(nameValue);
      }
    }
    if (StringUtil.hasValue(categoryCode)) {
      CategoryDao dao = DIContainer.getDao(CategoryDao.class);
      Category category = dao.load(categoryCode);
      if (category != null && category.getDepth() != 0) {
        CodeAttribute nameValue = new NameValue(utilService.getNameByLanguage(category.getCategoryNamePc(), category
            .getCategoryNamePcEn(), category.getCategoryNamePcJp()), "/category/C" + category.getCategoryCode());
        paramPath.add(nameValue);
      }
    }
    if (StringUtil.hasValue(brandCode)) {
      BrandDao bDao = DIContainer.getDao(BrandDao.class);
      List<Brand> brandList = bDao.findByQuery(CatalogQuery.GET_BRAND, brandCode);
      Brand brand = null;
      if (brandList.size() > 0) {
        brand = brandList.get(0);
      }
      if (brand != null) {
        // 20120529 tuxinwei add start
        CodeAttribute nameValue = new NameValue(utilService.getNameByLanguage(brand.getBrandName(), brand.getBrandEnglishName(),
            brand.getBrandJapaneseName()), "/category/B" + brand.getBrandCode());
        // 20120529 tuxinwei add end
        paramPath.add(nameValue);
      }
    }
    if (StringUtil.hasValue(reviewScore)) {
      if (reviewScore.equals(ReviewScore.FOUR_STARS.getValue())) {
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.5"), "/category/K"
            + reviewScore);
        paramPath.add(nameValue);
      } else if (reviewScore.equals(ReviewScore.THREE_STARS.getValue())) {
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.6"), "/category/K"
            + reviewScore);
        paramPath.add(nameValue);
      } else if (reviewScore.equals(ReviewScore.TWO_STARS.getValue())) {
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.7"), "/category/K"
            + reviewScore);
        paramPath.add(nameValue);
      } else if (reviewScore.equals(ReviewScore.ONE_STAR.getValue())) {
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.8"), "/category/K"
            + reviewScore);
        paramPath.add(nameValue);
      }
    }
    if (StringUtil.hasValue(priceType)) {
      if (priceType.equals(PriceList.ONE.getValue())) {
        String[] prices = PriceList.ONE.getName().split(",");
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + prices[0] + "～"
            + prices[1] + Messages.getString("service.impl.CatalogServiceImpl.10"), "/category/D" + priceType);
        paramPath.add(nameValue);
      } else if (priceType.equals(PriceList.TWO.getValue())) {
        String[] prices = PriceList.TWO.getName().split(",");
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + prices[0] + "～"
            + prices[1] + Messages.getString("service.impl.CatalogServiceImpl.10"), "/category/D" + priceType);
        paramPath.add(nameValue);
      } else if (priceType.equals(PriceList.THREE.getValue())) {
        String[] prices = PriceList.THREE.getName().split(",");
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + prices[0] + "～"
            + prices[1] + Messages.getString("service.impl.CatalogServiceImpl.10"), "/category/D" + priceType);
        paramPath.add(nameValue);
      } else if (priceType.equals(PriceList.FOUR.getValue())) {
        String[] prices = PriceList.FOUR.getName().split(",");
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + prices[0] + "～"
            + prices[1] + Messages.getString("service.impl.CatalogServiceImpl.10"), "/category/D" + priceType);
        paramPath.add(nameValue);
      } else if (priceType.equals(PriceList.FIVE.getValue())) {
        String[] prices = PriceList.FIVE.getName().split(",");
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + prices[0]
            + Messages.getString("service.impl.CatalogServiceImpl.11"), "/category/D" + priceType);
        paramPath.add(nameValue);
      }
    } else {
      if (StringUtil.hasValueAllOf(priceStart, priceEnd)) {
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + priceStart + "～"
            + priceEnd + Messages.getString("service.impl.CatalogServiceImpl.10"), "/category/N" + priceStart + "~" + priceEnd);
        paramPath.add(nameValue);
      } else if (StringUtil.hasValue(priceStart)) {
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + priceStart + "～  "
            + Messages.getString("service.impl.CatalogServiceImpl.10"), "/category/N" + priceStart + "~");
        paramPath.add(nameValue);
      } else if (StringUtil.hasValue(priceEnd)) {
        CodeAttribute nameValue = new NameValue(Messages.getString("service.impl.CatalogServiceImpl.9") + "～" + priceEnd
            + Messages.getString("service.impl.CatalogServiceImpl.10"), "/category/N" + "~" + priceEnd);
        paramPath.add(nameValue);
      }
    }
    if (StringUtil.hasValue(categoryAttribute1)) {
      CodeAttribute nameValue = new NameValue(categoryAttribute1, "/category/C" + categoryCode + "-T1" + categoryAttribute1);
      paramPath.add(nameValue);
    }
    if (StringUtil.hasValue(categoryAttribute2)) {
      CodeAttribute nameValue = new NameValue(categoryAttribute2, "/category/C" + categoryCode + "-T2" + categoryAttribute2);
      paramPath.add(nameValue);
    }
    if (StringUtil.hasValue(categoryAttribute3)) {
      CodeAttribute nameValue = new NameValue(categoryAttribute3, "/category/C" + categoryCode + "-T3" + categoryAttribute3);
      paramPath.add(nameValue);
    }
    return paramPath;
  }

  private List<CodeAttribute> getBrandPath(Category activeCategory, Brand brand) {

    // activeCategoryがnullか0階層（ルートカテゴリ）だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0 || brand == null) {
      return Collections.emptyList();
    }

    // パンくずリストに設定するカテゴリの設定

    String[] splitPath = activeCategory.getPath().split(CatalogQuery.CATEGORY_DELIMITER);
    Object[] categoryCodes = new Object[1];
    if (splitPath.length > 2) {
      categoryCodes[0] = splitPath[2];
    } else {
      categoryCodes[0] = activeCategory.getCategoryCode();
    }

    // IN検索文の生成
    SqlDialect dialect = SqlDialect.getDefault();
    SqlFragment fragment = dialect.createInClause("CATEGORY_CODE", categoryCodes);
    String inSql = fragment.getFragment();

    // SQLの実行

    Query query = new SimpleQuery(CatalogQuery.getTopicPathList(inSql), fragment.getParameters());
    List<Category> categoryList = DatabaseUtil.loadAsBeanList(query, Category.class);

    // 20120529 tuxinwei add start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // 20120529 tuxinwei add end

    // パンくずリストの生成
    List<CodeAttribute> brandPath = new ArrayList<CodeAttribute>();
    for (Category category : categoryList) {
      // 20120529 tuxinwei add start
      CodeAttribute nameValue = new NameValue(utilService.getNameByLanguage(category.getCategoryNamePc(), category
          .getCategoryNamePcEn(), category.getCategoryNamePcJp()), "/category/C" + category.getCategoryCode());
      // 20120529 tuxinwei add end
      brandPath.add(nameValue);
    }
    // 20120529 tuxinwei add start
    CodeAttribute nameValue = new NameValue(utilService.getNameByLanguage(brand.getBrandName(), brand.getBrandEnglishName(), brand
        .getBrandJapaneseName()), "/category/B" + brand.getBrandCode());
    // 20120529 tuxinwei add end
    brandPath.add(nameValue);
    return brandPath;
  }

  public List<CodeAttribute> getBrandPath(String categoryCode, String brandCode) {

    // categoryCodeが空の場合、空のパンくずリストを返す

    if (StringUtil.isNullOrEmpty(categoryCode) || StringUtil.isNullOrEmpty(brandCode)) {
      return Collections.emptyList();
    }

    // categoryCodeを主キーとするカテゴリの詳細情報を取得

    CategoryDao dao = DIContainer.getDao(CategoryDao.class);
    Category activeCategory = dao.load(categoryCode);

    // categoryCodeが不正、あるいは取得したカテゴリが0階層だった場合、空のパンくずリストを返す

    if (activeCategory == null || activeCategory.getDepth() == 0) {
      return Collections.emptyList();
    }

    BrandDao bDao = DIContainer.getDao(BrandDao.class);
    List<Brand> brandList = bDao.findByQuery(CatalogQuery.GET_BRAND, brandCode);
    Brand brand = null;
    if (brandList.size() > 0) {
      brand = brandList.get(0);
    }
    if (brand == null) {
      return Collections.emptyList();
    }

    // 取得したカテゴリ情報から生成されるパンくずリストを返す

    return getBrandPath(activeCategory, brand);
  }

  public TmallBrand getTmallBrand(String tmallBrandCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_TMALL_BRAND, tmallBrandCode);
    return DatabaseUtil.loadAsBean(query, TmallBrand.class);
  }

  /**
   * add by os014 2012-01-31 更新c_commodity_header 作用于修改产品属性
   * 
   * @param header
   * @return
   */
  @Override
  public ServiceResult updateCheaderStandardDetailInfo(CCommodityHeader header) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallPropertyDao propertyDao = DIContainer.getDao(TmallPropertyDao.class);
    CCommodityHeaderDao headerDao = DIContainer.getDao(CCommodityHeaderDao.class);

    CCommodityHeader versionObj = headerDao.load(header.getShopCode(), header.getCommodityCode());

    // 商品ヘッダの存在チェック
    if (versionObj == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    /**
     * 查询属性名称1 组装header对象
     */
    if (StringUtil.hasValue(header.getStandard1Id())) {
      TmallProperty pro = propertyDao.load(NumUtil.toString(header.getTmallCategoryId()), header.getStandard1Id());
      if (pro != null) {
        versionObj.setStandard1Name(pro.getPropertyName());
        versionObj.setStandard1Id(pro.getPropertyId());
      } else {
        versionObj.setStandard1Id(null);
        versionObj.setStandard1Name(null);
      }
    } else {
      versionObj.setStandard1Id(null);
      versionObj.setStandard1Name(null);
    }
    /**
     * 查询属性名称2 组装header对象
     */
    if (StringUtil.hasValue(header.getStandard2Id())) {
      TmallProperty pro = propertyDao.load(NumUtil.toString(header.getTmallCategoryId()), header.getStandard2Id());
      if (pro != null) {
        versionObj.setStandard2Name(pro.getPropertyName());
        versionObj.setStandard2Id(pro.getPropertyId());
      } else {
        versionObj.setStandard2Id(null);
        versionObj.setStandard2Name(null);
      }
    } else {
      versionObj.setStandard2Id(null);
      versionObj.setStandard2Name(null);
    }
    // 同期标志
    versionObj.setSyncFlagEc(header.getSyncFlagEc());
    versionObj.setSyncFlagTmall(header.getSyncFlagTmall());

    setUserStatus(versionObj);
    try {
      headerDao.update(versionObj);
    } catch (Exception e) {
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }

    return serviceResult;
  }

  @Override
  public ServiceResult deleteCCommoditySku(String shopCode, String skuCode) {
    ServiceResultImpl result = new ServiceResultImpl();

    // ショップコード、SKUコードのnullチェック
    if (!StringUtil.hasValueAllOf(shopCode, skuCode)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    // 指定された商品SKUが削除可能かチェック
    if (!isDeletableCommoditySku(shopCode, skuCode)) {
      // 商品削除エラーを返す
      result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
      return result;
    }

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      for (String deleteQuery : CommodityDeleteQuery.getCcSkuDeleteQuery()) {
        txMgr.executeUpdate(deleteQuery, shopCode, skuCode);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
    } finally {
      txMgr.dispose();
    }

    return result;
  }

  // add by lc 2012-02-01 start
  /*
   * (non-Javadoc)
   * @see
   * jp.co.sint.webshop.service.CatalogService#getFavoriteCommodity(java.lang
   * .String, java.lang.String, java.lang.String)
   */
  @Override
  public FavoriteCommodity getFavoriteCommodity(String customerCode, String shopCode, String skuCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_FAVORITECOMMODITY_QUERY, customerCode, shopCode, skuCode);
    FavoriteCommodity favoriteCommodity = DatabaseUtil.loadAsBean(query, FavoriteCommodity.class);
    return favoriteCommodity;
  }

  // add by lc 2012-02-01 end

  // add by ob 20120206 start

  public ServiceResult uploadImgBatch(String shopCode, List<String> errMsgList) {
    ServiceResultImpl result = new ServiceResultImpl();
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    if (null == config) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    boolean hadErr = false;
    boolean uploadTmallFail = false;
    boolean uploadECFail = false;
    // 2014/05/07 京东WBS对应 ob_姚 add start
    boolean uploadJdFail = false;
    // 2014/05/07 京东WBS对应 ob_姚 add end

    // 将tmall使用的并在tmall上存在的图片copy到原图片文件路径
    List<File> tmallDisabledFileList = new ArrayList<File>();
    FileUtil.findFilesByFileType(config.getTmallDisabledImgPath(), "jpg", tmallDisabledFileList);
    if (tmallDisabledFileList.size() > 0) {
      hadErr = moveTmallDisabledToOrg(tmallDisabledFileList, errMsgList, config, shopCode);
      if (!hadErr) {
        return result;
      }
    }

    // 2014/05/07 京东WBS对应 ob_姚 add start
    // 将JD使用的并在JD上存在的图片copy到原图片文件路径
    List<File> jdDisabledFileList = new ArrayList<File>();
    FileUtil.findFilesByFileType(config.getJdDisabledImgPath(), "jpg", jdDisabledFileList);
    if (jdDisabledFileList.size() > 0) {
      hadErr = moveJdDisabledToOrg(jdDisabledFileList, errMsgList, config, shopCode);
      if (!hadErr) {
        return result;
      }
    }
    // 2014/05/07 京东WBS对应 ob_姚 add end

    // 取得待处理文件夹下的所有文件
    List<File> orgFileList = new ArrayList<File>();
    FileUtil.findFilesByFileType(config.getOrgImgPath(), "jpg", orgFileList);

    if (orgFileList.size() < 1) {
      errMsgList.add("没有要处理的图片文件。");
      return result;
    }

    // 循环处理待处理文件夹下的所有图片
    for (File imgFile : orgFileList) {
      // 将源文件夹下的所有jpg移动到待处理文件夹下
      if (hadErr = !moveFile2UnOperFile(config, shopCode, errMsgList, imgFile.getName())) {
        continue;
      }

      // 将所有待处理的jpg进行resize
      if (hadErr = !moveFile2ResizeFlie(config, shopCode, errMsgList, imgFile.getName())) {
        continue;
      }

      // 将resize成功的文件上传到官网并将上传成功的图片从resize文件夹移动到upload文件夹下
      hadErr = !uploadFiles(config, shopCode, errMsgList, imgFile.getName());
      uploadECFail = hadErr;

      // 将resize成功的文件上传到淘宝
      hadErr = !uploadFiles2Tmall(config, shopCode, errMsgList, imgFile.getName());
      uploadTmallFail = hadErr;

      // 2014/05/07 京东WBS对应 ob_姚 add start
      // 将resize成功的文件上传到京东
      hadErr = !uploadFiles2Jd(config, shopCode, errMsgList, imgFile.getName());
      uploadJdFail = hadErr;
      // 2014/05/07 京东WBS对应 ob_姚 add end

      if (!uploadECFail) {
        // 获取resize目录图片集合
        List<File> unResizeFileList = new ArrayList<File>();
        int nReturn = FileUtil.findFilesByFileType(config.getResizeImgPath(), "jpg", unResizeFileList);

        if (nReturn != 1) {
          uploadECFail = true;
        }
        // 删除resize目录中图片
        for (File unResizeFile : unResizeFileList) {
          unResizeFile.delete();
        }
      }

      // 2014/05/07 京东WBS对应 ob_姚 update start
      // if (uploadTmallFail || uploadECFail) {
      if (uploadTmallFail || uploadECFail || uploadJdFail) {
        // 2014/05/07 京东WBS对应 ob_姚 update end

        continue;
      }

      // 删除orgImg中原始图片
      String skuCode = getSkuFromFileName(imgFile.getName());
      ImageUploadHistory dto = getImageUploadHistory(shopCode, skuCode, imgFile.getName());
      if (dto != null && (dto.getEc1UploadFlg() == 0 || dto.getEc2UploadFlg() == 0)) {
        errMsgList.add("官网图片上传失败，图片暂时保留在原始目录中。");
      } else {
        imgFile.delete();
      }
    }
    if (hadErr) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return result;
  }

  public ServiceResult uploadImgBatchForCampaign(String shopCode, List<String> errMsgList) {
    ServiceResultImpl result = new ServiceResultImpl();
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    if (null == config) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    boolean hadErr = false;

    // 取得待处理文件夹下的所有文件
    List<File> campFileList = new ArrayList<File>();
    FileUtil.findFilesByFileType(config.getCampImgPath(), "jpg", campFileList);

    if (campFileList.size() < 1) {
      errMsgList.add("没有要处理的活动图片文件。");
      return result;
    }

    // 循环处理待处理文件夹下的所有图片
    for (File imgFile : campFileList) {

      // 将活动商品图片上传到淘宝
      hadErr = !uploadCampFiles2Tmall(config, shopCode, errMsgList, imgFile.getName());

      if (hadErr) {
        continue;
      }

      imgFile.delete();
    }

    if (hadErr) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }

    return result;
  }

  // 将源文件夹下的所有jpg移动到待处理文件夹下
  private boolean moveFile2UnOperFile(ImageUploadConfig config, String shopCode, List<String> errMsgList, String FileName) {
    if (null == config || StringUtil.isNullOrEmptyAnyOf(shopCode, FileName) || null == errMsgList) {
      return false;
    }

    int nResult = checkFiles(config.getOrgImgPath(), config.getUnOperImgPath());
    if (2 == nResult) {
      return false;
    }
    if (0 == nResult) {
      errMsgList.add(",没有要处理的图片");
    }

    CommodityDetailDao commDetailDao = DIContainer.getDao(CommodityDetailDao.class);
    // 判断商品SKU是否存在
    String skuCode = getSkuFromFileName(FileName);
    CommodityDetail comDetail = commDetailDao.load(shopCode, skuCode);

    if (null == comDetail) {
      errMsgList.add(FileName + ",商品不存在");
      return false;
    }

    String commodityCode = comDetail.getCommodityCode();

    ImageUploadHistory dto = new ImageUploadHistory();
    // 20130906 txw update start
    ImageUploadHistoryDao dao = DIContainer.getDao(ImageUploadHistoryDao.class);
    ImageUploadHistory orgDto = getImageUploadHistory(shopCode, skuCode, FileName);

    // 如果已经存在数据,更新它,否则插入新数据
    if (orgDto != null) {
      dto = orgDto;
      dto.setLocalOperFlg(0L);
      // dto.setTmallImgId("");
      // dto.setTmallImgUrl("");
      dto.setTmallUploadFlg(0L);
      dto.setEc1UploadFlg(0L);
      dto.setEc2UploadFlg(0L);
      // 2014/05/11 京东WBS对应 ob_卢 add start
      dto.setJdUploadFlg(0L);
      // 2014/05/11 京东WBS对应 ob_卢 add end
      dto.setCommodityCode(commodityCode);
    } else {
      dto.setShopCode(shopCode);
      dto.setSkuCode(skuCode);
      dto.setCommodityCode(commodityCode);
      dto.setUploadCommodityImg(FileName);
    }
    // 将文件从源始图片文件夹移动到待处理文件夹下
    nResult = FileUtil.copyFile(config.getOrgImgPath() + "/" + FileName, config.getUnOperImgPath() + "/" + FileName);

    if (nResult != 1) {
      errMsgList.add(FileName + ",可能正在被使用");
      return false;
    }

    if (orgDto != null) {
      dao.update(dto, getLoginInfo());
    } else {
      dao.insert(dto, getLoginInfo());
    }
    // 20130906 txw update end

    return true;
  }

  // 将所有待处理的jpg进行resize
  private boolean moveFile2ResizeFlie(ImageUploadConfig config, String shopCode, List<String> errMsgList, String orgFileName) {
    if (null == config || StringUtil.isNullOrEmptyAnyOf(shopCode, orgFileName) || null == errMsgList) {
      return false;
    }

    // 取得修改sku图片尺寸的参数列表
    List<String> skuImgList = config.getSkuImgList();
    if (skuImgList.size() < 1) {
      errMsgList.add(",没有配置相关信息");
      return false;
    }

    int nResult = checkFiles(config.getUnOperImgPath(), config.getResizeImgPath());
    if (2 == nResult) {
      return false;
    }
    if (0 == nResult) {
      return false;
    }

    for (String tmp : skuImgList) {
      String[] skuImgInfo = tmp.split(",");
      if (skuImgInfo.length < 3) {
        errMsgList.add(",参数配置错误");
        continue;
      }

      // 取得待上传文件夹下所有jpg文件的文件名
      String sku = getSkuFromFileName(orgFileName);
      // 查询sku是否在表中存在
      // 20130906 txw update start
      ImageUploadHistory dto = getImageUploadHistory(shopCode, sku, orgFileName);
      ImageUploadHistoryDao dao = DIContainer.getDao(ImageUploadHistoryDao.class);
      if (dto == null) {
        errMsgList.add(skuImgInfo[0] + ",没有对应的图片");
        break;
      }

      String fix = "";
      if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg1Fix())) {
        fix = config.getAssistImg1Fix();
      } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg2Fix())) {
        fix = config.getAssistImg2Fix();
      } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg3Fix())) {
        fix = config.getAssistImg3Fix();
      } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg4Fix())) {
        fix = config.getAssistImg4Fix();
      }

      String desFileName = sku + fix + skuImgInfo[0] + ".jpg";

      int width = Integer.parseInt(skuImgInfo[1]);
      int height = Integer.parseInt(skuImgInfo[2]);

      nResult = resizeFile(config.getUnOperImgPath(), orgFileName, config.getResizeImgPath(), desFileName, width, height);

      // 查询sku是否为代表sku,如果是,还要处理商品图片
      CommodityHeaderDao commHeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
      boolean isRepSku = commHeaderDao.isRepresentSkuCode(shopCode, sku);

      if (isRepSku) {
        // 如果是代表SKU,通过商品编号resize商品图片
        CommodityHeader ch = commHeaderDao.loadByRepSku(shopCode, sku);

        desFileName = ch.getCommodityCode() + fix + skuImgInfo[0] + ".jpg";

        nResult = resizeFile(config.getUnOperImgPath(), orgFileName, config.getResizeImgPath(), desFileName, width, height);
      }

      // 先假设成功了处理成功，如果失败了，会把它再置回0
      dto.setLocalOperFlg(1L);
      if (nResult != 1) {
        errMsgList.add(orgFileName + ",可能正在被使用");
        dto.setLocalOperFlg(0L);
      }

      // 更新数据
      dao.update(dto, getLoginInfo());
    }

    // 获得工作目录unOperImg中文件集合
    List<File> unOperFileList = new ArrayList<File>();
    int nReturn = FileUtil.findFilesByFileType(config.getUnOperImgPath(), "jpg", unOperFileList);

    if (nReturn != 1) {
      return false;
    }

    // 删除工作目录unOperImg中文件
    for (File unOperFile : unOperFileList) {
      for (int j = 0; j < 3; j++) {
        if (unOperFile.renameTo(unOperFile)) {
          break;
        } else {
          System.gc();
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          continue;
        }
      }
      unOperFile.delete();
    }

    return true;
  }

  private boolean uploadFiles(ImageUploadConfig config, String shopCode, List<String> errMsgList, String orgFileName) {
    if (null == config || StringUtil.isNullOrEmptyAnyOf(shopCode, orgFileName) || null == errMsgList) {
      return false;
    }

    List<String> ftpInfoList = config.getFtpInfoList();
    if (ftpInfoList.size() < 1) {
      errMsgList.add(",ftp信息没有配置");
      return true;
    }

    // 目前只支持上传两个官网
    int index = 0;
    for (String tmp : ftpInfoList) {
      if (index > 1) {
        index++;
        continue;
      }
      // 切分ftp配置信息
      String[] ftpInfo = tmp.split(",");
      if (ftpInfo.length < 6) {
        errMsgList.add(",ftp信息配置错误");
        continue;
      }
      // 解析配置信息
      String serviceName = ftpInfo[0]; // 服务器地址
      int port = 0; // 端口号
      if (!StringUtil.isNullOrEmpty(ftpInfo[1])) {
        port = Integer.parseInt(ftpInfo[1]);
      }
      int connMode = Integer.parseInt(ftpInfo[2]);
      String username = ftpInfo[3]; // 用户名
      String password = ftpInfo[4]; // 地址
      // 20130906 txw update start
      String desFilePath1 = ftpInfo[5]; // 存放路径(主图)
      String desFilePath2 = ftpInfo[6]; // 存放路径(辅图)
      String sku = getSkuFromFileName(orgFileName);

      // 通过sku查询ImageUploadHistory dto
      ImageUploadHistoryDao dao = DIContainer.getDao(ImageUploadHistoryDao.class);
      ImageUploadHistory dto = getImageUploadHistory(shopCode, sku, orgFileName);
      // 如果已经存在数据,更新它
      if (null == dto) {
        errMsgList.add(orgFileName + ",商品图片不存在");
        return false;
      }
      if (0 == index) {
        dto.setEc1UploadFlg(1L);
      }
      if (1 == index) {
        dto.setEc2UploadFlg(1L);
      }

      List<File> orgFileList = new ArrayList<File>();
      int nReturn = FileUtil.findFilesByFileName(config.getResizeImgPath(), dto.getUploadCommodityImg().replaceAll(".jpg", ""),
          orgFileList);
      if (0 == index && orgFileList.size() > 0) {
        dto.setEc1UploadFlg(1L);
      }
      if (1 == index && orgFileList.size() > 0) {
        dto.setEc2UploadFlg(1L);
      }

      String fix = "";
      if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg1Fix())) {
        fix = config.getAssistImg1Fix();
      } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg2Fix())) {
        fix = config.getAssistImg2Fix();
      } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg3Fix())) {
        fix = config.getAssistImg3Fix();
      } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg4Fix())) {
        fix = config.getAssistImg4Fix();
      }

      for (File orgFile : orgFileList) {
        String orgFilePath = config.getResizeImgPath() + "/" + orgFile.getName();
        if (StringUtil.hasValue(fix)) {
          boolean flag = CommodityDescImageUtil.scpSendImg(serviceName, orgFilePath, desFilePath2, username, password);
          if (flag) {
            nReturn = 1;
          } else {
            nReturn = 0;
          }
          // nReturn = FTPFileUpload.uploadFile(orgFilePath, serviceName, port,
          // connMode, username, password, desFilePath2, orgFile
          // .getName());
        } else {
          boolean flag = CommodityDescImageUtil.scpSendImg(serviceName, orgFilePath, desFilePath1, username, password);
          if (flag) {
            nReturn = 1;
          } else {
            nReturn = 0;
          }
          // nReturn = FTPFileUpload.uploadFile(orgFilePath, serviceName, port,
          // connMode, username, password, desFilePath1, orgFile
          // .getName());
        }
        if (nReturn != 1) {
          errMsgList.add(orgFile.getName() + "," + FTPFileUpload.getErrMsg());
          if (0 == index) {
            dto.setEc1UploadFlg(0L);
          }
          if (1 == index) {
            dto.setEc2UploadFlg(0L);
          }
          continue;
        }

        // 将文件从resize文件夹移动到upload文件夹下
        int nResult = checkFiles(config.getResizeImgPath(), config.getUploadImgPath());
        if (2 == nResult) {
          return false;
        }
        if (0 == nResult) {
          return false;
        }

        nResult = FileUtil.copyFile(config.getResizeImgPath() + "/" + orgFile.getName(), config.getUploadImgPath() + "/"
            + orgFile.getName());
        if (nResult != 1) {
          errMsgList.add(orgFile.getName() + ",可能正在被使用");
          if (0 == index) {
            dto.setEc1UploadFlg(0L);
          }
          if (1 == index) {
            dto.setEc2UploadFlg(0L);
          }
          continue;
        }
      }

      // CommodityHeaderDao commHeaderDao =
      // DIContainer.getDao(CommodityHeaderDao.class);
      // boolean isRepSku = commHeaderDao.isRepresentSkuCode(shopCode,
      // dto.getSkuCode());

      // if (isRepSku) {
      // // 如果是代表SKU,还要上传商品图片
      // CommodityHeader ch = commHeaderDao.loadByRepSku(shopCode,
      // dto.getSkuCode());
      //
      // List<File> commodityFileList = new ArrayList<File>();
      // nReturn = FileUtil.findFilesByFileName(config.getResizeImgPath(),
      // ch.getCommodityCode() + fix, commodityFileList);
      //
      // if (commodityFileList.size() > 0) {
      // for (File orgFile : commodityFileList) {
      // String orgFilePath = config.getResizeImgPath() + "/" +
      // orgFile.getName();
      // if (StringUtil.hasValue(fix)) {
      // nReturn = FTPFileUpload.uploadFile(orgFilePath, serviceName, port,
      // connMode, username, password, desFilePath2,
      // orgFile.getName());
      // } else {
      // nReturn = FTPFileUpload.uploadFile(orgFilePath, serviceName, port,
      // connMode, username, password, desFilePath1,
      // orgFile.getName());
      // }
      // if (nReturn != 1) {
      // errMsgList.add(orgFile.getName() + "," + FTPFileUpload.getErrMsg());
      // if (0 == index) {
      // dto.setEc1UploadFlg(0L);
      // }
      // if (1 == index) {
      // dto.setEc2UploadFlg(0L);
      // }
      // continue;
      // }
      //
      // // 将文件从resize文件夹移动到upload文件夹下
      // int nResult = checkFiles(config.getResizeImgPath(),
      // config.getUploadImgPath());
      // if (2 == nResult || 0 == nResult) {
      // return false;
      // }
      //
      // nResult = FileUtil.copyFile(config.getResizeImgPath() + "/" +
      // orgFile.getName(), config.getUploadImgPath() + "/"
      // + orgFile.getName());
      // if (nResult != 1) {
      // errMsgList.add(orgFile.getName() + ",可能正在被使用");
      // if (0 == index) {
      // dto.setEc1UploadFlg(0L);
      // }
      // if (1 == index) {
      // dto.setEc2UploadFlg(0L);
      // }
      // continue;
      // }
      // }
      // }
      // }
      // 20130906 txw update end

      if (0 == index && dto.getEc1UploadFlg() != 0) {
        dto.setEc1UploadFlg(1L);
        dto.setUploadDatetime(DateUtil.getSysdate());
      }
      if (1 == index && dto.getEc2UploadFlg() != 0) {
        dto.setEc2UploadFlg(1L);
        dto.setUploadDatetime(DateUtil.getSysdate());
      }

      dao.update(dto, getLoginInfo());

      index++;
    }

    return true;
  }

  // 2014/05/08 京东WBS对应 ob_姚 add start
  // 将文件上传到JD
  private boolean uploadFiles2Jd(ImageUploadConfig config, String shopCode, List<String> errMsgList, String orgFileName) {
    if (null == config || StringUtil.isNullOrEmptyAnyOf(shopCode, orgFileName) || null == errMsgList) {
      return false;
    }

    boolean bReturn = true;

    String sku = getSkuFromFileName(orgFileName);

    // 通过sku查询ImageUploadHistory dto
    ImageUploadHistoryDao dao = DIContainer.getDao(ImageUploadHistoryDao.class);
    ImageUploadHistory dto = getImageUploadHistory(shopCode, sku, orgFileName);
    // 如果已经存在数据,更新它
    if (null == dto) {
      errMsgList.add(orgFileName + ",商品图片不存在");
      return false;
    }
    String fix = "";
    if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg1Fix())) {
      fix = config.getAssistImg1Fix();
    } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg2Fix())) {
      fix = config.getAssistImg2Fix();
    } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg3Fix())) {
      fix = config.getAssistImg3Fix();
    } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg4Fix())) {
      fix = config.getAssistImg4Fix();
    }

    if (1 == FileUtil.isFileExist(dto.getCommodityCode() + fix + config.getJdUploadImgFix() + ".jpg", config.getResizeImgPath())) {
      JdService jdService = ServiceLocator.getJdService(getLoginInfo());

      // 获取并设置JD商品id
      CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);
      CCommodityDetailDao ccdDao = DIContainer.getDao(CCommodityDetailDao.class);
      CCommodityHeader cchdto = cchDao.load(dto.getShopCode(), dto.getCommodityCode());
      CCommodityDetail ccddto = ccdDao.load(dto.getShopCode(), dto.getSkuCode());
      if (null == cchdto || NumUtil.isNull(cchdto.getJdCommodityId()) || ccddto == null
          || JdUseFlg.DISABLED.longValue().equals(ccddto.getJdUseFlg())) {
        errMsgList.add(dto.getCommodityCode() + fix + config.getJdUploadImgFix() + ".jpg" + ",京东商品不存在或通信超时");
        // 将文件京东不使用或者京东上不存在京东备份目录的不相符的jpg移动到待上传文件夹下
        boolean result = FileUtil.moveEcToEc(config.getOrgImgPath() + "/" + orgFileName, config.getJdDisabledImgPath() + "/"
            + orgFileName);

        if (!result) {
          errMsgList.add(orgFileName + ",可能正在被使用");
        } else {
          List<File> commodityFileList = new ArrayList<File>();
          FileUtil.findFilesByFileName(config.getOrgImgPath(), orgFileName.split("\\.")[0], commodityFileList);
          if (commodityFileList.size() > 0) {
            for (int j = 0; j < 3; j++) {
              if (commodityFileList.get(0).renameTo(commodityFileList.get(0))) {
                break;
              } else {
                System.gc();
                try {
                  Thread.sleep(1500);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                continue;
              }
            }
            commodityFileList.get(0).delete();
          }
        }
        return false;
      }

      // 根据ID判断主辅图，并判断更新与否
      int positionId = getPosition(orgFileName);

      ImageUploadHistory dtoTemp = new ImageUploadHistory();
      String uploadFile = config.getResizeImgPath() + "/" + dto.getCommodityCode() + fix + config.getJdUploadImgFix() + ".jpg";
      ServiceResult uploadResult = null;
      if (positionId == 0) {
        uploadResult = jdService.addMainCommodityImage(NumUtil.toString(cchdto.getJdCommodityId()), uploadFile, dtoTemp, dto);
      } else {
        uploadResult = jdService
            .addAssistCommodityImage(NumUtil.toString(cchdto.getJdCommodityId()), sku, fix, uploadFile, dtoTemp);
      }

      if (uploadResult.hasError()) {
        dto.setJdUploadFlg(0L);

        errMsgList.add(dto.getCommodityCode() + config.getJdUploadImgFix() + ".jpg" + ",京东商品不存在或通信超时");
        bReturn = false;
      } else {
        dto.setJdUploadFlg(1L);
        dto.setJdAttId(Integer.toString(positionId));
        dto.setJdImageId(dtoTemp.getJdImageId());
        dto.setJdUploadTime(dtoTemp.getJdUploadTime());
        dto.setJdImgUrl(dtoTemp.getJdImgUrl());
      }

      dao.update(dto, getLoginInfo());
    }

    return bReturn;
  }

  // 2014/05/08 京东WBS对应 ob_姚 add end

  // 将文件上传到tmall
  private boolean uploadFiles2Tmall(ImageUploadConfig config, String shopCode, List<String> errMsgList, String orgFileName) {
    if (null == config || StringUtil.isNullOrEmptyAnyOf(shopCode, orgFileName) || null == errMsgList) {
      return false;
    }

    boolean bReturn = true;

    String sku = getSkuFromFileName(orgFileName);

    // 通过sku查询ImageUploadHistory dto
    ImageUploadHistoryDao dao = DIContainer.getDao(ImageUploadHistoryDao.class);
    // 20130906 txw update start
    ImageUploadHistory dto = getImageUploadHistory(shopCode, sku, orgFileName);
    // 如果已经存在数据,更新它
    if (null == dto) {
      errMsgList.add(orgFileName + ",商品图片不存在");
      return false;
    }
    String fix = "";
    if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg1Fix())) {
      fix = config.getAssistImg1Fix();
    } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg2Fix())) {
      fix = config.getAssistImg2Fix();
    } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg3Fix())) {
      fix = config.getAssistImg3Fix();
    } else if (orgFileName.split("\\.")[0].endsWith(config.getAssistImg4Fix())) {
      fix = config.getAssistImg4Fix();
    }

    if (1 == FileUtil.isFileExist(dto.getCommodityCode() + fix + config.getTmallUploadImgFix() + ".jpg", config.getResizeImgPath())) {
      TmallService tmservice = ServiceLocator.getTmallService(getLoginInfo());
      TmallCommodityImg tci = new TmallCommodityImg();
      // tci.setTmallCommodityId("14099247543");
      // 获取并设置淘宝商品id
      CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);
      CCommodityDetailDao ccdDao = DIContainer.getDao(CCommodityDetailDao.class);
      CCommodityHeader cchdto = cchDao.load(dto.getShopCode(), dto.getCommodityCode());
      CCommodityDetail ccddto = ccdDao.load(dto.getShopCode(), dto.getSkuCode());
      if (null == cchdto || null == cchdto.getTmallCommodityId() || ccddto == null || ccddto.getTmallUseFlg() == null
          || ccddto.getTmallUseFlg() == 0) {
        errMsgList.add(dto.getCommodityCode() + fix + config.getTmallUploadImgFix() + ".jpg" + ",淘宝商品不存在或通信超时");
        // 将文件淘宝不使用或者淘宝上不存在商品备份目录的不相符的jpg移动到待上传文件夹下
        boolean result = FileUtil.moveEcToEc(config.getOrgImgPath() + "/" + orgFileName, config.getTmallDisabledImgPath() + "/"
            + orgFileName);

        if (!result) {
          errMsgList.add(orgFileName + ",可能正在被使用");
        } else {
          List<File> commodityFileList = new ArrayList<File>();
          FileUtil.findFilesByFileName(config.getOrgImgPath(), orgFileName.split("\\.")[0], commodityFileList);
          if (commodityFileList.size() > 0) {
            for (int j = 0; j < 3; j++) {
              if (commodityFileList.get(0).renameTo(commodityFileList.get(0))) {
                break;
              } else {
                System.gc();
                try {
                  Thread.sleep(1500);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                continue;
              }
            }
            commodityFileList.get(0).delete();
          }
        }
        return false;
      }

      // 根据ID判断主辅图，并判断更新与否
      int positionId = getPosition(orgFileName);

      if (positionId == 0) {
        tci.setIs_major(true);
        tci.setPosition(positionId + 1);
      } else {
        tci.setPosition(positionId + 1);
        tci.setIs_major(false);
      }

      if (!StringUtil.isNullOrEmpty(dto.getTmallImgId())) {
        tci.setImgId(dto.getTmallImgId());
      }

      tci.setTmallCommodityId(cchdto.getTmallCommodityId().toString());
      tci.setImageUrl(config.getResizeImgPath() + "/" + dto.getCommodityCode() + fix + config.getTmallUploadImgFix() + ".jpg");
      TmallCommodityImg rettci = tmservice.imageUpload(tci);

      if (null == rettci || !StringUtil.hasValueAllOf(rettci.getImgId(), rettci.getReturnImgUrl())) {
        dto.setTmallUploadFlg(0L);

        errMsgList.add(dto.getCommodityCode() + config.getTmallUploadImgFix() + ".jpg" + ",淘宝商品不存在或通信超时");
        bReturn = false;
      } else {
        if (rettci.getReturnImgUrl().equals("商品图片不能超过5张！")) {
          return bReturn;
        }
        dto.setTmallUploadFlg(1L);
        dto.setTmallImgId(rettci.getImgId());
        dto.setTmallImgUrl(rettci.getReturnImgUrl());
        dto.setUploadDatetime(DateUtil.getSysdate());
      }

      dao.update(dto, getLoginInfo());
    }

    return bReturn;
  }

  // 获取图片序号返回0为主图，其他为辅图
  private int getPosition(String FileName) {
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    if (StringUtil.isNullOrEmpty(FileName)) {
      return 0;
    }
    String[] tmp = FileName.split("\\.");
    if (tmp.length > 0) {
      String positionId = tmp[0];
      if (positionId.endsWith(config.getAssistImg1Fix())) {
        return 1;
      } else if (positionId.endsWith(config.getAssistImg2Fix())) {
        return 2;
      } else if (positionId.endsWith(config.getAssistImg3Fix())) {
        return 3;
      } else if (positionId.endsWith(config.getAssistImg4Fix())) {
        return 4;
      }
      return 0;
    } else {
      return 0;
    }
  }

  // 将文件上传到tmall
  private boolean uploadCampFiles2Tmall(ImageUploadConfig config, String shopCode, List<String> errMsgList, String orgFileName) {
    if (null == config || StringUtil.isNullOrEmptyAnyOf(shopCode, orgFileName) || null == errMsgList) {
      return false;
    }

    boolean bReturn = true;

    String sku = getSkuFromFileName(orgFileName);

    // 通过sku查询ImageUploadHistory dto
    ImageUploadHistoryDao dao = DIContainer.getDao(ImageUploadHistoryDao.class);
    ImageUploadHistory dto = dao.load(shopCode, sku);
    // 如果已经存在数据,更新它
    if (null == dto) {
      errMsgList.add(orgFileName + ",活动商品图片不存在");
      return false;
    }

    if (1 == FileUtil.isFileExist(dto.getCommodityCode() + ".jpg", config.getCampImgPath())) {
      TmallService tmservice = ServiceLocator.getTmallService(getLoginInfo());
      TmallCommodityImg tci = new TmallCommodityImg();
      // tci.setTmallCommodityId("14099247543");
      // 获取并设置淘宝商品id
      CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);
      CCommodityHeader cchdto = cchDao.load(dto.getShopCode(), dto.getCommodityCode());
      if (null == cchdto || null == cchdto.getTmallCommodityId()) {
        errMsgList.add(dto.getCommodityCode() + ".jpg" + ",淘宝商品不存在或通信超时");
        return false;
      }

      tci.setTmallCommodityId(cchdto.getTmallCommodityId().toString());
      tci.setIs_major(true);
      tci.setImageUrl(config.getCampImgPath() + "/" + dto.getCommodityCode() + ".jpg");
      TmallCommodityImg rettci = tmservice.imageUpload(tci);

      if (null == rettci || !StringUtil.hasValueAllOf(rettci.getImgId(), rettci.getReturnImgUrl())) {
        dto.setTmallUploadFlg(0L);

        errMsgList.add(dto.getCommodityCode() + ".jpg" + ",淘宝商品不存在或通信超时");
        bReturn = false;
      } else {
        dto.setTmallUploadFlg(1L);
        dto.setTmallImgId(rettci.getImgId());
        dto.setTmallImgUrl(rettci.getReturnImgUrl());
        dto.setUploadDatetime(DateUtil.getSysdate());
      }

      dao.update(dto, getLoginInfo());
    }

    return bReturn;
  }

  private int checkFiles(String orgPath, String desPath) {

    if (!StringUtil.hasValueAllOf(orgPath, desPath)) {
      return 2;
    }

    // 查看despath存不存在
    if (!FileUtil.isDirectoryExist(desPath)) {
      return 2;
    }

    // 查看orgPath存不存在,是不是空的,里面有没有jpg文件
    if (FileUtil.isDirectoryEmpty(orgPath) || FileUtil.findFilesByFileType(orgPath, "jpg", null) != 1) {
      return 0;
    }

    return 1;
  }

  private String getSkuFromFileName(String FileName) {
    // 20130906 txw update start
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    if (StringUtil.isNullOrEmpty(FileName)) {
      return "";
    }
    String[] tmp = FileName.split("\\.");
    if (tmp.length > 0) {
      String skuCode = tmp[0];
      if (skuCode.endsWith(config.getAssistImg1Fix())) {
        skuCode = skuCode.replace(config.getAssistImg1Fix(), "");
      } else if (skuCode.endsWith(config.getAssistImg2Fix())) {
        skuCode = skuCode.replace(config.getAssistImg2Fix(), "");
      } else if (skuCode.endsWith(config.getAssistImg3Fix())) {
        skuCode = skuCode.replace(config.getAssistImg3Fix(), "");
      } else if (skuCode.endsWith(config.getAssistImg4Fix())) {
        skuCode = skuCode.replace(config.getAssistImg4Fix(), "");
      }
      return skuCode;
    } else {
      return "";
    }
    // 20130906 txw update end
  }

  private int resizeFile(String orgFilePath, String orgFileName, String desFilePath, String desFileName, int width, int height) {
    if (!StringUtil.hasValueAllOf(orgFilePath, desFilePath, orgFileName, desFileName)) {
      return 2;
    }

    if (width < 0 || height < 0) {
      return 2;
    }

    // 去目标文件夹下查找目标文件是否已经存在
    int nReturn = FileUtil.isFileExist(desFileName, desFilePath);

    if (1 == nReturn) {
      File desFile = new File(desFilePath + "/" + desFileName);
      desFile.delete();
    }
    // 20130906 txw update start
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    if (StringUtil.isNullOrEmpty(orgFilePath) || StringUtil.isNullOrEmpty(desFilePath) || width < 0 || height < 0) {
      return 2;
    }
    if (width == config.getOrgImgWidth() && height == config.getOrgImgHight()) {
      int result = FileUtil.copyFile(orgFilePath + "/" + orgFileName, desFilePath + "/" + desFileName);
      if (result != 1) {
        // errMsgList.add(orgFileName + ",可能正在被使用");
        return 0;
      }
      return 1;
    } else {
      return FileUtil.resetJPGFilesSize(orgFilePath + "/" + orgFileName, desFilePath + "/" + desFileName, width, height);
    }
    // 20130906 txw update end
  }

  public List<ImageUploadHistory> getCommodityImgInfoList(String shopCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_IMG_INFO, shopCode);
    List<ImageUploadHistory> dtoList = DatabaseUtil.loadAsBeanList(query, ImageUploadHistory.class);
    return dtoList;
  }

  public SearchResult<ImageUploadHistory> getCommodityImgUploadInfoList(CommodityImageSearchCondition condition) {
    return DatabaseUtil.executeSearch(new ImageUploadSearchQuery(condition));
  }

  // add by ob 20120206 end

  public ReviewSummary getCommodityReviewSummary(String shopCode, String commodityCode) {
    ReviewSummaryDao dao = DIContainer.getDao(ReviewSummaryDao.class);
    ReviewSummary reviewSummary = dao.load(shopCode, commodityCode);
    return reviewSummary;
  }

  @Override
  public ServiceResult updateCcheader(CCommodityHeader header, String discountPrice, String tmallDiscountPrice,
      List<TmallCommodityProperty> propertys, String oldCommodityCode) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    CCommodityHeader updateHeader = dao.load(header.getShopCode(), oldCommodityCode);
    // 2014/4/28 京东WBS对应 ob_李 add start
    CCommodityDetailDao detailDao = DIContainer.getDao(CCommodityDetailDao.class);
    List<CCommodityDetail> details = detailDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), header.getShopCode(),
        oldCommodityCode);
    // 2014/4/28 京东WBS对应 ob_李 add end
    // 商品ヘッダの存在チェック
    if (updateHeader == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    // 如果商品以同期不能修改商品Code
    if (!NumUtil.isNull(updateHeader.getTmallCommodityId())) {
      if (!oldCommodityCode.equals(updateHeader.getCommodityCode())) {
        result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return result;
      }
    }
    // システム最大(or最小)日付を設定する
    if (header.getSaleStartDatetime() == null) {
      header.setSaleStartDatetime(DateUtil.getMin());
    }
    if (header.getSaleEndDatetime() == null) {
      header.setSaleEndDatetime(DateUtil.getMax());
    }
    header.setStandard1Id(updateHeader.getStandard1Id());
    header.setStandard1Name(updateHeader.getStandard1Name());
    header.setStandard2Id(updateHeader.getStandard2Id());
    header.setStandard2Name(updateHeader.getStandard2Name());
    header.setSyncFlagEc(1L);
    header.setSyncFlagTmall(1L);
    header.setUpdatedDatetime(updateHeader.getUpdatedDatetime());
    header.setExportFlagErp(1L);
    header.setExportFlagWms(1L);
    // 2014/4/28 京东WBS对应 ob_李 add start
    // 京东同期FLG JD使用标志为( 1：使用) 而且 商品Header.JD商品ID不为空时，设置1：同期可能；
    // 20141120 hdh update 去掉 && !NumUtil.isNull(updateHeader.getJdCommodityId())
    CCommodityDetail detailOld = detailDao.load(header.getShopCode(), header.getRepresentSkuCode());
    if (JdUseFlg.ENABLED.longValue().equals(detailOld.getJdUseFlg())) {
      header.setSyncFlagJd(SyncFlagJd.SYNCVISIBLE.longValue());
    } else {
      header.setSyncFlagJd(updateHeader.getSyncFlagJd());
    }
    // 2014/4/28 京东WBS对应 ob_李 add end
    setUserStatus(header);
    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      Object[] params = new Object[] {
          header.getCommodityCode(), header.getTmallCommoditySearchWords(), header.getCombinationAmount(),
          header.getExportFlagErp(), header.getExportFlagWms(), header.getCommodityName(), header.getRepresentSkuCode(),
          header.getCommodityNameEn(), header.getSupplierCode(), header.getBuyerCode(), header.getLeadTime(),
          header.getSaleStartDatetime(), header.getSaleEndDatetime(), header.getDiscountPriceStartDatetime(),
          header.getDiscountPriceEndDatetime(), header.getStandard1Name(), header.getStandard2Name(), header.getBrandCode(),
          header.getSaleFlgEc(), header.getSpecFlag(), header.getWarningFlag(), header.getCommoditySearchWords(),
          header.getCommodityDescription1(), header.getCommodityDescription2(), header.getCommodityDescription3(),
          header.getCommodityDescriptionShort(), header.getOriginalPlace(), header.getReturnFlg(), header.getSaleFlag(),
          header.getBigFlag(), header.getTmallCategoryId(), header.getIngredientName1(), header.getIngredientVal1(),
          header.getIngredientName2(), header.getIngredientVal2(), header.getIngredientName3(), header.getIngredientVal3(),
          header.getIngredientName4(), header.getIngredientVal4(), header.getIngredientName5(), header.getIngredientVal5(),
          header.getIngredientName6(), header.getIngredientVal6(), header.getIngredientName7(), header.getIngredientVal7(),
          header.getIngredientName8(), header.getIngredientVal8(), header.getIngredientName9(), header.getIngredientVal9(),
          header.getIngredientName10(), header.getIngredientVal10(), header.getIngredientName11(), header.getIngredientVal11(),
          header.getIngredientName12(), header.getIngredientVal12(), header.getIngredientName13(), header.getIngredientVal13(),
          header.getIngredientName14(), header.getIngredientVal14(), header.getIngredientName15(), header.getIngredientVal15(),
          header.getMaterial1(), header.getMaterial2(), header.getMaterial3(), header.getMaterial4(), header.getMaterial5(),
          header.getMaterial6(), header.getMaterial7(), header.getMaterial8(), header.getMaterial9(), header.getMaterial10(),
          header.getMaterial11(), header.getMaterial12(), header.getMaterial13(), header.getMaterial14(), header.getMaterial15(),
          header.getShelfLifeFlag(), header.getShelfLifeDays(), header.getInBoundLifeDays(), header.getOutBoundLifeDays(),
          header.getShelfLifeAlertDays(), header.getUpdatedUser(), header.getUpdatedDatetime(), header.getCommodityNameJp(),
          header.getCommodityDescription1En(), header.getCommodityDescription1Jp(), header.getCommodityDescription2En(),
          header.getCommodityDescription2Jp(), header.getCommodityDescription3En(), header.getCommodityDescription3Jp(),
          header.getCommodityDescriptionShortEn(), header.getCommodityDescriptionShortJp(), header.getFoodSecurityPrdLicenseNo(),
          header.getFoodSecurityDesignCode(), header.getFoodSecurityFactory(), header.getFoodSecurityFactorySite(),
          header.getFoodSecurityContact(), header.getFoodSecurityMix(), header.getFoodSecurityPlanStorage(),
          header.getFoodSecurityPeriod(), header.getFoodSecurityFoodAdditive(), header.getFoodSecuritySupplier(),
          header.getFoodSecurityProductDateStart(), header.getFoodSecurityProductDateEnd(), header.getFoodSecurityStockDateStart(),
          header.getFoodSecurityStockDateEnd(), header.getOriginalPlaceEn(), header.getKeywordCn2(), header.getKeywordEn2(),
          header.getKeywordJp2(), header.getOriginalPlaceJp(), header.getTmallMjsFlg(), header.getImportCommodityType(),
          header.getClearCommodityType(), header.getReserveCommodityType1(), header.getReserveCommodityType2(),
          header.getReserveCommodityType3(), header.getNewReserveCommodityType1(), header.getNewReserveCommodityType2(),
          header.getNewReserveCommodityType3(), header.getNewReserveCommodityType4(), header.getNewReserveCommodityType5(),
          header.getOriginalCode(), header.getHotFlgEn(), header.getHotFlgJp(),
          // 20130808 txw add start
          header.getTitle(), header.getTitleEn(), header.getTitleJp(), header.getDescription(), header.getDescriptionEn(),
          header.getDescriptionJp(), header.getKeyword(), header.getKeywordEn(), header.getKeywordJp(),
          // 20130808 txw add end
          // 2014/4/28 京东WBS对应 ob_李 add start
          header.getSyncFlagJd(), header.getAdvertContent(),
          // 2014/4/28 京东WBS对应 ob_李 add end
          header.getShopCode(), oldCommodityCode
      };
      manager.begin(getLoginInfo());
      manager.executeUpdate(CatalogQuery.Tmall_UPDATE_CCOMMONDITY_HEADER, params);
      // 2014/4/28 京东WBS对应 ob_李 del start
      // CCommodityDetailDao detailDao =
      // DIContainer.getDao(CCommodityDetailDao.class);
      // List<CCommodityDetail> details =
      // detailDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(),
      // header.getShopCode(),
      // oldCommodityCode);
      // 2014/4/28 京东WBS对应 ob_李 del end
      if (!oldCommodityCode.equals(header.getCommodityCode())) {
        // 修改commoditycode后 需要同时更新c_commodity_ext,c_commodity_detail
        // ,category_attribute_value,campaign_commodity,tmall_commodity_property,
        CategoryAttributeValueDao valueDao = DIContainer.getDao(CategoryAttributeValueDao.class);
        CategoryCommodityDao categoryCDao = DIContainer.getDao(CategoryCommodityDao.class);
        StockDao stockDao = DIContainer.getDao(StockDao.class);
        List<Stock> stocks = stockDao.loadByCommodityCode(header.getShopCode(), oldCommodityCode);

        if (stocks.size() == 0) {
          // 库存信息不存在
          result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          manager.rollback();
          manager.dispose();
          return result;
        } else {
          for (Stock stock : stocks) {
            stock.setCommodityCode(header.getCommodityCode());
            setUserStatus(stock);
            manager.update(stock);
          }
        }

        CCommodityExtDao extDao = DIContainer.getDao(CCommodityExtDao.class);
        List<CCommodityExt> exts = extDao.loadAllByCommodityCode(header.getShopCode(), oldCommodityCode);
        if (exts.size() == 0) {
        } else {
          for (CCommodityExt ext : exts) {
            ext.setCommodityCode(header.getCommodityCode());
            setUserStatus(ext);
            manager.update(ext);
          }
        }

        List<CategoryAttributeValue> attributeValues = valueDao.loadAllByCommodityCode(header.getShopCode(), oldCommodityCode);
        if (attributeValues.size() == 0) {

        } else {
          for (CategoryAttributeValue value : attributeValues) {
            value.setCommodityCode(header.getCommodityCode());
            setUserStatus(value);
            manager.update(value);
          }
        }

        List<CategoryCommodity> cList = categoryCDao.loadAllByCommodityCode(header.getShopCode(), oldCommodityCode);
        if (cList.size() == 0) {

        } else {
          for (CategoryCommodity categoryCommodity : cList) {
            categoryCommodity.setCommodityCode(header.getCommodityCode());
            setUserStatus(categoryCommodity);
            manager.update(categoryCommodity);
          }
        }

      }
      // 修改 detail 表的商品Code
      if (!oldCommodityCode.equals(header.getCommodityCode())) {
        for (CCommodityDetail detail : details) {
          detail.setCommodityCode(header.getCommodityCode());
        }
      }
      /**
       * 如果界面设置了特价需循环处理sku特价 修改
       */
      if (StringUtil.hasValueAnyOf(discountPrice, tmallDiscountPrice)) {
        for (CCommodityDetail detail : details) {
          setUserStatus(detail);
          if (StringUtil.hasValue(discountPrice)) {
            detail.setDiscountPrice(NumUtil.parse(discountPrice));
          }
          if (StringUtil.hasValue(tmallDiscountPrice)) {
            detail.setTmallDiscountPrice(NumUtil.parse(tmallDiscountPrice));
          }
          manager.update(detail);
          // if (detail.getSkuCode().equals(header.getRepresentSkuCode())) {
          // detail.setDiscountPrice(NumUtil.parse(discountPrice));
          // detail.setTmallDiscountPrice(NumUtil.parse(tmallDiscountPrice));
          //
          // manager.update(detail);
          // } else {
          // if (detail.getDiscountPrice() == null ||
          // detail.getTmallDiscountPrice() == null) {
          // if (detail.getDiscountPrice() == null) {
          // detail.setDiscountPrice(NumUtil.parse(discountPrice));
          // }
          // if (detail.getTmallDiscountPrice() == null) {
          // detail.setTmallDiscountPrice(NumUtil.parse(tmallDiscountPrice));
          // }
          // manager.update(detail);
          // }
          // }
        }
      } else {
        String sql = "update c_commodity_detail set discount_price=null ,tmall_discount_price=null where commodity_code=? and shop_code=?";
        manager.executeUpdate(sql, header.getCommodityCode(), header.getShopCode());
      }

      /**
       * 删除以前的属性
       */
      manager.executeUpdate(CatalogQuery.DELETE_BRAND_PROPERTY, oldCommodityCode);
      /**
       * 插入属性与属性值
       */
      for (int i = 0; i < propertys.size(); i++) {
        TmallCommodityProperty property = propertys.get(i);
        if (!oldCommodityCode.equals(header.getCommodityCode())) {
          property.setCommodityCode(header.getCommodityCode());
        }
        if (StringUtil.isNull(property.getCommodityCode()) || StringUtil.isNull(property.getValueId())
            || StringUtil.isNull(property.getPropertyId())) {
          result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return result;
        }
        setUserStatus(property);
        manager.insert(property);
      }

      manager.commit();
    } catch (Exception e) {
      logger.error("update c_commodity_header table DB error! commodityCode is " + header.getCommodityCode(), e);
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return result;
  }

  // 2014/4/28 京东WBS对应 ob_李 add start
  public ServiceResult updateJdCommodityProperty(CCommodityHeader header, List<JdCommodityProperty> propertys) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    CCommodityDetailDao detailDao = DIContainer.getDao(CCommodityDetailDao.class);
    List<CCommodityDetail> details = detailDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), header.getShopCode(),
        header.getCommodityCode());
    // 商品ヘッダの存在チェック
    if (header == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    // 如果商品以同期不能修改商品Code
    if (!NumUtil.isNull(header.getJdCommodityId())) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    // システム最大(or最小)日付を設定する
    if (header.getSaleStartDatetime() == null) {
      header.setSaleStartDatetime(DateUtil.getMin());
    }
    if (header.getSaleEndDatetime() == null) {
      header.setSaleEndDatetime(DateUtil.getMax());
    }

    setUserStatus(header);
    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(getLoginInfo());
      manager.update(header);
      for (CCommodityDetail detail : details) {
        setUserStatus(detail);
        manager.update(detail);
      }
      /**
       * 删除以前的属性
       */
      manager.executeUpdate(CatalogQuery.DELETE_JD_COMMODITY_PROPERTY, header.getCommodityCode());
      /**
       * 插入属性与属性值
       */
      for (int i = 0; i < propertys.size(); i++) {
        JdCommodityProperty property = propertys.get(i);
        property.setCommodityCode(header.getCommodityCode());

        if (StringUtil.hasValue(property.getValueText())) {
          if (StringUtil.isNull(property.getCommodityCode()) || StringUtil.isNull(property.getValueId())
              || StringUtil.isNull(property.getPropertyId())) {
            result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
            return result;
          }
          setUserStatus(property);
          manager.insert(property);
        }

      }

      manager.commit();
    } catch (Exception e) {
      logger.error("update c_commodity_header table DB error! commodityCode is " + header.getCommodityCode(), e);
      manager.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      manager.dispose();
    }
    return result;
  }

  // 2014/4/28 京东WBS对应 ob_李 add end

  @Override
  public boolean hasCommodityCode(String shopCode, String commodityCode) {
    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return false;
    }
    Query query = new SimpleQuery(CatalogQuery.GET_C_COMMODITY_EXT_LIST, shopCode, commodityCode);
    List<CCommodityExt> extList = DatabaseUtil.loadAsBeanList(query, CCommodityExt.class);

    if (extList.isEmpty()) {
      return false;
    }
    return true;
  }

  // 20120216 os013 add start
  // 更新商品基本表CategorySearchPath
  public void updateCategorySearchPath(TransactionManager txMgr, List<CategoryCommodity> categoryCommodity, String commodityCode,
      String shopCode) {
    String headerCategorySearchPath = null;
    CategoryDao catDao = DIContainer.getDao(CategoryDao.class);

    List<CategoryCommodity> categoryListNew = new ArrayList<CategoryCommodity>();
    List<CategoryCommodity> categoryList2 = new ArrayList<CategoryCommodity>();
    for (CategoryCommodity category : categoryCommodity) {
      if (category.getCategoryCode().equals("02001001") || category.getCategoryCode().equals("01001001")) {
        categoryListNew.add(category);
      } else {
        categoryList2.add(category);
      }
    }
    categoryListNew.addAll(categoryList2);
    for (CategoryCommodity category : categoryListNew) {
      Category cat = catDao.load(category.getCategoryCode());
      if (category == categoryListNew.get(0)) {
        headerCategorySearchPath = cat.getPath() + "~" + cat.getCategoryCode();
      } else {
        if ((headerCategorySearchPath + "#" + cat.getPath() + "~" + cat.getCategoryCode()).length() < 256) {
          headerCategorySearchPath = headerCategorySearchPath + "#" + cat.getPath() + "~" + cat.getCategoryCode();
        }
      }
    }
    CCommodityHeaderDao CHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
    CommodityHeaderDao HeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
    // 更新CCommodityHeader表的検索用カテゴリパス字段
    if (CHeaderDao.exists(shopCode, commodityCode)) {
      txMgr.executeUpdate(RelatedCategoryQuery.UPDATE_C_COMMODITY_HEADER_CATEGORY_SEARCH_PATH_QUERY, headerCategorySearchPath, 1L,
          1L, 1L, 1L, this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), commodityCode);
    }
    // 更新CommodityHeader表的商品目录路径字段
    if (HeaderDao.exists(shopCode, commodityCode)) {
      txMgr.executeUpdate(RelatedCategoryQuery.UPDATE_COMMODITY_HEADER_CATEGORY_PATH_QUERY, headerCategorySearchPath, this
          .getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), commodityCode);
    }
  }

  // 20120216 os013
  // 20120220 os013 add start
  public void updateCategoryAttributeValue(TransactionManager txMgr, List<CategoryAttributeValue> categoryAttributeValue,
      String commodityCode, String shopCode) {
    String headerCategoryAttributeValue = null;
    CategoryAttributeDao catDao = DIContainer.getDao(CategoryAttributeDao.class);
    for (int i = 0; i < categoryAttributeValue.size(); i++) {
      CategoryAttribute cat = catDao.load(categoryAttributeValue.get(i).getCategoryCode(), categoryAttributeValue.get(i)
          .getCategoryAttributeNo());
      if ((headerCategoryAttributeValue + "#" + cat.getCategoryAttributeName()).length() < 256) {
        if ((i + 1) < categoryAttributeValue.size()) {
          if (categoryAttributeValue.get(i).getCategoryCode().equals(categoryAttributeValue.get(i + 1).getCategoryCode())) {
            if (i == 0) {
              headerCategoryAttributeValue = cat.getCategoryAttributeName();
            } else {
              headerCategoryAttributeValue = headerCategoryAttributeValue + "|" + cat.getCategoryAttributeName();
            }
          } else {
            if (i == 0) {
              headerCategoryAttributeValue = cat.getCategoryAttributeName();
            } else {
              headerCategoryAttributeValue = headerCategoryAttributeValue + "#" + cat.getCategoryAttributeName();
            }
          }
        } else {
          if (i == 0) {
            headerCategoryAttributeValue = cat.getCategoryAttributeName();
          } else {
            if (categoryAttributeValue.get(i - 1).getCategoryCode().equals(categoryAttributeValue.get(i).getCategoryCode())) {
              headerCategoryAttributeValue = headerCategoryAttributeValue + "|" + cat.getCategoryAttributeName();
            } else {
              headerCategoryAttributeValue = headerCategoryAttributeValue + "#" + cat.getCategoryAttributeName();
            }
          }
        }
      }
    }
    CCommodityHeaderDao CHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
    CommodityHeaderDao HeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
    // 更新CCommodityHeader表的検索用カテゴリパス字段
    if (CHeaderDao.exists(shopCode, commodityCode)) {
      txMgr.executeUpdate(RelatedCategoryQuery.UPDATE_C_COMMODITY_HEADER_CATEGORY_ATTRIBUTE_VALUE_QUERY,
          headerCategoryAttributeValue, 1L, 1L, 1L, this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), commodityCode);
    }
    // 更新CommodityHeader表的商品目录路径字段
    if (HeaderDao.exists(shopCode, commodityCode)) {
      txMgr.executeUpdate(RelatedCategoryQuery.UPDATE_COMMODITY_HEADER_CATEGORY_ATTRIBUTE1_QUERY, headerCategoryAttributeValue,
          this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), commodityCode);
    }
  }

  public List<CategoryAttributeValue> getCategoryAttributeValueList(String commodityCode) {
    List<CategoryAttributeValue> list = null;

    if (StringUtil.hasValueAllOf(commodityCode)) {
      Query query = new SimpleQuery(RelatedCategoryQuery.GET_CATEGORY_CODE_LIST_QUERY, commodityCode);
      list = DatabaseUtil.loadAsBeanList(query, CategoryAttributeValue.class);
    }
    return list;
  }

  // 20120220 os013 add end
  /**
   * add by os014 2012-02-20 插入商品属性到tmall_commodity_property表 param: propertys
   * 属性集合
   */
  @Override
  public ServiceResult updateTmallCommodityProperty(List<TmallCommodityProperty> propertys) {
    TransactionManager tmgr = DIContainer.getTransactionManager();
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    String commodityId = propertys.get(0).getCommodityCode();
    try {
      tmgr.begin(getLoginInfo());
      /**
       * 删除以前的属性
       */
      tmgr.executeUpdate(CatalogQuery.DELETE_BRAND_PROPERTY, commodityId);
      /**
       * 插入属性与属性值
       */
      for (int i = 0; i < propertys.size(); i++) {
        TmallCommodityProperty property = propertys.get(i);
        if (StringUtil.isNull(property.getCommodityCode()) || StringUtil.isNull(property.getValueId())
            || StringUtil.isNull(property.getPropertyId())) {
          result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return result;
        }
        setUserStatus(property);
        tmgr.insert(property);
      }
      tmgr.commit();
    } catch (Exception e) {
      logger.error(e);
      tmgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      tmgr.dispose();
    }
    return result;
  }

  // 判断品牌是否有自定义属性
  public boolean brandInInputStr(String value) {
    // 如果是空值直接返回false
    if (StringUtil.isNullOrEmpty(value)) {
      return false;
    }
    int brandIndex = value.indexOf("20000");
    if (brandIndex >= 0) {
      String frontStr = value.substring(0, brandIndex);
      String backStr = value.substring(brandIndex + 5);
      if (("".equals(frontStr) || frontStr.endsWith(",")) && (("".equals(backStr) || backStr.startsWith(",")))) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  // 搜索产品和发布产品（商品同期到淘宝专用 ）
  public TmallCommodityHeader getProductId(TmallCommodityHeader tmallHeader, CCommodityHeader header, WebshopConfig config,
      TmallService manager, CyncroResult resultMap, long failCount) {

    String keyPropString = "";

    try {
      BrandDao brandDao = DIContainer.getDao(BrandDao.class);
      Brand brand = brandDao.load(header.getShopCode(), header.getBrandCode());

      // 查询是否存在产品
      String proId = manager.getProduct(tmallHeader);
      // 不存在时
      if (StringUtil.isNullOrEmpty(proId)) {
        // 子类目
        // tmallHeader.setCid("50012081");
        // 下列4个属性 如果是必须属性则必须传递
        // 非关键属性
        String propNoKeyString = queryNoKeyProperty(header.getCommodityCode());
        tmallHeader.setNoKeyProps(propNoKeyString);
        // 销售属性
        String propSaleString = querySaleProperty(header.getCommodityCode());
        tmallHeader.setSaleProps(propSaleString);
        // 自定义属性处理
        if (!StringUtil.isNullOrEmpty(tmallHeader.getInputPids())) {
          // 自定义属性
          String customerPropString = "";
          // 自定义ID
          String[] inputPids = tmallHeader.getInputPids().split(",");
          // 自定义名称
          String[] inputStr = tmallHeader.getInputStr().split(",");

          for (int i = 0; i < inputPids.length; i++) {
            customerPropString += inputPids[i] + ":" + inputStr[i].replace(";", ":") + ";";
          }
          // 自定义属性
          if (!(customerPropString.indexOf("20000") >= 0 && customerPropString.indexOf(brand.getBrandName()) >= 0)) {
            customerPropString = "20000:" + brand.getBrandName() + ";" + customerPropString;
          }
          tmallHeader.setCustomerProps(customerPropString.endsWith(";") ? customerPropString.substring(0, customerPropString
              .length() - 1) : customerPropString);
        } else {
          if (brand != null) {
            keyPropString = "20000:" + brand.getTmallBrandCode() + ";";
          }
          // 关键属性查询
          keyPropString += queryProperty(header.getCommodityCode());
          // 关键属性
          tmallHeader.setKeyProps(keyPropString);
        }

        // 产品图片
        tmallHeader.setImageUrl(config.getProductImgUrl());
        // 产品名称
        tmallHeader.setProName(header.getCommodityName());

        tmallHeader.setOuterId(header.getCommodityCode());
        // 新增产品
        proId = manager.insertProduct(tmallHeader);

        if (proId.indexOf("TmallReturn") >= 0) {
          resultMap.addErrorInfo("1", header.getCommodityCode() + "_" + proId);
          failCount++;
        } else {
          tmallHeader.setProId(proId);
        }
      } else {
        tmallHeader.setProId(proId);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tmallHeader;
  }

  /**
   * 发送库存警告邮件
   * 
   * @param sku_Code
   * @param fromStr
   */
  private void sandErrorMail(String sku_Code, String fromStr) {
    // TODO sand emall
    MailInfo mailInfo = new MailInfo();
    StringBuffer sb = new StringBuffer();
    sb.append("以下是接口执行警告<BR><BR>");
    sb.append("商品 " + sku_Code + "库存小于零<BR><BR>");
    sb.append("导入文件来源：" + fromStr);
    mailInfo.setText(sb.toString());
    mailInfo.setSubject("【品店】库存警告");
    mailInfo.setSendDate(DateUtil.getSysdate());

    TmallWarnStockSendMailConfig tmllMailSend = DIContainer.get(TmallWarnStockSendMailConfig.class.getSimpleName());
    mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
    String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
    String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
    MailingService svc = ServiceLocator.getMailingService(getLoginInfo());
    svc.sendImmediate(mailInfo);
  }

  /**
   * 根据商品skuCode取得对应佣金类型
   */
  // 商品分类 佣金类型 缩写代码
  // 食品 6% F
  // 饮料 6% D
  // 美容化妆&个人护理 6% B
  // 母婴产品 3% M
  // 家居清洁用品 6% F
  // 宠物用品 6% P
  // other 6% O
  public String getCommissionByCommodityCode(String commodityCodes) {
    CommodityHeaderDao hdao = DIContainer.getDao(CommodityHeaderDao.class);
    String categoryPath = "";
    String strCommission = "";
    String ct = "";
    try {

      for (String str : commodityCodes.split("\\|")) {
        CommodityHeader chBean = hdao.load("00000000", str);
        categoryPath = chBean.getCategoryPath();

        ct = "";
        if (categoryPath.indexOf("~10~") > 0) { // "食品"
          ct = "F";
        } else if (categoryPath.indexOf("~20~") > 0) { // "酒水/饮料/奶制品"
          ct = "D";
        } else if (categoryPath.indexOf("~30~") > 0) { // "个人护理/美容用品"
          ct = "B";
        } else if (categoryPath.indexOf("~40~") > 0) { // "母婴产品"
          ct = "M";
        } else if (categoryPath.indexOf("~50~") > 0) { // "家居清洁用品"
          ct = "H";
        } else if (categoryPath.indexOf("~60~") > 0) { // "保健和计生用品"
          ct = "O";
        } else if (categoryPath.indexOf("~70~") > 0) { // "进口食品"
          ct = "F";
        } else if (categoryPath.indexOf("~80~") > 0) { // "中秋礼品"
          ct = "F";
        } else if (categoryPath.indexOf("~90~") > 0) { // "宠物"
          ct = "P";
        } else { // "其他"
          ct = "O";
        }
        if (StringUtil.isNullOrEmpty(strCommission)) {
          strCommission = ct;
        } else {
          strCommission = strCommission + "|" + ct;
        }
      }
      return strCommission;

    } catch (Exception e) {
      return "";
    }
  }

  // add by tangweihui 2012-11-16 start
  // commodity_edit/select页面上套装商品区分状态改变时对数据库的操作
  @Override
  public ServiceResult updateSetCommodityFlg(String shopCode, String setCommodityFlg, String commodityCode) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();

    try {
      txMgr.begin(getLoginInfo());
      txMgr.executeUpdate(new SimpleQuery(CommodityDeleteQuery.setCommodityFlgUpdateQuery(), setCommodityFlg, shopCode,
          commodityCode));
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  // add by tangweihui 2012-11-16 end

  // 2012/11/16 促销对应 ob add start
  /**
   *商品是否促销，促销时赠品库存验证
   * 
   * @param 商品标号
   * @param 店铺编号
   * @return 若参加促销，返回赠品最小在库
   */
  public Long campaignAvailability(String commodityCode, String shopCode, List<GiftItem> giftItems) {
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
    Long availableStock = numberPolicy.getMaxTotalAmountNum();
    Long min = null;
    CampainFilter campainFilter = new CampainFilter();
    // 根据商品编号取得正在进行的促销
    List<CampaignMain> campaignMains = campainFilter.getSkuCampaigns(commodityCode);
    if (campaignMains == null || campaignMains.size() < 1) {
      return availableStock;
    }
    for (CampaignMain campaignMain : campaignMains) {
      if (campaignMain.getCampaignType().equals(CampaignMainType.GIFT.longValue())) {
        // 根据促销取得赠品信息
        Query query = new SimpleQuery(CatalogQuery.GET_CAMPAIGN_DOING_GIFT, campaignMain.getCampaignCode());
        List<String> giftcodes = DatabaseUtil.loadAsStringList(query);
        String campaignValue = null;
        if (giftcodes != null && giftcodes.size() > 0) {
          campaignValue = giftcodes.get(0);
        }
        if (!StringUtil.hasValue(campaignValue)) {
          if (min == null) {
            min = availableStock;
          }
          continue;
        }
        String[] giftCodes = campaignValue.split(",");
        for (String giftCode : giftCodes) {
          CommodityHeader commodityHeader = this.getCommodityHeader(shopCode, giftCode);
          if (commodityHeader == null) {
            continue;
          }
          if (commodityHeader.getStockManagementType().equals(StockManagementType.NOSTOCK.longValue())
              || commodityHeader.getStockManagementType().equals(StockManagementType.NONE.longValue())) {
            if (min == null) {
              min = availableStock;
            }
            continue;
          }
          Long quantitys = 0L;
          if (giftItems != null) {
            for (GiftItem giftitem : giftItems) {
              if (giftitem.getGiftCode().equals(giftCode)) {
                quantitys++;
              }
            }
          }
          // 在库验证
          StockUnit stockUnit = new StockUnit();
          stockUnit.setShopCode(shopCode);
          stockUnit.setSkuCode(commodityHeader.getRepresentSkuCode());
          stockUnit.setQuantity(1);
          stockUnit.setStockIODate(DateUtil.getSysdate());
          stockUnit.setLoginInfo(getLoginInfo());
          if (StockUtil.hasStock(stockUnit)) {
            Long num = getAvailableStock(shopCode, commodityHeader.getRepresentSkuCode());
            if (num == 0) {
              // 有效在库为0
              return null;
              // 0以外
            }
            if (giftItems != null) {
              if (num > quantitys) {
                num = num - quantitys;
              } else {
                return null;
              }
            }
            if (min == null || num < min) {
              min = num;
            }
          } else {
            return 0L;
          }
          // 贩卖区间及贩卖Flg验证
          // if (!DateUtil.isPeriodDate(commodityHeader.getSaleStartDatetime(),
          // commodityHeader.getSaleEndDatetime())
          // ||
          // !commodityHeader.getSaleFlg().equals(SaleFlg.FOR_SALE.longValue()))
          // {
          // return null;
          // }
        }
      }
    }
    return min;
  }

  /**
   *根据套餐构成子商品取得套餐构成
   * 
   * @param 商品标号
   * @param 店铺编号
   * @return 套餐构成
   */
  public List<String> getSetCommodityByClildCommodity(String commodityCode, String shopCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_SETCOMMODITY_COMPOSITION_BY_CHILDCOMMODITY2, shopCode, commodityCode,
        commodityCode);
    List<String> setCommodityCompositions = DatabaseUtil.loadAsStringList(query);
    return setCommodityCompositions;
  }

  /**
   *根据套餐构成商品取得套餐构成
   * 
   * @param 商品标号
   * @param 店铺编号
   * @return 套餐构成
   */
  public List<SetCommodityComposition> getSetCommodityCompositipon(String commodityCode, String shopCode) {
    SetCommodityCompositionDao setCompositonDao = DIContainer.getDao(SetCommodityCompositionDao.class);
    List<SetCommodityComposition> setCommodityCompositions = setCompositonDao.findByQuery(
        CatalogQuery.GET_SETCOMMODITY_COMPOSITION, commodityCode, shopCode);
    return setCommodityCompositions;
  }

  /**
   *套餐商品子商品信息取得
   * 
   * @param 商品标号
   * @param 店铺编号
   * @param 套餐构成
   * @return 验证子商品购买可能，如可购买，返回子商品列表
   */
  public List<CommodityContainer> isSetCanBuy(String shopCode, List<SetCommodityComposition> setCompositions,
      SetCommodityInfo setCommodityInfo) {
    List<String> childs = new ArrayList<String>();
    for (SetCommodityComposition set : setCompositions) {
      childs.add(set.getChildCommodityCode());
    }
    // 子商品取得
    Query query = new SimpleQuery(CatalogQuery.getChildSetCommodity(childs), shopCode);
    Query queryCount = new SimpleQuery(CatalogQuery.getDetailSkuCount(childs), shopCode);
    List<ChildSetCommodityInfo> childInfos = DatabaseUtil.loadAsBeanList(query, ChildSetCommodityInfo.class);
    // 规格数判断
    List<String> count = DatabaseUtil.loadAsStringList(queryCount);
    if (count != null && count.size() > 0 && count.get(0).equals(new Integer(childs.size()).toString())) {
      setCommodityInfo.setSingleSku(true);
    }
    // 子商品存在验证
    if (childInfos.size() != setCompositions.size()) {
      return null;
    }
    List<CommodityContainer> commodityContainers = new ArrayList<CommodityContainer>();
    for (ChildSetCommodityInfo child : childInfos) {
      CommodityHeader header = new CommodityHeader();
      header.setCommodityName(child.getCommodityName());
      header.setCommodityCode(child.getCommodityCode());
      header.setCommodityNameJp(child.getCommodityNameJp());
      header.setCommodityNameEn(child.getCommodityNameEn());
      header.setRepresentSkuCode(child.getRepresentSkuCode());
      header.setRepresentSkuUnitPrice(child.getRepresentSkuUnitPrice());
      header.setCommodityTaxType(child.getCommodityTaxType());
      header.setSaleFlag(child.getSaleFlg().toString());
      header.setSaleStartDatetime(child.getSaleStartDatetime());
      header.setSaleEndDatetime(child.getSaleEndDatetime());
      if (child.getDiscountPriceStartDatetime() != null) {
        header.setDiscountPriceStartDatetime(child.getDiscountPriceStartDatetime());
      }
      if (child.getDiscountPriceEndDatetime() != null) {
        header.setDiscountPriceStartDatetime(child.getDiscountPriceEndDatetime());
      }
      CommodityDetail detail = new CommodityDetail();
      detail.setUnitPrice(child.getUnitPrice());
      if (child.getDiscountPrice() != null) {
        detail.setDiscountPrice(child.getDiscountPrice());
      }
      CommodityContainer container = new CommodityContainer();
      container.setCommodityHeader(header);
      container.setCommodityDetail(detail);
      commodityContainers.add(container);
    }

    for (SetCommodityComposition set : setCompositions) {
      // 子商品库存验证
      StockUnit stockUnit = new StockUnit();
      stockUnit.setShopCode(shopCode);
      for (CommodityContainer child : commodityContainers) {
        if (child.getCommodityHeader().getCommodityCode().equals(set.getChildCommodityCode())) {
          stockUnit.setSkuCode(child.getCommodityHeader().getRepresentSkuCode());
          break;
        }
      }

      stockUnit.setQuantity(1);
      stockUnit.setStockIODate(DateUtil.getSysdate());
      stockUnit.setLoginInfo(getLoginInfo());
      if (!StockUtil.hasStock(stockUnit)) {
        return null;
      }
      CommodityAvailability availability = isAvailable(shopCode, stockUnit.getSkuCode(), 1, false);
      if (availability != CommodityAvailability.AVAILABLE) {
        return null;
      }
    }
    return commodityContainers;
  }

  /**
   *套餐商品商品信息取得
   * 
   * @param 商品标号
   * @param 店铺编号
   * @return 验证套餐商品购买可能，如可购买，返回子商品列表
   */
  public SetCommodityInfo getSetComposition(String commodityCode, String shopCode) {
    SetCommodityInfo setCommodityInfo = new SetCommodityInfo();
    List<SetCommodityComposition> setCompositions = getSetCommodityCompositipon(commodityCode, shopCode);

    // 套餐商品组成商品数验证
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
    if (setCompositions == null || setCompositions.size() < numberPolicy.getMinSetNum().intValue()) {
      return null;
    }

    List<CommodityContainer> childCommditys = isSetCanBuy(shopCode, setCompositions, setCommodityInfo);
    if (childCommditys == null || childCommditys.size() != setCompositions.size()) {
      return null;
    }

    // 套餐商品信息获得
    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setSearchShopCode(shopCode);
    condition.setSearchCommodityCode(commodityCode);
    SearchResult<CommodityContainer> commodityContainer = getCommodityContainerBySku(condition);
    if (commodityContainer.isEmpty()) {
      return null;
    }
    CommodityContainer setCommodity = commodityContainer.getRows().get(0);

    // 是否可获取
    CommodityAvailability availability = isAvailable(shopCode, setCommodity.getCommodityHeader().getRepresentSkuCode(), 1, false);

    // 套餐商品不验证库存
    if (availability != CommodityAvailability.AVAILABLE && availability != CommodityAvailability.OUT_OF_STOCK
        && availability != CommodityAvailability.STOCK_SHORTAGE) {
      return null;
    }

    setCommodityInfo.setChildCommodity(childCommditys);
    setCommodityInfo.setSetCommodity(setCommodity);

    return setCommodityInfo;
  }

  public SetCommodityComposition getSuitSalePrice(String commodityCode) {
    SetCommodityComposition result = new SetCommodityComposition();
    Query query = new SimpleQuery(CatalogQuery.GET_SUIT_SALE_PRICE, commodityCode);
    result = DatabaseUtil.loadAsBean(query, SetCommodityComposition.class);
    return result;
  }

  /**
   *套餐构成商品信息取得
   * 
   * @param 商品标号
   * @param 店铺编号
   * @return 套餐构成商品list
   */
  public List<CommodityCompositionContainer> getCommodityCompositionContainerList(String shopCode, String commodityCode) {

    List<CommodityCompositionContainer> result = new ArrayList<CommodityCompositionContainer>();

    SimpleQuery getHeaderListQuery = new SimpleQuery(CatalogQuery.GET_ALL_COMPOSITION_HEADER_QUERY, shopCode, commodityCode);
    SimpleQuery getDetailListQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(CommodityDetail.class)
        + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ORDER BY SKU_CODE ");
    SimpleQuery getStockListQuery = new SimpleQuery(DatabaseUtil.getSelectAllQuery(Stock.class)
        + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?");

    // 构成品取得
    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    List<CommodityHeader> headerList = hDao.findByQuery(getHeaderListQuery);

    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    StockDao sDao = DIContainer.getDao(StockDao.class);

    for (CommodityHeader header : headerList) {
      CommodityCompositionContainer container = new CommodityCompositionContainer();

      container.setShopCode(header.getShopCode());
      container.setCommodityCode(header.getCommodityCode());
      container.setCommodityName(header.getCommodityName());
      container.setCommodityNameEn(header.getCommodityNameEn());
      container.setCommodityNameJp(header.getCommodityNameJp());
      container.setRepresentSkuCode(header.getRepresentSkuCode());
      container.setRepresentSkuUnitPrice(NumUtil.toString(header.getRepresentSkuUnitPrice()));
      container.setCommodityStandard1Name(header.getCommodityStandard1Name());
      container.setCommodityStandard1NameEn(header.getCommodityStandard1NameEn());
      container.setCommodityStandard1NameJp(header.getCommodityStandard1NameJp());
      container.setCommodityStandard2Name(header.getCommodityStandard2Name());
      container.setCommodityStandard2NameEn(header.getCommodityStandard2NameEn());
      container.setCommodityStandard2NameJp(header.getCommodityStandard2NameJp());

      // 详细
      getDetailListQuery.setParameters(header.getShopCode(), header.getCommodityCode());
      List<CommodityDetail> detailList = dDao.findByQuery(getDetailListQuery);
      container.setCommodityDetailList(detailList);

      // 销售状态
      boolean isSalableComposition = false;
      for (CommodityDetail detail : detailList) {
        CommodityAvailability availability = isAvailable(shopCode, detail.getSkuCode(), 1, false);
        if (CommodityAvailability.AVAILABLE.equals(availability)) {
          isSalableComposition = true;
        }
      }
      container.setSalableComposition(isSalableComposition);

      // 在库
      getStockListQuery.setParameters(header.getShopCode(), header.getCommodityCode());
      List<Stock> stockList = sDao.findByQuery(getStockListQuery);
      container.setStockList(stockList);

      container.setStockManagementType(NumUtil.toString(header.getStockManagementType()));

      if (header.getStockManagementType().equals(StockManagementType.WITH_QUANTITY.longValue())
          || header.getStockManagementType().equals(StockManagementType.WITH_STATUS.longValue())) {
        // 在库管理
        container.setAvailableStockQuantity(true);
      } else if (header.getStockManagementType().equals(StockManagementType.NOSTOCK.longValue())
          || header.getStockManagementType().equals(StockManagementType.NONE.longValue())) {
        // 在库不管理及无库存销售
        container.setAvailableStockQuantity(false);
      }
      result.add(container);
    }

    return result;
  }

  /**
   *套餐构成信息取得
   * 
   * @param 商品标号
   * @param 店铺编号
   * @return 套餐构成list
   */
  @Override
  public List<SetCommodityComposition> getSetCommodityInfo(String shopCode, String commodityCode) {
    SetCommodityCompositionDao dao = DIContainer.getDao(SetCommodityCompositionDao.class);
    return dao.load(shopCode, commodityCode);
  }

  /**
   *套餐明细删除
   * 
   * @param 店铺编号
   * @param 商品编号
   * @param 套餐明细商品编号
   */
  @Override
  public ServiceResult deleteSetCommodityComposition(List<SetCommodityComposition> dto, CommodityHeader header,
      CommodityDetail detail) {
    ServiceResultImpl result = new ServiceResultImpl();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    setUserStatus(header);
    setUserStatus(detail);
    txMgr.begin(getLoginInfo());
    try {
      txMgr.update(header);
      txMgr.update(detail);
      for (SetCommodityComposition single : dto) {
        txMgr.delete(single);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  /**
   *套餐明细登录
   * 
   * @param 套餐明细dto
   */
  @Override
  public ServiceResult insertSetCommodityCompositionInfo(SetCommodityComposition dto, CommodityHeader header, CommodityDetail detail) {
    ServiceResultImpl result = new ServiceResultImpl();
    SetCommodityCompositionDao dao = DIContainer.getDao(SetCommodityCompositionDao.class);
    // 重複エラーチェック
    if (dao.exists(dto.getShopCode(), dto.getCommodityCode(), dto.getChildCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    setUserStatus(dto);
    setUserStatus(header);
    setUserStatus(detail);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(header);
      txMgr.update(detail);
      txMgr.insert(dto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  public ServiceResult updateSetCommodityCompositionInfo(SetCommodityComposition dto, CommodityHeader header, CommodityDetail detail) {
    ServiceResultImpl result = new ServiceResultImpl();
    SetCommodityCompositionDao dao = DIContainer.getDao(SetCommodityCompositionDao.class);
    SetCommodityComposition scc = dao.load(dto.getShopCode(), dto.getCommodityCode(), dto.getChildCommodityCode());
    // 重複エラーチェック
    if (scc == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }
    dto.setOrmRowid(scc.getOrmRowid());
    dto.setCreatedUser(scc.getCreatedUser());
    dto.setCreatedDatetime(scc.getCreatedDatetime());
    dto.setUpdatedUser(scc.getUpdatedUser());
    dto.setUpdatedDatetime(scc.getUpdatedDatetime());
    setUserStatus(dto);
    setUserStatus(header);
    setUserStatus(detail);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(header);
      txMgr.update(detail);
      txMgr.update(dto);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public ServiceResult insertTmallSuitCommodity(TmallSuitCommodity suitCommodity) {
    ServiceResultImpl result = new ServiceResultImpl();
    TmallSuitCommodityDao dao = DIContainer.getDao(TmallSuitCommodityDao.class);
    setUserStatus(suitCommodity);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      TmallSuitCommodity oldCommodity = dao.load(suitCommodity.getCommodityCode());
      if (oldCommodity != null) {
        oldCommodity.setStockQuantity(suitCommodity.getStockQuantity() + oldCommodity.getAllocatedQuantity());
        oldCommodity.setScaleValue(suitCommodity.getScaleValue());
        txMgr.update(oldCommodity);
      } else {
        suitCommodity.setAllocatedQuantity(0L);
        txMgr.insert(suitCommodity);
      }

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
      txMgr.rollback();
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  /**
   *商品信息取得
   * 
   * @param 商品标号
   * @param 商品SKU
   * @param 店铺编号
   * @return 商品信息
   */
  public CommodityInfo getCommodityInfoBySkuCode(String shopCode, String commodityCode, String skuCode) {

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return null;
    }

    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    StockDao sDao = DIContainer.getDao(StockDao.class);

    CommodityInfo commodityInfo = new CommodityInfo();
    CommodityHeader header = hDao.load(shopCode, commodityCode);
    CommodityDetail detail;
    Stock stock;
    if (header != null) {
      detail = dDao.load(shopCode, skuCode);
    } else {
      return null;
    }

    if (detail != null) {
      stock = sDao.load(shopCode, detail.getSkuCode());
    } else {
      return null;
    }

    commodityInfo.setHeader(header);
    commodityInfo.setDetail(detail);
    commodityInfo.setStock(stock);

    return commodityInfo;

    // 2012/11/19 促销对应 ob add end
  }

  // 20121122 add by yyq
  @Override
  public List<StockWarnInfo> getStockWarnCommdities() {
    List<StockWarnInfo> result = new ArrayList<StockWarnInfo>();
    SimpleQuery getCCommodityHeaderListQuery = new SimpleQuery(CatalogQuery.GET_STOCK_WARN_COMMODITIES);
    result = DatabaseUtil.loadAsBeanList(getCCommodityHeaderListQuery, StockWarnInfo.class);
    return result;
  }

  // 20121120 促销对应 ob add start
  public CommodityHeader loadBycommodityCode(String shopCode, String commodityCode, boolean flag, boolean resultFlg) {
    CommodityHeaderDao hDao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader header = hDao.loadBycommodityCode(shopCode, commodityCode, flag, resultFlg);
    return header;
  }

  /**
   * 验证套餐商品是否存在
   * 
   * @return
   */
  public boolean isExistsSetCommodityComposition(String shopCode, String commodityCode) {
    SimpleQuery getCCommodityHeaderListQuery = new SimpleQuery(CatalogQuery.GET_SET_COMMODITY_COMPOSITION_BY_COMMODITY_CODE,
        shopCode, commodityCode);
    List<SetCommodityComposition> result = DatabaseUtil.loadAsBeanList(getCCommodityHeaderListQuery, SetCommodityComposition.class);
    if (result != null && result.size() > 0) {
      return true;
    }
    return false;
  }

  // 20121120 促销对应 ob add end

  public List<CampaignMain> getCampaignMainByType(Date createdDatetime) {
    SimpleQuery getCampaignMainListQuery = new SimpleQuery(CatalogQuery.GET_CAMPAIGN_MAIN_TYPE, createdDatetime, createdDatetime,
        CampaignMainType.GIFT.longValue());
    return DatabaseUtil.loadAsBeanList(getCampaignMainListQuery, CampaignMain.class);
  }

  @Override
  public CampaignCondition getCampaignConditionByType(String campaignCode) {
    Query q = new SimpleQuery(CatalogQuery.GET_CAMPAIGN_CONDITION_TYPE, campaignCode);
    return DatabaseUtil.loadAsBean(q, CampaignCondition.class);
  }

  @Override
  public CampaignDoings getCampaignDoingsList(String campaignCode) {
    Query q = new SimpleQuery(CatalogQuery.GET_CAMPAIGN_DOINGS_TYPE, campaignCode);
    return DatabaseUtil.loadAsBean(q, CampaignDoings.class);
  }

  /**
   * 作用于EC系统同期化 add by os014 2012-01-04
   * 
   * @return 同期结果（成功条数，失败条数，同期开始，结束时间，同期错误信息）
   */
  public CyncroResult ecCynchroByCheckBox(String[] commodityCodes) {
    Logger logger = Logger.getLogger(this.getClass());
    CyncroResult resultMap = new CyncroResult();
    logger.info("EC同期化开始.............");
    // 执行开始时间
    long startTime = System.currentTimeMillis();
    // 记录成功条数
    long successCount = 0;
    // 记录失败条数
    long failCount = 0;
    // 获取ec同期化信息
    List<CCommodityHeader> ecList = getCynchEcInfoByCheckBox(commodityCodes);
    // 同期错误集合
    if (ecList.size() <= 0) {
      resultMap.addErrorInfo(new CyncroResult.CyncroError(CyncroResult.CyncroError.ERROR_RANK_HIGHEST, "没有同期数据或者数据已被删除"));
      return resultMap;
    }
    CCommodityHeaderDao ccdao = DIContainer.getDao(CCommodityHeaderDao.class);
    CommodityHeaderDao cDao = DIContainer.getDao(CommodityHeaderDao.class);
    CCommodityDetailDao cdDao = DIContainer.getDao(CCommodityDetailDao.class);
    CommodityDetailDao dDao = DIContainer.getDao(CommodityDetailDao.class);
    TransactionManager tManager = DIContainer.getTransactionManager();
    for (int i = 0; i < ecList.size(); i++) {
      tManager.begin(getLoginInfo());
      CCommodityHeader ccheader = ecList.get(i);
      /**
       * 查询ec表中是否以存在记录 return false 插入，true 更新
       */
      boolean isHave = cDao.exists(ccheader.getShopCode(), ccheader.getCommodityCode());
      CommodityHeader cHeader = buildCommodityHeader(ccheader);
      // 查询得到cc_detail表sku数据

      long result = 0;

      if (isHave) {
        try {
          // 开始ec同期流程。

          String sql = CatalogQuery.CYNCHRO_COMMDITYHEADER_BYCYNCHRO_UPDATE;
          // update by os014 2012-03-06 修改退换货标志通过把退货标志转成二进制后取最后一位
          Long custRetrunFlag = NumUtil.toLong(getCustReturnFlag(cHeader.getReturnFlg() != null ? String.valueOf(cHeader
              .getReturnFlg()) : null));
          if (custRetrunFlag == 1L) {
            custRetrunFlag = 0L;
          } else {
            custRetrunFlag = 1L;
          }
          Object param[] = {
              cHeader.getCommodityName(), cHeader.getCommodityNameEn(), cHeader.getRepresentSkuCode(),
              cHeader.getRepresentSkuUnitPrice(), cHeader.getCommodityDescriptionPc(), cHeader.getCommodityDescriptionMobile(),
              cHeader.getCommoditySearchWords(), cHeader.getSaleStartDatetime(), cHeader.getSaleEndDatetime(),
              cHeader.getDiscountPriceStartDatetime(), cHeader.getDiscountPriceEndDatetime(), cHeader.getCommodityStandard1Name(),
              cHeader.getCommodityStandard2Name(), cHeader.getSaleFlg(), custRetrunFlag, cHeader.getWarningFlag(),
              cHeader.getSaleFlag(), cHeader.getSpecFlag(), cHeader.getBrandCode(), cHeader.getCategoryPath(),
              cHeader.getCategoryAttribute1(), cHeader.getCategoryAttribute1En(), cHeader.getCategoryAttribute1Jp(),
              cHeader.getUpdatedUser(), cHeader.getUpdatedDatetime(), cHeader.getCommodityNameJp(),
              cHeader.getCommodityDescriptionPcEn(), cHeader.getCommodityDescriptionPcJp(),
              cHeader.getCommodityDescriptionMobileEn(), cHeader.getCommodityDescriptionMobileJp(),
              cHeader.getCommodityStandard1NameEn(), cHeader.getCommodityStandard1NameJp(), cHeader.getCommodityStandard2NameEn(),
              cHeader.getCommodityStandard2NameJp(), cHeader.getOriginalPlace(), cHeader.getOriginalPlaceEn(),
              cHeader.getOriginalPlaceJp(), cHeader.getShelfLifeDays(), cHeader.getShelfLifeFlag(), cHeader.getCommodityType(),
              cHeader.getSetCommodityFlg(), cHeader.getImportCommodityType(), cHeader.getClearCommodityType(),
              cHeader.getReserveCommodityType1(), cHeader.getReserveCommodityType2(), cHeader.getReserveCommodityType3(),
              cHeader.getNewReserveCommodityType1(), cHeader.getNewReserveCommodityType2(), cHeader.getNewReserveCommodityType3(),
              cHeader.getNewReserveCommodityType4(), cHeader.getNewReserveCommodityType5(), cHeader.getOriginalCode(),
              cHeader.getKeywordCn1(), cHeader.getKeywordCn2(), cHeader.getKeywordEn1(), cHeader.getKeywordEn2(),
              cHeader.getKeywordJp1(), cHeader.getKeywordJp2(),
              // txw 20130609 add start
              cHeader.getOriginalCommodityCode(),
              cHeader.getCombinationAmount(),
              // txw 20130609 add end
              // txw 20130808 add start
              cHeader.getTitle(), cHeader.getTitleEn(), cHeader.getTitleJp(), cHeader.getDescription(), cHeader.getDescriptionEn(),
              cHeader.getDescriptionJp(), cHeader.getKeyword(), cHeader.getKeywordEn(), cHeader.getKeywordJp(),
              // txw 20130808 add end
              cHeader.getHotFlgEn(), cHeader.getHotFlgJp(), cHeader.getShopCode(), cHeader.getCommodityCode()
          };
          SimpleQuery query = new SimpleQuery(sql, param);
          result = tManager.executeUpdate(query);
          if (result <= 0) {
            failCount++;
            logger.debug("[shopcode is " + cHeader.getShopCode() + "commodityCode is " + cHeader.getCommodityCode()
                + " EC update fail]");
            resultMap.addErrorInfo("2", "商品编号为 : " + cHeader.getCommodityCode() + "的商品EC同期化更新失败");
            continue;
          }
          List<CCommodityDetail> detailList = cdDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), ccheader
              .getShopCode(), ccheader.getCommodityCode());

          // sku 是否更新成功的标志
          boolean detailUpdateSuccess = true;
          for (int j = 0; j < detailList.size(); j++) {
            CCommodityDetail ccDetail = detailList.get(j);
            CommodityDetail detail = buildCommodityDetail(ccheader, ccDetail);

            if (!validateDiscountCommodity(cHeader, detail)) {
              result = -1;
              detailUpdateSuccess = false;
              resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品sku编号为： " + detail.getSkuCode()
                  + "的商品SKU同期化插入失败。失败原因是特价期间输入错误");
              break;
            }

            // 判断sku是否以存在，，true:更新 false:添加
            boolean haved = dDao.exists(detail.getShopCode(), detail.getSkuCode());
            if (haved) {
              Object detailParam[] = {
                  detail.getCommodityCode(), detail.getUnitPrice(), detail.getDiscountPrice(), detail.getStandardDetail1Name(),
                  detail.getStandardDetail1NameEn(), detail.getStandardDetail1NameJp(), detail.getStandardDetail2Name(),
                  detail.getStandardDetail2NameEn(), detail.getStandardDetail2NameJp(), detail.getWeight(), detail.getUseFlg(),
                  detail.getUpdatedUser(), detail.getUpdatedDatetime(), detail.getInnerQuantity(), detail.getShopCode(),
                  detail.getSkuCode()
              };
              query = new SimpleQuery(CatalogQuery.CYNCHRO_COMMODITYDETAIL_UPDATE, detailParam);
              int detailResult = tManager.executeUpdate(query);
              // 如果更新成功将结果置为大于0的值
              if (detailResult > 0) {
                result = 100;
              } else {
                detailUpdateSuccess = false;
                tManager.rollback();
                logger.debug("[shopcode is " + cHeader.getShopCode() + "skucode is " + detail.getSkuCode() + " EC update fail]");
                resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品sku编号为： " + detail.getSkuCode()
                    + "的商品SKU同期化更新失败");
                break;
              }
            } else {
              try {
                tManager.insert(detail);
                result = 100;
              } catch (Exception e) {
                result = -1;
                tManager.rollback();
                detailUpdateSuccess = false;
                logger.debug("商品编号为：" + cHeader.getCommodityCode() + " 商品SKU编号为 : " + detail.getSkuCode() + "的商品SKU同期化插入失败", e);
                resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品sku编号为： " + detail.getSkuCode()
                    + "的商品SKU同期化插入失败");
                break;
              }
            }

          }
          if (!detailUpdateSuccess) {
            failCount++;
            tManager.rollback();
            continue;
          }
          tManager.commit();
        } catch (Exception e) {
          failCount++;
          tManager.rollback();
          logger.error("[shopcode is " + cHeader.getShopCode() + "skucode is " + cHeader.getRepresentSkuCode()
              + "update fail,fail reason is ]" + e.getMessage());
          resultMap.addErrorInfo(new CyncroResult.CyncroError(CyncroResult.CyncroError.ERROR_RANK_GENERAL, "商品编号为："
              + cHeader.getCommodityCode() + "的商品同期化更新失败" + e.getMessage()));
          continue;
        } finally {
          tManager.dispose();
        }
      } else {
        try {
          /**
           * 在库管理区分只能添加不能更新。默认值为2
           */
          CCommodityExt ext = new CCommodityExt();
          CCommodityExtDao extDao = DIContainer.getDao(CCommodityExtDao.class);
          ext = extDao.load(cHeader.getShopCode(), cHeader.getCommodityCode());
          Long stockManagementType = StockManagementType.WITH_QUANTITY.longValue();
          if (ext != null && ext.getOnStockFlag() != null && (ext.getOnStockFlag() == 1 || ext.getOnStockFlag() == 2)) {
            if (1 == ext.getOnStockFlag()) {
              stockManagementType = StockManagementType.WITH_QUANTITY.longValue();
            } else {
              stockManagementType = StockManagementType.NOSTOCK.longValue();
            }
          }
          cHeader.setStockManagementType(stockManagementType);
          setUserStatus(cHeader);
          // result = cDao.insert(cHeader);
          try {
            tManager.insert(cHeader);
          } catch (Exception e) {
            failCount++;
            tManager.rollback();
            logger.debug("[shopcode is " + cHeader.getShopCode() + "skucode is " + cHeader.getRepresentSkuCode()
                + " EC insert header fail,fail reason is ]" + e.getMessage());
            resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + "的商品同期化插入失败");

            continue;
          }
          // sku插入成功标志
          boolean skuSuccess = true;
          // 查询sku信息
          List<CCommodityDetail> detailList = cdDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), ccheader
              .getShopCode(), ccheader.getCommodityCode());
          for (int j = 0; j < detailList.size(); j++) {
            CCommodityDetail ccDetail = detailList.get(j);
            CommodityDetail detail = buildCommodityDetail(ccheader, ccDetail);

            if (!validateDiscountCommodity(cHeader, detail)) {
              skuSuccess = false;
              resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品SKU编号为：" + detail.getSkuCode()
                  + "的商品 SKU同期化插入失败。失败原因是特价期间输入错误");
              break;
            }

            CommodityDetailDao detailDao = DIContainer.getDao(CommodityDetailDao.class);
            setUserStatus(detail);
            CommodityDetail delDetail = detailDao.load(cHeader.getShopCode(), detail.getSkuCode());
            if (delDetail != null) {
              tManager.delete(delDetail);
            }
            try {
              tManager.insert(detail);
            } catch (Exception e) {
              skuSuccess = false;
              logger.debug("[shopcode is " + cHeader.getShopCode() + " skucode is " + detail.getSkuCode()
                  + " EC insert detail fail,fail reason is ]" + e.getMessage());
              resultMap.addErrorInfo("2", "商品编号为：" + cHeader.getCommodityCode() + " 商品SKU编号为：" + detail.getSkuCode()
                  + "的商品 SKU同期化插入失败");
              break;
            }
          }
          // sku插入失败继续下次同期，本次记录为失败
          if (skuSuccess) {
            result = 10;
          } else {
            failCount++;
            tManager.rollback();
            continue;
          }
          tManager.commit();

        } catch (Exception e) {
          failCount += 1;
          logger.error("[shopcode is " + cHeader.getShopCode() + "skucode is " + cHeader.getRepresentSkuCode()
              + "insert fail,fail reason is ]" + e.getMessage());
          tManager.rollback();
          resultMap.addErrorInfo(new CyncroResult.CyncroError(CyncroResult.CyncroError.ERROR_RANK_GENERAL, "商品编号为："
              + cHeader.getCommodityCode() + "的商品同期化插入失败" + e.getMessage()));
          continue;
        } finally {
          tManager.dispose();
        }

      }
      // 每条记录执行完成都需要将同步标志修改为2 并更新同期时间
      if (result > 0) {
        successCount += 1;
        ccdao.updateByQuery(CatalogQuery.CYNCHRO_EC_ISCYNCHRO_INFO_UPDATE, CommoditySyncFlag.SYNC_FINISH.longValue(), new Date(
            startTime), ccheader.getShopCode(), ccheader.getCommodityCode());
      } else {
        failCount += 1;
      }

    }
    // 执行结束时间
    long endTime = System.currentTimeMillis();
    logger.info("EC同期化结束.............");
    // 返回同期结果
    resultMap.setFailCount(failCount);
    resultMap.setSeccessCount(successCount);
    resultMap.setTotalCount(new Long(ecList.size()));
    resultMap.setStartTime(startTime);
    resultMap.setEndTime(endTime);
    return resultMap;
  }

  public CyncroResult tmallCynchroByCheckBox(String[] commodityCodes) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info("淘宝同期化开始.............");
    // 同期开始时间
    long startTime = System.currentTimeMillis();
    // 同期成功记录数
    long successCount = 0;
    // 同期失败记录数
    long failCount = 0;
    // 获取ec同期化信息
    List<CCommodityHeader> tmallList = getCynchTmallInfoByCheckBox(commodityCodes);
    // 同期错误集合
    CyncroResult resultMap = new CyncroResult();
    WebshopConfig config = DIContainer.getWebshopConfig();

    for (CCommodityHeader header : tmallList) {

      try {
        // TMALL 更新 添加处理管理对象（调淘宝接口）
        TmallService manager = ServiceLocator.getTmallService(getLoginInfo());

        // 组装TmallCommodityHeader对象
        TmallCommodityHeader tmallHeader = new TmallCommodityHeader();
        /**
         * 查询商品库存数 param:commodity_code return: long
         */
        Long tmallNum = 0L;
        if (StringUtil.hasValue(header.getOriginalCommodityCode())) {
          // 组合商品分配数量
          tmallNum = StockQuantityUtil.getTmallStockAllocationNum(header.getCommodityCode(), header.getCombinationAmount());
        } else {
          // // 组合商品分配数量
          // Long tmallNum2 =
          // StockQuantityUtil.getOriginalTmallStockAllocationNum(header.getCommodityCode());
          // // 商品库存数量
          // tmallNum =
          // StockQuantityUtil.queryStockNumByCommodityCode(header.getCommodityCode());
          // // 商品库存数量 - 组合商品分配数量
          // tmallNum = tmallNum - tmallNum2;

          tmallNum = StockQuantityUtil.queryTmallNumsByCommodityCode(header.getCommodityCode());

        }

        /**
         * 查询商品header关键属性 QUERY_TMALL_PROPERTYS 格式：100001:201020；100003:201022
         */
        String propString = queryHeaderProperty(header.getCommodityCode());

        tmallHeader.setNum(String.valueOf(tmallNum));
        if (header.getRepresentSkuUnitPrice() == null) {
          resultMap.addErrorInfo("1", "商品编号为" + header.getCommodityCode() + "淘宝代表SKU单价为空");
          failCount++;
          continue;
        }
        if (StringUtil.hasValue(header.getOriginalCommodityCode())) {
          // 组合品商品设定价格
          tmallHeader.setPrice(String.valueOf(BigDecimalUtil.multiply(header.getRepresentSkuUnitPrice(), header
              .getCombinationAmount())));
        } else {
          // 非组合品设定价格
          tmallHeader.setPrice(String.valueOf(header.getRepresentSkuUnitPrice()));
        }
        // 如果是套装商品，初始上传价格10000元
        if (header.getSetCommodityFlg() != null && header.getSetCommodityFlg() == 1L) {
          tmallHeader.setPrice("10000");
        }
        String searchWords = "";
        if (StringUtil.hasValue(header.getTmallCommoditySearchWords())) {
          searchWords = " " + header.getTmallCommoditySearchWords();
        }
        tmallHeader.setTitle("品店 " + header.getCommodityName() + searchWords);
        String tmpDesc = getTmallCommodityDesc(header);

        if (StringUtil.isNotNull(tmpDesc)) {
          tmallHeader.setDesc(tmpDesc);
        }
        tmallHeader.setLocationState("上海");
        tmallHeader.setLocationCity("上海");

        // 生产许可证号(产地：是国外的不能填这个（包含港澳台）)
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityPrdLicenseNo())) {
          tmallHeader.setFoodSecurityPrdLicenseNo(header.getFoodSecurityPrdLicenseNo());
        }
        // 产品标准号(产地：是国外的不能填这个（包含港澳台）)
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityDesignCode())) {
          tmallHeader.setFoodSecurityDesignCode(header.getFoodSecurityDesignCode());
        }
        // 厂名
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityFactory())) {
          tmallHeader.setFoodSecurityFactory(header.getFoodSecurityFactory());
        }
        // 厂址
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityFactorySite())) {
          tmallHeader.setFoodSecurityFactorySite(header.getFoodSecurityFactorySite());
        }
        // 厂家联系方式
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityContact())) {
          tmallHeader.setFoodSecurityContact(header.getFoodSecurityContact());
        }
        // 配料表
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityMix())) {
          tmallHeader.setFoodSecurityMix(header.getFoodSecurityMix());
        }
        // 储藏方法
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityPlanStorage())) {
          tmallHeader.setFoodSecurityPlanStorage(header.getFoodSecurityPlanStorage());
        }
        // 保质期（必须是数字）
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityPeriod())) {
          tmallHeader.setFoodSecurityPeriod(header.getFoodSecurityPeriod());
        }
        // 食品添加剂
        if (!StringUtil.isNullOrEmpty(header.getFoodSecurityFoodAdditive())) {
          tmallHeader.setFoodSecurityFoodAdditive(header.getFoodSecurityFoodAdditive());
        }
        // 供货商
        if (!StringUtil.isNullOrEmpty(header.getFoodSecuritySupplier())) {
          tmallHeader.setFoodSecuritySupplier(header.getFoodSecuritySupplier());
        }
        // 生产开始日期，格式必须为yyyy-MM-dd
        if (header.getFoodSecurityProductDateStart() != null) {
          String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityProductDateStart());
          tmallHeader.setFoodSecurityProductDateStart(str);
        }
        // 生产结束日期,格式必须为yyyy-MM-dd
        if (header.getFoodSecurityProductDateEnd() != null) {
          String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityProductDateEnd());
          tmallHeader.setFoodSecurityProductDateEnd(str);
        }
        // 进货开始日期，要在生产日期之后，格式必须为yyyy-MM-dd
        if (header.getFoodSecurityStockDateStart() != null) {
          String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityStockDateStart());
          tmallHeader.setFoodSecurityStockDateStart(str);
        }
        // 进货结束日期，要在生产日期之后，格式必须为yyyy-MM-dd
        if (header.getFoodSecurityStockDateEnd() != null) {
          String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityStockDateEnd());
          tmallHeader.setFoodSecurityStockDateEnd(str);
        }

        tmallHeader.setCid(header.getTmallCategoryId() != null ? String.valueOf(header.getTmallCategoryId()) : null);
        // add by cs_yuli 20120314 start
        // 获取类目表淘宝分类编号
        List<Category> categoryList = getTmallCategoryCodeList(header.getCommodityCode());
        String tmallCategoryCode = "";
        int categoryCount = 0;
        for (Category category : categoryList) {
          if (categoryCount == 0) {
            tmallCategoryCode = category.getCategoryIdTmall();
          } else {
            tmallCategoryCode += "," + category.getCategoryIdTmall();
          }
          categoryCount++;
        }
        // 自定义类目
        tmallHeader.setSellerCids(tmallCategoryCode);
        // add by cs_yuli 20120314 end

        // 设置用户自定义的属性字符串
        Map<String, String> propsResult = queryPropStrAndPids(header.getCommodityCode(), String
            .valueOf(header.getTmallCategoryId()), header.getBrandCode(), header.getShopCode());

        if (propsResult != null && propsResult.size() > 0) {
          String endString = "";
          if (brandInInputStr(propsResult.get("pids").toString())) {
            BrandDao bDao = DIContainer.getDao(BrandDao.class);
            Brand brand = bDao.load(header.getShopCode(), header.getBrandCode());
            //
            String inputStr = propsResult.get("str").toString();
            StringBuffer brandStr = new StringBuffer("");
            String[] tempStrArr = inputStr.split(",");

            for (int x = 0; x < tempStrArr.length; x++) {
              if (tempStrArr[x].indexOf(brand.getBrandName()) >= 0) {
                String[] tempStrArr1 = tempStrArr[x].split(";");
                for (int y = 0; y < tempStrArr1.length; y++) {
                  // 判断是否包含品牌名
                  if (tempStrArr1[y].indexOf(brand.getBrandName()) >= 0) {
                    if (brandStr.toString().indexOf(brand.getBrandName()) < 0) {
                      brandStr.append(brand.getBrandName()).append(";");
                    }
                  } else {
                    brandStr.append(tempStrArr1[y]).append(";");
                  }
                }
              }
            }
            StringBuffer endStr = new StringBuffer("");
            for (int x = 0; x < tempStrArr.length; x++) {
              if (tempStrArr[x].indexOf(brand.getBrandName()) >= 0) {
                if (endStr.toString().indexOf(brand.getBrandName()) < 0) {
                  endStr.append(brandStr).append(",");
                }
              } else {
                endStr.append(tempStrArr[x]).append(",");
              }
            }
            endString = endStr.toString();
          } else {
            endString = propsResult.get("str").toString();
          }
          endString = endString.replaceAll(";,", ",");
          tmallHeader.setInputStr(endString.endsWith(",") ? endString.substring(0, endString.length() - 1) : endString);
          String pidStr = propsResult.get("pids").toString();
          tmallHeader.setInputPids(pidStr.endsWith(",") ? pidStr.substring(0, pidStr.length() - 1) : pidStr);
        }
        // 如果品牌没有自定义属性
        if (!brandInInputStr(tmallHeader.getInputPids())) {
          BrandDao brandDao = DIContainer.getDao(BrandDao.class);
          Brand brand = brandDao.load(header.getShopCode(), header.getBrandCode());
          if (brand != null) {
            propString = propString + ";20000:" + brand.getTmallBrandCode();
          }
        }
        tmallHeader.setProps(propString);
        CCommodityDetailDao detailDao = DIContainer.getDao(CCommodityDetailDao.class);
        /**
         * 查询商品的sku集合，如果有一个sku使用状态为使用， 将tmall的状态设置为:onsale否则：instock
         */
        List<CCommodityDetail> detailList = detailDao.findByQuery(CommodityDeleteQuery.getCCommoditySkuQuery(), header
            .getShopCode(), header.getCommodityCode());
        CCommodityDetail representSku = new CCommodityDetail();
        String tmallSaleTypeStr = "instock";
        for (CCommodityDetail d : detailList) {
          // 代表sku
          if (d.getSkuCode().equals(header.getRepresentSkuCode())) {
            representSku = d;
          }
          if (d.getTmallUseFlg() == 1) {
            tmallSaleTypeStr = "onsale";
            break;
          }
        }

        // ############################产品部分################################################

        if (NumUtil.isNull(header.getTmallCommodityId()) || header.getTmallCommodityId().equals(0L)) {
          tmallHeader = getProductId(tmallHeader, header, config, manager, resultMap, failCount);
          if (StringUtil.isNullOrEmpty(tmallHeader.getProId())) {
            failCount++;
            continue;
          }
        }
        // ############################产品部分################################################

        // 设置淘宝销售状态
        tmallHeader.setApproveStatus(tmallSaleTypeStr);
        /**
         * 当前商品的库存数是0，淘宝实体bean中的参数ApproveStatus应该等于"instock"
         */
        if (tmallNum == 0L) {
          tmallHeader.setApproveStatus("instock");
        }
        /**
         * 设置商品运费类型编码（按代表sku重量区分） 区分规则从配置文件取
         */

        List<String> deliveryTmplate = config.getDeliveryTemplate();
        if (representSku.getWeight() == null) {
          resultMap.addErrorInfo("2", "商品编号为：" + header.getCommodityCode() + "的商品，淘宝Header同期化更新失败 代表sku重量为空");
          failCount++;
          continue;
        }
        double weight = representSku.getWeight().doubleValue();
        String deliveryType = "";
        for (String str : deliveryTmplate) {
          String[] strs = str.split(",");
          double minWeight = Double.valueOf(strs[0]);
          double maxWeight = Double.valueOf(strs[1]);
          String typeTemp = strs[2];
          if (ValidatorUtil.lessThanOrEquals(minWeight, weight) && ValidatorUtil.lessThanOrEquals(weight, maxWeight)) {
            deliveryType = typeTemp;
            break;
          }
        }
        // 商品重量添加
        CCommodityDetailDao ccdDao = DIContainer.getDao(CCommodityDetailDao.class);
        CCommodityDetail ccd = ccdDao.load("00000000", header.getCommodityCode());
        if (ccd.getWeight() != null) {
          tmallHeader.setWeight(ccd.getWeight().toString());
        }

        // 运费模板
        tmallHeader.setPostageId(deliveryType);
        tmallHeader.setOuterNumiid(header.getCommodityCode());
        // update by os014 2012-03-06 退换货标志用客户的退货标志
        tmallHeader.setSellPromise(getCustReturnFlag(header.getReturnFlg() != null ? String.valueOf(header.getReturnFlg()) : null));
        /**
         * 查询SKU TMALL在庫数,商品明細.EC商品単価,商品明細.SKUコード QUERY_TMALL_SKU_PROPERTYS
         */
        SimpleQuery detailQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_DETAIL_INFO, header.getCommodityCode(), header
            .getShopCode());
        List<Map<String, String>> detail = DatabaseUtil.loadAsMapList(detailQuery);

        /**
         * 组装TmallCommoditySku对象 其中淘宝商品型号 在调淘宝接口返回后设值
         */
        List<TmallCommoditySku> skuList = new ArrayList<TmallCommoditySku>();
        // 淘宝代表sku
        TmallCommoditySku tmallRepresentSku = null;
        if (detail != null) {
          for (int i = 0; i < detail.size(); i++) {
            Map<String, String> detailInfo = new HashMap<String, String>();
            detailInfo = detail.get(i);
            TmallCommoditySku sku = new TmallCommoditySku();
            sku.setQuantity(detailInfo.get("stock_tmall"));
            // ec skuId
            sku.setOuterId(detailInfo.get("sku_code"));
            // 设置淘宝skuid
            sku.setSkuId(detailInfo.get("tmall_sku_id"));
            // 淘宝单价
            sku.setItemPrice(detailInfo.get("tmall_unit_price"));
            // 淘宝物价使用淘宝单价
            sku.setPrice(detailInfo.get("tmall_unit_price"));
            // 将代表sku放到集合的最后
            if (header.getRepresentSkuCode().equals(detailInfo.get("sku_code"))) {
              tmallRepresentSku = new TmallCommoditySku();
              tmallRepresentSku = sku;
              continue;
            }
            skuList.add(sku);
          }
          if (tmallRepresentSku == null) {
            resultMap.addErrorInfo("2", "商品编号为：" + header.getCommodityCode() + "的商品，淘宝Header同期化更新失败 代表sku设置不正确");
            failCount++;
            continue;
          }
          // 将代表sku添加到sku集合
          skuList.add(tmallRepresentSku);
        }

        String tmallCommodityCode = "";
        // 根据淘宝同期时间判断是更新还是添加
        if (header.getSyncTimeTmall() != null && header.getTmallCommodityId() != null) {// 执行更新
          long commodityId = header.getTmallCommodityId();

          /**
           * 只更新 title id desc三个字段，，
           */
          // 组装TmallCommodityHeader对象
          TmallCommodityHeader tmallHeaderUpdate = new TmallCommodityHeader();
          // 设置淘宝商品ID
          tmallHeaderUpdate.setNumiid(String.valueOf(commodityId));
          tmallHeaderUpdate.setDesc(tmallHeader.getDesc());
          tmallHeaderUpdate.setTitle(header.getCommodityName() + header.getTmallCommoditySearchWords());
          searchWords = "";
          if (StringUtil.hasValue(header.getTmallCommoditySearchWords())) {
            searchWords = " " + header.getTmallCommoditySearchWords();
          }
          tmallHeaderUpdate.setTitle("品店 " + header.getCommodityName() + searchWords);

          tmallHeaderUpdate.setCid(tmallHeader.getCid());
          tmallHeaderUpdate.setNum(String.valueOf(tmallNum));
          tmallHeaderUpdate.setProps(tmallHeader.getProps() != null ? tmallHeader.getProps() : "");
          tmallHeaderUpdate.setSellPromise(tmallHeader.getSellPromise()); // 退换货标志
          // 生产许可证号(产地：是国外的不能填这个（包含港澳台）)
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityPrdLicenseNo())) {
            tmallHeaderUpdate.setFoodSecurityPrdLicenseNo(header.getFoodSecurityPrdLicenseNo());
          }
          // 产品标准号(产地：是国外的不能填这个（包含港澳台）)
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityDesignCode())) {
            tmallHeaderUpdate.setFoodSecurityDesignCode(header.getFoodSecurityDesignCode());
          }
          // 厂名
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityFactory())) {
            tmallHeaderUpdate.setFoodSecurityFactory(header.getFoodSecurityFactory());
          }
          // 厂址
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityFactorySite())) {
            tmallHeaderUpdate.setFoodSecurityFactorySite(header.getFoodSecurityFactorySite());
          }
          // 厂家联系方式
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityContact())) {
            tmallHeaderUpdate.setFoodSecurityContact(header.getFoodSecurityContact());
          }
          // 配料表
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityMix())) {
            tmallHeaderUpdate.setFoodSecurityMix(header.getFoodSecurityMix());
          }
          // 储藏方法
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityPlanStorage())) {
            tmallHeaderUpdate.setFoodSecurityPlanStorage(header.getFoodSecurityPlanStorage());
          }
          // 保质期（必须是数字）
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityPeriod())) {
            tmallHeaderUpdate.setFoodSecurityPeriod(header.getFoodSecurityPeriod());
          }
          // 食品添加剂
          if (!StringUtil.isNullOrEmpty(header.getFoodSecurityFoodAdditive())) {
            tmallHeaderUpdate.setFoodSecurityFoodAdditive(header.getFoodSecurityFoodAdditive());
          }
          // 供货商
          if (!StringUtil.isNullOrEmpty(header.getFoodSecuritySupplier())) {
            tmallHeaderUpdate.setFoodSecuritySupplier(header.getFoodSecuritySupplier());
          }
          // 生产开始日期，格式必须为yyyy-MM-dd
          if (header.getFoodSecurityProductDateStart() != null) {
            String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityProductDateStart());
            tmallHeaderUpdate.setFoodSecurityProductDateStart(str);
          }
          // 生产结束日期,格式必须为yyyy-MM-dd
          if (header.getFoodSecurityProductDateEnd() != null) {
            String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityProductDateEnd());
            tmallHeaderUpdate.setFoodSecurityProductDateEnd(str);
          }
          // 进货开始日期，要在生产日期之后，格式必须为yyyy-MM-dd
          if (header.getFoodSecurityStockDateStart() != null) {
            String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityStockDateStart());
            tmallHeaderUpdate.setFoodSecurityStockDateStart(str);
          }
          // 进货结束日期，要在生产日期之后，格式必须为yyyy-MM-dd
          if (header.getFoodSecurityStockDateEnd() != null) {
            String str = (new SimpleDateFormat("yyyy-MM-dd")).format(header.getFoodSecurityStockDateEnd());
            tmallHeaderUpdate.setFoodSecurityStockDateEnd(str);
          }

          // add by lc 2012-04-20 start
          boolean propIsNull = false;
          String skuTmallPrice = "";
          String skuProStr = "";
          for (int i = 0; i < skuList.size(); i++) {
            TmallCommoditySku sku = skuList.get(i);

            // 设置淘宝sku商品型号（淘宝返回）
            sku.setNumiid(tmallCommodityCode);
            /*
             * 查询商品SKU关键属性 QUERY_TMALL_SKU_PROPERTYS
             * 格式：100001:201020；100003:201022
             */
            SimpleQuery propSkuQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_SKU_PROPERTYS, sku.getOuterId(), header
                .getCommodityCode());

            List<Map<String, String>> skuPropertys = DatabaseUtil.loadAsMapList(propSkuQuery);

            // 通过loadAsMapList返回List，如果返回一条，需判断本条数据内容是否为空
            boolean tmpSkuIsNull = false;
            if (skuPropertys.size() == 1) {
              if (StringUtil.isNullOrEmpty(skuPropertys.get(0).get("standard_detail1_id"))
                  && StringUtil.isNullOrEmpty(skuPropertys.get(0).get("standard_detail2_id"))) {
                tmpSkuIsNull = true;
              }
            }

            /**
             * sku属性是否为空的标志 如果为空且sku信息只有一条：不上传sku信息只上传header信息到tmall
             * 且更新c_commodity_detail表时tmall_sku_code用淘宝返回的商品编号
             */
            if (skuList.size() == 1 && (tmpSkuIsNull || skuPropertys.size() == 0)) {
              propIsNull = true;
              skuTmallPrice = sku.getPrice();
              break;
            }
            if (!StringUtil.isNullOrEmpty(sku.getSkuId())) {
              if (!StringUtil.isNullOrEmpty(skuPropertys.get(0).get("standard_detail1_id"))) {
                skuProStr += header.getStandard1Id() + ":" + skuPropertys.get(0).get("standard_detail1_id") + ";";
              }
              if (!StringUtil.isNullOrEmpty(skuPropertys.get(0).get("standard_detail2_id"))) {
                skuProStr += header.getStandard2Id() + ":" + skuPropertys.get(0).get("standard_detail2_id") + ";";
              }
            }
          }
          if (!StringUtil.isNullOrEmpty(skuProStr)) {
            tmallHeaderUpdate.setSkuPro(skuProStr);
          }

          if (propIsNull) {
            if (StringUtil.hasValue(header.getOriginalCommodityCode())) {
              skuTmallPrice = NumUtil
                  .toString(BigDecimalUtil.multiply(NumUtil.parse(skuTmallPrice), header.getCombinationAmount()));
            }
            tmallHeaderUpdate.setPrice(skuTmallPrice);
          }
          // add by lc 2012-04-20 end
          // add by cs_yuli 20120314 start
          tmallHeaderUpdate.setSellerCids(tmallHeader.getSellerCids());// 淘宝分类编号
          // add by cs_yuli 20120314 end
          // 商品重量添加
          if (ccd.getWeight() != null) {
            tmallHeaderUpdate.setWeight(ccd.getWeight().toString());
          }
          tmallHeaderUpdate.setPostageId(deliveryType);
          if (propsResult != null && propsResult.size() > 0) {
            tmallHeaderUpdate.setInputStr(tmallHeader.getInputStr() != null ? tmallHeader.getInputStr() : "");
            tmallHeaderUpdate.setInputPids(tmallHeader.getInputPids() != null ? tmallHeader.getInputPids() : "");
          }
          /**
           * 调用淘宝接口更新记录 return 淘宝商品编号
           */
          tmallCommodityCode = manager.insertOrUpdateCommodityHeader(tmallHeaderUpdate, "UPDATE");

          // 如果淘宝商品ID为空结束本次循环 本次商品同期记录为失败

          if (!StringUtil.isNullOrEmpty(tmallCommodityCode) && tmallCommodityCode.equals("IC_CHECKSTEP_SPU_NOT_EXIST")) {
            tmallHeader = getProductId(tmallHeader, header, config, manager, resultMap, failCount);
            if (StringUtil.isNullOrEmpty(tmallHeader.getProId())) {
              failCount++;
              continue;
            }
            tmallHeaderUpdate.setProId(tmallHeader.getProId());
            tmallCommodityCode = manager.insertOrUpdateCommodityHeader(tmallHeaderUpdate, "UPDATE");
          }

          if (tmallCommodityCode.indexOf("TmallReturn") >= 0) {
            failCount++;
            logger.error("commodity_code为：" + header.getCommodityCode() + "的商品，淘宝Header同期化更新失败");
            // "的商品，淘宝Header同期化更新失败");
            resultMap.addErrorInfo("2", header.getCommodityCode() + "_" + tmallCommodityCode);
            continue;
          }
          // 插入sku信息是否成功的标志 如果sku信息插入失败那此条商品记录上传认定为失败，
          // 在c_commodity_header表中的是否可上传标志不会被修改
          boolean isPass = true;
          // tmall_sku_code
          String tmallSkuCode = "";

          /**
           * 循环处理sku
           */
          for (int i = 0; i < skuList.size(); i++) {
            TmallCommoditySku sku = skuList.get(i);
            // 设置淘宝sku商品型号（淘宝返回）
            sku.setNumiid(tmallCommodityCode);
            /**
             * 查询商品SKU关键属性 QUERY_TMALL_SKU_PROPERTYS
             * 格式：100001:201020；100003:201022
             */
            SimpleQuery propSkuQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_SKU_PROPERTYS, sku.getOuterId(), header
                .getCommodityCode());

            List<Map<String, String>> skuPropertys = DatabaseUtil.loadAsMapList(propSkuQuery);
            /**
             * sku属性是否为空的标志 如果为空且sku信息只有一条：不上传sku信息只上传header信息到tmall
             * 且更新c_commodity_detail表时tmall_sku_code用淘宝返回的商品编号
             */
            boolean propertyIsNull = false;
            if (skuList.size() == 1 && (skuPropertys == null || skuPropertys.size() == 0)) {
              tmallSkuCode = tmallCommodityCode;
              propertyIsNull = true;
            }
            Map<String, String> skuProp = new HashMap<String, String>();
            String skuPropString = "";
            if (!propertyIsNull) {
              skuProp = skuPropertys.get(0);
              if (header.getStandard1Id() != null && skuProp.get("standard_detail1_id") != null) {
                skuPropString = header.getStandard1Id() + ":" + skuProp.get("standard_detail1_id");
              }
              if (header.getStandard2Id() != null && skuProp.get("standard_detail2_id") != null) {
                skuPropString += ";" + header.getStandard2Id() + ":" + skuProp.get("standard_detail2_id");
              }
            }
            sku.setProperties(skuPropString);

            if (StringUtil.isNullOrEmpty(skuPropString)) {
              failCount++;
              continue;
            }
            /**
             * 调用淘宝接口插入detail记录 return 淘宝商品sku编号 sku属性不为空时才上传sku信息
             */
            if (!propertyIsNull) {
              /**
               * 判断sku信息关键属性是否被修改： 修改：将新的sku信息插入到tmall 插入成功后删除tmall中对应的旧的sku信息
               * 没修改：直接update sku信息
               */
              if (tmallPropertyIsChange(sku)) {
                // 查询淘宝保存的旧的sku信息
                TmallCommoditySku tmallSku = getTmallSkuInfo(sku.getSkuId(), tmallCommodityCode);
                // 将新的sku信息插入到淘宝
                tmallSkuCode = insertSkuToTmall(sku);

                // 插入成功后 并且 淘宝skuid不为空（表示淘宝存在该sku）需要删除淘宝保存的旧的sku信息
                if (!StringUtil.isNullOrEmpty(tmallSkuCode) && sku.getSkuId() != null) {
                  deleteTmallSku(tmallSku);
                }
              } else {
                tmallSkuCode = manager.insertOrUpdateCommoditySku(sku, "UPDATE");
              }

            }

            if (StringUtil.isNullOrEmpty(skuPropString) && StringUtil.isNullOrEmpty(tmallSkuCode)) {
              failCount++;
              continue;
            }
            if (tmallSkuCode.indexOf("TmallReturn") >= 0) {
              failCount++;
              isPass = false;
              logger.error("commodity_code为：" + header.getCommodityCode() + "的商品，淘宝SKU同期化更新失败");
              resultMap.addErrorInfo("2", "商品编号:" + header.getCommodityCode() + "sku编号为：" + sku.getOuterId() + "_" + tmallSkuCode);
              break;
            }

            // sku_code更新到商品sku表(c_commodity_detail)
            CCommodityDetailDao cdDao = DIContainer.getDao(CCommodityDetailDao.class);
            CCommodityDetail updateDetail = cdDao.load(header.getShopCode(), sku.getOuterId());
            updateDetail.setTmallSkuId(NumUtil.toLong(tmallSkuCode));
            setUserStatus(updateDetail);

            TransactionManager tmgr = DIContainer.getTransactionManager();
            tmgr.begin(getLoginInfo());
            tmgr.update(updateDetail);
            tmgr.commit();
          }

          if (!isPass) {
            continue;
          }
          successCount++;
        } else {// 执行添加
          /**
           * 调用淘宝接口插入header记录 return 淘宝商品编号
           */
          tmallCommodityCode = manager.insertOrUpdateCommodityHeader(tmallHeader, "INSERT");
          if (tmallCommodityCode.indexOf("TmallReturn") >= 0) {
            failCount++;
            logger.error("commodity_code为：" + header.getCommodityCode() + "的商品，淘宝Header同期化更新失败");
            resultMap.addErrorInfo("2", header.getCommodityCode() + "_" + tmallCommodityCode);
            continue;
          }
          // 插入sku信息是否成功的标志
          boolean isPass = true;
          String tmallSkuCode = "";
          /**
           * 循环处理sku
           */
          for (int i = 0; i < skuList.size(); i++) {
            TmallCommoditySku sku = skuList.get(i);
            // 设置淘宝sku商品型号（淘宝返回）
            sku.setNumiid(tmallCommodityCode);
            /**
             * 查询商品SKU关键属性 QUERY_TMALL_SKU_PROPERTYS
             * 格式：100001:201020；100003:201022
             */
            SimpleQuery propSkuQuery = new SimpleQuery(CatalogQuery.QUERY_TMALL_SKU_PROPERTYS, sku.getOuterId(), header
                .getCommodityCode());
            List<Map<String, String>> skuPropertys = DatabaseUtil.loadAsMapList(propSkuQuery);
            /**
             * sku属性是否为空的标志 如果为空且sku信息只有一条：不上传sku信息只上传header信息到tmall
             * 且更新c_commodity_detail表时tmall_sku_code用淘宝返回的商品编号
             */
            boolean propertyIsNull = false;
            if (skuList.size() == 1 && (skuPropertys == null || skuPropertys.size() == 0)) {
              tmallSkuCode = tmallCommodityCode;
              propertyIsNull = true;
            }
            Map<String, String> skuProp = new HashMap<String, String>();
            String skuPropString = "";
            if (!propertyIsNull) {
              skuProp = skuPropertys.get(0);
              if (header.getStandard1Id() != null && skuProp.get("standard_detail1_id") != null) {
                skuPropString = header.getStandard1Id() + ":" + skuProp.get("standard_detail1_id");
              }
              if (header.getStandard2Id() != null && skuProp.get("standard_detail2_id") != null) {
                skuPropString += ";" + header.getStandard2Id() + ":" + skuProp.get("standard_detail2_id");
              }
            }
            sku.setProperties(skuPropString);
            if (StringUtil.isNullOrEmpty(skuPropString)) {
              continue;
            }
            /**
             * 调用淘宝接口插入detail记录 return 淘宝商品sku编号
             */
            if (!propertyIsNull) {
              tmallSkuCode = manager.insertOrUpdateCommoditySku(sku, "INSERT");
            }
            if (StringUtil.isNullOrEmpty(skuPropString) && StringUtil.isNullOrEmpty(tmallSkuCode)) {
              continue;
            }
            if (tmallSkuCode.indexOf("TmallReturn") >= 0) {
              // sku上传失败时，需要更新商品Header表
              CCommodityHeaderDao ccdao = DIContainer.getDao(CCommodityHeaderDao.class);
              ccdao.updateByQuery(CatalogQuery.CYNCHRO_TMALL_ISCYNCHRO_INFO_UPDATE, CommoditySyncFlag.CAN_SYNC.longValue(),
                  new Date(startTime), tmallCommodityCode, header.getShopCode(), header.getCommodityCode());
              failCount++;
              isPass = false;
              logger.error("commodity_code为：" + header.getCommodityCode() + "的商品，淘宝SKU同期化更新失败");
              resultMap.addErrorInfo("2", "商品编号:" + header.getCommodityCode() + "sku编号为：" + sku.getOuterId() + "_" + tmallSkuCode);
              break;
            }

            // sku_code更新到商品sku表(c_commodity_detail)
            CCommodityDetailDao cdDao = DIContainer.getDao(CCommodityDetailDao.class);
            // cdDao.updateByQuery("", tmallSkuCode);
            CCommodityDetail updateDetail = cdDao.load(header.getShopCode(), sku.getOuterId());
            updateDetail.setTmallSkuId(NumUtil.toLong(tmallSkuCode));
            setUserStatus(updateDetail);
            cdDao.update(updateDetail);
          }

          if (!isPass) {
            continue;
          } else {
            // 成功记录数加1
            successCount++;
          }

        }
        // 每条记录执行完成都需要将同步标志修改为2 并更新同期时间和淘宝商品ID(c_commodity_header)
        CCommodityHeaderDao ccdao = DIContainer.getDao(CCommodityHeaderDao.class);
        ccdao.updateByQuery(CatalogQuery.CYNCHRO_TMALL_ISCYNCHRO_INFO_UPDATE, CommoditySyncFlag.SYNC_FINISH.longValue(), new Date(
            startTime), tmallCommodityCode, header.getShopCode(), header.getCommodityCode());
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    // 执行结束时间
    long endTime = System.currentTimeMillis();
    logger.info("淘宝同期化结束.............");
    // 返回同期结果
    resultMap.setFailCount(failCount);
    resultMap.setSeccessCount(successCount);
    resultMap.setTotalCount(Long.valueOf(tmallList.size()));
    resultMap.setStartTime(startTime);
    resultMap.setEndTime(endTime);
    return resultMap;
  }

  @Override
  public List<Category> getSearchPathList(String name, String code, int num) {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    Query query = new SimpleQuery(CatalogQuery.getFreeCategoryPath(currentLanguageCode, code, name), code, "%~" + code + "~%",
        code, num);
    List<Category> categoryList = DatabaseUtil.loadAsBeanList(query, Category.class);
    return categoryList;
  }

  @Override
  public List<Brand> getBrandList(String name, int num) {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    Query query = new SimpleQuery(CatalogQuery.getFreeBrandName(currentLanguageCode, name), num);
    List<Brand> brandList = DatabaseUtil.loadAsBeanList(query, Brand.class);
    return brandList;
  }

  @Override
  public List<String> getSuggestionList(String name, String languageCode) {
    List<String> names = new ArrayList<String>();
    String str = SqlDialect.getDefault().escape(name).toLowerCase();
    int limitNum = DIContainer.getWebshopConfig().getSuggestSearchNum();
    Query query;
    if (languageCode.equals("zh-cn")) {
      query = new SimpleQuery(CatalogQuery.GET_SUGGESTION_SEARCHWORD, "%" + str + "%", "%" + str + "%", "CN", limitNum);
    } else if (languageCode.equals("ja-jp")) {
      query = new SimpleQuery(CatalogQuery.GET_SUGGESTION_SEARCHWORD, "%" + str + "%", "%" + str + "%", "JP", limitNum);
    } else {
      query = new SimpleQuery(CatalogQuery.GET_SUGGESTION_SEARCHWORD, "%" + str + "%", "%" + str + "%", "EN", limitNum);
    }
    List<CandidateWord> list = DatabaseUtil.loadAsBeanList(query, CandidateWord.class);
    for (CandidateWord c : list) {
      String strSearchWord = "";
      if (c.getSearchWord().length() > 15) {
        strSearchWord = c.getSearchWord().substring(0, 16) + "...";
      } else {
        strSearchWord = c.getSearchWord();
      }
      if (languageCode.equals("zh-cn")) {
        names.add("{" + JsonUtil.getPair("shortLabel", strSearchWord) + "," + JsonUtil.getPair("label", c.getSearchWord()) + ","
            + JsonUtil.getPair("category", "约 " + String.valueOf(c.getHitCount()) + " 商品") + "}");
      } else if (languageCode.equals("ja-jp")) {
        names.add("{" + JsonUtil.getPair("shortLabel", strSearchWord) + "," + JsonUtil.getPair("label", c.getSearchWord()) + ","
            + JsonUtil.getPair("category", "約 " + String.valueOf(c.getHitCount()) + " の結果") + "}");
      } else {
        names.add("{" + JsonUtil.getPair("shortLabel", strSearchWord) + "," + JsonUtil.getPair("label", c.getSearchWord()) + ","
            + JsonUtil.getPair("category", "about " + String.valueOf(c.getHitCount()) + " items") + "}");
      }
    }
    return names;
  }

  @Override
  public Category getCategoryByParenent(String code) {
    Query query = new SimpleQuery(CatalogQuery.GET_CATEGORY_BY_PAREMENT, code);

    Category list = DatabaseUtil.loadAsBean(query, Category.class);
    return list;

  }

  @Override
  public List<Category> getCategorySelf() {
    Query query = new SimpleQuery(CatalogQuery.GET_CATEGORY_BY_SELF);
    List<Category> list = DatabaseUtil.loadAsBeanList(query, Category.class);
    return list;

  }

  @Override
  public ServiceResult updateCategory(String code, String jp, String cn, String en) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    try {
      txMgr.begin(getLoginInfo());
      txMgr
          .executeUpdate(
              "UPDATE CATEGORY SET KEYWORD_JP1 = ?, KEYWORD_CN1 = ?, KEYWORD_EN1 = ? ,UPDATED_USER=?, UPDATED_DATETIME=?   WHERE CATEGORY_CODE = ? ",
              jp, cn, en, this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), code);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public List<Brand> getBrand() {
    BrandDao dao = DIContainer.getDao(BrandDao.class);
    return dao.loadAll();
  }

  @Override
  public ServiceResult updateBrand(String code, String jp, String cn, String en) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    try {
      txMgr.begin(getLoginInfo());
      txMgr
          .executeUpdate(
              "UPDATE BRAND SET KEYWORD_JP1 = ?,KEYWORD_CN1 = ?,KEYWORD_EN1 = ? ,UPDATED_USER=?, UPDATED_DATETIME=? WHERE BRAND_CODE = ?",
              jp, cn, en, this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), code);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public List<CCommodityHeader> getCCommodityHeader() {
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    return dao.loadAll();
  }

  // 2014/4/28 京东WBS对应 ob_李 add start
  public CCommodityHeader getCCommodityHeader(String shopCode, String commodityCode) {
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    return dao.load(shopCode, commodityCode);
  }

  // 2014/4/28 京东WBS对应 ob_李 add end
  @Override
  public List<CommodityHeader> getCommodityHeader() {

    CommodityHeaderDao dao = DIContainer.getDao(CommodityHeaderDao.class);
    return dao.loadAll();
  }

  @Override
  public ServiceResult updateCCommodityHeader(String code, String jp, String cn, String en) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    try {
      txMgr.begin(getLoginInfo());
      txMgr
          .executeUpdate(
              "UPDATE C_COMMODITY_HEADER SET KEYWORD_JP1 = ?,KEYWORD_CN1 = ?,KEYWORD_EN1 = ?  ,UPDATED_USER=?, UPDATED_DATETIME=? WHERE COMMODITY_CODE = ?",
              jp, cn, en, this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), code);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public ServiceResult updateCommodityKeyword(String code, String jp, String cn, String en, String jp2, String cn2, String en2) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    try {
      txMgr.begin(getLoginInfo());
      txMgr
          .executeUpdate(
              "UPDATE COMMODITY_HEADER SET KEYWORD_JP1 = ?,KEYWORD_CN1 = ?,KEYWORD_EN1 = ?, KEYWORD_JP2 = ?, KEYWORD_CN2 = ?, KEYWORD_EN2 = ? ,UPDATED_USER=?, UPDATED_DATETIME=? WHERE COMMODITY_CODE = ?",
              jp, cn, en, jp2, cn2, en2, this.getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), code);
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public Brand getBrand(String code) {
    BrandDao dao = DIContainer.getDao(BrandDao.class);
    return dao.load(code);
  }

  @Override
  public List<CategorySel> getCategorys(String code) {
    Query query = new SimpleQuery(CatalogQuery.GET_CATEGORYSEL_ALL, code);
    List<CategorySel> list = DatabaseUtil.loadAsBeanList(query, CategorySel.class);
    return list;
  }

  @Override
  public SearchKeywordLog getSearchCount(String searchWord) {
    Query query = new SimpleQuery(CatalogQuery.GET_SEARCH_KEYWORD_LOG_ALL, searchWord);
    if (DatabaseUtil.loadAsBean(query, SearchKeywordLog.class) != null) {
      return DatabaseUtil.loadAsBean(query, SearchKeywordLog.class);
    }
    return null;
  }

  @Override
  public ResultBean getCommodityHeaderCount(String keyword, String languageCode) {
    String[] strList = keyword.split(" ");
    String[] newStrList = new String[strList.length * 2];
    for (int i = 0; i < strList.length; i++) {
      newStrList[2 * i] = "%" + strList[i].toLowerCase() + "%";
      newStrList[2 * i + 1] = "%" + strList[i].toLowerCase() + "%";
    }

    Query query = new SimpleQuery(CatalogQuery.getCommodityHeaderCountByKeyword(strList, languageCode), newStrList);
    return DatabaseUtil.loadAsBean(query, ResultBean.class);
  }

  @Override
  public CandidateWord getCandidateWord(String keyword, String language) {
    Query query = new SimpleQuery(CatalogQuery.GET_CANDIDATE_WORD_ALL, keyword, language);
    return DatabaseUtil.loadAsBean(query, CandidateWord.class);
  }

  @Override
  public ServiceResult addCandidateWord(CandidateWord candidateword) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    Long orm_rowid = DatabaseUtil.generateSequence(SequenceType.STOCK_TEMP_NO);
    try {
      txMgr.begin(getLoginInfo());
      txMgr
          .executeUpdate(
              "INSERT INTO CANDIDATE_WORD(SEARCH_WORD, PINYIN, SEARCH_COUNT,ORM_ROWID,CREATED_USER, CREATED_DATETIME,UPDATED_USER, UPDATED_DATETIME, HIT_COUNT, LANG) VALUES (?, ?, ?, ?,?, ?,?,?,?,?)",
              candidateword.getSearchWord(), candidateword.getPinyin(), candidateword.getSearchCount(), orm_rowid, this
                  .getLoginInfo().getRecordingFormat(), DateUtil.getSysdate(), this.getLoginInfo().getRecordingFormat(), DateUtil
                  .getSysdate(), candidateword.getHitCount(), candidateword.getLang());
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public ServiceResult updateCandidateWord(CandidateWord candidateword) {
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();
    try {
      // 更新の場合はPinyinの更新は行わない
      txMgr.begin(getLoginInfo());
      txMgr
          .executeUpdate(
              "UPDATE CANDIDATE_WORD SET SEARCH_COUNT=?, HIT_COUNT=?,UPDATED_USER=?, UPDATED_DATETIME=?, LANG=? WHERE SEARCH_WORD = ? AND LANG = ?",
              candidateword.getSearchCount(), candidateword.getHitCount(), this.getLoginInfo().getRecordingFormat(), DateUtil
                  .getSysdate(), candidateword.getLang(), candidateword.getSearchWord(), candidateword.getLang());
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public Category getCategoryListForPath(String code) {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    Query query = new SimpleQuery(CatalogQuery.getCategoryForPath(currentLanguageCode), code);
    Category category = DatabaseUtil.loadAsBean(query, Category.class);
    return category;
  }

  // 2013/6/7 组合商品登录 zhangzhengtao add strat
  @Override
  public List<CCommodityDetail> getCCommodityDetailByCommodityCode(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_C_COMMODITY_DETAIL_BY_COMMODITYCODE, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, CCommodityDetail.class);
  }

  // 2014/4/28 京东WBS对应 ob_李 add start
  public CCommodityDetail getCCommodityDetail(String shopCode, String commodityCode) {
    CCommodityDetailDao dao = DIContainer.getDao(CCommodityDetailDao.class);
    return dao.load(shopCode, commodityCode);
  }

  @Override
  public List<String> getJdCommodiytyPropertyIdList(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_JD_COMMODITY_PROPERTY_ID, commodityCode);
    return DatabaseUtil.loadAsStringList(query);
  }

  @Override
  public List<JdCommodityProperty> getJdCommodiytyPropertyValueList(String commodityCode, String propertyId) {
    Query query = new SimpleQuery(CatalogQuery.GET_JD_COMMODITY_PROPERTY_VALUE_LIST, commodityCode, propertyId);
    return DatabaseUtil.loadAsBeanList(query, JdCommodityProperty.class);
  }

  // 2014/4/28 京东WBS对应 ob_李 add end
  @Override
  public List<CCommodityHeader> getCCommodityHeaderByOriginalCommodityCode(String originalCommodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_C_COMMODITY_HEADER_BY_ORIGINALCOMMODITYCODE, originalCommodityCode,
        originalCommodityCode);
    return DatabaseUtil.loadAsBeanList(query, CCommodityHeader.class);
  }

  @Override
  public ServiceResult deleteCCommodityDetailByCommodityCode(String commodityCode) {
    ServiceResultImpl result = new ServiceResultImpl();
    if (!StringUtil.hasValueAllOf(commodityCode)) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      for (String deleteQuery : CommodityDeleteQuery.getstituteDeleteQuery()) {
        txMgr.executeUpdate(deleteQuery, commodityCode);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      result.addServiceError(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  // 2013/6/7 组合商品登录 zhangzhengtao add end

  @Override
  public List<CCommodityHeader> getCode(String code) {
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_CODE, code);
    return DatabaseUtil.loadAsBeanList(query, CCommodityHeader.class);
  }

  @Override
  public List<CCommodityHeader> getOriginalCode(String code) {
    Query query = new SimpleQuery(CatalogQuery.ORIGINAL_COMMODITY_CODE, code);
    return DatabaseUtil.loadAsBeanList(query, CCommodityHeader.class);
  }

  @Override
  public ServiceResult addTmallStockAllocation(TmallStockAllocation tamll) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    tamll.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    setUserStatus(tamll);
    TransactionManager tmgr = DIContainer.getTransactionManager();
    try {
      tmgr.begin(getLoginInfo());
      tmgr.insert(tamll);
      tmgr.commit();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      tmgr.rollback();
    } finally {
      tmgr.dispose();
    }
    return result;
  }

  @Override
  public boolean isTmallStockAllocation(String code) {
    SimpleQuery getCCommodityHeaderListQuery = new SimpleQuery(CatalogQuery.GET_IS_TMALLSTOCKALLOCATION, code);
    List<TmallStockAllocation> result = DatabaseUtil.loadAsBeanList(getCCommodityHeaderListQuery, TmallStockAllocation.class);
    if (result != null && result.size() > 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isCCommodityHeader(String code) {
    SimpleQuery getCCommodityHeaderListQuery = new SimpleQuery(CatalogQuery.GET_IS_CCommodityHeader, code);
    List<CCommodityHeader> result = DatabaseUtil.loadAsBeanList(getCCommodityHeaderListQuery, CCommodityHeader.class);
    if (result != null && result.size() > 0) {
      return true;
    }
    return false;
  }

  public List<CustomerCommodity> getCusCommodityList() {
    SimpleQuery getCCommodityHeaderListQuery = new SimpleQuery(CatalogQuery.GET_CUSTOMER_COMMODITY_ALL);
    List<CustomerCommodity> result = DatabaseUtil.loadAsBeanList(getCCommodityHeaderListQuery, CustomerCommodity.class);
    return result;
  }

  // 2014/06/05 库存更新对应 ob_卢 add start
  // 京东商品获得
  public JdStockInfo getOrgJdStockInfo(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_JD_STOCK_INFO_BY_QUERY, commodityCode);
    return DatabaseUtil.loadAsBean(query, JdStockInfo.class);
  }

  // 获得全部组合品
  @Override
  public List<JdStockInfo> getJdStockInfo(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_JD_STOCK_INFO_LIST_BY_QUERY, commodityCode);
    List<JdStockInfo> list = DatabaseUtil.loadAsBeanList(query, JdStockInfo.class);
    return list;
  }

  // 天猫库存比例设定和库存再分配
  public ServiceResult updateTmallStockAll(String commodityCode, List<TmallStockAllocation> stockAlloctionList,
      List<String> tmallApiFailCodeList, List<String> jdApiFailCodeList, List<String> stockFailCodeList) {
    // 初始化同期错误集合
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // TMALL批处理
    JdService service = ServiceLocator.getJdService(getLoginInfo());
    int returnInt = 0;
    returnInt = service.jdOrderDownlaod("", "");
    if (returnInt == 0) {
      logger.debug("京东订单下载批处理成功!");
    } else if (returnInt == OrderDownLoadStatus.DOWNLOADFAILED.longValue().intValue()) {
      logger.debug("京东订单下载过程出现错误!");
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
      return serviceResult;
    }

    List<TmallStockAllocation> tmallList = new ArrayList<TmallStockAllocation>();
    TmallStockAllocationDao dao = DIContainer.getDao(TmallStockAllocationDao.class);
    for (TmallStockAllocation tsa : stockAlloctionList) {
      TmallStockAllocation tmallStock = dao.load(tsa.getShopCode(), tsa.getCommodityCode());
      tmallStock.setScaleValue(tsa.getScaleValue());
      setUserStatus(tmallStock);
      tmallList.add(tmallStock);
    }
    for (TmallStockAllocation tsa : tmallList) {
      ValidationSummary summary = BeanValidator.validate(tsa);
      if (summary.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return serviceResult;
      }
    }
    StockTempInfo stockTempInfo = new StockTempInfo();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    // 锁定库存表
    StockDao stockDao = DIContainer.getDao(StockDao.class);
    Stock stock = stockDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), commodityCode);
    stock.setStockQuantity(stock.getStockQuantity() + stock.getStockThreshold());
    setUserStatus(stock);
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(stock);
      for (TmallStockAllocation tsa : tmallList) {
        txMgr.update(tsa);
      }
      // 库存变更
      StockService stockService = ServiceLocator.getStockService(getLoginInfo());
      serviceResult = (ServiceResultImpl) stockService.stockRecalculation(txMgr, commodityCode, tmallApiFailCodeList,
          jdApiFailCodeList, stockFailCodeList, stockTempInfo);
      if (serviceResult.hasError()) {
        txMgr.rollback();
        return serviceResult;
      }
      // 库存增量登录
      stockService.updateStockTemp(txMgr, stockTempInfo);
      txMgr.commit();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.RUN_TIME_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  // 京东库存比例设定和库存再分配
  public ServiceResult updateJdStockAll(String commodityCode, List<JdStockAllocation> stockAlloctionList,
      List<String> tmallApiFailCodeList, List<String> jdApiFailCodeList, List<String> stockFailCodeList) {

    // 初始化同期错误集合
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    // TMALL批处理
    JdService service = ServiceLocator.getJdService(getLoginInfo());
    int returnInt = 0;
    returnInt = service.jdOrderDownlaod("", "");
    if (returnInt == 0) {
      logger.debug("京东订单下载批处理成功!");
    } else if (returnInt == OrderDownLoadStatus.DOWNLOADFAILED.longValue().intValue()) {
      logger.debug("京东订单下载过程出现错误!");
      serviceResult.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
      return serviceResult;
    }

    List<JdStockAllocation> jdList = new ArrayList<JdStockAllocation>();
    JdStockAllocationDao dao = DIContainer.getDao(JdStockAllocationDao.class);
    for (JdStockAllocation tsa : stockAlloctionList) {
      JdStockAllocation tmallStock = dao.load(tsa.getShopCode(), tsa.getCommodityCode());
      tmallStock.setScaleValue(tsa.getScaleValue());
      setUserStatus(tmallStock);
      jdList.add(tmallStock);
    }
    for (JdStockAllocation tsa : jdList) {
      ValidationSummary summary = BeanValidator.validate(tsa);
      if (summary.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return serviceResult;
      }
    }

    StockTempInfo stockTempInfo = new StockTempInfo();
    TransactionManager txMgr = DIContainer.getTransactionManager();

    // 锁定库存表
    StockDao stockDao = DIContainer.getDao(StockDao.class);
    Stock stock = stockDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), commodityCode);
    stock.setStockQuantity(stock.getStockQuantity() + stock.getStockThreshold());
    setUserStatus(stock);
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(stock);
      for (JdStockAllocation tsa : jdList) {
        txMgr.update(tsa);
      }
      // 库存变更
      StockService stockService = ServiceLocator.getStockService(getLoginInfo());
      serviceResult = (ServiceResultImpl) stockService.stockRecalculation(txMgr, commodityCode, tmallApiFailCodeList,
          jdApiFailCodeList, stockFailCodeList, stockTempInfo);
      if (serviceResult.hasError()) {
        txMgr.rollback();
        return serviceResult;
      }
      // 库存增量登录
      stockService.updateStockTemp(txMgr, stockTempInfo);
      txMgr.commit();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      txMgr.rollback();
      serviceResult.addServiceError(CommonServiceErrorContent.RUN_TIME_ERROR);
    } finally {
      txMgr.dispose();
    }
    return serviceResult;
  }

  // 2014/06/05 库存更新对应 ob_卢 add end

  public TmallStockInfo getOrgTmallStockInfo(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_TMALL_STOCK_INFO_BY_QUERY, commodityCode);
    return DatabaseUtil.loadAsBean(query, TmallStockInfo.class);
  }

  @Override
  public List<TmallStockInfo> getTmallStockInfo(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_TMALL_STOCK_INFO_LIST_BY_QUERY, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, TmallStockInfo.class);
  }

  public ServiceResult updateTmallStockAllocation(TmallStockAllocation tmallStockAllocation) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    TmallStockAllocation result = getTmallStockAllocation(tmallStockAllocation.getCommodityCode());
    if (result == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return serviceResult;
    }

    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(getLoginInfo());

      tmallStockAllocation.setOrmRowid(result.getOrmRowid());
      tmallStockAllocation.setCreatedUser(result.getCreatedUser());
      tmallStockAllocation.setCreatedDatetime(result.getCreatedDatetime());
      tmallStockAllocation.setUpdatedDatetime(result.getUpdatedDatetime());

      setUserStatus(tmallStockAllocation);
      ValidationSummary resultList = BeanValidator.validate(tmallStockAllocation);
      if (resultList.hasError()) {
        serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
        return serviceResult;
      } else {
        manager.update(tmallStockAllocation);
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

  public TmallStockAllocation getTmallStockAllocation(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_TMALL_STOCK_ALLOCATION_BY_COMMODITY_CODE, commodityCode);
    return DatabaseUtil.loadAsBean(query, TmallStockAllocation.class);
  }

  @Override
  public ServiceResult addCdetail(CCommodityDetail detail) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    detail.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    CCommodityDetailDao detaildao = DIContainer.get(CCommodityDetailDao.class.getSimpleName());
    // 商品ヘッダ の重複チェック
    if (detaildao.exists(detail.getShopCode(), detail.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    setUserStatus(detail);
    TransactionManager tmgr = DIContainer.getTransactionManager();
    try {
      tmgr.begin(getLoginInfo());
      tmgr.insert(detail);
      tmgr.commit();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      tmgr.rollback();
    } finally {
      tmgr.dispose();
    }
    return result;
  }

  @Override
  public ServiceResult addStock(Stock stock) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    stock.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    StockDao dao = DIContainer.get(StockDao.class.getSimpleName());
    // 商品ヘッダ の重複チェック
    if (dao.exists(stock.getShopCode(), stock.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    setUserStatus(stock);
    TransactionManager tmgr = DIContainer.getTransactionManager();
    try {
      tmgr.begin(getLoginInfo());
      tmgr.insert(stock);
      tmgr.commit();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      tmgr.rollback();
    } finally {
      tmgr.dispose();
    }
    return result;
  }

  @Override
  public ServiceResult addCategoryCommodity(CategoryCommodity comm) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    comm.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    CategoryCommodityDao dao = DIContainer.get(CategoryCommodityDao.class.getSimpleName());
    // 商品ヘッダ の重複チェック
    if (dao.exists(comm.getShopCode(), comm.getCategoryCode(), comm.getCommodityCode())) {
      result.addServiceError(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR);
      return result;
    }
    setUserStatus(comm);
    TransactionManager tmgr = DIContainer.getTransactionManager();
    try {
      tmgr.begin(getLoginInfo());
      tmgr.insert(comm);
      tmgr.commit();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      tmgr.rollback();
    } finally {
      tmgr.dispose();
    }
    return result;
  }

  @Override
  public List<CommodityHeadline> getCommodityHeaderBySaleFlg() {
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_HEADER_BY_SALE_FLG);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  @Override
  public Long getCommodityExtOnStockFlg(String Commodity) {
    Query query = new SimpleQuery(CatalogQuery.GET_ON_STOCK_FLAG, Commodity);
    return Long.valueOf(DatabaseUtil.executeScalar(query).toString());
  }

  // 2014/06/05 库存更新对应 ob_卢 update start
  // public ServiceResult addNewCommodityObjects(CCommodityHeader cheader,
  // CCommodityDetail detail, Stock stock,
  // CCommodityExt cceBean, CategoryCommodity commodity, CategoryAttributeValue
  // buteValue,
  // TmallStockAllocation tamllStockAllocation) {
  public ServiceResult addNewCommodityObjects(CCommodityHeader cheader, CCommodityDetail detail, Stock stock,
      CCommodityExt cceBean, CategoryCommodity commodity, CategoryAttributeValue buteValue,
      TmallStockAllocation tamllStockAllocation, JdStockAllocation jdStockAllocation) {
    // 2014/06/05 库存更新对应 ob_卢 update end
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    ServiceResultImpl result = new ServiceResultImpl();

    cheader.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    detail.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    stock.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    tamllStockAllocation.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    setUserStatus(cheader);
    setUserStatus(detail);
    setUserStatus(stock);
    setUserStatus(tamllStockAllocation);
    // 2014/06/05 库存更新对应 ob_卢 add start
    setUserStatus(jdStockAllocation);
    // 2014/06/05 库存更新对应 ob_卢 add end
    if (cceBean != null) {
      cceBean.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
      setUserStatus(cceBean);
    }
    if (commodity != null) {
      commodity.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
      setUserStatus(commodity);
    }
    if (buteValue != null) {
      buteValue.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
      setUserStatus(buteValue);
    }
    ValidationSummary summary = BeanValidator.validate(cheader);
    if (summary.hasError()) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (String error : summary.getErrorMessages()) {
        logger.error(error);
      }
      return result;
    }

    try {
      txMgr.begin(getLoginInfo());
      txMgr.insert(cheader);
      txMgr.insert(detail);
      txMgr.insert(stock);
      txMgr.insert(tamllStockAllocation);
      // 2014/06/05 库存更新对应 ob_卢 add start
      txMgr.insert(jdStockAllocation);
      // 2014/06/05 库存更新对应 ob_卢 add end
      if (commodity != null) {
        txMgr.insert(commodity);
      }
      if (cceBean != null) {
        txMgr.insert(cceBean);
      }
      if (buteValue != null) {
        txMgr.insert(buteValue);
      }
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }
    return result;
  }

  @Override
  public DiscountCommodity getDiscountCommodityByCommodityCode(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_DISCOUNT_COMMODITY, commodityCode);
    return DatabaseUtil.loadAsBean(query, DiscountCommodity.class);
  }

  @Override
  public Long getDiscountTypeByOrderDetail(String commodityCode, String OrderNo, Long discountType, BigDecimal price) {
    Query query = new SimpleQuery(CatalogQuery.GET_DISCOUNT_TYPE, commodityCode, discountType, price, OrderNo);
    Object ob = DatabaseUtil.executeScalar(query);
    if (ob != null) {
      return Long.valueOf(ob.toString());
    }
    return 999L;
  }

  @Override
  public DiscountHeader getDiscountHeaderByCommodityCode(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_DISCOUNT_HEADER, commodityCode);
    return DatabaseUtil.loadAsBean(query, DiscountHeader.class);
  }

  @Override
  public List<CommodityHeadline> getDiscountPlan(String languageCode) {

    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.getCommodityPlanDiscountEveryLanguageCode(languageCode));
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;

  }

  @Override
  public DiscountHeader getDiscountHeaderLeast() {
    Query query = new SimpleQuery(CatalogQuery.GET_DISCOUNT_HEADER_LEAST);
    return DatabaseUtil.loadAsBean(query, DiscountHeader.class);
  }

  @Override
  public List<jp.co.sint.webshop.data.dto.CampaignMain> getCampaignMainList() {
    Query query = new SimpleQuery(CatalogQuery.GET_CAMPAIGN_MAIN);
    return DatabaseUtil.loadAsBeanList(query, jp.co.sint.webshop.data.dto.CampaignMain.class);
  }

  @Override
  public List<CommodityHeadline> getNewCommodityPlan(Long amount) {
    Query query = new SimpleQuery(CatalogQuery.GET_NEW_COMMODITY_PLAN, amount);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  @Override
  public List<CommodityHeadline> getIndexBatchCommodity(Long type) {
    // Query query = new
    // SimpleQuery(CatalogQuery.GET_INDEX_BATCH_COMMODITY,type);
    // return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.GET_INDEX_BATCH_COMMODITY, type);
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;
  }

  @Override
  public List<CommodityHeadline> getIndexBatchCommodityNoCache(Long type) {
    Query query = new SimpleQuery(CatalogQuery.GET_INDEX_BATCH_COMMODITY, type);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  @Override
  public List<CommodityHeadline> getHotSaleCommodity(String languageCode) {
    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.GET_HOT_SALE_COMMODITY, languageCode);
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;
  }

  @Override
  public List<CommodityHeadline> getHotSaleCommodityNoCache(String languageCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_HOT_SALE_COMMODITY, languageCode);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  @Override
  public List<CommodityHeadline> getHotCommodityPlanByCategoryPath(String categoryPath1, String categoryPath2,
      String categoryPath3, String categoryPath4, String categoryPath5, String categoryPath6, String categoryPath7,
      String categoryPath8, Long amount) {
    Query query = new SimpleQuery(CatalogQuery.GET_HOT_COMMODITY_PLAN_BY_CATEGORY_PATH, categoryPath1, categoryPath2,
        categoryPath3, categoryPath4, categoryPath5, categoryPath6, categoryPath7, categoryPath8, amount);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  @Override
  public List<CommodityHeader> getHotCommodityPlanByBrand(String brandCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_HOT_COMMODITY_PLAN_BY_BRAND, brandCode);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeader.class);
  }

  @Override
  public List<CommodityHeadline> getHotCommodityPlan(Long amount) {
    Query query = new SimpleQuery(CatalogQuery.GET_HOT_COMMODITY_PLAN, amount);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  @Override
  public List<CommodityHeadline> getAvaCommodityPlan(String categoryPath, int num) {
    Query query = new SimpleQuery(CatalogQuery.GET_AVA_COMMODITY_PLAN, "%~" + categoryPath + "%", num);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  @Override
  public List<CommodityHeadline> getCommodityPlanFirstTime(String categoryCode) {
    // Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_PLAN_FIRST_TIME,
    // "%~" + categoryCode + "%");
    // return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_PLAN_FIRST_TIME, "%~" + categoryCode + "%");
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;
  }

  @Override
  public List<CommodityHeadline> getCommodityByBrandEach() {
    // Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_BY_BRAND_EACH);
    // return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_BY_BRAND_EACH);
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;
  }

  @Override
  public List<CommodityHeadline> getCommodityByFreePostage() {
    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_BY_FREE_POSTAGE);
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;
  }

  @Override
  public List<CommodityHeadline> getCommodityPlanSomeCategoty(String[] strList) {
    String[] newStrList = null;
    if (strList.length > 4) {
      newStrList = new String[4];
    } else {
      newStrList = new String[strList.length];
    }
    for (int i = 0; i < newStrList.length; i++) {
      newStrList[i] = "%" + strList[i] + "%";
    }
    String sql = CatalogQuery.getCommodityPlanSomeCategoty(newStrList);
    // Query query = new SimpleQuery(sql, newStrList);
    // return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);

    CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(sql, newStrList);
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;

  }

  @Override
  public Long getHistoryBuyAmount(String commodityCode, String customerCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_HISTORY_BUY_AMOUNT, commodityCode, commodityCode, 4, customerCode);
    Object ob = DatabaseUtil.executeScalar(query);
    if (ob != null) {
      return Long.valueOf(ob.toString());
    }
    return 0L;
  }

  @Override
  public Long getHistoryBuyAmountTotal(String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_HISTORY_BUY_AMOUNT_TOTAL, commodityCode, commodityCode, 4);
    Object ob = DatabaseUtil.executeScalar(query);
    if (ob != null) {
      return Long.valueOf(ob.toString());
    }
    return 0L;
  }

  @Override
  public List<OptionalCampaign> getOptionalCampaignList() {
    Query query = new SimpleQuery(CatalogQuery.GET_OPTIONAL_CAMPAIN_LIST);
    return DatabaseUtil.loadAsBeanList(query, OptionalCampaign.class);
  }

  public String getLoginDiscountCommodityName(Date startDate, Date endDate, String commodityCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_LOGIN_DISCOUNT_COMMODITY_NAME_QUERY);
    query.setParameters(commodityCode, startDate, endDate, startDate, endDate, startDate, endDate);
    Object ob = DatabaseUtil.executeScalar(query);
    if (ob != null) {
      return ob.toString();
    }
    return "";
  }

  // 20130906 txw add start
  // 将淘宝不使用或者淘宝上不存在商品备份目录的不相符的jpg移动到源文件夹下
  private boolean moveTmallDisabledToOrg(List<File> tmallDisabledFileList, List<String> errMsgList, ImageUploadConfig config,
      String shopCode) {
    if (FileUtil.isDirectoryExist(config.getOrgImgPath())) {
      CCommodityDetailDao ccdDao = DIContainer.getDao(CCommodityDetailDao.class);
      CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);

      for (File imgFile : tmallDisabledFileList) {
        // 判断商品SKU是否存在
        String skuCode = getSkuFromFileName(imgFile.getName());
        CCommodityDetail ccdDto = ccdDao.load(shopCode, skuCode);

        if (ccdDto == null) {
          errMsgList.add(skuCode + ",商品不存在");
          continue;
        }
        CCommodityHeader cchDto = cchDao.load(ccdDto.getShopCode(), ccdDto.getCommodityCode());

        if (cchDto.getTmallCommodityId() != null && ccdDto.getTmallUseFlg() != null && ccdDto.getTmallUseFlg() == 1) {
          int result = FileUtil.copyFile(config.getTmallDisabledImgPath() + "/" + imgFile.getName(), config.getOrgImgPath() + "/"
              + imgFile.getName());

          if (result != 1) {
            errMsgList.add(imgFile.getName() + ",可能正在被使用");
            return false;
          } else {
            imgFile.delete();
          }
        }
      }
      return true;
    } else {
      errMsgList.add("源目录不在在。");
      return false;
    }
  }

  // 2014/05/08 京东WBS对应 ob_姚 add start
  // 将京东不使用或者京东上不存在商品备份目录的不相符的jpg移动到源文件夹下
  private boolean moveJdDisabledToOrg(List<File> jdDisabledFileList, List<String> errMsgList, ImageUploadConfig config,
      String shopCode) {
    if (FileUtil.isDirectoryExist(config.getOrgImgPath())) {
      CCommodityDetailDao ccdDao = DIContainer.getDao(CCommodityDetailDao.class);
      CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);

      for (File imgFile : jdDisabledFileList) {
        // 判断商品SKU是否存在
        String skuCode = getSkuFromFileName(imgFile.getName());
        CCommodityDetail ccdDto = ccdDao.load(shopCode, skuCode);

        if (ccdDto == null) {
          errMsgList.add(skuCode + ",商品不存在");
          continue;
        }
        CCommodityHeader cchDto = cchDao.load(ccdDto.getShopCode(), ccdDto.getCommodityCode());

        if (cchDto.getJdCommodityId() != null && JdUseFlg.ENABLED.longValue().equals(ccdDto.getJdUseFlg())) {
          int result = FileUtil.copyFile(config.getJdDisabledImgPath() + "/" + imgFile.getName(), config.getOrgImgPath() + "/"
              + imgFile.getName());

          if (result != 1) {
            errMsgList.add(imgFile.getName() + ",可能正在被使用");
            return false;
          } else {
            imgFile.delete();
          }
        }
      }
      return true;
    } else {
      errMsgList.add("源目录不在在。");
      return false;
    }
  }

  // 2014/05/08 京东WBS对应 ob_姚 add end

  // 2014/05/09 京东WBS对应 ob_姚 update start
  public ServiceResult updateCommodityDescribe(CommodityDescribe describe) {
    return updateCommodityDescribe(describe, false);
  }

  public ServiceResult updateCommodityDescribe(CommodityDescribe describe, boolean jdUpdateFlg) {
    // 2014/05/09 京东WBS对应 ob_姚 update end
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    // 2014/05/09 京东WBS对应 ob_姚 add start
    CCommodityHeader cchDto = null;
    if (jdUpdateFlg) {
      // 更新京东同期标志
      CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);
      cchDto = cchDao.load(describe.getShopCode(), describe.getCommodityCode());

      cchDto.setSyncFlagJd(SyncFlagJd.SYNCVISIBLE.longValue());
      setUserStatus(cchDto);
    }
    // 2014/05/09 京东WBS对应 ob_姚 add end
    setUserStatus(describe);
    ValidationSummary summary = BeanValidator.validate(describe);
    if (summary.hasError()) {
      for (String message : summary.getErrorMessages()) {
        logger.error(message);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }
    TransactionManager txm = DIContainer.getTransactionManager();
    try {
      txm.begin(getLoginInfo());
      txm.update(describe);
      // 2014/05/09 京东WBS对应 ob_姚 add start
      if (jdUpdateFlg) {
        txm.update(cchDto);
      }
      // 2014/05/09 京东WBS对应 ob_姚 add end
      txm.commit();
    } catch (ConcurrencyFailureException e) {
      txm.rollback();
      throw e;
    } catch (Exception e) {
      txm.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txm.dispose();
    }
    return result;
  }

  // 2014/05/09 京东WBS对应 ob_姚 add start
  public ServiceResult insertCommodityDescribe(CommodityDescribe describe, boolean jdUpdateFlg) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    CCommodityHeader cchDto = null;

    if (jdUpdateFlg) {
      // 更新京东同期标志
      CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);
      cchDto = cchDao.load(describe.getShopCode(), describe.getCommodityCode());

      cchDto.setSyncFlagJd(SyncFlagJd.SYNCVISIBLE.longValue());
      setUserStatus(cchDto);
    }

    describe.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    setUserStatus(describe);

    ValidationSummary summary = BeanValidator.validate(describe);
    if (summary.hasError()) {
      for (String message : summary.getErrorMessages()) {
        logger.error(message);
      }
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      return result;
    }

    TransactionManager txm = DIContainer.getTransactionManager();
    try {
      txm.begin(getLoginInfo());
      txm.insert(describe);
      if (jdUpdateFlg) {
        txm.update(cchDto);
      }
      txm.commit();
    } catch (ConcurrencyFailureException e) {
      txm.rollback();
      throw e;
    } catch (Exception e) {
      txm.rollback();
      result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txm.dispose();
    }
    return result;
  }

  // 2014/05/09 京东WBS对应 ob_姚 add end

  private ImageUploadHistory getImageUploadHistory(String shopCode, String skuCode, String uploadCommodityImg) {
    Query query = new SimpleQuery(CatalogQuery.GET_IMAGE_UPLOAD_HISTORY_QUERY, shopCode, skuCode, uploadCommodityImg);
    return DatabaseUtil.loadAsBean(query, ImageUploadHistory.class);
  }

  // 20130906 txw add end

  public String getLoginFreePostageName(Date startDate, Date endDate, String issueCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_LOGIN_FREE_POSTAGE_NAME_QUERY);
    query.setParameters(issueCode, startDate, endDate, startDate, endDate, startDate, endDate);
    Object ob = DatabaseUtil.executeScalar(query);
    if (ob != null) {
      return ob.toString();
    }
    return "";
  }

  public String getNearCommodityMsg(String shopCode, String commodityCode) {
    String msg = "";
    WebshopConfig config = DIContainer.getWebshopConfig();
    String languageCode = getCurrentLanguageCode();
    CommodityHeader ch = getCommodityHeader(shopCode, commodityCode);
    if (ch != null && ch.getShelfLifeDays() != null) {
      CommodityProductionDateDao dao = DIContainer.getDao(CommodityProductionDateDao.class);
      String originalCommodityCode = commodityCode;
      if (StringUtil.hasValue(ch.getOriginalCommodityCode())) {
        originalCommodityCode = ch.getOriginalCommodityCode();
      }
      CommodityProductionDate commodityProductionDate = dao.load(originalCommodityCode);
      if (commodityProductionDate != null) {
        Long useSurplusDate = ch.getShelfLifeDays()
            - DateUtil.getDaysFromTwoDateString(DateUtil.toDateString(commodityProductionDate.getEarlistDate()), DateUtil
                .getSysdateString());
        Long nearSurplusDate = 0L;

        if (ch.getShelfLifeDays() >= 0 && ch.getShelfLifeDays() <= 365) {
          nearSurplusDate = (new BigDecimal(ch.getShelfLifeDays().toString())).divide(new BigDecimal("4"), 0).longValue();// BigDecimalUtil.divide(ch.getShelfLifeDays(),
                                                                                                                          // 4).setScale(0,
                                                                                                                          // BigDecimal.ROUND_UP).longValue();
        } else if (ch.getShelfLifeDays() >= 366 && ch.getShelfLifeDays() <= 730) {
          nearSurplusDate = (new BigDecimal(ch.getShelfLifeDays().toString())).divide(new BigDecimal("5"), 0).longValue();// BigDecimalUtil.divide(ch.getShelfLifeDays(),
                                                                                                                          // 5).setScale(0,
                                                                                                                          // BigDecimal.ROUND_UP).longValue();
        } else {
          nearSurplusDate = (new BigDecimal(ch.getShelfLifeDays().toString())).divide(new BigDecimal("6"), 0).longValue();// BigDecimalUtil.divide(ch.getShelfLifeDays(),
                                                                                                                          // 6).setScale(0,
                                                                                                                          // BigDecimal.ROUND_UP).longValue();
        }

        if (useSurplusDate <= nearSurplusDate && languageCode.equals(LanguageCode.Zh_Cn.getValue())) {
          msg = config.getNearMsgCn();
        } else if (useSurplusDate <= nearSurplusDate && languageCode.equals(LanguageCode.En_Us.getValue())) {
          msg = config.getNearMsgEn();
        } else if (useSurplusDate <= nearSurplusDate && languageCode.equals(LanguageCode.Ja_Jp.getValue())) {
          msg = config.getNearMsgJp();
        }
      }
    }
    return msg;
  }

  private String getCurrentLanguageCode() {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    if (StringUtil.isNullOrEmpty(currentLanguageCode)) {
      currentLanguageCode = DIContainer.getLocaleContext().getSystemLanguageCode();
    }

    return currentLanguageCode;
  }

  // 2014/05/02 京东WBS对应 ob_姚 add start
  public List<JdCommodityProperty> getJdCommodityPropertyInput(String shopCode, String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_JD_COMMODITY_PROPERTY_INPUT, shopCode, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, JdCommodityProperty.class);
  }

  public List<JdCommodityProperty> getJdCommodityPropertyNotInput(String shopCode, String commodityCode) {
    Query query = new SimpleQuery(CatalogQuery.GET_JD_COMMODITY_PROPERTY_NOT_INPUT, shopCode, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, JdCommodityProperty.class);
  }

  // 2014/05/02 京东WBS对应 ob_姚 add end

  @Override
  public List<CommodityHeadline> getChosenSortCommodity(Long type) {
     CacheContainer cc = getCacheContainer();
    final Query query = new SimpleQuery(CatalogQuery.GET_CHOSEN_SORT_COMMODITY, type);
    List<CommodityHeadline> lineList = cc.get(CacheKey.create(query), new CacheRetriever<List<CommodityHeadline>>() {

      public List<CommodityHeadline> retrieve() {
        return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
      }
    });
    return lineList;
//    Query query = new SimpleQuery(CatalogQuery.GET_CHOSEN_SORT_COMMODITY, type);
//    return DatabaseUtil.loadAsBeanList(query, CommodityHeadline.class);
  }

  // 2014/06/17 库存更新对应 ob_李先超 add start
  public Long getTmallStock(String commodityCode) {
    Long allTmallStock = 0L;
    Long allocatedTmallStock = 0L;
    Long tmallStock = 0L;

    Query allQuery = new SimpleQuery(CatalogQuery.GET_TMALL_ALL_STOCK, commodityCode);
    allTmallStock = DatabaseUtil.executeScalar(allQuery, Long.class);
    Query allocatedQuery = new SimpleQuery(CatalogQuery.GET_TMALL_ALLOCATED_STOCK, commodityCode);
    allocatedTmallStock = DatabaseUtil.executeScalar(allocatedQuery, Long.class);
    tmallStock = allTmallStock - allocatedTmallStock;
    return tmallStock;
  }

  public Long getJdStock(String commodityCode) {
    Long allJdStock = 0L;
    Long allocatedJdStock = 0L;
    Long jdStock = 0L;

    Query allQuery = new SimpleQuery(CatalogQuery.GET_JD_ALL_STOCK, commodityCode);
    allJdStock = DatabaseUtil.executeScalar(allQuery, Long.class);
    Query allocatedQuery = new SimpleQuery(CatalogQuery.GET_JD_ALLOCATED_STOCK, commodityCode);
    allocatedJdStock = DatabaseUtil.executeScalar(allocatedQuery, Long.class);
    jdStock = allJdStock - allocatedJdStock;
    return jdStock;
  }

  // 2014/06/17 库存更新对应 ob_李先超 add end

  public SearchResult<CommodityPriceChangeHistory> searchCommodityPriceChangeHistory(
      CommodityPriceChangeHistoryCondition commodityPriceChangeHistoryCondition) {
    return DatabaseUtil.executeSearch(new CommodityPriceChangeHistoryQuery(commodityPriceChangeHistoryCondition));
  }

  public ServiceResult insertCommodityPriceChangeHistory(CommodityPriceChangeHistory commodityPriceChangeHistory) {

    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());

      txMgr.insert(commodityPriceChangeHistory);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;
  }

  public SearchResult<CCommodityDetail> searchCCommodityDetail(String commodityCode) {
    return DatabaseUtil.executeSearch(new CCommodityDetailQuery(commodityCode));
  }

  public SearchResult<CCommodityHeader> searchCCommodityHeader(String commodityCode) {
    return DatabaseUtil.executeSearch(new CCommodityHeaderQuery(commodityCode));
  }

  public ServiceResult updateCommodityPriceChangeHistoryReviewFlg(String commodityCode, String updatedUser) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    CommodityPriceChangeHistoryDao commodityPriceChangeHistoryDao = DIContainer.getDao(CommodityPriceChangeHistoryDao.class);
    CommodityPriceChangeHistory commodityPriceChangeHistory = commodityPriceChangeHistoryDao.load(commodityCode);
    commodityPriceChangeHistory.setReviewOrNotFlg(new Long(1));
    commodityPriceChangeHistory.setUpdatedUser(updatedUser);
    TransactionManager txMgr = DIContainer.getTransactionManager();
    UpdateCommodityPriceChangeHistoryQuery updateQuery = new UpdateCommodityPriceChangeHistoryQuery(commodityCode, updatedUser,
        DateUtil.getSysdate().toString());
    try {
      txMgr.begin(getLoginInfo());
      txMgr.update(commodityPriceChangeHistory);

      txMgr.executeUpdate(updateQuery.getSqlString(), updatedUser, DateUtil.getSysdate().toString(), commodityCode);

      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      throw e;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    } finally {
      txMgr.dispose();
    }

    return serviceResult;

  }

  @Override
  public List<CampaignMain> getCampaignMainByDateAndType(Date startedDatetime, Date endDatetime, Long type) {
    SimpleQuery getCampaignMainListQuery = new SimpleQuery(CatalogQuery.GET_CAMPAIGN_MAIN_TYPE, startedDatetime, endDatetime, type);
    return DatabaseUtil.loadAsBeanList(getCampaignMainListQuery, CampaignMain.class);
  }

  @Override
  public List<Brand> getBrandListByRelatedCommodityCode(String commodityCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_BRAND_LIST_BY_RELATED_COMMODITY_CODE, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, Brand.class);
  }

  @Override
  public RelatedBrand loadRelatedBrand(String shopCode, String commodityCode, String brandCode) {
    RelatedBrandDao dao = DIContainer.getDao(RelatedBrandDao.class);
    return dao.load(shopCode, commodityCode, brandCode);
  }

  @Override
  public void insertRelatedBrand(RelatedBrand obj) {
    RelatedBrandDao dao = DIContainer.getDao(RelatedBrandDao.class);
    dao.insert(obj);
  }

  @Override
  public List<CCommodityHeader> getAllCCommodityHeader() {
    CCommodityHeaderDao dao = DIContainer.getDao(CCommodityHeaderDao.class);
    return dao.loadAll();
  }

  @Override
  public List<Category> getCategoryListByRelatedCommodityCode(String commodityCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_CATEGORY_LIST_BY_RELATED_COMMODITY_CODE, commodityCode);
    return DatabaseUtil.loadAsBeanList(query, Category.class);
  }

  @Override
  public void insertRelatedSiblingCategory(RelatedSiblingCategory obj) {
    RelatedSiblingCategoryDao dao = DIContainer.getDao(RelatedSiblingCategoryDao.class);
    dao.insert(obj);
  }

  @Override
  public RelatedSiblingCategory loadRelatedSiblingCategory(String shopCode, String commodityCode, String categoryCode) {
    RelatedSiblingCategoryDao dao = DIContainer.getDao(RelatedSiblingCategoryDao.class);
    return dao.load(shopCode, commodityCode, categoryCode);
  }

  @Override
  public List<RelatedBrand> getRelatedBrandByCommodityCode(String commodityCode,Long limitNum) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.getRelatedBrandByCommodityCode(limitNum), commodityCode);
    return DatabaseUtil.loadAsBeanList(query, RelatedBrand.class);
  }

  @Override
  public List<RelatedSiblingCategory> getRelatedSiblingCategoryByCommodityCode(String commodityCode,Long limitNum) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.getRelatedSiblingCategoryByCommodityCode(limitNum), commodityCode);
    return DatabaseUtil.loadAsBeanList(query, RelatedSiblingCategory.class);
  }

  @Override
  public SearchResult<CommodityHeadline> fastFindCommodityHeaderLine(CommodityContainerCondition condition, boolean planDetailFlag) {
    SearchResult<CommodityKey> keyList = findCommodityKey(condition);
    return getCommodityHeadlineForHotSale(keyList, condition.getAlignmentSequence(), planDetailFlag, condition);
  }

  public SearchResult<CommodityHeadline> getCommodityHeadlineForHotSale(SearchResult<CommodityKey> result, String alignmentSequence,
      boolean planDetailFlag, CommodityContainerCondition condition) {

    List<CommodityKey> keyList = result.getRows();
    SearchResult<CommodityHeadline> rr = null;
    if (keyList != null && keyList.size() > 0) {
      final ContainerFrontQuery query = new ContainerFrontQuery(keyList, alignmentSequence, planDetailFlag, condition);
      query.setMaxFetchSize(result.getMaxFetchSize());
      query.setPageSize(result.getPageSize());

      CacheContainer cc = getCacheContainer();
      rr= cc.get(CacheKey.create(query), new CacheRetriever<SearchResult<CommodityHeadline>>() {
        public SearchResult<CommodityHeadline> retrieve() {
          return DatabaseUtil.executeSearch(query);
        }
      });
    }

    return rr;
  }

  @Override
  public CommodityMaster getCommodityMasterByJdCode(String jdCommdityId) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_COMMODITY_MASTER_BY_JD_CODE,jdCommdityId);
    return DatabaseUtil.loadAsBean(query, CommodityMaster.class);
  }

  @Override
  public CommodityMaster getCommodityMasterByTmallCode(String commodityCode, String tmallCommdityCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_COMMODITY_MASTER_BY_TMALL_CODE, commodityCode,tmallCommdityCode);
    return DatabaseUtil.loadAsBean(query, CommodityMaster.class);
  }

  @Override
  public CommoditySku getCommoditySkuByJdCode(String commodityCode, String skuCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_COMMODITY_SKU_BY_JD_CODE, commodityCode,skuCode);
    return DatabaseUtil.loadAsBean(query, CommoditySku.class);
  }

  @Override
  public CommoditySku getCommoditySkuByTmallCode(String commodityCode, String skuCode) {
    SimpleQuery query = new SimpleQuery(CatalogQuery.GET_COMMODITY_SKU_BY_TMALL_CODE, commodityCode,skuCode);
    return DatabaseUtil.loadAsBean(query, CommoditySku.class);
  }
  @Override
  public SearchResult<CommodityMasterResult> getCommodityMasterResult(CommodityMasterSearchCondition condition) {
    return DatabaseUtil.executeSearch(new CommodityMasterSearchQuery(condition));
  }

  @Override
  public ServiceResult deleteCommodityMaster(String ccode) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    CommodityMasterDao cmdao=DIContainer.getDao(CommodityMasterDao.class);
    cmdao.delete(ccode);
    return serviceResult;
  }

  //查询 tm/jd多商品是否关联记录
  @Override
  public Long getCommoditySku(String ccode) {
    Query query = null;
    query = new SimpleQuery(CatalogQuery.GET_COMMODITY_SKU,ccode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  @Override
  public Long getCommodityCode1(String ccode) {
    Query query = null;
    query = new SimpleQuery(CatalogQuery.GET_COMMODITY_CODE1,ccode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }
  //添加 commodityMaster 信息
  @Override
  public void registerCommodityMaster(CommodityMaster cm) {
    CommodityMasterDao cmdao = DIContainer.getDao(CommodityMasterDao.class);
    cmdao.insert(cm);
  }

  @Override
  public CommodityMaster getCommodityMasters(String ccode) {
    CommodityMasterDao dao = DIContainer.getDao(CommodityMasterDao.class);
    return dao.load(ccode);
  }

  @Override
  public ServiceResult updateCommodityMaster(CommodityMaster cm) {
    ServiceResultImpl result = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    CommodityMasterDao dao = DIContainer.getDao(CommodityMasterDao.class);

    // 更新対象データが存在しない場合はエラー
    CommodityMaster cms = dao.load(cm.getCommodityCode());
    
    if (cms == null) {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug("no data. discountCode:" + cm.getCommodityCode());
      return result;
    }
    cm.setOrmRowid(cms.getOrmRowid());
    cm.setCreatedUser(cms.getCreatedUser());
    cm.setCreatedDatetime(cms.getCreatedDatetime());
    cm.setUpdatedUser(cms.getUpdatedUser());
    cm.setUpdatedDatetime(cms.getUpdatedDatetime());
    setUserStatus(cm);
    // validationチェック
    List<ValidationResult> resultList = BeanValidator.validate(cm).getErrors();
    if (resultList.size() > 0) {
      result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
    }
    if (result.hasError()) {
      return result;
    }
    dao.update(cm, getLoginInfo());
    return result;
  }

  @Override
  public CommodityMasterEditInfo getCommodityMasterEditInfo(String ccode) {
    return null;
  }
  @Override
  public List<CommoditySku> getCommoditySkuList(String ccode) {
    Object[] params = {ccode};
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_SKUS, params);
    return DatabaseUtil.loadAsBeanList(query, CommoditySku.class);
  }
  
  @Override
  public void deleteCommodityEdit(String skucode) {
    CommoditySkuDao cmdao=DIContainer.getDao(CommoditySkuDao.class);
    cmdao.delete(skucode);
   
  }

  @Override
  public List<CommodityHeader> getCommodityHeaderName(String ccode) {
    Object[] params = {ccode};
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_HEADER_NAME, params);
    return DatabaseUtil.loadAsBeanList(query, CommodityHeader.class);
  }

  @Override
  public Long getCommoditySkuCode(String ccode, String skucode) {
    Query query = null;
    query = new SimpleQuery(CatalogQuery.get_Commodity_Sku_Code, ccode,skucode);
    return DatabaseUtil.executeScalar(query, Long.class);
  }

  @Override
  public void registerCommoditySku(CommoditySku cs) {
    CommoditySkuDao cmdao = DIContainer.getDao(CommoditySkuDao.class);
    cmdao.insert(cs);
  }
  
  @Override
  public List<String> getCommoditySkuCodes(String skucode) {
    Query query = new SimpleQuery(CatalogQuery.GET_COMMODITY_SKU_CODES,skucode);
    List<String> commoditySkuCodes = DatabaseUtil.loadAsStringList(query);
    return commoditySkuCodes;
  }
}
  