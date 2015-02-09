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
import jp.co.sint.webshop.data.dao.CustomerStatisticsDao;
import jp.co.sint.webshop.data.dto.CustomerStatistics;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客統計
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerStatisticsDaoImpl implements CustomerStatisticsDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerStatistics, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerStatisticsDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerStatistics, Long>(CustomerStatistics.class);
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
   * 指定されたorm_rowidを持つ顧客統計のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerStatisticsのインスタンス
   */
  public CustomerStatistics loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客統計のインスタンスを取得します。
   * @param customerStatisticsId 顧客統計ID
   * @return 主キー列の値に対応するCustomerStatisticsのインスタンス
   */
  public CustomerStatistics load(Long customerStatisticsId) {
    Object[] params = new Object[]{customerStatisticsId};
    final String query = "SELECT * FROM CUSTOMER_STATISTICS"
        + " WHERE CUSTOMER_STATISTICS_ID = ?";
    List<CustomerStatistics> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客統計が既に存在するかどうかを返します。
   * @param customerStatisticsId 顧客統計ID
   * @return 主キー列の値に対応するCustomerStatisticsの行が存在すればtrue
   */
  public boolean exists(Long customerStatisticsId) {
    Object[] params = new Object[]{customerStatisticsId};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_STATISTICS"
        + " WHERE CUSTOMER_STATISTICS_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerStatisticsをデータベースに追加します。
   * @param obj 追加対象のCustomerStatistics
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerStatistics obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerStatisticsをデータベースに追加します。
   * @param obj 追加対象のCustomerStatistics
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerStatistics obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客統計を更新します。
   * @param obj 更新対象のCustomerStatistics
   */
  public void update(CustomerStatistics obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客統計を更新します。
   * @param obj 更新対象のCustomerStatistics
   */
  public void update(CustomerStatistics obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客統計を削除します。
   * @param obj 削除対象のCustomerStatistics
   */
  public void delete(CustomerStatistics obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客統計を削除します。
   * @param obj 削除対象のCustomerStatistics
   */
  public void delete(CustomerStatistics obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客統計を削除します。
   * @param customerStatisticsId 顧客統計ID
   */
  public void delete(Long customerStatisticsId) {
    Object[] params = new Object[]{customerStatisticsId};
    final String query = "DELETE FROM CUSTOMER_STATISTICS"
        + " WHERE CUSTOMER_STATISTICS_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客統計のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerStatisticsのリスト
   */
  public List<CustomerStatistics> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客統計のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerStatisticsのリスト
   */
  public List<CustomerStatistics> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerStatisticsのリスト
   */
  public List<CustomerStatistics> loadAll() {
    return genericDao.loadAll();
  }

}
