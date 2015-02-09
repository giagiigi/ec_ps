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
import jp.co.sint.webshop.data.dao.PopularRankingHeaderDao;
import jp.co.sint.webshop.data.dto.PopularRankingHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 人気ランキングヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class PopularRankingHeaderDaoImpl implements PopularRankingHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PopularRankingHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PopularRankingHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<PopularRankingHeader, Long>(PopularRankingHeader.class);
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
   * 指定されたorm_rowidを持つ人気ランキングヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPopularRankingHeaderのインスタンス
   */
  public PopularRankingHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して人気ランキングヘッダのインスタンスを取得します。
   * @param popularRankingCountId 人気ランキング集計ID
   * @return 主キー列の値に対応するPopularRankingHeaderのインスタンス
   */
  public PopularRankingHeader load(Long popularRankingCountId) {
    Object[] params = new Object[]{popularRankingCountId};
    final String query = "SELECT * FROM POPULAR_RANKING_HEADER"
        + " WHERE POPULAR_RANKING_COUNT_ID = ?";
    List<PopularRankingHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して人気ランキングヘッダが既に存在するかどうかを返します。
   * @param popularRankingCountId 人気ランキング集計ID
   * @return 主キー列の値に対応するPopularRankingHeaderの行が存在すればtrue
   */
  public boolean exists(Long popularRankingCountId) {
    Object[] params = new Object[]{popularRankingCountId};
    final String query = "SELECT COUNT(*) FROM POPULAR_RANKING_HEADER"
        + " WHERE POPULAR_RANKING_COUNT_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PopularRankingHeaderをデータベースに追加します。
   * @param obj 追加対象のPopularRankingHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PopularRankingHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PopularRankingHeaderをデータベースに追加します。
   * @param obj 追加対象のPopularRankingHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PopularRankingHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 人気ランキングヘッダを更新します。
   * @param obj 更新対象のPopularRankingHeader
   */
  public void update(PopularRankingHeader obj) {
    genericDao.update(obj);
  }

  /**
   * 人気ランキングヘッダを更新します。
   * @param obj 更新対象のPopularRankingHeader
   */
  public void update(PopularRankingHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 人気ランキングヘッダを削除します。
   * @param obj 削除対象のPopularRankingHeader
   */
  public void delete(PopularRankingHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * 人気ランキングヘッダを削除します。
   * @param obj 削除対象のPopularRankingHeader
   */
  public void delete(PopularRankingHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して人気ランキングヘッダを削除します。
   * @param popularRankingCountId 人気ランキング集計ID
   */
  public void delete(Long popularRankingCountId) {
    Object[] params = new Object[]{popularRankingCountId};
    final String query = "DELETE FROM POPULAR_RANKING_HEADER"
        + " WHERE POPULAR_RANKING_COUNT_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して人気ランキングヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPopularRankingHeaderのリスト
   */
  public List<PopularRankingHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して人気ランキングヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPopularRankingHeaderのリスト
   */
  public List<PopularRankingHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPopularRankingHeaderのリスト
   */
  public List<PopularRankingHeader> loadAll() {
    return genericDao.loadAll();
  }

}
