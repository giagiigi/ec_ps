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
import jp.co.sint.webshop.data.dao.JdPrefectureDao;
import jp.co.sint.webshop.data.dto.JdPrefecture;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 都道府県
 *
 * @author System Integrator Corp.
 *
 */
public class JdPrefectureDaoImpl implements JdPrefectureDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<JdPrefecture, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public JdPrefectureDaoImpl() {
    genericDao = new GenericDaoImpl<JdPrefecture, Long>(JdPrefecture.class);
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
   * 指定されたorm_rowidを持つ都道府県のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPrefectureのインスタンス
   */
  public JdPrefecture loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して都道府県のインスタンスを取得します。
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するPrefectureのインスタンス
   */
  public JdPrefecture load(String prefectureCode) {
    Object[] params = new Object[]{prefectureCode};
    final String query = "SELECT * FROM JD_PREFECTURE"
        + " WHERE PREFECTURE_CODE = ?";
    List<JdPrefecture> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して都道府県が既に存在するかどうかを返します。
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するPrefectureの行が存在すればtrue
   */
  public boolean exists(String prefectureCode) {
    Object[] params = new Object[]{prefectureCode};
    final String query = "SELECT COUNT(*) FROM JD_PREFECTURE"
        + " WHERE PREFECTURE_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Prefectureをデータベースに追加します。
   * @param obj 追加対象のPrefecture
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdPrefecture obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Prefectureをデータベースに追加します。
   * @param obj 追加対象のPrefecture
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdPrefecture obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 都道府県を更新します。
   * @param obj 更新対象のPrefecture
   */
  public void update(JdPrefecture obj) {
    genericDao.update(obj);
  }

  /**
   * 都道府県を更新します。
   * @param obj 更新対象のPrefecture
   */
  public void update(JdPrefecture obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 都道府県を削除します。
   * @param obj 削除対象のPrefecture
   */
  public void delete(JdPrefecture obj) {
    genericDao.delete(obj);
  }

  /**
   * 都道府県を削除します。
   * @param obj 削除対象のPrefecture
   */
  public void delete(JdPrefecture obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して都道府県を削除します。
   * @param prefectureCode 都道府県コード
   */
  public void delete(String prefectureCode) {
    Object[] params = new Object[]{prefectureCode};
    final String query = "DELETE FROM JD_PREFECTURE"
        + " WHERE PREFECTURE_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して都道府県のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPrefectureのリスト
   */
  public List<JdPrefecture> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して都道府県のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPrefectureのリスト
   */
  public List<JdPrefecture> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPrefectureのリスト
   */
  public List<JdPrefecture> loadAll() {
    return genericDao.loadAll();
  }
  /**
   * 
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPrefectureのリスト
   */
  public List<JdPrefecture> loadAllByOrmRowid() {
    final String query = "SELECT * FROM JD_PREFECTURE ORDER BY ORM_ROWID DESC ";
    List<JdPrefecture> result = genericDao.findByQuery(query);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }
  }

}
