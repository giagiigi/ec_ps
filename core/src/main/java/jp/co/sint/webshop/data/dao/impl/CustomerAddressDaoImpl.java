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
import jp.co.sint.webshop.data.dao.CustomerAddressDao;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 顧客アドレス帳
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerAddressDaoImpl implements CustomerAddressDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerAddress, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerAddressDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerAddress, Long>(CustomerAddress.class);
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
   * 指定されたorm_rowidを持つ顧客アドレス帳のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerAddressのインスタンス
   */
  public CustomerAddress loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して顧客アドレス帳のインスタンスを取得します。
   * @param customerCode 顧客コード
   * @param addressNo アドレス帳番号
   * @return 主キー列の値に対応するCustomerAddressのインスタンス
   */
  public CustomerAddress load(String customerCode, Long addressNo) {
    Object[] params = new Object[]{customerCode, addressNo};
    final String query = "SELECT * FROM CUSTOMER_ADDRESS"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ADDRESS_NO = ?";
    List<CustomerAddress> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して顧客アドレス帳が既に存在するかどうかを返します。
   * @param customerCode 顧客コード
   * @param addressNo アドレス帳番号
   * @return 主キー列の値に対応するCustomerAddressの行が存在すればtrue
   */
  public boolean exists(String customerCode, Long addressNo) {
    Object[] params = new Object[]{customerCode, addressNo};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_ADDRESS"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ADDRESS_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerAddressをデータベースに追加します。
   * @param obj 追加対象のCustomerAddress
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAddress obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerAddressをデータベースに追加します。
   * @param obj 追加対象のCustomerAddress
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerAddress obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 顧客アドレス帳を更新します。
   * @param obj 更新対象のCustomerAddress
   */
  public void update(CustomerAddress obj) {
    genericDao.update(obj);
  }

  /**
   * 顧客アドレス帳を更新します。
   * @param obj 更新対象のCustomerAddress
   */
  public void update(CustomerAddress obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 顧客アドレス帳を削除します。
   * @param obj 削除対象のCustomerAddress
   */
  public void delete(CustomerAddress obj) {
    genericDao.delete(obj);
  }

  /**
   * 顧客アドレス帳を削除します。
   * @param obj 削除対象のCustomerAddress
   */
  public void delete(CustomerAddress obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して顧客アドレス帳を削除します。
   * @param customerCode 顧客コード
   * @param addressNo アドレス帳番号
   */
  public void delete(String customerCode, Long addressNo) {
    Object[] params = new Object[]{customerCode, addressNo};
    final String query = "DELETE FROM CUSTOMER_ADDRESS"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND ADDRESS_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して顧客アドレス帳のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerAddressのリスト
   */
  public List<CustomerAddress> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して顧客アドレス帳のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerAddressのリスト
   */
  public List<CustomerAddress> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerAddressのリスト
   */
  public List<CustomerAddress> loadAll() {
    return genericDao.loadAll();
  }

}
