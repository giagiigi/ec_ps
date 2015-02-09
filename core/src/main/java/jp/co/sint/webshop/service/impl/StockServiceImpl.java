package jp.co.sint.webshop.service.impl;

import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.api.jd.JdApiProviderManager;
import jp.co.sint.webshop.api.jd.JdApiResult;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.configure.JdStockSendMailConfig;
import jp.co.sint.webshop.configure.TmallStockSendMailConfig;
import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CommoditySkuDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.dao.StockTempDao;
import jp.co.sint.webshop.data.domain.CommodityJdUseFlg;
import jp.co.sint.webshop.data.domain.CommodityTmallUseFlg;
import jp.co.sint.webshop.data.domain.OrderDownLoadStatus;
import jp.co.sint.webshop.data.domain.RatioType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockChangeType;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.data.dto.CommoditySku;
import jp.co.sint.webshop.data.dto.JdStock;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockHistory;
import jp.co.sint.webshop.data.dto.StockRatio;
import jp.co.sint.webshop.data.dto.StockTemp;
import jp.co.sint.webshop.data.dto.TmallStock;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.data.hibernate.TransactionManagerImpl;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.service.stock.JdCombinInfo;
import jp.co.sint.webshop.service.stock.JdSuitInfo;
import jp.co.sint.webshop.service.stock.StockServiceQuery;
import jp.co.sint.webshop.service.stock.StockTempInfo;
import jp.co.sint.webshop.service.stock.TmallCombinInfo;
import jp.co.sint.webshop.service.stock.TmallSuitInfo;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallManager;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.request.ware.SkuCustomGetRequest;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareSkuStockUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.response.ware.SkuCustomGetResponse;
import com.jd.open.api.sdk.response.ware.WareGetResponse;

public class StockServiceImpl extends AbstractServiceImpl implements StockService {

  private static final long serialVersionUID = 1L;

  public void setStockTempForAllocatedQuantity(String orderNo, Long stockChangeType){
    Logger logger = Logger.getLogger(this.getClass());
    String sql = "";
    if (StockChangeType.EC_ALLOCATED.longValue().equals(stockChangeType)){
      sql = StockServiceQuery.CHANGE_EC_ALLOCATED_QUANTITY;
    }else if(StockChangeType.TM_ALLOCATED.longValue().equals(stockChangeType)){
      sql = StockServiceQuery.CHANGE_TM_ALLOCATED_QUANTITY;
    }else{
      sql = StockServiceQuery.CHANGE_JD_ALLOCATED_QUANTITY;
    }
//    ServiceResultImpl result = new ServiceResultImpl();
    Query query = new SimpleQuery(sql, orderNo);
    List<OrderDetail> orderDetails =  DatabaseUtil.loadAsBeanList(query, OrderDetail.class);
    StockTempDao dao = DIContainer.getDao(StockTempDao.class);
    // 2.事物开始
    TransactionManager manager = DIContainer.getTransactionManager();
    try {
      manager.begin(this.getLoginInfo());
      // 2.1循环取得的商品List
      for (OrderDetail orderDetail : orderDetails){
        StockTemp stockTemp = new StockTemp();

        stockTemp.setShopCode(orderDetail.getShopCode());
        stockTemp.setSkuCode(orderDetail.getSkuCode());
        stockTemp.setStockChangeType(stockChangeType);

        if (dao.exists(stockTemp.getShopCode(), stockTemp.getSkuCode(), stockTemp.getStockChangeType())){
          // 2.2如SKU编号对应的StockTemp中已经存在时进行更新处理
          // 变更数量=原变更数量+本订单取得引当数
          stockTemp = dao.load(stockTemp.getShopCode(), stockTemp.getSkuCode(), stockTemp.getStockChangeType());
          stockTemp.setStockChangeQuantity(stockTemp.getStockChangeQuantity() + orderDetail.getPurchasingAmount());
          setUserStatus(stockTemp);
          manager.update(stockTemp);
        }else{
          // 2.3如SKU编号对应的StockTemp中未存在时进行登录处理
          // 库存变更数量 = 循环中的引当数
          stockTemp.setStockChangeQuantity(orderDetail.getPurchasingAmount());
          setUserStatus(stockTemp);
          manager.insert(stockTemp);
        }
      }
      // 3.提交事物
      manager.commit();
    } catch (Exception e) {
      manager.rollback();
      logger.error(e);
    }
  }
  
  public void sendStockCynchroMail(List<String> tmallCynchroFailCodeList, List<String> jdCynchroFailCodeList,
      List<String> commodityHasNotCodeList) {

    MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());

    // 天猫库存同步失败通知邮件发送
    if (tmallCynchroFailCodeList != null && tmallCynchroFailCodeList.size() > 0) {
      MailInfo mailInfo = new MailInfo();
      StringBuffer sb = new StringBuffer();
      sb.append("以下商品的库存同期至淘宝系统时，发生异常，请您手动调节这些商品在淘宝系统中的库存量。<BR>");
      sb.append("商品编号：<BR>");
      Set<String> tmallCynchroFailCodeSet = new HashSet<String>();
      for (String commodityCode : tmallCynchroFailCodeList) {
        tmallCynchroFailCodeSet.add(commodityCode);
      }
      for (String commodityCode : tmallCynchroFailCodeSet) {
        sb.append(commodityCode + "<BR>");
      }
      mailInfo.setText(sb.toString());
      mailInfo.setSubject("【品店】库存同步失败通知");
      mailInfo.setSendDate(DateUtil.getSysdate());

      TmallStockSendMailConfig tmllMailSend = DIContainer.get(TmallStockSendMailConfig.class.getSimpleName());
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
      svc.sendImmediate(mailInfo);
    }

    // 京东库存同步失败通知邮件发送
    if (jdCynchroFailCodeList != null && jdCynchroFailCodeList.size() > 0) {
      MailInfo mailInfo = new MailInfo();
      StringBuffer sb = new StringBuffer();
      sb.append("以下商品的库存同期至京东系统时，发生异常，请您手动调节这些商品在京东系统中的库存量。<BR>");
      sb.append("商品编号：<BR>");
      Set<String> jdCynchroFailCodeSet = new HashSet<String>();
      for (String commodityCode : jdCynchroFailCodeList) {
        jdCynchroFailCodeSet.add(commodityCode);
      }
      for (String commodityCode : jdCynchroFailCodeSet) {
        sb.append(commodityCode + "<BR>");
      }
      mailInfo.setText(sb.toString());
      mailInfo.setSubject("【品店】库存同步失败通知");
      mailInfo.setSendDate(DateUtil.getSysdate());

      JdStockSendMailConfig tmllMailSend = DIContainer.get(JdStockSendMailConfig.class.getSimpleName());
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
      svc.sendImmediate(mailInfo);
    }

