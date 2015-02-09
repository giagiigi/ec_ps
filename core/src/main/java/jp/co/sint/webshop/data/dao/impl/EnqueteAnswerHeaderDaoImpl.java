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
import jp.co.sint.webshop.data.dao.EnqueteAnswerHeaderDao;
import jp.co.sint.webshop.data.dto.EnqueteAnswerHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * アンケート回答ヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class EnqueteAnswerHeaderDaoImpl implements EnqueteAnswerHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<EnqueteAnswerHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public EnqueteAnswerHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<EnqueteAnswerHeader, Long>(EnqueteAnswerHeader.class);
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
   * 指定されたorm_rowidを持つアンケート回答ヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するEnqueteAnswerHeaderのインスタンス
   */
  public EnqueteAnswerHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してアンケート回答ヘッダのインスタンスを取得します。
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteAnswerHeaderのインスタンス
   */
  public EnqueteAnswerHeader load(String customerCode, String enqueteCode) {
    Object[] params = new Object[]{customerCode, enqueteCode};
    final String query = "SELECT * FROM ENQUETE_ANSWER_HEADER"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ENQUETE_CODE = ?";
    List<EnqueteAnswerHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してアンケート回答ヘッダが既に存在するかどうかを返します。
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   * @return 主キー列の値に対応するEnqueteAnswerHeaderの行が存在すればtrue
   */
  public boolean exists(String customerCode, String enqueteCode) {
    Object[] params = new Object[]{customerCode, enqueteCode};
    final String query = "SELECT COUNT(*) FROM ENQUETE_ANSWER_HEADER"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ENQUETE_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規EnqueteAnswerHeaderをデータベースに追加します。
   * @param obj 追加対象のEnqueteAnswerHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteAnswerHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規EnqueteAnswerHeaderをデータベースに追加します。
   * @param obj 追加対象のEnqueteAnswerHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(EnqueteAnswerHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * アンケート回答ヘッダを更新します。
   * @param obj 更新対象のEnqueteAnswerHeader
   */
  public void update(EnqueteAnswerHeader obj) {
    genericDao.update(obj);
  }

  /**
   * アンケート回答ヘッダを更新します。
   * @param obj 更新対象のEnqueteAnswerHeader
   */
  public void update(EnqueteAnswerHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * アンケート回答ヘッダを削除します。
   * @param obj 削除対象のEnqueteAnswerHeader
   */
  public void delete(EnqueteAnswerHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * アンケート回答ヘッダを削除します。
   * @param obj 削除対象のEnqueteAnswerHeader
   */
  public void delete(EnqueteAnswerHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してアンケート回答ヘッダを削除します。
   * @param customerCode 顧客コード
   * @param enqueteCode アンケートコード
   */
  public void delete(String customerCode, String enqueteCode) {
    Object[] params = new Object[]{customerCode, enqueteCode};
    final String query = "DELETE FROM ENQUETE_ANSWER_HEADER"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ENQUETE_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してアンケート回答ヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するEnqueteAnswerHeaderのリスト
   */
  public List<EnqueteAnswerHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してアンケート回答ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するEnqueteAnswerHeaderのリスト
   */
  public List<EnqueteAnswerHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のEnqueteAnswerHeaderのリスト
   */
  public List<EnqueteAnswerHeader> loadAll() {
    return genericDao.loadAll();
  }

}
