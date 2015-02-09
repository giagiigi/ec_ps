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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.JdBJCommodityDao;
import jp.co.sint.webshop.data.dto.JdBJCommodity;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;

import org.hibernate.SessionFactory;
/** 
 *jd订单北京仓接口实现
 */
public class JdBJCommodityDaoImpl implements JdBJCommodityDao, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GenericDaoImpl<JdBJCommodity, Long> genericDao;
  
  /** SessionFactory */
  private SessionFactory sessionFactory;
  
  public JdBJCommodityDaoImpl(){
    genericDao = new GenericDaoImpl<JdBJCommodity, Long>(JdBJCommodity.class);
  }
  
  
  @Override
  public List<JdBJCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  @Override
  public List<JdBJCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  @Override
  public JdBJCommodity load(String skuCode) {
    Object[] params = new Object[]{skuCode};
    final String query = "SELECT * FROM JD_BJ_COMMODITY"
        + " WHERE SKU_CODE = ?";
    List<JdBJCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<JdBJCommodity> loadAll() {
    return genericDao.loadAll();
  }
  
  @Override
  public JdBJCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  @Override
  public void delete(JdBJCommodity obj) {
    genericDao.delete(obj);
    
  }

  @Override
  public Long insert(JdBJCommodity obj) {
    return genericDao.insert(obj);
  }

  @Override
  public void update(JdBJCommodity obj) {
    genericDao.update(obj);
    
  }

  
  /**
   * @return the genericDao
   */
  public GenericDaoImpl<JdBJCommodity, Long> getGenericDao() {
    return genericDao;
  }

  
  /**
   * @param genericDao the genericDao to set
   */
  public void setGenericDao(GenericDaoImpl<JdBJCommodity, Long> genericDao) {
    this.genericDao = genericDao;
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

  

}
