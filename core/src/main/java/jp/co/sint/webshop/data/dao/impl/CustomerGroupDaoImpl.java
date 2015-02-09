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
import jp.co.sint.webshop.data.dao.CustomerGroupDao;
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客グループ
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerGroupDaoImpl implements CustomerGroupDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerGroup, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerGroupDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerGroup, Long>(CustomerGroup.class);
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
   * 指定されたorm_rowidを持つ顧客グループのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerGroupのインスタンス
   */
  public CustomerGroup loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客グループのインスタンスを取得します。
   * @param customerGroupCode 顧客グループコード
   * @return 主キー列の値に対応するCustomerGroupのインスタンス
   */
  public CustomerGroup load(String customerGroupCode) {
    Object[] params = new Object[]{customerGroupCode};
    final String query = "SELECT * FROM CUSTOMER_GROUP"
        + " WHERE CUSTOMER_GROUP_CODE = ?";
    List<CustomerGroup> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客グループが既に存在するかどうかを返します。
   * @param customerGroupCode 顧客グループコード
   * @return 主キー列の値に対応するCustomerGroupの行が存在すればtrue
   */
  public boolean exists(String customerGroupCode) {
    Object[] params = new Object[]{customerGroupCode};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_GROUP"
        + " WHERE CUSTOMER_GROUP_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerGroupをデータベースに追加します。
   * @param obj 追加対象のCustomerGroup
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerGroup obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerGroupをデータベースに追加します。
   * @param obj 追加対象のCustomerGroup
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerGroup obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客グループを更新します。
   * @param obj 更新対象のCustomerGroup
   */
  public void update(CustomerGroup obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客グループを更新します。
   * @param obj 更新対象のCustomerGroup
   */
  public void update(CustomerGroup obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客グループを削除します。
   * @param obj 削除対象のCustomerGroup
   */
  public void delete(CustomerGroup obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客グループを削除します。
   * @param obj 削除対象のCustomerGroup
   */
  public void delete(CustomerGroup obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客グループを削除します。
   * @param customerGroupCode 顧客グループコード
   */
  public void delete(String customerGroupCode) {
    Object[] params = new Object[]{customerGroupCode};
    final String query = "DELETE FROM CUSTOMER_GROUP"
        + " WHERE CUSTOMER_GROUP_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客グループのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerGroupのリスト
   */
  public List<CustomerGroup> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客グループのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerGroupのリスト
   */
  public List<CustomerGroup> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerGroupのリスト
   */
  public List<CustomerGroup> loadAll() {
    return genericDao.loadAll();
  }

}
