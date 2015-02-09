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
import jp.co.sint.webshop.data.dao.StockStatusDao;
import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 在庫状況
 *
 * @author System Integrator Corp.
 *
 */
public class StockStatusDaoImpl implements StockStatusDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<StockStatus, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public StockStatusDaoImpl() {
    genericDao = new GenericDaoImpl<StockStatus, Long>(StockStatus.class);
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
   * 指定されたorm_rowidを持つ在庫状況のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するStockStatusのインスタンス
   */
  public StockStatus loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して在庫状況のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param stockStatusNo 在庫状況番号
   * @return 主キー列の値に対応するStockStatusのインスタンス
   */
  public StockStatus load(String shopCode, Long stockStatusNo) {
    Object[] params = new Object[]{shopCode, stockStatusNo};
    final String query = "SELECT * FROM STOCK_STATUS"
        + " WHERE SHOP_CODE = ?"
        + " AND STOCK_STATUS_NO = ?";
    List<StockStatus> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して在庫状況が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param stockStatusNo 在庫状況番号
   * @return 主キー列の値に対応するStockStatusの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long stockStatusNo) {
    Object[] params = new Object[]{shopCode, stockStatusNo};
    final String query = "SELECT COUNT(*) FROM STOCK_STATUS"
        + " WHERE SHOP_CODE = ?"
        + " AND STOCK_STATUS_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規StockStatusをデータベースに追加します。
   * @param obj 追加対象のStockStatus
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockStatus obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規StockStatusをデータベースに追加します。
   * @param obj 追加対象のStockStatus
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockStatus obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 在庫状況を更新します。
   * @param obj 更新対象のStockStatus
   */
  public void update(StockStatus obj) {
    genericDao.update(obj);
  }

  /**
   * 在庫状況を更新します。
   * @param obj 更新対象のStockStatus
   */
  public void update(StockStatus obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 在庫状況を削除します。
   * @param obj 削除対象のStockStatus
   */
  public void delete(StockStatus obj) {
    genericDao.delete(obj);
  }

  /**
   * 在庫状況を削除します。
   * @param obj 削除対象のStockStatus
   */
  public void delete(StockStatus obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して在庫状況を削除します。
   * @param shopCode ショップコード
   * @param stockStatusNo 在庫状況番号
   */
  public void delete(String shopCode, Long stockStatusNo) {
    Object[] params = new Object[]{shopCode, stockStatusNo};
    final String query = "DELETE FROM STOCK_STATUS"
        + " WHERE SHOP_CODE = ?"
        + " AND STOCK_STATUS_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して在庫状況のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockStatusのリスト
   */
  public List<StockStatus> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して在庫状況のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockStatusのリスト
   */
  public List<StockStatus> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のStockStatusのリスト
   */
  public List<StockStatus> loadAll() {
    return genericDao.loadAll();
  }

}
