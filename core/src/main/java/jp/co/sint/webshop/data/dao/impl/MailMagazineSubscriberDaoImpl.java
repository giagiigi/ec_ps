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
import jp.co.sint.webshop.data.dao.MailMagazineSubscriberDao;
import jp.co.sint.webshop.data.dto.MailMagazineSubscriber;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * メールマガジン購読者
 *
 * @author System Integrator Corp.
 *
 */
public class MailMagazineSubscriberDaoImpl implements MailMagazineSubscriberDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<MailMagazineSubscriber, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public MailMagazineSubscriberDaoImpl() {
    genericDao = new GenericDaoImpl<MailMagazineSubscriber, Long>(MailMagazineSubscriber.class);
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
   * 指定されたorm_rowidを持つメールマガジン購読者のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するMailMagazineSubscriberのインスタンス
   */
  public MailMagazineSubscriber loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してメールマガジン購読者のインスタンスを取得します。
   * @param mailMagazineCode メールマガジンコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するMailMagazineSubscriberのインスタンス
   */
  public MailMagazineSubscriber load(String mailMagazineCode, String email) {
    Object[] params = new Object[]{mailMagazineCode, email};
    final String query = "SELECT * FROM MAIL_MAGAZINE_SUBSCRIBER"
        + " WHERE MAIL_MAGAZINE_CODE = ?"
        + " AND EMAIL = ?";
    List<MailMagazineSubscriber> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してメールマガジン購読者が既に存在するかどうかを返します。
   * @param mailMagazineCode メールマガジンコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するMailMagazineSubscriberの行が存在すればtrue
   */
  public boolean exists(String mailMagazineCode, String email) {
    Object[] params = new Object[]{mailMagazineCode, email};
    final String query = "SELECT COUNT(*) FROM MAIL_MAGAZINE_SUBSCRIBER"
        + " WHERE MAIL_MAGAZINE_CODE = ?"
        + " AND EMAIL = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規MailMagazineSubscriberをデータベースに追加します。
   * @param obj 追加対象のMailMagazineSubscriber
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailMagazineSubscriber obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規MailMagazineSubscriberをデータベースに追加します。
   * @param obj 追加対象のMailMagazineSubscriber
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailMagazineSubscriber obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * メールマガジン購読者を更新します。
   * @param obj 更新対象のMailMagazineSubscriber
   */
  public void update(MailMagazineSubscriber obj) {
    genericDao.update(obj);
  }

  /**
   * メールマガジン購読者を更新します。
   * @param obj 更新対象のMailMagazineSubscriber
   */
  public void update(MailMagazineSubscriber obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * メールマガジン購読者を削除します。
   * @param obj 削除対象のMailMagazineSubscriber
   */
  public void delete(MailMagazineSubscriber obj) {
    genericDao.delete(obj);
  }

  /**
   * メールマガジン購読者を削除します。
   * @param obj 削除対象のMailMagazineSubscriber
   */
  public void delete(MailMagazineSubscriber obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してメールマガジン購読者を削除します。
   * @param mailMagazineCode メールマガジンコード
   * @param email メールアドレス
   */
  public void delete(String mailMagazineCode, String email) {
    Object[] params = new Object[]{mailMagazineCode, email};
    final String query = "DELETE FROM MAIL_MAGAZINE_SUBSCRIBER"
        + " WHERE MAIL_MAGAZINE_CODE = ?"
        + " AND EMAIL = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してメールマガジン購読者のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailMagazineSubscriberのリスト
   */
  public List<MailMagazineSubscriber> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してメールマガジン購読者のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailMagazineSubscriberのリスト
   */
  public List<MailMagazineSubscriber> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のMailMagazineSubscriberのリスト
   */
  public List<MailMagazineSubscriber> loadAll() {
    return genericDao.loadAll();
  }

}
