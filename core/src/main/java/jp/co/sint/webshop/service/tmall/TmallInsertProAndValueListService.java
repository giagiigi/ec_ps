package jp.co.sint.webshop.service.tmall;

import java.util.List;

public interface TmallInsertProAndValueListService {

  // 获得淘宝类目关联的属性和值同步到EC
  boolean insertPropertyAndValue();

  // 下载子属性模板
  boolean insertChildProperty(List<String> brandCodeList);
}
