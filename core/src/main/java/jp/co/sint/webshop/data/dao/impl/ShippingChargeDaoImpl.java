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
import jp.co.sint.webshop.data.dao.ShippingChargeDao;
import jp.co.sint.webshop.data.dto.ShippingCharge;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 送料
 *
 * @author System Integrator Corp.
 *
 */
public class ShippingChargeDaoImpl implements ShippingChargeDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ShippingCharge, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ShippingChargeDaoImpl() {
    genericDao = new GenericDaoImpl<ShippingCharge, Long>(ShippingCharge.class);
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
   * 指定されたorm_rowidを持つ送料のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するShippingChargeのインスタンス
   */
  public ShippingCharge loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して送料のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するShippingChargeのインスタンス
   */
  public ShippingCharge load(String shopCode, Long deliveryTypeNo, Long regionBlockId) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo, regionBlockId};
    final String query = "SELECT * FROM SHIPPING_CHARGE"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?"
        + " AND REGION_BLOCK_ID = ?";
    List<ShippingCharge> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して送料が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するShippingChargeの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long deliveryTypeNo, Long regionBlockId) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo, regionBlockId};
    final String query = "SELECT COUNT(*) FROM SHIPPING_CHARGE"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?"
        + " AND REGION_BLOCK_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規ShippingChargeをデータベースに追加します。
   * @param obj 追加対象のShippingCharge
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ShippingCharge obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規ShippingChargeをデータベースに追加します。
   * @param obj 追加対象のShippingCharge
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ShippingCharge obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 送料を更新します。
   * @param obj 更新対象のShippingCharge
   */
  public void update(ShippingCharge obj) {
    genericDao.update(obj);
  }

  /**
   * 送料を更新します。
   * @param obj 更新対象のShippingCharge
   */
  public void update(ShippingCharge obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 送料を削除します。
   * @param obj 削除対象のShippingCharge
   */
  public void delete(ShippingCharge obj) {
    genericDao.delete(obj);
  }

  /**
   * 送料を削除します。
   * @param obj 削除対象のShippingCharge
   */
  public void delete(ShippingCharge obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して送料を削除します。
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param regionBlockId 地域ブロックID
   */
  public void delete(String shopCode, Long deliveryTypeNo, Long regionBlockId) {
    Object[] params = new Object[]{shopCode, deliveryTypeNo, regionBlockId};
    final String query = "DELETE FROM SHIPPING_CHARGE"
        + " WHERE SHOP_CODE = ?"
        + " AND DELIVERY_TYPE_NO = ?"
        + " AND REGION_BLOCK_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して送料のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShippingChargeのリスト
   */
  public List<ShippingCharge> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して送料のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShippingChargeのリスト
   */
  public List<ShippingCharge> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のShippingChargeのリスト
   */
  public List<ShippingCharge> loadAll() {
    return genericDao.loadAll();
  }

}
