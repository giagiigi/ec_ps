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
import jp.co.sint.webshop.data.dao.MailTemplateHeaderDao;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * メールテンプレートヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class MailTemplateHeaderDaoImpl implements MailTemplateHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<MailTemplateHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public MailTemplateHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<MailTemplateHeader, Long>(MailTemplateHeader.class);
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
   * 指定されたorm_rowidを持つメールテンプレートヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するMailTemplateHeaderのインスタンス
   */
  public MailTemplateHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してメールテンプレートヘッダのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @return 主キー列の値に対応するMailTemplateHeaderのインスタンス
   */
  public MailTemplateHeader load(String shopCode, String mailType, Long mailTemplateNo) {
    Object[] params = new Object[]{shopCode, mailType, mailTemplateNo};
    final String query = "SELECT * FROM MAIL_TEMPLATE_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND MAIL_TYPE = ?"
        + " AND MAIL_TEMPLATE_NO = ?";
    List<MailTemplateHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してメールテンプレートヘッダが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @return 主キー列の値に対応するMailTemplateHeaderの行が存在すればtrue
   */
  public boolean exists(String shopCode, String mailType, Long mailTemplateNo) {
    Object[] params = new Object[]{shopCode, mailType, mailTemplateNo};
    final String query = "SELECT COUNT(*) FROM MAIL_TEMPLATE_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND MAIL_TYPE = ?"
        + " AND MAIL_TEMPLATE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規MailTemplateHeaderをデータベースに追加します。
   * @param obj 追加対象のMailTemplateHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailTemplateHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規MailTemplateHeaderをデータベースに追加します。
   * @param obj 追加対象のMailTemplateHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailTemplateHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * メールテンプレートヘッダを更新します。
   * @param obj 更新対象のMailTemplateHeader
   */
  public void update(MailTemplateHeader obj) {
    genericDao.update(obj);
  }

  /**
   * メールテンプレートヘッダを更新します。
   * @param obj 更新対象のMailTemplateHeader
   */
  public void update(MailTemplateHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * メールテンプレートヘッダを削除します。
   * @param obj 削除対象のMailTemplateHeader
   */
  public void delete(MailTemplateHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * メールテンプレートヘッダを削除します。
   * @param obj 削除対象のMailTemplateHeader
   */
  public void delete(MailTemplateHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してメールテンプレートヘッダを削除します。
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   */
  public void delete(String shopCode, String mailType, Long mailTemplateNo) {
    Object[] params = new Object[]{shopCode, mailType, mailTemplateNo};
    final String query = "DELETE FROM MAIL_TEMPLATE_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND MAIL_TYPE = ?"
        + " AND MAIL_TEMPLATE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してメールテンプレートヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailTemplateHeaderのリスト
   */
  public List<MailTemplateHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してメールテンプレートヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailTemplateHeaderのリスト
   */
  public List<MailTemplateHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のMailTemplateHeaderのリスト
   */
  public List<MailTemplateHeader> loadAll() {
    return genericDao.loadAll();
  }

}
