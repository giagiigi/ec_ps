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
import jp.co.sint.webshop.data.dao.RecommendedCommodityDao;
import jp.co.sint.webshop.data.dto.RecommendedCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 個別リコメンド
 *
 * @author System Integrator Corp.
 *
 */
public class RecommendedCommodityDaoImpl implements RecommendedCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<RecommendedCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RecommendedCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<RecommendedCommodity, Long>(RecommendedCommodity.class);
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
   * 指定されたorm_rowidを持つ個別リコメンドのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRecommendedCommodityのインスタンス
   */
  public RecommendedCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して個別リコメンドのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するRecommendedCommodityのインスタンス
   */
  public RecommendedCommodity load(String shopCode, String commodityCode, String customerCode) {
    Object[] params = new Object[]{shopCode, commodityCode, customerCode};
    final String query = "SELECT * FROM RECOMMENDED_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND CUSTOMER_CODE = ?";
    List<RecommendedCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して個別リコメンドが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するRecommendedCommodityの行が存在すればtrue
   */
  public boolean exists(String shopCode, String commodityCode, String customerCode) {
    Object[] params = new Object[]{shopCode, commodityCode, customerCode};
    final String query = "SELECT COUNT(*) FROM RECOMMENDED_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND CUSTOMER_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規RecommendedCommodityをデータベースに追加します。
   * @param obj 追加対象のRecommendedCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RecommendedCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規RecommendedCommodityをデータベースに追加します。
   * @param obj 追加対象のRecommendedCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RecommendedCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 個別リコメンドを更新します。
   * @param obj 更新対象のRecommendedCommodity
   */
  public void update(RecommendedCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * 個別リコメンドを更新します。
   * @param obj 更新対象のRecommendedCommodity
   */
  public void update(RecommendedCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 個別リコメンドを削除します。
   * @param obj 削除対象のRecommendedCommodity
   */
  public void delete(RecommendedCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * 個別リコメンドを削除します。
   * @param obj 削除対象のRecommendedCommodity
   */
  public void delete(RecommendedCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して個別リコメンドを削除します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param customerCode 顧客コード
   */
  public void delete(String shopCode, String commodityCode, String customerCode) {
    Object[] params = new Object[]{shopCode, commodityCode, customerCode};
    final String query = "DELETE FROM RECOMMENDED_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND CUSTOMER_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して個別リコメンドのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRecommendedCommodityのリスト
   */
  public List<RecommendedCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して個別リコメンドのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRecommendedCommodityのリスト
   */
  public List<RecommendedCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRecommendedCommodityのリスト
   */
  public List<RecommendedCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
