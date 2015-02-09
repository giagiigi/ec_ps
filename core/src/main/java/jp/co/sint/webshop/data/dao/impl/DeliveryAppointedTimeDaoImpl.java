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
import jp.co.sint.webshop.data.dao.DeliveryAppointedTimeDao;
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 配送指定時間
 *
 * @author System Integrator Corp.
 *
 */
public class DeliveryAppointedTimeDaoImpl implements DeliveryAppointedTimeDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<DeliveryAppointedTime, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public DeliveryAppointedTimeDaoImpl() {
    genericDao = new GenericDaoImpl<DeliveryAppointedTime, Long>(DeliveryAppointedTime.class);
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
   * 指定されたorm_rowidを持つ配送指定時間のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するDeliveryAppointedTimeのインスタンス
   */
  public DeliveryAppointedTime loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して配送指定時間のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param deliveryAppointedTimeCode 配送指定時間コード
   * @return 主キー列の値に対応するDeliveryAppointedTimeのインスタンス
   */
  public DeliveryAppointedTime load(String shopCode, Long deliveryTypeNo, String deliveryAppointedTimeCode) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo, deliveryAppointedTimeCode};
    final String query = "SELECT * FROM DELIVERY_APPOINTED_TIME"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?"
        + " AND DELIVERY_APPOINTED_TIME_CODE = ?";
    List<DeliveryAppointedTime> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して配送指定時間が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param deliveryAppointedTimeCode 配送指定時間コード
   * @return 主キー列の値に対応するDeliveryAppointedTimeの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long deliveryTypeNo, String deliveryAppointedTimeCode) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo, deliveryAppointedTimeCode};
    final String query = "SELECT COUNT(*) FROM DELIVERY_APPOINTED_TIME"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?"
        + " AND DELIVERY_APPOINTED_TIME_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規DeliveryAppointedTimeをデータベースに追加します。
   * @param obj 追加対象のDeliveryAppointedTime
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DeliveryAppointedTime obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規DeliveryAppointedTimeをデータベースに追加します。
   * @param obj 追加対象のDeliveryAppointedTime
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DeliveryAppointedTime obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 配送指定時間を更新します。
   * @param obj 更新対象のDeliveryAppointedTime
   */
  public void update(DeliveryAppointedTime obj) {
    genericDao.update(obj);
  }

  /**
   * 配送指定時間を更新します。
   * @param obj 更新対象のDeliveryAppointedTime
   */
  public void update(DeliveryAppointedTime obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 配送指定時間を削除します。
   * @param obj 削除対象のDeliveryAppointedTime
   */
  public void delete(DeliveryAppointedTime obj) {
    genericDao.delete(obj);
  }

  /**
   * 配送指定時間を削除します。
   * @param obj 削除対象のDeliveryAppointedTime
   */
  public void delete(DeliveryAppointedTime obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して配送指定時間を削除します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param deliveryAppointedTimeCode 配送指定時間コード
   */
  public void delete(String shopCode, Long deliveryTypeNo, String deliveryAppointedTimeCode) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo, deliveryAppointedTimeCode};
    final String query = "DELETE FROM DELIVERY_APPOINTED_TIME"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?"
        + " AND DELIVERY_APPOINTED_TIME_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して配送指定時間のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するDeliveryAppointedTimeのリスト
   */
  public List<DeliveryAppointedTime> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して配送指定時間のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するDeliveryAppointedTimeのリスト
   */
  public List<DeliveryAppointedTime> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のDeliveryAppointedTimeのリスト
   */
  public List<DeliveryAppointedTime> loadAll() {
    return genericDao.loadAll();
  }

}
