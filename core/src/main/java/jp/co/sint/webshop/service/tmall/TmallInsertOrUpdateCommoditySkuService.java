package jp.co.sint.webshop.service.tmall;

public interface TmallInsertOrUpdateCommoditySkuService {

  // 商品SKU添加和更新   type:INSERT  or  UPDATE 
  String insertOrUpdateCommoditySku(TmallCommoditySku tcs, String type);
}
