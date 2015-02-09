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
import jp.co.sint.webshop.data.dao.TmallPropertyDao;
import jp.co.sint.webshop.data.dto.TmallProperty;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl; 
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 受注ヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class TmallPropertyDaoImpl implements TmallPropertyDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallProperty, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public  TmallPropertyDaoImpl() {
    genericDao = new GenericDaoImpl<TmallProperty, Long>(TmallProperty.class);
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
   * 指定されたorm_rowidを持つ受注ヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するTmallOrderHeaderのインスタンス
   */
  public TmallProperty loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderのインスタンス
   */
  public TmallProperty load(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT * FROM Tmall_Property"
        + " WHERE property_id = ?";
    List<TmallProperty> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderの行が存在すればtrue
   */
  public boolean exists(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT COUNT(*) FROM Tmall_Property"
        + " WHERE property_id = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }
  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   * @param TMALL_ID 受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderの行が存在すればtrue
   */
  public boolean existsTid(String tid) {
    Object[] params = new Object[]{tid};
    final String query = "SELECT COUNT(*) FROM Tmall_Property"
        + " WHERE property_id = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }
  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallProperty obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallProperty obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注ヘッダを更新します。
   * @param obj 更新対象のTmallOrderHeader
   */
  public void update(TmallProperty obj) {
    genericDao.update(obj);
  }

  /**
   * 受注ヘッダを更新します。
   * @param obj 更新対象のTmallOrderHeader
   */
  public void update(TmallProperty obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注ヘッダを削除します。
   * @param obj 削除対象のTmallOrderHeader
   */
  public void delete(TmallProperty obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注ヘッダを削除します。
   * @param obj 削除対象のTmallOrderHeader
   */
  public void delete(TmallProperty obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   * @param orderNo 受注番号
   */
  public void delete(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "DELETE FROM Tmall_Property"
        + " WHERE property_id = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注ヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTmallOrderHeaderのリスト
   */
  public List<TmallProperty> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallOrderHeaderのリスト
   */
  public List<TmallProperty> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTmallOrderHeaderのリスト
   */
  public List<TmallProperty> loadAll() {
    return genericDao.loadAll();
  }


  @Override
  public List<TmallProperty> loadCProByCategoryId(String categoryId) {
    String query = "SELECT * FROM tmall_property WHERE IS_MUST=1 AND IS_SALE=0 AND CATEGORY_ID=? and parent_pid='0'";
    return findByQuery(query, categoryId);
  }

  @Override
  public List<TmallProperty> loadSkuProByCategoryId(String categoryId) {
    String query = "SELECT * FROM tmall_property WHERE  IS_SALE=1 AND CATEGORY_ID=? ";
    return findByQuery(query, categoryId);
  }

  @Override
  public TmallProperty load(String categoryId, String propertyId) {
    String query = "SELECT * FROM tmall_property WHERE  CATEGORY_ID=? and property_id=?";
    List<TmallProperty> list = findByQuery(query, categoryId,propertyId);
    return list.size()>0?list.get(0):null;
  }

}
