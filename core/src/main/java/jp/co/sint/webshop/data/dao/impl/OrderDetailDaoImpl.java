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
import jp.co.sint.webshop.data.dao.OrderDetailDao;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 受注明細
 *
 * @author System Integrator Corp.
 *
 */
public class OrderDetailDaoImpl implements OrderDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<OrderDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public OrderDetailDaoImpl() {
    genericDao = new GenericDaoImpl<OrderDetail, Long>(OrderDetail.class);
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
  public OrderDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注明細のインスタンスを取得します。
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するOrderDetailのインスタンス
   */
  public OrderDetail load(String orderNo, String shopCode, String skuCode) {
    Object[] params = new Object[]{orderNo, shopCode, skuCode};
    final String query = "SELECT * FROM ORDER_DETAIL"
        + " WHERE ORDER_NO = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    List<OrderDetail> result = genericDao.findByQuery(query, params);
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
  public boolean exists(String orderNo, String shopCode, String skuCode) {
    Object[] params = new Object[]{orderNo, shopCode, skuCode};
    final String query = "SELECT COUNT(*) FROM ORDER_DETAIL"
        + " WHERE ORDER_NO = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規OrderDetailをデータベースに追加します。
   * @param obj 追加対象のOrderDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規OrderDetailをデータベースに追加します。
   * @param obj 追加対象のOrderDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注明細を更新します。
   * @param obj 更新対象のOrderDetail
   */
  public void update(OrderDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 受注明細を更新します。
   * @param obj 更新対象のOrderDetail
   */
  public void update(OrderDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注明細を削除します。
   * @param obj 削除対象のOrderDetail
   */
  public void delete(OrderDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注明細を削除します。
   * @param obj 削除対象のOrderDetail
   */
  public void delete(OrderDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注明細を削除します。
   * @param orderNo 受注番号
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String orderNo, String shopCode, String skuCode) {
    Object[] params = new Object[]{orderNo, shopCode, skuCode};
    final String query = "DELETE FROM ORDER_DETAIL"
        + " WHERE ORDER_NO = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOrderDetailのリスト
   */
  public List<OrderDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOrderDetailのリスト
   */
  public List<OrderDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のOrderDetailのリスト
   */
  public List<OrderDetail> loadAll() {
    return genericDao.loadAll();
  }

}
