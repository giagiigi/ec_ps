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
import jp.co.sint.webshop.data.dao.CommodityMasterDao;
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 *tm/jd多sku商品关联 实现类
 */
public class CommodityMasterDaoImpl implements CommodityMasterDao, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GenericDaoImpl<CommodityMaster, Long> genericDao;
  
  /** SessionFactory */
  private SessionFactory sessionFactory;
  
  
  public CommodityMasterDaoImpl(){
    genericDao = new GenericDaoImpl<CommodityMaster, Long>(CommodityMaster.class);
  }
  
  
  @Override
  
  public List<CommodityMaster> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  @Override
  public List<CommodityMaster> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  @Override
  public CommodityMaster load(String skuCode) {
    
    Object[] params = new Object[]{skuCode};
    final String query = "select * from commodity_master where commodity_code=?";
    List<CommodityMaster> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<CommodityMaster> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public CommodityMaster loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  @Override
  public void delete(String cmcode) {
    final String query = "delete from commodity_master where commodity_code=?";
    genericDao.updateByQuery(query, cmcode);
    
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


  @Override
  public Long insert(CommodityMaster obj) {
    return genericDao.insert(obj);
  }


  @Override
  public void delete(CommodityMaster obj) {
    genericDao.update(obj);
  }


  @Override
  public void update(CommodityMaster cm) {
    genericDao.update(cm);
    
  }


  @Override
  public void update(CommodityMaster cm, LoginInfo info) {
  genericDao.update(cm, info);
    
  }

 
}
