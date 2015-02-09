
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query; 
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.data.dto.UntrueOrderWord;
/** 
 * 虚假订单关键词管理
 *
 */
public interface UntrueOrderWordDao extends GenericDao<UntrueOrderWord, Long> {

  /**
   * 
   */
  UntrueOrderWord loadByRowid(Long id);

  /**
   * 主键列的值
   */
  UntrueOrderWord load(String orderNo);
 
  /**
   *Query对象指定的订单头信息列表取得。
   *@ param query Query对象
   *@ return搜索结果相当于jd名单
   */
  List<UntrueOrderWord> findByQuery(Query query);

  /**
     * SQL指定的订单头信息列表取得。
      *@ param sqlString缱绻(?)在内的SQL字符串变量
      * @ param params必将给变量值的排列
      *@ return搜索结果相当于OrderHeader名单
   */
  List<UntrueOrderWord> findByQuery(String sqlString, Object... params);
  Long insert(UntrueOrderWord obj);
  void delete(String uowcode);
 /**
  * 查询全部
  */
  List<UntrueOrderWord> loadAll();

}
