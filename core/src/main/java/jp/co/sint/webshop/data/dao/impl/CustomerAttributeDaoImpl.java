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
import jp.co.sint.webshop.data.dao.CustomerAttributeDao;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客属性
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerAttributeDaoImpl implements CustomerAttributeDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerAttribute, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerAttributeDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerAttribute, Long>(CustomerAttribute.class);
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
   * 指定されたorm_rowidを持つ顧客属性のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAttributeのインスタンス
   */
  public CustomerAttribute loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客属性のインスタンスを取得します。
   * @param customerAttributeNo 顧客属性番号
   * @return 主キー列の値に対応するCustomerAttributeのインスタンス
   */
  public CustomerAttribute load(Long customerAttributeNo) {
    Object[] params = new Object[]{customerAttributeNo};
    final String query = "SELECT * FROM CUSTOMER_ATTRIBUTE"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?";
    List<CustomerAttribute> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客属性が既に存在するかどうかを返します。
   * @param customerAttributeNo 顧客属性番号
   * @return 主キー列の値に対応するCustomerAttributeの行が存在すればtrue
   */
  public boolean exists(Long customerAttributeNo) {
    Object[] params = new Object[]{customerAttributeNo};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_ATTRIBUTE"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerAttributeをデータベースに追加します。
   * @param obj 追加対象のCustomerAttribute
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAttribute obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerAttributeをデータベースに追加します。
   * @param obj 追加対象のCustomerAttribute
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAttribute obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客属性を更新します。
   * @param obj 更新対象のCustomerAttribute
   */
  public void update(CustomerAttribute obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客属性を更新します。
   * @param obj 更新対象のCustomerAttribute
   */
  public void update(CustomerAttribute obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客属性を削除します。
   * @param obj 削除対象のCustomerAttribute
   */
  public void delete(CustomerAttribute obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客属性を削除します。
   * @param obj 削除対象のCustomerAttribute
   */
  public void delete(CustomerAttribute obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客属性を削除します。
   * @param customerAttributeNo 顧客属性番号
   */
  public void delete(Long customerAttributeNo) {
    Object[] params = new Object[]{customerAttributeNo};
    final String query = "DELETE FROM CUSTOMER_ATTRIBUTE"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客属性のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAttributeのリスト
   */
  public List<CustomerAttribute> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客属性のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAttributeのリスト
   */
  public List<CustomerAttribute> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerAttributeのリスト
   */
  public List<CustomerAttribute> loadAll() {
    return genericDao.loadAll();
  }

}
