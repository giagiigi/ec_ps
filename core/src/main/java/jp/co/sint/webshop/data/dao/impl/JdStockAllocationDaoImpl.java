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
import jp.co.sint.webshop.data.dao.JdStockAllocationDao;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * TMALL库存比率分配
 * 
 * @author System Integrator Corp.
 */
public class JdStockAllocationDaoImpl implements JdStockAllocationDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<JdStockAllocation, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public JdStockAllocationDaoImpl() {
    genericDao = new GenericDaoImpl<JdStockAllocation, Long>(JdStockAllocation.class);
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
   * @return idに対応するJdStockAllocationのインスタンス
   */
  public JdStockAllocation loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するJdStockAllocationのインスタンス
   */
  public JdStockAllocation load(String shopCode, String skuCode) {
    Object[] params = new Object[] {
        shopCode, skuCode
    };
    final String query = "SELECT * FROM JD_STOCK_ALLOCATION" + " WHERE SHOP_CODE = ?" + " AND SKU_CODE = ?";
    List<JdStockAllocation> result = genericDao.findByQuery(query, params);
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
   * @return 主キー列の値に対応するJdStockAllocationの行が存在すればtrue
   */
  public boolean exists(String shopCode, String skuCode) {
    Object[] params = new Object[] {
        shopCode, skuCode
    };
    final String query = "SELECT COUNT(*) FROM JD_STOCK_ALLOCATION" + " WHERE SHOP_CODE = ?" + " AND SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規JdStockAllocationをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のJdStockAllocation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdStockAllocation obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規JdStockAllocationをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のJdStockAllocation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdStockAllocation obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のJdStockAllocation
   */
  public void update(JdStockAllocation obj) {
    genericDao.update(obj);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のJdStockAllocation
   */
  public void update(JdStockAllocation obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のJdStockAllocation
   */
  public void delete(JdStockAllocation obj) {
    genericDao.delete(obj);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のJdStockAllocation
   */
  public void delete(JdStockAllocation obj, LoginInfo loginInfo) {
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
    final String query = "DELETE FROM JD_STOCK_ALLOCATION" + " WHERE SHOP_CODE = ?" + " AND SKU_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するJdStockAllocationのリスト
   */
  public List<JdStockAllocation> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してギフトのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するJdStockAllocationのリスト
   */
  public List<JdStockAllocation> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のJdStockAllocationのリスト
   */
  public List<JdStockAllocation> loadAll() {
    return genericDao.loadAll();
  }

}
