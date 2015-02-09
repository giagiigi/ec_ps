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
import jp.co.sint.webshop.data.dao.EnqueteChoiceDao;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * アンケート選択肢名
 *
 * @author System Integrator Corp.
 *
 */
public class EnqueteChoiceDaoImpl implements EnqueteChoiceDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<EnqueteChoice, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public EnqueteChoiceDaoImpl() {
    genericDao = new GenericDaoImpl<EnqueteChoice, Long>(EnqueteChoice.class);
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
   * 指定されたorm_rowidを持つアンケート選択肢名のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteChoiceのインスタンス
   */
  public EnqueteChoice loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してアンケート選択肢名のインスタンスを取得します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteChoiceのインスタンス
   */
  public EnqueteChoice load(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo, enqueteChoicesNo};
    final String query = "SELECT * FROM ENQUETE_CHOICE"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND ENQUETE_CHOICES_NO = ?";
    List<EnqueteChoice> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してアンケート選択肢名が既に存在するかどうかを返します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   * @return 主キー列の値に対応するEnqueteChoiceの行が存在すればtrue
   */
  public boolean exists(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo, enqueteChoicesNo};
    final String query = "SELECT COUNT(*) FROM ENQUETE_CHOICE"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND ENQUETE_CHOICES_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規EnqueteChoiceをデータベースに追加します。
   * @param obj 追加対象のEnqueteChoice
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteChoice obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規EnqueteChoiceをデータベースに追加します。
   * @param obj 追加対象のEnqueteChoice
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteChoice obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * アンケート選択肢名を更新します。
   * @param obj 更新対象のEnqueteChoice
   */
  public void update(EnqueteChoice obj) {
    genericDao.update(obj);
  }

  /**
   * アンケート選択肢名を更新します。
   * @param obj 更新対象のEnqueteChoice
   */
  public void update(EnqueteChoice obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * アンケート選択肢名を削除します。
   * @param obj 削除対象のEnqueteChoice
   */
  public void delete(EnqueteChoice obj) {
    genericDao.delete(obj);
  }

  /**
   * アンケート選択肢名を削除します。
   * @param obj 削除対象のEnqueteChoice
   */
  public void delete(EnqueteChoice obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してアンケート選択肢名を削除します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param enqueteChoicesNo アンケート選択肢番号
   */
  public void delete(String enqueteCode, Long enqueteQuestionNo, Long enqueteChoicesNo) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo, enqueteChoicesNo};
    final String query = "DELETE FROM ENQUETE_CHOICE"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND ENQUETE_CHOICES_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してアンケート選択肢名のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteChoiceのリスト
   */
  public List<EnqueteChoice> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してアンケート選択肢名のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteChoiceのリスト
   */
  public List<EnqueteChoice> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のEnqueteChoiceのリスト
   */
  public List<EnqueteChoice> loadAll() {
    return genericDao.loadAll();
  }

}
