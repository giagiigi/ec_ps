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
import jp.co.sint.webshop.data.dao.SalesAmountBySkuDao;
import jp.co.sint.webshop.data.dto.SalesAmountBySku;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * SKU別売上集計
 *
 * @author System Integrator Corp.
 *
 */
public class SalesAmountBySkuDaoImpl implements SalesAmountBySkuDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<SalesAmountBySku, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public SalesAmountBySkuDaoImpl() {
    genericDao = new GenericDaoImpl<SalesAmountBySku, Long>(SalesAmountBySku.class);
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
   * 指定されたorm_rowidを持つSKU別売上集計のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するSalesAmountBySkuのインスタンス
   */
  public SalesAmountBySku loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してSKU別売上集計のインスタンスを取得します。
   * @param salesAmountBySkuId SKU別売上集計ID
   * @return 主キー列の値に対応するSalesAmountBySkuのインスタンス
   */
  public SalesAmountBySku load(Long salesAmountBySkuId) {
    Object[] params = new Object[]{salesAmountBySkuId};
    final String query = "SELECT * FROM SALES_AMOUNT_BY_SKU"
        + " WHERE SALES_AMOUNT_BY_SKU_ID = ?";
    List<SalesAmountBySku> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してSKU別売上集計が既に存在するかどうかを返します。
   * @param salesAmountBySkuId SKU別売上集計ID
   * @return 主キー列の値に対応するSalesAmountBySkuの行が存在すればtrue
   */
  public boolean exists(Long salesAmountBySkuId) {
    Object[] params = new Object[]{salesAmountBySkuId};
    final String query = "SELECT COUNT(*) FROM SALES_AMOUNT_BY_SKU"
        + " WHERE SALES_AMOUNT_BY_SKU_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規SalesAmountBySkuをデータベースに追加します。
   * @param obj 追加対象のSalesAmountBySku
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SalesAmountBySku obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規SalesAmountBySkuをデータベースに追加します。
   * @param obj 追加対象のSalesAmountBySku
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SalesAmountBySku obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * SKU別売上集計を更新します。
   * @param obj 更新対象のSalesAmountBySku
   */
  public void update(SalesAmountBySku obj) {
    genericDao.update(obj);
  }

  /**
   * SKU別売上集計を更新します。
   * @param obj 更新対象のSalesAmountBySku
   */
  public void update(SalesAmountBySku obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * SKU別売上集計を削除します。
   * @param obj 削除対象のSalesAmountBySku
   */
  public void delete(SalesAmountBySku obj) {
    genericDao.delete(obj);
  }

  /**
   * SKU別売上集計を削除します。
   * @param obj 削除対象のSalesAmountBySku
   */
  public void delete(SalesAmountBySku obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してSKU別売上集計を削除します。
   * @param salesAmountBySkuId SKU別売上集計ID
   */
  public void delete(Long salesAmountBySkuId) {
    Object[] params = new Object[]{salesAmountBySkuId};
    final String query = "DELETE FROM SALES_AMOUNT_BY_SKU"
        + " WHERE SALES_AMOUNT_BY_SKU_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してSKU別売上集計のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するSalesAmountBySkuのリスト
   */
  public List<SalesAmountBySku> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してSKU別売上集計のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するSalesAmountBySkuのリスト
   */
  public List<SalesAmountBySku> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のSalesAmountBySkuのリスト
   */
  public List<SalesAmountBySku> loadAll() {
    return genericDao.loadAll();
  }

}
