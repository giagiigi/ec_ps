//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.JdGZCommodityDao;
import jp.co.sint.webshop.data.dto.JdBJCommodity;
import jp.co.sint.webshop.data.dto.JdGZCommodity;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
/** 
 *jd订单广州仓接口实现
 */
public class JdGZCommodityDaoImpl implements JdGZCommodityDao, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GenericDaoImpl<JdGZCommodity, Long> genericDao;
  
  /** SessionFactory */
  private SessionFactory sessionFactory;
  
  
  public JdGZCommodityDaoImpl(){
    genericDao = new GenericDaoImpl<JdGZCommodity, Long>(JdGZCommodity.class);
  }
  
  
  @Override
  
  public List<JdGZCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  @Override
  public List<JdGZCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  @Override
  public JdGZCommodity load(String skuCode) {
    Object[] params = new Object[]{skuCode};
    final String query = "SELECT * FROM JD_GZ_COMMODITY"
        + " WHERE SKU_CODE = ?";
    List<JdGZCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<JdGZCommodity> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public JdGZCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  @Override
  public void delete(JdGZCommodity obj) {
    genericDao.delete(obj);
    
  }

  @Override
  public Long insert(JdGZCommodity obj) {
    return genericDao.insert(obj);
  }

  @Override
  public void update(JdGZCommodity obj) {
     genericDao.update(obj);
    
  }

  /**
   * @return the sessionFactory
   */
  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  
  /**
   * @param sessionFactory the sessionFactory to set
   */
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    genericDao.setSessionFactory(sessionFactory);
  }

  
  /**
   * @return the genericDao
   */
  public GenericDaoImpl<JdGZCommodity, Long> getGenericDao() {
    return genericDao;
  }

  
  /**
   * @param genericDao the genericDao to set
   */
  public void setGenericDao(GenericDaoImpl<JdGZCommodity, Long> genericDao) {
    this.genericDao = genericDao;
  }

 
}
