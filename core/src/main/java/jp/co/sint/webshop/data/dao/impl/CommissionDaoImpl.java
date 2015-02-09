//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.CommissionDao;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 支払手数料
 *
 * @author System Integrator Corp.
 *
 */
public class CommissionDaoImpl implements CommissionDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Commission, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommissionDaoImpl() {
    genericDao = new GenericDaoImpl<Commission, Long>(Commission.class);
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
   * 指定されたorm_rowidを持つ支払手数料のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCommissionのインスタンス
   */
  public Commission loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して支払手数料のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param paymentPriceThreshold 支払金額閾値
   * @return 主キー列の値に対応するCommissionのインスタンス
   */
  public Commission load(String shopCode, Long paymentMethodNo, BigDecimal paymentPriceThreshold) {
    Object[] params = new Object[]{shopCode, paymentMethodNo, paymentPriceThreshold};
    final String query = "SELECT * FROM COMMISSION"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?"
        + " AND PAYMENT_PRICE_THRESHOLD = ?";
    List<Commission> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して支払手数料が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param paymentPriceThreshold 支払金額閾値
   * @return 主キー列の値に対応するCommissionの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long paymentMethodNo, Long paymentPriceThreshold) {
    Object[] params = new Object[]{shopCode, paymentMethodNo, paymentPriceThreshold};
    final String query = "SELECT COUNT(*) FROM COMMISSION"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?"
        + " AND PAYMENT_PRICE_THRESHOLD = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Commissionをデータベースに追加します。
   * @param obj 追加対象のCommission
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Commission obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Commissionをデータベースに追加します。
   * @param obj 追加対象のCommission
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Commission obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 支払手数料を更新します。
   * @param obj 更新対象のCommission
   */
  public void update(Commission obj) {
    genericDao.update(obj);
  }

  /**
   * 支払手数料を更新します。
   * @param obj 更新対象のCommission
   */
  public void update(Commission obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 支払手数料を削除します。
   * @param obj 削除対象のCommission
   */
  public void delete(Commission obj) {
    genericDao.delete(obj);
  }

  /**
   * 支払手数料を削除します。
   * @param obj 削除対象のCommission
   */
  public void delete(Commission obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して支払手数料を削除します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param paymentPriceThreshold 支払金額閾値
   */
  public void delete(String shopCode, Long paymentMethodNo, BigDecimal paymentPriceThreshold) {
    Object[] params = new Object[]{shopCode, paymentMethodNo, paymentPriceThreshold};
    final String query = "DELETE FROM COMMISSION"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?"
        + " AND PAYMENT_PRICE_THRESHOLD = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して支払手数料のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommissionのリスト
   */
  public List<Commission> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して支払手数料のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommissionのリスト
   */
  public List<Commission> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommissionのリスト
   */
  public List<Commission> loadAll() {
    return genericDao.loadAll();
  }

}
