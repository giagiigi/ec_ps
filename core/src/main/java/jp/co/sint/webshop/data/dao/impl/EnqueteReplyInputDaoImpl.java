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
import jp.co.sint.webshop.data.dao.EnqueteReplyInputDao;
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * アンケート回答入力式
 *
 * @author System Integrator Corp.
 *
 */
public class EnqueteReplyInputDaoImpl implements EnqueteReplyInputDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<EnqueteReplyInput, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public EnqueteReplyInputDaoImpl() {
    genericDao = new GenericDaoImpl<EnqueteReplyInput, Long>(EnqueteReplyInput.class);
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
   * 指定されたorm_rowidを持つアンケート回答入力式のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteReplyInputのインスタンス
   */
  public EnqueteReplyInput loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してアンケート回答入力式のインスタンスを取得します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するEnqueteReplyInputのインスタンス
   */
  public EnqueteReplyInput load(String enqueteCode, Long enqueteQuestionNo, String customerCode) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo, customerCode};
    final String query = "SELECT * FROM ENQUETE_REPLY_INPUT"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND CUSTOMER_CODE = ?";
    List<EnqueteReplyInput> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してアンケート回答入力式が既に存在するかどうかを返します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するEnqueteReplyInputの行が存在すればtrue
   */
  public boolean exists(String enqueteCode, Long enqueteQuestionNo, String customerCode) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo, customerCode};
    final String query = "SELECT COUNT(*) FROM ENQUETE_REPLY_INPUT"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND CUSTOMER_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規EnqueteReplyInputをデータベースに追加します。
   * @param obj 追加対象のEnqueteReplyInput
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteReplyInput obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規EnqueteReplyInputをデータベースに追加します。
   * @param obj 追加対象のEnqueteReplyInput
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteReplyInput obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * アンケート回答入力式を更新します。
   * @param obj 更新対象のEnqueteReplyInput
   */
  public void update(EnqueteReplyInput obj) {
    genericDao.update(obj);
  }

  /**
   * アンケート回答入力式を更新します。
   * @param obj 更新対象のEnqueteReplyInput
   */
  public void update(EnqueteReplyInput obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * アンケート回答入力式を削除します。
   * @param obj 削除対象のEnqueteReplyInput
   */
  public void delete(EnqueteReplyInput obj) {
    genericDao.delete(obj);
  }

  /**
   * アンケート回答入力式を削除します。
   * @param obj 削除対象のEnqueteReplyInput
   */
  public void delete(EnqueteReplyInput obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してアンケート回答入力式を削除します。
   * @param enqueteCode アンケートコード
   * @param enqueteQuestionNo アンケート設問番号
   * @param customerCode 顧客コード
   */
  public void delete(String enqueteCode, Long enqueteQuestionNo, String customerCode) {
    Object[] params = new Object[]{enqueteCode, enqueteQuestionNo, customerCode};
    final String query = "DELETE FROM ENQUETE_REPLY_INPUT"
        + " WHERE ENQUETE_CODE = ?"
        + " AND ENQUETE_QUESTION_NO = ?"
        + " AND CUSTOMER_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してアンケート回答入力式のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteReplyInputのリスト
   */
  public List<EnqueteReplyInput> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してアンケート回答入力式のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteReplyInputのリスト
   */
  public List<EnqueteReplyInput> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のEnqueteReplyInputのリスト
   */
  public List<EnqueteReplyInput> loadAll() {
    return genericDao.loadAll();
  }

}
