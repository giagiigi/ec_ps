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
import jp.co.sint.webshop.data.dao.SalesAmountByShopDao;
import jp.co.sint.webshop.data.dto.SalesAmountByShop;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * ショップ別売上集計
 *
 * @author System Integrator Corp.
 *
 */
public class SalesAmountByShopDaoImpl implements SalesAmountByShopDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<SalesAmountByShop, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public SalesAmountByShopDaoImpl() {
    genericDao = new GenericDaoImpl<SalesAmountByShop, Long>(SalesAmountByShop.class);
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
   * 指定されたorm_rowidを持つショップ別売上集計のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するSalesAmountByShopのインスタンス
   */
  public SalesAmountByShop loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してショップ別売上集計のインスタンスを取得します。
   * @param salesAmountByShopId ショップ別売上集計ID
   * @return 主キー列の値に対応するSalesAmountByShopのインスタンス
   */
  public SalesAmountByShop load(Long salesAmountByShopId) {
    Object[] params = new Object[]{salesAmountByShopId};
    final String query = "SELECT * FROM SALES_AMOUNT_BY_SHOP"
        + " WHERE SALES_AMOUNT_BY_SHOP_ID = ?";
    List<SalesAmountByShop> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してショップ別売上集計が既に存在するかどうかを返します。
   * @param salesAmountByShopId ショップ別売上集計ID
   * @return 主キー列の値に対応するSalesAmountByShopの行が存在すればtrue
   */
  public boolean exists(Long salesAmountByShopId) {
    Object[] params = new Object[]{salesAmountByShopId};
    final String query = "SELECT COUNT(*) FROM SALES_AMOUNT_BY_SHOP"
        + " WHERE SALES_AMOUNT_BY_SHOP_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規SalesAmountByShopをデータベースに追加します。
   * @param obj 追加対象のSalesAmountByShop
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SalesAmountByShop obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規SalesAmountByShopをデータベースに追加します。
   * @param obj 追加対象のSalesAmountByShop
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SalesAmountByShop obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ショップ別売上集計を更新します。
   * @param obj 更新対象のSalesAmountByShop
   */
  public void update(SalesAmountByShop obj) {
    genericDao.update(obj);
  }

  /**
   * ショップ別売上集計を更新します。
   * @param obj 更新対象のSalesAmountByShop
   */
  public void update(SalesAmountByShop obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ショップ別売上集計を削除します。
   * @param obj 削除対象のSalesAmountByShop
   */
  public void delete(SalesAmountByShop obj) {
    genericDao.delete(obj);
  }

  /**
   * ショップ別売上集計を削除します。
   * @param obj 削除対象のSalesAmountByShop
   */
  public void delete(SalesAmountByShop obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してショップ別売上集計を削除します。
   * @param salesAmountByShopId ショップ別売上集計ID
   */
  public void delete(Long salesAmountByShopId) {
    Object[] params = new Object[]{salesAmountByShopId};
    final String query = "DELETE FROM SALES_AMOUNT_BY_SHOP"
        + " WHERE SALES_AMOUNT_BY_SHOP_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してショップ別売上集計のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するSalesAmountByShopのリスト
   */
  public List<SalesAmountByShop> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してショップ別売上集計のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するSalesAmountByShopのリスト
   */
  public List<SalesAmountByShop> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のSalesAmountByShopのリスト
   */
  public List<SalesAmountByShop> loadAll() {
    return genericDao.loadAll();
  }

}
