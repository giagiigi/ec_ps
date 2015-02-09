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
import jp.co.sint.webshop.data.dao.GoogleAnalysisDao;
import jp.co.sint.webshop.data.dto.GoogleAnalysis;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 *
 * @author System Integrator Corp.
 *
 */
public class GoogleAnalysisDaoImpl implements GoogleAnalysisDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<GoogleAnalysis, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public GoogleAnalysisDaoImpl() {
    genericDao = new GenericDaoImpl<GoogleAnalysis, Long>(GoogleAnalysis.class);
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
   * 指定されたorm_rowidを持つGoogleAnalysisのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するGoogleAnalysisのインスタンス
   */
  public GoogleAnalysis loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してGoogleAnalysisのインスタンスを取得します。
   * @param GoogleAnalysisNo
   * @return 主キー列の値に対応するGoogleAnalysisのインスタンス
   */
  public GoogleAnalysis load(Long GoogleAnalysisNo) {
    Object[] params = new Object[]{GoogleAnalysisNo};
    final String query = "SELECT * FROM GOOGLE_ANALYSIS"
        + " WHERE GOOGLE_ANALYSIS_NO = ?";
    List<GoogleAnalysis> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   * @param GoogleAnalysis番号
   * @return 主キー列の値に対応するGoogleAnalysisの行が存在すればtrue
   */
  public boolean exists(Long GoogleAnalysisNo) {
    Object[] params = new Object[]{GoogleAnalysisNo};
    final String query = "SELECT COUNT(*) FROM GOOGLE_ANALYSIS"
        + " WHERE GOOGLE_ANALYSIS_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規GoogleAnalysisをデータベースに追加します。
   * @param obj 追加対象のGoogleAnalysis
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GoogleAnalysis obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規GoogleAnalysisをデータベースに追加します。
   * @param obj 追加対象のGoogleAnalysis
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GoogleAnalysis obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * GoogleAnalysisを更新します。
   * @param obj 更新対象のGoogleAnalysis
   */
  public void update(GoogleAnalysis obj) {
    genericDao.update(obj);
  }

  /**
   * GoogleAnalysisを更新します。
   * @param obj 更新対象のGoogleAnalysis
   */
  public void update(GoogleAnalysis obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * GoogleAnalysisを削除します。
   * @param obj 削除対象のGoogleAnalysis
   */
  public void delete(GoogleAnalysis obj) {
    genericDao.delete(obj);
  }

  /**
   * GoogleAnalysisを削除します。
   * @param obj 削除対象のGoogleAnalysis
   */
  public void delete(GoogleAnalysis obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してGoogleAnalysisを削除します。
   * @param GoogleAnalysis番号
   */
  public void delete(Long GoogleAnalysisNo) {
    Object[] params = new Object[]{GoogleAnalysisNo};
    final String query = "DELETE FROM GOOGLE_ANALYSIS"
        + " WHERE GOOGLE_ANALYSIS_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してGoogleAnalysisのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するGoogleAnalysisのリスト
   */
  public List<GoogleAnalysis> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してGoogleAnalysisのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するGoogleAnalysisのリスト
   */
  public List<GoogleAnalysis> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のGoogleAnalysisのリスト
   */
  public List<GoogleAnalysis> loadAll() {
    return genericDao.loadAll();
  }

}
