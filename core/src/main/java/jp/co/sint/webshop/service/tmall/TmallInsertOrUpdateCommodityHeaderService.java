package jp.co.sint.webshop.service.tmall;

public interface TmallInsertOrUpdateCommodityHeaderService {

  // 商品添加和更新   type:INSERT  or  UPDATE 
  String insertOrUpdateCommodityHeader(TmallCommodityHeader tch, String type);
}
