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
import jp.co.sint.webshop.data.dao.StockIODetailDao;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 入出庫明細
 *
 * @author System Integrator Corp.
 *
 */
public class StockIODetailDaoImpl implements StockIODetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<StockIODetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public StockIODetailDaoImpl() {
    genericDao = new GenericDaoImpl<StockIODetail, Long>(StockIODetail.class);
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
   * 指定されたorm_rowidを持つ入出庫明細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するStockIODetailのインスタンス
   */
  public StockIODetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して入出庫明細のインスタンスを取得します。
   * @param stockIOId 入出庫行ID
   * @return 主キー列の値に対応するStockIODetailのインスタンス
   */
  public StockIODetail load(Long stockIOId) {
    Object[] params = new Object[]{stockIOId};
    final String query = "SELECT * FROM STOCK_IO_DETAIL"
        + " WHERE STOCK_IO_ID = ?";
    List<StockIODetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して入出庫明細が既に存在するかどうかを返します。
   * @param stockIOId 入出庫行ID
   * @return 主キー列の値に対応するStockIODetailの行が存在すればtrue
   */
  public boolean exists(Long stockIOId) {
    Object[] params = new Object[]{stockIOId};
    final String query = "SELECT COUNT(*) FROM STOCK_IO_DETAIL"
        + " WHERE STOCK_IO_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規StockIODetailをデータベースに追加します。
   * @param obj 追加対象のStockIODetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockIODetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規StockIODetailをデータベースに追加します。
   * @param obj 追加対象のStockIODetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockIODetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 入出庫明細を更新します。
   * @param obj 更新対象のStockIODetail
   */
  public void update(StockIODetail obj) {
    genericDao.update(obj);
  }

  /**
   * 入出庫明細を更新します。
   * @param obj 更新対象のStockIODetail
   */
  public void update(StockIODetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 入出庫明細を削除します。
   * @param obj 削除対象のStockIODetail
   */
  public void delete(StockIODetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 入出庫明細を削除します。
   * @param obj 削除対象のStockIODetail
   */
  public void delete(StockIODetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して入出庫明細を削除します。
   * @param stockIOId 入出庫行ID
   */
  public void delete(Long stockIOId) {
    Object[] params = new Object[]{stockIOId};
    final String query = "DELETE FROM STOCK_IO_DETAIL"
        + " WHERE STOCK_IO_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して入出庫明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockIODetailのリスト
   */
  public List<StockIODetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して入出庫明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockIODetailのリスト
   */
  public List<StockIODetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のStockIODetailのリスト
   */
  public List<StockIODetail> loadAll() {
    return genericDao.loadAll();
  }

}
