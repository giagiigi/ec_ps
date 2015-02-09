package jp.co.sint.webshop.service.tmall.impl;

import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.service.impl.AbstractServiceImpl;
import jp.co.sint.webshop.service.order.JdServiceQuery;
import jp.co.sint.webshop.service.order.OrderServiceQuery;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallCommodityImg;
import jp.co.sint.webshop.service.tmall.TmallCommodityImgHistory;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallManager;
import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.service.tmall.TmallShippingDelivery;
import jp.co.sint.webshop.service.tmall.TmallTradeInfoList;

public class TmallServiceImpl extends AbstractServiceImpl implements TmallService {

  private static final long serialVersionUID = 1L;

  private TmallManager tm;

  // Alipay批量发货
  @Override
  public boolean ShippingDeliverySend(TmallShippingDelivery tsd) {
    tm = new TmallManager();
    return tm.ShippingDeliverySend(tsd);
  }

  // 商品下架
  @Override
  public String commodityDelisting(String numiid) {
    tm = new TmallManager();
    return tm.commodityDelisting(numiid);
  }

  // 获得淘宝地址薄信息发
  @Override
  public boolean getTmallAreas() {
    tm = new TmallManager();
    return tm.getTmallAreas();
  }

  // 商品添加和更新 type:INSERT or UPDATE
  @Override
  public String insertOrUpdateCommodityHeader(TmallCommodityHeader tch, String type) {
    tm = new TmallManager();
    return tm.insertOrUpdateCommodityHeader(tch, type);
  }

  // 商品SKU添加和更新 type:INSERT or UPDATE
  @Override
  public String insertOrUpdateCommoditySku(TmallCommoditySku tcs, String type) {
    tm = new TmallManager();
    return tm.insertOrUpdateCommoditySku(tcs, type);
  }

  // 查询单个商品
  @Override
  public TmallCommodityHeader searchCommodity(String numIid) {
    tm = new TmallManager();
    return tm.searchCommodity(numIid);
  }

  // 查询订单和发货数据封装到List
  @Override
  public TmallTradeInfoList searchOrderDetailList(String startTime, String endTime) {
    tm = new TmallManager();
    return tm.searchOrderDetailList(startTime, endTime);
  }

  // 查询单笔退货信息
  @Override
  public String searchRefundPayment(String refundId) {
    tm = new TmallManager();
    return tm.searchRefundPayment(refundId);
  }

  // 修改订单收获地址信息：需要传入淘宝发货信息类
  @Override
  public String updateShippingAddrInfo(TmallShippingDelivery tsh) {
    tm = new TmallManager();
    return tm.updateShippingAddrInfo(tsh);
  }

  // 获取淘宝系统时间
  @Override
  public String getTmallSysDateTime() {
    tm = new TmallManager();
    return tm.getSysDateTime();
  }

  // 获得淘宝商家授权的类目同步到EC
  @Override
  public boolean insertCategoryList() {
    tm = new TmallManager();
    return tm.insertCategoryList();
  }

  // 获得淘宝类目关联的属性和值同步到EC
  @Override
  public boolean insertPropertyAndValue() {
    tm = new TmallManager();
    return tm.insertPropertyAndValue();
  }

  // 获得淘宝商家授权的品牌同步到EC
  @Override
  public boolean insertTmallBrandList() {
    tm = new TmallManager();
    return tm.insertTmallBrandList();
  }

  // sku库存更新
  @Override
  public String updateSkuStock(TmallCommoditySku tcs) {
    tm = new TmallManager();
    return tm.updateSkuStock(tcs);
  }

  // 图片上传到tmall
  @Override
  public TmallCommodityImg imageUpload(TmallCommodityImg tci) {
    tm = new TmallManager();
    return tm.imageUpload(tci);
  }
  
  // 描述图片上传到tmall
  @Override
  public TmallCommodityImgHistory describeImageUpload(TmallCommodityImgHistory tci) {
    tm = new TmallManager();
    return tm.imageUpload(tci);
  }

  // 删除sku
  @Override
  public String deleteSku(TmallCommoditySku tcs) {
    tm = new TmallManager();
    return tm.deleteSku(tcs);
  }

  // 获取sku
  @Override
  public TmallCommoditySku getSku(TmallCommoditySku tcs) {
    tm = new TmallManager();
    return tm.getSku(tcs);
  }

  // 搜索产品
  @Override
  public String getProduct(TmallCommodityHeader tch) {
    tm = new TmallManager();
    return tm.getProduct(tch);
  }

  // 添加产品
  @Override
  public String insertProduct(TmallCommodityHeader tch) {
    tm = new TmallManager();
    return tm.insertProduct(tch);
  }

  // Cod批量发货
  @Override
  public boolean codShippingDeliverySend(TmallShippingDelivery tsd) {
    tm = new TmallManager();
    return tm.codShippingDeliverySend(tsd);
  }

  // tmall全部全量sku库存更新
  @Override
  public void updateTmallStockBySku() {
    tm = new TmallManager();
    tm.updateTmallStockBySku();
  }

  // 订单下载时判断网络连接是否断开
  @Override
  public String getIsNetError() {
    tm = new TmallManager();
    return tm.getIsNetError();
  }

  // 获得子属性模板
  @Override
  public boolean insertChildProperty() {
    tm = new TmallManager();
    return tm.insertChildProperty();
  }

  // 验证一天的订单下载数量
  @Override
  public List<String> searchOrderDownLoadNums(String startTime, String endTime) {
    tm = new TmallManager();
    return tm.searchOrderDownLoadNums(startTime, endTime);
  }
  
  // 查询单笔订单交易状态
  @Override
  public String searchTmallTradeGet(String tid) {
    tm = new TmallManager();
    return tm.searchTmallTradeGet(tid);
  }
  
  public List<TmallOrderDetail> getTmallOrderDetailCommodityList(String orderCode, String shopCode) {
    Query query = new SimpleQuery(OrderServiceQuery.TMALL_ORDER_DETAIL_COMMODITY_LIST, orderCode, shopCode);
    return DatabaseUtil.loadAsBeanList(query, TmallOrderDetail.class);
  }

}
