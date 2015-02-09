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
import jp.co.sint.webshop.data.dao.SearchKeywordLogDao;
import jp.co.sint.webshop.data.dto.SearchKeywordLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 検索キーワードログ
 *
 * @author System Integrator Corp.
 *
 */
public class SearchKeywordLogDaoImpl implements SearchKeywordLogDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<SearchKeywordLog, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public SearchKeywordLogDaoImpl() {
    genericDao = new GenericDaoImpl<SearchKeywordLog, Long>(SearchKeywordLog.class);
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
   * 指定されたorm_rowidを持つ検索キーワードログのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するSearchKeywordLogのインスタンス
   */
  public SearchKeywordLog loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して検索キーワードログのインスタンスを取得します。
   * @param searchKeywordLogId 検索キーワードログID
   * @return 主キー列の値に対応するSearchKeywordLogのインスタンス
   */
  public SearchKeywordLog load(Long searchKeywordLogId) {
    Object[] params = new Object[]{searchKeywordLogId};
    final String query = "SELECT * FROM SEARCH_KEYWORD_LOG"
        + " WHERE SEARCH_KEYWORD_LOG_ID = ?";
    List<SearchKeywordLog> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して検索キーワードログが既に存在するかどうかを返します。
   * @param searchKeywordLogId 検索キーワードログID
   * @return 主キー列の値に対応するSearchKeywordLogの行が存在すればtrue
   */
  public boolean exists(Long searchKeywordLogId) {
    Object[] params = new Object[]{searchKeywordLogId};
    final String query = "SELECT COUNT(*) FROM SEARCH_KEYWORD_LOG"
        + " WHERE SEARCH_KEYWORD_LOG_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規SearchKeywordLogをデータベースに追加します。
   * @param obj 追加対象のSearchKeywordLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SearchKeywordLog obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規SearchKeywordLogをデータベースに追加します。
   * @param obj 追加対象のSearchKeywordLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SearchKeywordLog obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 検索キーワードログを更新します。
   * @param obj 更新対象のSearchKeywordLog
   */
  public void update(SearchKeywordLog obj) {
    genericDao.update(obj);
  }

  /**
   * 検索キーワードログを更新します。
   * @param obj 更新対象のSearchKeywordLog
   */
  public void update(SearchKeywordLog obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 検索キーワードログを削除します。
   * @param obj 削除対象のSearchKeywordLog
   */
  public void delete(SearchKeywordLog obj) {
    genericDao.delete(obj);
  }

  /**
   * 検索キーワードログを削除します。
   * @param obj 削除対象のSearchKeywordLog
   */
  public void delete(SearchKeywordLog obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して検索キーワードログを削除します。
   * @param searchKeywordLogId 検索キーワードログID
   */
  public void delete(Long searchKeywordLogId) {
    Object[] params = new Object[]{searchKeywordLogId};
    final String query = "DELETE FROM SEARCH_KEYWORD_LOG"
        + " WHERE SEARCH_KEYWORD_LOG_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して検索キーワードログのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するSearchKeywordLogのリスト
   */
  public List<SearchKeywordLog> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して検索キーワードログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するSearchKeywordLogのリスト
   */
  public List<SearchKeywordLog> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のSearchKeywordLogのリスト
   */
  public List<SearchKeywordLog> loadAll() {
    return genericDao.loadAll();
  }

}
