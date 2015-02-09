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
import jp.co.sint.webshop.data.dao.EnqueteReplyChoicesDao;
import jp.co.sint.webshop.data.dto.EnqueteReplyChoices;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * アンケート回答選択式
 *
 * @author System Integrator Corp.
 *
 */
public class EnqueteReplyChoicesDaoImpl implements EnqueteReplyChoicesDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<EnqueteReplyChoices, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public EnqueteReplyChoicesDaoImpl() {
    genericDao = new GenericDaoImpl<EnqueteReplyChoices, Long>(EnqueteReplyChoices.class);
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
   * 指定されたorm_rowidを持つアンケート回答選択式のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteReplyChoicesのインスタンス
   */
  public EnqueteReplyChoices loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してアンケート回答選択式のインスタンスを取得します。
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteReplyChoicesのインスタンス
   */
  public EnqueteReplyChoices load(String customerCode, String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo) {
    Object[] params = new Object[]{customerCode, enqueteCode, enqueteQuestionNo, enqueteChoicesNo};
    final String query = "SELECT * FROM ENQUETE_REPLY_CHOICES"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND ENQUETE_CHOICES_NO = ?";
    List<EnqueteReplyChoices> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してアンケート回答選択式が既に存在するかどうかを返します。
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteReplyChoicesの行が存在すればtrue
   */
  public boolean exists(String customerCode, String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo) {
    Object[] params = new Object[]{customerCode, enqueteCode, enqueteQuestionNo, enqueteChoicesNo};
    final String query = "SELECT COUNT(*) FROM ENQUETE_REPLY_CHOICES"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND ENQUETE_CHOICES_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規EnqueteReplyChoicesをデータベースに追加します。
   * @param obj 追加対象のEnqueteReplyChoices
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteReplyChoices obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規EnqueteReplyChoicesをデータベースに追加します。
   * @param obj 追加対象のEnqueteReplyChoices
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteReplyChoices obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * アンケート回答選択式を更新します。
   * @param obj 更新対象のEnqueteReplyChoices
   */
  public void update(EnqueteReplyChoices obj) {
    genericDao.update(obj);
  }

  /**
   * アンケート回答選択式を更新します。
   * @param obj 更新対象のEnqueteReplyChoices
   */
  public void update(EnqueteReplyChoices obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * アンケート回答選択式を削除します。
   * @param obj 削除対象のEnqueteReplyChoices
   */
  public void delete(EnqueteReplyChoices obj) {
    genericDao.delete(obj);
  }

  /**
   * アンケート回答選択式を削除します。
   * @param obj 削除対象のEnqueteReplyChoices
   */
  public void delete(EnqueteReplyChoices obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してアンケート回答選択式を削除します。
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   */
  public void delete(String customerCode, String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo) {
    Object[] params = new Object[]{customerCode, enqueteCode, enqueteQuestionNo, enqueteChoicesNo};
    final String query = "DELETE FROM ENQUETE_REPLY_CHOICES"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND ENQUETE_CHOICES_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してアンケート回答選択式のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteReplyChoicesのリスト
   */
  public List<EnqueteReplyChoices> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してアンケート回答選択式のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteReplyChoicesのリスト
   */
  public List<EnqueteReplyChoices> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のEnqueteReplyChoicesのリスト
   */
  public List<EnqueteReplyChoices> loadAll() {
    return genericDao.loadAll();
  }

}
