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
import jp.co.sint.webshop.data.dao.MailTemplateDetailDao;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * メールテンプレート明細
 *
 * @author System Integrator Corp.
 *
 */
public class MailTemplateDetailDaoImpl implements MailTemplateDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<MailTemplateDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public MailTemplateDetailDaoImpl() {
    genericDao = new GenericDaoImpl<MailTemplateDetail, Long>(MailTemplateDetail.class);
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
   * 指定されたorm_rowidを持つメールテンプレート明細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するMailTemplateDetailのインスタンス
   */
  public MailTemplateDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してメールテンプレート明細のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @param mailTemplateBranchNo メールテンプレート枝番
   * @return 主キー列の値に対応するMailTemplateDetailのインスタンス
   */
  public MailTemplateDetail load(String shopCode, String mailType, Long mailTemplateNo, Long mailTemplateBranchNo) {
    Object[] params = new Object[]{shopCode, mailType, mailTemplateNo, mailTemplateBranchNo};
    final String query = "SELECT * FROM MAIL_TEMPLATE_DETAIL"
        + " WHERE SHOP_CODE = ?"
        + " AND MAIL_TYPE = ?"
        + " AND MAIL_TEMPLATE_NO = ?"
        + " AND MAIL_TEMPLATE_BRANCH_NO = ?";
    List<MailTemplateDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してメールテンプレート明細が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @param mailTemplateBranchNo メールテンプレート枝番
   * @return 主キー列の値に対応するMailTemplateDetailの行が存在すればtrue
   */
  public boolean exists(String shopCode, String mailType, Long mailTemplateNo, Long mailTemplateBranchNo) {
    Object[] params = new Object[]{shopCode, mailType, mailTemplateNo, mailTemplateBranchNo};
    final String query = "SELECT COUNT(*) FROM MAIL_TEMPLATE_DETAIL"
        + " WHERE SHOP_CODE = ?"
        + " AND MAIL_TYPE = ?"
        + " AND MAIL_TEMPLATE_NO = ?"
        + " AND MAIL_TEMPLATE_BRANCH_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規MailTemplateDetailをデータベースに追加します。
   * @param obj 追加対象のMailTemplateDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailTemplateDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規MailTemplateDetailをデータベースに追加します。
   * @param obj 追加対象のMailTemplateDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(MailTemplateDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * メールテンプレート明細を更新します。
   * @param obj 更新対象のMailTemplateDetail
   */
  public void update(MailTemplateDetail obj) {
    genericDao.update(obj);
  }

  /**
   * メールテンプレート明細を更新します。
   * @param obj 更新対象のMailTemplateDetail
   */
  public void update(MailTemplateDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * メールテンプレート明細を削除します。
   * @param obj 削除対象のMailTemplateDetail
   */
  public void delete(MailTemplateDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * メールテンプレート明細を削除します。
   * @param obj 削除対象のMailTemplateDetail
   */
  public void delete(MailTemplateDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してメールテンプレート明細を削除します。
   * @param shopCode ショップコード
   * @param mailType メールタイプ
   * @param mailTemplateNo メールテンプレート番号
   * @param mailTemplateBranchNo メールテンプレート枝番
   */
  public void delete(String shopCode, String mailType, Long mailTemplateNo, Long mailTemplateBranchNo) {
    Object[] params = new Object[]{shopCode, mailType, mailTemplateNo, mailTemplateBranchNo};
    final String query = "DELETE FROM MAIL_TEMPLATE_DETAIL"
        + " WHERE SHOP_CODE = ?"
        + " AND MAIL_TYPE = ?"
        + " AND MAIL_TEMPLATE_NO = ?"
        + " AND MAIL_TEMPLATE_BRANCH_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してメールテンプレート明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するMailTemplateDetailのリスト
   */
  public List<MailTemplateDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してメールテンプレート明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するMailTemplateDetailのリスト
   */
  public List<MailTemplateDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のMailTemplateDetailのリスト
   */
  public List<MailTemplateDetail> loadAll() {
    return genericDao.loadAll();
  }

}
