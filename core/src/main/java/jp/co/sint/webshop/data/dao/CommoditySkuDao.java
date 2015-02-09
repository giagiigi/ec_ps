
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query; 
import jp.co.sint.webshop.data.dto.CommoditySku;
/** 
 * tm/jd多sku商品关联
 *
 */
public interface CommoditySkuDao extends GenericDao<CommoditySku, Long> {

  /**
   * 
   */
  CommoditySku loadByRowid(Long id);

  /**
   * 主键列的值
   */
  CommoditySku load(String orderNo);
 
  /**
   *Query对象指定的订单头信息列表取得。
   *@ param query Query对象
   *@ return搜索结果相当于jd名单
   */
  List<CommoditySku> findByQuery(Query query);

  /**
     * SQL指定的订单头信息列表取得。
      *@ param sqlString缱绻(?)在内的SQL字符串变量
      * @ param params必将给变量值的排列
      *@ return搜索结果相当于OrderHeader名单
   */
  List<CommoditySku> findByQuery(String sqlString, Object... params);
  Long insert(CommoditySku obj);
  void delete(String skucode);
 /**
  * 查询全部
  */
  List<CommoditySku> loadAll();

}
