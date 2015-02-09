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
import jp.co.sint.webshop.data.dao.TmallStockAllocationDao;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * TMALL库存比率分配
 * 
 * @author System Integrator Corp.
 */
public class TmallStockAllocationDaoImpl implements TmallStockAllocationDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallStockAllocation, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TmallStockAllocationDaoImpl() {
    genericDao = new GenericDaoImpl<TmallStockAllocation, Long>(TmallStockAllocation.class);
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
   * @return idに対応するTmallStockAllocationのインスタンス
   */
  public TmallStockAllocation loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するTmallStockAllocationのインスタンス
   */
  public TmallStockAllocation load(String shopCode, String skuCode) {
    Object[] params = new Object[] {
        shopCode, skuCode
    };
    final String query = "SELECT * FROM TMALL_STOCK_ALLOCATION" + " WHERE SHOP_CODE = ?" + " AND SKU_CODE = ?";
    List<TmallStockAllocation> result = genericDao.findByQuery(query, params);
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
   * @return 主キー列の値に対応するTmallStockAllocationの行が存在すればtrue
   */
  public boolean exists(String shopCode, String skuCode) {
    Object[] params = new Object[] {
        shopCode, skuCode
    };
    final String query = "SELECT COUNT(*) FROM TMALL_STOCK_ALLOCATION" + " WHERE SHOP_CODE = ?" + " AND SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規TmallStockAllocationをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のTmallStockAllocation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallStockAllocation obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallStockAllocationをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のTmallStockAllocation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallStockAllocation obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のTmallStockAllocation
   */
  public void update(TmallStockAllocation obj) {
    genericDao.update(obj);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のTmallStockAllocation
   */
  public void update(TmallStockAllocation obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のTmallStockAllocation
   */
  public void delete(TmallStockAllocation obj) {
    genericDao.delete(obj);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のTmallStockAllocation
   */
  public void delete(TmallStockAllocation obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してギフトを削除します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   */
  public void delete(String shopCode, String skuCode) {
    Object[] params = new Object[] {
        shopCode, skuCode
    };
    final String query = "DELETE FROM TMALL_STOCK_ALLOCATION" + " WHERE SHOP_CODE = ?" + " AND SKU_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するTmallStockAllocationのリスト
   */
  public List<TmallStockAllocation> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してギフトのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するTmallStockAllocationのリスト
   */
  public List<TmallStockAllocation> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のTmallStockAllocationのリスト
   */
  public List<TmallStockAllocation> loadAll() {
    return genericDao.loadAll();
  }

}
