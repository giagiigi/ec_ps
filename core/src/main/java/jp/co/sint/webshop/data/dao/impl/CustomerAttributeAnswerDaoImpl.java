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
import jp.co.sint.webshop.data.dao.CustomerAttributeAnswerDao;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客属性回答
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerAttributeAnswerDaoImpl implements CustomerAttributeAnswerDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerAttributeAnswer, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerAttributeAnswerDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerAttributeAnswer, Long>(CustomerAttributeAnswer.class);
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
   * 指定されたorm_rowidを持つ顧客属性回答のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAttributeAnswerのインスタンス
   */
  public CustomerAttributeAnswer loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客属性回答のインスタンスを取得します。
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerAttributeAnswerのインスタンス
   */
  public CustomerAttributeAnswer load(Long customerAttributeNo, Long customerAttributeChoicesNo, String customerCode) {
    Object[] params = new Object[]{customerAttributeNo, customerAttributeChoicesNo, customerCode};
    final String query = "SELECT * FROM CUSTOMER_ATTRIBUTE_ANSWER"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?"
        + " AND CUSTOMER_ATTRIBUTE_CHOICES_NO = ?"
        + " AND CUSTOMER_CODE = ?";
    List<CustomerAttributeAnswer> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客属性回答が既に存在するかどうかを返します。
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するCustomerAttributeAnswerの行が存在すればtrue
   */
  public boolean exists(Long customerAttributeNo, Long customerAttributeChoicesNo, String customerCode) {
    Object[] params = new Object[]{customerAttributeNo, customerAttributeChoicesNo, customerCode};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_ATTRIBUTE_ANSWER"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?"
        + " AND CUSTOMER_ATTRIBUTE_CHOICES_NO = ?"
        + " AND CUSTOMER_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerAttributeAnswerをデータベースに追加します。
   * @param obj 追加対象のCustomerAttributeAnswer
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAttributeAnswer obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerAttributeAnswerをデータベースに追加します。
   * @param obj 追加対象のCustomerAttributeAnswer
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAttributeAnswer obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客属性回答を更新します。
   * @param obj 更新対象のCustomerAttributeAnswer
   */
  public void update(CustomerAttributeAnswer obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客属性回答を更新します。
   * @param obj 更新対象のCustomerAttributeAnswer
   */
  public void update(CustomerAttributeAnswer obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客属性回答を削除します。
   * @param obj 削除対象のCustomerAttributeAnswer
   */
  public void delete(CustomerAttributeAnswer obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客属性回答を削除します。
   * @param obj 削除対象のCustomerAttributeAnswer
   */
  public void delete(CustomerAttributeAnswer obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客属性回答を削除します。
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @param customerCode 顧客コード
   */
  public void delete(Long customerAttributeNo, Long customerAttributeChoicesNo, String customerCode) {
    Object[] params = new Object[]{customerAttributeNo, customerAttributeChoicesNo, customerCode};
    final String query = "DELETE FROM CUSTOMER_ATTRIBUTE_ANSWER"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?"
        + " AND CUSTOMER_ATTRIBUTE_CHOICES_NO = ?"
        + " AND CUSTOMER_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客属性回答のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAttributeAnswerのリスト
   */
  public List<CustomerAttributeAnswer> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客属性回答のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAttributeAnswerのリスト
   */
  public List<CustomerAttributeAnswer> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerAttributeAnswerのリスト
   */
  public List<CustomerAttributeAnswer> loadAll() {
    return genericDao.loadAll();
  }

}
