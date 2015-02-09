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
import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 支払方法
 *
 * @author System Integrator Corp.
 *
 */
public class PaymentMethodDaoImpl implements PaymentMethodDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PaymentMethod, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PaymentMethodDaoImpl() {
    genericDao = new GenericDaoImpl<PaymentMethod, Long>(PaymentMethod.class);
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
   * 指定されたorm_rowidを持つ支払方法のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPaymentMethodのインスタンス
   */
  public PaymentMethod loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して支払方法のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @return 主キー列の値に対応するPaymentMethodのインスタンス
   */
  public PaymentMethod load(String shopCode, Long paymentMethodNo) {
    Object[] params = new Object[]{shopCode, paymentMethodNo};
    final String query = "SELECT * FROM PAYMENT_METHOD"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?";
    List<PaymentMethod> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  // 20111206 lirong add start
  /**
   * 支払区分を指定して支払方法のインスタンスを取得します。
   * @param paymentMethodType 支払区分番号
   * @return 支払区分の値に対応するPaymentMethodのインスタンス
   */
  public PaymentMethod load(String paymentMethodType) {
    Object[] params = new Object[]{paymentMethodType};
    final String query = "SELECT * FROM PAYMENT_METHOD"
        + " WHERE PAYMENT_METHOD_TYPE = ?";
    List<PaymentMethod> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  // 20111206 lirong add end
  /**
   * 主キー列の値を指定して支払方法が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @return 主キー列の値に対応するPaymentMethodの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long paymentMethodNo) {
    Object[] params = new Object[]{shopCode, paymentMethodNo};
    final String query = "SELECT COUNT(*) FROM PAYMENT_METHOD"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PaymentMethodをデータベースに追加します。
   * @param obj 追加対象のPaymentMethod
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PaymentMethod obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PaymentMethodをデータベースに追加します。
   * @param obj 追加対象のPaymentMethod
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PaymentMethod obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 支払方法を更新します。
   * @param obj 更新対象のPaymentMethod
   */
  public void update(PaymentMethod obj) {
    genericDao.update(obj);
  }

  /**
   * 支払方法を更新します。
   * @param obj 更新対象のPaymentMethod
   */
  public void update(PaymentMethod obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 支払方法を削除します。
   * @param obj 削除対象のPaymentMethod
   */
  public void delete(PaymentMethod obj) {
    genericDao.delete(obj);
  }

  /**
   * 支払方法を削除します。
   * @param obj 削除対象のPaymentMethod
   */
  public void delete(PaymentMethod obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して支払方法を削除します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   */
  public void delete(String shopCode, Long paymentMethodNo) {
    Object[] params = new Object[]{shopCode, paymentMethodNo};
    final String query = "DELETE FROM PAYMENT_METHOD"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して支払方法のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPaymentMethodのリスト
   */
  public List<PaymentMethod> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して支払方法のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPaymentMethodのリスト
   */
  public List<PaymentMethod> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPaymentMethodのリスト
   */
  public List<PaymentMethod> loadAll() {
    return genericDao.loadAll();
  }

}
