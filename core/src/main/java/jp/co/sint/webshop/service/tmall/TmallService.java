package jp.co.sint.webshop.service.tmall;

import java.util.List;

import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;

public interface TmallService {

  // 商品下架
  String commodityDelisting(String numiid);

  // 获得淘宝地址薄信息发
  boolean getTmallAreas();

  // 商品添加和更新 type:INSERT or UPDATE
  String insertOrUpdateCommodityHeader(TmallCommodityHeader tch, String type);

  // 商品SKU添加和更新 type:INSERT or UPDATE
  String insertOrUpdateCommoditySku(TmallCommoditySku tcs, String type);

  // 查询单个商品
  TmallCommodityHeader searchCommodity(String numIid);

  // 查询订单和发货数据封装到List
  TmallTradeInfoList searchOrderDetailList(String startTime, String endTime);

  // 查询单笔退货信息
  String searchRefundPayment(String refundId);

  // Alipay批量发货
  boolean ShippingDeliverySend(TmallShippingDelivery tsd);
  
  // Cod批量发货
  boolean codShippingDeliverySend(TmallShippingDelivery tsd);

  // 修改订单收获地址信息：需要传入淘宝发货信息类
  String updateShippingAddrInfo(TmallShippingDelivery tsh);

  // 获取淘宝系统时间
  String getTmallSysDateTime();

  // 获得淘宝商家授权的类目同步到EC
  boolean insertCategoryList();

  // 获得淘宝商家授权的品牌同步到EC
  boolean insertTmallBrandList();

  // 获得淘宝类目关联的属性和值同步到EC
  boolean insertPropertyAndValue();

  // sku库存更新
  String updateSkuStock(TmallCommoditySku tcs);
  
  // tmall全部全量sku库存更新
  void updateTmallStockBySku();

  // 图片上传到tmall
  TmallCommodityImg imageUpload(TmallCommodityImg tci);
  
  TmallCommodityImgHistory describeImageUpload(TmallCommodityImgHistory tci);

  // 获取sku
  TmallCommoditySku getSku(TmallCommoditySku tcs);

  // 删除sku
  String deleteSku(TmallCommoditySku tcs);

  // 添加产品
  String insertProduct(TmallCommodityHeader tch);

  // 搜索产品
  String getProduct(TmallCommodityHeader tch);
  
  // 订单下载时判断网络连接是否断开
  String getIsNetError();
  
  // 下载子属性模板
  boolean insertChildProperty();
  
  // 验证一天的订单下载数量
  List<String> searchOrderDownLoadNums(String startTime, String endTime);
  
  // 查询单笔订单交易状态
  public String searchTmallTradeGet(String tid);
  
  // 查询天猫商品列表
  List<TmallOrderDetail> getTmallOrderDetailCommodityList(String orderCode, String shopCode);
  
}
