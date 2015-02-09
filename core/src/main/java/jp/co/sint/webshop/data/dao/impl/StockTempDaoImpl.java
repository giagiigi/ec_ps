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
import jp.co.sint.webshop.data.dao.StockTempDao;
import jp.co.sint.webshop.data.dto.StockTemp;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 在庫Temp
 *
 * @author System Integrator Corp.
 *
 */
public class StockTempDaoImpl implements StockTempDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<StockTemp, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public StockTempDaoImpl() {
    genericDao = new GenericDaoImpl<StockTemp, Long>(StockTemp.class);
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
   * 指定されたorm_rowidを持つ在庫のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するStockのインスタンス
   */
  public StockTemp loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して在庫のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockのインスタンス
   */
  //2014/06/11 库存更新对应 ob_zhangzhen update start
  // public StockTemp load(String shopCode, String skuCode) {
//    Object[] params = new Object[]{shopCode, skuCode};
  public StockTemp load(String shopCode, String skuCode, Long stockChangeType) {
      Object[] params = new Object[]{shopCode, skuCode, stockChangeType};
  //2014/06/11 库存更新对应 ob_zhangzhen update end
    final String query = "SELECT * FROM STOCK_TEMP"
        + " WHERE SHOP_CODE = ?"
        //2014/06/11 库存更新对应 ob_zhangzhen update start
        //+ " AND SKU_CODE = ?";
        + " AND SKU_CODE = ?"
        + " AND STOCK_CHANGE_TYPE = ?";
        //2014/06/11 库存更新对应 ob_zhangzhen update end
    List<StockTemp> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して在庫が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockの行が存在すればtrue
   */
  //2014/06/11 库存更新对应 ob_zhangzhen update start
  // public boolean exists(String shopCode, String skuCode) {
//      Object[] params = new Object[]{shopCode, skuCode};
  public boolean exists(String shopCode, String skuCode, Long stockChangeType) {
      Object[] params = new Object[]{shopCode, skuCode, stockChangeType};
  //2014/06/11 库存更新对应 ob_zhangzhen update end
    final String query = "SELECT COUNT(*) FROM STOCK_TEMP"
        + " WHERE SHOP_CODE = ?"
        //2014/06/11 库存更新对应 ob_zhangzhen update start
        //+ " AND SKU_CODE = ?";
        + " AND SKU_CODE = ?"
        + " AND STOCK_CHANGE_TYPE = ?";
        //2014/06/11 库存更新对应 ob_zhangzhen update end
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockTemp obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockTemp obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(StockTemp obj) {
    genericDao.update(obj);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(StockTemp obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(StockTemp obj) {
    genericDao.delete(obj);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(StockTemp obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して在庫を削除します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  //2014/06/11 库存更新对应 ob_zhangzhen update start
  //public void delete(String shopCode, String skuCode) {
//    Object[] params = new Object[]{shopCode, skuCode};
  public void delete(String shopCode, String skuCode, Long stockChangeType) {
    Object[] params = new Object[]{shopCode, skuCode, stockChangeType};
  //2014/06/11 库存更新对应 ob_zhangzhen update end
    final String query = "DELETE FROM STOCK_TEMP"
        + " WHERE SHOP_CODE = ?"
        //2014/06/11 库存更新对应 ob_zhangzhen update start
        //+ " AND SKU_CODE = ?";
        + " AND SKU_CODE = ?"
        + " AND STOCK_CHANGE_TYPE = ?";
        //2014/06/11 库存更新对应 ob_zhangzhen update end
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して在庫のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockのリスト
   */
  public List<StockTemp> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して在庫のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockのリスト
   */
  public List<StockTemp> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のStockのリスト
   */
  public List<StockTemp> loadAll() {
    return genericDao.loadAll();
  }
}
