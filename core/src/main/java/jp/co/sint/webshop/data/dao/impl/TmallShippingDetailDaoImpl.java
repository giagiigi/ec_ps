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
import jp.co.sint.webshop.data.dao.TmallShippingDetailDao; 
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 出荷明細
 *
 * @author System Integrator Corp.
 *
 */
public class TmallShippingDetailDaoImpl implements TmallShippingDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallShippingDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TmallShippingDetailDaoImpl() {
    genericDao = new GenericDaoImpl<TmallShippingDetail, Long>(TmallShippingDetail.class);
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
   * @return idに対応するTmallShippingDetailのインスタンス
   */
  public TmallShippingDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して出荷明細のインスタンスを取得します。
   * @param shippingNo 出荷番号
   * @param TmallShippingDetailNo 出荷明細番号
   * @return 主キー列の値に対応するTmallShippingDetailのインスタンス
   */
  public TmallShippingDetail load(String shippingNo, Long TmallShippingDetailNo) {
    Object[] params = new Object[]{shippingNo, TmallShippingDetailNo};
    final String query = "SELECT * FROM TMALL_SHIPPING_DETAIL"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?";
    List<TmallShippingDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して出荷明細が既に存在するかどうかを返します。
   * @param shippingNo 出荷番号
   * @param TmallShippingDetailNo 出荷明細番号
   * @return 主キー列の値に対応するTmallShippingDetailの行が存在すればtrue
   */
  public boolean exists(String shippingNo, Long shippingDetailNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo};
    final String query = "SELECT COUNT(*) FROM TMALL_SHIPPING_DETAIL"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規TmallShippingDetailをデータベースに追加します。
   * @param obj 追加対象のTmallShippingDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallShippingDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallShippingDetailをデータベースに追加します。
   * @param obj 追加対象のTmallShippingDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallShippingDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 出荷明細を更新します。
   * @param obj 更新対象のTmallShippingDetail
   */
  public void update(TmallShippingDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 出荷明細を更新します。
   * @param obj 更新対象のTmallShippingDetail
   */
  public void update(TmallShippingDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 出荷明細を削除します。
   * @param obj 削除対象のTmallShippingDetail
   */
  public void delete(TmallShippingDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 出荷明細を削除します。
   * @param obj 削除対象のTmallShippingDetail
   */
  public void delete(TmallShippingDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して出荷明細を削除します。
   * @param shippingNo 出荷番号
   * @param TmallShippingDetailNo 出荷明細番号
   */
  public void delete(String shippingNo, Long shippingDetailNo) {
    Object[] params = new Object[]{shippingNo, shippingDetailNo};
    final String query = "DELETE FROM TMALL_SHIPPING_DETAIL"
        + " WHERE SHIPPING_NO = ?"
        + " AND SHIPPING_DETAIL_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して出荷明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTmallShippingDetailのリスト
   */
  public List<TmallShippingDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して出荷明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallShippingDetailのリスト
   */
  public List<TmallShippingDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTmallShippingDetailのリスト
   */
  public List<TmallShippingDetail> loadAll() {
    return genericDao.loadAll();
  }

}
