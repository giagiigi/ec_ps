package jp.co.sint.webshop.service.tmall;

import java.util.List;

import jp.co.sint.webshop.utility.DIContainer;

public class TmallManager {

  private TmallCommodityDelistingService tmallCommodityDelistingService;

  private TmallInsertOrUpdateCommodityHeaderService tmallInsertOrUpdateCommodityHeaderService;

  private TmallInsertOrUpdateCommoditySkuService tmallInsertOrUpdateCommoditySkuService;

  private TmallSearchCommodityInfoService tmallSearchCommodityInfoService;

  private TmallSearchOrderInfoListService tmallSearchOrderInfoListService;

  private TmallSearchRefundInfoService tmallSearchRefundInfoService;

  private TmallShippingDeliverySendService tmallShippingDeliverySendService;

  private TmallCodShippingDeliverySendService tmallCodShippingDeliverySendService;

  private TmallUpdateShippingAddressInfoService tmallUpdateShippingAddressInfoService;

  private TmallGetAreasService tmallGetAreasService;

  private TmallGetSysDateTimeService tmallGetSysDateTimeService;

  private TmallInsertCategoryListService tmallInsertCategoryListService;

  private TmallInsertProAndValueListService tmallInsertProAndValueListService;

  private TmallUpdateSkuStockService tmallUpdateSkuStockService;

  private TmallUploadImgService tmallUploadImgService;
  
  private TmallUploadDescribeImgService tmallUploadDescribeImgService;

  private TmallGetSkuService tmallGetSkuService;

  private TmallDelSkuService tmallDelSkuService;

  private TmallGetProductService tmallGetProductService;

  private TmallInsertProductService tmallInsertProductService;
  
  private TmallSearchOrderDownloadNumService tmallSearchOrderDownloadNumService;
  
  private TmallTradeGetService tmallTradeGetService;

  public TmallManager() {
  }

  // 商品下架
  public String commodityDelisting(String numiid) {
    if (tmallCommodityDelistingService == null) {
      tmallCommodityDelistingService = DIContainer.get("TmallCommodityDelisting");
    }
    String result = tmallCommodityDelistingService.commodityDelisting(numiid);
    return result;
  }

  // 商品添加和更新 type:INSERT or UPDATE
  public String insertOrUpdateCommodityHeader(TmallCommodityHeader tch, String type) {
    if (tmallInsertOrUpdateCommodityHeaderService == null) {
      tmallInsertOrUpdateCommodityHeaderService = DIContainer.get("TmallInsertOrUpdateCommodityHeader");
    }
    String result = tmallInsertOrUpdateCommodityHeaderService.insertOrUpdateCommodityHeader(tch, type);
    return result;
  }

  // 商品SKU添加和更新 type:INSERT or UPDATE
  public String insertOrUpdateCommoditySku(TmallCommoditySku tcs, String type) {
    if (tmallInsertOrUpdateCommoditySkuService == null) {
      tmallInsertOrUpdateCommoditySkuService = DIContainer.get("TmallInsertOrUpdateCommoditySku");
    }
    String result = tmallInsertOrUpdateCommoditySkuService.insertOrUpdateCommoditySku(tcs, type);
    return result;
  }

  // 查询单个商品
  public TmallCommodityHeader searchCommodity(String numIid) {
    if (tmallSearchCommodityInfoService == null) {
      tmallSearchCommodityInfoService = DIContainer.get("TmallSearchCommodityInfo");
    }
    TmallCommodityHeader tch = tmallSearchCommodityInfoService.searchCommodity(numIid);
    return tch;
  }

  // 查询订单和发货数据封装到List
  public TmallTradeInfoList searchOrderDetailList(String startTime, String endTime) {
    if (tmallSearchOrderInfoListService == null) {
      tmallSearchOrderInfoListService = DIContainer.get("TmallSearchOrderInfoList");
    }
    TmallTradeInfoList ttil = tmallSearchOrderInfoListService.searchOrderDetailList(startTime, endTime);
    return ttil;
  }

  // 订单下载时判断网络连接是否断开
  public String getIsNetError() {
    if (tmallSearchOrderInfoListService == null) {
      tmallSearchOrderInfoListService = DIContainer.get("TmallSearchOrderInfoList");
    }
    String isNetRes = tmallSearchOrderInfoListService.getIsNetError();
    return isNetRes;
  }

  // 查询单笔退货信息
  public String searchRefundPayment(String refundId) {
    if (tmallSearchRefundInfoService == null) {
      tmallSearchRefundInfoService = DIContainer.get("TmallSearchRefundInfo");
    }
    String result = tmallSearchRefundInfoService.searchRefundPayment(refundId);
    return result;
  }

  // Alipay批量发货
  public boolean ShippingDeliverySend(TmallShippingDelivery tsd) {
    if (tmallShippingDeliverySendService == null) {
      tmallShippingDeliverySendService = DIContainer.get("TmallShippingDeliverySend");
    }
    boolean result = tmallShippingDeliverySendService.ShippingDeliverySend(tsd);
    return result;
  }

  // Cod批量发货
  public boolean codShippingDeliverySend(TmallShippingDelivery tsd) {
    if (tmallCodShippingDeliverySendService == null) {
      tmallCodShippingDeliverySendService = DIContainer.get("TmallCodShippingDeliverySend");
    }
    boolean result = tmallCodShippingDeliverySendService.codShippingDeliverySend(tsd);
    return result;
  }

