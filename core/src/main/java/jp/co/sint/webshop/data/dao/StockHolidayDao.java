//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.service.LoginInfo;

public interface StockHolidayDao extends GenericDao<StockHoliday, Long> {


  
  /**
   * 找到对应行号
   */
  StockHoliday loadByRowid(Long id);
  /**
   * 查询全部休息日数
   */
  List<StockHoliday> loadAll();

  /**
   * id查询休息日数
   */

  StockHoliday load(Long wid);

  /**
   * 增加休息日
   * @param obj
   * @param loginInfo
   * @return
   */
  Long insert(StockHoliday obj);

  /**
   * 修改休息日
   * @param obj
   * @param loginInfo
   */
  void update(StockHoliday obj, LoginInfo loginInfo);
  /**
   * 删除休息日
   * @param obj
   * @param loginInfo
   */
  void delete(Date shday);
  
  /**
   * 定义查询语句
   */
  List<StockHoliday> findByQuery(Query query);

  List<StockHoliday> findByQuery(String sqlString, Object... params);

}
