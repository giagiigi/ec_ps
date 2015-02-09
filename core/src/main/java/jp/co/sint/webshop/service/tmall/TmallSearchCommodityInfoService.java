package jp.co.sint.webshop.service.tmall;

public interface TmallSearchCommodityInfoService {

  // 查询单个商品
  TmallCommodityHeader searchCommodity(String numIid);
}