  // 修改订单收货信息
  public String updateShippingAddrInfo(TmallShippingDelivery tsh) {
    if (tmallUpdateShippingAddressInfoService == null) {
      tmallUpdateShippingAddressInfoService = DIContainer.get("TmallUpdateShippingAddressInfo");
    }
    String result = tmallUpdateShippingAddressInfoService.updateShippingAddrInfo(tsh);
    return result;
  }

  // 获得淘宝地址薄信息
  public boolean getTmallAreas() {
    if (tmallGetAreasService == null) {
      tmallGetAreasService = DIContainer.get("TmallGetAreas");
    }
    boolean result = tmallGetAreasService.getTmallAreas();
    return result;
  }

  // 获得淘宝系统时间
  public String getSysDateTime() {
    if (tmallGetSysDateTimeService == null) {
      tmallGetSysDateTimeService = DIContainer.get("TmallGetSysDateTime");
    }
    String result = tmallGetSysDateTimeService.getSysDateTime();
    return result;
  }

  // 获得淘宝商家授权的类目同步到EC
  public boolean insertCategoryList() {
    if (tmallInsertCategoryListService == null) {
      tmallInsertCategoryListService = DIContainer.get("TmallGetBrandAndCategoryList");
    }
    boolean result = tmallInsertCategoryListService.insertCategoryList();
    return result;
  }

  // 获得淘宝商家授权的品牌同步到EC
  public boolean insertTmallBrandList() {
    if (tmallInsertCategoryListService == null) {
      tmallInsertCategoryListService = DIContainer.get("TmallGetBrandAndCategoryList");
    }
    boolean result = tmallInsertCategoryListService.insertTmallBrandList();
    return result;
  }

  // 获得淘宝类目关联的属性和值同步到EC
  public boolean insertPropertyAndValue() {
    if (tmallInsertProAndValueListService == null) {
      tmallInsertProAndValueListService = DIContainer.get("TmallGetPropertyAndValueList");
    }
    boolean result = tmallInsertProAndValueListService.insertPropertyAndValue();
    return result;
  }
  
  // 获得子属性模板
  public boolean insertChildProperty() {
    if (tmallInsertProAndValueListService == null) {
      tmallInsertProAndValueListService = DIContainer.get("TmallGetPropertyAndValueList");
    }
    boolean result = tmallInsertProAndValueListService.insertChildProperty(null);
    return result;
  }

  // sku库存更新
  public String updateSkuStock(TmallCommoditySku tcs) {
    if (tmallUpdateSkuStockService == null) {
      tmallUpdateSkuStockService = DIContainer.get("TmallUpdateSkuStock");
    }
    String result = tmallUpdateSkuStockService.updateSkuStock(tcs);
    return result;
  }

  // sku库存更新
  public void updateTmallStockBySku() {
    if (tmallUpdateSkuStockService == null) {
      tmallUpdateSkuStockService = DIContainer.get("TmallUpdateSkuStock");
    }
    tmallUpdateSkuStockService.updateTmallStockBySku();
  }

  // 图片上传到tmall
  public TmallCommodityImg imageUpload(TmallCommodityImg tci) {
    if (tmallUploadImgService == null) {
      tmallUploadImgService = DIContainer.get("TmallCommodityImgUpload");
    }
    TmallCommodityImg result = tmallUploadImgService.imageUpload(tci);
    return result;
  }
  
  // 描述图片上传到tmall
  public TmallCommodityImgHistory imageUpload(TmallCommodityImgHistory tci) {
    if (tmallUploadDescribeImgService == null) {
      tmallUploadDescribeImgService = DIContainer.get("TmallCommodityDescribeImgUpload");
    }
    TmallCommodityImgHistory result = tmallUploadDescribeImgService.imageUpload(tci);
    return result;
  }

  // 获取sku
  public TmallCommoditySku getSku(TmallCommoditySku tcs) {
    if (tmallGetSkuService == null) {
      tmallGetSkuService = DIContainer.get("TmallGetSku");
    }
    TmallCommoditySku result = tmallGetSkuService.getSku(tcs);
    return result;
  }

  // 删除sku
  public String deleteSku(TmallCommoditySku tcs) {
    if (tmallDelSkuService == null) {
      tmallDelSkuService = DIContainer.get("TmallDelSku");
    }
    String result = tmallDelSkuService.deleteSku(tcs);
    return result;
  }

  // 搜索产品
  public String getProduct(TmallCommodityHeader tch) {
    if (tmallGetProductService == null) {
      tmallGetProductService = DIContainer.get("TmallGetProduct");
    }
    String result = tmallGetProductService.getProduct(tch);
    return result;
  }

  // 添加产品
  public String insertProduct(TmallCommodityHeader tch) {
    if (tmallInsertProductService == null) {
      tmallInsertProductService = DIContainer.get("TmallInsertProduct");
    }
    String result = tmallInsertProductService.insertProduct(tch);
    return result;
  }
  
  // 验证一天的订单下载数量
  public   List<String> searchOrderDownLoadNums(String startTime, String endTime) {
    if (tmallSearchOrderDownloadNumService == null) {
      tmallSearchOrderDownloadNumService = DIContainer.get("TmallSearchOrderDownloadNums");
    }
    List<String> result = tmallSearchOrderDownloadNumService.searchOrderDownLoadNums(startTime, endTime);
    return result;
  }
  
  // 查询单笔订单交易状态
  public  String searchTmallTradeGet(String tid) {
    if (tmallTradeGetService == null) {
      tmallTradeGetService = DIContainer.get("TmallTradeGet");
    }
    String result = tmallTradeGetService.searchTmallTradeGet(tid);
    return result;
  }
}
