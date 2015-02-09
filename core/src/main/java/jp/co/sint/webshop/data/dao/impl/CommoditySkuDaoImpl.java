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
import jp.co.sint.webshop.data.dao.CommoditySkuDao;
import jp.co.sint.webshop.data.dto.CommoditySku;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
/** 
 *tm/jd多sku商品关联 实现类
 */
public class CommoditySkuDaoImpl implements CommoditySkuDao, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GenericDaoImpl<CommoditySku, Long> genericDao;
  
  /** SessionFactory */
  private SessionFactory sessionFactory;
  
  
  public CommoditySkuDaoImpl(){
    genericDao = new GenericDaoImpl<CommoditySku, Long>(CommoditySku.class);
  }
  
  
  @Override
  
  public List<CommoditySku> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  @Override
  public List<CommoditySku> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  @Override
  public CommoditySku load(String skuCode) {
    Object[] params = new Object[]{skuCode};
    final String query = "select * from commodity_sku where commodity_code = ?";
    List<CommoditySku> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<CommoditySku> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public CommoditySku loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  @Override
  public void delete(String skucode) {
    final String query = "delete from commodity_sku where sku_code=?";
    genericDao.updateByQuery(query, skucode);
    
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
  public Long insert(CommoditySku obj) {
    return genericDao.insert(obj);
  }


  @Override
  public void delete(CommoditySku obj) {
    genericDao.update(obj);
  }


  @Override
  public void update(CommoditySku transactionObject) {
    // TODO Auto-generated method stub
    
  }



 
}
