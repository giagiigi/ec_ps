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
import jp.co.sint.webshop.data.dao.ShippingDetailCompositionDao;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 出荷明細構成品
 *
 * @author OB.
 *
 */
public class ShippingDetailCompositionDaoImpl implements ShippingDetailCompositionDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ShippingDetailComposition, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ShippingDetailCompositionDaoImpl() {
    genericDao = new GenericDaoImpl<ShippingDetailComposition, Long>(ShippingDetailComposition.class);
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
   * 指定されたorm_rowidを持つ出荷明細構成品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するShippingDetailCompositionのインスタンス
   */
  public ShippingDetailComposition loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して出荷明細構成品のインスタンスを取得します。
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @param compositionNo 構成品項番
   * @return 主キー列の値に対応するShippingDetailCompositionのインスタンス
   */
  public ShippingDetailComposition load(String shippingNo, Long shippingDetailNo, Long compositionNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo, compositionNo};
    final String query = "SELECT * FROM SHIPPING_DETAIL_COMPOSITION"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?"
        + " AND COMPOSITION_NO = ?";
    List<ShippingDetailComposition> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して出荷明細構成品が既に存在するかどうかを返します。
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @param compositionNo 構成品項番
   * @return 主キー列の値に対応するShippingDetailCompositionの行が存在すればtrue
   */
  public boolean exists(String shippingNo, Long shippingDetailNo, Long compositionNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo, compositionNo};
    final String query = "SELECT COUNT(*) FROM SHIPPING_DETAIL_COMPOSITION"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?"
        + " AND COMPOSITION_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規ShippingDetailCompositionをデータベースに追加します。
   * @param obj 追加対象のShippingDetailComposition
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ShippingDetailComposition obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規ShippingDetailCompositionをデータベースに追加します。
   * @param obj 追加対象のShippingDetailComposition
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ShippingDetailComposition obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 出荷明細構成品を更新します。
   * @param obj 更新対象のShippingDetailComposition
   */
  public void update(ShippingDetailComposition obj) {
    genericDao.update(obj);
  }

  /**
   * 出荷明細構成品を更新します。
   * @param obj 更新対象のShippingDetailComposition
   */
  public void update(ShippingDetailComposition obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 出荷明細構成品を削除します。
   * @param obj 削除対象のShippingDetailComposition
   */
  public void delete(ShippingDetailComposition obj) {
    genericDao.delete(obj);
  }

  /**
   * 出荷明細構成品を削除します。
   * @param obj 削除対象のShippingDetailComposition
   */
  public void delete(ShippingDetailComposition obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して出荷明細構成品を削除します。
   * @param shippingNo 出荷番号
   * @param shippingDetailNo 出荷明細番号
   * @param compositionNo 構成品項番
   */
  public void delete(String shippingNo, Long shippingDetailNo, Long compositionNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo, compositionNo};
    final String query = "DELETE FROM SHIPPING_DETAIL_COMPOSITION"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?"
        + " AND COMPOSITION_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して出荷明細構成品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShippingDetailCompositionのリスト
   */
  public List<ShippingDetailComposition> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して出荷明細構成品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShippingDetailCompositionのリスト
   */
  public List<ShippingDetailComposition> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のShippingDetailCompositionのリスト
   */
  public List<ShippingDetailComposition> loadAll() {
    return genericDao.loadAll();
  }

}
