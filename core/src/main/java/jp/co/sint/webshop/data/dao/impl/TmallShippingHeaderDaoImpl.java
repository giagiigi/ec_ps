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
import jp.co.sint.webshop.data.dao.TmallShippingHeaderDao; 
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 出荷ヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class TmallShippingHeaderDaoImpl implements TmallShippingHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallShippingHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TmallShippingHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<TmallShippingHeader, Long>(TmallShippingHeader.class);
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
   * 指定されたorm_rowidを持つ出荷ヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するTmallShippingHeaderのインスタンス
   */
  public TmallShippingHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して出荷ヘッダのインスタンスを取得します。
   * @param shippingNo 出荷番号
   * @return 主キー列の値に対応するTmallShippingHeaderのインスタンス
   */
  public TmallShippingHeader load(String shippingNo) {
    Object[] params = new Object[]{shippingNo};
    final String query = "SELECT * FROM TMALL_SHIPPING_HEADER"
        + " WHERE SHIPPING_NO = ?";
    List<TmallShippingHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  /**
   * 主キー列の値を指定して出荷ヘッダのインスタンスを取得します。
   * @param shippingNo 出荷番号
   * @return 主キー列の値に対応するTmallShippingHeaderのインスタンス
   */
  public TmallShippingHeader loadByOrderNo(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT * FROM TMALL_SHIPPING_HEADER"
        + " WHERE ORDER_NO = ?";
    List<TmallShippingHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  /**
   * 主キー列の値を指定して出荷ヘッダが既に存在するかどうかを返します。
   * @param shippingNo 出荷番号
   * @return 主キー列の値に対応するTmallShippingHeaderの行が存在すればtrue
   */
  public boolean exists(String shippingNo) {
    Object[] params = new Object[]{shippingNo};
    final String query = "SELECT COUNT(*) FROM TMALL_SHIPPING_HEADER"
        + " WHERE SHIPPING_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規TmallShippingHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallShippingHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallShippingHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallShippingHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallShippingHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallShippingHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 出荷ヘッダを更新します。
   * @param obj 更新対象のTmallShippingHeader
   */
  public void update(TmallShippingHeader obj) {
    genericDao.update(obj);
  }

  /**
   * 出荷ヘッダを更新します。
   * @param obj 更新対象のTmallShippingHeader
   */
  public void update(TmallShippingHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 出荷ヘッダを削除します。
   * @param obj 削除対象のTmallShippingHeader
   */
  public void delete(TmallShippingHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * 出荷ヘッダを削除します。
   * @param obj 削除対象のTmallShippingHeader
   */
  public void delete(TmallShippingHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して出荷ヘッダを削除します。
   * @param shippingNo 出荷番号
   */
  public void delete(String shippingNo) {
    Object[] params = new Object[]{shippingNo};
    final String query = "DELETE FROM TMALL_SHIPPING_HEADER"
        + " WHERE SHIPPING_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して出荷ヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTmallShippingHeaderのリスト
   */
  public List<TmallShippingHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して出荷ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallShippingHeaderのリスト
   */
  public List<TmallShippingHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTmallShippingHeaderのリスト
   */
  public List<TmallShippingHeader> loadAll() {
    return genericDao.loadAll();
  }

}
