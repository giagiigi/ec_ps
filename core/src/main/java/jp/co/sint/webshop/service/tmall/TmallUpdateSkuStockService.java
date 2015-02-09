package jp.co.sint.webshop.service.tmall;

public interface TmallUpdateSkuStockService {

  // sku库存更新
  String updateSkuStock(TmallCommoditySku tcs);
  
  // tmall全部全量sku库存更新
  void updateTmallStockBySku();
}
