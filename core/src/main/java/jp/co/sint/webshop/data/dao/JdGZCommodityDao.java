
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query; 
import jp.co.sint.webshop.data.dto.JdGZCommodity;
/** 
 * 京东订单拆分广州仓
 *
 */
public interface JdGZCommodityDao extends GenericDao<JdGZCommodity, Long> {

  /**
   * 
   */
  JdGZCommodity loadByRowid(Long id);

  /**
   * 主键列的值
   *订单编号获得
   */
  JdGZCommodity load(String orderNo);
 
  /**
   *Query对象指定的订单头信息列表取得。
   *@ param query Query对象
   *@ return搜索结果相当于jd名单
   */
  List<JdGZCommodity> findByQuery(Query query);

  /**
     * SQL指定的订单头信息列表取得。
      *@ param sqlString缱绻(?)在内的SQL字符串变量
      * @ param params必将给变量值的排列
      *@ return搜索结果相当于OrderHeader名单
   */
  List<JdGZCommodity> findByQuery(String sqlString, Object... params);

 /**
  * 查询全部
  */
  List<JdGZCommodity> loadAll();

}
