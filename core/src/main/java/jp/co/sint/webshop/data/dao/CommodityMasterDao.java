
package jp.co.sint.webshop.data.dao;
import java.util.List;
import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query; 
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * tm/jd 多sku商品关联 主
 *
 */
public interface CommodityMasterDao extends GenericDao<CommodityMaster, Long> {

  /**
   * 
   */
  CommodityMaster loadByRowid(Long id);

  /**
   * 主键列的值
   */
  CommodityMaster load(String orderNo);
 
  /**
   *Query对象指定的订单头信息列表取得。
   *@ param query Query对象
   *@ return搜索结果相当于jd名单
   */
  List<CommodityMaster> findByQuery(Query query);

  /**
     * SQL指定的订单头信息列表取得。
      *@ param sqlString缱绻(?)在内的SQL字符串变量
      * @ param params必将给变量值的排列
      *@ return搜索结果相当于OrderHeader名单
   */
  List<CommodityMaster> findByQuery(String sqlString, Object... params);
  Long insert(CommodityMaster obj);
  void delete(String cmcode);
 /**
  * 查询全部
  */
  List<CommodityMaster> loadAll();
  public void update(CommodityMaster cm,LoginInfo info);

}
