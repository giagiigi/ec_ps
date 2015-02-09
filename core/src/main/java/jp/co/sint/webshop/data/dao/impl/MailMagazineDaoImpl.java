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
import jp.co.sint.webshop.data.dao.MailMagazineDao;
import jp.co.sint.webshop.data.dto.MailMagazine;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * メールマガジン
 *
 * @author System Integrator Corp.
 *
 */
public class MailMagazineDaoImpl implements MailMagazineDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<MailMagazine, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public MailMagazineDaoImpl() {
    genericDao = new GenericDaoImpl<MailMagazine, Long>(MailMagazine.class);
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
   * 指定されたorm_rowidを持つメールマガジンのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するMailMagazineのインスタンス
   */
  public MailMagazine loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してメールマガジンのインスタンスを取得します。
   * @param mailMagazineCode メールマガジンコード
   * @return 主キー列の値に対応するMailMagazineのインスタンス
   */
  public MailMagazine load(String mailMagazineCode) {
    Object[] params = new Object[]{mailMagazineCode};
    final String query = "SELECT * FROM MAIL_MAGAZINE"
        + " WHERE MAIL_MAGAZINE_CODE = ?";
    List<MailMagazine> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してメールマガジンが既に存在するかどうかを返します。
   * @param mailMagazineCode メールマガジンコード
   * @return 主キー列の値に対応するMailMagazineの行が存在すればtrue
   */
  public boolean exists(String mailMagazineCode) {
    Object[] params = new Object[]{mailMagazineCode};
    final String query = "SELECT COUNT(*) FROM MAIL_MAGAZINE"
        + " WHERE MAIL_MAGAZINE_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規MailMagazineをデータベースに追加します。
   * @param obj 追加対象のMailMagazine
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailMagazine obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規MailMagazineをデータベースに追加します。
   * @param obj 追加対象のMailMagazine
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailMagazine obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * メールマガジンを更新します。
   * @param obj 更新対象のMailMagazine
   */
  public void update(MailMagazine obj) {
    genericDao.update(obj);
  }

  /**
   * メールマガジンを更新します。
   * @param obj 更新対象のMailMagazine
   */
  public void update(MailMagazine obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * メールマガジンを削除します。
   * @param obj 削除対象のMailMagazine
   */
  public void delete(MailMagazine obj) {
    genericDao.delete(obj);
  }

  /**
   * メールマガジンを削除します。
   * @param obj 削除対象のMailMagazine
   */
  public void delete(MailMagazine obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してメールマガジンを削除します。
   * @param mailMagazineCode メールマガジンコード
   */
  public void delete(String mailMagazineCode) {
    Object[] params = new Object[]{mailMagazineCode};
    final String query = "DELETE FROM MAIL_MAGAZINE"
        + " WHERE MAIL_MAGAZINE_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してメールマガジンのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailMagazineのリスト
   */
  public List<MailMagazine> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してメールマガジンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailMagazineのリスト
   */
  public List<MailMagazine> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のMailMagazineのリスト
   */
  public List<MailMagazine> loadAll() {
    return genericDao.loadAll();
  }

}
