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
import jp.co.sint.webshop.data.dao.DeliveryTypeDao;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 配送種別
 *
 * @author System Integrator Corp.
 *
 */
public class DeliveryTypeDaoImpl implements DeliveryTypeDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<DeliveryType, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public DeliveryTypeDaoImpl() {
    genericDao = new GenericDaoImpl<DeliveryType, Long>(DeliveryType.class);
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
   * 指定されたorm_rowidを持つ配送種別のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するDeliveryTypeのインスタンス
   */
  public DeliveryType loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して配送種別のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @return 主キー列の値に対応するDeliveryTypeのインスタンス
   */
  public DeliveryType load(String shopCode, Long deliveryTypeNo) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo};
    final String query = "SELECT * FROM DELIVERY_TYPE"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?";
    List<DeliveryType> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して配送種別が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @return 主キー列の値に対応するDeliveryTypeの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long deliveryTypeNo) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo};
    final String query = "SELECT COUNT(*) FROM DELIVERY_TYPE"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規DeliveryTypeをデータベースに追加します。
   * @param obj 追加対象のDeliveryType
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DeliveryType obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規DeliveryTypeをデータベースに追加します。
   * @param obj 追加対象のDeliveryType
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DeliveryType obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 配送種別を更新します。
   * @param obj 更新対象のDeliveryType
   */
  public void update(DeliveryType obj) {
    genericDao.update(obj);
  }

  /**
   * 配送種別を更新します。
   * @param obj 更新対象のDeliveryType
   */
  public void update(DeliveryType obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 配送種別を削除します。
   * @param obj 削除対象のDeliveryType
   */
  public void delete(DeliveryType obj) {
    genericDao.delete(obj);
  }

  /**
   * 配送種別を削除します。
   * @param obj 削除対象のDeliveryType
   */
  public void delete(DeliveryType obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して配送種別を削除します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   */
  public void delete(String shopCode, Long deliveryTypeNo) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo};
    final String query = "DELETE FROM DELIVERY_TYPE"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して配送種別のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するDeliveryTypeのリスト
   */
  public List<DeliveryType> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して配送種別のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するDeliveryTypeのリスト
   */
  public List<DeliveryType> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のDeliveryTypeのリスト
   */
  public List<DeliveryType> loadAll() {
    return genericDao.loadAll();
  }

}
