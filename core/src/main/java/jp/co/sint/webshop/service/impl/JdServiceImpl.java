package jp.co.sint.webshop.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.api.jd.JdApiProviderManager;
import jp.co.sint.webshop.api.jd.JdApiResult;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.CCommodityDetailDao;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CommodityDescribeDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dao.JdCategoryDao;
import jp.co.sint.webshop.data.dao.JdCouponDetailDao;
import jp.co.sint.webshop.data.dao.JdOrderDetailDao;
import jp.co.sint.webshop.data.dao.JdOrderHeaderDao;
import jp.co.sint.webshop.data.dao.JdShippingDetailCompositionDao;
import jp.co.sint.webshop.data.dao.JdShippingDetailDao;
import jp.co.sint.webshop.data.dao.JdShippingHeaderDao;
import jp.co.sint.webshop.data.dao.JdStockAllocationDao;
import jp.co.sint.webshop.data.dao.JdStockDao;
import jp.co.sint.webshop.data.dao.JdSuitCommodityDao;
import jp.co.sint.webshop.data.dao.OrderInvoiceDao;
import jp.co.sint.webshop.data.domain.ImportCommodityType;
import jp.co.sint.webshop.data.domain.IsOrNot;
import jp.co.sint.webshop.data.domain.JdOptionType;
import jp.co.sint.webshop.data.domain.ShelfLifeFlag;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityDescribe;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CyncroResult;
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.data.dto.JdBatchTime;
import jp.co.sint.webshop.data.dto.JdBrand;
import jp.co.sint.webshop.data.dto.JdCategory;
import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.data.dto.JdCouponDetail;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdProperty;
import jp.co.sint.webshop.data.dto.JdPropertyValue;
import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.JdStock;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.hibernate.TransactionManagerImpl;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.jd.JdOrderDownload;
import jp.co.sint.webshop.service.jd.JdOrderDownloadErrorInfo;
import jp.co.sint.webshop.service.jd.order.JdOrderManager;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchQuery;
import jp.co.sint.webshop.service.jd.order.JdOrderlLists;
import jp.co.sint.webshop.service.order.JdServiceQuery;
import jp.co.sint.webshop.service.order.OrderServiceQuery;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StockQuantityUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.FileItem;
import com.jd.open.api.sdk.domain.category.AttValue;
import com.jd.open.api.sdk.domain.category.Category;
import com.jd.open.api.sdk.domain.ware.WarePropimg;
import com.jd.open.api.sdk.request.category.CategoryAttributeSearchRequest;
import com.jd.open.api.sdk.request.category.CategoryAttributeValueSearchRequest;
import com.jd.open.api.sdk.request.category.CategorySearchRequest;
import com.jd.open.api.sdk.request.imgzone.ImgzonePictureUploadRequest;
import com.jd.open.api.sdk.request.seller.PopVenderCenerVenderBrandQueryRequest;
import com.jd.open.api.sdk.request.ware.WareAddRequest;
import com.jd.open.api.sdk.request.ware.WarePropimgAddRequest;
import com.jd.open.api.sdk.request.ware.WarePropimgDeleteRequest;
import com.jd.open.api.sdk.request.ware.WarePropimgsGetRequest;
import com.jd.open.api.sdk.request.ware.WarePropimgsSearchRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.response.category.CategoryAttributeSearchResponse;
import com.jd.open.api.sdk.response.category.CategoryAttributeValueSearchResponse;
import com.jd.open.api.sdk.response.category.CategorySearchResponse;
import com.jd.open.api.sdk.response.category.CategoryAttributeSearchResponse.Attribute;
import com.jd.open.api.sdk.response.imgzone.ImgzonePictureUploadResponse;
import com.jd.open.api.sdk.response.seller.PopVenderCenerVenderBrandQueryResponse;
import com.jd.open.api.sdk.response.seller.VenderBrandPubInfo;
import com.jd.open.api.sdk.response.ware.WareAddResponse;
import com.jd.open.api.sdk.response.ware.WarePropimgAddResponse;
import com.jd.open.api.sdk.response.ware.WarePropimgsGetResponse;
import com.jd.open.api.sdk.response.ware.WarePropimgsSearchResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateResponse;

public class JdServiceImpl extends AbstractServiceImpl implements JdService {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private JdOrderManager jm;
  
  public boolean getJdAreas(){
    jm = new JdOrderManager();
    return jm.getJdAreas();
  }
  
  
  public CyncroResult jdCommodityCyncro(String[] commodityCodes) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info("京东同期化开始.............");
    // 同期开始时间
    long startTime = System.currentTimeMillis();
    // 获取jd同期化信息
    List<CCommodityHeader> jdList = getCynchJdInfoByCheckBox(commodityCodes);
    // 同期错误集合
    CyncroResult resultMap = new CyncroResult();
    resultMap.setFailCount(0L);
    resultMap.setSeccessCount(0L);
    
    for (CCommodityHeader header : jdList) {
      // 根据京东同期时间判断是更新还是添加
      if (header.getJdCommodityId() != null) { // 执行更新
        updateCommodityApi(resultMap, header);
      } else { // 执行新规
        insertCommodityApi(resultMap, header);
      }
    }
    
