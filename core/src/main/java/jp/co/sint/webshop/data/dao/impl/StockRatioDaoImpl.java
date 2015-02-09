//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.StockRatioDao;
import jp.co.sint.webshop.data.dto.StockRatio;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 库存比例
 * 
 * @author System Integrator Corp.
 */
public class StockRatioDaoImpl implements StockRatioDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<StockRatio, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public StockRatioDaoImpl() {
    genericDao = new GenericDaoImpl<StockRatio, Long>(StockRatio.class);
  }

  /**
   * SessionFactoryを取得します
   * 
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactoryを設定します
   * 
   * @param factory
   *          SessionFactoryのインスタンス
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 指定されたorm_rowidを持つギフトのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するStockRatioのインスタンス
   */
  public StockRatio loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するStockRatioのインスタンス
   */
  public StockRatio load(String shopCode, String commodityCode, String ratioType) {
    Object[] params = new Object[] {
        shopCode, commodityCode, ratioType
    };
    final String query = "SELECT * FROM STOCK_RATIO" 
      + " WHERE SHOP_CODE = ?" + " AND COMMODITY_CODE = ?" + " AND RATIO_TYPE = ?";
    List<StockRatio> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してギフトが既に存在するかどうかを返します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するStockRatioの行が存在すればtrue
   */
  public boolean exists(String shopCode, String commodityCode, String ratioType) {
    Object[] params = new Object[] {
        shopCode, commodityCode, ratioType
    };
    final String query = "SELECT COUNT(*) FROM STOCK_RATIO" 
      + " WHERE SHOP_CODE = ?" + " AND COMMODITY_CODE = ?" + " AND RATIO_TYPE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規StockRatioをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のStockRatio
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockRatio obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規StockRatioをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のStockRatio
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(StockRatio obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のStockRatio
   */
  public void update(StockRatio obj) {
    genericDao.update(obj);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のStockRatio
   */
  public void update(StockRatio obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のStockRatio
   */
  public void delete(StockRatio obj) {
    genericDao.delete(obj);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のStockRatio
   */
  public void delete(StockRatio obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してギフトを削除します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          商品编号
   * @param ratioType
   *          在库比例区分
   */
  public void delete(String shopCode, String commodityCode, String ratioType) {
    Object[] params = new Object[] {
        shopCode, commodityCode, ratioType
    };
    final String query = "DELETE FROM STOCK_RATIO" + " WHERE SHOP_CODE = ?" 
    + " AND COMMODITY_CODE = ?" + " AND RATIO_TYPE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するStockRatioのリスト
   */
  public List<StockRatio> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してギフトのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するStockRatioのリスト
   */
  public List<StockRatio> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のStockRatioのリスト
   */
  public List<StockRatio> loadAll() {
    return genericDao.loadAll();
  }

}
