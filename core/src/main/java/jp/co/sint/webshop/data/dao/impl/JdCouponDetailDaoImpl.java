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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.JdCouponDetailDao;
import jp.co.sint.webshop.data.dto.JdCouponDetail;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 受注明細
 *
 * @author System Integrator Corp.
 *
 */
public class JdCouponDetailDaoImpl implements JdCouponDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<JdCouponDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public JdCouponDetailDaoImpl() {
    genericDao = new GenericDaoImpl<JdCouponDetail, Long>(JdCouponDetail.class);
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
   * 指定されたorm_rowidを持つ受注明細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するOrderDetailのインスタンス
   */
  public JdCouponDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注明細のインスタンスを取得します。
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するOrderDetailのインスタンス
   */
  public JdCouponDetail load(String orderNo, Long couponNo) {
    Object[] params = new Object[]{orderNo, couponNo};
    final String query = "SELECT * FROM JD_COUPON_DETAIL"
        + " WHERE ORDER_NO = ?"
        + " AND COUPON_NO = ?";
    List<JdCouponDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して受注明細が既に存在するかどうかを返します。
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するOrderDetailの行が存在すればtrue
   */
  public boolean exists(String orderNo, Long couponNo) {
    Object[] params = new Object[]{orderNo, couponNo};
    final String query = "SELECT COUNT(*) FROM JD_COUPON_DETAIL"
        + " WHERE ORDER_NO = ?"
        + " AND COUPON_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規OrderDetailをデータベースに追加します。
   * @param obj 追加対象のOrderDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdCouponDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規OrderDetailをデータベースに追加します。
   * @param obj 追加対象のOrderDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdCouponDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注明細を更新します。
   * @param obj 更新対象のOrderDetail
   */
  public void update(JdCouponDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 受注明細を更新します。
   * @param obj 更新対象のOrderDetail
   */
  public void update(JdCouponDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注明細を削除します。
   * @param obj 削除対象のOrderDetail
   */
  public void delete(JdCouponDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注明細を削除します。
   * @param obj 削除対象のOrderDetail
   */
  public void delete(JdCouponDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注明細を削除します。
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String orderNo, Long couponNo) {
    Object[] params = new Object[]{orderNo, couponNo};
    final String query = "DELETE FROM JD_COUPON_DETAIL"
        + " WHERE ORDER_NO = ?"
        + " AND COUPON_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOrderDetailのリスト
   */
  public List<JdCouponDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOrderDetailのリスト
   */
  public List<JdCouponDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のOrderDetailのリスト
   */
  public List<JdCouponDetail> loadAll() {
    return genericDao.loadAll();
  }

}