    // 执行结束时间
    long endTime = System.currentTimeMillis();
    logger.info("京东同期化结束.............");
    // 返回同期结果
    resultMap.setTotalCount(Long.valueOf(jdList.size()));
    resultMap.setStartTime(startTime);
    resultMap.setEndTime(endTime);
    return resultMap;
  }
  
  public ServiceResult getJdCategoryApi() {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    
    CategorySearchRequest jdRequest = new CategorySearchRequest();
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("京东类目取得失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      serviceResult.addServiceError(CatalogServiceErrorContent.GET_JD_CATEGORY_ERROR);
    } else {
      try {
        txMgr.begin(getLoginInfo());
        // 先将数据库中数据删除
        txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_JD_CATEGORY));
        
        CategorySearchResponse jdResponse = (CategorySearchResponse) jdApiResult.getResultBean();
        List<Category> categoryList = jdResponse.getCategory();
        for (Category categoryResut : categoryList) {
          if ("VALID".equals(categoryResut.getStatus())) {
            JdCategory jdCategory = new JdCategory();
            jdCategory.setCategoryId(Integer.toString(categoryResut.getId()));
            jdCategory.setCategoryName(categoryResut.getName());
            jdCategory.setParentId(Integer.toString(categoryResut.getFid()));
            if (categoryResut.isParent()) {
              jdCategory.setIsParent(IsOrNot.IS.longValue());
            } else {
              jdCategory.setIsParent(IsOrNot.ISNOT.longValue());
            }
            jdCategory.setLevel(NumUtil.toLong(Integer.toString(categoryResut.getLev())));
            jdCategory.setDisplayOrder(NumUtil.toLong(Integer.toString(categoryResut.getIndexId())));
            
            setUserStatus(jdCategory);
            
            txMgr.insert(jdCategory);
          }
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
    }
    
    return serviceResult;
  }
  
  public boolean getJdShippingApiList(String order_id,String logistics_id,String waybill){
    jm = new JdOrderManager();
    return jm.getJdShippingApiList(order_id,logistics_id,waybill);
  }
  
  
  public ServiceResult getJdPropertyApi() {
    return getJdPropertyApi("", "");
  }
  
  public ServiceResult getJdPropertyApi(String categoryId) {
    return getJdPropertyApi(categoryId, "");
  }
  
  public ServiceResult getJdPropertyApi(String categoryId, String attributeId) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    List<Attribute> attributesResult = new ArrayList<Attribute>();
    List<AttValue> attValueList = new ArrayList<AttValue>();
    List<AttValue> attValueAllList = new ArrayList<AttValue>();
    TransactionManager txMgr = DIContainer.getTransactionManager();
    
    // 传入参数的合法性验证
    if (StringUtil.hasValue(attributeId) && StringUtil.isNullOrEmpty(categoryId)) {
      logger.error("传入参数不合法，类目ID未指定时，属性ID不能指定。");
      serviceResult.addServiceError(CatalogServiceErrorContent.PARAM_ERROR);
      return serviceResult;
    }
    
    // 取得JD类目属性
    if (!StringUtil.hasValueAnyOf(categoryId, attributeId)) {
      serviceResult = (ServiceResultImpl) getAttributesListAll(attributesResult);
      if (serviceResult.hasError()) {
        return serviceResult;
      } 
    } else {
      serviceResult = (ServiceResultImpl) getAttributesList(attributesResult, categoryId, attributeId);
      if (serviceResult.hasError()) {
        return serviceResult;
      }
    }
    
    // 取得JD类目属性值
    String avs = "";
    int lastIndex = 0;
    if (attributesResult != null && attributesResult.size() > 0) {
      for (Attribute attribute : attributesResult) {
        lastIndex++;
        avs += attribute.getAid();
        //每500个属性查询一次(最后一次也查询)
        if (lastIndex%500==0 || lastIndex == attributesResult.size()) {
          //执行属性值查询处理
          serviceResult = (ServiceResultImpl) getJdPropertyValueApi(avs, attValueList);
          if (serviceResult.hasError()) {
            return serviceResult;
          }
          attValueAllList.addAll(attValueList);
          attValueList.clear();
          avs = "";
        }
        if (lastIndex != attributesResult.size()) {
          avs += ";";
        }
      }
    }
    
    
    try {
      txMgr.begin(getLoginInfo());
      Connection connection = ((TransactionManagerImpl) txMgr).getConnection();
      // 先将数据库中数据删除
      if (!StringUtil.hasValueAnyOf(categoryId, attributeId)) {
        txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_ALL_JD_PROPERTY));
        txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_ALL_JD_PROPERTY_VALUE));
      } else if (StringUtil.hasValue(categoryId) && StringUtil.isNullOrEmpty(attributeId)) {
        txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_JD_PROPERTY_BY_CATEGORY_ID, categoryId));
        for (Attribute attribute : attributesResult) {
          txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_JD_PROPERTY_VALUE_BY_PROPERTY_ID, NumUtil.toString(attribute.getAid())));
        }
      } else {
        txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_JD_PROPERTY_BY_CATEGORY_ID_AND_PROPERTY_ID, categoryId, attributeId));
        txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_JD_PROPERTY_VALUE_BY_PROPERTY_ID, attributeId));
      }
      
      
      for (Attribute attribute : attributesResult) {
        if ("VALID".equals(attribute.getStatus())) {
          
          Query exitsQuery = new SimpleQuery("SELECT * FROM JD_PROPERTY WHERE PROPERTY_ID = ? AND CATEGORY_ID = ?",
              NumUtil.toString(attribute.getAid()),attribute.getCid());
          List<JdProperty> proList = DatabaseUtil.loadAsBeanList(connection,exitsQuery, JdProperty.class);
          if (proList==null || proList.size() == 0) {
            JdProperty jdProperty = new JdProperty();

            jdProperty.setPropertyId(NumUtil.toString(attribute.getAid()));
            jdProperty.setCategoryId(attribute.getCid());
            jdProperty.setPropertyName(attribute.getName());
            if (attribute.getKeyProp()) {
              jdProperty.setIsKey(IsOrNot.IS.longValue());
            } else {
              jdProperty.setIsKey(IsOrNot.ISNOT.longValue());
            }
            if (attribute.getSaleProp()) {
              jdProperty.setIsSale(IsOrNot.IS.longValue());
            } else {
              jdProperty.setIsSale(IsOrNot.ISNOT.longValue());
            }
            if (attribute.isColorProp()) {
              jdProperty.setIsColor(IsOrNot.IS.longValue());
            } else {
              jdProperty.setIsColor(IsOrNot.ISNOT.longValue());
            }
            if (attribute.isSizeProp()) {
              jdProperty.setIsSize(IsOrNot.IS.longValue());
            } else {
              jdProperty.setIsSize(IsOrNot.ISNOT.longValue());
            }
            if ("true".equals(attribute.getReq())) {
              jdProperty.setIsReq(IsOrNot.IS.longValue());
            } else {
              jdProperty.setIsReq(IsOrNot.ISNOT.longValue());
            }
            if ("true".equals(attribute.getFet())) {
              jdProperty.setIsFet(IsOrNot.IS.longValue());
            } else {
              jdProperty.setIsFet(IsOrNot.ISNOT.longValue());
            }
            if ("true".equals(attribute.getNav())) {
              jdProperty.setIsNav(IsOrNot.IS.longValue());
            } else {
              jdProperty.setIsNav(IsOrNot.ISNOT.longValue());
            }
            jdProperty.setAttType(NumUtil.toLong(attribute.getAttType()));
            jdProperty.setInputType(attribute.getInputType());
            jdProperty.setDisplayOrder(attribute.getIndexId());
            setUserStatus(jdProperty);
            txMgr.insert(jdProperty);
          }
        }
      }
      
      
      for (AttValue attributeValue : attValueAllList) {
        if ("VALID".equals(attributeValue.getStatus())) {
          Query exitsQuery = new SimpleQuery("SELECT * FROM JD_PROPERTY_VALUE WHERE PROPERTY_ID = ? AND VALUE_ID = ?",
              NumUtil.toString(attributeValue.getAid()),NumUtil.toString(attributeValue.getVid()));
          List<JdPropertyValue> valList = DatabaseUtil.loadAsBeanList(connection,exitsQuery, JdPropertyValue.class); 
          if (valList==null || valList.size()==0) {
            JdPropertyValue jdPropertyValue = new JdPropertyValue();
            jdPropertyValue.setPropertyId(NumUtil.toString(attributeValue.getAid()));
            jdPropertyValue.setValueId(NumUtil.toString(attributeValue.getVid()));
            jdPropertyValue.setValueName(attributeValue.getName());
            jdPropertyValue.setDisplayOrder(attributeValue.getIndexId());
            setUserStatus(jdPropertyValue);
            txMgr.insert(jdPropertyValue);
          }
        }
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
  
  
  public ServiceResult getJdPropertyValueApi(String avs, List<AttValue> attValueList) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    
    CategoryAttributeValueSearchRequest jdRequest = new CategoryAttributeValueSearchRequest();
    jdRequest.setAvs(avs);
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("京东类目属性值取得失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      serviceResult.addServiceError(CatalogServiceErrorContent.GET_JD_PROPERTY_VALUE_ERROR);
      return serviceResult;
    } else {
      CategoryAttributeValueSearchResponse jdResponse = (CategoryAttributeValueSearchResponse) jdApiResult.getResultBean();
      attValueList.addAll(jdResponse.getAttValues());
    }
    
    return serviceResult;
  }
  
  public ServiceResult getVenderBrandApi() {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    
    PopVenderCenerVenderBrandQueryRequest jdRequest = new PopVenderCenerVenderBrandQueryRequest();
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("京东已授权品牌取得失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      serviceResult.addServiceError(CatalogServiceErrorContent.GET_VENDER_BRAND_ERROR);
    } else {
      try {
        txMgr.begin(getLoginInfo());
        Connection connection = ((TransactionManagerImpl) txMgr).getConnection();
        // 先将数据库中数据删除
        txMgr.executeUpdate(new SimpleQuery(CatalogQuery.DELETE_JD_BRAND));
        
        PopVenderCenerVenderBrandQueryResponse jdResponse = (PopVenderCenerVenderBrandQueryResponse) jdApiResult.getResultBean();
        List<VenderBrandPubInfo> venderBrandList = jdResponse.getBrandList();
        for (VenderBrandPubInfo venderBrandPubInfo : venderBrandList) {
          Query exitsQuery = new SimpleQuery("SELECT * FROM JD_BRAND WHERE JD_BRAND_CODE = ?",Integer.toString(venderBrandPubInfo.getErpBrandId()));
          List<JdBrand> list = DatabaseUtil.loadAsBeanList(connection, exitsQuery, JdBrand.class);
          if (list ==null || list.size()==0) {
            JdBrand jdBrand = new JdBrand();
            jdBrand.setJdBrandCode(Integer.toString(venderBrandPubInfo.getErpBrandId()));
            jdBrand.setJdBrandName(venderBrandPubInfo.getBrandName());
            
            setUserStatus(jdBrand);
            
            txMgr.insert(jdBrand);
          }
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
    }
    
    return serviceResult;
  }
  
  public ServiceResult addMainCommodityImage(String jdCommodityID, String imageFile, 
      ImageUploadHistory addDto, ImageUploadHistory delDto) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    String mainImageIdOld = "";
    
    // 查询主图
    WarePropimgsSearchRequest jdRequest = new WarePropimgsSearchRequest();
    jdRequest.setWareId(jdCommodityID);
    jdRequest.setPage("1");
    jdRequest.setPageSize("20");
    
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("商品主图查询失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
      return serviceResult;
    } else {
      WarePropimgsSearchResponse jdResponse = (WarePropimgsSearchResponse) jdApiResult.getResultBean();
      if (jdResponse.getWarePropimg() != null && jdResponse.getWarePropimg().size() > 0) {
        for (WarePropimg warePropimg : jdResponse.getWarePropimg()) {
          if ("是".equals(warePropimg.getMain())) {
            mainImageIdOld = Integer.toString(warePropimg.getImgId());
            break;
          }
        }
      }
      
    }
    
    // 调用增加图片API，将该图片上传至京东
    if (addCommodityImage(jdCommodityID, true, imageFile, addDto).hasError()) {
      serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
      return serviceResult;
    }
    
    // 调用删除图片API，将该图片从京东删除
    if (StringUtil.hasValue(mainImageIdOld)) {
      if (deleteCommodityImage(jdCommodityID, mainImageIdOld).hasError()) {
        serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
        return serviceResult;
      }
    }
    
    return serviceResult;
  }
  
  public ServiceResult addAssistCommodityImage(String jdCommodityID, String skuCode, String fix, String imageFile, 
      ImageUploadHistory addDto) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    ImageUploadHistory delDto = new ImageUploadHistory();
    
    // 查询出最新上传的商品图片的JD用图片ID
    Query query = new SimpleQuery(CatalogQuery.GET_LASTEST_IMAGE_UPLOAD_HIS, DIContainer.getWebshopConfig().getSiteShopCode(),
        skuCode, fix.replace("_", ""));
    List<ImageUploadHistory> historyList = DatabaseUtil.loadAsBeanList(query, ImageUploadHistory.class);
    
    if (historyList != null && historyList.size() > 0) {
      delDto = historyList.get(0);
      
      // 调用删除图片API，将该图片从京东删除
      if (deleteCommodityImage(jdCommodityID, delDto.getJdImageId()).hasError()) {
        serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
        return serviceResult;
      }
    }
    
    // 调用增加图片API，将该图片上传至京东
    if (addCommodityImage(jdCommodityID, false, imageFile, addDto).hasError()) {
      serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
      return serviceResult;
    }
    
    return serviceResult;
    
  }

  public ServiceResult addCommodityImage(String jdCommodityID, boolean isMainPic, String imageFile, 
      ImageUploadHistory newDto) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    WarePropimgAddRequest jdRequest = new WarePropimgAddRequest();
    
    jdRequest.setWareId(jdCommodityID);
    jdRequest.setAttributeValueId("0000000000");
    jdRequest.setMainPic(isMainPic);
    FileItem fileItem = new FileItem(new File(imageFile));
    jdRequest.setImage(fileItem);
    
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("图片：" + imageFile + "上传失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      if (StringUtil.hasValue(jdApiResult.getErrorMessage()) && jdApiResult.getErrorMessage().equals("超出图片限制最多的图片数6")) {
        
      } else {
        serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
      }
    } else {
      
      WarePropimgAddResponse jdResponse = (WarePropimgAddResponse) jdApiResult.getResultBean();

      newDto.setJdImageId(NumUtil.toString(jdResponse.getId()));
      newDto.setJdUploadTime(DateUtil.getSysdate());
      
      // 取得京东商品图片URL
      WarePropimg jdImageInfo = getCommodityImageApi(jdCommodityID, jdResponse.getAttributeValueId());
      if (jdImageInfo != null && StringUtil.hasValue(jdImageInfo.getImgUrl())) {
        newDto.setJdImgUrl(jdImageInfo.getImgUrl());
      } else {
        serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
      }
      
    }
    
    return serviceResult;
  }
  
  public ServiceResult deleteCommodityImage(String jdCommodityID, String imageId) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    
    WarePropimgDeleteRequest jdRequest = new WarePropimgDeleteRequest();
    jdRequest.setWareId(jdCommodityID);
    jdRequest.setAttributeValueId("0000000000");
    jdRequest.setImageId(imageId);
    
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("图片上传失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      serviceResult.addServiceError(CatalogServiceErrorContent.JD_IMAGE_UPLOAD_ERROR);
    }
    
    return serviceResult;
  }
  
  public String ImgzonePictureUploadApi(String imagePath, String imageName) {
    Logger logger = Logger.getLogger(this.getClass());
    String pictureUrl = "";
    
    ImgzonePictureUploadRequest jdRequest = new ImgzonePictureUploadRequest();
    FileItem file = new FileItem(imagePath);
    try {
      jdRequest.setImageData(file.getContent());
      JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
      JdApiResult jdApiResult = jdApi.execute();
      
      if (jdApiResult.hasError()) {
        logger.error("图片：" + imageName + "上传失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      } else {
        ImgzonePictureUploadResponse jdResponse = (ImgzonePictureUploadResponse) jdApiResult.getResultBean();
        pictureUrl = jdResponse.getPictureUrl();
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return pictureUrl;
  }
  
  /**
   * 根据商品Id，销售属性值Id查询图片
   * @param imagePath
   * @param imageName
   * @return
   */
  private WarePropimg getCommodityImageApi(String wareId, String attributeValueId) {
    Logger logger = Logger.getLogger(this.getClass());
    WarePropimg jdImageResult = null;
    
    WarePropimgsGetRequest jdRequest = new WarePropimgsGetRequest();
    jdRequest.setWareId(wareId);
    jdRequest.setAttributeValueId(attributeValueId);
    
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("商品图片查询失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      return null;
    } else {
      WarePropimgsGetResponse jdResponse = (WarePropimgsGetResponse) jdApiResult.getResultBean();
      List<WarePropimg> imageList = jdResponse.getWarePropimg();
      if (imageList != null && imageList.size() > 0) {
        Date lastDate = null;
        for (WarePropimg imageInfo : imageList) {
          Date currentDate = DateUtil.parseString(imageInfo.getCreated(), DateUtil.DEFAULT_TIMESTAMP_FORMAT);
          if (lastDate == null || lastDate.before(currentDate)) {
            lastDate = currentDate;
            jdImageResult = imageInfo;
          } 
        }
      }
      
    }

    return jdImageResult;
  }
  
  private void insertCommodityApi(CyncroResult resultMap, CCommodityHeader header) {
    Logger logger = Logger.getLogger(this.getClass());
    WareAddRequest wareAddRequest = new WareAddRequest();

    setCommodityParam(wareAddRequest, header);

    JdApiProviderManager jdApi = new JdApiProviderManager(wareAddRequest);
    JdApiResult jdApiResult = jdApi.execute();

    if (jdApiResult.hasError()) {
      logger.debug("错误代码:" + jdApiResult.getErrorCode() + "错误信息:" + jdApiResult.getErrorMessage());

      resultMap.setFailCount(resultMap.getFailCount() + 1L);
      logger.error("commodity_code为：" + header.getCommodityCode() + "的商品，京东商品同期化更新失败(错误信息:"
          + jdApiResult.getErrorMessage() + ")");
      resultMap.addErrorInfo("2", "commodity_code为：" + header.getCommodityCode() + "的商品，京东商品同期化更新失败(错误信息:"
          + jdApiResult.getErrorMessage() + ")");

    } else {
      // 成功记录数加1
      resultMap.setSeccessCount(resultMap.getSeccessCount() + 1L);

      WareAddResponse jdResponse = (WareAddResponse) jdApiResult.getResultBean();

      if (!updateSyncInfoToEc(false, header, jdResponse.getWareId())) {
        resultMap.addErrorInfo("2", "commodity_code为：" + header.getCommodityCode() + "的商品，EC数据更新失败");
      }
      
    }
    
  }
  
  public void updateCommodityApi(CyncroResult resultMap, CCommodityHeader header) {
    updateCommodityApi(resultMap, header, false);
  }
  
  public void updateCommodityApi(CyncroResult resultMap, CCommodityHeader header, boolean isDesc) {
    Logger logger = Logger.getLogger(this.getClass());
    WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();

    setCommodityParam(wareUpdateRequest, header, isDesc);

    JdApiProviderManager jdApi = new JdApiProviderManager(wareUpdateRequest);
    JdApiResult jdApiResult = jdApi.execute();

    if (jdApiResult.hasError()) {
      logger.debug("错误代码:" + jdApiResult.getErrorCode() + "错误信息:" + jdApiResult.getErrorMessage());

      resultMap.setFailCount(resultMap.getFailCount() + 1L);
      logger.error("commodity_code为：" + header.getCommodityCode() + "的商品，京东商品同期化更新失败(错误信息:"
          + jdApiResult.getErrorMessage() + ")");
      resultMap.addErrorInfo("2", "commodity_code为：" + header.getCommodityCode() + "的商品，京东商品同期化更新失败(错误信息:"
          + jdApiResult.getErrorMessage() + ")");

    } else {
      // 成功记录数加1
      resultMap.setSeccessCount(resultMap.getSeccessCount() + 1L);

      WareUpdateResponse jdResponse = (WareUpdateResponse) jdApiResult.getResultBean();
      
      if (!updateSyncInfoToEc(true, header, NumUtil.toLong(jdResponse.getWareId()))) {
        resultMap.addErrorInfo("2", "commodity_code为：" + header.getCommodityCode() + "的商品，EC数据更新失败");
      }
    }
  }
  
  private List<CCommodityHeader> getCynchJdInfoByCheckBox(String[] commodityCodes) {
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
  
  private boolean updateSyncInfoToEc(boolean updateFlg, CCommodityHeader header, Long wareId) {
    Logger logger = Logger.getLogger(this.getClass());
    
    // 每条记录执行完成都需要将同步标志修改为2 并更新同期时间和JD商品ID(c_commodity_header)
    header.setSyncFlagJd(SyncFlagJd.SYNCALREADY.longValue());
    header.setSyncTimeJd(DateUtil.fromString(DateUtil.toDateTimeString(DateUtil.getSysdate(), DateUtil.DEFAULT_DATETIME_FORMAT), true));
    if (!updateFlg) {
      header.setJdCommodityId(wareId);
    }
    header.setSyncUserJd(getLoginInfo().getRecordingFormat());
    setUserStatus(header);
    
    CCommodityDetailDao cchdao = DIContainer.getDao(CCommodityDetailDao.class);
    CCommodityDetail detail = cchdao.load(header.getShopCode(), header.getRepresentSkuCode());
    setUserStatus(detail);
    
    TransactionManager txMgr = DIContainer.getTransactionManager();
    try {
      txMgr.begin(getLoginInfo());
      
      txMgr.update(header);
      txMgr.update(detail);
      
      txMgr.commit();
    } catch (ConcurrencyFailureException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      return false;
    } catch (RuntimeException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      return false;
    } finally {
      txMgr.dispose();
    }
    
    return true;
  }
  
  private ServiceResult getAttributesListAll(List<Attribute> attributesResult) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());

    JdCategoryDao jdCategoryDao = DIContainer.getDao(JdCategoryDao.class);

    List<JdCategory> jdCategoryList = jdCategoryDao.loadAll();

    if (jdCategoryList != null && jdCategoryList.size() > 0) {
      for (JdCategory jdCategory : jdCategoryList) {
        CategoryAttributeSearchRequest jdRequest = new CategoryAttributeSearchRequest();
        jdRequest.setCid(jdCategory.getCategoryId());
        JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
        JdApiResult jdApiResult = jdApi.execute();

        if (jdApiResult.hasError()) {
          logger.error("京东类目属性取得失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
          serviceResult.addServiceError(CatalogServiceErrorContent.GET_JD_PROPERTY_ERROR);
          return serviceResult;
        } else {
          CategoryAttributeSearchResponse jdResponse = (CategoryAttributeSearchResponse) jdApiResult.getResultBean();
          attributesResult.addAll(jdResponse.getAttributes());
        }
      }
    }

    return serviceResult;

  }
  
  private ServiceResult getAttributesList(List<Attribute> attributesResult, String categoryId, String attributeId) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    
    CategoryAttributeSearchRequest jdRequest = new CategoryAttributeSearchRequest();
    jdRequest.setCid(categoryId);
    if (StringUtil.hasValue(attributeId)) {
      jdRequest.setAid(attributeId);
    }
    
    JdApiProviderManager jdApi = new JdApiProviderManager(jdRequest);
    JdApiResult jdApiResult = jdApi.execute();
    
    if (jdApiResult.hasError()) {
      logger.error("京东类目属性取得失败(错误信息:" + jdApiResult.getErrorMessage() + ")");
      serviceResult.addServiceError(CatalogServiceErrorContent.GET_JD_PROPERTY_ERROR);
      return serviceResult;
    } else {
      CategoryAttributeSearchResponse jdResponse = (CategoryAttributeSearchResponse) jdApiResult.getResultBean();
      attributesResult.addAll(jdResponse.getAttributes());
    }
    
    return serviceResult;
    
  }
  
  private void setCommodityParam(WareAddRequest wareAddRequest, CCommodityHeader header) {
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    
    CCommodityDetailDao detailDao = DIContainer.getDao(CCommodityDetailDao.class);
    CCommodityDetail detail = detailDao.load(header.getShopCode(), header.getRepresentSkuCode());
    
    wareAddRequest.setCid(header.getJdCategoryId());
//    wareAddRequest.setShopCategory("11111111-1111111");
    String searchWords = "";
    if (StringUtil.hasValue(header.getTmallCommoditySearchWords())) {
      searchWords = " " + header.getTmallCommoditySearchWords();
    }
    wareAddRequest.setTitle("品店 " + header.getCommodityName() + searchWords);
    wareAddRequest.setOptionType(JdOptionType.OFFSALE.getValue());
    wareAddRequest.setItemNum(header.getCommodityCode());
//    wareAddRequest.setWareLocation("20");
    wareAddRequest.setLength("100");
    wareAddRequest.setWide("100");
    wareAddRequest.setHigh("100");
    wareAddRequest.setWeight(NumUtil.toString(detail.getWeight()));

    if(StringUtil.hasValue(header.getOriginalCommodityCode())) {//组合品JD价格对应
      wareAddRequest.setMarketPrice(NumUtil.toString(BigDecimalUtil.multiply(header.getCombinationAmount(), getMarketPrice(detail))));  
      // 京东价格调整
      // wareAddRequest.setJdPrice(StringUtil.coalesceEmptyValue(NumUtil.toString(detail.getUnitPrice()), "0"));
      wareAddRequest.setJdPrice(NumUtil.toString(BigDecimalUtil.multiply(header.getCombinationAmount(), getJdPrice(detail))));  
      
    } else {//单品
      wareAddRequest.setMarketPrice(NumUtil.toString(getMarketPrice(detail)));  
      // 京东价格调整
      // wareAddRequest.setJdPrice(StringUtil.coalesceEmptyValue(NumUtil.toString(detail.getUnitPrice()), "0"));
      wareAddRequest.setJdPrice(NumUtil.toString(getJdPrice(detail)));  
    }

    
    String tmpDesc = getJdCommodityDesc(header);
    if (StringUtil.isNotNull(tmpDesc)) {
      wareAddRequest.setNotes(tmpDesc);
    } else {
      wareAddRequest.setNotes("");
    }
    String imagePath = DIContainer.getImageUploadConfig().getJdCommodityAddImgPath();
    String imgName = header.getCommodityCode() + "_l.jpg";
    String avalibleUrl = imagePath + imgName;
    FileItem fileItem = new FileItem(new File(avalibleUrl));
    try {
      wareAddRequest.setWareImage(fileItem.getContent());
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    Long jdNum = 0L;
    if (StringUtil.hasValue(header.getOriginalCommodityCode())) {
      // 组合商品分配数量
      jdNum = StockQuantityUtil.getJdStockAllocationNum(header.getCommodityCode(), header.getCombinationAmount());
    } else {
      // // 商品库存数量 - 组合商品分配数量
      // jdNum = jdNum - jdNum2;
      jdNum = StockQuantityUtil.queryJdNumsByCommodityCode(header.getCommodityCode());
    }
    
    wareAddRequest.setStockNum(jdNum.toString());
    wareAddRequest.setAttributes(getAttributesStr(header.getShopCode(), header.getCommodityCode()));
    wareAddRequest.setPayFirst("true");
    wareAddRequest.setCanVAT("false");
    if (ImportCommodityType.IMPORT.longValue().equals(header.getImportCommodityType())) {
      wareAddRequest.setImported("true");
    } else {
      wareAddRequest.setImported("false");
    }
    if (ShelfLifeFlag.BYENDTIMEORCREATETIME.longValue().equals(header.getShelfLifeFlag())) {
      wareAddRequest.setShelfLife("true");
      wareAddRequest.setShelfLifeDays(NumUtil.toString(header.getShelfLifeDays()));
    } else {
      wareAddRequest.setShelfLife("false");
      wareAddRequest.setShelfLifeDays("");
    }
    
    List<JdCommodityProperty> jdCommodityPropertyList  = cs.getJdCommodityPropertyInput(header.getShopCode(), 
        header.getCommodityCode());
    String pids = "";
    String strs = "";
    if (jdCommodityPropertyList != null && jdCommodityPropertyList.size() > 0) {
      int lastIndex = 0;
      for (JdCommodityProperty cp : jdCommodityPropertyList) {
        lastIndex++;
        if (lastIndex != jdCommodityPropertyList.size()) {
          pids += cp.getPropertyId() + "|";
          strs += cp.getValueText() + "|";
        }
      }
    }
    wareAddRequest.setInputPids(pids);
    wareAddRequest.setInputStrs(strs);
    
    wareAddRequest.setAdContent(header.getAdvertContent());
  }
  
  // 设置京东价
  private BigDecimal getJdPrice(CCommodityDetail cCommodityDetail) {
    BigDecimal jdPrice = BigDecimal.ZERO;
    BigDecimal unitPrice = cCommodityDetail.getUnitPrice();
    BigDecimal discountPrice = cCommodityDetail.getTmallDiscountPrice();

    if (!NumUtil.isNull(discountPrice)) {
      jdPrice = discountPrice;
    } else {
      jdPrice = unitPrice;
    }
    return jdPrice;
  }
  
  private void setCommodityParam(WareUpdateRequest wareUpdateRequest, CCommodityHeader header, boolean isDesc) {
    CCommodityDetailDao detailDao = DIContainer.getDao(CCommodityDetailDao.class);
    CCommodityDetail detail = detailDao.load(header.getShopCode(), header.getRepresentSkuCode());
    
    wareUpdateRequest.setWareId(NumUtil.toString(header.getJdCommodityId()));
    
    if (!isDesc) {
//    wareUpdateRequest.setShopCategory("11111111-1111111");
      String searchWords = "";
      if (StringUtil.hasValue(header.getTmallCommoditySearchWords())) {
        searchWords = " " + header.getTmallCommoditySearchWords();
      }
      wareUpdateRequest.setTitle("品店 " + header.getCommodityName() + searchWords);
//      wareUpdateRequest.setWareLocation("10");
      wareUpdateRequest.setWeight(NumUtil.toString(detail.getWeight()));

      //wareUpdateRequest.setJdPrice(StringUtil.coalesceEmptyValue(NumUtil.toString(detail.getUnitPrice()), "0"));
      if(StringUtil.hasValue(header.getOriginalCommodityCode())) {//组合品JD价格对应
        wareUpdateRequest.setMarketPrice(NumUtil.toString(BigDecimalUtil.multiply(header.getCombinationAmount(), getMarketPrice(detail))));
        wareUpdateRequest.setJdPrice(NumUtil.toString(BigDecimalUtil.multiply(header.getCombinationAmount(), getJdPrice(detail))));
      } else {//单品
        wareUpdateRequest.setMarketPrice(NumUtil.toString(getMarketPrice(detail)));
        wareUpdateRequest.setJdPrice(NumUtil.toString(getJdPrice(detail)));
      }


      if (ImportCommodityType.IMPORT.longValue().equals(header.getImportCommodityType())) {
        wareUpdateRequest.setImported("true");
      } else {
        wareUpdateRequest.setImported("false");
      }
      if (ShelfLifeFlag.BYENDTIMEORCREATETIME.longValue().equals(header.getShelfLifeFlag())) {
        wareUpdateRequest.setShelfLife("true");
        wareUpdateRequest.setShelfLifeDays(NumUtil.toString(header.getShelfLifeDays()));
      } else {
        wareUpdateRequest.setShelfLife("false");
        wareUpdateRequest.setShelfLifeDays("");
      }
      
      wareUpdateRequest.setAdContent(header.getAdvertContent());
    } else {
      String tmpDesc = getJdCommodityDesc(header);
      if (StringUtil.isNotNull(tmpDesc)) {
        wareUpdateRequest.setNotes(tmpDesc);
      } else {
        wareUpdateRequest.setNotes("");
      }
    }
  }
  
  private String getAttributesStr(String shopCode, String commodityCode) {
    String result = "";
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<JdCommodityProperty> jdCommodityPropertyList  = cs.getJdCommodityPropertyNotInput(shopCode, 
        commodityCode);
    
    if (jdCommodityPropertyList != null && jdCommodityPropertyList.size() > 0) {
      int lastIndex = 0;
      for (JdCommodityProperty cp : jdCommodityPropertyList) {
        result = result + cp.getPropertyId() + ":" + cp.getValueId();
        lastIndex++;
        if (lastIndex != jdCommodityPropertyList.size()) {
          result = result + "|";
        }
      }
    } 
    
    return result;
  }
  private BigDecimal getMarketPrice(CCommodityDetail cCommodityDetail) {
    BigDecimal marketPrice = BigDecimal.ZERO;
    BigDecimal suggestePrice = cCommodityDetail.getSuggestePrice();
   // BigDecimal unitPrice = cCommodityDetail.getUnitPrice();
    BigDecimal unitPrice = cCommodityDetail.getTmallDiscountPrice();
    
    if (NumUtil.isNull(suggestePrice)) {
      suggestePrice = BigDecimal.ZERO;
    }
    
    if (NumUtil.isNull(unitPrice)) {
      unitPrice = BigDecimal.ZERO;
    }
    
    if (BigDecimalUtil.isAbove(suggestePrice, unitPrice)) {
      marketPrice = suggestePrice;
    } else {
      marketPrice = unitPrice;
    }
    
    return marketPrice;
  }
  
  /**
   * 取得JD商品描述
   * 
   * @param header
   * @return
   */
  private String getJdCommodityDesc(CCommodityHeader header) {
    CommodityDescribeDao describeDao = DIContainer.getDao(CommodityDescribeDao.class);
    CommodityDescribe describe = describeDao.load("00000000", header.getCommodityCode());
    Boolean brFlag = false;
    String desStr = "";
    if (describe != null && StringUtil.hasValue(describe.getDecribeJd())) {
      desStr = describe.getDecribeJd();
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
    }

    return desStr;
  }
  @Override
  public CCommodityDetail getByJdCommodityCode(String jdCommodityCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_C_COMMDITY_DETAIL_BY_JD_COMMODITY_CODE, jdCommodityCode);
    CCommodityDetail cCommodityDetail = DatabaseUtil.loadAsBean(query, CCommodityDetail.class);
    return cCommodityDetail;
  }
  @Override
  public   CCommodityDetail getBySkuCode(String skuCode) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_C_COMMDITY_DETAIL_BY_SKU_CODE, skuCode);
    CCommodityDetail cCommodityDetail = DatabaseUtil.loadAsBean(query, CCommodityDetail.class);
    return cCommodityDetail;
  }

  @Override
  public JdOrderHeader getJdOrderHeaderByJdOrderNo(String JdOrderNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_JD_ORDER_HEADER_BY_JD_ORDERNO, JdOrderNo);
    return DatabaseUtil.loadAsBean(query, JdOrderHeader.class);
  }

  @Override
  public JdOrderDetail getJdOrderDetailByOrderNo(String orderNo, String shopCode, String skuCode) {
    JdOrderDetailDao jdOrderDetailDao = DIContainer.getDao(JdOrderDetailDao.class);
    return jdOrderDetailDao.load(orderNo, shopCode, skuCode);
  }

  @Override
  public JdShippingDetail getJdShippingDetailByOrderNo(String shippingNo, String shopCode, String skuCode) {
    Object[] param = new Object[] {
        shippingNo, shopCode, skuCode
    };
    Query query = new SimpleQuery(OrderServiceQuery.GET_JD_ORDER_HEADER_BY_JD_ORDERNO, param);
    return DatabaseUtil.loadAsBean(query, JdShippingDetail.class);
  }

  public List<JdOrderDetail> getJdOrderDetailCommodityList(String orderCode, String shopCode) {
    Query query = new SimpleQuery(JdServiceQuery.JD_ORDER_DETAIL_COMMODITY_LIST, orderCode, shopCode);
    return DatabaseUtil.loadAsBeanList(query, JdOrderDetail.class);
  }
  
 
  
  @Override
  public List<JdShippingHeader> getJdShippingHeaderByJdOrderNo(String jdOrderNo) {
    Query query = new SimpleQuery(OrderServiceQuery.GET_JD_SHIPPING_HEADER_BY_JD_ORDERNO, jdOrderNo);
    return DatabaseUtil.loadAsBeanList(query, JdShippingHeader.class);
  }

  @Override
  public void insertJdCouponDetail(JdCouponDetail obj) {
    JdCouponDetailDao dao = DIContainer.getDao(JdCouponDetailDao.class);
    dao.insert(obj);
  }

  @Override
  public void insertJdOrderDetail(JdOrderDetail obj) {
    JdOrderDetailDao dao = DIContainer.getDao(JdOrderDetailDao.class);
    dao.insert(obj);

  }

  @Override
  public void insertJdOrderHeader(JdOrderHeader obj) {
    JdOrderHeaderDao dao = DIContainer.getDao(JdOrderHeaderDao.class);
    dao.insert(obj);

  }

  @Override
  public void insertJdShippingDetail(JdShippingDetail obj) {
    JdShippingDetailDao dao = DIContainer.getDao(JdShippingDetailDao.class);
    dao.insert(obj);

  }

  @Override
  public void insertJdShippingHeader(JdShippingHeader obj) {
    JdShippingHeaderDao dao = DIContainer.getDao(JdShippingHeaderDao.class);
    dao.insert(obj);

  }

  @Override
  public void updateJdOrderHeader(JdOrderHeader obj) {
    JdOrderHeaderDao dao = DIContainer.getDao(JdOrderHeaderDao.class);
    dao.update(obj);

  }

  @Override
  public void updateJdShippingHeader(JdShippingHeader obj) {
    JdShippingHeaderDao dao = DIContainer.getDao(JdShippingHeaderDao.class);
    dao.update(obj);

  }
  
  @Override
  public int jdOrderDownlaod(String startDate,String endDate) {
    Logger logger = Logger.getLogger(this.getClass());
    int returnInt = 0;
    String errorMes=null;
    try {
      JdOrderDownload orderDownload = new JdOrderDownload();
      String start = orderDownload.GetDateStart(startDate).replaceAll("-", "/");// batch前一次执行时间
      String end = orderDownload.GetDateEnd(endDate).replaceAll("-", "/");// 系统现在执行时间
      // 获取API下载订单
      if (DateUtil.fromString(start, true).getTime() > DateUtil.fromString(end, true).getTime()) {
        errorMes = "京东订单下载时间不正确。";
        StringUtil.sendJdErrMail("",errorMes);
        logger.error(errorMes);
        return returnInt = -1;
      }
      
      if ((DateUtil.fromString(end, true).getTime() / 1000 - DateUtil.fromString(start, true).getTime() / 1000 > 60 * 60 * 24)) {
        errorMes =  "京东订单下载时间不正确。时间差必须在一天内。";
        StringUtil.sendJdErrMail("",errorMes);
        logger.error(errorMes);
        return returnInt = -1;
      }
      start = start.replaceAll("/", "-");
      end = end.replaceAll("/", "-");
      JdOrderDownloadErrorInfo errorInfo = orderDownload.orderDownload(start,end);
      if(errorInfo == null){
        StringUtil.sendJdSuccMail(start, end);
      }else{
        StringUtil.sendJdErrMail(errorInfo.getJdOrderNo(), errorInfo.getMessage());
        return returnInt = -1;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnInt;

  }

  @Override
  public JdBatchTime getJdBatchTime() {
    Query query = new SimpleQuery(OrderServiceQuery.JD_BATCH_INFO_QUERY);
    return DatabaseUtil.loadAsBean(query, JdBatchTime.class);
  }

  @Override
  public ServiceResult OperateJdBatchTime(Date date, Long status) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Logger logger = Logger.getLogger(this.getClass());
    TransactionManager txMgr = DIContainer.getTransactionManager();
    txMgr.begin(getLoginInfo());
    try {
      JdBatchTime batchTime = getJdBatchTime();
      if (batchTime == null) {
        // 添加
        JdBatchTime batchTime1 = new JdBatchTime();
        batchTime1.setFromTime(date);
        batchTime1.setCreatedDatetime(DateUtil.getSysdate());
        batchTime1.setCreatedUser(this.getLoginInfo().getRecordingFormat());
        batchTime1.setUpdatedDatetime(DateUtil.getSysdate());
        batchTime1.setUpdatedUser(this.getLoginInfo().getRecordingFormat());
        batchTime1.setBatchStatus(status);
        txMgr.insert(batchTime1);
      } else {
        // 更新
        Query query = new SimpleQuery(OrderServiceQuery.JD_BATCH_INFO_UPDATE, date, DateUtil.getSysdate(), getLoginInfo()
            .getRecordingFormat(), status);
        txMgr.executeUpdate(query);
      }
      txMgr.commit();
    } catch (DataAccessException e) {
      txMgr.rollback();
      logger.error(e.getMessage());
      serviceResult.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
    }
    return serviceResult;
  }

  public JdOrderHeader getJdOrderHeader(String orderNo) {
    JdOrderHeaderDao jHeaderDao = DIContainer.getDao(JdOrderHeaderDao.class);
    return jHeaderDao.load(orderNo);
  }


  @Override
  public void insertJdOrderInvoice(OrderInvoice obj) {
    OrderInvoiceDao dao = DIContainer.getDao(OrderInvoiceDao.class);
    dao.insert(obj);
   
  }
  
  public List<JdShippingHeader> getJdShippingHeaderList() {
    //JdShippingHeaderDao sHeaderDao = DIContainer.getDao(JdShippingHeaderDao.class);
    //return sHeaderDao.findByQuery(JdServiceQuery.JD_SHIPPING_HEADER_LIST_GET);
    Query query = new SimpleQuery(JdServiceQuery.JD_SHIPPING_HEADER_LIST_GET);
    return DatabaseUtil.loadAsBeanList(query, JdShippingHeader.class);
  }
  
  @Override
  public CommodityHeader getCommodityHeader(String shopCode, String commodityCode) {
    CommodityHeaderDao dao = DIContainer.getDao(CommodityHeaderDao.class);
    CommodityHeader commodityHeader = null;
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(commodityCode)) {
      commodityHeader = dao.load(shopCode, commodityCode);
    }
    return commodityHeader;
  }


  @Override
  public JdStock loadJdStock(String shopCode, String skuCode) {
    JdStockDao dao = DIContainer.getDao(JdStockDao.class);
    return dao.load(shopCode,skuCode);
  }


  @Override
  public JdStockAllocation loadJdStockAllocation(String shopCode, String skuCode) {
    JdStockAllocationDao dao = DIContainer.getDao(JdStockAllocationDao.class);
    return dao.load(shopCode,skuCode);
  }


  @Override
  public JdSuitCommodity loadJdSuitCommodity(String commodityCode) {
    JdSuitCommodityDao dao = DIContainer.getDao(JdSuitCommodityDao.class);
    return dao.load(commodityCode);
  }


  @Override
  public void updateJdStock(JdStock obj) {
    JdStockDao dao = DIContainer.getDao(JdStockDao.class);
     dao.update(obj);
    
  }


  @Override
  public void updateJdStockAllocation(JdStockAllocation obj) {
    JdStockAllocationDao dao = DIContainer.getDao(JdStockAllocationDao.class);
    dao.update(obj);
    
  }


  @Override
  public void updateJdSuitCommodity(JdSuitCommodity obj) {
    JdSuitCommodityDao dao = DIContainer.getDao(JdSuitCommodityDao.class);
    dao.update(obj);
  }
  
  // 2014/06/06 库存更新对应 ob_李先超 add start
  //商品库存同期到京东系统
  public boolean jdStockCyncroApi(Long jdCommodityId, Long jdEffectiveStock) {
    if (jdCommodityId == null) {
      return true;
    }
    
    WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
    wareUpdateRequest.setWareId(jdCommodityId.toString());
    wareUpdateRequest.setStockNum(NumUtil.toString(jdEffectiveStock));
    JdApiProviderManager jdApi = new JdApiProviderManager(wareUpdateRequest);
    JdApiResult jdApiResult = jdApi.execute();
    if (jdApiResult.hasError()) {
      return false;
    }
    return true;
  }
  // 2014/06/06 库存更新对应 ob_李先超 add end


  @Override
  public CCommodityDetail loadCCommodityDetail(String shopCode, String skuCode) {
    CCommodityDetailDao dao = DIContainer.getDao(CCommodityDetailDao.class);
    return dao.load(shopCode,skuCode);
  }


  @Override
  public void insertJdShippingDetailComposition(JdShippingDetailComposition obj) {
    JdShippingDetailCompositionDao dao = DIContainer.getDao(JdShippingDetailCompositionDao.class);
    dao.insert(obj);
  }


  @Override
  public SearchResult<JdOrderlLists> getJdSplistLists(JdOrderSearchCondition condition) {
    return DatabaseUtil.executeSearch(new JdOrderSearchQuery(condition));
  }
  
}
