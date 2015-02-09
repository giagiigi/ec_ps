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
import jp.co.sint.webshop.data.dao.EnqueteDao;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * アンケート
 *
 * @author System Integrator Corp.
 *
 */
public class EnqueteDaoImpl implements EnqueteDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Enquete, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public EnqueteDaoImpl() {
    genericDao = new GenericDaoImpl<Enquete, Long>(Enquete.class);
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
   * 指定されたorm_rowidを持つアンケートのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteのインスタンス
   */
  public Enquete loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してアンケートのインスタンスを取得します。
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteのインスタンス
   */
  public Enquete load(String enqueteCode) {
    Object[] params = new Object[]{enqueteCode};
    final String query = "SELECT * FROM ENQUETE"
        + " WHERE ENQUETE_CODE = ?";
    List<Enquete> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してアンケートが既に存在するかどうかを返します。
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteの行が存在すればtrue
   */
  public boolean exists(String enqueteCode) {
    Object[] params = new Object[]{enqueteCode};
    final String query = "SELECT COUNT(*) FROM ENQUETE"
        + " WHERE ENQUETE_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Enqueteをデータベースに追加します。
   * @param obj 追加対象のEnquete
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Enquete obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Enqueteをデータベースに追加します。
   * @param obj 追加対象のEnquete
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Enquete obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * アンケートを更新します。
   * @param obj 更新対象のEnquete
   */
  public void update(Enquete obj) {
    genericDao.update(obj);
  }

  /**
   * アンケートを更新します。
   * @param obj 更新対象のEnquete
   */
  public void update(Enquete obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * アンケートを削除します。
   * @param obj 削除対象のEnquete
   */
  public void delete(Enquete obj) {
    genericDao.delete(obj);
  }

  /**
   * アンケートを削除します。
   * @param obj 削除対象のEnquete
   */
  public void delete(Enquete obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してアンケートを削除します。
   * @param enqueteCode アンケートコード
   */
  public void delete(String enqueteCode) {
    Object[] params = new Object[]{enqueteCode};
    final String query = "DELETE FROM ENQUETE"
        + " WHERE ENQUETE_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してアンケートのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteのリスト
   */
  public List<Enquete> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してアンケートのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteのリスト
   */
  public List<Enquete> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のEnqueteのリスト
   */
  public List<Enquete> loadAll() {
    return genericDao.loadAll();
  }

}
