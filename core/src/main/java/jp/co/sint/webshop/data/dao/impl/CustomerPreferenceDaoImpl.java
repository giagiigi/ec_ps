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
import jp.co.sint.webshop.data.dao.CustomerPreferenceDao;
import jp.co.sint.webshop.data.dto.CustomerPreference;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客嗜好
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerPreferenceDaoImpl implements CustomerPreferenceDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerPreference, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerPreferenceDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerPreference, Long>(CustomerPreference.class);
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
   * 指定されたorm_rowidを持つ顧客嗜好のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerPreferenceのインスタンス
   */
  public CustomerPreference loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客嗜好のインスタンスを取得します。
   * @param customerPreferenceId 顧客嗜好ID
   * @return 主キー列の値に対応するCustomerPreferenceのインスタンス
   */
  public CustomerPreference load(Long customerPreferenceId) {
    Object[] params = new Object[]{customerPreferenceId};
    final String query = "SELECT * FROM CUSTOMER_PREFERENCE"
        + " WHERE CUSTOMER_PREFERENCE_ID = ?";
    List<CustomerPreference> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客嗜好が既に存在するかどうかを返します。
   * @param customerPreferenceId 顧客嗜好ID
   * @return 主キー列の値に対応するCustomerPreferenceの行が存在すればtrue
   */
  public boolean exists(Long customerPreferenceId) {
    Object[] params = new Object[]{customerPreferenceId};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_PREFERENCE"
        + " WHERE CUSTOMER_PREFERENCE_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerPreferenceをデータベースに追加します。
   * @param obj 追加対象のCustomerPreference
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerPreference obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerPreferenceをデータベースに追加します。
   * @param obj 追加対象のCustomerPreference
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerPreference obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客嗜好を更新します。
   * @param obj 更新対象のCustomerPreference
   */
  public void update(CustomerPreference obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客嗜好を更新します。
   * @param obj 更新対象のCustomerPreference
   */
  public void update(CustomerPreference obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客嗜好を削除します。
   * @param obj 削除対象のCustomerPreference
   */
  public void delete(CustomerPreference obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客嗜好を削除します。
   * @param obj 削除対象のCustomerPreference
   */
  public void delete(CustomerPreference obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客嗜好を削除します。
   * @param customerPreferenceId 顧客嗜好ID
   */
  public void delete(Long customerPreferenceId) {
    Object[] params = new Object[]{customerPreferenceId};
    final String query = "DELETE FROM CUSTOMER_PREFERENCE"
        + " WHERE CUSTOMER_PREFERENCE_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客嗜好のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerPreferenceのリスト
   */
  public List<CustomerPreference> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客嗜好のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerPreferenceのリスト
   */
  public List<CustomerPreference> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerPreferenceのリスト
   */
  public List<CustomerPreference> loadAll() {
    return genericDao.loadAll();
  }

}
