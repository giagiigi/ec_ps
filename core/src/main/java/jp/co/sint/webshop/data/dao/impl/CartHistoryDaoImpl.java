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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.CartHistoryDao;
import jp.co.sint.webshop.data.dto.CartHistory;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 购物车履历
 *
 * @author Kousen.
 *
 */
public class CartHistoryDaoImpl implements CartHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CartHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CartHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<CartHistory, Long>(CartHistory.class);
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
   * 指定されたorm_rowidを持つ购物车履历のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCartHistoryのインスタンス
   */
  public CartHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して购物车履历のインスタンスを取得します。
   * @param customerCode 顾客编号
   * @param shopCode 店铺编号
   * @param skuCode SKU编号
   * @return 主キー列の値に対応するCartHistoryのインスタンス
   */
  public CartHistory load(String customerCode, String shopCode, String skuCode) {
    Object[] params = new Object[]{customerCode, shopCode, skuCode};
    final String query = "SELECT * FROM CART_HISTORY"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    List<CartHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して购物车履历が既に存在するかどうかを返します。
   * @param customerCode 顾客编号
   * @param shopCode 店铺编号
   * @param skuCode SKU编号
   * @return 主キー列の値に対応するCartHistoryの行が存在すればtrue
   */
  public boolean exists(String customerCode, String shopCode, String skuCode) {
    Object[] params = new Object[]{customerCode, shopCode, skuCode};
    final String query = "SELECT COUNT(*) FROM CART_HISTORY"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CartHistoryをデータベースに追加します。
   * @param obj 追加対象のCartHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CartHistory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CartHistoryをデータベースに追加します。
   * @param obj 追加対象のCartHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CartHistory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 购物车履历を更新します。
   * @param obj 更新対象のCartHistory
   */
  public void update(CartHistory obj) {
    genericDao.update(obj);
  }

  /**
   * 购物车履历を更新します。
   * @param obj 更新対象のCartHistory
   */
  public void update(CartHistory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 购物车履历を削除します。
   * @param obj 削除対象のCartHistory
   */
  public void delete(CartHistory obj) {
    genericDao.delete(obj);
  }

  /**
   * 购物车履历を削除します。
   * @param obj 削除対象のCartHistory
   */
  public void delete(CartHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して购物车履历を削除します。
   * @param customerCode 顾客编号
   * @param shopCode 店铺编号
   * @param skuCode SKU编号
   */
  public void delete(String customerCode, String shopCode, String skuCode) {
    Object[] params = new Object[]{customerCode, shopCode, skuCode};
    final String query = "DELETE FROM CART_HISTORY"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して购物车履历のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCartHistoryのリスト
   */
  public List<CartHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して购物车履历のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCartHistoryのリスト
   */
  public List<CartHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCartHistoryのリスト
   */
  public List<CartHistory> loadAll() {
    return genericDao.loadAll();
  }

}
