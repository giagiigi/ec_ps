//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.CommodityPreviousDayDao;
import jp.co.sint.webshop.data.dto.CommodityPreviousDay;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 前一天临近有效期商品
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityPreviousDayDaoImpl implements CommodityPreviousDayDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CommodityPreviousDay, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommodityPreviousDayDaoImpl() {
    genericDao = new GenericDaoImpl<CommodityPreviousDay, Long>(CommodityPreviousDay.class);
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
   * 指定されたorm_rowidを持つ前一天临近有效期商品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityPreviousDayのインスタンス
   */
  public CommodityPreviousDay loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して前一天临近有效期商品のインスタンスを取得します。
   * @param commodityCode 前一天临近有效期商品コード
   * @return 主キー列の値に対応するCommodityPreviousDayのインスタンス
   */
  public CommodityPreviousDay load(String commodityCode) {
    Object[] params = new Object[]{commodityCode};
    final String query = "SELECT * FROM COMMODITY_PREVIOUS_DAY"
        + " WHERE COMMODITY_CODE = ?";
    List<CommodityPreviousDay> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して前一天临近有效期商品が既に存在するかどうかを返します。
   * @param commodityCode 前一天临近有效期商品コード
   * @return 主キー列の値に対応するCommodityPreviousDayの行が存在すればtrue
   */
  public boolean exists(String commodityCode) {
    Object[] params = new Object[]{commodityCode};
    final String query = "SELECT COUNT(*) FROM COMMODITY_PREVIOUS_DAY"
        + " WHERE COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityPreviousDayをデータベースに追加します。
   * @param obj 追加対象のCommodityPreviousDay
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityPreviousDay obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityPreviousDayをデータベースに追加します。
   * @param obj 追加対象のCommodityPreviousDay
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityPreviousDay obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 前一天临近有效期商品を更新します。
   * @param obj 更新対象のCommodityPreviousDay
   */
  public void update(CommodityPreviousDay obj) {
    genericDao.update(obj);
  }

  /**
   * 前一天临近有效期商品を更新します。
   * @param obj 更新対象のCommodityPreviousDay
   */
  public void update(CommodityPreviousDay obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 前一天临近有效期商品を削除します。
   * @param obj 削除対象のCommodityPreviousDay
   */
  public void delete(CommodityPreviousDay obj) {
    genericDao.delete(obj);
  }

  /**
   * 前一天临近有效期商品を削除します。
   * @param obj 削除対象のCommodityPreviousDay
   */
  public void delete(CommodityPreviousDay obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して前一天临近有效期商品を削除します。
   * @param commodityCode 前一天临近有效期商品コード
   */
  public void delete(String commodityCode) {
    Object[] params = new Object[]{commodityCode};
    final String query = "DELETE FROM COMMODITY_PREVIOUS_DAY"
      + " WHERE COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して前一天临近有效期商品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityPreviousDayのリスト
   */
  public List<CommodityPreviousDay> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して前一天临近有效期商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityPreviousDayのリスト
   */
  public List<CommodityPreviousDay> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommodityPreviousDayのリスト
   */
  public List<CommodityPreviousDay> loadAll() {
    return genericDao.loadAll();
  }
  
  /**
   * テーブルの全データを一括で削除します。
   */
  public void deleteAll() {
    final String query = "TRUNCATE TABLE COMMODITY_PREVIOUS_DAY";
    genericDao.updateByQuery(query);
  }

}
