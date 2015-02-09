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
import jp.co.sint.webshop.data.dao.PopularRankingDetailDao;
import jp.co.sint.webshop.data.dto.PopularRankingDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 人気ランキング詳細
 *
 * @author System Integrator Corp.
 *
 */
public class PopularRankingDetailDaoImpl implements PopularRankingDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PopularRankingDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PopularRankingDetailDaoImpl() {
    genericDao = new GenericDaoImpl<PopularRankingDetail, Long>(PopularRankingDetail.class);
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
   * 指定されたorm_rowidを持つ人気ランキング詳細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPopularRankingDetailのインスタンス
   */
  public PopularRankingDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して人気ランキング詳細のインスタンスを取得します。
   * @param popularRankingCountId 人気ランキング集計ID
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するPopularRankingDetailのインスタンス
   */
  public PopularRankingDetail load(Long popularRankingCountId, String shopCode, String commodityCode) {
    Object[] params = new Object[]{popularRankingCountId, shopCode, commodityCode};
    final String query = "SELECT * FROM POPULAR_RANKING_DETAIL"
        + " WHERE POPULAR_RANKING_COUNT_ID = ?"
        + " AND SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<PopularRankingDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して人気ランキング詳細が既に存在するかどうかを返します。
   * @param popularRankingCountId 人気ランキング集計ID
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するPopularRankingDetailの行が存在すればtrue
   */
  public boolean exists(Long popularRankingCountId, String shopCode, String commodityCode) {
    Object[] params = new Object[]{popularRankingCountId, shopCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM POPULAR_RANKING_DETAIL"
        + " WHERE POPULAR_RANKING_COUNT_ID = ?"
        + " AND SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PopularRankingDetailをデータベースに追加します。
   * @param obj 追加対象のPopularRankingDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PopularRankingDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PopularRankingDetailをデータベースに追加します。
   * @param obj 追加対象のPopularRankingDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PopularRankingDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 人気ランキング詳細を更新します。
   * @param obj 更新対象のPopularRankingDetail
   */
  public void update(PopularRankingDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 人気ランキング詳細を更新します。
   * @param obj 更新対象のPopularRankingDetail
   */
  public void update(PopularRankingDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 人気ランキング詳細を削除します。
   * @param obj 削除対象のPopularRankingDetail
   */
  public void delete(PopularRankingDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 人気ランキング詳細を削除します。
   * @param obj 削除対象のPopularRankingDetail
   */
  public void delete(PopularRankingDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して人気ランキング詳細を削除します。
   * @param popularRankingCountId 人気ランキング集計ID
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   */
  public void delete(Long popularRankingCountId, String shopCode, String commodityCode) {
    Object[] params = new Object[]{popularRankingCountId, shopCode, commodityCode};
    final String query = "DELETE FROM POPULAR_RANKING_DETAIL"
        + " WHERE POPULAR_RANKING_COUNT_ID = ?"
        + " AND SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して人気ランキング詳細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPopularRankingDetailのリスト
   */
  public List<PopularRankingDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して人気ランキング詳細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPopularRankingDetailのリスト
   */
  public List<PopularRankingDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPopularRankingDetailのリスト
   */
  public List<PopularRankingDetail> loadAll() {
    return genericDao.loadAll();
  }

}
