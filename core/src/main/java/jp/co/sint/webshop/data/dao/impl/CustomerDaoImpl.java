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
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerDaoImpl implements CustomerDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Customer, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerDaoImpl() {
    genericDao = new GenericDaoImpl<Customer, Long>(Customer.class);
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
   * 指定されたorm_rowidを持つ顧客のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerのインスタンス
   */
  public Customer loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客のインスタンスを取得します。
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerのインスタンス
   */
  public Customer load(String customerCode) {
    Object[] params = new Object[]{customerCode};
    final String query = "SELECT * FROM CUSTOMER"
        + " WHERE CUSTOMER_CODE = ?";
    List<Customer> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  
  public Customer loadByEmail(String email) {
    Object[] params = new Object[]{email};
    final String query = "SELECT * FROM CUSTOMER"
        + " WHERE email = ?";
    List<Customer> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客が既に存在するかどうかを返します。
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerの行が存在すればtrue
   */
  public boolean exists(String customerCode) {
    Object[] params = new Object[]{customerCode};
    final String query = "SELECT COUNT(*) FROM CUSTOMER"
        + " WHERE CUSTOMER_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Customerをデータベースに追加します。
   * @param obj 追加対象のCustomer
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Customer obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Customerをデータベースに追加します。
   * @param obj 追加対象のCustomer
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Customer obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客を更新します。
   * @param obj 更新対象のCustomer
   */
  public void update(Customer obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客を更新します。
   * @param obj 更新対象のCustomer
   */
  public void update(Customer obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客を削除します。
   * @param obj 削除対象のCustomer
   */
  public void delete(Customer obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客を削除します。
   * @param obj 削除対象のCustomer
   */
  public void delete(Customer obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客を削除します。
   * @param customerCode 顧客コード
   */
  public void delete(String customerCode) {
    Object[] params = new Object[]{customerCode};
    final String query = "DELETE FROM CUSTOMER"
        + " WHERE CUSTOMER_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerのリスト
   */
  public List<Customer> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerのリスト
   */
  public List<Customer> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerのリスト
   */
  public List<Customer> loadAll() {
    return genericDao.loadAll();
  }

}
