package jp.co.sint.webshop.service.tmall;

public interface TmallInsertCategoryListService {

  // 获得淘宝类目列表同步到EC
  boolean insertCategoryList();
  
  // 获得淘宝品牌列表同步到EC
  boolean insertTmallBrandList();
}
