//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.StockHolidayDao;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.service.LoginInfo;
/**
 * 库存休息日IMPL
 * @author zengzhiyuan
 *
 */
public class StockHolidayDaoImpl implements StockHolidayDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;
  
  /** Generic Dao */
  private GenericDaoImpl<StockHoliday, Long> genericDao;
  
  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public StockHolidayDaoImpl() {
    genericDao = new GenericDaoImpl<StockHoliday, Long>(StockHoliday.class);
  }

  /**
   * SessionFactory获取
   * @return SessionFactory实例
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }
  
  /**
   * SessionFactory设定
   * @param factory SessionFactory的实例
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 找到rowid对应的StockHoliday的实例
   */
  
  @Override
  public StockHoliday loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
    
  }


  @Override
  public List<StockHoliday> findByQuery(Query query) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<StockHoliday> findByQuery(String sqlString, Object... params) {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public StockHoliday load(Long wid) {
    Object[] params = new Object[]{wid};
    final String query = "SELECT * FROM stock_holiday"
        + " WHERE orm_rowid = ?";
    List<StockHoliday> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<StockHoliday> loadAll() {
    return genericDao.loadAll();
  }


  @Override
  public void delete(StockHoliday obj) {
    
    genericDao.delete(obj);
  }

  //添加库存休息日
  @Override
  public Long insert(StockHoliday obj) {
    
    return genericDao.insert(obj);
  }

  @Override
  public void update(StockHoliday obj) {
    genericDao.update(obj);
    
  }

  @Override
  public void update(StockHoliday obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
    
  }

  @Override
  public void delete(Date shday) {
    final String query = "delete from stock_holiday where holiday_day=?";
    genericDao.updateByQuery(query, shday);
    
  }

}
