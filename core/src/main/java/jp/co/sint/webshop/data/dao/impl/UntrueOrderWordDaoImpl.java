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
import jp.co.sint.webshop.data.dao.UntrueOrderWordDao;
import jp.co.sint.webshop.data.dto.UntrueOrderWord;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
/** 
 *虚假订单关键词管理
 */
public class UntrueOrderWordDaoImpl implements UntrueOrderWordDao, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GenericDaoImpl<UntrueOrderWord, Long> genericDao;
  
  /** SessionFactory */
  private SessionFactory sessionFactory;
  
  
  public UntrueOrderWordDaoImpl(){
    genericDao = new GenericDaoImpl<UntrueOrderWord, Long>(UntrueOrderWord.class);
  }
  
  
  @Override
  
  public List<UntrueOrderWord> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  @Override
  public List<UntrueOrderWord> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  @Override
  public UntrueOrderWord load(String skuCode) {
   /* Object[] params = new Object[]{skuCode};
    final String query = "SELECT * FROM JD_GZ_COMMODITY"
        + " WHERE SKU_CODE = ?";
    List<UntrueOrderWord> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }*/
    return null;
  }

  @Override
  public List<UntrueOrderWord> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public UntrueOrderWord loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  @Override
  public void delete(String uowcode) {
    final String query = "delete from UNTRUE_ORDER_WORD where order_word_code=? ";
    genericDao.updateByQuery(query, uowcode);
    
  }

  @Override
  public Long insert(UntrueOrderWord obj) {
    return genericDao.insert(obj);
  }

  @Override
  public void update(UntrueOrderWord obj) {
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
  public GenericDaoImpl<UntrueOrderWord, Long> getGenericDao() {
    return genericDao;
  }

  
  /**
   * @param genericDao the genericDao to set
   */
  public void setGenericDao(GenericDaoImpl<UntrueOrderWord, Long> genericDao) {
    this.genericDao = genericDao;
  }


  @Override
  public void delete(UntrueOrderWord transactionObject) {
    // TODO Auto-generated method stub
    
  }

 
}
