
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query; 
import jp.co.sint.webshop.data.dto.JdBJCommodity;
/** 
 * jd订单拆分 北京仓
 *
 */
public interface JdBJCommodityDao extends GenericDao<JdBJCommodity, Long> {

  /**
   * 
   */
  JdBJCommodity loadByRowid(Long id);

  /**
   * 主键列的值
   *订单编号获得
   */
  JdBJCommodity load(String skuCode);
 
  /**
   *Query对象指定的订单头信息列表取得。
   *@ param query Query对象
   *@ return搜索结果相当于jd名单
   */
  List<JdBJCommodity> findByQuery(Query query);

  /**
     * SQL指定的订单头信息列表取得。
      *@ param sqlString缱绻(?)在内的SQL字符串变量
      * @ param params必将给变量值的排列
      *@ return搜索结果相当于OrderHeader名单
   */
  List<JdBJCommodity> findByQuery(String sqlString, Object... params);

 /**
  * 查询全部
  */
  List<JdBJCommodity> loadAll();


}