    // 商品不存在通知邮件发送
    if (commodityHasNotCodeList != null && commodityHasNotCodeList.size() > 0) {
      ShopDao shopDao = DIContainer.getDao(ShopDao.class);
      Shop shop = shopDao.load(DIContainer.getWebshopConfig().getSiteShopCode());

      MailInfo mailInfo = new MailInfo();
      StringBuffer sb = new StringBuffer();
      sb.append("以下商品不存在，请确认。<BR>");
      sb.append("商品编号：<BR>");
      Set<String> commodityHasNotCodeSet = new HashSet<String>();
      for (String commodityCode : commodityHasNotCodeList) {
        commodityHasNotCodeSet.add(commodityCode);
      }
      for (String commodityCode : commodityHasNotCodeSet) {
        sb.append(commodityCode + "<BR>");
      }
      mailInfo.setText(sb.toString());
      mailInfo.setSubject("【品店】库存同步失败通知");
      mailInfo.setSendDate(DateUtil.getSysdate());
      mailInfo.setFromInfo(shop.getEmail(), shop.getShopName());
      mailInfo.addToList(shop.getEmail(), shop.getShopName());
      mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
      svc.sendImmediate(mailInfo);
    }
  }
  //2014/06/05 库存更新对应 ob_李先超 add start
  public void stockCynchroApi(List<String> tmallErrorList, List<String> jdErrorList, List<String> noDataErrorList) {
    Logger logger = Logger.getLogger(this.getClass());
    // 创建事务
    TransactionManager txm = DIContainer.getTransactionManager();
    try {
      txm.begin(getLoginInfo());
      //刪除未连携的数据
      Query deleteQuery = new SimpleQuery(StockServiceQuery.DELETE_STOCK_TEMP_NOT_SYNC);
      txm.executeUpdate(deleteQuery);
      txm.commit();
    } catch (ConcurrencyFailureException e) {
      txm.rollback();
      logger.error(e.getMessage());
    } catch (Exception e) {
      txm.rollback();
      logger.error(e.getMessage());
    } finally {
      txm.dispose();
    }
   
    //取得所有待连携的商品增量信息
    Query selectQuery = new SimpleQuery(StockServiceQuery.SELECT_STOCK_TEMP_SYNC);
    List<StockTemp> commodityList = DatabaseUtil.loadAsBeanList(selectQuery, StockTemp.class);
    
    //进行连携处理
    for (StockTemp stockTemp : commodityList) {
      // 创建事务
      txm = DIContainer.getTransactionManager();
      try {
        txm.begin(getLoginInfo());
        txm.delete(stockTemp);
        CCommodityHeader commodity = DIContainer.getDao(CCommodityHeaderDao.class).load(stockTemp.getShopCode(), stockTemp.getSkuCode());
        if (commodity != null) {
          Query skuQuery = new SimpleQuery(StockServiceQuery.GET_COMMODITY_MASTER_BY_SKU_CODE, commodity.getCommodityCode());
          
          CommodityMaster cm = DatabaseUtil.loadAsBean(skuQuery, CommodityMaster.class);
          
          CommoditySku cSku = DatabaseUtil.loadAsBean((new SimpleQuery(StockServiceQuery.GET_COMMODITY_SKU_BY_SKU_CODE, commodity.getCommodityCode())),CommoditySku.class);
          
          if (StockChangeType.TM_STOCK_ADD.longValue().equals(stockTemp.getStockChangeType())
              && (commodity.getTmallCommodityId() != null || (cm != null && cSku!=null))) {
            // TM同步处理
            Long tmallCommodityId = (cm != null && CommodityTmallUseFlg.ISTMUSEFLG.getValue().equals(
                NumUtil.toString(cSku.getTmallUseFlag()))) ? NumUtil.toLong(cm.getTmallCommodityCode()) : commodity
                .getTmallCommodityId();
            if (!tmallStockCyncroApi(tmallCommodityId, stockTemp.getStockChangeQuantity(), stockTemp.getSkuCode())) {
              txm.rollback();
              tmallErrorList.add(stockTemp.getCommodityCode());
              continue;
            } else if (!checkTmallStock(commodity)) {
              // txm.rollback();
              tmallErrorList.add(stockTemp.getCommodityCode());
              // continue;
            }
          } else if (StockChangeType.JD_STOCK_ADD.longValue().equals(stockTemp.getStockChangeType())) {
            // JD同步处理
            if ((cm != null && cSku != null && CommodityJdUseFlg.ISJDSEFLG.getValue().equals(NumUtil.toString(cSku.getJdUseFlag()))
                && !jdSkuStockCyncroApi(commodity.getCommodityCode(), stockTemp.getStockChangeQuantity()) || commodity
                .getJdCommodityId() != null
                && !jdStockCyncroApi(commodity.getJdCommodityId(), stockTemp.getStockChangeQuantity()))) {
              txm.rollback();
              jdErrorList.add(stockTemp.getCommodityCode());
              continue;
            }
          }
        }
        txm.commit();
      } catch (ConcurrencyFailureException e) {
        txm.rollback();
        logger.error(e.getMessage());
      } catch (Exception e) {
        txm.rollback();
        logger.error(e.getMessage());
      } finally {
        txm.dispose();
      }
    }

  }
  
  public JdSuitCommodity getJdSuitCommodity(String commodityCode){
    Query query = new SimpleQuery(StockServiceQuery.GET_ALL_JD_SUIT_COMMODITY, commodityCode);
    return DatabaseUtil.loadAsBean(query, JdSuitCommodity.class); 
  }
  
  public ServiceResult jdStockRedistribution(JdSuitCommodity oldJDSuitCommDto, JdSuitCommodity suitCommodityBean, List<String> tmallApiFailCodeList, List<String> jdApiFailCodeList, List<String> stockFailCodeList) {
    //初始化同期错误集合
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    // TMALL批处理
    JdService service = ServiceLocator.getJdService(getLoginInfo());
    int returnInt = 0;
    returnInt = service.jdOrderDownlaod("", "");
    if (returnInt == 0) {
      logger.debug("京东订单下载批处理成功!");
    }else if(returnInt== OrderDownLoadStatus.DOWNLOADFAILED.longValue().intValue()){
      logger.debug("京东订单下载过程出现错误!");
      result.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
      return result;
    }
    
    List<SetCommodityComposition> jdCompositionList = new ArrayList<SetCommodityComposition>();
    
    Long oldScaleValue = 0L;
    
    //创建事务
    TransactionManager txMgr = DIContainer.getTransactionManager();
    
    try {
      //事务开始
      txMgr.begin(getLoginInfo());
      StockTempInfo stockTempInfo = new StockTempInfo();
      
      //更新套装比例信息值
      if (oldJDSuitCommDto != null) {
        oldScaleValue = oldJDSuitCommDto.getScaleValue();
        oldJDSuitCommDto.setScaleValue(suitCommodityBean.getScaleValue());
        setUserStatus(oldJDSuitCommDto);
        txMgr.update(oldJDSuitCommDto);
      } else {
        suitCommodityBean.setStockQuantity(0L);
        suitCommodityBean.setAllocatedQuantity(0L);
        setUserStatus(suitCommodityBean);
        txMgr.insert(suitCommodityBean);
      }
      
      
      if (oldScaleValue > 0L || suitCommodityBean.getScaleValue() > 0L) {
        //取得套餐商品对应明细商品的List
        Query compositionQuery = new SimpleQuery(StockServiceQuery.GET_COMMODITY_COMPOSITION_LIST, suitCommodityBean.getCommodityCode());
        jdCompositionList = DatabaseUtil.loadAsBeanList(compositionQuery, SetCommodityComposition.class);
        
        //库存再计算处理
        for (SetCommodityComposition jdComposition : jdCompositionList) {
          Query stockQuery = new SimpleQuery(StockServiceQuery.UPDATE_STOCK_OF_CHILD_COMMODITY, getLoginInfo().getRecordingFormat(), 
              DateUtil.getSysdate(), jdComposition.getChildCommodityCode());
          txMgr.executeUpdate(stockQuery);
          
          //调用库存再计算处理函数，参数：1、事务 2、商品编号 3、tmallApiFailCodeList 4、jdApiFailCodeList 5、stockFailCodeList
          result = (ServiceResultImpl) stockRecalculation(txMgr, jdComposition.getChildCommodityCode(), 
              tmallApiFailCodeList, jdApiFailCodeList, stockFailCodeList,stockTempInfo);
          
          if (result.hasError()) {
            txMgr.rollback();
            return result;
          }
        }
      }
      
      //库存增量登录
      updateStockTemp(txMgr,stockTempInfo);
      
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
  
  public TmallSuitCommodity getTmallSuitCommodity(String commodityCode){
    Query query = new SimpleQuery(StockServiceQuery.GET_ALL_TMALL_SUIT_COMMODITY, commodityCode);
    return DatabaseUtil.loadAsBean(query, TmallSuitCommodity.class); 
  }
  
  public ServiceResult tmallStockRedistribution(TmallSuitCommodity oldTMSuitCommDto, TmallSuitCommodity suitCommodityBean, List<String> tmallApiFailCodeList, List<String> jdApiFailCodeList, List<String> stockFailCodeList) {
    // 初始化同期错误集合
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl result = new ServiceResultImpl();
    // TMALL批处理
    JdService service = ServiceLocator.getJdService(getLoginInfo());
    int returnInt = 0;
    returnInt = service.jdOrderDownlaod("", "");
    if (returnInt == 0) {
      logger.debug("京东订单下载批处理成功!");
    }else if(returnInt== OrderDownLoadStatus.DOWNLOADFAILED.longValue().intValue()){
      logger.debug("京东订单下载过程出现错误!");
      result.addServiceError(OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED);
      return result;
    }
    List<SetCommodityComposition> tmallCompositionList = new ArrayList<SetCommodityComposition>();
    
    Long oldScaleValue = 0L;

    // 创建事务
    TransactionManager txMgr = DIContainer.getTransactionManager();

    try {
      // 事务开始
      txMgr.begin(getLoginInfo());
      StockTempInfo stockTempInfo = new StockTempInfo();

      if (oldTMSuitCommDto != null) {
        oldScaleValue = oldTMSuitCommDto.getScaleValue();
        oldTMSuitCommDto.setScaleValue(suitCommodityBean.getScaleValue());
        setUserStatus(oldTMSuitCommDto);
        txMgr.update(oldTMSuitCommDto);
      } else {
        suitCommodityBean.setStockQuantity(0L);
        suitCommodityBean.setAllocatedQuantity(0L);
        setUserStatus(suitCommodityBean);
        txMgr.insert(suitCommodityBean);
      }
      
      if (oldScaleValue > 0L || suitCommodityBean.getScaleValue() > 0L) {
        // 取得套餐商品对应明细商品的List
        Query compositionQuery = new SimpleQuery(StockServiceQuery.GET_COMMODITY_COMPOSITION_LIST, suitCommodityBean.getCommodityCode());
        tmallCompositionList = DatabaseUtil.loadAsBeanList(compositionQuery, SetCommodityComposition.class);

        // 库存再计算处理
        for (SetCommodityComposition tmallComposition : tmallCompositionList) {
          Query stockQuery = new SimpleQuery(StockServiceQuery.UPDATE_STOCK_OF_CHILD_COMMODITY, getLoginInfo().getRecordingFormat(), 
              DateUtil.getSysdate(), tmallComposition.getChildCommodityCode());
          txMgr.executeUpdate(stockQuery);

          // 调用库存再计算处理函数，参数：1、事务 2、商品编号 3、tmallApiFailCodeList 4、jdApiFailCodeList 5、stockFailCodeList
          result = (ServiceResultImpl) stockRecalculation(txMgr, tmallComposition.getChildCommodityCode(),
              tmallApiFailCodeList, jdApiFailCodeList, stockFailCodeList, stockTempInfo);
          
          if (result.hasError()) {
            txMgr.rollback();
            return result;
          }
        }
      }
      // 库存增量登录
      updateStockTemp(txMgr, stockTempInfo);
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
  
  public ServiceResult stockRecomputingApi() {
    
    ServiceResultImpl result = new ServiceResultImpl();
    StockDao dao = DIContainer.getDao(StockDao.class);
    StockTempDao tempDao = DIContainer.getDao(StockTempDao.class);
    String shopCode = DIContainer.getWebshopConfig().getSiteShopCode();
    Logger logger = Logger.getLogger(this.getClass());
    
    //对象商品一览取得
    List<StockTemp> stockList = new ArrayList<StockTemp>();
    Query tempQuery = new SimpleQuery(StockServiceQuery.GET_STOCK_TEMP_SKU_CODE);
    stockList = DatabaseUtil.loadAsBeanList(tempQuery, StockTemp.class);
    
    //调用库存再分配API重新分配库存
    //TMAPI连协失败商品编号列表
    List<String> tmallApiFailCodeList = new ArrayList<String>();
    //JDAPI连协失败商品编号列表
    List<String> jdApiFailCodeList = new ArrayList<String>();
    //有效库存错误商品编号列表
    List<String> stockFailCodeList = new ArrayList<String>();
    
    for (StockTemp stockTemp : stockList) {
      //创建事务
      TransactionManager txMgr = DIContainer.getTransactionManager();
      
      try {
        //事务开始
        txMgr.begin(getLoginInfo());
        StockTempInfo stockTempInfo = new StockTempInfo();
        
        //取出该商品的临时库存一览
        Query query = new SimpleQuery(StockServiceQuery.GET_STOCK_TEMP_LIST, shopCode, stockTemp.getSkuCode());
        List<StockTemp> stockTempList = DatabaseUtil.loadAsBeanList(query, StockTemp.class);
        
        //更新EC在库
        Stock stock = dao.load(shopCode, stockTemp.getSkuCode());
        stock.setStockQuantity(stock.getStockQuantity() + stock.getStockThreshold());
        
        //更新该商品的临时库存：库存变更区分=【0：总库存变动】和库存变更区分=【4：安全库存变动】
        for (StockTemp stockTempSub : stockTempList) {
          //库存变更区分=【0：总库存变动】时
          if (StockChangeType.ALL.longValue().equals(stockTempSub.getStockChangeType())) {
            stock.setStockQuantity(stock.getStockQuantity() + stockTempSub.getStockChangeQuantity());
            
            // 总库存变动时，安全库存调整
            if (stock.getStockThreshold() > 0 && stockTempSub.getStockChangeQuantity() < 0) {
              if ((stockTempSub.getStockChangeQuantity() + stock.getStockThreshold()) >= 0) {
                stock.setStockThreshold(stockTempSub.getStockChangeQuantity() + stock.getStockThreshold());
              } else {
                stock.setStockThreshold(0L);
              }
            } 
          }
          
          //库存变更区分=【4：安全库存变动】时
          if (StockChangeType.SAFE_ALLOCATED.longValue().equals(stockTempSub.getStockChangeType())) {
            stock.setStockThreshold(stock.getStockThreshold() + stockTempSub.getStockChangeQuantity());
          }
        }
        
        setUserStatus(stock);
        
        ValidationSummary summary = BeanValidator.validate(stock);
        if (summary.hasError()) {
          for (String message : summary.getErrorMessages()) {
            logger.error(message);
          }
          result.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          return result;
        }
        
        txMgr.update(stock);
        
        //删除该商品的临时库存信息
        for (StockTemp tempStock : stockTempList) {
          StockTemp stocktemp = tempDao.load(shopCode, tempStock.getSkuCode(), tempStock.getStockChangeType());
          if (stocktemp != null) {
            txMgr.delete(stocktemp);
          }
        }
        
        //调用库存再计算处理函数，参数：1、事务 2、商品编号 3、tmallApiFailCodeList 4、jdApiFailCodeList 5、stockFailCodeList
        result = (ServiceResultImpl) stockRecalculation(txMgr, stockTemp.getSkuCode(), tmallApiFailCodeList, 
            jdApiFailCodeList, stockFailCodeList,stockTempInfo,true);

        if (result.hasError()){
          txMgr.rollback();
        } else {
          //库存增量登录
          updateStockTemp(txMgr,stockTempInfo);
          txMgr.commit();
          
          stockCynchroApi(tmallApiFailCodeList, jdApiFailCodeList, stockFailCodeList);
          
        }
        
      } catch (ConcurrencyFailureException e) {
        txMgr.rollback();
        throw e;
      } catch (RuntimeException e) {
        txMgr.rollback();
        result.addServiceError(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR);
        logger.error(e.getMessage());
      } finally {
        txMgr.dispose();
      }
    }
    //发送邮件
    if (tmallApiFailCodeList.size() > 0 || jdApiFailCodeList.size() > 0 || stockFailCodeList.size() > 0) {
      sendStockCynchroMail(tmallApiFailCodeList, jdApiFailCodeList, stockFailCodeList);
    }
    return result;
  }
  // 2014/06/05 库存更新对应 ob_李先超 add end
  
   public ServiceResult getTmApiStock(String commodityCode, Map<String, Long> tmApiStockMap, List<String> tmapiErrCommodityCode) {

    ServiceResultImpl result = new ServiceResultImpl();
//    Logger logger = Logger.getLogger(this.getClass());

    // Map中该商品的API有效库存是否存在验证
    if (tmApiStockMap.get(commodityCode) == null) {
      // 取得商品编号对应的 TM商品ID
      CCommodityHeader cCommodityHeader = DIContainer.getDao(CCommodityHeaderDao.class).load(
          DIContainer.getWebshopConfig().getSiteShopCode(), commodityCode);
      if (cCommodityHeader == null) {
        result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return result;
      } else {
        if (cCommodityHeader.getTmallCommodityId() != null) {
          // API连协取得API有效库存
          TmallManager manager = new TmallManager();
          TmallCommodityHeader tmallCommodity = manager.searchCommodity(NumUtil.toString(cCommodityHeader.getTmallCommodityId()));
          if (tmallCommodity == null) {
            tmapiErrCommodityCode.add(commodityCode);
            result.addServiceError(CommonServiceErrorContent.TM_API_ERROR);
            return result;
          } else {
            tmApiStockMap.put(commodityCode, NumUtil.toLong(tmallCommodity.getNum()));
          }
//          if (!tmallStockCyncroApi(cCommodityHeader.getTmallCommodityId(),0L,commodityCode)) {
//            tmapiErrCommodityCode.add(commodityCode);
//            result.addServiceError(CommonServiceErrorContent.TM_API_ERROR);
//            return result;
//          }
        }
      }
    }

    return result;

  }

  public ServiceResult getJdApiStock(String commodityCode, Map<String, Long> jdApiStockMap, List<String> jdapiErrCommodityCode) {

    ServiceResultImpl result = new ServiceResultImpl();
    //JDAPI取得的库存数为总库存,非有效库存，所以该处理
    /*// Map中该商品的API有效库存是否存在验证
    if (jdApiStockMap.get(commodityCode) == null) {
      // 取得商品编号对应的 jd商品ID
      CCommodityHeader cCommodityHeader = DIContainer.getDao(CCommodityHeaderDao.class).load(
          DIContainer.getWebshopConfig().getSiteShopCode(), commodityCode);
      if (cCommodityHeader == null) {
        result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
        return result;
      } else {
        if (cCommodityHeader.getJdCommodityId() != null) {
          // API连协取得API有效库存
          WareGetRequest wareGetRequest = new WareGetRequest();
          wareGetRequest.setWareId(NumUtil.toString(cCommodityHeader.getJdCommodityId()));
          //wareGetRequest.setFields("");
          JdApiProviderManager jdApi = new JdApiProviderManager(wareGetRequest);
          JdApiResult jdResult = jdApi.execute();
          WareGetResponse response = (WareGetResponse) jdResult.getResultBean();
          if (jdResult.hasError()) {
            jdapiErrCommodityCode.add(commodityCode);
            result.addServiceError(CommonServiceErrorContent.JD_API_ERROR);
            return result;
          } else {
            jdApiStockMap.put(commodityCode, NumUtil.toLong(String.valueOf(response.getWare().getStockNum())));
          }
        }
      }
    }*/

    return result;

  }

  public ServiceResult setStockHistory(TransactionManager transactionManager, String commodityCode,StockTempInfo orgTempInfo) {
    ServiceResultImpl result = new ServiceResultImpl();
    // EC库存信息
    Stock ecStock = new Stock();

    // TM单品库存信息
    TmallStock tmAllStock = new TmallStock();

    // TM组合库存信息
    List<TmallCombinInfo> tmallCombinInfoList = new ArrayList<TmallCombinInfo>();

    // TM套装库存信息
    List<TmallSuitInfo> tmallSuitInfoList = new ArrayList<TmallSuitInfo>();

    // JD单品库存信息
    JdStock jdStock = new JdStock();

    // JD组合库存信息
    List<JdCombinInfo> jdCombinInfoList = new ArrayList<JdCombinInfo>();

    // JD套装库存信息
    List<JdSuitInfo> jdSuitInfoList = new ArrayList<JdSuitInfo>();

    // 在库品区分
    Long onStockFlag = 2L;

    // 库存比例
    List<StockRatio> stockRatioList = new ArrayList<StockRatio>();

    // 在库备注
    String stockMemo;

    // EC库存信息取得
    Query query = new SimpleQuery(StockServiceQuery.GET_STOCK, commodityCode);
    List<Stock> stockList = DatabaseUtil.loadAsBeanList(query, Stock.class);

    // EC库存信息是否存在
    if (stockList.size() > 0) {
      ecStock = stockList.get(0);
    } else {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // TM单品库存信息取得
    query = new SimpleQuery(StockServiceQuery.GET_TMALL_STOCK, commodityCode);
    List<TmallStock> tmallStockList = DatabaseUtil.loadAsBeanList(query, TmallStock.class);

    // TM单品库存信息是否存在
    if (tmallStockList != null && tmallStockList.size() > 0) {
      tmAllStock = tmallStockList.get(0);
      if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.TM_STOCK_ADD.getValue()+tmAllStock.getSkuCode())){
        orgTempInfo.getCommodityCodeList().add(StockChangeType.TM_STOCK_ADD.getValue()+tmAllStock.getSkuCode());
        orgTempInfo.getTempMap().put(StockChangeType.TM_STOCK_ADD.getValue()+tmAllStock.getSkuCode(),tmAllStock.getStockQuantity());
      }
    }

    // 淘宝套装信息取得
    query = new SimpleQuery(StockServiceQuery.GET_TMALL_SUIT_COMMODITY, commodityCode);
    List<TmallSuitCommodity> tmallSuitCommodityList = DatabaseUtil.loadAsBeanList(query, TmallSuitCommodity.class);

    if (tmallSuitCommodityList != null && tmallSuitCommodityList.size() > 0) {
      for (TmallSuitCommodity tmallSuitCommodity : tmallSuitCommodityList) {
        TmallSuitInfo tmallSuitInfo = new TmallSuitInfo();
        tmallSuitInfo.setTmallSuitCommodity(tmallSuitCommodity);
        if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.TM_STOCK_ADD.getValue()+tmallSuitCommodity.getCommodityCode())){
          orgTempInfo.getCommodityCodeList().add(StockChangeType.TM_STOCK_ADD.getValue()+tmallSuitCommodity.getCommodityCode());
          orgTempInfo.getTempMap().put(StockChangeType.TM_STOCK_ADD.getValue()+tmallSuitCommodity.getCommodityCode(),tmallSuitCommodity.getStockQuantity());
        }
        // 取得该套装其他明细商品的TM单品库存信息
        query = new SimpleQuery(StockServiceQuery.GET_TMALL_SUIT_DETAIL_COMMODITY, tmallSuitCommodity
            .getCommodityCode(), commodityCode);
        tmallStockList = DatabaseUtil.loadAsBeanList(query, TmallStock.class);
        for (TmallStock tmSrock : tmallStockList) {
          tmallSuitInfo.getTmallSuitDetailList().add(tmSrock);
          if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.TM_STOCK_ADD.getValue()+tmSrock.getSkuCode())){
            orgTempInfo.getCommodityCodeList().add(StockChangeType.TM_STOCK_ADD.getValue()+tmSrock.getSkuCode());
            orgTempInfo.getTempMap().put(StockChangeType.TM_STOCK_ADD.getValue()+tmSrock.getSkuCode(),tmSrock.getStockQuantity());
          }
        }
        tmallSuitInfoList.add(tmallSuitInfo);
      }
    }

    // 淘宝组合信息取得
    query = new SimpleQuery(StockServiceQuery.GET_TMALL_STOCK_ALLOCATION, commodityCode);
    List<TmallStockAllocation> tmallStockAllocationList = DatabaseUtil
        .loadAsBeanList(query, TmallStockAllocation.class);

    if (tmallStockAllocationList != null && tmallStockAllocationList.size() > 0) {
      for (TmallStockAllocation tmallStockAllocation : tmallStockAllocationList) {
        TmallCombinInfo tmallCombinInfo = new TmallCombinInfo();
        tmallCombinInfo.setTmallStockAllocation(tmallStockAllocation);
        // 取得该组合商品的组合数
        CCommodityHeaderDao cCommodityHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
        CCommodityHeader cCommodityHeader = cCommodityHeaderDao.load(DIContainer.getWebshopConfig().getSiteShopCode(),
            tmallStockAllocation.getCommodityCode());
        if (cCommodityHeader == null) {
          result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return result;
        }
        tmallCombinInfo.setCombinationAmount(cCommodityHeader.getCombinationAmount());
        if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.TM_STOCK_ADD.getValue()+tmallStockAllocation.getSkuCode())){
          orgTempInfo.getCommodityCodeList().add(StockChangeType.TM_STOCK_ADD.getValue()+tmallStockAllocation.getSkuCode());
          orgTempInfo.getTempMap().put(StockChangeType.TM_STOCK_ADD.getValue()+tmallStockAllocation.getSkuCode(),tmallStockAllocation.getStockQuantity()/cCommodityHeader.getCombinationAmount());
        }
        tmallCombinInfoList.add(tmallCombinInfo);
      }
    }

    // JD单品库存信息取得
    query = new SimpleQuery(StockServiceQuery.GET_JD_STOCK, commodityCode);
    List<JdStock> jdStockList = DatabaseUtil.loadAsBeanList(query, JdStock.class);

    // JD单品库存信息是否存在
    if (jdStockList != null && jdStockList.size() > 0) {
      jdStock = jdStockList.get(0);
      if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.JD_STOCK_ADD.getValue()+jdStock.getSkuCode())){
        orgTempInfo.getCommodityCodeList().add(StockChangeType.JD_STOCK_ADD.getValue()+jdStock.getSkuCode());
        orgTempInfo.getTempMap().put(StockChangeType.JD_STOCK_ADD.getValue()+jdStock.getSkuCode(),jdStock.getStockQuantity());
      }
    }

    // JD套装信息取得
    query = new SimpleQuery(StockServiceQuery.GET_JD_SUIT_COMMODITY, commodityCode);
    List<JdSuitCommodity> jdSuitCommodityList = DatabaseUtil.loadAsBeanList(query, JdSuitCommodity.class);

    if (jdSuitCommodityList != null && jdSuitCommodityList.size() > 0) {
      for (JdSuitCommodity jdSuitCommodity : jdSuitCommodityList) {
        JdSuitInfo jdSuitInfo = new JdSuitInfo();
        jdSuitInfo.setJdSuitCommodity(jdSuitCommodity);
        if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.JD_STOCK_ADD.getValue()+jdSuitCommodity.getCommodityCode())){
          orgTempInfo.getCommodityCodeList().add(StockChangeType.JD_STOCK_ADD.getValue()+jdSuitCommodity.getCommodityCode());
          orgTempInfo.getTempMap().put(StockChangeType.JD_STOCK_ADD.getValue()+jdSuitCommodity.getCommodityCode(),jdSuitCommodity.getStockQuantity());
        }
        // 取得该套装其他明细商品的JD单品库存信息
        query = new SimpleQuery(StockServiceQuery.GET_JD_SUIT_DETAIL_COMMODITY, jdSuitCommodity.getCommodityCode(),
            commodityCode);
        jdStockList = DatabaseUtil.loadAsBeanList(query, JdStock.class);
        for (JdStock jdSrock : jdStockList) {
          jdSuitInfo.getJdSuitDetailList().add(jdSrock);
          if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.JD_STOCK_ADD.getValue()+jdSrock.getSkuCode())){
            orgTempInfo.getCommodityCodeList().add(StockChangeType.JD_STOCK_ADD.getValue()+jdSrock.getSkuCode());
            orgTempInfo.getTempMap().put(StockChangeType.JD_STOCK_ADD.getValue()+jdSrock.getSkuCode(),jdSrock.getStockQuantity());
          }
        }
        jdSuitInfoList.add(jdSuitInfo);
      }
    }

    // JD组合信息取得
    query = new SimpleQuery(StockServiceQuery.GET_JD_STOCK_ALLOCATION, commodityCode);
    List<JdStockAllocation> jdStockAllocationList = DatabaseUtil.loadAsBeanList(query, JdStockAllocation.class);

    if (jdStockAllocationList != null && jdStockAllocationList.size() > 0) {
      for (JdStockAllocation jdStockAllocation : jdStockAllocationList) {
        JdCombinInfo jdCombinInfo = new JdCombinInfo();
        jdCombinInfo.setJdStockAllocation(jdStockAllocation);
        // 取得该组合商品的组合数
        CCommodityHeaderDao cCommodityHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
        CCommodityHeader cCommodityHeader = cCommodityHeaderDao.load(DIContainer.getWebshopConfig().getSiteShopCode(),
            jdStockAllocation.getCommodityCode());
        if (cCommodityHeader == null) {
          result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return result;
        }
        jdCombinInfo.setCombinationAmount(cCommodityHeader.getCombinationAmount());
        if (!orgTempInfo.getCommodityCodeList().contains(StockChangeType.JD_STOCK_ADD.getValue()+jdStockAllocation.getSkuCode())){
          orgTempInfo.getCommodityCodeList().add(StockChangeType.JD_STOCK_ADD.getValue()+jdStockAllocation.getSkuCode());
          orgTempInfo.getTempMap().put(StockChangeType.JD_STOCK_ADD.getValue()+jdStockAllocation.getSkuCode(),jdStockAllocation.getStockQuantity()/cCommodityHeader.getCombinationAmount());
        }
        jdCombinInfoList.add(jdCombinInfo);

      }
    }

    // 库存比例的取得
    query = new SimpleQuery(StockServiceQuery.GET_STOCK_RATIO, commodityCode);
    stockRatioList = DatabaseUtil.loadAsBeanList(query, StockRatio.class);
    // 在库品区分的取得
    query = new SimpleQuery(StockServiceQuery.GET_C_COMMODITY_EXT, commodityCode);
    List<CCommodityExt> cCommodityExtList = DatabaseUtil.loadAsBeanList(query, CCommodityExt.class);
    if (cCommodityExtList != null && cCommodityExtList.size() > 0) {
      onStockFlag = cCommodityExtList.get(0).getOnStockFlag();
    }

    // 在库备注信息编辑
    // 设定在库品区分
    stockMemo = "在库品区分：" + onStockFlag + "；";

    // 设定库存比例
    for (StockRatio stockRatio : stockRatioList) {
      if (RatioType.EC.longValue().equals(stockRatio.getRatioType())) {
        stockMemo = stockMemo + RatioType.EC.getName() + ":" + stockRatio.getStockRatio() + ";";
      } else if (RatioType.JD.longValue().equals(stockRatio.getRatioType())) {
        stockMemo = stockMemo + RatioType.JD.getName() + ":" + stockRatio.getStockRatio() + ";";
      } else if (RatioType.TMALL.longValue().equals(stockRatio.getRatioType())) {
        stockMemo = stockMemo + RatioType.TMALL.getName() + ":" + stockRatio.getStockRatio() + ";";
      }
    }

    // EC在库信息编辑
    stockMemo = stockMemo + "EC库存：" + ecStock.getStockQuantity() + "；EC引当：" + ecStock.getAllocatedQuantity() + "；安全库存："
        + ecStock.getStockThreshold() + "；";

    // TM单品库存信息编辑
    if (tmAllStock != null) {
      stockMemo = stockMemo + "TM单品库存：" + tmAllStock.getStockQuantity() + "；TM单品引当："
          + tmAllStock.getAllocatedQuantity() + "；";
    }

    // TM套装库存信息编辑
    if (tmallSuitInfoList != null && tmallSuitInfoList.size() > 0) {
      for (TmallSuitInfo tmallSuitInfo : tmallSuitInfoList) {
        stockMemo = stockMemo + "TM套装" + tmallSuitInfo.getTmallSuitCommodity().getCommodityCode() + "{库存："
            + tmallSuitInfo.getTmallSuitCommodity().getStockQuantity() + "；引当："
            + tmallSuitInfo.getTmallSuitCommodity().getAllocatedQuantity() + "；分配比例："
            + tmallSuitInfo.getTmallSuitCommodity().getScaleValue() + "；}";
        for (TmallStock tmallStock : tmallSuitInfo.getTmallSuitDetailList()) {
          stockMemo = stockMemo + "TM单品" + tmallStock.getCommodityCode() + "{库存：" + tmallStock.getStockQuantity()
              + "；引当：" + tmallStock.getAllocatedQuantity() + "；}";
        }
      }
    }

    // TM组合库存信息编辑
    if (tmallCombinInfoList != null && tmallCombinInfoList.size() > 0) {
      for (TmallCombinInfo tmallCombinInfo : tmallCombinInfoList) {
        stockMemo = stockMemo + "TM组合" + tmallCombinInfo.getTmallStockAllocation().getCommodityCode() + "{库存："
            + tmallCombinInfo.getTmallStockAllocation().getStockQuantity() + "；引当："
            + tmallCombinInfo.getTmallStockAllocation().getAllocatedQuantity() + "；分配比例："
            + tmallCombinInfo.getTmallStockAllocation().getScaleValue() + "}";
      }
    }

    // JD单品库存信息编辑
    if (jdStock != null) {
      stockMemo = stockMemo + "JD单品库存：" + jdStock.getStockQuantity() + "；JD单品引当：" + jdStock.getAllocatedQuantity()
          + "；";
    }

    // JD套装库存信息编辑
    if (jdSuitInfoList != null && jdSuitInfoList.size() > 0) {
      for (JdSuitInfo jdSuitInfo : jdSuitInfoList) {
        stockMemo = stockMemo + "JD套装" + jdSuitInfo.getJdSuitCommodity().getCommodityCode() + "{库存："
            + jdSuitInfo.getJdSuitCommodity().getStockQuantity() + "；引当："
            + jdSuitInfo.getJdSuitCommodity().getAllocatedQuantity() + "；分配比例："
            + jdSuitInfo.getJdSuitCommodity().getScaleValue() + "；}";
        for (JdStock jDStock : jdSuitInfo.getJdSuitDetailList()) {
          stockMemo = stockMemo + "JD单品" + jDStock.getCommodityCode() + "{库存：" + jDStock.getStockQuantity() + "；引当："
              + jDStock.getAllocatedQuantity() + "；}";
        }
      }
    }

    // JD组合库存信息编辑
    if (jdCombinInfoList != null && jdCombinInfoList.size() > 0) {
      for (JdCombinInfo jdCombinInfo : jdCombinInfoList) {
        stockMemo = stockMemo + "JD组合" + jdCombinInfo.getJdStockAllocation().getCommodityCode() + "{库存："
            + jdCombinInfo.getJdStockAllocation().getStockQuantity() + "；引当："
            + jdCombinInfo.getJdStockAllocation().getAllocatedQuantity() + "；分配比例："
            + jdCombinInfo.getJdStockAllocation().getScaleValue() + "}";
      }
    }

    // 库存履历登录处理
    StockHistory stockHistory = new StockHistory();
    stockHistory.setStockHistoryId(DatabaseUtil.generateSequence(SequenceType.STOCK_HISTORY_ID));
    stockHistory.setShopCode(DIContainer.getWebshopConfig().getSiteShopCode());
    stockHistory.setCommodityCode(commodityCode);
    stockHistory.setSkuCode(commodityCode);
    if(stockMemo != null && stockMemo.length() > 1000){
      stockMemo = stockMemo.substring(0,1000);
    }
    stockHistory.setStockMemo(stockMemo);
    setUserStatus(stockHistory);

    transactionManager.insert(stockHistory);

    return result;
  }

  public ServiceResult stockRecalculation(TransactionManager transactionManager, String commodityCode,
      List<String> tmapiErrCommodityCode, List<String> jdapiErrCommodityCode, List<String> errorStockCommodityCode,
      StockTempInfo stockTempInfo) {
    return stockRecalculation(transactionManager,commodityCode,tmapiErrCommodityCode,jdapiErrCommodityCode,errorStockCommodityCode,
        stockTempInfo,false);
  }
  public ServiceResult stockRecalculation(TransactionManager transactionManager, String commodityCode,
      List<String> tmapiErrCommodityCode, List<String> jdapiErrCommodityCode, List<String> errorStockCommodityCode,
      StockTempInfo stockTempInfo,boolean batchFlg) {

    ServiceResultImpl result = new ServiceResultImpl();

    //原库存信息
    StockTempInfo orgTempInfo = new StockTempInfo();
    // 库存履历生成处理
    ServiceResult historyResult = setStockHistory(transactionManager, commodityCode, orgTempInfo);
    if (historyResult.hasError()) {
      return historyResult;
    }

    // EC库存信息
    Stock ecStock = null;

    // TM单品库存信息
    TmallStock tmAllStock = null;

    // TM组合库存信息
    List<TmallCombinInfo> tmallCombinInfoList = new ArrayList<TmallCombinInfo>();

    // TM套装库存信息
    List<TmallSuitInfo> tmallSuitInfoList = new ArrayList<TmallSuitInfo>();

    // JD单品库存信息
    JdStock jdStock = null;

    // JD组合库存信息
    List<JdCombinInfo> jdCombinInfoList = new ArrayList<JdCombinInfo>();

    // JD套装库存信息
    List<JdSuitInfo> jdSuitInfoList = new ArrayList<JdSuitInfo>();

    // 在库品区分
    Long onStockFlag = 2L;

    // 有效库存
    Long effectiveStock = 0L;

    // TM API有效库存Map
    Map<String, Long> tmApiStockMap = new HashMap<String, Long>();

    // Jd API有效库存Map
    Map<String, Long> jdApiStockMap = new HashMap<String, Long>();

    // EC库存信息取得
    Query query = new SimpleQuery(StockServiceQuery.GET_STOCK, commodityCode);
    Connection connection = ((TransactionManagerImpl) transactionManager).getConnection();
    List<Stock> stockList = DatabaseUtil.loadAsBeanList(connection, query, Stock.class);

    // EC库存信息是否存在
    if (stockList != null && stockList.size() > 0) {
      ecStock = stockList.get(0);
    } else {
      result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      return result;
    }

    // TM单品库存信息取得
    query = new SimpleQuery(StockServiceQuery.GET_TMALL_STOCK, commodityCode);
    List<TmallStock> tmallStockList = DatabaseUtil.loadAsBeanList(connection, query, TmallStock.class);

    // TM单品库存信息是否存在
    if (tmallStockList != null && tmallStockList.size() > 0) {
      tmAllStock = tmallStockList.get(0);
      result = (ServiceResultImpl) getTmApiStock(commodityCode, tmApiStockMap, tmapiErrCommodityCode);
      if (result.hasError()) {
        return result;
      }
    }

    // 淘宝套装信息取得
    query = new SimpleQuery(StockServiceQuery.GET_TMALL_SUIT_COMMODITY, commodityCode);
    List<TmallSuitCommodity> tmallSuitCommodityList = DatabaseUtil.loadAsBeanList(connection, query,
        TmallSuitCommodity.class);

    if (tmallSuitCommodityList != null && tmallSuitCommodityList.size() > 0) {
      for (TmallSuitCommodity tmallSuitCommodity : tmallSuitCommodityList) {
        TmallSuitInfo tmallSuitInfo = new TmallSuitInfo();
        tmallSuitInfo.setTmallSuitCommodity(tmallSuitCommodity);

        // 取得该套装其他明细商品的TM单品库存信息
        query = new SimpleQuery(StockServiceQuery.GET_TMALL_SUIT_DETAIL_COMMODITY, tmallSuitCommodity
            .getCommodityCode(), commodityCode);
        tmallStockList = DatabaseUtil.loadAsBeanList(connection, query, TmallStock.class);
        
        tmallSuitInfo.setTmallSuitDetailList(tmallStockList);
        
        // 取得套装商品的TM API有效库存
        result = (ServiceResultImpl) getTmApiStock(tmallSuitCommodity.getCommodityCode(), tmApiStockMap, tmapiErrCommodityCode);
        if (result.hasError()) {
          return result;
        }
        
        // 取得套装商品其他明细商品的 TM API有效库存
        for (TmallStock tmSrock : tmallStockList) {
          result = (ServiceResultImpl) getTmApiStock(tmSrock.getCommodityCode(), tmApiStockMap, tmapiErrCommodityCode);
          if (result.hasError()) {
            return result;
          }
        }

        tmallSuitInfoList.add(tmallSuitInfo);
      }
    }

    // 淘宝组合信息取得
    query = new SimpleQuery(StockServiceQuery.GET_TMALL_STOCK_ALLOCATION, commodityCode);
    List<TmallStockAllocation> tmallStockAllocationList = DatabaseUtil.loadAsBeanList(connection, query,
        TmallStockAllocation.class);

    if (tmallStockAllocationList != null && tmallStockAllocationList.size() > 0) {
      for (TmallStockAllocation tmallStockAllocation : tmallStockAllocationList) {
        TmallCombinInfo tmallCombinInfo = new TmallCombinInfo();
        tmallCombinInfo.setTmallStockAllocation(tmallStockAllocation);
        // 取得该组合商品的组合数
        CCommodityHeaderDao cCommodityHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
        CCommodityHeader cCommodityHeader = cCommodityHeaderDao.load(DIContainer.getWebshopConfig().getSiteShopCode(),
            tmallStockAllocation.getCommodityCode());
        if (cCommodityHeader == null) {
          result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return result;
        }
        tmallCombinInfo.setCombinationAmount(cCommodityHeader.getCombinationAmount());
        tmallCombinInfoList.add(tmallCombinInfo);

        // 取得组合商品的TM API有效库存
        result = (ServiceResultImpl) getTmApiStock(tmallStockAllocation.getCommodityCode(), tmApiStockMap, tmapiErrCommodityCode);
        if (result.hasError()) {
          return result;
        }
      }
    }

    // JD单品库存信息取得
    query = new SimpleQuery(StockServiceQuery.GET_JD_STOCK, commodityCode);
    List<JdStock> jdStockList = DatabaseUtil.loadAsBeanList(connection, query, JdStock.class);

    // JD单品库存信息是否存在
    if (jdStockList != null && jdStockList.size() > 0) {
      jdStock = jdStockList.get(0);
      result = (ServiceResultImpl) getJdApiStock(commodityCode, jdApiStockMap, jdapiErrCommodityCode);
      if (result.hasError()) {
        return result;
      }
    }
    // JD套装信息取得
    query = new SimpleQuery(StockServiceQuery.GET_JD_SUIT_COMMODITY, commodityCode);
    List<JdSuitCommodity> jdSuitCommodityList = DatabaseUtil.loadAsBeanList(connection, query, JdSuitCommodity.class);

    if (jdSuitCommodityList != null && jdSuitCommodityList.size() > 0) {
      for (JdSuitCommodity jdSuitCommodity : jdSuitCommodityList) {
        JdSuitInfo jdSuitInfo = new JdSuitInfo();
        jdSuitInfo.setJdSuitCommodity(jdSuitCommodity);

        // 取得套装商品的JD API有效库存
        result = (ServiceResultImpl) getJdApiStock(jdSuitCommodity.getCommodityCode(), jdApiStockMap, jdapiErrCommodityCode);
        if (result.hasError()) {
          return result;
        }

        // 取得该套装其他明细商品的JD单品库存信息
        query = new SimpleQuery(StockServiceQuery.GET_JD_SUIT_DETAIL_COMMODITY, jdSuitCommodity.getCommodityCode(),
            commodityCode);
        jdStockList = DatabaseUtil.loadAsBeanList(connection, query, JdStock.class);
        jdSuitInfo.setJdSuitDetailList(jdStockList);
        
        for (JdStock jdSrock : jdStockList) {
          // 取得套装商品其他明细商品的 JD API有效库存
          result = (ServiceResultImpl) getJdApiStock(jdSrock.getCommodityCode(), jdApiStockMap, jdapiErrCommodityCode);
          if (result.hasError()) {
            return result;
          }
        }
        jdSuitInfoList.add(jdSuitInfo);
      }
    }

    // JD组合信息取得
    query = new SimpleQuery(StockServiceQuery.GET_JD_STOCK_ALLOCATION, commodityCode);
    List<JdStockAllocation> jdStockAllocationList = DatabaseUtil.loadAsBeanList(connection, query,
        JdStockAllocation.class);

    if (jdStockAllocationList != null && jdStockAllocationList.size() > 0) {
      for (JdStockAllocation jdStockAllocation : jdStockAllocationList) {
        JdCombinInfo jdCombinInfo = new JdCombinInfo();
        jdCombinInfo.setJdStockAllocation(jdStockAllocation);
        // 取得该组合商品的组合数
        CCommodityHeaderDao cCommodityHeaderDao = DIContainer.getDao(CCommodityHeaderDao.class);
        CCommodityHeader cCommodityHeader = cCommodityHeaderDao.load(DIContainer.getWebshopConfig().getSiteShopCode(),
            jdStockAllocation.getCommodityCode());
        if (cCommodityHeader == null) {
          result.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return result;
        }
        jdCombinInfo.setCombinationAmount(cCommodityHeader.getCombinationAmount());
        jdCombinInfoList.add(jdCombinInfo);

        // 取得组合商品的JD API有效库存
        result = (ServiceResultImpl) getJdApiStock(jdStockAllocation.getCommodityCode(), jdApiStockMap, jdapiErrCommodityCode);
        if (result.hasError()) {
          return result;
        }
      }
    }

    // 有效库存合计以及各库存初期化
    // EC有效库存
    effectiveStock = ecStock.getStockQuantity() - ecStock.getAllocatedQuantity() - ecStock.getStockThreshold();
    ecStock.setStockQuantity(ecStock.getAllocatedQuantity());
    
    // TM单品有效库存
    if (tmAllStock != null) {
      // 已连协
      if (tmApiStockMap.get(commodityCode) != null) {
        effectiveStock = effectiveStock + tmApiStockMap.get(commodityCode);
        tmAllStock.setStockQuantity(tmAllStock.getStockQuantity() - tmApiStockMap.get(commodityCode));
      }
      // 未连协
      else {
        effectiveStock = effectiveStock + tmAllStock.getStockQuantity() - tmAllStock.getAllocatedQuantity();
        tmAllStock.setStockQuantity(tmAllStock.getAllocatedQuantity());
      }
    }

    // TM套餐有效庫存
    for (TmallSuitInfo tmallSuitInfo : tmallSuitInfoList) {
      // 已连协
      if (tmApiStockMap.get(tmallSuitInfo.getTmallSuitCommodity().getCommodityCode()) != null) {
        effectiveStock = effectiveStock + tmApiStockMap.get(tmallSuitInfo.getTmallSuitCommodity().getCommodityCode());
        tmallSuitInfo.setReturnAmount(tmApiStockMap.get(tmallSuitInfo.getTmallSuitCommodity().getCommodityCode()));
        tmallSuitInfo.getTmallSuitCommodity().setStockQuantity(
            tmallSuitInfo.getTmallSuitCommodity().getStockQuantity()
                - tmApiStockMap.get(tmallSuitInfo.getTmallSuitCommodity().getCommodityCode()));
      }
      // 未连协
      else {
        effectiveStock = effectiveStock + tmallSuitInfo.getTmallSuitCommodity().getStockQuantity()
            - tmallSuitInfo.getTmallSuitCommodity().getAllocatedQuantity();
        tmallSuitInfo.setReturnAmount(tmallSuitInfo.getTmallSuitCommodity().getStockQuantity()
            - tmallSuitInfo.getTmallSuitCommodity().getAllocatedQuantity());
        tmallSuitInfo.getTmallSuitCommodity().setStockQuantity(
            tmallSuitInfo.getTmallSuitCommodity().getAllocatedQuantity());
      }
    }

    // TM组合商品有效庫存
    for (TmallCombinInfo tmallCombinInfo : tmallCombinInfoList) {
      // 已连协
      if (tmApiStockMap.get(tmallCombinInfo.getTmallStockAllocation().getCommodityCode()) != null) {
        effectiveStock = effectiveStock
            + tmApiStockMap.get(tmallCombinInfo.getTmallStockAllocation().getCommodityCode())
            * tmallCombinInfo.getCombinationAmount();
        tmallCombinInfo.getTmallStockAllocation().setStockQuantity(
            tmallCombinInfo.getTmallStockAllocation().getStockQuantity()
                - tmApiStockMap.get(tmallCombinInfo.getTmallStockAllocation().getCommodityCode())
                * tmallCombinInfo.getCombinationAmount());
      }
      // 未连协
      else {
        effectiveStock = effectiveStock + tmallCombinInfo.getTmallStockAllocation().getStockQuantity()
            - tmallCombinInfo.getTmallStockAllocation().getAllocatedQuantity();
        tmallCombinInfo.getTmallStockAllocation().setStockQuantity(
            tmallCombinInfo.getTmallStockAllocation().getAllocatedQuantity());
      }
    }

    // JD单品有效库存
    if (jdStock != null) {
      // 已连协
      if (jdApiStockMap.get(commodityCode) != null) {
        effectiveStock = effectiveStock + jdApiStockMap.get(commodityCode);
        jdStock.setStockQuantity(jdStock.getStockQuantity() - jdApiStockMap.get(commodityCode));
      }
      // 未连协
      else {
        effectiveStock = effectiveStock + jdStock.getStockQuantity() - jdStock.getAllocatedQuantity();
        jdStock.setStockQuantity(jdStock.getAllocatedQuantity());
      }
    }

    // JD套餐有效庫存
    for (JdSuitInfo jdSuitInfo : jdSuitInfoList) {
      // 已连协
      if (jdApiStockMap.get(jdSuitInfo.getJdSuitCommodity().getCommodityCode()) != null) {
        effectiveStock = effectiveStock + jdApiStockMap.get(jdSuitInfo.getJdSuitCommodity().getCommodityCode());
        jdSuitInfo.setReturnAmount(jdApiStockMap.get(jdSuitInfo.getJdSuitCommodity().getCommodityCode()));
        jdSuitInfo.getJdSuitCommodity().setStockQuantity(
            jdSuitInfo.getJdSuitCommodity().getStockQuantity()
                - jdApiStockMap.get(jdSuitInfo.getJdSuitCommodity().getCommodityCode()));
      }
      // 未连协
      else {
        effectiveStock = effectiveStock + jdSuitInfo.getJdSuitCommodity().getStockQuantity()
            - jdSuitInfo.getJdSuitCommodity().getAllocatedQuantity();
        jdSuitInfo.setReturnAmount(jdSuitInfo.getJdSuitCommodity().getStockQuantity()
            - jdSuitInfo.getJdSuitCommodity().getAllocatedQuantity());
        jdSuitInfo.getJdSuitCommodity().setStockQuantity(jdSuitInfo.getJdSuitCommodity().getAllocatedQuantity());
      }
    }

    // JD组合商品有效庫存
    for (JdCombinInfo jdCombinInfo : jdCombinInfoList) {
      // 已连协
      if (jdApiStockMap.get(jdCombinInfo.getJdStockAllocation().getCommodityCode()) != null) {
        effectiveStock = effectiveStock + jdApiStockMap.get(jdCombinInfo.getJdStockAllocation().getCommodityCode())
            * jdCombinInfo.getCombinationAmount();
        jdCombinInfo.getJdStockAllocation().setStockQuantity(
            jdCombinInfo.getJdStockAllocation().getStockQuantity()
                - jdApiStockMap.get(jdCombinInfo.getJdStockAllocation().getCommodityCode())
                * jdCombinInfo.getCombinationAmount());
      }
      // 未连协
      else {
        effectiveStock = effectiveStock + jdCombinInfo.getJdStockAllocation().getStockQuantity()
            - jdCombinInfo.getJdStockAllocation().getAllocatedQuantity();
        jdCombinInfo.getJdStockAllocation().setStockQuantity(jdCombinInfo.getJdStockAllocation().getAllocatedQuantity());
      }
    }

    // batch调用时，如果有效库存《0时，只发送邮件，有效库存按照=0进行处理
    if (batchFlg && effectiveStock < 0L) {
      errorStockCommodityCode.add(commodityCode);
      effectiveStock = 0L;
    }
    // 有效库存的合法性验证
    if (effectiveStock < 0L) {
      result.addServiceError(CommonServiceErrorContent.STOCK_ERROR);
      errorStockCommodityCode.add(commodityCode);
      return result;
      
    } else if (effectiveStock > 0L) {
      // 库存比例的取得
      query = new SimpleQuery(StockServiceQuery.GET_STOCK_RATIO, commodityCode);
      List<StockRatio> stockRatioList = DatabaseUtil.loadAsBeanList(connection, query, StockRatio.class);
      
      // 在库品区分的取得
      query = new SimpleQuery(StockServiceQuery.GET_C_COMMODITY_EXT, commodityCode);
      List<CCommodityExt> cCommodityExtList = DatabaseUtil.loadAsBeanList(connection, query, CCommodityExt.class);
      if (cCommodityExtList != null && cCommodityExtList.size() > 0) {
        onStockFlag = cCommodityExtList.get(0).getOnStockFlag();
      }
      
      // 参数初期化
      // TM庫存比例
      Long tmStockRatio = 0L;
      // JD庫存比例
      Long jdStockRatio = 0L;
      if (onStockFlag != 2L) {
        for (StockRatio stockRatio : stockRatioList) {
          if (RatioType.TMALL.longValue().equals(stockRatio.getRatioType()) && stockRatio.getStockRatio() != null) {
            tmStockRatio = stockRatio.getStockRatio();
          } else if (RatioType.JD.longValue().equals(stockRatio.getRatioType()) && stockRatio.getStockRatio() != null) {
            jdStockRatio = stockRatio.getStockRatio();
          }
        }
      }

      // tm庫存增量
      Long tmStockIncremental = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(effectiveStock, tmStockRatio), 100, 0, RoundingMode.DOWN)));
      // JD庫存增量
      Long jdStockIncremental = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(effectiveStock, jdStockRatio), 100, 0, RoundingMode.DOWN)));
      // EC庫存增量
      Long ecStockIncremental = effectiveStock - tmStockIncremental - jdStockIncremental;

      // EC库存更新
      ecStock.setStockQuantity(ecStock.getStockQuantity() + ecStockIncremental);

      // TM库存再分配
      // 组合库存再分配
      for (int i = 0; i < tmallCombinInfoList.size(); i++) {
        // 此組合商品可分配數
        Long countableDistribution = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(tmStockIncremental, tmallCombinInfoList.get(i).getTmallStockAllocation().getScaleValue()), 100, 0, RoundingMode.DOWN)));
        countableDistribution = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(countableDistribution, tmallCombinInfoList.get(i).getCombinationAmount(), 0, RoundingMode.DOWN)));
        
        // tm庫存增量剩余
        tmStockIncremental = tmStockIncremental - NumUtil.toLong(NumUtil.toString(BigDecimalUtil.multiply(countableDistribution, tmallCombinInfoList.get(i).getCombinationAmount())));
        // 組合商品庫存設定
        tmallCombinInfoList.get(i).getTmallStockAllocation().setStockQuantity(
            tmallCombinInfoList.get(i).getTmallStockAllocation().getStockQuantity() 
            + NumUtil.toLong(NumUtil.toString(BigDecimalUtil.multiply(countableDistribution, tmallCombinInfoList.get(i).getCombinationAmount()))));
      }
      
      // 套装库存再分配
      for (int i = 0; i < tmallSuitInfoList.size(); i++) {
        // 此套餐商品可分配數
        Long countableDistribution = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(tmStockIncremental, tmallSuitInfoList.get(i).getTmallSuitCommodity().getScaleValue()), 100, 0, RoundingMode.DOWN)));
        
        for (TmallStock tmallStock : tmallSuitInfoList.get(i).getTmallSuitDetailList()) {
          // 取得此套餐明细可分配数
          Long cd = 0L;
          if (tmApiStockMap.get(tmallStock.getCommodityCode()) != null) {
            cd = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(tmApiStockMap.get(tmallStock.getCommodityCode()) + tmallSuitInfoList.get(i).getReturnAmount(), 
                tmallSuitInfoList.get(i).getTmallSuitCommodity().getScaleValue()), 100, 0, RoundingMode.DOWN)));

          } else {
            Long quantityTemp = tmallStock.getStockQuantity() - tmallStock.getAllocatedQuantity() + tmallSuitInfoList.get(i).getReturnAmount();
            cd = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(quantityTemp, tmallSuitInfoList.get(i).getTmallSuitCommodity().getScaleValue()), 100, 0, RoundingMode.DOWN)));
            
          }
          // 取得此套餐中最小可分配数
          if (cd < countableDistribution) {
            countableDistribution = cd;
          }
        }
        
        // tm庫存增量剩余
        tmStockIncremental = tmStockIncremental - countableDistribution;
        
        // 该套餐中关联明细商品库存设定
        for (int j = 0; j < tmallSuitInfoList.get(i).getTmallSuitDetailList().size(); j++) {
          tmallSuitInfoList.get(i).getTmallSuitDetailList().get(j).setStockQuantity(
              tmallSuitInfoList.get(i).getTmallSuitDetailList().get(j).getStockQuantity()
                  + tmallSuitInfoList.get(i).getReturnAmount() - countableDistribution);
        }
        // 该套餐商品库存设定
        tmallSuitInfoList.get(i).getTmallSuitCommodity().setStockQuantity(
            tmallSuitInfoList.get(i).getTmallSuitCommodity().getStockQuantity() + countableDistribution);
      }
      
      // 单品库存设置
      tmAllStock.setStockQuantity(tmAllStock.getStockQuantity() + tmStockIncremental);

      // JD库存再分配
      // 组合库存再分配
      for (int i = 0; i < jdCombinInfoList.size(); i++) {
        // 此組合商品可分配數
        Long countableDistribution = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(jdStockIncremental, jdCombinInfoList.get(i).getJdStockAllocation().getScaleValue()), 100, 0, RoundingMode.DOWN)));
        countableDistribution = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(countableDistribution, jdCombinInfoList.get(i).getCombinationAmount(), 0, RoundingMode.DOWN)));
        
        // jd庫存增量剩余
        jdStockIncremental = jdStockIncremental - NumUtil.toLong(NumUtil.toString(BigDecimalUtil.multiply(countableDistribution, jdCombinInfoList.get(i).getCombinationAmount())));;
        // 組合商品庫存設定
        jdCombinInfoList.get(i).getJdStockAllocation().setStockQuantity(
            jdCombinInfoList.get(i).getJdStockAllocation().getStockQuantity() + countableDistribution * jdCombinInfoList.get(i).getCombinationAmount());
      }
      
      // 套装库存再分配
      for (int i = 0; i < jdSuitInfoList.size(); i++) {
        // 此套餐商品可分配數
        Long countableDistribution = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(jdStockIncremental, jdSuitInfoList.get(i).getJdSuitCommodity().getScaleValue()), 100, 0, RoundingMode.DOWN)));
        for (JdStock jDStock : jdSuitInfoList.get(i).getJdSuitDetailList()) {
          // 取得此套餐明细可分配数
          Long cd = 0L;
     
          if (jdApiStockMap.get(jDStock.getCommodityCode()) != null) {
            cd = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(jdApiStockMap.get(jDStock.getCommodityCode()) + jdSuitInfoList.get(i).getReturnAmount(), 
                jdSuitInfoList.get(i).getJdSuitCommodity().getScaleValue()), 100, 0, RoundingMode.DOWN)));

          } else {
            Long quantityTemp = jDStock.getStockQuantity() - jDStock.getAllocatedQuantity() + jdSuitInfoList.get(i).getReturnAmount();
            cd = NumUtil.toLong(NumUtil.toString(BigDecimalUtil.divide(BigDecimalUtil.multiply(quantityTemp, jdSuitInfoList.get(i).getJdSuitCommodity().getScaleValue()), 100, 0, RoundingMode.DOWN)));
            
          }
          
          // 取得此套餐中最小可分配数
          if (cd < countableDistribution) {
            countableDistribution = cd;
          }
        }
        // JD庫存增量剩余
        jdStockIncremental = jdStockIncremental - countableDistribution;
        // 该套餐中关联明细商品库存设定
        for (int j = 0; j < jdSuitInfoList.get(i).getJdSuitDetailList().size(); j++) {
          jdSuitInfoList.get(i).getJdSuitDetailList().get(j).setStockQuantity(
              jdSuitInfoList.get(i).getJdSuitDetailList().get(j).getStockQuantity()
                  + jdSuitInfoList.get(i).getReturnAmount() - countableDistribution);
        }
        // 该套餐商品库存设定
        jdSuitInfoList.get(i).getJdSuitCommodity().setStockQuantity(
            jdSuitInfoList.get(i).getJdSuitCommodity().getStockQuantity() + countableDistribution);
      }
      // 单品库存设置
      jdStock.setStockQuantity(jdStock.getStockQuantity() + jdStockIncremental);

    } else {
      // 有效库存=0时的处理
      
      // TM套装库存再分配
      for (int i = 0; i < tmallSuitInfoList.size(); i++) {
        for (int j = 0; j < tmallSuitInfoList.get(i).getTmallSuitDetailList().size(); j++) {
          tmallSuitInfoList.get(i).getTmallSuitDetailList().get(j).setStockQuantity(
              tmallSuitInfoList.get(i).getTmallSuitDetailList().get(j).getStockQuantity()
                  + tmallSuitInfoList.get(i).getReturnAmount());
        }
      }
      
      // JD套装库存再分配
      for (int i = 0; i < jdSuitInfoList.size(); i++) {
        for (int j = 0; j < jdSuitInfoList.get(i).getJdSuitDetailList().size(); j++) {
          jdSuitInfoList.get(i).getJdSuitDetailList().get(j).setStockQuantity(
              jdSuitInfoList.get(i).getJdSuitDetailList().get(j).getStockQuantity()
                  + jdSuitInfoList.get(i).getReturnAmount());
        }
      }
    }

    // 库存更新处理
    // EC库存更新
    setUserStatus(ecStock);
    transactionManager.update(ecStock);
    
    // TM库存更新
    setUserStatus(tmAllStock);
    transactionManager.update(tmAllStock);
    //库存增量计算
    setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.TM_STOCK_ADD.getValue()+tmAllStock.getSkuCode(),tmAllStock.getStockQuantity());
    
    // TM组合库存更新
    for (TmallCombinInfo tmallCombinInfo : tmallCombinInfoList) {
      setUserStatus(tmallCombinInfo.getTmallStockAllocation());
      transactionManager.update(tmallCombinInfo.getTmallStockAllocation());
      //库存增量计算
      setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.TM_STOCK_ADD.getValue()+tmallCombinInfo.getTmallStockAllocation().getSkuCode(),
          tmallCombinInfo.getTmallStockAllocation().getStockQuantity()/tmallCombinInfo.getCombinationAmount());
    }
    
    // TM套装库存更新
    for (TmallSuitInfo tmallSuitInfo : tmallSuitInfoList) {
      setUserStatus(tmallSuitInfo.getTmallSuitCommodity());
      transactionManager.update(tmallSuitInfo.getTmallSuitCommodity());
      //库存增量计算
      setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.TM_STOCK_ADD.getValue()+tmallSuitInfo.getTmallSuitCommodity().getCommodityCode(),
          tmallSuitInfo.getTmallSuitCommodity().getStockQuantity());
      for (TmallStock tmallStock : tmallSuitInfo.getTmallSuitDetailList()) {
        setUserStatus(tmallStock);
        transactionManager.update(tmallStock);
        //库存增量计算
        setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.TM_STOCK_ADD.getValue()+tmallStock.getCommodityCode(),
            tmallStock.getStockQuantity());
      }
    }

    // JD库存更新
    setUserStatus(jdStock);
    transactionManager.update(jdStock);
    //库存增量计算
    setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.JD_STOCK_ADD.getValue()+jdStock.getSkuCode(),jdStock.getStockQuantity());
    
    // JD组合库存更新
    for (JdCombinInfo jdCombinInfo : jdCombinInfoList) {
      setUserStatus(jdCombinInfo.getJdStockAllocation());
      transactionManager.update(jdCombinInfo.getJdStockAllocation());
      //库存增量计算
      setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.JD_STOCK_ADD.getValue()+jdCombinInfo.getJdStockAllocation().getSkuCode(),
          jdCombinInfo.getJdStockAllocation().getStockQuantity()/jdCombinInfo.getCombinationAmount());
    }
    
    // JD套装库存更新
    for (JdSuitInfo jdSuitInfo : jdSuitInfoList) {
      setUserStatus(jdSuitInfo.getJdSuitCommodity());
      transactionManager.update(jdSuitInfo.getJdSuitCommodity());
      //库存增量计算
      setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.JD_STOCK_ADD.getValue()+jdSuitInfo.getJdSuitCommodity().getCommodityCode(),
          jdSuitInfo.getJdSuitCommodity().getStockQuantity());
      for (JdStock jDStock : jdSuitInfo.getJdSuitDetailList()) {
        setUserStatus(jDStock);
        transactionManager.update(jDStock);
      //库存增量计算
        setStockTempInfo(stockTempInfo,orgTempInfo,StockChangeType.JD_STOCK_ADD.getValue()+jDStock.getCommodityCode(),
            jDStock.getStockQuantity());
      }
    }
    
    // API同步Flg更新
   /* transactionManager.executeUpdate(StockServiceQuery.UPDATE_FLG_QUERY, SyncFlagJd.SYNCVISIBLE.longValue(), this
        .getLoginInfo().getRecordingFormat(), ecStock.getUpdatedDatetime(), commodityCode, commodityCode);*/

    return result;
  }
  /**
   * 库存增量计算
   * @param stockTempInfo
   * @param orgTempInfo
   * @param commodityCode
   * @param stockQuantity
   */
  private void setStockTempInfo(StockTempInfo stockTempInfo,StockTempInfo orgTempInfo,String commodityCode,Long stockQuantity) {
    Long orgStockQuantity = 0L;
    if (orgTempInfo.getCommodityCodeList().contains(commodityCode)) {
      orgStockQuantity = orgTempInfo.getTempMap().get(commodityCode);
    }
    if (!stockTempInfo.getCommodityCodeList().contains(commodityCode)) {
      stockTempInfo.getCommodityCodeList().add(commodityCode);
    }
    stockTempInfo.getTempMap().put(commodityCode, stockQuantity-orgStockQuantity);
  }
  /**
   * 
   */
  public void updateStockTemp(TransactionManager transactionManager, StockTempInfo stockTempInfo) {
    Connection connection = ((TransactionManagerImpl) transactionManager).getConnection();
    if (stockTempInfo != null && stockTempInfo.getCommodityCodeList() != null && stockTempInfo.getCommodityCodeList().size() > 0) {
      Collections.sort(stockTempInfo.getCommodityCodeList());
      for (String commodityCode : stockTempInfo.getCommodityCodeList()) {
        Long stockTempQuantity = stockTempInfo.getTempMap().get(commodityCode);
        if (stockTempQuantity != null && !stockTempQuantity.equals(0L)) {
          Query query = new SimpleQuery(StockServiceQuery.GET_STOCK_TEMP_BY_CODE, DIContainer.getWebshopConfig()
              .getSiteShopCode(), commodityCode.substring(1), commodityCode.substring(0, 1));
          List<StockTemp> list = DatabaseUtil.loadAsBeanList(connection, query, StockTemp.class);
          if (list != null && list.size() > 0) {
            StockTemp stockTemp = list.get(0);
            stockTemp.setStockChangeQuantity(stockTemp.getStockChangeQuantity() + stockTempQuantity);
            setUserStatus(stockTemp);
            transactionManager.update(stockTemp);
          } else {
            StockTemp stockTemp = new StockTemp();
            stockTemp.setShopCode(DIContainer.getWebshopConfig().getSiteShopCode());
            stockTemp.setSkuCode(commodityCode.substring(1));
            stockTemp.setCommodityCode(commodityCode.substring(1));
            stockTemp.setStockChangeType(NumUtil.toLong(commodityCode.substring(0, 1)));
            stockTemp.setStockChangeQuantity(stockTempQuantity);
            setUserStatus(stockTemp);
            transactionManager.insert(stockTemp);
          }
        }
      }
    }
  }
  
  /**
   * TM同步处理
   * @param tmallCatalogId
   * @param tmallEffectiveStock
   * @param tmallSkuCode
   * @return
   */
  public boolean tmallStockCyncroApi(Long tmallCatalogId, Long tmallEffectiveStock, String tmallSkuCode) {
    Logger logger = Logger.getLogger(this.getClass());
    
    if (tmallCatalogId == null) {
      return true;
    }
    
    TmallManager manager = new TmallManager();
    TmallCommoditySku sku = new TmallCommoditySku();
    // sku所属商品型号ID
    sku.setNumiid(tmallCatalogId.toString());
    // Sku的商家外部id
    sku.setOuterId(tmallSkuCode);
    // 淘宝差分=临时放置在tmall的库存
    sku.setQuantity(NumUtil.toString(tmallEffectiveStock));
    // 更新差分时 1 代表全量更新 2代表增减量更新
    sku.setUpdateType("2");
    // 更新淘宝的库存差分
    if (!StringUtil.isNullOrEmpty(manager.updateSkuStock(sku))) {                                       
      logger.info(tmallSkuCode + "更新淘宝成功。");                                        
      return true;                                        
    } else {                                        
      logger.info(tmallSkuCode + "更新淘宝失败。");                                        
      return false;                                       
    }                                       
  }
  
  public boolean jdStockCyncroApi(Long jdCommodityId, Long jdEffectiveStock) {
    if (jdCommodityId == null) {
      return true;
    }
    // API连协取得API有效库存
    WareGetRequest wareGetRequest = new WareGetRequest();
    wareGetRequest.setWareId(NumUtil.toString(jdCommodityId));
    wareGetRequest.setFields("");
    JdApiProviderManager jdApi = new JdApiProviderManager(wareGetRequest);
    JdApiResult jdResult = jdApi.execute();
    WareGetResponse response = (WareGetResponse) jdResult.getResultBean();
    if (jdResult.hasError()) {
      return false;
    } 
    jdEffectiveStock = jdEffectiveStock + NumUtil.toLong(String.valueOf(response.getWare().getStockNum()));
    WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
    wareUpdateRequest.setWareId(jdCommodityId.toString());
    wareUpdateRequest.setStockNum(NumUtil.toString(jdEffectiveStock));
    jdApi = new JdApiProviderManager(wareUpdateRequest);
    JdApiResult jdApiResult = jdApi.execute();
    if (jdApiResult.hasError()) {
      return false;
    }
    return true;
  }
  
  
  
  public boolean jdSkuStockCyncroApi(String jdSkuId, Long jdEffectiveStock) {
    if (jdSkuId == null) {
      return true;
    }
    // API连协取得API有效库存
    SkuCustomGetRequest  wareGetRequest = new SkuCustomGetRequest ();
    wareGetRequest.setOuterId(jdSkuId);
    wareGetRequest.setFields("");
    JdApiProviderManager jdApi = new JdApiProviderManager(wareGetRequest);
    JdApiResult jdResult = jdApi.execute();
    SkuCustomGetResponse response = (SkuCustomGetResponse) jdResult.getResultBean();
    if (jdResult.hasError()) {
      return false;
    } 
    jdEffectiveStock = jdEffectiveStock + NumUtil.toLong(String.valueOf(response.getSku().getStockNum()));
    WareSkuStockUpdateRequest wareUpdateRequest = new WareSkuStockUpdateRequest();
    wareUpdateRequest.setOuterId(jdSkuId);
    wareUpdateRequest.setQuantity(NumUtil.toString(jdEffectiveStock));
    jdApi = new JdApiProviderManager(wareUpdateRequest);
    JdApiResult jdApiResult = jdApi.execute();
    if (jdApiResult.hasError()) {
      return false;
    }
    return true;
  }
  
  
  private boolean checkTmallStock(CCommodityHeader commodity) {
    Logger logger = Logger.getLogger(this.getClass());
        Long stockNum = 0L;
        if (commodity.getSetCommodityFlg() != null && SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
          //套装商品
          Query query = new SimpleQuery("SELECT * FROM TMALL_SUIT_COMMODITY WHERE COMMODITY_CODE = ? ",commodity.getCommodityCode());
          TmallSuitCommodity stock = DatabaseUtil.loadAsBean(query, TmallSuitCommodity.class);
          if (stock!=null) {
            //有效庫存
            stockNum = stock.getStockQuantity()-stock.getAllocatedQuantity();
          } else {
            return true;
          }
        } else if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
          //组合商品
          Query query = new SimpleQuery("SELECT * FROM TMALL_STOCK_ALLOCATION WHERE COMMODITY_CODE = ? ",commodity.getCommodityCode());
          TmallStockAllocation stock = DatabaseUtil.loadAsBean(query, TmallStockAllocation.class);
          if (stock!=null && commodity.getCombinationAmount()!=null && commodity.getCombinationAmount()>0L) {
            //有效庫存
            stockNum = (stock.getStockQuantity()-stock.getAllocatedQuantity())/commodity.getCombinationAmount();
          } else {
            return true;
          }
        } else {
          //单品
          Query query = new SimpleQuery("SELECT * FROM TMALL_STOCK WHERE COMMODITY_CODE = ? ",commodity.getCommodityCode());
          TmallStock stock = DatabaseUtil.loadAsBean(query, TmallStock.class);
          if (stock!=null) {
            //有效庫存
            stockNum = stock.getStockQuantity()-stock.getAllocatedQuantity();
          } else {
            return true;
          }
        }
        // API连协取得API有效库存
        TmallManager manager = new TmallManager();
        TmallCommodityHeader tmallCommodity = manager.searchCommodity(NumUtil.toString(commodity.getTmallCommodityId()));
        if (tmallCommodity == null || StringUtil.isNullOrEmpty(tmallCommodity.getNum())) {
          logger.error("商品【" + commodity.getCommodityCode() + "】API连携失败了。");
          //return true;
        } else {
          if (!stockNum.toString().equals(tmallCommodity.getNum())) {
            logger.error("商品【" + commodity.getCommodityCode() + "】EC侧的有效库存和TMALL有效库存不一致。");
            return false;
          }
      }
        return true;
  }
}