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
import jp.co.sint.webshop.data.dao.CustomerAttributeChoiceDao;
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客属性選択肢名
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerAttributeChoiceDaoImpl implements CustomerAttributeChoiceDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerAttributeChoice, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerAttributeChoiceDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerAttributeChoice, Long>(CustomerAttributeChoice.class);
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
   * 指定されたorm_rowidを持つ顧客属性選択肢名のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAttributeChoiceのインスタンス
   */
  public CustomerAttributeChoice loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客属性選択肢名のインスタンスを取得します。
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @return 主キー列の値に対応するCustomerAttributeChoiceのインスタンス
   */
  public CustomerAttributeChoice load(Long customerAttributeNo, Long customerAttributeChoicesNo) {
    Object[] params = new Object[]{customerAttributeNo, customerAttributeChoicesNo};
    final String query = "SELECT * FROM CUSTOMER_ATTRIBUTE_CHOICE"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?"
        + " AND CUSTOMER_ATTRIBUTE_CHOICES_NO = ?";
    List<CustomerAttributeChoice> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客属性選択肢名が既に存在するかどうかを返します。
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   * @return 主キー列の値に対応するCustomerAttributeChoiceの行が存在すればtrue
   */
  public boolean exists(Long customerAttributeNo, Long customerAttributeChoicesNo) {
    Object[] params = new Object[]{customerAttributeNo, customerAttributeChoicesNo};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_ATTRIBUTE_CHOICE"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?"
        + " AND CUSTOMER_ATTRIBUTE_CHOICES_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerAttributeChoiceをデータベースに追加します。
   * @param obj 追加対象のCustomerAttributeChoice
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAttributeChoice obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerAttributeChoiceをデータベースに追加します。
   * @param obj 追加対象のCustomerAttributeChoice
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAttributeChoice obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客属性選択肢名を更新します。
   * @param obj 更新対象のCustomerAttributeChoice
   */
  public void update(CustomerAttributeChoice obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客属性選択肢名を更新します。
   * @param obj 更新対象のCustomerAttributeChoice
   */
  public void update(CustomerAttributeChoice obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客属性選択肢名を削除します。
   * @param obj 削除対象のCustomerAttributeChoice
   */
  public void delete(CustomerAttributeChoice obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客属性選択肢名を削除します。
   * @param obj 削除対象のCustomerAttributeChoice
   */
  public void delete(CustomerAttributeChoice obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客属性選択肢名を削除します。
   * @param customerAttributeNo 顧客属性番号
   * @param customerAttributeChoicesNo 顧客属性選択肢番号
   */
  public void delete(Long customerAttributeNo, Long customerAttributeChoicesNo) {
    Object[] params = new Object[]{customerAttributeNo, customerAttributeChoicesNo};
    final String query = "DELETE FROM CUSTOMER_ATTRIBUTE_CHOICE"
        + " WHERE CUSTOMER_ATTRIBUTE_NO = ?"
        + " AND CUSTOMER_ATTRIBUTE_CHOICES_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客属性選択肢名のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAttributeChoiceのリスト
   */
  public List<CustomerAttributeChoice> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客属性選択肢名のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAttributeChoiceのリスト
   */
  public List<CustomerAttributeChoice> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerAttributeChoiceのリスト
   */
  public List<CustomerAttributeChoice> loadAll() {
    return genericDao.loadAll();
  }

}
