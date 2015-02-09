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
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.ShippingRealityDetailDao;
import jp.co.sint.webshop.data.dto.ShippingRealityDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 出荷明細
 *
 * @author System Integrator Corp.
 *
 */
public class ShippingRealityDetailDaoImpl implements ShippingRealityDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ShippingRealityDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ShippingRealityDetailDaoImpl() {
    genericDao = new GenericDaoImpl<ShippingRealityDetail, Long>(ShippingRealityDetail.class);
  }

  /**
   * SessionFactoryを取得します
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactoryを設定します
   * @param factory SessionFactoryのインスタンス
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }


  /**
   * SQLを指定して出荷明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShippingRealityDetailのリスト
   */
  public List<ShippingRealityDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  @Override
  public void delete(ShippingRealityDetail transactionObject) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<ShippingRealityDetail> findByQuery(Query query) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long insert(ShippingRealityDetail newInstance) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ShippingRealityDetail> loadAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ShippingRealityDetail loadByRowid(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update(ShippingRealityDetail transactionObject) {
    // TODO Auto-generated method stub
    
  }

}
