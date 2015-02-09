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
import jp.co.sint.webshop.data.dao.EnqueteQuestionDao;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * アンケート設問
 *
 * @author System Integrator Corp.
 *
 */
public class EnqueteQuestionDaoImpl implements EnqueteQuestionDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<EnqueteQuestion, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public EnqueteQuestionDaoImpl() {
    genericDao = new GenericDaoImpl<EnqueteQuestion, Long>(EnqueteQuestion.class);
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
   * 指定されたorm_rowidを持つアンケート設問のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteQuestionのインスタンス
   */
  public EnqueteQuestion loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してアンケート設問のインスタンスを取得します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @return 主キー列の値に対応するEnqueteQuestionのインスタンス
   */
  public EnqueteQuestion load(String enqueteCode, Long enqueteQuestionNo) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo};
    final String query = "SELECT * FROM ENQUETE_QUESTION"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?";
    List<EnqueteQuestion> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してアンケート設問が既に存在するかどうかを返します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @return 主キー列の値に対応するEnqueteQuestionの行が存在すればtrue
   */
  public boolean exists(String enqueteCode, Long enqueteQuestionNo) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo};
    final String query = "SELECT COUNT(*) FROM ENQUETE_QUESTION"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規EnqueteQuestionをデータベースに追加します。
   * @param obj 追加対象のEnqueteQuestion
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteQuestion obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規EnqueteQuestionをデータベースに追加します。
   * @param obj 追加対象のEnqueteQuestion
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteQuestion obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * アンケート設問を更新します。
   * @param obj 更新対象のEnqueteQuestion
   */
  public void update(EnqueteQuestion obj) {
    genericDao.update(obj);
  }

  /**
   * アンケート設問を更新します。
   * @param obj 更新対象のEnqueteQuestion
   */
  public void update(EnqueteQuestion obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * アンケート設問を削除します。
   * @param obj 削除対象のEnqueteQuestion
   */
  public void delete(EnqueteQuestion obj) {
    genericDao.delete(obj);
  }

  /**
   * アンケート設問を削除します。
   * @param obj 削除対象のEnqueteQuestion
   */
  public void delete(EnqueteQuestion obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してアンケート設問を削除します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   */
  public void delete(String enqueteCode, Long enqueteQuestionNo) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo};
    final String query = "DELETE FROM ENQUETE_QUESTION"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してアンケート設問のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteQuestionのリスト
   */
  public List<EnqueteQuestion> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してアンケート設問のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteQuestionのリスト
   */
  public List<EnqueteQuestion> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のEnqueteQuestionのリスト
   */
  public List<EnqueteQuestion> loadAll() {
    return genericDao.loadAll();
  }

}
