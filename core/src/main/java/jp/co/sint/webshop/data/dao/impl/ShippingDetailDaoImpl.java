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
import jp.co.sint.webshop.data.dao.ShippingDetailDao;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 出荷明細
 *
 * @author System Integrator Corp.
 *
 */
public class ShippingDetailDaoImpl implements ShippingDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ShippingDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ShippingDetailDaoImpl() {
    genericDao = new GenericDaoImpl<ShippingDetail, Long>(ShippingDetail.class);
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
   * 指定されたorm_rowidを持つ出荷明細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するShippingDetailのインスタンス
   */
  public ShippingDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して出荷明細のインスタンスを取得します。
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @return 主キー列の値に対応するShippingDetailのインスタンス
   */
  public ShippingDetail load(String shippingNo, Long shippingDetailNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo};
    final String query = "SELECT * FROM SHIPPING_DETAIL"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?";
    List<ShippingDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して出荷明細が既に存在するかどうかを返します。
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @return 主キー列の値に対応するShippingDetailの行が存在すればtrue
   */
  public boolean exists(String shippingNo, Long shippingDetailNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo};
    final String query = "SELECT COUNT(*) FROM SHIPPING_DETAIL"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規ShippingDetailをデータベースに追加します。
   * @param obj 追加対象のShippingDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ShippingDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規ShippingDetailをデータベースに追加します。
   * @param obj 追加対象のShippingDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ShippingDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 出荷明細を更新します。
   * @param obj 更新対象のShippingDetail
   */
  public void update(ShippingDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 出荷明細を更新します。
   * @param obj 更新対象のShippingDetail
   */
  public void update(ShippingDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 出荷明細を削除します。
   * @param obj 削除対象のShippingDetail
   */
  public void delete(ShippingDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 出荷明細を削除します。
   * @param obj 削除対象のShippingDetail
   */
  public void delete(ShippingDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して出荷明細を削除します。
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   */
  public void delete(String shippingNo, Long shippingDetailNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo};
    final String query = "DELETE FROM SHIPPING_DETAIL"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して出荷明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShippingDetailのリスト
   */
  public List<ShippingDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して出荷明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShippingDetailのリスト
   */
  public List<ShippingDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のShippingDetailのリスト
   */
  public List<ShippingDetail> loadAll() {
    return genericDao.loadAll();
  }

}
