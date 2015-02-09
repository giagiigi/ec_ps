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
import jp.co.sint.webshop.data.dao.RfmDao;
import jp.co.sint.webshop.data.dto.Rfm;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * RFM
 *
 * @author System Integrator Corp.
 *
 */
public class RfmDaoImpl implements RfmDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Rfm, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RfmDaoImpl() {
    genericDao = new GenericDaoImpl<Rfm, Long>(Rfm.class);
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
   * 指定されたorm_rowidを持つRFMのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRfmのインスタンス
   */
  public Rfm loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してRFMのインスタンスを取得します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するRfmのインスタンス
   */
  public Rfm load(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT * FROM RFM"
        + " WHERE ORDER_NO = ?";
    List<Rfm> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してRFMが既に存在するかどうかを返します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するRfmの行が存在すればtrue
   */
  public boolean exists(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT COUNT(*) FROM RFM"
        + " WHERE ORDER_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Rfmをデータベースに追加します。
   * @param obj 追加対象のRfm
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Rfm obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Rfmをデータベースに追加します。
   * @param obj 追加対象のRfm
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Rfm obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * RFMを更新します。
   * @param obj 更新対象のRfm
   */
  public void update(Rfm obj) {
    genericDao.update(obj);
  }

  /**
   * RFMを更新します。
   * @param obj 更新対象のRfm
   */
  public void update(Rfm obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * RFMを削除します。
   * @param obj 削除対象のRfm
   */
  public void delete(Rfm obj) {
    genericDao.delete(obj);
  }

  /**
   * RFMを削除します。
   * @param obj 削除対象のRfm
   */
  public void delete(Rfm obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してRFMを削除します。
   * @param orderNo 受注番号
   */
  public void delete(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "DELETE FROM RFM"
        + " WHERE ORDER_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してRFMのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRfmのリスト
   */
  public List<Rfm> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してRFMのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRfmのリスト
   */
  public List<Rfm> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRfmのリスト
   */
  public List<Rfm> loadAll() {
    return genericDao.loadAll();
  }

}
